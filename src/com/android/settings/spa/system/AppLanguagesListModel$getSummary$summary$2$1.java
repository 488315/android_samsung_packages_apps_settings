package com.android.settings.spa.system;

import com.android.settings.applications.appinfo.AppLocaleDetails;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.flow.FlowCollector;

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
            "Lkotlinx/coroutines/flow/FlowCollector;",
            ApnSettings.MVNO_NONE
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppLanguagesListModel$getSummary$summary$2$1 extends SuspendLambda
        implements Function2 {
    final /* synthetic */ AppLanguagesRecord $record;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AppLanguagesListModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppLanguagesListModel$getSummary$summary$2$1(
            AppLanguagesListModel appLanguagesListModel,
            AppLanguagesRecord appLanguagesRecord,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = appLanguagesListModel;
        this.$record = appLanguagesRecord;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AppLanguagesListModel$getSummary$summary$2$1 appLanguagesListModel$getSummary$summary$2$1 =
                new AppLanguagesListModel$getSummary$summary$2$1(
                        this.this$0, this.$record, continuation);
        appLanguagesListModel$getSummary$summary$2$1.L$0 = obj;
        return appLanguagesListModel$getSummary$summary$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppLanguagesListModel$getSummary$summary$2$1)
                        create((FlowCollector) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AppLanguagesListModel appLanguagesListModel = this.this$0;
            String obj2 =
                    AppLocaleDetails.getSummary(appLanguagesListModel.context, this.$record.app)
                            .toString();
            this.label = 1;
            if (flowCollector.emit(obj2, this) == coroutineSingletons) {
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
