package com.samsung.android.knox.license;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LicenseResult$Type$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ LicenseResult$Type$$ExternalSyntheticLambda0(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean lambda$fromKlmStatus$1;
        boolean lambda$fromElmStatus$0;
        int i = this.$r8$classId;
        int i2 = this.f$0;
        LicenseResult.Type type = (LicenseResult.Type) obj;
        switch (i) {
            case 0:
                lambda$fromKlmStatus$1 = LicenseResult.Type.lambda$fromKlmStatus$1(i2, type);
                return lambda$fromKlmStatus$1;
            default:
                lambda$fromElmStatus$0 = LicenseResult.Type.lambda$fromElmStatus$0(i2, type);
                return lambda$fromElmStatus$0;
        }
    }
}
