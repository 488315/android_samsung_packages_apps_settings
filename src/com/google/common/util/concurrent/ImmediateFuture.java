package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ImmediateFuture implements ListenableFuture {
    public static final ImmediateFuture NULL = new ImmediateFuture(null);
    public static final LazyLogger log = new LazyLogger(ImmediateFuture.class);
    public final Object value;

    public ImmediateFuture(Object obj) {
        this.value = obj;
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public final void addListener(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(runnable);
        } catch (Exception e) {
            log.get()
                    .log(
                            Level.SEVERE,
                            "RuntimeException while executing runnable "
                                    + runnable
                                    + " with executor "
                                    + executor,
                            (Throwable) e);
        }
    }

    @Override // java.util.concurrent.Future
    public final boolean cancel(boolean z) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public final Object get() {
        return this.value;
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return true;
    }

    public final String toString() {
        return super.toString() + "[status=SUCCESS, result=[" + this.value + "]]";
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) {
        timeUnit.getClass();
        return this.value;
    }
}
