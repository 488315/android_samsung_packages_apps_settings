package com.android.settings.accessibility;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.android.settings.bluetooth.HearingAidPairingDialogFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HearingAidUtils {
    public static void launchHearingAidPairingDialog(
            FragmentManager fragmentManager, CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (!cachedBluetoothDevice.getProfiles().stream()
                        .anyMatch(new HearingAidUtils$$ExternalSyntheticLambda0())
                && cachedBluetoothDevice.isConnectedAshaHearingAidDevice()) {
            HearingAidInfo hearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
            if ((hearingAidInfo != null ? hearingAidInfo.mMode : -1) == 1
                    && cachedBluetoothDevice.mSubDevice == null) {
                if (cachedBluetoothDevice.getDeviceSide() == -1) {
                    Log.w(
                            "HearingAidUtils",
                            "Can not launch hearing aid pairing dialog for invalid side");
                    return;
                }
                String address = cachedBluetoothDevice.mDevice.getAddress();
                Bundle bundle = new Bundle(1);
                bundle.putString("device_address", address);
                bundle.putInt("launch_page", i);
                HearingAidPairingDialogFragment hearingAidPairingDialogFragment =
                        new HearingAidPairingDialogFragment();
                hearingAidPairingDialogFragment.setArguments(bundle);
                hearingAidPairingDialogFragment.show(
                        fragmentManager, "HearingAidPairingDialogFragment");
            }
        }
    }
}
