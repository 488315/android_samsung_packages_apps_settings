package com.samsung.android.settings.analyzestorage.presenter.execution;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ExecuteCancel extends AbsExecute {
    @Override // com.samsung.android.settings.analyzestorage.presenter.execution.AbsExecute
    public final boolean onExecute(
            int i, ExecutionParams executionParams, MenuExecuteManager menuExecuteManager) {
        int i2 = executionParams.mInstanceId;
        Context context = ActivityInfoStore.context;
        FragmentActivity fragmentActivity =
                ActivityInfoStore.Companion.getInstance(i2).getFragmentActivity();
        if (fragmentActivity == null) {
            Log.e(this.tag, "Activity is null and instanceId : " + i2 + ".");
            return true;
        }
        Log.d(this.tag, "Activity will be finished and instanceId : " + i2 + ".");
        fragmentActivity.finish();
        return true;
    }
}
