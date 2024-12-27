package com.samsung.android.settings.analyzestorage.presenter.controllers.filelist;

import androidx.lifecycle.MutableLiveData;

import com.samsung.android.settings.analyzestorage.data.database.repository.AnalyzeStorageFileInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader$$ExternalSyntheticLambda1;
import com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoaderParams;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ListLoading {
    public final ListItemHandler mListItemInterface;
    public final DataLoader mLoader;
    public final int mSessionId;
    public final MutableLiveData mLoading = new MutableLiveData(Boolean.TRUE);
    public final MutableLiveData mIsEmptyList = new MutableLiveData(Boolean.FALSE);
    public boolean mCancelLoad = false;
    public int mLoadExecutionId = -1;

    public ListLoading(ListItemHandler listItemHandler) {
        int incrementAndGet;
        AtomicInteger atomicInteger = DataLoader.sSessionIdGenerator;
        DataLoader dataLoader = DataLoader.DataLoaderHolder.INSTANCE;
        this.mLoader = dataLoader;
        synchronized (dataLoader) {
            incrementAndGet = DataLoader.sSessionIdGenerator.incrementAndGet();
        }
        this.mSessionId = incrementAndGet;
        this.mListItemInterface = listItemHandler;
    }

    public final void clear() {
        this.mCancelLoad = true;
        DataLoader dataLoader = this.mLoader;
        int i = this.mSessionId;
        dataLoader.getClass();
        synchronized (DataLoader.class) {
            dataLoader.mSessionArgsMap.remove(i);
        }
        int i2 = this.mLoadExecutionId;
        if (i2 > -1) {
            this.mLoader.finishExecution(i2);
        }
    }

    public final void finishLoadingState() {
        turnOffLoading();
        this.mLoader.finishExecution(this.mLoadExecutionId);
        this.mLoadExecutionId = -1;
    }

    public final void loadListItem(
            IDataInfoRepository iDataInfoRepository,
            DataLoaderParams dataLoaderParams,
            DataLoader.IDataLoaderCallback iDataLoaderCallback) {
        int incrementAndGet;
        synchronized (this.mLoader) {
            incrementAndGet = DataLoader.sExecutionIdGenerator.incrementAndGet();
        }
        dataLoaderParams.loadExecutionId = incrementAndGet;
        this.mLoadExecutionId = incrementAndGet;
        DataLoader dataLoader = this.mLoader;
        dataLoader.getClass();
        if (!(iDataInfoRepository instanceof AnalyzeStorageFileInfoRepository)) {
            ThreadExecutor.runOnWorkThread(
                    new DataLoader$$ExternalSyntheticLambda1(
                            DataLoader.createTask(dataLoaderParams, iDataInfoRepository),
                            iDataLoaderCallback));
            return;
        }
        AnalyzeStorageFileInfoRepository analyzeStorageFileInfoRepository =
                (AnalyzeStorageFileInfoRepository) iDataInfoRepository;
        if (analyzeStorageFileInfoRepository == null) {
            Log.e("DataLoader", "execute() : fileInfoRepository is null");
            return;
        }
        Log.i(
                "DataLoader",
                "execute():"
                        + dataLoaderParams.sessionId
                        + "] "
                        + analyzeStorageFileInfoRepository.getClass().getSimpleName());
        DataLoader.SessionArgs sessionArgs =
                new DataLoader.SessionArgs(
                        analyzeStorageFileInfoRepository, dataLoaderParams, iDataLoaderCallback);
        try {
            synchronized (DataLoader.class) {
                dataLoader.mSessionArgsMap.put(dataLoaderParams.sessionId, sessionArgs);
            }
            dataLoader.startTask(dataLoader.getTask(sessionArgs), sessionArgs);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public final void setEmptyList(boolean z) {
        Boolean bool = Boolean.TRUE;
        MutableLiveData mutableLiveData = this.mIsEmptyList;
        if (bool.equals(mutableLiveData.getValue()) != z) {
            mutableLiveData.setValue(Boolean.valueOf(z));
        }
    }

    public final void turnOffLoading() {
        Boolean bool = Boolean.TRUE;
        MutableLiveData mutableLiveData = this.mLoading;
        if (bool.equals(mutableLiveData.getValue())) {
            mutableLiveData.setValue(Boolean.FALSE);
        }
    }
}
