package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ModuleInfo;
import android.content.pm.PackageManager;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\"\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$loadApps$2$hiddenSystemModulesDeferred$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$loadApps$2$hiddenSystemModulesDeferred$1(
            AppListRepositoryImpl appListRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppListRepositoryImpl$loadApps$2$hiddenSystemModulesDeferred$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$loadApps$2$hiddenSystemModulesDeferred$1)
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
        PackageManager packageManager = this.this$0.packageManager;
        Intrinsics.checkNotNullExpressionValue(packageManager, "access$getPackageManager$p(...)");
        List<ModuleInfo> installedModules = packageManager.getInstalledModules(0);
        Intrinsics.checkNotNullExpressionValue(installedModules, "getInstalledModules(...)");
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : installedModules) {
            if (((ModuleInfo) obj2).isHidden()) {
                arrayList.add(obj2);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String packageName = ((ModuleInfo) it.next()).getPackageName();
            if (packageName != null) {
                arrayList2.add(packageName);
            }
        }
        Set mutableSet = CollectionsKt___CollectionsKt.toMutableSet(arrayList2);
        if (Flags.provideInfoOfApkInApex()) {
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Collection apkInApexPackageNames =
                        ((ModuleInfo) it2.next()).getApkInApexPackageNames();
                Intrinsics.checkNotNullExpressionValue(
                        apkInApexPackageNames, "getApkInApexPackageNames(...)");
                CollectionsKt___CollectionsKt.addAll(apkInApexPackageNames, arrayList3);
            }
            CollectionsKt___CollectionsKt.addAll(arrayList3, mutableSet);
        }
        return mutableSet;
    }
}
