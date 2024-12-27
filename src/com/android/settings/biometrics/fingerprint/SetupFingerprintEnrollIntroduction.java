package com.android.settings.biometrics.fingerprint;

import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;

import com.android.settings.Utils;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupFingerprintEnrollIntroduction extends FingerprintEnrollIntroduction {
    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcut;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final Intent getSecBiometricsEnrollIntent() {
        return FingerprintSettingsUtils.getFingerprintEnrollmentIntentForAosp(this, true);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z;
        if (intent != null) {
            boolean booleanExtra = intent.getBooleanExtra("finished_enrolling_face", false);
            z = intent.getBooleanExtra("finished_enrolling_fingerprint", false);
            if (booleanExtra) {
                getIntent().removeExtra("enroll_after_face");
                getIntent().removeExtra("enroll_after_finger");
            }
        } else {
            z = false;
        }
        if (i == 2
                && ((KeyguardManager) getSystemService(KeyguardManager.class)).isKeyguardSecure()) {
            if (i2 == 1) {
                if (intent == null) {
                    intent = new Intent();
                }
                FingerprintManager fingerprintManagerOrNull =
                        Utils.getFingerprintManagerOrNull(this);
                if (fingerprintManagerOrNull != null) {
                    intent.putExtra(
                            "fingerprint_enrolled_count",
                            fingerprintManagerOrNull.getEnrolledFingerprints(this.mUserId).size());
                }
            }
            if (i2 == 0 && z) {
                setResult(i2, intent);
                finish();
                return;
            }
        } else if (i == 6) {
            boolean z2 = checkMaxEnrolled() == 0;
            if (i2 == 2 || i2 == 1) {
                setResult(1, intent);
                finish();
                return;
            } else if (i2 != 0) {
                super.onActivityResult(i, i2, intent);
                return;
            } else {
                if (z2) {
                    return;
                }
                finish();
                return;
            }
        }
        super.onActivityResult(i, i2, intent);
    }
}
