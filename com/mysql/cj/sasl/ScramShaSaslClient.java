package com.mysql.cj.sasl;

import javax.security.sasl.*;
import com.mysql.cj.util.*;
import java.security.*;
import java.util.*;

public abstract class ScramShaSaslClient implements SaslClient
{
    protected static final int MINIMUM_ITERATIONS = 4096;
    protected static final String GS2_CBIND_FLAG = "n";
    protected static final byte[] CLIENT_KEY;
    protected static final byte[] SERVER_KEY;
    protected String authorizationId;
    protected String authenticationId;
    protected String password;
    protected ScramExchangeStage scramStage;
    protected String cNonce;
    protected String gs2Header;
    protected String clientFirstMessageBare;
    protected byte[] serverSignature;
    
    public ScramShaSaslClient(final String authorizationId, final String authenticationId, final String password) throws SaslException {
        this.scramStage = ScramExchangeStage.CLIENT_FIRST;
        this.authorizationId = (StringUtils.isNullOrEmpty(authorizationId) ? "" : authorizationId);
        this.authenticationId = (StringUtils.isNullOrEmpty(authenticationId) ? this.authorizationId : authenticationId);
        if (StringUtils.isNullOrEmpty(this.authenticationId)) {
            throw new SaslException("The authenticationId cannot be null or empty.");
        }
        this.password = (StringUtils.isNullOrEmpty(password) ? "" : password);
        this.scramStage = ScramExchangeStage.CLIENT_FIRST;
    }
    
    abstract String getIanaMechanismName();
    
    @Override
    public boolean hasInitialResponse() {
        return true;
    }
    
    @Override
    public byte[] evaluateChallenge(final byte[] challenge) throws SaslException {
        try {
            switch (this.scramStage) {
                case CLIENT_FIRST: {
                    this.gs2Header = "n," + (StringUtils.isNullOrEmpty(this.authorizationId) ? "" : ("a=" + this.prepUserName(this.authorizationId))) + ",";
                    this.cNonce = this.generateRandomPrintableAsciiString(32);
                    this.clientFirstMessageBare = "n=" + this.prepUserName(this.authenticationId) + ",r=" + this.cNonce;
                    final String clientFirstMessage = this.gs2Header + this.clientFirstMessageBare;
                    return StringUtils.getBytes(clientFirstMessage, "UTF-8");
                }
                case SERVER_FIRST_CLIENT_FINAL: {
                    final String serverFirstMessage = StringUtils.toString(challenge, "UTF-8");
                    final Map<String, String> serverFirstAttributes = this.parseChallenge(serverFirstMessage);
                    if (!serverFirstAttributes.containsKey("r") || !serverFirstAttributes.containsKey("s") || !serverFirstAttributes.containsKey("i")) {
                        throw new SaslException("Missing required SCRAM attribute from server first message.");
                    }
                    final String sNonce = serverFirstAttributes.get("r");
                    if (!sNonce.startsWith(this.cNonce)) {
                        throw new SaslException("Invalid server nonce for " + this.getIanaMechanismName() + " authentication.");
                    }
                    final byte[] salt = Base64.getDecoder().decode(serverFirstAttributes.get("s"));
                    final int iterations = Integer.parseInt(serverFirstAttributes.get("i"));
                    if (iterations < 4096) {
                        throw new SaslException("Announced " + this.getIanaMechanismName() + " iteration count is too low.");
                    }
                    final String clientFinalMessageWithoutProof = "c=" + Base64.getEncoder().encodeToString(StringUtils.getBytes(this.gs2Header, "UTF-8")) + ",r=" + sNonce;
                    final byte[] saltedPassword = this.hi(SaslPrep.prepare(this.password, SaslPrep.StringType.STORED), salt, iterations);
                    final byte[] clientKey = this.hmac(saltedPassword, ScramShaSaslClient.CLIENT_KEY);
                    final byte[] storedKey = this.h(clientKey);
                    final String authMessage = this.clientFirstMessageBare + "," + serverFirstMessage + "," + clientFinalMessageWithoutProof;
                    final byte[] clientSignature = this.hmac(storedKey, StringUtils.getBytes(authMessage, "UTF-8"));
                    final byte[] clientProof = clientKey.clone();
                    this.xorInPlace(clientProof, clientSignature);
                    final String clientFinalMessage = clientFinalMessageWithoutProof + ",p=" + Base64.getEncoder().encodeToString(clientProof);
                    final byte[] serverKey = this.hmac(saltedPassword, ScramShaSaslClient.SERVER_KEY);
                    this.serverSignature = this.hmac(serverKey, StringUtils.getBytes(authMessage, "UTF-8"));
                    return StringUtils.getBytes(clientFinalMessage, "UTF-8");
                }
                case SERVER_FINAL: {
                    final String serverFinalMessage = StringUtils.toString(challenge, "UTF-8");
                    final Map<String, String> serverFinalAttributes = this.parseChallenge(serverFinalMessage);
                    if (serverFinalAttributes.containsKey("e")) {
                        throw new SaslException("Authentication failed due to server error '" + serverFinalAttributes.get("e") + "'.");
                    }
                    if (!serverFinalAttributes.containsKey("v")) {
                        throw new SaslException("Missing required SCRAM attribute from server final message.");
                    }
                    final byte[] verifier = Base64.getDecoder().decode(serverFinalAttributes.get("v"));
                    if (!MessageDigest.isEqual(this.serverSignature, verifier)) {
                        throw new SaslException(this.getIanaMechanismName() + " server signature could not be verified.");
                    }
                    return null;
                }
                default: {
                    throw new SaslException("Unexpected SCRAM authentication message.");
                }
            }
        }
        catch (Throwable e) {
            this.scramStage = ScramExchangeStage.TERMINATED;
            throw e;
        }
        finally {
            this.scramStage = this.scramStage.getNext();
        }
    }
    
