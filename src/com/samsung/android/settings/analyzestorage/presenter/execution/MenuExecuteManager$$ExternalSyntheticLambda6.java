package com.samsung.android.settings.analyzestorage.presenter.execution;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class MenuExecuteManager$$ExternalSyntheticLambda6
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (((MenuExecuteManager$$ExternalSyntheticLambda0) obj).$r8$classId) {
            case 0:
                ExecuteUninstall executeUninstall = new ExecuteUninstall();
                executeUninstall.tag = "ExecuteUninstall";
                return executeUninstall;
            case 1:
                ExecuteClearAppCache executeClearAppCache = new ExecuteClearAppCache();
                executeClearAppCache.tag = "ExecuteClearAppCache";
                return executeClearAppCache;
            case 2:
                ExecuteUnmount executeUnmount = new ExecuteUnmount();
                executeUnmount.tag = "ExecuteUnmount";
                return executeUnmount;
            case 3:
                ExecuteFormat executeFormat = new ExecuteFormat();
                executeFormat.tag = "ExecuteFormat";
                return executeFormat;
            case 4:
                ExecuteCancel executeCancel = new ExecuteCancel();
                executeCancel.tag = "ExecuteCancel";
                return executeCancel;
            default:
                ExecuteArchive executeArchive = new ExecuteArchive();
                executeArchive.tag = "ExecuteArchive";
                return executeArchive;
        }
    }
}
