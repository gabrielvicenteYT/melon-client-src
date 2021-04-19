package com.mysql.cj.sasl;

import javax.security.sasl.*;
import com.mysql.cj.exceptions.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.spec.*;

public class ScramSha256SaslClient extends ScramShaSaslClient
{
    public static final String IANA_MECHANISM_NAME = "SCRAM-SHA-256";
    public static final String MECHANISM_NAME = "MYSQLCJ-SCRAM-SHA-256";
    private static final String SHA256_ALGORITHM = "SHA-256";
    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final String PBKCF2_HMAC_SHA256_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SHA256_HASH_LENGTH = 32;
    
    public ScramSha256SaslClient(final String authorizationId, final String authenticationId, final String password) throws SaslException {
        super(authorizationId, authenticationId, password);
    }
    
    @Override
    String getIanaMechanismName() {
        return "SCRAM-SHA-256";
    }
    
    @Override
    public String getMechanismName() {
        return "MYSQLCJ-SCRAM-SHA-256";
    }
    
    @Override
    byte[] h(final byte[] str) {
        try {
            final MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            return sha256.digest(str);
        }
        catch (NoSuchAlgorithmException e) {
            throw ExceptionFactory.createException("Failed computing authentication hashes", e);
        }
    }
    
    @Override
    byte[] hmac(final byte[] key, final byte[] str) {
        try {
            final Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            hmacSha256.init(new SecretKeySpec(key, "HmacSHA256"));
            return hmacSha256.doFinal(str);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException("Failed computing authentication hashes", e);
        }
    }
    
    @Override
    byte[] hi(final String str, final byte[] salt, final int iterations) {
        final KeySpec spec = new PBEKeySpec(str.toCharArray(), salt, iterations, 256);
        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(e.getMessage());
        }
    }
}
