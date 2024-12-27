package com.android.settingslib.spa.framework.compose;

import androidx.activity.OnBackPressedDispatcher;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavHostController;
import androidx.navigation.NavOptionsBuilder;
import androidx.navigation.NavOptionsBuilderKt;
import androidx.navigation.PopUpToBuilder;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NavControllerWrapperImpl implements NavControllerWrapper {
    public String highlightId;
    public final NavHostController navController;
    public final OnBackPressedDispatcher onBackPressedDispatcher;
    public String sessionName;

    public NavControllerWrapperImpl(
            NavHostController navHostController, OnBackPressedDispatcher onBackPressedDispatcher) {
        this.navController = navHostController;
        this.onBackPressedDispatcher = onBackPressedDispatcher;
    }

    @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
    public final String getHighlightEntryId() {
        return this.highlightId;
    }

    @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
    public final String getSessionSourceName() {
        return this.sessionName;
    }

    @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
    public final void navigate(String route, final boolean z) {
        Intrinsics.checkNotNullParameter(route, "route");
        Function1 function1 =
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl$navigate$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        NavDestination currentDestination;
                        NavOptionsBuilder navigate = (NavOptionsBuilder) obj;
                        Intrinsics.checkNotNullParameter(navigate, "$this$navigate");
                        if (z
                                && (currentDestination = this.navController.getCurrentDestination())
                                        != null) {
                            navigate.popUpTo(
                                    currentDestination.id,
                                    new Function1() { // from class:
                                                      // com.android.settingslib.spa.framework.compose.NavControllerWrapperImpl$navigate$1$1$1
                                        @Override // kotlin.jvm.functions.Function1
                                        public final Object invoke(Object obj2) {
                                            PopUpToBuilder popUpTo = (PopUpToBuilder) obj2;
                                            Intrinsics.checkNotNullParameter(
                                                    popUpTo, "$this$popUpTo");
                                            popUpTo.inclusive = true;
                                            return Unit.INSTANCE;
                                        }
                                    });
                        }
                        return Unit.INSTANCE;
                    }
                };
        NavHostController navHostController = this.navController;
        navHostController.getClass();
        NavController.navigate$default(
                navHostController, route, NavOptionsBuilderKt.navOptions(function1), 4);
    }

    @Override // com.android.settingslib.spa.framework.compose.NavControllerWrapper
    public final void navigateBack() {
        OnBackPressedDispatcher onBackPressedDispatcher = this.onBackPressedDispatcher;
        if (onBackPressedDispatcher != null) {
            onBackPressedDispatcher.onBackPressed();
        }
    }
}
