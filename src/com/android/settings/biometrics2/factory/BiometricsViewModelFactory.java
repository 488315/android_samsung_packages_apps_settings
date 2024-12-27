package com.android.settings.biometrics2.factory;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.biometrics.fingerprint.FingerprintUpdater;
import com.android.settings.biometrics2.data.repository.FingerprintRepository;
import com.android.settings.biometrics2.ui.model.CredentialModel;
import com.android.settings.biometrics2.ui.model.EnrollmentRequest;
import com.android.settings.biometrics2.ui.viewmodel.AutoCredentialViewModel;
import com.android.settings.biometrics2.ui.viewmodel.DeviceFoldedViewModel;
import com.android.settings.biometrics2.ui.viewmodel.DeviceRotationViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollEnrollingViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFindSensorViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollFinishViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollIntroViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollProgressViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollmentViewModel;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BiometricsViewModelFactory implements ViewModelProvider.Factory {
    public static final AnonymousClass1 CHALLENGE_GENERATOR_KEY = new AnonymousClass1();
    public static final AnonymousClass1 ENROLLMENT_REQUEST_KEY = new AnonymousClass1();
    public static final AnonymousClass1 CREDENTIAL_MODEL_KEY = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.biometrics2.factory.BiometricsViewModelFactory$1, reason: invalid class name */
    public final class AnonymousClass1 implements CreationExtras.Key {}

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls, CreationExtras creationExtras) {
        EnrollmentRequest enrollmentRequest;
        Application application =
                (Application)
                        creationExtras.get(
                                ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
        if (application == null) {
            Log.w("BiometricsViewModelFactory", "create, null application");
            throw new UnsupportedOperationException(
                    "`Factory.create(String, CreationExtras)` is not implemented. You may need to"
                        + " override the method and provide a custom implementation. Note that"
                        + " using `Factory.create(String)` is not supported and considered an"
                        + " error.");
        }
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        BiometricsRepositoryProviderImpl biometricsRepositoryProviderImpl =
                (BiometricsRepositoryProviderImpl)
                        featureFactoryImpl.biometricsRepositoryProvider$delegate.getValue();
        boolean isAssignableFrom = cls.isAssignableFrom(AutoCredentialViewModel.class);
        AnonymousClass1 anonymousClass1 = CREDENTIAL_MODEL_KEY;
        if (isAssignableFrom) {
            LockPatternUtils lockPatternUtils =
                    featureFactoryImpl
                            .getSecurityFeatureProvider()
                            .getLockPatternUtils(application);
            AutoCredentialViewModel.FingerprintChallengeGenerator fingerprintChallengeGenerator =
                    (AutoCredentialViewModel.FingerprintChallengeGenerator)
                            creationExtras.get(CHALLENGE_GENERATOR_KEY);
            CredentialModel credentialModel = (CredentialModel) creationExtras.get(anonymousClass1);
            if (fingerprintChallengeGenerator != null && credentialModel != null) {
                return new AutoCredentialViewModel(
                        application,
                        lockPatternUtils,
                        fingerprintChallengeGenerator,
                        credentialModel);
            }
        } else {
            if (cls.isAssignableFrom(DeviceFoldedViewModel.class)) {
                return new DeviceFoldedViewModel(
                        new ScreenSizeFoldProvider(application), application.getMainExecutor());
            }
            if (cls.isAssignableFrom(DeviceRotationViewModel.class)) {
                return new DeviceRotationViewModel(application);
            }
            boolean isAssignableFrom2 =
                    cls.isAssignableFrom(FingerprintEnrollFindSensorViewModel.class);
            AnonymousClass1 anonymousClass12 = ENROLLMENT_REQUEST_KEY;
            if (isAssignableFrom2) {
                EnrollmentRequest enrollmentRequest2 =
                        (EnrollmentRequest) creationExtras.get(anonymousClass12);
                if (enrollmentRequest2 != null) {
                    return new FingerprintEnrollFindSensorViewModel(
                            application, enrollmentRequest2.isSuw);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollIntroViewModel.class)) {
                biometricsRepositoryProviderImpl.getClass();
                FingerprintRepository fingerprintRepository =
                        BiometricsRepositoryProviderImpl.getFingerprintRepository(application);
                EnrollmentRequest enrollmentRequest3 =
                        (EnrollmentRequest) creationExtras.get(anonymousClass12);
                CredentialModel credentialModel2 =
                        (CredentialModel) creationExtras.get(anonymousClass1);
                if (fingerprintRepository != null
                        && enrollmentRequest3 != null
                        && credentialModel2 != null) {
                    return new FingerprintEnrollIntroViewModel(
                            credentialModel2.userId,
                            application,
                            fingerprintRepository,
                            enrollmentRequest3);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollmentViewModel.class)) {
                biometricsRepositoryProviderImpl.getClass();
                FingerprintRepository fingerprintRepository2 =
                        BiometricsRepositoryProviderImpl.getFingerprintRepository(application);
                EnrollmentRequest enrollmentRequest4 =
                        (EnrollmentRequest) creationExtras.get(anonymousClass12);
                if (fingerprintRepository2 != null && enrollmentRequest4 != null) {
                    return new FingerprintEnrollmentViewModel(
                            application, fingerprintRepository2, enrollmentRequest4);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollProgressViewModel.class)) {
                CredentialModel credentialModel3 =
                        (CredentialModel) creationExtras.get(anonymousClass1);
                if (credentialModel3 != null) {
                    return new FingerprintEnrollProgressViewModel(
                            application,
                            new FingerprintUpdater(application),
                            credentialModel3.userId);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollEnrollingViewModel.class)) {
                CredentialModel credentialModel4 =
                        (CredentialModel) creationExtras.get(anonymousClass1);
                biometricsRepositoryProviderImpl.getClass();
                FingerprintRepository fingerprintRepository3 =
                        BiometricsRepositoryProviderImpl.getFingerprintRepository(application);
                if (fingerprintRepository3 != null && credentialModel4 != null) {
                    return new FingerprintEnrollEnrollingViewModel(
                            application, credentialModel4.userId, fingerprintRepository3);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollFinishViewModel.class)) {
                CredentialModel credentialModel5 =
                        (CredentialModel) creationExtras.get(anonymousClass1);
                EnrollmentRequest enrollmentRequest5 =
                        (EnrollmentRequest) creationExtras.get(anonymousClass12);
                biometricsRepositoryProviderImpl.getClass();
                FingerprintRepository fingerprintRepository4 =
                        BiometricsRepositoryProviderImpl.getFingerprintRepository(application);
                if (fingerprintRepository4 != null
                        && credentialModel5 != null
                        && enrollmentRequest5 != null) {
                    return new FingerprintEnrollFinishViewModel(
                            credentialModel5.userId,
                            application,
                            fingerprintRepository4,
                            enrollmentRequest5);
                }
            } else if (cls.isAssignableFrom(FingerprintEnrollErrorDialogViewModel.class)
                    && (enrollmentRequest =
                                    (EnrollmentRequest) creationExtras.get(anonymousClass12))
                            != null) {
                return new FingerprintEnrollErrorDialogViewModel(
                        application, enrollmentRequest.isSuw);
            }
        }
        throw new UnsupportedOperationException(
                "`Factory.create(String, CreationExtras)` is not implemented. You may need to"
                    + " override the method and provide a custom implementation. Note that using"
                    + " `Factory.create(String)` is not supported and considered an error.");
    }
}
