package com.android.settings.biometrics2.ui.view;

import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FingerprintEnrollEnrollingSfpsFragment this$0;

    public /* synthetic */ FingerprintEnrollEnrollingSfpsFragment$delayedFinishRunnable$1(
            FingerprintEnrollEnrollingSfpsFragment fingerprintEnrollEnrollingSfpsFragment, int i) {
        this.$r8$classId = i;
        this.this$0 = fingerprintEnrollEnrollingSfpsFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                        this.this$0._enrollingViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
                fingerprintEnrollEnrollingViewModel.mActionLiveData.postValue(0);
                break;
            default:
                FingerprintEnrollEnrollingSfpsFragment fingerprintEnrollEnrollingSfpsFragment =
                        this.this$0;
                fingerprintEnrollEnrollingSfpsFragment.iconTouchCount = 0;
                FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel2 =
                        fingerprintEnrollEnrollingSfpsFragment._enrollingViewModel;
                Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel2);
                fingerprintEnrollEnrollingViewModel2.mActionLiveData.postValue(1);
                break;
        }
    }
}
