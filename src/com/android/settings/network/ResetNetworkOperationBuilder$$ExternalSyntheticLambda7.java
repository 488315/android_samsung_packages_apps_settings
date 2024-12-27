package com.android.settings.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.net.ConnectivityManager;
import android.net.VpnManager;
import android.net.wifi.WifiManager;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ResetNetworkOperationBuilder$$ExternalSyntheticLambda7
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((VpnManager) obj).factoryReset();
                break;
            case 1:
                BluetoothAdapter adapter = ((BluetoothManager) obj).getAdapter();
                if (adapter != null) {
                    adapter.clearBluetooth();
                    break;
                }
                break;
            case 2:
                ((Runnable) obj).run();
                break;
            case 3:
                ((ConnectivityManager) obj).factoryReset();
                break;
            default:
                ((WifiManager) obj).factoryReset();
                break;
        }
    }
}
