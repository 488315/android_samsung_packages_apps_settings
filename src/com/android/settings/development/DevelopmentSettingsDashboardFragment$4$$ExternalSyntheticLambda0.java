package com.android.settings.development;

import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class DevelopmentSettingsDashboardFragment$4$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DevelopmentSettingsDashboardFragment$4$$ExternalSyntheticLambda0(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                DevelopmentSettingsDashboardFragment developmentSettingsDashboardFragment =
                        DevelopmentSettingsDashboardFragment.this;
                BaseSearchIndexProvider baseSearchIndexProvider =
                        DevelopmentSettingsDashboardFragment.SEARCH_INDEX_DATA_PROVIDER;
                developmentSettingsDashboardFragment.updatePreferenceStates();
                break;
            default:
                DevelopmentSettingsDashboardFragment.this.finishFragment();
                break;
        }
    }
}
