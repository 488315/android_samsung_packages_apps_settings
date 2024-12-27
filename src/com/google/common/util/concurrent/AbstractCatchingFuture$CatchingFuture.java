package com.google.common.util.concurrent;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class AbstractCatchingFuture$CatchingFuture extends FluentFuture.TrustedFuture
        implements Runnable {
    public Class exceptionType;
    public Object fallback;
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
        this.exceptionType = null;
        this.fallback = null;
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public final String pendingToString() {
        String str;
        ListenableFuture listenableFuture = this.inputFuture;
        Class cls = this.exceptionType;
        Object obj = this.fallback;
        String pendingToString = super.pendingToString();
        if (listenableFuture != null) {
            str = "inputFuture=[" + listenableFuture + "], ";
        } else {
            str = ApnSettings.MVNO_NONE;
        }
        if (cls == null || obj == null) {
            if (pendingToString != null) {
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, pendingToString);
            }
            return null;
        }
        return str + "exceptionType=[" + cls + "], fallback=[" + obj + "]";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0074  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r8 = this;
            com.google.common.util.concurrent.ListenableFuture r0 = r8.inputFuture
            java.lang.Class r1 = r8.exceptionType
            java.lang.Object r2 = r8.fallback
            r3 = 0
            r4 = 1
            if (r0 != 0) goto Lc
            r5 = r4
            goto Ld
        Lc:
            r5 = r3
        Ld:
            if (r1 != 0) goto L11
            r6 = r4
            goto L12
        L11:
            r6 = r3
        L12:
            r5 = r5 | r6
            if (r2 != 0) goto L16
            r3 = r4
        L16:
            r3 = r3 | r5
            if (r3 != 0) goto La8
            java.lang.Object r3 = r8.value
            boolean r3 = r3 instanceof com.google.common.util.concurrent.AbstractFuture.Cancellation
            if (r3 == 0) goto L21
            goto La8
        L21:
            r3 = 0
            r8.inputFuture = r3
            boolean r4 = r0 instanceof com.google.common.util.concurrent.internal.InternalFutureFailureAccess     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            if (r4 == 0) goto L34
            r4 = r0
            com.google.common.util.concurrent.internal.InternalFutureFailureAccess r4 = (com.google.common.util.concurrent.internal.InternalFutureFailureAccess) r4     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            java.lang.Throwable r4 = com.google.common.util.concurrent.internal.InternalFutures.tryInternalFastPathGetFailure(r4)     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            goto L35
        L30:
            r4 = move-exception
            goto L3c
        L32:
            r4 = move-exception
            goto L3e
        L34:
            r4 = r3
        L35:
            if (r4 != 0) goto L3c
            java.lang.Object r5 = com.google.common.util.concurrent.Futures.getDone(r0)     // Catch: java.lang.Throwable -> L30 java.util.concurrent.ExecutionException -> L32
            goto L6e
        L3c:
            r5 = r3
            goto L6e
        L3e:
            java.lang.Throwable r5 = r4.getCause()
            if (r5 != 0) goto L6c
            java.lang.NullPointerException r5 = new java.lang.NullPointerException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Future type "
            r6.<init>(r7)
            java.lang.Class r7 = r0.getClass()
            r6.append(r7)
            java.lang.String r7 = " threw "
            r6.append(r7)
            java.lang.Class r4 = r4.getClass()
            r6.append(r4)
            java.lang.String r4 = " without a cause"
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
        L6c:
            r4 = r5
            goto L3c
        L6e:
            if (r4 != 0) goto L74
            r8.set(r5)
            return
        L74:
            boolean r1 = r1.isInstance(r4)
            if (r1 != 0) goto L80
            com.google.common.util.concurrent.FluentFuture r0 = (com.google.common.util.concurrent.FluentFuture) r0
            r8.setFuture(r0)
            return
        L80:
            com.google.common.base.Function r2 = (com.google.common.base.Function) r2     // Catch: java.lang.Throwable -> L8e
            java.lang.Object r0 = r2.apply(r4)     // Catch: java.lang.Throwable -> L8e
            r8.exceptionType = r3
            r8.fallback = r3
            r8.set(r0)
            return
        L8e:
            r0 = move-exception
            boolean r1 = r0 instanceof java.lang.InterruptedException     // Catch: java.lang.Throwable -> La2
            if (r1 == 0) goto L9a
            java.lang.Thread r1 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> La2
            r1.interrupt()     // Catch: java.lang.Throwable -> La2
        L9a:
            r8.setException(r0)     // Catch: java.lang.Throwable -> La2
            r8.exceptionType = r3
            r8.fallback = r3
            return
        La2:
            r0 = move-exception
            r8.exceptionType = r3
            r8.fallback = r3
            throw r0
        La8:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.common.util.concurrent.AbstractCatchingFuture$CatchingFuture.run():void");
    }
}
