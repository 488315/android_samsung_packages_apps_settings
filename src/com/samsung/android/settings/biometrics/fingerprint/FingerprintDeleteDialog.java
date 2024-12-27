package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintDeleteDialog extends DialogFragment
        implements DialogInterface.OnClickListener {
    public AlertDialog mAlertDialog;
    public int mButton = -1;
    public ConfirmationDialogFragmentListener mListener;
    public String mMsg;
    public String mTitle;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConfirmationDialogFragmentListener {
        void onDismiss();

        void onNegativeClick();

        void onPositiveClick();
    }

    public FingerprintDeleteDialog() {
        Log.d("FpstFingerprintDeleteDialog", "FingerprintDeleteDialog created illegally");
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener;
        if (i != -2) {
            if (i == -1 && (confirmationDialogFragmentListener = this.mListener) != null) {
                confirmationDialogFragmentListener.onPositiveClick();
                return;
            }
            return;
        }
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener2 = this.mListener;
        if (confirmationDialogFragmentListener2 != null) {
            confirmationDialogFragmentListener2.onNegativeClick();
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FpstFingerprintDeleteDialog", "onCreate");
        getActivity();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        if (this.mMsg == null || this.mListener == null) {
            Log.d("FpstFingerprintDeleteDialog", "onCreateDialog illegalCreated");
            dismissInternal(false, false);
            return null;
        }
        if (this.mButton == -1) {
            this.mButton = R.string.common_remove;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.P.mMessage = this.mMsg;
        builder.setPositiveButton(this.mButton, this);
        builder.setNegativeButton(R.string.common_cancel, this);
        AlertDialog create = builder.create();
        this.mAlertDialog = create;
        String str = this.mTitle;
        if (str != null) {
            create.setTitle(str);
        }
        this.mAlertDialog.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintDeleteDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        FingerprintDeleteDialog fingerprintDeleteDialog =
                                FingerprintDeleteDialog.this;
                        fingerprintDeleteDialog.getClass();
                        ((AlertDialog) dialogInterface)
                                .getButton(-1)
                                .setTextColor(
                                        fingerprintDeleteDialog
                                                .getActivity()
                                                .getColor(
                                                        R.color
                                                                .sec_biometrics_dialog_remove_btn_color));
                    }
                });
        return this.mAlertDialog;
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        Log.d("FpstFingerprintDeleteDialog", "onDismiss");
        super.onDismiss(dialogInterface);
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener = this.mListener;
        if (confirmationDialogFragmentListener != null) {
            confirmationDialogFragmentListener.onDismiss();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        Log.d("FpstFingerprintDeleteDialog", "onPause");
        super.onPause();
        dismissAllowingStateLoss();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        Log.d("FpstFingerprintDeleteDialog", "onResume");
        super.onResume();
    }
}
