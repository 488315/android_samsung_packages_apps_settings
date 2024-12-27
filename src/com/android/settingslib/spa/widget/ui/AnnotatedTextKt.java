package com.android.settingslib.spa.widget.ui;

import android.content.Context;
import android.content.res.Resources;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;

import androidx.compose.material3.ColorScheme;
import androidx.compose.material3.ColorSchemeKt;
import androidx.compose.material3.Typography;
import androidx.compose.material3.TypographyKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextLinkStyles;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextDecoration;

import com.samsung.android.knox.custom.CustomDeviceManager;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AnnotatedTextKt {
    public static final void AnnotatedText(final int i, Composer composer, final int i2) {
        int i3;
        AnnotatedString annotatedString;
        long j;
        int i4;
        int i5;
        ComposerImpl composerImpl;
        int i6 = 1;
        ComposerImpl composerImpl2 = (ComposerImpl) composer;
        composerImpl2.startRestartGroup(1624189519);
        int i7 = 2;
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
            composerImpl2.startReplaceGroup(-777390858);
            Resources resources =
                    ((Context)
                                    composerImpl2.consume(
                                            AndroidCompositionLocals_androidKt.LocalContext))
                            .getResources();
            long j2 = ((ColorScheme) composerImpl2.consume(ColorSchemeKt.LocalColorScheme)).primary;
            composerImpl2.startReplaceGroup(1539852851);
            boolean z = (((i3 & 14) ^ 6) > 4 && composerImpl2.changed(i)) || (i3 & 6) == 4;
            Object rememberedValue = composerImpl2.rememberedValue();
            if (z || rememberedValue == Composer.Companion.Empty) {
                CharSequence text = resources.getText(i);
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                if (text instanceof Spanned) {
                    AnnotatedString.Builder builder = new AnnotatedString.Builder();
                    builder.text.append(text.toString());
                    Spanned spanned = (Spanned) text;
                    Object[] spans = spanned.getSpans(0, text.length(), Object.class);
                    Intrinsics.checkNotNullExpressionValue(spans, "getSpans(...)");
                    int length = spans.length;
                    int i8 = 0;
                    while (i8 < length) {
                        Object obj = spans[i8];
                        int spanStart = spanned.getSpanStart(obj);
                        int spanEnd = spanned.getSpanEnd(obj);
                        if (obj instanceof StyleSpan) {
                            Intrinsics.checkNotNull(obj);
                            int style = ((StyleSpan) obj).getStyle();
                            if (style == 0) {
                                builder.addStyle(
                                        new SpanStyle(
                                                0L,
                                                0L,
                                                FontWeight.Normal,
                                                new FontStyle(0),
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                65523),
                                        spanStart,
                                        spanEnd);
                            } else if (style == i6) {
                                builder.addStyle(
                                        new SpanStyle(
                                                0L,
                                                0L,
                                                FontWeight.Bold,
                                                new FontStyle(0),
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                65523),
                                        spanStart,
                                        spanEnd);
                            } else if (style == i7) {
                                builder.addStyle(
                                        new SpanStyle(
                                                0L,
                                                0L,
                                                FontWeight.Normal,
                                                new FontStyle(i6),
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                65523),
                                        spanStart,
                                        spanEnd);
                            } else if (style == 3) {
                                builder.addStyle(
                                        new SpanStyle(
                                                0L,
                                                0L,
                                                FontWeight.Bold,
                                                new FontStyle(i6),
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                65523),
                                        spanStart,
                                        spanEnd);
                            }
                            j = j2;
                            i4 = i8;
                            i5 = length;
                        } else {
                            if (obj instanceof URLSpan) {
                                Intrinsics.checkNotNull(obj);
                                String url = ((URLSpan) obj).getURL();
                                Intrinsics.checkNotNullExpressionValue(url, "getURL(...)");
                                i4 = i8;
                                i5 = length;
                                j = j2;
                                ((ArrayList) builder.annotations)
                                        .add(
                                                new AnnotatedString.Builder.MutableRange(
                                                        spanStart,
                                                        spanEnd,
                                                        new LinkAnnotation.Url(
                                                                url,
                                                                new TextLinkStyles(
                                                                        new SpanStyle(
                                                                                j2,
                                                                                0L,
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                0L,
                                                                                null,
                                                                                null,
                                                                                null,
                                                                                0L,
                                                                                TextDecoration
                                                                                        .Underline,
                                                                                null,
                                                                                61438),
                                                                        null,
                                                                        null,
                                                                        null))));
                            } else {
                                j = j2;
                                i4 = i8;
                                i5 = length;
                                builder.addStyle(
                                        new SpanStyle(
                                                0L,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                null,
                                                0L,
                                                null,
                                                null,
                                                CustomDeviceManager.QUICK_PANEL_ALL),
                                        spanStart,
                                        spanEnd);
                            }
                            i6 = 1;
                        }
                        i8 = i4 + 1;
                        length = i5;
                        j2 = j;
                        i7 = 2;
                    }
                    annotatedString = builder.toAnnotatedString();
                } else {
                    annotatedString = new AnnotatedString(text.toString(), null, 6);
                }
                rememberedValue = annotatedString;
                composerImpl2.updateRememberedValue(rememberedValue);
            }
            AnnotatedString annotatedString2 = (AnnotatedString) rememberedValue;
            composerImpl2.end(false);
            OpaqueKey opaqueKey2 = ComposerKt.invocation;
            composerImpl2.end(false);
            composerImpl = composerImpl2;
            androidx.compose.material3.TextKt.m211TextIbK3jfQ(
                    annotatedString2,
                    null,
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
                    null,
                    TextStyle.m527copyp1EtxEg$default(
                            ((Typography) composerImpl2.consume(TypographyKt.LocalTypography))
                                    .bodyMedium,
                            ((ColorScheme) composerImpl2.consume(ColorSchemeKt.LocalColorScheme))
                                    .onSurfaceVariant,
                            0L,
                            null,
                            null,
                            0L,
                            0L,
                            null,
                            null,
                            16777214),
                    composerImpl,
                    0,
                    0,
                    131070);
        }
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            endRestartGroup.block =
                    new Function2() { // from class:
                                      // com.android.settingslib.spa.widget.ui.AnnotatedTextKt$AnnotatedText$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // kotlin.jvm.functions.Function2
                        public final Object invoke(Object obj2, Object obj3) {
                            ((Number) obj3).intValue();
                            AnnotatedTextKt.AnnotatedText(
                                    i,
                                    (Composer) obj2,
                                    RecomposeScopeImplKt.updateChangedFlags(i2 | 1));
                            return Unit.INSTANCE;
                        }
                    };
        }
    }
}
