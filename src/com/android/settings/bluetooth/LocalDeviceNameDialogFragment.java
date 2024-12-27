package com.android.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocalDeviceNameDialogFragment extends BluetoothNameDialogFragment {
    public BluetoothAdapter mBluetoothAdapter;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.bluetooth.LocalDeviceNameDialogFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    LocalDeviceNameDialogFragment localDeviceNameDialogFragment;
                    String deviceName;
                    String action = intent.getAction();
                    if (("android.bluetooth.adapter.action.LOCAL_NAME_CHANGED".equals(action)
                                    || ("android.bluetooth.adapter.action.STATE_CHANGED"
                                                    .equals(action)
                                            && intent.getIntExtra(
                                                            "android.bluetooth.adapter.extra.STATE",
                                                            Integer.MIN_VALUE)
                                                    == 12))
                            && (deviceName =
                                            (localDeviceNameDialogFragment =
                                                            LocalDeviceNameDialogFragment.this)
                                                    .getDeviceName())
                                    != null) {
                        localDeviceNameDialogFragment.mDeviceNameUpdated = true;
                        localDeviceNameDialogFragment.mDeviceNameEdited = false;
                        localDeviceNameDialogFragment.mDeviceNameView.setText(deviceName);
                    }
                }
            };

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final String getDeviceName() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            return null;
        }
        return this.mBluetoothAdapter.getName();
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final int getDialogTitle() {
        return R.string.bluetooth_rename_device;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 538;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity()
                .registerReceiver(
                        this.mReceiver,
                        ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m(
                                "android.bluetooth.adapter.action.STATE_CHANGED",
                                "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED"));
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final void setDeviceName(String str) {
        this.mBluetoothAdapter.setName(str);
    }
}
