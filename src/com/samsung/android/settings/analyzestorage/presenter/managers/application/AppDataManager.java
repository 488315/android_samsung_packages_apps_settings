package com.samsung.android.settings.analyzestorage.presenter.managers.application;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;

import androidx.compose.animation.Scale$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AppDataManager {
    public static final ConcurrentHashMap appsDataMap = new ConcurrentHashMap();
    public static List applicationInfo = new CopyOnWriteArrayList();
    public static int lastCalculatedAppCount = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppDataInfo {
        public long cache;
        public String packageName;
        public long packageSize;
        public long size;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AppDataInfo)) {
                return false;
            }
            AppDataInfo appDataInfo = (AppDataInfo) obj;
            return Intrinsics.areEqual(this.packageName, appDataInfo.packageName)
                    && this.packageSize == appDataInfo.packageSize
                    && this.size == appDataInfo.size
                    && this.cache == appDataInfo.cache;
        }

        public final int hashCode() {
            return Long.hashCode(this.cache)
                    + Scale$$ExternalSyntheticOutline0.m(
                            Scale$$ExternalSyntheticOutline0.m(
                                    this.packageName.hashCode() * 31, 31, this.packageSize),
                            31,
                            this.size);
        }

        public final String toString() {
            return "AppDataInfo(packageName="
                    + this.packageName
                    + ", packageSize="
                    + this.packageSize
                    + ", size="
                    + this.size
                    + ", cache="
                    + this.cache
                    + ")";
        }
    }

    public static long getAppsSize(final Context context) {
        AppDataInterface$AppData appDataInterface$AppData = AppDataInterface$AppData.TOTAL;
        Intrinsics.checkNotNullParameter(context, "context");
        final TotalAppsData totalAppsData = new TotalAppsData();
        final PackageManager packageManager = context.getPackageManager();
        if (applicationInfo.isEmpty()) {
            List<ApplicationInfo> installedApplications =
                    packageManager.getInstalledApplications(9856);
            Intrinsics.checkNotNullExpressionValue(
                    installedApplications, "getInstalledApplications(...)");
            applicationInfo = installedApplications;
        }
        HashMap hashMap = new HashMap(applicationInfo.size());
        for (ApplicationInfo applicationInfo2 : applicationInfo) {
            if (!hashMap.containsKey(Integer.valueOf(applicationInfo2.uid))) {
                hashMap.put(Integer.valueOf(applicationInfo2.uid), applicationInfo2);
            }
        }
        List list =
                (List)
                        hashMap.values().parallelStream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataManager$getAppsSize$filteredList$1
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                ApplicationInfo appInfo = (ApplicationInfo) obj;
                                                TotalAppsData totalAppsData2 = TotalAppsData.this;
                                                String packageName = appInfo.packageName;
                                                Intrinsics.checkNotNullExpressionValue(
                                                        packageName, "packageName");
                                                totalAppsData2.getClass();
                                                if (ArraysKt___ArraysKt.contains(
                                                        packageName,
                                                        TotalAppsData.APP_SIZE_SKIP_LIST)) {
                                                    return false;
                                                }
                                                TotalAppsData totalAppsData3 = TotalAppsData.this;
                                                PackageManager pm = packageManager;
                                                Intrinsics.checkNotNullExpressionValue(pm, "$pm");
                                                totalAppsData3.getClass();
                                                Intrinsics.checkNotNullParameter(pm, "pm");
                                                Intrinsics.checkNotNullParameter(
                                                        appInfo, "appInfo");
                                                return true;
                                            }
                                        })
                                .collect(Collectors.toList());
        AppDataInterface$AppData appDataInterface$AppData2 = AppDataInterface$AppData.TOTAL;
        lastCalculatedAppCount = list.size();
        long currentTimeMillis = System.currentTimeMillis();
        long sum =
                list.parallelStream()
                        .mapToLong(
                                new ToLongFunction() { // from class:
                                    // com.samsung.android.settings.analyzestorage.presenter.managers.application.AppDataManager$getAppsSize$createAppDataMapTime$1$1
                                    @Override // java.util.function.ToLongFunction
                                    public final long applyAsLong(Object obj) {
                                        ApplicationInfo applicationInfo3 = (ApplicationInfo) obj;
                                        ConcurrentHashMap concurrentHashMap =
                                                AppDataManager.appsDataMap;
                                        if (!concurrentHashMap.containsKey(
                                                applicationInfo3.packageName)) {
                                            String packageName = applicationInfo3.packageName;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    packageName, "packageName");
                                            TotalAppsData totalAppsData2 = TotalAppsData.this;
                                            Context context2 = context;
                                            totalAppsData2.getClass();
                                            Intrinsics.checkNotNullParameter(context2, "context");
                                            AppDataManager.AppDataInfo appDataInfo =
                                                    new AppDataManager.AppDataInfo();
                                            appDataInfo.packageName = ApnSettings.MVNO_NONE;
                                            appDataInfo.packageSize = 0L;
                                            appDataInfo.size = 0L;
                                            appDataInfo.cache = 0L;
                                            try {
                                                Object systemService =
                                                        context2.getSystemService("storagestats");
                                                Intrinsics.checkNotNull(
                                                        systemService,
                                                        "null cannot be cast to non-null type"
                                                            + " android.app.usage.StorageStatsManager");
                                                StorageStatsManager storageStatsManager =
                                                        (StorageStatsManager) systemService;
                                                StorageStats queryStatsForUid =
                                                        storageStatsManager.queryStatsForUid(
                                                                applicationInfo3.storageUuid,
                                                                applicationInfo3.uid);
                                                Intrinsics.checkNotNullExpressionValue(
                                                        queryStatsForUid, "queryStatsForUid(...)");
                                                StorageStats queryStatsForPackage =
                                                        storageStatsManager.queryStatsForPackage(
                                                                applicationInfo3.storageUuid,
                                                                applicationInfo3.packageName,
                                                                Process.myUserHandle());
                                                Intrinsics.checkNotNullExpressionValue(
                                                        queryStatsForPackage,
                                                        "queryStatsForPackage(...)");
                                                long appBytes =
                                                        queryStatsForUid.getAppBytes()
                                                                + queryStatsForUid.dataBytes;
                                                appDataInfo.size = appBytes;
                                                appDataInfo.cache =
                                                        appBytes > 0
                                                                ? queryStatsForUid.cacheBytes
                                                                : 0L;
                                                String packageName2 = applicationInfo3.packageName;
                                                Intrinsics.checkNotNullExpressionValue(
                                                        packageName2, "packageName");
                                                appDataInfo.packageName = packageName2;
                                                appDataInfo.packageSize =
                                                        queryStatsForPackage.getAppBytes()
                                                                + queryStatsForPackage.dataBytes;
                                            } catch (Exception e) {
                                                e.getStackTrace();
                                            }
                                            concurrentHashMap.put(packageName, appDataInfo);
                                        }
                                        TotalAppsData totalAppsData3 = TotalAppsData.this;
                                        AppDataManager.AppDataInfo appDataInfo2 =
                                                (AppDataManager.AppDataInfo)
                                                        AppDataManager.appsDataMap.get(
                                                                applicationInfo3.packageName);
                                        totalAppsData3.getClass();
                                        if (appDataInfo2 != null) {
                                            return appDataInfo2.size;
                                        }
                                        return 0L;
                                    }
                                })
                        .sum();
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "getAppsSize() ] applicationInfo.size = ",
                        ", lastCalculatedAppCount = ",
                        applicationInfo.size(),
                        lastCalculatedAppCount,
                        ", createAppDataMapTime = ");
        m.append(currentTimeMillis2);
        Log.d("AppDataManager", m.toString());
        return sum;
    }
}
