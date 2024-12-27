package com.samsung.android.settings.languagepack.service;

import java.util.Map;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDownloadService$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder =
                LanguagePackDownloadService.mLanguagePackService;
        return (String) ((Map.Entry) obj).getValue();
    }
}
