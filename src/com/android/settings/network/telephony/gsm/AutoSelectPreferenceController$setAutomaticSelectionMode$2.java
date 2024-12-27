package com.android.settings.network.telephony.gsm;

import android.telephony.TelephonyManager;

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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;

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
public final class AutoSelectPreferenceController$setAutomaticSelectionMode$2 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AutoSelectPreferenceController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AutoSelectPreferenceController$setAutomaticSelectionMode$2(
            AutoSelectPreferenceController autoSelectPreferenceController,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = autoSelectPreferenceController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AutoSelectPreferenceController$setAutomaticSelectionMode$2
                autoSelectPreferenceController$setAutomaticSelectionMode$2 =
                        new AutoSelectPreferenceController$setAutomaticSelectionMode$2(
                                this.this$0, continuation);
        autoSelectPreferenceController$setAutomaticSelectionMode$2.L$0 = obj;
        return autoSelectPreferenceController$setAutomaticSelectionMode$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AutoSelectPreferenceController$setAutomaticSelectionMode$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        TelephonyManager telephonyManager;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DeferredCoroutine async$default =
                    BuildersKt.async$default(
                            (CoroutineScope) this.L$0,
                            new AutoSelectPreferenceController$setAutomaticSelectionMode$2$minimumDialogTimeDeferred$1(
                                    2, null));
            telephonyManager = this.this$0.telephonyManager;
            if (telephonyManager == null) {
                Intrinsics.throwUninitializedPropertyAccessException("telephonyManager");
                throw null;
            }
            telephonyManager.setNetworkSelectionModeAutomatic();
            this.label = 1;
            if (async$default.awaitInternal(this) == coroutineSingletons) {
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
