package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInstallerInfoPresenter$special$$inlined$map$3$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ AppInstallerInfoPresenter this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return AppInstallerInfoPresenter$special$$inlined$map$3$2.this.emit(null, this);
        }
    }

    public AppInstallerInfoPresenter$special$$inlined$map$3$2(
            FlowCollector flowCollector, AppInstallerInfoPresenter appInstallerInfoPresenter) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = appInstallerInfoPresenter;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L30
            if (r2 != r3) goto L28
            kotlin.ResultKt.throwOnFailure(r8)
            goto La7
        L28:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L30:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r8 = r6.this$0
            android.content.pm.ApplicationInfo r2 = r8.app
            boolean r2 = r2.isInstantApp()
            if (r2 == 0) goto L4d
            android.content.Context r8 = r8.context
            r2 = 2132021805(0x7f14122d, float:1.9682012E38)
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r7 = r8.getString(r2, r7)
            goto L9c
        L4d:
            android.content.pm.InstallSourceInfo r2 = r8.installSourceInfo
            r4 = 2132018023(0x7f140367, float:1.967434E38)
            if (r2 != 0) goto L55
            goto L92
        L55:
            java.lang.String r5 = r2.getInitiatingPackageName()
            if (r5 == 0) goto L92
            java.lang.String r2 = r2.getInstallingPackageName()
            boolean r2 = android.text.TextUtils.equals(r2, r5)
            if (r2 != 0) goto L92
            android.content.Context r2 = r8.context
            android.content.pm.InstallSourceInfo r5 = r8.installSourceInfo
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            java.lang.String r5 = r5.getInitiatingPackageName()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            java.lang.CharSequence r2 = com.android.settings.Utils.getApplicationLabel(r2, r5)
            if (r2 == 0) goto L87
            android.content.Context r8 = r8.context
            r4 = 2132018021(0x7f140365, float:1.9674337E38)
            java.lang.Object[] r7 = new java.lang.Object[]{r7, r2}
            java.lang.String r7 = r8.getString(r4, r7)
            goto L9c
        L87:
            android.content.Context r8 = r8.context
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r7 = r8.getString(r4, r7)
            goto L9c
        L92:
            android.content.Context r8 = r8.context
            java.lang.Object[] r7 = new java.lang.Object[]{r7}
            java.lang.String r7 = r8.getString(r4, r7)
        L9c:
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
            java.lang.Object r6 = r6.emit(r7, r0)
            if (r6 != r1) goto La7
            return r1
        La7:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$3$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
