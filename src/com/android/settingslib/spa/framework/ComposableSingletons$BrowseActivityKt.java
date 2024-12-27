package com.android.settingslib.spa.framework;

import androidx.compose.animation.AnimatedContentScope;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.navigation.NavBackStackEntry;

import kotlin.Unit;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ComposableSingletons$BrowseActivityKt {

    /* renamed from: lambda-1, reason: not valid java name */
    public static final ComposableLambdaImpl f71lambda1 =
            new ComposableLambdaImpl(
                    416282417,
                    false,
                    new Function4() { // from class:
                                      // com.android.settingslib.spa.framework.ComposableSingletons$BrowseActivityKt$lambda-1$1
                        @Override // kotlin.jvm.functions.Function4
                        public final Object invoke(
                                Object obj, Object obj2, Object obj3, Object obj4) {
                            AnimatedContentScope composable = (AnimatedContentScope) obj;
                            NavBackStackEntry it = (NavBackStackEntry) obj2;
                            ((Number) obj4).intValue();
                            Intrinsics.checkNotNullParameter(composable, "$this$composable");
                            Intrinsics.checkNotNullParameter(it, "it");
                            OpaqueKey opaqueKey = ComposerKt.invocation;
                            return Unit.INSTANCE;
                        }
                    });
}
