package com.android.settings.connecteddevice.audiosharing;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothLeBroadcastReceiveState;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.bluetooth.BluetoothDeviceUpdater;
import com.android.settings.bluetooth.Utils;
import com.android.settings.connecteddevice.DevicePreferenceCallback;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingDevicePreferenceController extends BasePreferenceController
        implements DefaultLifecycleObserver,
                DevicePreferenceCallback,
                BluetoothCallback,
                LocalBluetoothProfileManager.ServiceListener {
    private static final boolean DEBUG = true;
    private static final String KEY = "audio_sharing_device_list";
    private static final String KEY_AUDIO_SHARING_SETTINGS =
            "connected_device_audio_sharing_settings";
    private static final String TAG = "AudioSharingDevicePrefController";
    private final LocalBluetoothLeBroadcastAssistant mAssistant;
    private Preference mAudioSharingSettingsPreference;
    private BluetoothDeviceUpdater mBluetoothDeviceUpdater;
    BluetoothLeBroadcastAssistant.Callback mBroadcastAssistantCallback;
    private final LocalBluetoothManager mBtManager;
    private final CachedBluetoothDeviceManager mDeviceManager;
    private AudioSharingDialogHandler mDialogHandler;
    private final BluetoothEventManager mEventManager;
    private final Executor mExecutor;
    private DashboardFragment mFragment;
    private AtomicBoolean mIntentHandled;
    private PreferenceGroup mPreferenceGroup;
    private final LocalBluetoothProfileManager mProfileManager;

    public AudioSharingDevicePreferenceController(Context context) {
        super(context, KEY);
        this.mIntentHandled = new AtomicBoolean(false);
        this.mBroadcastAssistantCallback =
                new BluetoothLeBroadcastAssistant
                        .Callback() { // from class:
                                      // com.android.settings.connecteddevice.audiosharing.AudioSharingDevicePreferenceController.1
                    public final void onReceiveStateChanged(
                            BluetoothDevice bluetoothDevice,
                            int i,
                            BluetoothLeBroadcastReceiveState bluetoothLeBroadcastReceiveState) {
                        CachedBluetoothDevice findDevice;
                        if (BluetoothUtils.isConnected(bluetoothLeBroadcastReceiveState)) {
                            Log.d(
                                    AudioSharingDevicePreferenceController.TAG,
                                    "onSourceAdded: update sharing device list.");
                            if (AudioSharingDevicePreferenceController.this.mBluetoothDeviceUpdater
                                    != null) {
                                AudioSharingDevicePreferenceController.this.mBluetoothDeviceUpdater
                                        .forceUpdate();
                            }
                            if (AudioSharingDevicePreferenceController.this.mDeviceManager == null
                                    || AudioSharingDevicePreferenceController.this.mDialogHandler
                                            == null
                                    || (findDevice =
                                                    AudioSharingDevicePreferenceController.this
                                                            .mDeviceManager.findDevice(
                                                            bluetoothDevice))
                                            == null) {
                                return;
                            }
                            AudioSharingDevicePreferenceController.this.mDialogHandler
                                    .closeOpeningDialogsForLeaDevice(findDevice);
                        }
                    }

                    public final void onSourceAddFailed(
                            BluetoothDevice bluetoothDevice,
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata,
                            int i) {
                        Context context2 =
                                ((AbstractPreferenceController)
                                                AudioSharingDevicePreferenceController.this)
                                        .mContext;
                        Locale locale = Locale.US;
                        AudioSharingUtils.toastMessage(
                                context2,
                                "Fail to add source to "
                                        + bluetoothDevice.getAddress()
                                        + " reason "
                                        + i);
                    }

                    public final void onSourceRemoveFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        Context context2 =
                                ((AbstractPreferenceController)
                                                AudioSharingDevicePreferenceController.this)
                                        .mContext;
                        Locale locale = Locale.US;
                        AudioSharingUtils.toastMessage(
                                context2,
                                "Fail to remove source from "
                                        + bluetoothDevice.getAddress()
                                        + " reason "
                                        + i2);
                    }

                    public final void onSourceRemoved(
                            BluetoothDevice bluetoothDevice, int i, int i2) {
                        Log.d(
                                AudioSharingDevicePreferenceController.TAG,
                                "onSourceRemoved: update sharing device list.");
                        if (AudioSharingDevicePreferenceController.this.mBluetoothDeviceUpdater
                                != null) {
                            AudioSharingDevicePreferenceController.this.mBluetoothDeviceUpdater
                                    .forceUpdate();
                        }
                    }

                    public final void onSearchStartFailed(int i) {}

                    public final void onSearchStarted(int i) {}

                    public final void onSearchStopFailed(int i) {}

                    public final void onSearchStopped(int i) {}

                    public final void onSourceFound(
                            BluetoothLeBroadcastMetadata bluetoothLeBroadcastMetadata) {}

                    public final void onSourceAdded(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModified(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}

                    public final void onSourceModifyFailed(
                            BluetoothDevice bluetoothDevice, int i, int i2) {}
                };
        LocalBluetoothManager localBluetoothManager =
                LocalBluetoothManager.getInstance(this.mContext, Utils.mOnInitCallback);
        this.mBtManager = localBluetoothManager;
        this.mEventManager =
                localBluetoothManager == null ? null : localBluetoothManager.mEventManager;
        this.mDeviceManager =
                localBluetoothManager == null ? null : localBluetoothManager.mCachedDeviceManager;
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager == null ? null : localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mAssistant =
                localBluetoothProfileManager != null
                        ? localBluetoothProfileManager.mLeAudioBroadcastAssistant
                        : null;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    private void handleDeviceClickFromIntent() {
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager;
        DashboardFragment dashboardFragment = this.mFragment;
        if (dashboardFragment == null
                || dashboardFragment.getActivity() == null
                || this.mFragment.getActivity().getIntent() == null) {
            Log.d(TAG, "Skip handleDeviceClickFromIntent, fragment intent is null");
            return;
        }
        Bundle bundleExtra =
                this.mFragment
                        .getActivity()
                        .getIntent()
                        .getBundleExtra(":settings:show_fragment_args");
        CachedBluetoothDevice cachedBluetoothDevice = null;
        BluetoothDevice bluetoothDevice =
                bundleExtra == null
                        ? null
                        : (BluetoothDevice)
                                bundleExtra.getParcelable(
                                        "BLUETOOTH_DEVICE", BluetoothDevice.class);
        if (bluetoothDevice != null
                && (cachedBluetoothDeviceManager = this.mDeviceManager) != null) {
            cachedBluetoothDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
        }
        if (cachedBluetoothDevice == null) {
            Log.d(TAG, "Skip handleDeviceClickFromIntent, device is null");
            return;
        }
        if (bluetoothDevice != null && !bluetoothDevice.isConnected()) {
            Log.d(TAG, "handleDeviceClickFromIntent: connect");
            cachedBluetoothDevice.connect$1();
        } else if (this.mDialogHandler != null) {
            Log.d(TAG, "handleDeviceClickFromIntent: trigger dialog handler");
            this.mDialogHandler.handleDeviceConnected(cachedBluetoothDevice, true);
        }
    }

    private void handleOnProfileStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        boolean anyMatch =
                cachedBluetoothDevice.getProfiles().stream()
                        .anyMatch(
                                new AudioSharingUtils$$ExternalSyntheticLambda1(
                                        cachedBluetoothDevice));
        if (anyMatch && i != 29) {
            Log.d(
                    TAG,
                    "Ignore onProfileConnectionStateChanged, not the le assistant profile for le"
                        + " audio device");
            return;
        }
        boolean isFirstConnectedProfile = isFirstConnectedProfile(cachedBluetoothDevice, i);
        if (!anyMatch && !isFirstConnectedProfile) {
            Log.d(
                    TAG,
                    "Ignore onProfileConnectionStateChanged, not the first connected profile for"
                        + " non le audio device");
            return;
        }
        Log.d(
                TAG,
                "Start handling onProfileConnectionStateChanged for "
                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
        AudioSharingDialogHandler audioSharingDialogHandler = this.mDialogHandler;
        if (audioSharingDialogHandler != null) {
            audioSharingDialogHandler.handleDeviceConnected(cachedBluetoothDevice, false);
        }
    }

    private boolean isFirstConnectedProfile(
            final CachedBluetoothDevice cachedBluetoothDevice, final int i) {
        return cachedBluetoothDevice.getProfiles().stream()
                .noneMatch(
                        new Predicate() { // from class:
                                          // com.android.settings.connecteddevice.audiosharing.AudioSharingDevicePreferenceController$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean lambda$isFirstConnectedProfile$1;
                                lambda$isFirstConnectedProfile$1 =
                                        AudioSharingDevicePreferenceController
                                                .lambda$isFirstConnectedProfile$1(
                                                        i,
                                                        cachedBluetoothDevice,
                                                        (LocalBluetoothProfile) obj);
                                return lambda$isFirstConnectedProfile$1;
                            }
                        });
    }

    private boolean isMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        return cachedBluetoothDevice.getConnectableProfiles().stream()
                .anyMatch(new AudioSharingDevicePreferenceController$$ExternalSyntheticLambda0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean lambda$isFirstConnectedProfile$1(
            int i,
            CachedBluetoothDevice cachedBluetoothDevice,
            LocalBluetoothProfile localBluetoothProfile) {
        return localBluetoothProfile.getProfileId() != i
                && localBluetoothProfile.getConnectionStatus(cachedBluetoothDevice.mDevice) == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$isMediaDevice$0(
            LocalBluetoothProfile localBluetoothProfile) {
        return (localBluetoothProfile instanceof A2dpProfile)
                || (localBluetoothProfile instanceof HearingAidProfile)
                || (localBluetoothProfile instanceof LeAudioProfile)
                || (localBluetoothProfile instanceof HeadsetProfile);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        PreferenceGroup preferenceGroup = (PreferenceGroup) preferenceScreen.findPreference(KEY);
        this.mPreferenceGroup = preferenceGroup;
        if (preferenceGroup != null) {
            this.mAudioSharingSettingsPreference =
                    preferenceGroup.findPreference(KEY_AUDIO_SHARING_SETTINGS);
            this.mPreferenceGroup.setVisible(false);
        }
        Preference preference = this.mAudioSharingSettingsPreference;
        if (preference != null) {
            preference.setVisible(false);
        }
        if (isAvailable()) {
            BluetoothDeviceUpdater bluetoothDeviceUpdater = this.mBluetoothDeviceUpdater;
            if (bluetoothDeviceUpdater != null) {
                bluetoothDeviceUpdater.mPrefContext = preferenceScreen.getContext();
                this.mBluetoothDeviceUpdater.forceUpdate();
            }
            if (!AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)
                    || this.mIntentHandled.get()) {
                return;
            }
            Log.d(TAG, "displayPreference: profile ready, handleDeviceClickFromIntent");
            handleDeviceClickFromIntent();
            this.mIntentHandled.set(true);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        BluetoothAdapter.getDefaultAdapter();
        return 3;
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
        this.mFragment = dashboardFragment;
        Context context = dashboardFragment.getContext();
        AudioSharingBluetoothDeviceUpdater audioSharingBluetoothDeviceUpdater =
                new AudioSharingBluetoothDeviceUpdater(
                        context, this, dashboardFragment.getMetricsCategory());
        Utils.getLocalBluetoothManager(context);
        this.mBluetoothDeviceUpdater = audioSharingBluetoothDeviceUpdater;
        this.mDialogHandler = new AudioSharingDialogHandler(this.mContext, dashboardFragment);
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
    public void onDeviceRemoved(Preference preference) {
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        if (preferenceGroup != null) {
            preferenceGroup.removePreference(preference);
            if (this.mPreferenceGroup.getPreferenceCount() == 1) {
                this.mPreferenceGroup.setVisible(false);
                Preference preference2 = this.mAudioSharingSettingsPreference;
                if (preference2 != null) {
                    preference2.setVisible(false);
                }
            }
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onPause(LifecycleOwner lifecycleOwner) {
        super.onPause(lifecycleOwner);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (this.mDialogHandler == null || this.mAssistant == null || this.mFragment == null) {
            Log.d(TAG, "Ignore onProfileConnectionStateChanged, not init correctly");
            return;
        }
        if (!isMediaDevice(cachedBluetoothDevice)) {
            Log.d(TAG, "Ignore onProfileConnectionStateChanged, not a media device");
            return;
        }
        if (i == 0) {
            boolean anyMatch =
                    cachedBluetoothDevice.getProfiles().stream()
                            .anyMatch(
                                    new AudioSharingUtils$$ExternalSyntheticLambda1(
                                            cachedBluetoothDevice));
            if (anyMatch && i2 == 29) {
                this.mDialogHandler.closeOpeningDialogsForLeaDevice(cachedBluetoothDevice);
                return;
            }
            if (!anyMatch && !cachedBluetoothDevice.isConnected()) {
                Fragment fragment = this.mDialogHandler.mHostFragment;
                if (fragment == null) {
                    return;
                }
                cachedBluetoothDevice.mDevice.getAddress();
                Iterator it =
                        fragment.getChildFragmentManager().mFragmentStore.getFragments().iterator();
                while (it.hasNext()) {
                    AudioSharingDialogHandler.getCachedBluetoothDeviceFromDialog(
                            (Fragment) it.next());
                }
                return;
            }
        }
        if (i == 2 && cachedBluetoothDevice.mDevice.isConnected()) {
            handleOnProfileStateChanged(cachedBluetoothDevice, i2);
        } else {
            Log.d(TAG, "Ignore onProfileConnectionStateChanged, not connected state");
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onResume(LifecycleOwner lifecycleOwner) {
        super.onResume(lifecycleOwner);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        if (AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)) {
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.removeServiceListener(this);
            }
            if (this.mIntentHandled.get()) {
                return;
            }
            Log.d(TAG, "onServiceConnected: handleDeviceClickFromIntent");
            handleDeviceClickFromIntent();
            this.mIntentHandled.set(true);
        }
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStart(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip onStart(), feature is not supported.");
            return;
        }
        if (!AudioSharingUtils.isAudioSharingProfileReady(this.mProfileManager)
                && this.mProfileManager != null) {
            Log.d(TAG, "Register profile service listener");
            this.mProfileManager.addServiceListener(this);
        }
        if (this.mEventManager == null
                || this.mAssistant == null
                || this.mDialogHandler == null
                || this.mBluetoothDeviceUpdater == null) {
            Log.d(TAG, "Skip onStart(), profile is not ready.");
            return;
        }
        Log.d(TAG, "onStart() Register callbacks.");
        this.mEventManager.registerCallback(this);
        this.mAssistant.registerServiceCallBack(this.mExecutor, this.mBroadcastAssistantCallback);
        this.mDialogHandler.registerCallbacks(this.mExecutor);
        this.mBluetoothDeviceUpdater.registerCallback();
        this.mBluetoothDeviceUpdater.getClass();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onStop(LifecycleOwner lifecycleOwner) {
        if (!isAvailable()) {
            Log.d(TAG, "Skip onStop(), feature is not supported.");
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            localBluetoothProfileManager.removeServiceListener(this);
        }
        if (this.mEventManager == null
                || this.mAssistant == null
                || this.mDialogHandler == null
                || this.mBluetoothDeviceUpdater == null) {
            Log.d(TAG, "Skip onStop(), profile is not ready.");
            return;
        }
        Log.d(TAG, "onStop() Unregister callbacks.");
        this.mEventManager.unregisterCallback(this);
        this.mAssistant.unregisterServiceCallBack(this.mBroadcastAssistantCallback);
        this.mDialogHandler.unregisterCallbacks();
        this.mBluetoothDeviceUpdater.unregisterCallback();
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

    public void setHostFragment(DashboardFragment dashboardFragment) {
        this.mFragment = dashboardFragment;
    }

    public void setIntentHandled(boolean z) {
        this.mIntentHandled.set(z);
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
            if (preferenceGroup.getPreferenceCount() == 1) {
                this.mPreferenceGroup.setVisible(true);
                Preference preference2 = this.mAudioSharingSettingsPreference;
                if (preference2 != null) {
                    preference2.setVisible(true);
                }
            }
            this.mPreferenceGroup.addPreference(preference);
        }
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onBluetoothStateChanged(int i) {}

    @Override // com.android.settings.connecteddevice.DevicePreferenceCallback
    public /* bridge */ /* synthetic */ void onDeviceClick(Preference preference) {}

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
