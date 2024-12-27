package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;
import android.icu.text.CollationKey;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppListViewModelImpl$special$$inlined$asyncMapItem$1$2 implements FlowCollector {
    public final /* synthetic */ FlowCollector $this_unsafeFlow;
    public final /* synthetic */ AppListViewModelImpl this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$1, reason: invalid class name */
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
            return AppListViewModelImpl$special$$inlined$asyncMapItem$1$2.this.emit(null, this);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0010\n"
                    + "\u0002\b\u0002\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010 \n"
                    + "\u0002\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@¢\u0006\u0004\b\u0004\u0010\u0005"
            },
            d2 = {
                "R",
                "T",
                "Lkotlinx/coroutines/CoroutineScope;",
                ApnSettings.MVNO_NONE,
                "<anonymous>",
                "(Lkotlinx/coroutines/CoroutineScope;)Ljava/util/List;"
            },
            k = 3,
            mv = {1, 9, 0})
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$2, reason: invalid class name */
    public final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Iterable $this_asyncMap;
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ AppListViewModelImpl this$0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        @Metadata(
                d1 = {
                    "\u0000\n\n"
                        + "\u0002\b\u0002\n"
                        + "\u0002\u0018\u0002\n"
                        + "\u0000\u0010\u0003\u001a\u00028\u0000\"\u0004\b\u0000\u0010\u0000\"\u0004\b\u0001\u0010\u0001*\u00020\u0002H\u008a@"
                },
                d2 = {"R", "T", "Lkotlinx/coroutines/CoroutineScope;", "<anonymous>"},
                k = 3,
                mv = {1, 9, 0})
        /* renamed from: com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$2$1, reason: invalid class name */
        public final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ Object $item;
            int label;
            final /* synthetic */ AppListViewModelImpl this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(
                    Object obj,
                    Continuation continuation,
                    AppListViewModelImpl appListViewModelImpl) {
                super(2, continuation);
                this.$item = obj;
                this.this$0 = appListViewModelImpl;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$item, continuation, this.this$0);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                        .invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                AppRecord appRecord = (AppRecord) this.$item;
                AppListViewModelImpl appListViewModelImpl = this.this$0;
                ApplicationInfo app = appRecord.getApp();
                Object computeIfAbsent =
                        appListViewModelImpl.labelMap.computeIfAbsent(
                                app.packageName,
                                new AppListViewModelImpl$getLabel$1(appListViewModelImpl, app));
                Intrinsics.checkNotNullExpressionValue(computeIfAbsent, "computeIfAbsent(...)");
                String str = (String) computeIfAbsent;
                CollationKey collationKey = this.this$0.collator.getCollationKey(str);
                Intrinsics.checkNotNullExpressionValue(collationKey, "getCollationKey(...)");
                return new AppEntry(appRecord, str, collationKey);
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(
                Iterable iterable,
                Continuation continuation,
                AppListViewModelImpl appListViewModelImpl) {
            super(2, continuation);
            this.$this_asyncMap = iterable;
            this.this$0 = appListViewModelImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 =
                    new AnonymousClass2(this.$this_asyncMap, continuation, this.this$0);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                Iterable iterable = this.$this_asyncMap;
                ArrayList arrayList =
                        new ArrayList(
                                CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
                Iterator it = iterable.iterator();
                while (it.hasNext()) {
                    arrayList.add(
                            BuildersKt.async$default(
                                    coroutineScope,
                                    new AnonymousClass1(it.next(), null, this.this$0)));
                }
                this.label = 1;
                obj = AwaitKt.awaitAll(arrayList, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        }
    }

    public AppListViewModelImpl$special$$inlined$asyncMapItem$1$2(
            FlowCollector flowCollector, AppListViewModelImpl appListViewModelImpl) {
        this.$this_unsafeFlow = flowCollector;
        this.this$0 = appListViewModelImpl;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0060 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    @Override // kotlinx.coroutines.flow.FlowCollector
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$1 r0 = (com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$1 r0 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$1
            r0.<init>(r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L3b
            if (r2 == r5) goto L33
            if (r2 != r4) goto L2b
            kotlin.ResultKt.throwOnFailure(r8)
            goto L61
        L2b:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L33:
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L56
        L3b:
            kotlin.ResultKt.throwOnFailure(r8)
            java.util.List r7 = (java.util.List) r7
            java.lang.Iterable r7 = (java.lang.Iterable) r7
            com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$2 r8 = new com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2$2
            com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl r2 = r6.this$0
            r8.<init>(r7, r3, r2)
            kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
            r0.L$0 = r6
            r0.label = r5
            java.lang.Object r8 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r8, r0)
            if (r8 != r1) goto L56
            return r1
        L56:
            r0.L$0 = r3
            r0.label = r4
            java.lang.Object r6 = r6.emit(r8, r0)
            if (r6 != r1) goto L61
            return r1
        L61:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spaprivileged.model.app.AppListViewModelImpl$special$$inlined$asyncMapItem$1$2.emit(java.lang.Object,"
                    + " kotlin.coroutines.Continuation):java.lang.Object");
    }
}
