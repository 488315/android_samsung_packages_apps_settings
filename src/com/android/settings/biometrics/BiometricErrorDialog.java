package com.android.settings.biometrics;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BiometricErrorDialog extends InstrumentedDialogFragment {
    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence charSequence = getArguments().getCharSequence("error_msg");
        final int i = getArguments().getInt("error_id");
        builder.setTitle(R.string.security_settings_face_enroll_error_dialog_title);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = charSequence;
        alertParams.mCancelable = false;
        builder.setPositiveButton(
                R.string.security_settings_face_enroll_dialog_ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.BiometricErrorDialog.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        dialogInterface.dismiss();
                        boolean z = i == 3;
                        FragmentActivity activity = BiometricErrorDialog.this.getActivity();
                        activity.setResult(z ? 3 : 1);
                        activity.finish();
                    }
                });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
