package com.android.settings.biometrics.fingerprint;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupFingerprintEnrollFindSensor extends FingerprintEnrollFindSensor {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SkipFingerprintDialog extends InstrumentedDialogFragment
            implements DialogInterface.OnClickListener {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 573;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            FragmentActivity activity;
            if (i == -1 && (activity = getActivity()) != null) {
                activity.setResult(2);
                activity.finish();
            }
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2132084211);
            builder.setTitle(R.string.setup_fingerprint_enroll_skip_title);
            builder.setPositiveButton(R.string.skip_anyway_button_label, this);
            builder.setNegativeButton(R.string.go_back_button_label, this);
            builder.setMessage(R.string.setup_fingerprint_enroll_skip_after_adding_lock_text);
            return builder.create();
        }
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor,
              // com.android.settings.biometrics.BiometricEnrollBase
    public final Intent getFingerprintEnrollingIntent() {
        Intent intent = new Intent(this, (Class<?>) SetupFingerprintEnrollEnrolling.class);
        intent.putExtra("hw_auth_token", this.mToken);
        intent.putExtra("challenge", this.mChallenge);
        intent.putExtra("sensor_id", this.mSensorId);
        int i = this.mUserId;
        if (i != -10000) {
            intent.putExtra("android.intent.extra.USER_ID", i);
        }
        BiometricUtils.copyMultiBiometricExtras(getIntent(), intent);
        WizardManagerHelper.copyWizardManagerExtras(getIntent(), intent);
        return intent;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor
    public final void onSkipButtonClick() {
        new SkipFingerprintDialog().show(getSupportFragmentManager(), "skip_dialog");
    }
}
