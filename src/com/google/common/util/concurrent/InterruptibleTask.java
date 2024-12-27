package com.google.common.util.concurrent;

import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.LockSupport;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {
    public static final DoNothingRunnable DONE = new DoNothingRunnable();
    public static final DoNothingRunnable PARKED = new DoNothingRunnable();

    @Override // java.lang.Runnable
    public final void run() {
        Thread currentThread = Thread.currentThread();
        Object obj = null;
        if (compareAndSet(null, currentThread)) {
            TrustedListenableFutureTask.TrustedFutureInterruptibleTask
                    trustedFutureInterruptibleTask =
                            (TrustedListenableFutureTask.TrustedFutureInterruptibleTask) this;
            boolean z = !TrustedListenableFutureTask.this.isDone();
            DoNothingRunnable doNothingRunnable = DONE;
            if (z) {
                try {
                    obj = runInterruptibly();
                } catch (Throwable th) {
                    try {
                        if (th instanceof InterruptedException) {
                            Thread.currentThread().interrupt();
                        }
                        if (!compareAndSet(currentThread, doNothingRunnable)) {
                            waitForInterrupt(currentThread);
                        }
                        if (z) {
                            TrustedListenableFutureTask.this.setException(th);
                            return;
                        }
                        return;
                    } finally {
                        if (!compareAndSet(currentThread, doNothingRunnable)) {
                            waitForInterrupt(currentThread);
                        }
                        if (z) {
                            TrustedListenableFutureTask.this.set(null);
                        }
                    }
                }
            }
        }
    }

    public abstract Object runInterruptibly();

    public abstract String toPendingString();

    @Override // java.util.concurrent.atomic.AtomicReference
    public final String toString() {
        String str;
        Runnable runnable = get();
        if (runnable == DONE) {
            str = "running=[DONE]";
        } else if (runnable instanceof Blocker) {
            str = "running=[INTERRUPTED]";
        } else if (runnable instanceof Thread) {
            str = "running=[RUNNING ON " + ((Thread) runnable).getName() + "]";
        } else {
            str = "running=[NOT STARTED YET]";
        }
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, ", ");
        m.append(toPendingString());
        return m.toString();
    }

    public final void waitForInterrupt(Thread thread) {
        Runnable runnable = get();
        Blocker blocker = null;
        boolean z = false;
        int i = 0;
        while (true) {
            boolean z2 = runnable instanceof Blocker;
            DoNothingRunnable doNothingRunnable = PARKED;
            if (!z2 && runnable != doNothingRunnable) {
                break;
            }
            if (z2) {
                blocker = (Blocker) runnable;
            }
            i++;
            if (i <= 1000) {
                Thread.yield();
            } else if (runnable == doNothingRunnable
                    || compareAndSet(runnable, doNothingRunnable)) {
                z = Thread.interrupted() || z;
                LockSupport.park(blocker);
            }
            runnable = get();
        }
        if (z) {
            thread.interrupt();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class Blocker extends AbstractOwnableSynchronizer implements Runnable {
        private final InterruptibleTask<?> task;

        public Blocker(InterruptibleTask interruptibleTask) {
            this.task = interruptibleTask;
        }

        public final String toString() {
            return this.task.toString();
        }

        @Override // java.lang.Runnable
        public final void run() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DoNothingRunnable implements Runnable {
        @Override // java.lang.Runnable
        public final void run() {}
    }
}
