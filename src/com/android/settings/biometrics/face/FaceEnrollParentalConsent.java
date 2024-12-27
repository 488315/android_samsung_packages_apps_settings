package com.android.settings.biometrics.face;

import android.content.Intent;
import android.os.Bundle;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollParentalConsent extends FaceEnrollIntroduction {
    public static final int[] CONSENT_STRING_RESOURCES = {
        R.string.security_settings_face_enroll_consent_introduction_title,
        R.string.security_settings_face_enroll_introduction_consent_message,
        R.string.security_settings_face_enroll_introduction_info_consent_glasses,
        R.string.security_settings_face_enroll_introduction_info_consent_looking,
        R.string.security_settings_face_enroll_introduction_info_consent_gaze,
        R.string.security_settings_face_enroll_introduction_how_consent_message,
        R.string.security_settings_face_enroll_introduction_control_consent_title,
        R.string.security_settings_face_enroll_introduction_control_consent_message,
        R.string.security_settings_face_enroll_introduction_consent_message_0,
        R.string.security_settings_face_enroll_introduction_consent_message_0_class3,
        R.string.security_settings_face_enroll_introduction_info_consent_less_secure
    };

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction,
              // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1893;
    }

    public final void onConsentResult(boolean z) {
        Intent intent = new Intent();
        intent.putExtra("sensor_modality", 8);
        setResult(z ? 4 : 5, intent);
        finish();
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        updateDescriptionText();
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void onEnrollmentSkipped(Intent intent) {
        onConsentResult(false);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void onFinishedEnrolling(Intent intent) {
        onConsentResult(true);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction
    public final void onSkipButtonClick() {
        onConsentResult(false);
    }

    @Override // com.android.settings.biometrics.face.FaceEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void updateDescriptionText() {
        super.updateDescriptionText();
        setDescriptionText(R.string.security_settings_face_enroll_introduction_consent_message_0);
    }
}
