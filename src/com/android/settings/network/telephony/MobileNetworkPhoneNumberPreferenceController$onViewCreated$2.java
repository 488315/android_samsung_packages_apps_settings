package com.android.settings.network.telephony;

import android.telephony.SubscriptionInfo;

import androidx.lifecycle.LifecycleCoroutineScope;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", "Landroid/telephony/SubscriptionInfo;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkPhoneNumberPreferenceController$onViewCreated$2
        extends SuspendLambda implements Function2 {
    final /* synthetic */ LifecycleCoroutineScope $coroutineScope;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileNetworkPhoneNumberPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkPhoneNumberPreferenceController$onViewCreated$2(
            LifecycleCoroutineScope lifecycleCoroutineScope,
            MobileNetworkPhoneNumberPreferenceController
                    mobileNetworkPhoneNumberPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.$coroutineScope = lifecycleCoroutineScope;
        this.this$0 = mobileNetworkPhoneNumberPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileNetworkPhoneNumberPreferenceController$onViewCreated$2
                mobileNetworkPhoneNumberPreferenceController$onViewCreated$2 =
                        new MobileNetworkPhoneNumberPreferenceController$onViewCreated$2(
                                this.$coroutineScope, this.this$0, continuation);
        mobileNetworkPhoneNumberPreferenceController$onViewCreated$2.L$0 = obj;
        return mobileNetworkPhoneNumberPreferenceController$onViewCreated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MobileNetworkPhoneNumberPreferenceController$onViewCreated$2
                mobileNetworkPhoneNumberPreferenceController$onViewCreated$2 =
                        (MobileNetworkPhoneNumberPreferenceController$onViewCreated$2)
                                create((SubscriptionInfo) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mobileNetworkPhoneNumberPreferenceController$onViewCreated$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) this.L$0;
        if (subscriptionInfo != null) {
            BuildersKt.launch$default(
                    this.$coroutineScope,
                    null,
                    null,
                    new MobileNetworkPhoneNumberPreferenceController$onViewCreated$2$1$1(
                            this.this$0, subscriptionInfo, null),
                    3);
        }
        return Unit.INSTANCE;
    }
}
