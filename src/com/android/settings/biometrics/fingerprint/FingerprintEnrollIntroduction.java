package com.android.settings.biometrics.fingerprint;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollIntroduction;
import com.android.settings.biometrics.BiometricEnrollIntroduction$$ExternalSyntheticLambda0;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settingslib.HelpUtils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.span.LinkSpan;
import com.google.android.setupdesign.util.DeviceHelper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollIntroduction extends BiometricEnrollIntroduction {

    @VisibleForTesting private FingerprintManager mFingerprintManager;

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final int checkMaxEnrolled() {
        List sensorPropertiesInternal;
        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
        Intent intent = getIntent();
        boolean z = intent != null && intent.getBooleanExtra("deferredSetup", false);
        Intent intent2 = getIntent();
        boolean z2 = intent2 != null && intent2.getBooleanExtra("portalSetup", false);
        Intent intent3 = getIntent();
        boolean z3 = intent3 != null && intent3.getBooleanExtra("isSuwSuggestedActionFlow", false);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager == null
                || (sensorPropertiesInternal = fingerprintManager.getSensorPropertiesInternal())
                        == null
                || sensorPropertiesInternal.isEmpty()) {
            return R.string.fingerprint_intro_error_unknown;
        }
        int i =
                ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0))
                        .maxEnrollmentsPerUser;
        int size = this.mFingerprintManager.getEnrolledFingerprints(this.mUserId).size();
        int integer =
                getApplicationContext()
                        .getResources()
                        .getInteger(R.integer.suw_max_fingerprints_enrollable);
        if (!isAnySetupWizard || z || z2 || z3) {
            if (size >= i) {
                return R.string.fingerprint_intro_error_max;
            }
            return 0;
        }
        if (size >= integer) {
            return R.string.fingerprint_intro_error_max;
        }
        return 0;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final void getChallenge(
            final BiometricEnrollIntroduction$$ExternalSyntheticLambda0
                    biometricEnrollIntroduction$$ExternalSyntheticLambda0) {
        this.mFingerprintManager.generateChallenge(
                this.mUserId,
                new FingerprintManager
                        .GenerateChallengeCallback() { // from class:
                                                       // com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction$$ExternalSyntheticLambda0
                    public final void onChallengeGenerated(int i, int i2, long j) {
                        BiometricEnrollIntroduction$$ExternalSyntheticLambda0.this
                                .onChallengeGenerated(i, j);
                    }
                });
    }

    @VisibleForTesting
    public FingerprintManager getFingerprintManager() {
        return Utils.getFingerprintManagerOrNull(this);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentMode;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final FooterButton getNextButton() {
        FooterBarMixin footerBarMixin = this.mFooterBarMixin;
        if (footerBarMixin != null) {
            return footerBarMixin.primaryButton;
        }
        return null;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public Intent getSecBiometricsEnrollIntent() {
        Log.d("SecBiometricsEnrollment", "return Fingerprint enrollment intent");
        return FingerprintSettingsUtils.getFingerprintEnrollmentIntentForAosp(this, false);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public final Intent getSetResultIntentExtra(Intent intent) {
        if (this.mFromSettingsSummary && this.mToken != null && this.mChallenge != -1) {
            if (intent == null) {
                intent = new Intent();
            }
            intent.putExtra("hw_auth_token", this.mToken);
            intent.putExtra("challenge", this.mChallenge);
        }
        return intent;
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase
    public void initViews$1() {
        setDescriptionText(
                getString(
                        Utils.isPrivateProfile(getApplicationContext(), this.mUserId)
                                ? R.string.private_space_fingerprint_enroll_introduction_message
                                : R.string
                                        .security_settings_fingerprint_enroll_introduction_v3_message,
                        new Object[] {DeviceHelper.getDeviceName(this)}));
        super.initViews$1();
    }

    @Override // com.google.android.setupdesign.span.LinkSpan.OnClickListener
    public final void onClick(LinkSpan linkSpan) {
        if ("url".equals(linkSpan.link)) {
            Intent helpIntent =
                    HelpUtils.getHelpIntent(
                            this, getString(R.string.help_url_fingerprint), getClass().getName());
            if (helpIntent == null) {
                Log.w("FingerprintIntro", "Null help intent.");
                return;
            }
            try {
                startActivityForResult(helpIntent, 3);
            } catch (ActivityNotFoundException e) {
                Log.w("FingerprintIntro", "Activity was not found for intent, " + e);
            }
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction,
              // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FingerprintManager fingerprintManager = getFingerprintManager();
        this.mFingerprintManager = fingerprintManager;
        if (fingerprintManager == null) {
            Log.e("FingerprintIntro", "Null FingerprintManager");
            finish();
            return;
        }
        List sensorPropertiesInternal =
                ((FingerprintManager) getSystemService(FingerprintManager.class))
                        .getSensorPropertiesInternal();
        if (sensorPropertiesInternal != null && sensorPropertiesInternal.size() == 1) {
            ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0))
                    .isAnyUdfpsType();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        this.mFingerprintManager = null;
        super.onDestroy();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollIntroduction
    public void onFinishedEnrolling(Intent intent) {
        if (BiometricUtils.tryStartingNextBiometricEnroll(this)) {
            return;
        }
        setResult(1, intent);
        finish();
    }
}
