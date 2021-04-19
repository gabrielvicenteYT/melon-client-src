package com.mysql.cj.xdevapi;

public class InsertResultBuilder extends UpdateResultBuilder<InsertResult>
{
    @Override
    public InsertResult build() {
        return new InsertResultImpl(this.statementExecuteOkBuilder.build());
    }
}
