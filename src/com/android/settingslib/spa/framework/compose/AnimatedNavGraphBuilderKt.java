package com.android.settingslib.spa.framework.compose;

import androidx.compose.animation.AnimatedContentTransitionScopeImpl;
import androidx.compose.animation.EnterExitTransitionKt;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.compose.NavGraphBuilderKt;

import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AnimatedNavGraphBuilderKt {
    public static final TweenSpec fadeInEffect;
    public static final TweenSpec fadeOutEffect;
    public static final Function1 offsetFunc;
    public static final TweenSpec slideInEffect;
    public static final TweenSpec slideOutEffect;

    static {
        CubicBezierEasing cubicBezierEasing = EasingKt.LinearOutSlowInEasing;
        slideInEffect = new TweenSpec(300, 75, cubicBezierEasing);
        slideOutEffect = AnimationSpecKt.tween$default(300, 0, null, 6);
        fadeOutEffect = AnimationSpecKt.tween$default(75, 0, EasingKt.FastOutLinearInEasing, 2);
        fadeInEffect = new TweenSpec(300, 75, cubicBezierEasing);
        offsetFunc =
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt$offsetFunc$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Integer.valueOf(((Number) obj).intValue() / 5);
                    }
                };
    }

    public static void animatedComposable$default(
            NavGraphBuilder navGraphBuilder,
            String route,
            List arguments,
            ComposableLambdaImpl composableLambdaImpl) {
        EmptyList deepLinks = EmptyList.INSTANCE;
        Intrinsics.checkNotNullParameter(navGraphBuilder, "<this>");
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(deepLinks, "deepLinks");
        NavGraphBuilderKt.composable$default(
                navGraphBuilder,
                route,
                arguments,
                deepLinks,
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt$animatedComposable$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AnimatedContentTransitionScopeImpl composable =
                                (AnimatedContentTransitionScopeImpl) obj;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        if (Intrinsics.areEqual(
                                ((NavBackStackEntry) composable.getInitialState())
                                        .destination
                                        .route,
                                "NULL")) {
                            return null;
                        }
                        return composable
                                .m10slideIntoContainermOhB8PU(
                                        4,
                                        AnimatedNavGraphBuilderKt.slideInEffect,
                                        AnimatedNavGraphBuilderKt.offsetFunc)
                                .plus(
                                        EnterExitTransitionKt.fadeIn$default(
                                                AnimatedNavGraphBuilderKt.fadeInEffect, 2));
                    }
                },
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt$animatedComposable$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AnimatedContentTransitionScopeImpl composable =
                                (AnimatedContentTransitionScopeImpl) obj;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        return composable
                                .m11slideOutOfContainermOhB8PU(
                                        4,
                                        AnimatedNavGraphBuilderKt.slideOutEffect,
                                        AnimatedNavGraphBuilderKt.offsetFunc)
                                .plus(
                                        EnterExitTransitionKt.fadeOut$default(
                                                AnimatedNavGraphBuilderKt.fadeOutEffect, 2));
                    }
                },
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt$animatedComposable$3
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AnimatedContentTransitionScopeImpl composable =
                                (AnimatedContentTransitionScopeImpl) obj;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        return composable
                                .m10slideIntoContainermOhB8PU(
                                        5,
                                        AnimatedNavGraphBuilderKt.slideInEffect,
                                        AnimatedNavGraphBuilderKt.offsetFunc)
                                .plus(
                                        EnterExitTransitionKt.fadeIn$default(
                                                AnimatedNavGraphBuilderKt.fadeInEffect, 2));
                    }
                },
                new Function1() { // from class:
                                  // com.android.settingslib.spa.framework.compose.AnimatedNavGraphBuilderKt$animatedComposable$4
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        AnimatedContentTransitionScopeImpl composable =
                                (AnimatedContentTransitionScopeImpl) obj;
                        Intrinsics.checkNotNullParameter(composable, "$this$composable");
                        return composable
                                .m11slideOutOfContainermOhB8PU(
                                        5,
                                        AnimatedNavGraphBuilderKt.slideOutEffect,
                                        AnimatedNavGraphBuilderKt.offsetFunc)
                                .plus(
                                        EnterExitTransitionKt.fadeOut$default(
                                                AnimatedNavGraphBuilderKt.fadeOutEffect, 2));
                    }
                },
                composableLambdaImpl,
                128);
    }
}
