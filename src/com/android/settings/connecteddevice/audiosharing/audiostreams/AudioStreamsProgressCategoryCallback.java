package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.util.Log;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioStreamsProgressCategoryCallback
        extends AudioStreamsBroadcastAssistantCallback {
    public final AudioStreamsProgressCategoryController mCategoryController;

    public AudioStreamsProgressCategoryCallback(
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController) {
        this.mCategoryController = audioStreamsProgressCategoryController;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onReceiveStateChanged(
            BluetoothDevice bluetoothDevice,
            int i,
            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
        super.onReceiveStateChanged(bluetoothDevice, i, bluetoothLeBroadcastReceiveState);
        if (AudioStreamsHelper.isConnected(bluetoothLeBroadcastReceiveState)) {
            this.mCategoryController.handleSourceConnected(bluetoothLeBroadcastReceiveState);
        } else if (bluetoothLeBroadcastReceiveState.getPaSyncState() == 2
                && bluetoothLeBroadcastReceiveState.getBigEncryptionState() == 3) {
            this.mCategoryController.handleSourceConnectBadCode(bluetoothLeBroadcastReceiveState);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSearchStartFailed(int i) {
        super.onSearchStartFailed(i);
        this.mCategoryController.showToast("Failed to start scanning. Try again.");
        this.mCategoryController.setScanning(false);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSearchStarted(int i) {
        super.onSearchStarted(i);
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                this.mCategoryController;
        if (audioStreamsProgressCategoryController == null) {
            Log.w(
                    "AudioStreamsProgressCategoryCallback",
                    "onSearchStarted() : mCategoryController is null!");
        } else {
            audioStreamsProgressCategoryController.setScanning(true);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSearchStopFailed(int i) {
        super.onSearchStopFailed(i);
        this.mCategoryController.showToast("Failed to stop scanning. Try again.");
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSearchStopped(int i) {
        super.onSearchStopped(i);
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                this.mCategoryController;
        if (audioStreamsProgressCategoryController == null) {
            Log.w(
                    "AudioStreamsProgressCategoryCallback",
                    "onSearchStopped() : mCategoryController is null!");
        } else {
            audioStreamsProgressCategoryController.setScanning(false);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSourceAddFailed(
            BluetoothDevice bluetoothDevice,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
            int i) {
        super.onSourceAddFailed(bluetoothDevice, bluetoothLeBroadcastMetadata, i);
        this.mCategoryController.handleSourceFailedToConnect(
                bluetoothLeBroadcastMetadata.getBroadcastId());
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSourceFound(BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        super.onSourceFound(bluetoothLeBroadcastMetadata);
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                this.mCategoryController;
        if (audioStreamsProgressCategoryController == null) {
            Log.w(
                    "AudioStreamsProgressCategoryCallback",
                    "onSourceFound() : mCategoryController is null!");
        } else {
            audioStreamsProgressCategoryController.handleSourceFound(bluetoothLeBroadcastMetadata);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSourceLost(int i) {
        super.onSourceLost(i);
        this.mCategoryController.handleSourceLost(i);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSourceRemoveFailed(BluetoothDevice bluetoothDevice, int i, int i2) {
        super.onSourceRemoveFailed(bluetoothDevice, i, i2);
        this.mCategoryController.showToast("Failed to remove source.");
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsBroadcastAssistantCallback
    public final void onSourceRemoved(BluetoothDevice bluetoothDevice, int i, int i2) {
        super.onSourceRemoved(bluetoothDevice, i, i2);
        this.mCategoryController.handleSourceRemoved();
    }
}
