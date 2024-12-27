package com.samsung.android.settings.languagepack.manager;

import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda9
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((PackageInfo) obj).mPkgName;
            default:
                return new PackageInfo((String) obj, 8);
        }
    }
}
