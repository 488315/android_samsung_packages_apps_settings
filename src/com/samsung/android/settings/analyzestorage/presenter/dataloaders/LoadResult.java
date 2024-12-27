package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LoadResult {
    public List appDataList;
    public List data;
    public AbsMyFilesException.ErrorType errorType;
    public final Bundle extras = new Bundle();
    public Bundle groupInfo;
    public int loadExecutionId;
    public boolean success;

    public final List getAppDataList() {
        List list = this.appDataList;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException("appDataList");
        throw null;
    }
}
