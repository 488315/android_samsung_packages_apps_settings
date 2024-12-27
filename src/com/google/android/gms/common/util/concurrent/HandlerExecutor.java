package com.google.android.gms.common.util.concurrent;

import android.os.Looper;

import com.google.android.gms.internal.common.zzi;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HandlerExecutor implements Executor {
    public final zzi zza;

    public HandlerExecutor(Looper looper) {
        this.zza = new zzi(looper);
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.zza.post(runnable);
    }
}
