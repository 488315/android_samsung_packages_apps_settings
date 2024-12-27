package com.samsung.android.settings.languagepack.service;

import android.content.Intent;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.logger.Log;
import com.samsung.android.settings.languagepack.manager.LanguagePackManager;
import com.samsung.android.sivs.ai.sdkcommon.asr.SpeechRecognitionConst;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDownloadService$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ LanguagePackDownloadService$$ExternalSyntheticLambda0(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((LanguagePackDownloadService) this.f$0)
                        .mLanguagePackManager.updateLatestPackageVersion((List) this.f$1);
                break;
            case 1:
                LanguagePackDownloadService languagePackDownloadService =
                        (LanguagePackDownloadService) this.f$0;
                LanguageInfo languageInfo = (LanguageInfo) this.f$1;
                LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder =
                        LanguagePackDownloadService.mLanguagePackService;
                if (languageInfo.isAllPackageInstalled(
                        languagePackDownloadService.getApplicationContext())) {
                    Intent intent =
                            new Intent("com.samsung.android.settings.action.LANGUAGE_PACK_ADDED");
                    intent.putExtra(SpeechRecognitionConst.Key.LOCALE, languageInfo.mLanguageCode);
                    languagePackDownloadService.sendBroadcast(intent);
                    Log.d(
                            "LanguagePackDownloadService",
                            "Broadcast intent LANGUAGE_PACK_ADDED : "
                                    + languageInfo.mLanguageCode
                                    + " : "
                                    + languagePackDownloadService.mResultBroadcastPackage);
                    break;
                }
                break;
            default:
                LanguagePackDownloadService.LanguagePackServiceBinder languagePackServiceBinder2 =
                        (LanguagePackDownloadService.LanguagePackServiceBinder) this.f$0;
                String str = (String) this.f$1;
                LanguagePackManager languagePackManager =
                        LanguagePackDownloadService.this.mLanguagePackManager;
                if (languagePackManager != null) {
                    if (!languagePackManager.isPausedState(str)) {
                        LanguagePackDownloadService.this.mLanguagePackManager.setCancel();
                        break;
                    } else {
                        LanguagePackManager languagePackManager2 =
                                LanguagePackDownloadService.this.mLanguagePackManager;
                        languagePackManager2.notifyUpdateStatus(
                                languagePackManager2.getLanguageInfo(str),
                                LanguagePackDownloadService.Status.STATUS_CANCEL,
                                0);
                        break;
                    }
                }
                break;
        }
    }
}
