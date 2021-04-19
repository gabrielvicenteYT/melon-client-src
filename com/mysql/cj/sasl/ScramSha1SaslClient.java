package com.mysql.cj.sasl;

import javax.security.sasl.*;
import com.mysql.cj.exceptions.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.spec.*;

public class ScramSha1SaslClient extends ScramShaSaslClient
{
    public static final String IANA_MECHANISM_NAME = "SCRAM-SHA-1";
    public static final String MECHANISM_NAME = "MYSQLCJ-SCRAM-SHA-1";
    private static final String SHA1_ALGORITHM = "SHA-1";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private static final String PBKCF2_HMAC_SHA1_ALGORITHM = "PBKDF2WithHmacSHA1";
    private static final int SHA1_HASH_LENGTH = 20;
    
    public ScramSha1SaslClient(final String authorizationId, final String authenticationId, final String password) throws SaslException {
        super(authorizationId, authenticationId, password);
    }
    
    @Override
    String getIanaMechanismName() {
        return "SCRAM-SHA-1";
    }
    
    @Override
    public String getMechanismName() {
        return "MYSQLCJ-SCRAM-SHA-1";
    }
    
    @Override
    byte[] h(final byte[] str) {
        try {
            final MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            return sha1.digest(str);
        }
        catch (NoSuchAlgorithmException e) {
            throw ExceptionFactory.createException("Failed computing authentication hashes", e);
        }
    }
    
    @Override
    byte[] hmac(final byte[] key, final byte[] str) {
        try {
            final Mac hmacSha1 = Mac.getInstance("HmacSHA1");
            hmacSha1.init(new SecretKeySpec(key, "HmacSHA1"));
            return hmacSha1.doFinal(str);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException("Failed computing authentication hashes", e);
        }
    }
    
    @Override
    byte[] hi(final String str, final byte[] salt, final int iterations) {
        final KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, iterations, 160);
        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(e.getMessage());
        }
    }
}
