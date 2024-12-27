package com.google.android.gms.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.google.android.gms.common.internal.Preconditions;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SupportErrorDialogFragment extends DialogFragment {
    public Dialog zaa;
    public DialogInterface.OnCancelListener zab;
    public Dialog zac;

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        DialogInterface.OnCancelListener onCancelListener = this.zab;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = this.zaa;
        if (dialog != null) {
            return dialog;
        }
        this.mShowsDialog = false;
        if (this.zac == null) {
            Context context = getContext();
            Preconditions.checkNotNull(context);
            this.zac = new AlertDialog.Builder(context).create();
        }
        return this.zac;
    }
}
