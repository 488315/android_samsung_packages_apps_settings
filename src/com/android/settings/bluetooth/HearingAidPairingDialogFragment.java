package com.android.settings.bluetooth;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.accessibility.HearingDevicePairingFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HearingAidPairingDialogFragment extends InstrumentedDialogFragment
        implements CachedBluetoothDevice.Callback {
    public CachedBluetoothDevice mDevice;
    public LocalBluetoothManager mLocalBluetoothManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1930;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mLocalBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        CachedBluetoothDevice findDevice =
                this.mLocalBluetoothManager.mCachedDeviceManager.findDevice(
                        BluetoothAdapter.getDefaultAdapter()
                                .getRemoteDevice(getArguments().getString("device_address")));
        this.mDevice = findDevice;
        if (findDevice != null) {
            findDevice.registerCallback(this);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int deviceSide = this.mDevice.getDeviceSide();
        int i =
                deviceSide == 0
                        ? R.string.bluetooth_pair_other_ear_dialog_left_ear_message
                        : R.string.bluetooth_pair_other_ear_dialog_right_ear_message;
        int i2 =
                deviceSide == 0
                        ? R.string.bluetooth_pair_other_ear_dialog_right_ear_positive_button
                        : R.string.bluetooth_pair_other_ear_dialog_left_ear_positive_button;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.bluetooth_pair_other_ear_dialog_title);
        builder.setMessage(i);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(
                i2,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.bluetooth.HearingAidPairingDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        HearingAidPairingDialogFragment hearingAidPairingDialogFragment =
                                HearingAidPairingDialogFragment.this;
                        int i4 =
                                hearingAidPairingDialogFragment
                                        .getArguments()
                                        .getInt("launch_page");
                        String name =
                                (i4 == 2 || i4 == 2024)
                                        ? HearingDevicePairingFragment.class.getName()
                                        : BluetoothPairingDetail.class.getName();
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(
                                        hearingAidPairingDialogFragment.getActivity());
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 1930;
                        subSettingLauncher.launch();
                    }
                });
        return builder.create();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        super.onDetach();
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.unregisterCallback(this);
        }
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice.mSubDevice;
        if (cachedBluetoothDevice == null
                || !cachedBluetoothDevice.isConnectedAshaHearingAidDevice()) {
            return;
        }
        dismissInternal(false, false);
    }
}
