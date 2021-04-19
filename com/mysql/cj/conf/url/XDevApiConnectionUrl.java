package com.mysql.cj.conf.url;

import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import com.mysql.cj.conf.*;
import java.util.function.*;
import java.util.*;
import java.util.stream.*;

public class XDevApiConnectionUrl extends ConnectionUrl
{
    private static final int DEFAULT_PORT = 33060;
    private boolean prioritySorted;
    private boolean hasDuplicatedPriorities;
    
    public XDevApiConnectionUrl(final ConnectionUrlParser connStrParser, final Properties info) {
        super(connStrParser, info);
        this.prioritySorted = false;
        this.hasDuplicatedPriorities = false;
        this.type = Type.XDEVAPI_SESSION;
        boolean first = true;
        String user = null;
        String password = null;
        boolean hasPriority = false;
        final Set<Integer> priorities = new HashSet<Integer>();
        for (final HostInfo hi2 : this.hosts) {
            if (first) {
                first = false;
                user = hi2.getUser();
                password = hi2.getPassword();
                hasPriority = hi2.getHostProperties().containsKey(PropertyKey.PRIORITY.getKeyName());
            }
            else {
                if (!user.equals(hi2.getUser()) || !password.equals(hi2.getPassword())) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.14", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
                if (hasPriority ^ hi2.getHostProperties().containsKey(PropertyKey.PRIORITY.getKeyName())) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.15", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
            }
            if (hasPriority) {
                try {
                    final int priority = Integer.parseInt(hi2.getProperty(PropertyKey.PRIORITY.getKeyName()));
                    if (priority < 0 || priority > 100) {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                    }
                    if (priorities.contains(priority)) {
                        this.hasDuplicatedPriorities = true;
                    }
                    else {
                        priorities.add(priority);
                    }
                }
                catch (NumberFormatException e) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ConnectionString.16", new Object[] { Type.XDEVAPI_SESSION.getScheme() }));
                }
            }
        }
        if (hasPriority) {
            this.prioritySorted = true;
            this.hosts.sort(Comparator.comparing(hi -> Integer.parseInt(hi.getHostProperties().get(PropertyKey.PRIORITY.getKeyName()))).reversed());
        }
    }
    
    @Override
    protected void preprocessPerTypeHostProperties(final Map<String, String> hostProps) {
        if (hostProps.containsKey(PropertyKey.ADDRESS.getKeyName())) {
            final String address = hostProps.get(PropertyKey.ADDRESS.getKeyName());
            final ConnectionUrlParser.Pair<String, Integer> hostPortPair = ConnectionUrlParser.parseHostPortPair(address);
            final String host = StringUtils.safeTrim(hostPortPair.left);
            final Integer port = hostPortPair.right;
            if (!StringUtils.isNullOrEmpty(host) && !hostProps.containsKey(PropertyKey.HOST.getKeyName())) {
                hostProps.put(PropertyKey.HOST.getKeyName(), host);
            }
            if (port != -1 && !hostProps.containsKey(PropertyKey.PORT.getKeyName())) {
                hostProps.put(PropertyKey.PORT.getKeyName(), port.toString());
            }
        }
    }
    
    @Override
    public int getDefaultPort() {
        return 33060;
    }
    
    @Override
    protected void fixProtocolDependencies(final Map<String, String> hostProps) {
    }
    
    @Override
    public List<HostInfo> getHostsList(final HostsListView view) {
        if (this.prioritySorted) {
            if (this.hasDuplicatedPriorities) {
                final Map<Integer, List<HostInfo>> hostsByPriority = this.hosts.stream().collect((Collector<? super Object, ?, Map<Integer, List<HostInfo>>>)Collectors.groupingBy(hi -> Integer.valueOf(hi.getHostProperties().get(PropertyKey.PRIORITY.getKeyName()))));
                this.hosts = hostsByPriority.entrySet().stream().sorted(Comparator.comparing((Function<? super Object, ? extends Comparable>)Map.Entry::getKey).reversed()).map((Function<? super Object, ?>)Map.Entry::getValue).peek((Consumer<? super Object>)Collections::shuffle).flatMap((Function<? super Object, ? extends Stream<?>>)Collection::stream).collect((Collector<? super Object, ?, List<HostInfo>>)Collectors.toList());
            }
        }
        else {
            Collections.shuffle(this.hosts);
        }
        return super.getHostsList(view);
    }
}
