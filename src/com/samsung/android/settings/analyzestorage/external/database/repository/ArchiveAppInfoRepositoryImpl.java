package com.samsung.android.settings.analyzestorage.external.database.repository;

import android.content.Context;
import android.content.pm.PackageManager;

import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.datasource.UnusedAppDataSource;
import com.samsung.android.settings.analyzestorage.presenter.managers.ArchiveManager;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ArchiveAppInfoRepositoryImpl implements IAppInfoRepository {
    public static final Companion Companion = new Companion();
    public static volatile ArchiveAppInfoRepositoryImpl instance;
    public final Context context;
    public final UnusedAppDataSource unusedAppDataSource;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    public ArchiveAppInfoRepositoryImpl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.unusedAppDataSource = new UnusedAppDataSource();
    }

    @Override // com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository
    public final List getAppInfoList() {
        List unusedAppInfoList = this.unusedAppDataSource.getUnusedAppInfoList(this.context);
        Intrinsics.checkNotNullExpressionValue(unusedAppInfoList, "getUnusedAppInfoList(...)");
        final PackageManager packageManager = this.context.getPackageManager();
        Object collect =
                unusedAppInfoList.parallelStream()
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.analyzestorage.external.database.repository.ArchiveAppInfoRepositoryImpl$getAppInfoList$1
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        UnusedAppInfo unusedAppInfo = (UnusedAppInfo) obj;
                                        if (unusedAppInfo.isUnusedApps()) {
                                            PackageManager packageManager2 = packageManager;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    packageManager2, "$packageManager");
                                            String packageName = unusedAppInfo.getPackageName();
                                            Intrinsics.checkNotNullExpressionValue(
                                                    packageName, "getPackageName(...)");
                                            if (ArchiveManager.isArchivalApp(
                                                    packageManager2, packageName)) {
                                                return true;
                                            }
                                        }
                                        return false;
                                    }
                                })
                        .collect(Collectors.toList());
        Intrinsics.checkNotNullExpressionValue(collect, "collect(...)");
        return (List) collect;
    }
}
