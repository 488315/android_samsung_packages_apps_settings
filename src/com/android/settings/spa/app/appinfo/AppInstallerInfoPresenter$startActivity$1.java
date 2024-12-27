package com.android.settings.spa.app.appinfo;

import android.content.Intent;

import com.android.settingslib.spaprivileged.model.app.ApplicationInfosKt;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.KotlinNothingValueException;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

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
/* loaded from: classes2.dex */
final class AppInstallerInfoPresenter$startActivity$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AppInstallerInfoPresenter this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppInstallerInfoPresenter$startActivity$1(
            AppInstallerInfoPresenter appInstallerInfoPresenter, Continuation continuation) {
        super(2, continuation);
        this.this$0 = appInstallerInfoPresenter;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppInstallerInfoPresenter$startActivity$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        ((AppInstallerInfoPresenter$startActivity$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
        return CoroutineSingletons.COROUTINE_SUSPENDED;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final AppInstallerInfoPresenter appInstallerInfoPresenter = this.this$0;
            ReadonlySharedFlow readonlySharedFlow = appInstallerInfoPresenter.intentFlow;
            FlowCollector flowCollector =
                    new FlowCollector() { // from class:
                                          // com.android.settings.spa.app.appinfo.AppInstallerInfoPresenter$startActivity$1.1
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            Intent intent = (Intent) obj2;
                            if (intent != null) {
                                AppInstallerInfoPresenter appInstallerInfoPresenter2 =
                                        AppInstallerInfoPresenter.this;
                                appInstallerInfoPresenter2.context.startActivityAsUser(
                                        intent,
                                        ApplicationInfosKt.getUserHandle(
                                                appInstallerInfoPresenter2.app));
                            }
                            return Unit.INSTANCE;
                        }
                    };
            this.label = 1;
            if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this)
                    == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
