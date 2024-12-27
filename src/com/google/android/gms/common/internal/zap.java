package com.google.android.gms.common.internal;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class zap implements PendingResult.StatusListener {
    public final /* synthetic */ PendingResult zaa;
    public final /* synthetic */ TaskCompletionSource zab;
    public final /* synthetic */ zaq zac;

    public zap(
            com.google.android.gms.internal.safetynet.zzn zznVar,
            TaskCompletionSource taskCompletionSource,
            zaq zaqVar) {
        this.zaa = zznVar;
        this.zab = taskCompletionSource;
        this.zac = zaqVar;
    }

    @Override // com.google.android.gms.common.api.PendingResult.StatusListener
    public final void onComplete(Status status) {
        Result result;
        if (status.zzc > 0) {
            this.zab.setException(
                    status.hasResolution()
                            ? new ResolvableApiException(status)
                            : new ApiException(status));
            return;
        }
        PendingResult pendingResult = this.zaa;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        BasePendingResult basePendingResult = (BasePendingResult) pendingResult;
        Preconditions.checkState("Result has already been consumed.", !basePendingResult.zal);
        try {
            if (!basePendingResult.zaf.await(0L, timeUnit)) {
                basePendingResult.forceFailureUnlessReady(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException unused) {
            basePendingResult.forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState("Result is not ready.", basePendingResult.isReady());
        synchronized (basePendingResult.zae) {
            Preconditions.checkState("Result has already been consumed.", !basePendingResult.zal);
            Preconditions.checkState("Result is not ready.", basePendingResult.isReady());
            result = basePendingResult.zaj;
            basePendingResult.zaj = null;
            basePendingResult.zal = true;
        }
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(
                basePendingResult.zai.getAndSet(null));
        Preconditions.checkNotNull(result);
        TaskCompletionSource taskCompletionSource = this.zab;
        Response response = this.zac.zaa;
        response.zza = result;
        taskCompletionSource.setResult(response);
    }
}
