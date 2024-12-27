package com.samsung.android.sdk.routines.v3.data;

import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionResult$Error {
    public final int customErrorCode;
    public final ParameterValues outputValues;
    public final ActionResult$ResultCode resultCode;

    public ActionResult$Error(int i) {
        this(i, false);
    }

    public ActionResult$Error(ActionResult$ResultCode actionResult$ResultCode) {
        this(actionResult$ResultCode, false);
    }

    public ActionResult$Error(ActionResult$ResultCode actionResult$ResultCode, boolean z) {
        this.resultCode = actionResult$ResultCode;
        this.outputValues = null;
        this.customErrorCode = -1;
    }

    public ActionResult$Error(int i, boolean z) {
        if (i < 1 || i > 16777215) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "ActionResult: Out of range of custom code:", "RoutineSDK");
            i = 1;
        }
        this.resultCode = ActionResult$ResultCode.FAIL_NOT_AVAILABLE;
        this.outputValues = null;
        this.customErrorCode = i;
    }
}
