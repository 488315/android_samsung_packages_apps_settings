package com.android.settings.network;

import android.content.Context;

import com.android.settings.wifi.WifiPickerTrackerHelper;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

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
final class SimOnboardingService$startSetupPrimarySim$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ WifiPickerTrackerHelper $wifiPickerTrackerHelper;
    int label;
    final /* synthetic */ SimOnboardingService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimOnboardingService$startSetupPrimarySim$2(
            SimOnboardingService simOnboardingService,
            Context context,
            WifiPickerTrackerHelper wifiPickerTrackerHelper,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = simOnboardingService;
        this.$context = context;
        this.$wifiPickerTrackerHelper = wifiPickerTrackerHelper;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SimOnboardingService$startSetupPrimarySim$2(
                this.this$0, this.$context, this.$wifiPickerTrackerHelper, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SimOnboardingService$startSetupPrimarySim$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0056 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0069  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            Method dump skipped, instructions count: 260
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.network.SimOnboardingService$startSetupPrimarySim$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
