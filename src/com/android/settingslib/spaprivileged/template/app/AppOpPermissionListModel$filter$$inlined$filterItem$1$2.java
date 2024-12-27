package com.android.settingslib.spaprivileged.template.app;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOpPermissionListModel$filter$$inlined$filterItem$1$2
        implements FlowCollector {
    public final /* synthetic */ AppOpPermissionListModel $receiver$inlined;
    public final /* synthetic */ FlowCollector $this_unsafeFlow;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2$1, reason: invalid class name */
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
            return AppOpPermissionListModel$filter$$inlined$filterItem$1$2.this.emit(null, this);
        }
    }

    public AppOpPermissionListModel$filter$$inlined$filterItem$1$2(
            FlowCollector flowCollector, AppOpPermissionListModel appOpPermissionListModel) {
        this.$this_unsafeFlow = flowCollector;
        this.$receiver$inlined = appOpPermissionListModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2$1 r0 = (com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2$1 r0 = new com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r8)
            goto L63
        L27:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L2f:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.List r7 = (java.util.List) r7
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.Iterator r7 = r7.iterator()
        L3f:
            boolean r2 = r7.hasNext()
            if (r2 == 0) goto L58
            java.lang.Object r2 = r7.next()
            r4 = r2
            com.android.settingslib.spaprivileged.template.app.AppOpPermissionRecord r4 = (com.android.settingslib.spaprivileged.template.app.AppOpPermissionRecord) r4
            com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel r5 = r6.$receiver$inlined
            boolean r4 = r5.isChangeable(r4)
            if (r4 == 0) goto L3f
            r8.add(r2)
            goto L3f
        L58:
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
            java.lang.Object r6 = r6.emit(r8, r0)
            if (r6 != r1) goto L63
            return r1
        L63:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.template.app.AppOpPermissionListModel$filter$$inlined$filterItem$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
