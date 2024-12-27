package com.samsung.android.settings.voiceinput;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.samsung.android.sdk.scs.ai.asr.Environment;
import com.samsung.android.settings.voiceinput.offline.Language;
import com.samsung.android.util.SemLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class OnDeviceLanguageList {
    private static final String TAG = "@VoiceIn: OnDeviceLanguageList";
    private static List<Language> languageList;

    public static List getLanguageList() {
        if (languageList.size() == 0) {
            SemLog.i(TAG, "No language received");
        }
        return languageList;
    }

    public static void setLanguageList(Context context) {
        String langpackConfigInfo = Environment.getLangpackConfigInfo(context);
        SemLog.i(TAG, "langpackConfigInfo = " + langpackConfigInfo);
        languageList = new ArrayList();
        if (langpackConfigInfo == null || langpackConfigInfo.isEmpty()) {
            return;
        }
        final int i = 0;
        Stream filter =
                Arrays.stream(
                                (LanguagePack[])
                                        new Gson()
                                                .fromJson(langpackConfigInfo, LanguagePack[].class))
                        .filter(
                                new Predicate() { // from class:
                                                  // com.samsung.android.settings.voiceinput.OnDeviceLanguageList$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        LanguagePack languagePack = (LanguagePack) obj;
                                        switch (i) {
                                            case 0:
                                                return !TextUtils.isEmpty(
                                                        languagePack.languageCode);
                                            default:
                                                return languagePack.isLanguageSupported;
                                        }
                                    }
                                });
        final int i2 = 1;
        languageList =
                (List)
                        filter.filter(
                                        new Predicate() { // from class:
                                                          // com.samsung.android.settings.voiceinput.OnDeviceLanguageList$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                LanguagePack languagePack = (LanguagePack) obj;
                                                switch (i2) {
                                                    case 0:
                                                        return !TextUtils.isEmpty(
                                                                languagePack.languageCode);
                                                    default:
                                                        return languagePack.isLanguageSupported;
                                                }
                                            }
                                        })
                                .map(new OnDeviceLanguageList$$ExternalSyntheticLambda2())
                                .collect(Collectors.toList());
        SemLog.i(TAG, "languageList = " + languageList);
    }
}
