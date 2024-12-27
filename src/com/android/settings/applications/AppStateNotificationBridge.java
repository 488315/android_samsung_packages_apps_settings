package com.android.settings.applications;

import android.app.NotificationChannel;
import android.app.usage.IUsageStatsManager;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.notification.NotificationBackend;
import com.android.settingslib.SecNotificationBlockManager;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppStateNotificationBridge extends AppStateBaseBridge {
    public static final AnonymousClass1 FILTER_APP_NOTIFICATION_BLOCKED;
    public static final AnonymousClass1 FILTER_APP_NOTIFICATION_FREQUENCY;
    public static final AnonymousClass1 FILTER_APP_NOTIFICATION_RECENCY;
    public static final AnonymousClass1 FILTER_APP_NOTI_ALL;
    public static final AnonymousClass1 FILTER_APP_NOTI_BLOCKABLE;
    public static final AnonymousClass7 FREQUENCY_NOTIFICATION_COMPARATOR;
    public static final AnonymousClass7 RECENT_NOTIFICATION_COMPARATOR;
    public final NotificationBackend mBackend;
    public final Context mContext;
    public final IUsageStatsManager mUsageStatsManager;
    public final List mUserIds;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class NotificationsSentState {
        public boolean allChannelsNonBlockable;
        public boolean blockable;
        public boolean blocked;
        public boolean hasChannels;
        public boolean isPermissionDeclared;
        public int avgSentDaily = 0;
        public int avgSentWeekly = 0;
        public long lastSent = 0;
        public int sentCount = 0;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.applications.AppStateNotificationBridge$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.applications.AppStateNotificationBridge$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.settings.applications.AppStateNotificationBridge$1] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.applications.AppStateNotificationBridge$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.applications.AppStateNotificationBridge$1] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.settings.applications.AppStateNotificationBridge$7] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.settings.applications.AppStateNotificationBridge$7] */
    static {
        final int i = 0;
        FILTER_APP_NOTIFICATION_RECENCY =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateNotificationBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i) {
                            case 0:
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState == null
                                                || notificationsSentState.lastSent == 0
                                                || !notificationsSentState.hasChannels)
                                        ? false
                                        : true;
                            case 1:
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState2 == null
                                                || notificationsSentState2.sentCount == 0
                                                || !notificationsSentState2.hasChannels)
                                        ? false
                                        : true;
                            case 2:
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState3 != null
                                        && notificationsSentState3.blocked
                                        && notificationsSentState3.hasChannels;
                            case 3:
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState4 != null
                                        && notificationsSentState4.isPermissionDeclared
                                        && notificationsSentState4.hasChannels;
                            default:
                                NotificationsSentState notificationsSentState5 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                if (notificationsSentState5 != null) {
                                    return notificationsSentState5.blockable;
                                }
                                return false;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i2 = i;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$3() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$4() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$5() {}
                };
        final int i2 = 1;
        FILTER_APP_NOTIFICATION_FREQUENCY =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateNotificationBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i2) {
                            case 0:
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState == null
                                                || notificationsSentState.lastSent == 0
                                                || !notificationsSentState.hasChannels)
                                        ? false
                                        : true;
                            case 1:
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState2 == null
                                                || notificationsSentState2.sentCount == 0
                                                || !notificationsSentState2.hasChannels)
                                        ? false
                                        : true;
                            case 2:
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState3 != null
                                        && notificationsSentState3.blocked
                                        && notificationsSentState3.hasChannels;
                            case 3:
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState4 != null
                                        && notificationsSentState4.isPermissionDeclared
                                        && notificationsSentState4.hasChannels;
                            default:
                                NotificationsSentState notificationsSentState5 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                if (notificationsSentState5 != null) {
                                    return notificationsSentState5.blockable;
                                }
                                return false;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i2;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$3() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$4() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$5() {}
                };
        final int i3 = 2;
        FILTER_APP_NOTIFICATION_BLOCKED =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateNotificationBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i3) {
                            case 0:
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState == null
                                                || notificationsSentState.lastSent == 0
                                                || !notificationsSentState.hasChannels)
                                        ? false
                                        : true;
                            case 1:
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState2 == null
                                                || notificationsSentState2.sentCount == 0
                                                || !notificationsSentState2.hasChannels)
                                        ? false
                                        : true;
                            case 2:
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState3 != null
                                        && notificationsSentState3.blocked
                                        && notificationsSentState3.hasChannels;
                            case 3:
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState4 != null
                                        && notificationsSentState4.isPermissionDeclared
                                        && notificationsSentState4.hasChannels;
                            default:
                                NotificationsSentState notificationsSentState5 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                if (notificationsSentState5 != null) {
                                    return notificationsSentState5.blockable;
                                }
                                return false;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i3;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$3() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$4() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$5() {}
                };
        final int i4 = 3;
        FILTER_APP_NOTI_ALL =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateNotificationBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i4) {
                            case 0:
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState == null
                                                || notificationsSentState.lastSent == 0
                                                || !notificationsSentState.hasChannels)
                                        ? false
                                        : true;
                            case 1:
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState2 == null
                                                || notificationsSentState2.sentCount == 0
                                                || !notificationsSentState2.hasChannels)
                                        ? false
                                        : true;
                            case 2:
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState3 != null
                                        && notificationsSentState3.blocked
                                        && notificationsSentState3.hasChannels;
                            case 3:
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState4 != null
                                        && notificationsSentState4.isPermissionDeclared
                                        && notificationsSentState4.hasChannels;
                            default:
                                NotificationsSentState notificationsSentState5 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                if (notificationsSentState5 != null) {
                                    return notificationsSentState5.blockable;
                                }
                                return false;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i4;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$3() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$4() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$5() {}
                };
        final int i5 = 4;
        FILTER_APP_NOTI_BLOCKABLE =
                new ApplicationsState
                        .AppFilter() { // from class:
                                       // com.android.settings.applications.AppStateNotificationBridge.1
                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final boolean filterApp(ApplicationsState.AppEntry appEntry) {
                        switch (i5) {
                            case 0:
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState == null
                                                || notificationsSentState.lastSent == 0
                                                || !notificationsSentState.hasChannels)
                                        ? false
                                        : true;
                            case 1:
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return (notificationsSentState2 == null
                                                || notificationsSentState2.sentCount == 0
                                                || !notificationsSentState2.hasChannels)
                                        ? false
                                        : true;
                            case 2:
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState3 != null
                                        && notificationsSentState3.blocked
                                        && notificationsSentState3.hasChannels;
                            case 3:
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                return notificationsSentState4 != null
                                        && notificationsSentState4.isPermissionDeclared
                                        && notificationsSentState4.hasChannels;
                            default:
                                NotificationsSentState notificationsSentState5 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                if (notificationsSentState5 != null) {
                                    return notificationsSentState5.blockable;
                                }
                                return false;
                        }
                    }

                    @Override // com.android.settingslib.applications.ApplicationsState.AppFilter
                    public final void init() {
                        int i22 = i5;
                    }

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$1() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$2() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$3() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$4() {}

                    private final void
                            init$com$android$settings$applications$AppStateNotificationBridge$5() {}
                };
        final int i6 = 0;
        RECENT_NOTIFICATION_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settings.applications.AppStateNotificationBridge.7
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i6) {
                            case 0:
                                ApplicationsState.AppEntry appEntry =
                                        (ApplicationsState.AppEntry) obj;
                                ApplicationsState.AppEntry appEntry2 =
                                        (ApplicationsState.AppEntry) obj2;
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry2);
                                if (notificationsSentState == null
                                        && notificationsSentState2 != null) {
                                    return -1;
                                }
                                if (notificationsSentState == null
                                        || notificationsSentState2 != null) {
                                    if (notificationsSentState != null
                                            && notificationsSentState2 != null) {
                                        long j = notificationsSentState.lastSent;
                                        long j2 = notificationsSentState2.lastSent;
                                        if (j >= j2) {
                                            if (j > j2) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return ApplicationsState.ALPHA_COMPARATOR.compare(
                                            appEntry, appEntry2);
                                }
                                return 1;
                            default:
                                ApplicationsState.AppEntry appEntry3 =
                                        (ApplicationsState.AppEntry) obj;
                                ApplicationsState.AppEntry appEntry4 =
                                        (ApplicationsState.AppEntry) obj2;
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry3);
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry4);
                                if (notificationsSentState3 == null
                                        && notificationsSentState4 != null) {
                                    return -1;
                                }
                                if (notificationsSentState3 == null
                                        || notificationsSentState4 != null) {
                                    if (notificationsSentState3 != null
                                            && notificationsSentState4 != null) {
                                        int i7 = notificationsSentState3.sentCount;
                                        int i8 = notificationsSentState4.sentCount;
                                        if (i7 >= i8) {
                                            if (i7 > i8) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return ApplicationsState.ALPHA_COMPARATOR.compare(
                                            appEntry3, appEntry4);
                                }
                                return 1;
                        }
                    }
                };
        final int i7 = 1;
        FREQUENCY_NOTIFICATION_COMPARATOR =
                new Comparator() { // from class:
                                   // com.android.settings.applications.AppStateNotificationBridge.7
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        switch (i7) {
                            case 0:
                                ApplicationsState.AppEntry appEntry =
                                        (ApplicationsState.AppEntry) obj;
                                ApplicationsState.AppEntry appEntry2 =
                                        (ApplicationsState.AppEntry) obj2;
                                NotificationsSentState notificationsSentState =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry);
                                NotificationsSentState notificationsSentState2 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry2);
                                if (notificationsSentState == null
                                        && notificationsSentState2 != null) {
                                    return -1;
                                }
                                if (notificationsSentState == null
                                        || notificationsSentState2 != null) {
                                    if (notificationsSentState != null
                                            && notificationsSentState2 != null) {
                                        long j = notificationsSentState.lastSent;
                                        long j2 = notificationsSentState2.lastSent;
                                        if (j >= j2) {
                                            if (j > j2) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return ApplicationsState.ALPHA_COMPARATOR.compare(
                                            appEntry, appEntry2);
                                }
                                return 1;
                            default:
                                ApplicationsState.AppEntry appEntry3 =
                                        (ApplicationsState.AppEntry) obj;
                                ApplicationsState.AppEntry appEntry4 =
                                        (ApplicationsState.AppEntry) obj2;
                                NotificationsSentState notificationsSentState3 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry3);
                                NotificationsSentState notificationsSentState4 =
                                        AppStateNotificationBridge.getNotificationsSentState(
                                                appEntry4);
                                if (notificationsSentState3 == null
                                        && notificationsSentState4 != null) {
                                    return -1;
                                }
                                if (notificationsSentState3 == null
                                        || notificationsSentState4 != null) {
                                    if (notificationsSentState3 != null
                                            && notificationsSentState4 != null) {
                                        int i72 = notificationsSentState3.sentCount;
                                        int i8 = notificationsSentState4.sentCount;
                                        if (i72 >= i8) {
                                            if (i72 > i8) {
                                                return -1;
                                            }
                                        }
                                    }
                                    return ApplicationsState.ALPHA_COMPARATOR.compare(
                                            appEntry3, appEntry4);
                                }
                                return 1;
                        }
                    }
                };
    }

    public AppStateNotificationBridge(
            FragmentActivity fragmentActivity,
            ApplicationsState applicationsState,
            AppStateBaseBridge.Callback callback,
            IUsageStatsManager iUsageStatsManager,
            UserManager userManager,
            NotificationBackend notificationBackend) {
        super(applicationsState, callback);
        this.mContext = fragmentActivity;
        this.mUsageStatsManager = iUsageStatsManager;
        this.mBackend = notificationBackend;
        ArrayList arrayList = new ArrayList();
        this.mUserIds = arrayList;
        arrayList.add(Integer.valueOf(fragmentActivity.getUserId()));
        int managedProfileId = Utils.getManagedProfileId(userManager, fragmentActivity.getUserId());
        if (managedProfileId != -10000) {
            arrayList.add(Integer.valueOf(managedProfileId));
        }
    }

    public static String getKey(int i, String str) {
        return i + "|" + str;
    }

    public static NotificationsSentState getNotificationsSentState(
            ApplicationsState.AppEntry appEntry) {
        Object obj;
        if (appEntry == null
                || (obj = appEntry.extraInfo) == null
                || !(obj instanceof NotificationsSentState)) {
            return null;
        }
        return (NotificationsSentState) obj;
    }

    public static CharSequence getSummary(
            Context context, NotificationsSentState notificationsSentState, int i) {
        return i == R.id.sort_order_recent_notification
                ? notificationsSentState.lastSent == 0
                        ? context.getString(R.string.notifications_sent_never)
                        : StringUtil.formatRelativeTime(
                                context,
                                System.currentTimeMillis() - notificationsSentState.lastSent,
                                true,
                                RelativeDateTimeFormatter.Style.LONG)
                : i == R.id.sort_order_frequent_notification
                        ? notificationsSentState.avgSentDaily > 0
                                ? context.getResources()
                                        .getString(
                                                R.string.sec_notifications_sent_daily,
                                                Integer.valueOf(
                                                        notificationsSentState.avgSentDaily))
                                : context.getResources()
                                        .getString(
                                                R.string.sec_notifications_sent_weekly,
                                                Integer.valueOf(
                                                        notificationsSentState.avgSentWeekly))
                        : ApnSettings.MVNO_NONE;
    }

    public final void allNotificationChannelsNonBlockable(
            NotificationsSentState notificationsSentState, String str, int i) {
        ParceledListSlice emptyList;
        this.mBackend.getClass();
        try {
            emptyList = NotificationBackend.sINM.getNotificationChannelsForPackage(str, i, false);
        } catch (Exception e) {
            Log.w("NotificationBackend", "Error calling NoMan", e);
            emptyList = ParceledListSlice.emptyList();
        }
        if (notificationsSentState.blockable) {
            notificationsSentState.allChannelsNonBlockable = false;
            return;
        }
        for (int i2 = 0; i2 < emptyList.getList().size(); i2++) {
            if (SecNotificationBlockManager.isBlockableNotificationChannel(
                    this.mContext, str, (NotificationChannel) emptyList.getList().get(i2))) {
                notificationsSentState.allChannelsNonBlockable = false;
                return;
            }
        }
        notificationsSentState.allChannelsNonBlockable = true;
    }

    public final void isNotificationPermissionDeclared(
            NotificationsSentState notificationsSentState, String str, int i) {
        try {
            notificationsSentState.isPermissionDeclared = true;
            PackageInfo packageInfoAsUser =
                    this.mContext
                            .getPackageManager()
                            .getPackageInfoAsUser(str, 4096, UserHandle.getUserId(i));
            if (packageInfoAsUser.applicationInfo.targetSdkVersion > 32) {
                String[] strArr = packageInfoAsUser.requestedPermissions;
                if (strArr != null
                        && !Arrays.stream(strArr)
                                .noneMatch(
                                        new AppStateNotificationBridge$$ExternalSyntheticLambda0())) {
                    return;
                }
                notificationsSentState.isPermissionDeclared = false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x009d A[SYNTHETIC] */
    @Override // com.android.settings.applications.AppStateBaseBridge
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadAllExtraInfo() {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.applications.AppStateNotificationBridge.loadAllExtraInfo():void");
    }

    @Override // com.android.settings.applications.AppStateBaseBridge
    public final void updateExtraInfo(ApplicationsState.AppEntry appEntry, String str, int i) {
        UsageEvents usageEvents;
        int userId = UserHandle.getUserId(appEntry.info.uid);
        String str2 = appEntry.info.packageName;
        long currentTimeMillis = System.currentTimeMillis();
        NotificationsSentState notificationsSentState = null;
        try {
            usageEvents =
                    this.mUsageStatsManager.queryEventsForPackageForUser(
                            currentTimeMillis - 604800000,
                            currentTimeMillis,
                            userId,
                            str2,
                            this.mContext.getPackageName());
        } catch (RemoteException e) {
            e.printStackTrace();
            usageEvents = null;
        }
        if (usageEvents != null) {
            UsageEvents.Event event = new UsageEvents.Event();
            while (usageEvents.hasNextEvent()) {
                usageEvents.getNextEvent(event);
                if (event.getEventType() == 12) {
                    if (notificationsSentState == null) {
                        notificationsSentState = new NotificationsSentState();
                    }
                    if (event.getTimeStamp() > notificationsSentState.lastSent) {
                        notificationsSentState.lastSent = event.getTimeStamp();
                    }
                    notificationsSentState.sentCount++;
                }
            }
        }
        if (notificationsSentState == null) {
            notificationsSentState = new NotificationsSentState();
        }
        notificationsSentState.avgSentDaily = Math.round(notificationsSentState.sentCount / 7.0f);
        int i2 = notificationsSentState.sentCount;
        if (i2 < 7) {
            notificationsSentState.avgSentWeekly = i2;
        }
        ApplicationInfo applicationInfo = appEntry.info;
        String str3 = applicationInfo.packageName;
        int i3 = applicationInfo.uid;
        this.mBackend.getClass();
        notificationsSentState.blocked = NotificationBackend.getNotificationsBanned(i3, str3);
        notificationsSentState.blockable =
                SecNotificationBlockManager.isBlockablePackage(
                        this.mContext, appEntry.info.packageName);
        notificationsSentState.hasChannels =
                (this.mBackend != null ? NotificationBackend.getChannelCount(i, str) : 0) > 0;
        allNotificationChannelsNonBlockable(notificationsSentState, str, i);
        ApplicationInfo applicationInfo2 = appEntry.info;
        isNotificationPermissionDeclared(
                notificationsSentState, applicationInfo2.packageName, applicationInfo2.uid);
        appEntry.extraInfo = notificationsSentState;
    }
}
