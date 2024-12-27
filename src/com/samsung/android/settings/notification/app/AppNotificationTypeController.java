package com.samsung.android.settings.notification.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.app.NotificationPreferenceController;
import com.android.settings.notification.app.NotificationSettings;

import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppNotificationTypeController extends NotificationPreferenceController
        implements AppNotificationTypeAdapter.ClickListener {
    public NotificationBackend mBackend;
    public NotificationSettings.DependentFieldListener mDependentFieldListener;
    public SecRecyclerViewPreference mPreference;

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        AppNotificationTypeAdapter appNotificationTypeAdapter;
        super.displayPreference(preferenceScreen);
        SecRecyclerViewPreference secRecyclerViewPreference =
                (SecRecyclerViewPreference) preferenceScreen.findPreference("notification_types");
        this.mPreference = secRecyclerViewPreference;
        if (secRecyclerViewPreference == null
                || (appNotificationTypeAdapter =
                                secRecyclerViewPreference.mAppNotificationTypeAdapter)
                        == null) {
            return;
        }
        appNotificationTypeAdapter.mListener = this;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "notification_types";
    }

    @Override // com.android.settings.notification.app.NotificationPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (!super.isAvailable()) {
            return false;
        }
        if (Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_show_notifications",
                                0,
                                -2)
                        == 0
                && Settings.Secure.getInt(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "notification_badging",
                                1)
                        == 0) {
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            int i = appRow.uid;
            this.mBackend.getClass();
            if (!NotificationBackend.getNotificationAlertsEnabledForPackage(i, str)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        boolean z;
        super.updateState(preference);
        SecRecyclerViewPreference secRecyclerViewPreference = this.mPreference;
        secRecyclerViewPreference.mNestedEnable = false;
        secRecyclerViewPreference.notifyChanged();
        secRecyclerViewPreference.COLUMN_COUNT = 3;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 3; i++) {
            AppNotificationTypeInfo appNotificationTypeInfo = new AppNotificationTypeInfo();
            NotificationBackend.AppRow appRow = this.mAppRow;
            String str = appRow.pkg;
            appNotificationTypeInfo.mPackage = str;
            int i2 = appRow.uid;
            appNotificationTypeInfo.uId = i2;
            NotificationBackend notificationBackend = this.mBackend;
            if (i == 0) {
                if (Settings.Secure.getIntForUser(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "lock_screen_show_notifications",
                                0,
                                -2)
                        != 0) {
                    appNotificationTypeInfo.notificationType = "lockscreen";
                    NotificationBackend.AppRow appRow2 = this.mAppRow;
                    String str2 = appRow2.pkg;
                    int i3 = appRow2.uid;
                    notificationBackend.getClass();
                    appNotificationTypeInfo.selected =
                            Boolean.valueOf(
                                    NotificationBackend
                                                    .getLockScreenNotificationVisibilityForPackage(
                                                            i3, str2)
                                            != -1);
                    appNotificationTypeInfo.title =
                            ((NotificationPreferenceController) this)
                                    .mContext.getString(R.string.sec_app_notification_lock_screen);
                    appNotificationTypeInfo.imageEntry = R.drawable.ic_illust_appnoti_lock;
                    arrayList.add(appNotificationTypeInfo);
                    break;
                    break;
                }
            } else {
                if (i != 1) {
                    if (i == 2) {
                        notificationBackend.getClass();
                        if (NotificationBackend.getNotificationAlertsEnabledForPackage(i2, str)) {
                            appNotificationTypeInfo.notificationType = "popup";
                            NotificationBackend.AppRow appRow3 = this.mAppRow;
                            try {
                                z =
                                        NotificationBackend.sINM.isAllowNotificationPopUpForPackage(
                                                appRow3.pkg, appRow3.uid);
                            } catch (Exception e) {
                                Log.w("NotificationBackend", "Error calling NoMan", e);
                                z = false;
                            }
                            appNotificationTypeInfo.selected = Boolean.valueOf(z);
                            appNotificationTypeInfo.imageEntry = R.drawable.ic_illust_appnoti_popup;
                            NotificationBackend.AppRow appRow4 = this.mAppRow;
                            NotificationBackend.getNotificationAlertsEnabledForPackage(
                                    appRow4.uid, appRow4.pkg);
                            appNotificationTypeInfo.title =
                                    ((NotificationPreferenceController) this)
                                            .mContext.getString(
                                                    R.string.sec_app_notification_pop_up);
                        }
                    }
                    arrayList.add(appNotificationTypeInfo);
                    break;
                }
                if (Settings.Secure.getInt(
                                ((NotificationPreferenceController) this)
                                        .mContext.getContentResolver(),
                                "notification_badging",
                                1)
                        != 0) {
                    String str3 = this.mAppRow.pkg;
                    PackageManager packageManager =
                            ((NotificationPreferenceController) this).mContext.getPackageManager();
                    Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
                    intent.addCategory("android.intent.category.LAUNCHER");
                    Iterator<ResolveInfo> it =
                            packageManager.queryIntentActivities(intent, 131584).iterator();
                    while (it.hasNext()) {
                        if (it.next().activityInfo.packageName.contains(str3)) {
                            appNotificationTypeInfo.notificationType = "badge";
                            NotificationBackend.AppRow appRow5 = this.mAppRow;
                            String str4 = appRow5.pkg;
                            int i4 = appRow5.uid;
                            notificationBackend.getClass();
                            appNotificationTypeInfo.selected =
                                    Boolean.valueOf(NotificationBackend.canShowBadge(i4, str4));
                            appNotificationTypeInfo.imageEntry = R.drawable.ic_illust_appnoti_badge;
                            appNotificationTypeInfo.title =
                                    ((NotificationPreferenceController) this)
                                            .mContext.getString(
                                                    R.string.sec_app_notification_badge);
                            arrayList.add(appNotificationTypeInfo);
                            break;
                            break;
                        }
                    }
                }
            }
        }
        secRecyclerViewPreference.mAppNotificationTypeInfoList.clear();
        secRecyclerViewPreference.mAppNotificationTypeInfoList.addAll(arrayList);
        secRecyclerViewPreference.mAppNotificationTypeAdapter.notifyDataSetChanged();
    }
}
