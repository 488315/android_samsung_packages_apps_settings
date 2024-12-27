package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.sysprop.BluetoothProperties;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.TwoStatePreference;

import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.MapProfile;
import com.android.settingslib.bluetooth.PbapServerProfile;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsProfilesController extends BluetoothDetailsController
        implements Preference.OnPreferenceClickListener,
                LocalBluetoothProfileManager.ServiceListener {
    static final String HIGH_QUALITY_AUDIO_PREF_TAG = "A2dpProfileHighQualityAudio";
    public List mAllOfCachedDevices;
    public final CachedBluetoothDevice mCachedDevice;
    public final AtomicReference mInvisiblePreferenceKey;
    public boolean mIsLeAudioToggleEnabled;
    public boolean mIsLeContactSharingEnabled;
    public final LocalBluetoothManager mManager;
    public final Map mProfileDeviceMap;
    public final LocalBluetoothProfileManager mProfileManager;
    PreferenceCategory mProfilesContainer;

    public BluetoothDetailsProfilesController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        this.mInvisiblePreferenceKey = new AtomicReference();
        this.mProfileDeviceMap = new HashMap();
        this.mIsLeContactSharingEnabled = false;
        this.mIsLeAudioToggleEnabled = false;
        this.mManager = localBluetoothManager;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mCachedDevice = cachedBluetoothDevice;
        this.mAllOfCachedDevices =
                Utils.getAllOfCachedBluetoothDevices(cachedBluetoothDevice, localBluetoothManager);
        lifecycle.addObserver(this);
    }

    public final void disableProfileBeforeUserEnablesLeAudio(
            LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile != null) {
            if (((HashMap) this.mProfileDeviceMap).get(localBluetoothProfile.toString()) != null) {
                Log.d(
                        "BtDetailsProfilesCtrl",
                        "Disable " + localBluetoothProfile.toString() + " before user enables LE");
                for (CachedBluetoothDevice cachedBluetoothDevice :
                        (List)
                                ((HashMap) this.mProfileDeviceMap)
                                        .get(localBluetoothProfile.toString())) {
                    if (localBluetoothProfile.isEnabled(cachedBluetoothDevice.mDevice)) {
                        Log.d(
                                "BtDetailsProfilesCtrl",
                                "The "
                                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                                        + ":"
                                        + localBluetoothProfile.toString()
                                        + " set disable");
                        localBluetoothProfile.setEnabled(cachedBluetoothDevice.mDevice, false);
                    } else {
                        Log.d(
                                "BtDetailsProfilesCtrl",
                                "The "
                                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                                        + ":"
                                        + localBluetoothProfile.toString()
                                        + " profile is disabled. Do nothing.");
                    }
                }
                return;
            }
        }
        if (localBluetoothProfile == null) {
            Log.w("BtDetailsProfilesCtrl", "profile is null");
            return;
        }
        Log.w(
                "BtDetailsProfilesCtrl",
                localBluetoothProfile.toString() + " is not in " + this.mProfileDeviceMap);
    }

    public final void enableProfileAfterUserDisablesLeAudio(
            LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile != null) {
            if (((HashMap) this.mProfileDeviceMap).get(localBluetoothProfile.toString()) != null) {
                Log.d(
                        "BtDetailsProfilesCtrl",
                        "enable " + localBluetoothProfile.toString() + "after user disables LE");
                for (CachedBluetoothDevice cachedBluetoothDevice :
                        (List)
                                ((HashMap) this.mProfileDeviceMap)
                                        .get(localBluetoothProfile.toString())) {
                    if (localBluetoothProfile.isEnabled(cachedBluetoothDevice.mDevice)) {
                        Log.d(
                                "BtDetailsProfilesCtrl",
                                "The "
                                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                                        + ":"
                                        + localBluetoothProfile.toString()
                                        + " profile is enabled. Do nothing.");
                    } else {
                        Log.d(
                                "BtDetailsProfilesCtrl",
                                "The "
                                        + cachedBluetoothDevice.mDevice.getAnonymizedAddress()
                                        + ":"
                                        + localBluetoothProfile.toString()
                                        + " set enable");
                        localBluetoothProfile.setEnabled(cachedBluetoothDevice.mDevice, true);
                    }
                }
                return;
            }
        }
        if (localBluetoothProfile == null) {
            Log.w("BtDetailsProfilesCtrl", "profile is null");
            return;
        }
        Log.w(
                "BtDetailsProfilesCtrl",
                localBluetoothProfile.toString() + " is not in " + this.mProfileDeviceMap);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_profiles";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("bluetooth_profiles");
        this.mProfilesContainer = preferenceCategory;
        preferenceCategory.setLayoutResource(R.layout.preference_bluetooth_profile_category);
        refresh();
    }

    public boolean isModelNameInAllowList(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return BluetoothProperties.le_audio_allow_list().contains(str);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        Iterator it = ((ArrayList) this.mAllOfCachedDevices).iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).unregisterCallback(this);
        }
        List allOfCachedBluetoothDevices =
                Utils.getAllOfCachedBluetoothDevices(this.mCachedDevice, this.mManager);
        this.mAllOfCachedDevices = allOfCachedBluetoothDevices;
        Iterator it2 = ((ArrayList) allOfCachedBluetoothDevices).iterator();
        while (it2.hasNext()) {
            ((CachedBluetoothDevice) it2.next()).registerCallback(this);
        }
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        Iterator it = this.mAllOfCachedDevices.iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).unregisterCallback(this);
        }
        this.mProfileManager.removeServiceListener(this);
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        LocalBluetoothProfile profileByName = localBluetoothProfileManager.getProfileByName(key);
        if (profileByName == null) {
            profileByName = this.mManager.mProfileManager.mPbapProfile;
            String key2 = preference.getKey();
            profileByName.getClass();
            if (!TextUtils.equals(key2, PbapServerProfile.NAME)) {
                return false;
            }
        }
        TwoStatePreference twoStatePreference = (TwoStatePreference) preference;
        boolean isChecked = twoStatePreference.isChecked();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (isChecked) {
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            if (profileByName instanceof PbapServerProfile) {
                bluetoothDevice.setPhonebookAccessPermission(1);
            } else {
                if (profileByName instanceof MapProfile) {
                    bluetoothDevice.setMessageAccessPermission(1);
                }
                if (!(profileByName instanceof LeAudioProfile)) {
                    profileByName.setEnabled(bluetoothDevice, true);
                } else if (((HashMap) this.mProfileDeviceMap).get("LE_AUDIO") == null) {
                    Log.e(
                            "BtDetailsProfilesCtrl",
                            "There is no the LE profile or no device in mProfileDeviceMap. Do"
                                + " nothing.");
                } else {
                    if (!SystemProperties.getBoolean(
                            "persist.bluetooth.enable_dual_mode_audio", false)) {
                        Log.i(
                                "BtDetailsProfilesCtrl",
                                "Disabling classic audio profiles because dual mode is disabled");
                        disableProfileBeforeUserEnablesLeAudio(
                                localBluetoothProfileManager.mA2dpProfile);
                        disableProfileBeforeUserEnablesLeAudio(
                                localBluetoothProfileManager.mHeadsetProfile);
                    }
                    HearingAidProfile hearingAidProfile =
                            localBluetoothProfileManager.mHearingAidProfile;
                    LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant =
                            localBluetoothProfileManager.mLeAudioBroadcastAssistant;
                    for (CachedBluetoothDevice cachedBluetoothDevice2 :
                            (List) ((HashMap) this.mProfileDeviceMap).get("LE_AUDIO")) {
                        Log.d(
                                "BtDetailsProfilesCtrl",
                                "device:"
                                        + cachedBluetoothDevice2.mDevice.getAnonymizedAddress()
                                        + " enable LE profile");
                        ((LeAudioProfile) profileByName)
                                .setEnabled(cachedBluetoothDevice2.mDevice, true);
                        if (hearingAidProfile != null) {
                            hearingAidProfile.setEnabled(cachedBluetoothDevice2.mDevice, false);
                        }
                        if (localBluetoothLeBroadcastAssistant != null) {
                            Log.d(
                                    "BtDetailsProfilesCtrl",
                                    "device:"
                                            + cachedBluetoothDevice2.mDevice.getAnonymizedAddress()
                                            + " enable LE broadcast assistant profile");
                            localBluetoothLeBroadcastAssistant.setEnabled(
                                    cachedBluetoothDevice2.mDevice, true);
                        }
                    }
                }
            }
        } else if (!(profileByName instanceof LeAudioProfile)) {
            BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
            profileByName.setEnabled(bluetoothDevice2, false);
            if (profileByName instanceof MapProfile) {
                bluetoothDevice2.setMessageAccessPermission(2);
            } else if (profileByName instanceof PbapServerProfile) {
                bluetoothDevice2.setPhonebookAccessPermission(2);
            }
        } else if (((HashMap) this.mProfileDeviceMap).get("LE_AUDIO") == null) {
            Log.e(
                    "BtDetailsProfilesCtrl",
                    "There is no the LE profile or no device in mProfileDeviceMap. Do nothing.");
        } else {
            HearingAidProfile hearingAidProfile2 = localBluetoothProfileManager.mHearingAidProfile;
            LocalBluetoothLeBroadcastAssistant localBluetoothLeBroadcastAssistant2 =
                    localBluetoothProfileManager.mLeAudioBroadcastAssistant;
            for (CachedBluetoothDevice cachedBluetoothDevice3 :
                    (List) ((HashMap) this.mProfileDeviceMap).get("LE_AUDIO")) {
                Log.d(
                        "BtDetailsProfilesCtrl",
                        "device:"
                                + cachedBluetoothDevice3.mDevice.getAnonymizedAddress()
                                + " disable LE profile");
                ((LeAudioProfile) profileByName).setEnabled(cachedBluetoothDevice3.mDevice, false);
                if (hearingAidProfile2 != null) {
                    hearingAidProfile2.setEnabled(cachedBluetoothDevice3.mDevice, true);
                }
                if (localBluetoothLeBroadcastAssistant2 != null) {
                    Log.d(
                            "BtDetailsProfilesCtrl",
                            "device:"
                                    + cachedBluetoothDevice3.mDevice.getAnonymizedAddress()
                                    + " disable LE broadcast assistant profile");
                    localBluetoothLeBroadcastAssistant2.setEnabled(
                            cachedBluetoothDevice3.mDevice, false);
                }
            }
            if (!SystemProperties.getBoolean("persist.bluetooth.enable_dual_mode_audio", false)) {
                Log.i(
                        "BtDetailsProfilesCtrl",
                        "Enabling classic audio profiles because dual mode is disabled");
                enableProfileAfterUserDisablesLeAudio(localBluetoothProfileManager.mA2dpProfile);
                enableProfileAfterUserDisablesLeAudio(localBluetoothProfileManager.mHeadsetProfile);
            }
        }
        refreshProfilePreference(twoStatePreference, profileByName);
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        boolean z = true;
        this.mIsLeContactSharingEnabled =
                DeviceConfig.getBoolean("settings_ui", "bt_le_audio_contact_sharing_enabled", true);
        boolean z2 = SystemProperties.getBoolean("persist.bluetooth.leaudio.toggle_visible", true);
        boolean z3 =
                SystemProperties.getBoolean(
                        "ro.bluetooth.leaudio.le_audio_connection_by_default", true);
        if (!z2 && !z3) {
            z = false;
        }
        this.mIsLeAudioToggleEnabled = z;
        StringBuilder sb = new StringBuilder("BT_LE_AUDIO_CONTACT_SHARING_ENABLED:");
        sb.append(this.mIsLeContactSharingEnabled);
        sb.append(", LE_AUDIO_TOGGLE_VISIBLE_PROPERTY:");
        sb.append(z2);
        sb.append(", LE_AUDIO_CONNECTION_BY_DEFAULT_PROPERTY:");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z3, "BtDetailsProfilesCtrl");
        Iterator it = this.mAllOfCachedDevices.iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).registerCallback(this);
        }
        this.mProfileManager.addServiceListener(this);
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ThreadUtils.postOnBackgroundThread(
                new BluetoothDetailsProfilesController$$ExternalSyntheticLambda4(this, 1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x018e, code lost:

       if (r11 == 1) goto L117;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refreshProfilePreference(
            androidx.preference.TwoStatePreference r11,
            com.android.settingslib.bluetooth.LocalBluetoothProfile r12) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.bluetooth.BluetoothDetailsProfilesController.refreshProfilePreference(androidx.preference.TwoStatePreference,"
                    + " com.android.settingslib.bluetooth.LocalBluetoothProfile):void");
    }
}
