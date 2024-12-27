package com.android.settings.biometrics.combination;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener;
import com.android.settings.biometrics.activeunlock.ActiveUnlockDeviceNameListener;
import com.android.settings.biometrics.activeunlock.ActiveUnlockStatusPreferenceController;
import com.android.settings.biometrics.activeunlock.ActiveUnlockStatusUtils;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CombinedBiometricSettings extends BiometricsSettingsBase {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new CombinedBiometricSearchIndexProvider(R.xml.security_settings_combined_biometric);
    public ActiveUnlockDeviceNameListener mActiveUnlockDeviceNameListener;
    public ActiveUnlockStatusUtils mActiveUnlockStatusUtils;

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFacePreferenceKey() {
        return "biometric_face_settings";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getFingerprintPreferenceKey() {
        return "biometric_fingerprint_settings";
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BiometricSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1878;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.security_settings_combined_biometric;
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUnlockPhonePreferenceKey() {
        return "biometric_settings_biometric_keyguard";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUseAnyBiometricSummary() {
        ActiveUnlockDeviceNameListener activeUnlockDeviceNameListener =
                this.mActiveUnlockDeviceNameListener;
        if (activeUnlockDeviceNameListener == null
                || activeUnlockDeviceNameListener.mActiveUnlockContentListener.mContent == null) {
            return super.getUseAnyBiometricSummary();
        }
        ActiveUnlockStatusUtils activeUnlockStatusUtils = this.mActiveUnlockStatusUtils;
        boolean hasFaceHardware = Utils.hasFaceHardware(activeUnlockStatusUtils.mContext);
        boolean hasFingerprintHardware =
                Utils.hasFingerprintHardware(activeUnlockStatusUtils.mContext);
        return activeUnlockStatusUtils.mContext.getString(
                (hasFaceHardware && hasFingerprintHardware)
                        ? R.string
                                .biometric_settings_use_face_fingerprint_or_watch_preference_summary
                        : hasFaceHardware
                                ? R.string.biometric_settings_use_face_or_watch_preference_summary
                                : hasFingerprintHardware
                                        ? R.string
                                                .biometric_settings_use_fingerprint_or_watch_preference_summary
                                        : R.string.biometric_settings_use_watch_preference_summary);
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final String getUseInAppsPreferenceKey() {
        return "biometric_settings_biometric_app";
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((BiometricSettingsKeyguardPreferenceController)
                        use(BiometricSettingsKeyguardPreferenceController.class))
                .setUserId(this.mUserId);
        ((BiometricSettingsAppPreferenceController)
                        use(BiometricSettingsAppPreferenceController.class))
                .setUserId(this.mUserId);
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String string;
        super.onCreate(bundle);
        this.mActiveUnlockStatusUtils = new ActiveUnlockStatusUtils(getActivity());
        FragmentActivity activity = getActivity();
        Utils.getFingerprintManagerOrNull(activity);
        Utils.getFaceManagerOrNull(activity);
        if (this.mActiveUnlockStatusUtils.isAvailable()) {
            ActiveUnlockDeviceNameListener activeUnlockDeviceNameListener =
                    new ActiveUnlockDeviceNameListener(
                            getActivity(),
                            new ActiveUnlockContentListener
                                    .OnContentChangedListener() { // from class:
                                                                  // com.android.settings.biometrics.combination.CombinedBiometricSettings.1
                                @Override // com.android.settings.biometrics.activeunlock.ActiveUnlockContentListener.OnContentChangedListener
                                public final void onContentChanged(String str) {
                                    CombinedBiometricSettings combinedBiometricSettings =
                                            CombinedBiometricSettings.this;
                                    combinedBiometricSettings.getClass();
                                    Preference findPreference =
                                            combinedBiometricSettings.findPreference(
                                                    "biometric_settings_biometric_keyguard");
                                    if (findPreference != null) {
                                        findPreference.setSummary(
                                                combinedBiometricSettings
                                                        .getUseAnyBiometricSummary());
                                    }
                                }
                            });
            this.mActiveUnlockDeviceNameListener = activeUnlockDeviceNameListener;
            activeUnlockDeviceNameListener.mActiveUnlockContentListener.subscribe();
            Preference findPreference = findPreference("biometric_intro");
            if (findPreference != null) {
                ActiveUnlockStatusUtils activeUnlockStatusUtils = this.mActiveUnlockStatusUtils;
                boolean hasFaceHardware = Utils.hasFaceHardware(activeUnlockStatusUtils.mContext);
                boolean hasFingerprintHardware =
                        Utils.hasFingerprintHardware(activeUnlockStatusUtils.mContext);
                if (activeUnlockStatusUtils.isAvailable()) {
                    int i =
                            (hasFaceHardware && hasFingerprintHardware)
                                    ? R.string.biometric_settings_intro_with_activeunlock
                                    : hasFaceHardware
                                            ? R.string.biometric_settings_intro_with_face
                                            : hasFingerprintHardware
                                                    ? R.string
                                                            .biometric_settings_intro_with_fingerprint
                                                    : 0;
                    string =
                            i == 0
                                    ? ApnSettings.MVNO_NONE
                                    : activeUnlockStatusUtils.mContext.getString(i);
                } else {
                    string =
                            activeUnlockStatusUtils.mContext.getString(
                                    R.string.biometric_settings_intro);
                }
                findPreference.setTitle(string);
            }
            Preference findPreference2 = findPreference("biometric_ways_to_use");
            if (findPreference2 != null) {
                ActiveUnlockStatusUtils activeUnlockStatusUtils2 = this.mActiveUnlockStatusUtils;
                boolean hasFaceHardware2 = Utils.hasFaceHardware(activeUnlockStatusUtils2.mContext);
                boolean hasFingerprintHardware2 =
                        Utils.hasFingerprintHardware(activeUnlockStatusUtils2.mContext);
                findPreference2.setTitle(
                        activeUnlockStatusUtils2.mContext.getString(
                                (hasFaceHardware2 && hasFingerprintHardware2)
                                        ? R.string
                                                .biometric_settings_use_face_fingerprint_or_watch_for
                                        : hasFaceHardware2
                                                ? R.string.biometric_settings_use_face_or_watch_for
                                                : hasFingerprintHardware2
                                                        ? R.string
                                                                .biometric_settings_use_fingerprint_or_watch_for
                                                        : R.string
                                                                .biometric_settings_use_watch_for));
            }
            getActivity().setTitle(this.mActiveUnlockStatusUtils.getTitleForActiveUnlock());
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        ActiveUnlockDeviceNameListener activeUnlockDeviceNameListener =
                this.mActiveUnlockDeviceNameListener;
        if (activeUnlockDeviceNameListener != null) {
            activeUnlockDeviceNameListener.mActiveUnlockContentListener.unsubscribe();
        }
        super.onDestroy();
    }

    @Override // com.android.settings.biometrics.combination.BiometricsSettingsBase
    public final boolean onRetryPreferenceTreeClick(Preference preference, boolean z) {
        if (!this.mActiveUnlockStatusUtils.isAvailable()
                || !ActiveUnlockStatusPreferenceController.KEY_ACTIVE_UNLOCK_SETTINGS.equals(
                        preference.getKey())) {
            return super.onRetryPreferenceTreeClick(preference, z);
        }
        this.mDoNotFinishActivity = true;
        this.mActiveUnlockStatusUtils.getClass();
        Intent intent = this.mActiveUnlockStatusUtils.getIntent();
        if (intent != null) {
            startActivityForResult(intent, VolteConstants.ErrorCode.MAKECALL_REG_FAILURE_REG_403);
        }
        return true;
    }
}
