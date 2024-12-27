package com.android.settings.sound;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class HandsFreeProfileOutputPreferenceController extends AudioSwitchPreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final int INVALID_INDEX = -1;

    public HandsFreeProfileOutputPreferenceController(Context context, String str) {
        super(context, str);
    }

    private int getConnectedDeviceIndex(String str) {
        List<BluetoothDevice> list = this.mConnectedDevices;
        if (list == null) {
            return -1;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(this.mConnectedDevices.get(i).getAddress(), str)) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public BluetoothDevice findActiveDevice() {
        BluetoothDevice findActiveHearingAidDevice = findActiveHearingAidDevice();
        BluetoothDevice findActiveLeAudioDevice = findActiveLeAudioDevice();
        HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
        if (findActiveHearingAidDevice != null) {
            return findActiveHearingAidDevice;
        }
        if (findActiveLeAudioDevice != null) {
            return findActiveLeAudioDevice;
        }
        if (headsetProfile == null || headsetProfile.getActiveDevice() == null) {
            return null;
        }
        return headsetProfile.getActiveDevice();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public int getDefaultDeviceIndex() {
        return this.mConnectedDevices.size();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        String str = (String) obj;
        if (!(preference instanceof ListPreference)) {
            return false;
        }
        CharSequence text = this.mContext.getText(R.string.media_output_default_summary);
        ListPreference listPreference = (ListPreference) preference;
        if (TextUtils.equals(str, text)) {
            this.mSelectedIndex = getDefaultDeviceIndex();
            setActiveBluetoothDevice(null);
            listPreference.setSummary(text);
            return true;
        }
        int connectedDeviceIndex = getConnectedDeviceIndex(str);
        if (connectedDeviceIndex == -1) {
            return false;
        }
        BluetoothDevice bluetoothDevice = this.mConnectedDevices.get(connectedDeviceIndex);
        this.mSelectedIndex = connectedDeviceIndex;
        setActiveBluetoothDevice(bluetoothDevice);
        listPreference.setSummary(bluetoothDevice.getAlias());
        return true;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setActiveBluetoothDevice(BluetoothDevice bluetoothDevice) {
        BluetoothAdapter bluetoothAdapter;
        if (Utils.isAudioModeOngoingCall(this.mContext)) {
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
            HeadsetProfile headsetProfile = localBluetoothProfileManager.mHeadsetProfile;
            if (hearingAidProfile != null && headsetProfile != null && bluetoothDevice == null) {
                BluetoothAdapter bluetoothAdapter2 = headsetProfile.mBluetoothAdapter;
                if (bluetoothAdapter2 != null) {
                    bluetoothAdapter2.removeActiveDevice(1);
                }
                hearingAidProfile.setActiveDevice(null);
                return;
            }
            if (hearingAidProfile != null
                    && bluetoothDevice != null
                    && hearingAidProfile.getHiSyncId(bluetoothDevice) != 0) {
                hearingAidProfile.setActiveDevice(bluetoothDevice);
                return;
            }
            if (headsetProfile == null
                    || (bluetoothAdapter = headsetProfile.mBluetoothAdapter) == null) {
                return;
            }
            if (bluetoothDevice == null) {
                bluetoothAdapter.removeActiveDevice(1);
            } else {
                bluetoothAdapter.setActiveDevice(bluetoothDevice, 1);
            }
        }
    }

    public void setPreference(
            CharSequence[] charSequenceArr,
            CharSequence[] charSequenceArr2,
            Preference preference) {
        ListPreference listPreference = (ListPreference) preference;
        listPreference.setEntries(charSequenceArr);
        listPreference.mEntryValues = charSequenceArr2;
        listPreference.setValueIndex(this.mSelectedIndex);
        listPreference.setSummary(charSequenceArr[this.mSelectedIndex]);
        this.mAudioSwitchPreferenceCallback.onPreferenceDataChanged(listPreference);
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    public void setupPreferenceEntries(
            CharSequence[] charSequenceArr,
            CharSequence[] charSequenceArr2,
            BluetoothDevice bluetoothDevice) {
        this.mSelectedIndex = getDefaultDeviceIndex();
        CharSequence text = this.mContext.getText(R.string.media_output_default_summary);
        int i = this.mSelectedIndex;
        charSequenceArr[i] = text;
        charSequenceArr2[i] = text;
        int size = this.mConnectedDevices.size();
        for (int i2 = 0; i2 < size; i2++) {
            BluetoothDevice bluetoothDevice2 = this.mConnectedDevices.get(i2);
            charSequenceArr[i2] = bluetoothDevice2.getAlias();
            charSequenceArr2[i2] = bluetoothDevice2.getAddress();
            if (bluetoothDevice2.equals(bluetoothDevice)) {
                this.mSelectedIndex = i2;
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference == null) {
            return;
        }
        if (!Utils.isAudioModeOngoingCall(this.mContext)) {
            this.mPreference.setVisible(false);
            preference.setSummary(this.mContext.getText(R.string.media_output_default_summary));
            return;
        }
        this.mConnectedDevices.clear();
        this.mConnectedDevices.addAll(getConnectedHfpDevices());
        this.mConnectedDevices.addAll(getConnectedHearingAidDevices());
        this.mConnectedDevices.addAll(getConnectedLeAudioDevices());
        int size = this.mConnectedDevices.size();
        if (size == 0) {
            this.mPreference.setVisible(false);
            CharSequence text = this.mContext.getText(R.string.media_output_default_summary);
            CharSequence[] charSequenceArr = {text};
            this.mSelectedIndex = getDefaultDeviceIndex();
            preference.setSummary(text);
            setPreference(charSequenceArr, charSequenceArr, preference);
            return;
        }
        this.mPreference.setVisible(true);
        int i = size + 1;
        CharSequence[] charSequenceArr2 = new CharSequence[i];
        CharSequence[] charSequenceArr3 = new CharSequence[i];
        setupPreferenceEntries(charSequenceArr2, charSequenceArr3, findActiveDevice());
        setPreference(charSequenceArr2, charSequenceArr3, preference);
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAutoOnStateChanged(int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceDeleted(
            CachedBluetoothDevice cachedBluetoothDevice) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onScanningStateChanged(boolean z) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onAclConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onConnectionStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}

    @Override // com.android.settings.sound.AudioSwitchPreferenceController,
              // com.android.settingslib.bluetooth.BluetoothCallback
    public /* bridge */ /* synthetic */ void onDeviceBondStateChanged(
            CachedBluetoothDevice cachedBluetoothDevice, int i) {}
}
