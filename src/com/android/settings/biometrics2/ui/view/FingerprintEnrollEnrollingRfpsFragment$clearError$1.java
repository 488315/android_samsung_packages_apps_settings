package com.android.settings.biometrics2.ui.view;

import android.graphics.drawable.AnimatedVectorDrawable;

import com.android.settings.R;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;

import com.google.android.setupdesign.GlifLayout;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingRfpsFragment$clearError$1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintEnrollEnrollingRfpsFragment this$0;

    public /* synthetic */ FingerprintEnrollEnrollingRfpsFragment$clearError$1(
            FingerprintEnrollEnrollingRfpsFragment fingerprintEnrollEnrollingRfpsFragment, int i) {
        this.$r8$classId = i;
        this.this$0 = fingerprintEnrollEnrollingRfpsFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.this$0.getErrorText().setVisibility(4);
                break;
            case 1:
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                        this.this$0._enrollingViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                fingerprintEnrollEnrollingViewModel.mActionLiveData.postValue(0);
                break;
            case 2:
                AnimatedVectorDrawable iconAnimationDrawable =
                        this.this$0.getIconAnimationDrawable();
                if (iconAnimationDrawable != null) {
                    iconAnimationDrawable.start();
                    break;
                }
                break;
            case 3:
                FingerprintEnrollEnrollingRfpsFragment fingerprintEnrollEnrollingRfpsFragment =
                        this.this$0;
                fingerprintEnrollEnrollingRfpsFragment.iconTouchCount = 0;
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel2 =
                        fingerprintEnrollEnrollingRfpsFragment._enrollingViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel2);
                fingerprintEnrollEnrollingViewModel2.mActionLiveData.postValue(1);
                break;
            default:
                FingerprintEnrollEnrollingRfpsFragment fingerprintEnrollEnrollingRfpsFragment2 =
                        this.this$0;
                GlifLayout glifLayout = fingerprintEnrollEnrollingRfpsFragment2.enrollingView;
                Intrinsics.checkNotNull(glifLayout);
                String string =
                        glifLayout
                                .getContext()
                                .getString(
                                        R.string
                                                .security_settings_fingerprint_enroll_lift_touch_again);
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                fingerprintEnrollEnrollingRfpsFragment2.showError$2(string);
                break;
        }
    }
}
