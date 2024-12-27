package com.android.settings.spa.app.appinfo;

import android.app.ActivityManager;
import android.app.Flags;
import android.content.Context;
import android.util.Log;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

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
final class PackageInfoPresenter$forceStop$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ PackageInfoPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackageInfoPresenter$forceStop$1(
            PackageInfoPresenter packageInfoPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = packageInfoPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PackageInfoPresenter$forceStop$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        PackageInfoPresenter$forceStop$1 packageInfoPresenter$forceStop$1 =
                (PackageInfoPresenter$forceStop$1)
                        create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        packageInfoPresenter$forceStop$1.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Log.d("PackageInfoPresenter", "Stopping package " + this.this$0.packageName);
        if (Flags.appRestrictionsApi()) {
            int packageUid =
                    this.this$0.getUserPackageManager().getPackageUid(this.this$0.packageName, 0);
            Context context = this.this$0.context;
            Intrinsics.checkNotNullParameter(context, "<this>");
            Object systemService = context.getSystemService((Class<Object>) ActivityManager.class);
            Intrinsics.checkNotNull(systemService);
            ((ActivityManager) systemService)
                    .noteAppRestrictionEnabled(
                            this.this$0.packageName, packageUid, 60, true, 4, "settings", 1, 0L);
        }
        Context context2 = this.this$0.context;
        Intrinsics.checkNotNullParameter(context2, "<this>");
        Object systemService2 = context2.getSystemService((Class<Object>) ActivityManager.class);
        Intrinsics.checkNotNull(systemService2);
        PackageInfoPresenter packageInfoPresenter = this.this$0;
        ((ActivityManager) systemService2)
                .forceStopPackageAsUser(
                        packageInfoPresenter.packageName, packageInfoPresenter.userId);
        return Unit.INSTANCE;
    }
}
