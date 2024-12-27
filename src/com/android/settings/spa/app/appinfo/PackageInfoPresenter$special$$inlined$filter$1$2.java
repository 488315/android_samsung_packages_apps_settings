package com.android.settings.spa.app.appinfo;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PackageInfoPresenter$special$$inlined$filter$1$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ PackageInfoPresenter receiver$inlined;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2$1, reason: invalid class name */
    public final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return PackageInfoPresenter$special$$inlined$filter$1$2.this.emit(null, this);
        }
    }

    public PackageInfoPresenter$special$$inlined$filter$1$2(
            FlowCollector flowCollector, PackageInfoPresenter packageInfoPresenter) {
        this.$this_unsafeFlow = flowCollector;
        this.receiver$inlined = packageInfoPresenter;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2$1 r0 = (com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2$1 r0 = new com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            goto L48
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            r6 = r5
            android.content.Intent r6 = (android.content.Intent) r6
            com.android.settings.spa.app.appinfo.PackageInfoPresenter r2 = r4.receiver$inlined
            boolean r6 = r2.isInterestedAppChange(r6)
            if (r6 == 0) goto L48
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
            java.lang.Object r4 = r4.emit(r5, r0)
            if (r4 != r1) goto L48
            return r1
        L48:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.appinfo.PackageInfoPresenter$special$$inlined$filter$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
