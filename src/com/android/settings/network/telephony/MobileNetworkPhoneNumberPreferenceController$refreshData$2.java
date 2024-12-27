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

import kotlinx.coroutines.CoroutineScope;

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
public final class MobileNetworkPhoneNumberPreferenceController$refreshData$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ SubscriptionInfo $subscriptionInfo;
    int label;
    final /* synthetic */ MobileNetworkPhoneNumberPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkPhoneNumberPreferenceController$refreshData$2(
            MobileNetworkPhoneNumberPreferenceController
                    mobileNetworkPhoneNumberPreferenceController,
            SubscriptionInfo subscriptionInfo,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileNetworkPhoneNumberPreferenceController;
        this.$subscriptionInfo = subscriptionInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileNetworkPhoneNumberPreferenceController$refreshData$2(
                this.this$0, this.$subscriptionInfo, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MobileNetworkPhoneNumberPreferenceController$refreshData$2
                mobileNetworkPhoneNumberPreferenceController$refreshData$2 =
                        (MobileNetworkPhoneNumberPreferenceController$refreshData$2)
                                create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mobileNetworkPhoneNumberPreferenceController$refreshData$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        String formattedPhoneNumber;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        MobileNetworkPhoneNumberPreferenceController mobileNetworkPhoneNumberPreferenceController =
                this.this$0;
        formattedPhoneNumber =
                mobileNetworkPhoneNumberPreferenceController.getFormattedPhoneNumber(
                        this.$subscriptionInfo);
        mobileNetworkPhoneNumberPreferenceController.phoneNumber = formattedPhoneNumber;
        return Unit.INSTANCE;
    }
}
