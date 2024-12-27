package com.samsung.android.settings.actions.development;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActionDashboardFragment extends DashboardFragment
        implements LoaderManager.LoaderCallbacks {
    public static final /* synthetic */ int $r8$clinit = 0;
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
        return R.xml.sec_action_dashboard_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        CommandManager commandManager = CommandManager.getInstance(getContext());
        commandManager.getClass();
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> queryIntentContentProviders =
                commandManager
                        .mContext
                        .getPackageManager()
                        .queryIntentContentProviders(
                                new Intent("com.samsung.android.sdk.command.COMMAND_PROVIDER"), 0);
        if (queryIntentContentProviders != null) {
            Iterator<ResolveInfo> it = queryIntentContentProviders.iterator();
            while (it.hasNext()) {
                ProviderInfo providerInfo = it.next().providerInfo;
                arrayList.add(new Pair(providerInfo.authority, providerInfo.packageName));
            }
        }
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Pair pair = (Pair) it2.next();
            try {
                PackageManager packageManager = getContext().getPackageManager();
                String str = (String) pair.first;
                String str2 =
                        (String)
                                packageManager
                                        .getApplicationInfo((String) pair.second, 0)
                                        .loadLabel(packageManager);
                if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                    SemLog.d("ActionDashboardFragment", "skipped authority : " + str);
                } else {
                    arrayList2.add(str2);
                    arrayList3.add(str);
                }
            } catch (PackageManager.NameNotFoundException e) {
                SemLog.e("ActionDashboardFragment", "NameNotFoundException : " + e.getMessage());
            } catch (NullPointerException e2) {
                SemLog.e("ActionDashboardFragment", "NullPointerException : " + e2.getMessage());
            }
        }
        if (arrayList2.size() > 0) {
            SecDropDownPreference secDropDownPreference =
                    (SecDropDownPreference) findPreference("source_filter");
            secDropDownPreference.setSummary((CharSequence) arrayList3.get(0));
            SecPreferenceUtils.applySummaryColor(secDropDownPreference, true);
            secDropDownPreference.setEntries(
                    (CharSequence[]) arrayList2.toArray(new CharSequence[arrayList2.size()]));
            secDropDownPreference.mEntryValues =
                    (CharSequence[]) arrayList3.toArray(new CharSequence[arrayList3.size()]);
            secDropDownPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.samsung.android.settings.actions.development.ActionDashboardFragment.2
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            preference.setSummary((String) obj);
                            int i = ActionDashboardFragment.$r8$clinit;
                            ActionDashboardFragment actionDashboardFragment =
                                    ActionDashboardFragment.this;
                            actionDashboardFragment
                                    .getLoaderManager()
                                    .restartLoader(0, null, actionDashboardFragment)
                                    .onForceLoad();
                            return true;
                        }
                    });
        }
        getLoaderManager().restartLoader(0, null, this).onForceLoad();
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final Loader onCreateLoader(int i, Bundle bundle) {
        return new ActionLoader(
                getContext(), (String) findPreference("source_filter").getSummary());
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
                                                 // com.samsung.android.settings.actions.development.ActionDashboardFragment.1
                    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                    public final boolean onQueryTextChange(String str) {
                        boolean z;
                        int i = ActionDashboardFragment.$r8$clinit;
                        PreferenceGroup preferenceGroup =
                                (PreferenceGroup)
                                        ActionDashboardFragment.this
                                                .getPreferenceScreen()
                                                .findPreference("action_list_category");
                        for (int i2 = 0; i2 < preferenceGroup.getPreferenceCount(); i2++) {
                            if (!TextUtils.isEmpty(str)) {
                                String str2 = (String) preferenceGroup.getPreference(i2).getTitle();
                                String str3 =
                                        (String) preferenceGroup.getPreference(i2).getSummary();
                                if (!str2.toLowerCase().contains(str.toLowerCase())
                                        && !str3.toLowerCase().contains(str.toLowerCase())) {
                                    z = false;
                                    preferenceGroup.getPreference(i2).setVisible(z);
                                }
                            }
                            z = true;
                            preferenceGroup.getPreference(i2).setVisible(z);
                        }
                        return false;
                    }

                    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
                    public final void onQueryTextSubmit(String str) {}
                };
    }

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoadFinished(Loader loader, Object obj) {
        Map map = (Map) obj;
        PreferenceGroup preferenceGroup =
                (PreferenceGroup) getPreferenceScreen().findPreference("action_list_category");
        preferenceGroup.removeAll();
        for (String str : map.keySet()) {
            Preference preference = new Preference(getContext());
            preference.setTitle((CharSequence) map.get(str));
            preference.setSummary(Uri.parse(str).getLastPathSegment());
            preference.setKey(str);
            preferenceGroup.addPreference(preference);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("source_filter")) {
            this.mSearchMenu.collapseActionView();
            return false;
        }
        String key = preference.getKey();
        ActionControlDialog actionControlDialog = new ActionControlDialog();
        Bundle bundle = new Bundle();
        bundle.putString("deeplink", key);
        actionControlDialog.setArguments(bundle);
        actionControlDialog.show(getFragmentManager(), "dialog");
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

    @Override // androidx.loader.app.LoaderManager.LoaderCallbacks
    public final void onLoaderReset(Loader loader) {}
}
