package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.android.settings.fuelgauge.batteryusage.BatteryChartPreferenceController;
import com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\f\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\n"
                + " \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/fuelgauge/batteryusage/BatteryDiffEntry;",
            "kotlin.jvm.PlatformType",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppBatteryPresenter$getBatteryDiffEntry$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AppBatteryPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppBatteryPresenter$getBatteryDiffEntry$2(
            AppBatteryPresenter appBatteryPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appBatteryPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppBatteryPresenter$getBatteryDiffEntry$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppBatteryPresenter$getBatteryDiffEntry$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AppBatteryPresenter appBatteryPresenter = this.this$0;
        Context context = appBatteryPresenter.context;
        ApplicationInfo applicationInfo = appBatteryPresenter.app;
        BatteryDiffEntry appBatteryUsageData =
                BatteryChartPreferenceController.getAppBatteryUsageData(
                        context,
                        ApplicationInfosKt.getUserId(applicationInfo),
                        applicationInfo.packageName);
        Log.d("AppBatteryPresenter", "loadBatteryDiffEntries():\n" + appBatteryUsageData);
        return appBatteryUsageData;
    }
}
