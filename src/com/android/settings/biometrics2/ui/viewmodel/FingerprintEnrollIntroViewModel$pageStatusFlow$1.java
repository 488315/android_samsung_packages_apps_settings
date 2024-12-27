package com.android.settings.biometrics2.ui.viewmodel;

import com.android.settings.biometrics2.ui.model.FingerprintEnrollIntroStatus;
import com.android.settings.biometrics2.ui.model.FingerprintEnrollable;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0012\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/biometrics2/ui/model/FingerprintEnrollIntroStatus;",
            "hasScrolledToBottom",
            ApnSettings.MVNO_NONE,
            "enrollableStatus",
            "Lcom/android/settings/biometrics2/ui/model/FingerprintEnrollable;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class FingerprintEnrollIntroViewModel$pageStatusFlow$1 extends SuspendLambda
        implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        FingerprintEnrollIntroViewModel$pageStatusFlow$1
                fingerprintEnrollIntroViewModel$pageStatusFlow$1 =
                        new FingerprintEnrollIntroViewModel$pageStatusFlow$1(
                                3, (Continuation) obj3);
        fingerprintEnrollIntroViewModel$pageStatusFlow$1.Z$0 = booleanValue;
        fingerprintEnrollIntroViewModel$pageStatusFlow$1.L$0 = (FingerprintEnrollable) obj2;
        return fingerprintEnrollIntroViewModel$pageStatusFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new FingerprintEnrollIntroStatus(this.Z$0, (FingerprintEnrollable) this.L$0);
    }
}
