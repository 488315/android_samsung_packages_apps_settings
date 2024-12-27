package com.android.settings.sound;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothLeAudio;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.Looper;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.sec.ims.presence.ServiceTuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioSwitchPreferenceController extends BasePreferenceController
        implements BluetoothCallback, LifecycleObserver, OnStart, OnStop {
    private static final String TAG = "AudioSwitchPrefCtrl";
    protected final AudioManager mAudioManager;
    private final AudioManagerAudioDeviceCallback mAudioManagerAudioDeviceCallback;
    protected AudioSwitchCallback mAudioSwitchPreferenceCallback;
    protected final List<BluetoothDevice> mConnectedDevices;
    private final Handler mHandler;
    private LocalBluetoothManager mLocalBluetoothManager;
    protected final MediaRouter mMediaRouter;
    private MediaSessionManager mMediaSessionManager;
    protected Preference mPreference;
    protected LocalBluetoothProfileManager mProfileManager;
    private final WiredHeadsetBroadcastReceiver mReceiver;
    protected int mSelectedIndex;
    private MediaSessionManager.OnActiveSessionsChangedListener mSessionListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AudioManagerAudioDeviceCallback extends AudioDeviceCallback {
        public AudioManagerAudioDeviceCallback() {}

        @Override // android.media.AudioDeviceCallback
        public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
            AudioSwitchPreferenceController audioSwitchPreferenceController =
                    AudioSwitchPreferenceController.this;
            audioSwitchPreferenceController.updateState(
                    audioSwitchPreferenceController.mPreference);
        }

        @Override // android.media.AudioDeviceCallback
        public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
            AudioSwitchPreferenceController audioSwitchPreferenceController =
                    AudioSwitchPreferenceController.this;
            audioSwitchPreferenceController.updateState(
                    audioSwitchPreferenceController.mPreference);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface AudioSwitchCallback {
        void onPreferenceDataChanged(ListPreference listPreference);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SessionChangeListener
            implements MediaSessionManager.OnActiveSessionsChangedListener {
        public SessionChangeListener() {}

        @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
        public final void onActiveSessionsChanged(List list) {
            AudioSwitchPreferenceController audioSwitchPreferenceController =
                    AudioSwitchPreferenceController.this;
            audioSwitchPreferenceController.updateState(
                    audioSwitchPreferenceController.mPreference);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WiredHeadsetBroadcastReceiver extends BroadcastReceiver {
        public WiredHeadsetBroadcastReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.HEADSET_PLUG".equals(action)
                    || "android.media.STREAM_DEVICES_CHANGED_ACTION".equals(action)) {
                AudioSwitchPreferenceController audioSwitchPreferenceController =
                        AudioSwitchPreferenceController.this;
                audioSwitchPreferenceController.updateState(
                        audioSwitchPreferenceController.mPreference);
            }
        }
    }

    public AudioSwitchPreferenceController(Context context, String str) {
        super(context, str);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        this.mMediaRouter = (MediaRouter) context.getSystemService("media_router");
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mAudioManagerAudioDeviceCallback = new AudioManagerAudioDeviceCallback();
        this.mReceiver = new WiredHeadsetBroadcastReceiver();
        this.mConnectedDevices = new ArrayList();
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.android.settings.sound.AudioSwitchPreferenceController$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                LocalBluetoothManager lambda$new$0;
                                lambda$new$0 = AudioSwitchPreferenceController.this.lambda$new$0();
                                return lambda$new$0;
                            }
                        });
        try {
            futureTask.run();
            LocalBluetoothManager localBluetoothManager = (LocalBluetoothManager) futureTask.get();
            this.mLocalBluetoothManager = localBluetoothManager;
            if (localBluetoothManager == null) {
                Log.e(TAG, "Bluetooth is not supported on this device");
                return;
            }
            this.mProfileManager = localBluetoothManager.mProfileManager;
            this.mMediaSessionManager =
                    (MediaSessionManager) context.getSystemService(MediaSessionManager.class);
            this.mSessionListener = new SessionChangeListener();
        } catch (InterruptedException | ExecutionException e) {
            Log.w(TAG, "Error getting LocalBluetoothManager.", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LocalBluetoothManager lambda$new$0() throws Exception {
        return LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
    }

    private void register() {
        this.mLocalBluetoothManager.mEventManager.registerCallback(this);
        this.mAudioManager.registerAudioDeviceCallback(
                this.mAudioManagerAudioDeviceCallback, this.mHandler);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
        intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
        this.mContext.registerReceiver(this.mReceiver, intentFilter, 4);
        MediaSessionManager mediaSessionManager = this.mMediaSessionManager;
        if (mediaSessionManager != null) {
            mediaSessionManager.addOnActiveSessionsChangedListener(
                    this.mSessionListener, null, this.mHandler);
        }
    }

    private void unregister() {
        this.mLocalBluetoothManager.mEventManager.unregisterCallback(this);
        this.mAudioManager.unregisterAudioDeviceCallback(this.mAudioManagerAudioDeviceCallback);
        this.mContext.unregisterReceiver(this.mReceiver);
        MediaSessionManager mediaSessionManager = this.mMediaSessionManager;
        if (mediaSessionManager != null) {
            mediaSessionManager.removeOnActiveSessionsChangedListener(this.mSessionListener);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(this.mPreferenceKey);
        this.mPreference = findPreference;
        findPreference.setVisible(false);
    }

    public abstract BluetoothDevice findActiveDevice();

    public BluetoothDevice findActiveHearingAidDevice() {
        HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
        if (hearingAidProfile == null) {
            return null;
        }
        for (BluetoothDevice bluetoothDevice : hearingAidProfile.getActiveDevices()) {
            if (bluetoothDevice != null && this.mConnectedDevices.contains(bluetoothDevice)) {
                return bluetoothDevice;
            }
        }
        return null;
    }

    public BluetoothDevice findActiveLeAudioDevice() {
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        if (leAudioProfile != null) {
            for (BluetoothDevice bluetoothDevice : leAudioProfile.getActiveDevices()) {
                if (bluetoothDevice != null) {
                    return bluetoothDevice;
                }
            }
        }
        Log.d(TAG, "There is no LE audio profile or no active LE audio device");
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public final int getAvailabilityStatus() {
        return (FeatureFlagUtils.isEnabled(this.mContext, "settings_audio_switcher")
                        && this.mContext
                                .getPackageManager()
                                .hasSystemFeature("android.hardware.bluetooth"))
                ? 0
                : 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public List<BluetoothDevice> getConnectedA2dpDevices() {
        A2dpProfile a2dpProfile = this.mProfileManager.mA2dpProfile;
        if (a2dpProfile == null) {
            return new ArrayList();
        }
        int[] iArr = {2, 1, 3};
        BluetoothA2dp bluetoothA2dp = a2dpProfile.mService;
        return bluetoothA2dp == null
                ? new ArrayList(0)
                : bluetoothA2dp.getDevicesMatchingConnectionStates(iArr);
    }

    public List<BluetoothDevice> getConnectedHearingAidDevices() {
        ArrayList arrayList = new ArrayList();
        HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
        if (hearingAidProfile == null) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        int[] iArr = {2, 1, 3};
        BluetoothHearingAid bluetoothHearingAid = hearingAidProfile.mService;
        for (BluetoothDevice bluetoothDevice :
                bluetoothHearingAid == null
                        ? new ArrayList<>(0)
                        : bluetoothHearingAid.getDevicesMatchingConnectionStates(iArr)) {
            long hiSyncId = hearingAidProfile.getHiSyncId(bluetoothDevice);
            if (!arrayList2.contains(Long.valueOf(hiSyncId)) && bluetoothDevice.isConnected()) {
                arrayList2.add(Long.valueOf(hiSyncId));
                arrayList.add(bluetoothDevice);
            }
        }
        return arrayList;
    }

    public List<BluetoothDevice> getConnectedHfpDevices() {
        ArrayList arrayList = new ArrayList();
        HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
        if (headsetProfile == null) {
            return arrayList;
        }
        BluetoothHeadset bluetoothHeadset = headsetProfile.mService;
        for (BluetoothDevice bluetoothDevice :
                bluetoothHeadset == null
                        ? new ArrayList<>(0)
                        : bluetoothHeadset.getDevicesMatchingConnectionStates(
                                new int[] {2, 1, 3})) {
            if (bluetoothDevice.isConnected()) {
                arrayList.add(bluetoothDevice);
            }
        }
        return arrayList;
    }

    public List<BluetoothDevice> getConnectedLeAudioDevices() {
        ArrayList arrayList = new ArrayList();
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        if (leAudioProfile == null) {
            Log.d(TAG, "LeAudioProfile is null");
            return arrayList;
        }
        int[] iArr = {2, 1, 3};
        BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
        List<BluetoothDevice> arrayList2 =
                bluetoothLeAudio == null
                        ? new ArrayList<>(0)
                        : bluetoothLeAudio.getDevicesMatchingConnectionStates(iArr);
        if (arrayList2 == null) {
            Log.d(TAG, "No connected LeAudioProfile devices");
            return arrayList;
        }
        for (BluetoothDevice bluetoothDevice : arrayList2) {
            if (bluetoothDevice.isConnected() && isDeviceInCachedList(bluetoothDevice)) {
                arrayList.add(bluetoothDevice);
            }
        }
        return arrayList;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isDeviceInCachedList(BluetoothDevice bluetoothDevice) {
        Iterator it =
                ((ArrayList)
                                this.mLocalBluetoothManager.mCachedDeviceManager
                                        .getCachedDevicesCopy())
                        .iterator();
        while (it.hasNext()) {
            if (((CachedBluetoothDevice) it.next()).mDevice.equals(bluetoothDevice)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public boolean isStreamFromOutputDevice(int i, int i2) {
        return (this.mAudioManager.getDevicesForStream(i) & i2) != 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onAudioModeChanged() {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onBluetoothStateChanged(int i) {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        updateState(this.mPreference);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e(TAG, "Bluetooth is not supported on this device");
        } else {
            localBluetoothManager.setForegroundActivity(this.mContext);
            register();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            Log.e(TAG, "Bluetooth is not supported on this device");
        } else {
            localBluetoothManager.setForegroundActivity(null);
            unregister();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setCallback(AudioSwitchCallback audioSwitchCallback) {
        this.mAudioSwitchPreferenceCallback = audioSwitchCallback;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
