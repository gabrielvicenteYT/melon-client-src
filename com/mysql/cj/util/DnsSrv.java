package com.mysql.cj.util;

import javax.naming.directory.*;
import javax.naming.*;
import java.util.stream.*;
import java.util.function.*;
import java.util.*;

public class DnsSrv
{
    public static List<SrvRecord> lookupSrvRecords(final String serviceName) throws NamingException {
        final List<SrvRecord> srvRecords = new ArrayList<SrvRecord>();
        final Properties environment = new Properties();
        ((Hashtable<String, String>)environment).put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        final DirContext context = new InitialDirContext(environment);
        final Attributes dnsEntries = context.getAttributes(serviceName, new String[] { "SRV" });
        if (dnsEntries != null) {
            final Attribute hosts = dnsEntries.get("SRV");
            if (hosts != null) {
                for (int i = 0; i < hosts.size(); ++i) {
                    srvRecords.add(SrvRecord.buildFrom((String)hosts.get(i)));
                }
            }
        }
        return sortSrvRecords(srvRecords);
    }
    
    public static List<SrvRecord> sortSrvRecords(final List<SrvRecord> srvRecords) {
        final List<SrvRecord> srvRecordsSortedNatural = srvRecords.stream().sorted().collect((Collector<? super Object, ?, List<SrvRecord>>)Collectors.toList());
        final Random random = new Random(System.nanoTime());
        final List<SrvRecord> srvRecordsSortedRfc2782 = new ArrayList<SrvRecord>();
        final List<Integer> priorities = srvRecordsSortedNatural.stream().map((Function<? super Object, ?>)SrvRecord::getPriority).distinct().collect((Collector<? super Object, ?, List<Integer>>)Collectors.toList());
        for (final Integer priority : priorities) {
            final List<SrvRecord> srvRecordsSamePriority = srvRecordsSortedNatural.stream().filter(s -> s.getPriority() == priority).collect((Collector<? super Object, ?, List<SrvRecord>>)Collectors.toList());
            while (srvRecordsSamePriority.size() > 1) {
                final int recCount = srvRecordsSamePriority.size();
                int sumOfWeights = 0;
                final int[] weights = new int[recCount];
                for (int i = 0; i < recCount; ++i) {
                    sumOfWeights += srvRecordsSamePriority.get(i).getWeight();
                    weights[i] = sumOfWeights;
                }
                int selection;
                int pos;
                for (selection = random.nextInt(sumOfWeights + 1), pos = 0; pos < recCount && weights[pos] < selection; ++pos) {}
                srvRecordsSortedRfc2782.add(srvRecordsSamePriority.remove(pos));
            }
            srvRecordsSortedRfc2782.add(srvRecordsSamePriority.get(0));
        }
        return srvRecordsSortedRfc2782;
    }
    
    public static class SrvRecord implements Comparable<SrvRecord>
    {
        private final int priority;
        private final int weight;
        private final int port;
        private final String target;
        
        public static SrvRecord buildFrom(final String srvLine) {
            final String[] srvParts = srvLine.split("\\s+");
            if (srvParts.length == 4) {
                final int priority = Integer.parseInt(srvParts[0]);
                final int weight = Integer.parseInt(srvParts[1]);
                final int port = Integer.parseInt(srvParts[2]);
                final String target = srvParts[3].replaceFirst("\\.$", "");
                return new SrvRecord(priority, weight, port, target);
            }
            return null;
        }
        
        public SrvRecord(final int priority, final int weight, final int port, final String target) {
            this.priority = priority;
            this.weight = weight;
            this.port = port;
            this.target = target;
        }
        
        public int getPriority() {
            return this.priority;
        }
        
        public int getWeight() {
            return this.weight;
        }
        
        public int getPort() {
            return this.port;
        }
        
        public String getTarget() {
            return this.target;
        }
        
        @Override
        public String toString() {
            return String.format("{\"Priority\": %d, \"Weight\": %d, \"Port\": %d, \"Target\": \"%s\"}", this.getPriority(), this.getWeight(), this.getPort(), this.getTarget());
        }
        
        @Override
        public int compareTo(final SrvRecord o) {
            final int priorityDiff = this.getPriority() - o.getPriority();
            return (priorityDiff == 0) ? (this.getWeight() - o.getWeight()) : priorityDiff;
        }
    }
}
