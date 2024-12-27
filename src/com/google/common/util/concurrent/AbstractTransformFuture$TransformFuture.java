package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.google.common.base.Function;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AbstractTransformFuture$TransformFuture extends FluentFuture.TrustedFuture
        implements Runnable {
    public Object function;
    public ListenableFuture inputFuture;

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final void afterDone() {
        ListenableFuture listenableFuture = this.inputFuture;
        boolean z = false;
        if ((listenableFuture != null) & (this.value instanceof AbstractFuture.Cancellation)) {
            Object obj = this.value;
            if ((obj instanceof AbstractFuture.Cancellation)
                    && ((AbstractFuture.Cancellation) obj).wasInterrupted) {
                z = true;
            }
            listenableFuture.cancel(z);
        }
        this.inputFuture = null;
        this.function = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        String str;
        ListenableFuture listenableFuture = this.inputFuture;
        Object obj = this.function;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (obj == null) {
            if (pendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, pendingToString);
            }
            return null;
        }
        return str + "function=[" + obj + "]";
    }

    @Override // java.lang.Runnable
    public final void run() {
        ListenableFuture listenableFuture = this.inputFuture;
        Object obj = this.function;
        if (((this.value instanceof AbstractFuture.Cancellation) | (listenableFuture == null))
                || (obj == null)) {
            return;
        }
        this.inputFuture = null;
        if (listenableFuture.isCancelled()) {
            setFuture((FluentFuture) listenableFuture);
            return;
        }
        try {
            try {
                Object apply = ((Function) obj).apply(Futures.getDone(listenableFuture));
                this.function = null;
                set(apply);
            } catch (Throwable th) {
                try {
                    if (th instanceof InterruptedException) {
                        Thread.currentThread().interrupt();
                    }
                    setException(th);
                } finally {
                    this.function = null;
                }
            }
        } catch (Error e) {
            setException(e);
        } catch (CancellationException unused) {
            cancel(false);
        } catch (ExecutionException e2) {
            setException(e2.getCause());
        } catch (Exception e3) {
            setException(e3);
        }
    }
}
