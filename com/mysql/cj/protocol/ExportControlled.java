package com.mysql.cj.protocol;

import java.util.function.*;
import java.util.stream.*;
import com.mysql.cj.*;
import java.net.*;
import com.mysql.cj.conf.*;
import javax.net.ssl.*;
import java.io.*;
import java.security.interfaces.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.util.*;
import java.security.spec.*;
import javax.crypto.*;
import java.util.*;
import java.security.*;
import javax.naming.ldap.*;
import javax.naming.*;
import java.security.cert.*;

public class ExportControlled
{
    private static final String TLSv1 = "TLSv1";
    private static final String TLSv1_1 = "TLSv1.1";
    private static final String TLSv1_2 = "TLSv1.2";
    private static final String TLSv1_3 = "TLSv1.3";
    private static final String[] TLS_PROTOCOLS;
    private static final String TLS_SETTINGS_RESOURCE = "/com/mysql/cj/TlsSettings.properties";
    private static final List<String> ALLOWED_CIPHERS;
    private static final List<String> RESTRICTED_CIPHER_SUBSTR;
    
    private ExportControlled() {
    }
    
    public static boolean enabled() {
        return true;
    }
    
    private static String[] getAllowedCiphers(final PropertySet pset, final List<String> socketCipherSuites) {
        final String enabledSSLCipherSuites = pset.getStringProperty(PropertyKey.enabledSSLCipherSuites).getValue();
        final Stream<String> filterStream = StringUtils.isNullOrEmpty(enabledSSLCipherSuites) ? socketCipherSuites.stream() : Arrays.stream(enabledSSLCipherSuites.split("\\s*,\\s*")).filter(socketCipherSuites::contains);
        final List<String> allowedCiphers = filterStream.filter(ExportControlled.ALLOWED_CIPHERS::contains).filter(c -> !ExportControlled.RESTRICTED_CIPHER_SUBSTR.stream().filter(r -> c.contains(r)).findFirst().isPresent()).collect((Collector<? super String, ?, List<String>>)Collectors.toList());
        return allowedCiphers.toArray(new String[0]);
    }
    
    private static String[] getAllowedProtocols(final PropertySet pset, final ServerVersion serverVersion, final String[] socketProtocols) {
        String[] tryProtocols = null;
        final String enabledTLSProtocols = pset.getStringProperty(PropertyKey.enabledTLSProtocols).getValue();
        if (enabledTLSProtocols != null && enabledTLSProtocols.length() > 0) {
            tryProtocols = enabledTLSProtocols.split("\\s*,\\s*");
        }
        else if (serverVersion == null) {
            tryProtocols = ExportControlled.TLS_PROTOCOLS;
        }
        else if (serverVersion.meetsMinimum(new ServerVersion(5, 7, 28)) || (serverVersion.meetsMinimum(new ServerVersion(5, 6, 46)) && !serverVersion.meetsMinimum(new ServerVersion(5, 7, 0))) || (serverVersion.meetsMinimum(new ServerVersion(5, 6, 0)) && Util.isEnterpriseEdition(serverVersion.toString()))) {
            tryProtocols = ExportControlled.TLS_PROTOCOLS;
        }
        else {
            tryProtocols = new String[] { "TLSv1.1", "TLSv1" };
        }
        final List<String> configuredProtocols = new ArrayList<String>(Arrays.asList(tryProtocols));
        final List<String> jvmSupportedProtocols = Arrays.asList(socketProtocols);
        final List<String> allowedProtocols = new ArrayList<String>();
        for (final String protocol : ExportControlled.TLS_PROTOCOLS) {
            if (jvmSupportedProtocols.contains(protocol) && configuredProtocols.contains(protocol)) {
                allowedProtocols.add(protocol);
            }
        }
        return allowedProtocols.toArray(new String[0]);
    }
    
