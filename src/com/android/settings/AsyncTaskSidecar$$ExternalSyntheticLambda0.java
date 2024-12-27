package com.android.settings;

import com.android.settings.network.SwitchSlotSidecar;
import com.android.settingslib.utils.ThreadUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class AsyncTaskSidecar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AsyncTaskSidecar f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ AsyncTaskSidecar$$ExternalSyntheticLambda0(
            AsyncTaskSidecar asyncTaskSidecar, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = asyncTaskSidecar;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AsyncTaskSidecar asyncTaskSidecar = this.f$0;
                ThreadUtils.postOnMainThread(
                        new AsyncTaskSidecar$$ExternalSyntheticLambda0(
                                asyncTaskSidecar,
                                asyncTaskSidecar.doInBackground((SwitchSlotSidecar.Param) this.f$1),
                                1));
                break;
            default:
                this.f$0.onPostExecute$1(this.f$1);
                break;
        }
    }
}
