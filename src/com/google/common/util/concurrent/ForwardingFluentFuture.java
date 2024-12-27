package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ForwardingFluentFuture extends FluentFuture {
    public final ListenableFuture delegate;

    public ForwardingFluentFuture(ListenableFuture listenableFuture) {
        listenableFuture.getClass();
        this.delegate = listenableFuture;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture,
              // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        this.delegate.addListener(runnable, executor);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        return this.delegate.cancel(z);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public final Object get() {
        return this.delegate.get();
    }

    @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public final boolean isCancelled() {
        return this.delegate.isCancelled();
    }

    @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public final boolean isDone() {
        return this.delegate.isDone();
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String toString() {
        return this.delegate.toString();
    }

    @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) {
        return this.delegate.get(j, timeUnit);
    }
}
