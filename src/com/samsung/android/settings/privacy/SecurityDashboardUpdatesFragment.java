package com.samsung.android.settings.privacy;

import android.content.Context;
import android.net.Uri;
import android.os.UserHandle;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.dynamicmenu.SecDynamicFragment;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecurityDashboardUpdatesFragment extends SecDynamicFragment {
    public static final String TAG = SecurityDashboardUpdatesFragment.class.getName();
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_security_and_privacy_updates_settings);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.privacy.SecurityDashboardUpdatesFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            Uri uri = SecurityDashboardUtils.FMM_SUPPORT_URI;
            return (UserHandle.myUserId() == 0
                            ? SoftwareUpdateUtils.isOTAUpgradeAllowed(context)
                            : false)
                    || SecurityDashboardUtils.isGPSuSupported(context);
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
        return R.xml.sec_security_and_privacy_updates_settings;
    }
}
