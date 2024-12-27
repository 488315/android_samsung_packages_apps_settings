package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Process;

import com.samsung.android.settings.analyzestorage.data.model.CommonAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.FileOperationResult;
import com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback;
import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;
import com.samsung.android.util.SemLog;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteArchive extends AbsExecute {
    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute
    public final boolean onExecute(
            final int i,
            final ExecutionParams executionParams,
            final MenuExecuteManager menuExecuteManager) {
        final Context context = executionParams.mContext;
        AbsDialog absDialog = executionParams.mDialog;
        if (absDialog == null) {
            return false;
        }
        absDialog.showDialog(
                new UserInteractionDialog$Callback() { // from class:
                    // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteArchive$onExecute$1
                    @Override // com.samsung.android.settings.analyzestorage.domain.usecase.userinteraction.UserInteractionDialog$Callback
                    public final void onOk() {
                        final ArrayList<AppInfo> arrayList =
                                (ArrayList) ExecutionParams.this.mFileOperationArgs.mSelectedApps;
                        if (arrayList.isEmpty()) {
                            return;
                        }
                        final AtomicInteger atomicInteger = new AtomicInteger();
                        IntentFilter intentFilter =
                                new IntentFilter("com.android.settings.archive.action");
                        final ExecutionParams executionParams2 = ExecutionParams.this;
                        final IExecutable iExecutable = menuExecuteManager;
                        final int i2 = i;
                        context.registerReceiverAsUser(
                                new BroadcastReceiver() { // from class:
                                    // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteArchive$onExecute$1$onOk$receiver$1
                                    @Override // android.content.BroadcastReceiver
                                    public final void onReceive(
                                            final Context context2, Intent intent) {
                                        Intrinsics.checkNotNullParameter(context2, "context");
                                        Intrinsics.checkNotNullParameter(intent, "intent");
                                        if (arrayList.size() == atomicInteger.incrementAndGet()) {
                                            final ExecutionParams executionParams3 =
                                                    executionParams2;
                                            final IExecutable iExecutable2 = iExecutable;
                                            final int i3 = i2;
                                            ThreadExecutor.runOnMainThread(
                                                    new Runnable() { // from class:
                                                        // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteArchive$onExecute$1$onOk$receiver$1$onReceive$1
                                                        @Override // java.lang.Runnable
                                                        public final void run() {
                                                            ExecutionParams.this.mProgressListener
                                                                    .onFinishProgress();
                                                            FileOperationResult
                                                                    fileOperationResult =
                                                                            new FileOperationResult();
                                                            fileOperationResult.mIsSuccess = true;
                                                            fileOperationResult.mNeedRefresh = true;
                                                            IExecutable iExecutable3 = iExecutable2;
                                                            int i4 = i3;
                                                            ExecutionParams executionParams4 =
                                                                    ExecutionParams.this;
                                                            MenuExecuteManager menuExecuteManager2 =
                                                                    (MenuExecuteManager)
                                                                            iExecutable3;
                                                            menuExecuteManager2.getClass();
                                                            menuExecuteManager2.onResult(
                                                                    fileOperationResult,
                                                                    i4,
                                                                    executionParams4,
                                                                    Collections.emptyList());
                                                            context2.unregisterReceiver(this);
                                                        }
                                                    });
                                        }
                                    }
                                },
                                Process.myUserHandle(),
                                intentFilter,
                                null,
                                null);
                        ExecutionParams executionParams3 = ExecutionParams.this;
                        executionParams3.mProgressListener.onPrepareProgress(
                                executionParams3.mFileOperationArgs);
                        ScheduledExecutorService newSingleThreadScheduledExecutor =
                                Executors.newSingleThreadScheduledExecutor();
                        final Context context2 = context;
                        long j = 0;
                        for (final AppInfo appInfo : arrayList) {
                            newSingleThreadScheduledExecutor.schedule(
                                    new Runnable() { // from class:
                                        // com.samsung.android.settings.analyzestorage.presenter.execution.ExecuteArchive$onExecute$1$onOk$1$1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            Context context3 = context2;
                                            Intrinsics.checkNotNullExpressionValue(
                                                    context3, "$context");
                                            String packageName =
                                                    ((CommonAppInfo) appInfo).getPackageName();
                                            Intrinsics.checkNotNullExpressionValue(
                                                    packageName, "getPackageName(...)");
                                            PackageManager packageManager =
                                                    context3.getPackageManager();
                                            Intent intent =
                                                    new Intent(
                                                            "com.android.settings.archive.action");
                                            intent.setPackage(context3.getPackageName());
                                            try {
                                                packageManager
                                                        .getPackageInstaller()
                                                        .requestArchive(
                                                                packageName,
                                                                PendingIntent.getBroadcastAsUser(
                                                                                context3,
                                                                                0,
                                                                                intent,
                                                                                33554432,
                                                                                Process
                                                                                        .myUserHandle())
                                                                        .getIntentSender());
                                            } catch (PackageManager.NameNotFoundException e) {
                                                SemLog.e(
                                                        "ArchiveManager",
                                                        "requestArchiveApp() ]"
                                                            + " NameNotFoundException : "
                                                                + e.getMessage());
                                            }
                                        }
                                    },
                                    j,
                                    TimeUnit.MILLISECONDS);
                            j += 50;
                        }
                    }
                });
        return true;
    }
}
