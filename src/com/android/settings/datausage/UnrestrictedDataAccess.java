package com.android.settings.datausage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.applications.AppIconCacheManager;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecDividerItemDecorator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UnrestrictedDataAccess extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.unrestricted_data_access_settings);
    public boolean mShowSortSelected;
    public boolean mShowSystem;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "UnrestrictedDataAccess";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 7102;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.unrestricted_data_access_settings;
    }

    public final void initData(int i) {
        switch (i) {
            case 43:
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .setFilter(
                                this.mShowSystem
                                        ? ApplicationsState.FILTER_ALL_ENABLED
                                        : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER);
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .rebuild();
                LoggingHelper.insertEventLogging(7102, 7104);
                break;
            case 44:
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .setSort(this.mShowSortSelected);
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .rebuild();
                LoggingHelper.insertEventLogging(7102, 7105);
                break;
            case 45:
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .setFilter(
                                this.mShowSystem
                                        ? ApplicationsState.FILTER_ALL_ENABLED
                                        : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER);
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .setSort(this.mShowSortSelected);
                ((UnrestrictedDataAccessPreferenceController)
                                use(UnrestrictedDataAccessPreferenceController.class))
                        .rebuild();
                break;
            default:
                Log.e("UnrestrictedDataAccess", "initData: wrong mode code");
                break;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((UnrestrictedDataAccessPreferenceController)
                        use(UnrestrictedDataAccessPreferenceController.class))
                .setParentFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = false;
        this.mShowSystem = bundle != null && bundle.getBoolean("show_system");
        if (bundle != null && bundle.getBoolean("show_sorted")) {
            z = true;
        }
        this.mShowSortSelected = z;
        ((UnrestrictedDataAccessPreferenceController)
                        use(UnrestrictedDataAccessPreferenceController.class))
                .setFilter(
                        this.mShowSystem
                                ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                                : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER_NOT_QUIET);
        ((UnrestrictedDataAccessPreferenceController)
                        use(UnrestrictedDataAccessPreferenceController.class))
                .setSession(getSettingsLifecycle());
        boolean z2 = this.mShowSystem;
        if (z2 && this.mShowSortSelected) {
            initData(45);
        } else if (z2) {
            initData(43);
        } else if (this.mShowSortSelected) {
            initData(44);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(
                0,
                43,
                0,
                this.mShowSystem ? R.string.sec_menu_hide_system : R.string.sec_menu_show_system);
        menu.add(
                0,
                44,
                0,
                this.mShowSortSelected ? R.string.sort_order_alpha : R.string.menu_show_sorted);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        AppIconCacheManager.getInstance();
        AppIconCacheManager appIconCacheManager = AppIconCacheManager.sAppIconCacheManager;
        if (appIconCacheManager != null) {
            appIconCacheManager.mDrawableCache.evictAll();
            AppIconCacheManager.mCurCacheSize = 0;
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 43) {
            boolean z = !this.mShowSystem;
            this.mShowSystem = z;
            menuItem.setTitle(z ? R.string.menu_hide_system : R.string.menu_show_system);
            ((UnrestrictedDataAccessPreferenceController)
                            use(UnrestrictedDataAccessPreferenceController.class))
                    .setFilter(
                            this.mShowSystem
                                    ? ApplicationsState.FILTER_ENABLED_NOT_QUIET
                                    : ApplicationsState.FILTER_DOWNLOADED_AND_LAUNCHER_NOT_QUIET);
            ((UnrestrictedDataAccessPreferenceController)
                            use(UnrestrictedDataAccessPreferenceController.class))
                    .rebuild();
            return true;
        }
        if (menuItem.getItemId() == 44) {
            boolean z2 = !this.mShowSortSelected;
            this.mShowSortSelected = z2;
            menuItem.setTitle(z2 ? R.string.sort_order_alpha : R.string.menu_show_sorted);
            initData(44);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("show_system", this.mShowSystem);
        bundle.putBoolean("show_sorted", this.mShowSortSelected);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (getListView() != null) {
            getListView().seslSetGoToTopEnabled(true);
            setDivider(null);
            getListView()
                    .addItemDecoration(
                            new SecDividerItemDecorator(
                                    (int)
                                            (getResources()
                                                            .getDimension(
                                                                    R.dimen
                                                                            .sec_app_list_item_icon_min_width)
                                                    + getResources()
                                                            .getDimension(
                                                                    R.dimen
                                                                            .sec_widget_list_item_padding_start)),
                                    getContext(),
                                    getResources()
                                            .getDrawable(R.drawable.sec_top_level_list_divider)));
        }
        if (!Rune.SUPPORT_SMARTMANAGER_CN) {
            getPreferenceScreen().setTitle(R.string.unrestricted_data_saver);
        } else {
            getPreferenceScreen().setTitle(R.string.unrestricted_app_title_chn);
            getPreferenceScreen().getPreference(0).setVisible(false);
        }
    }
}
