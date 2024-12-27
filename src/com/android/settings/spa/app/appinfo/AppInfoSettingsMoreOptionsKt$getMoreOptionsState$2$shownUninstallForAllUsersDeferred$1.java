package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.UserInfo;
import android.os.UserManager;

import com.android.settings.Utils;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;
import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;
import com.android.settingslib.spaprivileged.model.app.IPackageManagers;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\n\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shownUninstallForAllUsersDeferred$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ IPackageManagers $packageManagers;
    final /* synthetic */ ApplicationInfo $this_getMoreOptionsState;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shownUninstallForAllUsersDeferred$1(
            Context context,
            ApplicationInfo applicationInfo,
            IPackageManagers iPackageManagers,
            Continuation continuation) {
        super(2, continuation);
        this.$this_getMoreOptionsState = applicationInfo;
        this.$context = context;
        this.$packageManagers = iPackageManagers;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shownUninstallForAllUsersDeferred$1(
                this.$context, this.$this_getMoreOptionsState, this.$packageManagers, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2$shownUninstallForAllUsersDeferred$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        boolean z;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ApplicationInfo applicationInfo = this.$this_getMoreOptionsState;
        UserManager userManager = ContextsKt.getUserManager(this.$context);
        IPackageManagers iPackageManagers = this.$packageManagers;
        if (ApplicationInfosKt.getUserId(applicationInfo) == 0
                && !applicationInfo.isSystemApp()
                && !applicationInfo.isInstantApp()) {
            List aliveUsers = userManager.getAliveUsers();
            Intrinsics.checkNotNullExpressionValue(aliveUsers, "getAliveUsers(...)");
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : aliveUsers) {
                if (((UserInfo) obj2).id != ApplicationInfosKt.getUserId(applicationInfo)) {
                    arrayList.add(obj2);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (true) {
                z = true;
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (true ^ Utils.shouldHideUser(((UserInfo) next).getUserHandle(), userManager)) {
                    arrayList2.add(next);
                }
            }
            if (!arrayList2.isEmpty()) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    UserInfo userInfo = (UserInfo) it2.next();
                    String packageName = applicationInfo.packageName;
                    Intrinsics.checkNotNullExpressionValue(packageName, "packageName");
                    if (iPackageManagers.isPackageInstalledAsUser(userInfo.id, packageName)) {
                        break;
                    }
                }
            }
        }
        z = false;
        return Boolean.valueOf(z);
    }
}
