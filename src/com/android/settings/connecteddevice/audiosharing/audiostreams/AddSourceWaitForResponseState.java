package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.os.Handler;
import android.util.Log;

import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AddSourceWaitForResponseState extends AudioStreamStateHandler {
    static final int ADD_SOURCE_WAIT_FOR_RESPONSE_TIMEOUT_MILLIS = 20000;
    static final int AUDIO_STREAM_ADD_SOURCE_WAIT_FOR_RESPONSE_STATE_SUMMARY = 2132018355;
    public static AddSourceWaitForResponseState sInstance;

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.ADD_SOURCE_WAIT_FOR_RESPONSE;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final int getSummary() {
        return AUDIO_STREAM_ADD_SOURCE_WAIT_FOR_RESPONSE_STATE_SUMMARY;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final void performAction(
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamsHelper audioStreamsHelper) {
        Handler handler = this.mHandler;
        handler.removeCallbacksAndMessages(audioStreamPreference);
        AudioStreamPreference.AudioStream audioStream = audioStreamPreference.mAudioStream;
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                audioStream != null ? audioStream.mMetadata : null;
        if (bluetoothLeBroadcastMetadata != null) {
            if (audioStreamsHelper.mLeBroadcastAssistant == null) {
                Log.w("AudioStreamsHelper", "addSource(): LeBroadcastAssistant is null!");
            } else {
                ThreadUtils.postOnBackgroundThread(
                        new AudioStreamsHelper$$ExternalSyntheticLambda7(
                                audioStreamsHelper, bluetoothLeBroadcastMetadata));
            }
            this.mMetricsFeatureProvider.action(
                    audioStreamPreference.getContext(),
                    1945,
                    audioStreamPreference.getSourceOriginForLogging().ordinal());
            AudioStreamsRepository audioStreamsRepository = this.mAudioStreamsRepository;
            audioStreamsRepository.getClass();
            Log.d(
                    "AudioStreamsRepository",
                    "cacheMetadata(): broadcastId "
                            + bluetoothLeBroadcastMetadata.getBroadcastId()
                            + " saved in local cache.");
            audioStreamsRepository.mBroadcastIdToMetadataCacheMap.put(
                    Integer.valueOf(bluetoothLeBroadcastMetadata.getBroadcastId()),
                    bluetoothLeBroadcastMetadata);
            handler.postDelayed(
                    new AddSourceWaitForResponseState$$ExternalSyntheticLambda0(
                            this,
                            audioStreamPreference,
                            audioStreamsProgressCategoryController,
                            bluetoothLeBroadcastMetadata),
                    audioStreamPreference,
                    20000L);
        }
    }
}
