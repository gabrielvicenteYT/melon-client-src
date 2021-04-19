package com.mysql.cj.protocol.a;

import java.lang.ref.*;
import com.mysql.cj.interceptors.*;
import com.mysql.cj.conf.*;
import java.util.function.*;
import com.mysql.cj.log.*;
import com.mysql.cj.exceptions.*;
import com.mysql.cj.protocol.a.result.*;
import com.mysql.cj.protocol.*;
import com.mysql.cj.*;
import com.mysql.cj.util.*;
import java.net.*;
import java.nio.file.*;
import java.lang.management.*;
import com.mysql.cj.result.*;
import com.mysql.cj.jdbc.exceptions.*;
import java.sql.*;
import java.util.*;
import java.time.*;
import java.io.*;

public class NativeProtocol extends AbstractProtocol<NativePacketPayload> implements Protocol<NativePacketPayload>, RuntimeProperty.RuntimePropertyListener
{
    protected static final int INITIAL_PACKET_SIZE = 1024;
    protected static final int COMP_HEADER_LENGTH = 3;
    protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 1048576;
    protected static final int SSL_REQUEST_LENGTH = 32;
    private static final String EXPLAINABLE_STATEMENT = "SELECT";
    private static final String[] EXPLAINABLE_STATEMENT_EXTENSION;
    protected MessageSender<NativePacketPayload> packetSender;
    protected MessageReader<NativePacketHeader, NativePacketPayload> packetReader;
    protected NativeServerSession serverSession;
    protected CompressedPacketSender compressedPacketSender;
    protected NativePacketPayload sharedSendPacket;
    protected NativePacketPayload reusablePacket;
    private SoftReference<NativePacketPayload> loadFileBufRef;
    protected byte packetSequence;
    protected boolean useCompression;
    private RuntimeProperty<Integer> maxAllowedPacket;
    private RuntimeProperty<Boolean> useServerPrepStmts;
    private boolean autoGenerateTestcaseScript;
    private boolean logSlowQueries;
    private boolean useAutoSlowLog;
    private boolean profileSQL;
    private long slowQueryThreshold;
    private int commandCount;
    protected boolean hadWarnings;
    private int warningCount;
    protected Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, ? extends Message>> PROTOCOL_ENTITY_CLASS_TO_TEXT_READER;
    protected Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, ? extends Message>> PROTOCOL_ENTITY_CLASS_TO_BINARY_READER;
    protected boolean platformDbCharsetMatches;
    private int statementExecutionDepth;
    private List<QueryInterceptor> queryInterceptors;
    private RuntimeProperty<Boolean> maintainTimeStats;
    private RuntimeProperty<Integer> maxQuerySizeToLog;
    private InputStream localInfileInputStream;
    private BaseMetricsHolder metricsHolder;
    private String queryComment;
    private static String jvmPlatformCharset;
    private NativeMessageBuilder commandBuilder;
    private ResultsetRows streamingData;
    
    public static NativeProtocol getInstance(final Session session, final SocketConnection socketConnection, final PropertySet propertySet, final Log log, final TransactionEventHandler transactionManager) {
        final NativeProtocol protocol = new NativeProtocol(log);
        protocol.init(session, socketConnection, propertySet, transactionManager);
        return protocol;
    }
    
    public NativeProtocol(final Log logger) {
        this.sharedSendPacket = null;
        this.reusablePacket = null;
        this.packetSequence = 0;
        this.useCompression = false;
        this.logSlowQueries = false;
        this.profileSQL = false;
        this.commandCount = 0;
        this.hadWarnings = false;
        this.warningCount = 0;
        this.platformDbCharsetMatches = true;
        this.statementExecutionDepth = 0;
        this.queryComment = null;
        this.commandBuilder = new NativeMessageBuilder();
        this.streamingData = null;
        this.log = logger;
        this.metricsHolder = new BaseMetricsHolder();
    }
    
