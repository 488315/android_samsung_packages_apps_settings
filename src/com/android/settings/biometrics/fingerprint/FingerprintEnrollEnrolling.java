package com.android.settings.biometrics.fingerprint;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.window.embedding.ActivityEmbeddingController;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.biometrics.BiometricEnrollSidecar;
import com.android.settings.biometrics.BiometricUtils;
import com.android.settings.biometrics.BiometricsEnrollEnrolling;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.biometrics.BiometricsSplitScreenDialog;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling;
import com.android.settings.biometrics.fingerprint.UdfpsEnrollHelper;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature;
import com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeatureImpl;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.Utils;
import com.android.settingslib.display.DisplayDensityUtils;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.template.DescriptionMixin;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.view.BottomScrollView;
import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FingerprintEnrollEnrolling extends BiometricsEnrollEnrolling {
    public AccessibilityManager mAccessibilityManager;
    public boolean mAnimationCancelled;
    public boolean mCanAssumeSfps;
    public boolean mCanAssumeUdfps;
    public TextView mErrorText;
    public Interpolator mFastOutLinearInInterpolator;
    public Interpolator mFastOutSlowInInterpolator;
    public FingerprintManager mFingerprintManager;
    public boolean mHaveShownSfpsCenterLottie;
    public boolean mHaveShownSfpsLeftEdgeLottie;
    public boolean mHaveShownSfpsNoAnimationLottie;
    public boolean mHaveShownSfpsRightEdgeLottie;
    public boolean mHaveShownSfpsTipLottie;
    public boolean mHaveShownUdfpsCenterLottie;
    public boolean mHaveShownUdfpsGuideLottie;
    public boolean mHaveShownUdfpsLeftEdgeLottie;
    public boolean mHaveShownUdfpsRightEdgeLottie;
    public boolean mHaveShownUdfpsTipLottie;
    public Animator mHelpAnimation;
    public AnimatedVectorDrawable mIconAnimationDrawable;
    public AnimatedVectorDrawable mIconBackgroundBlinksDrawable;
    public int mIconTouchCount;
    public LottieAnimationView mIllustrationLottie;
    public boolean mIsAccessibilityEnabled;

    @VisibleForTesting
    boolean mIsCanceled;
    public boolean mIsSetupWizard;
    public Interpolator mLinearOutSlowInInterpolator;
    public AnonymousClass1 mOrientationEventListener;
    public ObjectAnimator mProgressAnim;
    public ProgressBar mProgressBar;
    public boolean mRestoring;
    public boolean mShouldShowLottie;

    @VisibleForTesting
    UdfpsEnrollHelper mUdfpsEnrollHelper;
    public Vibrator mVibrator;
    public static final VibrationEffect VIBRATE_EFFECT_ERROR = VibrationEffect.createWaveform(new long[]{0, 5, 55, 60}, -1);
    public static final VibrationAttributes FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES = VibrationAttributes.createForUsage(66);
    public int mPreviousRotation = 0;
    public SfpsEnrollmentFeature mSfpsEnrollmentFeature = new EmptySfpsEnrollmentFeature();
    public final AnonymousClass2 mProgressAnimationListener = new Animator.AnimatorListener() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling.2
        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
            VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
            fingerprintEnrollEnrolling.mAnimationCancelled = true;
            AnimatedVectorDrawable animatedVectorDrawable = fingerprintEnrollEnrolling.mIconAnimationDrawable;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.stop();
            }
            if (FingerprintEnrollEnrolling.this.mProgressBar.getProgress() >= 10000) {
                FingerprintEnrollEnrolling fingerprintEnrollEnrolling2 = FingerprintEnrollEnrolling.this;
                fingerprintEnrollEnrolling2.mProgressBar.postDelayed(fingerprintEnrollEnrolling2.mDelayedFinishRunnable, fingerprintEnrollEnrolling2.mCanAssumeUdfps ? 400L : 250L);
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationStart(Animator animator) {
            FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
            VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
            AnimatedVectorDrawable animatedVectorDrawable = fingerprintEnrollEnrolling.mIconAnimationDrawable;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.start();
            }
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public final void onAnimationRepeat(Animator animator) {
        }
    };
    public final AnonymousClass3 mDelayedFinishRunnable = new AnonymousClass3(0, this);
    public final AnonymousClass4 mIconAnimationCallback = new AnonymousClass4();
    public final AnonymousClass3 mShowDialogRunnable = new AnonymousClass3(1, this);
    public final AnonymousClass3 mTouchAgainRunnable = new AnonymousClass3(2, this);

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$3, reason: invalid class name */
    public final class AnonymousClass3 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass3(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    FingerprintEnrollEnrolling fingerprintEnrollEnrolling = (FingerprintEnrollEnrolling) this.this$0;
                    VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    fingerprintEnrollEnrolling.launchFinish(fingerprintEnrollEnrolling.mToken);
                    break;
                case 1:
                    FingerprintEnrollEnrolling fingerprintEnrollEnrolling2 = (FingerprintEnrollEnrolling) this.this$0;
                    VibrationEffect vibrationEffect2 = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    fingerprintEnrollEnrolling2.mIconTouchCount = 0;
                    new IconTouchDialog().show(fingerprintEnrollEnrolling2.getSupportFragmentManager(), "fps_icon_touch_dialog");
                    break;
                case 2:
                    FingerprintEnrollEnrolling fingerprintEnrollEnrolling3 = (FingerprintEnrollEnrolling) this.this$0;
                    String string = fingerprintEnrollEnrolling3.getString(R.string.security_settings_fingerprint_enroll_lift_touch_again);
                    VibrationEffect vibrationEffect3 = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    fingerprintEnrollEnrolling3.showError$1(string);
                    break;
                default:
                    FingerprintEnrollEnrolling fingerprintEnrollEnrolling4 = FingerprintEnrollEnrolling.this;
                    VibrationEffect vibrationEffect4 = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    AnimatedVectorDrawable animatedVectorDrawable = fingerprintEnrollEnrolling4.mIconAnimationDrawable;
                    if (animatedVectorDrawable != null) {
                        animatedVectorDrawable.start();
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$4, reason: invalid class name */
    public final class AnonymousClass4 extends Animatable2.AnimationCallback {
        public AnonymousClass4() {
        }

        @Override // android.graphics.drawable.Animatable2.AnimationCallback
        public final void onAnimationEnd(Drawable drawable) {
            FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
            if (fingerprintEnrollEnrolling.mAnimationCancelled) {
                return;
            }
            fingerprintEnrollEnrolling.mProgressBar.post(new AnonymousClass3(3, this));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EmptySfpsEnrollmentFeature implements SfpsEnrollmentFeature {
        @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
        public final int getCurrentSfpsEnrollStage(int i, FingerprintEnrollEnrolling$$ExternalSyntheticLambda0 fingerprintEnrollEnrolling$$ExternalSyntheticLambda0) {
            throw new IllegalStateException("Assume sfps but no SfpsEnrollmentFeature impl.");
        }

        @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
        public final float getEnrollStageThreshold(Context context, int i) {
            throw new IllegalStateException("Assume sfps but no SfpsEnrollmentFeature impl.");
        }

        @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
        public final int getFeaturedStageHeaderResource(int i) {
            throw new IllegalStateException("Assume sfps but no SfpsEnrollmentFeature impl.");
        }

        @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
        public final Animator getHelpAnimator(View view) {
            throw new IllegalStateException("Assume sfps but no SfpsEnrollmentFeature impl.");
        }

        @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
        public final int getSfpsEnrollLottiePerStage(int i) {
            throw new IllegalStateException("Assume sfps but no SfpsEnrollmentFeature impl.");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class IconTouchDialog extends InstrumentedDialogFragment {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$IconTouchDialog$1, reason: invalid class name */
        public final class AnonymousClass1 implements DialogInterface.OnClickListener {
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 568;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), 2132084211);
            builder.setTitle(R.string.security_settings_fingerprint_enroll_touch_dialog_title);
            builder.setMessage(R.string.security_settings_fingerprint_enroll_touch_dialog_message);
            builder.setPositiveButton(R.string.security_settings_fingerprint_enroll_dialog_ok, new AnonymousClass1());
            return builder.create();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    @Retention(RetentionPolicy.SOURCE)
    public @interface SfpsEnrollStage {
    }

    public final void announceEnrollmentProgress(CharSequence charSequence) {
        AccessibilityEvent obtain = AccessibilityEvent.obtain();
        obtain.setEventType(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        obtain.setClassName(getClass().getName());
        obtain.setPackageName(getPackageName());
        obtain.getText().add(charSequence);
        this.mAccessibilityManager.sendAccessibilityEvent(obtain);
    }

    public final void applySfpsErrorDynamicColors(Context context, boolean z) {
        if (this.mProgressBar != null) {
            int color = context.getColor(R.color.sfps_enrollment_progress_bar_error_color);
            int color2 = context.getColor(R.color.sfps_enrollment_progress_bar_fill_color);
            if (!z) {
                color = color2;
            }
            this.mProgressBar.setProgressTintList(ColorStateList.valueOf(color));
            this.mProgressBar.setProgressTintMode(PorterDuff.Mode.SRC);
            this.mProgressBar.invalidate();
        }
        if (this.mIllustrationLottie != null) {
            final int color3 = context.getColor(R.color.sfps_enrollment_fp_error_color);
            int color4 = context.getColor(R.color.sfps_enrollment_fp_captured_color);
            if (!z) {
                color3 = color4;
            }
            LottieAnimationView lottieAnimationView = this.mIllustrationLottie;
            lottieAnimationView.lottieDrawable.addValueCallback(new KeyPath(".blue100", "**"), LottieProperty.COLOR_FILTER, new LottieAnimationView.AnonymousClass1(new SimpleLottieValueCallback() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda5
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final Object getValue() {
                    VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    return new PorterDuffColorFilter(color3, PorterDuff.Mode.SRC_ATOP);
                }
            }));
            this.mIllustrationLottie.invalidate();
        }
    }

    @VisibleForTesting
    public void configureEnrollmentStage(int i) {
        if (!this.mCanAssumeSfps) {
            setDescriptionText(ApnSettings.MVNO_NONE);
        }
        LottieCompositionFactory.fromRawRes(this, i, LottieCompositionFactory.rawResCacheKey(this, i)).addListener(new LottieListener() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda4
            @Override // com.airbnb.lottie.LottieListener
            public final void onResult(Object obj) {
                LottieComposition lottieComposition = (LottieComposition) obj;
                FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
                LottieAnimationView lottieAnimationView = fingerprintEnrollEnrolling.mIllustrationLottie;
                if (lottieAnimationView == null || lottieComposition == null) {
                    return;
                }
                lottieAnimationView.setComposition(lottieComposition);
                lottieAnimationView.setVisibility(0);
                lottieAnimationView.playAnimation$1();
                if (fingerprintEnrollEnrolling.mCanAssumeSfps) {
                    fingerprintEnrollEnrolling.mSfpsEnrollmentFeature.getClass();
                }
            }
        });
    }

    public final void dismissTouchDialogIfSfps() {
        IconTouchDialog iconTouchDialog;
        if (this.mCanAssumeSfps && (iconTouchDialog = (IconTouchDialog) getSupportFragmentManager().findFragmentByTag("fps_icon_touch_dialog")) != null && iconTouchDialog.isResumed()) {
            iconTouchDialog.dismissInternal(false, false);
        }
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public Intent getFinishIntent() {
        return new Intent(this, (Class<?>) FingerprintEnrollFinish.class);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp;
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final BiometricEnrollSidecar getSidecar() {
        return new FingerprintEnrollSidecar(2, this, getIntent());
    }

    @VisibleForTesting
    public int getStageThresholdSteps(int i) {
        BiometricEnrollSidecar biometricEnrollSidecar = this.mSidecar;
        if (biometricEnrollSidecar != null && biometricEnrollSidecar.mEnrollmentSteps != -1) {
            return Math.round(this.mSidecar.mEnrollmentSteps * (this.mCanAssumeSfps ? this.mSfpsEnrollmentFeature.getEnrollStageThreshold(this, i) : this.mFingerprintManager.getEnrollStageThreshold(i)));
        }
        Log.w("FingerprintEnrollEnrolling", "getStageThresholdSteps: Enrollment not started yet");
        return 1;
    }

    public final boolean isStageHalfCompleted() {
        int i;
        BiometricEnrollSidecar biometricEnrollSidecar = this.mSidecar;
        if (biometricEnrollSidecar == null || (i = biometricEnrollSidecar.mEnrollmentSteps) == -1) {
            return false;
        }
        int i2 = i - biometricEnrollSidecar.mEnrollmentRemaining;
        int i3 = 0;
        int i4 = 0;
        while (i3 < this.mFingerprintManager.getEnrollStageCount()) {
            int stageThresholdSteps = getStageThresholdSteps(i3);
            if (i2 >= i4 && i2 < stageThresholdSteps) {
                return i2 - i4 >= (stageThresholdSteps - i4) / 2;
            }
            i3++;
            i4 = stageThresholdSteps;
        }
        return true;
    }

    public final void maybeHideSfpsText(Configuration configuration) {
        HeaderMixin headerMixin = (HeaderMixin) getLayout().getMixin(HeaderMixin.class);
        DescriptionMixin descriptionMixin = (DescriptionMixin) getLayout().getMixin(DescriptionMixin.class);
        boolean z = configuration.orientation == 2;
        if (this.mCanAssumeSfps) {
            descriptionMixin.getTextView().setVisibility(8);
            headerMixin.getTextView().setHyphenationFrequency(0);
            if (!z) {
                headerMixin.setAutoTextSizeEnabled(false);
                headerMixin.getTextView().setLines(4);
            } else {
                headerMixin.setAutoTextSizeEnabled(true);
                headerMixin.getTextView().setMinLines(0);
                headerMixin.getTextView().setMaxLines(10);
            }
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onAcquired(boolean z) {
        UdfpsEnrollHelper.Listener listener;
        int i;
        UdfpsEnrollHelper udfpsEnrollHelper = this.mUdfpsEnrollHelper;
        if (udfpsEnrollHelper != null && (listener = udfpsEnrollHelper.mListener) != null) {
            final boolean z2 = z && (i = udfpsEnrollHelper.mRemainingSteps) <= udfpsEnrollHelper.mPace && i >= 0;
            final UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) listener;
            udfpsEnrollView.mHandler.post(new Runnable() { // from class: com.android.settings.biometrics.fingerprint.UdfpsEnrollView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsEnrollView udfpsEnrollView2 = UdfpsEnrollView.this;
                    boolean z3 = z2;
                    int i2 = UdfpsEnrollView.$r8$clinit;
                    UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView2.mFingerprintDrawable;
                    if (udfpsEnrollDrawable.mSkipDraw) {
                        udfpsEnrollDrawable.mSkipDraw = false;
                        udfpsEnrollDrawable.invalidateSelf();
                    }
                    udfpsEnrollView2.mFingerprintDrawable.invalidateSelf();
                    if (z3) {
                        UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable = udfpsEnrollView2.mFingerprintProgressDrawable;
                        udfpsEnrollProgressBarDrawable.updateState(0, udfpsEnrollProgressBarDrawable.mTotalSteps, false);
                    }
                }
            });
        }
        if (this.mCanAssumeSfps) {
            this.mSfpsEnrollmentFeature.getClass();
        }
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(R.style.SetupWizardPartnerResource, true);
        super.onApplyThemeResource(theme, i, z);
    }

    @VisibleForTesting
    public void onCancelEnrollment(int i) {
        this.mIsCanceled = true;
        FingerprintErrorDialog.showErrorDialog(this, i, this instanceof SetupFingerprintEnrollEnrolling);
        cancelEnrollment();
        this.mAnimationCancelled = true;
        AnimatedVectorDrawable animatedVectorDrawable = this.mIconAnimationDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.stop();
        }
        AnonymousClass1 anonymousClass1 = this.mOrientationEventListener;
        if (anonymousClass1 != null) {
            anonymousClass1.disable();
        }
        this.mOrientationEventListener = null;
        if (this.mCanAssumeUdfps) {
            return;
        }
        this.mErrorText.removeCallbacks(this.mTouchAgainRunnable);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        maybeHideSfpsText(configuration);
        int i = configuration.orientation;
        if (i == 1) {
            updateOrientation(1);
        } else if (i != 2) {
            Log.e("FingerprintEnrollEnrolling", "Error unhandled configuration change");
        } else {
            updateOrientation(2);
        }
    }

    /* JADX WARN: Type inference failed for: r2v11, types: [android.view.OrientationEventListener, com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$1] */
    @Override // com.android.settings.biometrics.BiometricEnrollBase, com.android.settings.core.InstrumentedActivity, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (isInMultiWindowMode() && !ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this)) {
            BiometricsSplitScreenDialog biometricsSplitScreenDialog = new BiometricsSplitScreenDialog();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("biometrics_modality", 2);
            bundle2.putBoolean("destroy_activity", true);
            biometricsSplitScreenDialog.setArguments(bundle2);
            biometricsSplitScreenDialog.setCancelable(false);
            biometricsSplitScreenDialog.show(getSupportFragmentManager(), BiometricsSplitScreenDialog.class.getName());
        }
        if (bundle != null) {
            this.mRestoring = true;
            this.mIsCanceled = bundle.getBoolean("is_canceled", false);
            this.mPreviousRotation = bundle.getInt("previous_rotation", getDisplay().getRotation());
        }
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FingerprintManager.class);
        this.mFingerprintManager = fingerprintManager;
        List sensorPropertiesInternal = fingerprintManager.getSensorPropertiesInternal();
        this.mCanAssumeUdfps = sensorPropertiesInternal != null && sensorPropertiesInternal.size() == 1 && ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0)).isAnyUdfpsType();
        this.mCanAssumeSfps = sensorPropertiesInternal != null && sensorPropertiesInternal.size() == 1 && ((FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0)).isAnySidefpsType();
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(AccessibilityManager.class);
        this.mAccessibilityManager = accessibilityManager;
        this.mIsAccessibilityEnabled = accessibilityManager.isEnabled();
        ?? r2 = new OrientationEventListener(this) { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling.1
            @Override // android.view.OrientationEventListener
            public final void onOrientationChanged(int i) {
                int rotation = FingerprintEnrollEnrolling.this.getDisplay().getRotation();
                FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
                int i2 = fingerprintEnrollEnrolling.mPreviousRotation;
                if ((i2 == 1 && rotation == 3) || (i2 == 3 && rotation == 1)) {
                    fingerprintEnrollEnrolling.mPreviousRotation = rotation;
                    fingerprintEnrollEnrolling.recreate();
                }
            }
        };
        this.mOrientationEventListener = r2;
        r2.enable();
        this.mPreviousRotation = getDisplay().getRotation();
        if (this.mCanAssumeUdfps) {
            final UdfpsEnrollEnrollingView udfpsEnrollEnrollingView = (UdfpsEnrollEnrollingView) getLayoutInflater().inflate(R.layout.udfps_enroll_enrolling, (ViewGroup) null, false);
            UdfpsEnrollHelper udfpsEnrollHelper = (UdfpsEnrollHelper) getSupportFragmentManager().findFragmentByTag("udfps_helper");
            this.mUdfpsEnrollHelper = udfpsEnrollHelper;
            if (udfpsEnrollHelper == null) {
                this.mUdfpsEnrollHelper = new UdfpsEnrollHelper(getApplicationContext(), this.mFingerprintManager);
                FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
                BackStackRecord m = BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(supportFragmentManager, supportFragmentManager);
                m.doAddOp(0, this.mUdfpsEnrollHelper, "udfps_helper", 1);
                m.commitInternal(true);
            }
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = (FingerprintSensorPropertiesInternal) sensorPropertiesInternal.get(0);
            UdfpsEnrollHelper udfpsEnrollHelper2 = this.mUdfpsEnrollHelper;
            udfpsEnrollEnrollingView.mAccessibilityManager = this.mAccessibilityManager;
            DisplayInfo displayInfo = new DisplayInfo();
            udfpsEnrollEnrollingView.mContext.getDisplay().getDisplayInfo(displayInfo);
            udfpsEnrollEnrollingView.mUdfpsUtils.getClass();
            float scaleFactor = UdfpsUtils.getScaleFactor(displayInfo);
            Rect rect = fingerprintSensorPropertiesInternal.getLocation().getRect();
            rect.scale(scaleFactor);
            udfpsEnrollEnrollingView.mUdfpsEnrollView.setOverlayParams(new UdfpsOverlayParams(rect, new Rect(0, displayInfo.getNaturalHeight() / 2, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight()), displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight(), scaleFactor, displayInfo.rotation, fingerprintSensorPropertiesInternal.sensorType));
            udfpsEnrollEnrollingView.mUdfpsEnrollView.setEnrollHelper(udfpsEnrollHelper2);
            if (!udfpsEnrollEnrollingView.mIsLandscape) {
                FrameLayout frameLayout = (FrameLayout) udfpsEnrollEnrollingView.findViewById(R.id.layout_container);
                int dimension = (int) udfpsEnrollEnrollingView.getResources().getDimension(R.dimen.udfps_lottie_padding_top);
                frameLayout.setPadding(0, dimension, 0, 0);
                int i = -dimension;
                ((ImageView) udfpsEnrollEnrollingView.mUdfpsEnrollView.findViewById(R.id.udfps_enroll_animation_fp_progress_view)).setPadding(0, i, 0, dimension);
                ((ImageView) udfpsEnrollEnrollingView.mUdfpsEnrollView.findViewById(R.id.udfps_enroll_animation_fp_view)).setPadding(0, i, 0, dimension);
                final TextView descriptionTextView = udfpsEnrollEnrollingView.getDescriptionTextView();
                udfpsEnrollEnrollingView.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() { // from class: com.android.settings.biometrics.fingerprint.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnDrawListener
                    public final void onDraw() {
                        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2 = UdfpsEnrollEnrollingView.this;
                        View view = descriptionTextView;
                        int i2 = UdfpsEnrollEnrollingView.$r8$clinit;
                        udfpsEnrollEnrollingView2.getClass();
                        if (view.getVisibility() == 0 && udfpsEnrollEnrollingView2.hasOverlap(view, udfpsEnrollEnrollingView2.mUdfpsEnrollView)) {
                            view.setVisibility(8);
                        }
                    }
                });
            } else if (udfpsEnrollEnrollingView.mShouldUseReverseLandscape) {
                ViewGroup viewGroup = (ViewGroup) udfpsEnrollEnrollingView.mHeaderView.getParent();
                viewGroup.removeView(udfpsEnrollEnrollingView.mHeaderView);
                viewGroup.addView(udfpsEnrollEnrollingView.mHeaderView);
                ((BottomScrollView) udfpsEnrollEnrollingView.mHeaderView.findViewById(R.id.sud_header_scroll_view)).setScrollIndicators(0);
            }
            if (udfpsEnrollEnrollingView.mAccessibilityManager.isEnabled()) {
                udfpsEnrollEnrollingView.findViewById(udfpsEnrollEnrollingView.mIsLandscape ? R.id.sud_landscape_content_area : R.id.sud_layout_content).setOnHoverListener(new View.OnHoverListener() { // from class: com.android.settings.biometrics.fingerprint.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnHoverListener
                    public final boolean onHover(View view, MotionEvent motionEvent) {
                        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2 = UdfpsEnrollEnrollingView.this;
                        UdfpsUtils udfpsUtils = udfpsEnrollEnrollingView2.mUdfpsUtils;
                        int pointerId = motionEvent.getPointerId(0);
                        UdfpsOverlayParams overlayParams = udfpsEnrollEnrollingView2.mUdfpsEnrollView.getOverlayParams();
                        udfpsUtils.getClass();
                        Point portraitTouch = UdfpsUtils.getPortraitTouch(pointerId, motionEvent, overlayParams);
                        float f = portraitTouch.x;
                        float f2 = overlayParams.scaleFactor;
                        portraitTouch.x = (int) (f / f2);
                        portraitTouch.y = (int) (portraitTouch.y / f2);
                        UdfpsUtils udfpsUtils2 = udfpsEnrollEnrollingView2.mUdfpsUtils;
                        int pointerId2 = motionEvent.getPointerId(0);
                        UdfpsOverlayParams overlayParams2 = udfpsEnrollEnrollingView2.mUdfpsEnrollView.getOverlayParams();
                        udfpsUtils2.getClass();
                        Point portraitTouch2 = UdfpsUtils.getPortraitTouch(pointerId2, motionEvent, overlayParams2);
                        if (!overlayParams2.sensorBounds.contains(portraitTouch2.x, portraitTouch2.y)) {
                            UdfpsUtils udfpsUtils3 = udfpsEnrollEnrollingView2.mUdfpsUtils;
                            boolean isTouchExplorationEnabled = udfpsEnrollEnrollingView2.mAccessibilityManager.isTouchExplorationEnabled();
                            Context context = udfpsEnrollEnrollingView2.mContext;
                            int i2 = portraitTouch.x;
                            int i3 = portraitTouch.y;
                            UdfpsOverlayParams overlayParams3 = udfpsEnrollEnrollingView2.mUdfpsEnrollView.getOverlayParams();
                            udfpsUtils3.getClass();
                            String onTouchOutsideOfSensorArea = UdfpsUtils.onTouchOutsideOfSensorArea(isTouchExplorationEnabled, context, i2, i3, overlayParams3);
                            if (onTouchOutsideOfSensorArea != null) {
                                view.announceForAccessibility(onTouchOutsideOfSensorArea);
                            }
                        }
                        return false;
                    }
                });
            }
            setContentView(udfpsEnrollEnrollingView);
            setDescriptionText(R.string.security_settings_udfps_enroll_start_message);
        } else if (this.mCanAssumeSfps) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            FingerprintFeatureProviderImpl fingerprintFeatureProvider = featureFactoryImpl.getFingerprintFeatureProvider();
            if (fingerprintFeatureProvider.mSfpsEnrollmentFeatureImpl == null) {
                SfpsEnrollmentFeatureImpl sfpsEnrollmentFeatureImpl = new SfpsEnrollmentFeatureImpl();
                sfpsEnrollmentFeatureImpl.mFingerprintManager = null;
                fingerprintFeatureProvider.mSfpsEnrollmentFeatureImpl = sfpsEnrollmentFeatureImpl;
            }
            this.mSfpsEnrollmentFeature = fingerprintFeatureProvider.mSfpsEnrollmentFeatureImpl;
            setContentView(R.layout.sfps_enroll_enrolling);
            this.mHelpAnimation = this.mSfpsEnrollmentFeature.getHelpAnimator((RelativeLayout) findViewById(R.id.progress_lottie));
        } else {
            setContentView(R.layout.fingerprint_enroll_enrolling);
            setDescriptionText(R.string.security_settings_fingerprint_enroll_start_message);
        }
        this.mIsSetupWizard = WizardManagerHelper.isAnySetupWizard(getIntent());
        if (this.mCanAssumeUdfps || this.mCanAssumeSfps) {
            updateTitleAndDescription(true);
        } else {
            setHeaderText(R.string.security_settings_fingerprint_enroll_repeat_title);
        }
        this.mShouldShowLottie = shouldShowLottie();
        updateOrientation((getApplicationContext().getDisplay().getRotation() == 3 || getApplicationContext().getDisplay().getRotation() == 1) ? 2 : 1);
        this.mErrorText = (TextView) findViewById(R.id.error_text);
        this.mProgressBar = (ProgressBar) findViewById(R.id.fingerprint_progress_bar);
        this.mVibrator = (Vibrator) getSystemService(Vibrator.class);
        FooterBarMixin footerBarMixin = (FooterBarMixin) getLayout().getMixin(FooterBarMixin.class);
        this.mFooterBarMixin = footerBarMixin;
        footerBarMixin.setSecondaryButton(new FooterButton(getString(R.string.security_settings_fingerprint_enroll_enrolling_skip), new View.OnClickListener() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
                VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                fingerprintEnrollEnrolling.cancelEnrollment();
                fingerprintEnrollEnrolling.setResult(2);
                fingerprintEnrollEnrolling.finish();
            }
        }, 7, 2132083806), false);
        if (this.mCanAssumeUdfps) {
            this.mShouldSetFooterBarBackground = false;
            UdfpsEnrollEnrollingView udfpsEnrollEnrollingView2 = (UdfpsEnrollEnrollingView) getLayout();
            ColorStateList colorAttr = Utils.getColorAttr(this, android.R.attr.windowBackground);
            udfpsEnrollEnrollingView2.setSecondaryButtonBackground(colorAttr != null ? colorAttr.getDefaultColor() : 0);
        }
        ProgressBar progressBar = this.mProgressBar;
        LayerDrawable layerDrawable = progressBar != null ? (LayerDrawable) progressBar.getBackground() : null;
        if (layerDrawable != null) {
            this.mIconAnimationDrawable = (AnimatedVectorDrawable) layerDrawable.findDrawableByLayerId(R.id.fingerprint_animation);
            this.mIconBackgroundBlinksDrawable = (AnimatedVectorDrawable) layerDrawable.findDrawableByLayerId(R.id.fingerprint_background);
            this.mIconAnimationDrawable.registerAnimationCallback(this.mIconAnimationCallback);
        }
        this.mFastOutSlowInInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
        this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_linear_in);
        ProgressBar progressBar2 = this.mProgressBar;
        if (progressBar2 != null) {
            progressBar2.setProgressBackgroundTintMode(PorterDuff.Mode.SRC);
            this.mProgressBar.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda3
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    FingerprintEnrollEnrolling fingerprintEnrollEnrolling = FingerprintEnrollEnrolling.this;
                    VibrationEffect vibrationEffect = FingerprintEnrollEnrolling.VIBRATE_EFFECT_ERROR;
                    fingerprintEnrollEnrolling.getClass();
                    int actionMasked = motionEvent.getActionMasked();
                    FingerprintEnrollEnrolling.AnonymousClass3 anonymousClass3 = fingerprintEnrollEnrolling.mShowDialogRunnable;
                    if (actionMasked == 0) {
                        int i2 = fingerprintEnrollEnrolling.mIconTouchCount + 1;
                        fingerprintEnrollEnrolling.mIconTouchCount = i2;
                        if (i2 == 3) {
                            fingerprintEnrollEnrolling.mIconTouchCount = 0;
                            new FingerprintEnrollEnrolling.IconTouchDialog().show(fingerprintEnrollEnrolling.getSupportFragmentManager(), "fps_icon_touch_dialog");
                        } else {
                            fingerprintEnrollEnrolling.mProgressBar.postDelayed(anonymousClass3, 500L);
                        }
                    } else if (motionEvent.getActionMasked() == 3 || motionEvent.getActionMasked() == 1) {
                        fingerprintEnrollEnrolling.mProgressBar.removeCallbacks(anonymousClass3);
                    }
                    return true;
                }
            });
        }
        maybeHideSfpsText(getApplicationContext().getResources().getConfiguration());
        if (this.mIsSetupWizard) {
            return;
        }
        FeatureFactoryImpl featureFactoryImpl2 = FeatureFactoryImpl._factory;
        if (featureFactoryImpl2 == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        FingerprintFeatureProviderImpl fingerprintFeatureProvider2 = featureFactoryImpl2.getFingerprintFeatureProvider();
        getApplicationContext();
        getIntent();
        fingerprintFeatureProvider2.getClass();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        AnonymousClass1 anonymousClass1 = this.mOrientationEventListener;
        if (anonymousClass1 != null) {
            anonymousClass1.disable();
        }
        this.mOrientationEventListener = null;
        super.onDestroy();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentError(int i, CharSequence charSequence) {
        onCancelEnrollment(i);
        dismissTouchDialogIfSfps();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentHelp(int i, CharSequence charSequence) {
        UdfpsEnrollHelper.Listener listener;
        if (this.mCanAssumeSfps) {
            this.mSfpsEnrollmentFeature.getClass();
        }
        if (i == 3 && this.mCanAssumeUdfps) {
            charSequence = getResources().getString(R.string.fingerprint_acquired_imager_dirty_udfps);
        }
        if (!TextUtils.isEmpty(charSequence)) {
            if (!this.mCanAssumeUdfps && !this.mCanAssumeSfps) {
                this.mErrorText.removeCallbacks(this.mTouchAgainRunnable);
            }
            showError$1(charSequence);
            UdfpsEnrollHelper udfpsEnrollHelper = this.mUdfpsEnrollHelper;
            if (udfpsEnrollHelper != null && (listener = udfpsEnrollHelper.mListener) != null) {
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) listener;
                udfpsEnrollView.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda1(udfpsEnrollView, udfpsEnrollHelper.mRemainingSteps, udfpsEnrollHelper.mTotalSteps, 1));
            }
        }
        dismissTouchDialogIfSfps();
        if (this.mCanAssumeSfps) {
            this.mSfpsEnrollmentFeature.getClass();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onEnrollmentProgressChange(int i, int i2) {
        updateProgress(true);
        int i3 = (int) (((i - i2) / i) * 100.0f);
        if (this.mCanAssumeSfps) {
            this.mSfpsEnrollmentFeature.getClass();
            if (this.mIsAccessibilityEnabled) {
                announceEnrollmentProgress(getString(R.string.security_settings_sfps_enroll_progress_a11y_message, new Object[]{Integer.valueOf(i3)}));
            }
        }
        updateTitleAndDescription(false);
        AnimatedVectorDrawable animatedVectorDrawable = this.mIconBackgroundBlinksDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }
        if (this.mCanAssumeUdfps) {
            if (this.mIsAccessibilityEnabled) {
                announceEnrollmentProgress(getString(R.string.security_settings_udfps_enroll_progress_a11y_message, new Object[]{Integer.valueOf(i3)}));
            }
        } else if (!this.mCanAssumeSfps) {
            TextView textView = this.mErrorText;
            AnonymousClass3 anonymousClass3 = this.mTouchAgainRunnable;
            textView.removeCallbacks(anonymousClass3);
            this.mErrorText.postDelayed(anonymousClass3, 2500L);
        }
        dismissTouchDialogIfSfps();
    }

    @Override // android.app.Activity
    public final void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        if (this.mCanAssumeUdfps && (!isInMultiWindowMode() || ActivityEmbeddingController.getInstance(this).isActivityEmbedded(this))) {
            startEnrollmentInternal();
        }
        this.mAnimationCancelled = false;
        AnimatedVectorDrawable animatedVectorDrawable = this.mIconAnimationDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.start();
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("is_canceled", this.mIsCanceled);
        bundle.putInt("previous_rotation", this.mPreviousRotation);
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        AnimatedVectorDrawable animatedVectorDrawable;
        super.onStart();
        updateProgress(false);
        updateTitleAndDescription(true);
        if (!this.mRestoring || (animatedVectorDrawable = this.mIconAnimationDrawable) == null) {
            return;
        }
        animatedVectorDrawable.start();
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling, com.android.settings.biometrics.BiometricEnrollBase, com.android.settingslib.core.lifecycle.ObservableActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        if (!isChangingConfigurations()) {
            if (!WizardManagerHelper.isAnySetupWizard(getIntent()) && !BiometricUtils.isAnyMultiBiometricFlow(this) && !this.mFromSettingsSummary) {
                setResult(3);
            }
            finish();
        }
        this.mAnimationCancelled = true;
        AnimatedVectorDrawable animatedVectorDrawable = this.mIconAnimationDrawable;
        if (animatedVectorDrawable != null) {
            animatedVectorDrawable.stop();
        }
        super.onStop();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onUdfpsOverlayShown() {
        if (this.mCanAssumeUdfps) {
            findViewById(R.id.udfps_animation_view).setVisibility(0);
        }
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onUdfpsPointerDown() {
        UdfpsEnrollHelper.Listener listener;
        UdfpsEnrollHelper udfpsEnrollHelper = this.mUdfpsEnrollHelper;
        if (udfpsEnrollHelper == null || (listener = udfpsEnrollHelper.mListener) == null) {
            return;
        }
        UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) listener;
        if (udfpsEnrollView.mOverlayParams.sensorType == 3) {
            UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView.mFingerprintDrawable;
            if (!udfpsEnrollDrawable.mSkipDraw) {
                udfpsEnrollDrawable.mSkipDraw = true;
                udfpsEnrollDrawable.invalidateSelf();
            }
        }
        udfpsEnrollView.mFingerprintDrawable.invalidateSelf();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollSidecar.Listener
    public final void onUdfpsPointerUp() {
        UdfpsEnrollHelper.Listener listener;
        UdfpsEnrollHelper udfpsEnrollHelper = this.mUdfpsEnrollHelper;
        if (udfpsEnrollHelper == null || (listener = udfpsEnrollHelper.mListener) == null) {
            return;
        }
        UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) listener;
        UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView.mFingerprintDrawable;
        if (udfpsEnrollDrawable.mSkipDraw) {
            udfpsEnrollDrawable.mSkipDraw = false;
            udfpsEnrollDrawable.invalidateSelf();
        }
        udfpsEnrollView.mFingerprintDrawable.invalidateSelf();
    }

    @Override // com.android.settings.biometrics.BiometricEnrollBase
    public final boolean shouldFinishWhenBackgrounded() {
        return false;
    }

    @VisibleForTesting
    public boolean shouldShowLottie() {
        DisplayDensityUtils displayDensityUtils = new DisplayDensityUtils(getApplicationContext());
        return getResources().getConfiguration().fontScale <= 1.0f && displayDensityUtils.mDefaultDensityForDefaultDisplay == displayDensityUtils.mDefaultDisplayDensityValues[displayDensityUtils.mCurrentIndex];
    }

    @Override // com.android.settings.biometrics.BiometricsEnrollEnrolling
    public final boolean shouldStartAutomatically() {
        if (this.mCanAssumeUdfps) {
            return this.mRestoring && !this.mIsCanceled;
        }
        return true;
    }

    public final void showError$1(CharSequence charSequence) {
        if (this.mCanAssumeSfps) {
            if (getLayout() != null) {
                getLayout().setHeaderText(charSequence);
                getLayout().getHeaderTextView().setContentDescription(charSequence);
            }
            if (!this.mHelpAnimation.isRunning()) {
                this.mHelpAnimation.start();
            }
            applySfpsErrorDynamicColors(getApplicationContext(), true);
        } else if (this.mCanAssumeUdfps) {
            if (getLayout() != null) {
                getLayout().setHeaderText(charSequence);
                getLayout().getHeaderTextView().setContentDescription(charSequence);
            }
            setDescriptionText(ApnSettings.MVNO_NONE);
        } else {
            this.mErrorText.setText(charSequence);
            if (this.mErrorText.getVisibility() == 4) {
                this.mErrorText.setVisibility(0);
                this.mErrorText.setTranslationY(getResources().getDimensionPixelSize(R.dimen.fingerprint_error_text_appear_distance));
                this.mErrorText.setAlpha(0.0f);
                this.mErrorText.animate().alpha(1.0f).translationY(0.0f).setDuration(200L).setInterpolator(this.mLinearOutSlowInInterpolator).start();
            } else {
                this.mErrorText.animate().cancel();
                this.mErrorText.setAlpha(1.0f);
                this.mErrorText.setTranslationY(0.0f);
            }
        }
        if (isResumed() && this.mIsAccessibilityEnabled && !this.mCanAssumeUdfps) {
            this.mVibrator.vibrate(Process.myUid(), getApplicationContext().getOpPackageName(), VIBRATE_EFFECT_ERROR, getClass().getSimpleName().concat("::showError"), FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
        }
    }

    public final void updateOrientation(int i) {
        if (this.mCanAssumeSfps) {
            this.mIllustrationLottie = (LottieAnimationView) findViewById(R.id.illustration_lottie);
            return;
        }
        if (i == 1) {
            if (this.mShouldShowLottie) {
                this.mIllustrationLottie = (LottieAnimationView) findViewById(R.id.illustration_lottie);
            }
        } else if (i != 2) {
            Log.e("FingerprintEnrollEnrolling", "Error unhandled configuration change");
        } else {
            this.mIllustrationLottie = null;
        }
    }

    public final void updateProgress(boolean z) {
        int max;
        int i;
        BiometricEnrollSidecar biometricEnrollSidecar = this.mSidecar;
        if (biometricEnrollSidecar == null || !biometricEnrollSidecar.mEnrolling) {
            Log.d("FingerprintEnrollEnrolling", "Enrollment not started yet");
            return;
        }
        int i2 = biometricEnrollSidecar.mEnrollmentSteps;
        int i3 = biometricEnrollSidecar.mEnrollmentRemaining;
        if (i2 == -1) {
            max = 0;
        } else {
            int i4 = i2 + 1;
            max = (Math.max(0, i4 - i3) * EnterpriseContainerConstants.SYSTEM_SIGNED_APP) / i4;
        }
        ProgressBar progressBar = this.mProgressBar;
        if (progressBar != null && progressBar.getProgress() < max) {
            if (this.mCanAssumeSfps) {
                applySfpsErrorDynamicColors(getApplicationContext(), false);
            }
            if (!this.mCanAssumeUdfps && !this.mCanAssumeSfps && this.mErrorText.getVisibility() == 0) {
                this.mErrorText.animate().alpha(0.0f).translationY(getResources().getDimensionPixelSize(R.dimen.fingerprint_error_text_disappear_distance)).setDuration(100L).setInterpolator(this.mFastOutLinearInInterpolator).withEndAction(new Runnable() { // from class: com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FingerprintEnrollEnrolling.this.mErrorText.setVisibility(4);
                    }
                }).start();
            }
        }
        UdfpsEnrollHelper udfpsEnrollHelper = this.mUdfpsEnrollHelper;
        if (udfpsEnrollHelper != null) {
            BiometricEnrollSidecar biometricEnrollSidecar2 = this.mSidecar;
            int i5 = biometricEnrollSidecar2.mEnrollmentSteps;
            int i6 = biometricEnrollSidecar2.mEnrollmentRemaining;
            if (udfpsEnrollHelper.mTotalSteps == -1) {
                udfpsEnrollHelper.mTotalSteps = i5;
            }
            if (i6 != udfpsEnrollHelper.mRemainingSteps) {
                udfpsEnrollHelper.mLocationsEnrolled++;
                if (udfpsEnrollHelper.isCenterEnrollmentStage()) {
                    udfpsEnrollHelper.mCenterTouchCount++;
                }
            }
            int i7 = udfpsEnrollHelper.mRemainingSteps;
            if (i7 > i6) {
                udfpsEnrollHelper.mPace = i7 - i6;
            }
            udfpsEnrollHelper.mRemainingSteps = i6;
            UdfpsEnrollHelper.Listener listener = udfpsEnrollHelper.mListener;
            if (listener != null && (i = udfpsEnrollHelper.mTotalSteps) != -1) {
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) listener;
                udfpsEnrollView.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda1(udfpsEnrollView, i6, i, 0));
            }
        }
        AnonymousClass3 anonymousClass3 = this.mDelayedFinishRunnable;
        if (!z) {
            ProgressBar progressBar2 = this.mProgressBar;
            if (progressBar2 != null) {
                progressBar2.setProgress(max);
            }
            if (max >= 10000) {
                anonymousClass3.run();
                return;
            }
            return;
        }
        if (this.mCanAssumeUdfps) {
            if (max >= 10000) {
                getMainThreadHandler().postDelayed(anonymousClass3, this.mCanAssumeUdfps ? 400L : 250L);
                return;
            }
            return;
        }
        ObjectAnimator objectAnimator = this.mProgressAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ProgressBar progressBar3 = this.mProgressBar;
        ObjectAnimator ofInt = ObjectAnimator.ofInt(progressBar3, "progress", progressBar3.getProgress(), max);
        ofInt.addListener(this.mProgressAnimationListener);
        ofInt.setInterpolator(this.mFastOutSlowInInterpolator);
        ofInt.setDuration(250L);
        ofInt.start();
        this.mProgressAnim = ofInt;
    }

    public final void updateTitleAndDescription(boolean z) {
        LottieAnimationView lottieAnimationView;
        LottieAnimationView lottieAnimationView2;
        LottieAnimationView lottieAnimationView3;
        LottieAnimationView lottieAnimationView4;
        LottieAnimationView lottieAnimationView5;
        LottieAnimationView lottieAnimationView6;
        int i;
        char c = 0;
        if (!this.mCanAssumeUdfps) {
            if (!this.mCanAssumeSfps) {
                BiometricEnrollSidecar biometricEnrollSidecar = this.mSidecar;
                if (biometricEnrollSidecar == null || biometricEnrollSidecar.mEnrollmentSteps == -1) {
                    setDescriptionText(R.string.security_settings_fingerprint_enroll_start_message);
                    return;
                } else {
                    setDescriptionText(R.string.security_settings_fingerprint_enroll_repeat_message);
                    return;
                }
            }
            if (!z) {
                this.mSfpsEnrollmentFeature.getClass();
            }
            if (this.mIsAccessibilityEnabled) {
                AccessibilityManager.getInstance(getApplicationContext()).interrupt();
                getLayout().getDescriptionTextView().setAccessibilityLiveRegion(1);
            }
            BiometricEnrollSidecar biometricEnrollSidecar2 = this.mSidecar;
            int currentSfpsEnrollStage = biometricEnrollSidecar2 != null ? this.mSfpsEnrollmentFeature.getCurrentSfpsEnrollStage(biometricEnrollSidecar2.mEnrollmentSteps - biometricEnrollSidecar2.mEnrollmentRemaining, new FingerprintEnrollEnrolling$$ExternalSyntheticLambda0(this)) : -1;
            if (currentSfpsEnrollStage == 0) {
                setHeaderText(this.mSfpsEnrollmentFeature.getFeaturedStageHeaderResource(0));
                if (this.mHaveShownSfpsNoAnimationLottie || (lottieAnimationView = this.mIllustrationLottie) == null) {
                    return;
                }
                this.mHaveShownSfpsNoAnimationLottie = true;
                lottieAnimationView.setContentDescription(getString(R.string.security_settings_sfps_animation_a11y_label, new Object[]{0}));
                configureEnrollmentStage(this.mSfpsEnrollmentFeature.getSfpsEnrollLottiePerStage(0));
                return;
            }
            if (currentSfpsEnrollStage == 1) {
                setHeaderText(this.mSfpsEnrollmentFeature.getFeaturedStageHeaderResource(1));
                if (this.mHaveShownSfpsCenterLottie || this.mIllustrationLottie == null) {
                    return;
                }
                this.mHaveShownSfpsCenterLottie = true;
                configureEnrollmentStage(this.mSfpsEnrollmentFeature.getSfpsEnrollLottiePerStage(1));
                return;
            }
            if (currentSfpsEnrollStage == 2) {
                setHeaderText(this.mSfpsEnrollmentFeature.getFeaturedStageHeaderResource(2));
                if (this.mHaveShownSfpsTipLottie || this.mIllustrationLottie == null) {
                    return;
                }
                this.mHaveShownSfpsTipLottie = true;
                configureEnrollmentStage(this.mSfpsEnrollmentFeature.getSfpsEnrollLottiePerStage(2));
                return;
            }
            if (currentSfpsEnrollStage == 3) {
                setHeaderText(this.mSfpsEnrollmentFeature.getFeaturedStageHeaderResource(3));
                if (this.mHaveShownSfpsLeftEdgeLottie || this.mIllustrationLottie == null) {
                    return;
                }
                this.mHaveShownSfpsLeftEdgeLottie = true;
                configureEnrollmentStage(this.mSfpsEnrollmentFeature.getSfpsEnrollLottiePerStage(3));
                return;
            }
            if (currentSfpsEnrollStage != 4) {
                getLayout().setHeaderText(R.string.security_settings_sfps_enroll_find_sensor_title);
                String string = getString(R.string.security_settings_sfps_enroll_find_sensor_message);
                getLayout().getHeaderTextView().setContentDescription(string);
                setTitle(string);
                return;
            }
            setHeaderText(this.mSfpsEnrollmentFeature.getFeaturedStageHeaderResource(4));
            if (this.mHaveShownSfpsRightEdgeLottie || this.mIllustrationLottie == null) {
                return;
            }
            this.mHaveShownSfpsRightEdgeLottie = true;
            configureEnrollmentStage(this.mSfpsEnrollmentFeature.getSfpsEnrollLottiePerStage(4));
            return;
        }
        BiometricEnrollSidecar biometricEnrollSidecar3 = this.mSidecar;
        if (biometricEnrollSidecar3 == null || (i = biometricEnrollSidecar3.mEnrollmentSteps) == -1) {
            c = 65535;
        } else {
            int i2 = i - biometricEnrollSidecar3.mEnrollmentRemaining;
            if (i2 >= getStageThresholdSteps(0)) {
                c = i2 < getStageThresholdSteps(1) ? (char) 1 : i2 < getStageThresholdSteps(2) ? (char) 2 : i2 < getStageThresholdSteps(3) ? (char) 3 : (char) 4;
            }
        }
        if (c == 0) {
            setHeaderText(R.string.security_settings_fingerprint_enroll_repeat_title);
            if (this.mIsAccessibilityEnabled || (lottieAnimationView2 = this.mIllustrationLottie) == null) {
                setDescriptionText(R.string.security_settings_udfps_enroll_start_message);
                return;
            } else {
                if (this.mHaveShownUdfpsCenterLottie) {
                    return;
                }
                this.mHaveShownUdfpsCenterLottie = true;
                lottieAnimationView2.setContentDescription(getString(R.string.security_settings_sfps_enroll_finger_center_title));
                configureEnrollmentStage(R.raw.udfps_center_hint_lottie);
                return;
            }
        }
        if (c == 1) {
            setHeaderText(R.string.security_settings_fingerprint_enroll_repeat_title);
            if (this.mIsAccessibilityEnabled || (lottieAnimationView3 = this.mIllustrationLottie) == null) {
                setDescriptionText(R.string.security_settings_udfps_enroll_repeat_a11y_message);
                return;
            } else {
                if (this.mHaveShownUdfpsGuideLottie) {
                    return;
                }
                this.mHaveShownUdfpsGuideLottie = true;
                lottieAnimationView3.setContentDescription(getString(R.string.security_settings_fingerprint_enroll_repeat_message));
                configureEnrollmentStage(R.raw.udfps_center_hint_lottie);
                return;
            }
        }
        if (c == 2) {
            setHeaderText(R.string.security_settings_udfps_enroll_fingertip_title);
            if (this.mHaveShownUdfpsTipLottie || (lottieAnimationView4 = this.mIllustrationLottie) == null) {
                return;
            }
            this.mHaveShownUdfpsTipLottie = true;
            lottieAnimationView4.setContentDescription(getString(R.string.security_settings_udfps_tip_fingerprint_help));
            configureEnrollmentStage(R.raw.udfps_tip_hint_lottie);
            return;
        }
        if (c == 3) {
            setHeaderText(R.string.security_settings_udfps_enroll_left_edge_title);
            if (!this.mHaveShownUdfpsLeftEdgeLottie && (lottieAnimationView5 = this.mIllustrationLottie) != null) {
                this.mHaveShownUdfpsLeftEdgeLottie = true;
                lottieAnimationView5.setContentDescription(getString(R.string.security_settings_udfps_side_fingerprint_help));
                configureEnrollmentStage(R.raw.udfps_left_edge_hint_lottie);
                return;
            } else {
                if (this.mIllustrationLottie == null) {
                    if (isStageHalfCompleted()) {
                        setDescriptionText(R.string.security_settings_fingerprint_enroll_repeat_message);
                        return;
                    } else {
                        setDescriptionText(R.string.security_settings_udfps_enroll_edge_message);
                        return;
                    }
                }
                return;
            }
        }
        if (c != 4) {
            getLayout().setHeaderText(R.string.security_settings_fingerprint_enroll_udfps_title);
            setDescriptionText(R.string.security_settings_udfps_enroll_start_message);
            String string2 = getString(R.string.security_settings_udfps_enroll_a11y);
            getLayout().getHeaderTextView().setContentDescription(string2);
            setTitle(string2);
            return;
        }
        setHeaderText(R.string.security_settings_udfps_enroll_right_edge_title);
        if (!this.mHaveShownUdfpsRightEdgeLottie && (lottieAnimationView6 = this.mIllustrationLottie) != null) {
            this.mHaveShownUdfpsRightEdgeLottie = true;
            lottieAnimationView6.setContentDescription(getString(R.string.security_settings_udfps_side_fingerprint_help));
            configureEnrollmentStage(R.raw.udfps_right_edge_hint_lottie);
        } else if (this.mIllustrationLottie == null) {
            if (isStageHalfCompleted()) {
                setDescriptionText(R.string.security_settings_fingerprint_enroll_repeat_message);
            } else {
                setDescriptionText(R.string.security_settings_udfps_enroll_edge_message);
            }
        }
    }
}
