package com.samsung.android.settings.analyzestorage.external.database.datasource;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;

import androidx.preference.PreferenceManager;

import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;
import com.samsung.android.settings.analyzestorage.presenter.utils.preference.SettingStatusPreferenceUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UnusedAppDataSource extends AbsAppDataSource {
    public UnusedAppDataSource() {
        this.mTag = "UnusedAppDataSource";
    }

    public final List getUnusedAppInfoList(Context context) {
        List targetAppList = getTargetAppList(context);
        if (((ArrayList) targetAppList).isEmpty()) {
            SettingStatusPreferenceUtils.setInternalUnusedApplicationInfo(context, 0, "0MB");
            return Collections.emptyList();
        }
        UsageStatsManager usageStatsManager =
                (UsageStatsManager) context.getSystemService("usagestats");
        long currentTimeMillis = System.currentTimeMillis();
        List<UsageStats> queryUsageStats =
                usageStatsManager.queryUsageStats(
                        2, currentTimeMillis - 31104000000L, currentTimeMillis);
        final HashMap hashMap = new HashMap();
        if (queryUsageStats != null) {
            queryUsageStats.forEach(
                    new Consumer() { // from class:
                                     // com.samsung.android.settings.analyzestorage.external.database.datasource.UnusedAppDataSource$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            UnusedAppDataSource unusedAppDataSource = UnusedAppDataSource.this;
                            Map map = hashMap;
                            UsageStats usageStats = (UsageStats) obj;
                            unusedAppDataSource.getClass();
                            String packageName = usageStats.getPackageName();
                            long lastTimeUsed = usageStats.getLastTimeUsed();
                            Long l = (Long) map.get(packageName);
                            if (l != null || lastTimeUsed > 0) {
                                Log.i(
                                        unusedAppDataSource.mTag,
                                        "getAppLastLaunchTimeMap()] "
                                                + packageName
                                                + " : "
                                                + lastTimeUsed
                                                + "(prev:"
                                                + l
                                                + ")");
                            }
                            if (l != null) {
                                lastTimeUsed = Math.max(lastTimeUsed, l.longValue());
                            }
                            map.put(packageName, Long.valueOf(lastTimeUsed));
                        }
                    });
        }
        final StorageStatsManager storageStatsManager =
                (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
        final PackageManager packageManager = context.getPackageManager();
        final long j =
                context.getSharedPreferences(
                                PreferenceManager.getDefaultSharedPreferencesName(context), 0)
                        .getLong("setup_wizard_complete_time", -1L);
        Log.d(this.mTag, "createUnusedAppInfoList()] setupWizardCompleteTime : " + j);
        return (List)
                targetAppList.parallelStream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.analyzestorage.external.database.datasource.UnusedAppDataSource$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        UnusedAppDataSource unusedAppDataSource =
                                                UnusedAppDataSource.this;
                                        PackageManager packageManager2 = packageManager;
                                        long j2 = j;
                                        StorageStatsManager storageStatsManager2 =
                                                storageStatsManager;
                                        Map map = hashMap;
                                        ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                                        unusedAppDataSource.getClass();
                                        String str = applicationInfo.sourceDir;
                                        long currentTimeMillis2 = System.currentTimeMillis();
                                        long max =
                                                currentTimeMillis2
                                                        - Math.max(
                                                                new FileWrapper(str).lastModified(),
                                                                j2);
                                        Long l = (Long) map.get(applicationInfo.packageName);
                                        long longValue = l == null ? 0L : l.longValue();
                                        boolean z = false;
                                        boolean z2 = currentTimeMillis2 - longValue > 2592000000L;
                                        StorageStats storageStats = null;
                                        if (!z2) {
                                            return null;
                                        }
                                        if (max > 2592000000L && z2) {
                                            z = true;
                                        }
                                        UnusedAppInfo unusedAppInfo = new UnusedAppInfo();
                                        unusedAppInfo.setPackageName(applicationInfo.packageName);
                                        unusedAppInfo.setLastLaunchTime(longValue);
                                        unusedAppInfo.setUnusedApps(z);
                                        unusedAppInfo.setLabel(
                                                packageManager2
                                                        .getApplicationLabel(applicationInfo)
                                                        .toString());
                                        try {
                                            storageStats =
                                                    storageStatsManager2.queryStatsForPackage(
                                                            applicationInfo.storageUuid,
                                                            applicationInfo.packageName,
                                                            Process.myUserHandle());
                                        } catch (Exception e) {
                                            Log.e(
                                                    "StorageManageHelper",
                                                    "getPackageStorageStats ] : " + e);
                                        }
                                        if (storageStats != null) {
                                            unusedAppInfo.setPackageAppByte(
                                                    storageStats.getAppBytes());
                                            unusedAppInfo.setSize(
                                                    storageStats.getDataBytes()
                                                            + storageStats.getAppBytes());
                                        }
                                        return unusedAppInfo;
                                    }
                                })
                        .filter(new UnusedAppDataSource$$ExternalSyntheticLambda2())
                        .collect(Collectors.toList());
    }

    @Override // com.samsung.android.settings.analyzestorage.external.database.datasource.AbsAppDataSource
    public final boolean isSuitableTargetApp(
            PackageManager packageManager, ApplicationInfo applicationInfo) {
        int i = applicationInfo.flags;
        return ((i & 1) == 0 && (i & 128) == 0)
                && (packageManager.getLaunchIntentForPackage(applicationInfo.packageName) != null);
    }
}
