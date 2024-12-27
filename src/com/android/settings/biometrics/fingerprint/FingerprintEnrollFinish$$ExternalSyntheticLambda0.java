package com.android.settings.biometrics.fingerprint;

import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class FingerprintEnrollFinish$$ExternalSyntheticLambda0
        implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintEnrollFinish f$0;

    public /* synthetic */ FingerprintEnrollFinish$$ExternalSyntheticLambda0(
            FingerprintEnrollFinish fingerprintEnrollFinish, int i) {
        this.$r8$classId = i;
        this.f$0 = fingerprintEnrollFinish;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        FingerprintEnrollFinish fingerprintEnrollFinish = this.f$0;
        switch (i) {
            case 0:
                fingerprintEnrollFinish.mIsAddAnotherOrFinish = true;
                fingerprintEnrollFinish.startActivityForResult(
                        fingerprintEnrollFinish.getFingerprintEnrollingIntent(), 7);
                break;
            default:
                fingerprintEnrollFinish.updateFingerprintSuggestionEnableState();
                fingerprintEnrollFinish.finishAndToNext(1);
                break;
        }
    }
}
