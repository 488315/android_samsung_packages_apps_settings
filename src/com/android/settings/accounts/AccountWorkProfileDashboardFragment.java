package com.android.settings.accounts;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.users.AutoSyncDataPreferenceController;
import com.android.settings.users.AutoSyncWorkDataPreferenceController;

import com.samsung.android.settings.widget.SecDividerItemDecorator;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AccountWorkProfileDashboardFragment extends DashboardFragment {
    public AccountPreferenceController mAccountPreferenceController;

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        ArrayList arrayList = new ArrayList();
        AccountPreferenceController accountPreferenceController =
                new AccountPreferenceController(
                        context, this, getIntent().getStringArrayExtra("authorities"), 2);
        getSettingsLifecycle().addObserver(accountPreferenceController);
        arrayList.add(accountPreferenceController);
        AutoSyncDataPreferenceController autoSyncDataPreferenceController =
                new AutoSyncDataPreferenceController(context, this);
        getSettingsLifecycle().addObserver(autoSyncDataPreferenceController);
        arrayList.add(autoSyncDataPreferenceController);
        AutoSyncWorkDataPreferenceController autoSyncWorkDataPreferenceController =
                new AutoSyncWorkDataPreferenceController(context, this);
        getSettingsLifecycle().addObserver(autoSyncWorkDataPreferenceController);
        arrayList.add(autoSyncWorkDataPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "AccountWorkProfileFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 11;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.accounts_work_dashboard_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mAccountPreferenceController =
                (AccountPreferenceController) use(AccountPreferenceController.class);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updatePreferenceStates();
        this.mAccountPreferenceController.updateAddAccountButtonState();
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
