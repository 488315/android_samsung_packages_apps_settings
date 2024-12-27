package com.samsung.android.settings.softwareupdate;

import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.widget.SecRadioButtonPreference;

import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AutoDownloadSettings extends DashboardFragment {
    public SecRadioButtonPreference mNever;
    public SecRadioButtonPreference mWifiOnly;
    public SecRadioButtonPreference mWifiOrMobile;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SoftwareUpdateSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 8107;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_auto_download_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mNever = (SecRadioButtonPreference) findPreference("key_never");
        this.mWifiOnly = (SecRadioButtonPreference) findPreference("key_wifi_only");
        this.mWifiOrMobile = (SecRadioButtonPreference) findPreference("key_wifi_or_mobile");
        int i =
                Settings.System.getInt(
                        getContext().getContentResolver(), "SOFTWARE_UPDATE_WIFI_ONLY2", -1);
        if (i == 0) {
            this.mNever.setChecked(true);
        } else if (i == 1) {
            this.mWifiOnly.setChecked(true);
        } else {
            if (i != 2) {
                return;
            }
            this.mWifiOrMobile.setChecked(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (!(preference instanceof SecRadioButtonPreference)) {
            return super.onPreferenceTreeClick(preference);
        }
        SecRadioButtonPreference secRadioButtonPreference = (SecRadioButtonPreference) preference;
        SecRadioButtonPreference[] secRadioButtonPreferenceArr = {
            this.mNever, this.mWifiOnly, this.mWifiOrMobile
        };
        for (int i = 0; i < 3; i++) {
            SecRadioButtonPreference secRadioButtonPreference2 = secRadioButtonPreferenceArr[i];
            secRadioButtonPreference2.setChecked(
                    secRadioButtonPreference2.getKey().equals(secRadioButtonPreference.getKey()));
        }
        Optional.ofNullable(
                        (Integer)
                                AutoDownloadPreferenceType.AutoDownload.map.get(
                                        secRadioButtonPreference.getKey()))
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.settings.softwareupdate.AutoDownloadSettings$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AutoDownloadPreferenceType.AUTO_DOWNLOAD.setDBAndLogging(
                                        AutoDownloadSettings.this.getContext(),
                                        ((Integer) obj).intValue());
                            }
                        });
        return true;
    }
}
