package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BroadcastReceiveCenter$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BroadcastReceiveCenter$$ExternalSyntheticLambda1(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BroadcastListener) obj).onReceive((BroadcastType) this.f$0, (Bundle) this.f$1);
                break;
            case 1:
                ((BroadcastListener) obj).onReceive((BroadcastType) this.f$0, (Bundle) this.f$1);
                break;
            default:
                BroadcastReceiveCenter broadcastReceiveCenter = (BroadcastReceiveCenter) this.f$0;
                Context context = (Context) this.f$1;
                IntentFilter intentFilter = (IntentFilter) obj;
                StorageBroadcastHandler storageBroadcastHandler =
                        broadcastReceiveCenter.mStorageBroadcastHandler;
                if (storageBroadcastHandler.mBroadcastReceiver == null) {
                    storageBroadcastHandler.mBroadcastReceiver =
                            new BroadcastHandler$1(storageBroadcastHandler);
                }
                context.registerReceiver(
                        storageBroadcastHandler.mBroadcastReceiver, intentFilter, 4);
                break;
        }
    }
}
