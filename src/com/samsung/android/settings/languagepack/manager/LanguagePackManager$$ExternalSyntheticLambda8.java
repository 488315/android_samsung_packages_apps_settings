package com.samsung.android.settings.languagepack.manager;

import android.text.TextUtils;

import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda8
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda8(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean needToUpdate;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return LanguagePackManager.m1245$r8$lambda$DzXnHny8MJoNnQYzR88feiog0w(
                        (LanguagePackManager) obj2, (PackageInfo) obj);
            case 1:
                needToUpdate =
                        ((PackageInfo) obj).needToUpdate(((LanguagePackManager) obj2).mContext);
                return needToUpdate;
            default:
                return TextUtils.equals(((PackageInfo) obj).mPkgName, (String) obj2);
        }
    }
}
