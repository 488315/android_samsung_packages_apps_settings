package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.material.icons.automirrored.outlined.ArrowBackKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.material3.IconKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.graphics.vector.PathBuilder;
import androidx.compose.ui.graphics.vector.VectorKt;
import androidx.compose.ui.res.StringResources_androidKt;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.NavControllerWrapper;
import com.android.settingslib.spa.framework.compose.NavControllerWrapperKt;

import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ActionsKt {
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.settingslib.spa.widget.scaffold.ActionsKt$BackAction$1, kotlin.jvm.internal.Lambda] */
    public static final void BackAction(
            final String str, final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-493718307);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(function0) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            IconButtonKt.IconButton(
                    function0,
                    null,
                    false,
                    null,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            -172089504,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.scaffold.ActionsKt$BackAction$1
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
                                    ImageVector imageVector = ArrowBackKt._arrowBack;
                                    if (imageVector == null) {
                                        ImageVector.Builder builder =
                                                new ImageVector.Builder(
                                                        "AutoMirrored.Outlined.ArrowBack",
                                                        24.0f,
                                                        24.0f,
                                                        24.0f,
                                                        24.0f,
                                                        0L,
                                                        0,
                                                        true,
                                                        96);
                                        EmptyList emptyList = VectorKt.EmptyPath;
                                        SolidColor solidColor = new SolidColor(Color.Black);
                                        PathBuilder pathBuilder = new PathBuilder();
                                        pathBuilder.moveTo(20.0f, 11.0f);
                                        pathBuilder.horizontalLineTo(7.83f);
                                        pathBuilder.lineToRelative(5.59f, -5.59f);
                                        pathBuilder.lineTo(12.0f, 4.0f);
                                        pathBuilder.lineToRelative(-8.0f, 8.0f);
                                        pathBuilder.lineToRelative(8.0f, 8.0f);
                                        pathBuilder.lineToRelative(1.41f, -1.41f);
                                        pathBuilder.lineTo(7.83f, 13.0f);
                                        pathBuilder.horizontalLineTo(20.0f);
                                        pathBuilder.verticalLineToRelative(-2.0f);
                                        pathBuilder.close();
                                        ImageVector.Builder.m390addPathoIyEayM$default(
                                                builder, pathBuilder._nodes, solidColor);
                                        imageVector = builder.build();
                                        ArrowBackKt._arrowBack = imageVector;
                                    }
                                    IconKt.m188Iconww6aTOc(
                                            imageVector,
                                            str,
                                            (Modifier) null,
                                            0L,
                                            composer2,
                                            0,
                                            12);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl),
                    composerImpl,
                    ((i2 >> 3) & 14) | 196608,
                    30);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$BackAction$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionsKt.BackAction(
                                    str,
                                    function0,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void ClearAction(final Function0 onClick, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-963093934);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(onClick) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            IconButtonKt.IconButton(
                    onClick,
                    null,
                    false,
                    null,
                    null,
                    ComposableSingletons$ActionsKt.f81lambda2,
                    composerImpl,
                    (i2 & 14) | 196608,
                    30);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$ClearAction$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionsKt.ClearAction(
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void CollapseAction(
            final Function0 onClick, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1041752106);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(onClick) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            BackAction(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.abc_toolbar_collapse_description),
                    onClick,
                    composerImpl,
                    (i2 << 3) & 112);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$CollapseAction$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionsKt.CollapseAction(
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void NavigateBack(Composer composer, final int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1735257205);
        if (i == 0 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final NavControllerWrapper navControllerWrapper =
                    (NavControllerWrapper)
                            composerImpl.consume(NavControllerWrapperKt.LocalNavController);
            BackAction(
                    StringResources_androidKt.stringResource(
                            composerImpl, R.string.abc_action_bar_up_description),
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$NavigateBack$1
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            NavControllerWrapper.this.navigateBack();
                            return Unit.INSTANCE;
                        }
                    },
                    composerImpl,
                    0);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$NavigateBack$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionsKt.NavigateBack(
                                    (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void SearchAction(final Function0 onClick, Composer composer, final int i) {
        int i2;
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-424104645);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(onClick) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            IconButtonKt.IconButton(
                    onClick,
                    null,
                    false,
                    null,
                    null,
                    ComposableSingletons$ActionsKt.f80lambda1,
                    composerImpl,
                    (i2 & 14) | 196608,
                    30);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.ActionsKt$SearchAction$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            ActionsKt.SearchAction(
                                    onClick,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
