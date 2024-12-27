package com.android.settings.accessibility;

import android.content.DialogInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class MagnificationModePreferenceController$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ MagnificationModePreferenceController f$0;

    public /* synthetic */ MagnificationModePreferenceController$$ExternalSyntheticLambda1(
            MagnificationModePreferenceController magnificationModePreferenceController, int i) {
        this.$r8$classId = i;
        this.f$0 = magnificationModePreferenceController;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        MagnificationModePreferenceController magnificationModePreferenceController = this.f$0;
        switch (i2) {
            case 0:
                magnificationModePreferenceController
                        .onMagnificationModeDialogPositiveButtonClicked(dialogInterface, i);
                break;
            case 1:
                magnificationModePreferenceController
                        .onMagnificationTripleTapWarningDialogPositiveButtonClicked(
                                dialogInterface, i);
                break;
            default:
                magnificationModePreferenceController
                        .onMagnificationTripleTapWarningDialogNegativeButtonClicked(
                                dialogInterface, i);
                break;
        }
    }
}
