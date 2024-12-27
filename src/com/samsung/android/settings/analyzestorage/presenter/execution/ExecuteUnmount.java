package com.samsung.android.settings.analyzestorage.presenter.execution;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteUnmount extends AbsExecute {
    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute
    public final boolean onExecute(
            int i, ExecutionParams executionParams, MenuExecuteManager menuExecuteManager) {
        Log.e(
                this.tag,
                "onExecute() ] mPageInfo.getPath() : "
                        + executionParams.mPageInfo.getPath()
                        + " , mPageInfo.domainType :  "
                        + executionParams.mPageInfo.mExtra.getInt("domainType", -1));
        StorageOperationManager.StorageOperationManagerHolder.INSTANCE.storageOperation(
                executionParams.mContext,
                1,
                executionParams.mPageInfo.mExtra.getInt("domainType", -1));
        return false;
    }
}
