package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.TwoTargetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AudioStreamPreference extends TwoTargetPreference {
    public AudioStream mAudioStream;
    public boolean mIsConnected;
    public boolean mIsEncrypted;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AudioStream {
        public BluetoothLeBroadcastMetadata mMetadata;
        public BluetoothLeBroadcastReceiveState mReceiveState;
        public SourceOriginForLogging mSourceOriginForLogging;
        public AudioStreamsProgressCategoryController.AudioStreamState mState;
    }

    public AudioStreamPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsConnected = false;
        this.mIsEncrypted = true;
        setIcon(R.drawable.ic_bt_le_audio_sharing);
    }

    public final int getAudioStreamBroadcastId() {
        AudioStream audioStream = this.mAudioStream;
        if (audioStream == null) {
            return -1;
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = audioStream.mMetadata;
        if (bluetoothLeBroadcastMetadata != null) {
            return bluetoothLeBroadcastMetadata.getBroadcastId();
        }
        BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState =
                audioStream.mReceiveState;
        if (bluetoothLeBroadcastReceiveState != null) {
            return bluetoothLeBroadcastReceiveState.getBroadcastId();
        }
        return -1;
    }

    public final AudioStreamsProgressCategoryController.AudioStreamState getAudioStreamState() {
        AudioStream audioStream = this.mAudioStream;
        return audioStream != null
                ? audioStream.mState
                : AudioStreamsProgressCategoryController.AudioStreamState.UNKNOWN;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final int getSecondTargetResId() {
        return R.layout.preference_widget_lock;
    }

    public final SourceOriginForLogging getSourceOriginForLogging() {
        AudioStream audioStream = this.mAudioStream;
        return audioStream != null
                ? audioStream.mSourceOriginForLogging
                : SourceOriginForLogging.UNKNOWN;
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
    }

    public final void setAudioStreamMetadata(
            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {
        AudioStream audioStream = this.mAudioStream;
        if (audioStream != null) {
            audioStream.mMetadata = bluetoothLeBroadcastMetadata;
            final String broadcastName =
                    AudioStreamsHelper.getBroadcastName(bluetoothLeBroadcastMetadata);
            ThreadUtils.postOnMainThread(
                    new Runnable() { // from class:
                                     // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamPreference$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AudioStreamPreference.this.setTitle(broadcastName);
                        }
                    });
        }
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return this.mIsConnected || !this.mIsEncrypted;
    }
}
