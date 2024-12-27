package com.samsung.android.sdk.scs.ai.asr;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class ExpiringData$$ExternalSyntheticLambda3 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                Duration duration = ExpiringData.DEFAULT_DURATION;
                return Boolean.TRUE;
            default:
                return ((Supplier) obj).get();
        }
    }
}
