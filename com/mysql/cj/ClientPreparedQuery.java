package com.mysql.cj;

public class ClientPreparedQuery extends AbstractPreparedQuery<ClientPreparedQueryBindings>
{
    public ClientPreparedQuery(final NativeSession sess) {
        super(sess);
    }
    
    @Override
    protected long[] computeMaxParameterSetSizeAndBatchSize(final int numBatchedArgs) {
        long sizeOfEntireBatch = 0L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            final ClientPreparedQueryBindings qBindings = this.batchedArgs.get(i);
            final BindValue[] bindValues = ((AbstractQueryBindings<BindValue>)qBindings).getBindValues();
            long sizeOfParameterSet = 0L;
            for (int j = 0; j < bindValues.length; ++j) {
                if (!bindValues[j].isNull()) {
                    if (bindValues[j].isStream()) {
                        final long streamLength = bindValues[j].getStreamLength();
                        if (streamLength != -1L) {
                            sizeOfParameterSet += streamLength * 2L;
                        }
                        else {
                            final int paramLength = qBindings.getBindValues()[j].getByteValue().length;
                            sizeOfParameterSet += paramLength;
                        }
                    }
                    else {
                        sizeOfParameterSet += qBindings.getBindValues()[j].getByteValue().length;
                    }
                }
                else {
                    sizeOfParameterSet += 4L;
                }
            }
            if (this.parseInfo.getValuesClause() != null) {
                sizeOfParameterSet += this.parseInfo.getValuesClause().length() + 1;
            }
            else {
                sizeOfParameterSet += this.originalSql.length() + 1;
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
                maxSizeOfParameterSet = sizeOfParameterSet;
            }
        }
        return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
    }
}
