package com.android.settings.fuelgauge.batteryusage;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryChartPreferenceController$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatteryChartPreferenceController f$0;

    public /* synthetic */ BatteryChartPreferenceController$$ExternalSyntheticLambda1(
            BatteryChartPreferenceController batteryChartPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = batteryChartPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BatteryChartPreferenceController batteryChartPreferenceController = this.f$0;
        switch (i) {
            case 0:
                batteryChartPreferenceController.mDailyChartView.announceForAccessibility(
                        batteryChartPreferenceController.getAccessibilityAnnounceMessage());
                break;
            case 1:
                batteryChartPreferenceController.mDailyChartView.announceForAccessibility(
                        batteryChartPreferenceController.getAccessibilityAnnounceMessage());
                break;
            default:
                batteryChartPreferenceController.mHourlyChartView.announceForAccessibility(
                        batteryChartPreferenceController.getAccessibilityAnnounceMessage());
                break;
        }
    }
}
