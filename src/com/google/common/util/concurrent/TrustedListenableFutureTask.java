package com.google.common.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.locks.LockSupport;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class TrustedListenableFutureTask extends FluentFuture.TrustedFuture
        implements RunnableFuture {
    public volatile TrustedFutureInterruptibleTask task;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    final class TrustedFutureInterruptibleTask extends InterruptibleTask<Object> {
        private final Callable<Object> callable;

        public TrustedFutureInterruptibleTask(Callable callable) {
            callable.getClass();
            this.callable = callable;
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final Object runInterruptibly() {
            return this.callable.call();
        }

        @Override // com.google.common.util.concurrent.InterruptibleTask
        public final String toPendingString() {
            return this.callable.toString();
        }
    }

    public TrustedListenableFutureTask(Callable callable) {
        this.task = new TrustedFutureInterruptibleTask(callable);
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask;
        Object obj = this.value;
        if (((obj instanceof AbstractFuture.Cancellation)
                        && ((AbstractFuture.Cancellation) obj).wasInterrupted)
                && (trustedFutureInterruptibleTask = this.task) != null) {
            Runnable runnable = trustedFutureInterruptibleTask.get();
            if (runnable instanceof Thread) {
                InterruptibleTask.Blocker blocker =
                        new InterruptibleTask.Blocker(trustedFutureInterruptibleTask);
                blocker.setExclusiveOwnerThread(Thread.currentThread());
                if (trustedFutureInterruptibleTask.compareAndSet(runnable, blocker)) {
                    try {
                        ((Thread) runnable).interrupt();
                    } finally {
                        if (trustedFutureInterruptibleTask.getAndSet(InterruptibleTask.DONE)
                                == InterruptibleTask.PARKED) {
                            LockSupport.unpark((Thread) runnable);
                        }
                    }
                }
            }
        }
        this.task = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.task;
        if (trustedFutureInterruptibleTask == null) {
            return super.pendingToString();
        }
        return "task=[" + trustedFutureInterruptibleTask + "]";
    }

    @Override // java.util.concurrent.RunnableFuture, java.lang.Runnable
    public final void run() {
        TrustedFutureInterruptibleTask trustedFutureInterruptibleTask = this.task;
        if (trustedFutureInterruptibleTask != null) {
            trustedFutureInterruptibleTask.run();
        }
        this.task = null;
    }
}
