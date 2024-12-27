package com.android.settings.connecteddevice.audiosharing.audiostreams;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AddSourceFailedState extends SyncedState {
    static final int AUDIO_STREAM_ADD_SOURCE_FAILED_STATE_SUMMARY = 2132018352;
    public static AddSourceFailedState sInstance;

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.SyncedState,
              // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.ADD_SOURCE_FAILED;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final int getSummary() {
        return AUDIO_STREAM_ADD_SOURCE_FAILED_STATE_SUMMARY;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final void performAction(
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamsHelper audioStreamsHelper) {
        this.mMetricsFeatureProvider.action(
                audioStreamPreference.getContext(),
                1955,
                audioStreamPreference.getSourceOriginForLogging().ordinal());
    }
}
