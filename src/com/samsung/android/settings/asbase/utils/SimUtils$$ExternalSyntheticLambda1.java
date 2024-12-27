package com.samsung.android.settings.asbase.utils;

import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;

import java.util.Collection;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SimUtils$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((UiccCardInfo) obj).getPorts();
            case 1:
                return ((Collection) obj).stream();
            default:
                return Integer.valueOf(((UiccPortInfo) obj).getLogicalSlotIndex());
        }
    }
}
