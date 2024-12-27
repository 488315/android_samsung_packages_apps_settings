package com.samsung.android.sdk.routines.v3.internal;

import com.sec.ims.IMSParameter;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public enum ExtraValue {
    UNKNOWN("unknown"),
    /* JADX INFO: Fake field, exist only in values array */
    CALL_TYPE_CONDITION("condition"),
    /* JADX INFO: Fake field, exist only in values array */
    CALL_TYPE_ACTION(IMSParameter.CALL.ACTION);

    public final String a;

    ExtraValue(String str) {
        this.a = str;
    }
}
