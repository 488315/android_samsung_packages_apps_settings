package com.samsung.android.settings.bluetooth;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecDevicePickerDialog extends AppCompatActivity {
    public static boolean IS_ACTION_BUTTON = false;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getApplicationContext(), Utils.mOnInitCallback);
        if (localBluetoothManager == null) {
            Log.e("SecDevicePickerDialog", "Bluetooth is not supported on this device");
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.getState() != 12) {
            Log.e(
                    "SecDevicePickerDialog",
                    "onCreate :: Can't lunch device picker dialog, Bluetooth is not ON state");
            finish();
        }
        getResources().getString(R.string.screen_bluetooth_scan_dialog);
        IS_ACTION_BUTTON = false;
        if (bundle == null) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            Fragment findFragmentByTag =
                    getSupportFragmentManager()
                            .findFragmentByTag("bluetooth.pickerdialog.fragment");
            if (findFragmentByTag != null) {
                m.remove(findFragmentByTag);
            }
            m.addToBackStack(null);
            SecDevicePickerFragment secDevicePickerFragment = new SecDevicePickerFragment();
            secDevicePickerFragment.setCancelable(true);
            secDevicePickerFragment.show(
                    getSupportFragmentManager(), "bluetooth.pickerdialog.fragment");
        }
        getWindow().setGravity(80);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        super.onStop();
        IS_ACTION_BUTTON = false;
    }
}
