package com.samsung.android.settings.languagepack;

import com.samsung.android.settings.languagepack.data.LanguageInfo;

import java.util.ArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackSettingsFragment$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LanguagePackSettingsFragment f$0;

    public /* synthetic */ LanguagePackSettingsFragment$$ExternalSyntheticLambda0(
            LanguagePackSettingsFragment languagePackSettingsFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = languagePackSettingsFragment;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        LanguagePackSettingsFragment languagePackSettingsFragment = this.f$0;
        LanguageInfo languageInfo = (LanguageInfo) obj;
        switch (i) {
            case 0:
                return ((ArrayList) languagePackSettingsFragment.mRequiredLangCodeList)
                        .contains(languageInfo.mLanguageCode);
            default:
                return languageInfo.showItemOnListWithType(
                        languagePackSettingsFragment.mRequestType);
        }
    }
}
