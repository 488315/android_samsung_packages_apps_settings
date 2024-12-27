package com.samsung.android.settings.analyzestorage.data.database.repository;

import android.database.Cursor;

import com.samsung.android.settings.analyzestorage.data.database.repository.abstraction.AbsFileRepository$QueryParams;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AnalyzeStorageFileInfoRepository implements IDataInfoRepository {
    public abstract Cursor query(AbsFileRepository$QueryParams absFileRepository$QueryParams);
}
