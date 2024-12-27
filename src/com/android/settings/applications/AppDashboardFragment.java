package com.android.settings.applications;

import android.content.Context;
import android.provider.SearchIndexableResource;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.applications.appcompat.UserAspectRatioAppsPreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.PreferenceCategoryController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppDashboardFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER = new AnonymousClass1();
    public AppsPreferenceController mAppsPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.AppDashboardFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return AppDashboardFragment.buildPreferenceControllers(context);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getXmlResourcesToIndex(Context context) {
            SearchIndexableResource searchIndexableResource = new SearchIndexableResource(context);
            searchIndexableResource.xmlResId = R.xml.apps;
            return Arrays.asList(searchIndexableResource);
        }
    }

    public static List buildPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new AppsPreferenceController(context));
        UserAspectRatioAppsPreferenceController userAspectRatioAppsPreferenceController =
                new UserAspectRatioAppsPreferenceController(context, "aspect_ratio_apps");
        AdvancedAppsPreferenceCategoryController advancedAppsPreferenceCategoryController =
                new AdvancedAppsPreferenceCategoryController(context, "advanced_category");
        advancedAppsPreferenceCategoryController.setChildren(
                List.of(userAspectRatioAppsPreferenceController));
        arrayList.add(advancedAppsPreferenceCategoryController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context);
    }

    @VisibleForTesting
    public PreferenceCategoryController getAdvancedAppsPreferenceCategoryController() {
        return (PreferenceCategoryController) use(AdvancedAppsPreferenceCategoryController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AppDashboardFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 65;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.apps;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        AppsPreferenceController appsPreferenceController =
                (AppsPreferenceController) use(AppsPreferenceController.class);
        this.mAppsPreferenceController = appsPreferenceController;
        appsPreferenceController.setFragment(this);
        getSettingsLifecycle().addObserver(this.mAppsPreferenceController);
        getSettingsLifecycle()
                .addObserver(
                        (HibernatedAppsPreferenceController)
                                use(HibernatedAppsPreferenceController.class));
    }
}
