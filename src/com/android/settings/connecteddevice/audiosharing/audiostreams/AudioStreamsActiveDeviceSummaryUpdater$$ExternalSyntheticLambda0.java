package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.text.TextUtils;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.utils.ThreadUtils;

import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsActiveDeviceSummaryUpdater$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ AudioStreamsActiveDeviceSummaryUpdater f$0;

    @Override // java.lang.Runnable
    public final void run() {
        final AudioStreamsActiveDeviceSummaryUpdater audioStreamsActiveDeviceSummaryUpdater =
                this.f$0;
        Optional cachedBluetoothDeviceInSharingOrLeConnected =
                AudioStreamsHelper.getCachedBluetoothDeviceInSharingOrLeConnected(
                        audioStreamsActiveDeviceSummaryUpdater.mBluetoothManager);
        final String string =
                cachedBluetoothDeviceInSharingOrLeConnected.isEmpty()
                        ? audioStreamsActiveDeviceSummaryUpdater.mContext.getString(
                                R.string.audio_streams_dialog_no_le_device_title)
                        : ((CachedBluetoothDevice)
                                        cachedBluetoothDeviceInSharingOrLeConnected.get())
                                .getName();
        if (TextUtils.equals(audioStreamsActiveDeviceSummaryUpdater.mSummary, string)) {
            return;
        }
        audioStreamsActiveDeviceSummaryUpdater.mSummary = string;
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsActiveDeviceSummaryUpdater$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioStreamsActiveDeviceSummaryUpdater
                                audioStreamsActiveDeviceSummaryUpdater2 =
                                        AudioStreamsActiveDeviceSummaryUpdater.this;
                        audioStreamsActiveDeviceSummaryUpdater2.mListener.onSummaryChanged(string);
                    }
                });
    }
}
