package com.samsung.android.settings.accessibility.shortcut;

import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.accessibility.HearingAidHelper;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HearingAidSupportShortcut extends BaseShortcutActivity {
    @Override // com.samsung.android.settings.accessibility.shortcut.BaseShortcutActivity
    public final void performShortcutOperation() {
        CachedBluetoothDevice connectedHearingAidDevice =
                new HearingAidHelper(this).getConnectedHearingAidDevice();
        if (connectedHearingAidDevice == null) {
            Intent intent = new Intent("android.bluetooth.devicepicker.action.LAUNCH");
            intent.putExtra("android.bluetooth.devicepicker.extra.NEED_AUTH", true);
            intent.putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 8);
            intent.setFlags(268468224);
            try {
                startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                Log.e("HearingAidSupportShortcut", "BluetoothSettings not found", e);
                return;
            }
        }
        BluetoothDevice bluetoothDevice = connectedHearingAidDevice.mDevice;
        Bundle bundle = new Bundle();
        bundle.putParcelable("device", bluetoothDevice);
        Intent intent2 = new Intent("com.samsung.settings.DEVICE_PROFILES_SETTINGS");
        intent2.putExtra("device", bundle);
        intent2.setFlags(335544320);
        try {
            startActivity(intent2);
        } catch (ActivityNotFoundException e2) {
            Log.e(
                    "HearingAidSupportShortcut",
                    "com.samsung.settings.DEVICE_PROFILES_SETTINGS not found",
                    e2);
        }
    }
}
