package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;

import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSettingsNavigationViewModel extends ViewModel {
    public final StateFlowImpl _nextStep;
    public final CoroutineDispatcher backgroundDispatcher;
    public Long challenge;
    public final FingerprintManagerInteractorImpl fingerprintManagerInteractor;
    public final ReadonlyStateFlow nextStep;
    public byte[] token;
    public final int userId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsNavigationViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FingerprintSettingsNavigationViewModel.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object value;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SafeFlow safeFlow =
                        FingerprintSettingsNavigationViewModel.this
                                .fingerprintManagerInteractor
                                .enrolledFingerprints;
                this.label = 1;
                obj = FlowKt.last(safeFlow, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            List list = (List) obj;
            if (list == null || !list.isEmpty()) {
                FingerprintSettingsNavigationViewModel.this.showSettingsHelper();
            } else {
                FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel =
                        FingerprintSettingsNavigationViewModel.this;
                StateFlowImpl stateFlowImpl = fingerprintSettingsNavigationViewModel._nextStep;
                do {
                    value = stateFlowImpl.getValue();
                } while (!stateFlowImpl.compareAndSet(
                        value,
                        new EnrollFirstFingerprint(
                                fingerprintSettingsNavigationViewModel.userId,
                                null,
                                fingerprintSettingsNavigationViewModel.challenge,
                                fingerprintSettingsNavigationViewModel.token)));
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FingerprintSettingsNavigationModelFactory
            implements ViewModelProvider.Factory {
        public final CoroutineDispatcher backgroundDispatcher;
        public final Long challenge;
        public final FingerprintManagerInteractorImpl interactor;
        public final byte[] token;
        public final int userId;

        public FingerprintSettingsNavigationModelFactory(
                int i,
                FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl,
                CoroutineDispatcher backgroundDispatcher,
                byte[] bArr,
                Long l) {
            Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
            this.userId = i;
            this.interactor = fingerprintManagerInteractorImpl;
            this.backgroundDispatcher = backgroundDispatcher;
            this.token = bArr;
            this.challenge = l;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            return new FingerprintSettingsNavigationViewModel(
                    this.userId,
                    this.interactor,
                    this.backgroundDispatcher,
                    this.token,
                    this.challenge);
        }
    }

    public FingerprintSettingsNavigationViewModel(
            int i,
            FingerprintManagerInteractorImpl fingerprintManagerInteractor,
            CoroutineDispatcher backgroundDispatcher,
            byte[] bArr,
            Long l) {
        Object value;
        Intrinsics.checkNotNullParameter(
                fingerprintManagerInteractor, "fingerprintManagerInteractor");
        Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
        this.userId = i;
        this.fingerprintManagerInteractor = fingerprintManagerInteractor;
        this.backgroundDispatcher = backgroundDispatcher;
        this.token = bArr;
        this.challenge = l;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._nextStep = MutableStateFlow;
        this.nextStep = FlowKt.asStateFlow(MutableStateFlow);
        if (l != null && bArr != null) {
            BuildersKt.launch$default(
                    ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3);
            return;
        }
        do {
            value = MutableStateFlow.getValue();
        } while (!MutableStateFlow.compareAndSet(
                value, new LaunchConfirmDeviceCredential(this.userId)));
    }

    public final void launchFinishSettings(String str) {
        StateFlowImpl stateFlowImpl;
        Object value;
        do {
            stateFlowImpl = this._nextStep;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, new FinishSettings(str)));
    }

    public final void setStepToLaunched() {
        StateFlowImpl stateFlowImpl;
        Object value;
        do {
            stateFlowImpl = this._nextStep;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, LaunchedActivity.INSTANCE));
    }

    public final void showSettingsHelper() {
        StateFlowImpl stateFlowImpl;
        Object value;
        do {
            stateFlowImpl = this._nextStep;
            value = stateFlowImpl.getValue();
        } while (!stateFlowImpl.compareAndSet(value, ShowSettings.INSTANCE));
    }
}
