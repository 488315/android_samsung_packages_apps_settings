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
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012*\u0010\u0002\u001a&\u0012\f\u0012\n"
                + " \u0005*\u0004\u0018\u00010\u00040\u0004 \u0005*\u0012\u0012\f\u0012\n"
                + " \u0005*\u0004\u0018\u00010\u00040\u0004\u0018\u00010\u00030\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "subscriptionInfoList",
            ApnSettings.MVNO_NONE,
            "Landroid/telephony/SubscriptionInfo;",
            "kotlin.jvm.PlatformType"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
public final class MobileNetworkImeiPreferenceController$onViewCreated$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ LifecycleCoroutineScope $coroutineScope;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MobileNetworkImeiPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkImeiPreferenceController$onViewCreated$1(
            MobileNetworkImeiPreferenceController mobileNetworkImeiPreferenceController,
            LifecycleCoroutineScope lifecycleCoroutineScope,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileNetworkImeiPreferenceController;
        this.$coroutineScope = lifecycleCoroutineScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileNetworkImeiPreferenceController$onViewCreated$1
                mobileNetworkImeiPreferenceController$onViewCreated$1 =
                        new MobileNetworkImeiPreferenceController$onViewCreated$1(
                                this.this$0, this.$coroutineScope, continuation);
        mobileNetworkImeiPreferenceController$onViewCreated$1.L$0 = obj;
        return mobileNetworkImeiPreferenceController$onViewCreated$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        MobileNetworkImeiPreferenceController$onViewCreated$1
                mobileNetworkImeiPreferenceController$onViewCreated$1 =
                        (MobileNetworkImeiPreferenceController$onViewCreated$1)
                                create((List) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mobileNetworkImeiPreferenceController$onViewCreated$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        Intrinsics.checkNotNull(list);
        MobileNetworkImeiPreferenceController mobileNetworkImeiPreferenceController = this.this$0;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj2 = null;
                break;
            }
            obj2 = it.next();
            if (((SubscriptionInfo) obj2).getSubscriptionId()
                    == mobileNetworkImeiPreferenceController.mSubId) {
                break;
            }
        }
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) obj2;
        if (subscriptionInfo != null) {
            BuildersKt.launch$default(
                    this.$coroutineScope,
                    null,
                    null,
                    new MobileNetworkImeiPreferenceController$onViewCreated$1$2$1(
                            this.this$0, subscriptionInfo, null),
                    3);
        }
        return Unit.INSTANCE;
    }
}
