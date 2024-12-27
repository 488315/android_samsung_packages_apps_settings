package com.android.settings.spa.app.storage;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class StorageAppListModel$transform$$inlined$mapItem$1$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ StorageAppListModel this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2$1, reason: invalid class name */
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
            return StorageAppListModel$transform$$inlined$mapItem$1$2.this.emit(null, this);
        }
    }

    public StorageAppListModel$transform$$inlined$mapItem$1$2(
            FlowCollector flowCollector, StorageAppListModel storageAppListModel) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = storageAppListModel;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2$1 r0 = (com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2$1 r0 = new com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r9)
            goto L76
        L27:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L2f:
            kotlin.ResultKt.throwOnFailure(r9)
            java.util.List r8 = (java.util.List) r8
            java.lang.Iterable r8 = (java.lang.Iterable) r8
            java.util.ArrayList r9 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r8, r2)
            r9.<init>(r2)
            java.util.Iterator r8 = r8.iterator()
        L45:
            boolean r2 = r8.hasNext()
            if (r2 == 0) goto L6b
            java.lang.Object r2 = r8.next()
            android.content.pm.ApplicationInfo r2 = (android.content.pm.ApplicationInfo) r2
            com.android.settings.spa.app.storage.AppRecordWithSize r4 = new com.android.settings.spa.app.storage.AppRecordWithSize
            com.android.settings.spa.app.storage.StorageAppListModel r5 = r7.this$0
            android.content.Context r5 = r5.context
            java.lang.Long r5 = com.android.settingslib.spaprivileged.template.app.AppStorageSizeKt.calculateSizeBytes(r5, r2)
            if (r5 == 0) goto L62
            long r5 = r5.longValue()
            goto L64
        L62:
            r5 = 0
        L64:
            r4.<init>(r2, r5)
            r9.add(r4)
            goto L45
        L6b:
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
            java.lang.Object r7 = r7.emit(r9, r0)
            if (r7 != r1) goto L76
            return r1
        L76:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.spa.app.storage.StorageAppListModel$transform$$inlined$mapItem$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
