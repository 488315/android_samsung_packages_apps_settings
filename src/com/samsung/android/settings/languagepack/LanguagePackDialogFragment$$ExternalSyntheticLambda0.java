package com.samsung.android.settings.languagepack;

import com.samsung.android.settings.languagepack.data.LanguageInfo;
import com.samsung.android.settings.languagepack.service.LanguagePackDownloadService;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDialogFragment$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        LanguagePackDownloadService.Status status =
                LanguagePackDownloadService.Status.STATUS_DOWNLOADING;
        LanguagePackDownloadService.Status status2 =
                LanguagePackDownloadService.Status.STATUS_WAITING;
        LanguageInfo languageInfo = (LanguageInfo) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = LanguagePackDialogFragment.$r8$clinit;
                if (languageInfo.mStatus == status) {
                    break;
                }
                break;
            case 1:
                int i2 = LanguagePackDialogFragment.$r8$clinit;
                if (languageInfo.mStatus == status2) {
                    break;
                }
                break;
            case 2:
                int i3 = LanguagePackDialogFragment.$r8$clinit;
                LanguagePackDownloadService.Status status3 = languageInfo.mStatus;
                if (status3 == status
                        || status3 == LanguagePackDownloadService.Status.STATUS_INSTALLING) {
                    break;
                }
                break;
            default:
                int i4 = LanguagePackDialogFragment.$r8$clinit;
                if (languageInfo.mStatus == status2) {
                    break;
                }
                break;
        }
        return true;
    }
}
