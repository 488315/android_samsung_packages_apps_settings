package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AppDataLoaderTask extends AbsDataLoaderTask {
    @Override // com.samsung.android.settings.analyzestorage.presenter.dataloaders.AbsDataLoaderTask
    public final void loadInBackground(LoadResult loadResult) {
        Bundle bundle;
        List appInfoList = ((IAppInfoRepository) this.repository).getAppInfoList();
        Intrinsics.checkNotNullExpressionValue(appInfoList, "getAppInfoList(...)");
        loadResult.data = appInfoList;
        int size = appInfoList.size();
        if (size > 0) {
            bundle = new Bundle();
            bundle.putInt("childCount", size);
        } else {
            bundle = null;
        }
        loadResult.groupInfo = bundle;
        List list = loadResult.data;
        if (list != null) {
            loadResult.appDataList = list;
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("data");
            throw null;
        }
    }
}
