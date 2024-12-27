package com.android.settings.enterprise;

import android.content.Context;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class EnterpriseSetDefaultAppsListFragment extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new EnterpriseSetDefaultAppsListPreferenceController(
                        context, this, context.getPackageManager()));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "EnterprisePrivacySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 940;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.enterprise_set_default_apps_settings;
    }
}
