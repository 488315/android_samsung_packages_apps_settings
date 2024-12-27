package com.android.settings.bluetooth;

import android.content.Context;
import android.media.AudioManager;
import android.media.audio.Flags;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BluetoothDetailsAudioDeviceTypeController extends BluetoothDetailsController
        implements Preference.OnPreferenceChangeListener {
    public ListPreference mAudioDeviceTypePreference;
    public final AudioManager mAudioManager;
    public final LocalBluetoothProfileManager mProfileManager;
    PreferenceCategory mProfilesContainer;

    public BluetoothDetailsAudioDeviceTypeController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context, preferenceFragmentCompat, cachedBluetoothDevice, lifecycle);
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mProfileManager = localBluetoothManager.mProfileManager;
    }

    public void createAudioDeviceTypePreference(Context context) {
        int bluetoothAudioDeviceCategory_legacy;
        ListPreference listPreference = new ListPreference(context, null);
        this.mAudioDeviceTypePreference = listPreference;
        listPreference.setKey("bluetooth_audio_device_type");
        this.mAudioDeviceTypePreference.setTitle(
                ((BluetoothDetailsController) this)
                        .mContext.getString(R.string.bluetooth_details_audio_device_types_title));
        this.mAudioDeviceTypePreference.setEntries(
                new CharSequence[] {
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_details_audio_device_type_unknown),
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_details_audio_device_type_speaker),
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_details_audio_device_type_headphones),
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_details_audio_device_type_carkit),
                    ((BluetoothDetailsController) this)
                            .mContext.getString(
                                    R.string.bluetooth_details_audio_device_type_hearing_aid),
                    ((BluetoothDetailsController) this)
                            .mContext.getString(R.string.bluetooth_details_audio_device_type_other)
                });
        this.mAudioDeviceTypePreference.mEntryValues =
                new CharSequence[] {
                    Integer.toString(0),
                    Integer.toString(2),
                    Integer.toString(3),
                    Integer.toString(4),
                    Integer.toString(6),
                    Integer.toString(1)
                };
        boolean automaticBtDeviceType = Flags.automaticBtDeviceType();
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        if (automaticBtDeviceType) {
            bluetoothAudioDeviceCategory_legacy =
                    this.mAudioManager.getBluetoothAudioDeviceCategory(
                            cachedBluetoothDevice.mDevice.getAddress());
        } else {
            bluetoothAudioDeviceCategory_legacy =
                    this.mAudioManager.getBluetoothAudioDeviceCategory_legacy(
                            cachedBluetoothDevice.mDevice.getAddress(),
                            cachedBluetoothDevice.mDevice.getType() == 2);
        }
        this.mAudioDeviceTypePreference.setValue(
                Integer.toString(bluetoothAudioDeviceCategory_legacy));
        if (Flags.automaticBtDeviceType()
                && this.mAudioManager.isBluetoothAudioDeviceCategoryFixed(
                        cachedBluetoothDevice.mDevice.getAddress())) {
            this.mAudioDeviceTypePreference.setEnabled(false);
        }
        ListPreference listPreference2 = this.mAudioDeviceTypePreference;
        listPreference2.setSummary(listPreference2.getEntry());
        this.mAudioDeviceTypePreference.setOnPreferenceChangeListener(this);
    }

    public ListPreference getAudioDeviceTypePreference() {
        return this.mAudioDeviceTypePreference;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_audio_device_type_group";
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void init(PreferenceScreen preferenceScreen) {
        this.mProfilesContainer =
                (PreferenceCategory)
                        preferenceScreen.findPreference("bluetooth_audio_device_type_group");
        refresh();
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
        CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
        boolean isEnabled =
                a2dpProfile != null ? a2dpProfile.isEnabled(cachedBluetoothDevice.mDevice) : false;
        LeAudioProfile leAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
        return isEnabled
                || (leAudioProfile != null
                        ? leAudioProfile.isEnabled(cachedBluetoothDevice.mDevice)
                        : false);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String str;
        int findIndexOfValue;
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            if (listPreference.getKey().equals("bluetooth_audio_device_type")) {
                if ((obj instanceof String)
                        && (findIndexOfValue =
                                        listPreference.findIndexOfValue((str = (String) obj)))
                                >= 0) {
                    listPreference.setSummary(listPreference.mEntries[findIndexOfValue]);
                    boolean automaticBtDeviceType = Flags.automaticBtDeviceType();
                    CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevice;
                    if (automaticBtDeviceType) {
                        this.mAudioManager.setBluetoothAudioDeviceCategory(
                                cachedBluetoothDevice.mDevice.getAddress(), Integer.parseInt(str));
                    } else {
                        this.mAudioManager.setBluetoothAudioDeviceCategory_legacy(
                                cachedBluetoothDevice.mDevice.getAddress(),
                                cachedBluetoothDevice.mDevice.getType() == 2,
                                Integer.parseInt(str));
                    }
                    cachedBluetoothDevice.dispatchAttributesChanged();
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.settings.bluetooth.BluetoothDetailsController
    public final void refresh() {
        ListPreference listPreference =
                (ListPreference)
                        this.mProfilesContainer.findPreference("bluetooth_audio_device_type");
        this.mAudioDeviceTypePreference = listPreference;
        if (listPreference == null) {
            createAudioDeviceTypePreference(this.mProfilesContainer.getContext());
            this.mProfilesContainer.addPreference(this.mAudioDeviceTypePreference);
        }
    }
}
