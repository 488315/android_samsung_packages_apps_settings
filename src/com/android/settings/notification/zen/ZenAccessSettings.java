package com.android.settings.notification.zen;

import android.app.NotificationManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.applications.specialaccess.zenaccess.FriendlyWarningDialogFragment;
import com.android.settings.applications.specialaccess.zenaccess.ScaryWarningDialogFragment;
import com.android.settings.applications.specialaccess.zenaccess.ZenAccessController;
import com.android.settings.applications.specialaccess.zenaccess.ZenAccessSettingObserverMixin;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.EmptyTextSettings;
import com.android.settingslib.widget.AppSwitchPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenAccessSettings extends EmptyTextSettings
        implements ZenAccessSettingObserverMixin.Listener,
                SearchView.OnQueryTextListener,
                MenuItem.OnActionExpandListener {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.zen_access_settings);
    public ArrayList apps = new ArrayList();
    public boolean isSearch = false;
    public ArrayList mAppList;
    public ArrayList mAppTempList;
    public FragmentActivity mContext;
    public NotificationManager mNoMan;
    public PackageManager mPkgMan;
    public SearchView mSearchView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 180;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.zen_access_settings;
    }

    public final boolean isMatchedEntries(
            ApplicationInfo applicationInfo, CharSequence charSequence) {
        StringTokenizer stringTokenizer = new StringTokenizer(charSequence.toString(), " ");
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            String valueOf = String.valueOf(applicationInfo.loadLabel(this.mPkgMan));
            if (!TextUtils.isEmpty(valueOf)
                    && !valueOf.replaceAll("\u200b", ApnSettings.MVNO_NONE)
                            .toLowerCase()
                            .contains(nextToken.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        this.mPkgMan = activity.getPackageManager();
        this.mNoMan =
                (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        getSettingsLifecycle().addObserver(new ZenAccessSettingObserverMixin(getContext(), this));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        if (getActivity() == null) {
            return;
        }
        menuInflater.inflate(R.menu.zen_mode_list_menu, menu);
        MenuItem findItem = menu.findItem(R.id.zen_mode_list_search_menu);
        menu.removeItem(R.id.cancel);
        if (findItem != null) {
            findItem.setOnActionExpandListener(this);
            SearchView searchView = (SearchView) findItem.getActionView();
            this.mSearchView = searchView;
            if (searchView != null) {
                searchView.setQueryHint(getText(R.string.search_settings));
                SearchView searchView2 = this.mSearchView;
                searchView2.mOnQueryChangeListener = this;
                LinearLayout linearLayout =
                        (LinearLayout) searchView2.findViewById(R.id.search_plate);
                if (linearLayout != null) {
                    linearLayout.setPadding(
                            0, linearLayout.getPaddingTop(), 0, linearLayout.getPaddingBottom());
                }
                findItem.getIcon()
                        .setColorFilter(
                                getResources()
                                        .getColor(R.color.sec_search_magnifier_icon_tint_color),
                                PorterDuff.Mode.SRC_IN);
            }
        }
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.isSearch = false;
        reloadList();
        setEmptyText(R.string.zen_access_empty_text);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        ArrayList arrayList = new ArrayList();
        this.mAppTempList = arrayList;
        arrayList.addAll(this.apps);
        return true;
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final boolean onQueryTextChange(String str) {
        ArrayList arrayList;
        if (TextUtils.isEmpty(str)) {
            this.isSearch = false;
            arrayList = this.mAppTempList;
        } else {
            this.isSearch = true;
            ArrayList arrayList2 = new ArrayList();
            try {
                Iterator it = this.mAppList.iterator();
                while (it.hasNext()) {
                    ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
                    if (isMatchedEntries(applicationInfo, str)) {
                        arrayList2.add(applicationInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            arrayList = arrayList2;
        }
        this.mAppTempList = arrayList;
        reloadList();
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        reloadList();
    }

    @Override // com.android.settings.widget.EmptyTextSettings,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setEmptyText(R.string.zen_access_empty_text);
    }

    @Override // com.android.settings.applications.specialaccess.zenaccess.ZenAccessSettingObserverMixin.Listener
    public final void onZenAccessPolicyChanged() {
        reloadList();
    }

    public final void reloadList() {
        if (((UserManager) this.mContext.getSystemService(UserManager.class))
                .isManagedProfile(UserHandle.myUserId())) {
            Log.w("ZenAccessSettings", "DND access cannot be enabled in a work profile");
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        preferenceScreen.removeAll();
        if (this.isSearch) {
            ArrayList arrayList = this.mAppTempList;
            this.apps = arrayList;
            if (arrayList.isEmpty()) {
                setEmptyText(R.string.sec_app_search_no_result);
            }
        } else {
            this.apps.clear();
            Set<String> packagesRequestingNotificationPolicyAccess =
                    ZenAccessController.getPackagesRequestingNotificationPolicyAccess();
            if (!packagesRequestingNotificationPolicyAccess.isEmpty()) {
                List<ApplicationInfo> installedApplications =
                        this.mPkgMan.getInstalledApplications(0);
                if (installedApplications != null) {
                    for (ApplicationInfo applicationInfo : installedApplications) {
                        if (packagesRequestingNotificationPolicyAccess.contains(
                                applicationInfo.packageName)) {
                            this.apps.add(applicationInfo);
                        }
                    }
                }
                this.mAppList = this.apps;
            }
        }
        ArraySet arraySet = new ArraySet();
        arraySet.addAll(this.mNoMan.getEnabledNotificationListenerPackages());
        arraySet.addAll(ZenAccessController.getPackagesWithManageNotifications());
        Collections.sort(this.apps, new PackageItemInfo.DisplayNameComparator(this.mPkgMan));
        Iterator it = this.apps.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfo2 = (ApplicationInfo) it.next();
            final String str = applicationInfo2.packageName;
            final CharSequence loadLabel = applicationInfo2.loadLabel(this.mPkgMan);
            AppSwitchPreference appSwitchPreference = new AppSwitchPreference(getPrefContext());
            appSwitchPreference.setKey(str);
            appSwitchPreference.setPersistent();
            appSwitchPreference.setIcon(applicationInfo2.loadIcon(this.mPkgMan));
            appSwitchPreference.setTitle(loadLabel);
            if (arraySet.contains(str)) {
                appSwitchPreference.setEnabled(false);
                appSwitchPreference.setSummary(
                        getString(R.string.zen_access_disabled_package_warning));
            } else {
                appSwitchPreference.setSummary(
                        ZenAccessController.hasAccess(getContext(), str)
                                ? R.string.app_permission_summary_allowed
                                : R.string.app_permission_summary_not_allowed);
            }
            appSwitchPreference.setChecked(ZenAccessController.hasAccess(this.mContext, str));
            appSwitchPreference.setOnPreferenceChangeListener(
                    new Preference
                            .OnPreferenceChangeListener() { // from class:
                                                            // com.android.settings.notification.zen.ZenAccessSettings$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceChangeListener
                        public final boolean onPreferenceChange(Preference preference, Object obj) {
                            CharSequence charSequence = loadLabel;
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    ZenAccessSettings.SEARCH_INDEX_DATA_PROVIDER;
                            ZenAccessSettings zenAccessSettings = ZenAccessSettings.this;
                            zenAccessSettings.getClass();
                            boolean booleanValue = ((Boolean) obj).booleanValue();
                            String str2 = str;
                            if (booleanValue) {
                                ScaryWarningDialogFragment scaryWarningDialogFragment =
                                        new ScaryWarningDialogFragment();
                                scaryWarningDialogFragment.setPkgInfo$1(
                                        str2, charSequence, zenAccessSettings);
                                scaryWarningDialogFragment.show(
                                        zenAccessSettings.getFragmentManager(), "dialog");
                                return false;
                            }
                            FriendlyWarningDialogFragment friendlyWarningDialogFragment =
                                    new FriendlyWarningDialogFragment();
                            friendlyWarningDialogFragment.setPkgInfo(
                                    str2, charSequence, zenAccessSettings);
                            friendlyWarningDialogFragment.show(
                                    zenAccessSettings.getFragmentManager(), "dialog");
                            return false;
                        }
                    });
            preferenceScreen.addPreference(appSwitchPreference);
        }
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {}
}
