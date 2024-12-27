package com.samsung.android.settings.analyzestorage.presenter.controllers;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.model.RecommendCardState;
import com.samsung.android.settings.analyzestorage.presenter.utils.ExtensionFunctionsKt;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\f\b\u0001\u0010\u0004*\u0006\u0012\u0002\b\u00030\u0005*\u00020\u0006H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lcom/samsung/android/settings/analyzestorage/domain/entity/DataInfo;",
            "S",
            "Lcom/samsung/android/settings/analyzestorage/domain/repository/IDataInfoRepository;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
final class RecommendCardController$updateSupportedRecommendCardList$1 extends SuspendLambda
        implements Function2 {
    int label;
    final /* synthetic */ RecommendCardController this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0016\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003\"\f\b\u0001\u0010\u0004*\u0006\u0012\u0002\b\u00030\u0005*\u00020\u0006H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "T",
                "Lcom/samsung/android/settings/analyzestorage/domain/entity/DataInfo;",
                "S",
                "Lcom/samsung/android/settings/analyzestorage/domain/repository/IDataInfoRepository;",
                "Lkotlinx/coroutines/CoroutineScope;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController$updateSupportedRecommendCardList$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ RecommendCardController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                RecommendCardController recommendCardController, Continuation continuation) {
            super(2, continuation);
            this.this$0 = recommendCardController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            AnonymousClass1 anonymousClass1 =
                    (AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2);
            Unit unit = Unit.INSTANCE;
            anonymousClass1.invokeSuspend(unit);
            return unit;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.this$0.setLoadingData$1(false);
            ExtensionFunctionsKt.updateValue(
                    this.this$0.recommendCardState,
                    new RecommendCardState(
                            0, this.this$0.supportedRecommendCardListTemp, 0, 0, 12));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecommendCardController$updateSupportedRecommendCardList$1(
            RecommendCardController recommendCardController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = recommendCardController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RecommendCardController$updateSupportedRecommendCardList$1(
                this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RecommendCardController$updateSupportedRecommendCardList$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0155  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            Method dump skipped, instructions count: 739
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController$updateSupportedRecommendCardList$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
