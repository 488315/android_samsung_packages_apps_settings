package com.android.settings.network.telephony;

import android.content.Context;
import android.telephony.SubscriptionManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.android.settings.network.apn.ApnSettings$$ExternalSyntheticLambda0;
import com.android.settingslib.spa.framework.util.FlowsKt;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SubscriptionRepository {
    public final Context context;
    public final SubscriptionManager subscriptionManager;

    public SubscriptionRepository(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.subscriptionManager = SubscriptionRepositoryKt.requireSubscriptionManager(context);
    }

    public final void collectSubscriptionEnabled(
            int i,
            LifecycleOwner lifecycleOwner,
            ApnSettings$$ExternalSyntheticLambda0 apnSettings$$ExternalSyntheticLambda0) {
        Intrinsics.checkNotNullParameter(lifecycleOwner, "lifecycleOwner");
        FlowsKt.collectLatestWithLifecycle(
                isSubscriptionEnabledFlow(i),
                lifecycleOwner,
                Lifecycle.State.STARTED,
                new SubscriptionRepository$collectSubscriptionEnabled$1(
                        2,
                        apnSettings$$ExternalSyntheticLambda0,
                        Intrinsics.Kotlin.class,
                        "suspendConversion0",
                        "collectSubscriptionEnabled$suspendConversion0(Lkotlin/jvm/functions/Function1;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;",
                        0));
    }

    public final Flow isSubscriptionEnabledFlow(final int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
        }
        final Flow subscriptionsChangedFlow =
                SubscriptionRepositoryKt.subscriptionsChangedFlow(this.context);
        return FlowKt.flowOn(
                FlowKt.onEach(
                        FlowKt.buffer$default(
                                new Flow() { // from class:
                                    // com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1

                                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                    /* renamed from: com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1$2, reason: invalid class name */
                                    public final class AnonymousClass2 implements FlowCollector {
                                        public final /* synthetic */ int $subId$inlined;
                                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                        public final /* synthetic */ SubscriptionRepository this$0;

                                        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                        @Metadata(
                                                k = 3,
                                                mv = {1, 9, 0},
                                                xi = 48)
                                        /* renamed from: com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1$2$1, reason: invalid class name */
                                        public final class AnonymousClass1
                                                extends ContinuationImpl {
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

                                        public AnonymousClass2(
                                                FlowCollector flowCollector,
                                                SubscriptionRepository subscriptionRepository,
                                                int i) {
                                            this.$this_unsafeFlow = flowCollector;
                                            this.this$0 = subscriptionRepository;
                                            this.$subId$inlined = i;
                                        }

                                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                        /*
                                            Code decompiled incorrectly, please refer to instructions dump.
                                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                                        */
                                        public final java.lang.Object emit(
                                                java.lang.Object r5,
                                                kotlin.coroutines.Continuation r6) {
                                            /*
                                                r4 = this;
                                                boolean r0 = r6 instanceof com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                                if (r0 == 0) goto L13
                                                r0 = r6
                                                com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1$2$1 r0 = (com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                                int r1 = r0.label
                                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                                r3 = r1 & r2
                                                if (r3 == 0) goto L13
                                                int r1 = r1 - r2
                                                r0.label = r1
                                                goto L18
                                            L13:
                                                com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1$2$1 r0 = new com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1$2$1
                                                r0.<init>(r6)
                                            L18:
                                                java.lang.Object r6 = r0.result
                                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                                int r2 = r0.label
                                                r3 = 1
                                                if (r2 == 0) goto L2f
                                                if (r2 != r3) goto L27
                                                kotlin.ResultKt.throwOnFailure(r6)
                                                goto L4d
                                            L27:
                                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                                r4.<init>(r5)
                                                throw r4
                                            L2f:
                                                kotlin.ResultKt.throwOnFailure(r6)
                                                kotlin.Unit r5 = (kotlin.Unit) r5
                                                com.android.settings.network.telephony.SubscriptionRepository r5 = r4.this$0
                                                android.telephony.SubscriptionManager r5 = r5.subscriptionManager
                                                int r6 = r4.$subId$inlined
                                                boolean r5 = r5.isSubscriptionEnabled(r6)
                                                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                                                r0.label = r3
                                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                                java.lang.Object r4 = r4.emit(r5, r0)
                                                if (r4 != r1) goto L4d
                                                return r1
                                            L4d:
                                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                                return r4
                                            */
                                            throw new UnsupportedOperationException(
                                                    "Method not decompiled:"
                                                        + " com.android.settings.network.telephony.SubscriptionRepository$isSubscriptionEnabledFlow$$inlined$map$1.AnonymousClass2.emit(java.lang.Object,"
                                                        + " kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(
                                            FlowCollector flowCollector,
                                            Continuation continuation) {
                                        Object collect =
                                                Flow.this.collect(
                                                        new AnonymousClass2(flowCollector, this, i),
                                                        continuation);
                                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED
                                                ? collect
                                                : Unit.INSTANCE;
                                    }
                                },
                                -1),
                        new SubscriptionRepository$isSubscriptionEnabledFlow$2(i, null)),
                Dispatchers.Default);
    }
}
