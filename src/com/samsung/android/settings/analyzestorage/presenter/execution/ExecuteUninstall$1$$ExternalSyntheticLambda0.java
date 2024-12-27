package com.samsung.android.settings.analyzestorage.presenter.execution;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.FileOperationResult;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ExecuteUninstall$1$$ExternalSyntheticLambda0 {
    public final /* synthetic */ ExecuteUninstall.AnonymousClass1 f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ AtomicInteger f$2;
    public final /* synthetic */ ExecutionParams f$3;
    public final /* synthetic */ List f$4;
    public final /* synthetic */ IExecutable f$5;
    public final /* synthetic */ int f$6;
    public final /* synthetic */ ScheduledExecutorService f$7;

    public /* synthetic */ ExecuteUninstall$1$$ExternalSyntheticLambda0(
            ExecuteUninstall.AnonymousClass1 anonymousClass1,
            int i,
            AtomicInteger atomicInteger,
            ExecutionParams executionParams,
            List list,
            IExecutable iExecutable,
            int i2,
            ScheduledExecutorService scheduledExecutorService) {
        this.f$0 = anonymousClass1;
        this.f$1 = i;
        this.f$2 = atomicInteger;
        this.f$3 = executionParams;
        this.f$4 = list;
        this.f$5 = iExecutable;
        this.f$6 = i2;
        this.f$7 = scheduledExecutorService;
    }

    public final void onPackageDeleted(String str, final boolean z) {
        AtomicInteger atomicInteger = this.f$2;
        final List list = this.f$4;
        ScheduledExecutorService scheduledExecutorService = this.f$7;
        ExecuteUninstall.AnonymousClass1 anonymousClass1 = this.f$0;
        if (z) {
            anonymousClass1.getClass();
        } else {
            Log.w(
                    anonymousClass1.this$0.tag,
                    "PackageDeleteListener ] " + str + " uninstall fail.");
        }
        if (this.f$1 == atomicInteger.incrementAndGet()) {
            final ExecutionParams executionParams = this.f$3;
            executionParams.mProgressListener.onFinishProgress();
            final IExecutable iExecutable = this.f$5;
            final int i = this.f$6;
            ThreadExecutor.runOnMainThread(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteUninstall$1$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean z2 = z;
                            List list2 = list;
                            IExecutable iExecutable2 = iExecutable;
                            int i2 = i;
                            ExecutionParams executionParams2 = executionParams;
                            FileOperationResult fileOperationResult = new FileOperationResult();
                            fileOperationResult.mIsSuccess = z2;
                            fileOperationResult.mNeedRefresh = z2;
                            fileOperationResult.mBundle.putLong(
                                    "unused_app_freed_up_size",
                                    list2.stream()
                                            .mapToLong(
                                                    new ExecuteClearAppCache$1$$ExternalSyntheticLambda3())
                                            .sum());
                            MenuExecuteManager menuExecuteManager =
                                    (MenuExecuteManager) iExecutable2;
                            menuExecuteManager.getClass();
                            menuExecuteManager.onResult(
                                    fileOperationResult,
                                    i2,
                                    executionParams2,
                                    Collections.emptyList());
                        }
                    });
            scheduledExecutorService.shutdown();
        }
    }
}
