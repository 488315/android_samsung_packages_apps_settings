package com.android.settingslib.spa.framework.compose;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.graphics.AndroidImageBitmap;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.painter.BitmapPainter;
import androidx.compose.ui.graphics.painter.ColorPainter;
import androidx.compose.ui.graphics.painter.Painter;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class DrawablePainterKt {
    public static final Lazy MAIN_HANDLER$delegate =
            LazyKt__LazyJVMKt.lazy(
                    LazyThreadSafetyMode.NONE,
                    new Function0() { // from class:
                                      // com.android.settingslib.spa.framework.compose.DrawablePainterKt$MAIN_HANDLER$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new Handler(Looper.getMainLooper());
                        }
                    });

    public static final Painter rememberDrawablePainter(Drawable drawable, Composer composer) {
        Object drawablePainter;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1125592882);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(1505783286);
        boolean changed = composerImpl.changed(drawable);
        Object rememberedValue = composerImpl.rememberedValue();
        if (changed || rememberedValue == Composer.Companion.Empty) {
            if (drawable == null) {
                rememberedValue = EmptyPainter.INSTANCE;
            } else {
                if (drawable instanceof BitmapDrawable) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    Intrinsics.checkNotNullExpressionValue(bitmap, "getBitmap(...)");
                    drawablePainter = new BitmapPainter(new AndroidImageBitmap(bitmap));
                } else if (drawable instanceof ColorDrawable) {
                    drawablePainter =
                            new ColorPainter(ColorKt.Color(((ColorDrawable) drawable).getColor()));
                } else {
                    Drawable mutate = drawable.mutate();
                    Intrinsics.checkNotNullExpressionValue(mutate, "mutate(...)");
                    drawablePainter = new DrawablePainter(mutate);
                }
                rememberedValue = drawablePainter;
            }
            composerImpl.updateRememberedValue(rememberedValue);
        }
        Painter painter = (Painter) rememberedValue;
        composerImpl.end(false);
        composerImpl.end(false);
        return painter;
    }
}
