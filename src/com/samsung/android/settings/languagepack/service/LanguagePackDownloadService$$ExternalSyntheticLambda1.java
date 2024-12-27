package com.samsung.android.settings.languagepack.service;

import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDownloadService$$ExternalSyntheticLambda1
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder =
                LanguagePackDownloadService.mLanguagePackService;
        return ((Map.Entry) obj).getValue() instanceof String;
    }
}