    @Override
    public void init(final Session sess, final SocketConnection phConnection, final PropertySet propSet, final TransactionEventHandler trManager) {
        super.init(sess, phConnection, propSet, trManager);
        this.maintainTimeStats = this.propertySet.getBooleanProperty(PropertyKey.maintainTimeStats);
        this.maxQuerySizeToLog = this.propertySet.getIntegerProperty(PropertyKey.maxQuerySizeToLog);
        this.useAutoSlowLog = this.propertySet.getBooleanProperty(PropertyKey.autoSlowLog).getValue();
        this.logSlowQueries = this.propertySet.getBooleanProperty(PropertyKey.logSlowQueries).getValue();
        this.maxAllowedPacket = this.propertySet.getIntegerProperty(PropertyKey.maxAllowedPacket);
        this.profileSQL = this.propertySet.getBooleanProperty(PropertyKey.profileSQL).getValue();
        this.autoGenerateTestcaseScript = this.propertySet.getBooleanProperty(PropertyKey.autoGenerateTestcaseScript).getValue();
        this.useServerPrepStmts = this.propertySet.getBooleanProperty(PropertyKey.useServerPrepStmts);
        this.reusablePacket = new NativePacketPayload(1024);
        try {
            this.packetSender = new SimplePacketSender(this.socketConnection.getMysqlOutput());
            this.packetReader = new SimplePacketReader(this.socketConnection, this.maxAllowedPacket);
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
        if (this.propertySet.getBooleanProperty(PropertyKey.logSlowQueries).getValue()) {
            this.calculateSlowQueryThreshold();
        }
        (this.authProvider = (AuthenticationProvider<M>)new NativeAuthenticationProvider()).init((Protocol<M>)this, this.getPropertySet(), this.socketConnection.getExceptionInterceptor());
        final Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, NativePacketPayload>> protocolEntityClassToTextReader = new HashMap<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, NativePacketPayload>>();
        protocolEntityClassToTextReader.put(ColumnDefinition.class, new ColumnDefinitionReader(this));
        protocolEntityClassToTextReader.put(ResultsetRow.class, new ResultsetRowReader(this));
        protocolEntityClassToTextReader.put(Resultset.class, new TextResultsetReader(this));
        this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER = Collections.unmodifiableMap((Map<? extends Class<? extends ProtocolEntity>, ? extends ProtocolEntityReader<? extends ProtocolEntity, ? extends Message>>)protocolEntityClassToTextReader);
        final Map<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, NativePacketPayload>> protocolEntityClassToBinaryReader = new HashMap<Class<? extends ProtocolEntity>, ProtocolEntityReader<? extends ProtocolEntity, NativePacketPayload>>();
        protocolEntityClassToBinaryReader.put(ColumnDefinition.class, new ColumnDefinitionReader(this));
        protocolEntityClassToBinaryReader.put(Resultset.class, new BinaryResultsetReader(this));
        this.PROTOCOL_ENTITY_CLASS_TO_BINARY_READER = Collections.unmodifiableMap((Map<? extends Class<? extends ProtocolEntity>, ? extends ProtocolEntityReader<? extends ProtocolEntity, ? extends Message>>)protocolEntityClassToBinaryReader);
    }
    
    @Override
    public MessageBuilder<NativePacketPayload> getMessageBuilder() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    public MessageSender<NativePacketPayload> getPacketSender() {
        return this.packetSender;
    }
    
    public MessageReader<NativePacketHeader, NativePacketPayload> getPacketReader() {
        return this.packetReader;
    }
    
    @Override
    public void negotiateSSLConnection() {
        if (!ExportControlled.enabled()) {
            throw new CJConnectionFeatureNotAvailableException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder(), null);
        }
        final long clientParam = this.serverSession.getClientParam();
        final NativePacketPayload packet = new NativePacketPayload(32);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, clientParam);
        packet.writeInteger(NativeConstants.IntegerDataType.INT4, 16777215L);
        packet.writeInteger(NativeConstants.IntegerDataType.INT1, AuthenticationProvider.getCharsetForHandshake(this.authProvider.getEncodingForHandshake(), this.serverSession.getCapabilities().getServerVersion()));
        packet.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, new byte[23]);
        this.send(packet, packet.getPosition());
        try {
            this.socketConnection.performTlsHandshake(this.serverSession);
            this.packetSender = new SimplePacketSender(this.socketConnection.getMysqlOutput());
            this.packetReader = new SimplePacketReader(this.socketConnection, this.maxAllowedPacket);
        }
        catch (FeatureNotAvailableException nae) {
            throw new CJConnectionFeatureNotAvailableException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder(), nae);
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
    }
    
    public void rejectProtocol(final NativePacketPayload msg) {
        try {
            this.socketConnection.getMysqlSocket().close();
        }
        catch (Exception ex) {}
        int errno = 2000;
        final NativePacketPayload buf = msg;
        buf.setPosition(1);
        errno = (int)buf.readInteger(NativeConstants.IntegerDataType.INT2);
        String serverErrorMessage = "";
        try {
            serverErrorMessage = buf.readString(NativeConstants.StringSelfDataType.STRING_TERM, "ASCII");
        }
        catch (Exception ex2) {}
        final StringBuilder errorBuf = new StringBuilder(Messages.getString("Protocol.0"));
        errorBuf.append(serverErrorMessage);
        errorBuf.append("\"");
        final String xOpen = MysqlErrorNumbers.mysqlToSqlState(errno);
        throw ExceptionFactory.createException(MysqlErrorNumbers.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno, false, null, this.getExceptionInterceptor());
    }
    
    @Override
    public void beforeHandshake() {
        this.packetReader.resetMessageSequence();
        (this.serverSession = new NativeServerSession(this.propertySet)).setCapabilities(this.readServerCapabilities());
    }
    
    @Override
    public void afterHandshake() {
        this.checkTransactionState();
        try {
            if ((this.serverSession.getCapabilities().getCapabilityFlags() & 0x20) != 0x0 && this.propertySet.getBooleanProperty(PropertyKey.useCompression).getValue() && !(this.socketConnection.getMysqlInput().getUnderlyingStream() instanceof CompressedInputStream)) {
                this.useCompression = true;
                this.socketConnection.setMysqlInput(new FullReadInputStream(new CompressedInputStream(this.socketConnection.getMysqlInput(), this.propertySet.getBooleanProperty(PropertyKey.traceProtocol), this.log)));
                this.compressedPacketSender = new CompressedPacketSender(this.socketConnection.getMysqlOutput());
                this.packetSender = this.compressedPacketSender;
            }
            this.applyPacketDecorators(this.packetSender, this.packetReader);
            this.socketConnection.getSocketFactory().afterHandshake();
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
        final RuntimeProperty<Boolean> useInformationSchema = this.propertySet.getProperty(PropertyKey.useInformationSchema);
        if (this.versionMeetsMinimum(8, 0, 3) && !useInformationSchema.getValue() && !useInformationSchema.isExplicitlySet()) {
            useInformationSchema.setValue(true);
        }
        this.maintainTimeStats.addListener(this);
        this.propertySet.getBooleanProperty(PropertyKey.traceProtocol).addListener(this);
        this.propertySet.getBooleanProperty(PropertyKey.enablePacketDebug).addListener(this);
    }
    
    @Override
    public void handlePropertyChange(final RuntimeProperty<?> prop) {
        switch (prop.getPropertyDefinition().getPropertyKey()) {
            case maintainTimeStats:
            case traceProtocol:
            case enablePacketDebug: {
                this.applyPacketDecorators(this.packetSender.undecorateAll(), this.packetReader.undecorateAll());
                break;
            }
        }
    }
    
    public void applyPacketDecorators(MessageSender<NativePacketPayload> sender, MessageReader<NativePacketHeader, NativePacketPayload> messageReader) {
        TimeTrackingPacketSender ttSender = null;
        TimeTrackingPacketReader ttReader = null;
        LinkedList<StringBuilder> debugRingBuffer = null;
        if (this.maintainTimeStats.getValue()) {
            ttSender = (TimeTrackingPacketSender)(sender = new TimeTrackingPacketSender(sender));
            ttReader = (TimeTrackingPacketReader)(messageReader = new TimeTrackingPacketReader(messageReader));
        }
        if (this.propertySet.getBooleanProperty(PropertyKey.traceProtocol).getValue()) {
            sender = new TracingPacketSender(sender, this.log, this.socketConnection.getHost(), this.getServerSession().getCapabilities().getThreadId());
            messageReader = new TracingPacketReader(messageReader, this.log);
        }
        if (this.getPropertySet().getBooleanProperty(PropertyKey.enablePacketDebug).getValue()) {
            debugRingBuffer = new LinkedList<StringBuilder>();
            sender = new DebugBufferingPacketSender(sender, debugRingBuffer, this.propertySet.getIntegerProperty(PropertyKey.packetDebugBufferSize));
            messageReader = new DebugBufferingPacketReader(messageReader, debugRingBuffer, this.propertySet.getIntegerProperty(PropertyKey.packetDebugBufferSize));
        }
        messageReader = new MultiPacketReader(messageReader);
        synchronized (this.packetReader) {
            this.packetReader = messageReader;
            this.packetDebugRingBuffer = debugRingBuffer;
            this.setPacketSentTimeHolder((ttSender != null) ? ttSender : new PacketSentTimeHolder() {});
        }
        synchronized (this.packetSender) {
            this.packetSender = sender;
            this.setPacketReceivedTimeHolder((ttReader != null) ? ttReader : new PacketReceivedTimeHolder() {});
        }
    }
    
    @Override
    public NativeCapabilities readServerCapabilities() {
        final NativePacketPayload buf = this.readMessage(null);
        if (buf.isErrorPacket()) {
            this.rejectProtocol(buf);
        }
        final NativeCapabilities serverCapabilities = new NativeCapabilities();
        serverCapabilities.setInitialHandshakePacket(buf);
        return serverCapabilities;
    }
    
    @Override
    public NativeServerSession getServerSession() {
        return this.serverSession;
    }
    
    @Override
    public void changeDatabase(final String database) {
        if (database == null || database.length() == 0) {
            return;
        }
        try {
            this.sendCommand(this.commandBuilder.buildComInitDb(this.getSharedSendPacket(), database), false, 0);
        }
        catch (CJException ex) {
            if (!this.getPropertySet().getBooleanProperty(PropertyKey.createDatabaseIfNotExist).getValue()) {
                throw ExceptionFactory.createCommunicationsException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ex, this.getExceptionInterceptor());
            }
            this.sendCommand(this.commandBuilder.buildComQuery(this.getSharedSendPacket(), "CREATE DATABASE IF NOT EXISTS " + database), false, 0);
            this.sendCommand(this.commandBuilder.buildComInitDb(this.getSharedSendPacket(), database), false, 0);
        }
    }
    
    @Override
    public final NativePacketPayload readMessage(final NativePacketPayload reuse) {
        try {
            final NativePacketHeader header = this.packetReader.readHeader();
            final NativePacketPayload buf = this.packetReader.readMessage(Optional.ofNullable(reuse), header);
            this.packetSequence = header.getMessageSequence();
            return buf;
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
        catch (OutOfMemoryError oom) {
            throw ExceptionFactory.createException(oom.getMessage(), "HY001", 0, false, oom, this.exceptionInterceptor);
        }
    }
    
    @Override
    public final void send(final Message packet, final int packetLen) {
        try {
            if (this.maxAllowedPacket.getValue() > 0 && packetLen > this.maxAllowedPacket.getValue()) {
                throw new CJPacketTooBigException(packetLen, this.maxAllowedPacket.getValue());
            }
            ++this.packetSequence;
            this.packetSender.send(packet.getByteBuffer(), packetLen, this.packetSequence);
            if (packet == this.sharedSendPacket) {
                this.reclaimLargeSharedSendPacket();
            }
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.getPropertySet(), this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
    }
    
    @Override
    public final NativePacketPayload sendCommand(final Message queryPacket, final boolean skipCheck, final int timeoutMillis) {
        final int command = queryPacket.getByteBuffer()[0];
        ++this.commandCount;
        if (this.queryInterceptors != null) {
            final NativePacketPayload interceptedPacketPayload = this.invokeQueryInterceptorsPre(queryPacket, false);
            if (interceptedPacketPayload != null) {
                return interceptedPacketPayload;
            }
        }
        this.packetReader.resetMessageSequence();
        int oldTimeout = 0;
        Label_0120: {
            if (timeoutMillis == 0) {
                break Label_0120;
            }
            try {
                oldTimeout = this.socketConnection.getMysqlSocket().getSoTimeout();
                this.socketConnection.getMysqlSocket().setSoTimeout(timeoutMillis);
            }
            catch (IOException e) {
                throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), e, this.getExceptionInterceptor());
            }
            try {
                this.checkForOutstandingStreamingData();
                this.serverSession.setStatusFlags(0, true);
                this.hadWarnings = false;
                this.setWarningCount(0);
                if (this.useCompression) {
                    final int bytesLeft = this.socketConnection.getMysqlInput().available();
                    if (bytesLeft > 0) {
                        this.socketConnection.getMysqlInput().skip(bytesLeft);
                    }
                }
                try {
                    this.clearInputStream();
                    this.packetSequence = -1;
                    this.send(queryPacket, queryPacket.getPosition());
                }
                catch (CJException ex) {
                    throw ex;
                }
                catch (Exception ex2) {
                    throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ex2, this.getExceptionInterceptor());
                }
                NativePacketPayload returnPacket = null;
                if (!skipCheck) {
                    if (command == 23 || command == 26) {
                        this.packetReader.resetMessageSequence();
                    }
                    returnPacket = this.checkErrorMessage(command);
                    if (this.queryInterceptors != null) {
                        returnPacket = this.invokeQueryInterceptorsPost(queryPacket, returnPacket, false);
                    }
                }
                return returnPacket;
            }
            catch (IOException ioEx) {
                this.serverSession.preserveOldTransactionState();
                throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
            }
            catch (CJException e2) {
                this.serverSession.preserveOldTransactionState();
                throw e2;
            }
            finally {
                if (timeoutMillis != 0) {
                    try {
                        this.socketConnection.getMysqlSocket().setSoTimeout(oldTimeout);
                    }
                    catch (IOException e3) {
                        throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), e3, this.getExceptionInterceptor());
                    }
                }
            }
        }
    }
    
    public void checkTransactionState() {
        final int transState = this.serverSession.getTransactionState();
        if (transState == 3) {
            this.transactionManager.transactionCompleted();
        }
        else if (transState == 2) {
            this.transactionManager.transactionBegun();
        }
    }
    
    @Override
    public NativePacketPayload checkErrorMessage() {
        return this.checkErrorMessage(-1);
    }
    
    private NativePacketPayload checkErrorMessage(final int command) {
        NativePacketPayload resultPacket = null;
        this.serverSession.setStatusFlags(0);
        try {
            resultPacket = this.readMessage(this.reusablePacket);
        }
        catch (CJException ex) {
            throw ex;
        }
        catch (Exception fallThru) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), fallThru, this.getExceptionInterceptor());
        }
        this.checkErrorMessage(resultPacket);
        return resultPacket;
    }
    
    public void checkErrorMessage(final NativePacketPayload resultPacket) {
        resultPacket.setPosition(0);
        final byte statusCode = (byte)resultPacket.readInteger(NativeConstants.IntegerDataType.INT1);
        if (statusCode == -1) {
            int errno = 2000;
            errno = (int)resultPacket.readInteger(NativeConstants.IntegerDataType.INT2);
            String xOpen = null;
            String serverErrorMessage = resultPacket.readString(NativeConstants.StringSelfDataType.STRING_TERM, this.serverSession.getErrorMessageEncoding());
            if (serverErrorMessage.charAt(0) == '#') {
                if (serverErrorMessage.length() > 6) {
                    xOpen = serverErrorMessage.substring(1, 6);
                    serverErrorMessage = serverErrorMessage.substring(6);
                    if (xOpen.equals("HY000")) {
                        xOpen = MysqlErrorNumbers.mysqlToSqlState(errno);
                    }
                }
                else {
                    xOpen = MysqlErrorNumbers.mysqlToSqlState(errno);
                }
            }
            else {
                xOpen = MysqlErrorNumbers.mysqlToSqlState(errno);
            }
            this.clearInputStream();
            final StringBuilder errorBuf = new StringBuilder();
            final String xOpenErrorMessage = MysqlErrorNumbers.get(xOpen);
            final boolean useOnlyServerErrorMessages = this.propertySet.getBooleanProperty(PropertyKey.useOnlyServerErrorMessages).getValue();
            if (!useOnlyServerErrorMessages && xOpenErrorMessage != null) {
                errorBuf.append(xOpenErrorMessage);
                errorBuf.append(Messages.getString("Protocol.0"));
            }
            errorBuf.append(serverErrorMessage);
            if (!useOnlyServerErrorMessages && xOpenErrorMessage != null) {
                errorBuf.append("\"");
            }
            this.appendDeadlockStatusInformation(this.session, xOpen, errorBuf);
            if (xOpen != null) {
                if (xOpen.startsWith("22")) {
                    throw new DataTruncationException(errorBuf.toString(), 0, true, false, 0, 0, errno);
                }
                if (errno == 1820) {
                    throw ExceptionFactory.createException(PasswordExpiredException.class, errorBuf.toString(), this.getExceptionInterceptor());
                }
                if (errno == 1862) {
                    throw ExceptionFactory.createException(ClosedOnExpiredPasswordException.class, errorBuf.toString(), this.getExceptionInterceptor());
                }
            }
            throw ExceptionFactory.createException(errorBuf.toString(), xOpen, errno, false, null, this.getExceptionInterceptor());
        }
    }
    
    private void reclaimLargeSharedSendPacket() {
        if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 1048576) {
            this.sharedSendPacket = new NativePacketPayload(1024);
        }
    }
    
    public void clearInputStream() {
        try {
            int len;
            while ((len = this.socketConnection.getMysqlInput().available()) > 0 && this.socketConnection.getMysqlInput().skip(len) > 0L) {}
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
    }
    
    public void reclaimLargeReusablePacket() {
        if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 1048576) {
            this.reusablePacket = new NativePacketPayload(1024);
        }
    }
    
    public final <T extends Resultset> T sendQueryString(final Query callingQuery, final String query, final String characterEncoding, final int maxRows, final boolean streamResults, final ColumnDefinition cachedMetadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) throws IOException {
        String statementComment = this.queryComment;
        if (this.propertySet.getBooleanProperty(PropertyKey.includeThreadNamesAsStatementComment).getValue()) {
            statementComment = ((statementComment != null) ? (statementComment + ", ") : "") + "java thread: " + Thread.currentThread().getName();
        }
        int packLength = 1 + query.length() * 4 + 2;
        byte[] commentAsBytes = null;
        if (statementComment != null) {
            commentAsBytes = StringUtils.getBytes(statementComment, characterEncoding);
            packLength += commentAsBytes.length;
            packLength += 6;
        }
        final NativePacketPayload sendPacket = new NativePacketPayload(packLength);
        sendPacket.setPosition(0);
        sendPacket.writeInteger(NativeConstants.IntegerDataType.INT1, 3L);
        if (commentAsBytes != null) {
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, Constants.SLASH_STAR_SPACE_AS_BYTES);
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, commentAsBytes);
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
        }
        if (!this.platformDbCharsetMatches && StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(query));
        }
        else {
            sendPacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, StringUtils.getBytes(query, characterEncoding));
        }
        return this.sendQueryPacket(callingQuery, sendPacket, maxRows, streamResults, cachedMetadata, resultSetFactory);
    }
    
    public final <T extends Resultset> T sendQueryPacket(final Query callingQuery, final NativePacketPayload queryPacket, final int maxRows, final boolean streamResults, final ColumnDefinition cachedMetadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) throws IOException {
        final long queryStartTime = this.getCurrentTimeNanosOrMillis();
        ++this.statementExecutionDepth;
        final byte[] queryBuf = queryPacket.getByteBuffer();
        final int oldPacketPosition = queryPacket.getPosition();
        final LazyString query = new LazyString(queryBuf, 1, oldPacketPosition - 1);
        try {
            if (this.queryInterceptors != null) {
                final T interceptedResults = this.invokeQueryInterceptorsPre(query, callingQuery, false);
                if (interceptedResults != null) {
                    return interceptedResults;
                }
            }
            if (this.autoGenerateTestcaseScript) {
                final StringBuilder debugBuf = new StringBuilder(query.length() + 32);
                this.generateQueryCommentBlock(debugBuf);
                debugBuf.append(query);
                debugBuf.append(';');
                TestUtils.dumpTestcaseQuery(debugBuf.toString());
            }
            final NativePacketPayload resultPacket = this.sendCommand(queryPacket, false, 0);
            final long queryEndTime = this.getCurrentTimeNanosOrMillis();
            final long queryDuration = queryEndTime - queryStartTime;
            if (callingQuery != null) {
                callingQuery.setExecuteTime(queryDuration);
            }
            final boolean queryWasSlow = this.logSlowQueries && (this.useAutoSlowLog ? this.metricsHolder.checkAbonormallyLongQuery(queryDuration) : (queryDuration > this.propertySet.getIntegerProperty(PropertyKey.slowQueryThresholdMillis).getValue()));
            final long fetchBeginTime = this.profileSQL ? this.getCurrentTimeNanosOrMillis() : 0L;
            T rs = this.readAllResults(maxRows, streamResults, resultPacket, false, cachedMetadata, resultSetFactory);
            if (this.profileSQL || queryWasSlow) {
                final long fetchEndTime = this.profileSQL ? this.getCurrentTimeNanosOrMillis() : 0L;
                final boolean truncated = oldPacketPosition > this.maxQuerySizeToLog.getValue();
                final int extractPosition = truncated ? (this.maxQuerySizeToLog.getValue() + 1) : oldPacketPosition;
                String extractedQuery = StringUtils.toString(queryBuf, 1, extractPosition - 1);
                if (truncated) {
                    extractedQuery += Messages.getString("Protocol.2");
                }
                final ProfilerEventHandler eventSink = this.session.getProfilerEventHandler();
                if (this.logSlowQueries) {
                    if (queryWasSlow) {
                        eventSink.processEvent((byte)6, this.session, callingQuery, rs, queryDuration, new Throwable(), Messages.getString("Protocol.SlowQuery", new Object[] { this.useAutoSlowLog ? " 95% of all queries " : String.valueOf(this.slowQueryThreshold), this.queryTimingUnits, queryDuration, extractedQuery }));
                        if (this.propertySet.getBooleanProperty(PropertyKey.explainSlowQueries).getValue()) {
                            if (oldPacketPosition < 1048576) {
                                queryPacket.setPosition(1);
                                this.explainSlowQuery(query.toString(), extractedQuery);
                            }
                            else {
                                this.log.logWarn(Messages.getString("Protocol.3", new Object[] { 1048576 }));
                            }
                        }
                    }
                    if (this.serverSession.noGoodIndexUsed()) {
                        eventSink.processEvent((byte)6, this.session, callingQuery, rs, queryDuration, new Throwable(), Messages.getString("Protocol.4") + extractedQuery);
                    }
                    if (this.serverSession.noIndexUsed()) {
                        eventSink.processEvent((byte)6, this.session, callingQuery, rs, queryDuration, new Throwable(), Messages.getString("Protocol.5") + extractedQuery);
                    }
                    if (this.serverSession.queryWasSlow()) {
                        eventSink.processEvent((byte)6, this.session, callingQuery, rs, queryDuration, new Throwable(), Messages.getString("Protocol.ServerSlowQuery") + extractedQuery);
                    }
                }
                if (this.profileSQL) {
                    eventSink.processEvent((byte)3, this.session, callingQuery, rs, queryDuration, new Throwable(), extractedQuery);
                    eventSink.processEvent((byte)5, this.session, callingQuery, rs, fetchEndTime - fetchBeginTime, new Throwable(), null);
                }
            }
            if (this.hadWarnings) {
                this.scanForAndThrowDataTruncation();
            }
            if (this.queryInterceptors != null) {
                rs = this.invokeQueryInterceptorsPost(query, callingQuery, rs, false);
            }
            return rs;
        }
        catch (CJException sqlEx) {
            if (this.queryInterceptors != null) {
                this.invokeQueryInterceptorsPost(query, callingQuery, (Resultset)null, false);
            }
            if (callingQuery != null) {
                callingQuery.checkCancelTimeout();
            }
            throw sqlEx;
        }
        finally {
            --this.statementExecutionDepth;
        }
    }
    
    public <T extends Resultset> T invokeQueryInterceptorsPre(final Supplier<String> sql, final Query interceptedQuery, final boolean forceExecute) {
        T previousResultSet = null;
        for (int i = 0, s = this.queryInterceptors.size(); i < s; ++i) {
            final QueryInterceptor interceptor = this.queryInterceptors.get(i);
            final boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
            final boolean shouldExecute = (executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly;
            if (shouldExecute) {
                final T interceptedResultSet = interceptor.preProcess(sql, interceptedQuery);
                if (interceptedResultSet != null) {
                    previousResultSet = interceptedResultSet;
                }
            }
        }
        return previousResultSet;
    }
    
    public <M extends Message> M invokeQueryInterceptorsPre(final M queryPacket, final boolean forceExecute) {
        M previousPacketPayload = null;
        for (int i = 0, s = this.queryInterceptors.size(); i < s; ++i) {
            final QueryInterceptor interceptor = this.queryInterceptors.get(i);
            final M interceptedPacketPayload = interceptor.preProcess(queryPacket);
            if (interceptedPacketPayload != null) {
                previousPacketPayload = interceptedPacketPayload;
            }
        }
        return previousPacketPayload;
    }
    
    public <T extends Resultset> T invokeQueryInterceptorsPost(final Supplier<String> sql, final Query interceptedQuery, T originalResultSet, final boolean forceExecute) {
        for (int i = 0, s = this.queryInterceptors.size(); i < s; ++i) {
            final QueryInterceptor interceptor = this.queryInterceptors.get(i);
            final boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
            final boolean shouldExecute = (executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly;
            if (shouldExecute) {
                final T interceptedResultSet = interceptor.postProcess(sql, interceptedQuery, originalResultSet, this.serverSession);
                if (interceptedResultSet != null) {
                    originalResultSet = interceptedResultSet;
                }
            }
        }
        return originalResultSet;
    }
    
    public <M extends Message> M invokeQueryInterceptorsPost(final M queryPacket, M originalResponsePacket, final boolean forceExecute) {
        for (int i = 0, s = this.queryInterceptors.size(); i < s; ++i) {
            final QueryInterceptor interceptor = this.queryInterceptors.get(i);
            final M interceptedPacketPayload = interceptor.postProcess(queryPacket, originalResponsePacket);
            if (interceptedPacketPayload != null) {
                originalResponsePacket = interceptedPacketPayload;
            }
        }
        return originalResponsePacket;
    }
    
    public long getCurrentTimeNanosOrMillis() {
        return this.useNanosForElapsedTime ? TimeUtil.getCurrentTimeNanosOrMillis() : System.currentTimeMillis();
    }
    
    public boolean hadWarnings() {
        return this.hadWarnings;
    }
    
    public void setHadWarnings(final boolean hadWarnings) {
        this.hadWarnings = hadWarnings;
    }
    
    public void explainSlowQuery(final String query, final String truncatedQuery) {
        if (!StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT")) {
            if (!this.versionMeetsMinimum(5, 6, 3) || StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, NativeProtocol.EXPLAINABLE_STATEMENT_EXTENSION) == -1) {
                return;
            }
        }
        try {
            final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(this.getSharedSendPacket(), "EXPLAIN " + query), false, 0);
            final Resultset rs = this.readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
            final StringBuilder explainResults = new StringBuilder(Messages.getString("Protocol.6"));
            explainResults.append(truncatedQuery);
            explainResults.append(Messages.getString("Protocol.7"));
            this.appendResultSetSlashGStyle(explainResults, rs);
            this.log.logWarn(explainResults.toString());
        }
        catch (CJException sqlEx) {
            throw sqlEx;
        }
        catch (Exception ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex, this.getExceptionInterceptor());
        }
    }
    
    public final void skipPacket() {
        try {
            final int packetLength = this.packetReader.readHeader().getMessageSize();
            this.socketConnection.getMysqlInput().skipFully(packetLength);
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
    }
    
    public final void quit() {
        try {
            try {
                if (!ExportControlled.isSSLEstablished(this.socketConnection.getMysqlSocket()) && !this.socketConnection.getMysqlSocket().isClosed()) {
                    try {
                        this.socketConnection.getMysqlSocket().shutdownInput();
                    }
                    catch (UnsupportedOperationException ex) {}
                }
            }
            catch (IOException ex2) {}
            this.packetSequence = -1;
            final NativePacketPayload packet = new NativePacketPayload(1);
            this.send(this.commandBuilder.buildComQuit(packet), packet.getPosition());
        }
        finally {
            this.socketConnection.forceClose();
            this.localInfileInputStream = null;
        }
    }
    
    public NativePacketPayload getSharedSendPacket() {
        if (this.sharedSendPacket == null) {
            this.sharedSendPacket = new NativePacketPayload(1024);
        }
        this.sharedSendPacket.setPosition(0);
        return this.sharedSendPacket;
    }
    
    private void calculateSlowQueryThreshold() {
        this.slowQueryThreshold = this.propertySet.getIntegerProperty(PropertyKey.slowQueryThresholdMillis).getValue();
        if (this.propertySet.getBooleanProperty(PropertyKey.useNanosForElapsedTime).getValue()) {
            final long nanosThreshold = this.propertySet.getLongProperty(PropertyKey.slowQueryThresholdNanos).getValue();
            if (nanosThreshold != 0L) {
                this.slowQueryThreshold = nanosThreshold;
            }
            else {
                this.slowQueryThreshold *= 1000000L;
            }
        }
    }
    
    @Override
    public void changeUser(final String user, final String password, final String database) {
        this.packetSequence = -1;
        this.packetSender = this.packetSender.undecorateAll();
        this.packetReader = this.packetReader.undecorateAll();
        this.authProvider.changeUser(this.serverSession, user, password, database);
    }
    
    public void checkForCharsetMismatch() {
        final String characterEncoding = this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (characterEncoding != null) {
            String encodingToCheck = NativeProtocol.jvmPlatformCharset;
            if (encodingToCheck == null) {
                encodingToCheck = Constants.PLATFORM_ENCODING;
            }
            if (encodingToCheck == null) {
                this.platformDbCharsetMatches = false;
            }
            else {
                this.platformDbCharsetMatches = encodingToCheck.equals(characterEncoding);
            }
        }
    }
    
    protected boolean useNanosForElapsedTime() {
        return this.useNanosForElapsedTime;
    }
    
    public long getSlowQueryThreshold() {
        return this.slowQueryThreshold;
    }
    
    public int getCommandCount() {
        return this.commandCount;
    }
    
    public void setQueryInterceptors(final List<QueryInterceptor> queryInterceptors) {
        this.queryInterceptors = (queryInterceptors.isEmpty() ? null : queryInterceptors);
    }
    
    public List<QueryInterceptor> getQueryInterceptors() {
        return this.queryInterceptors;
    }
    
    public void setSocketTimeout(final int milliseconds) {
        try {
            final Socket soc = this.socketConnection.getMysqlSocket();
            if (soc != null) {
                soc.setSoTimeout(milliseconds);
            }
        }
        catch (IOException e) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("Protocol.8"), e, this.getExceptionInterceptor());
        }
    }
    
    public void releaseResources() {
        if (this.compressedPacketSender != null) {
            this.compressedPacketSender.stop();
        }
    }
    
    @Override
    public void connect(final String user, final String password, final String database) {
        this.beforeHandshake();
        this.authProvider.connect(this.serverSession, user, password, database);
    }
    
    protected boolean isDataAvailable() {
        try {
            return this.socketConnection.getMysqlInput().available() > 0;
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createCommunicationsException(this.propertySet, this.serverSession, this.getPacketSentTimeHolder(), this.getPacketReceivedTimeHolder(), ioEx, this.getExceptionInterceptor());
        }
    }
    
    public NativePacketPayload getReusablePacket() {
        return this.reusablePacket;
    }
    
    public int getWarningCount() {
        return this.warningCount;
    }
    
    public void setWarningCount(final int warningCount) {
        this.warningCount = warningCount;
    }
    
    public void dumpPacketRingBuffer() {
        final LinkedList<StringBuilder> localPacketDebugRingBuffer = this.packetDebugRingBuffer;
        if (localPacketDebugRingBuffer != null) {
            final StringBuilder dumpBuffer = new StringBuilder();
            dumpBuffer.append("Last " + localPacketDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n");
            dumpBuffer.append("\n");
            final Iterator<StringBuilder> ringBufIter = localPacketDebugRingBuffer.iterator();
            while (ringBufIter.hasNext()) {
                dumpBuffer.append((CharSequence)ringBufIter.next());
                dumpBuffer.append("\n");
            }
            this.log.logTrace(dumpBuffer.toString());
        }
    }
    
    public boolean doesPlatformDbCharsetMatches() {
        return this.platformDbCharsetMatches;
    }
    
    @Override
    public String getPasswordCharacterEncoding() {
        String encoding;
        if ((encoding = this.propertySet.getStringProperty(PropertyKey.passwordCharacterEncoding).getStringValue()) != null) {
            return encoding;
        }
        if ((encoding = this.propertySet.getStringProperty(PropertyKey.characterEncoding).getValue()) != null) {
            return encoding;
        }
        return "UTF-8";
    }
    
    @Override
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) {
        return this.serverSession.getServerVersion().meetsMinimum(new ServerVersion(major, minor, subminor));
    }
    
    public static MysqlType findMysqlType(final PropertySet propertySet, final int mysqlTypeId, final short colFlag, final long length, final LazyString tableName, final LazyString originalTableName, final int collationIndex, final String encoding) {
        final boolean isUnsigned = (colFlag & 0x20) > 0;
        final boolean isFromFunction = originalTableName.length() == 0;
        final boolean isBinary = (colFlag & 0x80) > 0;
        final boolean isImplicitTemporaryTable = tableName.length() > 0 && tableName.toString().startsWith("#sql_");
        final boolean isOpaqueBinary = (isBinary && collationIndex == 63 && (mysqlTypeId == 254 || mysqlTypeId == 253 || mysqlTypeId == 15)) ? (!isImplicitTemporaryTable) : "binary".equalsIgnoreCase(encoding);
        switch (mysqlTypeId) {
            case 0:
            case 246: {
                return isUnsigned ? MysqlType.DECIMAL_UNSIGNED : MysqlType.DECIMAL;
            }
            case 1: {
                if (isUnsigned || length != 1L || !propertySet.getBooleanProperty(PropertyKey.tinyInt1isBit).getValue()) {
                    return isUnsigned ? MysqlType.TINYINT_UNSIGNED : MysqlType.TINYINT;
                }
                if (propertySet.getBooleanProperty(PropertyKey.transformedBitIsBoolean).getValue()) {
                    return MysqlType.BOOLEAN;
                }
                return MysqlType.BIT;
            }
            case 2: {
                return isUnsigned ? MysqlType.SMALLINT_UNSIGNED : MysqlType.SMALLINT;
            }
            case 3: {
                return isUnsigned ? MysqlType.INT_UNSIGNED : MysqlType.INT;
            }
            case 4: {
                return isUnsigned ? MysqlType.FLOAT_UNSIGNED : MysqlType.FLOAT;
            }
            case 5: {
                return isUnsigned ? MysqlType.DOUBLE_UNSIGNED : MysqlType.DOUBLE;
            }
            case 6: {
                return MysqlType.NULL;
            }
            case 7: {
                return MysqlType.TIMESTAMP;
            }
            case 8: {
                return isUnsigned ? MysqlType.BIGINT_UNSIGNED : MysqlType.BIGINT;
            }
            case 9: {
                return isUnsigned ? MysqlType.MEDIUMINT_UNSIGNED : MysqlType.MEDIUMINT;
            }
            case 10: {
                return MysqlType.DATE;
            }
            case 11: {
                return MysqlType.TIME;
            }
            case 12: {
                return MysqlType.DATETIME;
            }
            case 13: {
                return MysqlType.YEAR;
            }
            case 15:
            case 253: {
                if (isOpaqueBinary && (!isFromFunction || !propertySet.getBooleanProperty(PropertyKey.functionsNeverReturnBlobs).getValue())) {
                    return MysqlType.VARBINARY;
                }
                return MysqlType.VARCHAR;
            }
            case 16: {
                return MysqlType.BIT;
            }
            case 245: {
                return MysqlType.JSON;
            }
            case 247: {
                return MysqlType.ENUM;
            }
            case 248: {
                return MysqlType.SET;
            }
            case 249: {
                if (!isBinary || collationIndex != 63 || propertySet.getBooleanProperty(PropertyKey.blobsAreStrings).getValue() || (isFromFunction && propertySet.getBooleanProperty(PropertyKey.functionsNeverReturnBlobs).getValue())) {
                    return MysqlType.TINYTEXT;
                }
                return MysqlType.TINYBLOB;
            }
            case 250: {
                if (!isBinary || collationIndex != 63 || propertySet.getBooleanProperty(PropertyKey.blobsAreStrings).getValue() || (isFromFunction && propertySet.getBooleanProperty(PropertyKey.functionsNeverReturnBlobs).getValue())) {
                    return MysqlType.MEDIUMTEXT;
                }
                return MysqlType.MEDIUMBLOB;
            }
            case 251: {
                if (!isBinary || collationIndex != 63 || propertySet.getBooleanProperty(PropertyKey.blobsAreStrings).getValue() || (isFromFunction && propertySet.getBooleanProperty(PropertyKey.functionsNeverReturnBlobs).getValue())) {
                    return MysqlType.LONGTEXT;
                }
                return MysqlType.LONGBLOB;
            }
            case 252: {
                int newMysqlTypeId = mysqlTypeId;
                if (length <= MysqlType.TINYBLOB.getPrecision()) {
                    newMysqlTypeId = 249;
                }
                else if (length <= MysqlType.BLOB.getPrecision()) {
                    if (!isBinary || collationIndex != 63 || propertySet.getBooleanProperty(PropertyKey.blobsAreStrings).getValue() || (isFromFunction && propertySet.getBooleanProperty(PropertyKey.functionsNeverReturnBlobs).getValue())) {
                        newMysqlTypeId = 15;
                        return MysqlType.TEXT;
                    }
                    return MysqlType.BLOB;
                }
                else if (length <= MysqlType.MEDIUMBLOB.getPrecision()) {
                    newMysqlTypeId = 250;
                }
                else {
                    newMysqlTypeId = 251;
                }
                return findMysqlType(propertySet, newMysqlTypeId, colFlag, length, tableName, originalTableName, collationIndex, encoding);
            }
            case 254: {
                if (isOpaqueBinary && !propertySet.getBooleanProperty(PropertyKey.blobsAreStrings).getValue()) {
                    return MysqlType.BINARY;
                }
                return MysqlType.CHAR;
            }
            case 255: {
                return MysqlType.GEOMETRY;
            }
            default: {
                return MysqlType.UNKNOWN;
            }
        }
    }
    
    @Override
    public <T extends ProtocolEntity> T read(final Class<T> requiredClass, final ProtocolEntityFactory<T, NativePacketPayload> protocolEntityFactory) throws IOException {
        final ProtocolEntityReader<T, NativePacketPayload> sr = (ProtocolEntityReader<T, NativePacketPayload>)this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER.get(requiredClass);
        if (sr == null) {
            throw ExceptionFactory.createException(FeatureNotAvailableException.class, "ProtocolEntityReader isn't available for class " + requiredClass);
        }
        return sr.read(protocolEntityFactory);
    }
    
    @Override
    public <T extends ProtocolEntity> T read(final Class<Resultset> requiredClass, final int maxRows, final boolean streamResults, final NativePacketPayload resultPacket, final boolean isBinaryEncoded, final ColumnDefinition metadata, final ProtocolEntityFactory<T, NativePacketPayload> protocolEntityFactory) throws IOException {
        final ProtocolEntityReader<T, NativePacketPayload> sr = (ProtocolEntityReader<T, NativePacketPayload>)(isBinaryEncoded ? this.PROTOCOL_ENTITY_CLASS_TO_BINARY_READER.get(requiredClass) : this.PROTOCOL_ENTITY_CLASS_TO_TEXT_READER.get(requiredClass));
        if (sr == null) {
            throw ExceptionFactory.createException(FeatureNotAvailableException.class, "ProtocolEntityReader isn't available for class " + requiredClass);
        }
        return sr.read(maxRows, streamResults, resultPacket, metadata, protocolEntityFactory);
    }
    
    public <T extends ProtocolEntity> T readNextResultset(final T currentProtocolEntity, final int maxRows, final boolean streamResults, final boolean isBinaryEncoded, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) throws IOException {
        T result = null;
        if (Resultset.class.isAssignableFrom(currentProtocolEntity.getClass()) && this.serverSession.useMultiResults() && this.serverSession.hasMoreResults()) {
            T currentResultSet = currentProtocolEntity;
            do {
                final NativePacketPayload fieldPacket = this.checkErrorMessage();
                fieldPacket.setPosition(0);
                final T newResultSet = this.read(Resultset.class, maxRows, streamResults, fieldPacket, isBinaryEncoded, null, resultSetFactory);
                ((Resultset)currentResultSet).setNextResultset((Resultset)newResultSet);
                currentResultSet = newResultSet;
                if (result == null) {
                    result = currentResultSet;
                }
            } while (streamResults && this.serverSession.hasMoreResults() && !((Resultset)currentResultSet).hasRows());
        }
        return result;
    }
    
    public <T extends Resultset> T readAllResults(final int maxRows, final boolean streamResults, final NativePacketPayload resultPacket, final boolean isBinaryEncoded, final ColumnDefinition metadata, final ProtocolEntityFactory<T, NativePacketPayload> resultSetFactory) throws IOException {
        resultPacket.setPosition(0);
        final T topLevelResultSet = this.read(Resultset.class, maxRows, streamResults, resultPacket, isBinaryEncoded, metadata, resultSetFactory);
        if (this.serverSession.hasMoreResults()) {
            T currentResultSet = topLevelResultSet;
            if (streamResults) {
                currentResultSet = this.readNextResultset(currentResultSet, maxRows, true, isBinaryEncoded, resultSetFactory);
            }
            else {
                while (this.serverSession.hasMoreResults()) {
                    currentResultSet = this.readNextResultset(currentResultSet, maxRows, false, isBinaryEncoded, resultSetFactory);
                }
                this.clearInputStream();
            }
        }
        if (this.hadWarnings) {
            this.scanForAndThrowDataTruncation();
        }
        this.reclaimLargeReusablePacket();
        return topLevelResultSet;
    }
    
    public final <T> T readServerStatusForResultSets(final NativePacketPayload rowPacket, final boolean saveOldStatus) {
        T result = null;
        if (rowPacket.isEOFPacket()) {
            rowPacket.readInteger(NativeConstants.IntegerDataType.INT1);
            this.warningCount = (int)rowPacket.readInteger(NativeConstants.IntegerDataType.INT2);
            if (this.warningCount > 0) {
                this.hadWarnings = true;
            }
            this.serverSession.setStatusFlags((int)rowPacket.readInteger(NativeConstants.IntegerDataType.INT2), saveOldStatus);
            this.checkTransactionState();
        }
        else {
            final OkPacket ok = (OkPacket)(result = (T)OkPacket.parse(rowPacket, this.serverSession.getErrorMessageEncoding()));
            this.serverSession.setStatusFlags(ok.getStatusFlags(), saveOldStatus);
            this.checkTransactionState();
            this.warningCount = ok.getWarningCount();
            if (this.warningCount > 0) {
                this.hadWarnings = true;
            }
        }
        return result;
    }
    
    @Override
    public <T extends QueryResult> T readQueryResult(final ResultBuilder<T> resultBuilder) {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public InputStream getLocalInfileInputStream() {
        return this.localInfileInputStream;
    }
    
    @Override
    public void setLocalInfileInputStream(final InputStream stream) {
        this.localInfileInputStream = stream;
    }
    
    public final NativePacketPayload sendFileToServer(final String fileName) {
        NativePacketPayload filePacket = (this.loadFileBufRef == null) ? null : this.loadFileBufRef.get();
        final int bigPacketLength = Math.min(this.maxAllowedPacket.getValue() - 12, this.alignPacketSize(this.maxAllowedPacket.getValue() - 16, 4096) - 12);
        final int oneMeg = 1048576;
        final int smallerPacketSizeAligned = Math.min(oneMeg - 12, this.alignPacketSize(oneMeg - 16, 4096) - 12);
        final int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
        if (filePacket == null) {
            try {
                filePacket = new NativePacketPayload(packetLength);
                this.loadFileBufRef = new SoftReference<NativePacketPayload>(filePacket);
            }
            catch (OutOfMemoryError oom) {
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.111", new Object[] { packetLength }), "HY001", 0, false, oom, this.exceptionInterceptor);
            }
        }
        filePacket.setPosition(0);
        final byte[] fileBuf = new byte[packetLength];
        BufferedInputStream fileIn = null;
        try {
            fileIn = this.getFileStream(fileName);
            int bytesRead = 0;
            while ((bytesRead = fileIn.read(fileBuf)) != -1) {
                filePacket.setPosition(0);
                filePacket.writeBytes(NativeConstants.StringLengthDataType.STRING_FIXED, fileBuf, 0, bytesRead);
                this.send(filePacket, filePacket.getPosition());
            }
        }
        catch (IOException ioEx) {
            final boolean isParanoid = this.propertySet.getBooleanProperty(PropertyKey.paranoid).getValue();
            final StringBuilder messageBuf = new StringBuilder(Messages.getString("MysqlIO.62"));
            if (fileName != null && !isParanoid) {
                messageBuf.append("'");
                messageBuf.append(fileName);
                messageBuf.append("'");
            }
            messageBuf.append(Messages.getString("MysqlIO.63"));
            if (!isParanoid) {
                messageBuf.append(Messages.getString("MysqlIO.64"));
                messageBuf.append(Util.stackTraceToString(ioEx));
            }
            throw ExceptionFactory.createException(messageBuf.toString(), ioEx, this.exceptionInterceptor);
        }
        finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                }
                catch (Exception ex) {
                    throw ExceptionFactory.createException(Messages.getString("MysqlIO.65"), ex, this.exceptionInterceptor);
                }
                fileIn = null;
            }
            else {
                filePacket.setPosition(0);
                this.send(filePacket, filePacket.getPosition());
                this.checkErrorMessage();
            }
        }
        filePacket.setPosition(0);
        this.send(filePacket, filePacket.getPosition());
        return this.checkErrorMessage();
    }
    
    private BufferedInputStream getFileStream(final String fileName) throws IOException {
        final RuntimeProperty<Boolean> allowLoadLocalInfile = this.propertySet.getBooleanProperty(PropertyKey.allowLoadLocalInfile);
        final RuntimeProperty<String> allowLoadLocaInfileInPath = this.propertySet.getStringProperty(PropertyKey.allowLoadLocalInfileInPath);
        final RuntimeProperty<Boolean> allowUrlInLocalInfile = this.propertySet.getBooleanProperty(PropertyKey.allowUrlInLocalInfile);
        if (!allowLoadLocalInfile.getValue() && !allowLoadLocaInfileInPath.isExplicitlySet()) {
            throw ExceptionFactory.createException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), this.exceptionInterceptor);
        }
        if (allowLoadLocalInfile.getValue()) {
            final InputStream hookedStream = this.getLocalInfileInputStream();
            if (hookedStream != null) {
                return new BufferedInputStream(hookedStream);
            }
            if (allowUrlInLocalInfile.getValue() && fileName.indexOf(58) != -1) {
                try {
                    final URL urlFromFileName = new URL(fileName);
                    return new BufferedInputStream(urlFromFileName.openStream());
                }
                catch (MalformedURLException ex2) {}
            }
            return new BufferedInputStream(new FileInputStream(fileName));
        }
        else {
            final String safePathValue = allowLoadLocaInfileInPath.getValue();
            if (safePathValue.length() == 0) {
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.60", new Object[] { safePathValue, PropertyKey.allowLoadLocalInfileInPath.getKeyName() }), this.exceptionInterceptor);
            }
            Path safePath;
            try {
                safePath = Paths.get(safePathValue, new String[0]).toRealPath(new LinkOption[0]);
            }
            catch (IOException | InvalidPathException ex3) {
                final Exception ex;
                final Exception e = ex;
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.60", new Object[] { safePathValue, PropertyKey.allowLoadLocalInfileInPath.getKeyName() }), e, this.exceptionInterceptor);
            }
            if (allowUrlInLocalInfile.getValue()) {
                try {
                    final URL urlFromFileName2 = new URL(fileName);
                    if (!urlFromFileName2.getProtocol().equalsIgnoreCase("file")) {
                        throw ExceptionFactory.createException(Messages.getString("MysqlIO.66", new Object[] { urlFromFileName2.getProtocol() }), this.exceptionInterceptor);
                    }
                    try {
                        final InetAddress addr = InetAddress.getByName(urlFromFileName2.getHost());
                        if (!addr.isLoopbackAddress()) {
                            throw ExceptionFactory.createException(Messages.getString("MysqlIO.67", new Object[] { urlFromFileName2.getHost() }), this.exceptionInterceptor);
                        }
                    }
                    catch (UnknownHostException e2) {
                        throw ExceptionFactory.createException(Messages.getString("MysqlIO.68", new Object[] { fileName }), e2, this.exceptionInterceptor);
                    }
                    Path filePath = null;
                    try {
                        filePath = Paths.get(urlFromFileName2.toURI()).toRealPath(new LinkOption[0]);
                    }
                    catch (InvalidPathException e3) {
                        String pathString = urlFromFileName2.getPath();
                        if (pathString.indexOf(58) != -1 && (pathString.startsWith("/") || pathString.startsWith("\\"))) {
                            pathString = pathString.replaceFirst("^[/\\\\]*", "");
                        }
                        filePath = Paths.get(pathString, new String[0]).toRealPath(new LinkOption[0]);
                    }
                    catch (IllegalArgumentException e4) {
                        filePath = Paths.get(urlFromFileName2.getPath(), new String[0]).toRealPath(new LinkOption[0]);
                    }
                    if (!filePath.startsWith(safePath)) {
                        throw ExceptionFactory.createException(Messages.getString("MysqlIO.61", new Object[] { filePath, safePath }), this.exceptionInterceptor);
                    }
                    return new BufferedInputStream(urlFromFileName2.openStream());
                }
                catch (MalformedURLException ex4) {}
                catch (URISyntaxException ex5) {}
            }
            final Path filePath2 = Paths.get(fileName, new String[0]).toRealPath(new LinkOption[0]);
            if (!filePath2.startsWith(safePath)) {
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.61", new Object[] { filePath2, safePath }), this.exceptionInterceptor);
            }
            return new BufferedInputStream(new FileInputStream(filePath2.toFile()));
        }
    }
    
    private int alignPacketSize(final int a, final int l) {
        return a + l - 1 & ~(l - 1);
    }
    
    public ResultsetRows getStreamingData() {
        return this.streamingData;
    }
    
    public void setStreamingData(final ResultsetRows streamingData) {
        this.streamingData = streamingData;
    }
    
    public void checkForOutstandingStreamingData() {
        if (this.streamingData != null) {
            final boolean shouldClobber = this.propertySet.getBooleanProperty(PropertyKey.clobberStreamingResults).getValue();
            if (!shouldClobber) {
                throw ExceptionFactory.createException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"), this.exceptionInterceptor);
            }
            this.streamingData.getOwner().closeOwner(false);
            this.clearInputStream();
        }
    }
    
    public void unsetStreamingData(final ResultsetRows streamer) {
        if (this.streamingData == null) {
            throw ExceptionFactory.createException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"), this.exceptionInterceptor);
        }
        if (streamer == this.streamingData) {
            this.streamingData = null;
        }
    }
    
    public void scanForAndThrowDataTruncation() {
        if (this.streamingData == null && this.propertySet.getBooleanProperty(PropertyKey.jdbcCompliantTruncation).getValue() && this.getWarningCount() > 0) {
            final int warningCountOld = this.getWarningCount();
            this.convertShowWarningsToSQLWarnings(this.getWarningCount(), true);
            this.setWarningCount(warningCountOld);
        }
    }
    
    public StringBuilder generateQueryCommentBlock(final StringBuilder buf) {
        buf.append("/* conn id ");
        buf.append(this.getServerSession().getCapabilities().getThreadId());
        buf.append(" clock: ");
        buf.append(System.currentTimeMillis());
        buf.append(" */ ");
        return buf;
    }
    
    public BaseMetricsHolder getMetricsHolder() {
        return this.metricsHolder;
    }
    
    @Override
    public String getQueryComment() {
        return this.queryComment;
    }
    
    @Override
    public void setQueryComment(final String comment) {
        this.queryComment = comment;
    }
    
    private void appendDeadlockStatusInformation(final Session sess, final String xOpen, final StringBuilder errorBuf) {
        if (sess.getPropertySet().getBooleanProperty(PropertyKey.includeInnodbStatusInDeadlockExceptions).getValue() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && this.getStreamingData() == null) {
            try {
                final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(this.getSharedSendPacket(), "SHOW ENGINE INNODB STATUS"), false, 0);
                final Resultset rs = this.readAllResults(-1, false, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, null));
                int colIndex = 0;
                Field f = null;
                for (int i = 0; i < rs.getColumnDefinition().getFields().length; ++i) {
                    f = rs.getColumnDefinition().getFields()[i];
                    if ("Status".equals(f.getName())) {
                        colIndex = i;
                        break;
                    }
                }
                final ValueFactory<String> vf = new StringValueFactory(this.propertySet);
                final Row r;
                if ((r = rs.getRows().next()) != null) {
                    errorBuf.append("\n\n").append(r.getValue(colIndex, vf));
                }
                else {
                    errorBuf.append("\n\n").append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
                }
            }
            catch (IOException | CJException ex3) {
                final Exception ex2;
                final Exception ex = ex2;
                errorBuf.append("\n\n").append(Messages.getString("MysqlIO.InnoDBStatusFailed")).append("\n\n").append(Util.stackTraceToString(ex));
            }
        }
        if (sess.getPropertySet().getBooleanProperty(PropertyKey.includeThreadDumpInDeadlockExceptions).getValue()) {
            errorBuf.append("\n\n*** Java threads running at time of deadlock ***\n\n");
            final ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
            final long[] threadIds = threadMBean.getAllThreadIds();
            final ThreadInfo[] threads = threadMBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
            final List<ThreadInfo> activeThreads = new ArrayList<ThreadInfo>();
            for (final ThreadInfo info : threads) {
                if (info != null) {
                    activeThreads.add(info);
                }
            }
            for (final ThreadInfo threadInfo : activeThreads) {
                errorBuf.append('\"').append(threadInfo.getThreadName()).append("\" tid=").append(threadInfo.getThreadId()).append(" ").append(threadInfo.getThreadState());
                if (threadInfo.getLockName() != null) {
                    errorBuf.append(" on lock=").append(threadInfo.getLockName());
                }
                if (threadInfo.isSuspended()) {
                    errorBuf.append(" (suspended)");
                }
                if (threadInfo.isInNative()) {
                    errorBuf.append(" (running in native)");
                }
                final StackTraceElement[] stackTrace = threadInfo.getStackTrace();
                if (stackTrace.length > 0) {
                    errorBuf.append(" in ");
                    errorBuf.append(stackTrace[0].getClassName()).append(".");
                    errorBuf.append(stackTrace[0].getMethodName()).append("()");
                }
                errorBuf.append("\n");
                if (threadInfo.getLockOwnerName() != null) {
                    errorBuf.append("\t owned by ").append(threadInfo.getLockOwnerName()).append(" Id=").append(threadInfo.getLockOwnerId()).append("\n");
                }
                for (int j = 0; j < stackTrace.length; ++j) {
                    final StackTraceElement ste = stackTrace[j];
                    errorBuf.append("\tat ").append(ste.toString()).append("\n");
                }
            }
        }
    }
    
    private StringBuilder appendResultSetSlashGStyle(final StringBuilder appendTo, final Resultset rs) {
        final Field[] fields = rs.getColumnDefinition().getFields();
        int maxWidth = 0;
        for (int i = 0; i < fields.length; ++i) {
            if (fields[i].getColumnLabel().length() > maxWidth) {
                maxWidth = fields[i].getColumnLabel().length();
            }
        }
        int rowCount = 1;
        Row r;
        while ((r = rs.getRows().next()) != null) {
            appendTo.append("*************************** ");
            appendTo.append(rowCount++);
            appendTo.append(". row ***************************\n");
            for (int j = 0; j < fields.length; ++j) {
                for (int leftPad = maxWidth - fields[j].getColumnLabel().length(), k = 0; k < leftPad; ++k) {
                    appendTo.append(" ");
                }
                appendTo.append(fields[j].getColumnLabel()).append(": ");
                final String stringVal = r.getValue(j, (ValueFactory<String>)new StringValueFactory(this.propertySet));
                appendTo.append((stringVal != null) ? stringVal : "NULL").append("\n");
            }
            appendTo.append("\n");
        }
        return appendTo;
    }
    
    public SQLWarning convertShowWarningsToSQLWarnings(final int warningCountIfKnown, final boolean forTruncationOnly) {
        SQLWarning currentWarning = null;
        ResultsetRows rows = null;
        try {
            final NativePacketPayload resultPacket = this.sendCommand(this.commandBuilder.buildComQuery(this.getSharedSendPacket(), "SHOW WARNINGS"), false, 0);
            final Resultset warnRs = this.readAllResults(-1, warningCountIfKnown > 99, resultPacket, false, null, (ProtocolEntityFactory<Resultset, NativePacketPayload>)new ResultsetFactory(Resultset.Type.FORWARD_ONLY, Resultset.Concurrency.READ_ONLY));
            final int codeFieldIndex = warnRs.getColumnDefinition().findColumn("Code", false, 1) - 1;
            final int messageFieldIndex = warnRs.getColumnDefinition().findColumn("Message", false, 1) - 1;
            final ValueFactory<String> svf = new StringValueFactory(this.propertySet);
            final ValueFactory<Integer> ivf = new IntegerValueFactory(this.propertySet);
            rows = warnRs.getRows();
            Row r;
            while ((r = rows.next()) != null) {
                final int code = r.getValue(codeFieldIndex, ivf);
                if (forTruncationOnly) {
                    if (code != 1265 && code != 1264) {
                        continue;
                    }
                    final DataTruncation newTruncation = new MysqlDataTruncation(r.getValue(messageFieldIndex, svf), 0, false, false, 0, 0, code);
                    if (currentWarning == null) {
                        currentWarning = newTruncation;
                    }
                    else {
                        currentWarning.setNextWarning(newTruncation);
                    }
                }
                else {
                    final String message = r.getValue(messageFieldIndex, svf);
                    final SQLWarning newWarning = new SQLWarning(message, MysqlErrorNumbers.mysqlToSqlState(code), code);
                    if (currentWarning == null) {
                        currentWarning = newWarning;
                    }
                    else {
                        currentWarning.setNextWarning(newWarning);
                    }
                }
            }
            if (forTruncationOnly && currentWarning != null) {
                throw ExceptionFactory.createException(currentWarning.getMessage(), currentWarning);
            }
            return currentWarning;
        }
        catch (IOException ex) {
            throw ExceptionFactory.createException(ex.getMessage(), ex);
        }
        finally {
            if (rows != null) {
                rows.close();
            }
        }
    }
    
    @Override
    public ColumnDefinition readMetadata() {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void close() throws IOException {
        throw ExceptionFactory.createException(CJOperationNotSupportedException.class, "Not supported");
    }
    
    @Override
    public void configureTimeZone() {
        final String connectionTimeZone = this.getPropertySet().getStringProperty(PropertyKey.connectionTimeZone).getValue();
        TimeZone selectedTz = null;
        if (connectionTimeZone == null || StringUtils.isEmptyOrWhitespaceOnly(connectionTimeZone) || "LOCAL".equals(connectionTimeZone)) {
            selectedTz = TimeZone.getDefault();
        }
        else {
            if ("SERVER".equals(connectionTimeZone)) {
                return;
            }
            selectedTz = TimeZone.getTimeZone(ZoneId.of(connectionTimeZone));
        }
        this.serverSession.setSessionTimeZone(selectedTz);
        if (this.getPropertySet().getBooleanProperty(PropertyKey.forceConnectionTimeZoneToSession).getValue()) {
            final StringBuilder query = new StringBuilder("SET SESSION time_zone='");
            final ZoneId zid = selectedTz.toZoneId().normalized();
            if (zid instanceof ZoneOffset) {
                final String offsetStr = ((ZoneOffset)zid).getId().replace("Z", "+00:00");
                query.append(offsetStr);
                this.serverSession.getServerVariables().put("time_zone", offsetStr);
            }
            else {
                query.append(selectedTz.getID());
                this.serverSession.getServerVariables().put("time_zone", selectedTz.getID());
            }
            query.append("'");
            this.sendCommand(this.commandBuilder.buildComQuery(null, query.toString()), false, 0);
        }
    }
    
    @Override
    public void initServerSession() {
        this.configureTimeZone();
        if (this.session.getServerSession().getServerVariables().containsKey("max_allowed_packet")) {
            final int serverMaxAllowedPacket = this.session.getServerSession().getServerVariable("max_allowed_packet", -1);
            if (serverMaxAllowedPacket != -1 && (!this.maxAllowedPacket.isExplicitlySet() || serverMaxAllowedPacket < this.maxAllowedPacket.getValue())) {
                this.maxAllowedPacket.setValue(serverMaxAllowedPacket);
            }
            if (this.useServerPrepStmts.getValue()) {
                final RuntimeProperty<Integer> blobSendChunkSize = this.propertySet.getProperty(PropertyKey.blobSendChunkSize);
                final int preferredBlobSendChunkSize = blobSendChunkSize.getValue();
                final int packetHeaderSize = 8203;
                final int allowedBlobSendChunkSize = Math.min(preferredBlobSendChunkSize, this.maxAllowedPacket.getValue()) - packetHeaderSize;
                if (allowedBlobSendChunkSize <= 0) {
                    throw ExceptionFactory.createException(Messages.getString("Connection.15", new Object[] { packetHeaderSize }), "01S00", 0, false, null, this.exceptionInterceptor);
                }
                blobSendChunkSize.setValue(allowedBlobSendChunkSize);
            }
        }
    }
    
    static {
        EXPLAINABLE_STATEMENT_EXTENSION = new String[] { "INSERT", "UPDATE", "REPLACE", "DELETE" };
        NativeProtocol.jvmPlatformCharset = null;
        OutputStreamWriter outWriter = null;
        try {
            outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
            NativeProtocol.jvmPlatformCharset = outWriter.getEncoding();
        }
        finally {
            try {
                if (outWriter != null) {
                    outWriter.close();
                }
            }
            catch (IOException ex) {}
        }
    }
}
