package com.android.settings.biometrics;

import android.content.Intent;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.sysprop.SetupWizardProperties;
import android.util.FeatureFlagUtils;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.face.FaceEnrollIntroduction;
import com.android.settings.biometrics.face.FaceEnrollParentalConsent;
import com.android.settings.biometrics.face.FaceFeatureProvider;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollIntroduction;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollParentalConsent;
import com.android.settings.biometrics.fingerprint.SetupFingerprintEnrollFindSensor;
import com.android.settings.biometrics.fingerprint.SetupFingerprintEnrollIntroduction;
import com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity;
import com.android.settings.core.InstrumentedActivity;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.ChooseLockSettingsHelper;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.transition.TransitionHelper;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BiometricEnrollActivity extends InstrumentedActivity {
    public boolean mConfirmingCredentials;
    public boolean mFaceConsent;
    public boolean mFingerConsent;
    public Long mGkPwHandle;
    public boolean mIsEnrollActionLogged;
    public boolean mIsSingleEnrolling;
    public ParentalConsentHelper mParentalConsentHelper;
    public Bundle mParentalOptions;
    public int mUserId = UserHandle.myUserId();
    public Bundle mPassThroughExtrasFromChosenLockInSuw = null;
    public boolean mHasFeatureFace = false;
    public boolean mHasFeatureFingerprint = false;
    public boolean mIsFaceEnrollable = false;
    public boolean mIsFingerprintEnrollable = false;
    public boolean mParentalOptionsRequired = false;
    public boolean mSkipReturnToParent = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class InternalActivity extends BiometricEnrollActivity {}

    @Override // android.app.Activity
    public final void finish() {
        Long l = this.mGkPwHandle;
        if (l != null && !(this instanceof InternalActivity)) {
            new LockPatternUtils(this).removeGatekeeperPasswordHandle(l.longValue());
            Log.d("BiometricUtils", "Removed handle");
        }
        super.finish();
    }

    public final void finishOrLaunchHandToParent(int i) {
        if (!this.mParentalOptionsRequired) {
            setResult(i, newResultIntent());
            finish();
        } else if (this.mSkipReturnToParent || this.mFaceConsent || this.mFingerConsent) {
            setResult(-1, newResultIntent());
            finish();
        } else {
            Intent intent = getIntent();
            Intent intent2 = new Intent(this, (Class<?>) BiometricHandoffActivity.class);
            WizardManagerHelper.copyWizardManagerExtras(intent, intent2);
            startActivityForResult(intent2, 4);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1586;
    }

    public final void launchFaceOnlyEnroll() {
        if (this.mIsSingleEnrolling) {
            return;
        }
        this.mIsSingleEnrolling = true;
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, (Class<?>) FaceEnrollIntroduction.class);
        WizardManagerHelper.copyWizardManagerExtras(intent, intent2);
        launchSingleSensorEnrollActivity(6, intent2);
    }

    public final void launchFingerprintOnlyEnroll() {
        Intent intent;
        if (this.mIsSingleEnrolling) {
            return;
        }
        this.mIsSingleEnrolling = true;
        if (getIntent().getBooleanExtra("skip_intro", false)
                && (this instanceof InternalActivity)) {
            Intent intent2 = getIntent();
            boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(intent2);
            if (FeatureFlagUtils.isEnabled(this, "settings_biometrics2_enrollment")) {
                intent =
                        new Intent(
                                this,
                                isAnySetupWizard
                                        ? FingerprintEnrollmentActivity.SetupActivity.class
                                        : FingerprintEnrollmentActivity.class);
                intent.putExtra("skip_intro", true);
            } else {
                intent =
                        new Intent(
                                this,
                                (Class<?>)
                                        (isAnySetupWizard
                                                ? SetupFingerprintEnrollFindSensor.class
                                                : FingerprintEnrollFindSensor.class));
            }
            if (isAnySetupWizard) {
                WizardManagerHelper.copyWizardManagerExtras(intent2, intent);
            }
        } else {
            Intent intent3 = getIntent();
            boolean isAnySetupWizard2 = WizardManagerHelper.isAnySetupWizard(intent3);
            if (FeatureFlagUtils.isEnabled(this, "settings_biometrics2_enrollment")) {
                intent =
                        new Intent(
                                this,
                                isAnySetupWizard2
                                        ? FingerprintEnrollmentActivity.SetupActivity.class
                                        : FingerprintEnrollmentActivity.class);
            } else {
                intent =
                        new Intent(
                                this,
                                (Class<?>)
                                        (isAnySetupWizard2
                                                ? SetupFingerprintEnrollIntroduction.class
                                                : FingerprintEnrollIntroduction.class));
            }
            if (isAnySetupWizard2) {
                WizardManagerHelper.copyWizardManagerExtras(intent3, intent);
            }
        }
        launchSingleSensorEnrollActivity(5, intent);
    }

    public final void launchSingleSensorEnrollActivity(int i, Intent intent) {
        boolean z = this instanceof InternalActivity;
        byte[] byteArrayExtra = z ? getIntent().getByteArrayExtra("hw_auth_token") : null;
        Long l = this.mGkPwHandle;
        int i2 = this.mUserId;
        if (byteArrayExtra != null) {
            intent.putExtra("hw_auth_token", byteArrayExtra);
        }
        if (l != null) {
            intent.putExtra("gk_pw_handle", l.longValue());
        }
        if (z) {
            intent.putExtra("android.intent.extra.USER_ID", i2);
        }
        if (i != 0) {
            startActivityForResult(intent, i);
        } else {
            startActivity(intent);
            finish();
        }
    }

    public final Intent newResultIntent() {
        FingerprintManager fingerprintManagerOrNull;
        FaceManager faceManagerOrNull;
        Intent intent = new Intent();
        Bundle bundle = this.mPassThroughExtrasFromChosenLockInSuw;
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("face", this.mFaceConsent);
        bundle2.putBoolean("fingerprint", this.mFingerConsent);
        int i = 0;
        bundle2.putBoolean("iris", false);
        intent.putExtra("consent_status", bundle2);
        if (this.mFaceConsent
                && (faceManagerOrNull = Utils.getFaceManagerOrNull(this)) != null
                && faceManagerOrNull.hasEnrolledTemplates(this.mUserId)) {
            i = 256;
        }
        if (this.mFingerConsent
                && (fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this)) != null
                && fingerprintManagerOrNull.hasEnrolledFingerprints(this.mUserId)) {
            i |= 1;
        }
        if (i > 0) {
            SecurityUtils.setBiometricLock(
                    getApplicationContext(), new LockPatternUtils(this), i, this.mUserId);
        }
        return intent;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1
                && i2 == 1
                && intent != null
                && intent.getExtras() != null
                && intent.getExtras().size() > 0
                && WizardManagerHelper.isAnySetupWizard(getIntent())) {
            this.mPassThroughExtrasFromChosenLockInSuw = intent.getExtras();
        }
        Log.d(
                "BiometricEnrollActivity",
                CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m(
                        "onActivityResult(requestCode=", ", resultCode=", i, i2, ")"));
        if (this.mParentalConsentHelper == null) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    "handleOnActivityResultWhileEnrolling, request = ",
                    ", resultCode = ",
                    i,
                    i2,
                    "BiometricEnrollActivity");
            if (i == 1 || i == 2) {
                this.mConfirmingCredentials = false;
                if (((i != 1 || i2 != 1) && (i != 2 || i2 != -1))
                        || (!this.mIsFaceEnrollable && !this.mIsFingerprintEnrollable)) {
                    Log.d("BiometricEnrollActivity", "Unknown result for set/choose lock: " + i2);
                    setResult(i2, newResultIntent());
                    finish();
                    return;
                }
                TransitionHelper.applyForwardTransition(this, 6);
                this.mGkPwHandle = Long.valueOf(BiometricUtils.getGatekeeperPasswordHandle(intent));
                ParentalConsentHelper parentalConsentHelper = this.mParentalConsentHelper;
                if (parentalConsentHelper != null) {
                    parentalConsentHelper.mGkPwHandle =
                            BiometricUtils.getGatekeeperPasswordHandle(intent);
                }
                if (this.mIsFingerprintEnrollable) {
                    launchFingerprintOnlyEnroll();
                    return;
                } else {
                    if (this.mIsFaceEnrollable) {
                        launchFaceOnlyEnroll();
                        return;
                    }
                    return;
                }
            }
            if (i == 4) {
                setResult(-1, newResultIntent());
                finish();
                return;
            }
            if (i == 5) {
                this.mIsSingleEnrolling = false;
                if (this.mIsFaceEnrollable) {
                    launchFaceOnlyEnroll();
                    return;
                } else {
                    finishOrLaunchHandToParent(i2);
                    return;
                }
            }
            if (i == 6) {
                this.mIsSingleEnrolling = false;
                finishOrLaunchHandToParent(i2);
                return;
            }
            if (i != 10) {
                Log.w(
                        "BiometricEnrollActivity",
                        "Unknown enrolling requestCode: " + i + ", finishing");
                finish();
                return;
            }
            if (i2 == 4) {
                this.mFaceConsent = true;
                this.mFingerConsent = true;
                if (this.mIsFingerprintEnrollable) {
                    launchFingerprintOnlyEnroll();
                    return;
                } else if (this.mIsFaceEnrollable) {
                    launchFaceOnlyEnroll();
                    return;
                }
            }
            setResult(-1, newResultIntent());
            finish();
            return;
        }
        boolean z = ParentalControlsUtils.parentConsentRequired(this, 8) != null;
        boolean z2 = ParentalControlsUtils.parentConsentRequired(this, 2) != null;
        boolean z3 = z && this.mHasFeatureFace && this.mIsFaceEnrollable;
        boolean z4 = z2 && this.mHasFeatureFingerprint;
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "faceConsentRequired: ",
                        z,
                        ", fpConsentRequired: ",
                        z2,
                        ", hasFeatureFace: ");
        m.append(this.mHasFeatureFace);
        m.append(", hasFeatureFingerprint: ");
        m.append(this.mHasFeatureFingerprint);
        m.append(", faceEnrollable: ");
        m.append(this.mIsFaceEnrollable);
        m.append(", fpEnrollable: ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                m, this.mIsFingerprintEnrollable, "BiometricEnrollActivity");
        ParentalConsentHelper parentalConsentHelper2 = this.mParentalConsentHelper;
        parentalConsentHelper2.mRequireFace = z3;
        parentalConsentHelper2.mRequireFingerprint = z4;
        overridePendingTransition(R.anim.sud_slide_next_in, R.anim.sud_slide_next_out);
        if (i == 1 || i == 2) {
            this.mConfirmingCredentials = false;
            if ((i != 1 || i2 != 1) && (i != 2 || i2 != -1)) {
                Log.d("BiometricEnrollActivity", "Unknown result for set/choose lock: " + i2);
                setResult(i2);
                finish();
                return;
            }
            this.mGkPwHandle = Long.valueOf(BiometricUtils.getGatekeeperPasswordHandle(intent));
            ParentalConsentHelper parentalConsentHelper3 = this.mParentalConsentHelper;
            if (parentalConsentHelper3 != null) {
                parentalConsentHelper3.mGkPwHandle =
                        BiometricUtils.getGatekeeperPasswordHandle(intent);
            }
            if (this.mParentalConsentHelper.launchNext(this)) {
                return;
            }
            Log.e(
                    "BiometricEnrollActivity",
                    "Nothing to prompt for consent (no modalities enabled)!");
            finish();
            return;
        }
        if (i != 3) {
            Log.w(
                    "BiometricEnrollActivity",
                    "Unknown consenting requestCode: " + i + ", finishing");
            finish();
            return;
        }
        if (i2 != 4 && i2 != 5) {
            Log.d("BiometricEnrollActivity", "Unknown or cancelled parental consent");
            setResult(0, newResultIntent());
            finish();
            return;
        }
        ParentalConsentHelper parentalConsentHelper4 = this.mParentalConsentHelper;
        if (intent != null) {
            parentalConsentHelper4.getClass();
            int intExtra = intent.getIntExtra("sensor_modality", 0);
            if (intExtra == 2) {
                Boolean bool = parentalConsentHelper4.mConsentFingerprint;
                if (i2 == 4) {
                    bool = Boolean.TRUE;
                } else if (i2 == 5) {
                    bool = Boolean.FALSE;
                }
                parentalConsentHelper4.mConsentFingerprint = bool;
            } else if (intExtra == 8) {
                Boolean bool2 = parentalConsentHelper4.mConsentFace;
                if (i2 == 4) {
                    bool2 = Boolean.TRUE;
                } else if (i2 == 5) {
                    bool2 = Boolean.FALSE;
                }
                parentalConsentHelper4.mConsentFace = bool2;
            }
        }
        if (parentalConsentHelper4.launchNext(this)) {
            return;
        }
        ParentalConsentHelper parentalConsentHelper5 = this.mParentalConsentHelper;
        parentalConsentHelper5.getClass();
        Bundle bundle = new Bundle();
        Boolean bool3 = parentalConsentHelper5.mConsentFace;
        bundle.putBoolean("face", bool3 != null ? bool3.booleanValue() : false);
        bundle.putIntArray("face_strings", FaceEnrollParentalConsent.CONSENT_STRING_RESOURCES);
        Boolean bool4 = parentalConsentHelper5.mConsentFingerprint;
        bundle.putBoolean("fingerprint", bool4 != null ? bool4.booleanValue() : false);
        bundle.putIntArray(
                "fingerprint_strings", FingerprintEnrollParentalConsent.CONSENT_STRING_RESOURCES);
        bundle.putBoolean("iris", false);
        bundle.putIntArray("iris_strings", new int[0]);
        this.mParentalOptions = bundle;
        this.mParentalConsentHelper = null;
        Log.d(
                "BiometricEnrollActivity",
                "Enrollment consent options set, starting enrollment: " + this.mParentalOptions);
        startEnrollWith(4095, WizardManagerHelper.isAnySetupWizard(getIntent()));
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        int theme2 = SetupWizardUtils.getTheme(this, getIntent());
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, theme2, z);
    }

    @Override // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        int i;
        int i2;
        int i3;
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (this instanceof InternalActivity) {
            this.mUserId =
                    intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
            if (BiometricUtils.containsGatekeeperPasswordHandle(getIntent())) {
                this.mGkPwHandle =
                        Long.valueOf(BiometricUtils.getGatekeeperPasswordHandle(getIntent()));
            }
        } else if (WizardManagerHelper.isAnySetupWizard(getIntent())
                && BiometricUtils.containsGatekeeperPasswordHandle(getIntent())) {
            this.mGkPwHandle =
                    Long.valueOf(BiometricUtils.getGatekeeperPasswordHandle(getIntent()));
        }
        if (bundle != null) {
            this.mConfirmingCredentials = bundle.getBoolean("confirming_credentials", false);
            this.mIsSingleEnrolling = bundle.getBoolean("is_single_enrolling", false);
            this.mPassThroughExtrasFromChosenLockInSuw =
                    bundle.getBundle("pass_through_extras_from_chosen_lock_in_suw");
            this.mIsEnrollActionLogged = bundle.getBoolean("enroll_action_logged", false);
            this.mParentalOptions = bundle.getBundle("enroll_preferences");
            if (bundle.containsKey("gk_pw_handle")) {
                this.mGkPwHandle = Long.valueOf(bundle.getLong("gk_pw_handle"));
            }
        }
        if (!this.mIsEnrollActionLogged
                && "android.settings.BIOMETRIC_ENROLL".equals(intent.getAction())) {
            this.mIsEnrollActionLogged = true;
            BiometricManager biometricManager =
                    (BiometricManager) getSystemService(BiometricManager.class);
            if (biometricManager != null) {
                i = biometricManager.canAuthenticate(15);
                i3 = biometricManager.canAuthenticate(255);
                i2 =
                        biometricManager.canAuthenticate(
                                NetworkAnalyticsConstants.DataPoints.FLAG_UID);
            } else {
                i = 12;
                i2 = 12;
                i3 = 12;
            }
            FrameworkStatsLog.write(
                    355,
                    i == 0,
                    i3 == 0,
                    i2 == 0,
                    intent.hasExtra("android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED"),
                    intent.getIntExtra(
                            "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED", 0));
        }
        if (intent.getStringExtra("theme") == null) {
            String stringExtra = intent.getStringExtra("theme");
            if (stringExtra == null) {
                stringExtra = (String) SetupWizardProperties.theme().orElse(ApnSettings.MVNO_NONE);
            }
            intent.putExtra("theme", stringExtra);
        }
        this.mHasFeatureFingerprint =
                getApplicationContext()
                        .getPackageManager()
                        .hasSystemFeature("android.hardware.fingerprint");
        this.mHasFeatureFace = !intent.getBooleanExtra("fingerprint_enrollment_only", false);
        int intExtra =
                getIntent()
                        .getIntExtra(
                                "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED", 255);
        Log.d(
                "BiometricEnrollActivity",
                "Authenticators: " + BiometricManager.authenticatorToStr(intExtra));
        this.mParentalOptionsRequired = intent.getBooleanExtra("require_consent", false);
        this.mSkipReturnToParent = intent.getBooleanExtra("skip_return_to_parent", false);
        boolean isAnySetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
        boolean z = this.mHasFeatureFace && this.mHasFeatureFingerprint;
        StringBuilder sb = new StringBuilder("parentalOptionsRequired: ");
        sb.append(this.mParentalOptionsRequired);
        sb.append(", skipReturnToParent: ");
        sb.append(this.mSkipReturnToParent);
        sb.append(", isSetupWizard: ");
        sb.append(isAnySetupWizard);
        sb.append(", isMultiSensor: ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "BiometricEnrollActivity");
        if (this.mHasFeatureFace) {
            FaceManager faceManager = (FaceManager) getSystemService(FaceManager.class);
            List sensorPropertiesInternal = faceManager.getSensorPropertiesInternal();
            int integer =
                    getApplicationContext()
                            .getResources()
                            .getInteger(R.integer.suw_max_faces_enrollable);
            if (!sensorPropertiesInternal.isEmpty()) {
                FaceSensorPropertiesInternal faceSensorPropertiesInternal =
                        (FaceSensorPropertiesInternal) sensorPropertiesInternal.get(0);
                if (!isAnySetupWizard) {
                    integer = faceSensorPropertiesInternal.maxEnrollmentsPerUser;
                }
                boolean z2 = faceSensorPropertiesInternal.sensorStrength == 2;
                this.mIsFaceEnrollable =
                        faceManager.getEnrolledFaces(this.mUserId).size() < integer;
                if (intExtra == 15 && !z2) {
                    this.mIsFaceEnrollable = false;
                }
                if ((isAnySetupWizard
                                || (this.mParentalOptionsRequired
                                        && !WizardManagerHelper.isUserSetupComplete(this)))
                        && z
                        && this.mIsFaceEnrollable) {
                    FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                    if (featureFactoryImpl == null) {
                        throw new UnsupportedOperationException("No feature factory configured");
                    }
                    FaceFeatureProvider faceFeatureProvider =
                            featureFactoryImpl.getFaceFeatureProvider();
                    getApplicationContext();
                    faceFeatureProvider.getClass();
                    this.mIsFaceEnrollable = true;
                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                            new StringBuilder("config_suw_support_face_enroll: "),
                            this.mIsFaceEnrollable,
                            "BiometricEnrollActivity");
                }
            }
        }
        if (this.mHasFeatureFingerprint) {
            int intExtra2 =
                    getIntent()
                            .getIntExtra(
                                    "android.provider.extra.BIOMETRIC_AUTHENTICATORS_ALLOWED", 255);
            FingerprintManager fingerprintManager =
                    (FingerprintManager) getSystemService(FingerprintManager.class);
            List sensorPropertiesInternal2 = fingerprintManager.getSensorPropertiesInternal();
            int integer2 =
                    getApplicationContext()
                            .getResources()
                            .getInteger(R.integer.suw_max_fingerprints_enrollable);
            if (!sensorPropertiesInternal2.isEmpty()) {
                if (!isAnySetupWizard) {
                    integer2 =
                            ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal2.get(0))
                                    .maxEnrollmentsPerUser;
                }
                boolean z3 =
                        ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal2.get(0))
                                        .sensorStrength
                                == 2;
                this.mIsFingerprintEnrollable =
                        fingerprintManager.getEnrolledFingerprints(this.mUserId).size() < integer2;
                if (intExtra2 == 15 && !z3) {
                    this.mIsFingerprintEnrollable = false;
                }
            }
        }
        if (isAnySetupWizard && this.mParentalOptionsRequired) {
            Log.w(
                    "BiometricEnrollActivity",
                    "Enrollment with parental consent is not supported when launched  directly from"
                        + " SuW - skipping enrollment");
            setResult(2);
            finish();
            return;
        }
        if (isAnySetupWizard
                && this.mParentalOptionsRequired
                && ParentalControlsUtils.parentConsentRequired(this, 10) != null) {
            Log.w("BiometricEnrollActivity", "Consent was already setup - skipping enrollment");
            setResult(2);
            finish();
            return;
        }
        boolean z4 = this.mParentalOptionsRequired;
        if (z4) {
            if (!this.mSkipReturnToParent) {
                setOrConfirmCredentialsNow();
                return;
            }
            this.mFaceConsent = true;
            this.mFingerConsent = true;
            setResult(-1, newResultIntent());
            finish();
            return;
        }
        if (!z4 || this.mParentalOptions != null) {
            startEnrollWith(intExtra, isAnySetupWizard);
            return;
        }
        Long l = this.mGkPwHandle;
        ParentalConsentHelper parentalConsentHelper = new ParentalConsentHelper();
        parentalConsentHelper.mGkPwHandle = l != null ? l.longValue() : 0L;
        this.mParentalConsentHelper = parentalConsentHelper;
        setOrConfirmCredentialsNow();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("confirming_credentials", this.mConfirmingCredentials);
        bundle.putBoolean("is_single_enrolling", this.mIsSingleEnrolling);
        bundle.putBundle(
                "pass_through_extras_from_chosen_lock_in_suw",
                this.mPassThroughExtrasFromChosenLockInSuw);
        bundle.putBoolean("enroll_action_logged", this.mIsEnrollActionLogged);
        Bundle bundle2 = this.mParentalOptions;
        if (bundle2 != null) {
            bundle.putBundle("enroll_preferences", bundle2);
        }
        Long l = this.mGkPwHandle;
        if (l != null) {
            bundle.putLong("gk_pw_handle", l.longValue());
        }
    }

    public final void setOrConfirmCredentialsNow() {
        if (this.mConfirmingCredentials) {
            return;
        }
        this.mConfirmingCredentials = true;
        int i = this.mUserId;
        if (new LockPatternUtils(this)
                        .getActivePasswordQuality(
                                ((UserManager) getSystemService(UserManager.class))
                                        .getCredentialOwnerProfile(i))
                != 0) {
            Log.d("BiometricEnrollActivity", "launchConfirmLock");
            ChooseLockSettingsHelper.Builder builder = new ChooseLockSettingsHelper.Builder(this);
            builder.mRequestCode = 2;
            builder.mRequestGatekeeperPasswordHandle = true;
            builder.mForegroundOnly = true;
            builder.mReturnCredentials = true;
            int i2 = this.mUserId;
            if (i2 != -10000) {
                builder.mUserId = i2;
            }
            if (builder.show()) {
                return;
            }
            finish();
            return;
        }
        Log.d("BiometricEnrollActivity", "launchChooseLock");
        Intent chooseLockIntent = BiometricUtils.getChooseLockIntent(this, getIntent());
        chooseLockIntent.putExtra("hide_insecure_options", true);
        chooseLockIntent.putExtra("request_gk_pw_handle", true);
        boolean z = this.mIsFingerprintEnrollable;
        if (z && this.mIsFaceEnrollable) {
            chooseLockIntent.putExtra("for_biometrics", true);
        } else if (this.mIsFaceEnrollable) {
            chooseLockIntent.putExtra("for_face", true);
        } else if (z) {
            chooseLockIntent.putExtra("for_fingerprint", true);
        }
        int i3 = this.mUserId;
        if (i3 != -10000) {
            chooseLockIntent.putExtra("android.intent.extra.USER_ID", i3);
        }
        startActivityForResult(chooseLockIntent, 1);
    }

    public final void startEnrollWith(int i, boolean z) {
        int canAuthenticate;
        if (!z
                && !this.mParentalOptionsRequired
                && (canAuthenticate =
                                ((BiometricManager) getSystemService(BiometricManager.class))
                                        .canAuthenticate(i))
                        != 11) {
            Log.e(
                    "BiometricEnrollActivity",
                    "Unexpected result (has enrollments): " + canAuthenticate);
            finish();
            return;
        }
        boolean z2 = this.mHasFeatureFace;
        boolean z3 = this.mHasFeatureFingerprint;
        boolean z4 = this.mParentalOptionsRequired;
        if (z4) {
            if (!this.mSkipReturnToParent) {
                setOrConfirmCredentialsNow();
                return;
            }
            this.mFaceConsent = true;
            this.mFingerConsent = true;
            setResult(-1, newResultIntent());
            finish();
            return;
        }
        if (!z && i == 32768) {
            Intent intent = new Intent(this, (Class<?>) ChooseLockGeneric.class);
            intent.putExtra("hide_insecure_options", true);
            launchSingleSensorEnrollActivity(0, intent);
            finish();
            return;
        }
        if (z2 || z3) {
            if (z3) {
                launchFingerprintOnlyEnroll();
                return;
            } else {
                if (this.mIsFaceEnrollable) {
                    launchFaceOnlyEnroll();
                    return;
                }
                return;
            }
        }
        if (z4) {
            Log.d("BiometricEnrollActivity", "No consent for any modality: skipping enrollment");
            setResult(-1, newResultIntent());
        } else {
            Log.e("BiometricEnrollActivity", "Unknown state, finishing (was SUW: " + z + ")");
        }
        finish();
    }
}
