package com.android.settings.spa.app.appinfo;

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
final class HibernationSwitchPresenter$isExempt$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HibernationSwitchPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HibernationSwitchPresenter$isExempt$2(
            HibernationSwitchPresenter hibernationSwitchPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = hibernationSwitchPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HibernationSwitchPresenter$isExempt$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HibernationSwitchPresenter$isExempt$2)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:

       if (r4 != 0) goto L14;
    */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0033, code lost:

       if (r3.app.targetSdkVersion <= 29) goto L14;
    */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r4) {
        /*
            r3 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r0 = r3.label
            if (r0 != 0) goto L3f
            kotlin.ResultKt.throwOnFailure(r4)
            com.android.settings.spa.app.appinfo.HibernationSwitchPresenter r4 = r3.this$0
            android.app.AppOpsManager r0 = r4.appOpsManager
            android.content.pm.ApplicationInfo r4 = r4.app
            int r1 = r4.uid
            java.lang.String r4 = r4.packageName
            r2 = 97
            int r4 = r0.checkOpNoThrow(r2, r1, r4)
            r0 = 3
            r1 = 1
            r2 = 0
            if (r4 != r0) goto L36
            com.android.settings.spa.app.appinfo.HibernationSwitchPresenter r3 = r3.this$0
            r3.getClass()
            java.lang.String r4 = "app_hibernation"
            java.lang.String r0 = "app_hibernation_targets_pre_s_apps"
            boolean r4 = android.provider.DeviceConfig.getBoolean(r4, r0, r2)
            if (r4 != 0) goto L39
            android.content.pm.ApplicationInfo r3 = r3.app
            int r3 = r3.targetSdkVersion
            r4 = 29
            if (r3 > r4) goto L39
            goto L3a
        L36:
            if (r4 == 0) goto L39
            goto L3a
        L39:
            r1 = r2
        L3a:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r1)
            return r3
        L3f:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.HibernationSwitchPresenter$isExempt$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
