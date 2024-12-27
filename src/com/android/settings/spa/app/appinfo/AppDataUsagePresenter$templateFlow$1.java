package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.net.NetworkTemplate;
import android.telephony.SubscriptionManager;

import com.android.settings.datausage.DataUsageUtils;
import com.android.settings.datausage.lib.INetworkTemplates;
import com.android.settings.datausage.lib.NetworkTemplates;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            "Landroid/net/NetworkTemplate;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppDataUsagePresenter$templateFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ INetworkTemplates $networkTemplates;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppDataUsagePresenter this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {
                "<anonymous>",
                "Landroid/net/NetworkTemplate;",
                "Lkotlinx/coroutines/CoroutineScope;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.appinfo.AppDataUsagePresenter$templateFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ INetworkTemplates $networkTemplates;
        int label;
        final /* synthetic */ AppDataUsagePresenter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                INetworkTemplates iNetworkTemplates,
                AppDataUsagePresenter appDataUsagePresenter,
                Continuation continuation) {
            super(2, continuation);
            this.$networkTemplates = iNetworkTemplates;
            this.this$0 = appDataUsagePresenter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$networkTemplates, this.this$0, continuation);
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
            INetworkTemplates iNetworkTemplates = this.$networkTemplates;
            Context context = this.this$0.context;
            ((NetworkTemplates) iNetworkTemplates).getClass();
            Intrinsics.checkNotNullParameter(context, "context");
            NetworkTemplate defaultTemplate =
                    DataUsageUtils.getDefaultTemplate(
                            context, SubscriptionManager.getDefaultDataSubscriptionId());
            Intrinsics.checkNotNullExpressionValue(defaultTemplate, "getDefaultTemplate(...)");
            return defaultTemplate;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppDataUsagePresenter$templateFlow$1(
            INetworkTemplates iNetworkTemplates,
            AppDataUsagePresenter appDataUsagePresenter,
            Continuation continuation) {
        super(2, continuation);
        this.$networkTemplates = iNetworkTemplates;
        this.this$0 = appDataUsagePresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppDataUsagePresenter$templateFlow$1 appDataUsagePresenter$templateFlow$1 =
                new AppDataUsagePresenter$templateFlow$1(
                        this.$networkTemplates, this.this$0, continuation);
        appDataUsagePresenter$templateFlow$1.L$0 = obj;
        return appDataUsagePresenter$templateFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppDataUsagePresenter$templateFlow$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(this.$networkTemplates, this.this$0, null);
            this.L$0 = flowCollector;
            this.label = 1;
            obj = BuildersKt.withContext(defaultIoScheduler, anonymousClass1, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        this.L$0 = null;
        this.label = 2;
        if (flowCollector.emit(obj, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
