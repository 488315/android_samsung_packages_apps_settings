package com.samsung.android.settings.bluetooth.tethering;

import com.android.settings.datausage.DataSaverBackend;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class BluetoothTetheringUtils$$ExternalSyntheticLambda0
        implements DataSaverBackend.Listener {
    public final /* synthetic */ Runnable f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ BluetoothTetheringUtils$$ExternalSyntheticLambda0(
            Runnable runnable,
            BluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0
                    bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0) {
        this.f$0 = runnable;
        this.f$1 = bluetoothTetheringSwitchEnabler$$ExternalSyntheticLambda0;
    }

    @Override // com.android.settings.datausage.DataSaverBackend.Listener
    public final void onDataSaverChanged(boolean z) {
        Runnable runnable = this.f$0;
        Runnable runnable2 = this.f$1;
        if (z && runnable != null) {
            runnable.run();
        }
        if (runnable2 != null) {
            runnable2.run();
        }
    }
}
