package com.android.settings.sound;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.media.MediaOutputUtils;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MediaOutputPreferenceController extends AudioSwitchPreferenceController {
    private static final String TAG = "MediaOutputPreferenceController";
    private final BluetoothLeBroadcast.Callback mBroadcastCallback;
    private LocalBluetoothLeBroadcast mLocalBluetoothLeBroadcast;
    private MediaController mMediaController;
    private MediaSessionManager mMediaSessionManager;

    public MediaOutputPreferenceController(Context context, String str) {
        super(context, str);
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.sound.MediaOutputPreferenceController.1
                    public final void onBroadcastStartFailed(int i) {
                        MediaOutputPreferenceController mediaOutputPreferenceController =
                                MediaOutputPreferenceController.this;
                        mediaOutputPreferenceController.updateState(
                                mediaOutputPreferenceController.mPreference);
                    }

                    public final void onBroadcastStarted(int i, int i2) {
                        MediaOutputPreferenceController mediaOutputPreferenceController =
                                MediaOutputPreferenceController.this;
                        mediaOutputPreferenceController.updateState(
                                mediaOutputPreferenceController.mPreference);
                    }

                    public final void onBroadcastStopFailed(int i) {
                        MediaOutputPreferenceController mediaOutputPreferenceController =
                                MediaOutputPreferenceController.this;
                        mediaOutputPreferenceController.updateState(
                                mediaOutputPreferenceController.mPreference);
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        MediaOutputPreferenceController mediaOutputPreferenceController =
                                MediaOutputPreferenceController.this;
                        mediaOutputPreferenceController.updateState(
                                mediaOutputPreferenceController.mPreference);
                    }

                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onBroadcastUpdateFailed(int i, int i2) {}

                    public final void onBroadcastUpdated(int i, int i2) {}

                    public final void onPlaybackStarted(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        MediaSessionManager mediaSessionManager =
                (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
        this.mMediaSessionManager = mediaSessionManager;
        this.mMediaController = MediaOutputUtils.getActiveLocalMediaController(mediaSessionManager);
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        if (localBluetoothManager != null) {
            this.mLocalBluetoothLeBroadcast =
                    localBluetoothManager.mProfileManager.mLeAudioBroadcast;
        }
    }

    private boolean isDeviceBroadcasting() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference.setVisible(
                !com.android.settingslib.Utils.isAudioModeOngoingCall(this.mContext));
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public BluetoothDevice findActiveDevice() {
        BluetoothDevice findActiveHearingAidDevice = findActiveHearingAidDevice();
        BluetoothDevice findActiveLeAudioDevice = findActiveLeAudioDevice();
        A2dpProfile a2dpProfile = this.mProfileManager.mA2dpProfile;
        if (findActiveHearingAidDevice != null) {
            return findActiveHearingAidDevice;
        }
        if (findActiveLeAudioDevice != null) {
            return findActiveLeAudioDevice;
        }
        if (a2dpProfile == null || a2dpProfile.getActiveDevice() == null) {
            return null;
        }
        return a2dpProfile.getActiveDevice();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public BluetoothDevice findActiveHearingAidDevice() {
        HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
        if (hearingAidProfile == null) {
            return null;
        }
        for (BluetoothDevice bluetoothDevice : hearingAidProfile.getActiveDevices()) {
            if (bluetoothDevice != null) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            this.mContext.sendBroadcast(
                    new Intent()
                            .setAction(
                                    "com.android.systemui.action.LAUNCH_SYSTEM_MEDIA_OUTPUT_DIALOG")
                            .setPackage("com.android.systemui"));
            return true;
        }
        if (mediaController == null) {
            return true;
        }
        this.mContext.sendBroadcast(
                new Intent()
                        .setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG")
                        .setPackage("com.android.systemui")
                        .putExtra("package_name", this.mMediaController.getPackageName())
                        .putExtra(
                                "key_media_session_token",
                                this.mMediaController.getSessionToken()));
        return true;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        super.onStart();
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothLeBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.registerServiceCallBack(
                    this.mContext.getMainExecutor(), this.mBroadcastCallback);
        }
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        super.onStop();
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mLocalBluetoothLeBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
        }
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        this.mMediaController =
                MediaOutputUtils.getActiveLocalMediaController(this.mMediaSessionManager);
        this.mPreference.setEnabled(true);
        if (com.android.settingslib.Utils.isAudioModeOngoingCall(this.mContext)) {
            this.mPreference.setVisible(false);
            preference.setSummary(
                    this.mContext.getText(R.string.media_out_summary_ongoing_call_state));
            return;
        }
        List<BluetoothDevice> connectedA2dpDevices = getConnectedA2dpDevices();
        List<BluetoothDevice> connectedHearingAidDevices = getConnectedHearingAidDevices();
        List<BluetoothDevice> connectedLeAudioDevices = getConnectedLeAudioDevices();
        BluetoothDevice findActiveDevice =
                (this.mAudioManager.getMode() != 0
                                || ((connectedA2dpDevices == null || connectedA2dpDevices.isEmpty())
                                        && ((connectedHearingAidDevices == null
                                                        || connectedHearingAidDevices.isEmpty())
                                                && (connectedLeAudioDevices == null
                                                        || connectedLeAudioDevices.isEmpty()))))
                        ? null
                        : findActiveDevice();
        MediaController mediaController = this.mMediaController;
        if (mediaController == null) {
            this.mPreference.setTitle(
                    this.mContext.getString(R.string.media_output_title_without_playing));
        } else {
            Preference preference2 = this.mPreference;
            Context context = this.mContext;
            preference2.setTitle(
                    context.getString(
                            R.string.media_output_label_title,
                            com.android.settings.Utils.getApplicationLabel(
                                    context, mediaController.getPackageName())));
        }
        if (!isDeviceBroadcasting()) {
            this.mPreference.setSummary(
                    findActiveDevice == null
                            ? this.mContext.getText(R.string.media_output_default_summary)
                            : findActiveDevice.getAlias());
        } else {
            this.mPreference.setSummary(R.string.media_output_audio_sharing);
            this.mPreference.setEnabled(false);
        }
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
