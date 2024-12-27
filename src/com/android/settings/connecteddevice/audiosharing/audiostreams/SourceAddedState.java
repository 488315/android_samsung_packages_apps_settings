package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SourceAddedState extends AudioStreamStateHandler {
    static final int AUDIO_STREAM_SOURCE_ADDED_STATE_SUMMARY = 2132018374;
    public static SourceAddedState sInstance;

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final Preference.OnPreferenceClickListener getOnClickListener(
            final AudioStreamsProgressCategoryController audioStreamsProgressCategoryController) {
        return new Preference
                .OnPreferenceClickListener() { // from class:
                                               // com.android.settings.connecteddevice.audiosharing.audiostreams.SourceAddedState$$ExternalSyntheticLambda0
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                int i;
                AudioStreamPreference audioStreamPreference = (AudioStreamPreference) preference;
                Bundle bundle = new Bundle();
                bundle.putString("broadcast_name", (String) audioStreamPreference.getTitle());
                bundle.putInt("broadcast_id", audioStreamPreference.getAudioStreamBroadcastId());
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(audioStreamPreference.getContext());
                String string =
                        audioStreamPreference
                                .getContext()
                                .getString(R.string.audio_streams_detail_page_title);
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mTitle = string;
                launchRequest.mDestinationName = AudioStreamDetailsFragment.class.getName();
                AudioStreamsProgressCategoryController audioStreamsProgressCategoryController2 =
                        AudioStreamsProgressCategoryController.this;
                if (audioStreamsProgressCategoryController2.getFragment() == null) {
                    i = 0;
                } else {
                    audioStreamsProgressCategoryController2.getFragment().getClass();
                    i = 2093;
                }
                launchRequest.mSourceMetricsCategory = i;
                launchRequest.mArguments = bundle;
                subSettingLauncher.launch();
                return true;
            }
        };
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.SOURCE_ADDED;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final int getSummary() {
        return AUDIO_STREAM_SOURCE_ADDED_STATE_SUMMARY;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final void performAction(
            AudioStreamPreference audioStreamPreference,
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController,
            AudioStreamsHelper audioStreamsHelper) {
        final Context context = audioStreamPreference.getContext();
        final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                (BluetoothLeBroadcastMetadata)
                        this.mAudioStreamsRepository.mBroadcastIdToMetadataCacheMap.get(
                                Integer.valueOf(audioStreamPreference.getAudioStreamBroadcastId()));
        if (bluetoothLeBroadcastMetadata == null) {
            Log.w(
                    "AudioStreamsRepository",
                    "getCachedMetadata(): broadcastId not found in"
                        + " mBroadcastIdToMetadataCacheMap.");
            bluetoothLeBroadcastMetadata = null;
        }
        if (bluetoothLeBroadcastMetadata != null) {
            ThreadUtils.postOnBackgroundThread(
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsRepository$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            Context context2 = context;
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata2 =
                                    bluetoothLeBroadcastMetadata;
                            SharedPreferences sharedPreferences =
                                    context2.getSharedPreferences("bluetooth_audio_stream_pref", 0);
                            if (sharedPreferences != null) {
                                SharedPreferences.Editor edit = sharedPreferences.edit();
                                edit.putString(
                                        "bluetooth_audio_stream_metadata",
                                        BluetoothLeBroadcastMetadataExt.toQrCodeString(
                                                bluetoothLeBroadcastMetadata2));
                                edit.apply();
                                Log.d(
                                        "AudioStreamsRepository",
                                        "saveMetadata(): broadcastId "
                                                + bluetoothLeBroadcastMetadata2.getBroadcastId()
                                                + " metadata saved in storage.");
                            }
                        }
                    });
        }
        audioStreamsHelper.startMediaService(
                context,
                audioStreamPreference.getAudioStreamBroadcastId(),
                String.valueOf(audioStreamPreference.getTitle()));
        this.mMetricsFeatureProvider.action(
                audioStreamPreference.getContext(),
                1946,
                audioStreamPreference.getSourceOriginForLogging().ordinal());
    }
}
