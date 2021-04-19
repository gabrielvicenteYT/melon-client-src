package com.mysql.cj.protocol.x;

import java.nio.channels.*;
import java.util.concurrent.*;

public class ErrorToFutureCompletionHandler<T> implements CompletionHandler<T, Void>
{
    private CompletableFuture<?> future;
    private Runnable successCallback;
    
    public ErrorToFutureCompletionHandler(final CompletableFuture<?> future, final Runnable successCallback) {
        this.future = future;
        this.successCallback = successCallback;
    }
    
    @Override
    public void completed(final T result, final Void attachment) {
        this.successCallback.run();
    }
    
    @Override
    public void failed(final Throwable ex, final Void attachment) {
        this.future.completeExceptionally(ex);
    }
}
