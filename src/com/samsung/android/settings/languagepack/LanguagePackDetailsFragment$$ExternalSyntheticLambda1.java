package com.samsung.android.settings.languagepack;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDetailsFragment$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LanguagePackDetailsFragment$$ExternalSyntheticLambda1(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((PackageInfo) obj)
                        .isPackageInstalled(((LanguagePackDetailsFragment) obj2).getContext());
            case 1:
                return ((PackageInfo) obj)
                        .isPackageInstalled(((LanguagePackDetailsFragment) obj2).getContext());
            case 2:
                return ((PackageInfo) obj)
                        .isPackageInstalled(((LanguagePackDetailsFragment) obj2).getContext());
            case 3:
                return ((PackageInfo) obj)
                        .isPackageInstalled(((LanguagePackDetailsFragment) obj2).getContext());
            default:
                return ((LanguageInfo) obj).mLanguageCode.equals((String) obj2);
        }
    }
}
