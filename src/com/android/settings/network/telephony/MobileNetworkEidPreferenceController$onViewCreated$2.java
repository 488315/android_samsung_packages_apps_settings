package com.android.settings.network.telephony;

import android.telephony.SubscriptionInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "subscriptionInfo",
            "Landroid/telephony/SubscriptionInfo;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkEidPreferenceController$onViewCreated$2 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileNetworkEidPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkEidPreferenceController$onViewCreated$2(
            MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileNetworkEidPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileNetworkEidPreferenceController$onViewCreated$2
                mobileNetworkEidPreferenceController$onViewCreated$2 =
                        new MobileNetworkEidPreferenceController$onViewCreated$2(
                                this.this$0, continuation);
        mobileNetworkEidPreferenceController$onViewCreated$2.L$0 = obj;
        return mobileNetworkEidPreferenceController$onViewCreated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MobileNetworkEidPreferenceController$onViewCreated$2
                mobileNetworkEidPreferenceController$onViewCreated$2 =
                        (MobileNetworkEidPreferenceController$onViewCreated$2)
                                create((SubscriptionInfo) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mobileNetworkEidPreferenceController$onViewCreated$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineScope coroutineScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) this.L$0;
        if (subscriptionInfo != null) {
            MobileNetworkEidPreferenceController mobileNetworkEidPreferenceController = this.this$0;
            coroutineScope = mobileNetworkEidPreferenceController.coroutineScope;
            if (coroutineScope != null) {
                BuildersKt.launch$default(
                        coroutineScope,
                        null,
                        null,
                        new MobileNetworkEidPreferenceController$onViewCreated$2$1$1(
                                mobileNetworkEidPreferenceController, subscriptionInfo, null),
                        3);
            }
        }
        return Unit.INSTANCE;
    }
}
