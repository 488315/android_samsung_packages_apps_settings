package com.android.settings.notification;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerFilter;
import android.util.IconDrawableFactory;
import android.util.Log;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.notification.ConversationIconFactory;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NotificationBackend {
    public final AnonymousClass1 mAppNameComparator =
            new Comparator() { // from class:
                               // com.android.settings.notification.NotificationBackend.1
                public final Collator collator = Collator.getInstance();

                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    try {
                        return this.collator.compare((String) obj, (String) obj2);
                    } catch (NullPointerException e) {
                        Log.e("NotificationBackend", "Failed to compare AppInfo. " + e);
                        return 0;
                    }
                }
            };
    public static final IUsageStatsManager sUsageStatsManager =
            IUsageStatsManager.Stub.asInterface(ServiceManager.getService("usagestats"));
    public static INotificationManager sINM =
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRow {
        public boolean banned;
        public int bubblePreference;
        public int channelCount;
        public Drawable icon;
        public CharSequence label;
        public boolean lockedImportance;
        public boolean packageBlockable;
        public String pkg;
        public NotificationsSentState sentByApp;
        public Map sentByChannel;
        public Intent settingsIntent;
        public boolean showBadge;
        public boolean systemApp;
        public int uid;
        public int userId;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotificationsSentState {
        public long lastSent = 0;
        public int sentCount = 0;
    }

    public static boolean canShowBadge(int i, String str) {
        try {
            return sINM.canShowBadge(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static ComponentName getAllowedNotificationAssistant() {
        try {
            return sINM.getAllowedNotificationAssistant();
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return null;
        }
    }

    public static int getBubblePreference(int i, String str) {
        try {
            return sINM.getBubblePreferenceForPackage(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return -1;
        }
    }

    public static NotificationChannel getChannel(int i, String str, String str2, String str3) {
        if (str2 == null) {
            return null;
        }
        try {
            return sINM.getNotificationChannelForPackage(str, i, str2, str3, true);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return null;
        }
    }

    public static int getChannelCount(int i, String str) {
        try {
            return sINM.getNumNotificationChannelsForPackage(str, i, false);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return 0;
        }
    }

    public static ConversationIconFactory.ConversationIconDrawable getConversationDrawable(
            Context context, ShortcutInfo shortcutInfo, String str, int i, boolean z) {
        Drawable defaultActivityIcon;
        if (shortcutInfo == null) {
            return null;
        }
        LauncherApps launcherApps = (LauncherApps) context.getSystemService(LauncherApps.class);
        PackageManager packageManager = context.getPackageManager();
        IconDrawableFactory.newInstance(context, false);
        ConversationIconFactory conversationIconFactory =
                new ConversationIconFactory(
                        context,
                        launcherApps,
                        packageManager,
                        context.getResources()
                                .getDimensionPixelSize(R.dimen.conversation_icon_size));
        Drawable shortcutIconDrawable =
                conversationIconFactory.mLauncherApps.getShortcutIconDrawable(
                        shortcutInfo, conversationIconFactory.mFillResIconDpi);
        try {
            defaultActivityIcon =
                    Utils.getBadgedIcon(
                            conversationIconFactory.mContext,
                            conversationIconFactory.mPackageManager.getApplicationInfoAsUser(
                                    str, 128, UserHandle.getUserId(i)));
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = conversationIconFactory.mPackageManager.getDefaultActivityIcon();
        }
        int i2 = conversationIconFactory.mIconBitmapSize;
        int i3 = conversationIconFactory.mImportantConversationColor;
        ConversationIconFactory.ConversationIconDrawable conversationIconDrawable =
                new ConversationIconFactory.ConversationIconDrawable();
        conversationIconDrawable.mBaseIcon = shortcutIconDrawable;
        conversationIconDrawable.mBadgeIcon = defaultActivityIcon;
        conversationIconDrawable.mIconSize = i2;
        conversationIconDrawable.mShowRing = z;
        Paint paint = new Paint();
        conversationIconDrawable.mRingPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(i3);
        Paint paint2 = new Paint();
        conversationIconDrawable.mPaddingPaint = paint2;
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setColor(-1);
        return conversationIconDrawable;
    }

    public static ParceledListSlice getConversations(int i, String str) {
        try {
            return sINM.getConversationsForPackage(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return ParceledListSlice.emptyList();
        }
    }

    public static ComponentName getDefaultNotificationAssistant() {
        try {
            return sINM.getDefaultNotificationAssistant();
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return null;
        }
    }

    public static NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) {
        NotificationListenerFilter notificationListenerFilter;
        try {
            notificationListenerFilter = sINM.getListenerFilter(componentName, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            notificationListenerFilter = null;
        }
        return notificationListenerFilter != null
                ? notificationListenerFilter
                : new NotificationListenerFilter();
    }

    public static int getLockScreenNotificationVisibilityForPackage(int i, String str) {
        try {
            return sINM.getLockScreenNotificationVisibilityForPackage(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return 0;
        }
    }

    public static boolean getNotificationAlertsEnabledForPackage(int i, String str) {
        Log.d(
                "NotificationBackend",
                "getNotificationAlertsEnabledForPackage: pkg=" + str + " uid=" + i);
        try {
            return sINM.getNotificationAlertsEnabledForPackage(str, i);
        } catch (Exception e) {
            Log.d("NotificationBackend", "exception in get alerts: ", e);
            return false;
        }
    }

    public static List getNotificationPackagesList(Context context, int i) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        List<ApplicationInfo> installedApplicationsAsUser =
                packageManager.getInstalledApplicationsAsUser(0, i);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        try {
            for (ApplicationInfo applicationInfo : installedApplicationsAsUser) {
                if (getChannelCount(applicationInfo.uid, applicationInfo.packageName) > 0) {
                    arrayList.add(applicationInfo.packageName);
                }
            }
            for (ResolveInfo resolveInfo :
                    packageManager.queryIntentActivitiesAsUser(intent, 0, i)) {
                if (arrayList.contains(resolveInfo.activityInfo.packageName)) {
                    arrayList2.add(resolveInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList2;
    }

    public static boolean getNotificationsBanned(int i, String str) {
        try {
            return !sINM.areNotificationsEnabledForPackage(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static boolean isInInvalidMsgState(int i, String str) {
        try {
            return sINM.isInInvalidMsgState(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static boolean isOngoingActivityAllowed(int i, String str) {
        Log.d("NotificationBackend", "isOngoingActivityAllowed: pkg=" + str + " uid=" + i);
        try {
            return sINM.isOngoingActivityAllowed(str, i);
        } catch (Exception e) {
            Log.d("NotificationBackend", "exception in get isOngoingActivityAllowed: ", e);
            return false;
        }
    }

    public static AppRow loadAppRow(
            Context context, PackageManager packageManager, ApplicationInfo applicationInfo) {
        UsageEvents usageEvents;
        String str;
        AppRow appRow = new AppRow();
        appRow.bubblePreference = 0;
        appRow.pkg = applicationInfo.packageName;
        appRow.uid = applicationInfo.uid;
        try {
            appRow.label = applicationInfo.loadLabel(packageManager);
        } catch (Throwable th) {
            Log.e("NotificationBackend", "Error loading application label for " + appRow.pkg, th);
            appRow.label = appRow.pkg;
        }
        appRow.icon = IconDrawableFactory.newInstance(context).getBadgedIcon(applicationInfo);
        appRow.banned = getNotificationsBanned(appRow.uid, appRow.pkg);
        appRow.showBadge = canShowBadge(appRow.uid, appRow.pkg);
        appRow.bubblePreference = getBubblePreference(appRow.uid, appRow.pkg);
        appRow.userId = UserHandle.getUserId(appRow.uid);
        try {
            sINM.getBlockedChannelCount(appRow.pkg, appRow.uid);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
        appRow.channelCount = getChannelCount(appRow.uid, appRow.pkg);
        long currentTimeMillis = System.currentTimeMillis();
        try {
            usageEvents =
                    sUsageStatsManager.queryEventsForPackageForUser(
                            currentTimeMillis - 604800000,
                            currentTimeMillis,
                            appRow.userId,
                            appRow.pkg,
                            context.getPackageName());
        } catch (RemoteException e2) {
            e2.printStackTrace();
            usageEvents = null;
        }
        appRow.sentByChannel = new HashMap();
        appRow.sentByApp = new NotificationsSentState();
        if (usageEvents != null) {
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == 12 && (str = event.mNotificationChannelId) != null) {
                    NotificationsSentState notificationsSentState =
                            (NotificationsSentState) ((HashMap) appRow.sentByChannel).get(str);
                    if (notificationsSentState == null) {
                        notificationsSentState = new NotificationsSentState();
                        ((HashMap) appRow.sentByChannel).put(str, notificationsSentState);
                    }
                    if (event.getTimeStamp() > notificationsSentState.lastSent) {
                        notificationsSentState.lastSent = event.getTimeStamp();
                        appRow.sentByApp.lastSent = event.getTimeStamp();
                    }
                    notificationsSentState.sentCount++;
                    appRow.sentByApp.sentCount++;
                    Math.round(notificationsSentState.sentCount / 7.0f);
                }
            }
            if (appRow.sentByApp != null) {
                Math.round(r10.sentCount / 7.0f);
            }
        }
        return appRow;
    }

    public static boolean onlyHasDefaultChannel(int i, String str) {
        try {
            return sINM.onlyHasDefaultChannel(str, i);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static void setAllowBubbles(int i, int i2, String str) {
        try {
            sINM.setBubblesAllowed(str, i, i2);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
    }

    public static void setLockScreenNotificationVisibilityForPackage(int i, int i2, String str) {
        try {
            sINM.setLockScreenNotificationVisibilityForPackage(str, i, i2);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
    }

    public static boolean setNotificationAssistantGranted(ComponentName componentName) {
        try {
            sINM.setNotificationAssistantAccessGranted(componentName, true);
            return componentName == null
                    ? sINM.getAllowedNotificationAssistant() == null
                    : componentName.equals(sINM.getAllowedNotificationAssistant());
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return false;
        }
    }

    public static void setNotificationsEnabledForPackage(int i, String str, boolean z) {
        try {
            if (onlyHasDefaultChannel(i, str)) {
                NotificationChannel channel = getChannel(i, str, "miscellaneous", null);
                channel.setImportance(z ? -1000 : 0);
                updateChannel(str, i, channel);
            }
            sINM.setNotificationsEnabledForPackage(str, i, z);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
    }

    public static void updateChannel(String str, int i, NotificationChannel notificationChannel) {
        try {
            sINM.updateNotificationChannelForPackage(str, i, notificationChannel);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
        }
    }

    public final String getEdgeLightingEnabledList(Context context) {
        boolean z;
        String str;
        PackageManager packageManager = context.getPackageManager();
        List notificationPackagesList = getNotificationPackagesList(context, context.getUserId());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        String string =
                Settings.Secure.getString(
                        context.getContentResolver(), "edge_lighting_recommend_app_list");
        List arrayList3 = new ArrayList();
        if (string != null) {
            arrayList3 = Arrays.asList(string.split(","));
        }
        Iterator it = ((ArrayList) notificationPackagesList).iterator();
        boolean z2 = true;
        while (it.hasNext()) {
            ActivityInfo activityInfo = ((ResolveInfo) it.next()).activityInfo;
            String str2 = activityInfo.name;
            String str3 = activityInfo.packageName;
            try {
                try {
                    z = sINM.isEdgeLightingAllowed(str3, packageManager.getPackageUid(str3, 0));
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
                        Log.e("NotificationBackend", "Error...");
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
        if (z2) {
            return "AllAppsAvailable";
        }
        AnonymousClass1 anonymousClass1 = this.mAppNameComparator;
        Collections.sort(arrayList, anonymousClass1);
        Collections.sort(arrayList2, anonymousClass1);
        Iterator it2 = arrayList2.iterator();
        String str4 = ApnSettings.MVNO_NONE;
        while (it2.hasNext()) {
            String str5 = (String) it2.next();
            str4 =
                    str4.equals(ApnSettings.MVNO_NONE)
                            ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str4, str5)
                            : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    str4, ",", str5);
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            String str6 = (String) it3.next();
            str4 =
                    str4.equals(ApnSettings.MVNO_NONE)
                            ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str4, str6)
                            : AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    str4, ",", str6);
        }
        return str4;
    }

    public void setNm(INotificationManager iNotificationManager) {
        sINM = iNotificationManager;
    }

    public static ParceledListSlice getConversations(boolean z) {
        try {
            return sINM.getConversations(z);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            return ParceledListSlice.emptyList();
        }
    }
}
