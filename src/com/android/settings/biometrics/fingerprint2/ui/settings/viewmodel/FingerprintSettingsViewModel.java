package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.lifecycle.ViewModelProvider;

import com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl;
import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintData;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FingerprintSettingsViewModel extends ViewModel {
    public final StateFlowImpl _attemptsSoFar;
    public final SharedFlowImpl _authSucceeded;
    public final StateFlowImpl _consumerShouldAuthenticate;
    public final StateFlowImpl _enrolledFingerprints;
    public final FingerprintSettingsViewModel$special$$inlined$map$1 _fingerprintSensorType;
    public final StateFlowImpl _isLockedOut;
    public final StateFlowImpl _isShowingDialog;
    public final FingerprintSettingsViewModel$special$$inlined$map$1 _sensorNullOrEmpty;
    public final SafeFlow addFingerprintPrefInfo;
    public final Flow authFlow;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SafeFlow enrolledFingerprints;
    public final FingerprintManagerInteractorImpl fingerprintManagerInteractor;
    public final SafeFlow isSfpsPrefVisible;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShowingDialog;
    public final FingerprintSettingsNavigationViewModel navigationViewModel;
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
    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return FingerprintSettingsViewModel.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull =
                        FlowKt.filterNotNull(
                                FingerprintSettingsViewModel.this.navigationViewModel.nextStep);
                final FingerprintSettingsViewModel fingerprintSettingsViewModel =
                        FingerprintSettingsViewModel.this;
                FlowCollector flowCollector =
                        new FlowCollector() { // from class:
                                              // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Object value;
                                Object access$updateEnrolledFingerprints;
                                NextStepViewModel nextStepViewModel = (NextStepViewModel) obj2;
                                FingerprintSettingsViewModel fingerprintSettingsViewModel2 =
                                        FingerprintSettingsViewModel.this;
                                StateFlowImpl stateFlowImpl =
                                        fingerprintSettingsViewModel2._isShowingDialog;
                                do {
                                    value = stateFlowImpl.getValue();
                                } while (!stateFlowImpl.compareAndSet(value, null));
                                boolean z = nextStepViewModel instanceof ShowSettings;
                                Unit unit = Unit.INSTANCE;
                                return (z
                                                && (access$updateEnrolledFingerprints =
                                                                FingerprintSettingsViewModel
                                                                        .access$updateEnrolledFingerprints(
                                                                                fingerprintSettingsViewModel2,
                                                                                continuation))
                                                        == CoroutineSingletons.COROUTINE_SUSPENDED)
                                        ? access$updateEnrolledFingerprints
                                        : unit;
                            }
                        };
                this.label = 1;
                if (filterNotNull.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FingerprintSettingsViewModelFactory implements ViewModelProvider.Factory {
        public final CoroutineDispatcher backgroundDispatcher;
        public final FingerprintManagerInteractorImpl interactor;
        public final FingerprintSettingsNavigationViewModel navigationViewModel;
        public final int userId;

        public FingerprintSettingsViewModelFactory(
                int i,
                FingerprintManagerInteractorImpl fingerprintManagerInteractorImpl,
                CoroutineDispatcher backgroundDispatcher,
                FingerprintSettingsNavigationViewModel fingerprintSettingsNavigationViewModel) {
            Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
            this.userId = i;
            this.interactor = fingerprintManagerInteractorImpl;
            this.backgroundDispatcher = backgroundDispatcher;
            this.navigationViewModel = fingerprintSettingsNavigationViewModel;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            return new FingerprintSettingsViewModel(
                    this.userId,
                    this.interactor,
                    this.backgroundDispatcher,
                    this.navigationViewModel);
        }
    }

    /* JADX WARN: Type inference failed for: r13v2, types: [com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1] */
    public FingerprintSettingsViewModel(
            int i,
            FingerprintManagerInteractorImpl fingerprintManagerInteractor,
            CoroutineDispatcher backgroundDispatcher,
            FingerprintSettingsNavigationViewModel navigationViewModel) {
        Intrinsics.checkNotNullParameter(
                fingerprintManagerInteractor, "fingerprintManagerInteractor");
        Intrinsics.checkNotNullParameter(backgroundDispatcher, "backgroundDispatcher");
        Intrinsics.checkNotNullParameter(navigationViewModel, "navigationViewModel");
        this.userId = i;
        this.fingerprintManagerInteractor = fingerprintManagerInteractor;
        this.backgroundDispatcher = backgroundDispatcher;
        this.navigationViewModel = navigationViewModel;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._enrolledFingerprints = MutableStateFlow;
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull =
                FlowKt.filterNotNull(FlowKt.asStateFlow(MutableStateFlow));
        FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1
                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1 =
                        new FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1(4, null);
        ReadonlyStateFlow flow = navigationViewModel.nextStep;
        Intrinsics.checkNotNullParameter(flow, "flow");
        this.enrolledFingerprints =
                new SafeFlow(
                        new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1(
                                new Flow[] {filterNotNull, flow},
                                null,
                                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1));
        FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1
                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$12 =
                        new FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1(4, null);
        Intrinsics.checkNotNullParameter(flow, "flow");
        this.addFingerprintPrefInfo =
                new SafeFlow(
                        new FingerprintSettingsViewModel$special$$inlined$transform$1(
                                new SafeFlow(
                                        new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1(
                                                new Flow[] {MutableStateFlow, flow},
                                                null,
                                                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$12)),
                                null,
                                this));
        FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1
                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$13 =
                        new FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1(4, null);
        Intrinsics.checkNotNullParameter(flow, "flow");
        this.isSfpsPrefVisible =
                new SafeFlow(
                        new FingerprintSettingsViewModel$special$$inlined$transform$2(
                                new SafeFlow(
                                        new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1(
                                                new Flow[] {MutableStateFlow, flow},
                                                null,
                                                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$13)),
                                null,
                                this));
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(null);
        this._isShowingDialog = MutableStateFlow2;
        this.isShowingDialog =
                FlowKt.flowCombine(
                        MutableStateFlow2,
                        flow,
                        new FingerprintSettingsViewModel$isShowingDialog$1(3, null));
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._consumerShouldAuthenticate = MutableStateFlow3;
        final Flow flow2 = fingerprintManagerInteractor.sensorPropertiesInternal;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 filterNotNull2 =
                FlowKt.filterNotNull(flow2);
        final int i2 = 0;
        Flow flow3 = new Flow() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(
                                java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L41
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.biometrics.shared.model.FingerprintSensor r5 = (com.android.systemui.biometrics.shared.model.FingerprintSensor) r5
                                com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = r5.sensorType
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L41
                                return r1
                            L41:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i2) {
                            case 0:
                                Object collect =
                                        ((Flow) filterNotNull2)
                                                .collect(
                                                        new AnonymousClass2(flowCollector),
                                                        continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        ((Flow) filterNotNull2)
                                                .collect(
                                                        new FingerprintSettingsViewModel$special$$inlined$map$2$2(
                                                                flowCollector),
                                                        continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                final Flow[] flowArr = (Flow[]) filterNotNull2;
                                Object combineInternal =
                                        CombineKt.combineInternal(
                                                continuation,
                                                new Function0() { // from class:
                                                    // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$combine$1$2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        return new Object[flowArr.length];
                                                    }
                                                },
                                                new FingerprintSettingsViewModel$special$$inlined$combine$1$3(
                                                        3, null),
                                                flowCollector,
                                                flowArr);
                                if (combineInternal != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        final int i3 = 1;
        Flow flow4 = new Flow() { // from class:
                    // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                        @Metadata(
                                k = 3,
                                mv = {1, 9, 0},
                                xi = 48)
                        /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
                        }

                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj, Continuation continuation) {
                            /*
                                this = this;
                                boolean r0 = r6 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L41
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                com.android.systemui.biometrics.shared.model.FingerprintSensor r5 = (com.android.systemui.biometrics.shared.model.FingerprintSensor) r5
                                com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = r5.sensorType
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L41
                                return r1
                            L41:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(
                            FlowCollector flowCollector, Continuation continuation) {
                        switch (i3) {
                            case 0:
                                Object collect =
                                        ((Flow) flow2)
                                                .collect(
                                                        new AnonymousClass2(flowCollector),
                                                        continuation);
                                if (collect != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            case 1:
                                Object collect2 =
                                        ((Flow) flow2)
                                                .collect(
                                                        new FingerprintSettingsViewModel$special$$inlined$map$2$2(
                                                                flowCollector),
                                                        continuation);
                                if (collect2 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                            default:
                                final Flow[] flowArr = (Flow[]) flow2;
                                Object combineInternal =
                                        CombineKt.combineInternal(
                                                continuation,
                                                new Function0() { // from class:
                                                    // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$combine$1$2
                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    {
                                                        super(0);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function0
                                                    /* renamed from: invoke */
                                                    public final Object mo1068invoke() {
                                                        return new Object[flowArr.length];
                                                    }
                                                },
                                                new FingerprintSettingsViewModel$special$$inlined$combine$1$3(
                                                        3, null),
                                                flowCollector,
                                                flowArr);
                                if (combineInternal != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                    break;
                                }
                                break;
                        }
                        return Unit.INSTANCE;
                    }
                };
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._isLockedOut = MutableStateFlow4;
        this._authSucceeded = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(0);
        this._attemptsSoFar = MutableStateFlow5;
        final Flow[] flowArr = {
            MutableStateFlow2,
            flow,
            MutableStateFlow3,
            MutableStateFlow,
            MutableStateFlow4,
            MutableStateFlow5,
            flow3,
            flow4
        };
        final int i4 = 2;
        this.authFlow =
                FlowKt.flowOn(
                        FlowKt.transformLatest(
                                FlowKt.distinctUntilChanged(
                                        FlowKt.sample(
                                                new Flow() { // from class:
                                                    // com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1

                                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                    /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2, reason: invalid class name */
                                                    public final class AnonymousClass2
                                                            implements FlowCollector {
                                                        public final /* synthetic */ FlowCollector
                                                                $this_unsafeFlow;

                                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                        @Metadata(
                                                                k = 3,
                                                                mv = {1, 9, 0},
                                                                xi = 48)
                                                        /* renamed from: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                                                        public final class AnonymousClass1
                                                                extends ContinuationImpl {
                                                            Object L$0;
                                                            int label;
                                                            /* synthetic */ Object result;

                                                            public AnonymousClass1(
                                                                    Continuation continuation) {
                                                                super(continuation);
                                                            }

                                                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                            public final Object invokeSuspend(
                                                                    Object obj) {
                                                                this.result = obj;
                                                                this.label |= Integer.MIN_VALUE;
                                                                return AnonymousClass2.this.emit(
                                                                        null, this);
                                                            }
                                                        }

                                                        public AnonymousClass2(
                                                                FlowCollector flowCollector) {
                                                            this.$this_unsafeFlow = flowCollector;
                                                        }

                                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                                        public final Object emit(
                                                                Object obj,
                                                                Continuation continuation) {
                                                            /*
                                                                this = this;
                                                                boolean r0 = r6 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                                if (r0 == 0) goto L13
                                                                r0 = r6
                                                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                                int r1 = r0.label
                                                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                                r3 = r1 & r2
                                                                if (r3 == 0) goto L13
                                                                int r1 = r1 - r2
                                                                r0.label = r1
                                                                goto L18
                                                            L13:
                                                                com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1$2$1
                                                                r0.<init>(r6)
                                                            L18:
                                                                java.lang.Object r6 = r0.result
                                                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                                int r2 = r0.label
                                                                r3 = 1
                                                                if (r2 == 0) goto L2f
                                                                if (r2 != r3) goto L27
                                                                kotlin.ResultKt.throwOnFailure(r6)
                                                                goto L41
                                                            L27:
                                                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                                r4.<init>(r5)
                                                                throw r4
                                                            L2f:
                                                                kotlin.ResultKt.throwOnFailure(r6)
                                                                com.android.systemui.biometrics.shared.model.FingerprintSensor r5 = (com.android.systemui.biometrics.shared.model.FingerprintSensor) r5
                                                                com.android.systemui.biometrics.shared.model.FingerprintSensorType r5 = r5.sensorType
                                                                r0.label = r3
                                                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                                java.lang.Object r4 = r4.emit(r5, r0)
                                                                if (r4 != r1) goto L41
                                                                return r1
                                                            L41:
                                                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                                return r4
                                                            */
                                                            throw new UnsupportedOperationException(
                                                                    "Method not decompiled:"
                                                                        + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                                                        }
                                                    }

                                                    @Override // kotlinx.coroutines.flow.Flow
                                                    public final Object collect(
                                                            FlowCollector flowCollector,
                                                            Continuation continuation) {
                                                        switch (i4) {
                                                            case 0:
                                                                Object collect =
                                                                        ((Flow) flowArr)
                                                                                .collect(
                                                                                        new AnonymousClass2(
                                                                                                flowCollector),
                                                                                        continuation);
                                                                if (collect
                                                                        != CoroutineSingletons
                                                                                .COROUTINE_SUSPENDED) {
                                                                    break;
                                                                }
                                                                break;
                                                            case 1:
                                                                Object collect2 =
                                                                        ((Flow) flowArr)
                                                                                .collect(
                                                                                        new FingerprintSettingsViewModel$special$$inlined$map$2$2(
                                                                                                flowCollector),
                                                                                        continuation);
                                                                if (collect2
                                                                        != CoroutineSingletons
                                                                                .COROUTINE_SUSPENDED) {
                                                                    break;
                                                                }
                                                                break;
                                                            default:
                                                                final Flow[] flowArr2 =
                                                                        (Flow[]) flowArr;
                                                                Object combineInternal =
                                                                        CombineKt.combineInternal(
                                                                                continuation,
                                                                                new Function0() { // from class: com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$special$$inlined$combine$1$2
                                                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                    {
                                                                                        super(0);
                                                                                    }

                                                                                    @Override // kotlin.jvm.functions.Function0
                                                                                    /* renamed from: invoke */
                                                                                    public final
                                                                                    Object
                                                                                            mo1068invoke() {
                                                                                        return new Object
                                                                                                [flowArr2.length];
                                                                                    }
                                                                                },
                                                                                new FingerprintSettingsViewModel$special$$inlined$combine$1$3(
                                                                                        3, null),
                                                                                flowCollector,
                                                                                flowArr2);
                                                                if (combineInternal
                                                                        != CoroutineSingletons
                                                                                .COROUTINE_SUSPENDED) {
                                                                    break;
                                                                }
                                                                break;
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                })),
                                new FingerprintSettingsViewModel$authFlow$1(this, null)),
                        backgroundDispatcher);
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$onAuthSuccess(
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel
                            .FingerprintSettingsViewModel
                    r4,
            com.android.settings.biometrics.fingerprint2.lib.model.FingerprintAuthAttemptModel
                            .Success
                    r5,
            kotlin.coroutines.Continuation r6) {
        /*
            r4.getClass()
            boolean r0 = r6 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$onAuthSuccess$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$onAuthSuccess$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$onAuthSuccess$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$onAuthSuccess$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$onAuthSuccess$1
            r0.<init>(r4, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r4 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L46
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.flow.SharedFlowImpl r6 = r4._authSucceeded
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r6.emit(r5, r0)
            if (r5 != r1) goto L46
            goto L60
        L46:
            kotlinx.coroutines.flow.StateFlowImpl r4 = r4._attemptsSoFar
        L48:
            java.lang.Object r5 = r4.getValue()
            r6 = r5
            java.lang.Number r6 = (java.lang.Number) r6
            r6.intValue()
            java.lang.Integer r6 = new java.lang.Integer
            r0 = 0
            r6.<init>(r0)
            boolean r5 = r4.compareAndSet(r5, r6)
            if (r5 == 0) goto L48
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L60:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel.access$onAuthSuccess(com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel,"
                    + " com.android.settings.biometrics.fingerprint2.lib.model.FingerprintAuthAttemptModel$Success,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x005c -> B:10:0x0060). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateEnrolledFingerprints(
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel
                            .FingerprintSettingsViewModel
                    r6,
            kotlin.coroutines.Continuation r7) {
        /*
            r6.getClass()
            boolean r0 = r7 instanceof com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$updateEnrolledFingerprints$1
            if (r0 == 0) goto L16
            r0 = r7
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$updateEnrolledFingerprints$1 r0 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$updateEnrolledFingerprints$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$updateEnrolledFingerprints$1 r0 = new com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel$updateEnrolledFingerprints$1
            r0.<init>(r6, r7)
        L1b:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3c
            if (r2 != r3) goto L34
            java.lang.Object r6 = r0.L$2
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.flow.MutableStateFlow r2 = (kotlinx.coroutines.flow.MutableStateFlow) r2
            java.lang.Object r4 = r0.L$0
            com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel r4 = (com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L60
        L34:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3c:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.flow.StateFlowImpl r7 = r6._enrolledFingerprints
            r2 = r7
        L42:
            java.lang.Object r7 = r2.getValue()
            r4 = r7
            java.util.List r4 = (java.util.List) r4
            com.android.settings.biometrics.fingerprint2.domain.interactor.FingerprintManagerInteractorImpl r4 = r6.fingerprintManagerInteractor
            kotlinx.coroutines.flow.SafeFlow r4 = r4.enrolledFingerprints
            r0.L$0 = r6
            r0.L$1 = r2
            r0.L$2 = r7
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.flow.FlowKt.first(r4, r0)
            if (r4 != r1) goto L5c
            goto L6c
        L5c:
            r5 = r4
            r4 = r6
            r6 = r7
            r7 = r5
        L60:
            java.util.List r7 = (java.util.List) r7
            kotlinx.coroutines.flow.StateFlowImpl r2 = (kotlinx.coroutines.flow.StateFlowImpl) r2
            boolean r6 = r2.compareAndSet(r6, r7)
            if (r6 == 0) goto L6d
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L6c:
            return r1
        L6d:
            r6 = r4
            goto L42
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel.access$updateEnrolledFingerprints(com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel.FingerprintSettingsViewModel,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void deleteFingerprint(FingerprintData fp) {
        Intrinsics.checkNotNullParameter(fp, "fp");
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                this.backgroundDispatcher,
                null,
                new FingerprintSettingsViewModel$deleteFingerprint$1(this, fp, null),
                2);
    }

    public final void onDeleteClicked(FingerprintData fingerprintViewModel) {
        Intrinsics.checkNotNullParameter(fingerprintViewModel, "fingerprintViewModel");
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                null,
                null,
                new FingerprintSettingsViewModel$onDeleteClicked$1(
                        this, fingerprintViewModel, null),
                3);
    }

    public final void onPrefClicked(FingerprintData fingerprintViewModel) {
        Intrinsics.checkNotNullParameter(fingerprintViewModel, "fingerprintViewModel");
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                null,
                null,
                new FingerprintSettingsViewModel$onPrefClicked$1(this, fingerprintViewModel, null),
                3);
    }

    public final void renameFingerprint(FingerprintData fp, String newName) {
        Intrinsics.checkNotNullParameter(fp, "fp");
        Intrinsics.checkNotNullParameter(newName, "newName");
        BuildersKt.launch$default(
                ViewModelKt.getViewModelScope(this),
                null,
                null,
                new FingerprintSettingsViewModel$renameFingerprint$1(this, fp, newName, null),
                3);
    }

    public final String toString() {
        return "userId: "
                + this.userId
                + "\nenrolledFingerprints: "
                + this._enrolledFingerprints.getValue()
                + "\n";
    }
}
