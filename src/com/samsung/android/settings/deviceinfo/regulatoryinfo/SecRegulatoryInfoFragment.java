package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import android.os.Bundle;
import android.os.SystemProperties;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoFragment extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 77030;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Class cls;
        super.onCreate(bundle);
        if (Rune.isDomesticModel()) {
            cls = SecRegulatoryInfoActivityKorea.class;
        } else if (Rune.isChinaModel()) {
            cls = SecRegulatoryInfoActivityChina.class;
        } else {
            cls =
                    ("IN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"))
                                    || "india"
                                            .equalsIgnoreCase(
                                                    SystemProperties.get("ro.csc.country_code")))
                            ? SecRegulatoryInfoActivityIndia.class
                            : SecRegulatoryInfoActivityGlobal.class;
        }
        Log.d("SecRegulatoryInfoFragment", "regulatory activity : ".concat(cls.getSimpleName()));
        String canonicalName = cls.getCanonicalName();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getActivity());
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mSourceMetricsCategory = 40;
        launchRequest.mDestinationName = canonicalName;
        subSettingLauncher.setTitleRes(R.string.regulatory_information, null);
        subSettingLauncher.launch();
        finish();
    }
}
