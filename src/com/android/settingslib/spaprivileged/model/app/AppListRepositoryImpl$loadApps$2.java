package com.android.settingslib.spaprivileged.model.app;

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
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u0003H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroid/content/pm/ApplicationInfo;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListRepositoryImpl$loadApps$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $loadInstantApps;
    final /* synthetic */ boolean $matchAnyUserForAdmin;
    final /* synthetic */ int $userId;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ AppListRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListRepositoryImpl$loadApps$2(
            AppListRepositoryImpl appListRepositoryImpl,
            int i,
            boolean z,
            boolean z2,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appListRepositoryImpl;
        this.$userId = i;
        this.$matchAnyUserForAdmin = z;
        this.$loadInstantApps = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppListRepositoryImpl$loadApps$2 appListRepositoryImpl$loadApps$2 =
                new AppListRepositoryImpl$loadApps$2(
                        this.this$0,
                        this.$userId,
                        this.$matchAnyUserForAdmin,
                        this.$loadInstantApps,
                        continuation);
        appListRepositoryImpl$loadApps$2.L$0 = obj;
        return appListRepositoryImpl$loadApps$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListRepositoryImpl$loadApps$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00d4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d5  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.model.app.AppListRepositoryImpl$loadApps$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
