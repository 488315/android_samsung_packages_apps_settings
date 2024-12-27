package com.samsung.android.settings.bluetooth.controller;

import android.os.Debug;
import android.util.Log;
import android.view.ContextThemeWrapper;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothDetailsController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsGroupController extends BluetoothDetailsController
        implements Preference.OnPreferenceClickListener,
                LocalBluetoothProfileManager.ServiceListener,
                Preference.OnPreferenceChangeListener {
    public CachedBluetoothDevice mCachedDevice;
    public PreferenceGroup mProfileContainer;
    public LocalBluetoothProfileManager mProfileManager;

    static {
        Debug.semIsProductDev();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_group";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mFragment
                .getActivity()
                .getResources()
                .getString(R.string.screen_device_profiles_setting);
        this.mProfileContainer =
                (PreferenceGroup) preferenceScreen.findPreference("bluetooth_group");
        setProfileContainer();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return true;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        CachedBluetoothDevice cachedBluetoothDevice;
        Log.d(
                "SecBluetoothDetailsGroupController",
                "refresh: member size = " + ((HashSet) this.mCachedDevice.mMemberDevices).size());
        PreferenceGroup preferenceGroup = this.mProfileContainer;
        if (preferenceGroup != null) {
            preferenceGroup.removeAll();
            Set<CachedBluetoothDevice> set = this.mCachedDevice.mMemberDevices;
            if (set != null) {
                if (((HashSet) set).size() == 0
                        && (cachedBluetoothDevice = this.mCachedDevice.mLeadDevice) != null) {
                    this.mCachedDevice = cachedBluetoothDevice;
                    set = cachedBluetoothDevice.mMemberDevices;
                }
                for (CachedBluetoothDevice cachedBluetoothDevice2 : set) {
                    if (cachedBluetoothDevice2 != null) {
                        Log.d(
                                "SecBluetoothDetailsGroupController",
                                "setGroupMember: createPreference = "
                                        + cachedBluetoothDevice2.getName());
                        Log.d(
                                "SecBluetoothDetailsGroupController",
                                "createPreference :: cachedDevice : " + cachedBluetoothDevice2);
                        Preference preference =
                                new Preference(
                                        new ContextThemeWrapper(
                                                this.mProfileContainer.getContext(),
                                                R.style.PreferenceThemeOverlay));
                        preference.setTitle(
                                cachedBluetoothDevice2.getNameForLog()
                                        + "("
                                        + cachedBluetoothDevice2.mDevice.getAddress()
                                        + ")");
                        int maxConnectionState = cachedBluetoothDevice2.getMaxConnectionState();
                        preference.setSummary(
                                maxConnectionState != 1
                                        ? maxConnectionState != 2
                                                ? maxConnectionState != 3
                                                        ? "disconnected"
                                                        : "disconnecting"
                                                : "connected"
                                        : "connecting");
                        preference.setKey(cachedBluetoothDevice2.mDevice.getAddress());
                        preference.setPersistent();
                        preference.setOnPreferenceClickListener(this);
                        this.mProfileContainer.addPreference(preference);
                    }
                }
            }
        }
        setProfileContainer();
    }

    public final void setProfileContainer() {
        PreferenceGroup preferenceGroup = this.mProfileContainer;
        if (preferenceGroup != null) {
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
            if (cachedBluetoothDevice.mGroupId <= 0) {
                preferenceGroup.setTitle(ApnSettings.MVNO_NONE);
                return;
            }
            int profileConnectionState =
                    cachedBluetoothDevice.getProfileConnectionState(
                            this.mProfileManager.mLeAudioProfile);
            PreferenceGroup preferenceGroup2 = this.mProfileContainer;
            StringBuilder sb = new StringBuilder("Group ID: ");
            sb.append(this.mCachedDevice.mGroupId);
            sb.append(", ");
            sb.append(
                    profileConnectionState != 1
                            ? profileConnectionState != 2
                                    ? profileConnectionState != 3 ? "disconnected" : "disconnecting"
                                    : "connected"
                            : "connecting");
            preferenceGroup2.setTitle(sb.toString());
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {}

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}
}
