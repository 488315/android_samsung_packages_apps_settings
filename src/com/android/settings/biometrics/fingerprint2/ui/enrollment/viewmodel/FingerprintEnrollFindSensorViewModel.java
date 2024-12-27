package com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel;

import androidx.lifecycle.HasDefaultViewModelProviderFactory;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory;

import com.android.settings.SettingsApplication;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.viewmodel.RFPSViewModel$Companion$Factory$1$1$$ExternalSyntheticThrowCCEIfNotNull0;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KClass;

import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintEnrollFindSensorViewModel extends ViewModel {
    public static final InitializerViewModelFactory Factory;
    public final StateFlowImpl _education;
    public final SafeFlow sfpsLottieInfo;
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 showErrorDialog;
    public final FingerprintEnrollFindSensorViewModel$special$$inlined$filter$1 showPrimaryButton;
    public final FingerprintEnrollFindSensorViewModel$special$$inlined$filter$1 showRfpsAnimation;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 udfpsLottieInfo;

    static {
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getOrCreateKotlinClass(FingerprintNavigationStep$Education.class);
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder =
                new InitializerViewModelFactoryBuilder();
        initializerViewModelFactoryBuilder.addInitializer(
                reflectionFactory.getOrCreateKotlinClass(
                        FingerprintEnrollFindSensorViewModel.class),
                new Function1() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel$Companion$Factory$1$1
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
                        Intrinsics.checkNotNull(
                                ((SettingsApplication) obj2).mBiometricsEnvironment);
                        Object obj3 =
                                initializer.get(SavedStateHandleSupport.VIEW_MODEL_STORE_OWNER_KEY);
                        Intrinsics.checkNotNull(obj3);
                        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) obj3;
                        ViewModelStore store = viewModelStoreOwner.getViewModelStore();
                        ViewModelProvider.Factory factory =
                                viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory
                                        ? ((HasDefaultViewModelProviderFactory) viewModelStoreOwner)
                                                .getDefaultViewModelProviderFactory()
                                        : DefaultViewModelProviderFactory.INSTANCE;
                        CreationExtras defaultCreationExtras =
                                viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory
                                        ? ((HasDefaultViewModelProviderFactory) viewModelStoreOwner)
                                                .getDefaultViewModelCreationExtras()
                                        : CreationExtras.Empty.INSTANCE;
                        Intrinsics.checkNotNullParameter(store, "store");
                        Intrinsics.checkNotNullParameter(factory, "factory");
                        Intrinsics.checkNotNullParameter(
                                defaultCreationExtras, "defaultCreationExtras");
                        ViewModelProviderImpl viewModelProviderImpl =
                                new ViewModelProviderImpl(store, factory, defaultCreationExtras);
                        KClass orCreateKotlinClass =
                                Reflection.factory.getOrCreateKotlinClass(
                                        FingerprintNavigationViewModel.class);
                        String qualifiedName = orCreateKotlinClass.getQualifiedName();
                        if (qualifiedName == null) {
                            throw new IllegalArgumentException(
                                    "Local and anonymous classes can not be ViewModels".toString());
                        }
                        RFPSViewModel$Companion$Factory$1$1$$ExternalSyntheticThrowCCEIfNotNull0.m(
                                viewModelProviderImpl.getViewModel$lifecycle_viewmodel_release(
                                        orCreateKotlinClass,
                                        "androidx.lifecycle.ViewModelProvider.DefaultKey:"
                                                .concat(qualifiedName)));
                        throw null;
                    }
                });
        Factory = initializerViewModelFactoryBuilder.build();
    }
}