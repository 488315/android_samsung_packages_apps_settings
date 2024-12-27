package com.android.settings.biometrics.fingerprint;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollParentalConsent extends FingerprintEnrollIntroduction {
    public static final int[] CONSENT_STRING_RESOURCES = {
        R.string.security_settings_fingerprint_enroll_consent_introduction_title,
        R.string.security_settings_fingerprint_enroll_introduction_consent_message,
        R.string.security_settings_fingerprint_enroll_introduction_footer_title_consent_1,
        R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_2,
        R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_3,
        R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_4,
        R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_5,
        R.string.security_settings_fingerprint_v2_enroll_introduction_footer_message_consent_6
    };

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1892;
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setDescriptionText(
                R.string.security_settings_fingerprint_enroll_introduction_consent_message);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void onEnrollmentSkipped(Intent intent) {
        Intent intent2 = new Intent();
        intent2.putExtra("sensor_modality", 2);
        setResult(5, intent2);
        finish();
    }

    @Override // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void onFinishedEnrolling(Intent intent) {
        Intent intent2 = new Intent();
        intent2.putExtra("sensor_modality", 2);
        setResult(4, intent2);
        finish();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final boolean onSetOrConfirmCredentials() {
        return true;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void updateDescriptionText() {
        setDescriptionText(
                R.string.security_settings_fingerprint_enroll_introduction_consent_message);
    }
}
