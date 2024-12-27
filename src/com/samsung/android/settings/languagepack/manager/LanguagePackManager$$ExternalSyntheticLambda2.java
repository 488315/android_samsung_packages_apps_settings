package com.samsung.android.settings.languagepack.manager;

import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda2
        implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LanguagePackManager f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda2(
            LanguagePackManager languagePackManager, List list, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = languagePackManager;
        this.f$1 = list;
        this.f$2 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return LanguagePackManager.$r8$lambda$noDwASTUeaavHNfVfZBM2knNJmk(
                        this.f$0, this.f$1, (String) this.f$2, (LanguagePack) obj);
            default:
                return LanguagePackManager.$r8$lambda$AZRpAaaiFzu2x4MhN8lGEMhuapw(
                        this.f$0, this.f$1, (LanguagePack) this.f$2, (TtsInfo) obj);
        }
    }
}
