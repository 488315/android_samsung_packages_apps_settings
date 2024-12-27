package com.android.settings.deviceinfo.simstatus;

import android.os.PersistableBundle;

import com.android.settings.network.telephony.CarrierConfigManagerExtKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Lcom/android/settings/deviceinfo/simstatus/SimStatusDialogRepository$SimStatusDialogVisibility;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class SimStatusDialogRepository$showUpFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $subId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SimStatusDialogRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimStatusDialogRepository$showUpFlow$1(
            int i, SimStatusDialogRepository simStatusDialogRepository, Continuation continuation) {
        super(2, continuation);
        this.this$0 = simStatusDialogRepository;
        this.$subId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SimStatusDialogRepository$showUpFlow$1 simStatusDialogRepository$showUpFlow$1 =
                new SimStatusDialogRepository$showUpFlow$1(this.$subId, this.this$0, continuation);
        simStatusDialogRepository$showUpFlow$1.L$0 = obj;
        return simStatusDialogRepository$showUpFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimStatusDialogRepository$showUpFlow$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            PersistableBundle safeGetConfig =
                    CarrierConfigManagerExtKt.safeGetConfig(
                            this.this$0.carrierConfigManager,
                            CollectionsKt__CollectionsKt.listOf(
                                    (Object[])
                                            new String[] {
                                                "show_signal_strength_in_sim_status_bool",
                                                "show_ims_registration_status_bool"
                                            }),
                            this.$subId);
            SimStatusDialogRepository.SimStatusDialogVisibility simStatusDialogVisibility =
                    new SimStatusDialogRepository.SimStatusDialogVisibility(
                            safeGetConfig.getBoolean(
                                    "show_signal_strength_in_sim_status_bool", true),
                            safeGetConfig.getBoolean("show_ims_registration_status_bool"));
            this.label = 1;
            if (flowCollector.emit(simStatusDialogVisibility, this) == coroutineSingletons) {
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
