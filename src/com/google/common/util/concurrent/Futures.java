package com.google.common.util.concurrent;

import com.google.common.base.MoreObjects$ToStringHelper;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Futures {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CallbackListener implements Runnable {
        public final FutureCallback callback;
        public final Future future;

        public CallbackListener(Future future, FutureCallback futureCallback) {
            this.future = future;
            this.callback = futureCallback;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Throwable tryInternalFastPathGetFailure;
            Object obj = this.future;
            if ((obj instanceof InternalFutureFailureAccess)
                    && (tryInternalFastPathGetFailure =
                                    InternalFutures.tryInternalFastPathGetFailure(
                                            (InternalFutureFailureAccess) obj))
                            != null) {
                this.callback.onFailure(tryInternalFastPathGetFailure);
                return;
            }
            try {
                this.callback.onSuccess(Futures.getDone(this.future));
            } catch (ExecutionException e) {
                this.callback.onFailure(e.getCause());
            } catch (Throwable th) {
                this.callback.onFailure(th);
            }
        }

        public final String toString() {
            MoreObjects$ToStringHelper moreObjects$ToStringHelper =
                    new MoreObjects$ToStringHelper(CallbackListener.class.getSimpleName());
            FutureCallback futureCallback = this.callback;
            MoreObjects$ToStringHelper.ValueHolder valueHolder =
                    new MoreObjects$ToStringHelper.ValueHolder();
            moreObjects$ToStringHelper.holderTail.next = valueHolder;
            moreObjects$ToStringHelper.holderTail = valueHolder;
            valueHolder.value = futureCallback;
            return moreObjects$ToStringHelper.toString();
        }
    }

    public static Object getDone(Future future) {
        Object obj;
        if (!future.isDone()) {
            throw new IllegalStateException(
                    Strings.lenientFormat("Future was expected to be done: %s", future));
        }
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

    public static ImmediateFuture immediateFuture(Object obj) {
        return obj == null ? ImmediateFuture.NULL : new ImmediateFuture(obj);
    }
}