    public static void checkValidProtocols(final List<String> protocols) {
        final List<String> validProtocols = Arrays.asList(ExportControlled.TLS_PROTOCOLS);
        for (final String protocol : protocols) {
            if (!validProtocols.contains(protocol)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, "'" + protocol + "' not recognized as a valid TLS protocol version (should be one of " + Arrays.stream(ExportControlled.TLS_PROTOCOLS).collect(Collectors.joining(", ")) + ").");
            }
        }
    }
    
    private static KeyStoreConf getTrustStoreConf(final PropertySet propertySet, final boolean required) {
        String trustStoreUrl = propertySet.getStringProperty(PropertyKey.trustCertificateKeyStoreUrl).getValue();
        String trustStorePassword = propertySet.getStringProperty(PropertyKey.trustCertificateKeyStorePassword).getValue();
        String trustStoreType = propertySet.getStringProperty(PropertyKey.trustCertificateKeyStoreType).getValue();
        final boolean fallbackToSystemTrustStore = propertySet.getBooleanProperty(PropertyKey.fallbackToSystemTrustStore).getValue();
        if (fallbackToSystemTrustStore && StringUtils.isNullOrEmpty(trustStoreUrl)) {
            trustStoreUrl = System.getProperty("javax.net.ssl.trustStore");
            trustStorePassword = System.getProperty("javax.net.ssl.trustStorePassword");
            trustStoreType = System.getProperty("javax.net.ssl.trustStoreType");
            if (StringUtils.isNullOrEmpty(trustStoreType)) {
                trustStoreType = propertySet.getStringProperty(PropertyKey.trustCertificateKeyStoreType).getInitialValue();
            }
            if (!StringUtils.isNullOrEmpty(trustStoreUrl)) {
                try {
                    new URL(trustStoreUrl);
                }
                catch (MalformedURLException e) {
                    trustStoreUrl = "file:" + trustStoreUrl;
                }
            }
        }
        if (required && StringUtils.isNullOrEmpty(trustStoreUrl)) {
            throw new CJCommunicationsException("No truststore provided to verify the Server certificate.");
        }
        return new KeyStoreConf(trustStoreUrl, trustStorePassword, trustStoreType);
    }
    
    private static KeyStoreConf getKeyStoreConf(final PropertySet propertySet) {
        String keyStoreUrl = propertySet.getStringProperty(PropertyKey.clientCertificateKeyStoreUrl).getValue();
        String keyStorePassword = propertySet.getStringProperty(PropertyKey.clientCertificateKeyStorePassword).getValue();
        String keyStoreType = propertySet.getStringProperty(PropertyKey.clientCertificateKeyStoreType).getValue();
        final boolean fallbackToSystemKeyStore = propertySet.getBooleanProperty(PropertyKey.fallbackToSystemKeyStore).getValue();
        if (fallbackToSystemKeyStore && StringUtils.isNullOrEmpty(keyStoreUrl)) {
            keyStoreUrl = System.getProperty("javax.net.ssl.keyStore");
            keyStorePassword = System.getProperty("javax.net.ssl.keyStorePassword");
            keyStoreType = System.getProperty("javax.net.ssl.keyStoreType");
            if (StringUtils.isNullOrEmpty(keyStoreType)) {
                keyStoreType = propertySet.getStringProperty(PropertyKey.clientCertificateKeyStoreType).getInitialValue();
            }
            if (!StringUtils.isNullOrEmpty(keyStoreUrl)) {
                try {
                    new URL(keyStoreUrl);
                }
                catch (MalformedURLException e) {
                    keyStoreUrl = "file:" + keyStoreUrl;
                }
            }
        }
        return new KeyStoreConf(keyStoreUrl, keyStorePassword, keyStoreType);
    }
    
    public static Socket performTlsHandshake(final Socket rawSocket, final SocketConnection socketConnection, final ServerVersion serverVersion) throws IOException, SSLParamsException, FeatureNotAvailableException {
        final PropertySet pset = socketConnection.getPropertySet();
        final PropertyDefinitions.SslMode sslMode = pset.getEnumProperty(PropertyKey.sslMode).getValue();
        final boolean verifyServerCert = sslMode == PropertyDefinitions.SslMode.VERIFY_CA || sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY;
        final boolean fallbackToSystemTrustStore = pset.getBooleanProperty(PropertyKey.fallbackToSystemTrustStore).getValue();
        final KeyStoreConf trustStore = verifyServerCert ? getTrustStoreConf(pset, serverVersion == null && verifyServerCert && !fallbackToSystemTrustStore) : new KeyStoreConf();
        final KeyStoreConf keyStore = getKeyStoreConf(pset);
        final SSLSocketFactory socketFactory = getSSLContext(keyStore, trustStore, fallbackToSystemTrustStore, verifyServerCert, (sslMode == PropertyDefinitions.SslMode.VERIFY_IDENTITY) ? socketConnection.getHost() : null, socketConnection.getExceptionInterceptor()).getSocketFactory();
        final SSLSocket sslSocket = (SSLSocket)socketFactory.createSocket(rawSocket, socketConnection.getHost(), socketConnection.getPort(), true);
        final String[] allowedProtocols = getAllowedProtocols(pset, serverVersion, sslSocket.getSupportedProtocols());
        sslSocket.setEnabledProtocols(allowedProtocols);
        final String[] allowedCiphers = getAllowedCiphers(pset, Arrays.asList(sslSocket.getEnabledCipherSuites()));
        if (allowedCiphers != null) {
            sslSocket.setEnabledCipherSuites(allowedCiphers);
        }
        sslSocket.startHandshake();
        return sslSocket;
    }
    
    public static SSLContext getSSLContext(final KeyStoreConf clientCertificateKeyStore, final KeyStoreConf trustCertificateKeyStore, final boolean fallbackToDefaultTrustStore, final boolean verifyServerCert, final String hostName, final ExceptionInterceptor exceptionInterceptor) throws SSLParamsException {
        final String clientCertificateKeyStoreUrl = clientCertificateKeyStore.keyStoreUrl;
        final String clientCertificateKeyStoreType = clientCertificateKeyStore.keyStoreType;
        final String clientCertificateKeyStorePassword = clientCertificateKeyStore.keyStorePassword;
        final String trustCertificateKeyStoreUrl = trustCertificateKeyStore.keyStoreUrl;
        final String trustCertificateKeyStoreType = trustCertificateKeyStore.keyStoreType;
        final String trustCertificateKeyStorePassword = trustCertificateKeyStore.keyStorePassword;
        TrustManagerFactory tmf = null;
        KeyManagerFactory kmf = null;
        KeyManager[] kms = null;
        final List<TrustManager> tms = new ArrayList<TrustManager>();
        try {
            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        }
        catch (NoSuchAlgorithmException nsae) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Default algorithm definitions for TrustManager and/or KeyManager are invalid.  Check java security properties file.", nsae, exceptionInterceptor);
        }
        if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreUrl)) {
            InputStream ksIS = null;
            try {
                if (!StringUtils.isNullOrEmpty(clientCertificateKeyStoreType)) {
                    final KeyStore clientKeyStore = KeyStore.getInstance(clientCertificateKeyStoreType);
                    final URL ksURL = new URL(clientCertificateKeyStoreUrl);
                    final char[] password = (clientCertificateKeyStorePassword == null) ? new char[0] : clientCertificateKeyStorePassword.toCharArray();
                    ksIS = ksURL.openStream();
                    clientKeyStore.load(ksIS, password);
                    kmf.init(clientKeyStore, password);
                    kms = kmf.getKeyManagers();
                }
            }
            catch (UnrecoverableKeyException uke) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not recover keys from client keystore.  Check password?", uke, exceptionInterceptor);
            }
            catch (NoSuchAlgorithmException nsae2) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + nsae2.getMessage() + "]", nsae2, exceptionInterceptor);
            }
            catch (KeyStoreException kse) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + kse.getMessage() + "]", kse, exceptionInterceptor);
            }
            catch (CertificateException nsae3) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Could not load client" + clientCertificateKeyStoreType + " keystore from " + clientCertificateKeyStoreUrl, nsae3, exceptionInterceptor);
            }
            catch (MalformedURLException mue) {
                throw ExceptionFactory.createException(SSLParamsException.class, clientCertificateKeyStoreUrl + " does not appear to be a valid URL.", mue, exceptionInterceptor);
            }
            catch (IOException ioe) {
                throw ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + clientCertificateKeyStoreUrl + " [" + ioe.getMessage() + "]", ioe, exceptionInterceptor);
            }
            finally {
                if (ksIS != null) {
                    try {
                        ksIS.close();
                    }
                    catch (IOException ex) {}
                }
            }
        }
        InputStream trustStoreIS = null;
        try {
            String trustStoreType = "";
            char[] trustStorePassword = null;
            KeyStore trustKeyStore = null;
            if (!StringUtils.isNullOrEmpty(trustCertificateKeyStoreUrl) && !StringUtils.isNullOrEmpty(trustCertificateKeyStoreType)) {
                trustStoreType = trustCertificateKeyStoreType;
                trustStorePassword = ((trustCertificateKeyStorePassword == null) ? new char[0] : trustCertificateKeyStorePassword.toCharArray());
                trustStoreIS = new URL(trustCertificateKeyStoreUrl).openStream();
                trustKeyStore = KeyStore.getInstance(trustStoreType);
                trustKeyStore.load(trustStoreIS, trustStorePassword);
            }
            if (trustKeyStore != null || (verifyServerCert && fallbackToDefaultTrustStore)) {
                tmf.init(trustKeyStore);
                final TrustManager[] trustManagers;
                final TrustManager[] origTms = trustManagers = tmf.getTrustManagers();
                for (final TrustManager tm : trustManagers) {
                    tms.add((tm instanceof X509TrustManager) ? new X509TrustManagerWrapper((X509TrustManager)tm, verifyServerCert, hostName) : tm);
                }
            }
        }
        catch (MalformedURLException e) {
            throw ExceptionFactory.createException(SSLParamsException.class, trustCertificateKeyStoreUrl + " does not appear to be a valid URL.", e, exceptionInterceptor);
        }
        catch (NoSuchAlgorithmException e2) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Unsupported keystore algorithm [" + e2.getMessage() + "]", e2, exceptionInterceptor);
        }
        catch (KeyStoreException e3) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Could not create KeyStore instance [" + e3.getMessage() + "]", e3, exceptionInterceptor);
        }
        catch (CertificateException e4) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Could not load trust" + trustCertificateKeyStoreType + " keystore from " + trustCertificateKeyStoreUrl, e4, exceptionInterceptor);
        }
        catch (IOException e5) {
            throw ExceptionFactory.createException(SSLParamsException.class, "Cannot open " + trustCertificateKeyStoreUrl + " [" + e5.getMessage() + "]", e5, exceptionInterceptor);
        }
        finally {
            if (trustStoreIS != null) {
                try {
                    trustStoreIS.close();
                }
                catch (IOException ex2) {}
            }
        }
        if (tms.size() == 0) {
            tms.add(new X509TrustManagerWrapper(verifyServerCert, hostName));
        }
        try {
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kms, tms.toArray(new TrustManager[tms.size()]), null);
            return sslContext;
        }
        catch (NoSuchAlgorithmException nsae2) {
            throw new SSLParamsException("TLS is not a valid SSL protocol.", nsae2);
        }
        catch (KeyManagementException kme) {
            throw new SSLParamsException("KeyManagementException: " + kme.getMessage(), kme);
        }
    }
    
    public static boolean isSSLEstablished(final Socket socket) {
        return socket != null && SSLSocket.class.isAssignableFrom(socket.getClass());
    }
    
    public static RSAPublicKey decodeRSAPublicKey(final String key) throws RSAException {
        if (key == null) {
            throw ExceptionFactory.createException(RSAException.class, "Key parameter is null");
        }
        final int offset = key.indexOf("\n") + 1;
        final int len = key.indexOf("-----END PUBLIC KEY-----") - offset;
        final byte[] certificateData = Base64Decoder.decode(key.getBytes(), offset, len);
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(certificateData);
        try {
            final KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey)kf.generatePublic(spec);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(RSAException.class, "Unable to decode public key", e);
        }
    }
    
    public static byte[] encryptWithRSAPublicKey(final byte[] source, final RSAPublicKey key, final String transformation) throws RSAException {
        try {
            final Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(1, key);
            return cipher.doFinal(source);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex2) {
            final GeneralSecurityException ex;
            final GeneralSecurityException e = ex;
            throw ExceptionFactory.createException(RSAException.class, e.getMessage(), e);
        }
    }
    
    public static byte[] encryptWithRSAPublicKey(final byte[] source, final RSAPublicKey key) throws RSAException {
        return encryptWithRSAPublicKey(source, key, "RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
    }
    
    static {
        TLS_PROTOCOLS = new String[] { "TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1" };
        ALLOWED_CIPHERS = new ArrayList<String>();
        RESTRICTED_CIPHER_SUBSTR = new ArrayList<String>();
        try {
            final Properties tlsSettings = new Properties();
            tlsSettings.load(ExportControlled.class.getResourceAsStream("/com/mysql/cj/TlsSettings.properties"));
            Arrays.stream(tlsSettings.getProperty("TLSCiphers.Mandatory").split("\\s*,\\s*")).forEach(s -> ExportControlled.ALLOWED_CIPHERS.add(s.trim()));
            Arrays.stream(tlsSettings.getProperty("TLSCiphers.Approved").split("\\s*,\\s*")).forEach(s -> ExportControlled.ALLOWED_CIPHERS.add(s.trim()));
            Arrays.stream(tlsSettings.getProperty("TLSCiphers.Deprecated").split("\\s*,\\s*")).forEach(s -> ExportControlled.ALLOWED_CIPHERS.add(s.trim()));
            Arrays.stream(tlsSettings.getProperty("TLSCiphers.Unacceptable.Mask").split("\\s*,\\s*")).forEach(s -> ExportControlled.RESTRICTED_CIPHER_SUBSTR.add(s.trim()));
        }
        catch (IOException e) {
            throw ExceptionFactory.createException("Unable to load TlsSettings.properties");
        }
    }
    
    private static class KeyStoreConf
    {
        public String keyStoreUrl;
        public String keyStorePassword;
        public String keyStoreType;
        
        public KeyStoreConf() {
            this.keyStoreUrl = null;
            this.keyStorePassword = null;
            this.keyStoreType = "JKS";
        }
        
        public KeyStoreConf(final String keyStoreUrl, final String keyStorePassword, final String keyStoreType) {
            this.keyStoreUrl = null;
            this.keyStorePassword = null;
            this.keyStoreType = "JKS";
            this.keyStoreUrl = keyStoreUrl;
            this.keyStorePassword = keyStorePassword;
            this.keyStoreType = keyStoreType;
        }
    }
    
    public static class X509TrustManagerWrapper implements X509TrustManager
    {
        private X509TrustManager origTm;
        private boolean verifyServerCert;
        private String hostName;
        private CertificateFactory certFactory;
        private PKIXParameters validatorParams;
        private CertPathValidator validator;
        
        public X509TrustManagerWrapper(final X509TrustManager tm, final boolean verifyServerCertificate, final String hostName) throws CertificateException {
            this.origTm = null;
            this.verifyServerCert = false;
            this.hostName = null;
            this.certFactory = null;
            this.validatorParams = null;
            this.validator = null;
            this.origTm = tm;
            this.verifyServerCert = verifyServerCertificate;
            this.hostName = hostName;
            if (verifyServerCertificate) {
                try {
                    final Set<TrustAnchor> anch = Arrays.stream(tm.getAcceptedIssuers()).map(c -> new TrustAnchor(c, null)).collect((Collector<? super Object, ?, Set<TrustAnchor>>)Collectors.toSet());
                    (this.validatorParams = new PKIXParameters(anch)).setRevocationEnabled(false);
                    this.validator = CertPathValidator.getInstance("PKIX");
                    this.certFactory = CertificateFactory.getInstance("X.509");
                }
                catch (Exception e) {
                    throw new CertificateException(e);
                }
            }
        }
        
        public X509TrustManagerWrapper(final boolean verifyServerCertificate, final String hostName) {
            this.origTm = null;
            this.verifyServerCert = false;
            this.hostName = null;
            this.certFactory = null;
            this.validatorParams = null;
            this.validator = null;
            this.verifyServerCert = verifyServerCertificate;
            this.hostName = hostName;
        }
        
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return (this.origTm != null) ? this.origTm.getAcceptedIssuers() : new X509Certificate[0];
        }
        
        @Override
        public void checkServerTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            for (int i = 0; i < chain.length; ++i) {
                chain[i].checkValidity();
            }
            if (this.validatorParams != null) {
                final X509CertSelector certSelect = new X509CertSelector();
                certSelect.setSerialNumber(chain[0].getSerialNumber());
                try {
                    final CertPath certPath = this.certFactory.generateCertPath(Arrays.asList(chain));
                    final CertPathValidatorResult result = this.validator.validate(certPath, this.validatorParams);
                    ((PKIXCertPathValidatorResult)result).getTrustAnchor().getTrustedCert().checkValidity();
                }
                catch (InvalidAlgorithmParameterException e) {
                    throw new CertificateException(e);
                }
                catch (CertPathValidatorException e2) {
                    throw new CertificateException(e2);
                }
            }
            if (this.verifyServerCert) {
                if (this.origTm == null) {
                    throw new CertificateException("Can't verify server certificate because no trust manager is found.");
                }
                this.origTm.checkServerTrusted(chain, authType);
                if (this.hostName != null) {
                    boolean hostNameVerified = false;
                    final Collection<List<?>> subjectAltNames = chain[0].getSubjectAlternativeNames();
                    if (subjectAltNames != null) {
                        boolean sanVerification = false;
                        for (final List<?> san : subjectAltNames) {
                            final Integer nameType = (Integer)san.get(0);
                            if (nameType == 2) {
                                sanVerification = true;
                                if (this.verifyHostName((String)san.get(1))) {
                                    hostNameVerified = true;
                                    break;
                                }
                                continue;
                            }
                            else {
                                if (nameType != 7) {
                                    continue;
                                }
                                sanVerification = true;
                                if (this.hostName.equalsIgnoreCase((String)san.get(1))) {
                                    hostNameVerified = true;
                                    break;
                                }
                                continue;
                            }
                        }
                        if (sanVerification && !hostNameVerified) {
                            throw new CertificateException("Server identity verification failed. None of the DNS or IP Subject Alternative Name entries matched the server hostname/IP '" + this.hostName + "'.");
                        }
                    }
                    if (!hostNameVerified) {
                        final String dn = chain[0].getSubjectX500Principal().getName("RFC2253");
                        String cn = null;
                        try {
                            final LdapName ldapDN = new LdapName(dn);
                            for (final Rdn rdn : ldapDN.getRdns()) {
                                if (rdn.getType().equalsIgnoreCase("CN")) {
                                    cn = rdn.getValue().toString();
                                    break;
                                }
                            }
                        }
                        catch (InvalidNameException e3) {
                            throw new CertificateException("Failed to retrieve the Common Name (CN) from the server certificate.");
                        }
                        if (!this.verifyHostName(cn)) {
                            throw new CertificateException("Server identity verification failed. The certificate Common Name '" + cn + "' does not match '" + this.hostName + "'.");
                        }
                    }
                }
            }
        }
        
        @Override
        public void checkClientTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
            this.origTm.checkClientTrusted(chain, authType);
        }
        
        private boolean verifyHostName(final String ptn) {
            final int indexOfStar = ptn.indexOf(42);
            if (indexOfStar >= 0 && indexOfStar < ptn.indexOf(46)) {
                final String head = ptn.substring(0, indexOfStar);
                final String tail = ptn.substring(indexOfStar + 1);
                return StringUtils.startsWithIgnoreCase(this.hostName, head) && StringUtils.endsWithIgnoreCase(this.hostName, tail) && this.hostName.substring(head.length(), this.hostName.length() - tail.length()).indexOf(46) == -1;
            }
            return this.hostName.equalsIgnoreCase(ptn);
        }
    }
}
