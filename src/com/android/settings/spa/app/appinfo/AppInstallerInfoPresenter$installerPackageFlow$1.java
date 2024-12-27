package com.android.settings.spa.app.appinfo;

import android.content.pm.InstallSourceInfo;
import android.util.Pair;

import com.android.settings.applications.AppStoreUtil;

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
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.scheduling.DefaultIoScheduler;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\u0010\u0000\u001a\u00020\u0001*\u0010\u0012\f\u0012\n"
                + " \u0004*\u0004\u0018\u00010\u00030\u00030\u0002H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE,
            "kotlin.jvm.PlatformType"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppInstallerInfoPresenter$installerPackageFlow$1 extends SuspendLambda
        implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppInstallerInfoPresenter this$0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\f\n"
                    + "\u0000\n"
                    + "\u0002\u0010\u000e\n"
                    + "\u0000\n"
                    + "\u0002\u0018\u0002\u0010\u0000\u001a\n"
                    + " \u0002*\u0004\u0018\u00010\u00010\u0001*\u00020\u0003H\u008a@"
            },
            d2 = {
                "<anonymous>",
                ApnSettings.MVNO_NONE,
                "kotlin.jvm.PlatformType",
                "Lkotlinx/coroutines/CoroutineScope;"
            },
            k = 3,
            mv = {1, 9, 0},
            xi = 48)
    /* renamed from: com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$installerPackageFlow$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ AppInstallerInfoPresenter this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(
                AppInstallerInfoPresenter appInstallerInfoPresenter, Continuation continuation) {
            super(2, continuation);
            this.this$0 = appInstallerInfoPresenter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
            AppInstallerInfoPresenter appInstallerInfoPresenter = this.this$0;
            Pair installerPackageNameAndInstallSourceInfo =
                    AppStoreUtil.getInstallerPackageNameAndInstallSourceInfo(
                            appInstallerInfoPresenter.userContext,
                            appInstallerInfoPresenter.app.packageName);
            this.this$0.installSourceInfo =
                    (InstallSourceInfo) installerPackageNameAndInstallSourceInfo.second;
            return installerPackageNameAndInstallSourceInfo.first;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppInstallerInfoPresenter$installerPackageFlow$1(
            AppInstallerInfoPresenter appInstallerInfoPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appInstallerInfoPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppInstallerInfoPresenter$installerPackageFlow$1
                appInstallerInfoPresenter$installerPackageFlow$1 =
                        new AppInstallerInfoPresenter$installerPackageFlow$1(
                                this.this$0, continuation);
        appInstallerInfoPresenter$installerPackageFlow$1.L$0 = obj;
        return appInstallerInfoPresenter$installerPackageFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppInstallerInfoPresenter$installerPackageFlow$1)
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
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
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
