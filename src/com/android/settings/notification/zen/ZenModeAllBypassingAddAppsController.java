package com.android.settings.notification.zen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;

import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.notification.app.AppNotificationTypeInfo;
import com.samsung.android.settings.notification.reminder.NotificationUtils;
import com.samsung.android.settings.widget.SecRecyclerViewPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeAllBypassingAddAppsController
        extends AbstractZenModePreferenceController {
    public final Context mContext;
    public final boolean mIsFromDnd;
    public SecRecyclerViewPreference mPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.notification.zen.ZenModeAllBypassingAddAppsController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            AppNotificationTypeInfo appNotificationTypeInfo = (AppNotificationTypeInfo) obj;
            AppNotificationTypeInfo appNotificationTypeInfo2 = (AppNotificationTypeInfo) obj2;
            if ((appNotificationTypeInfo2 == null) || (appNotificationTypeInfo == null)) {
                return 0;
            }
            return appNotificationTypeInfo.title.compareToIgnoreCase(
                    appNotificationTypeInfo2.title);
        }
    }

    public ZenModeAllBypassingAddAppsController(Context context, Lifecycle lifecycle, boolean z) {
        super(context, "zen_contact", lifecycle);
        this.mContext = context;
        BixbyRoutineActionHandler.getInstance();
        this.mIsFromDnd = z;
    }

    @Override // com.android.settings.notification.zen.AbstractZenModePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecRecyclerViewPreference) preferenceScreen.findPreference("zen_contact");
        super.displayPreference(preferenceScreen);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        AppNotificationTypeInfo addAppNotificationTypeInfo;
        BixbyRoutineActionHandler bixbyRoutineActionHandler;
        super.updateState(preference);
        SecRecyclerViewPreference secRecyclerViewPreference = this.mPreference;
        int i = 4;
        secRecyclerViewPreference.COLUMN_COUNT = 4;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        AppNotificationTypeInfo appNotificationTypeInfo = new AppNotificationTypeInfo();
        appNotificationTypeInfo.isAddAppException = Boolean.TRUE;
        appNotificationTypeInfo.imageEntry = R.drawable.ic_dnd_add_apps;
        appNotificationTypeInfo.title =
                this.mContext.getString(R.string.zen_mode_bypassing_apps_add);
        arrayList.add(appNotificationTypeInfo);
        if (this.mIsFromDnd) {
            arrayList2.addAll(NotificationUtils.getDndException(this.mContext, this.mBackend));
        } else {
            Context context = this.mContext;
            int i2 = NotificationUtils.mInstalledAppCountInWhiteList;
            BixbyRoutineActionHandler bixbyRoutineActionHandler2 =
                    BixbyRoutineActionHandler.getInstance();
            PackageManager packageManager = context.getPackageManager();
            UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
            ArrayList arrayList3 = new ArrayList();
            for (UserInfo userInfo : userManager.getProfiles(UserHandle.myUserId())) {
                int i3 = userInfo.id;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                List queryIntentActivitiesAsUser =
                        packageManager.queryIntentActivitiesAsUser(intent, 0, i3);
                if (queryIntentActivitiesAsUser != null) {
                    ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(i);
                    ArrayList arrayList4 = new ArrayList();
                    Iterator it = queryIntentActivitiesAsUser.iterator();
                    while (it.hasNext()) {
                        UserInfo userInfo2 = userInfo;
                        UserInfo userInfo3 = userInfo;
                        ArrayList arrayList5 = arrayList4;
                        arrayList5.add(
                                new NotificationUtils.AnonymousClass1(
                                        (ResolveInfo) it.next(),
                                        bixbyRoutineActionHandler2,
                                        userInfo2,
                                        packageManager,
                                        arrayList3));
                        arrayList4 = arrayList5;
                        newFixedThreadPool = newFixedThreadPool;
                        userInfo = userInfo3;
                        bixbyRoutineActionHandler2 = bixbyRoutineActionHandler2;
                    }
                    bixbyRoutineActionHandler = bixbyRoutineActionHandler2;
                    ExecutorService executorService = newFixedThreadPool;
                    try {
                        executorService.invokeAll(arrayList4);
                        executorService.shutdown();
                        executorService.awaitTermination(3L, TimeUnit.MINUTES);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    bixbyRoutineActionHandler = bixbyRoutineActionHandler2;
                }
                bixbyRoutineActionHandler2 = bixbyRoutineActionHandler;
                i = 4;
            }
            if (BixbyRoutineActionHandler.app_exist_list.contains(
                            "com.google.android.cellbroadcastreceiver:0")
                    && (addAppNotificationTypeInfo =
                                    NotificationUtils.addAppNotificationTypeInfo(packageManager))
                            != null) {
                arrayList3.add(addAppNotificationTypeInfo);
            }
            arrayList2.addAll(arrayList3);
        }
        Collections.sort(arrayList2, new AnonymousClass1());
        arrayList.addAll(arrayList2);
        secRecyclerViewPreference.mAppNotificationTypeInfoList.clear();
        secRecyclerViewPreference.mAppNotificationTypeInfoList.addAll(arrayList);
        BixbyRoutineActionHandler.setAppSummary(this.mContext);
        secRecyclerViewPreference.mAppNotificationTypeAdapter.notifyDataSetChanged();
    }
}
