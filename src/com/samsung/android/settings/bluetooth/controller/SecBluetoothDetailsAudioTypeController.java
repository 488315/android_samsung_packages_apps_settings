package com.samsung.android.settings.bluetooth.controller;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.bluetooth.fragment.BluetoothAudioTypeFragment;
import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecBluetoothDetailsAudioTypeController extends AbstractPreferenceController
        implements Preference.OnPreferenceClickListener, LifecycleObserver {
    public PreferenceGroup mAdvancedContainer;
    public Preference mAudioTypePreference;
    public final CachedBluetoothDevice mCachedDevice;
    public final BluetoothDevice mDevice;
    public final PreferenceFragmentCompat mFragment;
    public final LocalBluetoothManager mManager;
    public final String mScreenId;

    public SecBluetoothDetailsAudioTypeController(
            Context context,
            PreferenceFragmentCompat preferenceFragmentCompat,
            LocalBluetoothManager localBluetoothManager,
            CachedBluetoothDevice cachedBluetoothDevice,
            Lifecycle lifecycle) {
        super(context);
        this.mManager = localBluetoothManager;
        this.mCachedDevice = cachedBluetoothDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mFragment = preferenceFragmentCompat;
        this.mScreenId =
                SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        preferenceFragmentCompat, R.string.screen_device_profiles_setting);
        lifecycle.addObserver(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mAdvancedContainer =
                (PreferenceGroup) preferenceScreen.findPreference("bluetooth_audio_type");
        Preference findPreference = preferenceScreen.findPreference("bluetooth_pref_audio_type");
        this.mAudioTypePreference = findPreference;
        String audioTypeString = getAudioTypeString();
        if (!TextUtils.isEmpty(null)) {
            findPreference.setTitle((CharSequence) null);
        }
        if (!TextUtils.isEmpty(audioTypeString)) {
            findPreference.setSummary(audioTypeString);
        }
        findPreference.setOnPreferenceClickListener(this);
        if (isAvailable()) {
            this.mAdvancedContainer.setVisible(true);
        } else {
            this.mAdvancedContainer.setVisible(false);
        }
        super.displayPreference(preferenceScreen);
    }

    public final String getAudioTypeString() {
        int semGetAudioType = this.mDevice.semGetAudioType();
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                semGetAudioType,
                "getAudioTypeString :: audioType = ",
                "SecBluetoothDetailsAudioTypeController");
        PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
        return semGetAudioType != 2
                ? semGetAudioType != 3
                        ? SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat, R.string.sec_bluetooth_audio_type_others)
                        : SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                                preferenceFragmentCompat, R.string.sec_bluetooth_audio_type_speaker)
                : SecBluetoothBroadcastAssistantController$$ExternalSyntheticOutline0.m(
                        preferenceFragmentCompat, R.string.sec_bluetooth_audio_type_headphones);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "bluetooth_audio_type";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!this.mCachedDevice.hasProfile(this.mManager.mProfileManager.mA2dpProfile)) {
            return false;
        }
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOLUME_MONITOR");
        return ("DSP".equals(string) || "FW".equals(string))
                && SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_AUDIO_CONFIG_VOLUMEMONITOR_STAGE")
                        >= 2;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Log.d("SecBluetoothDetailsAudioTypeController", "onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        isAvailable();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Log.d(
                "SecBluetoothDetailsAudioTypeController",
                "onPreferenceClick :: key = " + preference.getKey());
        if ("bluetooth_pref_audio_type".equals(preference.getKey())) {
            String string =
                    this.mContext
                            .getResources()
                            .getString(R.string.event_device_profiles_setting_audio_type);
            int semGetAudioType = this.mDevice.semGetAudioType();
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    semGetAudioType,
                    "getAudioTypeInt :: audioType = ",
                    "SecBluetoothDetailsAudioTypeController");
            SALogging.insertSALog(
                    semGetAudioType != 2 ? semGetAudioType != 3 ? 2 : 1 : 0,
                    this.mScreenId,
                    string);
            BluetoothDevice bluetoothDevice = this.mDevice;
            String canonicalName = BluetoothAudioTypeFragment.class.getCanonicalName();
            Bundle bundle = new Bundle(2);
            bundle.putString("device_address", bluetoothDevice.getAddress());
            PreferenceFragmentCompat preferenceFragmentCompat = this.mFragment;
            if (preferenceFragmentCompat.getActivity() instanceof SettingsActivity) {
                SubSettingLauncher subSettingLauncher =
                        new SubSettingLauncher(preferenceFragmentCompat.getActivity());
                SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
                launchRequest.mSourceMetricsCategory = 24;
                launchRequest.mDestinationName = canonicalName;
                launchRequest.mArguments = bundle;
                subSettingLauncher.launch();
            }
        }
        return true;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Log.d("SecBluetoothDetailsAudioTypeController", "onResume");
        if (!isAvailable()) {
            this.mAdvancedContainer.setVisible(false);
            return;
        }
        if (!this.mAdvancedContainer.isVisible()) {
            this.mAdvancedContainer.setVisible(true);
        }
        refresh();
    }

    public final void refresh() {
        Preference preference = this.mAudioTypePreference;
        if (preference != null) {
            preference.setSummary(getAudioTypeString());
            this.mAudioTypePreference.seslSetSummaryColor(
                    this.mContext.getResources().getColor(R.color.bluetooth_highlight_text_color));
        }
    }
}
