package com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class BackgroundViewModel extends ViewModel {
    public final StateFlowImpl _background;
    public final ReadonlyStateFlow background;

    static {
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder =
                new InitializerViewModelFactoryBuilder();
        initializerViewModelFactoryBuilder.addInitializer(
                Reflection.factory.getOrCreateKotlinClass(BackgroundViewModel.class),
                new Function1() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.BackgroundViewModel$Companion$Factory$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        CreationExtras initializer = (CreationExtras) obj;
                        Intrinsics.checkNotNullParameter(initializer, "$this$initializer");
                        return new BackgroundViewModel();
                    }
                });
        initializerViewModelFactoryBuilder.build();
    }

    public BackgroundViewModel() {
        FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(Boolean.FALSE));
    }
}
