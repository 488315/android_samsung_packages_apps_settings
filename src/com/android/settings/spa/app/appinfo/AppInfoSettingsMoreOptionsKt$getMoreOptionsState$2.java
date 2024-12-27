package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.android.settingslib.spaprivileged.model.app.IPackageManagers;

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
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            "Lcom/android/settings/spa/app/appinfo/AppInfoSettingsMoreOptionsState;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ IPackageManagers $packageManagers;
    final /* synthetic */ ApplicationInfo $this_getMoreOptionsState;
    int I$0;
    int I$1;
    private /* synthetic */ Object L$0;
    Object L$1;
    boolean Z$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2(
            Context context,
            ApplicationInfo applicationInfo,
            IPackageManagers iPackageManagers,
            Continuation continuation) {
        super(2, continuation);
        this.$context = context;
        this.$this_getMoreOptionsState = applicationInfo;
        this.$packageManagers = iPackageManagers;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2
                appInfoSettingsMoreOptionsKt$getMoreOptionsState$2 =
                        new AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2(
                                this.$context,
                                this.$this_getMoreOptionsState,
                                this.$packageManagers,
                                continuation);
        appInfoSettingsMoreOptionsKt$getMoreOptionsState$2.L$0 = obj;
        return appInfoSettingsMoreOptionsKt$getMoreOptionsState$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00e3  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
