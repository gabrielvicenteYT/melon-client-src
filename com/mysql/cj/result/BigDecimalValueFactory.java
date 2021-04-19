package com.mysql.cj.result;

import com.mysql.cj.conf.*;
import java.math.*;
import java.nio.*;

public class BigDecimalValueFactory extends AbstractNumericValueFactory<BigDecimal>
{
    int scale;
    boolean hasScale;
    
    public BigDecimalValueFactory(final PropertySet pset) {
        super(pset);
    }
    
    public BigDecimalValueFactory(final PropertySet pset, final int scale) {
        super(pset);
        this.scale = scale;
        this.hasScale = true;
    }
    
    private BigDecimal adjustResult(final BigDecimal d) {
        if (this.hasScale) {
            try {
                return d.setScale(this.scale);
            }
            catch (ArithmeticException ex) {
                return d.setScale(this.scale, 4);
            }
        }
        return d;
    }
    
    @Override
    public BigDecimal createFromBigInteger(final BigInteger i) {
        return this.adjustResult(new BigDecimal(i));
    }
    
    @Override
    public BigDecimal createFromLong(final long l) {
        return this.adjustResult(BigDecimal.valueOf(l));
    }
    
    @Override
    public BigDecimal createFromBigDecimal(final BigDecimal d) {
        return this.adjustResult(d);
    }
    
    @Override
    public BigDecimal createFromDouble(final double d) {
        return this.adjustResult(BigDecimal.valueOf(d));
    }
    
    @Override
    public BigDecimal createFromBit(final byte[] bytes, final int offset, final int length) {
        return new BigDecimal(new BigInteger(ByteBuffer.allocate(length + 1).put((byte)0).put(bytes, offset, length).array()));
    }
    
    @Override
    public String getTargetTypeName() {
        return BigDecimal.class.getName();
    }
}
