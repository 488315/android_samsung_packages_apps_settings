package com.samsung.android.settings.share;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SelectAppActivity$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = SelectAppActivity.$r8$clinit;
                return (Boolean) ((SelectAppViewModel) obj).modifiedLiveData.getValue();
            default:
                Integer num = (Integer) obj;
                int i2 = SelectAppActivity.$r8$clinit;
                return num;
        }
    }
}
