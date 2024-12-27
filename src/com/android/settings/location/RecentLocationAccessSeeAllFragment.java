package com.android.settings.location;

import android.content.Context;
import android.os.Bundle;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.widget.SecDividerItemDecorator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RecentLocationAccessSeeAllFragment extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.location_recent_access_see_all);
    public RecentLocationAccessSeeAllPreferenceController mController;
    public MenuItem mHideSystemMenu;
    public boolean mShowSystem = false;
    public MenuItem mShowSystemMenu;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.location.RecentLocationAccessSeeAllFragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode", 0) != 0;
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "RecentLocAccessSeeAll";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1996;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.location_recent_access_see_all;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_location";
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final boolean isPreferenceAnimationAllowed() {
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        RecentLocationAccessSeeAllPreferenceController
                recentLocationAccessSeeAllPreferenceController =
                        (RecentLocationAccessSeeAllPreferenceController)
                                use(RecentLocationAccessSeeAllPreferenceController.class);
        this.mController = recentLocationAccessSeeAllPreferenceController;
        recentLocationAccessSeeAllPreferenceController.init(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        boolean z = false;
        if (DeviceConfig.getBoolean("privacy", "location_indicators_small_enabled", false)
                && Settings.Secure.getInt(getContentResolver(), "locationShowSystemOps", 0) == 1) {
            z = true;
        }
        this.mShowSystem = z;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        this.mShowSystemMenu = menu.add(0, 2, 0, R.string.sec_menu_show_system);
        this.mHideSystemMenu = menu.add(0, 3, 0, R.string.sec_menu_hide_system);
        this.mShowSystemMenu.setVisible(!this.mShowSystem);
        this.mHideSystemMenu.setVisible(this.mShowSystem);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != 2 && itemId != 3) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.mShowSystem = menuItem.getItemId() == 2;
        Settings.Secure.putInt(
                getContentResolver(), "locationShowSystemOps", this.mShowSystem ? 1 : 0);
        this.mShowSystemMenu.setVisible(!this.mShowSystem);
        this.mHideSystemMenu.setVisible(this.mShowSystem);
        RecentLocationAccessSeeAllPreferenceController
                recentLocationAccessSeeAllPreferenceController = this.mController;
        if (recentLocationAccessSeeAllPreferenceController != null) {
            recentLocationAccessSeeAllPreferenceController.setShowSystem(this.mShowSystem);
        }
        return true;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
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
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }
}
