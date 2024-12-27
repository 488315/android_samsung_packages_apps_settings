package com.samsung.android.settings.share;

import com.samsung.android.settings.share.structure.CallerComponent;
import com.samsung.android.settings.share.structure.ShareComponent;

import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SelectAppActivity$$ExternalSyntheticLambda2
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                int i = SelectAppActivity.$r8$clinit;
                return !(((ShareComponent) obj) instanceof CallerComponent);
            default:
                int i2 = SelectAppActivity.$r8$clinit;
                return ((List) obj).size() > 0;
        }
    }
}
