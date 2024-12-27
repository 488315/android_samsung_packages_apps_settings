package com.android.settings.notification.modes;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ZenModeSummaryHelper$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Integer num = (Integer) obj;
        switch (this.$r8$classId) {
            case 0:
                if (2 == num.intValue() || 8 == num.intValue()) {}
                break;
            case 1:
                if (3 == num.intValue() || 4 == num.intValue()) {}
                break;
            default:
                if (5 == num.intValue()
                        || 6 == num.intValue()
                        || 7 == num.intValue()
                        || num.intValue() == 0
                        || 1 == num.intValue()) {}
                break;
        }
        return true;
    }
}
