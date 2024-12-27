package com.android.settings.remoteauth.enrolling;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.RepeatOnLifecycleKt;
import androidx.navigation.NavController;

import com.android.settings.R;

import com.google.android.setupcompat.template.FooterButton;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

import java.util.List;

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
final class RemoteAuthEnrollEnrolling$onViewCreated$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ RemoteAuthEnrollEnrolling this$0;

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
    /* renamed from: com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling$onViewCreated$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ RemoteAuthEnrollEnrolling this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                RemoteAuthEnrollEnrolling remoteAuthEnrollEnrolling, Continuation continuation) {
            super(2, continuation);
            this.this$0 = remoteAuthEnrollEnrolling;
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
                RemoteAuthEnrollEnrollingViewModel remoteAuthEnrollEnrollingViewModel =
                        (RemoteAuthEnrollEnrollingViewModel)
                                this.this$0.viewModel$delegate.getValue();
                final RemoteAuthEnrollEnrolling remoteAuthEnrollEnrolling = this.this$0;
                FlowCollector flowCollector =
                        new FlowCollector() { // from class:
                                              // com.android.settings.remoteauth.enrolling.RemoteAuthEnrollEnrolling.onViewCreated.1.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                RemoteAuthEnrollEnrollingUiState remoteAuthEnrollEnrollingUiState =
                                        (RemoteAuthEnrollEnrollingUiState) obj2;
                                RemoteAuthEnrollEnrolling remoteAuthEnrollEnrolling2 =
                                        RemoteAuthEnrollEnrolling.this;
                                ((ProgressBar)
                                                remoteAuthEnrollEnrolling2.progressBar$delegate
                                                        .getValue())
                                        .setVisibility(4);
                                remoteAuthEnrollEnrolling2
                                        .getPrimaryFooterButton()
                                        .setEnabled(false);
                                int ordinal =
                                        remoteAuthEnrollEnrollingUiState.enrollmentUiState
                                                .ordinal();
                                if (ordinal == 0) {
                                    RemoteAuthEnrollEnrollingRecyclerViewAdapter
                                            remoteAuthEnrollEnrollingRecyclerViewAdapter =
                                                    remoteAuthEnrollEnrolling2.adapter;
                                    List value =
                                            remoteAuthEnrollEnrollingUiState
                                                    .discoveredDeviceUiStates;
                                    remoteAuthEnrollEnrollingRecyclerViewAdapter.getClass();
                                    Intrinsics.checkNotNullParameter(value, "value");
                                    remoteAuthEnrollEnrollingRecyclerViewAdapter.uiStates = value;
                                    remoteAuthEnrollEnrollingRecyclerViewAdapter
                                            .notifyDataSetChanged();
                                    FooterButton primaryFooterButton =
                                            remoteAuthEnrollEnrolling2.getPrimaryFooterButton();
                                    RemoteAuthEnrollEnrollingViewModel
                                            remoteAuthEnrollEnrollingViewModel2 =
                                                    (RemoteAuthEnrollEnrollingViewModel)
                                                            remoteAuthEnrollEnrolling2
                                                                    .viewModel$delegate.getValue();
                                    primaryFooterButton.setEnabled(
                                            remoteAuthEnrollEnrollingViewModel2
                                                            .selectedDevice$delegate.getValue(
                                                            remoteAuthEnrollEnrollingViewModel2,
                                                            RemoteAuthEnrollEnrollingViewModel
                                                                    .$$delegatedProperties[0])
                                                    != null);
                                } else if (ordinal == 1) {
                                    ((ProgressBar)
                                                    remoteAuthEnrollEnrolling2.progressBar$delegate
                                                            .getValue())
                                            .setVisibility(0);
                                } else if (ordinal == 3) {
                                    ((NavController)
                                                    remoteAuthEnrollEnrolling2
                                                            .navController$delegate.getValue())
                                            .navigate(R.id.action_enrolling_to_finish);
                                }
                                String str = remoteAuthEnrollEnrollingUiState.errorMsg;
                                if (str != null) {
                                    ((TextView)
                                                    remoteAuthEnrollEnrolling2.errorText$delegate
                                                            .getValue())
                                            .setVisibility(0);
                                    ((TextView)
                                                    remoteAuthEnrollEnrolling2.errorText$delegate
                                                            .getValue())
                                            .setText(str);
                                } else {
                                    ((TextView)
                                                    remoteAuthEnrollEnrolling2.errorText$delegate
                                                            .getValue())
                                            .setVisibility(4);
                                    ((TextView)
                                                    remoteAuthEnrollEnrolling2.errorText$delegate
                                                            .getValue())
                                            .setText(ApnSettings.MVNO_NONE);
                                }
                                return Unit.INSTANCE;
                            }
                        };
                this.label = 1;
                if (remoteAuthEnrollEnrollingViewModel.uiState.$$delegate_0.collect(
                                flowCollector, this)
                        == coroutineSingletons) {
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
    public RemoteAuthEnrollEnrolling$onViewCreated$1(
            RemoteAuthEnrollEnrolling remoteAuthEnrollEnrolling, Continuation continuation) {
        super(2, continuation);
        this.this$0 = remoteAuthEnrollEnrolling;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RemoteAuthEnrollEnrolling$onViewCreated$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RemoteAuthEnrollEnrolling$onViewCreated$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            RemoteAuthEnrollEnrolling remoteAuthEnrollEnrolling = this.this$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(remoteAuthEnrollEnrolling, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(
                            remoteAuthEnrollEnrolling, state, anonymousClass1, this)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
