package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import android.os.Handler;
import android.util.SparseArray;

import com.samsung.android.settings.analyzestorage.data.database.repository.AnalyzeStorageFileInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.repository.IAppInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.repository.IDataInfoRepository;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;

import kotlin.jvm.internal.Intrinsics;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DataLoader {
    public static final AtomicInteger sSessionIdGenerator = new AtomicInteger(0);
    public static final AtomicInteger sExecutionIdGenerator = new AtomicInteger(0);
    public final SparseArray mTaskMap = new SparseArray();
    public final SparseArray mSessionArgsMap = new SparseArray();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class DataLoaderHolder {
        public static final DataLoader INSTANCE = new DataLoader();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface IDataLoaderCallback {
        void onLoadFinished(LoadResult loadResult);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SessionArgs {
        public final WeakReference mLoaderCallbackWeakReference;
        public final DataLoaderParams mLoaderParams;
        public final AnalyzeStorageFileInfoRepository mRepository;

        public SessionArgs(
                AnalyzeStorageFileInfoRepository analyzeStorageFileInfoRepository,
                DataLoaderParams dataLoaderParams,
                IDataLoaderCallback iDataLoaderCallback) {
            this.mRepository = analyzeStorageFileInfoRepository;
            this.mLoaderParams = dataLoaderParams;
            this.mLoaderCallbackWeakReference = new WeakReference(iDataLoaderCallback);
        }
    }

    public static AbsDataLoaderTask createTask(
            DataLoaderParams dataLoaderParams, IDataInfoRepository appInfoRepository) {
        AbsDataLoaderTask absDataLoaderTask;
        if (appInfoRepository instanceof AnalyzeStorageFileInfoRepository) {
            AnalyzeStorageFileInfoRepository fileInfoRepository =
                    (AnalyzeStorageFileInfoRepository) appInfoRepository;
            Intrinsics.checkNotNullParameter(fileInfoRepository, "fileInfoRepository");
            absDataLoaderTask = new AnalyzeStorageLoaderTask(dataLoaderParams, fileInfoRepository);
            Log.d(
                    "AnalyzeStorageLoaderTask",
                    "AnalyzeStorageLoaderTask - page info: "
                            + absDataLoaderTask.pageInfo.mPageType.name());
        } else if (appInfoRepository instanceof IAppInfoRepository) {
            Intrinsics.checkNotNullParameter(appInfoRepository, "appInfoRepository");
            absDataLoaderTask =
                    new AppDataLoaderTask(dataLoaderParams, (IAppInfoRepository) appInfoRepository);
        } else {
            absDataLoaderTask = null;
        }
        if (absDataLoaderTask != null) {
            return absDataLoaderTask;
        }
        StringBuilder sb = new StringBuilder("There is no loader task for ");
        PageInfo pageInfo = dataLoaderParams.pageInfo;
        if (pageInfo != null) {
            sb.append(pageInfo.mPageType);
            throw new IllegalArgumentException(sb.toString());
        }
        Intrinsics.throwUninitializedPropertyAccessException("pageInfo");
        throw null;
    }

    public final void finishExecution(int i) {
        Reference reference = (Reference) this.mTaskMap.get(i);
        AbsDataLoaderTask absDataLoaderTask =
                reference == null ? null : (AbsDataLoaderTask) reference.get();
        if (absDataLoaderTask != null) {
            this.mTaskMap.remove(i);
            Log.i(
                    "DataLoader",
                    "finishExecution ] task : "
                            + absDataLoaderTask.pageInfo.mPageType.name()
                            + ", task id : "
                            + absDataLoaderTask.id
                            + ", taskMap size : "
                            + this.mTaskMap.size());
        }
    }

    public final AbsDataLoaderTask getTask(SessionArgs sessionArgs) {
        DataLoaderParams dataLoaderParams = sessionArgs.mLoaderParams;
        int i = dataLoaderParams.loadExecutionId;
        StringBuilder sb = new StringBuilder("getTask ] sessionId : ");
        sb.append(dataLoaderParams.loadExecutionId);
        sb.append(", loader : ");
        PageInfo pageInfo = dataLoaderParams.pageInfo;
        if (pageInfo == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageInfo");
            throw null;
        }
        sb.append(pageInfo.mPageType.name());
        sb.append(", mTaskMap size : ");
        sb.append(this.mTaskMap.size());
        Log.i("DataLoader", sb.toString());
        AnalyzeStorageFileInfoRepository analyzeStorageFileInfoRepository = sessionArgs.mRepository;
        if (i < 0) {
            return createTask(dataLoaderParams, analyzeStorageFileInfoRepository);
        }
        Reference reference = (Reference) this.mTaskMap.get(i);
        AbsDataLoaderTask absDataLoaderTask =
                reference == null ? null : (AbsDataLoaderTask) reference.get();
        if (absDataLoaderTask == null) {
            Log.i("DataLoader", "getTask ] task : null");
            AbsDataLoaderTask createTask =
                    createTask(dataLoaderParams, analyzeStorageFileInfoRepository);
            this.mTaskMap.put(i, new SoftReference(createTask));
            return createTask;
        }
        Log.i("DataLoader", "getTask ] task : " + absDataLoaderTask.pageInfo.mPageType.name());
        absDataLoaderTask.id = dataLoaderParams.loadExecutionId;
        PageInfo pageInfo2 = dataLoaderParams.pageInfo;
        if (pageInfo2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("pageInfo");
            throw null;
        }
        absDataLoaderTask.pageInfo = pageInfo2;
        absDataLoaderTask.needRefresh = dataLoaderParams.forceUpdate;
        return absDataLoaderTask;
    }

    public final void startTask(
            final AbsDataLoaderTask absDataLoaderTask, final SessionArgs sessionArgs) {
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.samsung.android.settings.analyzestorage.presenter.dataloaders.DataLoader$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DataLoader dataLoader = DataLoader.this;
                        AbsDataLoaderTask absDataLoaderTask2 = absDataLoaderTask;
                        DataLoader.SessionArgs sessionArgs2 = sessionArgs;
                        dataLoader.getClass();
                        LoadResult doInBackground = absDataLoaderTask2.doInBackground();
                        DataLoader.IDataLoaderCallback iDataLoaderCallback =
                                (DataLoader.IDataLoaderCallback)
                                        sessionArgs2.mLoaderCallbackWeakReference.get();
                        synchronized (DataLoader.class) {
                            try {
                                if (dataLoader.mSessionArgsMap.indexOfKey(
                                                sessionArgs2.mLoaderParams.sessionId)
                                        < 0) {
                                    return;
                                }
                                if (iDataLoaderCallback != null) {
                                    ThreadExecutor.runOnMainThread(
                                            new DataLoader$$ExternalSyntheticLambda1(
                                                    iDataLoaderCallback, doInBackground, 1));
                                }
                            } finally {
                            }
                        }
                    }
                };
        String str = "data-loader-thread-" + sessionArgs.mLoaderParams.sessionId;
        Handler handler = ThreadExecutor.sMainHandler;
        try {
            new Thread(runnable, str).start();
        } catch (OutOfMemoryError e) {
            Log.e("ThreadExecutor", "runOnWorkThread() ] OutOfMemoryError : " + e);
            runnable.run();
        }
    }
}
