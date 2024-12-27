package com.samsung.android.settings.languagepack.data;

import android.text.TextUtils;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguageInfo$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LanguageInfo f$0;

    public /* synthetic */ LanguageInfo$$ExternalSyntheticLambda0(
            LanguageInfo languageInfo, int i) {
        this.$r8$classId = i;
        this.f$0 = languageInfo;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        LanguageInfo languageInfo = this.f$0;
        LanguageInfo languageInfo2 = (LanguageInfo) obj;
        switch (i) {
            case 0:
                return TextUtils.equals(
                        languageInfo.mLanguageCode.split("-")[0],
                        languageInfo2.mLanguageCode.split("-")[0]);
            default:
                return TextUtils.equals(
                        languageInfo.getTranslationPackageName(),
                        languageInfo2.getTranslationPackageName());
        }
    }
}
