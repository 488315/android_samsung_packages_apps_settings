package com.samsung.android.settings.languagepack.manager;

import android.text.TextUtils;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return !TextUtils.isEmpty(((LanguagePack) obj).mLanguageCode);
            case 1:
                return ((LanguagePack) obj).mShowLanguageName;
            case 2:
                return !TextUtils.isEmpty(((PackageInfo) obj).mPkgName);
            default:
                return !((LanguageInfo) obj).mList.isEmpty();
        }
    }
}
