package com.android.settings.accessibility;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.samsung.android.settings.accessibility.dialog.AccessibilityDialogActivity$$ExternalSyntheticLambda3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class AccessibilityDialogUtils$$ExternalSyntheticLambda1
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AccessibilityDialogUtils$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        Object obj = this.f$0;
        switch (i2) {
            case 0:
                ((DialogInterface.OnClickListener) obj).onClick(dialogInterface, i);
                break;
            case 1:
                DialogInterface.OnClickListener onClickListener =
                        (DialogInterface.OnClickListener) obj;
                if (onClickListener != null) {
                    onClickListener.onClick(dialogInterface, i);
                    break;
                }
                break;
            case 2:
                ((DialogInterface.OnClickListener) obj).onClick(dialogInterface, i);
                break;
            default:
                ((Context) obj).startActivity(new Intent("com.android.settings.TTS_SETTINGS"));
                break;
        }
    }

    public /* synthetic */ AccessibilityDialogUtils$$ExternalSyntheticLambda1(
            AccessibilityDialogActivity$$ExternalSyntheticLambda3
                    accessibilityDialogActivity$$ExternalSyntheticLambda3) {
        this.$r8$classId = 1;
        this.f$0 = accessibilityDialogActivity$$ExternalSyntheticLambda3;
    }
}
