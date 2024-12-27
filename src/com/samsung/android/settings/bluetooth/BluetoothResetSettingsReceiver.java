package com.samsung.android.settings.bluetooth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothResetSettingsReceiver extends BroadcastReceiver {
    public LocalBluetoothAdapter mLocalBluetoothAdapter;

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            Log.d("BluetoothResetSettingsReceiver", "Received null intent");
            return;
        }
        Log.d("BluetoothResetSettingsReceiver", "Received :: ".concat(action));
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            Log.d(
                    "BluetoothResetSettingsReceiver",
                    "mLocalBluetoothManager is null, Can't process factory reset about bluetooth");
            return;
        }
        this.mLocalBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (action.equals("com.samsung.intent.action.SETTINGS_SOFT_RESET")) {
            if ("ATT".equals(com.android.settings.Utils.getSalesCode())) {
                Log.d("BluetoothResetSettingsReceiver", "Does not support Soft Reset");
                return;
            }
            LocalBluetoothAdapter localBluetoothAdapter = this.mLocalBluetoothAdapter;
            if (localBluetoothAdapter == null) {
                Log.d(
                        "BluetoothResetSettingsReceiver",
                        "LocalBluetoothAdapter is null, Can't process factory reset about"
                            + " bluetooth");
            } else if (localBluetoothAdapter.mAdapter.factoryReset()) {
                Settings.Global.putInt(context.getContentResolver(), "bluetooth_on", 0);
            } else {
                Log.d("BluetoothResetSettingsReceiver", "Factory Reset processed unsuccessfully");
            }
        }
    }
}
