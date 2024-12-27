package com.android.settings.security.trustagent;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TrustAgentSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.trust_agent_settings);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TrustAgentSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 91;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.trust_agent_settings;
    }
}