package com.android.settingslib.spa.widget.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import androidx.compose.animation.AnimatedContentKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.compose.AnimateLottieCompositionAsStateKt;
import com.airbnb.lottie.compose.LottieAnimatable;
import com.airbnb.lottie.compose.LottieAnimatableImpl;
import com.airbnb.lottie.compose.LottieAnimationKt;
import com.airbnb.lottie.compose.LottieCompositionResultImpl;
import com.airbnb.lottie.compose.LottieCompositionSpec;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import com.airbnb.lottie.compose.LottieDynamicPropertiesKt;
import com.airbnb.lottie.compose.LottieDynamicProperty;
import com.airbnb.lottie.compose.RememberLottieCompositionKt;
import com.airbnb.lottie.value.LottieFrameInfo;

import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LottieKt {
    public static final void BaseLottie(final int i, Composer composer, final int i2) {
        int i3;
        Object obj;
        ComposerImpl composerImpl;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(-798920766);
        if ((i2 & 14) == 0) {
            i3 = (composerImpl2.changed(i) ? 4 : 2) | i2;
        } else {
            i3 = i2;
        }
        if ((i3 & 11) == 2 && composerImpl2.getSkipping()) {
            composerImpl2.skipToGroupEnd();
            composerImpl = composerImpl2;
        } else {
            OpaqueKey opaqueKey = ComposerKt.invocation;
            LottieCompositionResultImpl rememberLottieComposition =
                    RememberLottieCompositionKt.rememberLottieComposition(
                            new LottieCompositionSpec.RawRes(i), composerImpl2);
            final LottieAnimatable animateLottieCompositionAsState =
                    AnimateLottieCompositionAsStateKt.animateLottieCompositionAsState(
                            (LottieComposition) rememberLottieComposition.getValue(),
                            composerImpl2);
            boolean z =
                    !((((Configuration)
                                                    composerImpl2.consume(
                                                            AndroidCompositionLocals_androidKt
                                                                    .LocalConfiguration))
                                            .uiMode
                                    & 48)
                            == 32);
            LottieComposition lottieComposition =
                    (LottieComposition) rememberLottieComposition.getValue();
            Map map = LottieColorUtils.DARK_TO_LIGHT_THEME_COLOR_MAP;
            composerImpl2.startReplaceGroup(142041330);
            composerImpl2.startReplaceGroup(-1402300798);
            Map map2 = LottieColorUtils.DARK_TO_LIGHT_THEME_COLOR_MAP;
            ArrayList arrayList = new ArrayList(map2.size());
            Iterator it = map2.entrySet().iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                obj = Composer.Companion.Empty;
                if (!hasNext) {
                    break;
                }
                Map.Entry entry = (Map.Entry) it.next();
                String str = (String) entry.getKey();
                int intValue = ((Number) entry.getValue()).intValue();
                OpaqueKey opaqueKey2 = ComposerKt.invocation;
                Context context =
                        (Context)
                                composerImpl2.consume(
                                        AndroidCompositionLocals_androidKt.LocalContext);
                final int m323toArgb8_81llA =
                        ColorKt.m323toArgb8_81llA(
                                ColorKt.Color(
                                        context.getResources()
                                                .getColor(intValue, context.getTheme())));
                ColorFilter colorFilter = LottieProperty.COLOR_FILTER;
                String[] strArr = {"**", str, "**"};
                composerImpl2.startReplaceGroup(186126031);
                boolean changed = composerImpl2.changed(m323toArgb8_81llA);
                Object rememberedValue = composerImpl2.rememberedValue();
                if (changed || rememberedValue == obj) {
                    rememberedValue =
                            new Function1() { // from class:
                                              // com.android.settingslib.spa.widget.ui.LottieColorUtils$getDefaultPropertiesList$1$1$1
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                public final Object invoke(Object obj2) {
                                    LottieFrameInfo it2 = (LottieFrameInfo) obj2;
                                    Intrinsics.checkNotNullParameter(it2, "it");
                                    return new PorterDuffColorFilter(
                                            m323toArgb8_81llA, PorterDuff.Mode.SRC_ATOP);
                                }
                            };
                    composerImpl2.updateRememberedValue(rememberedValue);
                }
                composerImpl2.end(false);
                arrayList.add(
                        LottieDynamicPropertiesKt.rememberLottieDynamicProperty(
                                colorFilter, strArr, (Function1) rememberedValue, composerImpl2));
            }
            OpaqueKey opaqueKey3 = ComposerKt.invocation;
            composerImpl2.end(false);
            LottieDynamicProperty[] lottieDynamicPropertyArr =
                    (LottieDynamicProperty[]) arrayList.toArray(new LottieDynamicProperty[0]);
            LottieDynamicProperty[] properties =
                    (LottieDynamicProperty[])
                            Arrays.copyOf(
                                    lottieDynamicPropertyArr, lottieDynamicPropertyArr.length);
            Intrinsics.checkNotNullParameter(properties, "properties");
            composerImpl2.startReplaceableGroup(34467846);
            Object valueOf = Integer.valueOf(Arrays.hashCode(properties));
            composerImpl2.startReplaceableGroup(-3686930);
            boolean changed2 = composerImpl2.changed(valueOf);
            Object rememberedValue2 = composerImpl2.rememberedValue();
            if (changed2 || rememberedValue2 == obj) {
                rememberedValue2 =
                        new LottieDynamicProperties(ArraysKt___ArraysKt.toList(properties));
                composerImpl2.updateRememberedValue(rememberedValue2);
            }
            composerImpl2.end(false);
            LottieDynamicProperties lottieDynamicProperties =
                    (LottieDynamicProperties) rememberedValue2;
            composerImpl2.end(false);
            composerImpl2.end(false);
            LottieDynamicProperties lottieDynamicProperties2 = z ? lottieDynamicProperties : null;
            composerImpl2.startReplaceGroup(1187397633);
            boolean changed3 = composerImpl2.changed(animateLottieCompositionAsState);
            Object rememberedValue3 = composerImpl2.rememberedValue();
            if (changed3 || rememberedValue3 == obj) {
                rememberedValue3 =
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.widget.ui.LottieKt$BaseLottie$2$1
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return Float.valueOf(
                                        ((Number)
                                                        ((LottieAnimatableImpl)
                                                                        animateLottieCompositionAsState)
                                                                .getValue())
                                                .floatValue());
                            }
                        };
                composerImpl2.updateRememberedValue(rememberedValue3);
            }
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            LottieAnimationKt.LottieAnimation(
                    lottieComposition,
                    (Function0) rememberedValue3,
                    null,
                    false,
                    false,
                    false,
                    null,
                    false,
                    lottieDynamicProperties2,
                    null,
                    null,
                    false,
                    composerImpl2,
                    134217736,
                    0,
                    3836);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.LottieKt$BaseLottie$3
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            LottieKt.BaseLottie(
                                    i,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }

    public static final void Lottie(
            final int i, final Modifier modifier, Composer composer, final int i2, final int i3) {
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(-1945057972);
        if ((i3 & 1) != 0) {
            i4 = i2 | 6;
        } else if ((i2 & 14) == 0) {
            i4 = (composerImpl.changed(i) ? 4 : 2) | i2;
        } else {
            i4 = i2;
        }
        int i5 = 2 & i3;
        if (i5 != 0) {
            i4 |= 48;
        } else if ((i2 & 112) == 0) {
            i4 |= composerImpl.changed(modifier) ? 32 : 16;
        }
        if ((i4 & 91) == 18 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i5 != 0) {
                modifier = Modifier.Companion.$$INSTANCE;
            }
            OpaqueKey opaqueKey = ComposerKt.invocation;
            MeasurePolicy maybeCachedBoxMeasurePolicy =
                    BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.TopStart, false);
            int i6 = composerImpl.compoundKeyHash;
            PersistentCompositionLocalMap currentCompositionLocalScope =
                    composerImpl.currentCompositionLocalScope();
            Modifier materializeModifier =
                    ComposedModifierKt.materializeModifier(composerImpl, modifier);
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
                    composerImpl,
                    maybeCachedBoxMeasurePolicy,
                    ComposeUiNode.Companion.SetMeasurePolicy);
            Updater.m221setimpl(
                    composerImpl,
                    currentCompositionLocalScope,
                    ComposeUiNode.Companion.SetResolvedCompositionLocals);
            Function2 function2 = ComposeUiNode.Companion.SetCompositeKeyHash;
            if (composerImpl.inserting
                    || !Intrinsics.areEqual(composerImpl.rememberedValue(), Integer.valueOf(i6))) {
                AnimatedContentKt$$ExternalSyntheticOutline0.m(i6, composerImpl, i6, function2);
            }
            Updater.m221setimpl(
                    composerImpl, materializeModifier, ComposeUiNode.Companion.SetModifier);
            BaseLottie(i, composerImpl, i4 & 14);
            composerImpl.end(true);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.LottieKt$Lottie$2
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj, Object obj2) {
                            ((Number) obj2).intValue();
                            LottieKt.Lottie(
                                    i,
                                    modifier,
                                    (Composer) obj,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1),
                                    i3);
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
