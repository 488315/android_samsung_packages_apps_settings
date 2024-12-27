package com.samsung.android.settings.bluetooth;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BluetoothScanDialog extends AppCompatActivity implements BluetoothCallback {
    public static boolean IS_ACTION_BUTTON = false;
    public LocalBluetoothManager mLocalManager;

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        if (i == 13) {
            Log.d(
                    "BluetoothScanDialog",
                    "onBluetoothStateChanged :: BluetoothScanDialog will finish by bluetooth"
                        + " disable");
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        setFinishOnTouchOutside(true);
        requestWindowFeature(1);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(getApplicationContext(), Utils.mOnInitCallback);
        this.mLocalManager = localBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e("BluetoothScanDialog", "Bluetooth is not supported on this device");
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.getState() != 12) {
            Log.e(
                    "BluetoothScanDialog",
                    "onCreate :: Can't lunch Scandialog, Bluetooth is not ON state");
            finish();
        } else if (this.mLocalManager.semIsForegroundActivity()) {
            Log.e(
                    "BluetoothScanDialog",
                    "onCreate :: Can't lunch Scandialog, BluetoothSettings is foreground");
            finish();
        }
        LocalBluetoothManager localBluetoothManager2 = this.mLocalManager;
        if (localBluetoothManager2 != null) {
            localBluetoothManager2.mEventManager.registerCallback(this);
        }
        getResources().getString(R.string.screen_bluetooth_scan_dialog);
        IS_ACTION_BUTTON = false;
        if (bundle == null) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m =
                    BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                            supportFragmentManager, supportFragmentManager);
            Fragment findFragmentByTag =
                    getSupportFragmentManager().findFragmentByTag("bluetooth.scandialog.fragment");
            if (findFragmentByTag != null) {
                m.remove(findFragmentByTag);
            }
            m.addToBackStack(null);
            BluetoothScanDialogFragment bluetoothScanDialogFragment =
                    new BluetoothScanDialogFragment();
            bluetoothScanDialogFragment.setCancelable(true);
            bluetoothScanDialogFragment.show(
                    getSupportFragmentManager(), "bluetooth.scandialog.fragment");
        }
        getWindow().setGravity(80);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        LocalBluetoothManager localBluetoothManager = this.mLocalManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.unregisterCallback(this);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "onScanningStateChanged :: started = ", "BluetoothScanDialog", z);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        super.onStop();
        IS_ACTION_BUTTON = false;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
