package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.collect.ImmutableList;
import com.samsung.android.knox.net.apn.ApnSettings;
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
public class AudioSharingCallAudioPreferenceController extends AudioSharingBasePreferenceController
        implements BluetoothCallback {
    private static final String PREF_KEY = "calls_and_alarms";
    private static final String TAG = "CallsAndAlarmsPreferenceController";
    private final LocalBluetoothLeBroadcastAssistant mAssistant;
    final BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    private final LocalBluetoothManager mBtManager;
    private final CachedBluetoothDeviceManager mCacheManager;
    private final AtomicBoolean mCallbacksRegistered;
    private final ContentResolver mContentResolver;
    private List<AudioSharingDeviceItem> mDeviceItemsInSharingSession;
    private final BluetoothEventManager mEventManager;
    private final Executor mExecutor;
    private Fragment mFragment;
    Map<Integer, List<CachedBluetoothDevice>> mGroupedConnectedDevices;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final ContentObserver mSettingsObserver;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class ChangeCallAudioType {
        public static final /* synthetic */ ChangeCallAudioType[] $VALUES;
        public static final ChangeCallAudioType CONNECTED_EARLIER;
        public static final ChangeCallAudioType CONNECTED_LATER;
        public static final ChangeCallAudioType UNKNOWN;

        static {
            ChangeCallAudioType changeCallAudioType = new ChangeCallAudioType("UNKNOWN", 0);
            UNKNOWN = changeCallAudioType;
            ChangeCallAudioType changeCallAudioType2 =
                    new ChangeCallAudioType("CONNECTED_EARLIER", 1);
            CONNECTED_EARLIER = changeCallAudioType2;
            ChangeCallAudioType changeCallAudioType3 =
                    new ChangeCallAudioType("CONNECTED_LATER", 2);
            CONNECTED_LATER = changeCallAudioType3;
            $VALUES =
                    new ChangeCallAudioType[] {
                        changeCallAudioType, changeCallAudioType2, changeCallAudioType3
                    };
        }

        public static ChangeCallAudioType valueOf(String str) {
            return (ChangeCallAudioType) Enum.valueOf(ChangeCallAudioType.class, str);
        }

        public static ChangeCallAudioType[] values() {
            return (ChangeCallAudioType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FallbackDeviceGroupIdSettingsObserver extends ContentObserver {
        public FallbackDeviceGroupIdSettingsObserver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            Log.d(
                    AudioSharingCallAudioPreferenceController.TAG,
                    "onChange, fallback device group id has been changed");
            ThreadUtils.postOnBackgroundThread(
                    new AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda1(
                            AudioSharingCallAudioPreferenceController.this, 1));
        }
    }

    public AudioSharingCallAudioPreferenceController(Context context) {
        super(context, PREF_KEY);
        this.mGroupedConnectedDevices = new HashMap();
        this.mDeviceItemsInSharingSession = new ArrayList();
        this.mCallbacksRegistered = new AtomicBoolean(false);
        this.mBroadcastAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingCallAudioPreferenceController.1
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        if (BluetoothUtils.isConnected(bluetoothLeBroadcastReceiveState)) {
                            Log.d(
                                    AudioSharingCallAudioPreferenceController.TAG,
                                    "onReceiveStateChanged: synced, updateSummary");
                            AudioSharingCallAudioPreferenceController.this.updateSummary();
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

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}
                };
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mEventManager =
                localBluetoothManager == null ? null : localBluetoothManager.mEventManager;
        this.mAssistant =
                localBluetoothProfileManager == null
                        ? null
                        : localBluetoothProfileManager.mLeAudioBroadcastAssistant;
        this.mCacheManager =
                localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new FallbackDeviceGroupIdSettingsObserver();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private void lambda$displayPreference$0(AudioSharingDeviceItem audioSharingDeviceItem) {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_le_broadcast_fallback_active_group_id",
                        -1);
        int i2 = audioSharingDeviceItem.mGroupId;
        if (i2 == i) {
            Log.d(TAG, "Skip set fallback active device: unchanged");
            return;
        }
        CachedBluetoothDevice leadDevice =
                AudioSharingUtils.getLeadDevice(
                        this.mGroupedConnectedDevices.getOrDefault(
                                Integer.valueOf(i2), ImmutableList.of()));
        if (leadDevice == null) {
            Log.d(TAG, "Fail to set fallback active device: no lead device");
            return;
        }
        Log.d(TAG, "Set fallback active device: " + leadDevice.mDevice.getAnonymizedAddress());
        leadDevice.setActive();
        logCallAudioDeviceChange(i, leadDevice);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$displayPreference$1(Preference preference) {
        if (this.mFragment == null) {
            Log.w(TAG, "Dialog fail to show due to null host.");
            return true;
        }
        updateDeviceItemsInSharingSession();
        if (!this.mDeviceItemsInSharingSession.isEmpty()) {
            BluetoothAdapter.getDefaultAdapter();
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$logCallAudioDeviceChange$4(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {
        ChangeCallAudioType changeCallAudioType = ChangeCallAudioType.UNKNOWN;
        if (this.mCacheManager != null) {
            int groupId = AudioSharingUtils.getGroupId(cachedBluetoothDevice);
            List mostRecentlyConnectedDevices =
                    BluetoothAdapter.getDefaultAdapter().getMostRecentlyConnectedDevices();
            int i2 = -1;
            int i3 = -1;
            for (int i4 = 0; i4 < mostRecentlyConnectedDevices.size(); i4++) {
                CachedBluetoothDevice findDevice =
                        this.mCacheManager.findDevice(
                                (BluetoothDevice) mostRecentlyConnectedDevices.get(i4));
                int groupId2 = findDevice != null ? AudioSharingUtils.getGroupId(findDevice) : -1;
                if (groupId2 != -1) {
                    if (groupId2 == groupId) {
                        i2 = i4;
                    } else if (groupId2 == i) {
                        i3 = i4;
                    }
                }
                if (i2 != -1 && i3 != -1) {
                    break;
                }
            }
            if (i2 != -1 && i3 != -1) {
                changeCallAudioType =
                        i2 < i3
                                ? ChangeCallAudioType.CONNECTED_LATER
                                : ChangeCallAudioType.CONNECTED_EARLIER;
            }
        }
        this.mMetricsFeatureProvider.action(this.mContext, 1930, changeCallAudioType.ordinal());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateSummary$2(AudioSharingDeviceItem audioSharingDeviceItem) {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(
                    this.mContext.getString(
                            R.string.audio_sharing_call_audio_description,
                            audioSharingDeviceItem.mName));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateSummary$3() {
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setSummary(ApnSettings.MVNO_NONE);
        }
    }

    private void registerCallbacks() {
        if (!isAvailable()) {
            Log.d(TAG, "Skip registerCallbacks(). Feature is not available.");
            return;
        }
        if (this.mEventManager == null
                || this.mContentResolver == null
                || this.mAssistant == null) {
            StringBuilder sb =
                    new StringBuilder(
                            "Skip registerCallbacks(). Init is not ready: eventManager = ");
            sb.append(this.mEventManager == null);
            sb.append(", contentResolver");
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    sb, this.mContentResolver == null, TAG);
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            return;
        }
        Log.d(TAG, "registerCallbacks()");
        this.mEventManager.registerCallback(this);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("bluetooth_le_broadcast_fallback_active_group_id"),
                false,
                this.mSettingsObserver);
        this.mAssistant.registerServiceCallBack(this.mExecutor, this.mBroadcastAssistantCallback);
        this.mCallbacksRegistered.set(true);
    }

    private void unregisterCallbacks() {
        if (!isAvailable()) {
            Log.d(TAG, "Skip unregisterCallbacks(). Feature is not available.");
            return;
        }
        if (this.mEventManager == null
                || this.mContentResolver == null
                || this.mAssistant == null) {
            Log.d(TAG, "Skip unregisterCallbacks(). Init is not ready.");
            return;
        }
        if (this.mCallbacksRegistered.get()) {
            Log.d(TAG, "unregisterCallbacks()");
            this.mEventManager.unregisterCallback(this);
            this.mContentResolver.unregisterContentObserver(this.mSettingsObserver);
            this.mAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
            this.mCallbacksRegistered.set(false);
        }
    }

    private void updateDeviceItemsInSharingSession() {
        Map<Integer, List<CachedBluetoothDevice>> fetchConnectedDevicesByGroupId =
                AudioSharingUtils.fetchConnectedDevicesByGroupId(this.mBtManager);
        this.mGroupedConnectedDevices = fetchConnectedDevicesByGroupId;
        this.mDeviceItemsInSharingSession =
                AudioSharingUtils.buildOrderedConnectedLeadAudioSharingDeviceItem(
                        this.mBtManager, fetchConnectedDevicesByGroupId, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSummary() {
        updateDeviceItemsInSharingSession();
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_le_broadcast_fallback_active_group_id",
                        -1);
        if (i != -1) {
            for (final AudioSharingDeviceItem audioSharingDeviceItem :
                    this.mDeviceItemsInSharingSession) {
                if (audioSharingDeviceItem.mGroupId == i) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            i, "updatePreference: set summary to fallback group ", TAG);
                    AudioSharingUtils.postOnMainThread(
                            this.mContext,
                            new Runnable() { // from class:
                                             // com.android.settings.connecteddevice.audiosharing.AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    AudioSharingCallAudioPreferenceController.this
                                            .lambda$updateSummary$2(audioSharingDeviceItem);
                                }
                            });
                    return;
                }
            }
        }
        Log.d(TAG, "updatePreference: set empty summary");
        AudioSharingUtils.postOnMainThread(
                this.mContext,
                new AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda1(this, 0));
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference preference = this.mPreference;
        if (preference != null) {
            preference.setVisible(false);
            updateSummary();
            this.mPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.connecteddevice.audiosharing.AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda3
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            boolean lambda$displayPreference$1;
                            lambda$displayPreference$1 =
                                    AudioSharingCallAudioPreferenceController.this
                                            .lambda$displayPreference$1(preference2);
                            return lambda$displayPreference$1;
                        }
                    });
        }
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
        return PREF_KEY;
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

    public void init(Fragment fragment) {
        this.mFragment = fragment;
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

    public void logCallAudioDeviceChange(
            final int i, final CachedBluetoothDevice cachedBluetoothDevice) {
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingCallAudioPreferenceController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        AudioSharingCallAudioPreferenceController.this
                                .lambda$logCallAudioDeviceChange$4(cachedBluetoothDevice, i);
                    }
                });
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (i == 0 && i2 == 29) {
            Log.d(TAG, "updatePreference, LE_AUDIO_BROADCAST_ASSISTANT is disconnected.");
            updateSummary();
        }
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

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.connecteddevice.audiosharing.AudioSharingBasePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onBluetoothStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceAdded(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onActiveDeviceChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
