package com.mysql.cj.xdevapi;

import com.mysql.cj.util.*;
import java.util.stream.*;
import com.mysql.cj.*;
import com.mysql.cj.exceptions.*;
import java.util.concurrent.*;
import com.mysql.cj.protocol.x.*;
import com.mysql.cj.conf.*;
import java.lang.ref.*;
import java.util.*;
import java.util.function.*;
import java.io.*;

public class ClientImpl implements Client
{
    boolean isClosed;
    private ConnectionUrl connUrl;
    private boolean poolingEnabled;
    private int maxSize;
    int maxIdleTime;
    private int queueTimeout;
    private int demotedTimeout;
    Map<HostInfo, Long> demotedHosts;
    BlockingQueue<PooledXProtocol> idleProtocols;
    Set<WeakReference<PooledXProtocol>> activeProtocols;
    Set<WeakReference<Session>> nonPooledSessions;
    SessionFactory sessionFactory;
    
    public ClientImpl(final String url, final String clientPropsJson) {
        this.isClosed = false;
        this.connUrl = null;
        this.poolingEnabled = true;
        this.maxSize = 25;
        this.maxIdleTime = 0;
        this.queueTimeout = 0;
        this.demotedTimeout = 120000;
        this.demotedHosts = null;
        this.idleProtocols = null;
        this.activeProtocols = null;
        this.nonPooledSessions = null;
        this.sessionFactory = new SessionFactory();
        final Properties clientProps = StringUtils.isNullOrEmpty(clientPropsJson) ? new Properties() : this.clientPropsFromJson(clientPropsJson);
        this.init(url, clientProps);
    }
    
    public ClientImpl(final String url, final Properties clientProps) {
        this.isClosed = false;
        this.connUrl = null;
        this.poolingEnabled = true;
        this.maxSize = 25;
        this.maxIdleTime = 0;
        this.queueTimeout = 0;
        this.demotedTimeout = 120000;
        this.demotedHosts = null;
        this.idleProtocols = null;
        this.activeProtocols = null;
        this.nonPooledSessions = null;
        this.sessionFactory = new SessionFactory();
        this.init(url, (clientProps != null) ? clientProps : new Properties());
    }
    
