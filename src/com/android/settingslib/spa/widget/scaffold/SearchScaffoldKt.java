package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.foundation.layout.RowScope;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.text.KeyboardActionRunner;
import androidx.compose.foundation.text.KeyboardActions;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material3.ExitUntilCollapsedScrollBehavior;
import androidx.compose.material3.TextFieldDefaults;
import androidx.compose.material3.TextFieldKt;
import androidx.compose.material3.TextKt;
import androidx.compose.material3.TopAppBarScrollBehavior;
import androidx.compose.material3.TopAppBarState;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.AlphaKt;
import androidx.compose.ui.focus.FocusRequester;
import androidx.compose.ui.focus.FocusRequesterModifierKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.input.TextFieldValue;

import com.android.settings.R;
import com.android.settingslib.spa.framework.compose.KeyboardsKt;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class SearchScaffoldKt {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0055  */
    /* JADX WARN: Type inference failed for: r12v1, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchScaffold$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchScaffold$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SearchScaffold(
            final java.lang.String r25,
            kotlin.jvm.functions.Function3 r26,
            final kotlin.jvm.functions.Function4 r27,
            androidx.compose.runtime.Composer r28,
            final int r29,
            final int r30) {
        /*
            Method dump skipped, instructions count: 325
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt.SearchScaffold(java.lang.String,"
                    + " kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function4,"
                    + " androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008e  */
    /* JADX WARN: Type inference failed for: r6v18, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchTopAppBar$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r7v4, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchTopAppBar$2, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r8v6, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchTopAppBar$3, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SearchTopAppBar(
            final androidx.compose.ui.text.input.TextFieldValue r15,
            final kotlin.jvm.functions.Function1 r16,
            final kotlin.jvm.functions.Function0 r17,
            kotlin.jvm.functions.Function3 r18,
            androidx.compose.runtime.Composer r19,
            final int r20,
            final int r21) {
        /*
            Method dump skipped, instructions count: 253
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt.SearchTopAppBar(androidx.compose.ui.text.input.TextFieldValue,"
                    + " kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0,"
                    + " kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int,"
                    + " int):void");
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchBox$1, kotlin.jvm.internal.Lambda] */
    public static final void access$SearchBox(
            final TextFieldValue textFieldValue,
            final Function1 function1,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl;
        FocusRequester focusRequester;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(531369344);
        if ((i & 14) == 0) {
            i2 = (composerImpl2.changed(textFieldValue) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl2.changedInstance(function1) ? 32 : 16;
        }
        if ((i2 & 91) == 18 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            composerImpl2.startReplaceGroup(1574560222);
            Object rememberedValue = composerImpl2.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (rememberedValue == composer$Companion$Empty$1) {
                rememberedValue = new FocusRequester();
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            FocusRequester focusRequester2 = (FocusRequester) rememberedValue;
            composerImpl2.end(false);
            final TextStyle textStyle =
                    ((Typography) composerImpl2.consume(TypographyKt.LocalTypography)).bodyLarge;
            final Function0 hideKeyboardAction = KeyboardsKt.hideKeyboardAction(composerImpl2);
            Modifier focusRequester3 =
                    FocusRequesterModifierKt.focusRequester(
                            SizeKt.FillWholeMaxWidth, focusRequester2);
            ComposableLambdaImpl rememberComposableLambda =
                    ComposableLambdaKt.rememberComposableLambda(
                            255837413,
                            new Function2() { // from class:
                                              // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchBox$1
                                {
                                    super(2);
                                }

                                @Override // kotlin.jvm.functions.Function2
                                public final Object invoke(Object obj, Object obj2) {
                                    Composer composer2 = (Composer) obj;
                                    if ((((Number) obj2).intValue() & 11) == 2) {
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        if (composerImpl3.getSkipping()) {
                                            composerImpl3.skipToGroupEnd();
                                            return Unit.INSTANCE;
                                        }
                                    }
                                    OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                    TextKt.m210Text4IGK_g(
                                            StringResources_androidKt.stringResource(
                                                    composer2, R.string.abc_search_hint),
                                            AlphaKt.alpha(Modifier.Companion.$$INSTANCE, 0.9f),
                                            0L,
                                            0L,
                                            null,
                                            null,
                                            null,
                                            0L,
                                            null,
                                            null,
                                            0L,
                                            0,
                                            false,
                                            0,
                                            0,
                                            null,
                                            TextStyle.this,
                                            composer2,
                                            48,
                                            0,
                                            65532);
                                    return Unit.INSTANCE;
                                }
                            },
                            composerImpl2);
            KeyboardOptions keyboardOptions = new KeyboardOptions(0, 3, 119);
            composerImpl2.startReplaceGroup(1574560924);
            boolean changed = composerImpl2.changed(hideKeyboardAction);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changed || rememberedValue2 == composer$Companion$Empty$1) {
                rememberedValue2 =
                        new Function1() { // from class:
                                          // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchBox$2$1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj) {
                                KeyboardActionRunner $receiver = (KeyboardActionRunner) obj;
                                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                                Function0.this.mo1068invoke();
                                return Unit.INSTANCE;
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            KeyboardActions keyboardActions = new KeyboardActions(47, (Function1) rememberedValue2);
            TextFieldDefaults textFieldDefaults = TextFieldDefaults.INSTANCE;
            long j = Color.Transparent;
            TextFieldKt.TextField(
                    textFieldValue,
                    function1,
                    focusRequester3,
                    false,
                    false,
                    textStyle,
                    null,
                    rememberComposableLambda,
                    null,
                    null,
                    null,
                    null,
                    null,
                    false,
                    null,
                    keyboardOptions,
                    keyboardActions,
                    true,
                    0,
                    0,
                    null,
                    null,
                    TextFieldDefaults.m206colors0hiis_0(j, j, j, j, composerImpl2),
                    composerImpl2,
                    12582912 | (i2 & 14) | (i2 & 112),
                    12779520,
                    0,
                    3964760);
            composerImpl = composerImpl2;
            composerImpl.startReplaceGroup(1574561298);
            Object rememberedValue3 = composerImpl.rememberedValue();
            if (rememberedValue3 == composer$Companion$Empty$1) {
                focusRequester = focusRequester2;
                rememberedValue3 = new SearchScaffoldKt$SearchBox$3$1(focusRequester, null);
                composerImpl.updateRememberedValue(rememberedValue3);
            } else {
                focusRequester = focusRequester2;
            }
            composerImpl.end(false);
            EffectsKt.LaunchedEffect(composerImpl, focusRequester, (Function2) rememberedValue3);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchBox$4
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SearchScaffoldKt.access$SearchBox(
                                    TextFieldValue.this,
                                    function1,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    /* JADX WARN: Type inference failed for: r3v5, types: [com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchableTopAppBar$2, kotlin.jvm.internal.Lambda] */
    public static final void access$SearchableTopAppBar(
            final String str,
            final Function3 function3,
            final TopAppBarScrollBehavior topAppBarScrollBehavior,
            final boolean z,
            final Function1 function1,
            final TextFieldValue textFieldValue,
            final Function1 function12,
            Composer composer,
            final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(442053095);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changed(str) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changed(topAppBarScrollBehavior) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl.changed(z) ? 2048 : 1024;
        }
        if ((i & 57344) == 0) {
            i2 |= composerImpl.changedInstance(function1) ? 16384 : 8192;
        }
        if ((458752 & i) == 0) {
            i2 |= composerImpl.changed(textFieldValue) ? 131072 : 65536;
        }
        if ((3670016 & i) == 0) {
            i2 |=
                    composerImpl.changedInstance(function12)
                            ? 1048576
                            : NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME;
        }
        if ((2995931 & i2) == 599186 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            if (z) {
                composerImpl.startReplaceGroup(-1542587991);
                composerImpl.startReplaceGroup(-1542587870);
                boolean z2 = (57344 & i2) == 16384;
                Object rememberedValue = composerImpl.rememberedValue();
                if (z2 || rememberedValue == Composer.Companion.Empty) {
                    rememberedValue =
                            new Function0() { // from class:
                                              // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchableTopAppBar$1$1
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* renamed from: invoke */
                                public final Object mo1068invoke() {
                                    Function1.this.invoke(Boolean.FALSE);
                                    return Unit.INSTANCE;
                                }
                            };
                    composerImpl.updateRememberedValue(rememberedValue);
                }
                composerImpl.end(false);
                SearchTopAppBar(
                        textFieldValue,
                        function12,
                        (Function0) rememberedValue,
                        function3,
                        composerImpl,
                        ((i2 >> 15) & 126) | ((i2 << 6) & 7168),
                        0);
                composerImpl.end(false);
            } else {
                composerImpl.startReplaceGroup(-1542587777);
                SettingsTopAppBarKt.SettingsTopAppBar(
                        str,
                        topAppBarScrollBehavior,
                        ComposableLambdaKt.rememberComposableLambda(
                                646257018,
                                new Function3() { // from class:
                                                  // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchableTopAppBar$2
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // kotlin.jvm.functions.Function3
                                    public final Object invoke(
                                            Object obj, Object obj2, Object obj3) {
                                        RowScope SettingsTopAppBar = (RowScope) obj;
                                        Composer composer2 = (Composer) obj2;
                                        int intValue = ((Number) obj3).intValue();
                                        Intrinsics.checkNotNullParameter(
                                                SettingsTopAppBar, "$this$SettingsTopAppBar");
                                        if ((intValue & 14) == 0) {
                                            intValue |=
                                                    ((ComposerImpl) composer2)
                                                                    .changed(SettingsTopAppBar)
                                                            ? 4
                                                            : 2;
                                        }
                                        if ((intValue & 91) == 18) {
                                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                            if (composerImpl2.getSkipping()) {
                                                composerImpl2.skipToGroupEnd();
                                                return Unit.INSTANCE;
                                            }
                                        }
                                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                                        ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                                        composerImpl3.startReplaceGroup(1435890788);
                                        boolean changed =
                                                composerImpl3.changed(TopAppBarScrollBehavior.this)
                                                        | composerImpl3.changed(function12)
                                                        | composerImpl3.changed(function1);
                                        final TopAppBarScrollBehavior topAppBarScrollBehavior2 =
                                                TopAppBarScrollBehavior.this;
                                        final Function1 function13 = function12;
                                        final Function1 function14 = function1;
                                        Object rememberedValue2 = composerImpl3.rememberedValue();
                                        if (changed
                                                || rememberedValue2 == Composer.Companion.Empty) {
                                            rememberedValue2 =
                                                    new Function0() { // from class:
                                                                      // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchableTopAppBar$2$1$1
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // kotlin.jvm.functions.Function0
                                                        /* renamed from: invoke */
                                                        public final Object mo1068invoke() {
                                                            TopAppBarScrollBehavior
                                                                    topAppBarScrollBehavior3 =
                                                                            TopAppBarScrollBehavior
                                                                                    .this;
                                                            Intrinsics.checkNotNullParameter(
                                                                    topAppBarScrollBehavior3,
                                                                    "<this>");
                                                            TopAppBarState topAppBarState =
                                                                    ((ExitUntilCollapsedScrollBehavior)
                                                                                    topAppBarScrollBehavior3)
                                                                            .state;
                                                            topAppBarState.setHeightOffset(
                                                                    topAppBarState
                                                                            .heightOffsetLimit$delegate
                                                                            .getFloatValue());
                                                            function13.invoke(
                                                                    new TextFieldValue(
                                                                            0L, 7, (String) null));
                                                            function14.invoke(Boolean.TRUE);
                                                            return Unit.INSTANCE;
                                                        }
                                                    };
                                            composerImpl3.updateRememberedValue(rememberedValue2);
                                        }
                                        composerImpl3.end(false);
                                        ActionsKt.SearchAction(
                                                (Function0) rememberedValue2, composerImpl3, 0);
                                        function3.invoke(
                                                SettingsTopAppBar,
                                                composerImpl3,
                                                Integer.valueOf(intValue & 14));
                                        return Unit.INSTANCE;
                                    }
                                },
                                composerImpl),
                        composerImpl,
                        ((i2 >> 3) & 112) | (i2 & 14) | 384);
                composerImpl.end(false);
            }
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.scaffold.SearchScaffoldKt$SearchableTopAppBar$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            SearchScaffoldKt.access$SearchableTopAppBar(
                                    str,
                                    function3,
                                    topAppBarScrollBehavior,
                                    z,
                                    function1,
                                    textFieldValue,
                                    function12,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
