package com.samsung.android.settings.voiceinput;

import com.samsung.android.settings.voiceinput.offline.Language;

import java.util.Locale;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class OnDeviceLanguageList$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        LanguagePack languagePack = (LanguagePack) obj;
        Locale forLanguageTag = Locale.forLanguageTag(languagePack.languageCode);
        String str = languagePack.displayName;
        if (str == null) {
            str = forLanguageTag.getDisplayName(forLanguageTag);
        }
        return new Language(forLanguageTag, str, languagePack.packageName);
    }
}
