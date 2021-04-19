package com.mysql.cj.protocol.x;

import com.mysql.cj.protocol.*;
import com.mysql.cj.x.protobuf.*;
import com.mysql.cj.xdevapi.*;
import java.util.stream.*;
import java.util.*;
import com.mysql.cj.*;

public class XServerCapabilities implements ServerCapabilities
{
    private Map<String, MysqlxDatatypes.Any> capabilities;
    static String KEY_COMPRESSION;
    static String KEY_SESSION_CONNECT_ATTRS;
    static String KEY_TLS;
    static String KEY_NODE_TYPE;
    static String KEY_CLIENT_PWD_EXPIRE_OK;
    static String KEY_AUTHENTICATION_MECHANISMS;
    static String KEY_DOC_FORMATS;
    static String SUBKEY_COMPRESSION_ALGORITHM;
    static String SUBKEY_COMPRESSION_SERVER_COMBINE_MIXED_MESSAGES;
    static String SUBKEY_COMPRESSION_SERVER_MAX_COMBINE_MESSAGES;
    
    public XServerCapabilities(final Map<String, MysqlxDatatypes.Any> capabilities) {
        this.capabilities = capabilities;
    }
    
    public void setCapability(final String name, final Object value) {
        if (!XServerCapabilities.KEY_SESSION_CONNECT_ATTRS.equals(name) && !XServerCapabilities.KEY_COMPRESSION.equals(name)) {
            this.capabilities.put(name, ExprUtil.argObjectToScalarAny(value));
        }
    }
    
    public boolean hasCapability(final String name) {
        return this.capabilities.containsKey(name);
    }
    
    public String getNodeType() {
        return this.capabilities.get(XServerCapabilities.KEY_NODE_TYPE).getScalar().getVString().getValue().toStringUtf8();
    }
    
    public boolean getTls() {
        return this.hasCapability(XServerCapabilities.KEY_TLS) && this.capabilities.get(XServerCapabilities.KEY_TLS).getScalar().getVBool();
    }
    
    public boolean getClientPwdExpireOk() {
        return this.capabilities.get(XServerCapabilities.KEY_CLIENT_PWD_EXPIRE_OK).getScalar().getVBool();
    }
    
    public List<String> getAuthenticationMechanisms() {
        return this.capabilities.get(XServerCapabilities.KEY_AUTHENTICATION_MECHANISMS).getArray().getValueList().stream().map(v -> v.getScalar().getVString().getValue().toStringUtf8()).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
    }
    
    public String getDocFormats() {
        return this.capabilities.get(XServerCapabilities.KEY_DOC_FORMATS).getScalar().getVString().getValue().toStringUtf8();
    }
    
    public Map<String, List<String>> getCompression() {
        if (this.hasCapability(XServerCapabilities.KEY_COMPRESSION)) {
            return this.capabilities.get(XServerCapabilities.KEY_COMPRESSION).getObj().getFldList().stream().collect(Collectors.toMap(f -> f.getKey().toLowerCase(), f -> f.getValue().getArray().getValueList().stream().map(v -> v.getScalar().getVString().getValue().toStringUtf8().toLowerCase()).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())));
        }
        return Collections.emptyMap();
    }
    
    @Override
    public int getCapabilityFlags() {
        return 0;
    }
    
    @Override
    public void setCapabilityFlags(final int capabilityFlags) {
    }
    
    @Override
    public ServerVersion getServerVersion() {
        return null;
    }
    
    @Override
    public void setServerVersion(final ServerVersion serverVersion) {
    }
    
    @Override
    public boolean serverSupportsFracSecs() {
        return true;
    }
    
    static {
        XServerCapabilities.KEY_COMPRESSION = "compression";
        XServerCapabilities.KEY_SESSION_CONNECT_ATTRS = "session_connect_attrs";
        XServerCapabilities.KEY_TLS = "tls";
        XServerCapabilities.KEY_NODE_TYPE = "node_type";
        XServerCapabilities.KEY_CLIENT_PWD_EXPIRE_OK = "client.pwd_expire_ok";
        XServerCapabilities.KEY_AUTHENTICATION_MECHANISMS = "authentication.mechanisms";
        XServerCapabilities.KEY_DOC_FORMATS = "doc.formats";
        XServerCapabilities.SUBKEY_COMPRESSION_ALGORITHM = "algorithm";
        XServerCapabilities.SUBKEY_COMPRESSION_SERVER_COMBINE_MIXED_MESSAGES = "server_combine_mixed_messages";
        XServerCapabilities.SUBKEY_COMPRESSION_SERVER_MAX_COMBINE_MESSAGES = "server_max_combine_messages";
    }
}
