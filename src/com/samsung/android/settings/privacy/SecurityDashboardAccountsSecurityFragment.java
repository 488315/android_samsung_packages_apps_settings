package com.samsung.android.settings.privacy;

import android.content.Context;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardAccountsSecurityFragment extends SecDynamicFragment {
    public static final String TAG = SecurityDashboardAccountsSecurityFragment.class.getName();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_security_and_privacy_accounts_security_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.SecurityDashboardAccountsSecurityFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            boolean isGoogleServiceEnabled =
                    SecurityDashboardUtils.isChinaModel()
                            ? false
                            : SecurityDashboardUtils.isGoogleServiceEnabled(context);
            boolean isSamsungAccountSupported =
                    SecurityDashboardUtils.isSamsungAccountSupported(context);
            if (Utils.isRestrictedProfile(context)) {
                return false;
            }
            return isGoogleServiceEnabled || isSamsungAccountSupported;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_security_and_privacy_accounts_security_settings;
    }

    @Override // com.samsung.android.settings.dynamicmenu.SecDynamicFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Preference findPreference = findPreference("key_account_security_page");
        if (findPreference == null || !SecurityDashboardUtils.isChinaModel()) {
            return;
        }
        findPreference.setTitle(
                R.string.security_dashboard_action_accountsecurity_activity_description_chn);
    }
}
