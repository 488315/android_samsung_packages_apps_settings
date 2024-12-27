package com.samsung.android.settings.knox;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class TwoStepAuthenticationCallback$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TwoStepAuthenticationCallback f$0;

    public /* synthetic */ TwoStepAuthenticationCallback$$ExternalSyntheticLambda0(
            TwoStepAuthenticationCallback twoStepAuthenticationCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = twoStepAuthenticationCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        TwoStepAuthenticationCallback twoStepAuthenticationCallback = this.f$0;
        switch (i) {
            case 0:
                twoStepAuthenticationCallback.mDevicePolicyManager.reportFailedBiometricAttempt(
                        twoStepAuthenticationCallback.mUserId);
                break;
            case 1:
                twoStepAuthenticationCallback.callback.enableConfirmCredentialCallback();
                break;
            default:
                twoStepAuthenticationCallback.activity.setResult(0);
                twoStepAuthenticationCallback.activity.finish();
                break;
        }
    }
}