    private Properties clientPropsFromJson(final String clientPropsJson) {
        final Properties props = new Properties();
        final DbDoc clientPropsDoc = JsonParser.parseDoc(clientPropsJson);
        final JsonValue pooling = ((Map<K, JsonValue>)clientPropsDoc).remove("pooling");
        if (pooling != null) {
            if (!DbDoc.class.isAssignableFrom(pooling.getClass())) {
                throw new XDevAPIError(String.format("Client option 'pooling' does not support value '%s'.", pooling.toFormattedString()));
            }
            final DbDoc poolingDoc = (DbDoc)pooling;
            JsonValue jsonVal = ((Map<K, JsonValue>)poolingDoc).remove("enabled");
            if (jsonVal != null) {
                if (JsonLiteral.class.isAssignableFrom(jsonVal.getClass())) {
                    final JsonLiteral pe = (JsonLiteral)jsonVal;
                    if (pe != JsonLiteral.FALSE && pe != JsonLiteral.TRUE) {
                        throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_ENABLED.getKeyName(), jsonVal.toFormattedString()));
                    }
                    props.setProperty(ClientProperty.POOLING_ENABLED.getKeyName(), pe.value);
                }
                else {
                    if (JsonString.class.isAssignableFrom(jsonVal.getClass())) {
                        throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_ENABLED.getKeyName(), ((JsonString)jsonVal).getString()));
                    }
                    throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_ENABLED.getKeyName(), jsonVal.toFormattedString()));
                }
            }
            jsonVal = ((Map<K, JsonValue>)poolingDoc).remove("maxSize");
            if (jsonVal != null) {
                if (JsonNumber.class.isAssignableFrom(jsonVal.getClass())) {
                    props.setProperty(ClientProperty.POOLING_MAX_SIZE.getKeyName(), ((JsonNumber)jsonVal).toString());
                }
                else {
                    if (JsonString.class.isAssignableFrom(jsonVal.getClass())) {
                        throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_MAX_SIZE.getKeyName(), ((JsonString)jsonVal).getString()));
                    }
                    throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_MAX_SIZE.getKeyName(), jsonVal.toFormattedString()));
                }
            }
            jsonVal = ((Map<K, JsonValue>)poolingDoc).remove("maxIdleTime");
            if (jsonVal != null) {
                if (JsonNumber.class.isAssignableFrom(jsonVal.getClass())) {
                    props.setProperty(ClientProperty.POOLING_MAX_IDLE_TIME.getKeyName(), ((JsonNumber)jsonVal).toString());
                }
                else {
                    if (JsonString.class.isAssignableFrom(jsonVal.getClass())) {
                        throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_MAX_IDLE_TIME.getKeyName(), ((JsonString)jsonVal).getString()));
                    }
                    throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_MAX_IDLE_TIME.getKeyName(), jsonVal.toFormattedString()));
                }
            }
            jsonVal = ((Map<K, JsonValue>)poolingDoc).remove("queueTimeout");
            if (jsonVal != null) {
                if (JsonNumber.class.isAssignableFrom(jsonVal.getClass())) {
                    props.setProperty(ClientProperty.POOLING_QUEUE_TIMEOUT.getKeyName(), ((JsonNumber)jsonVal).toString());
                }
                else {
                    if (JsonString.class.isAssignableFrom(jsonVal.getClass())) {
                        throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_QUEUE_TIMEOUT.getKeyName(), ((JsonString)jsonVal).getString()));
                    }
                    throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", ClientProperty.POOLING_QUEUE_TIMEOUT.getKeyName(), jsonVal.toFormattedString()));
                }
            }
            if (poolingDoc.size() > 0) {
                final String key = poolingDoc.keySet().stream().findFirst().get();
                throw new XDevAPIError(String.format("Client option 'pooling.%s' is not recognized as valid.", key));
            }
        }
        if (!clientPropsDoc.isEmpty()) {
            final String key2 = clientPropsDoc.keySet().stream().findFirst().get();
            throw new XDevAPIError(String.format("Client option '%s' is not recognized as valid.", key2));
        }
        return props;
    }
    
    private void validateAndInitializeClientProps(final Properties clientProps) {
        String propKey = "";
        String propValue = "";
        propKey = ClientProperty.POOLING_ENABLED.getKeyName();
        if (clientProps.containsKey(propKey)) {
            propValue = clientProps.getProperty(propKey);
            try {
                this.poolingEnabled = BooleanPropertyDefinition.booleanFrom(propKey, propValue, null);
            }
            catch (CJException e) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue), e);
            }
        }
        propKey = ClientProperty.POOLING_MAX_SIZE.getKeyName();
        if (clientProps.containsKey(propKey)) {
            propValue = clientProps.getProperty(propKey);
            try {
                this.maxSize = IntegerPropertyDefinition.integerFrom(propKey, propValue, 1, null);
            }
            catch (WrongArgumentException e2) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue), e2);
            }
            if (this.maxSize <= 0) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue));
            }
        }
        propKey = ClientProperty.POOLING_MAX_IDLE_TIME.getKeyName();
        if (clientProps.containsKey(propKey)) {
            propValue = clientProps.getProperty(propKey);
            try {
                this.maxIdleTime = IntegerPropertyDefinition.integerFrom(propKey, propValue, 1, null);
            }
            catch (WrongArgumentException e2) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue), e2);
            }
            if (this.maxIdleTime < 0) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue));
            }
        }
        propKey = ClientProperty.POOLING_QUEUE_TIMEOUT.getKeyName();
        if (clientProps.containsKey(propKey)) {
            propValue = clientProps.getProperty(propKey);
            try {
                this.queueTimeout = IntegerPropertyDefinition.integerFrom(propKey, propValue, 1, null);
            }
            catch (WrongArgumentException e2) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue), e2);
            }
            if (this.queueTimeout < 0) {
                throw new XDevAPIError(String.format("Client option '%s' does not support value '%s'.", propKey, propValue));
            }
        }
        final List<String> clientPropsAsString = Stream.of(ClientProperty.values()).map((Function<? super ClientProperty, ?>)ClientProperty::getKeyName).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        propKey = clientProps.keySet().stream().filter(k -> !clientPropsAsString.contains(k)).findFirst().orElse(null);
        if (propKey != null) {
            throw new XDevAPIError(String.format("Client option '%s' is not recognized as valid.", propKey));
        }
    }
    
    private void init(final String url, final Properties clientProps) {
        this.connUrl = this.sessionFactory.parseUrl(url);
        this.validateAndInitializeClientProps(clientProps);
        if (this.poolingEnabled) {
            this.demotedHosts = new HashMap<HostInfo, Long>();
            this.idleProtocols = new LinkedBlockingQueue<PooledXProtocol>(this.maxSize);
            this.activeProtocols = new HashSet<WeakReference<PooledXProtocol>>(this.maxSize);
        }
        else {
            this.nonPooledSessions = new HashSet<WeakReference<Session>>();
        }
    }
    
    @Override
    public Session getSession() {
        if (this.isClosed) {
            throw new XDevAPIError("Client is closed.");
        }
        if (!this.poolingEnabled) {
            final List<WeakReference<Session>> obsoletedSessions = new ArrayList<WeakReference<Session>>();
            for (final WeakReference<Session> ws : this.nonPooledSessions) {
                if (ws != null) {
                    final Session s = ws.get();
                    if (s != null && s.isOpen()) {
                        continue;
                    }
                    obsoletedSessions.add(ws);
                }
            }
            for (final WeakReference<Session> ws : obsoletedSessions) {
                this.nonPooledSessions.remove(ws);
            }
            final Session sess = this.sessionFactory.getSession(this.connUrl);
            this.nonPooledSessions.add(new WeakReference<Session>(sess));
            return sess;
        }
        PooledXProtocol prot = null;
        final List<HostInfo> hostsList = this.connUrl.getHostsList();
        synchronized (this.idleProtocols) {
            final List<PooledXProtocol> toCloseAndRemove = this.idleProtocols.stream().filter(p -> !p.isHostInfoValid(hostsList)).collect((Collector<? super Object, ?, List<PooledXProtocol>>)Collectors.toList());
            toCloseAndRemove.stream().peek(PooledXProtocol::realClose).peek(this.idleProtocols::remove).map((Function<? super Object, ?>)PooledXProtocol::getHostInfo).sequential().forEach(this.demotedHosts::remove);
        }
        final long start = System.currentTimeMillis();
        while (prot == null && (this.queueTimeout == 0 || System.currentTimeMillis() < start + this.queueTimeout)) {
            synchronized (this.idleProtocols) {
                if (this.idleProtocols.peek() != null) {
                    final PooledXProtocol tryProt = this.idleProtocols.poll();
                    if (!tryProt.isOpen()) {
                        continue;
                    }
                    if (tryProt.isIdleTimeoutReached()) {
                        tryProt.realClose();
                    }
                    else {
                        try {
                            tryProt.reset();
                            prot = tryProt;
                        }
                        catch (CJCommunicationsException ex) {}
                        catch (XProtocolError xProtocolError) {}
                    }
                }
                else if (this.idleProtocols.size() + this.activeProtocols.size() < this.maxSize) {
                    CJException latestException = null;
                    final List<HostInfo> hostsToRevisit = new ArrayList<HostInfo>();
                    for (final HostInfo hi : hostsList) {
                        if (this.demotedHosts.containsKey(hi)) {
                            if (start - this.demotedHosts.get(hi) <= this.demotedTimeout) {
                                hostsToRevisit.add(hi);
                                continue;
                            }
                            this.demotedHosts.remove(hi);
                        }
                        try {
                            prot = this.newPooledXProtocol(hi);
                        }
                        catch (CJCommunicationsException e) {
                            if (e.getCause() == null) {
                                throw e;
                            }
                            latestException = e;
                            this.demotedHosts.put(hi, System.currentTimeMillis());
                            continue;
                        }
                        break;
                    }
                    if (prot == null) {
                        for (final HostInfo hi : hostsToRevisit) {
                            try {
                                prot = this.newPooledXProtocol(hi);
                                this.demotedHosts.remove(hi);
                            }
                            catch (CJCommunicationsException e) {
                                if (e.getCause() == null) {
                                    throw e;
                                }
                                latestException = e;
                                this.demotedHosts.put(hi, System.currentTimeMillis());
                                continue;
                            }
                            break;
                        }
                    }
                    if (prot == null && latestException != null) {
                        throw ExceptionFactory.createException(CJCommunicationsException.class, Messages.getString("Session.Create.Failover.0"), latestException);
                    }
                    continue;
                }
                else if (this.queueTimeout > 0) {
                    final long currentTimeout = this.queueTimeout - (System.currentTimeMillis() - start);
                    try {
                        if (currentTimeout <= 0L) {
                            continue;
                        }
                        prot = this.idleProtocols.poll(currentTimeout, TimeUnit.MILLISECONDS);
                    }
                    catch (InterruptedException e2) {
                        throw new XDevAPIError("Session can not be obtained within " + this.queueTimeout + " milliseconds.", e2);
                    }
                }
                else {
                    prot = this.idleProtocols.poll();
                }
            }
        }
        if (prot == null) {
            throw new XDevAPIError("Session can not be obtained within " + this.queueTimeout + " milliseconds.");
        }
        this.activeProtocols.add(new WeakReference<PooledXProtocol>(prot));
        final SessionImpl sess2 = new SessionImpl(prot);
        return sess2;
    }
    
    private PooledXProtocol newPooledXProtocol(final HostInfo hi) {
        final PropertySet pset = new DefaultPropertySet();
        pset.initializeProperties(hi.exposeAsProperties());
        final PooledXProtocol tryProt = new PooledXProtocol(hi, pset);
        tryProt.connect(hi.getUser(), hi.getPassword(), hi.getDatabase());
        return tryProt;
    }
    
    @Override
    public void close() {
        if (this.poolingEnabled) {
            synchronized (this.idleProtocols) {
                if (!this.isClosed) {
                    this.isClosed = true;
                    this.idleProtocols.forEach(s -> s.realClose());
                    this.idleProtocols.clear();
                    this.activeProtocols.stream().map((Function<? super Object, ?>)Reference::get).filter(Objects::nonNull).forEach(s -> s.realClose());
                    this.activeProtocols.clear();
                }
            }
        }
        else {
            this.nonPooledSessions.stream().map((Function<? super Object, ?>)Reference::get).filter(Objects::nonNull).filter(Session::isOpen).forEach(s -> s.close());
        }
    }
    
    void idleProtocol(final PooledXProtocol sess) {
        synchronized (this.idleProtocols) {
            if (!this.isClosed) {
                final List<WeakReference<PooledXProtocol>> removeThem = new ArrayList<WeakReference<PooledXProtocol>>();
                for (final WeakReference<PooledXProtocol> wps : this.activeProtocols) {
                    if (wps != null) {
                        final PooledXProtocol as = wps.get();
                        if (as == null) {
                            removeThem.add(wps);
                        }
                        else {
                            if (as != sess) {
                                continue;
                            }
                            removeThem.add(wps);
                            this.idleProtocols.add(as);
                        }
                    }
                }
                for (final WeakReference<PooledXProtocol> wr : removeThem) {
                    this.activeProtocols.remove(wr);
                }
            }
        }
    }
    
    public class PooledXProtocol extends XProtocol
    {
        long idleSince;
        HostInfo hostInfo;
        
        public PooledXProtocol(final HostInfo hostInfo, final PropertySet propertySet) {
            super(hostInfo, propertySet);
            this.idleSince = -1L;
            this.hostInfo = null;
            this.hostInfo = hostInfo;
        }
        
        @Override
        public void close() {
            this.reset();
            this.idleSince = System.currentTimeMillis();
            ClientImpl.this.idleProtocol(this);
        }
        
        public HostInfo getHostInfo() {
            return this.hostInfo;
        }
        
        boolean isIdleTimeoutReached() {
            return ClientImpl.this.maxIdleTime > 0 && this.idleSince > 0L && System.currentTimeMillis() > this.idleSince + ClientImpl.this.maxIdleTime;
        }
        
        boolean isHostInfoValid(final List<HostInfo> hostsList) {
            return hostsList.stream().filter(h -> h.equalHostPortPair(this.hostInfo)).findFirst().isPresent();
        }
        
        void realClose() {
            try {
                super.close();
            }
            catch (IOException ex) {}
        }
    }
}
