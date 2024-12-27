package com.samsung.android.settings.analyzestorage.external.database.repository;

import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.samsung.android.settings.analyzestorage.data.database.repository.comparator.ComparatorFactory;
import com.samsung.android.settings.analyzestorage.data.model.ClearCacheAppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.datasource.ClearCacheAppDataSource;
import com.samsung.android.settings.analyzestorage.external.database.datasource.ClearCacheAppDataSource$$ExternalSyntheticLambda1;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageUsageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ClearCacheAppInfoRepositoryImpl implements IAppInfoRepository {
    public static volatile ClearCacheAppInfoRepositoryImpl sInstance;
    public final ClearCacheAppDataSource mClearCacheAppDataSource;
    public final Context mContext;

    public ClearCacheAppInfoRepositoryImpl(Context context) {
        this.mContext = context;
        ClearCacheAppDataSource clearCacheAppDataSource = new ClearCacheAppDataSource();
        clearCacheAppDataSource.mTag = "ClearCacheAppDataSource";
        this.mClearCacheAppDataSource = clearCacheAppDataSource;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository
    public final List getAppInfoList() {
        Context context = this.mContext;
        final ClearCacheAppDataSource clearCacheAppDataSource = this.mClearCacheAppDataSource;
        clearCacheAppDataSource.getClass();
        if (!StorageUsageManager.needToShowAppCacheList(context)) {
            Log.d(
                    clearCacheAppDataSource.mTag,
                    "getClearCacheAppInfoList() ] Do not need to show App Cache List.");
            return new ArrayList();
        }
        final StorageStatsManager storageStatsManager =
                (StorageStatsManager) context.getSystemService(StorageStatsManager.class);
        List targetAppList = clearCacheAppDataSource.getTargetAppList(context);
        Log.d(
                clearCacheAppDataSource.mTag,
                "getClearCacheAppInfoList() ] downloadedAppInfoList size : "
                        + ((ArrayList) targetAppList).size());
        return (List)
                targetAppList.parallelStream()
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.settings.analyzestorage.external.database.datasource.ClearCacheAppDataSource$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        long j;
                                        ClearCacheAppDataSource clearCacheAppDataSource2 =
                                                ClearCacheAppDataSource.this;
                                        StorageStatsManager storageStatsManager2 =
                                                storageStatsManager;
                                        ApplicationInfo applicationInfo = (ApplicationInfo) obj;
                                        clearCacheAppDataSource2.getClass();
                                        ClearCacheAppInfo clearCacheAppInfo =
                                                new ClearCacheAppInfo();
                                        clearCacheAppInfo.setPackageName(
                                                applicationInfo.packageName);
                                        try {
                                            j =
                                                    storageStatsManager2
                                                            .queryStatsForUid(
                                                                    applicationInfo.storageUuid,
                                                                    applicationInfo.uid)
                                                            .getCacheBytes();
                                        } catch (Exception e) {
                                            Log.e(
                                                    "StorageManageHelper",
                                                    "getPackageCacheSize()] Exception e : "
                                                            + e
                                                            + "\n - Package Name : "
                                                            + applicationInfo.packageName);
                                            j = 0;
                                        }
                                        clearCacheAppInfo.setSize(j);
                                        return clearCacheAppInfo;
                                    }
                                })
                        .filter(new ClearCacheAppDataSource$$ExternalSyntheticLambda1())
                        .sorted(ComparatorFactory.getAppSortComparator(0, false))
                        .collect(Collectors.toList());
    }
}
