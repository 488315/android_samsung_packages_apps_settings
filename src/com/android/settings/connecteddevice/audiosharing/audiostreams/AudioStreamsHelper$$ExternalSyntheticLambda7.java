package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsHelper$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ AudioStreamsHelper f$0;
    public final /* synthetic */ BluetoothLeBroadcastMetadata f$1;

    public /* synthetic */ AudioStreamsHelper$$ExternalSyntheticLambda7(
            AudioStreamsHelper audioStreamsHelper,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.f$0 = audioStreamsHelper;
        this.f$1 = bluetoothLeBroadcastMetadata;
    }

    @Override // java.lang.Runnable
    public final void run() {
        AudioStreamsHelper audioStreamsHelper = this.f$0;
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.f$1;
        for (BluetoothDevice bluetoothDevice :
                AudioStreamsHelper.getConnectedBluetoothDevices(
                        audioStreamsHelper.mBluetoothManager, false)) {
            Log.d(
                    "AudioStreamsHelper",
                    "addSource(): join broadcast broadcastId : "
                            + bluetoothLeBroadcastMetadata.getBroadcastId()
                            + " sink : "
                            + bluetoothDevice.getAddress());
            audioStreamsHelper.mLeBroadcastAssistant.addSource(
                    bluetoothDevice, bluetoothLeBroadcastMetadata, false);
        }
    }
}
