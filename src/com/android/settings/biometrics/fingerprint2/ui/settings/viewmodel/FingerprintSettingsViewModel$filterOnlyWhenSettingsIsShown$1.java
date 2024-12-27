package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "value",
            "currStep",
            "Lcom/android/settings/biometrics/fingerprint2/ui/settings/viewmodel/NextStepViewModel;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1 extends SuspendLambda
        implements Function4 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1
                fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1 =
                        new FingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1(
                                4, (Continuation) obj4);
        fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1.L$0 = (FlowCollector) obj;
        fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1.L$1 = obj2;
        fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1.L$2 = (NextStepViewModel) obj3;
        return fingerprintSettingsViewModel$filterOnlyWhenSettingsIsShown$1.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Object obj2 = this.L$1;
            NextStepViewModel nextStepViewModel = (NextStepViewModel) this.L$2;
            if (nextStepViewModel != null && (nextStepViewModel instanceof ShowSettings)) {
                this.L$0 = null;
                this.L$1 = null;
                this.label = 1;
                if (flowCollector.emit(obj2, this) == coroutineSingletons) {
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
