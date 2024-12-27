package com.android.settings.fuelgauge;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1
        implements BatteryBroadcastReceiver.OnBatteryChangedListener {
    public final /* synthetic */ TopLevelBatteryPreferenceController f$0;

    public /* synthetic */ TopLevelBatteryPreferenceController$$ExternalSyntheticLambda1(
            TopLevelBatteryPreferenceController topLevelBatteryPreferenceController) {
        this.f$0 = topLevelBatteryPreferenceController;
    }

    @Override // com.android.settings.fuelgauge.BatteryBroadcastReceiver.OnBatteryChangedListener
    public void onBatteryChanged(int i) {
        this.f$0.lambda$new$1(i);
    }
}
