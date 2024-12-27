package com.samsung.android.settings.analyzestorage.presenter.dataloaders;

import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class DataLoader$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DataLoader.IDataLoaderCallback f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DataLoader$$ExternalSyntheticLambda1(
            AbsDataLoaderTask absDataLoaderTask,
            DataLoader.IDataLoaderCallback iDataLoaderCallback) {
        this.$r8$classId = 2;
        this.f$1 = absDataLoaderTask;
        this.f$0 = iDataLoaderCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.onLoadFinished((LoadResult) this.f$1);
                break;
            case 1:
                this.f$0.onLoadFinished((LoadResult) this.f$1);
                break;
            default:
                AbsDataLoaderTask absDataLoaderTask = (AbsDataLoaderTask) this.f$1;
                DataLoader.IDataLoaderCallback iDataLoaderCallback = this.f$0;
                LoadResult doInBackground = absDataLoaderTask.doInBackground();
                if (iDataLoaderCallback != null) {
                    ThreadExecutor.runOnMainThread(
                            new DataLoader$$ExternalSyntheticLambda1(
                                    iDataLoaderCallback, doInBackground, 0));
                    break;
                }
                break;
        }
    }

    public /* synthetic */ DataLoader$$ExternalSyntheticLambda1(
            DataLoader.IDataLoaderCallback iDataLoaderCallback, LoadResult loadResult, int i) {
        this.$r8$classId = i;
        this.f$0 = iDataLoaderCallback;
        this.f$1 = loadResult;
    }
}
