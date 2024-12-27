package com.android.settings.spa.network;

import android.app.Activity;
import android.content.Context;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "isAirplaneModeOn", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $context;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1(
            Context context, Continuation continuation) {
        super(2, continuation);
        this.$context = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1
                networkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1 =
                        new NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1(
                                this.$context, continuation);
        networkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1.Z$0 =
                ((Boolean) obj).booleanValue();
        return networkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1
                networkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1 =
                        (NetworkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1)
                                create(bool, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        networkCellularGroupProviderKt$CollectAirplaneModeAndFinishIfOn$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Activity activity;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.Z$0
                && (activity = SimOnboardingPageProviderKt.getActivity(this.$context)) != null) {
            activity.finish();
        }
        return Unit.INSTANCE;
    }
}
