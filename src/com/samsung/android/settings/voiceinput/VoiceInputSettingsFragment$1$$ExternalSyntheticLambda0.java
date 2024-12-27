package com.samsung.android.settings.voiceinput;

import com.samsung.android.settings.voiceinput.offline.OfflineLanguageUtil;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VoiceInputSettingsFragment$1$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        OfflineLanguageUtil.onLangPackResultListener onlangpackresultlistener =
                (OfflineLanguageUtil.onLangPackResultListener) obj;
        switch (this.$r8$classId) {
            case 0:
                onlangpackresultlistener.onLangPackResultUpdated();
                break;
            case 1:
                onlangpackresultlistener.onLangPackResultUpdated();
                break;
            default:
                onlangpackresultlistener.onLangPackResultSubscribed();
                break;
        }
    }
}
