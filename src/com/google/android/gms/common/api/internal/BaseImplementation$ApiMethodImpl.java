package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class BaseImplementation$ApiMethodImpl extends BasePendingResult {
    public BaseImplementation$ApiMethodImpl(
            BasePendingResult.CallbackHandler<Result> callbackHandler) {
        super(callbackHandler);
    }

    public abstract void doExecute(Api.Client client);

    public final void setFailedResult(Status status) {
        if (!(!(status.zzc <= 0))) {
            throw new IllegalArgumentException("Failed result must not be success");
        }
        setResult(createFailedResult(status));
    }
}
