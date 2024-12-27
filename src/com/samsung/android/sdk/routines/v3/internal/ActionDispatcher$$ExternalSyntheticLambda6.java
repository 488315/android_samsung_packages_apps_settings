package com.samsung.android.sdk.routines.v3.internal;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import com.samsung.android.sdk.routines.v3.data.ActionValidity$Default;
import com.samsung.android.sdk.routines.v3.data.ActionValidity$Validity;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ActionDispatcher$$ExternalSyntheticLambda6
        implements ResponseCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Bundle f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ActionDispatcher$$ExternalSyntheticLambda6(
            Bundle bundle, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = bundle;
        this.f$1 = obj;
    }

    @Override // com.samsung.android.sdk.routines.v3.interfaces.ResponseCallback
    public final void setResponse(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Bundle bundle = this.f$0;
                Object obj2 = this.f$1;
                bundle.putString(ExtraKey.CONFIG_LABEL_PARAMS.a, (String) obj);
                synchronized (obj2) {
                    obj2.notify();
                }
                return;
            case 1:
                Bundle bundle2 = this.f$0;
                Object obj3 = this.f$1;
                ((ActionValidity$Default) obj).getClass();
                bundle2.putInt(ExtraKey.RESULT_INT.a, ActionValidity$Validity.VALID.value);
                synchronized (obj3) {
                    obj3.notify();
                }
                return;
            case 2:
                Bundle bundle3 = this.f$0;
                Object obj4 = this.f$1;
                bundle3.putParcelable(
                        ExtraKey.PREVIEW_IMAGE_FILE_DESCRIPTOR.a, (ParcelFileDescriptor) obj);
                synchronized (obj4) {
                    obj4.notify();
                }
                return;
            default:
                Bundle bundle4 = this.f$0;
                Object obj5 = this.f$1;
                bundle4.putString(
                        ExtraKey.PARAMETER_VALUES.a, ((ParameterValues) obj).toJsonString());
                synchronized (obj5) {
                    obj5.notify();
                }
                return;
        }
    }
}
