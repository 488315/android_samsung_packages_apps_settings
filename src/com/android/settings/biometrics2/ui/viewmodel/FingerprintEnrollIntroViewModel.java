package com.android.settings.biometrics2.ui.viewmodel;

import android.app.Application;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleCoroutineScopeImpl;

import com.android.settings.R;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.model.EnrollmentRequest;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollable;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollIntroViewModel extends AndroidViewModel {
    public final SharedFlowImpl _actionFlow;
    public final StateFlowImpl enrollableStatusFlow;
    public final FingerprintRepository fingerprintRepository;
    public final StateFlowImpl hasScrolledToBottomFlow;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 pageStatusFlow;
    public final EnrollmentRequest request;
    public final int userId;

    static {
        FingerprintEnrollable fingerprintEnrollable =
                FingerprintEnrollable.FINGERPRINT_ENROLLABLE_OK;
    }

    public FingerprintEnrollIntroViewModel(
            int i,
            Application application,
            FingerprintRepository fingerprintRepository,
            EnrollmentRequest enrollmentRequest) {
        super(application);
        this.fingerprintRepository = fingerprintRepository;
        this.request = enrollmentRequest;
        this.userId = i;
        this._actionFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.hasScrolledToBottomFlow = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(getEnrollableStatus());
        this.enrollableStatusFlow = MutableStateFlow2;
        this.pageStatusFlow =
                FlowKt.flowCombine(
                        MutableStateFlow,
                        MutableStateFlow2,
                        new FingerprintEnrollIntroViewModel$pageStatusFlow$1(3, null));
    }

    public final FingerprintEnrollable getEnrollableStatus() {
        int i;
        FingerprintRepository fingerprintRepository = this.fingerprintRepository;
        int numOfEnrolledFingerprintsSize =
                fingerprintRepository.getNumOfEnrolledFingerprintsSize(this.userId);
        EnrollmentRequest enrollmentRequest = this.request;
        if (!enrollmentRequest.isSuw || enrollmentRequest.isAfterSuwOrSuwSuggestedAction) {
            FingerprintSensorPropertiesInternal firstFingerprintSensorPropertiesInternal =
                    fingerprintRepository.getFirstFingerprintSensorPropertiesInternal();
            i =
                    firstFingerprintSensorPropertiesInternal != null
                            ? firstFingerprintSensorPropertiesInternal.maxEnrollmentsPerUser
                            : 0;
        } else {
            i =
                    getApplication()
                            .getResources()
                            .getInteger(R.integer.suw_max_fingerprints_enrollable);
        }
        return numOfEnrolledFingerprintsSize >= i
                ? FingerprintEnrollable.FINGERPRINT_ENROLLABLE_ERROR_REACH_MAX
                : FingerprintEnrollable.FINGERPRINT_ENROLLABLE_OK;
    }

    public final void onNextButtonClick(LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintEnrollIntroViewModel$onNextButtonClick$1(this, null),
                3);
    }

    public final void onSkipOrCancelButtonClick(
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintEnrollIntroViewModel$onSkipOrCancelButtonClick$1(this, null),
                3);
    }

    public final void setHasScrolledToBottom(
            boolean z, LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintEnrollIntroViewModel$setHasScrolledToBottom$1(this, z, null),
                3);
    }

    public final void updateEnrollableStatus(
            LifecycleCoroutineScopeImpl lifecycleCoroutineScopeImpl) {
        BuildersKt.launch$default(
                lifecycleCoroutineScopeImpl,
                null,
                null,
                new FingerprintEnrollIntroViewModel$updateEnrollableStatus$1(this, null),
                3);
    }
}
