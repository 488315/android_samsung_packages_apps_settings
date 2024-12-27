package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingObject;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MoreExecutors {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ListeningDecorator extends AbstractExecutorService
            implements ListeningExecutorService {
        public final ExecutorService delegate;

        public ListeningDecorator(ExecutorService executorService) {
            executorService.getClass();
            this.delegate = executorService;
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean awaitTermination(long j, TimeUnit timeUnit) {
            return this.delegate.awaitTermination(j, timeUnit);
        }

        @Override // java.util.concurrent.Executor
        public final void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public final boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        @Override // java.util.concurrent.AbstractExecutorService
        public final RunnableFuture newTaskFor(Callable callable) {
            return new TrustedListenableFutureTask(callable);
        }

        @Override // java.util.concurrent.ExecutorService
        public final void shutdown() {
            this.delegate.shutdown();
        }

        @Override // java.util.concurrent.ExecutorService
        public final List shutdownNow() {
            return this.delegate.shutdownNow();
        }

        @Override // java.util.concurrent.AbstractExecutorService,
                  // java.util.concurrent.ExecutorService
        public final ListenableFuture submit(Runnable runnable) {
            return (ListenableFuture) super.submit(runnable);
        }

        public final String toString() {
            return super.toString() + "[" + this.delegate + "]";
        }

        @Override // java.util.concurrent.AbstractExecutorService
        public final RunnableFuture newTaskFor(Runnable runnable, Object obj) {
            return new TrustedListenableFutureTask(Executors.callable(runnable, obj));
        }

        @Override // java.util.concurrent.AbstractExecutorService,
                  // java.util.concurrent.ExecutorService
        public final Future submit(Runnable runnable) {
            return (ListenableFuture) super.submit(runnable);
        }

        @Override // java.util.concurrent.AbstractExecutorService,
                  // java.util.concurrent.ExecutorService
        public final Future submit(Runnable runnable, Object obj) {
            return (ListenableFuture) super.submit(runnable, obj);
        }

        @Override // java.util.concurrent.AbstractExecutorService,
                  // java.util.concurrent.ExecutorService
        public final ListenableFuture submit(Callable callable) {
            return (ListenableFuture) super.submit(callable);
        }

        @Override // java.util.concurrent.AbstractExecutorService,
                  // java.util.concurrent.ExecutorService
        public final Future submit(Callable callable) {
            return (ListenableFuture) super.submit(callable);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ScheduledListeningDecorator extends ListeningDecorator
            implements ScheduledExecutorService {
        public final ScheduledExecutorService delegate;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class NeverSuccessfulListenableFutureTask extends AbstractFuture.TrustedFuture
                implements Runnable {
            public final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable runnable) {
                runnable.getClass();
                this.delegate = runnable;
            }

            @Override // com.google.common.util.concurrent.AbstractFuture
            public final String pendingToString() {
                return "task=[" + this.delegate + "]";
            }

            @Override // java.lang.Runnable
            public final void run() {
                try {
                    this.delegate.run();
                } catch (Throwable th) {
                    setException(th);
                    throw th;
                }
            }
        }

        public ScheduledListeningDecorator(ScheduledExecutorService scheduledExecutorService) {
            super(scheduledExecutorService);
            this.delegate = scheduledExecutorService;
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        public final ScheduledFuture schedule(Callable callable, long j, TimeUnit timeUnit) {
            TrustedListenableFutureTask trustedListenableFutureTask =
                    new TrustedListenableFutureTask(callable);
            return new ListenableScheduledTask(
                    trustedListenableFutureTask,
                    this.delegate.schedule(trustedListenableFutureTask, j, timeUnit));
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        public final ScheduledFuture scheduleAtFixedRate(
                Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask =
                    new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(
                    neverSuccessfulListenableFutureTask,
                    this.delegate.scheduleAtFixedRate(
                            neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        public final ScheduledFuture scheduleWithFixedDelay(
                Runnable runnable, long j, long j2, TimeUnit timeUnit) {
            NeverSuccessfulListenableFutureTask neverSuccessfulListenableFutureTask =
                    new NeverSuccessfulListenableFutureTask(runnable);
            return new ListenableScheduledTask(
                    neverSuccessfulListenableFutureTask,
                    this.delegate.scheduleWithFixedDelay(
                            neverSuccessfulListenableFutureTask, j, j2, timeUnit));
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class ListenableScheduledTask extends ForwardingObject
                implements ScheduledFuture, ListenableFuture, Future {
            public final ListenableFuture delegate;
            public final ScheduledFuture scheduledDelegate;

            public ListenableScheduledTask(
                    AbstractFuture abstractFuture, ScheduledFuture scheduledFuture) {
                this.delegate = abstractFuture;
                this.scheduledDelegate = scheduledFuture;
            }

            @Override // com.google.common.util.concurrent.ListenableFuture
            public final void addListener(Runnable runnable, Executor executor) {
                this.delegate.addListener(runnable, executor);
            }

            @Override // java.util.concurrent.Future
            public final boolean cancel(boolean z) {
                boolean cancel$com$google$common$util$concurrent$ForwardingFuture =
                        cancel$com$google$common$util$concurrent$ForwardingFuture(z);
                if (cancel$com$google$common$util$concurrent$ForwardingFuture) {
                    this.scheduledDelegate.cancel(z);
                }
                return cancel$com$google$common$util$concurrent$ForwardingFuture;
            }

            public final boolean cancel$com$google$common$util$concurrent$ForwardingFuture(
                    boolean z) {
                return this.delegate.cancel(z);
            }

            @Override // java.lang.Comparable
            public final int compareTo(Delayed delayed) {
                return this.scheduledDelegate.compareTo(delayed);
            }

            @Override // java.util.concurrent.Future
            public final Object get() {
                return this.delegate.get();
            }

            @Override // java.util.concurrent.Delayed
            public final long getDelay(TimeUnit timeUnit) {
                return this.scheduledDelegate.getDelay(timeUnit);
            }

            @Override // java.util.concurrent.Future
            public final boolean isCancelled() {
                return this.delegate.isCancelled();
            }

            @Override // java.util.concurrent.Future
            public final boolean isDone() {
                return this.delegate.isDone();
            }

            @Override // java.util.concurrent.Future
            public final Object get(long j, TimeUnit timeUnit) {
                return this.delegate.get(j, timeUnit);
            }
        }

        @Override // java.util.concurrent.ScheduledExecutorService
        public final ScheduledFuture schedule(Runnable runnable, long j, TimeUnit timeUnit) {
            TrustedListenableFutureTask trustedListenableFutureTask =
                    new TrustedListenableFutureTask(Executors.callable(runnable, null));
            return new ListenableScheduledTask(
                    trustedListenableFutureTask,
                    this.delegate.schedule(trustedListenableFutureTask, j, timeUnit));
        }
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    public static ListeningExecutorService listeningDecorator(ExecutorService executorService) {
        if (executorService instanceof ListeningExecutorService) {
            return (ListeningExecutorService) executorService;
        }
        return executorService instanceof ScheduledExecutorService
                ? new ScheduledListeningDecorator((ScheduledExecutorService) executorService)
                : new ListeningDecorator(executorService);
    }

    public static Executor rejectionPropagatingExecutor(FluentFuture.TrustedFuture trustedFuture) {
        return DirectExecutor.INSTANCE;
    }
}
