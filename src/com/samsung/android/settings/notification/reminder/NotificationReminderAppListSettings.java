package com.samsung.android.settings.notification.reminder;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.SearchView;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.internal.util.CollectionUtils;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.AssistantReminderPreferenceController;
import com.android.settings.notification.NotificationBackend;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.sec.ims.configuration.DATA;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NotificationReminderAppListSettings extends DashboardFragment
        implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public NotificationReminderAppItemPreference mAllSwitchPreference;
    public String mAppAppsPreferenceTitle;
    public ArrayList mAppList;
    public PreferenceCategory mAppListPref;
    public ArrayList mAppTempList;
    public NotificationBackend mBackend;
    public boolean mIsDirty;
    public boolean mIsToggledByAllSwitch;
    public SearchView mSearchView;
    public final ArrayList mItemPrefList = new ArrayList();
    public boolean isSearch = false;
    public final AnonymousClass1 mAppNameComparator =
            new Comparator() { // from class:
                               // com.samsung.android.settings.notification.reminder.NotificationReminderAppListSettings.1
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    ReminderAppListInfo reminderAppListInfo = (ReminderAppListInfo) obj;
                    ReminderAppListInfo reminderAppListInfo2 = (ReminderAppListInfo) obj2;
                    try {
                        reminderAppListInfo.getClass();
                        reminderAppListInfo2.getClass();
                        return this.collator.compare(
                                reminderAppListInfo.appName, reminderAppListInfo2.appName);
                    } catch (NullPointerException e) {
                        Log.e(
                                "NotificationReminderAppListSettings",
                                "Failed to compare AppInfo. " + e);
                        return 0;
                    }
                }
            };
    public final AnonymousClass2 mPreferenceChange =
            new Preference
                    .OnPreferenceChangeListener() { // from class:
                                                    // com.samsung.android.settings.notification.reminder.NotificationReminderAppListSettings.2
                @Override // androidx.preference.Preference.OnPreferenceChangeListener
                public final boolean onPreferenceChange(Preference preference, Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    NotificationReminderAppItemPreference notificationReminderAppItemPreference =
                            (NotificationReminderAppItemPreference) preference;
                    boolean equals =
                            "AllAppsBtn"
                                    .equals(
                                            notificationReminderAppItemPreference
                                                    .mReminderAppListInfo
                                                    .packageName);
                    NotificationReminderAppListSettings notificationReminderAppListSettings =
                            NotificationReminderAppListSettings.this;
                    if (equals) {
                        notificationReminderAppListSettings.mIsToggledByAllSwitch = true;
                    }
                    notificationReminderAppItemPreference.mReminderAppListInfo.isSelected =
                            booleanValue;
                    Log.d(
                            "NotificationReminderAppListSettings",
                            "app: "
                                    + notificationReminderAppItemPreference
                                            .mReminderAppListInfo
                                            .appName
                                    + ", isChecked :"
                                    + booleanValue);
                    notificationReminderAppListSettings.mIsDirty = true;
                    Settings.System.putInt(
                            notificationReminderAppListSettings.getContentResolver(),
                            AssistantReminderPreferenceController.NOTIFICATION_REMINDER_TYPE,
                            1);
                    notificationReminderAppListSettings.getClass();
                    Log.d("NotificationReminderAppListSettings", "updateAllSwitch called");
                    boolean z = false;
                    if (notificationReminderAppListSettings.mIsToggledByAllSwitch) {
                        NotificationUtils.mAllAppsEnabled = booleanValue;
                        Iterator it = notificationReminderAppListSettings.mItemPrefList.iterator();
                        while (it.hasNext()) {
                            NotificationReminderAppItemPreference
                                    notificationReminderAppItemPreference2 =
                                            (NotificationReminderAppItemPreference) it.next();
                            notificationReminderAppItemPreference2.setChecked(booleanValue);
                            notificationReminderAppItemPreference2.mReminderAppListInfo.isSelected =
                                    booleanValue;
                        }
                        notificationReminderAppListSettings.mIsToggledByAllSwitch = false;
                    } else {
                        Iterator it2 = notificationReminderAppListSettings.mItemPrefList.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                z = true;
                                break;
                            }
                            if (!((NotificationReminderAppItemPreference) it2.next())
                                    .mReminderAppListInfo
                                    .isSelected) {
                                break;
                            }
                        }
                        notificationReminderAppListSettings.mAllSwitchPreference.setChecked(z);
                        NotificationUtils.mAllAppsEnabled = z;
                    }
                    notificationReminderAppListSettings.updateNotificationReminderList();
                    return true;
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.reminder.NotificationReminderAppListSettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            String num;
            ArrayList arrayList = new ArrayList();
            String[] strArr = {NotificationUtils.getNotificationReminderEnabledList(context)};
            boolean equals = "AllAppsAvailable".equals(strArr[0]);
            String str = strArr[0];
            if (str == null) {
                num = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
            } else if (equals) {
                num = context.getString(R.string.reminder_select_apps_summary_all_apps);
            } else {
                num =
                        Integer.toString(
                                ApnSettings.MVNO_NONE.equals(strArr[0])
                                        ? 0
                                        : str.split(",").length);
            }
            String valueOf = String.valueOf(36417);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = num;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(36417);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = num;
            statusData2.mStatusKey = valueOf2;
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GetAppListAsyncTask extends AsyncTask {
        public GetAppListAsyncTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            Log.d(
                    "NotificationReminderAppListSettings",
                    "GetAppListAsyncTask doInBackground called");
            return NotificationUtils.getAppList(
                    NotificationReminderAppListSettings.this.getContext());
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            ArrayList arrayList = (ArrayList) obj;
            super.onPostExecute(arrayList);
            if (arrayList != null) {
                Log.d(
                        "NotificationReminderAppListSettings",
                        "GetAppListAsyncTask onPostExecute called" + arrayList.size());
                NotificationReminderAppListSettings.this.fillInAppList(arrayList);
            }
        }
    }

    public final void fillInAppList(ArrayList arrayList) {
        Log.d("NotificationReminderAppListSettings", "fillInAppList called");
        this.mAppList = arrayList;
        if (!this.isSearch) {
            this.mAppTempList = arrayList;
        }
        Collections.sort(arrayList, this.mAppNameComparator);
        ArrayList arrayList2 = this.mAppList;
        boolean z = NotificationUtils.mAllAppsEnabled;
        int i = NotificationUtils.mInstalledAppCountInWhiteList;
        Log.d("NotificationReminderAppListSettings", "updateAppList called");
        this.mAppListPref.removeAll();
        this.mItemPrefList.clear();
        Context context = getContext();
        if (context == null && (context = getActivity()) == null) {
            return;
        }
        if (!this.isSearch) {
            NotificationReminderAppItemPreference notificationReminderAppItemPreference =
                    new NotificationReminderAppItemPreference(context, 0);
            this.mAllSwitchPreference = notificationReminderAppItemPreference;
            notificationReminderAppItemPreference.mReminderAppListInfo =
                    new ReminderAppListInfo(
                            getString(R.string.brief_popup_all_apps_btn_text),
                            "AllAppsBtn",
                            null,
                            z);
            NotificationReminderAppItemPreference notificationReminderAppItemPreference2 =
                    this.mAllSwitchPreference;
            notificationReminderAppItemPreference2.mAllowDrawerDivider = true;
            notificationReminderAppItemPreference2.mAllowLineDivider = false;
            notificationReminderAppItemPreference2.setTitle(this.mAppAppsPreferenceTitle);
            this.mAllSwitchPreference.setChecked(z);
            this.mAllSwitchPreference.setOnPreferenceChangeListener(this.mPreferenceChange);
            this.mAppListPref.addPreference(this.mAllSwitchPreference);
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            ReminderAppListInfo reminderAppListInfo = (ReminderAppListInfo) arrayList2.get(i2);
            if (reminderAppListInfo == null) {
                Log.e("NotificationReminderAppListSettings", "updateAppList item is null..");
            } else {
                NotificationReminderAppItemPreference notificationReminderAppItemPreference3 =
                        new NotificationReminderAppItemPreference(context);
                if (i2 == i - 1) {
                    notificationReminderAppItemPreference3.mAllowDrawerDivider = true;
                    notificationReminderAppItemPreference3.mAllowLineDivider = false;
                } else if (i2 == arrayList2.size() - 1) {
                    notificationReminderAppItemPreference3.mAllowLineDivider = false;
                }
                notificationReminderAppItemPreference3.setTitle(reminderAppListInfo.appName);
                notificationReminderAppItemPreference3.mReminderAppListInfo = reminderAppListInfo;
                notificationReminderAppItemPreference3.setChecked(reminderAppListInfo.isSelected);
                notificationReminderAppItemPreference3.setOnPreferenceChangeListener(
                        this.mPreferenceChange);
                this.mAppListPref.addPreference(notificationReminderAppItemPreference3);
                this.mItemPrefList.add(notificationReminderAppItemPreference3);
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        Log.d("NotificationReminderAppListSettings", "getLogTag called");
        return "NotificationReminderAppListSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 36046;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.noti_reminder_list_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.d("NotificationReminderAppListSettings", "onActivityCreated called");
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
        Log.d("NotificationReminderAppListSettings", "onCreate called");
        this.mIsDirty = false;
        NotificationUtils.mInstalledAppCountInWhiteList = 0;
        this.mAppListPref = (PreferenceCategory) findPreference("notification_reminder_list_key");
        this.mAppAppsPreferenceTitle = getString(R.string.reminder_select_apps_summary_all_apps);
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
        Log.d("NotificationReminderAppListSettings", "onDestroyView called");
        this.mAppListPref.removeAll();
        this.mItemPrefList.clear();
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.isSearch = false;
        fillInAppList(this.mAppTempList);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.isSearch = true;
        fillInAppList(this.mAppTempList);
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.d("NotificationReminderAppListSettings", "onPause called");
        super.onPause();
        if (this.mIsDirty) {
            updateNotificationReminderList();
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
                ReminderAppListInfo reminderAppListInfo = (ReminderAppListInfo) it.next();
                StringTokenizer stringTokenizer = new StringTokenizer(str.toString(), " ");
                while (true) {
                    if (!stringTokenizer.hasMoreTokens()) {
                        arrayList2.add(reminderAppListInfo);
                        break;
                    }
                    String nextToken = stringTokenizer.nextToken();
                    String str2 = reminderAppListInfo.appName;
                    if (!TextUtils.isEmpty(str2)
                            && !str2.replaceAll("\u200b", ApnSettings.MVNO_NONE)
                                    .toLowerCase()
                                    .contains(nextToken.toLowerCase())) {
                        break;
                    }
                }
            }
            arrayList = arrayList2;
        }
        fillInAppList(arrayList);
        return false;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        Log.d("NotificationReminderAppListSettings", "onResume called");
    }

    public final synchronized void updateNotificationReminderList() {
        Integer num;
        Log.d("NotificationReminderAppListSettings", "updateNotificationReminderList called");
        ArrayList arrayList = new ArrayList();
        getContext().getPackageManager();
        for (int i = 0; i < this.mItemPrefList.size(); i++) {
            NotificationReminderAppItemPreference notificationReminderAppItemPreference =
                    (NotificationReminderAppItemPreference) this.mItemPrefList.get(i);
            ReminderAppListInfo reminderAppListInfo =
                    notificationReminderAppItemPreference.mReminderAppListInfo;
            if (reminderAppListInfo.isSelected) {
                arrayList.add(reminderAppListInfo.packageName);
                Log.d(
                        "NotificationReminderAppListSettings",
                        "Selected app "
                                + notificationReminderAppItemPreference
                                        .mReminderAppListInfo
                                        .packageName
                                + " will be stored");
            }
            try {
                NotificationBackend notificationBackend = this.mBackend;
                String str = notificationReminderAppItemPreference.mReminderAppListInfo.packageName;
                Context context = getContext();
                String str2 =
                        notificationReminderAppItemPreference.mReminderAppListInfo.packageName;
                int i2 = NotificationUtils.mInstalledAppCountInWhiteList;
                try {
                    num =
                            Integer.valueOf(
                                    context.getPackageManager()
                                            .getPackageUidAsUser(
                                                    str2, ActivityManager.getCurrentUser()));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                    num = null;
                }
                int intValue = num.intValue();
                boolean z = notificationReminderAppItemPreference.mReminderAppListInfo.isSelected;
                notificationBackend.getClass();
                try {
                    NotificationBackend.sINM.setReminderEnabledForPackage(str, intValue, z);
                } catch (Exception e2) {
                    Log.w("NotificationBackend", "Error calling NoMan", e2);
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        Context context2 = getContext();
        int i3 = NotificationUtils.mInstalledAppCountInWhiteList;
        StringBuilder sb = new StringBuilder();
        for (String str3 : CollectionUtils.emptyIfNull(arrayList)) {
            if (str3 != null && !str3.equals(ApnSettings.MVNO_NONE)) {
                sb.append(str3);
                sb.append(":");
            }
        }
        Settings.Secure.putStringForUser(
                context2.getContentResolver(),
                "notification_reminder_app_list",
                sb.toString(),
                UserHandle.myUserId());
    }

    @Override // androidx.appcompat.widget.SearchView.OnQueryTextListener
    public final void onQueryTextSubmit(String str) {}
}
