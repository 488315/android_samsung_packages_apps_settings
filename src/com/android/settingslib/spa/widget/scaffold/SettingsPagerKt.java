package com.android.settingslib.spa.widget.scaffold;

import androidx.activity.compose.PredictiveBackHandlerKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.ColumnKt;
import androidx.compose.foundation.layout.ColumnMeasurePolicy;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.pager.DefaultPagerState;
import androidx.compose.foundation.pager.PagerKt;
import androidx.compose.foundation.pager.PagerScopeImpl;
import androidx.compose.foundation.pager.PagerState;
import androidx.compose.foundation.pager.PagerStateKt;
import androidx.compose.material3.TabRowKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionScopedCoroutineScopeCanceller;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.node.ComposeUiNode;

import com.android.settingslib.spa.framework.theme.SettingsDimension;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SettingsPagerKt {
    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$2, kotlin.jvm.internal.Lambda] */
    public static final void SettingsPager(
            final List titles, final Function3 content, Composer composer, final int i) {
        Intrinsics.checkNotNullParameter(titles, "titles");
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1649302163);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        if (!(!titles.isEmpty())) {
            throw new IllegalStateException("Check failed.".toString());
        }
        composerImpl.startReplaceGroup(1559326644);
        if (titles.size() == 1) {
            content.invoke(0, composerImpl, Integer.valueOf((i & 112) | 6));
            composerImpl.end(false);
            RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
            if (endRestartGroup != null) {
                endRestartGroup.block =
                        new Function2() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                ((Number) obj2).intValue();
                                SettingsPagerKt.SettingsPager(
                                        titles,
                                        content,
                                        (Composer) obj,
                                        RecomposeScopeImplKt.updateChangedFlags(i | 1));
                                return Unit.INSTANCE;
                            }
                        };
                return;
            }
            return;
        }
        composerImpl.end(false);
        Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
        ColumnMeasurePolicy columnMeasurePolicy =
                ColumnKt.columnMeasurePolicy(
                        Arrangement.Top, Alignment.Companion.Start, composerImpl, 0);
        int i2 = composerImpl.compoundKeyHash;
        PersistentCompositionLocalMap currentCompositionLocalScope =
                composerImpl.currentCompositionLocalScope();
        Modifier materializeModifier =
                ComposedModifierKt.materializeModifier(composerImpl, companion);
        ComposeUiNode.Companion.getClass();
        Function0 function0 = ComposeUiNode.Companion.Constructor;
        if (!(composerImpl.applier instanceof Applier)) {
            ComposablesKt.invalidApplier();
            throw null;
        }
        composerImpl.startReusableNode();
        if (composerImpl.inserting) {
            composerImpl.createNode(function0);
        } else {
            composerImpl.useNode();
        }
        Updater.m221setimpl(
                composerImpl, columnMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
        Updater.m221setimpl(
                composerImpl,
                currentCompositionLocalScope,
                ComposeUiNode.Companion.SetResolvedCompositionLocals);
        Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
        if (composerImpl.inserting
                || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i2))) {
            AnimatedContentKt$$ExternalSyntheticOutline0.m(i2, composerImpl, i2, function2);
        }
        Updater.m221setimpl(composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
        Object rememberedValue = composerImpl.rememberedValue();
        if (rememberedValue == Composer.Companion.Empty) {
            rememberedValue =
                    PredictiveBackHandlerKt$$ExternalSyntheticOutline0.m(
                            EffectsKt.createCompositionCoroutineScope(
                                    EmptyCoroutineContext.INSTANCE, composerImpl),
                            composerImpl);
        }
        CoroutineScope coroutineScope =
                ((CompositionScopedCoroutineScopeCanceller) rememberedValue).coroutineScope;
        final DefaultPagerState rememberPagerState =
                PagerStateKt.rememberPagerState(
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$pagerState$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Integer.valueOf(titles.size());
                            }
                        },
                        composerImpl);
        final ContextScope contextScope = (ContextScope) coroutineScope;
        TabRowKt.m204TabRowpAZo6Ak(
                rememberPagerState.getCurrentPage(),
                PaddingKt.m87paddingVpY3zN4$default(
                        companion, SettingsDimension.itemPaddingEnd, 0.0f, 2),
                Color.Transparent,
                0L,
                ComposableSingletons$SettingsPagerKt.f95lambda1,
                ComposableSingletons$SettingsPagerKt.f96lambda2,
                ComposableLambdaKt.rememberComposableLambda(
                        628394529,
                        new Function2() { // from class:
                            // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(2);
                            }

                            @Override // kotlin.jvm.functions.Function2
                            public final Object invoke(Object obj, Object obj2) {
                                Composer composer2 = (Composer) obj;
                                if ((((Number) obj2).intValue() & 11) == 2) {
                                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                    if (composerImpl2.getSkipping()) {
                                        composerImpl2.skipToGroupEnd();
                                        return Unit.INSTANCE;
                                    }
                                }
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                List<String> list = titles;
                                final PagerState pagerState = rememberPagerState;
                                final CoroutineScope coroutineScope2 = contextScope;
                                final int i3 = 0;
                                for (Object obj3 : list) {
                                    int i4 = i3 + 1;
                                    if (i3 < 0) {
                                        CollectionsKt__CollectionsKt.throwIndexOverflow();
                                        throw null;
                                    }
                                    SettingsTabKt.SettingsTab(
                                            (String) obj3,
                                            pagerState.getCurrentPage() == i3,
                                            Math.abs(
                                                    pagerState.scrollPosition
                                                            .getCurrentPageOffsetFraction()),
                                            new Function0() { // from class:
                                                // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$1$1$1

                                                /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                                                @Metadata(
                                                        d1 = {
                                                            "\u0000\n\n"
                                                                + "\u0000\n"
                                                                + "\u0002\u0010\u0002\n"
                                                                + "\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u008a@"
                                                        },
                                                        d2 = {
                                                            "<anonymous>",
                                                            ApnSettings.MVNO_NONE,
                                                            "Lkotlinx/coroutines/CoroutineScope;"
                                                        },
                                                        k = 3,
                                                        mv = {1, 9, 0},
                                                        xi = 48)
                                                /* renamed from: com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$1$1$1$1, reason: invalid class name */
                                                final class AnonymousClass1 extends SuspendLambda
                                                        implements Function2 {
                                                    final /* synthetic */ int $page;
                                                    final /* synthetic */ PagerState $pagerState;
                                                    int label;

                                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                    public AnonymousClass1(
                                                            PagerState pagerState,
                                                            int i,
                                                            Continuation continuation) {
                                                        super(2, continuation);
                                                        this.$pagerState = pagerState;
                                                        this.$page = i;
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Continuation create(
                                                            Object obj, Continuation continuation) {
                                                        return new AnonymousClass1(
                                                                this.$pagerState,
                                                                this.$page,
                                                                continuation);
                                                    }

                                                    @Override // kotlin.jvm.functions.Function2
                                                    public final Object invoke(
                                                            Object obj, Object obj2) {
                                                        return ((AnonymousClass1)
                                                                        create(
                                                                                (CoroutineScope)
                                                                                        obj,
                                                                                (Continuation)
                                                                                        obj2))
                                                                .invokeSuspend(Unit.INSTANCE);
                                                    }

                                                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                                    public final Object invokeSuspend(Object obj) {
                                                        Object animateScrollToPage;
                                                        CoroutineSingletons coroutineSingletons =
                                                                CoroutineSingletons
                                                                        .COROUTINE_SUSPENDED;
                                                        int i = this.label;
                                                        if (i == 0) {
                                                            ResultKt.throwOnFailure(obj);
                                                            PagerState pagerState =
                                                                    this.$pagerState;
                                                            int i2 = this.$page;
                                                            this.label = 1;
                                                            animateScrollToPage =
                                                                    pagerState.animateScrollToPage(
                                                                            i2,
                                                                            0.0f,
                                                                            AnimationSpecKt
                                                                                    .spring$default(
                                                                                            0.0f,
                                                                                            null,
                                                                                            7),
                                                                            this);
                                                            if (animateScrollToPage
                                                                    == coroutineSingletons) {
                                                                return coroutineSingletons;
                                                            }
                                                        } else {
                                                            if (i != 1) {
                                                                throw new IllegalStateException(
                                                                        "call to 'resume' before"
                                                                            + " 'invoke' with"
                                                                            + " coroutine");
                                                            }
                                                            ResultKt.throwOnFailure(obj);
                                                        }
                                                        return Unit.INSTANCE;
                                                    }
                                                }

                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                {
                                                    super(0);
                                                }

                                                @Override // kotlin.jvm.functions.Function0
                                                /* renamed from: invoke */
                                                public final Object mo1068invoke() {
                                                    BuildersKt.launch$default(
                                                            CoroutineScope.this,
                                                            null,
                                                            null,
                                                            new AnonymousClass1(
                                                                    pagerState, i3, null),
                                                            3);
                                                    return Unit.INSTANCE;
                                                }
                                            },
                                            composer2,
                                            0);
                                    i3 = i4;
                                }
                                OpaqueKey opaqueKey3 = ComposerKt.invocation;
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                1794480,
                8);
        PagerKt.m114HorizontalPageroI3XNZo(
                rememberPagerState,
                null,
                null,
                null,
                0,
                0.0f,
                null,
                null,
                false,
                false,
                null,
                null,
                null,
                ComposableLambdaKt.rememberComposableLambda(
                        -1032171093,
                        new Function4() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$2$2
                            {
                                super(4);
                            }

                            @Override // kotlin.jvm.functions.Function4
                            public final Object invoke(
                                    Object obj, Object obj2, Object obj3, Object obj4) {
                                PagerScopeImpl HorizontalPager = (PagerScopeImpl) obj;
                                int intValue = ((Number) obj2).intValue();
                                int intValue2 = ((Number) obj4).intValue();
                                Intrinsics.checkNotNullParameter(
                                        HorizontalPager, "$this$HorizontalPager");
                                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                Function3.this.invoke(
                                        Integer.valueOf(intValue),
                                        (Composer) obj3,
                                        Integer.valueOf((intValue2 >> 3) & 14));
                                return Unit.INSTANCE;
                            }
                        },
                        composerImpl),
                composerImpl,
                0,
                3072,
                8190);
        composerImpl.end(true);
        RecomposeScopeImpl endRestartGroup2 = composerImpl.endRestartGroup();
        if (endRestartGroup2 != null) {
            endRestartGroup2.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SettingsPagerKt$SettingsPager$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SettingsPagerKt.SettingsPager(
                                    titles,
                                    content,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
