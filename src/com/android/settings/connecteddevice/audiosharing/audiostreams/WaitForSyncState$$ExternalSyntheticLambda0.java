package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.AlertDialog;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.utils.ThreadUtils;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WaitForSyncState$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ WaitForSyncState f$0;
    public final /* synthetic */ AudioStreamPreference f$1;
    public final /* synthetic */ AudioStreamsProgressCategoryController f$2;
    public final /* synthetic */ BluetoothLeBroadcastMetadata f$3;

    public /* synthetic */ WaitForSyncState$$ExternalSyntheticLambda0(
            WaitForSyncState waitForSyncState,
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.f$0 = waitForSyncState;
        this.f$1 = audioStreamPreference;
        this.f$2 = audioStreamsProgressCategoryController;
        this.f$3 = bluetoothLeBroadcastMetadata;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                WaitForSyncState waitForSyncState = this.f$0;
                AudioStreamPreference audioStreamPreference = this.f$1;
                AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                        this.f$2;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = this.f$3;
                waitForSyncState.getClass();
                if (audioStreamPreference.isShown()
                        && audioStreamPreference.getAudioStreamState()
                                == AudioStreamsProgressCategoryController.AudioStreamState
                                        .WAIT_FOR_SYNC) {
                    audioStreamsProgressCategoryController.handleSourceLost(
                            audioStreamPreference.getAudioStreamBroadcastId());
                    waitForSyncState.mMetricsFeatureProvider.action(
                            audioStreamPreference.getContext(),
                            1956,
                            audioStreamPreference.getSourceOriginForLogging().ordinal());
                    ThreadUtils.postOnMainThread(
                            new WaitForSyncState$$ExternalSyntheticLambda0(
                                    waitForSyncState,
                                    audioStreamsProgressCategoryController,
                                    audioStreamPreference,
                                    bluetoothLeBroadcastMetadata));
                    break;
                }
                break;
            default:
                final WaitForSyncState waitForSyncState2 = this.f$0;
                AudioStreamsProgressCategoryController audioStreamsProgressCategoryController2 =
                        this.f$2;
                AudioStreamPreference audioStreamPreference2 = this.f$1;
                BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 = this.f$3;
                waitForSyncState2.getClass();
                if (audioStreamsProgressCategoryController2.getFragment() != null) {
                    final AudioStreamsDashboardFragment fragment =
                            audioStreamsProgressCategoryController2.getFragment();
                    final Context context = audioStreamPreference2.getContext();
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
                    dialogBuilder.mLeftButtonText =
                            context.getString(R.string.audio_streams_dialog_close);
                    dialogBuilder.mLeftButtonOnClickListener =
                            new AddSourceWaitForResponseState$$ExternalSyntheticLambda2();
                    dialogBuilder.mRightButtonText =
                            context.getString(R.string.audio_streams_dialog_retry);
                    dialogBuilder.mRightButtonOnClickListener =
                            new Consumer() { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.audiostreams.WaitForSyncState$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    WaitForSyncState waitForSyncState3 = WaitForSyncState.this;
                                    Context context2 = context;
                                    Fragment fragment2 = fragment;
                                    waitForSyncState3.getClass();
                                    SubSettingLauncher subSettingLauncher =
                                            new SubSettingLauncher(context2);
                                    subSettingLauncher.setTitleRes(
                                            R.string.audio_streams_main_page_scan_qr_code_title,
                                            null);
                                    String name = AudioStreamsQrCodeScanFragment.class.getName();
                                    SubSettingLauncher.LaunchRequest launchRequest =
                                            subSettingLauncher.mLaunchRequest;
                                    launchRequest.mDestinationName = name;
                                    subSettingLauncher.setResultListener(fragment2, 0);
                                    launchRequest.mSourceMetricsCategory = 2097;
                                    subSettingLauncher.launch();
                                    ((AlertDialog) obj).dismiss();
                                }
                            };
                    AudioStreamsDialogFragment.show(fragment, dialogBuilder, 2097);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ WaitForSyncState$$ExternalSyntheticLambda0(
            WaitForSyncState waitForSyncState,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamPreference audioStreamPreference,
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        this.f$0 = waitForSyncState;
        this.f$2 = audioStreamsProgressCategoryController;
        this.f$1 = audioStreamPreference;
        this.f$3 = bluetoothLeBroadcastMetadata;
    }
}
