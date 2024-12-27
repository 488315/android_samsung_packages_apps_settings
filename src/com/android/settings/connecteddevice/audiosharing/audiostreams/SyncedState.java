package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.AlertDialog;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.utils.ThreadUtils;

import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SyncedState extends AudioStreamStateHandler {
    public static SyncedState sInstance;

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public final Preference.OnPreferenceClickListener getOnClickListener(
            final AudioStreamsProgressCategoryController audioStreamsProgressCategoryController) {
        return new Preference
                .OnPreferenceClickListener() { // from class:
                                               // com.android.settings.connecteddevice.audiosharing.audiostreams.SyncedState$$ExternalSyntheticLambda0
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                final SyncedState syncedState = SyncedState.this;
                syncedState.getClass();
                final AudioStreamPreference audioStreamPreference =
                        (AudioStreamPreference) preference;
                Log.d(
                        "SyncedState",
                        "preferenceClicked(): attempt to join broadcast id : "
                                + audioStreamPreference.getAudioStreamBroadcastId());
                AudioStreamPreference.AudioStream audioStream = audioStreamPreference.mAudioStream;
                final BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata =
                        audioStream != null ? audioStream.mMetadata : null;
                if (bluetoothLeBroadcastMetadata == null) {
                    return true;
                }
                boolean isEncrypted = bluetoothLeBroadcastMetadata.isEncrypted();
                final AudioStreamsProgressCategoryController
                        audioStreamsProgressCategoryController2 =
                                audioStreamsProgressCategoryController;
                if (isEncrypted) {
                    ThreadUtils.postOnMainThread(
                            new Runnable() { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.audiostreams.SyncedState$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SyncedState syncedState2 = SyncedState.this;
                                    final BluetoothLeBroadcastMetadata
                                            bluetoothLeBroadcastMetadata2 =
                                                    bluetoothLeBroadcastMetadata;
                                    final AudioStreamPreference audioStreamPreference2 =
                                            audioStreamPreference;
                                    final AudioStreamsProgressCategoryController
                                            audioStreamsProgressCategoryController3 =
                                                    audioStreamsProgressCategoryController2;
                                    syncedState2.getClass();
                                    final View inflate =
                                            LayoutInflater.from(audioStreamPreference2.getContext())
                                                    .inflate(
                                                            R.layout
                                                                    .bluetooth_find_broadcast_password_dialog,
                                                            (ViewGroup) null);
                                    ((TextView) inflate.requireViewById(R.id.broadcast_name_text))
                                            .setText(audioStreamPreference2.getTitle());
                                    new AlertDialog.Builder(audioStreamPreference2.getContext())
                                            .setTitle(R.string.find_broadcast_password_dialog_title)
                                            .setView(inflate)
                                            .setNeutralButton(
                                                    android.R.string.cancel,
                                                    (DialogInterface.OnClickListener) null)
                                            .setPositiveButton(
                                                    R.string
                                                            .bluetooth_connect_access_dialog_positive,
                                                    new DialogInterface
                                                            .OnClickListener() { // from class:
                                                                                 // com.android.settings.connecteddevice.audiosharing.audiostreams.SyncedState$$ExternalSyntheticLambda2
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(
                                                                DialogInterface dialogInterface,
                                                                int i) {
                                                            View view = inflate;
                                                            BluetoothLeBroadcastMetadata
                                                                    bluetoothLeBroadcastMetadata3 =
                                                                            bluetoothLeBroadcastMetadata2;
                                                            audioStreamsProgressCategoryController3
                                                                    .handleSourceAddRequest(
                                                                            audioStreamPreference2,
                                                                            new BluetoothLeBroadcastMetadata
                                                                                            .Builder(
                                                                                            bluetoothLeBroadcastMetadata3)
                                                                                    .setBroadcastCode(
                                                                                            ((EditText)
                                                                                                            view
                                                                                                                    .requireViewById(
                                                                                                                            R
                                                                                                                                    .id
                                                                                                                                    .broadcast_edit_text))
                                                                                                    .getText()
                                                                                                    .toString()
                                                                                                    .getBytes(
                                                                                                            StandardCharsets
                                                                                                                    .UTF_8))
                                                                                    .build());
                                                        }
                                                    })
                                            .create()
                                            .show();
                                }
                            });
                    return true;
                }
                audioStreamsProgressCategoryController2.handleSourceAddRequest(
                        audioStreamPreference, bluetoothLeBroadcastMetadata);
                return true;
            }
        };
    }

    @Override // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamStateHandler
    public AudioStreamsProgressCategoryController.AudioStreamState getStateEnum() {
        return AudioStreamsProgressCategoryController.AudioStreamState.SYNCED;
    }
}
