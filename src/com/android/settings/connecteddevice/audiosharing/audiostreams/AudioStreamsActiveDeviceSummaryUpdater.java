package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.content.Context;
import android.util.Log;

import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioStreamsActiveDeviceSummaryUpdater implements BluetoothCallback {
    public final LocalBluetoothManager mBluetoothManager;
    public final Context mContext;
    public final AudioStreamsActiveDeviceController mListener;
    public String mSummary;

    public AudioStreamsActiveDeviceSummaryUpdater(
            Context context,
            AudioStreamsActiveDeviceController audioStreamsActiveDeviceController) {
        this.mContext = context;
        this.mBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mListener = audioStreamsActiveDeviceController;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        StringBuilder sb = new StringBuilder("onActiveDeviceChanged() with activeDevice : ");
        sb.append(
                cachedBluetoothDevice == null
                        ? "null"
                        : cachedBluetoothDevice.mDevice.getAddress());
        sb.append(" on profile : ");
        sb.append(i);
        Log.d("AudioStreamsActiveDeviceSummaryUpdater", sb.toString());
        if (i == 22) {
            ThreadUtils.postOnBackgroundThread(
                    new AudioStreamsActiveDeviceSummaryUpdater$$ExternalSyntheticLambda0(this));
        }
    }
}
