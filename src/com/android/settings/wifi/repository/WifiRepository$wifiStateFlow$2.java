package com.android.settings.wifi.repository;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

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
                + "\u0002\u0010\b\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", ApnSettings.MVNO_NONE},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class WifiRepository$wifiStateFlow$2 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepository$wifiStateFlow$2 wifiRepository$wifiStateFlow$2 =
                new WifiRepository$wifiStateFlow$2(2, continuation);
        wifiRepository$wifiStateFlow$2.I$0 = ((Number) obj).intValue();
        return wifiRepository$wifiStateFlow$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        WifiRepository$wifiStateFlow$2 wifiRepository$wifiStateFlow$2 =
                (WifiRepository$wifiStateFlow$2)
                        create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        wifiRepository$wifiStateFlow$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                this.I$0, "wifiStateFlow: ", "WifiRepository");
        return Unit.INSTANCE;
    }
}
