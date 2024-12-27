package com.android.settings.accessibility;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccessibilityHearingAidPreferenceController extends BasePreferenceController
        implements LifecycleObserver,
                OnStart,
                OnStop,
                BluetoothCallback,
                LocalBluetoothProfileManager.ServiceListener {
    private static final String TAG = "AccessibilityHearingAidPreferenceController";
    private FragmentManager mFragmentManager;
    private final BroadcastReceiver mHearingAidChangedReceiver;
    private Preference mHearingAidPreference;
    protected final HearingAidHelper mHelper;
    private final LocalBluetoothManager mLocalBluetoothManager;
    private final LocalBluetoothProfileManager mProfileManager;

    public AccessibilityHearingAidPreferenceController(Context context, String str) {
        super(context, str);
        this.mHearingAidChangedReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.accessibility.AccessibilityHearingAidPreferenceController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        AccessibilityHearingAidPreferenceController
                                accessibilityHearingAidPreferenceController =
                                        AccessibilityHearingAidPreferenceController.this;
                        accessibilityHearingAidPreferenceController.updateState(
                                accessibilityHearingAidPreferenceController.mHearingAidPreference);
                    }
                };
        LocalBluetoothManager localBluetoothManager = Utils.getLocalBluetoothManager(context);
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mHelper = new HearingAidHelper(context);
    }

    private int getConnectedHearingAidDeviceNum() {
        return this.mHelper.getConnectedHearingAidDeviceList().size();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$refreshSummary$1(Preference preference) {
        ThreadUtils.getUiThreadHandler()
                .post(
                        new AccessibilityHearingAidPreferenceController$$ExternalSyntheticLambda0(
                                preference, loadSummary()));
    }

    private void launchHearingAidPage() {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = AccessibilityHearingAidsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.launch();
    }

    private CharSequence loadSummary() {
        CachedBluetoothDevice connectedHearingAidDevice =
                this.mHelper.getConnectedHearingAidDevice();
        return connectedHearingAidDevice == null
                ? this.mContext.getString(R.string.bluetooth_hearing_aid_summary)
                : connectedHearingAidDevice.mDevice.semGetAliasName();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mHearingAidPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mHelper.isHearingAidSupported() ? 0 : 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        launchHearingAidPage();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
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

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceConnected() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mHelper.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        if (hearingAidProfile == null || hearingAidProfile.mIsProfileReady) {
            HapClientProfile hapClientProfile = localBluetoothProfileManager.mHapClientProfile;
            if (hapClientProfile == null || hapClientProfile.mIsProfileReady) {
                updateState(this.mHearingAidPreference);
                this.mProfileManager.removeServiceListener(this);
            }
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        HapClientProfile hapClientProfile;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(
                "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.HAP_CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
        intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        this.mContext.registerReceiver(this.mHearingAidChangedReceiver, intentFilter);
        this.mLocalBluetoothManager.mEventManager.registerCallback(this);
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mHelper.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        if ((hearingAidProfile == null || hearingAidProfile.mIsProfileReady)
                && ((hapClientProfile = localBluetoothProfileManager.mHapClientProfile) == null
                        || hapClientProfile.mIsProfileReady)) {
            return;
        }
        this.mProfileManager.addServiceListener(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.unregisterReceiver(this.mHearingAidChangedReceiver);
        this.mLocalBluetoothManager.mEventManager.unregisterCallback(this);
        this.mProfileManager.removeServiceListener(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        if (preference == null) {
            return;
        }
        ThreadUtils.postOnBackgroundThread(
                new AccessibilityHearingAidPreferenceController$$ExternalSyntheticLambda0(
                        this, preference));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public void setPreference(Preference preference) {
        this.mHearingAidPreference = preference;
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
    public /* bridge */ /* synthetic */ void onAudioModeChanged() {}

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public void onServiceDisconnected() {}

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
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onProfileConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {}
}
