package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

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

import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u0010\u0012\f\u0012\n"
                + " \u0003*\u0004\u0018\u00010\u00020\u00020\u0001*\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ApplicationInfo;",
            "kotlin.jvm.PlatformType",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$getInstalledApplications$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ PackageManager.ApplicationInfoFlags $regularFlags;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$getInstalledApplications$2(
            int i,
            PackageManager.ApplicationInfoFlags applicationInfoFlags,
            AppListRepositoryImpl appListRepositoryImpl,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
        this.$userId = i;
        this.$regularFlags = applicationInfoFlags;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppListRepositoryImpl$getInstalledApplications$2
                appListRepositoryImpl$getInstalledApplications$2 =
                        new AppListRepositoryImpl$getInstalledApplications$2(
                                this.$userId, this.$regularFlags, this.this$0, continuation);
        appListRepositoryImpl$getInstalledApplications$2.L$0 = obj;
        return appListRepositoryImpl$getInstalledApplications$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$getInstalledApplications$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            int[] profileIdsWithDisabled =
                    this.this$0.userManager.getProfileIdsWithDisabled(this.$userId);
            Intrinsics.checkNotNullExpressionValue(
                    profileIdsWithDisabled, "getProfileIdsWithDisabled(...)");
            int i2 = this.$userId;
            ArrayList arrayList = new ArrayList();
            for (int i3 : profileIdsWithDisabled) {
                if (i3 != i2) {
                    arrayList.add(new Integer(i3));
                }
            }
            AppListRepositoryImpl appListRepositoryImpl = this.this$0;
            PackageManager.ApplicationInfoFlags applicationInfoFlags = this.$regularFlags;
            ArrayList arrayList2 =
                    new ArrayList(
                            CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.add(
                        BuildersKt.async$default(
                                coroutineScope,
                                new AppListRepositoryImpl$getInstalledApplications$2$deferredPackageNamesInChildProfiles$2$1(
                                        ((Number) it.next()).intValue(),
                                        applicationInfoFlags,
                                        appListRepositoryImpl,
                                        null)));
            }
            PackageManager.ApplicationInfoFlags of =
                    PackageManager.ApplicationInfoFlags.of(this.$regularFlags.getValue() | 4194304);
            Intrinsics.checkNotNullExpressionValue(of, "of(...)");
            List installedApplicationsAsUser =
                    this.this$0.packageManager.getInstalledApplicationsAsUser(of, this.$userId);
            Intrinsics.checkNotNullExpressionValue(
                    installedApplicationsAsUser, "getInstalledApplicationsAsUser(...)");
            this.L$0 = installedApplicationsAsUser;
            this.label = 1;
            Object awaitAll = AwaitKt.awaitAll(arrayList2, this);
            if (awaitAll == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = awaitAll;
            list = installedApplicationsAsUser;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        Set set =
                CollectionsKt___CollectionsKt.toSet(
                        CollectionsKt__IterablesKt.flatten((Iterable) obj));
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : list) {
            ApplicationInfo applicationInfo = (ApplicationInfo) obj2;
            Intrinsics.checkNotNull(applicationInfo);
            if (ApplicationInfosKt.getInstalled(applicationInfo)
                    || !set.contains(applicationInfo.packageName)) {
                arrayList3.add(obj2);
            }
        }
        return arrayList3;
    }
}
