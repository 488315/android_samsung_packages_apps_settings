package com.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.notification.zen.SecZenModeBypassingAppsController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeBypassingAppsPreferenceController
        extends AbstractZenModePreferenceController {
    public final NotificationBackend mNotificationBackend;

    @VisibleForTesting protected SecPreference mPreference;
    public String mSummary;
    public final UserManager mUm;
    public final ZenModeBackend mZenModeBackend;

    public ZenModeBypassingAppsPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, "zen_mode_behavior_apps", lifecycle);
        this.mNotificationBackend = new NotificationBackend();
        this.mZenModeBackend = new ZenModeBackend(context);
        this.mUm = (UserManager) this.mContext.getSystemService(UserManager.class);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference("zen_mode_behavior_apps");
        this.mPreference = secPreference;
        secPreference.getClass();
        SecPreferenceUtils.applySummaryColor(secPreference, true);
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "zen_mode_behavior_apps";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final CharSequence getSummary() {
        return updateAppsBypassingDndSummaryText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean getSummaryOnBackground() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @VisibleForTesting
    public CharSequence updateAppsBypassingDndSummaryText() {
        int zenMode = getZenMode();
        int i = 0;
        if (zenMode == 2 || zenMode == 3) {
            this.mPreference.setEnabled(false);
            String string =
                    this.mContext
                            .getResources()
                            .getString(R.string.zen_mode_bypassing_apps_subtext_none);
            this.mSummary = string;
            return string;
        }
        this.mPreference.setEnabled(true);
        ArrayList arrayList = new ArrayList();
        List appBypassDndList =
                this.mZenModeBackend
                        .mNotificationManager
                        .getNotificationPolicy()
                        .getAppBypassDndList();
        PackageManager packageManager = this.mContext.getPackageManager();
        Iterator it = appBypassDndList.iterator();
        while (it.hasNext()) {
            try {
                String[] split = ((String) it.next()).split(":");
                arrayList.add(
                        packageManager
                                .getPackageInfoAsUser(
                                        split[0],
                                        0,
                                        UserHandle.getUserId(Integer.parseInt(split[1])))
                                .applicationInfo
                                .loadLabel(packageManager)
                                .toString());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(arrayList);
        int size = arrayList.size();
        String[] strArr = (String[]) arrayList.toArray(new String[size]);
        PackageManager packageManager2 = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add("com.google.android.cellbroadcastreceiver:0");
        for (UserInfo userInfo : this.mUm.getProfiles(UserHandle.myUserId())) {
            try {
                List<ResolveInfo> queryIntentActivitiesAsUser =
                        packageManager2.queryIntentActivitiesAsUser(intent, i, userInfo.id);
                List<ApplicationInfo> installedApplicationsAsUser =
                        packageManager2.getInstalledApplicationsAsUser(i, userInfo.id);
                ArrayList arrayList3 = new ArrayList();
                for (ApplicationInfo applicationInfo : installedApplicationsAsUser) {
                    NotificationBackend notificationBackend = this.mNotificationBackend;
                    String str = applicationInfo.packageName;
                    int i2 = applicationInfo.uid;
                    notificationBackend.getClass();
                    if (NotificationBackend.getChannelCount(i2, str) > 0) {
                        arrayList3.add(applicationInfo.packageName);
                    }
                }
                for (ResolveInfo resolveInfo : queryIntentActivitiesAsUser) {
                    if (arrayList3.contains(resolveInfo.activityInfo.packageName)) {
                        if (!arrayList2.contains(
                                resolveInfo.activityInfo.packageName + ":" + userInfo.id)) {
                            arrayList2.add(
                                    resolveInfo.activityInfo.packageName + ":" + userInfo.id);
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            i = 0;
        }
        int size2 = arrayList2.size();
        this.mSummary = this.mContext.getString(R.string.sec_zen_items_none_allowed);
        int i3 =
                Settings.Global.getInt(
                        this.mContext.getContentResolver(),
                        SecZenModeBypassingAppsController.ZEN_MODE_BYPASSING_APPS_DB_KEY,
                        0);
        if (size == 0 && i3 == 1) {
            this.mSummary = this.mContext.getString(R.string.sec_zen_people_summery_allow_all);
        }
        if (size2 == size) {
            if (i3 == 0) {
                this.mSummary = this.mContext.getString(R.string.sec_zen_people_summery_allow_all);
            } else {
                this.mSummary = this.mContext.getString(R.string.sec_zen_items_none_allowed);
            }
        } else if (size == 1) {
            if (i3 == 0) {
                this.mSummary =
                        this.mContext.getString(R.string.sec_zen_items_one_allowed, strArr[0]);
            } else {
                this.mSummary =
                        this.mContext.getString(
                                R.string.sec_zen_items_all_except_one_allowed, strArr[0]);
            }
        } else if (size == 2) {
            if (i3 == 0) {
                this.mSummary =
                        this.mContext.getString(
                                R.string.sec_zen_items_two_allowed, strArr[0], strArr[1]);
            } else {
                this.mSummary =
                        this.mContext.getString(
                                R.string.sec_zen_items_all_except_two_allowed,
                                strArr[0],
                                strArr[1]);
            }
        } else if (size > 2) {
            if (i3 == 0) {
                this.mSummary =
                        this.mContext.getString(
                                R.string.sec_zen_items_more_allowed,
                                strArr[0],
                                strArr[1],
                                Integer.valueOf(size - 2));
            } else {
                this.mSummary =
                        this.mContext.getString(
                                R.string.sec_zen_items_all_except_more_allowed,
                                strArr[0],
                                strArr[1],
                                Integer.valueOf(size - 2));
            }
        }
        return this.mSummary;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        refreshSummary(preference);
    }
}
