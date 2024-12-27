package com.android.settings.biometrics.face;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.face.FaceManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ScrollView;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.biometrics.BiometricEnrollBase;
import com.android.settings.biometrics.BiometricUtils;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.unfold.updates.FoldProvider$FoldCallback;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.view.IllustrationVideoView;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceEnrollEducation extends BiometricEnrollBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAccessibilityEnabled;
    public FaceManager mFaceManager;
    public View mIllustrationAccessibility;
    public IllustrationVideoView mIllustrationDefault;
    public LottieAnimationView mIllustrationLottie;
    public boolean mIsUsingLottie;
    public Intent mResultIntent;
    public FaceEnrollAccessibilityToggle mSwitchDiversity;
    public final AnonymousClass1 mSwitchDiversityListener = new AnonymousClass1();
    public final FaceEnrollEducation$$ExternalSyntheticLambda4
            mSwitchDiversityOnLayoutChangeListener =
                    new View
                            .OnLayoutChangeListener() { // from class:
                                                        // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view,
                                int i,
                                int i2,
                                int i3,
                                int i4,
                                int i5,
                                int i6,
                                int i7,
                                int i8) {
                            final FaceEnrollEducation faceEnrollEducation =
                                    FaceEnrollEducation.this;
                            int i9 = FaceEnrollEducation.$r8$clinit;
                            faceEnrollEducation.getClass();
                            if (i8 != 0 || i4 == 0) {
                                return;
                            }
                            new Handler(Looper.getMainLooper())
                                    .post(
                                            new Runnable() { // from class:
                                                             // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda6
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    FaceEnrollEducation faceEnrollEducation2 =
                                                            FaceEnrollEducation.this;
                                                    int i10 = FaceEnrollEducation.$r8$clinit;
                                                    ScrollView scrollView =
                                                            (ScrollView)
                                                                    faceEnrollEducation2
                                                                            .findViewById(
                                                                                    R.id
                                                                                            .sud_scroll_view);
                                                    if (scrollView != null) {
                                                        scrollView.fullScroll(130);
                                                    }
                                                    FaceEnrollAccessibilityToggle
                                                            faceEnrollAccessibilityToggle =
                                                                    faceEnrollEducation2
                                                                            .mSwitchDiversity;
                                                    if (faceEnrollAccessibilityToggle != null) {
                                                        faceEnrollAccessibilityToggle
                                                                .removeOnLayoutChangeListener(
                                                                        faceEnrollEducation2
                                                                                .mSwitchDiversityOnLayoutChangeListener);
                                                    }
                                                }
                                            });
                        }
                    };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.face.FaceEnrollEducation$1, reason: invalid class name */
    public final class AnonymousClass1 implements CompoundButton.OnCheckedChangeListener {
        public AnonymousClass1() {}

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            int i =
                    z
                            ? R.string.security_settings_face_enroll_education_message_accessibility
                            : R.string.security_settings_face_enroll_education_message;
            FaceEnrollEducation faceEnrollEducation = FaceEnrollEducation.this;
            int i2 = FaceEnrollEducation.$r8$clinit;
            faceEnrollEducation.setDescriptionText(i);
            if (z) {
                FaceEnrollEducation faceEnrollEducation2 = FaceEnrollEducation.this;
                if (faceEnrollEducation2.mIsUsingLottie) {
                    faceEnrollEducation2.mIllustrationLottie.cancelAnimation();
                    faceEnrollEducation2.mIllustrationLottie.setVisibility(4);
                } else {
                    faceEnrollEducation2.mIllustrationDefault.stop();
                    faceEnrollEducation2.mIllustrationDefault.setVisibility(4);
                }
                FaceEnrollEducation.this.mIllustrationAccessibility.setVisibility(0);
                return;
            }
            FaceEnrollEducation faceEnrollEducation3 = FaceEnrollEducation.this;
            if (faceEnrollEducation3.mIsUsingLottie) {
                faceEnrollEducation3.mIllustrationLottie.setAnimation(R.raw.face_education_lottie);
                faceEnrollEducation3.mIllustrationLottie.setVisibility(0);
                faceEnrollEducation3.mIllustrationLottie.playAnimation$1();
                faceEnrollEducation3.mIllustrationLottie.setProgressInternal(0.0f, true);
            } else {
                faceEnrollEducation3.mIllustrationDefault.setVisibility(0);
                faceEnrollEducation3.mIllustrationDefault.start();
            }
            FaceEnrollEducation.this.mIllustrationAccessibility.setVisibility(4);
        }
    }

    public int getDevicePostureState() {
        return this.mDevicePostureState;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.SDP_PROCESSING_FAILED;
    }

    public FoldProvider$FoldCallback getPostureCallback() {
        return this.mFoldCallback;
    }

    public Intent getPostureGuidanceIntent() {
        return this.mPostureGuidanceIntent;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 7) {
            this.mLaunchedPostureGuidance = false;
            if (i2 == 0 || i2 == 2) {
                getCurrentFocus();
                onSkipButtonClick();
                return;
            }
            return;
        }
        this.mResultIntent = intent;
        boolean booleanExtra =
                intent != null ? intent.getBooleanExtra("finished_enrolling_face", false) : false;
        if (i2 != 3) {
            int i3 = this.mDevicePostureState;
            int i4 = BiometricUtils.sAllowEnrollPosture;
            if (i4 == 0 || i3 == i4) {
                if ((i == 2 || i == 6) && (i2 == 2 || i2 == 1 || i2 == 11 || booleanExtra)) {
                    setResult(i2, intent);
                    finish();
                }
                this.mNextLaunched = false;
                super.onActivityResult(i, i2, intent);
            }
        }
        setResult(i2, intent);
        finish();
        this.mNextLaunched = false;
        super.onActivityResult(i, i2, intent);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.mScreenSizeFoldProvider == null || getPostureCallback() == null) {
            return;
        }
        this.mScreenSizeFoldProvider.onConfigurationChange(configuration);
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase,
              // com.android.settings.core.InstrumentedActivity,
              // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.face_enroll_education);
        setTitle(R.string.security_settings_face_enroll_education_title);
        setDescriptionText(R.string.security_settings_face_enroll_education_message);
        this.mFaceManager = Utils.getFaceManagerOrNull(this);
        this.mIllustrationDefault = (IllustrationVideoView) findViewById(R.id.illustration_default);
        this.mIllustrationLottie = (LottieAnimationView) findViewById(R.id.illustration_lottie);
        this.mIllustrationAccessibility = findViewById(R.id.illustration_accessibility);
        boolean z = getResources().getBoolean(R.bool.config_face_education_use_lottie);
        this.mIsUsingLottie = z;
        boolean z2 = false;
        if (z) {
            this.mIllustrationDefault.stop();
            this.mIllustrationDefault.setVisibility(4);
            this.mIllustrationLottie.setAnimation(R.raw.face_education_lottie);
            this.mIllustrationLottie.setVisibility(0);
            this.mIllustrationLottie.playAnimation$1();
        }
        this.mFooterBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        if (WizardManagerHelper.isAnySetupWizard(getIntent())) {
            final int i = 0;
            this.mFooterBarMixin.setSecondaryButton(
                    new FooterButton(
                            getString(R.string.skip_label),
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda0
                                public final /* synthetic */ FaceEnrollEducation f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i2 = i;
                                    final FaceEnrollEducation faceEnrollEducation = this.f$0;
                                    switch (i2) {
                                        case 0:
                                            faceEnrollEducation.onSkipButtonClick();
                                            break;
                                        case 1:
                                            faceEnrollEducation.getClass();
                                            final Intent intent = new Intent();
                                            byte[] bArr = faceEnrollEducation.mToken;
                                            if (bArr != null) {
                                                intent.putExtra("hw_auth_token", bArr);
                                            }
                                            int i3 = faceEnrollEducation.mUserId;
                                            if (i3 != -10000) {
                                                intent.putExtra("android.intent.extra.USER_ID", i3);
                                            }
                                            intent.putExtra(
                                                    "challenge", faceEnrollEducation.mChallenge);
                                            intent.putExtra(
                                                    "sensor_id", faceEnrollEducation.mSensorId);
                                            intent.putExtra(
                                                    "from_settings_summary",
                                                    faceEnrollEducation.mFromSettingsSummary);
                                            BiometricUtils.copyMultiBiometricExtras(
                                                    faceEnrollEducation.getIntent(), intent);
                                            String string =
                                                    faceEnrollEducation.getString(
                                                            R.string.config_face_enroll);
                                            if (TextUtils.isEmpty(string)) {
                                                intent.setClass(
                                                        faceEnrollEducation,
                                                        FaceEnrollEnrolling.class);
                                            } else {
                                                intent.setComponent(
                                                        ComponentName.unflattenFromString(string));
                                            }
                                            WizardManagerHelper.copyWizardManagerExtras(
                                                    faceEnrollEducation.getIntent(), intent);
                                            Intent intent2 = faceEnrollEducation.mResultIntent;
                                            if (intent2 != null) {
                                                intent.putExtras(intent2);
                                            }
                                            intent.putExtra(
                                                    "accessibility_diversity",
                                                    !faceEnrollEducation.mSwitchDiversity.mSwitch
                                                            .isChecked());
                                            intent.putExtra(
                                                    "enroll_reason",
                                                    faceEnrollEducation
                                                            .getIntent()
                                                            .getIntExtra("enroll_reason", -1));
                                            if (!faceEnrollEducation.mSwitchDiversity.mSwitch
                                                            .isChecked()
                                                    && faceEnrollEducation.mAccessibilityEnabled) {
                                                FaceEnrollAccessibilityDialog
                                                        faceEnrollAccessibilityDialog =
                                                                new FaceEnrollAccessibilityDialog();
                                                faceEnrollAccessibilityDialog
                                                                .mPositiveButtonListener =
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                                                     // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda7
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface dialogInterface,
                                                                    int i4) {
                                                                FaceEnrollEducation
                                                                        faceEnrollEducation2 =
                                                                                FaceEnrollEducation
                                                                                        .this;
                                                                Intent intent3 = intent;
                                                                int i5 =
                                                                        FaceEnrollEducation
                                                                                .$r8$clinit;
                                                                faceEnrollEducation2
                                                                        .startActivityForResult(
                                                                                intent3, 2);
                                                                faceEnrollEducation2.mNextLaunched =
                                                                        true;
                                                            }
                                                        };
                                                faceEnrollAccessibilityDialog.show(
                                                        faceEnrollEducation
                                                                .getSupportFragmentManager(),
                                                        FaceEnrollAccessibilityDialog.class
                                                                .getName());
                                                break;
                                            } else {
                                                faceEnrollEducation.startActivityForResult(
                                                        intent, 2);
                                                faceEnrollEducation.mNextLaunched = true;
                                                break;
                                            }
                                            break;
                                        default:
                                            faceEnrollEducation
                                                    .mSwitchDiversity
                                                    .getSwitch()
                                                    .toggle();
                                            break;
                                    }
                                }
                            },
                            7,
                            2132083806),
                    false);
        } else {
            final int i2 = 0;
            this.mFooterBarMixin.setSecondaryButton(
                    new FooterButton(
                            getString(R.string.security_settings_face_enroll_introduction_cancel),
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda0
                                public final /* synthetic */ FaceEnrollEducation f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    int i22 = i2;
                                    final FaceEnrollEducation faceEnrollEducation = this.f$0;
                                    switch (i22) {
                                        case 0:
                                            faceEnrollEducation.onSkipButtonClick();
                                            break;
                                        case 1:
                                            faceEnrollEducation.getClass();
                                            final Intent intent = new Intent();
                                            byte[] bArr = faceEnrollEducation.mToken;
                                            if (bArr != null) {
                                                intent.putExtra("hw_auth_token", bArr);
                                            }
                                            int i3 = faceEnrollEducation.mUserId;
                                            if (i3 != -10000) {
                                                intent.putExtra("android.intent.extra.USER_ID", i3);
                                            }
                                            intent.putExtra(
                                                    "challenge", faceEnrollEducation.mChallenge);
                                            intent.putExtra(
                                                    "sensor_id", faceEnrollEducation.mSensorId);
                                            intent.putExtra(
                                                    "from_settings_summary",
                                                    faceEnrollEducation.mFromSettingsSummary);
                                            BiometricUtils.copyMultiBiometricExtras(
                                                    faceEnrollEducation.getIntent(), intent);
                                            String string =
                                                    faceEnrollEducation.getString(
                                                            R.string.config_face_enroll);
                                            if (TextUtils.isEmpty(string)) {
                                                intent.setClass(
                                                        faceEnrollEducation,
                                                        FaceEnrollEnrolling.class);
                                            } else {
                                                intent.setComponent(
                                                        ComponentName.unflattenFromString(string));
                                            }
                                            WizardManagerHelper.copyWizardManagerExtras(
                                                    faceEnrollEducation.getIntent(), intent);
                                            Intent intent2 = faceEnrollEducation.mResultIntent;
                                            if (intent2 != null) {
                                                intent.putExtras(intent2);
                                            }
                                            intent.putExtra(
                                                    "accessibility_diversity",
                                                    !faceEnrollEducation.mSwitchDiversity.mSwitch
                                                            .isChecked());
                                            intent.putExtra(
                                                    "enroll_reason",
                                                    faceEnrollEducation
                                                            .getIntent()
                                                            .getIntExtra("enroll_reason", -1));
                                            if (!faceEnrollEducation.mSwitchDiversity.mSwitch
                                                            .isChecked()
                                                    && faceEnrollEducation.mAccessibilityEnabled) {
                                                FaceEnrollAccessibilityDialog
                                                        faceEnrollAccessibilityDialog =
                                                                new FaceEnrollAccessibilityDialog();
                                                faceEnrollAccessibilityDialog
                                                                .mPositiveButtonListener =
                                                        new DialogInterface
                                                                .OnClickListener() { // from class:
                                                                                     // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda7
                                                            @Override // android.content.DialogInterface.OnClickListener
                                                            public final void onClick(
                                                                    DialogInterface dialogInterface,
                                                                    int i4) {
                                                                FaceEnrollEducation
                                                                        faceEnrollEducation2 =
                                                                                FaceEnrollEducation
                                                                                        .this;
                                                                Intent intent3 = intent;
                                                                int i5 =
                                                                        FaceEnrollEducation
                                                                                .$r8$clinit;
                                                                faceEnrollEducation2
                                                                        .startActivityForResult(
                                                                                intent3, 2);
                                                                faceEnrollEducation2.mNextLaunched =
                                                                        true;
                                                            }
                                                        };
                                                faceEnrollAccessibilityDialog.show(
                                                        faceEnrollEducation
                                                                .getSupportFragmentManager(),
                                                        FaceEnrollAccessibilityDialog.class
                                                                .getName());
                                                break;
                                            } else {
                                                faceEnrollEducation.startActivityForResult(
                                                        intent, 2);
                                                faceEnrollEducation.mNextLaunched = true;
                                                break;
                                            }
                                            break;
                                        default:
                                            faceEnrollEducation
                                                    .mSwitchDiversity
                                                    .getSwitch()
                                                    .toggle();
                                            break;
                                    }
                                }
                            },
                            2,
                            2132083806),
                    false);
        }
        final int i3 = 1;
        FooterButton footerButton =
                new FooterButton(
                        getString(R.string.security_settings_face_enroll_education_start),
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda0
                            public final /* synthetic */ FaceEnrollEducation f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                int i22 = i3;
                                final FaceEnrollEducation faceEnrollEducation = this.f$0;
                                switch (i22) {
                                    case 0:
                                        faceEnrollEducation.onSkipButtonClick();
                                        break;
                                    case 1:
                                        faceEnrollEducation.getClass();
                                        final Intent intent = new Intent();
                                        byte[] bArr = faceEnrollEducation.mToken;
                                        if (bArr != null) {
                                            intent.putExtra("hw_auth_token", bArr);
                                        }
                                        int i32 = faceEnrollEducation.mUserId;
                                        if (i32 != -10000) {
                                            intent.putExtra("android.intent.extra.USER_ID", i32);
                                        }
                                        intent.putExtra(
                                                "challenge", faceEnrollEducation.mChallenge);
                                        intent.putExtra("sensor_id", faceEnrollEducation.mSensorId);
                                        intent.putExtra(
                                                "from_settings_summary",
                                                faceEnrollEducation.mFromSettingsSummary);
                                        BiometricUtils.copyMultiBiometricExtras(
                                                faceEnrollEducation.getIntent(), intent);
                                        String string =
                                                faceEnrollEducation.getString(
                                                        R.string.config_face_enroll);
                                        if (TextUtils.isEmpty(string)) {
                                            intent.setClass(
                                                    faceEnrollEducation, FaceEnrollEnrolling.class);
                                        } else {
                                            intent.setComponent(
                                                    ComponentName.unflattenFromString(string));
                                        }
                                        WizardManagerHelper.copyWizardManagerExtras(
                                                faceEnrollEducation.getIntent(), intent);
                                        Intent intent2 = faceEnrollEducation.mResultIntent;
                                        if (intent2 != null) {
                                            intent.putExtras(intent2);
                                        }
                                        intent.putExtra(
                                                "accessibility_diversity",
                                                !faceEnrollEducation.mSwitchDiversity.mSwitch
                                                        .isChecked());
                                        intent.putExtra(
                                                "enroll_reason",
                                                faceEnrollEducation
                                                        .getIntent()
                                                        .getIntExtra("enroll_reason", -1));
                                        if (!faceEnrollEducation.mSwitchDiversity.mSwitch
                                                        .isChecked()
                                                && faceEnrollEducation.mAccessibilityEnabled) {
                                            FaceEnrollAccessibilityDialog
                                                    faceEnrollAccessibilityDialog =
                                                            new FaceEnrollAccessibilityDialog();
                                            faceEnrollAccessibilityDialog.mPositiveButtonListener =
                                                    new DialogInterface
                                                            .OnClickListener() { // from class:
                                                                                 // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda7
                                                        @Override // android.content.DialogInterface.OnClickListener
                                                        public final void onClick(
                                                                DialogInterface dialogInterface,
                                                                int i4) {
                                                            FaceEnrollEducation
                                                                    faceEnrollEducation2 =
                                                                            FaceEnrollEducation
                                                                                    .this;
                                                            Intent intent3 = intent;
                                                            int i5 = FaceEnrollEducation.$r8$clinit;
                                                            faceEnrollEducation2
                                                                    .startActivityForResult(
                                                                            intent3, 2);
                                                            faceEnrollEducation2.mNextLaunched =
                                                                    true;
                                                        }
                                                    };
                                            faceEnrollAccessibilityDialog.show(
                                                    faceEnrollEducation.getSupportFragmentManager(),
                                                    FaceEnrollAccessibilityDialog.class.getName());
                                            break;
                                        } else {
                                            faceEnrollEducation.startActivityForResult(intent, 2);
                                            faceEnrollEducation.mNextLaunched = true;
                                            break;
                                        }
                                        break;
                                    default:
                                        faceEnrollEducation.mSwitchDiversity.getSwitch().toggle();
                                        break;
                                }
                            }
                        },
                        5,
                        2132083805);
        AccessibilityManager accessibilityManager =
                (AccessibilityManager)
                        getApplicationContext().getSystemService(AccessibilityManager.class);
        if (accessibilityManager != null) {
            if (accessibilityManager.isEnabled()
                    && accessibilityManager.isTouchExplorationEnabled()) {
                z2 = true;
            }
            this.mAccessibilityEnabled = z2;
        }
        this.mFooterBarMixin.setPrimaryButton(footerButton);
        final Button button = (Button) findViewById(R.id.accessibility_button);
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FaceEnrollEducation faceEnrollEducation = FaceEnrollEducation.this;
                        Button button2 = button;
                        faceEnrollEducation.mSwitchDiversity.setChecked(true);
                        button2.setVisibility(8);
                        faceEnrollEducation.mSwitchDiversity.setVisibility(0);
                        faceEnrollEducation.mSwitchDiversity.addOnLayoutChangeListener(
                                faceEnrollEducation.mSwitchDiversityOnLayoutChangeListener);
                    }
                });
        FaceEnrollAccessibilityToggle faceEnrollAccessibilityToggle =
                (FaceEnrollAccessibilityToggle) findViewById(R.id.toggle_diversity);
        this.mSwitchDiversity = faceEnrollAccessibilityToggle;
        faceEnrollAccessibilityToggle.setListener(this.mSwitchDiversityListener);
        final int i4 = 2;
        this.mSwitchDiversity.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda0
                    public final /* synthetic */ FaceEnrollEducation f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i22 = i4;
                        final FaceEnrollEducation faceEnrollEducation = this.f$0;
                        switch (i22) {
                            case 0:
                                faceEnrollEducation.onSkipButtonClick();
                                break;
                            case 1:
                                faceEnrollEducation.getClass();
                                final Intent intent = new Intent();
                                byte[] bArr = faceEnrollEducation.mToken;
                                if (bArr != null) {
                                    intent.putExtra("hw_auth_token", bArr);
                                }
                                int i32 = faceEnrollEducation.mUserId;
                                if (i32 != -10000) {
                                    intent.putExtra("android.intent.extra.USER_ID", i32);
                                }
                                intent.putExtra("challenge", faceEnrollEducation.mChallenge);
                                intent.putExtra("sensor_id", faceEnrollEducation.mSensorId);
                                intent.putExtra(
                                        "from_settings_summary",
                                        faceEnrollEducation.mFromSettingsSummary);
                                BiometricUtils.copyMultiBiometricExtras(
                                        faceEnrollEducation.getIntent(), intent);
                                String string =
                                        faceEnrollEducation.getString(R.string.config_face_enroll);
                                if (TextUtils.isEmpty(string)) {
                                    intent.setClass(faceEnrollEducation, FaceEnrollEnrolling.class);
                                } else {
                                    intent.setComponent(ComponentName.unflattenFromString(string));
                                }
                                WizardManagerHelper.copyWizardManagerExtras(
                                        faceEnrollEducation.getIntent(), intent);
                                Intent intent2 = faceEnrollEducation.mResultIntent;
                                if (intent2 != null) {
                                    intent.putExtras(intent2);
                                }
                                intent.putExtra(
                                        "accessibility_diversity",
                                        !faceEnrollEducation.mSwitchDiversity.mSwitch.isChecked());
                                intent.putExtra(
                                        "enroll_reason",
                                        faceEnrollEducation
                                                .getIntent()
                                                .getIntExtra("enroll_reason", -1));
                                if (!faceEnrollEducation.mSwitchDiversity.mSwitch.isChecked()
                                        && faceEnrollEducation.mAccessibilityEnabled) {
                                    FaceEnrollAccessibilityDialog faceEnrollAccessibilityDialog =
                                            new FaceEnrollAccessibilityDialog();
                                    faceEnrollAccessibilityDialog.mPositiveButtonListener =
                                            new DialogInterface
                                                    .OnClickListener() { // from class:
                                                                         // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda7
                                                @Override // android.content.DialogInterface.OnClickListener
                                                public final void onClick(
                                                        DialogInterface dialogInterface, int i42) {
                                                    FaceEnrollEducation faceEnrollEducation2 =
                                                            FaceEnrollEducation.this;
                                                    Intent intent3 = intent;
                                                    int i5 = FaceEnrollEducation.$r8$clinit;
                                                    faceEnrollEducation2.startActivityForResult(
                                                            intent3, 2);
                                                    faceEnrollEducation2.mNextLaunched = true;
                                                }
                                            };
                                    faceEnrollAccessibilityDialog.show(
                                            faceEnrollEducation.getSupportFragmentManager(),
                                            FaceEnrollAccessibilityDialog.class.getName());
                                    break;
                                } else {
                                    faceEnrollEducation.startActivityForResult(intent, 2);
                                    faceEnrollEducation.mNextLaunched = true;
                                    break;
                                }
                                break;
                            default:
                                faceEnrollEducation.mSwitchDiversity.getSwitch().toggle();
                                break;
                        }
                    }
                });
        if (this.mAccessibilityEnabled) {
            button.callOnClick();
        }
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        this.mSwitchDiversityListener.onCheckedChanged(
                this.mSwitchDiversity.getSwitch(), this.mSwitchDiversity.mSwitch.isChecked());
        if (this.mFaceManager.getEnrolledFaces(this.mUserId).size()
                >= getResources()
                        .getInteger(
                                android.R.integer.config_lowMemoryKillerMinFreeKbytesAbsolute)) {
            finish();
        }
    }

    public final void onSkipButtonClick() {
        if (BiometricUtils.tryStartingNextBiometricEnroll(this)) {
            return;
        }
        setResult(2);
        finish();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        if (getPostureGuidanceIntent() == null) {
            Log.d("FaceEducation", "Device do not support posture guidance");
            return;
        }
        BiometricUtils.sAllowEnrollPosture =
                getResources().getInteger(R.integer.config_face_enroll_supported_posture);
        if (getPostureCallback() == null) {
            this.mFoldCallback =
                    new FoldProvider$FoldCallback() { // from class:
                                                      // com.android.settings.biometrics.face.FaceEnrollEducation$$ExternalSyntheticLambda5
                        @Override // com.android.systemui.unfold.updates.FoldProvider$FoldCallback
                        public final void onFoldUpdated(boolean z) {
                            int i = FaceEnrollEducation.$r8$clinit;
                            FaceEnrollEducation faceEnrollEducation = FaceEnrollEducation.this;
                            faceEnrollEducation.getClass();
                            int i2 = z ? 1 : 3;
                            faceEnrollEducation.mDevicePostureState = i2;
                            boolean z2 = faceEnrollEducation.mLaunchedPostureGuidance;
                            int i3 = BiometricUtils.sAllowEnrollPosture;
                            if (i3 == 0 || i2 == i3 || z2 || faceEnrollEducation.mNextLaunched) {
                                return;
                            }
                            faceEnrollEducation.launchPostureGuidance();
                        }
                    };
        }
        if (this.mScreenSizeFoldProvider == null) {
            ScreenSizeFoldProvider screenSizeFoldProvider =
                    new ScreenSizeFoldProvider(getApplicationContext());
            this.mScreenSizeFoldProvider = screenSizeFoldProvider;
            screenSizeFoldProvider.registerCallback(this.mFoldCallback, getMainExecutor());
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        if (super.shouldFinishWhenBackgrounded() && !this.mNextLaunched) {
            int i = this.mDevicePostureState;
            boolean z = this.mLaunchedPostureGuidance;
            int i2 = BiometricUtils.sAllowEnrollPosture;
            if (i2 == 0 || i == i2 || !z) {
                return true;
            }
        }
        return false;
    }
}
