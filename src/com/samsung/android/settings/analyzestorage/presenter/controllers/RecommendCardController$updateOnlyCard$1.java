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

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

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
final class RecommendCardController$updateOnlyCard$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $recommendType;
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
    /* renamed from: com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController$updateOnlyCard$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $indexCard;
        final /* synthetic */ boolean $needUpdate;
        int label;
        final /* synthetic */ RecommendCardController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                RecommendCardController recommendCardController,
                boolean z,
                int i,
                Continuation continuation) {
            super(2, continuation);
            this.this$0 = recommendCardController;
            this.$needUpdate = z;
            this.$indexCard = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.this$0, this.$needUpdate, this.$indexCard, continuation);
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
            if (this.$needUpdate) {
                ExtensionFunctionsKt.updateValue(
                        this.this$0.recommendCardState,
                        new RecommendCardState(
                                2,
                                this.this$0.supportedRecommendCardListTemp,
                                0,
                                this.$indexCard,
                                4));
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecommendCardController$updateOnlyCard$1(
            RecommendCardController recommendCardController, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = recommendCardController;
        this.$recommendType = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new RecommendCardController$updateOnlyCard$1(
                this.this$0, this.$recommendType, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RecommendCardController$updateOnlyCard$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            int indexOf =
                    this.this$0.supportedRecommendCardListTemp.indexOf(
                            new Integer(this.$recommendType));
            boolean z = indexOf != -1 || this.$recommendType == 5;
            if (z) {
                if (indexOf != -1) {
                    this.this$0.supportedRecommendCardListTemp.remove(indexOf);
                }
                int i2 = this.$recommendType;
                if (i2 == 6) {
                    this.this$0.getTopInstalledAppSize();
                } else if (i2 == 5) {
                    this.this$0.getCloudStatus();
                }
            }
            DefaultScheduler defaultScheduler = Dispatchers.Default;
            HandlerContext handlerContext = MainDispatcherLoader.dispatcher;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, z, indexOf, null);
            this.label = 1;
            if (BuildersKt.withContext(handlerContext, anonymousClass1, this)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
