package com.android.settings.biometrics.fingerprint.feature;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SfpsEnrollmentFeatureImpl implements SfpsEnrollmentFeature {

    @VisibleForTesting public static final int HELP_ANIMATOR_DURATION = 550;
    public FingerprintManager mFingerprintManager;

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public final int getCurrentSfpsEnrollStage(
            int i,
            FingerprintEnrollEnrolling$$ExternalSyntheticLambda0
                    fingerprintEnrollEnrolling$$ExternalSyntheticLambda0) {
        if (i
                < ((Integer) fingerprintEnrollEnrolling$$ExternalSyntheticLambda0.apply(0))
                        .intValue()) {
            return 0;
        }
        if (i
                < ((Integer) fingerprintEnrollEnrolling$$ExternalSyntheticLambda0.apply(1))
                        .intValue()) {
            return 1;
        }
        if (i
                < ((Integer) fingerprintEnrollEnrolling$$ExternalSyntheticLambda0.apply(2))
                        .intValue()) {
            return 2;
        }
        return i
                        < ((Integer) fingerprintEnrollEnrolling$$ExternalSyntheticLambda0.apply(3))
                                .intValue()
                ? 3
                : 4;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public final float getEnrollStageThreshold(Context context, int i) {
        if (this.mFingerprintManager == null) {
            this.mFingerprintManager =
                    (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        return this.mFingerprintManager.getEnrollStageThreshold(i);
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public final int getFeaturedStageHeaderResource(int i) {
        if (i == 0) {
            return R.string.security_settings_fingerprint_enroll_repeat_title;
        }
        if (i == 1) {
            return R.string.security_settings_sfps_enroll_finger_center_title;
        }
        if (i == 2) {
            return R.string.security_settings_sfps_enroll_fingertip_title;
        }
        if (i == 3) {
            return R.string.security_settings_sfps_enroll_left_edge_title;
        }
        if (i == 4) {
            return R.string.security_settings_sfps_enroll_right_edge_title;
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Invalid stage: "));
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public final Animator getHelpAnimator(View view) {
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(view, "translationX", 0.0f, 40.0f, -40.0f, 40.0f, 0.0f);
        ofFloat.setInterpolator(new AccelerateInterpolator());
        ofFloat.setDuration(550L);
        ofFloat.setAutoCancel(false);
        return ofFloat;
    }

    @Override // com.android.settings.biometrics.fingerprint.feature.SfpsEnrollmentFeature
    public final int getSfpsEnrollLottiePerStage(int i) {
        if (i == 0) {
            return R.raw.sfps_lottie_no_animation;
        }
        if (i == 1) {
            return R.raw.sfps_lottie_pad_center;
        }
        if (i == 2) {
            return R.raw.sfps_lottie_tip;
        }
        if (i == 3) {
            return R.raw.sfps_lottie_left_edge;
        }
        if (i == 4) {
            return R.raw.sfps_lottie_right_edge;
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Invalid stage: "));
    }
}
