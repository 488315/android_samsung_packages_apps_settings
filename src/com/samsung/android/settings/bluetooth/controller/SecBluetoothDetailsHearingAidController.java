package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothHearingAid;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.bluetooth.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsHearingAidController extends AbstractPreferenceController
        implements LifecycleObserver, Preference.OnPreferenceChangeListener {
    public SecBluetoothHearingAidVolumePreference dualVolumeController;
    public SecBluetoothHearingAidVolumePreference leftVolumeController;
    public final CachedBluetoothDevice mCachedDevice;
    public final Context mContext;
    public final PreferenceFragmentCompat mFragment;
    public PreferenceCategory mHearingAidContainer;
    public LeAudioProfile mLeAudioProfile;
    public LocalBluetoothProfileManager mProfileManager;
    public HearingAidProfile mProfileProxy;
    public String mScreenId;
    public SecBluetoothHearingAidVolumePreference rightVolumeController;
    public SwitchPreferenceCompat switchAdjust;
    public SwitchPreferenceCompat switchLeft;
    public SwitchPreferenceCompat switchRight;

    public SecBluetoothDetailsHearingAidController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context);
        this.mContext = context;
        this.mFragment = preferenceFragmentCompat;
        this.mCachedDevice = cachedBluetoothDevice;
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        final Context context = this.mContext;
        FutureTask futureTask =
                new FutureTask(
                        new Callable() { // from class:
                                         // com.samsung.android.settings.bluetooth.controller.SecBluetoothDetailsHearingAidController$$ExternalSyntheticLambda0
                            @Override // java.util.concurrent.Callable
                            public final Object call() {
                                return LocalBluetoothManager.getInstance(
                                        context, Utils.mOnInitCallback);
                            }
                        });
        try {
            futureTask.run();
            LocalBluetoothManager localBluetoothManager = (LocalBluetoothManager) futureTask.get();
            if (localBluetoothManager == null) {
                Log.e(
                        "SecBluetoothHearingAidController",
                        "Bluetooth is not supported on this device");
            } else {
                LocalBluetoothProfileManager localBluetoothProfileManager =
                        localBluetoothManager.mProfileManager;
                this.mProfileManager = localBluetoothProfileManager;
                if (localBluetoothProfileManager == null) {
                    Log.e("SecBluetoothHearingAidController", "profileManager is not supported");
                } else {
                    HearingAidProfile hearingAidProfile =
                            localBluetoothProfileManager.mHearingAidProfile;
                    this.mProfileProxy = hearingAidProfile;
                    if (hearingAidProfile == null) {
                        Log.d(
                                "SecBluetoothHearingAidController",
                                "Hearing aid profile is not supported");
                    }
                    LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
                    this.mLeAudioProfile = leAudioProfile;
                    if (leAudioProfile == null) {
                        Log.e(
                                "SecBluetoothHearingAidController",
                                " LeAudio profile is not supported");
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            Log.w("SecBluetoothHearingAidController", "Error getting LocalBluetoothManager.", e);
        }
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        this.mFragment, R.string.screen_device_profiles_setting);
        this.mHearingAidContainer =
                (PreferenceCategory) preferenceScreen.findPreference("bluetooth_hearing_aid");
        this.switchLeft =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference("bluetooth_switch_hearing_aid_left");
        this.switchRight =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference("bluetooth_switch_hearing_aid_right");
        this.switchAdjust =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference("bluetooth_switch_hearing_aid_adjust");
        this.switchLeft.setPersistent();
        this.switchRight.setPersistent();
        this.switchAdjust.setPersistent();
        this.switchLeft.setOnPreferenceChangeListener(this);
        this.switchRight.setOnPreferenceChangeListener(this);
        this.switchAdjust.setOnPreferenceChangeListener(this);
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "bluetooth_hearing_aid_stream_left",
                                1)
                        == 1;
        boolean z2 =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "bluetooth_hearing_aid_stream_right",
                                1)
                        == 1;
        boolean z3 =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "bluetooth_hearing_aid_gain_dual_mode",
                                1)
                        == 1;
        this.switchLeft.setChecked(z);
        this.switchRight.setChecked(z2);
        this.switchAdjust.setChecked(z3);
        this.leftVolumeController =
                (SecBluetoothHearingAidVolumePreference)
                        preferenceScreen.findPreference("bluetooth_volume_left");
        this.rightVolumeController =
                (SecBluetoothHearingAidVolumePreference)
                        preferenceScreen.findPreference("bluetooth_volume_right");
        SecBluetoothHearingAidVolumePreference secBluetoothHearingAidVolumePreference =
                (SecBluetoothHearingAidVolumePreference)
                        preferenceScreen.findPreference("bluetooth_volume_dual");
        this.dualVolumeController = secBluetoothHearingAidVolumePreference;
        SecBluetoothHearingAidVolumePreference secBluetoothHearingAidVolumePreference2 =
                this.leftVolumeController;
        secBluetoothHearingAidVolumePreference2.mVolumeKey = 0;
        secBluetoothHearingAidVolumePreference2.mCallBack = this;
        SecBluetoothHearingAidVolumePreference secBluetoothHearingAidVolumePreference3 =
                this.rightVolumeController;
        secBluetoothHearingAidVolumePreference3.mVolumeKey = 1;
        secBluetoothHearingAidVolumePreference3.mCallBack = this;
        secBluetoothHearingAidVolumePreference.mVolumeKey = 2;
        secBluetoothHearingAidVolumePreference.mCallBack = this;
        secBluetoothHearingAidVolumePreference.setVisible(!this.switchAdjust.isChecked());
        this.leftVolumeController.setVisible(this.switchAdjust.isChecked());
        this.rightVolumeController.setVisible(this.switchAdjust.isChecked());
        super.displayPreference(preferenceScreen);
    }

    public final int getGain(int i) {
        if (i == 0) {
            return Settings.Secure.getInt(
                    this.mContext.getContentResolver(), "bluetooth_hearing_aid_gain_left", 100);
        }
        if (i == 1) {
            return Settings.Secure.getInt(
                    this.mContext.getContentResolver(), "bluetooth_hearing_aid_gain_right", 100);
        }
        if (i != 2) {
            return 0;
        }
        return (Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "bluetooth_hearing_aid_gain_right",
                                100)
                        + Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "bluetooth_hearing_aid_gain_left",
                                100))
                / 2;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_hearing_aid";
    }

    public final boolean isAnyLeDeviceConnected(LocalBluetoothProfile localBluetoothProfile) {
        CachedBluetoothDevice cachedBluetoothDevice;
        if (localBluetoothProfile != null
                && (cachedBluetoothDevice = this.mCachedDevice) != null
                && (localBluetoothProfile instanceof LeAudioProfile)) {
            if (cachedBluetoothDevice.isConnectedProfile(localBluetoothProfile)) {
                return true;
            }
            Iterator it = cachedBluetoothDevice.mMemberDevices.iterator();
            while (it.hasNext()) {
                if (((CachedBluetoothDevice) it.next()).isConnectedProfile(localBluetoothProfile)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mCachedDevice.isHearingAidDevice();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String str;
        BluetoothHearingAid bluetoothHearingAid;
        BluetoothHearingAid bluetoothHearingAid2;
        String key = preference.getKey();
        if (preference instanceof SwitchPreferenceCompat) {
            SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) preference;
            boolean isChecked = switchPreferenceCompat.isChecked();
            boolean z = !isChecked;
            Log.d(
                    "SecBluetoothHearingAidController",
                    "onPreferenceChange :: key = " + key + ", isChecked = " + isChecked);
            switchPreferenceCompat.setChecked(z);
            boolean equals = "bluetooth_switch_hearing_aid_left".equals(key);
            PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
            if (equals) {
                str =
                        SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat,
                                R.string.event_device_profiles_setting_hearing_left);
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_hearing_aid_stream_left",
                        z ? 1 : 0);
                HearingAidProfile hearingAidProfile = this.mProfileProxy;
                if (hearingAidProfile != null
                        && (bluetoothHearingAid2 = hearingAidProfile.mService) != null) {
                    bluetoothHearingAid2.setStreamEnable(0, z);
                }
            } else if ("bluetooth_switch_hearing_aid_right".equals(key)) {
                str =
                        SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat,
                                R.string.event_device_profiles_setting_hearing_right);
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_hearing_aid_stream_right",
                        z ? 1 : 0);
                HearingAidProfile hearingAidProfile2 = this.mProfileProxy;
                if (hearingAidProfile2 != null
                        && (bluetoothHearingAid = hearingAidProfile2.mService) != null) {
                    bluetoothHearingAid.setStreamEnable(1, z);
                }
            } else if ("bluetooth_switch_hearing_aid_adjust".equals(key)) {
                str =
                        SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat,
                                R.string.event_device_profiles_setting_hearing_adjust);
                Settings.Secure.putInt(
                        this.mContext.getContentResolver(),
                        "bluetooth_hearing_aid_gain_dual_mode",
                        z ? 1 : 0);
                this.dualVolumeController.setVisible(isChecked);
                this.leftVolumeController.setVisible(z);
                this.rightVolumeController.setVisible(z);
            } else {
                str = ApnSettings.MVNO_NONE;
            }
            SALogging.insertSALog(this.mScreenId, str, String.valueOf(z ? 1 : 0));
        }
        return false;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        if (isAnyLeDeviceConnected(this.mLeAudioProfile)) {
            this.mHearingAidContainer.setVisible(false);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {}

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {}
}
