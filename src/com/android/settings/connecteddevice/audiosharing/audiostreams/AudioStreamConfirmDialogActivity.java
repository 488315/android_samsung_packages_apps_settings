package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.SettingsActivity;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamConfirmDialogActivity extends SettingsActivity
        implements LocalBluetoothProfileManager.ServiceListener {
    public LocalBluetoothProfileManager mProfileManager;

    @Override // com.android.settings.SettingsActivity
    public final void createUiFromIntent(Intent intent, Bundle bundle) {
        BluetoothAdapter.getDefaultAdapter();
        Log.d(
                "AudioStreamConfirmDialogActivity",
                "createUiFromIntent() : not supported or already connected, starting"
                    + " createUiFromIntent");
        super.createUiFromIntent(intent, bundle);
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return AudioStreamConfirmDialog.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(this);
        this.mProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        super.onCreate(bundle);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        BluetoothAdapter.getDefaultAdapter();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStart() {
        BluetoothAdapter.getDefaultAdapter();
        super.onStart();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.removeServiceListener(this);
        }
        super.onStop();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}
}
