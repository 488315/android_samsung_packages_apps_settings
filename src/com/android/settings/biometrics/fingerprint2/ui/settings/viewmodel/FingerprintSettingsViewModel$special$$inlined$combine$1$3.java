package com.android.settings.biometrics.fingerprint2.ui.settings.viewmodel;

import com.android.settings.biometrics.fingerprint2.lib.model.FingerprintAuthAttemptModel;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

import kotlinx.coroutines.flow.FlowCollector;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u0011\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\u0010\u0006\u001a\u00020\u0005\"\u0006\b\u0000\u0010\u0000\u0018\u0001\"\u0004\b\u0001\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00010\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u008a@¢\u0006\u0004\b\u0006\u0010\u0007"
        },
        d2 = {
            "T",
            "R",
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "it",
            ApnSettings.MVNO_NONE,
            "<anonymous>",
            "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/Array;)V"
        },
        k = 3,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class FingerprintSettingsViewModel$special$$inlined$combine$1$3 extends SuspendLambda
        implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        FingerprintSettingsViewModel$special$$inlined$combine$1$3
                fingerprintSettingsViewModel$special$$inlined$combine$1$3 =
                        new FingerprintSettingsViewModel$special$$inlined$combine$1$3(
                                3, (Continuation) obj3);
        fingerprintSettingsViewModel$special$$inlined$combine$1$3.L$0 = (FlowCollector) obj;
        fingerprintSettingsViewModel$special$$inlined$combine$1$3.L$1 = (Object[]) obj2;
        return fingerprintSettingsViewModel$special$$inlined$combine$1$3.invokeSuspend(
                Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Boolean bool;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Object[] objArr = (Object[]) this.L$1;
            boolean z = false;
            Object obj2 = objArr[0];
            Object obj3 = objArr[1];
            Object obj4 = objArr[2];
            Object obj5 = objArr[3];
            Object obj6 = objArr[4];
            Object obj7 = objArr[5];
            Object obj8 = objArr[6];
            boolean booleanValue = ((Boolean) objArr[7]).booleanValue();
            FingerprintSensorType fingerprintSensorType = (FingerprintSensorType) obj8;
            int intValue = ((Number) obj7).intValue();
            FingerprintAuthAttemptModel.Error error = (FingerprintAuthAttemptModel.Error) obj6;
            List list = (List) obj5;
            boolean booleanValue2 = ((Boolean) obj4).booleanValue();
            NextStepViewModel nextStepViewModel = (NextStepViewModel) obj3;
            PreferenceViewModel preferenceViewModel = (PreferenceViewModel) obj2;
            if (booleanValue) {
                bool = Boolean.FALSE;
            } else if (CollectionsKt__CollectionsKt.listOf(
                            (Object[])
                                    new FingerprintSensorType[] {
                                        FingerprintSensorType.UDFPS_ULTRASONIC,
                                        FingerprintSensorType.UDFPS_OPTICAL
                                    })
                    .contains(fingerprintSensorType)) {
                bool = Boolean.FALSE;
            } else if (nextStepViewModel == null
                    || !(nextStepViewModel instanceof ShowSettings)
                    || list == null
                    || !(!list.isEmpty())) {
                bool = Boolean.FALSE;
            } else {
                if (preferenceViewModel == null
                        && error == null
                        && booleanValue2
                        && intValue < 15) {
                    z = true;
                }
                bool = Boolean.valueOf(z);
            }
            this.label = 1;
            if (flowCollector.emit(bool, this) == coroutineSingletons) {
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
