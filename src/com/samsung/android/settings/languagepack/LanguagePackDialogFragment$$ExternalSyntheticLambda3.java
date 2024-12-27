package com.samsung.android.settings.languagepack;

import com.android.settings.R;

import com.samsung.android.settings.languagepack.data.LanguageInfo;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class LanguagePackDialogFragment$$ExternalSyntheticLambda3
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LanguagePackDialogFragment f$0;

    public /* synthetic */ LanguagePackDialogFragment$$ExternalSyntheticLambda3(
            LanguagePackDialogFragment languagePackDialogFragment, int i) {
        this.$r8$classId = i;
        this.f$0 = languagePackDialogFragment;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        LanguagePackDialogFragment languagePackDialogFragment = this.f$0;
        LanguageInfo languageInfo = (LanguageInfo) obj;
        switch (i) {
            case 0:
                if (languageInfo == null) {
                    int i2 = LanguagePackDialogFragment.$r8$clinit;
                    languagePackDialogFragment.getClass();
                    break;
                } else {
                    languagePackDialogFragment.mTitleText.setText(
                            languagePackDialogFragment
                                    .getContext()
                                    .getString(
                                            R.string
                                                    .sec_offline_lang_pack_dialog_download_waiting_text,
                                            languageInfo.getDisplayName()));
                    break;
                }
            default:
                int i3 = LanguagePackDialogFragment.$r8$clinit;
                languagePackDialogFragment.getClass();
                if (languageInfo != null) {
                    languagePackDialogFragment.updateStatus$1(languageInfo.mLanguageCode);
                    break;
                }
                break;
        }
    }
}
