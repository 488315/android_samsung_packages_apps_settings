package com.android.settingslib.spaprivileged.model.app;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0010\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003*\u00020\u0004H\u008a@"
        },
        d2 = {
            "<anonymous>",
            ApnSettings.MVNO_NONE,
            "T",
            "Lcom/android/settingslib/spaprivileged/model/app/AppRecord;",
            "Lkotlinx/coroutines/CoroutineScope;"
        },
        k = 3,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes2.dex */
final class AppListViewModelImpl$UserSubGraph$reloadApps$1 extends SuspendLambda
        implements Function2 {
    Object L$0;
    int label;
    final /* synthetic */ AppListViewModelImpl.UserSubGraph this$0;
    final /* synthetic */ AppListViewModelImpl this$1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppListViewModelImpl$UserSubGraph$reloadApps$1(
            AppListViewModelImpl.UserSubGraph userSubGraph,
            AppListViewModelImpl appListViewModelImpl,
            Continuation continuation) {
        super(2, continuation);
        this.this$0 = userSubGraph;
        this.this$1 = appListViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AppListViewModelImpl$UserSubGraph$reloadApps$1(
                this.this$0, this.this$1, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AppListViewModelImpl$UserSubGraph$reloadApps$1)
                        create((CoroutineScope) obj, (Continuation) obj2))
                .invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MutableStateFlow mutableStateFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AppListViewModelImpl.UserSubGraph userSubGraph = this.this$0;
            StateFlowImpl stateFlowImpl = userSubGraph.appsStateFlow;
            AppListRepository appListRepository = this.this$1.appListRepository;
            this.L$0 = stateFlowImpl;
            this.label = 1;
            obj =
                    ((AppListRepositoryImpl) appListRepository)
                            .loadApps(
                                    userSubGraph.userId,
                                    userSubGraph.showInstantApps,
                                    userSubGraph.matchAnyUserForAdmin,
                                    this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            mutableStateFlow = stateFlowImpl;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            mutableStateFlow = (MutableStateFlow) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        ((StateFlowImpl) mutableStateFlow).setValue(obj);
        return Unit.INSTANCE;
    }
}
