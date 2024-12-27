package com.android.settings.biometrics.fingerprint.feature;

import android.animation.Animator;
import android.content.Context;
import android.view.View;

import com.android.settings.biometrics.fingerprint.FingerprintEnrollEnrolling$$ExternalSyntheticLambda0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public interface SfpsEnrollmentFeature {
    int getCurrentSfpsEnrollStage(
            int i,
            FingerprintEnrollEnrolling$$ExternalSyntheticLambda0
                    fingerprintEnrollEnrolling$$ExternalSyntheticLambda0);

    float getEnrollStageThreshold(Context context, int i);

    int getFeaturedStageHeaderResource(int i);

    Animator getHelpAnimator(View view);

    int getSfpsEnrollLottiePerStage(int i);
}
