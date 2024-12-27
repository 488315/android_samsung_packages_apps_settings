package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.InitializerViewModelFactory;
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RFPSIconTouchViewModel extends ViewModel {
    public static final InitializerViewModelFactory Factory;
    public final StateFlowImpl _touches;
    public final ReadonlySharedFlow shouldShowDialog;

    static {
        InitializerViewModelFactoryBuilder initializerViewModelFactoryBuilder =
                new InitializerViewModelFactoryBuilder();
        initializerViewModelFactoryBuilder.addInitializer(
                Reflection.factory.getOrCreateKotlinClass(RFPSIconTouchViewModel.class),
                new Function1() { // from class:
                                  // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.rfps.ui.viewmodel.RFPSIconTouchViewModel$Companion$Factory$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        CreationExtras initializer = (CreationExtras) obj;
                        Intrinsics.checkNotNullParameter(initializer, "$this$initializer");
                        return new RFPSIconTouchViewModel();
                    }
                });
        Factory = initializerViewModelFactoryBuilder.build();
    }

    public RFPSIconTouchViewModel() {
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0);
        this._touches = MutableStateFlow;
        FlowKt.shareIn(
                new SafeFlow(
                        new RFPSIconTouchViewModel$special$$inlined$transform$1(
                                MutableStateFlow, null)),
                ViewModelKt.getViewModelScope(this),
                SharingStarted.Companion.Eagerly,
                0);
    }
}
