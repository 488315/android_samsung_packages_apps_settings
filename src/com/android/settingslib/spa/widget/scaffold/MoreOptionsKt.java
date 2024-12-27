package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.foundation.layout.ColumnScope;
import androidx.compose.material3.AndroidMenu_androidKt;
import androidx.compose.material3.IconButtonKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.RememberSaveableKt;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class MoreOptionsKt {
    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$2, kotlin.jvm.internal.Lambda] */
    public static final void MoreOptionsAction(
            final Function3 content, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl;
        Intrinsics.checkNotNullParameter(content, "content");
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1472534825);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changedInstance(content) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final MutableState mutableState =
                    (MutableState)
                            RememberSaveableKt.rememberSaveable(
                                    new Object[0],
                                    null,
                                    new Function0() { // from class:
                                                      // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$expanded$2
                                        @Override // kotlin.jvm.functions.Function0
                                        /* renamed from: invoke */
                                        public final Object mo1068invoke() {
                                            return SnapshotStateKt.mutableStateOf(
                                                    Boolean.FALSE,
                                                    StructuralEqualityPolicy.INSTANCE);
                                        }
                                    },
                                    composerImpl2,
                                    3080,
                                    6);
            composerImpl2.startReplaceGroup(-861873751);
            boolean changed = composerImpl2.changed(mutableState);
            Object rememberedValue = composerImpl2.rememberedValue();
            Object obj = Composer.Companion.Empty;
            if (changed || rememberedValue == obj) {
                rememberedValue =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                MutableState.this.setValue(Boolean.TRUE);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            composerImpl2.end(false);
            MoreOptionsActionButton((Function0) rememberedValue, composerImpl2, 0);
            composerImpl2.startReplaceGroup(-861873711);
            boolean changed2 = composerImpl2.changed(mutableState);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue2 == obj) {
                rememberedValue2 =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$onDismiss$1$1
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                MutableState.this.setValue(Boolean.FALSE);
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            final Function0 function0 = (Function0) rememberedValue2;
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            AndroidMenu_androidKt.m176DropdownMenuIlH_yew(
                    ((Boolean) mutableState.getValue()).booleanValue(),
                    function0,
                    null,
                    0L,
                    null,
                    null,
                    null,
                    0L,
                    0.0f,
                    0.0f,
                    null,
                    ComposableLambdaKt.rememberComposableLambda(
                            1994292068,
                            new Function3() { // from class:
                                              // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$2
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(3);
                                }

                                @Override // kotlin.jvm.functions.Function3
                                public final Object invoke(Object obj2, Object obj3, Object obj4) {
                                    ColumnScope DropdownMenu = (ColumnScope) obj2;
                                    Composer composer2 = (Composer) obj3;
                                    int intValue = ((Number) obj4).intValue();
                                    Intrinsics.checkNotNullParameter(
                                            DropdownMenu, "$this$DropdownMenu");
                                    if ((intValue & 14) == 0) {
                                        intValue |=
                                                ((ComposerImpl) composer2).changed(DropdownMenu)
                                                        ? 4
                                                        : 2;
                                    }
                                    if ((intValue & 91) == 18) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    ComposerImpl composerImpl4 = (ComposerImpl) composer2;
                                    composerImpl4.startReplaceGroup(-408925460);
                                    boolean z = (intValue & 14) == 4;
                                    Function0 function02 = function0;
                                    Object rememberedValue3 = composerImpl4.rememberedValue();
                                    if (z || rememberedValue3 == Composer.Companion.Empty) {
                                        rememberedValue3 =
                                                new MoreOptionsKt$MoreOptionsAction$2$moreOptionsScope$1$1(
                                                        function02);
                                        composerImpl4.updateRememberedValue(rememberedValue3);
                                    }
                                    composerImpl4.end(false);
                                    Function3.this.invoke(
                                            (MoreOptionsKt$MoreOptionsAction$2$moreOptionsScope$1$1)
                                                    rememberedValue3,
                                            composerImpl4,
                                            0);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2),
                    composerImpl,
                    0,
                    48,
                    2044);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsAction$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            MoreOptionsKt.MoreOptionsAction(
                                    Function3.this,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void MoreOptionsActionButton(
            final Function0 function0, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-2074982344);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i2 & 11) == 2 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            IconButtonKt.IconButton(
                    function0,
                    null,
                    false,
                    null,
                    null,
                    ComposableSingletons$MoreOptionsKt.f88lambda1,
                    composerImpl,
                    (i2 & 14) | 196608,
                    30);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.MoreOptionsKt$MoreOptionsActionButton$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            MoreOptionsKt.MoreOptionsActionButton(
                                    function0,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