    @Override
    public boolean isComplete() {
        return this.scramStage == ScramExchangeStage.TERMINATED;
    }
    
    @Override
    public byte[] unwrap(final byte[] incoming, final int offset, final int len) throws SaslException {
        throw new IllegalStateException("Integrity and/or privacy has not been negotiated.");
    }
    
    @Override
    public byte[] wrap(final byte[] outgoing, final int offset, final int len) throws SaslException {
        throw new IllegalStateException("Integrity and/or privacy has not been negotiated.");
    }
    
    @Override
    public Object getNegotiatedProperty(final String propName) {
        return null;
    }
    
    @Override
    public void dispose() throws SaslException {
    }
    
    private String prepUserName(final String userName) {
        return SaslPrep.prepare(userName, SaslPrep.StringType.QUERY).replace("=", "=2D").replace(",", "=2C");
    }
    
    private Map<String, String> parseChallenge(final String challenge) {
        final Map<String, String> attributesMap = new HashMap<String, String>();
        for (final String attribute : challenge.split(",")) {
            final String[] keyValue = attribute.split("=", 2);
            attributesMap.put(keyValue[0], keyValue[1]);
        }
        return attributesMap;
    }
    
    private String generateRandomPrintableAsciiString(final int length) {
        final int first = 33;
        final int last = 126;
        final int excl = 44;
        final int bound = 93;
        final Random random = new SecureRandom();
        final char[] result = new char[length];
        int randomValue;
        for (int i = 0; i < length; result[i++] = (char)randomValue) {
            randomValue = random.nextInt(93) + 33;
            if (randomValue != 44) {}
        }
        return new String(result);
    }
    
    abstract byte[] h(final byte[] p0);
    
    abstract byte[] hmac(final byte[] p0, final byte[] p1);
    
    abstract byte[] hi(final String p0, final byte[] p1, final int p2);
    
    byte[] xorInPlace(final byte[] inOut, final byte[] other) {
        for (int i = 0; i < inOut.length; ++i) {
            final int n = i;
            inOut[n] ^= other[i];
        }
        return inOut;
    }
    
    static {
        CLIENT_KEY = "Client Key".getBytes();
        SERVER_KEY = "Server Key".getBytes();
    }
    
    protected enum ScramExchangeStage
    {
        TERMINATED((ScramExchangeStage)null), 
        SERVER_FINAL(ScramExchangeStage.TERMINATED), 
        SERVER_FIRST_CLIENT_FINAL(ScramExchangeStage.SERVER_FINAL), 
        CLIENT_FIRST(ScramExchangeStage.SERVER_FIRST_CLIENT_FINAL);
        
        private ScramExchangeStage next;
        
        private ScramExchangeStage(final ScramExchangeStage next) {
            this.next = next;
        }
        
        public ScramExchangeStage getNext() {
            return (this.next == null) ? this : this.next;
        }
    }
}
