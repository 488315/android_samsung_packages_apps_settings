package com.android.settingslib.spaprivileged.model.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.UserInfo;

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
import kotlin.jvm.internal.Intrinsics;

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
                + "\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"
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
final class AppRepository$produceLabel$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ApplicationInfo $app;
    final /* synthetic */ Context $context;
    final /* synthetic */ boolean $isClonedAppPage;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppRepository this$0;

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
    /* renamed from: com.android.settingslib.spaprivileged.model.app.AppRepository$produceLabel$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProduceStateScope $$this$produceState;
        final /* synthetic */ ApplicationInfo $app;
        final /* synthetic */ Context $context;
        final /* synthetic */ boolean $isClonedAppPage;
        int label;
        final /* synthetic */ AppRepository this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                ProduceStateScope produceStateScope,
                boolean z,
                AppRepository appRepository,
                Context context,
                ApplicationInfo applicationInfo,
                Continuation continuation) {
            super(2, continuation);
            this.$$this$produceState = produceStateScope;
            this.$isClonedAppPage = z;
            this.this$0 = appRepository;
            this.$context = context;
            this.$app = applicationInfo;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.$$this$produceState,
                    this.$isClonedAppPage,
                    this.this$0,
                    this.$context,
                    this.$app,
                    continuation);
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
            String string;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ProduceStateScope produceStateScope = this.$$this$produceState;
            if (!this.$isClonedAppPage) {
                AppRepository appRepository = this.this$0;
                Context context = this.$context;
                ApplicationInfo applicationInfo = this.$app;
                appRepository.getClass();
                UserInfo userInfo =
                        ContextsKt.getUserManager(context)
                                .getUserInfo(ApplicationInfosKt.getUserId(applicationInfo));
                if (userInfo == null || !userInfo.isCloneProfile()) {
                    AppRepository appRepository2 = this.this$0;
                    ApplicationInfo app = this.$app;
                    AppRepositoryImpl appRepositoryImpl = (AppRepositoryImpl) appRepository2;
                    appRepositoryImpl.getClass();
                    Intrinsics.checkNotNullParameter(app, "app");
                    string = app.loadLabel(appRepositoryImpl.packageManager).toString();
                    ((ProduceStateScopeImpl) produceStateScope).setValue(string);
                    return Unit.INSTANCE;
                }
            }
            Context context2 = this.$context;
            AppRepository appRepository3 = this.this$0;
            ApplicationInfo app2 = this.$app;
            AppRepositoryImpl appRepositoryImpl2 = (AppRepositoryImpl) appRepository3;
            appRepositoryImpl2.getClass();
            Intrinsics.checkNotNullParameter(app2, "app");
            string =
                    context2.getString(
                            R.string.cloned_app_info_label,
                            app2.loadLabel(appRepositoryImpl2.packageManager).toString());
            Intrinsics.checkNotNull(string);
            ((ProduceStateScopeImpl) produceStateScope).setValue(string);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppRepository$produceLabel$1(
            boolean z,
            AppRepository appRepository,
            Context context,
            ApplicationInfo applicationInfo,
            Continuation continuation) {
        super(2, continuation);
        this.$isClonedAppPage = z;
        this.this$0 = appRepository;
        this.$context = context;
        this.$app = applicationInfo;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppRepository$produceLabel$1 appRepository$produceLabel$1 =
                new AppRepository$produceLabel$1(
                        this.$isClonedAppPage, this.this$0, this.$context, this.$app, continuation);
        appRepository$produceLabel$1.L$0 = obj;
        return appRepository$produceLabel$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppRepository$produceLabel$1) create((ProduceStateScope) obj, (Continuation) obj2))
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
                    new AnonymousClass1(
                            produceStateScope,
                            this.$isClonedAppPage,
                            this.this$0,
                            this.$context,
                            this.$app,
                            null);
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
