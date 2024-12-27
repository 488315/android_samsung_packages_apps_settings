package com.android.settings.users;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UserDialogs$$ExternalSyntheticLambda2
        implements DialogInterface.OnShowListener {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Context f$0;

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        int i = this.$r8$classId;
        Context context = this.f$0;
        switch (i) {
            case 0:
                ((AlertDialog) dialogInterface)
                        .getButton(-1)
                        .setTextColor(
                                context.getColor(R.color.sec_biometrics_dialog_remove_btn_color));
                break;
            default:
                ((AlertDialog) dialogInterface)
                        .getButton(-1)
                        .setTextColor(
                                context.getColor(R.color.sec_biometrics_dialog_remove_btn_color));
                break;
        }
    }
}
