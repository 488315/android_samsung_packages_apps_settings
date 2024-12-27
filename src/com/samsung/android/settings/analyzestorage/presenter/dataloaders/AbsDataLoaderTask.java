package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.os.Bundle;

import com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbsDataLoaderTask {
    public int id;
    public boolean needRefresh;
    public PageInfo pageInfo;
    public final IDataInfoRepository repository;

    public AbsDataLoaderTask(
            DataLoaderParams dataLoaderParams, IDataInfoRepository dataInfoRepository) {
        Intrinsics.checkNotNullParameter(dataInfoRepository, "dataInfoRepository");
        this.id = dataLoaderParams.loadExecutionId;
        PageInfo pageInfo = dataLoaderParams.pageInfo;
        if (pageInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageInfo");
            throw null;
        }
        this.pageInfo = pageInfo;
        this.needRefresh = dataLoaderParams.forceUpdate;
        this.repository = dataInfoRepository;
    }

    public final LoadResult doInBackground() {
        LoadResult loadResult = new LoadResult();
        loadResult.success = true;
        loadResult.loadExecutionId = this.id;
        loadResult.groupInfo = new Bundle();
        try {
            loadInBackground(loadResult);
        } catch (AbsMyFilesException e) {
            loadResult.success = false;
            loadResult.errorType = e.getExceptionType();
            loadResult.extras.putString("message", e.getDetailMessage());
            int i = e.getInt(-1);
            if (i != -1) {
                loadResult.extras.putInt("messageRes", i);
                loadResult.extras.putString("details", e.getDetailMessage());
            }
            Log.e(
                    "AbsDataLoaderTask",
                    "doInBackground() - LOAD_FILES : "
                            + e.getMessage()
                            + " "
                            + loadResult.errorType);
        }
        return loadResult;
    }

    public abstract void loadInBackground(LoadResult loadResult);
}
