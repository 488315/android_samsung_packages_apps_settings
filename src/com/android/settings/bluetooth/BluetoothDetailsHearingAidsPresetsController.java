package com.android.settings.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHapPresetInfo;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.ListeningExecutorService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsHearingAidsPresetsController extends BluetoothDetailsController
        implements Preference.OnPreferenceChangeListener,
                BluetoothHapClient.Callback,
                LocalBluetoothProfileManager.ServiceListener,
                OnStart,
                OnStop {
    public final HapClientProfile mHapClientProfile;
    public ListPreference mPreference;
    public final LocalBluetoothProfileManager mProfileManager;

    public BluetoothDetailsHearingAidsPresetsController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        LocalBluetoothProfileManager localBluetoothProfileManager =
                localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mHapClientProfile = localBluetoothProfileManager.mHapClientProfile;
    }

    public ListPreference getPreference() {
        return this.mPreference;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "hearing_aids_presets";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("hearing_device_group");
        if (preferenceCategory != null) {
            Context context = preferenceCategory.getContext();
            ListPreference listPreference = new ListPreference(context, null);
            listPreference.setKey("hearing_aids_presets");
            listPreference.setOrder(2);
            listPreference.setTitle(context.getString(R.string.bluetooth_hearing_aids_presets));
            listPreference.setOnPreferenceChangeListener(this);
            this.mPreference = listPreference;
            preferenceCategory.addPreference(listPreference);
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (this.mHapClientProfile == null) {
            return false;
        }
        return this.mCachedDevice.getProfiles().stream()
                .anyMatch(
                        new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda2(
                                0));
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnPause
    public final void onPause() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile != null) {
            try {
                BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                if (bluetoothHapClient == null) {
                    Log.w(
                            "HapClientProfile",
                            "Proxy not attached to service. Cannot unregister callback.");
                } else {
                    bluetoothHapClient.unregisterCallback(this);
                }
            } catch (IllegalArgumentException e) {
                Log.w(
                        "BluetoothDetailsHearingAidsPresetsController",
                        "Cannot unregister callback: " + e.getMessage());
            }
        }
        super.onPause();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (TextUtils.equals(preference.getKey(), "hearing_aids_presets")
                && (obj instanceof String)) {
            String str = (String) obj;
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                String charSequence =
                        listPreference.mEntries[listPreference.findIndexOfValue(str)].toString();
                int parseInt = Integer.parseInt(str);
                listPreference.setSummary(charSequence);
                StringBuilder sb = new StringBuilder("onPreferenceChange, presetIndex: ");
                sb.append(parseInt);
                sb.append(", presetName: ");
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        sb, charSequence, "BluetoothDetailsHearingAidsPresetsController");
                HapClientProfile hapClientProfile = this.mHapClientProfile;
                CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
                BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                boolean supportsSynchronizedPresets =
                        bluetoothHapClient != null
                                ? bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice)
                                : false;
                int hapGroup = this.mHapClientProfile.getHapGroup(cachedBluetoothDevice.mDevice);
                if (!supportsSynchronizedPresets) {
                    selectPresetIndependently(parseInt);
                    return true;
                }
                if (hapGroup == -1) {
                    Log.w(
                            "BluetoothDetailsHearingAidsPresetsController",
                            "supportSynchronizedPresets but hapGroupId is invalid.");
                    selectPresetIndependently(parseInt);
                    return true;
                }
                if (this.mPreference == null) {
                    return true;
                }
                StringBuilder m =
                        RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                                "selectPresetSynchronously, presetIndex: ",
                                ", groupId: ",
                                parseInt,
                                hapGroup,
                                ", device: ");
                m.append(cachedBluetoothDevice.mDevice.getAddress());
                Log.d("BluetoothDetailsHearingAidsPresetsController", m.toString());
                BluetoothHapClient bluetoothHapClient2 = this.mHapClientProfile.mService;
                if (bluetoothHapClient2 == null) {
                    Log.w(
                            "HapClientProfile",
                            "Proxy not attached to service. Cannot select preset for group.");
                    return true;
                }
                bluetoothHapClient2.selectPresetForGroup(hapGroup, parseInt);
                return true;
            }
        }
        return false;
    }

    public final void onPresetInfoChanged(BluetoothDevice bluetoothDevice, List list, int i) {
        if (bluetoothDevice.equals(this.mCachedDevice.mDevice)) {
            Log.d(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "onPresetInfoChanged, device: "
                            + bluetoothDevice.getAddress()
                            + ", reason: "
                            + i);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                BluetoothHapPresetInfo bluetoothHapPresetInfo = (BluetoothHapPresetInfo) it.next();
                Log.d(
                        "BluetoothDetailsHearingAidsPresetsController",
                        "    preset "
                                + bluetoothHapPresetInfo.getIndex()
                                + ": "
                                + bluetoothHapPresetInfo.getName());
            }
            ((BluetoothDetailsController) this)
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
                                    this, 0));
        }
    }

    public final void onPresetSelected(BluetoothDevice bluetoothDevice, int i, int i2) {
        if (bluetoothDevice.equals(this.mCachedDevice.mDevice)) {
            StringBuilder sb = new StringBuilder("onPresetSelected, device: ");
            sb.append(bluetoothDevice.getAddress());
            sb.append(", presetIndex: ");
            sb.append(i);
            sb.append(", reason: ");
            Preference$$ExternalSyntheticOutline0.m(
                    sb, i2, "BluetoothDetailsHearingAidsPresetsController");
            ((BluetoothDetailsController) this)
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
                                    this, 0));
        }
    }

    public final void onPresetSelectionFailed(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice.equals(this.mCachedDevice.mDevice)) {
            Log.w(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "onPresetSelectionFailed, device: "
                            + bluetoothDevice.getAddress()
                            + ", reason: "
                            + i);
            ((BluetoothDetailsController) this)
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
                                    this, 1));
        }
    }

    public final void onPresetSelectionForGroupFailed(int i, int i2) {
        if (i == this.mHapClientProfile.getHapGroup(this.mCachedDevice.mDevice)) {
            Log.w(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "onPresetSelectionForGroupFailed, group: " + i + ", reason: " + i2);
            ListPreference listPreference = this.mPreference;
            if (listPreference != null) {
                selectPresetIndependently(Integer.parseInt(listPreference.mValue));
            }
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        registerHapCallback();
        super.onResume();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile == null || !hapClientProfile.mIsProfileReady) {
            return;
        }
        this.mProfileManager.removeServiceListener(this);
        registerHapCallback();
        refresh();
    }

    public final void onSetPresetNameFailed(BluetoothDevice bluetoothDevice, int i) {
        if (bluetoothDevice.equals(this.mCachedDevice.mDevice)) {
            Log.w(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "onSetPresetNameFailed, device: "
                            + bluetoothDevice.getAddress()
                            + ", reason: "
                            + i);
            ((BluetoothDetailsController) this)
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
                                    this, 3));
        }
    }

    public final void onSetPresetNameForGroupFailed(int i, int i2) {
        if (i == this.mHapClientProfile.getHapGroup(this.mCachedDevice.mDevice)) {
            Log.w(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "onSetPresetNameForGroupFailed, group: " + i + ", reason: " + i2);
            ((BluetoothDetailsController) this)
                    .mContext
                    .getMainExecutor()
                    .execute(
                            new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda0(
                                    this, 2));
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile == null || hapClientProfile.mIsProfileReady) {
            return;
        }
        this.mProfileManager.addServiceListener(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public final void onStop() {
        this.mProfileManager.removeServiceListener(this);
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ListPreference listPreference;
        List allPresetInfo;
        if (!isAvailable() || (listPreference = this.mPreference) == null) {
            return;
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        listPreference.setEnabled(cachedBluetoothDevice.isConnectedHapClientDevice());
        if (this.mPreference != null) {
            HapClientProfile hapClientProfile = this.mHapClientProfile;
            BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
            BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
            if (bluetoothHapClient == null) {
                Log.w(
                        "HapClientProfile",
                        "Proxy not attached to service. Cannot get all preset info.");
                allPresetInfo = new ArrayList();
            } else {
                allPresetInfo = bluetoothHapClient.getAllPresetInfo(bluetoothDevice);
            }
            List list =
                    allPresetInfo.stream()
                            .filter(
                                    new BluetoothDetailsHearingAidsPresetsController$$ExternalSyntheticLambda2(
                                            1))
                            .toList();
            CharSequence[] charSequenceArr = new CharSequence[list.size()];
            CharSequence[] charSequenceArr2 = new CharSequence[list.size()];
            for (int i = 0; i < list.size(); i++) {
                charSequenceArr[i] = ((BluetoothHapPresetInfo) list.get(i)).getName();
                charSequenceArr2[i] =
                        Integer.toString(((BluetoothHapPresetInfo) list.get(i)).getIndex());
            }
            ListPreference listPreference2 = this.mPreference;
            listPreference2.mEntries = charSequenceArr;
            listPreference2.mEntryValues = charSequenceArr2;
        }
        if (this.mPreference.mEntries.length == 0) {
            Log.w(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "Disable the preference since preset info size = 0");
            this.mPreference.setEnabled(false);
            return;
        }
        int activePresetIndex =
                this.mHapClientProfile.getActivePresetIndex(cachedBluetoothDevice.mDevice);
        if (activePresetIndex == 0) {
            this.mPreference.setSummary((CharSequence) null);
            return;
        }
        this.mPreference.setValue(Integer.toString(activePresetIndex));
        ListPreference listPreference3 = this.mPreference;
        listPreference3.setSummary(listPreference3.getEntry());
    }

    public final void registerHapCallback() {
        HapClientProfile hapClientProfile = this.mHapClientProfile;
        if (hapClientProfile != null) {
            try {
                ListeningExecutorService backgroundExecutor = ThreadUtils.getBackgroundExecutor();
                BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                if (bluetoothHapClient == null) {
                    Log.w(
                            "HapClientProfile",
                            "Proxy not attached to service. Cannot register callback.");
                } else {
                    bluetoothHapClient.registerCallback(backgroundExecutor, this);
                }
            } catch (IllegalArgumentException e) {
                Log.w(
                        "BluetoothDetailsHearingAidsPresetsController",
                        "Cannot register callback: " + e.getMessage());
            }
        }
    }

    public final void selectPresetIndependently(int i) {
        if (this.mPreference == null) {
            return;
        }
        StringBuilder m =
                ListPopupWindow$$ExternalSyntheticOutline0.m(
                        i, "selectPresetIndependently, presetIndex: ", ", device: ");
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        m.append(cachedBluetoothDevice.mDevice.getAddress());
        Log.d("BluetoothDetailsHearingAidsPresetsController", m.toString());
        this.mHapClientProfile.selectPreset(cachedBluetoothDevice.mDevice, i);
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        if (cachedBluetoothDevice2 != null) {
            Log.d(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "selectPreset for subDevice, device: " + cachedBluetoothDevice2);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice2.mDevice, i);
        }
        for (CachedBluetoothDevice cachedBluetoothDevice3 : cachedBluetoothDevice.mMemberDevices) {
            Log.d(
                    "BluetoothDetailsHearingAidsPresetsController",
                    "selectPreset for memberDevice, device: " + cachedBluetoothDevice3);
            this.mHapClientProfile.selectPreset(cachedBluetoothDevice3.mDevice, i);
        }
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {}
}
