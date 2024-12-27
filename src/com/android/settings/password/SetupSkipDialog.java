package com.android.settings.password;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupSkipDialog extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    public static SetupSkipDialog newInstance(boolean z, boolean z2) {
        SetupSkipDialog setupSkipDialog = new SetupSkipDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("lock_credential_type", -1);
        bundle.putBoolean("frp_supported", z);
        bundle.putBoolean("for_fingerprint", false);
        bundle.putBoolean("for_face", false);
        bundle.putBoolean("for_biometrics", false);
        bundle.putBoolean("is_suw", z2);
        setupSkipDialog.setArguments(bundle);
        return setupSkipDialog;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 573;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        FragmentActivity activity = getActivity();
        if (i != -1) {
            return;
        }
        activity.setResult(11);
        activity.finish();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        boolean z;
        int i;
        int i2;
        AlertDialog.Builder builder;
        Bundle arguments = getArguments();
        boolean z2 = arguments.getBoolean("is_suw");
        boolean z3 = arguments.getBoolean("for_biometrics");
        boolean z4 = arguments.getBoolean("for_face");
        boolean z5 = arguments.getBoolean("for_fingerprint");
        int i3 = arguments.getInt("lock_credential_type");
        if (z4 || z5 || z3) {
            boolean hasFaceHardware = Utils.hasFaceHardware(getContext());
            boolean hasFingerprintHardware = Utils.hasFingerprintHardware(getContext());
            boolean z6 = false;
            if (hasFaceHardware) {
                if (z2) {
                    getContext();
                    FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                    if (featureFactoryImpl == null) {
                        throw new UnsupportedOperationException("No feature factory configured");
                    }
                    featureFactoryImpl.getFaceFeatureProvider().getClass();
                }
                z = true;
            } else {
                z = false;
            }
            if (i3 == 1) {
                boolean z7 = hasFaceHardware && z;
                i =
                        (z7 && hasFingerprintHardware)
                                ? R.string.lock_screen_pattern_skip_biometrics_message
                                : z7
                                        ? R.string.lock_screen_pattern_skip_face_message
                                        : hasFingerprintHardware
                                                ? R.string
                                                        .lock_screen_pattern_skip_fingerprint_message
                                                : R.string.lock_screen_pattern_skip_message;
                i2 = R.string.sec_unlock_set_unlock_pattern_title;
            } else if (i3 != 4) {
                boolean z8 = hasFaceHardware && z;
                i =
                        (z8 && hasFingerprintHardware)
                                ? R.string.lock_screen_pin_skip_biometrics_message
                                : z8
                                        ? R.string.lock_screen_pin_skip_face_message
                                        : hasFingerprintHardware
                                                ? R.string.lock_screen_pin_skip_fingerprint_message
                                                : R.string.lock_screen_pin_skip_message;
                i2 = R.string.sec_unlock_set_unlock_pin_title;
            } else {
                boolean z9 = hasFaceHardware && z;
                i =
                        (z9 && hasFingerprintHardware)
                                ? R.string.lock_screen_password_skip_biometrics_message
                                : z9
                                        ? R.string.lock_screen_password_skip_face_message
                                        : hasFingerprintHardware
                                                ? R.string
                                                        .lock_screen_password_skip_fingerprint_message
                                                : R.string.lock_screen_password_skip_message;
                i2 = R.string.sec_unlock_set_unlock_password_title;
            }
            AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
            builder2.setPositiveButton(R.string.skip_lock_screen_dialog_button_label, this);
            builder2.setNegativeButton(R.string.cancel_lock_screen_dialog_button_label, this);
            if (hasFaceHardware && z) {
                z6 = true;
            }
            builder2.P.mTitle =
                    getString(
                            R.string.lock_screen_skip_setup_title,
                            BiometricUtils.getCombinedScreenLockOptions(
                                    getContext(), getString(i2), hasFingerprintHardware, z6));
            builder2.setMessage(i);
            builder = builder2;
        } else {
            builder = new AlertDialog.Builder(getContext());
            builder.setPositiveButton(R.string.skip_anyway_button_label, this);
            builder.setNegativeButton(R.string.go_back_button_label, this);
            builder.setTitle(R.string.sec_lock_screen_intro_skip_title);
            builder.setMessage(
                    arguments.getBoolean("frp_supported")
                            ? R.string.sec_setup_lock_screen_intro_skip_dialog_text_frp
                            : R.string.sec_setup_lock_screen_intro_skip_dialog_text);
        }
        return builder.create();
    }

    public final void show(FragmentManager fragmentManager) {
        show(fragmentManager, "skip_dialog");
    }
}
