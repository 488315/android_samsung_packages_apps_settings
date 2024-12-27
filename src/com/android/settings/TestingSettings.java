package com.android.settings;

import android.content.Context;
import android.os.Bundle;
import android.os.UserManager;

import androidx.preference.PreferenceScreen;

import com.android.settings.network.telephony.MobileNetworkUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class TestingSettings extends SettingsPreferenceFragment {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 89;
    }

    public boolean isRadioInfoVisible(Context context) {
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager == null || userManager.isAdminUser()) {
            return !MobileNetworkUtils.isMobileNetworkUserRestricted(context);
        }
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.testing_settings);
        if (isRadioInfoVisible(getContext())) {
            return;
        }
        getPreferenceScreen()
                .removePreference((PreferenceScreen) findPreference("radio_info_settings"));
    }
}
