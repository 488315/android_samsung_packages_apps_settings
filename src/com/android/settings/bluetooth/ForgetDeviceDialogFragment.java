package com.android.settings.bluetooth;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ForgetDeviceDialogFragment extends InstrumentedDialogFragment {
    public CachedBluetoothDevice mDevice;

    public CachedBluetoothDevice getDevice(Context context) {
        String string = getArguments().getString("device_address");
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        return localBluetoothManager.mCachedDeviceManager.findDevice(
                localBluetoothManager.mLocalAdapter.mAdapter.getRemoteDevice(string));
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1031;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Context context = getContext();
        CachedBluetoothDevice device = getDevice(context);
        this.mDevice = device;
        if (device == null) {
            Log.e("ForgetBluetoothDevice", "onCreateDialog: Device is null.");
            return null;
        }
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.bluetooth.ForgetDeviceDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        ForgetDeviceDialogFragment forgetDeviceDialogFragment =
                                ForgetDeviceDialogFragment.this;
                        forgetDeviceDialogFragment.mDevice.unpair();
                        FragmentActivity activity = forgetDeviceDialogFragment.getActivity();
                        if (activity != null) {
                            activity.finish();
                        }
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setPositiveButton(
                R.string.bluetooth_unpair_dialog_forget_confirm_button, onClickListener);
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        create.setTitle(R.string.bluetooth_unpair_dialog_title);
        create.setMessage(
                context.getString(R.string.bluetooth_unpair_dialog_body, this.mDevice.getName()));
        return create;
    }
}
