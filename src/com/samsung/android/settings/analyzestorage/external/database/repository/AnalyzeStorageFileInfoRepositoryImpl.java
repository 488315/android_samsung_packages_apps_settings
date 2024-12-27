package com.samsung.android.settings.analyzestorage.external.database.repository;

import android.content.Context;
import android.util.SparseArray;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.data.constant.AnalyzeStorageConstants$UiItemType;
import com.samsung.android.settings.analyzestorage.data.database.repository.AnalyzeStorageFileInfoRepository;
import com.samsung.android.settings.analyzestorage.external.database.datasource.MediaProviderDataSource;
import com.samsung.android.settings.analyzestorage.external.database.detector.DatabaseChangeDetector;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.InternalAppCloneStorageQueryHelper;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.InternalKnoxStorageQueryHelper;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.InternalSecureFolderStorageQueryHelper;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.InternalStorageQueryHelper;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.LocalStorageQueryHelper;
import com.samsung.android.settings.analyzestorage.external.database.repository.analyzestorage.SdCardQueryHelper;

import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AnalyzeStorageFileInfoRepositoryImpl extends AnalyzeStorageFileInfoRepository {
    public static final int[] AS_LOCAL_DOMAIN_TYPE = {0, 1, 2, 3, 4};
    public static final List MEDIA_ITEMS =
            Arrays.asList(
                    AnalyzeStorageConstants$UiItemType.IMAGES,
                    AnalyzeStorageConstants$UiItemType.VIDEOS,
                    AnalyzeStorageConstants$UiItemType.AUDIO,
                    AnalyzeStorageConstants$UiItemType.DOCUMENTS,
                    AnalyzeStorageConstants$UiItemType.APK,
                    AnalyzeStorageConstants$UiItemType.COMPRESSED);
    public static volatile AnalyzeStorageFileInfoRepositoryImpl sInstance;
    public final Context mContext;
    public final DatabaseChangeDetector mDatabaseChangeDetector;
    public final SparseArray mQueryHelperMap = new SparseArray();

    public AnalyzeStorageFileInfoRepositoryImpl(
            Context context, MediaProviderDataSource mediaProviderDataSource) {
        LocalStorageQueryHelper internalStorageQueryHelper;
        this.mContext = context;
        this.mDatabaseChangeDetector = new DatabaseChangeDetector(context);
        int[] iArr = AS_LOCAL_DOMAIN_TYPE;
        for (int i = 0; i < 5; i++) {
            int i2 = iArr[i];
            SparseArray sparseArray = this.mQueryHelperMap;
            Context context2 = this.mContext;
            if (i2 == 0) {
                internalStorageQueryHelper =
                        new InternalStorageQueryHelper(context2, mediaProviderDataSource);
            } else if (i2 == 1) {
                internalStorageQueryHelper =
                        new SdCardQueryHelper(context2, mediaProviderDataSource);
            } else if (i2 == 2) {
                internalStorageQueryHelper =
                        new InternalAppCloneStorageQueryHelper(context2, mediaProviderDataSource);
            } else if (i2 == 3) {
                internalStorageQueryHelper =
                        new InternalKnoxStorageQueryHelper(context2, mediaProviderDataSource);
            } else {
                if (i2 != 4) {
                    throw new IllegalArgumentException(
                            LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                    i2,
                                    "QueryHelperFactory createLocalStorageQueryHelper ]"
                                        + " domainType(",
                                    ") is not supported."));
                }
                internalStorageQueryHelper =
                        new InternalSecureFolderStorageQueryHelper(
                                context2, mediaProviderDataSource);
            }
            sparseArray.put(i2, internalStorageQueryHelper);
        }
    }

    public static AnalyzeStorageFileInfoRepository getInstance(
            Context context, MediaProviderDataSource mediaProviderDataSource) {
        if (sInstance == null) {
            synchronized (AnalyzeStorageFileInfoRepositoryImpl.class) {
                try {
                    if (sInstance == null) {
                        sInstance =
                                new AnalyzeStorageFileInfoRepositoryImpl(
                                        context, mediaProviderDataSource);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0113 A[Catch: all -> 0x009a, TryCatch #1 {all -> 0x009a, blocks: (B:11:0x0060, B:13:0x0070, B:14:0x0097, B:103:0x009d, B:105:0x00a1, B:107:0x00b1, B:108:0x00e1, B:110:0x00e4, B:117:0x0113, B:118:0x0132, B:119:0x0168), top: B:10:0x0060 }] */
    @Override // com.samsung.android.settings.analyzestorage.data.database.repository.AnalyzeStorageFileInfoRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.database.Cursor query(
            com.samsung.android.settings.analyzestorage.data.database.repository.abstraction
                            .AbsFileRepository$QueryParams
                    r21) {
        /*
            Method dump skipped, instructions count: 1106
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.external.database.repository.AnalyzeStorageFileInfoRepositoryImpl.query(com.samsung.android.settings.analyzestorage.data.database.repository.abstraction.AbsFileRepository$QueryParams):android.database.Cursor");
    }
}
