package com.samsung.android.settings.languagepack.manager;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.data.PackageInfo;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackManager$$ExternalSyntheticLambda4
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LanguagePackManager f$0;

    public /* synthetic */ LanguagePackManager$$ExternalSyntheticLambda4(
            LanguagePackManager languagePackManager, int i) {
        this.$r8$classId = i;
        this.f$0 = languagePackManager;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        LanguagePackManager languagePackManager = this.f$0;
        switch (i) {
            case 0:
                languagePackManager.mLanguageInfoMap.put(r3.mLanguageCode, (LanguageInfo) obj);
                break;
            case 1:
                LanguagePackManager.$r8$lambda$Sezx9BbzOdJHrjfzecLGXwx9PTc(
                        languagePackManager, (PackageInfo) obj);
                break;
            case 2:
                languagePackManager.getClass();
                ((LanguageInfo) obj)
                        .mList.forEach(
                                new LanguagePackManager$$ExternalSyntheticLambda4(
                                        languagePackManager, 3));
                break;
            default:
                LanguagePackManager.$r8$lambda$bKNyHMbzoepa90lkuraZgq7LrjA(
                        languagePackManager, (PackageInfo) obj);
                break;
        }
    }
}
