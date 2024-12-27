package com.android.settings.bluetooth;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BluetoothAutoOnPreferenceController f$0;

    public /* synthetic */ BluetoothAutoOnPreferenceController$$ExternalSyntheticLambda2(
            BluetoothAutoOnPreferenceController bluetoothAutoOnPreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = bluetoothAutoOnPreferenceController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        BluetoothAutoOnPreferenceController bluetoothAutoOnPreferenceController = this.f$0;
        switch (i) {
            case 0:
                bluetoothAutoOnPreferenceController.updateValue();
                break;
            default:
                bluetoothAutoOnPreferenceController.lambda$onAutoOnStateChanged$0();
                break;
        }
    }
}
