package com.android.settings.print;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2
        implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ PrintRepository this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2$1, reason: invalid class name */
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
            return PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2.this.emit(
                    null, this);
        }
    }

    public PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2(
            FlowCollector flowCollector, PrintRepository printRepository) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = printRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r13, kotlin.coroutines.Continuation r14) {
        /*
            r12 = this;
            boolean r0 = r14 instanceof com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r14
            com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2$1 r0 = (com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2$1 r0 = new com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2$1
            r0.<init>(r14)
        L18:
            java.lang.Object r14 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L30
            if (r2 != r3) goto L28
            kotlin.ResultKt.throwOnFailure(r14)
            goto Lb2
        L28:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r13 = "call to 'resume' before 'invoke' with coroutine"
            r12.<init>(r13)
            throw r12
        L30:
            kotlin.ResultKt.throwOnFailure(r14)
            java.util.List r13 = (java.util.List) r13
            java.lang.Iterable r13 = (java.lang.Iterable) r13
            java.util.ArrayList r14 = new java.util.ArrayList
            r2 = 10
            int r2 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r13, r2)
            r14.<init>(r2)
            java.util.Iterator r13 = r13.iterator()
        L46:
            boolean r2 = r13.hasNext()
            if (r2 == 0) goto La7
            java.lang.Object r2 = r13.next()
            android.printservice.PrintServiceInfo r2 = (android.printservice.PrintServiceInfo) r2
            com.android.settings.print.PrintRepository r4 = r12.this$0
            r4.getClass()
            com.android.settings.print.PrintRepository$PrintServiceDisplayInfo r11 = new com.android.settings.print.PrintRepository$PrintServiceDisplayInfo
            android.content.pm.ResolveInfo r5 = r2.getResolveInfo()
            android.content.pm.PackageManager r6 = r4.packageManager
            java.lang.CharSequence r5 = r5.loadLabel(r6)
            java.lang.String r6 = r5.toString()
            boolean r7 = r2.isEnabled()
            android.content.Context r5 = r4.context
            boolean r8 = r2.isEnabled()
            if (r8 == 0) goto L77
            r8 = 2132024520(0x7f141cc8, float:1.9687518E38)
            goto L7a
        L77:
            r8 = 2132024519(0x7f141cc7, float:1.9687516E38)
        L7a:
            java.lang.String r8 = r5.getString(r8)
            java.lang.String r5 = "getString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r5)
            android.content.pm.ResolveInfo r5 = r2.getResolveInfo()
            android.content.pm.PackageManager r4 = r4.packageManager
            android.graphics.drawable.Drawable r9 = r5.loadIcon(r4)
            java.lang.String r4 = "loadIcon(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r4)
            android.content.ComponentName r2 = r2.getComponentName()
            java.lang.String r10 = r2.flattenToString()
            java.lang.String r2 = "flattenToString(...)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r2)
            r5 = r11
            r5.<init>(r6, r7, r8, r9, r10)
            r14.add(r11)
            goto L46
        La7:
            r0.label = r3
            kotlinx.coroutines.flow.FlowCollector r12 = r12.$this_unsafeFlow
            java.lang.Object r12 = r12.emit(r14, r0)
            if (r12 != r1) goto Lb2
            return r1
        Lb2:
            kotlin.Unit r12 = kotlin.Unit.INSTANCE
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.print.PrintRepository$printServiceDisplayInfosFlow$$inlined$mapItem$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
