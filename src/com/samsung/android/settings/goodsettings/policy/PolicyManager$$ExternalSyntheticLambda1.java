package com.samsung.android.settings.goodsettings.policy;

import android.text.TextUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class PolicyManager$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PolicyManager$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                String str = (String) obj2;
                return str == null || TextUtils.equals(((PolicyInfo) obj).mKey, str);
            case 1:
                return TextUtils.equals(((PolicyInfo) obj).mKey, (String) obj2);
            default:
                return TextUtils.equals(((PolicyInfo) obj).mKey, ((PolicyInfo) obj2).mKey);
        }
    }
}
