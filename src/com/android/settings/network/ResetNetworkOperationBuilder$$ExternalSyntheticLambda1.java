package com.android.settings.network;

import android.os.RecoverySystem;
import android.os.SystemClock;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;

import com.android.settings.ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ResetNetworkOperationBuilder$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ ResetNetworkOperationBuilder$$ExternalSyntheticLambda1(
            ResetNetworkOperationBuilder resetNetworkOperationBuilder,
            String str,
            ResetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0
                    resetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0) {
        this.f$0 = resetNetworkOperationBuilder;
        this.f$1 = str;
        this.f$2 = resetNetworkConfirm$ResetNetworkTask$$ExternalSyntheticLambda0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ResetNetworkOperationBuilder resetNetworkOperationBuilder =
                        (ResetNetworkOperationBuilder) this.f$0;
                String str = this.f$1;
                Consumer consumer = this.f$2;
                resetNetworkOperationBuilder.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Boolean valueOf =
                        Boolean.valueOf(
                                RecoverySystem.wipeEuiccData(
                                        resetNetworkOperationBuilder.mContext, str));
                if (consumer != null) {
                    consumer.accept(valueOf);
                }
                Log.i(
                        "ResetNetworkOpBuilder",
                        "Reset eSIM, takes "
                                + (SystemClock.elapsedRealtime() - elapsedRealtime)
                                + " ms");
                break;
            default:
                Consumer consumer2 = this.f$2;
                Object obj = this.f$0;
                String str2 = this.f$1;
                long elapsedRealtime2 = SystemClock.elapsedRealtime();
                consumer2.accept(obj);
                long elapsedRealtime3 = SystemClock.elapsedRealtime();
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "Reset ", str2, ", takes ");
                m.append(elapsedRealtime3 - elapsedRealtime2);
                m.append(" ms");
                Log.i("ResetNetworkOpBuilder", m.toString());
                break;
        }
    }

    public /* synthetic */ ResetNetworkOperationBuilder$$ExternalSyntheticLambda1(
            Consumer consumer, Object obj, String str) {
        this.f$2 = consumer;
        this.f$0 = obj;
        this.f$1 = str;
    }
}
