package com.android.settingslib.core;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AbstractPreferenceController$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AbstractPreferenceController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AbstractPreferenceController$$ExternalSyntheticLambda0(
            AbstractPreferenceController abstractPreferenceController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = abstractPreferenceController;
        this.f$1 = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                AbstractPreferenceController abstractPreferenceController = this.f$0;
                return abstractPreferenceController.mContext.getString(this.f$1);
            default:
                AbstractPreferenceController abstractPreferenceController2 = this.f$0;
                return abstractPreferenceController2.mContext.getString(this.f$1);
        }
    }
}
