package com.samsung.android.settings.lockscreen;

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

import androidx.appcompat.widget.SearchView;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.NotificationBackend;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.notification.brief.policy.BriefPopupPolicyManager;
import com.samsung.android.settings.notification.reminder.NotificationUtils;

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
public class LockScreenNotificationShowContentListSettings extends DashboardFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass4();
    public ShowContentListItemPreference mAllSwitchPreference;
    public String mAppAppsPreferenceTitle;
    public AppBarLayout mAppBarLayout;
    public ArrayList mAppList;
    public PreferenceCategory mAppListPref;
    public ArrayList mAppTempList;
    public NotificationBackend mBackend;
    public int mInstalledAppCountInWhiteList;
    public boolean mIsDirty;
    public boolean mIsToggledByAllSwitch;
    public SearchView mSearchView;
    public final AnonymousClass1 mAppNameComparator =
            new Comparator() { // from class:
                               // com.samsung.android.settings.lockscreen.LockScreenNotificationShowContentListSettings.1
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    ShowContentAppInfo showContentAppInfo = (ShowContentAppInfo) obj;
                    ShowContentAppInfo showContentAppInfo2 = (ShowContentAppInfo) obj2;
                    try {
                        int i = showContentAppInfo.priority;
                        int i2 = showContentAppInfo2.priority;
                        return i == i2
                                ? this.collator.compare(
                                        showContentAppInfo.appName, showContentAppInfo2.appName)
                                : i2 - i;
                    } catch (NullPointerException e) {
                        Log.e(
                                "LockScreenNotificationShowContentListSettings",
                                "Failed to compare AppInfo. " + e);
                        return 0;
                    }
                }
            };
    public final ArrayList mItemPrefList = new ArrayList();
    public boolean mAllAppsEnabled = true;
    public final AnonymousClass2 mPreferenceChange =
            new Preference
                    .OnPreferenceChangeListener() { // from class:
                                                    // com.samsung.android.settings.lockscreen.LockScreenNotificationShowContentListSettings.2
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    ShowContentListItemPreference showContentListItemPreference =
                            (ShowContentListItemPreference) preference;
                    String str = showContentListItemPreference.mShowContentAppInfo.packageName;
                    LockScreenNotificationShowContentListSettings
                            lockScreenNotificationShowContentListSettings =
                                    LockScreenNotificationShowContentListSettings.this;
                    lockScreenNotificationShowContentListSettings.getClass();
                    boolean z = false;
                    SALogging.insertSALog(String.valueOf(0), "A4452", str);
                    if ("AllAppsBtn"
                            .equals(
                                    showContentListItemPreference
                                            .mShowContentAppInfo
                                            .packageName)) {
                        lockScreenNotificationShowContentListSettings.mIsToggledByAllSwitch = true;
                    }
                    showContentListItemPreference.mShowContentAppInfo.isSelected = booleanValue;
                    Log.d(
                            "LockScreenNotificationShowContentListSettings",
                            "app: "
                                    + showContentListItemPreference.mShowContentAppInfo.appName
                                    + ", isChecked :"
                                    + booleanValue);
                    lockScreenNotificationShowContentListSettings.mIsDirty = true;
                    if (lockScreenNotificationShowContentListSettings.mIsToggledByAllSwitch) {
                        lockScreenNotificationShowContentListSettings.mAllAppsEnabled =
                                booleanValue;
                        Iterator it =
                                lockScreenNotificationShowContentListSettings.mItemPrefList
                                        .iterator();
                        while (it.hasNext()) {
                            ShowContentListItemPreference showContentListItemPreference2 =
                                    (ShowContentListItemPreference) it.next();
                            showContentListItemPreference2.setChecked(booleanValue);
                            showContentListItemPreference2.mShowContentAppInfo.isSelected =
                                    booleanValue;
                        }
                        lockScreenNotificationShowContentListSettings.mIsToggledByAllSwitch = false;
                    } else {
                        Iterator it2 =
                                lockScreenNotificationShowContentListSettings.mItemPrefList
                                        .iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = true;
                                break;
                            }
                            if (!((ShowContentListItemPreference) it2.next())
                                    .mShowContentAppInfo
                                    .isSelected) {
                                break;
                            }
                        }
                        lockScreenNotificationShowContentListSettings.mAllSwitchPreference
                                .setChecked(z);
                        lockScreenNotificationShowContentListSettings.mAllAppsEnabled = z;
                    }
                    lockScreenNotificationShowContentListSettings
                            .updateLockScreenNotificationVisibility();
                    return true;
                }
            };
    public boolean isSearch = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.lockscreen.LockScreenNotificationShowContentListSettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            int size = NotificationUtils.getAppListForShowContent(context).size();
            ArrayList arrayList2 = new ArrayList();
            ArrayList appListForShowContent = NotificationUtils.getAppListForShowContent(context);
            if (appListForShowContent.size() > 0) {
                Iterator it = appListForShowContent.iterator();
                while (it.hasNext()) {
                    ShowContentAppInfo showContentAppInfo = (ShowContentAppInfo) it.next();
                    if (showContentAppInfo != null && showContentAppInfo.isSelected) {
                        arrayList2.add(showContentAppInfo.packageName);
                    }
                }
            }
            int size2 = arrayList2.size();
            String valueOf = size == size2 ? "All" : String.valueOf(size2);
            String valueOf2 = String.valueOf(36116);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf;
            statusData.mStatusKey = valueOf2;
            arrayList.add(statusData);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GetAppListAsyncTask extends AsyncTask {
        public GetAppListAsyncTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            final LockScreenNotificationShowContentListSettings
                    lockScreenNotificationShowContentListSettings =
                            LockScreenNotificationShowContentListSettings.this;
            final int[] iArr = {0};
            PackageManager packageManager =
                    lockScreenNotificationShowContentListSettings.getContext().getPackageManager();
            Context context = lockScreenNotificationShowContentListSettings.getContext();
            BriefPopupPolicyManager briefPopupPolicyManager =
                    BriefPopupPolicyManager.getInstance(context);
            final HashMap hashMap =
                    (briefPopupPolicyManager.mPolicyType & 4) != 0
                            ? (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(2)
                            : null;
            final HashMap hashMap2 = (HashMap) briefPopupPolicyManager.mPolicyInfoData.get(10);
            final ArrayList arrayList = new ArrayList();
            NotificationBackend notificationBackend =
                    lockScreenNotificationShowContentListSettings.mBackend;
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
                            // com.samsung.android.settings.lockscreen.LockScreenNotificationShowContentListSettings.3
                            /* JADX WARN: Removed duplicated region for block: B:37:0x00c2  */
                            /* JADX WARN: Removed duplicated region for block: B:39:0x00c5  */
                            /* JADX WARN: Removed duplicated region for block: B:49:0x00e7  */
                            /* JADX WARN: Removed duplicated region for block: B:51:0x00ee  */
                            /* JADX WARN: Removed duplicated region for block: B:53:0x00f5  */
                            @Override // java.util.concurrent.Callable
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object call() {
                                /*
                                    Method dump skipped, instructions count: 259
                                    To view this dump change 'Code comments level' option to 'DEBUG'
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.samsung.android.settings.lockscreen.LockScreenNotificationShowContentListSettings.AnonymousClass3.call():java.lang.Object");
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
            lockScreenNotificationShowContentListSettings.mInstalledAppCountInWhiteList = iArr[0];
            return arrayList;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ArrayList arrayList = (ArrayList) obj;
            super.onPostExecute(arrayList);
            if (arrayList != null) {
                LockScreenNotificationShowContentListSettings.this.fillInAppList(arrayList);
            }
        }
    }

    public final void fillInAppList(ArrayList arrayList) {
        if (arrayList == null) {
            return;
        }
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
            ShowContentListItemPreference showContentListItemPreference =
                    new ShowContentListItemPreference(context2, 0);
            this.mAllSwitchPreference = showContentListItemPreference;
            showContentListItemPreference.mShowContentAppInfo =
                    new ShowContentAppInfo(
                            getString(
                                    R.string
                                            .sec_lockscreen_notifications_show_content_all_apps_text),
                            "AllAppsBtn",
                            null,
                            0,
                            z);
            ShowContentListItemPreference showContentListItemPreference2 =
                    this.mAllSwitchPreference;
            showContentListItemPreference2.mAllowDrawerDivider = true;
            showContentListItemPreference2.mAllowLineDivider = false;
            showContentListItemPreference2.setTitle(this.mAppAppsPreferenceTitle);
            this.mAllSwitchPreference.setChecked(z);
            this.mAllSwitchPreference.setOnPreferenceChangeListener(this.mPreferenceChange);
            this.mAppListPref.addPreference(this.mAllSwitchPreference);
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            ShowContentAppInfo showContentAppInfo = (ShowContentAppInfo) arrayList2.get(i2);
            if (showContentAppInfo == null) {
                Log.e(
                        "LockScreenNotificationShowContentListSettings",
                        "updateAppList item is null..");
            } else {
                ShowContentListItemPreference showContentListItemPreference3 =
                        new ShowContentListItemPreference(context2);
                if (i2 == i - 1) {
                    showContentListItemPreference3.mAllowDrawerDivider = true;
                    showContentListItemPreference3.mAllowLineDivider = false;
                } else if (i2 == arrayList2.size() - 1) {
                    showContentListItemPreference3.mAllowLineDivider = false;
                }
                showContentListItemPreference3.setTitle(showContentAppInfo.appName);
                showContentListItemPreference3.mShowContentAppInfo = showContentAppInfo;
                showContentListItemPreference3.setChecked(showContentAppInfo.isSelected);
                showContentListItemPreference3.setOnPreferenceChangeListener(
                        this.mPreferenceChange);
                this.mAppListPref.addPreference(showContentListItemPreference3);
                this.mItemPrefList.add(showContentListItemPreference3);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "LockScreenNotificationShowContentListSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_lock_screen_notification_show_content_list;
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
                (PreferenceCategory) findPreference("apps_that_can_show_content_category");
        this.mAppAppsPreferenceTitle =
                getString(R.string.sec_lockscreen_notifications_show_content_all_apps_text);
        this.mBackend = new NotificationBackend();
        new GetAppListAsyncTask().execute(new Void[0]);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
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
            updateLockScreenNotificationVisibility();
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
            Iterator it = this.mAppTempList.iterator();
            while (it.hasNext()) {
                ShowContentAppInfo showContentAppInfo = (ShowContentAppInfo) it.next();
                if (showContentAppInfo != null) {
                    StringTokenizer stringTokenizer = new StringTokenizer(str.toString(), " ");
                    while (true) {
                        if (!stringTokenizer.hasMoreTokens()) {
                            arrayList2.add(showContentAppInfo);
                            break;
                        }
                        String nextToken = stringTokenizer.nextToken();
                        String str2 = showContentAppInfo.appName;
                        if (!TextUtils.isEmpty(str2)
                                && !str2.replaceAll("\u200b", ApnSettings.MVNO_NONE)
                                        .toLowerCase()
                                        .contains(nextToken.toLowerCase())) {
                            break;
                        }
                    }
                }
            }
            arrayList = arrayList2;
        }
        fillInAppList(arrayList);
        return false;
    }

    public final synchronized void updateLockScreenNotificationVisibility() {
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = getContext().getPackageManager();
        for (int i = 0; i < this.mItemPrefList.size(); i++) {
            ShowContentListItemPreference showContentListItemPreference =
                    (ShowContentListItemPreference) this.mItemPrefList.get(i);
            ShowContentAppInfo showContentAppInfo =
                    showContentListItemPreference.mShowContentAppInfo;
            if (showContentAppInfo.isSelected) {
                arrayList.add(showContentAppInfo.packageName);
                Log.d(
                        "LockScreenNotificationShowContentListSettings",
                        "Selected app "
                                + showContentListItemPreference.mShowContentAppInfo.packageName
                                + " will be stored");
            }
            try {
                NotificationBackend notificationBackend = this.mBackend;
                String str = showContentListItemPreference.mShowContentAppInfo.packageName;
                int packageUid = packageManager.getPackageUid(str, 0);
                boolean z = showContentListItemPreference.mShowContentAppInfo.isSelected;
                notificationBackend.getClass();
                NotificationBackend.setLockScreenNotificationVisibilityForPackage(
                        packageUid, z ? 1 : 0, str);
            } catch (Exception e) {
                Log.e(
                        "LockScreenNotificationShowContentListSettings",
                        e.getStackTrace()[0].toString());
            }
        }
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {}
}
