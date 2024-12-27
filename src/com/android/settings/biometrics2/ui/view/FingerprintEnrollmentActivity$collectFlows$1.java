package com.android.settings.biometrics2.ui.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResult;

import com.android.settings.biometrics2.ui.viewmodel.FingerprintEnrollErrorDialogViewModel;
import com.android.settings.biometrics2.ui.viewmodel.FingerprintErrorDialogSetResultAction;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

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
/* loaded from: classes2.dex */
final class FingerprintEnrollmentActivity$collectFlows$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ FingerprintEnrollmentActivity this$0;

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollmentActivity this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$1$1, reason: invalid class name and collision with other inner class name */
        public final class C00351 implements FlowCollector {
            public final /* synthetic */ int $r8$classId;
            public final /* synthetic */ FingerprintEnrollmentActivity this$0;

            public /* synthetic */ C00351(
                    FingerprintEnrollmentActivity fingerprintEnrollmentActivity, int i) {
                this.$r8$classId = i;
                this.this$0 = fingerprintEnrollmentActivity;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                Unit unit = Unit.INSTANCE;
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                switch (this.$r8$classId) {
                    case 0:
                        ActivityResult activityResult = (ActivityResult) obj;
                        Log.d(
                                "FingerprintEnrollmentActivity",
                                "setResultLiveData(" + activityResult + ")");
                        int i = FingerprintEnrollmentActivity.$r8$clinit;
                        fingerprintEnrollmentActivity.onSetActivityResult(activityResult);
                        break;
                    case 1:
                        Log.d(
                                "FingerprintEnrollmentActivity",
                                "generateChallengeFailedFlow("
                                        + ((Boolean) obj).booleanValue()
                                        + ")");
                        ActivityResult activityResult2 = new ActivityResult(0, null);
                        int i2 = FingerprintEnrollmentActivity.$r8$clinit;
                        fingerprintEnrollmentActivity.onSetActivityResult(activityResult2);
                        break;
                    case 2:
                        int intValue = ((Number) obj).intValue();
                        Log.d(
                                "FingerprintEnrollmentActivity",
                                "newErrorDialogFlow(" + intValue + ")");
                        FingerprintEnrollErrorDialog fingerprintEnrollErrorDialog =
                                new FingerprintEnrollErrorDialog();
                        Bundle bundle = new Bundle();
                        bundle.putInt("error_msg_id", intValue);
                        fingerprintEnrollErrorDialog.setArguments(bundle);
                        fingerprintEnrollErrorDialog.show(
                                fingerprintEnrollmentActivity.getSupportFragmentManager(),
                                "error-dialog");
                        break;
                    default:
                        FingerprintErrorDialogSetResultAction
                                fingerprintErrorDialogSetResultAction =
                                        (FingerprintErrorDialogSetResultAction) obj;
                        Log.d(
                                "FingerprintEnrollmentActivity",
                                "errorDialogSetResultFlow("
                                        + fingerprintErrorDialogSetResultAction
                                        + ")");
                        int ordinal = fingerprintErrorDialogSetResultAction.ordinal();
                        if (ordinal == 0) {
                            ActivityResult activityResult3 = new ActivityResult(1, null);
                            int i3 = FingerprintEnrollmentActivity.$r8$clinit;
                            fingerprintEnrollmentActivity.onSetActivityResult(activityResult3);
                            break;
                        } else if (ordinal == 1) {
                            ActivityResult activityResult4 = new ActivityResult(3, null);
                            int i4 = FingerprintEnrollmentActivity.$r8$clinit;
                            fingerprintEnrollmentActivity.onSetActivityResult(activityResult4);
                            break;
                        }
                        break;
                }
                return unit;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                int i2 = FingerprintEnrollmentActivity.$r8$clinit;
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                fingerprintEnrollmentActivity.getViewModel()._setResultFlow);
                C00351 c00351 = new C00351(this.this$0, 0);
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(c00351, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollmentActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            int i = 1;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity = this.this$0;
                int i3 = FingerprintEnrollmentActivity.$r8$clinit;
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                fingerprintEnrollmentActivity.getAutoCredentialViewModel()
                                        ._generateChallengeFailedFlow);
                AnonymousClass1.C00351 c00351 = new AnonymousClass1.C00351(this.this$0, i);
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(c00351, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollmentActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                ((FingerprintEnrollErrorDialogViewModel)
                                                this.this$0.errorDialogViewModel$delegate
                                                        .getValue())
                                        ._newDialogFlow);
                AnonymousClass1.C00351 c00351 = new AnonymousClass1.C00351(this.this$0, 2);
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(c00351, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

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
    /* renamed from: com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ FingerprintEnrollmentActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(
                FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = fingerprintEnrollmentActivity;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
            return CoroutineSingletons.COROUTINE_SUSPENDED;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ReadonlySharedFlow asSharedFlow =
                        FlowKt.asSharedFlow(
                                ((FingerprintEnrollErrorDialogViewModel)
                                                this.this$0.errorDialogViewModel$delegate
                                                        .getValue())
                                        ._setResultFlow);
                AnonymousClass1.C00351 c00351 = new AnonymousClass1.C00351(this.this$0, 3);
                this.label = 1;
                if (asSharedFlow.$$delegate_0.collect(c00351, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FingerprintEnrollmentActivity$collectFlows$1(
            FingerprintEnrollmentActivity fingerprintEnrollmentActivity,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = fingerprintEnrollmentActivity;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FingerprintEnrollmentActivity$collectFlows$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FingerprintEnrollmentActivity$collectFlows$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x006f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x005f A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            r6 = 0
            androidx.lifecycle.Lifecycle$State r7 = androidx.lifecycle.Lifecycle.State.STARTED
            if (r1 == 0) goto L2d
            if (r1 == r5) goto L29
            if (r1 == r4) goto L25
            if (r1 == r3) goto L21
            if (r1 != r2) goto L19
            kotlin.ResultKt.throwOnFailure(r9)
            goto L70
        L19:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L21:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L60
        L25:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L50
        L29:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L40
        L2d:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity r9 = r8.this$0
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$1 r1 = new com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$1
            r1.<init>(r9, r6)
            r8.label = r5
            java.lang.Object r9 = androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r9, r7, r1, r8)
            if (r9 != r0) goto L40
            return r0
        L40:
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity r9 = r8.this$0
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$2 r1 = new com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$2
            r1.<init>(r9, r6)
            r8.label = r4
            java.lang.Object r9 = androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r9, r7, r1, r8)
            if (r9 != r0) goto L50
            return r0
        L50:
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity r9 = r8.this$0
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$3 r1 = new com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$3
            r1.<init>(r9, r6)
            r8.label = r3
            java.lang.Object r9 = androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r9, r7, r1, r8)
            if (r9 != r0) goto L60
            return r0
        L60:
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity r9 = r8.this$0
            com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$4 r1 = new com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1$4
            r1.<init>(r9, r6)
            r8.label = r2
            java.lang.Object r8 = androidx.lifecycle.RepeatOnLifecycleKt.repeatOnLifecycle(r9, r7, r1, r8)
            if (r8 != r0) goto L70
            return r0
        L70:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.biometrics2.ui.view.FingerprintEnrollmentActivity$collectFlows$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
