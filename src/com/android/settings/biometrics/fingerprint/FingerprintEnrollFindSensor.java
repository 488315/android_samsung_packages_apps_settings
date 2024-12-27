package com.android.settings.biometrics.fingerprint;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import androidx.compose.foundation.text.CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import com.airbnb.lottie.LottieAnimationView;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollBase;
import com.android.settings.biometrics.BiometricEnrollSidecar;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.widget.LottieColorUtils;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollFindSensor extends BiometricEnrollBase implements BiometricEnrollSidecar.Listener, FoldProvider$FoldCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FingerprintFindSensorAnimation mAnimation;
    public boolean mCanAssumeSfps;
    public boolean mCanAssumeUdfps;
    public LottieAnimationView mIllustrationLottie;
    public boolean mIsFolded;
    public boolean mIsReverseDefaultRotation;
    public boolean mNextClicked;
    public AnonymousClass1 mOrientationEventListener;
    public int mPreviousRotation = 0;
    public ScreenSizeFoldProvider mScreenSizeFoldProvider;
    public FingerprintEnrollSidecar mSidecar;

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public Intent getFingerprintEnrollingIntent() {
        Intent fingerprintEnrollingIntent = super.getFingerprintEnrollingIntent();
        fingerprintEnrollingIntent.putExtra("enroll_reason", getIntent().getIntExtra("enroll_reason", -1));
        return fingerprintEnrollingIntent;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_setZeroPageState;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        Log.d("FingerprintEnrollFindSensor", CoreTextFieldKt$CoreTextField$semanticsModifier$1$1$3$$ExternalSyntheticOutline0.m("onActivityResult(requestCode=", ", resultCode=", i, i2, ")"));
        boolean booleanExtra = intent != null ? intent.getBooleanExtra("finished_enrolling_fingerprint", false) : false;
        if (i2 == 0 && booleanExtra) {
            setResult(i2, intent);
            finish();
            return;
        }
        if (i == 4) {
            if (i2 != -1 || intent == null) {
                finish();
                return;
            } else {
                finish();
                return;
            }
        }
        if (i != 5) {
            super.onActivityResult(i, i2, intent);
            return;
        }
        if (i2 == 1 || i2 == 2 || i2 == 3) {
            setResult(i2);
            finish();
            return;
        }
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this);
        if (fingerprintManagerOrNull.getEnrolledFingerprints().size() >= ((FingerprintSensorPropertiesInternal) fingerprintManagerOrNull.getSensorPropertiesInternal().get(0)).maxEnrollmentsPerUser) {
            finish();
        } else {
            this.mNextClicked = false;
            startLookingForFingerprint();
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        stopLookingForFingerprint();
        super.onBackPressed();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mScreenSizeFoldProvider.onConfigurationChange(configuration);
    }

    /* JADX WARN: Type inference failed for: r1v37, types: [android.view.OrientationEventListener, com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor$1] */
    @Override // com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FingerprintManager fingerprintManagerOrNull = Utils.getFingerprintManagerOrNull(this);
        List sensorPropertiesInternal = fingerprintManagerOrNull.getSensorPropertiesInternal();
        this.mCanAssumeUdfps = sensorPropertiesInternal != null && sensorPropertiesInternal.size() == 1 && ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0)).isAnyUdfpsType();
        boolean z = sensorPropertiesInternal != null && sensorPropertiesInternal.size() == 1 && ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0)).isAnySidefpsType();
        this.mCanAssumeSfps = z;
        setContentView(this.mCanAssumeUdfps ? R.layout.udfps_enroll_find_sensor_layout : z ? R.layout.sfps_enroll_find_sensor_layout : R.layout.fingerprint_enroll_find_sensor);
        ScreenSizeFoldProvider screenSizeFoldProvider = new ScreenSizeFoldProvider(getApplicationContext());
        this.mScreenSizeFoldProvider = screenSizeFoldProvider;
        screenSizeFoldProvider.registerCallback(this, getApplicationContext().getMainExecutor());
        this.mScreenSizeFoldProvider.onConfigurationChange(getApplicationContext().getResources().getConfiguration());
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        final int i = 0;
        footerBarMixin.setSecondaryButton(new FooterButton(getString(R.string.security_settings_fingerprint_enroll_enrolling_skip), new View.OnClickListener(this) { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor$$ExternalSyntheticLambda0
            public final /* synthetic */ FingerprintEnrollFindSensor f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                FingerprintEnrollFindSensor fingerprintEnrollFindSensor = this.f$0;
                switch (i2) {
                    case 0:
                        fingerprintEnrollFindSensor.onSkipButtonClick();
                        break;
                    default:
                        fingerprintEnrollFindSensor.mNextClicked = true;
                        fingerprintEnrollFindSensor.startActivityForResult(fingerprintEnrollFindSensor.getFingerprintEnrollingIntent(), 5);
                        break;
                }
            }
        }, 7, 2132083806), false);
        getLayout().getHeaderTextView().setHyphenationFrequency(1);
        if (this.mCanAssumeSfps) {
            ?? r1 = new OrientationEventListener(this) { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor.1
                @Override // android.view.OrientationEventListener
                public final void onOrientationChanged(int i2) {
                    FingerprintEnrollFindSensor fingerprintEnrollFindSensor = FingerprintEnrollFindSensor.this;
                    int rotation = fingerprintEnrollFindSensor.getDisplay().getRotation();
                    int i3 = FingerprintEnrollFindSensor.$r8$clinit;
                    if (fingerprintEnrollFindSensor.mIsReverseDefaultRotation) {
                        rotation = (rotation + 1) % 4;
                    }
                    int i4 = (rotation + 2) % 4;
                    FingerprintEnrollFindSensor fingerprintEnrollFindSensor2 = FingerprintEnrollFindSensor.this;
                    if (i4 == fingerprintEnrollFindSensor2.mPreviousRotation) {
                        fingerprintEnrollFindSensor2.mPreviousRotation = rotation;
                        fingerprintEnrollFindSensor2.recreate();
                    }
                }
            };
            this.mOrientationEventListener = r1;
            r1.enable();
            int rotation = getDisplay().getRotation();
            if (this.mIsReverseDefaultRotation) {
                rotation = (rotation + 1) % 4;
            }
            this.mPreviousRotation = rotation;
        }
        if (this.mCanAssumeUdfps) {
            setHeaderText(R.string.security_settings_udfps_enroll_find_sensor_title);
            setDescriptionText(R.string.security_settings_udfps_enroll_find_sensor_message);
            this.mIllustrationLottie = (LottieAnimationView) findViewById(R.id.illustration_lottie);
            if (((AccessibilityManager) getSystemService(AccessibilityManager.class)).isEnabled()) {
                this.mIllustrationLottie.setAnimation(R.raw.udfps_edu_a11y_lottie);
            }
        } else if (this.mCanAssumeSfps) {
            setHeaderText(R.string.security_settings_sfps_enroll_find_sensor_title);
            setDescriptionText(R.string.security_settings_sfps_enroll_find_sensor_message);
            this.mIsReverseDefaultRotation = getApplicationContext().getResources().getBoolean(android.R.bool.config_shortPressEarlyOnStemPrimary);
        } else {
            setHeaderText(R.string.security_settings_fingerprint_enroll_find_sensor_title);
            setDescriptionText(R.string.security_settings_fingerprint_enroll_find_sensor_message);
        }
        if (bundle != null) {
            this.mNextClicked = bundle.getBoolean("is_next_clicked", this.mNextClicked);
        }
        if (this.mToken == null && BiometricUtils.containsGatekeeperPasswordHandle(getIntent())) {
            fingerprintManagerOrNull.generateChallenge(this.mUserId, new FingerprintManager.GenerateChallengeCallback() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor$$ExternalSyntheticLambda1
                public final void onChallengeGenerated(int i2, int i3, long j) {
                    FingerprintEnrollFindSensor fingerprintEnrollFindSensor = FingerprintEnrollFindSensor.this;
                    int i4 = FingerprintEnrollFindSensor.$r8$clinit;
                    fingerprintEnrollFindSensor.mChallenge = j;
                    fingerprintEnrollFindSensor.mSensorId = i2;
                    fingerprintEnrollFindSensor.mToken = BiometricUtils.requestGatekeeperHat(fingerprintEnrollFindSensor, fingerprintEnrollFindSensor.getIntent(), fingerprintEnrollFindSensor.mUserId, j);
                    fingerprintEnrollFindSensor.getIntent().putExtra("hw_auth_token", fingerprintEnrollFindSensor.mToken);
                    if (fingerprintEnrollFindSensor.mNextClicked) {
                        return;
                    }
                    fingerprintEnrollFindSensor.startLookingForFingerprint();
                }
            });
        } else if (this.mToken == null) {
            finish();
        } else if (!this.mNextClicked) {
            startLookingForFingerprint();
        }
        this.mAnimation = null;
        if (!this.mCanAssumeUdfps) {
            if (this.mCanAssumeSfps) {
                return;
            }
            KeyEvent.Callback findViewById = findViewById(R.id.fingerprint_sensor_location_animation);
            if (findViewById instanceof FingerprintFindSensorAnimation) {
                this.mAnimation = (FingerprintFindSensorAnimation) findViewById;
                return;
            }
            return;
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        FingerprintFeatureProviderImpl fingerprintFeatureProvider = featureFactoryImpl.getFingerprintFeatureProvider();
        getApplicationContext();
        getIntent();
        fingerprintFeatureProvider.getClass();
        if (isFinishing()) {
            return;
        }
        FooterBarMixin footerBarMixin2 = this.mFooterBarMixin;
        if (footerBarMixin2.primaryButton == null) {
            final int i2 = 1;
            footerBarMixin2.setPrimaryButton(new FooterButton(getString(R.string.security_settings_udfps_enroll_find_sensor_start_button), new View.OnClickListener(this) { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor$$ExternalSyntheticLambda0
                public final /* synthetic */ FingerprintEnrollFindSensor f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i2;
                    FingerprintEnrollFindSensor fingerprintEnrollFindSensor = this.f$0;
                    switch (i22) {
                        case 0:
                            fingerprintEnrollFindSensor.onSkipButtonClick();
                            break;
                        default:
                            fingerprintEnrollFindSensor.mNextClicked = true;
                            fingerprintEnrollFindSensor.startActivityForResult(fingerprintEnrollFindSensor.getFingerprintEnrollingIntent(), 5);
                            break;
                    }
                }
            }, 5, 2132083805));
        }
        LottieAnimationView lottieAnimationView = this.mIllustrationLottie;
        if (lottieAnimationView != null) {
            final int i3 = 1;
            lottieAnimationView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollFindSensor$$ExternalSyntheticLambda0
                public final /* synthetic */ FingerprintEnrollFindSensor f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i3;
                    FingerprintEnrollFindSensor fingerprintEnrollFindSensor = this.f$0;
                    switch (i22) {
                        case 0:
                            fingerprintEnrollFindSensor.onSkipButtonClick();
                            break;
                        default:
                            fingerprintEnrollFindSensor.mNextClicked = true;
                            fingerprintEnrollFindSensor.startActivityForResult(fingerprintEnrollFindSensor.getFingerprintEnrollingIntent(), 5);
                            break;
                    }
                }
            });
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        if (this.mCanAssumeSfps) {
            AnonymousClass1 anonymousClass1 = this.mOrientationEventListener;
            if (anonymousClass1 != null) {
                anonymousClass1.disable();
            }
            this.mOrientationEventListener = null;
        }
        super.onDestroy();
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.mAnimation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.stopAnimation();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {
        if (this.mNextClicked && i == 5) {
            proceedToEnrolling(false);
        } else {
            FingerprintErrorDialog.showErrorDialog(this, i, this instanceof SetupFingerprintEnrollFindSensor);
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        this.mNextClicked = true;
        proceedToEnrolling(true);
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
    public final void onFoldUpdated(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("onFoldUpdated= ", "FingerprintEnrollFindSensor", z);
        this.mIsFolded = z;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.mCanAssumeSfps) {
            this.mScreenSizeFoldProvider.onConfigurationChange(getApplicationContext().getResources().getConfiguration());
            this.mIllustrationLottie = (LottieAnimationView) findViewById(R.id.illustration_lottie);
            int rotation = getApplicationContext().getDisplay().getRotation();
            if (this.mIsReverseDefaultRotation) {
                rotation = (rotation + 1) % 4;
            }
            if (rotation != 1) {
                if (rotation != 2) {
                    if (rotation != 3) {
                        if (this.mIsFolded) {
                            this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_folded_top_right);
                        } else {
                            this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_landscape_top_right);
                        }
                    } else if (this.mIsFolded) {
                        this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_folded_bottom_right);
                    } else {
                        this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_portrait_bottom_right);
                    }
                } else if (this.mIsFolded) {
                    this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_folded_bottom_left);
                } else {
                    this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_landscape_bottom_left);
                }
            } else if (this.mIsFolded) {
                this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_folded_top_left);
            } else {
                this.mIllustrationLottie.setAnimation(R.raw.fingerprint_edu_lottie_portrait_top_left);
            }
            LottieColorUtils.applyDynamicColors(getApplicationContext(), this.mIllustrationLottie);
            this.mIllustrationLottie.setVisibility(0);
            this.mIllustrationLottie.playAnimation$1();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("is_next_clicked", this.mNextClicked);
    }

    public void onSkipButtonClick() {
        stopLookingForFingerprint();
        setResult(2);
        finish();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.mAnimation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.startAnimation();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        super.onStop();
        ScreenSizeFoldProvider screenSizeFoldProvider = this.mScreenSizeFoldProvider;
        screenSizeFoldProvider.getClass();
        ((ArrayList) screenSizeFoldProvider.callbacks).remove(this);
        FingerprintFindSensorAnimation fingerprintFindSensorAnimation = this.mAnimation;
        if (fingerprintFindSensorAnimation != null) {
            fingerprintFindSensorAnimation.pauseAnimation();
        }
    }

    public final void proceedToEnrolling(boolean z) {
        FingerprintEnrollSidecar fingerprintEnrollSidecar = this.mSidecar;
        if (fingerprintEnrollSidecar != null) {
            if (z && fingerprintEnrollSidecar.cancelEnrollment()) {
                return;
            }
            this.mSidecar.mListener = null;
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m = BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(supportFragmentManager, supportFragmentManager);
            m.remove(this.mSidecar);
            m.commitInternal(true);
            this.mSidecar = null;
            startActivityForResult(getFingerprintEnrollingIntent(), 5);
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        return super.shouldFinishWhenBackgrounded() && !this.mNextClicked;
    }

    public final void startLookingForFingerprint() {
        if (this.mCanAssumeUdfps) {
            return;
        }
        FingerprintEnrollSidecar fingerprintEnrollSidecar = (FingerprintEnrollSidecar) getSupportFragmentManager().findFragmentByTag("sidecar");
        this.mSidecar = fingerprintEnrollSidecar;
        if (fingerprintEnrollSidecar == null) {
            this.mSidecar = new FingerprintEnrollSidecar(1, this, getIntent());
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            BackStackRecord m = BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(supportFragmentManager, supportFragmentManager);
            m.doAddOp(0, this.mSidecar, "sidecar", 1);
            m.commitInternal(true);
        }
        this.mSidecar.setListener(this);
    }

    public final void stopLookingForFingerprint() {
        FingerprintEnrollSidecar fingerprintEnrollSidecar = this.mSidecar;
        if (fingerprintEnrollSidecar != null) {
            fingerprintEnrollSidecar.mListener = null;
            fingerprintEnrollSidecar.cancelEnrollment();
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.remove(this.mSidecar);
            backStackRecord.commitInternal(true);
            this.mSidecar = null;
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
    }
}
