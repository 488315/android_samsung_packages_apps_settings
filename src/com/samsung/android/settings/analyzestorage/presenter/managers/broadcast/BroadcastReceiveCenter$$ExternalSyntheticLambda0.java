package com.samsung.android.settings.analyzestorage.presenter.managers.broadcast;

import android.os.Bundle;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BroadcastReceiveCenter$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BroadcastListener f$0;
    public final /* synthetic */ BroadcastType f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ BroadcastReceiveCenter$$ExternalSyntheticLambda0(
            BroadcastListener broadcastListener,
            BroadcastType broadcastType,
            Bundle bundle,
            int i) {
        this.$r8$classId = i;
        this.f$0 = broadcastListener;
        this.f$1 = broadcastType;
        this.f$2 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Optional.ofNullable(this.f$0)
                        .ifPresent(
                                new BroadcastReceiveCenter$$ExternalSyntheticLambda1(
                                        0, this.f$1, this.f$2));
                break;
            default:
                Optional.ofNullable(this.f$0)
                        .ifPresent(
                                new BroadcastReceiveCenter$$ExternalSyntheticLambda1(
                                        1, this.f$1, this.f$2));
                break;
        }
    }
}
