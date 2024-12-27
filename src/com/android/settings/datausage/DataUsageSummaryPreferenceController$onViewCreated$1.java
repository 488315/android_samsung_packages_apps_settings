package com.android.settings.datausage;

import android.net.NetworkPolicy;
import android.telephony.SubscriptionInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "policy", "Landroid/net/NetworkPolicy;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class DataUsageSummaryPreferenceController$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ DataUsageSummaryPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataUsageSummaryPreferenceController$onViewCreated$1(
            DataUsageSummaryPreferenceController dataUsageSummaryPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataUsageSummaryPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataUsageSummaryPreferenceController$onViewCreated$1
                dataUsageSummaryPreferenceController$onViewCreated$1 =
                        new DataUsageSummaryPreferenceController$onViewCreated$1(
                                this.this$0, continuation);
        dataUsageSummaryPreferenceController$onViewCreated$1.L$0 = obj;
        return dataUsageSummaryPreferenceController$onViewCreated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataUsageSummaryPreferenceController$onViewCreated$1)
                        create((NetworkPolicy) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        DataUsageSummaryPreference dataUsageSummaryPreference;
        SubscriptionInfo subInfo;
        Object update;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            NetworkPolicy networkPolicy = (NetworkPolicy) this.L$0;
            dataUsageSummaryPreference = this.this$0.preference;
            if (dataUsageSummaryPreference == null) {
                Intrinsics.throwUninitializedPropertyAccessException("preference");
                throw null;
            }
            subInfo = this.this$0.getSubInfo();
            dataUsageSummaryPreference.setVisible(
                    (subInfo == null || networkPolicy == null) ? false : true);
            if (networkPolicy != null) {
                DataUsageSummaryPreferenceController dataUsageSummaryPreferenceController =
                        this.this$0;
                this.label = 1;
                update = dataUsageSummaryPreferenceController.update(networkPolicy, this);
                if (update == coroutineSingletons) {
                    return coroutineSingletons;
                }
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
