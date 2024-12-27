package com.samsung.android.settings.notification.reminder;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.notification.zen.ZenModeBackend;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.bixbyroutinehandler.BixbyRoutineActionHandler;
import com.samsung.android.settings.connection.moreconnection.EmergencyAlertsPreferenceController;
import com.samsung.android.settings.notification.app.AppNotificationTypeInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class NotificationUtils {
    public static boolean mAllAppsEnabled = true;
    public static final AnonymousClass4 mAppNameComparator =
            new Comparator() { // from class:
                               // com.samsung.android.settings.notification.reminder.NotificationUtils.4
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    try {
                        return this.collator.compare((String) obj, (String) obj2);
                    } catch (NullPointerException e) {
                        int i = NotificationUtils.mInstalledAppCountInWhiteList;
                        Log.e("NotificationUtils", "Failed to compare AppInfo. " + e);
                        return 0;
                    }
                }
            };
    public static int mInstalledAppCountInWhiteList;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.reminder.NotificationUtils$1, reason: invalid class name */
    public final class AnonymousClass1 implements Callable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ ArrayList val$appInfoArrayList;
        public final /* synthetic */ Object val$mContext;
        public final /* synthetic */ PackageManager val$pm;
        public final /* synthetic */ ResolveInfo val$resolveInfo;

        public /* synthetic */ AnonymousClass1(
                ResolveInfo resolveInfo,
                PackageManager packageManager,
                Context context,
                ArrayList arrayList,
                int i) {
            this.$r8$classId = i;
            this.val$resolveInfo = resolveInfo;
            this.val$pm = packageManager;
            this.val$mContext = context;
            this.val$appInfoArrayList = arrayList;
        }

        /* JADX WARN: Removed duplicated region for block: B:36:0x0108  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x010b  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x01a2  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x01a5  */
        @Override // java.util.concurrent.Callable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object call() {
            /*
                Method dump skipped, instructions count: 458
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.notification.reminder.NotificationUtils.AnonymousClass1.call():java.lang.Object");
        }

        public AnonymousClass1(
                ResolveInfo resolveInfo,
                BixbyRoutineActionHandler bixbyRoutineActionHandler,
                UserInfo userInfo,
                PackageManager packageManager,
                ArrayList arrayList) {
            this.$r8$classId = 2;
            this.val$resolveInfo = resolveInfo;
            this.val$mContext = userInfo;
            this.val$pm = packageManager;
            this.val$appInfoArrayList = arrayList;
        }
    }

    public static AppNotificationTypeInfo addAppNotificationTypeInfo(
            PackageManager packageManager) {
        AppNotificationTypeInfo appNotificationTypeInfo = new AppNotificationTypeInfo();
        try {
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(
                            EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE, 0);
            packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appNotificationTypeInfo.title =
                    packageInfo.applicationInfo.loadLabel(packageManager).toString();
            appNotificationTypeInfo.mPackage =
                    EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE;
            appNotificationTypeInfo.mPackageIcon =
                    packageManager.getUserBadgedIcon(
                            packageInfo.applicationInfo.loadIcon(packageManager),
                            new UserHandle(0));
            appNotificationTypeInfo.isAddAppException = Boolean.TRUE;
            appNotificationTypeInfo.uId = 0;
            return appNotificationTypeInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Drawable getAppIcon(
            PackageManager packageManager, ComponentName componentName, int i) {
        try {
            return packageManager.getUserBadgedIcon(
                    packageManager
                            .getPackageInfoAsUser(componentName.getPackageName(), 4096, i)
                            .applicationInfo
                            .loadIcon(packageManager),
                    new UserHandle(i));
        } catch (Exception e) {
            Log.w("NotificationUtils", "Exception", e);
            return null;
        }
    }

    public static ArrayList getAppList(Context context) {
        int[] iArr = {0};
        PackageManager packageManager = context.getPackageManager();
        new NotificationBackend();
        ArrayList arrayList = new ArrayList();
        List notificationPackagesList =
                NotificationBackend.getNotificationPackagesList(context, context.getUserId());
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = ((ArrayList) notificationPackagesList).iterator();
        while (it.hasNext()) {
            arrayList2.add(
                    new AnonymousClass1(
                            (ResolveInfo) it.next(), packageManager, context, arrayList, 1));
        }
        try {
            newFixedThreadPool.invokeAll(arrayList2);
            newFixedThreadPool.shutdown();
            newFixedThreadPool.awaitTermination(3L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mInstalledAppCountInWhiteList = iArr[0];
        return arrayList;
    }

    public static ArrayList getAppListForShowContent(Context context) {
        int[] iArr = {0};
        PackageManager packageManager = context.getPackageManager();
        new NotificationBackend();
        ArrayList arrayList = new ArrayList();
        List notificationPackagesList =
                NotificationBackend.getNotificationPackagesList(context, context.getUserId());
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
        ArrayList arrayList2 = new ArrayList();
        Iterator it = ((ArrayList) notificationPackagesList).iterator();
        while (it.hasNext()) {
            arrayList2.add(
                    new AnonymousClass1(
                            (ResolveInfo) it.next(), packageManager, context, arrayList, 0));
        }
        try {
            newFixedThreadPool.invokeAll(arrayList2);
            newFixedThreadPool.shutdown();
            newFixedThreadPool.awaitTermination(3L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mInstalledAppCountInWhiteList = iArr[0];
        return arrayList;
    }

    public static ArrayList getDndException(
            final Context context, final ZenModeBackend zenModeBackend) {
        AppNotificationTypeInfo addAppNotificationTypeInfo;
        final PackageManager packageManager = context.getPackageManager();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        final ArrayList arrayList = new ArrayList();
        for (UserInfo userInfo : userManager.getProfiles(UserHandle.myUserId())) {
            int i = userInfo.id;
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> queryIntentActivitiesAsUser =
                    packageManager.queryIntentActivitiesAsUser(intent, 0, i);
            if (queryIntentActivitiesAsUser != null) {
                ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(4);
                ArrayList arrayList2 = new ArrayList();
                for (final ResolveInfo resolveInfo : queryIntentActivitiesAsUser) {
                    final UserInfo userInfo2 = userInfo;
                    arrayList2.add(
                            new Callable() { // from class:
                                             // com.samsung.android.settings.notification.reminder.NotificationUtils.3
                                @Override // java.util.concurrent.Callable
                                public final Object call() {
                                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                                    String str = activityInfo.name;
                                    String str2 = activityInfo.packageName;
                                    int packageUidAsUser =
                                            context.getPackageManager()
                                                    .getPackageUidAsUser(str2, userInfo2.id);
                                    zenModeBackend.getClass();
                                    if (!ZenModeBackend.canAppBypassDnd(packageUidAsUser, str2)) {
                                        return null;
                                    }
                                    ComponentName componentName = new ComponentName(str2, str);
                                    String flattenToString = componentName.flattenToString();
                                    if (flattenToString != null) {
                                        componentName =
                                                ComponentName.unflattenFromString(flattenToString);
                                    }
                                    try {
                                        PackageManager packageManager2 = packageManager;
                                        String charSequence =
                                                packageManager2
                                                        .getApplicationLabel(
                                                                packageManager2
                                                                        .getApplicationInfoAsUser(
                                                                                str2,
                                                                                0,
                                                                                userInfo2.id))
                                                        .toString();
                                        Drawable appIcon =
                                                NotificationUtils.getAppIcon(
                                                        packageManager,
                                                        componentName,
                                                        userInfo2.id);
                                        AppNotificationTypeInfo appNotificationTypeInfo =
                                                new AppNotificationTypeInfo();
                                        appNotificationTypeInfo.title = charSequence;
                                        appNotificationTypeInfo.mPackage = str2;
                                        appNotificationTypeInfo.mPackageIcon = appIcon;
                                        appNotificationTypeInfo.isAddAppException = Boolean.TRUE;
                                        appNotificationTypeInfo.uId = userInfo2.id;
                                        if (charSequence == null || str2 == null) {
                                            return null;
                                        }
                                        arrayList.add(appNotificationTypeInfo);
                                        return null;
                                    } catch (PackageManager.NameNotFoundException e) {
                                        e.printStackTrace();
                                        return null;
                                    }
                                }
                            });
                    userInfo = userInfo;
                }
                try {
                    newFixedThreadPool.invokeAll(arrayList2);
                    newFixedThreadPool.shutdown();
                    newFixedThreadPool.awaitTermination(3L, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            int packageUidAsUser =
                    context.getPackageManager()
                            .getPackageUidAsUser(
                                    EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE,
                                    0);
            zenModeBackend.getClass();
            if (ZenModeBackend.canAppBypassDnd(
                            packageUidAsUser,
                            EmergencyAlertsPreferenceController.AOSP_GOOGLE_CMAS_PACKAGE)
                    && (addAppNotificationTypeInfo = addAppNotificationTypeInfo(packageManager))
                            != null) {
                arrayList.add(addAppNotificationTypeInfo);
            }
        } catch (PackageManager.NameNotFoundException e2) {
            e2.printStackTrace();
        }
        return arrayList;
    }

    public static String getNotificationReminderEnabledList(Context context) {
        boolean z;
        String str;
        PackageManager packageManager = context.getPackageManager();
        new NotificationBackend();
        List notificationPackagesList =
                NotificationBackend.getNotificationPackagesList(context, context.getUserId());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(),
                        "notification_reminder_app_list",
                        UserHandle.myUserId());
        ArrayList arrayList4 =
                stringForUser == null
                        ? new ArrayList()
                        : new ArrayList(Arrays.asList(stringForUser.split(":")));
        if (arrayList4.size() > 0) {
            arrayList3 = arrayList4;
        }
        Iterator it = ((ArrayList) notificationPackagesList).iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            String str2 = activityInfo.name;
            String str3 = activityInfo.packageName;
            if (context.getPackageManager().getLaunchIntentForPackage(str3) != null
                    && str2 != null) {
                try {
                    try {
                        z =
                                NotificationBackend.sINM.isReminderEnabled(
                                        str3,
                                        packageManager.getPackageUidAsUser(
                                                str3, ActivityManager.getCurrentUser()));
                    } catch (Exception e) {
                        Log.w("NotificationBackend", "Error calling NoMan", e);
                        z = false;
                    }
                    if (z) {
                        ComponentName componentName = new ComponentName(str3, str2);
                        String flattenToString = componentName.flattenToString();
                        if (flattenToString != null) {
                            componentName = ComponentName.unflattenFromString(flattenToString);
                        }
                        try {
                            str =
                                    packageManager
                                            .getActivityInfo(componentName, 0)
                                            .loadLabel(packageManager)
                                            .toString();
                        } catch (PackageManager.NameNotFoundException e2) {
                            e2.printStackTrace();
                            str = null;
                        }
                        if (str == null || str3 == null) {
                            Log.e("NotificationUtils", "Error...");
                        } else if (arrayList3.contains(str3)) {
                            arrayList2.add(str);
                        } else {
                            arrayList.add(str);
                        }
                    } else {
                        z2 = false;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
        if (z2) {
            return "AllAppsAvailable";
        }
        AnonymousClass4 anonymousClass4 = mAppNameComparator;
        Collections.sort(arrayList, anonymousClass4);
        Collections.sort(arrayList2, anonymousClass4);
        StringBuilder sb = new StringBuilder();
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str4 = (String) it2.next();
            if (sb.isEmpty()) {
                sb.append(str4);
            } else {
                sb.append("," + str4);
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            String str5 = (String) it3.next();
            if (sb.isEmpty()) {
                sb.append(str5);
            } else {
                sb.append("," + str5);
            }
        }
        return sb.toString();
    }

    public static String getNotificationReminderSummary(Context context) {
        String[] strArr = {getNotificationReminderEnabledList(context)};
        if ("AllAppsAvailable".equals(strArr[0])) {
            return context.getString(R.string.reminder_select_apps_summary_all_apps);
        }
        String[] split = strArr[0].split(",");
        int length = !ApnSettings.MVNO_NONE.equals(strArr[0]) ? split.length : 0;
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                new StringBuilder("notification reminder enabled applist : "),
                strArr[0],
                "NotificationUtils");
        return length != 0
                ? length != 1
                        ? length != 2
                                ? context.getResources()
                                        .getQuantityString(
                                                R.plurals.reminder_select_apps,
                                                length,
                                                Integer.valueOf(length))
                                : context.getString(
                                        R.string.reminder_select_apps_summary_p1ss_and_p2ss,
                                        split[0],
                                        split[1])
                        : context.getString(
                                R.string.sec_edge_lighting_enable_app_list_one, split[0])
                : context.getString(R.string.sec_apps_to_get_reminder_for_summary);
    }
}
