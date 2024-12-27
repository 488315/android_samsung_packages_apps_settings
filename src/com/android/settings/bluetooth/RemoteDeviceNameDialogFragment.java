package com.android.settings.bluetooth;

import android.content.Context;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RemoteDeviceNameDialogFragment extends BluetoothNameDialogFragment {
    public CachedBluetoothDevice mDevice;

    public CachedBluetoothDevice getDevice(Context context) {
        String string = getArguments().getString("cached_device");
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(context, Utils.mOnInitCallback);
        return localBluetoothManager.mCachedDeviceManager.findDevice(
                localBluetoothManager.mLocalAdapter.mAdapter.getRemoteDevice(string));
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final String getDeviceName() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null) {
            return cachedBluetoothDevice.getName();
        }
        return null;
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final int getDialogTitle() {
        return R.string.bluetooth_device_name;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED;
    }

    @Override // com.android.settings.core.instrumentation.InstrumentedDialogFragment,
              // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
              // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mDevice = getDevice(context);
    }

    @Override // com.android.settings.bluetooth.BluetoothNameDialogFragment
    public final void setDeviceName(String str) {
        CachedBluetoothDevice cachedBluetoothDevice = this.mDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.setName(str);
        }
    }
}
