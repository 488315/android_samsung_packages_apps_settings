package com.android.settings.fuelgauge.batteryusage;

import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryUsageBreakdownController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BatteryUsageBreakdownController$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        MetricsFeatureProvider metricsFeatureProvider;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((BatteryUsageBreakdownController) obj).lambda$showSpinnerAndAppList$0();
                break;
            case 1:
                ((BatteryUsageBreakdownController) obj).lambda$showSpinnerAndAppList$1();
                break;
            default:
                BatteryUsageBreakdownController.AnonymousClass1 anonymousClass1 =
                        (BatteryUsageBreakdownController.AnonymousClass1) obj;
                BatteryUsageBreakdownController.this.lambda$showSpinnerAndAppList$0();
                BatteryUsageBreakdownController.this.addAllPreferences();
                metricsFeatureProvider =
                        BatteryUsageBreakdownController.this.mMetricsFeatureProvider;
                BatteryUsageBreakdownController batteryUsageBreakdownController =
                        BatteryUsageBreakdownController.this;
                metricsFeatureProvider.action(
                        batteryUsageBreakdownController.mPrefContext,
                        1822,
                        batteryUsageBreakdownController.mSpinnerPosition);
                break;
        }
    }
}
