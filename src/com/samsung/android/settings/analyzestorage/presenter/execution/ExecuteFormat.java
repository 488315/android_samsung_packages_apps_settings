package com.samsung.android.settings.analyzestorage.presenter.execution;

import com.samsung.android.settings.analyzestorage.ui.dialog.AbsDialog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteFormat extends AbsExecute {
    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute
    public final boolean onExecute(
            int i, ExecutionParams executionParams, MenuExecuteManager menuExecuteManager) {
        AbsDialog absDialog = executionParams.mDialog;
        if (absDialog == null) {
            return false;
        }
        absDialog.showDialog(null);
        return true;
    }
}
