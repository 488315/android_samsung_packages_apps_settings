package com.android.settings.wifi;

import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;

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
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\n"
                + " \u0002*\u0004\u0018\u00010\u00010\u00012\u0006\u0010\u0003\u001a\u00020\u00012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            "wifiStatusSummary",
            "wifiEntry",
            "Lcom/android/wifitrackerlib/WifiEntry;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class WifiSummaryRepository$summaryFlow$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        WifiSummaryRepository$summaryFlow$1 wifiSummaryRepository$summaryFlow$1 =
                new WifiSummaryRepository$summaryFlow$1(3, (Continuation) obj3);
        wifiSummaryRepository$summaryFlow$1.L$0 = (String) obj;
        wifiSummaryRepository$summaryFlow$1.L$1 = (WifiEntry) obj2;
        return wifiSummaryRepository$summaryFlow$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        String str = (String) this.L$0;
        WifiEntry wifiEntry = (WifiEntry) this.L$1;
        return wifiEntry instanceof HotspotNetworkEntry
                ? ((HotspotNetworkEntry) wifiEntry).getAlternateSummary()
                : str;
    }
}
