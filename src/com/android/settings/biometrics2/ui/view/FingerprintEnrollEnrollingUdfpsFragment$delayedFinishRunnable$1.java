package com.android.settings.biometrics2.ui.view;

import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollEnrollingUdfpsFragment$delayedFinishRunnable$1
        implements Runnable {
    public final /* synthetic */ FingerprintEnrollEnrollingUdfpsFragment this$0;

    public FingerprintEnrollEnrollingUdfpsFragment$delayedFinishRunnable$1(
            FingerprintEnrollEnrollingUdfpsFragment fingerprintEnrollEnrollingUdfpsFragment) {
        this.this$0 = fingerprintEnrollEnrollingUdfpsFragment;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FingerprintEnrollEnrollingViewModel fingerprintEnrollEnrollingViewModel =
                this.this$0._enrollingViewModel;
        Intrinsics.checkNotNull(fingerprintEnrollEnrollingViewModel);
        fingerprintEnrollEnrollingViewModel.mActionLiveData.postValue(0);
    }
}
