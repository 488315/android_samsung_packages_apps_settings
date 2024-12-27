package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.bluetooth.BluetoothVolumeControl;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.VolumeControlProfile;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDeviceVolumeGroupController extends AudioSharingBasePreferenceController
        implements DevicePreferenceCallback {
    private static final String KEY = "audio_sharing_device_volume_group";
    private static final String TAG = "AudioSharingDeviceVolumeGroupController";
    private final LocalBluetoothLeBroadcastAssistant mAssistant;
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    private final LocalBluetoothManager mBtManager;
    private AtomicBoolean mCallbacksRegistered;
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;
    private PreferenceGroup mPreferenceGroup;
    private final LocalBluetoothProfileManager mProfileManager;
    private final ContentObserver mSettingsObserver;
    private Map<Integer, Integer> mValueMap;
    private final VolumeControlProfile mVolumeControl;
    BluetoothVolumeControl.Callback mVolumeControlCallback;
    private List<AudioSharingDeviceVolumePreference> mVolumePreferences;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.d(
                    AudioSharingDeviceVolumeGroupController.TAG,
                    "onChange, fallback device group id has been changed");
            for (AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference :
                    AudioSharingDeviceVolumeGroupController.this.mVolumePreferences) {
                audioSharingDeviceVolumePreference.setOrder(
                        AudioSharingDeviceVolumeGroupController.this.getPreferenceOrderForDevice(
                                audioSharingDeviceVolumePreference.mCachedDevice));
            }
        }
    }

    public AudioSharingDeviceVolumeGroupController(Context context) {
        super(context, KEY);
        this.mVolumePreferences = new ArrayList();
        this.mValueMap = new HashMap();
        this.mCallbacksRegistered = new AtomicBoolean(false);
        this.mVolumeControlCallback =
                new BluetoothVolumeControl
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceVolumeGroupController.1
                    public final void onDeviceVolumeChanged(
                            BluetoothDevice bluetoothDevice, int i) {
                        CachedBluetoothDevice findDevice =
                                AudioSharingDeviceVolumeGroupController.this.mBtManager == null
                                        ? null
                                        : AudioSharingDeviceVolumeGroupController.this.mBtManager
                                                .mCachedDeviceManager.findDevice(bluetoothDevice);
                        if (findDevice == null) {
                            return;
                        }
                        int groupId = AudioSharingUtils.getGroupId(findDevice);
                        AudioSharingDeviceVolumeGroupController.this.mValueMap.put(
                                Integer.valueOf(groupId), Integer.valueOf(i));
                        for (AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference :
                                AudioSharingDeviceVolumeGroupController.this.mVolumePreferences) {
                            CachedBluetoothDevice cachedBluetoothDevice =
                                    audioSharingDeviceVolumePreference.mCachedDevice;
                            if (cachedBluetoothDevice != null
                                    && AudioSharingUtils.getGroupId(cachedBluetoothDevice)
                                            == groupId) {
                                int audioVolumeIfNeeded =
                                        AudioSharingDeviceVolumeGroupController.this
                                                .getAudioVolumeIfNeeded(i);
                                StringBuilder m =
                                        ListPopupWindow$$ExternalSyntheticOutline0.m(
                                                audioVolumeIfNeeded,
                                                "onDeviceVolumeChanged: set volume to ",
                                                " for ");
                                m.append(bluetoothDevice.getAnonymizedAddress());
                                Log.d(AudioSharingDeviceVolumeGroupController.TAG, m.toString());
                                ((AbstractPreferenceController)
                                                AudioSharingDeviceVolumeGroupController.this)
                                        .mContext
                                        .getMainExecutor()
                                        .execute(
                                                new AudioSharingDeviceVolumeGroupController$$ExternalSyntheticLambda0(
                                                        audioSharingDeviceVolumePreference,
                                                        audioVolumeIfNeeded,
                                                        1));
                                return;
                            }
                        }
                    }
                };
        this.mBroadcastAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingDeviceVolumeGroupController.2
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        if (BluetoothUtils.isConnected(bluetoothLeBroadcastReceiveState)) {
                            Log.d(
                                    AudioSharingDeviceVolumeGroupController.TAG,
                                    "onReceiveStateChanged: synced, update volume list.");
                            if (AudioSharingDeviceVolumeGroupController.this.mBluetoothDeviceUpdater
                                    != null) {
                                AudioSharingDeviceVolumeGroupController.this.mBluetoothDeviceUpdater
                                        .forceUpdate();
                            }
                        }
                    }

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        Log.d(
                                AudioSharingDeviceVolumeGroupController.TAG,
                                "onSourceRemoved: update volume list.");
                        if (AudioSharingDeviceVolumeGroupController.this.mBluetoothDeviceUpdater
                                != null) {
                            AudioSharingDeviceVolumeGroupController.this.mBluetoothDeviceUpdater
                                    .forceUpdate();
                        }
                    }

                    public final void onSearchStartFailed(int i) {}

                    public final void onSearchStarted(int i) {}

                    public final void onSearchStopFailed(int i) {}

                    public final void onSearchStopped(int i) {}

                    public final void onSourceFound(
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onSourceAddFailed(
                            BluetoothDevice bluetoothDevice,
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                            int i) {}

                    public final void onSourceAdded(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModified(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModifyFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceRemoveFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}
                };
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mAssistant =
                localBluetoothProfileManager == null
                        ? null
                        : localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        this.mVolumeControl =
                localBluetoothProfileManager != null
                        ? localBluetoothProfileManager.mVolumeControlProfile
                        : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new SettingsObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getAudioVolumeIfNeeded(int i) {
        if (i >= 0) {
            return i;
        }
        try {
            AudioManager audioManager =
                    (AudioManager) this.mContext.getSystemService(AudioManager.class);
            return Math.round(
                    (audioManager.getStreamVolume(3) * 255.0f)
                            / (audioManager.getStreamMaxVolume(3)
                                    - audioManager.getStreamMinVolume(3)));
        } catch (RuntimeException e) {
            Log.e(TAG, "Fail to fetch current music stream volume, error = " + e);
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getPreferenceOrderForDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        int groupId = AudioSharingUtils.getGroupId(cachedBluetoothDevice);
        return (groupId == -1
                        || groupId
                                != Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "bluetooth_le_broadcast_fallback_active_group_id",
                                        -1))
                ? 1
                : 0;
    }

    private void registerCallbacks() {
        if (!isAvailable()) {
            Log.d(TAG, "Skip registerCallbacks(). Feature is not available.");
            return;
        }
        if (this.mAssistant == null
                || this.mVolumeControl == null
                || this.mBluetoothDeviceUpdater == null
                || this.mContentResolver == null
                || !AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            Log.d(TAG, "Skip registerCallbacks(). Profile is not ready.");
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            return;
        }
        Log.d(TAG, "registerCallbacks()");
        this.mAssistant.registerServiceCallBack(this.mExecutor, this.mBroadcastAssistantCallback);
        VolumeControlProfile volumeControlProfile = this.mVolumeControl;
        Executor executor = this.mExecutor;
        BluetoothVolumeControl.Callback callback = this.mVolumeControlCallback;
        BluetoothVolumeControl bluetoothVolumeControl = volumeControlProfile.mService;
        if (bluetoothVolumeControl == null) {
            Log.w(
                    "VolumeControlProfile",
                    "Proxy not attached to service. Cannot register callback.");
        } else {
            bluetoothVolumeControl.registerCallback(executor, callback);
        }
        this.mBluetoothDeviceUpdater.registerCallback();
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("bluetooth_le_broadcast_fallback_active_group_id"),
                false,
                this.mSettingsObserver);
        this.mCallbacksRegistered.set(true);
    }

    private void unregisterCallbacks() {
        if (!isAvailable()) {
            Log.d(TAG, "Skip unregister callbacks. Feature is not available.");
            return;
        }
        if (this.mAssistant == null
                || this.mVolumeControl == null
                || this.mBluetoothDeviceUpdater == null
                || this.mContentResolver == null
                || !AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            Log.d(TAG, "Skip unregisterCallbacks(). Profile is not ready.");
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            Log.d(TAG, "unregisterCallbacks()");
            this.mAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
            VolumeControlProfile volumeControlProfile = this.mVolumeControl;
            BluetoothVolumeControl.Callback callback = this.mVolumeControlCallback;
            BluetoothVolumeControl bluetoothVolumeControl = volumeControlProfile.mService;
            if (bluetoothVolumeControl == null) {
                Log.w(
                        "VolumeControlProfile",
                        "Proxy not attached to service. Cannot unregister callback.");
            } else {
                bluetoothVolumeControl.unregisterCallback(callback);
            }
            this.mBluetoothDeviceUpdater.unregisterCallback();
            this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
            this.mValueMap.clear();
            this.mCallbacksRegistered.set(false);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        BluetoothDeviceUpdater bluetoothDeviceUpdater;
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup = (PreferenceGroup) preferenceScreen.findPreference(KEY);
        this.mPreferenceGroup = preferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.setVisible(false);
        }
        if (!isAvailable() || (bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater) == null) {
            return;
        }
        bluetoothDeviceUpdater.mPrefContext = preferenceScreen.getContext();
        this.mBluetoothDeviceUpdater.forceUpdate();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
    }

    public ContentObserver getSettingsObserver() {
        return this.mSettingsObserver;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public void init(DashboardFragment dashboardFragment) {
        this.mBluetoothDeviceUpdater =
                new AudioSharingDeviceVolumeControlUpdater(
                        dashboardFragment.getContext(),
                        this,
                        dashboardFragment.getMetricsCategory());
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public void onAudioSharingProfilesConnected() {
        registerCallbacks();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        this.mVolumePreferences.clear();
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceAdded(Preference preference) {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup != null) {
            if (preferenceGroup.getPreferenceCount() == 0) {
                this.mPreferenceGroup.setVisible(true);
            }
            this.mPreferenceGroup.addPreference(preference);
        }
        if (preference instanceof AudioSharingDeviceVolumePreference) {
            AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference =
                    (AudioSharingDeviceVolumePreference) preference;
            CachedBluetoothDevice cachedBluetoothDevice =
                    audioSharingDeviceVolumePreference.mCachedDevice;
            audioSharingDeviceVolumePreference.setOrder(
                    getPreferenceOrderForDevice(cachedBluetoothDevice));
            this.mVolumePreferences.add(audioSharingDeviceVolumePreference);
            if (audioSharingDeviceVolumePreference.mProgress > 0) {
                return;
            }
            int audioVolumeIfNeeded =
                    getAudioVolumeIfNeeded(
                            this.mValueMap
                                    .getOrDefault(
                                            Integer.valueOf(
                                                    AudioSharingUtils.getGroupId(
                                                            cachedBluetoothDevice)),
                                            -1)
                                    .intValue());
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            audioVolumeIfNeeded, "onDeviceAdded: set volume to ", " for ");
            m.append(cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            Log.d(TAG, m.toString());
            AudioSharingUtils.postOnMainThread(
                    this.mContext,
                    new AudioSharingDeviceVolumeGroupController$$ExternalSyntheticLambda0(
                            audioSharingDeviceVolumePreference, audioVolumeIfNeeded, 0));
        }
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceRemoved(Preference preference) {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.removePreference(preference);
            if (this.mPreferenceGroup.getPreferenceCount() == 0) {
                this.mPreferenceGroup.setVisible(false);
            }
        }
        if (preference instanceof AudioSharingDeviceVolumePreference) {
            AudioSharingDeviceVolumePreference audioSharingDeviceVolumePreference =
                    (AudioSharingDeviceVolumePreference) preference;
            if (this.mVolumePreferences.contains(audioSharingDeviceVolumePreference)) {
                this.mVolumePreferences.remove(audioSharingDeviceVolumePreference);
            }
            CachedBluetoothDevice cachedBluetoothDevice =
                    audioSharingDeviceVolumePreference.mCachedDevice;
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onDeviceRemoved: "),
                    cachedBluetoothDevice == null
                            ? "null"
                            : cachedBluetoothDevice.mDevice.getAnonymizedAddress(),
                    TAG);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
        registerCallbacks();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
        unregisterCallbacks();
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setCallbacksRegistered(boolean z) {
        this.mCallbacksRegistered.set(z);
    }

    public void setDeviceUpdater(
            AudioSharingDeviceVolumeControlUpdater audioSharingDeviceVolumeControlUpdater) {
        this.mBluetoothDeviceUpdater = audioSharingDeviceVolumeControlUpdater;
    }

    public void setPreferenceGroup(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        this.mPreference = preferenceGroup;
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public void setVolumeMap(Map<Integer, Integer> map) {
        this.mValueMap.clear();
        this.mValueMap.putAll(map);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController
    public void updateVisibility() {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup == null || preferenceGroup.getPreferenceCount() != 0) {
            super.updateVisibility();
        } else {
            this.mPreferenceGroup.setVisible(false);
        }
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}
}
