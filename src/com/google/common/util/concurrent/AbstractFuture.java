package com.google.common.util.concurrent;

import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbstractFuture extends InternalFutureFailureAccess
        implements ListenableFuture {
    public static final AtomicHelper ATOMIC_HELPER;
    public static final boolean GENERATE_CANCELLATION_CAUSES;
    public static final Object NULL;
    public static final LazyLogger log;
    public volatile Listener listeners;
    public volatile Object value;
    public volatile Waiter waiters;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AtomicHelper {
        public abstract boolean casListeners(
                AbstractFuture abstractFuture, Listener listener, Listener listener2);

        public abstract boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2);

        public abstract boolean casWaiters(
                AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2);

        public abstract Listener gasListeners(AbstractFuture abstractFuture);

        public abstract Waiter gasWaiters(AbstractFuture abstractFuture);

        public abstract void putNext(Waiter waiter, Waiter waiter2);

        public abstract void putThread(Waiter waiter, Thread thread);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Cancellation {
        public static final Cancellation CAUSELESS_CANCELLED;
        public static final Cancellation CAUSELESS_INTERRUPTED;
        public final Throwable cause;
        public final boolean wasInterrupted;

        static {
            if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
            } else {
                CAUSELESS_CANCELLED = new Cancellation(null, false);
                CAUSELESS_INTERRUPTED = new Cancellation(null, true);
            }
        }

        public Cancellation(Throwable th, boolean z) {
            this.wasInterrupted = z;
            this.cause = th;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Failure {
        public static final Failure FALLBACK_INSTANCE =
                new Failure(
                        new Throwable(
                                "Failure occurred while trying to finish a future.") { // from
                                                                                       // class:
                                                                                       // com.google.common.util.concurrent.AbstractFuture.Failure.1
                            @Override // java.lang.Throwable
                            public final synchronized Throwable fillInStackTrace() {
                                return this;
                            }
                        });
        public final Throwable exception;

        public Failure(Throwable th) {
            th.getClass();
            this.exception = th;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SafeAtomicHelper extends AtomicHelper {
        public final AtomicReferenceFieldUpdater listenersUpdater;
        public final AtomicReferenceFieldUpdater valueUpdater;
        public final AtomicReferenceFieldUpdater waiterNextUpdater;
        public final AtomicReferenceFieldUpdater waiterThreadUpdater;
        public final AtomicReferenceFieldUpdater waitersUpdater;

        public SafeAtomicHelper(
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater,
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater2,
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater3,
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater4,
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater5) {
            this.waiterThreadUpdater = atomicReferenceFieldUpdater;
            this.waiterNextUpdater = atomicReferenceFieldUpdater2;
            this.waitersUpdater = atomicReferenceFieldUpdater3;
            this.listenersUpdater = atomicReferenceFieldUpdater4;
            this.valueUpdater = atomicReferenceFieldUpdater5;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(
                AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return this.listenersUpdater.compareAndSet(abstractFuture, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return this.valueUpdater.compareAndSet(abstractFuture, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(
                AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return this.waitersUpdater.compareAndSet(abstractFuture, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture) {
            return (Listener) this.listenersUpdater.getAndSet(abstractFuture, Listener.TOMBSTONE);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture) {
            return (Waiter) this.waitersUpdater.getAndSet(abstractFuture, Waiter.TOMBSTONE);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            this.waiterNextUpdater.lazySet(waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            this.waiterThreadUpdater.lazySet(waiter, thread);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SetFuture implements Runnable {
        public final ListenableFuture future;
        public final AbstractFuture owner;

        public SetFuture(AbstractFuture abstractFuture, FluentFuture fluentFuture) {
            this.owner = abstractFuture;
            this.future = fluentFuture;
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.owner.value != this) {
                return;
            }
            if (AbstractFuture.ATOMIC_HELPER.casValue(
                    this.owner, this, AbstractFuture.getFutureValue(this.future))) {
                AbstractFuture.complete(this.owner, false);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SynchronizedHelper extends AtomicHelper {
        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(
                AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.listeners != listener) {
                        return false;
                    }
                    abstractFuture.listeners = listener2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.value != obj) {
                        return false;
                    }
                    abstractFuture.value = obj2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(
                AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            synchronized (abstractFuture) {
                try {
                    if (abstractFuture.waiters != waiter) {
                        return false;
                    }
                    abstractFuture.waiters = waiter2;
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture) {
            Listener listener;
            Listener listener2 = Listener.TOMBSTONE;
            synchronized (abstractFuture) {
                listener = abstractFuture.listeners;
                if (listener != listener2) {
                    abstractFuture.listeners = listener2;
                }
            }
            return listener;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture) {
            Waiter waiter;
            Waiter waiter2 = Waiter.TOMBSTONE;
            synchronized (abstractFuture) {
                waiter = abstractFuture.waiters;
                if (waiter != waiter2) {
                    abstractFuture.waiters = waiter2;
                }
            }
            return waiter;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            waiter.next = waiter2;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            waiter.thread = thread;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Trusted extends ListenableFuture {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class TrustedFuture extends AbstractFuture implements Trusted {
        @Override // com.google.common.util.concurrent.AbstractFuture, java.util.concurrent.Future
        public final boolean isCancelled() {
            return this.value instanceof Cancellation;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UnsafeAtomicHelper extends AtomicHelper {
        public static final long LISTENERS_OFFSET;
        public static final Unsafe UNSAFE;
        public static final long VALUE_OFFSET;
        public static final long WAITERS_OFFSET;
        public static final long WAITER_NEXT_OFFSET;
        public static final long WAITER_THREAD_OFFSET;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1, reason: invalid class name */
        public final class AnonymousClass1 implements PrivilegedExceptionAction {
            @Override // java.security.PrivilegedExceptionAction
            public final Object run() {
                for (Field field : Unsafe.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    Object obj = field.get(null);
                    if (Unsafe.class.isInstance(obj)) {
                        return (Unsafe) Unsafe.class.cast(obj);
                    }
                }
                throw new NoSuchFieldError("the Unsafe");
            }
        }

        static {
            Unsafe unsafe;
            try {
                try {
                    unsafe = Unsafe.getUnsafe();
                } catch (SecurityException unused) {
                    unsafe = (Unsafe) AccessController.doPrivileged(new AnonymousClass1());
                }
                try {
                    WAITERS_OFFSET =
                            unsafe.objectFieldOffset(
                                    AbstractFuture.class.getDeclaredField("waiters"));
                    LISTENERS_OFFSET =
                            unsafe.objectFieldOffset(
                                    AbstractFuture.class.getDeclaredField("listeners"));
                    VALUE_OFFSET =
                            unsafe.objectFieldOffset(
                                    AbstractFuture.class.getDeclaredField("value"));
                    WAITER_THREAD_OFFSET =
                            unsafe.objectFieldOffset(Waiter.class.getDeclaredField("thread"));
                    WAITER_NEXT_OFFSET =
                            unsafe.objectFieldOffset(Waiter.class.getDeclaredField("next"));
                    UNSAFE = unsafe;
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casListeners(
                AbstractFuture abstractFuture, Listener listener, Listener listener2) {
            return UNSAFE.compareAndSwapObject(
                    abstractFuture, LISTENERS_OFFSET, listener, listener2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casValue(AbstractFuture abstractFuture, Object obj, Object obj2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, VALUE_OFFSET, obj, obj2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final boolean casWaiters(
                AbstractFuture abstractFuture, Waiter waiter, Waiter waiter2) {
            return UNSAFE.compareAndSwapObject(abstractFuture, WAITERS_OFFSET, waiter, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Listener gasListeners(AbstractFuture abstractFuture) {
            Listener listener;
            Listener listener2 = Listener.TOMBSTONE;
            do {
                listener = abstractFuture.listeners;
                if (listener2 == listener) {
                    return listener;
                }
            } while (!casListeners(abstractFuture, listener, listener2));
            return listener;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final Waiter gasWaiters(AbstractFuture abstractFuture) {
            Waiter waiter;
            Waiter waiter2 = Waiter.TOMBSTONE;
            do {
                waiter = abstractFuture.waiters;
                if (waiter2 == waiter) {
                    return waiter;
                }
            } while (!casWaiters(abstractFuture, waiter, waiter2));
            return waiter;
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putNext(Waiter waiter, Waiter waiter2) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, waiter2);
        }

        @Override // com.google.common.util.concurrent.AbstractFuture.AtomicHelper
        public final void putThread(Waiter waiter, Thread thread) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, thread);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Waiter {
        public static final Waiter TOMBSTONE = new Waiter();
        public volatile Waiter next;
        public volatile Thread thread;

        public Waiter() {
            AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }
    }

    static {
        boolean z;
        AtomicHelper synchronizedHelper;
        try {
            z =
                    Boolean.parseBoolean(
                            System.getProperty(
                                    "guava.concurrent.generate_cancellation_cause", "false"));
        } catch (SecurityException unused) {
            z = false;
        }
        GENERATE_CANCELLATION_CAUSES = z;
        log = new LazyLogger(AbstractFuture.class);
        Throwable th = null;
        try {
            synchronizedHelper = new UnsafeAtomicHelper();
            e = null;
        } catch (Error | Exception e) {
            e = e;
            try {
                synchronizedHelper =
                        new SafeAtomicHelper(
                                AtomicReferenceFieldUpdater.newUpdater(
                                        Waiter.class, Thread.class, "thread"),
                                AtomicReferenceFieldUpdater.newUpdater(
                                        Waiter.class, Waiter.class, "next"),
                                AtomicReferenceFieldUpdater.newUpdater(
                                        AbstractFuture.class, Waiter.class, "waiters"),
                                AtomicReferenceFieldUpdater.newUpdater(
                                        AbstractFuture.class, Listener.class, "listeners"),
                                AtomicReferenceFieldUpdater.newUpdater(
                                        AbstractFuture.class, Object.class, "value"));
            } catch (Error | Exception e2) {
                th = e2;
                synchronizedHelper = new SynchronizedHelper();
            }
        }
        ATOMIC_HELPER = synchronizedHelper;
        if (th != null) {
            LazyLogger lazyLogger = log;
            Logger logger = lazyLogger.get();
            Level level = Level.SEVERE;
            logger.log(level, "UnsafeAtomicHelper is broken!", e);
            lazyLogger.get().log(level, "SafeAtomicHelper is broken!", th);
        }
        NULL = new Object();
    }

    public static void complete(AbstractFuture abstractFuture, boolean z) {
        Listener listener = null;
        while (true) {
            abstractFuture.getClass();
            for (Waiter gasWaiters = ATOMIC_HELPER.gasWaiters(abstractFuture);
                    gasWaiters != null;
                    gasWaiters = gasWaiters.next) {
                Thread thread = gasWaiters.thread;
                if (thread != null) {
                    gasWaiters.thread = null;
                    LockSupport.unpark(thread);
                }
            }
            if (z) {
                z = false;
            }
            abstractFuture.afterDone();
            Listener listener2 = listener;
            Listener gasListeners = ATOMIC_HELPER.gasListeners(abstractFuture);
            Listener listener3 = listener2;
            while (gasListeners != null) {
                Listener listener4 = gasListeners.next;
                gasListeners.next = listener3;
                listener3 = gasListeners;
                gasListeners = listener4;
            }
            while (listener3 != null) {
                listener = listener3.next;
                Runnable runnable = listener3.task;
                Objects.requireNonNull(runnable);
                if (runnable instanceof SetFuture) {
                    SetFuture setFuture = (SetFuture) runnable;
                    abstractFuture = setFuture.owner;
                    if (abstractFuture.value == setFuture) {
                        if (ATOMIC_HELPER.casValue(
                                abstractFuture, setFuture, getFutureValue(setFuture.future))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else {
                    Executor executor = listener3.executor;
                    Objects.requireNonNull(executor);
                    executeListener(runnable, executor);
                }
                listener3 = listener;
            }
            return;
        }
    }

    public static void executeListener(Runnable runnable, Executor executor) {
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

    public static Object getDoneValue(Object obj) {
        if (obj instanceof Cancellation) {
            Throwable th = ((Cancellation) obj).cause;
            CancellationException cancellationException =
                    new CancellationException("Task was cancelled.");
            cancellationException.initCause(th);
            throw cancellationException;
        }
        if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        }
        if (obj == NULL) {
            return null;
        }
        return obj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Object getFutureValue(ListenableFuture listenableFuture) {
        Throwable tryInternalFastPathGetFailure;
        if (listenableFuture instanceof Trusted) {
            Object obj = ((AbstractFuture) listenableFuture).value;
            if (obj instanceof Cancellation) {
                Cancellation cancellation = (Cancellation) obj;
                if (cancellation.wasInterrupted) {
                    obj =
                            cancellation.cause != null
                                    ? new Cancellation(cancellation.cause, false)
                                    : Cancellation.CAUSELESS_CANCELLED;
                }
            }
            Objects.requireNonNull(obj);
            return obj;
        }
        if ((listenableFuture instanceof InternalFutureFailureAccess)
                && (tryInternalFastPathGetFailure =
                                InternalFutures.tryInternalFastPathGetFailure(
                                        (InternalFutureFailureAccess) listenableFuture))
                        != null) {
            return new Failure(tryInternalFastPathGetFailure);
        }
        boolean isCancelled = listenableFuture.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && isCancelled) {
            Cancellation cancellation2 = Cancellation.CAUSELESS_CANCELLED;
            Objects.requireNonNull(cancellation2);
            return cancellation2;
        }
        try {
            Object uninterruptibly = getUninterruptibly(listenableFuture);
            if (!isCancelled) {
                return uninterruptibly == null ? NULL : uninterruptibly;
            }
            return new Cancellation(
                    new IllegalArgumentException(
                            "get() did not throw CancellationException, despite reporting"
                                + " isCancelled() == true: "
                                    + listenableFuture),
                    false);
        } catch (Error | Exception e) {
            return new Failure(e);
        } catch (CancellationException e2) {
            if (isCancelled) {
                return new Cancellation(e2, false);
            }
            return new Failure(
                    new IllegalArgumentException(
                            "get() threw CancellationException, despite reporting isCancelled() =="
                                + " false: "
                                    + listenableFuture,
                            e2));
        } catch (ExecutionException e3) {
            if (!isCancelled) {
                return new Failure(e3.getCause());
            }
            return new Cancellation(
                    new IllegalArgumentException(
                            "get() did not throw CancellationException, despite reporting"
                                + " isCancelled() == true: "
                                    + listenableFuture,
                            e3),
                    false);
        }
    }

    public static Object getUninterruptibly(Future future) {
        Object obj;
        boolean z = false;
        while (true) {
            try {
                obj = future.get();
                break;
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return obj;
    }

    public final void addDoneString(StringBuilder sb) {
        try {
            Object uninterruptibly = getUninterruptibly(this);
            sb.append("SUCCESS, result=[");
            appendResultObject(sb, uninterruptibly);
            sb.append("]");
        } catch (CancellationException unused) {
            sb.append("CANCELLED");
        } catch (ExecutionException e) {
            sb.append("FAILURE, cause=[");
            sb.append(e.getCause());
            sb.append("]");
        } catch (Exception e2) {
            sb.append("UNKNOWN, cause=[");
            sb.append(e2.getClass());
            sb.append(" thrown from get()]");
        }
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        Listener listener;
        Listener listener2;
        Preconditions.checkNotNull(executor, "Executor was null.");
        if (!isDone() && (listener = this.listeners) != (listener2 = Listener.TOMBSTONE)) {
            Listener listener3 = new Listener(runnable, executor);
            do {
                listener3.next = listener;
                if (ATOMIC_HELPER.casListeners(this, listener, listener3)) {
                    return;
                } else {
                    listener = this.listeners;
                }
            } while (listener != listener2);
        }
        executeListener(runnable, executor);
    }

    public final void appendResultObject(StringBuilder sb, Object obj) {
        if (obj == null) {
            sb.append("null");
        } else {
            if (obj == this) {
                sb.append("this future");
                return;
            }
            sb.append(obj.getClass().getName());
            sb.append("@");
            sb.append(Integer.toHexString(System.identityHashCode(obj)));
        }
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        Cancellation cancellation;
        Object obj = this.value;
        if (!(obj == null) && !(obj instanceof SetFuture)) {
            return false;
        }
        if (GENERATE_CANCELLATION_CAUSES) {
            cancellation =
                    new Cancellation(new CancellationException("Future.cancel() was called."), z);
        } else {
            cancellation =
                    z ? Cancellation.CAUSELESS_INTERRUPTED : Cancellation.CAUSELESS_CANCELLED;
            Objects.requireNonNull(cancellation);
        }
        boolean z2 = false;
        while (true) {
            if (ATOMIC_HELPER.casValue(this, obj, cancellation)) {
                complete(this, z);
                if (!(obj instanceof SetFuture)) {
                    return true;
                }
                ListenableFuture listenableFuture = ((SetFuture) obj).future;
                if (!(listenableFuture instanceof Trusted)) {
                    listenableFuture.cancel(z);
                    return true;
                }
                this = (AbstractFuture) listenableFuture;
                obj = this.value;
                if (!(obj == null) && !(obj instanceof SetFuture)) {
                    return true;
                }
                z2 = true;
            } else {
                obj = this.value;
                if (!(obj instanceof SetFuture)) {
                    return z2;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c1  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00b4 -> B:33:0x007d). Please report as a decompilation issue!!! */
    @Override // java.util.concurrent.Future
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object get(long r20, java.util.concurrent.TimeUnit r22) {
        /*
            Method dump skipped, instructions count: 369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.google.common.util.concurrent.AbstractFuture.get(long,"
                    + " java.util.concurrent.TimeUnit):java.lang.Object");
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return (!(r2 instanceof SetFuture)) & (this.value != null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String pendingToString() {
        if (!(this instanceof ScheduledFuture)) {
            return null;
        }
        return "remaining delay=["
                + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS)
                + " ms]";
    }

    public final void removeWaiter(Waiter waiter) {
        waiter.thread = null;
        while (true) {
            Waiter waiter2 = this.waiters;
            if (waiter2 == Waiter.TOMBSTONE) {
                return;
            }
            Waiter waiter3 = null;
            while (waiter2 != null) {
                Waiter waiter4 = waiter2.next;
                if (waiter2.thread != null) {
                    waiter3 = waiter2;
                } else if (waiter3 != null) {
                    waiter3.next = waiter4;
                    if (waiter3.thread == null) {
                        break;
                    }
                } else if (!ATOMIC_HELPER.casWaiters(this, waiter2, waiter4)) {
                    break;
                }
                waiter2 = waiter4;
            }
            return;
        }
    }

    public boolean set(Object obj) {
        if (obj == null) {
            obj = NULL;
        }
        if (!ATOMIC_HELPER.casValue(this, null, obj)) {
            return false;
        }
        complete(this, false);
        return true;
    }

    public boolean setException(Throwable th) {
        th.getClass();
        if (!ATOMIC_HELPER.casValue(this, null, new Failure(th))) {
            return false;
        }
        complete(this, false);
        return true;
    }

    public final void setFuture(FluentFuture fluentFuture) {
        Failure failure;
        fluentFuture.getClass();
        Object obj = this.value;
        if (obj == null) {
            if (fluentFuture.isDone()) {
                if (ATOMIC_HELPER.casValue(this, null, getFutureValue(fluentFuture))) {
                    complete(this, false);
                    return;
                }
                return;
            }
            SetFuture setFuture = new SetFuture(this, fluentFuture);
            if (ATOMIC_HELPER.casValue(this, null, setFuture)) {
                try {
                    fluentFuture.addListener(setFuture, DirectExecutor.INSTANCE);
                    return;
                } catch (Throwable th) {
                    try {
                        failure = new Failure(th);
                    } catch (Error | Exception unused) {
                        failure = Failure.FALLBACK_INSTANCE;
                    }
                    ATOMIC_HELPER.casValue(this, setFuture, failure);
                    return;
                }
            }
            obj = this.value;
        }
        if (obj instanceof Cancellation) {
            fluentFuture.cancel(((Cancellation) obj).wasInterrupted);
        }
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        if (getClass().getName().startsWith("com.google.common.util.concurrent.")) {
            sb.append(getClass().getSimpleName());
        } else {
            sb.append(getClass().getName());
        }
        sb.append('@');
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("[status=");
        if (isCancelled()) {
            sb.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(sb);
        } else {
            int length = sb.length();
            sb.append("PENDING");
            Object obj = this.value;
            if (obj instanceof SetFuture) {
                sb.append(", setFuture=[");
                ListenableFuture listenableFuture = ((SetFuture) obj).future;
                try {
                    if (listenableFuture == this) {
                        sb.append("this future");
                    } else {
                        sb.append(listenableFuture);
                    }
                } catch (Exception | StackOverflowError e) {
                    sb.append("Exception thrown from implementation: ");
                    sb.append(e.getClass());
                }
                sb.append("]");
            } else {
                try {
                    str = pendingToString();
                    if (Platform.stringIsNullOrEmpty(str)) {
                        str = null;
                    }
                } catch (Exception | StackOverflowError e2) {
                    str = "Exception thrown from implementation: " + e2.getClass();
                }
                if (str != null) {
                    sb.append(", info=[");
                    sb.append(str);
                    sb.append("]");
                }
            }
            if (isDone()) {
                sb.delete(length, sb.length());
                addDoneString(sb);
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Listener {
        public static final Listener TOMBSTONE = new Listener();
        public final Executor executor;
        public Listener next;
        public final Runnable task;

        public Listener(Runnable runnable, Executor executor) {
            this.task = runnable;
            this.executor = executor;
        }

        public Listener() {
            this.task = null;
            this.executor = null;
        }
    }

    public void afterDone() {}

    @Override // java.util.concurrent.Future
    public Object get() {
        Object obj;
        if (!Thread.interrupted()) {
            Object obj2 = this.value;
            if ((obj2 != null) & (!(obj2 instanceof SetFuture))) {
                return getDoneValue(obj2);
            }
            Waiter waiter = this.waiters;
            Waiter waiter2 = Waiter.TOMBSTONE;
            if (waiter != waiter2) {
                Waiter waiter3 = new Waiter();
                do {
                    AtomicHelper atomicHelper = ATOMIC_HELPER;
                    atomicHelper.putNext(waiter3, waiter);
                    if (atomicHelper.casWaiters(this, waiter, waiter3)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                obj = this.value;
                            } else {
                                removeWaiter(waiter3);
                                throw new InterruptedException();
                            }
                        } while (!((obj != null) & (!(obj instanceof SetFuture))));
                        return getDoneValue(obj);
                    }
                    waiter = this.waiters;
                } while (waiter != waiter2);
            }
            Object obj3 = this.value;
            Objects.requireNonNull(obj3);
            return getDoneValue(obj3);
        }
        throw new InterruptedException();
    }
}
