package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.model.EnrollmentRequest;

import kotlinx.atomicfu.AtomicBoolean;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollmentViewModel extends AndroidViewModel {
    public final SharedFlowImpl _setResultFlow;
    public final FingerprintRepository fingerprintRepository;
    public boolean isNewFingerprintAdded;
    public final AtomicBoolean isWaitingActivityResult;
    public final EnrollmentRequest request;

    public FingerprintEnrollmentViewModel(
            Application application,
            FingerprintRepository fingerprintRepository,
            EnrollmentRequest enrollmentRequest) {
        super(application);
        this.fingerprintRepository = fingerprintRepository;
        this.request = enrollmentRequest;
        this.isWaitingActivityResult = new AtomicBoolean(false);
        this._setResultFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    }

    public final void checkFinishActivityDuringOnPause(
            boolean z, boolean z2, LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        if (z2 || z || this.request.isSuw || this.isWaitingActivityResult.getValue()) {
            return;
        }
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintEnrollmentViewModel$checkFinishActivityDuringOnPause$1(this, null),
                3);
    }
}
