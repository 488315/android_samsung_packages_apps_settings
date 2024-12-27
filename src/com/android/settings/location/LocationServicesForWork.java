package com.android.settings.location;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.widget.SecDividerItemDecorator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocationServicesForWork extends DashboardFragment {
    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LocationServicesForWork";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1994;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.location_services_workprofile;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((LocationInjectedServicesForWorkPreferenceController)
                        use(LocationInjectedServicesForWorkPreferenceController.class))
                .init(this);
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
