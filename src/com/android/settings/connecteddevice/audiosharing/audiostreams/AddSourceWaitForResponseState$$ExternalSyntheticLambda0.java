package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AddSourceWaitForResponseState$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ AddSourceWaitForResponseState f$0;
    public final /* synthetic */ AudioStreamPreference f$1;
    public final /* synthetic */ AudioStreamsProgressCategoryController f$2;
    public final /* synthetic */ BluetoothLeBroadcastMetadata f$3;

    public /* synthetic */ AddSourceWaitForResponseState$$ExternalSyntheticLambda0(
            AddSourceWaitForResponseState addSourceWaitForResponseState,
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.f$0 = addSourceWaitForResponseState;
        this.f$1 = audioStreamPreference;
        this.f$2 = audioStreamsProgressCategoryController;
        this.f$3 = bluetoothLeBroadcastMetadata;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AddSourceWaitForResponseState addSourceWaitForResponseState = this.f$0;
                AudioStreamPreference audioStreamPreference = this.f$1;
                AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                        this.f$2;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.f$3;
                addSourceWaitForResponseState.getClass();
                if (audioStreamPreference.isShown()
                        && audioStreamPreference.getAudioStreamState()
                                == AudioStreamsProgressCategoryController.AudioStreamState
                                        .ADD_SOURCE_WAIT_FOR_RESPONSE) {
                    audioStreamsProgressCategoryController.handleSourceFailedToConnect(
                            audioStreamPreference.getAudioStreamBroadcastId());
                    addSourceWaitForResponseState.mMetricsFeatureProvider.action(
                            audioStreamPreference.getContext(),
                            1954,
                            audioStreamPreference.getSourceOriginForLogging().ordinal());
                    ThreadUtils.postOnMainThread(
                            new AddSourceWaitForResponseState$$ExternalSyntheticLambda0(
                                    addSourceWaitForResponseState,
                                    audioStreamsProgressCategoryController,
                                    audioStreamPreference,
                                    bluetoothLeBroadcastMetadata));
                    break;
                }
                break;
            default:
                AddSourceWaitForResponseState addSourceWaitForResponseState2 = this.f$0;
                AudioStreamsProgressCategoryController audioStreamsProgressCategoryController2 =
                        this.f$2;
                AudioStreamPreference audioStreamPreference2 = this.f$1;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.f$3;
                addSourceWaitForResponseState2.getClass();
                if (audioStreamsProgressCategoryController2.getFragment() != null) {
                    AudioStreamsDashboardFragment fragment =
                            audioStreamsProgressCategoryController2.getFragment();
                    Context context = audioStreamPreference2.getContext();
                    String broadcastName =
                            AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata2);
                    AudioStreamsDialogFragment.DialogBuilder dialogBuilder =
                            new AudioStreamsDialogFragment.DialogBuilder(context);
                    dialogBuilder.mTitle =
                            context.getString(
                                    R.string.audio_streams_dialog_stream_is_not_available);
                    dialogBuilder.mSubTitle1 = broadcastName;
                    dialogBuilder.mSubTitle2 =
                            context.getString(R.string.audio_streams_is_not_playing);
                    dialogBuilder.mRightButtonText =
                            context.getString(R.string.audio_streams_dialog_close);
                    dialogBuilder.mRightButtonOnClickListener =
                            new AddSourceWaitForResponseState$$ExternalSyntheticLambda2();
                    AudioStreamsDialogFragment.show(fragment, dialogBuilder, 2098);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ AddSourceWaitForResponseState$$ExternalSyntheticLambda0(
            AddSourceWaitForResponseState addSourceWaitForResponseState,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamPreference audioStreamPreference,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.f$0 = addSourceWaitForResponseState;
        this.f$2 = audioStreamsProgressCategoryController;
        this.f$1 = audioStreamPreference;
        this.f$3 = bluetoothLeBroadcastMetadata;
    }
}
