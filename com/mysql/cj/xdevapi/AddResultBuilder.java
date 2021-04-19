package com.mysql.cj.xdevapi;

public class AddResultBuilder extends UpdateResultBuilder<AddResult>
{
    @Override
    public AddResult build() {
        return new AddResultImpl(this.statementExecuteOkBuilder.build());
    }
}
