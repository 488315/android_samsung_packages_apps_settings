package com.android.settings.network.policy;

import android.net.NetworkPolicy;
import android.net.NetworkTemplate;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Landroid/net/NetworkPolicy;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkPolicyRepository$networkPolicyFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ NetworkTemplate $networkTemplate;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NetworkPolicyRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkPolicyRepository$networkPolicyFlow$1(
            NetworkPolicyRepository networkPolicyRepository,
            NetworkTemplate networkTemplate,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = networkPolicyRepository;
        this.$networkTemplate = networkTemplate;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NetworkPolicyRepository$networkPolicyFlow$1 networkPolicyRepository$networkPolicyFlow$1 =
                new NetworkPolicyRepository$networkPolicyFlow$1(
                        this.this$0, this.$networkTemplate, continuation);
        networkPolicyRepository$networkPolicyFlow$1.L$0 = obj;
        return networkPolicyRepository$networkPolicyFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NetworkPolicyRepository$networkPolicyFlow$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        NetworkPolicy networkPolicy;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            NetworkPolicyRepository networkPolicyRepository = this.this$0;
            NetworkTemplate networkTemplate = this.$networkTemplate;
            networkPolicyRepository.getClass();
            Intrinsics.checkNotNullParameter(networkTemplate, "networkTemplate");
            NetworkPolicy[] networkPolicies =
                    networkPolicyRepository.networkPolicyManager.getNetworkPolicies();
            Intrinsics.checkNotNullExpressionValue(networkPolicies, "getNetworkPolicies(...)");
            int length = networkPolicies.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    networkPolicy = null;
                    break;
                }
                networkPolicy = networkPolicies[i2];
                if (Intrinsics.areEqual(networkPolicy.template, networkTemplate)) {
                    break;
                }
                i2++;
            }
            this.label = 1;
            if (flowCollector.emit(networkPolicy, this) == coroutineSingletons) {
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
