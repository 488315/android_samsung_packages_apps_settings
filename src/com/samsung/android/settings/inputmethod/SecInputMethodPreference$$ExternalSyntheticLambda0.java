package com.samsung.android.settings.inputmethod;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecInputMethodPreference$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecInputMethodPreference f$0;

    public /* synthetic */ SecInputMethodPreference$$ExternalSyntheticLambda0(
            SecInputMethodPreference secInputMethodPreference, int i) {
        this.$r8$classId = i;
        this.f$0 = secInputMethodPreference;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        SecInputMethodPreference secInputMethodPreference = this.f$0;
        switch (i2) {
            case 0:
                if (!secInputMethodPreference.mImi.getServiceInfo().directBootAware
                        && !secInputMethodPreference.isTv()) {
                    secInputMethodPreference.showDirectBootWarnDialog();
                    break;
                } else {
                    secInputMethodPreference.setCheckedInternal(true);
                    break;
                }
                break;
            case 1:
                int i3 = SecInputMethodPreference.$r8$clinit;
                secInputMethodPreference.setCheckedInternal(false);
                break;
            case 2:
                int i4 = SecInputMethodPreference.$r8$clinit;
                secInputMethodPreference.setCheckedInternal(true);
                break;
            default:
                int i5 = SecInputMethodPreference.$r8$clinit;
                secInputMethodPreference.setCheckedInternal(false);
                break;
        }
    }
}
