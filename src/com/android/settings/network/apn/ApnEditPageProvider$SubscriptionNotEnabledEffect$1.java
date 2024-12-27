package com.android.settings.network.apn;

import android.content.Context;

import com.android.settings.network.telephony.SubscriptionRepository;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            com.samsung.android.knox.net.apn.ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class ApnEditPageProvider$SubscriptionNotEnabledEffect$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ NavControllerWrapper $navController;
    final /* synthetic */ int $subId;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ApnEditPageProvider$SubscriptionNotEnabledEffect$1(
            Context context,
            int i,
            NavControllerWrapper navControllerWrapper,
            Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$subId = i;
        this.$navController = navControllerWrapper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ApnEditPageProvider$SubscriptionNotEnabledEffect$1(
                this.$context, this.$subId, this.$navController, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ApnEditPageProvider$SubscriptionNotEnabledEffect$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow isSubscriptionEnabledFlow =
                    new SubscriptionRepository(this.$context)
                            .isSubscriptionEnabledFlow(this.$subId);
            final NavControllerWrapper navControllerWrapper = this.$navController;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settings.network.apn.ApnEditPageProvider$SubscriptionNotEnabledEffect$1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            if (!((Boolean) obj2).booleanValue()) {
                                NavControllerWrapper.this.navigateBack();
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (isSubscriptionEnabledFlow.collect(flowCollector, this) == coroutineSingletons) {
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
