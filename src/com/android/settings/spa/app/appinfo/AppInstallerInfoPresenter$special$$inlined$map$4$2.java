package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppInstallerInfoPresenter$special$$inlined$map$4$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ AppInstallerInfoPresenter this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2$1, reason: invalid class name */
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
            return AppInstallerInfoPresenter$special$$inlined$map$4$2.this.emit(null, this);
        }
    }

    public AppInstallerInfoPresenter$special$$inlined$map$4$2(
            FlowCollector flowCollector, AppInstallerInfoPresenter appInstallerInfoPresenter) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = appInstallerInfoPresenter;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2$1 r0 = (com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2$1 r0 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 == r5) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r9)
            goto L61
        L2b:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L33:
            java.lang.Object r7 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L56
        L3b:
            kotlin.ResultKt.throwOnFailure(r9)
            java.lang.String r8 = (java.lang.String) r8
            kotlinx.coroutines.scheduling.DefaultIoScheduler r9 = kotlinx.coroutines.Dispatchers.IO
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$intentFlow$1$1 r2 = new com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$intentFlow$1$1
            com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter r6 = r7.this$0
            r2.<init>(r6, r8, r3)
            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
            r0.L$0 = r7
            r0.label = r5
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r9, r2, r0)
            if (r9 != r1) goto L56
            return r1
        L56:
            r0.L$0 = r3
            r0.label = r4
            java.lang.Object r7 = r7.emit(r9, r0)
            if (r7 != r1) goto L61
            return r1
        L61:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$special$$inlined$map$4$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
