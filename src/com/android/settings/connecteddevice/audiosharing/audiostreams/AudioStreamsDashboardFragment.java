package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.bluetooth.BluetoothLeBroadcastMetadataExt;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsDashboardFragment extends DashboardFragment {
    public AudioStreamsProgressCategoryController mAudioStreamsProgressCategoryController;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AudioStreamsDashboardFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2093;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bluetooth_le_audio_streams;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "onActivityResult() requestCode : ",
                " resultCode : ",
                i,
                i2,
                "AudioStreamsDashboardFrag");
        if (i == 0 && i2 == -1) {
            BluetoothLeBroadcastMetadata convertToBroadcastMetadata =
                    BluetoothLeBroadcastMetadataExt.convertToBroadcastMetadata(
                            intent != null
                                    ? intent.getStringExtra("key_broadcast_metadata")
                                    : ApnSettings.MVNO_NONE);
            if (convertToBroadcastMetadata == null) {
                Log.w("AudioStreamsDashboardFrag", "onActivityResult() source is null!");
                return;
            }
            Log.d(
                    "AudioStreamsDashboardFrag",
                    "onActivityResult() broadcastId : "
                            + convertToBroadcastMetadata.getBroadcastId());
            AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                    this.mAudioStreamsProgressCategoryController;
            if (audioStreamsProgressCategoryController == null) {
                Log.w(
                        "AudioStreamsDashboardFrag",
                        "onActivityResult() AudioStreamsProgressCategoryController is null!");
            } else {
                audioStreamsProgressCategoryController.setSourceFromQrCode(
                        convertToBroadcastMetadata, SourceOriginForLogging.QR_CODE_SCAN_SETTINGS);
                this.mMetricsFeatureProvider.action(getContext(), 1950, 1);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata;
        super.onAttach(context);
        ((AudioStreamsScanQrCodeController) use(AudioStreamsScanQrCodeController.class))
                .setFragment(this);
        AudioStreamsProgressCategoryController audioStreamsProgressCategoryController =
                (AudioStreamsProgressCategoryController)
                        use(AudioStreamsProgressCategoryController.class);
        this.mAudioStreamsProgressCategoryController = audioStreamsProgressCategoryController;
        audioStreamsProgressCategoryController.setFragment(this);
        if (getArguments() == null
                || (bluetoothLeBroadcastMetadata =
                                (BluetoothLeBroadcastMetadata)
                                        getArguments()
                                                .getParcelable(
                                                        "key_broadcast_metadata",
                                                        BluetoothLeBroadcastMetadata.class))
                        == null) {
            return;
        }
        this.mAudioStreamsProgressCategoryController.setSourceFromQrCode(
                bluetoothLeBroadcastMetadata, SourceOriginForLogging.QR_CODE_SCAN_OTHER);
        this.mMetricsFeatureProvider.action(getContext(), 1950, 2);
    }
}
