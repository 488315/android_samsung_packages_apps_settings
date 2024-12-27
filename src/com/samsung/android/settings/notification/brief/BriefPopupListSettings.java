package com.samsung.android.settings.notification.brief;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.window.OnBackInvokedCallback;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager;
import com.samsung.android.settings.notification.brief.widget.BriefAppInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BriefPopupListSettings extends DashboardFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    public BriefPopupAppItemPreference mAllSwitchPreference;
    public String mAppAppsPreferenceTitle;
    public AppBarLayout mAppBarLayout;
    public ArrayList mAppList;
    public PreferenceCategory mAppListPref;
    public ArrayList mAppTempList;
    public NotificationBackend mBackend;
    public int mInstalledAppCountInWhiteList;
    public boolean mIsDirty;
    public boolean mIsToggledByAllSwitch;
    public Menu mOptionsMenu;
    public SearchView mSearchView;
    public final ArrayList mItemPrefList = new ArrayList();
    public boolean mAllAppsEnabled = true;
    public boolean isSearch = false;
    public final AnonymousClass2 mAppNameComparator =
            new Comparator() { // from class:
                               // com.samsung.android.settings.notification.brief.BriefPopupListSettings.2
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    BriefAppInfo briefAppInfo = (BriefAppInfo) obj;
                    BriefAppInfo briefAppInfo2 = (BriefAppInfo) obj2;
                    try {
                        int i = briefAppInfo.priority;
                        int i2 = briefAppInfo2.priority;
                        return i == i2
                                ? this.collator.compare(briefAppInfo.appName, briefAppInfo2.appName)
                                : i2 - i;
                    } catch (NullPointerException e) {
                        Log.e("BriefPopupListSettings", "Failed to compare AppInfo. " + e);
                        return 0;
                    }
                }
            };
    public final AnonymousClass3 mPreferenceChange =
            new Preference
                    .OnPreferenceChangeListener() { // from class:
                                                    // com.samsung.android.settings.notification.brief.BriefPopupListSettings.3
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    BriefPopupAppItemPreference briefPopupAppItemPreference =
                            (BriefPopupAppItemPreference) preference;
                    boolean equals =
                            "AllAppsBtn"
                                    .equals(briefPopupAppItemPreference.mBriefAppInfo.packageName);
                    BriefPopupListSettings briefPopupListSettings = BriefPopupListSettings.this;
                    if (equals) {
                        briefPopupListSettings.mIsToggledByAllSwitch = true;
                    }
                    briefPopupAppItemPreference.mBriefAppInfo.isSelected = booleanValue;
                    Log.d(
                            "BriefPopupListSettings",
                            "app: "
                                    + briefPopupAppItemPreference.mBriefAppInfo.appName
                                    + ", isChecked :"
                                    + booleanValue);
                    briefPopupListSettings.mIsDirty = true;
                    HashMap hashMap = new HashMap();
                    StringBuilder sb = new StringBuilder();
                    sb.append(briefPopupAppItemPreference.mBriefAppInfo.packageName);
                    sb.append(" : ");
                    sb.append(booleanValue ? "on" : "off");
                    hashMap.put("NSTE0030", sb.toString());
                    boolean z = false;
                    SALogging.insertSALog(Integer.toString(36012), "NSTE0030", hashMap, 0);
                    if (briefPopupListSettings.mIsToggledByAllSwitch) {
                        briefPopupListSettings.mAllAppsEnabled = booleanValue;
                        Iterator it = briefPopupListSettings.mItemPrefList.iterator();
                        while (it.hasNext()) {
                            BriefPopupAppItemPreference briefPopupAppItemPreference2 =
                                    (BriefPopupAppItemPreference) it.next();
                            briefPopupAppItemPreference2.setChecked(booleanValue);
                            briefPopupAppItemPreference2.mBriefAppInfo.isSelected = booleanValue;
                        }
                        briefPopupListSettings.mIsToggledByAllSwitch = false;
                    } else {
                        Iterator it2 = briefPopupListSettings.mItemPrefList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = true;
                                break;
                            }
                            if (!((BriefPopupAppItemPreference) it2.next())
                                    .mBriefAppInfo
                                    .isSelected) {
                                break;
                            }
                        }
                        briefPopupListSettings.mAllSwitchPreference.setChecked(z);
                        briefPopupListSettings.mAllAppsEnabled = z;
                    }
                    briefPopupListSettings.updateEdgeLightingList();
                    return true;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GetAppListAsyncTask extends AsyncTask {
        public GetAppListAsyncTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            final BriefPopupListSettings briefPopupListSettings = BriefPopupListSettings.this;
            final int[] iArr = {0};
            PackageManager packageManager =
                    briefPopupListSettings.getActivity().getPackageManager();
            Context context = briefPopupListSettings.getContext();
            BriefPopupPolicyManager briefPopupPolicyManager =
                    BriefPopupPolicyManager.getInstance(context);
            final HashMap hashMap =
                    (briefPopupPolicyManager.mPolicyType & 4) != 0
                            ? (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(2)
                            : null;
            final HashMap hashMap2 = (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(10);
            final ArrayList arrayList = new ArrayList();
            NotificationBackend notificationBackend = briefPopupListSettings.mBackend;
            int userId = context.getUserId();
            notificationBackend.getClass();
            List notificationPackagesList =
                    NotificationBackend.getNotificationPackagesList(context, userId);
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
            ArrayList arrayList2 = new ArrayList();
            Iterator it = ((ArrayList) notificationPackagesList).iterator();
            while (it.hasNext()) {
                final ResolveInfo resolveInfo = (ResolveInfo) it.next();
                final PackageManager packageManager2 = packageManager;
                PackageManager packageManager3 = packageManager;
                ArrayList arrayList3 = arrayList2;
                arrayList3.add(
                        new Callable() { // from class:
                            // com.samsung.android.settings.notification.brief.BriefPopupListSettings.4
                            /* JADX WARN: Removed duplicated region for block: B:31:0x00ba  */
                            /* JADX WARN: Removed duplicated region for block: B:33:0x00bd  */
                            @Override // java.util.concurrent.Callable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object call() {
                                /*
                                    r10 = this;
                                    android.content.pm.ResolveInfo r0 = r2
                                    android.content.pm.ActivityInfo r0 = r0.activityInfo
                                    java.lang.String r1 = r0.name
                                    java.lang.String r4 = r0.packageName
                                    java.util.HashMap r0 = r3
                                    r8 = 0
                                    if (r0 == 0) goto L15
                                    boolean r0 = r0.containsKey(r4)
                                    if (r0 == 0) goto L15
                                    goto Ldb
                                L15:
                                    android.content.ComponentName r0 = new android.content.ComponentName
                                    r0.<init>(r4, r1)
                                    java.lang.String r1 = r0.flattenToString()
                                    if (r1 == 0) goto L24
                                    android.content.ComponentName r0 = android.content.ComponentName.unflattenFromString(r1)
                                L24:
                                    r1 = 1
                                    r2 = 0
                                    android.content.pm.PackageManager r3 = r4     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5c
                                    android.content.pm.ActivityInfo r3 = r3.getActivityInfo(r0, r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5c
                                    android.content.pm.PackageManager r5 = r4     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5c
                                    java.lang.CharSequence r3 = r3.loadLabel(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5c
                                    java.lang.String r3 = r3.toString()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5c
                                    android.content.pm.PackageManager r5 = r4     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                    java.lang.String r6 = r0.getClassName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                    android.graphics.drawable.Drawable r6 = r5.semGetCscPackageItemIcon(r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                    if (r6 == 0) goto L43
                                    goto L58
                                L43:
                                    java.lang.String r6 = r0.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                    android.graphics.drawable.Drawable r6 = r5.semGetCscPackageItemIcon(r6)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                    if (r6 == 0) goto L4e
                                    goto L58
                                L4e:
                                    android.graphics.drawable.Drawable r0 = r5.semGetActivityIconForIconTray(r0, r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L54
                                    r6 = r0
                                    goto L58
                                L54:
                                    r0 = move-exception
                                    r0.printStackTrace()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5a
                                L58:
                                    r5 = r6
                                    goto L62
                                L5a:
                                    r0 = move-exception
                                    goto L5e
                                L5c:
                                    r0 = move-exception
                                    r3 = r8
                                L5e:
                                    r0.printStackTrace()
                                    r5 = r8
                                L62:
                                    java.lang.String r0 = "BriefPopupListSettings"
                                    if (r3 == 0) goto Ld6
                                    if (r5 == 0) goto Ld6
                                    if (r4 == 0) goto Ld6
                                    java.util.HashMap r6 = r5
                                    if (r6 == 0) goto L75
                                    java.lang.Object r6 = r6.get(r4)
                                    com.samsung.android.settings.notification.brief.policy.PolicyInfo r6 = (com.samsung.android.settings.notification.brief.policy.PolicyInfo) r6
                                    goto L76
                                L75:
                                    r6 = r8
                                L76:
                                    if (r6 == 0) goto L83
                                    int[] r7 = r6
                                    r9 = r7[r2]
                                    int r9 = r9 + r1
                                    r7[r2] = r9
                                    int r1 = r6.priority
                                    r6 = r1
                                    goto L84
                                L83:
                                    r6 = r2
                                L84:
                                    com.samsung.android.settings.notification.brief.BriefPopupListSettings r1 = com.samsung.android.settings.notification.brief.BriefPopupListSettings.this
                                    r1.getClass()
                                    java.lang.String r7 = "notification"
                                    android.os.IBinder r7 = android.os.ServiceManager.getService(r7)
                                    android.app.INotificationManager r7 = android.app.INotificationManager.Stub.asInterface(r7)
                                    android.content.pm.PackageManager r9 = r1.getPackageManager()
                                    int r9 = r9.getPackageUid(r4, r2)     // Catch: java.lang.Exception -> La1 android.content.pm.PackageManager.NameNotFoundException -> Lae
                                    boolean r7 = r7.isEdgeLightingAllowed(r4, r9)     // Catch: java.lang.Exception -> La1 android.content.pm.PackageManager.NameNotFoundException -> Lae
                                    goto Lb8
                                La1:
                                    r7 = move-exception
                                    java.lang.StackTraceElement[] r7 = r7.getStackTrace()
                                    java.lang.String r7 = r7.toString()
                                    android.util.Log.e(r0, r7)
                                    goto Lb7
                                Lae:
                                    java.lang.String r7 = "NameNotFound - "
                                    java.lang.String r7 = r7.concat(r4)
                                    android.util.Log.e(r0, r7)
                                Lb7:
                                    r7 = r2
                                Lb8:
                                    if (r7 != 0) goto Lbd
                                    r1.mAllAppsEnabled = r2
                                    goto Lca
                                Lbd:
                                    java.lang.String r1 = "Enabled : appName = "
                                    java.lang.String r2 = ", appPackageName ="
                                    java.lang.String r9 = " priority : "
                                    java.lang.StringBuilder r1 = androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0.m(r1, r3, r2, r4, r9)
                                    androidx.preference.Preference$$ExternalSyntheticOutline0.m(r1, r6, r0)
                                Lca:
                                    java.util.ArrayList r10 = r7
                                    com.samsung.android.settings.notification.brief.widget.BriefAppInfo r0 = new com.samsung.android.settings.notification.brief.widget.BriefAppInfo
                                    r2 = r0
                                    r2.<init>(r3, r4, r5, r6, r7)
                                    r10.add(r0)
                                    goto Ldb
                                Ld6:
                                    java.lang.String r10 = "Error..."
                                    android.util.Log.e(r0, r10)
                                Ldb:
                                    return r8
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.samsung.android.settings.notification.brief.BriefPopupListSettings.AnonymousClass4.call():java.lang.Object");
                            }
                        });
                arrayList2 = arrayList3;
                packageManager = packageManager3;
            }
            try {
                newFixedThreadPool.invokeAll(arrayList2);
                newFixedThreadPool.shutdown();
                newFixedThreadPool.awaitTermination(3L, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            briefPopupListSettings.mInstalledAppCountInWhiteList = iArr[0];
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ArrayList arrayList = (ArrayList) obj;
            super.onPostExecute(arrayList);
            if (arrayList != null) {
                BriefPopupListSettings.this.fillInAppList(arrayList);
            }
        }
    }

    public final void fillInAppList(ArrayList arrayList) {
        this.mAppList = arrayList;
        Collections.sort(arrayList, this.mAppNameComparator);
        if (!this.isSearch) {
            this.mAppTempList = this.mAppList;
        }
        ArrayList arrayList2 = this.mAppList;
        boolean z = this.mAllAppsEnabled;
        int i = this.mInstalledAppCountInWhiteList;
        this.mAppListPref.removeAll();
        this.mItemPrefList.clear();
        Context context = getContext();
        if (context == null && (context = getActivity()) == null) {
            return;
        }
        Context context2 = context;
        if (!this.isSearch) {
            BriefPopupAppItemPreference briefPopupAppItemPreference =
                    new BriefPopupAppItemPreference(context2, 0);
            this.mAllSwitchPreference = briefPopupAppItemPreference;
            briefPopupAppItemPreference.mBriefAppInfo =
                    new BriefAppInfo(
                            getString(R.string.brief_popup_all_apps_btn_text),
                            "AllAppsBtn",
                            null,
                            0,
                            z);
            BriefPopupAppItemPreference briefPopupAppItemPreference2 = this.mAllSwitchPreference;
            briefPopupAppItemPreference2.mAllowDrawerDivider = true;
            briefPopupAppItemPreference2.mAllowLineDivider = false;
            briefPopupAppItemPreference2.setTitle(this.mAppAppsPreferenceTitle);
            this.mAllSwitchPreference.setChecked(z);
            this.mAllSwitchPreference.setOnPreferenceChangeListener(this.mPreferenceChange);
            this.mAppListPref.addPreference(this.mAllSwitchPreference);
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            BriefAppInfo briefAppInfo = (BriefAppInfo) arrayList2.get(i2);
            if (briefAppInfo == null) {
                Log.e("BriefPopupListSettings", "updateAppList item is null..");
            } else {
                BriefPopupAppItemPreference briefPopupAppItemPreference3 =
                        new BriefPopupAppItemPreference(context2);
                if (i2 == i - 1) {
                    briefPopupAppItemPreference3.mAllowDrawerDivider = true;
                    briefPopupAppItemPreference3.mAllowLineDivider = false;
                } else if (i2 == arrayList2.size() - 1) {
                    briefPopupAppItemPreference3.mAllowLineDivider = false;
                }
                briefPopupAppItemPreference3.setTitle(briefAppInfo.appName);
                briefPopupAppItemPreference3.mBriefAppInfo = briefAppInfo;
                briefPopupAppItemPreference3.setChecked(briefAppInfo.isSelected);
                briefPopupAppItemPreference3.setOnPreferenceChangeListener(this.mPreferenceChange);
                this.mAppListPref.addPreference(briefPopupAppItemPreference3);
                this.mItemPrefList.add(briefPopupAppItemPreference3);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "BriefPopupListSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.brief_popup_list_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (getListView() != null) {
            getListView().seslSetGoToTopEnabled(true);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIsDirty = false;
        this.mInstalledAppCountInWhiteList = 0;
        this.mAppListPref =
                (PreferenceCategory) findPreference("brief_popup_list_settings_category");
        this.mAppAppsPreferenceTitle = getString(R.string.brief_popup_all_apps_btn_text);
        this.mBackend = new NotificationBackend();
        new GetAppListAsyncTask().execute(new Void[0]);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar);
        if (toolbar != null) {
            toolbar.setBackInvokedCallbackEnabled(false);
        }
        getActivity()
                .getOnBackInvokedDispatcher()
                .registerSystemOnBackInvokedCallback(
                        new OnBackInvokedCallback() { // from class:
                                                      // com.samsung.android.settings.notification.brief.BriefPopupListSettings.1
                            @Override // android.window.OnBackInvokedCallback
                            public final void onBackInvoked() {
                                MenuItem findItem;
                                BriefPopupListSettings briefPopupListSettings =
                                        BriefPopupListSettings.this;
                                if (!briefPopupListSettings.isSearch) {
                                    briefPopupListSettings.getActivity().finish();
                                    return;
                                }
                                Menu menu = briefPopupListSettings.mOptionsMenu;
                                if (menu == null
                                        || (findItem = menu.findItem(R.id.search_app_list_menu))
                                                == null) {
                                    return;
                                }
                                findItem.collapseActionView();
                                BriefPopupListSettings.this.isSearch = false;
                            }
                        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.mOptionsMenu = menu;
        super.onCreateOptionsMenu(menu, menuInflater);
        if (getActivity() == null) {
            return;
        }
        this.mAppBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        menuInflater.inflate(R.menu.brief_pop_up_list_menu, menu);
        MenuItem findItem = menu.findItem(R.id.search_app_list_menu);
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

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mAppListPref.removeAll();
        this.mItemPrefList.clear();
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        this.isSearch = false;
        fillInAppList(this.mAppTempList);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        this.isSearch = true;
        fillInAppList(this.mAppTempList);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        if (this.mIsDirty) {
            updateEdgeLightingList();
        }
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final boolean onQueryTextChange(String str) {
        ArrayList arrayList;
        if (TextUtils.isEmpty(str)) {
            arrayList = this.mAppTempList;
        } else {
            this.isSearch = true;
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = this.mAppTempList;
            if (arrayList3 != null) {
                Iterator it = arrayList3.iterator();
                while (it.hasNext()) {
                    BriefAppInfo briefAppInfo = (BriefAppInfo) it.next();
                    if (briefAppInfo != null) {
                        StringTokenizer stringTokenizer = new StringTokenizer(str.toString(), " ");
                        while (true) {
                            if (!stringTokenizer.hasMoreTokens()) {
                                arrayList2.add(briefAppInfo);
                                break;
                            }
                            String nextToken = stringTokenizer.nextToken();
                            String str2 = briefAppInfo.appName;
                            if (!TextUtils.isEmpty(str2)
                                    && !str2.replaceAll("\u200b", ApnSettings.MVNO_NONE)
                                            .toLowerCase()
                                            .contains(nextToken.toLowerCase())) {
                                break;
                            }
                        }
                    }
                }
            }
            arrayList = arrayList2;
        }
        fillInAppList(arrayList);
        return false;
    }

    public final synchronized void updateEdgeLightingList() {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = getContext().getPackageManager();
        for (int i = 0; i < this.mItemPrefList.size(); i++) {
            BriefPopupAppItemPreference briefPopupAppItemPreference =
                    (BriefPopupAppItemPreference) this.mItemPrefList.get(i);
            BriefAppInfo briefAppInfo = briefPopupAppItemPreference.mBriefAppInfo;
            if (briefAppInfo.isSelected) {
                arrayList.add(briefAppInfo.packageName);
                Log.d(
                        "BriefPopupListSettings",
                        "Selected app "
                                + briefPopupAppItemPreference.mBriefAppInfo.packageName
                                + " will be stored");
            }
            try {
                NotificationBackend notificationBackend = this.mBackend;
                String str = briefPopupAppItemPreference.mBriefAppInfo.packageName;
                int packageUid = packageManager.getPackageUid(str, 0);
                boolean z = briefPopupAppItemPreference.mBriefAppInfo.isSelected;
                notificationBackend.getClass();
                try {
                    NotificationBackend.sINM.setAllowEdgeLighting(str, packageUid, z);
                } catch (Exception e) {
                    Log.w("NotificationBackend", "Error calling NoMan", e);
                }
            } catch (Exception unused) {
            }
        }
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {}
}
