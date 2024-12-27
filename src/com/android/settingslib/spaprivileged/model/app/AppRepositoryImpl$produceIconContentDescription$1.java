package com.android.settingslib.spaprivileged.model.app;

import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.ProduceStateScope;
import androidx.compose.runtime.ProduceStateScopeImpl;

import com.android.settings.R;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.knox.net.apn.ApnSettings;

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
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u0001*\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/compose/runtime/ProduceStateScope;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppRepositoryImpl$produceIconContentDescription$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ ApplicationInfo $app;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppRepositoryImpl this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u0002\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
            },
            d2 = {"<anonymous>", ApnSettings.MVNO_NONE, "Lkotlinx/coroutines/CoroutineScope;"},
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppRepositoryImpl$produceIconContentDescription$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProduceStateScope $$this$produceState;
        final /* synthetic */ ApplicationInfo $app;
        int label;
        final /* synthetic */ AppRepositoryImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                ProduceStateScope produceStateScope,
                AppRepositoryImpl appRepositoryImpl,
                ApplicationInfo applicationInfo,
                Continuation continuation) {
            super(2, continuation);
            this.$$this$produceState = produceStateScope;
            this.this$0 = appRepositoryImpl;
            this.$app = applicationInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.$$this$produceState, this.this$0, this.$app, continuation);
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
            ((ProduceStateScopeImpl) this.$$this$produceState)
                    .setValue(
                            ContextsKt.getUserManager(this.this$0.context)
                                            .isManagedProfile(
                                                    ApplicationInfosKt.getUserId(this.$app))
                                    ? this.this$0.context.getString(R.string.category_work)
                                    : null);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppRepositoryImpl$produceIconContentDescription$1(
            AppRepositoryImpl appRepositoryImpl,
            ApplicationInfo applicationInfo,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appRepositoryImpl;
        this.$app = applicationInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppRepositoryImpl$produceIconContentDescription$1
                appRepositoryImpl$produceIconContentDescription$1 =
                        new AppRepositoryImpl$produceIconContentDescription$1(
                                this.this$0, this.$app, continuation);
        appRepositoryImpl$produceIconContentDescription$1.L$0 = obj;
        return appRepositoryImpl$produceIconContentDescription$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppRepositoryImpl$produceIconContentDescription$1)
                        create((ProduceStateScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProduceStateScope produceStateScope = (ProduceStateScope) this.L$0;
            DefaultIoScheduler defaultIoScheduler = Dispatchers.IO;
            AnonymousClass1 anonymousClass1 =
                    new AnonymousClass1(produceStateScope, this.this$0, this.$app, null);
            this.label = 1;
            if (BuildersKt.withContext(defaultIoScheduler, anonymousClass1, this)
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
