package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.content.Context;
import android.content.pm.IPackageDeleteObserver;

import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteUninstall extends AbsExecute {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteUninstall$1, reason: invalid class name */
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
            ArrayList arrayList = (ArrayList) this.val$params.mFileOperationArgs.mSelectedApps;
            if (arrayList.isEmpty()) {
                return;
            }
            ScheduledExecutorService newSingleThreadScheduledExecutor =
                    Executors.newSingleThreadScheduledExecutor();
            int size = arrayList.size();
            AtomicInteger atomicInteger = new AtomicInteger();
            ExecutionParams executionParams = this.val$params;
            final ExecuteUninstall$1$$ExternalSyntheticLambda0
                    executeUninstall$1$$ExternalSyntheticLambda0 =
                            new ExecuteUninstall$1$$ExternalSyntheticLambda0(
                                    this,
                                    size,
                                    atomicInteger,
                                    executionParams,
                                    arrayList,
                                    this.val$executable,
                                    this.val$menuType,
                                    newSingleThreadScheduledExecutor);
            executionParams.mProgressListener.onPrepareProgress(executionParams.mFileOperationArgs);
            Iterator it = arrayList.iterator();
            int i = 0;
            while (it.hasNext()) {
                final AppInfo appInfo = (AppInfo) it.next();
                final Context context = this.val$context;
                long j = i;
                newSingleThreadScheduledExecutor.schedule(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteUninstall$1$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Context context2 = context;
                                AppInfo appInfo2 = appInfo;
                                final ExecuteUninstall$1$$ExternalSyntheticLambda0
                                        executeUninstall$1$$ExternalSyntheticLambda02 =
                                                executeUninstall$1$$ExternalSyntheticLambda0;
                                String packageName = ((CommonAppInfo) appInfo2).getPackageName();
                                try {
                                    context2.getPackageManager()
                                            .deletePackage(
                                                    packageName,
                                                    new IPackageDeleteObserver.Stub(
                                                            executeUninstall$1$$ExternalSyntheticLambda02) { // from class: com.samsung.android.settings.analyzestorage.presenter.utils.PackageDeleteUtils$PackageDeleteObserver
                                                        public final
                                                        ExecuteUninstall$1$$ExternalSyntheticLambda0
                                                                mPackageDeleteListener;

                                                        {
                                                            this.mPackageDeleteListener =
                                                                    executeUninstall$1$$ExternalSyntheticLambda02;
                                                        }

                                                        public final void packageDeleted(
                                                                String str, int i2) {
                                                            boolean z = i2 == 1;
                                                            ExecuteUninstall$1$$ExternalSyntheticLambda0
                                                                    executeUninstall$1$$ExternalSyntheticLambda03 =
                                                                            this
                                                                                    .mPackageDeleteListener;
                                                            if (executeUninstall$1$$ExternalSyntheticLambda03
                                                                    != null) {
                                                                executeUninstall$1$$ExternalSyntheticLambda03
                                                                        .onPackageDeleted(str, z);
                                                            }
                                                            if (z) {
                                                                return;
                                                            }
                                                            Log.w(
                                                                    "PackageDeleteUtils",
                                                                    "packageDeleted ] package = "
                                                                            + str
                                                                            + " result "
                                                                            + i2);
                                                        }
                                                    },
                                                    2);
                                } catch (Exception e) {
                                    Log.w(
                                            "PackageDeleteUtils",
                                            "deletePackage ] deletePackage is fail.");
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
