package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.content.Context;
import android.content.pm.IPackageDataObserver;

import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.fileoperation.FileOperationArgs;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteClearAppCache extends AbsExecute {
    public static final List mSuccessList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteClearAppCache$1, reason: invalid class name */
    class AnonymousClass1 implements UserInteractionDialog$Callback {
        final /* synthetic */ Context val$context;
        final /* synthetic */ IExecutable val$executable;
        final /* synthetic */ int val$menuType;
        final /* synthetic */ ExecutionParams val$params;

        public AnonymousClass1(
                ExecutionParams executionParams,
                MenuExecuteManager menuExecuteManager,
                int i,
                Context context) {
            this.val$params = executionParams;
            this.val$executable = menuExecuteManager;
            this.val$menuType = i;
            this.val$context = context;
        }

        @Override // com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback
        public final void onOk() {
            FileOperationArgs fileOperationArgs = this.val$params.mFileOperationArgs;
            List<AppInfo> emptyList =
                    fileOperationArgs == null
                            ? Collections.emptyList()
                            : fileOperationArgs.mSelectedApps;
            if (emptyList.isEmpty()) {
                return;
            }
            ((ArrayList) ExecuteClearAppCache.mSuccessList).clear();
            ScheduledExecutorService newSingleThreadScheduledExecutor =
                    Executors.newSingleThreadScheduledExecutor();
            int size = emptyList.size();
            AtomicInteger atomicInteger = new AtomicInteger();
            ExecutionParams executionParams = this.val$params;
            final ExecuteClearAppCache$1$$ExternalSyntheticLambda0
                    executeClearAppCache$1$$ExternalSyntheticLambda0 =
                            new ExecuteClearAppCache$1$$ExternalSyntheticLambda0(
                                    this,
                                    size,
                                    atomicInteger,
                                    executionParams,
                                    emptyList,
                                    this.val$executable,
                                    this.val$menuType,
                                    newSingleThreadScheduledExecutor);
            executionParams.mProgressListener.onPrepareProgress(executionParams.mFileOperationArgs);
            int i = 0;
            for (final AppInfo appInfo : emptyList) {
                final Context context = this.val$context;
                long j = i;
                newSingleThreadScheduledExecutor.schedule(
                        new Runnable() { // from class:
                            // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteClearAppCache$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Context context2 = context;
                                AppInfo appInfo2 = appInfo;
                                final ExecuteClearAppCache$1$$ExternalSyntheticLambda0
                                        executeClearAppCache$1$$ExternalSyntheticLambda02 =
                                                executeClearAppCache$1$$ExternalSyntheticLambda0;
                                String packageName = ((CommonAppInfo) appInfo2).getPackageName();
                                try {
                                    context2.getPackageManager()
                                            .deleteApplicationCacheFiles(
                                                    packageName,
                                                    new IPackageDataObserver.Stub(
                                                            executeClearAppCache$1$$ExternalSyntheticLambda02) { // from class: com.samsung.android.settings.analyzestorage.presenter.utils.PackageCacheDeleteUtils$PackageDataObserver
                                                        public final
                                                        ExecuteClearAppCache$1$$ExternalSyntheticLambda0
                                                                mPackageDataListener;

                                                        {
                                                            this.mPackageDataListener =
                                                                    executeClearAppCache$1$$ExternalSyntheticLambda02;
                                                        }

                                                        public final void onRemoveCompleted(
                                                                String str, boolean z) {
                                                            ExecuteClearAppCache$1$$ExternalSyntheticLambda0
                                                                    executeClearAppCache$1$$ExternalSyntheticLambda03 =
                                                                            this
                                                                                    .mPackageDataListener;
                                                            if (executeClearAppCache$1$$ExternalSyntheticLambda03
                                                                    != null) {
                                                                executeClearAppCache$1$$ExternalSyntheticLambda03
                                                                        .onRemoveCompleted(str, z);
                                                            }
                                                            if (z) {
                                                                return;
                                                            }
                                                            Log.w(
                                                                    "PackageCacheDeleteUtils",
                                                                    "onRemoveCompleted() ]"
                                                                        + " Application cache is"
                                                                        + " not deleted."
                                                                        + " PackageName : "
                                                                            + str);
                                                        }
                                                    });
                                } catch (Exception e) {
                                    Log.w(
                                            "PackageCacheDeleteUtils",
                                            "deletePackageCache() ] Exception e : "
                                                    + e.getMessage()
                                                    + "\n - target packageName : "
                                                    + packageName);
                                    e.printStackTrace();
                                }
                            }
                        },
                        j,
                        TimeUnit.MILLISECONDS);
                i = (int) (j + 50);
            }
        }
    }

    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute
    public final boolean onExecute(
            int i, ExecutionParams executionParams, MenuExecuteManager menuExecuteManager) {
        Context context = executionParams.mContext;
        AbsDialog absDialog = executionParams.mDialog;
        if (absDialog == null) {
            return false;
        }
        absDialog.showDialog(new AnonymousClass1(executionParams, menuExecuteManager, i, context));
        return true;
    }
}
