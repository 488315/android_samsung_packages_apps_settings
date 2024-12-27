package com.android.settings.network;

import com.android.settings.SettingsPreferenceFragment;

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
final class MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ SettingsPreferenceFragment $this_collectAirplaneModeAndFinishIfOn;
    /* synthetic */ boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1(
            SettingsPreferenceFragment settingsPreferenceFragment, Continuation continuation) {
        super(2, continuation);
        this.$this_collectAirplaneModeAndFinishIfOn = settingsPreferenceFragment;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1
                mobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1 =
                        new MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1(
                                this.$this_collectAirplaneModeAndFinishIfOn, continuation);
        mobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1.Z$0 =
                ((Boolean) obj).booleanValue();
        return mobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1
                mobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1 =
                        (MobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1)
                                create(bool, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        mobileNetworkListFragment$Companion$collectAirplaneModeAndFinishIfOn$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (this.Z$0) {
            this.$this_collectAirplaneModeAndFinishIfOn.finish();
        }
        return Unit.INSTANCE;
    }
}
