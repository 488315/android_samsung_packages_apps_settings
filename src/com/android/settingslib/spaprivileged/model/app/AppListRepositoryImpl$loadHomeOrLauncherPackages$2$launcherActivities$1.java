package com.android.settingslib.spaprivileged.model.app;

import android.content.Intent;
import android.content.pm.PackageManager;

import com.samsung.android.knox.net.apn.ApnSettings;

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
            "\u0000\u0014\n"
                + "\u0000\n"
                + "\u0002\u0010!\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a$\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00020\u0002 \u0003*\u0010\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00020\u00020\u00040\u0001*\u00020\u0005H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ResolveInfo;",
            "kotlin.jvm.PlatformType",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$loadHomeOrLauncherPackages$2$launcherActivities$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ PackageManager.ResolveInfoFlags $flags;
    final /* synthetic */ Intent $launchIntent;
    final /* synthetic */ int $userId;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$loadHomeOrLauncherPackages$2$launcherActivities$1(
            AppListRepositoryImpl appListRepositoryImpl,
            Intent intent,
            PackageManager.ResolveInfoFlags resolveInfoFlags,
            int i,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
        this.$launchIntent = intent;
        this.$flags = resolveInfoFlags;
        this.$userId = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppListRepositoryImpl$loadHomeOrLauncherPackages$2$launcherActivities$1(
                this.this$0, this.$launchIntent, this.$flags, this.$userId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$loadHomeOrLauncherPackages$2$launcherActivities$1)
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
        return this.this$0.packageManager.queryIntentActivitiesAsUser(
                this.$launchIntent, this.$flags, this.$userId);
    }
}
