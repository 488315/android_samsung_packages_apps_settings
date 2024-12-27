package com.samsung.android.settings.accessibility.advanced.flashnotification;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class FlashNotificationUtil$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ FlashNotificationUtil.InstalledApplicationsListener f$1;

    public /* synthetic */ FlashNotificationUtil$$ExternalSyntheticLambda0(
            Context context,
            FlashNotificationUtil.InstalledApplicationsListener installedApplicationsListener) {
        this.f$0 = context;
        this.f$1 = installedApplicationsListener;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Context context = (Context) this.f$0;
                new Handler(Looper.getMainLooper())
                        .post(
                                new FlashNotificationUtil$$ExternalSyntheticLambda0(
                                        this.f$1,
                                        FlashNotificationUtil
                                                .getInstalledPackageNameUnmodifiableSet(context)));
                break;
            default:
                this.f$1.onGetInstalledPackageNameUnmodifiableSet((Set) this.f$0);
                break;
        }
    }

    public /* synthetic */ FlashNotificationUtil$$ExternalSyntheticLambda0(
            FlashNotificationUtil.InstalledApplicationsListener installedApplicationsListener,
            Set set) {
        this.f$1 = installedApplicationsListener;
        this.f$0 = set;
    }
}
