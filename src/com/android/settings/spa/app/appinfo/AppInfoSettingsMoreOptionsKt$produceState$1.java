package com.android.settings.spa.app.appinfo;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import androidx.compose.runtime.ProduceStateScope;
import androidx.compose.runtime.ProduceStateScopeImpl;

import com.android.settingslib.spaprivileged.model.app.IPackageManagers;

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
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u000e\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\n"
                + "\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Landroidx/compose/runtime/ProduceStateScope;",
            "Lcom/android/settings/spa/app/appinfo/AppInfoSettingsMoreOptionsState;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppInfoSettingsMoreOptionsKt$produceState$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ IPackageManagers $packageManagers;
    final /* synthetic */ ApplicationInfo $this_produceState;
    private /* synthetic */ Object L$0;
    int label;

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
    /* renamed from: com.android.settings.spa.app.appinfo.AppInfoSettingsMoreOptionsKt$produceState$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ ProduceStateScope $$this$produceState;
        final /* synthetic */ Context $context;
        final /* synthetic */ IPackageManagers $packageManagers;
        final /* synthetic */ ApplicationInfo $this_produceState;
        Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                ProduceStateScope produceStateScope,
                ApplicationInfo applicationInfo,
                Context context,
                IPackageManagers iPackageManagers,
                Continuation continuation) {
            super(2, continuation);
            this.$$this$produceState = produceStateScope;
            this.$this_produceState = applicationInfo;
            this.$context = context;
            this.$packageManagers = iPackageManagers;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(
                    this.$$this$produceState,
                    this.$this_produceState,
                    this.$context,
                    this.$packageManagers,
                    continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2))
                    .invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            ProduceStateScope produceStateScope;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ProduceStateScope produceStateScope2 = this.$$this$produceState;
                ApplicationInfo applicationInfo = this.$this_produceState;
                Context context = this.$context;
                IPackageManagers iPackageManagers = this.$packageManagers;
                this.L$0 = produceStateScope2;
                this.label = 1;
                Object coroutineScope =
                        CoroutineScopeKt.coroutineScope(
                                new AppInfoSettingsMoreOptionsKt$getMoreOptionsState$2(
                                        context, applicationInfo, iPackageManagers, null),
                                this);
                if (coroutineScope == coroutineSingletons) {
                    return coroutineSingletons;
                }
                obj = coroutineScope;
                produceStateScope = produceStateScope2;
            } else {
                if (i != 1) {
                    throw new IllegalStateException(
                            "call to 'resume' before 'invoke' with coroutine");
                }
                produceStateScope = (ProduceStateScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            ((ProduceStateScopeImpl) produceStateScope).setValue(obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppInfoSettingsMoreOptionsKt$produceState$1(
            Context context,
            ApplicationInfo applicationInfo,
            IPackageManagers iPackageManagers,
            Continuation continuation) {
        super(2, continuation);
        this.$this_produceState = applicationInfo;
        this.$context = context;
        this.$packageManagers = iPackageManagers;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppInfoSettingsMoreOptionsKt$produceState$1 appInfoSettingsMoreOptionsKt$produceState$1 =
                new AppInfoSettingsMoreOptionsKt$produceState$1(
                        this.$context,
                        this.$this_produceState,
                        this.$packageManagers,
                        continuation);
        appInfoSettingsMoreOptionsKt$produceState$1.L$0 = obj;
        return appInfoSettingsMoreOptionsKt$produceState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppInfoSettingsMoreOptionsKt$produceState$1)
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
                    new AnonymousClass1(
                            produceStateScope,
                            this.$this_produceState,
                            this.$context,
                            this.$packageManagers,
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
