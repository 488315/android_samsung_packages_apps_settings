package com.android.settings.wifi.repository;

import android.util.Log;

import com.android.wifitrackerlib.WifiEntry;

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
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "it", "Lcom/android/wifitrackerlib/WifiEntry;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class WifiPickerRepository$connectedWifiEntryFlow$2 extends SuspendLambda
        implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiPickerRepository$connectedWifiEntryFlow$2
                wifiPickerRepository$connectedWifiEntryFlow$2 =
                        new WifiPickerRepository$connectedWifiEntryFlow$2(2, continuation);
        wifiPickerRepository$connectedWifiEntryFlow$2.L$0 = obj;
        return wifiPickerRepository$connectedWifiEntryFlow$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        WifiPickerRepository$connectedWifiEntryFlow$2
                wifiPickerRepository$connectedWifiEntryFlow$2 =
                        (WifiPickerRepository$connectedWifiEntryFlow$2)
                                create((WifiEntry) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        wifiPickerRepository$connectedWifiEntryFlow$2.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("WifiPickerRepository", "connectedWifiEntryFlow: " + ((WifiEntry) this.L$0));
        return Unit.INSTANCE;
    }
}
