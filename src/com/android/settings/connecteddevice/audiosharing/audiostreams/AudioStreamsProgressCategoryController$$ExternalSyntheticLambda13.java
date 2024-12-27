package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;

import androidx.preference.Preference;

import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AudioStreamsProgressCategoryController$$ExternalSyntheticLambda13
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        AudioStreamPreference.AudioStream audioStream = ((AudioStreamPreference) obj).mAudioStream;
        if (audioStream == null) {
            return -1;
        }
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata = audioStream.mMetadata;
        return bluetoothLeBroadcastMetadata != null
                ? bluetoothLeBroadcastMetadata.getRssi()
                : Preference.DEFAULT_ORDER;
    }
}
