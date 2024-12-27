package com.android.settings.connecteddevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcast;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.HearingAidUtils;
import com.android.settings.bluetooth.AvailableMediaBluetoothDeviceUpdater;
import com.android.settings.bluetooth.BluetoothDevicePreference;
import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.audiosharing.AudioSharingDialogHandler;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcast;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AvailableMediaDeviceGroupController extends BasePreferenceController
        implements DefaultLifecycleObserver, DevicePreferenceCallback, BluetoothCallback {
    private static final String KEY = "available_device_list";
    private static final String TAG = "AvailableMediaDeviceGroupController";
    private LocalBluetoothLeBroadcastAssistant mAssistant;
    private BluetoothLeBroadcastAssistant.Callback mAssistantCallback;
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    private LocalBluetoothLeBroadcast mBroadcast;
    private BluetoothLeBroadcast.Callback mBroadcastCallback;
    LocalBluetoothManager mBtManager;
    private AudioSharingDialogHandler mDialogHandler;
    private final Executor mExecutor;
    private FragmentManager mFragmentManager;
    PreferenceGroup mPreferenceGroup;

    public AvailableMediaDeviceGroupController(Context context) {
        super(context, KEY);
        this.mBroadcastCallback =
                new BluetoothLeBroadcast
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.AvailableMediaDeviceGroupController.1
                    public final void onBroadcastStarted(int i, int i2) {
                        Log.d(
                                AvailableMediaDeviceGroupController.TAG,
                                "onBroadcastStarted: update title.");
                        AvailableMediaDeviceGroupController.this.updateTitle();
                    }

                    public final void onBroadcastStopped(int i, int i2) {
                        Log.d(
                                AvailableMediaDeviceGroupController.TAG,
                                "onBroadcastStopped: update title.");
                        AvailableMediaDeviceGroupController.this.updateTitle();
                    }

                    public final void onBroadcastStartFailed(int i) {}

                    public final void onBroadcastStopFailed(int i) {}

                    public final void onBroadcastMetadataChanged(
                            int i, BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onBroadcastUpdateFailed(int i, int i2) {}

                    public final void onBroadcastUpdated(int i, int i2) {}

                    public final void onPlaybackStarted(int i, int i2) {}

                    public final void onPlaybackStopped(int i, int i2) {}
                };
        this.mAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.AvailableMediaDeviceGroupController.2
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        if (BluetoothUtils.isConnected(bluetoothLeBroadcastReceiveState)) {
                            Log.d(
                                    AvailableMediaDeviceGroupController.TAG,
                                    "onReceiveStateChanged: synced, update media device list.");
                            if (AvailableMediaDeviceGroupController.this.mBluetoothDeviceUpdater
                                    != null) {
                                AvailableMediaDeviceGroupController.this.mBluetoothDeviceUpdater
                                        .forceUpdate();
                            }
                        }
                    }

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        Log.d(
                                AvailableMediaDeviceGroupController.TAG,
                                "onSourceRemoved: update media device list.");
                        if (AvailableMediaDeviceGroupController.this.mBluetoothDeviceUpdater
                                != null) {
                            AvailableMediaDeviceGroupController.this.mBluetoothDeviceUpdater
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
        this.mBtManager = LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mExecutor = Executors.newSingleThreadExecutor();
        BluetoothAdapter.getDefaultAdapter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTitle$0(int i) {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.setTitle(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$updateTitle$1() {
        final int i;
        if (com.android.settingslib.Utils.isAudioModeOngoingCall(this.mContext)) {
            i = R.string.connected_device_call_device_title;
        } else {
            BluetoothAdapter.getDefaultAdapter();
            i = R.string.connected_device_media_device_title;
        }
        this.mContext
                .getMainExecutor()
                .execute(
                        new Runnable() { // from class:
                                         // com.android.settings.connecteddevice.AvailableMediaDeviceGroupController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                AvailableMediaDeviceGroupController.this.lambda$updateTitle$0(i);
                            }
                        });
    }

    private void registerAudioSharingCallbacks() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.registerServiceCallBack(
                    this.mExecutor, this.mBroadcastCallback);
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = this.mAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            localBluetoothLeBroadcastAssistant.registerServiceCallBack(
                    this.mExecutor, this.mAssistantCallback);
        }
        AudioSharingDialogHandler audioSharingDialogHandler = this.mDialogHandler;
        if (audioSharingDialogHandler != null) {
            audioSharingDialogHandler.registerCallbacks(this.mExecutor);
        }
    }

    private void unregisterAudioSharingCallbacks() {
        LocalBluetoothLeBroadcast localBluetoothLeBroadcast = this.mBroadcast;
        if (localBluetoothLeBroadcast != null) {
            localBluetoothLeBroadcast.unregisterServiceCallBack(this.mBroadcastCallback);
        }
        LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant = this.mAssistant;
        if (localBluetoothLeBroadcastAssistant != null) {
            localBluetoothLeBroadcastAssistant.unregisterServiceCallBack(this.mAssistantCallback);
        }
        AudioSharingDialogHandler audioSharingDialogHandler = this.mDialogHandler;
        if (audioSharingDialogHandler != null) {
            audioSharingDialogHandler.unregisterCallbacks();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTitle() {
        if (this.mPreferenceGroup == null) {
            return;
        }
        ThreadUtils.postOnBackgroundThread(
                new Runnable() { // from class:
                                 // com.android.settings.connecteddevice.AvailableMediaDeviceGroupController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AvailableMediaDeviceGroupController.this.lambda$updateTitle$1();
                    }
                });
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
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

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth")
                ? 1
                : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY;
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

    public void init(DashboardFragment dashboardFragment) {
        this.mFragmentManager = dashboardFragment.getParentFragmentManager();
        this.mBluetoothDeviceUpdater =
                new AvailableMediaBluetoothDeviceUpdater(
                        dashboardFragment.getContext(),
                        this,
                        dashboardFragment.getMetricsCategory());
        BluetoothAdapter.getDefaultAdapter();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (cachedBluetoothDevice != null && i == 21) {
            HearingAidUtils.launchHearingAidPairingDialog(
                    this.mFragmentManager, cachedBluetoothDevice, getMetricsCategory());
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onAudioModeChanged() {
        updateTitle();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceAdded(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public void onDeviceClick(Preference preference) {
        CachedBluetoothDevice cachedBluetoothDevice =
                ((BluetoothDevicePreference) preference).mCachedDevice;
        BluetoothAdapter.getDefaultAdapter();
        cachedBluetoothDevice.setActive();
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
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (isAvailable()) {
            updateTitle();
        }
        if (this.mBtManager == null) {
            Log.d(TAG, "onStart() Bluetooth is not supported on this device");
            return;
        }
        BluetoothAdapter.getDefaultAdapter();
        this.mBtManager.mEventManager.registerCallback(this);
        BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
        if (bluetoothDeviceUpdater != null) {
            bluetoothDeviceUpdater.registerCallback();
            this.mBluetoothDeviceUpdater.getClass();
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (this.mBtManager == null) {
            Log.d(TAG, "onStop() Bluetooth is not supported on this device");
            return;
        }
        BluetoothAdapter.getDefaultAdapter();
        BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
        if (bluetoothDeviceUpdater != null) {
            bluetoothDeviceUpdater.unregisterCallback();
        }
        this.mBtManager.mEventManager.unregisterCallback(this);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setBluetoothDeviceUpdater(BluetoothDeviceUpdater bluetoothDeviceUpdater) {
        this.mBluetoothDeviceUpdater = bluetoothDeviceUpdater;
    }

    public void setDialogHandler(AudioSharingDialogHandler audioSharingDialogHandler) {
        this.mDialogHandler = audioSharingDialogHandler;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
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
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onBluetoothStateChanged(int i) {}

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

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}
