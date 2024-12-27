package com.android.settingslib.spa.framework;

import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.NavOptionsBuilderKt;
import androidx.navigation.PopUpToBuilder;

import com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.CoroutineScope;

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
final class BrowseActivityKt$InitialDestination$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $initialDestination;
    final /* synthetic */ String $initialEntryId;
    final /* synthetic */ String $sessionSourceName;
    final /* synthetic */ NavControllerWrapperImpl $this_InitialDestination;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BrowseActivityKt$InitialDestination$3(
            NavControllerWrapperImpl navControllerWrapperImpl,
            String str,
            String str2,
            String str3,
            Continuation continuation) {
        super(2, continuation);
        this.$this_InitialDestination = navControllerWrapperImpl;
        this.$initialEntryId = str;
        this.$sessionSourceName = str2;
        this.$initialDestination = str3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BrowseActivityKt$InitialDestination$3(
                this.$this_InitialDestination,
                this.$initialEntryId,
                this.$sessionSourceName,
                this.$initialDestination,
                continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        BrowseActivityKt$InitialDestination$3 browseActivityKt$InitialDestination$3 =
                (BrowseActivityKt$InitialDestination$3)
                        create((CoroutineScope) obj, (Continuation) obj2);
        Unit unit = Unit.INSTANCE;
        browseActivityKt$InitialDestination$3.invokeSuspend(unit);
        return unit;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final NavControllerWrapperImpl navControllerWrapperImpl = this.$this_InitialDestination;
        navControllerWrapperImpl.highlightId = this.$initialEntryId;
        navControllerWrapperImpl.sessionName = this.$sessionSourceName;
        String route = this.$initialDestination;
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.BrowseActivityKt$InitialDestination$3.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj2) {
                        NavOptionsBuilder navigate = (NavOptionsBuilder) obj2;
                        Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                        int i = NavGraph.$r8$clinit;
                        navigate.popUpTo(
                                NavGraph.Companion.findStartDestination(
                                                NavControllerWrapperImpl.this.navController
                                                        .getGraph())
                                        .id,
                                new Function1() { // from class:
                                                  // com.android.settingslib.spa.framework.BrowseActivityKt.InitialDestination.3.1.1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj3) {
                                        PopUpToBuilder popUpTo = (PopUpToBuilder) obj3;
                                        Intrinsics.checkNotNullParameter(popUpTo, "$this$popUpTo");
                                        popUpTo.inclusive = true;
                                        return Unit.INSTANCE;
                                    }
                                });
                        return Unit.INSTANCE;
                    }
                };
        NavHostController navHostController = navControllerWrapperImpl.navController;
        navHostController.getClass();
        Intrinsics.checkNotNullParameter(route, "route");
        NavController.navigate$default(
                navHostController, route, NavOptionsBuilderKt.navOptions(function1), 4);
        return Unit.INSTANCE;
    }
}
