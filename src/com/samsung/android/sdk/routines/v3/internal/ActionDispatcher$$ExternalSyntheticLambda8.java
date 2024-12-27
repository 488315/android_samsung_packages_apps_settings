package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;

import com.samsung.android.sdk.routines.v3.data.ActionResult$Error;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ActionDispatcher$$ExternalSyntheticLambda8
        implements ActionResultCallback {
    public final /* synthetic */ Bundle f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ActionDispatcher$$ExternalSyntheticLambda8(
            Bundle bundle, long j, Object obj) {
        this.f$0 = bundle;
        this.f$1 = j;
        this.f$2 = obj;
    }

    public final void actionFinished(ActionResult$Error actionResult$Error) {
        Bundle bundle = this.f$0;
        long j = this.f$1;
        Object obj = this.f$2;
        bundle.putLong(ExtraKey.INSTANCE_ID.a, j);
        int i = actionResult$Error.customErrorCode;
        if (i < 1 || i > 16777215) {
            bundle.putInt(ExtraKey.RESULT_INT.a, actionResult$Error.resultCode.value);
        } else {
            bundle.putInt(ExtraKey.RESULT_TYPE.a, 16);
            bundle.putInt(ExtraKey.RESULT_INT.a, actionResult$Error.customErrorCode);
        }
        ParameterValues parameterValues = actionResult$Error.outputValues;
        if (parameterValues != null) {
            bundle.putString(ExtraKey.OUTPUT_PARAMS.a, parameterValues.toJsonString());
        }
        synchronized (obj) {
            obj.notify();
        }
    }
}
