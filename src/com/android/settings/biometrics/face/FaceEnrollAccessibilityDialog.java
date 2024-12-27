package com.android.settings.biometrics.face;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollAccessibilityDialog extends InstrumentedDialogFragment {
    public DialogInterface.OnClickListener mPositiveButtonListener;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.SDP_PROCESSING_FAILED;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(
                        R.string
                                .security_settings_face_enroll_education_accessibility_dialog_message)
                .setNegativeButton(
                        R.string
                                .security_settings_face_enroll_education_accessibility_dialog_negative,
                        new FaceEnrollAccessibilityDialog$$ExternalSyntheticLambda0())
                .setPositiveButton(
                        R.string
                                .security_settings_face_enroll_education_accessibility_dialog_positive,
                        new DialogInterface
                                .OnClickListener() { // from class:
                                                     // com.android.settings.biometrics.face.FaceEnrollAccessibilityDialog$$ExternalSyntheticLambda1
                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i) {
                                FaceEnrollAccessibilityDialog.this.mPositiveButtonListener.onClick(
                                        dialogInterface, i);
                            }
                        });
        return builder.create();
    }
}
