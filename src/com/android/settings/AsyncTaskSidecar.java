package com.android.settings;

import com.android.settings.network.SwitchSlotSidecar;
import com.android.settingslib.utils.ThreadUtils;

import com.google.common.util.concurrent.ListenableFuture;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public abstract class AsyncTaskSidecar extends SidecarFragment {
    public ListenableFuture mAsyncTask;

    public abstract Object doInBackground(SwitchSlotSidecar.Param param);

    @Override // com.android.settings.SidecarFragment, android.app.Fragment
    public final void onDestroy() {
        ListenableFuture listenableFuture = this.mAsyncTask;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        super.onDestroy();
    }

    public abstract void onPostExecute$1(Object obj);

    public final void run(SwitchSlotSidecar.Param param) {
        setState(1, 0);
        ListenableFuture listenableFuture = this.mAsyncTask;
        if (listenableFuture != null) {
            listenableFuture.cancel(true);
        }
        this.mAsyncTask =
                ThreadUtils.postOnBackgroundThread(
                        new AsyncTaskSidecar$$ExternalSyntheticLambda0(this, param, 0));
    }
}
