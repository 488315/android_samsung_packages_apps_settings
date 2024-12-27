package com.android.settings.fuelgauge;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TopLevelBatteryPreferenceController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TopLevelBatteryPreferenceController f$0;
    public final /* synthetic */ BatteryInfo f$1;

    public /* synthetic */ TopLevelBatteryPreferenceController$$ExternalSyntheticLambda0(
            TopLevelBatteryPreferenceController topLevelBatteryPreferenceController,
            BatteryInfo batteryInfo,
            int i) {
        this.$r8$classId = i;
        this.f$0 = topLevelBatteryPreferenceController;
        this.f$1 = batteryInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$setSummaryAsync$3(this.f$1);
                break;
            default:
                this.f$0.lambda$setSummaryAsync$2(false, this.f$1);
                break;
        }
    }
}
