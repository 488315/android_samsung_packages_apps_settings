package com.samsung.android.settings.actions.development;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilitySettings$$ExternalSyntheticOutline0;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.settings.bixby.actionhandler.AODActionHandler;
import com.samsung.android.settings.bixby.actionhandler.BaseActionHandler;
import com.samsung.android.settings.bixby.actionhandler.EdgeActionHandler;
import com.samsung.android.settings.bixby.actionhandler.SettingsActionHandler;
import com.samsung.android.settings.bixby.actionhandler.SystemUIActionHandler;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SettingsBixbyFragment extends DashboardFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BaseActionHandler mActionHandler;
    public MenuItem mSearchMenu;

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.bixby_test;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList.add("Settings");
        arrayList.add("SystemUI");
        arrayList.add("AOD");
        arrayList.add("Edge");
        AccessibilitySettings$$ExternalSyntheticOutline0.m(
                arrayList2,
                "SettingsActionHandler",
                "SystemUIActionHandler",
                "AODActionHandler",
                "EdgeActionHandler");
        if (arrayList.size() > 0) {
            SecDropDownPreference secDropDownPreference =
                    (SecDropDownPreference) findPreference("feature_filter");
            secDropDownPreference.setSummary((CharSequence) arrayList.get(0));
            SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
            secDropDownPreference.setEntries(
                    (CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]));
            secDropDownPreference.mEntryValues =
                    (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]);
            secDropDownPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.actions.development.SettingsBixbyFragment.2
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            String str = (String) obj;
                            preference.setSummary(str);
                            int i = SettingsBixbyFragment.$r8$clinit;
                            SettingsBixbyFragment.this.replaceFeature(str);
                            return true;
                        }
                    });
        }
        replaceFeature("SettingsActionHandler");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.sec_action_dashboard_menu, menu);
        MenuItem findItem = menu.findItem(R.id.search);
        this.mSearchMenu = findItem;
        findItem.getIcon()
                .setColorFilter(
                        getResources().getColor(R.color.sec_search_magnifier_icon_tint_color),
                        PorterDuff.Mode.SRC_IN);
        SearchView searchView = (SearchView) this.mSearchMenu.getActionView();
        LinearLayout linearLayout = (LinearLayout) searchView.findViewById(android.R.id.sync);
        if (linearLayout != null) {
            linearLayout.setPadding(
                    0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
        }
        searchView.setQueryHint(getString(R.string.sec_search_bar_hint));
        searchView.mOnQueryChangeListener =
                new SearchView
                        .OnQueryTextListener() { // from class:
                                                 // com.samsung.android.settings.actions.development.SettingsBixbyFragment.1
                    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                    public final boolean onQueryTextChange(String str) {
                        int i = SettingsBixbyFragment.$r8$clinit;
                        PreferenceGroup preferenceGroup =
                                (PreferenceGroup)
                                        SettingsBixbyFragment.this
                                                .getPreferenceScreen()
                                                .findPreference("bixby_action_list");
                        for (int i2 = 0; i2 < preferenceGroup.getPreferenceCount(); i2++) {
                            preferenceGroup
                                    .getPreference(i2)
                                    .setVisible(
                                            !TextUtils.isEmpty(str)
                                                    ? ((String)
                                                                    preferenceGroup
                                                                            .getPreference(i2)
                                                                            .getTitle())
                                                            .toLowerCase()
                                                            .contains(str.toLowerCase())
                                                    : true);
                        }
                        return false;
                    }

                    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                    public final void onQueryTextSubmit(String str) {}
                };
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("feature_filter")) {
            this.mSearchMenu.collapseActionView();
            return false;
        }
        String str = (String) preference.getTitle();
        SettingsBixbyDialog settingsBixbyDialog = new SettingsBixbyDialog();
        Bundle bundle = new Bundle();
        bundle.putString(IMSParameter.CALL.ACTION, str);
        settingsBixbyDialog.setArguments(bundle);
        settingsBixbyDialog.mActionHandler = this.mActionHandler;
        settingsBixbyDialog.show(getFragmentManager(), "dialog");
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = this.mSearchMenu;
        if (menuItem != null) {
            menuItem.setVisible(true);
        }
    }

    public final void replaceFeature(String str) {
        String[] strArr;
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) getPreferenceScreen().findPreference("bixby_action_list");
        preferenceGroup.removeAll();
        str.getClass();
        switch (str) {
            case "AODActionHandler":
                this.mActionHandler = new AODActionHandler(getContext());
                strArr = AODActionHandler.ACTIONS;
                break;
            case "EdgeActionHandler":
                this.mActionHandler = new EdgeActionHandler(getContext());
                strArr = EdgeActionHandler.ACTIONS;
                break;
            case "SystemUIActionHandler":
                this.mActionHandler = new SystemUIActionHandler(getContext());
                strArr = SystemUIActionHandler.ACTIONS;
                break;
            case "SettingsActionHandler":
                this.mActionHandler = new SettingsActionHandler(getContext());
                strArr = SettingsActionHandler.ACTIONS;
                break;
            default:
                strArr = null;
                break;
        }
        for (String str2 : strArr) {
            Preference preference = new Preference(getContext());
            preference.setTitle(str2);
            preference.setKey(str2);
            preferenceGroup.addPreference(preference);
        }
    }
}
