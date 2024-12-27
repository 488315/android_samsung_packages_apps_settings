package com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;

import com.android.settings.SettingsApplication;
import com.android.settings.biometrics.fingerprint2.BiometricsEnvironment;
import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintGatekeeperViewModel extends ViewModel {
    public static final InitializerViewModelFactory Factory;
    public final StateFlowImpl _gatekeeperInfo;
    public final ReadonlyStateFlow gatekeeperInfo;
    public final FingerprintGatekeeperViewModel$special$$inlined$map$1 hasValidGatekeeperInfo;

    static {
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder =
                new InitializerViewModelFactoryBuilder();
        initializerViewModelFactoryBuilder.addInitializer(
                Reflection.factory.getOrCreateKotlinClass(FingerprintGatekeeperViewModel.class),
                new Function1() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintGatekeeperViewModel$Companion$Factory$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        CreationExtras initializer = (CreationExtras) obj;
                        Intrinsics.checkNotNullParameter(initializer, "$this$initializer");
                        Object obj2 =
                                initializer.get(
                                        ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY);
                        Intrinsics.checkNotNull(
                                obj2,
                                "null cannot be cast to non-null type"
                                    + " com.android.settings.SettingsApplication");
                        BiometricsEnvironment biometricsEnvironment =
                                ((SettingsApplication) obj2).mBiometricsEnvironment;
                        Intrinsics.checkNotNull(biometricsEnvironment);
                        return new FingerprintGatekeeperViewModel(
                                (FingerprintManagerInteractorImpl)
                                        biometricsEnvironment.fingerprintManagerInteractor$delegate
                                                .getValue());
                    }
                });
        Factory = initializerViewModelFactoryBuilder.build();
    }

    public FingerprintGatekeeperViewModel(
            FingerprintManagerInteractorImpl fingerprintManagerInteractor) {
        Intrinsics.checkNotNullParameter(
                fingerprintManagerInteractor, "fingerprintManagerInteractor");
        this.hasValidGatekeeperInfo =
                new FingerprintGatekeeperViewModel$special$$inlined$map$1(
                        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(null)));
    }
}
