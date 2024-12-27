package com.android.settings.biometrics;

import android.content.Intent;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BiometricEnrollIntroduction$$ExternalSyntheticLambda0 {
    public final /* synthetic */ BiometricEnrollIntroduction f$0;
    public final /* synthetic */ Intent f$1;

    public /* synthetic */ BiometricEnrollIntroduction$$ExternalSyntheticLambda0(
            BiometricEnrollIntroduction biometricEnrollIntroduction, Intent intent) {
        this.f$0 = biometricEnrollIntroduction;
        this.f$1 = intent;
    }

    public final void onChallengeGenerated(int i, long j) {
        Intent intent = this.f$1;
        BiometricEnrollIntroduction biometricEnrollIntroduction = this.f$0;
        biometricEnrollIntroduction.mSensorId = i;
        biometricEnrollIntroduction.mChallenge = j;
        biometricEnrollIntroduction.mToken =
                BiometricUtils.requestGatekeeperHat(
                        biometricEnrollIntroduction,
                        intent,
                        biometricEnrollIntroduction.mUserId,
                        j);
        BiometricUtils.removeGatekeeperPasswordHandle(biometricEnrollIntroduction, intent);
        biometricEnrollIntroduction.getNextButton().setEnabled(true);
    }
}
