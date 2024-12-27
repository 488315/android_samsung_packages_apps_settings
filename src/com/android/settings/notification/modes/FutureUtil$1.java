package com.android.settings.notification.modes;

import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FutureUtil$1 implements FutureCallback {
    public final /* synthetic */ Consumer val$consumer;
    public final /* synthetic */ String val$errorLogMessage = "Error in future";
    public final /* synthetic */ Object[] val$errorLogMessageArgs;

    public FutureUtil$1(Consumer consumer, Object[] objArr) {
        this.val$consumer = consumer;
        this.val$errorLogMessageArgs = objArr;
    }

    @Override // com.google.common.util.concurrent.FutureCallback
    public final void onFailure(Throwable th) {
        Log.e(
                "ZenFutureUtil",
                String.format(this.val$errorLogMessage, this.val$errorLogMessageArgs),
                th);
    }

    @Override // com.google.common.util.concurrent.FutureCallback
    public final void onSuccess(Object obj) {
        this.val$consumer.accept(obj);
    }
}
