package com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education;

import com.android.settings.biometrics.fingerprint.FingerprintErrorDialog;
import com.android.settings.biometrics.fingerprint2.ui.enrollment.viewmodel.FingerprintEnrollFindSensorViewModel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

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
final class SfpsEnrollFindSensorFragment$onCreateView$4 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SfpsEnrollFindSensorFragment this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SfpsEnrollFindSensorFragment$onCreateView$4(
            SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sfpsEnrollFindSensorFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SfpsEnrollFindSensorFragment$onCreateView$4(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SfpsEnrollFindSensorFragment$onCreateView$4)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FingerprintEnrollFindSensorViewModel access$getViewModel =
                    SfpsEnrollFindSensorFragment.access$getViewModel(this.this$0);
            final SfpsEnrollFindSensorFragment sfpsEnrollFindSensorFragment = this.this$0;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.fragment.education.SfpsEnrollFindSensorFragment$onCreateView$4.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Pair pair = (Pair) obj2;
                            FingerprintErrorDialog.showErrorDialog(
                                    SfpsEnrollFindSensorFragment.this.requireActivity(),
                                    ((Number) pair.getFirst()).intValue(),
                                    ((Boolean) pair.getSecond()).booleanValue());
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (access$getViewModel.showErrorDialog.collect(flowCollector, this)
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
