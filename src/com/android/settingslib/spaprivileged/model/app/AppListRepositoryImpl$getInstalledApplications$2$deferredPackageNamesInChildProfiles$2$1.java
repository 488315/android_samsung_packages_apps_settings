package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
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
final class AppListRepositoryImpl$getInstalledApplications$2$deferredPackageNamesInChildProfiles$2$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ int $it;
    final /* synthetic */ PackageManager.ApplicationInfoFlags $regularFlags;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$getInstalledApplications$2$deferredPackageNamesInChildProfiles$2$1(
            int i,
            PackageManager.ApplicationInfoFlags applicationInfoFlags,
            AppListRepositoryImpl appListRepositoryImpl,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
        this.$regularFlags = applicationInfoFlags;
        this.$it = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppListRepositoryImpl appListRepositoryImpl = this.this$0;
        return new AppListRepositoryImpl$getInstalledApplications$2$deferredPackageNamesInChildProfiles$2$1(
                this.$it, this.$regularFlags, appListRepositoryImpl, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$getInstalledApplications$2$deferredPackageNamesInChildProfiles$2$1)
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
        List installedApplicationsAsUser =
                this.this$0.packageManager.getInstalledApplicationsAsUser(
                        this.$regularFlags, this.$it);
        Intrinsics.checkNotNullExpressionValue(
                installedApplicationsAsUser, "getInstalledApplicationsAsUser(...)");
        List list = installedApplicationsAsUser;
        ArrayList arrayList =
                new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((ApplicationInfo) it.next()).packageName);
        }
        return arrayList;
    }
}
