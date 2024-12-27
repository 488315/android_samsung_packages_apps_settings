package com.android.settings.dashboard;

import androidx.preference.Preference;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DashboardFragment$$ExternalSyntheticLambda11
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DashboardFragment f$0;

    public /* synthetic */ DashboardFragment$$ExternalSyntheticLambda11(
            DashboardFragment dashboardFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = dashboardFragment;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        DashboardFragment dashboardFragment = this.f$0;
        switch (i) {
            case 0:
                dashboardFragment.getClass();
                ((Preference) obj)
                        .getExtras()
                        .putInt("category", dashboardFragment.getMetricsCategory());
                break;
            default:
                dashboardFragment.getClass();
                try {
                    ((DashboardFeatureProviderImpl.AnonymousClass1) obj)
                            .mCountDownLatch.await(50L, TimeUnit.MILLISECONDS);
                    break;
                } catch (InterruptedException unused) {
                    return;
                }
        }
    }
}
