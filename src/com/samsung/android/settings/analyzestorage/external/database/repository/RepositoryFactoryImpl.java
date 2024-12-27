package com.samsung.android.settings.analyzestorage.external.database.repository;

import android.content.Context;

import com.samsung.android.settings.analyzestorage.data.database.dao.UnusedAppBnRInfoDao_Impl;
import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.ManageStorageDatabase;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RepositoryFactoryImpl {
    public static Context sContext;

    public static IAppInfoRepository getAppRepository(Object obj) {
        PageType pageType = (PageType) obj;
        pageType.getClass();
        if (pageType == PageType.ANALYZE_STORAGE_UNUSED_APPS) {
            Context context = sContext;
            UnusedAppBnRInfoDao_Impl unusedAppBnRInfoDao =
                    ManageStorageDatabase.getInstance(context).unusedAppBnRInfoDao();
            if (UnusedAppInfoRepositoryImpl.sInstance == null) {
                synchronized (UnusedAppInfoRepositoryImpl.class) {
                    try {
                        if (UnusedAppInfoRepositoryImpl.sInstance == null) {
                            UnusedAppInfoRepositoryImpl.sInstance =
                                    new UnusedAppInfoRepositoryImpl(context, unusedAppBnRInfoDao);
                        }
                    } finally {
                    }
                }
            }
            return UnusedAppInfoRepositoryImpl.sInstance;
        }
        if (pageType != PageType.ANALYZE_STORAGE_APP_CACHE) {
            return null;
        }
        Context context2 = sContext;
        if (ClearCacheAppInfoRepositoryImpl.sInstance == null) {
            synchronized (UnusedAppInfoRepositoryImpl.class) {
                try {
                    if (ClearCacheAppInfoRepositoryImpl.sInstance == null) {
                        ClearCacheAppInfoRepositoryImpl.sInstance =
                                new ClearCacheAppInfoRepositoryImpl(context2);
                    }
                } finally {
                }
            }
        }
        return ClearCacheAppInfoRepositoryImpl.sInstance;
    }
}
