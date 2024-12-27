package com.android.settings.notification.zen;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ZenModeSettings$SummaryBuilder$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Integer num = (Integer) obj;
        switch (this.$r8$classId) {
            case 0:
                if (4 == num.intValue() || 256 == num.intValue()) {}
                break;
            case 1:
                if (32 == num.intValue()
                        || 64 == num.intValue()
                        || 128 == num.intValue()
                        || 1 == num.intValue()
                        || 2 == num.intValue()) {}
                break;
            default:
                if (8 == num.intValue() || 16 == num.intValue()) {}
                break;
        }
        return true;
    }
}
