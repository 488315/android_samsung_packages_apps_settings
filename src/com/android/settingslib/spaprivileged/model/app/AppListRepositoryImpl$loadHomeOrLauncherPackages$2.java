package com.android.settingslib.spaprivileged.model.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\"\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0010\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00020\u00020\u0001*\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$loadHomeOrLauncherPackages$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ PackageManager.ResolveInfoFlags $flags;
    final /* synthetic */ Intent $launchIntent;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$loadHomeOrLauncherPackages$2(
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
        AppListRepositoryImpl$loadHomeOrLauncherPackages$2
                appListRepositoryImpl$loadHomeOrLauncherPackages$2 =
                        new AppListRepositoryImpl$loadHomeOrLauncherPackages$2(
                                this.this$0,
                                this.$launchIntent,
                                this.$flags,
                                this.$userId,
                                continuation);
        appListRepositoryImpl$loadHomeOrLauncherPackages$2.L$0 = obj;
        return appListRepositoryImpl$loadHomeOrLauncherPackages$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$loadHomeOrLauncherPackages$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        ArrayList arrayList;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            DeferredCoroutine async$default =
                    BuildersKt.async$default(
                            (CoroutineScope) this.L$0,
                            new AppListRepositoryImpl$loadHomeOrLauncherPackages$2$launcherActivities$1(
                                    this.this$0,
                                    this.$launchIntent,
                                    this.$flags,
                                    this.$userId,
                                    null));
            ArrayList arrayList2 = new ArrayList();
            this.this$0.packageManager.getHomeActivities(arrayList2);
            this.L$0 = arrayList2;
            this.label = 1;
            obj = async$default.awaitInternal(this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            arrayList = arrayList2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            arrayList = (ArrayList) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Intrinsics.checkNotNullExpressionValue(obj, "await(...)");
        List plus = CollectionsKt___CollectionsKt.plus((Iterable) arrayList, (Collection) obj);
        ArrayList arrayList3 =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(plus, 10));
        Iterator it = ((ArrayList) plus).iterator();
        while (it.hasNext()) {
            arrayList3.add(((ResolveInfo) it.next()).activityInfo.packageName);
        }
        return CollectionsKt___CollectionsKt.toSet(arrayList3);
    }
}
