package com.android.settings.biometrics2.ui.widget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class UdfpsEnrollView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UdfpsEnrollView f$0;

    public /* synthetic */ UdfpsEnrollView$$ExternalSyntheticLambda2(
            UdfpsEnrollView udfpsEnrollView, int i) {
        this.$r8$classId = i;
        this.f$0 = udfpsEnrollView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        UdfpsEnrollView udfpsEnrollView = this.f$0;
        switch (i) {
            case 0:
                UdfpsEnrollView.$r8$lambda$PiM0aR_wvNKlynWGHro_9xCsfqk(udfpsEnrollView);
                break;
            default:
                udfpsEnrollView.mFingerprintProgressDrawable.updateState(
                        udfpsEnrollView.mRemainingSteps, udfpsEnrollView.mTotalSteps, true);
                break;
        }
    }
}
