package com.android.settingslib.spa.widget.scaffold;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.Arrangement$End$1;
import androidx.compose.foundation.layout.LimitInsets;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowMeasurePolicy;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsHolder;
import androidx.compose.foundation.layout.WindowInsetsPaddingKt;
import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.SurfaceKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.BiasAlignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.unit.Density;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CustomizedAppBarKt {
    public static final float TopAppBarHorizontalPadding;
    public static final float TopAppBarTitleInset;
    public static final CubicBezierEasing TopTitleAlphaEasing = new CubicBezierEasing(0.8f, 0.0f, 0.8f, 0.15f);
    public static final float MaxHeightWithoutTitle = 124;
    public static final float DefaultTitleHeight = 52;
    public static final float ContainerHeight = 56;
    public static final float LargeTitleBottomPadding = 28;

    static {
        float f = 4;
        TopAppBarHorizontalPadding = f;
        TopAppBarTitleInset = 16 - f;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0051  */
    /* JADX WARN: Type inference failed for: r7v7, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$CustomizedLargeTopAppBar$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r7v8, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$CustomizedLargeTopAppBar$2, kotlin.jvm.internal.Lambda] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CustomizedLargeTopAppBar(final java.lang.String r24, androidx.compose.ui.Modifier r25, kotlin.jvm.functions.Function2 r26, kotlin.jvm.functions.Function3 r27, androidx.compose.material3.TopAppBarScrollBehavior r28, androidx.compose.runtime.Composer r29, final int r30, final int r31) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.CustomizedLargeTopAppBar(java.lang.String, androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.material3.TopAppBarScrollBehavior, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void CustomizedTopAppBar(final kotlin.jvm.functions.Function2 r13, kotlin.jvm.functions.Function2 r14, kotlin.jvm.functions.Function3 r15, androidx.compose.runtime.Composer r16, final int r17, final int r18) {
        /*
            Method dump skipped, instructions count: 186
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.CustomizedTopAppBar(kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$SingleRowTopAppBar$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$SingleRowTopAppBar$actionsRow$1, kotlin.jvm.internal.Lambda] */
    public static final void SingleRowTopAppBar(final Function2 function2, final TextStyle textStyle, final Function2 function22, final Function3 function3, final WindowInsets windowInsets, final TopAppBarColors topAppBarColors, Composer composer, final int i) {
        int i2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(821573152);
        if ((i & 14) == 0) {
            i2 = (composerImpl.changedInstance(function2) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 112) == 0) {
            i2 |= composerImpl.changed(textStyle) ? 32 : 16;
        }
        if ((i & 896) == 0) {
            i2 |= composerImpl.changedInstance(function22) ? 256 : 128;
        }
        if ((i & 7168) == 0) {
            i2 |= composerImpl.changedInstance(function3) ? 2048 : 1024;
        }
        if ((57344 & i) == 0) {
            i2 |= composerImpl.changed(windowInsets) ? NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT : 8192;
        }
        if ((458752 & i) == 0) {
            i2 |= composerImpl.changed(topAppBarColors) ? 131072 : 65536;
        }
        if ((i2 & 374491) == 74898 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            final ComposableLambdaImpl rememberComposableLambda = ComposableLambdaKt.rememberComposableLambda(-1439130825, new Function2() { // from class: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$SingleRowTopAppBar$actionsRow$1
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
                    Arrangement$End$1 arrangement$End$1 = Arrangement.End;
                    BiasAlignment.Vertical vertical = Alignment.Companion.CenterVertically;
                    Function3 function32 = Function3.this;
                    Modifier.Companion companion = Modifier.Companion.$$INSTANCE;
                    RowMeasurePolicy rowMeasurePolicy = RowKt.rowMeasurePolicy(arrangement$End$1, vertical, composer2, 54);
                    int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2);
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    PersistentCompositionLocalMap currentCompositionLocalScope = composerImpl3.currentCompositionLocalScope();
                    Modifier materializeModifier = ComposedModifierKt.materializeModifier(composer2, companion);
                    ComposeUiNode.Companion.getClass();
                    Function0 function0 = ComposeUiNode.Companion.Constructor;
                    if (!(composerImpl3.applier instanceof Applier)) {
                        ComposablesKt.invalidApplier();
                        throw null;
                    }
                    composerImpl3.startReusableNode();
                    if (composerImpl3.inserting) {
                        composerImpl3.createNode(function0);
                    } else {
                        composerImpl3.useNode();
                    }
                    Updater.m221setimpl(composer2, rowMeasurePolicy, ComposeUiNode.Companion.SetMeasurePolicy);
                    Updater.m221setimpl(composer2, currentCompositionLocalScope, ComposeUiNode.Companion.SetResolvedCompositionLocals);
                    Function2 function23 = ComposeUiNode.Companion.SetCompositeKeyHash;
                    if (composerImpl3.inserting || !Intrinsics.areEqual(composerImpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                        AnimatedContentKt$$ExternalSyntheticOutline0.m(currentCompositeKeyHash, composerImpl3, currentCompositeKeyHash, function23);
                    }
                    Updater.m221setimpl(composer2, materializeModifier, ComposeUiNode.Companion.SetModifier);
                    function32.invoke(RowScopeInstance.INSTANCE, composer2, 6);
                    composerImpl3.end(true);
                    return Unit.INSTANCE;
                }
            }, composerImpl);
            SurfaceKt.m198SurfaceT9BRK9s(null, null, topAppBarColors.scrolledContainerColor, 0L, 0.0f, 0.0f, null, ComposableLambdaKt.rememberComposableLambda(-1755180741, new Function2() { // from class: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$SingleRowTopAppBar$1
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
                    ComposerImpl composerImpl3 = (ComposerImpl) composer2;
                    float mo54toPx0680j_4 = ((Density) composerImpl3.consume(CompositionLocalsKt.LocalDensity)).mo54toPx0680j_4(CustomizedAppBarKt.ContainerHeight);
                    Modifier clipToBounds = ClipKt.clipToBounds(WindowInsetsPaddingKt.windowInsetsPadding(WindowInsets.this));
                    TopAppBarColors topAppBarColors2 = topAppBarColors;
                    CustomizedAppBarKt.m1048access$TopAppBarLayout7QJOWzY(clipToBounds, mo54toPx0680j_4, topAppBarColors2.navigationIconContentColor, topAppBarColors2.titleContentColor, topAppBarColors2.actionIconContentColor, function2, textStyle, 1.0f, Arrangement.Center, 0, false, function22, rememberComposableLambda, false, composerImpl3, 918552576, 3462, 0);
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 12582912, 123);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block = new Function2() { // from class: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$SingleRowTopAppBar$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    CustomizedAppBarKt.SingleRowTopAppBar(Function2.this, textStyle, function22, function3, windowInsets, topAppBarColors, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x0360  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01a4  */
    /* JADX WARN: Type inference failed for: r10v21, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$TwoRowsTopAppBar$actionsRow$1, kotlin.jvm.internal.Lambda] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$TwoRowsTopAppBar$3, kotlin.jvm.internal.Lambda] */
    /* renamed from: TwoRowsTopAppBar-T_xyU-w, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1047TwoRowsTopAppBarT_xyUw(androidx.compose.ui.Modifier r40, final kotlin.jvm.functions.Function2 r41, final androidx.compose.ui.text.TextStyle r42, final float r43, final kotlin.jvm.functions.Function2 r44, final androidx.compose.ui.text.TextStyle r45, final kotlin.jvm.functions.Function2 r46, final kotlin.jvm.functions.Function3 r47, final androidx.compose.foundation.layout.WindowInsets r48, final com.android.settingslib.spa.widget.scaffold.TopAppBarColors r49, final float r50, final androidx.compose.material3.TopAppBarScrollBehavior r51, androidx.compose.runtime.Composer r52, final int r53, final int r54, final int r55) {
        /*
            Method dump skipped, instructions count: 872
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.m1047TwoRowsTopAppBarT_xyUw(androidx.compose.ui.Modifier, kotlin.jvm.functions.Function2, androidx.compose.ui.text.TextStyle, float, kotlin.jvm.functions.Function2, androidx.compose.ui.text.TextStyle, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, androidx.compose.foundation.layout.WindowInsets, com.android.settingslib.spa.widget.scaffold.TopAppBarColors, float, androidx.compose.material3.TopAppBarScrollBehavior, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void access$Title(final java.lang.String r27, int r28, androidx.compose.runtime.Composer r29, final int r30, final int r31) {
        /*
            r1 = r27
            r0 = r30
            r15 = r31
            r2 = 2
            r13 = r29
            androidx.compose.runtime.ComposerImpl r13 = (androidx.compose.runtime.ComposerImpl) r13
            r3 = -153097447(0xfffffffff6dfeb19, float:-2.2708018E33)
            r13.startRestartGroup(r3)
            r3 = r15 & 1
            if (r3 == 0) goto L18
            r3 = r0 | 6
            goto L28
        L18:
            r3 = r0 & 14
            if (r3 != 0) goto L27
            boolean r3 = r13.changed(r1)
            if (r3 == 0) goto L24
            r3 = 4
            goto L25
        L24:
            r3 = r2
        L25:
            r3 = r3 | r0
            goto L28
        L27:
            r3 = r0
        L28:
            r2 = r2 & r15
            if (r2 == 0) goto L30
            r3 = r3 | 48
        L2d:
            r4 = r28
            goto L42
        L30:
            r4 = r0 & 112(0x70, float:1.57E-43)
            if (r4 != 0) goto L2d
            r4 = r28
            boolean r5 = r13.changed(r4)
            if (r5 == 0) goto L3f
            r5 = 32
            goto L41
        L3f:
            r5 = 16
        L41:
            r3 = r3 | r5
        L42:
            r5 = r3 & 91
            r6 = 18
            if (r5 != r6) goto L55
            boolean r5 = r13.getSkipping()
            if (r5 != 0) goto L4f
            goto L55
        L4f:
            r13.skipToGroupEnd()
            r26 = r13
            goto Lab
        L55:
            if (r2 == 0) goto L5d
            r2 = 2147483647(0x7fffffff, float:NaN)
            r25 = r2
            goto L5f
        L5d:
            r25 = r4
        L5f:
            androidx.compose.runtime.OpaqueKey r2 = androidx.compose.runtime.ComposerKt.invocation
            androidx.compose.ui.Modifier$Companion r4 = androidx.compose.ui.Modifier.Companion.$$INSTANCE
            float r5 = com.android.settingslib.spa.framework.theme.SettingsDimension.itemPaddingAround
            float r7 = com.android.settingslib.spa.framework.theme.SettingsDimension.itemPaddingEnd
            r8 = 0
            r9 = 10
            r6 = 0
            androidx.compose.ui.Modifier r2 = androidx.compose.foundation.layout.PaddingKt.m89paddingqDBjuR0$default(r4, r5, r6, r7, r8, r9)
            com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1 r4 = new kotlin.jvm.functions.Function1() { // from class: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1
                static {
                    /*
                        com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1 r0 = new com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1) com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1.INSTANCE com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r2) {
                    /*
                        r1 = this;
                        androidx.compose.ui.semantics.SemanticsPropertyReceiver r2 = (androidx.compose.ui.semantics.SemanticsPropertyReceiver) r2
                        java.lang.String r1 = "$this$semantics"
                        kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r1)
                        kotlin.reflect.KProperty[] r1 = androidx.compose.ui.semantics.SemanticsPropertiesKt.$$delegatedProperties
                        androidx.compose.ui.semantics.SemanticsPropertyKey r1 = androidx.compose.ui.semantics.SemanticsProperties.Heading
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        androidx.compose.ui.semantics.SemanticsConfiguration r2 = (androidx.compose.ui.semantics.SemanticsConfiguration) r2
                        r2.set(r1, r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$1.invoke(java.lang.Object):java.lang.Object");
                }
            }
            r5 = 0
            androidx.compose.ui.Modifier r17 = androidx.compose.ui.semantics.SemanticsModifierKt.semantics(r2, r5, r4)
            r22 = r3 & 14
            int r2 = r3 << 6
            r2 = r2 & 7168(0x1c00, float:1.0045E-41)
            r23 = r2 | 48
            r18 = 0
            r24 = 120828(0x1d7fc, float:1.69316E-40)
            r2 = 0
            r4 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r11 = 0
            r12 = 0
            r19 = 0
            r26 = r13
            r13 = r19
            r16 = 2
            r15 = r16
            r16 = 0
            r19 = 0
            r20 = 0
            r0 = r27
            r1 = r17
            r17 = r25
            r21 = r26
            androidx.compose.material3.TextKt.m210Text4IGK_g(r0, r1, r2, r4, r6, r7, r8, r9, r11, r12, r13, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
            r4 = r25
        Lab:
            androidx.compose.runtime.RecomposeScopeImpl r0 = r26.endRestartGroup()
            if (r0 == 0) goto Lbe
            com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$2 r1 = new com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$Title$2
            r2 = r27
            r3 = r30
            r5 = r31
            r1.<init>()
            r0.block = r1
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.access$Title(java.lang.String, int, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x023f  */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt$TopAppBarLayout$1$3$1, kotlin.jvm.internal.Lambda] */
    /* renamed from: access$TopAppBarLayout-7QJOWzY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void m1048access$TopAppBarLayout7QJOWzY(final androidx.compose.ui.Modifier r35, final float r36, final long r37, final long r39, final long r41, final kotlin.jvm.functions.Function2 r43, final androidx.compose.ui.text.TextStyle r44, final float r45, final androidx.compose.foundation.layout.Arrangement.Vertical r46, final int r47, final boolean r48, final kotlin.jvm.functions.Function2 r49, final kotlin.jvm.functions.Function2 r50, boolean r51, androidx.compose.runtime.Composer r52, final int r53, final int r54, final int r55) {
        /*
            Method dump skipped, instructions count: 1079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.m1048access$TopAppBarLayout7QJOWzY(androidx.compose.ui.Modifier, float, long, long, long, kotlin.jvm.functions.Function2, androidx.compose.ui.text.TextStyle, float, androidx.compose.foundation.layout.Arrangement$Vertical, int, boolean, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function2, boolean, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$settleAppBar(final androidx.compose.material3.TopAppBarState r9, float r10, androidx.compose.animation.core.DecayAnimationSpecImpl r11, androidx.compose.animation.core.AnimationSpec r12, kotlin.coroutines.Continuation r13) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.spa.widget.scaffold.CustomizedAppBarKt.access$settleAppBar(androidx.compose.material3.TopAppBarState, float, androidx.compose.animation.core.DecayAnimationSpecImpl, androidx.compose.animation.core.AnimationSpec, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static final LimitInsets getWindowInsets(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1815562930);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        WindowInsetsHolder.Companion companion = WindowInsetsHolder.Companion;
        WindowInsetsHolder current = WindowInsetsHolder.Companion.current(composerImpl);
        LimitInsets limitInsets = new LimitInsets(current.safeDrawing, WindowInsetsSides.Horizontal | 16);
        composerImpl.end(false);
        return limitInsets;
    }

    public static final TopAppBarColors topAppBarColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1650510745);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        StaticProvidableCompositionLocal staticProvidableCompositionLocal = ColorSchemeKt.LocalColorScheme;
        ColorScheme colorScheme = (ColorScheme) composerImpl.consume(staticProvidableCompositionLocal);
        Intrinsics.checkNotNullParameter(colorScheme, "<this>");
        TopAppBarColors topAppBarColors = new TopAppBarColors(colorScheme.surfaceContainer, ((ColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).surfaceVariant, ((ColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).onSurface, ((ColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).onSurface, ((ColorScheme) composerImpl.consume(staticProvidableCompositionLocal)).onSurfaceVariant);
        composerImpl.end(false);
        return topAppBarColors;
    }
}
