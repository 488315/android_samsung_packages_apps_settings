package com.android.settings.wifi.dpp;

import android.os.Handler;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiDppUtils$$ExternalSyntheticLambda1 implements Executor {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Handler f$0;

    public /* synthetic */ WifiDppUtils$$ExternalSyntheticLambda1(Handler handler, int i) {
        this.$r8$classId = i;
        this.f$0 = handler;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        int i = this.$r8$classId;
        Handler handler = this.f$0;
        switch (i) {
            case 0:
                handler.post(runnable);
                break;
            default:
                handler.post(runnable);
                break;
        }
    }
}
