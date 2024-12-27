package com.android.settings.biometrics.fingerprint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintErrorDialog extends InstrumentedDialogFragment {
    public static void showErrorDialog(FragmentActivity fragmentActivity, int i, boolean z) {
        CharSequence text;
        if (fragmentActivity.isFinishing()) {
            return;
        }
        FragmentManagerImpl supportFragmentManager = fragmentActivity.getSupportFragmentManager();
        if (supportFragmentManager.mDestroyed || supportFragmentManager.isStateSaved()) {
            return;
        }
        int i2 = R.string.security_settings_fingerprint_bad_calibration;
        if (z) {
            if (i == 2) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_unable_to_process_message_setup;
            } else if (i == 3) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_timeout_dialog_message_setup;
            } else if (i != 18) {
                i2 =
                        R.string
                                .security_settings_fingerprint_enroll_error_generic_dialog_message_setup;
            }
            text = fragmentActivity.getText(i2);
        } else {
            if (i == 2) {
                i2 = R.string.security_settings_fingerprint_enroll_error_unable_to_process_message;
            } else if (i == 3) {
                i2 = R.string.security_settings_fingerprint_enroll_error_timeout_dialog_message;
            } else if (i != 18) {
                i2 = R.string.security_settings_fingerprint_enroll_error_generic_dialog_message;
            }
            text = fragmentActivity.getText(i2);
        }
        CharSequence text2 =
                fragmentActivity.getText(
                        i != 3
                                ? i != 18
                                        ? R.string
                                                .security_settings_fingerprint_enroll_error_unable_to_process_dialog_title
                                        : R.string
                                                .security_settings_fingerprint_bad_calibration_title
                                : R.string.security_settings_fingerprint_enroll_error_dialog_title);
        FingerprintErrorDialog fingerprintErrorDialog = new FingerprintErrorDialog();
        Bundle bundle = new Bundle();
        bundle.putCharSequence("error_msg", text);
        bundle.putCharSequence("error_title", text2);
        bundle.putInt("error_id", i);
        fingerprintErrorDialog.setArguments(bundle);
        fingerprintErrorDialog.show(supportFragmentManager, FingerprintErrorDialog.class.getName());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 569;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        CharSequence charSequence = getArguments().getCharSequence("error_msg");
        CharSequence charSequence2 = getArguments().getCharSequence("error_title");
        int i = getArguments().getInt("error_id");
        final boolean z = i == 3;
        boolean z2 = i == 2;
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mTitle = charSequence2;
        alertParams.mMessage = charSequence;
        alertParams.mCancelable = false;
        builder.setPositiveButton(
                R.string.security_settings_fingerprint_enroll_dialog_ok,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.fingerprint.FingerprintErrorDialog$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        FingerprintErrorDialog fingerprintErrorDialog = FingerprintErrorDialog.this;
                        boolean z3 = z;
                        fingerprintErrorDialog.getClass();
                        dialogInterface.dismiss();
                        FragmentActivity activity = fingerprintErrorDialog.getActivity();
                        if (z3) {
                            activity.setResult(3);
                        } else {
                            activity.setResult(1);
                        }
                        activity.finish();
                    }
                });
        if (z2) {
            final int i2 = 0;
            builder.setPositiveButton(
                    R.string.security_settings_fingerprint_enroll_dialog_try_again,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.biometrics.fingerprint.FingerprintErrorDialog$$ExternalSyntheticLambda1
                        public final /* synthetic */ FingerprintErrorDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            int i4 = i2;
                            FingerprintErrorDialog fingerprintErrorDialog = this.f$0;
                            fingerprintErrorDialog.getClass();
                            switch (i4) {
                                case 0:
                                    dialogInterface.dismiss();
                                    FragmentActivity activity =
                                            fingerprintErrorDialog.getActivity();
                                    Intent intent = activity.getIntent();
                                    intent.addFlags(33554432);
                                    intent.putExtra("is_canceled", false);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                default:
                                    dialogInterface.dismiss();
                                    FragmentActivity activity2 =
                                            fingerprintErrorDialog.getActivity();
                                    activity2.setResult(1);
                                    activity2.finish();
                                    break;
                            }
                        }
                    });
            final int i3 = 1;
            builder.setNegativeButton(
                    R.string.security_settings_fingerprint_enroll_dialog_ok,
                    new DialogInterface.OnClickListener(
                            this) { // from class:
                                    // com.android.settings.biometrics.fingerprint.FingerprintErrorDialog$$ExternalSyntheticLambda1
                        public final /* synthetic */ FingerprintErrorDialog f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i32) {
                            int i4 = i3;
                            FingerprintErrorDialog fingerprintErrorDialog = this.f$0;
                            fingerprintErrorDialog.getClass();
                            switch (i4) {
                                case 0:
                                    dialogInterface.dismiss();
                                    FragmentActivity activity =
                                            fingerprintErrorDialog.getActivity();
                                    Intent intent = activity.getIntent();
                                    intent.addFlags(33554432);
                                    intent.putExtra("is_canceled", false);
                                    activity.startActivity(intent);
                                    activity.finish();
                                    break;
                                default:
                                    dialogInterface.dismiss();
                                    FragmentActivity activity2 =
                                            fingerprintErrorDialog.getActivity();
                                    activity2.setResult(1);
                                    activity2.finish();
                                    break;
                            }
                        }
                    });
        }
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
