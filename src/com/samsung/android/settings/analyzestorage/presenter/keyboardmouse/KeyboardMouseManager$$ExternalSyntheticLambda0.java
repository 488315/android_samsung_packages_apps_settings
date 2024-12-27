package com.samsung.android.settings.analyzestorage.presenter.keyboardmouse;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class KeyboardMouseManager$$ExternalSyntheticLambda0
        implements Supplier {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyboardMouseManager$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new CtrlKeyMouseCommand();
            case 1:
                return new NormalKeyMouseCommand();
            case 2:
                return new CtrlShiftKeyMouseCommand();
            case 3:
                return new AltKeyMouseCommand();
            default:
                return new ShiftKeyMouseCommand();
        }
    }
}
