package com.android.settings.dashboard;

import com.android.settings.core.BasePreferenceController;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFragment$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DashboardFragment$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DashboardFeatureProviderImpl.AnonymousClass1 anonymousClass1 =
                        (DashboardFeatureProviderImpl.AnonymousClass1) obj;
                synchronized (anonymousClass1) {
                    anonymousClass1.mUpdateDelegated = true;
                    Runnable runnable = anonymousClass1.mUpdateRunnable;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
                return;
            default:
                ((BasePreferenceController) obj).setUiBlockerFinished(true);
                return;
        }
    }
}
