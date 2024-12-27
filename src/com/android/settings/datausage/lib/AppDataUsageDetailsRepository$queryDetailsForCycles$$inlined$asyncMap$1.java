package com.android.settings.datausage.lib;

import android.util.Range;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.AwaitKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
/* loaded from: classes2.dex */
public final class AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1
        extends SuspendLambda implements Function2 {
    final /* synthetic */ Iterable $this_asyncMap;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppDataUsageDetailsRepository this$0;

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
    /* renamed from: com.android.settings.datausage.lib.AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1$1, reason: invalid class name */
    public final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ Object $item;
        int label;
        final /* synthetic */ AppDataUsageDetailsRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                Object obj,
                Continuation continuation,
                AppDataUsageDetailsRepository appDataUsageDetailsRepository) {
            super(2, continuation);
            this.$item = obj;
            this.this$0 = appDataUsageDetailsRepository;
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
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            Range range = (Range) this.$item;
            AppDataUsageDetailsRepository appDataUsageDetailsRepository = this.this$0;
            String str = AppDataUsageDetailsRepository.TAG;
            appDataUsageDetailsRepository.getClass();
            Object lower = range.getLower();
            Intrinsics.checkNotNullExpressionValue(lower, "getLower(...)");
            long longValue = ((Number) lower).longValue();
            Object upper = range.getUpper();
            Intrinsics.checkNotNullExpressionValue(upper, "getUpper(...)");
            List queryBuckets =
                    appDataUsageDetailsRepository.networkStatsRepository.queryBuckets(
                            longValue, ((Number) upper).longValue());
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : queryBuckets) {
                if (appDataUsageDetailsRepository.withSdkSandboxUids.contains(
                        Integer.valueOf(((NetworkStatsRepository.Companion.Bucket) obj2).uid))) {
                    arrayList.add(obj2);
                }
            }
            Iterator it = arrayList.iterator();
            long j = 0;
            while (it.hasNext()) {
                j += ((NetworkStatsRepository.Companion.Bucket) it.next()).bytes;
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                if (((NetworkStatsRepository.Companion.Bucket) next).state == 2) {
                    arrayList2.add(next);
                }
            }
            Iterator it3 = arrayList2.iterator();
            long j2 = 0;
            while (it3.hasNext()) {
                j2 += ((NetworkStatsRepository.Companion.Bucket) it3.next()).bytes;
            }
            return new NetworkUsageDetailsData(range, j, j2, j - j2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1(
            Iterable iterable,
            Continuation continuation,
            AppDataUsageDetailsRepository appDataUsageDetailsRepository) {
        super(2, continuation);
        this.$this_asyncMap = iterable;
        this.this$0 = appDataUsageDetailsRepository;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1
                appDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1 =
                        new AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1(
                                this.$this_asyncMap, continuation, this.this$0);
        appDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1.L$0 = obj;
        return appDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsageDetailsRepository$queryDetailsForCycles$$inlined$asyncMap$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
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
                    new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                arrayList.add(
                        BuildersKt.async$default(
                                coroutineScope, new AnonymousClass1(it.next(), null, this.this$0)));
            }
            this.label = 1;
            obj = AwaitKt.awaitAll(arrayList, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}
