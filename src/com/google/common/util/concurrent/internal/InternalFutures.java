package com.google.common.util.concurrent.internal;

import com.google.common.util.concurrent.AbstractFuture;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class InternalFutures {
    public static Throwable tryInternalFastPathGetFailure(
            InternalFutureFailureAccess internalFutureFailureAccess) {
        AbstractFuture abstractFuture = (AbstractFuture) internalFutureFailureAccess;
        abstractFuture.getClass();
        if (abstractFuture instanceof AbstractFuture.Trusted) {
            Object obj = abstractFuture.value;
            if (obj instanceof AbstractFuture.Failure) {
                return ((AbstractFuture.Failure) obj).exception;
            }
        }
        return null;
    }
}
