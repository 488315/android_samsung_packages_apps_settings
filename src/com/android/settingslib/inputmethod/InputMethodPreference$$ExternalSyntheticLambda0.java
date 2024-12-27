package com.android.settingslib.inputmethod;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class InputMethodPreference$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ InputMethodPreference f$0;

    public /* synthetic */ InputMethodPreference$$ExternalSyntheticLambda0(
            InputMethodPreference inputMethodPreference, int i) {
        this.$r8$classId = i;
        this.f$0 = inputMethodPreference;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        InputMethodPreference inputMethodPreference = this.f$0;
        switch (i2) {
            case 0:
                int i3 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setCheckedInternal$1(true);
                break;
            case 1:
                int i4 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setCheckedInternal$1(false);
                break;
            case 2:
                if (!inputMethodPreference.mImi.getServiceInfo().directBootAware
                        && !inputMethodPreference.isTv$1()) {
                    inputMethodPreference.showDirectBootWarnDialog$1();
                    break;
                } else {
                    inputMethodPreference.setCheckedInternal$1(true);
                    break;
                }
            default:
                int i5 = InputMethodPreference.$r8$clinit;
                inputMethodPreference.setCheckedInternal$1(false);
                break;
        }
    }
}
