package com.samsung.android.settings.analyzestorage.external.database.repository;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;

import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl;
import com.samsung.android.settings.analyzestorage.data.database.repository.comparator.ComparatorFactory;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppBnRInfo;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.datasource.UnusedAppDataSource;

import kotlin.jvm.functions.Function1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class UnusedAppInfoRepositoryImpl implements IAppInfoRepository {
    public static volatile UnusedAppInfoRepositoryImpl sInstance;
    public final Context mContext;
    public final UnusedAppBnRInfoDao_Impl mUnusedAppBnRInfoDao;
    public final UnusedAppDataSource mUnusedAppDataSource = new UnusedAppDataSource();

    public UnusedAppInfoRepositoryImpl(
            Context context, UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao_Impl) {
        this.mContext = context;
        this.mUnusedAppBnRInfoDao = unusedAppBnRInfoDao_Impl;
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository
    public final List getAppInfoList() {
        List<UnusedAppInfo> unusedAppInfoList =
                this.mUnusedAppDataSource.getUnusedAppInfoList(this.mContext);
        if (System.currentTimeMillis()
                        - PreferenceManager.getDefaultSharedPreferences(this.mContext)
                                .getLong("manage_storage_smart_switch_restore_time", -1L)
                < 2592000000L) {
            ArrayList arrayList = new ArrayList();
            for (UnusedAppInfo unusedAppInfo : unusedAppInfoList) {
                if (!unusedAppInfo.isUnusedApps()) {
                    final String packageName = unusedAppInfo.getPackageName();
                    UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao_Impl = this.mUnusedAppBnRInfoDao;
                    unusedAppBnRInfoDao_Impl.getClass();
                    if (((UnusedAppBnRInfo)
                                    DBUtil.performBlocking(
                                            unusedAppBnRInfoDao_Impl.__db,
                                            true,
                                            false,
                                            new Function1() { // from class:
                                                // com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl$$ExternalSyntheticLambda2
                                                /* JADX WARN: Multi-variable type inference failed */
                                                @Override // kotlin.jvm.functions.Function1
                                                public final Object invoke(Object obj) {
                                                    SQLiteStatement prepare =
                                                            ((SQLiteConnection) obj)
                                                                    .prepare(
                                                                            "SELECT * FROM"
                                                                                + " bnr_unused_apps"
                                                                                + " WHERE"
                                                                                + " package_name ="
                                                                                + " ?");
                                                    String str = packageName;
                                                    try {
                                                        if (str == null) {
                                                            prepare.bindNull(1);
                                                        } else {
                                                            prepare.bindText(1, str);
                                                        }
                                                        int columnIndexOrThrow =
                                                                SQLiteStatementUtil
                                                                        .getColumnIndexOrThrow(
                                                                                prepare,
                                                                                "package_name");
                                                        String str2 = null;
                                                        if (prepare.step()) {
                                                            UnusedAppBnRInfo unusedAppBnRInfo =
                                                                    new UnusedAppBnRInfo();
                                                            if (!prepare.isNull(
                                                                    columnIndexOrThrow)) {
                                                                str2 =
                                                                        prepare.getText(
                                                                                columnIndexOrThrow);
                                                            }
                                                            unusedAppBnRInfo.mPackageName = str2;
                                                            str2 = unusedAppBnRInfo;
                                                        }
                                                        prepare.close();
                                                        return str2;
                                                    } catch (Throwable th) {
                                                        prepare.close();
                                                        throw th;
                                                    }
                                                }
                                            }))
                            != null) {
                        unusedAppInfo.setUnusedApps(true);
                        arrayList.add(unusedAppInfo.getPackageName());
                    }
                }
            }
            Log.d(
                    "UnusedAppInfoRepositoryImpl",
                    "restoreUnusedAppInfoFromBnr ] restore size : "
                            + arrayList.size()
                            + ", names : "
                            + arrayList);
        }
        final int i = 0;
        boolean anyMatch =
                unusedAppInfoList.stream()
                        .anyMatch(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.analyzestorage.external.database.repository.UnusedAppInfoRepositoryImpl$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        UnusedAppInfo unusedAppInfo2 = (UnusedAppInfo) obj;
                                        switch (i) {
                                            case 0:
                                                return unusedAppInfo2.getLastLaunchTime() != 0;
                                            default:
                                                return unusedAppInfo2.isUnusedApps();
                                        }
                                    }
                                });
        int i2 =
                PreferenceManager.getDefaultSharedPreferences(this.mContext)
                        .getInt("sort_type_unused_apps", 0);
        if (!anyMatch && i2 == 2) {
            SharedPreferences.Editor edit =
                    PreferenceManager.getDefaultSharedPreferences(this.mContext).edit();
            edit.putInt("sort_type_unused_apps", 0);
            edit.apply();
            i2 = 0;
        }
        final int i3 = 1;
        return (List)
                unusedAppInfoList.parallelStream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.analyzestorage.external.database.repository.UnusedAppInfoRepositoryImpl$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        UnusedAppInfo unusedAppInfo2 = (UnusedAppInfo) obj;
                                        switch (i3) {
                                            case 0:
                                                return unusedAppInfo2.getLastLaunchTime() != 0;
                                            default:
                                                return unusedAppInfo2.isUnusedApps();
                                        }
                                    }
                                })
                        .sorted(
                                ComparatorFactory.getAppSortComparator(
                                        i2,
                                        PreferenceManager.getDefaultSharedPreferences(this.mContext)
                                                .getBoolean("sort_order_unused_apps", false)))
                        .collect(Collectors.toList());
    }
}
