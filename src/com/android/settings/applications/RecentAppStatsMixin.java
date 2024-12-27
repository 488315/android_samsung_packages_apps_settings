package com.android.settings.applications;

import android.app.Application;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.ArraySet;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settingslib.applications.AppUtils;
import com.android.settingslib.applications.ApplicationsState;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RecentAppStatsMixin implements LifecycleObserver, OnStart {
    public static final Set SKIP_SYSTEM_PACKAGES;
    public final ApplicationsState mApplicationsState;
    public Calendar mCalendar;
    public final Context mContext;
    public final PackageManager mPm;
    public final PowerManager mPowerManager;
    public final UserManager mUserManager;
    List<UsageStatsWrapper> mRecentApps = new ArrayList();
    public final List mAppStatsListeners = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UsageStatsWrapper {
        public final UsageStats mUsageStats;
        public final int mUserId;

        public UsageStatsWrapper(UsageStats usageStats, int i) {
            this.mUsageStats = usageStats;
            this.mUserId = i;
        }

        public final String toString() {
            return "UsageStatsWrapper(pkg:"
                    + this.mUsageStats.getPackageName()
                    + ",uid:"
                    + this.mUserId
                    + ")";
        }
    }

    static {
        ArraySet arraySet = new ArraySet();
        SKIP_SYSTEM_PACKAGES = arraySet;
        arraySet.addAll(
                Arrays.asList(
                        RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME,
                        "com.android.phone",
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.systemui",
                        "com.android.providers.calendar",
                        "com.android.providers.media"));
    }

    public RecentAppStatsMixin(Context context) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mApplicationsState =
                ApplicationsState.getInstance((Application) context.getApplicationContext());
    }

    public void loadDisplayableRecentApps(int i) {
        Optional map;
        this.mRecentApps.clear();
        Calendar calendar = Calendar.getInstance();
        this.mCalendar = calendar;
        calendar.add(6, -1);
        ArrayList arrayList = new ArrayList();
        for (UserHandle userHandle : this.mUserManager.getUserProfiles()) {
            final int identifier = userHandle.getIdentifier();
            if (userHandle.getIdentifier() == UserHandle.myUserId()) {
                final int i2 = 0;
                map =
                        Optional.ofNullable(userHandle)
                                .map(
                                        new Function(
                                                this) { // from class:
                                                        // com.android.settings.applications.RecentAppStatsMixin$$ExternalSyntheticLambda1
                                            public final /* synthetic */ RecentAppStatsMixin f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                int i3 = i2;
                                                RecentAppStatsMixin recentAppStatsMixin = this.f$0;
                                                UserHandle userHandle2 = (UserHandle) obj;
                                                switch (i3) {
                                                    case 0:
                                                        return (UsageStatsManager)
                                                                recentAppStatsMixin.mContext
                                                                        .getSystemService(
                                                                                UsageStatsManager
                                                                                        .class);
                                                    default:
                                                        return (UsageStatsManager)
                                                                recentAppStatsMixin
                                                                        .mContext
                                                                        .createContextAsUser(
                                                                                userHandle2, 0)
                                                                        .getSystemService(
                                                                                UsageStatsManager
                                                                                        .class);
                                                }
                                            }
                                        });
            } else {
                final int i3 = 1;
                map =
                        Optional.ofNullable(userHandle)
                                .map(
                                        new Function(
                                                this) { // from class:
                                                        // com.android.settings.applications.RecentAppStatsMixin$$ExternalSyntheticLambda1
                                            public final /* synthetic */ RecentAppStatsMixin f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                int i32 = i3;
                                                RecentAppStatsMixin recentAppStatsMixin = this.f$0;
                                                UserHandle userHandle2 = (UserHandle) obj;
                                                switch (i32) {
                                                    case 0:
                                                        return (UsageStatsManager)
                                                                recentAppStatsMixin.mContext
                                                                        .getSystemService(
                                                                                UsageStatsManager
                                                                                        .class);
                                                    default:
                                                        return (UsageStatsManager)
                                                                recentAppStatsMixin
                                                                        .mContext
                                                                        .createContextAsUser(
                                                                                userHandle2, 0)
                                                                        .getSystemService(
                                                                                UsageStatsManager
                                                                                        .class);
                                                }
                                            }
                                        });
            }
            arrayList.addAll(
                    (Collection)
                            ((List)
                                            map.map(
                                                            new Function() { // from class:
                                                                // com.android.settings.applications.RecentAppStatsMixin$$ExternalSyntheticLambda3
                                                                @Override // java.util.function.Function
                                                                public final Object apply(
                                                                        Object obj) {
                                                                    String str;
                                                                    ApplicationsState.AppEntry
                                                                            entry;
                                                                    ApplicationInfo applicationInfo;
                                                                    RecentAppStatsMixin
                                                                            recentAppStatsMixin =
                                                                                    RecentAppStatsMixin
                                                                                            .this;
                                                                    int i4 = identifier;
                                                                    List<UsageStats> arrayList2 =
                                                                            recentAppStatsMixin
                                                                                            .mPowerManager
                                                                                            .isPowerSaveMode()
                                                                                    ? new ArrayList<>()
                                                                                    : ((UsageStatsManager)
                                                                                                    obj)
                                                                                            .queryUsageStats(
                                                                                                    4,
                                                                                                    recentAppStatsMixin
                                                                                                            .mCalendar
                                                                                                            .getTimeInMillis(),
                                                                                                    System
                                                                                                            .currentTimeMillis());
                                                                    ArrayMap arrayMap =
                                                                            new ArrayMap();
                                                                    for (UsageStats usageStats :
                                                                            arrayList2) {
                                                                        String packageName =
                                                                                usageStats
                                                                                        .getPackageName();
                                                                        if (usageStats
                                                                                        .getLastTimeUsed()
                                                                                < recentAppStatsMixin
                                                                                        .mCalendar
                                                                                        .getTimeInMillis()) {
                                                                            str =
                                                                                    "Invalid"
                                                                                        + " timestamp"
                                                                                        + " (usage"
                                                                                        + " time is"
                                                                                        + " more"
                                                                                        + " than 24"
                                                                                        + " hours"
                                                                                        + " ago),"
                                                                                        + " skipping"
                                                                                        + " ";
                                                                        } else if (((ArraySet)
                                                                                        RecentAppStatsMixin
                                                                                                .SKIP_SYSTEM_PACKAGES)
                                                                                .contains(
                                                                                        packageName)) {
                                                                            str =
                                                                                    "System"
                                                                                        + " package,"
                                                                                        + " skipping"
                                                                                        + " ";
                                                                        } else {
                                                                            Context context =
                                                                                    recentAppStatsMixin
                                                                                            .mContext;
                                                                            Intent intent =
                                                                                    AppUtils
                                                                                            .sBrowserIntent;
                                                                            Boolean bool =
                                                                                    (Boolean)
                                                                                            ApplicationsState
                                                                                                    .getInstance(
                                                                                                            (Application)
                                                                                                                    context
                                                                                                                            .getApplicationContext())
                                                                                                    .mSystemModules
                                                                                                    .get(
                                                                                                            packageName);
                                                                            if (!(bool == null
                                                                                            ? false
                                                                                            : bool
                                                                                                    .booleanValue())
                                                                                    && (entry =
                                                                                                    recentAppStatsMixin
                                                                                                            .mApplicationsState
                                                                                                            .getEntry(
                                                                                                                    i4,
                                                                                                                    packageName))
                                                                                            != null) {
                                                                                if (recentAppStatsMixin
                                                                                                        .mPm
                                                                                                        .resolveActivityAsUser(
                                                                                                                new Intent()
                                                                                                                        .addCategory(
                                                                                                                                "android.intent.category.LAUNCHER")
                                                                                                                        .setPackage(
                                                                                                                                packageName),
                                                                                                                0,
                                                                                                                i4)
                                                                                                != null
                                                                                        || ((applicationInfo =
                                                                                                                entry.info)
                                                                                                        != null
                                                                                                && AppUtils
                                                                                                        .isInstant(
                                                                                                                applicationInfo))) {
                                                                                    String
                                                                                            packageName2 =
                                                                                                    usageStats
                                                                                                            .getPackageName();
                                                                                    UsageStats
                                                                                            usageStats2 =
                                                                                                    (UsageStats)
                                                                                                            arrayMap
                                                                                                                    .get(
                                                                                                                            packageName2);
                                                                                    if (usageStats2
                                                                                            == null) {
                                                                                        arrayMap
                                                                                                .put(
                                                                                                        packageName2,
                                                                                                        usageStats);
                                                                                    } else {
                                                                                        usageStats2
                                                                                                .add(
                                                                                                        usageStats);
                                                                                    }
                                                                                } else {
                                                                                    str =
                                                                                            "Not a"
                                                                                                + " user"
                                                                                                + " visible"
                                                                                                + " or instant"
                                                                                                + " app,"
                                                                                                + " skipping"
                                                                                                + " ";
                                                                                }
                                                                            }
                                                                        }
                                                                        DialogFragment$$ExternalSyntheticOutline0
                                                                                .m(
                                                                                        str,
                                                                                        packageName,
                                                                                        "RecentAppStatsMixin");
                                                                    }
                                                                    ArrayList arrayList3 =
                                                                            new ArrayList(
                                                                                    arrayMap
                                                                                            .values());
                                                                    arrayList3.sort(
                                                                            Comparator
                                                                                    .comparingLong(
                                                                                            new RecentAppStatsMixin$$ExternalSyntheticLambda5(
                                                                                                    1))
                                                                                    .reversed());
                                                                    return arrayList3;
                                                                }
                                                            })
                                                    .orElse(new ArrayList()))
                                    .stream()
                                            .map(
                                                    new Function() { // from class:
                                                        // com.android.settings.applications.RecentAppStatsMixin$$ExternalSyntheticLambda4
                                                        @Override // java.util.function.Function
                                                        public final Object apply(Object obj) {
                                                            return new RecentAppStatsMixin
                                                                    .UsageStatsWrapper(
                                                                    (UsageStats) obj, identifier);
                                                        }
                                                    })
                                            .collect(Collectors.toList()));
        }
        arrayList.sort(
                Comparator.comparingLong(new RecentAppStatsMixin$$ExternalSyntheticLambda5(0)));
        this.mRecentApps.addAll(
                (Collection) arrayList.stream().limit(i).collect(Collectors.toList()));
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public final void onStart() {
        ThreadUtils.postOnBackgroundThread(
                new RecentAppStatsMixin$$ExternalSyntheticLambda7(this, 1));
    }
}
