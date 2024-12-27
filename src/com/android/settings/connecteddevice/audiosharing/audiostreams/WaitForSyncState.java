package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WaitForSyncState extends AudioStreamStateHandler {
    static final int AUDIO_STREAM_WAIT_FOR_SYNC_STATE_SUMMARY = 2132018355;
    static final int WAIT_FOR_SYNC_TIMEOUT_MILLIS = 15000;
    public static WaitForSyncState sInstance;

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.WAIT_FOR_SYNC;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final int getSummary() {
        return AUDIO_STREAM_WAIT_FOR_SYNC_STATE_SUMMARY;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final void performAction(
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamsHelper audioStreamsHelper) {
        AudioStreamPreference.AudioStream audioStream = audioStreamPreference.mAudioStream;
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                audioStream != null ? audioStream.mMetadata : null;
        if (bluetoothLeBroadcastMetadata != null) {
            this.mHandler.postDelayed(
                    new WaitForSyncState$$ExternalSyntheticLambda0(
                            this,
                            audioStreamPreference,
                            audioStreamsProgressCategoryController,
                            bluetoothLeBroadcastMetadata),
                    15000L);
        }
    }
}
