package com.android.settings.biometrics;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricsSplitScreenDialog extends InstrumentedDialogFragment {
    public int mBiometricsModality;
    public boolean mDestroyActivity;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mBiometricsModality != 8 ? 2028 : 2029;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i;
        int i2;
        this.mBiometricsModality = getArguments().getInt("biometrics_modality");
        this.mDestroyActivity = getArguments().getBoolean("destroy_activity");
        if (this.mBiometricsModality != 8) {
            i = R.string.biometric_settings_add_fingerprint_in_split_mode_title;
            i2 = R.string.biometric_settings_add_fingerprint_in_split_mode_message;
        } else {
            i = R.string.biometric_settings_add_face_in_split_mode_title;
            i2 = R.string.biometric_settings_add_face_in_split_mode_message;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(i);
        builder.setMessage(i2);
        builder.P.mCancelable = false;
        builder.setPositiveButton(
                R.string.biometric_settings_add_biometrics_in_split_mode_ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.BiometricsSplitScreenDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        BiometricsSplitScreenDialog biometricsSplitScreenDialog =
                                BiometricsSplitScreenDialog.this;
                        biometricsSplitScreenDialog.getClass();
                        dialogInterface.dismiss();
                        if (biometricsSplitScreenDialog.mDestroyActivity) {
                            biometricsSplitScreenDialog.getActivity().setResult(2);
                            biometricsSplitScreenDialog.getActivity().finish();
                        }
                    }
                });
        return builder.create();
    }
}
