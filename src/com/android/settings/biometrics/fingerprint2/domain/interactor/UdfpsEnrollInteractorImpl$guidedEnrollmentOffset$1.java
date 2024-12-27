package com.android.settings.biometrics.fingerprint2.domain.interactor;

import android.graphics.PointF;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Landroid/graphics/PointF;",
            "point",
            "accessibilityEnabled",
            ApnSettings.MVNO_NONE,
            "guidedEnrollmentEnabled"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class UdfpsEnrollInteractorImpl$guidedEnrollmentOffset$1 extends SuspendLambda
        implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        UdfpsEnrollInteractorImpl$guidedEnrollmentOffset$1
                udfpsEnrollInteractorImpl$guidedEnrollmentOffset$1 =
                        new UdfpsEnrollInteractorImpl$guidedEnrollmentOffset$1(
                                4, (Continuation) obj4);
        udfpsEnrollInteractorImpl$guidedEnrollmentOffset$1.L$0 = (PointF) obj;
        udfpsEnrollInteractorImpl$guidedEnrollmentOffset$1.Z$0 = booleanValue;
        udfpsEnrollInteractorImpl$guidedEnrollmentOffset$1.Z$1 = booleanValue2;
        return udfpsEnrollInteractorImpl$guidedEnrollmentOffset$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PointF pointF = (PointF) this.L$0;
        return (this.Z$0 || !this.Z$1)
                ? new PointF(0.0f, 0.0f)
                : new PointF(pointF.x * 0.5f, pointF.y * 0.5f);
    }
}
