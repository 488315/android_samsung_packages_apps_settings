package com.android.settingslib.spa.framework.compose;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Handler;

import androidx.compose.runtime.ParcelableSnapshotMutableState;
import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StructuralEqualityPolicy;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.Canvas;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;
import androidx.compose.ui.unit.LayoutDirection;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DrawablePainter extends Painter implements RememberObserver {
    public final Lazy callback$delegate;
    public final ParcelableSnapshotMutableState drawInvalidateTick$delegate;
    public final Drawable drawable;
    public final ParcelableSnapshotMutableState drawableIntrinsicSize$delegate;

    public DrawablePainter(Drawable drawable) {
        this.drawable = drawable;
        StructuralEqualityPolicy structuralEqualityPolicy = StructuralEqualityPolicy.INSTANCE;
        this.drawInvalidateTick$delegate =
                SnapshotStateKt.mutableStateOf(0, structuralEqualityPolicy);
        this.drawableIntrinsicSize$delegate =
                SnapshotStateKt.mutableStateOf(
                        new Size(
                                (drawable.getIntrinsicWidth() < 0
                                                || drawable.getIntrinsicHeight() < 0)
                                        ? 9205357640488583168L
                                        : SizeKt.Size(
                                                drawable.getIntrinsicWidth(),
                                                drawable.getIntrinsicHeight())),
                        structuralEqualityPolicy);
        this.callback$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settingslib.spa.framework.compose.DrawablePainter$callback$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                final DrawablePainter drawablePainter = DrawablePainter.this;
                                return new Drawable
                                        .Callback() { // from class:
                                                      // com.android.settingslib.spa.framework.compose.DrawablePainter$callback$2.1
                                    @Override // android.graphics.drawable.Drawable.Callback
                                    public final void invalidateDrawable(Drawable d) {
                                        Intrinsics.checkNotNullParameter(d, "d");
                                        DrawablePainter drawablePainter2 = DrawablePainter.this;
                                        drawablePainter2.drawInvalidateTick$delegate.setValue(
                                                Integer.valueOf(
                                                        ((Number)
                                                                                drawablePainter2
                                                                                        .drawInvalidateTick$delegate
                                                                                        .getValue())
                                                                        .intValue()
                                                                + 1));
                                        DrawablePainter drawablePainter3 = DrawablePainter.this;
                                        Drawable drawable2 = drawablePainter3.drawable;
                                        Lazy lazy = DrawablePainterKt.MAIN_HANDLER$delegate;
                                        drawablePainter3.drawableIntrinsicSize$delegate.setValue(
                                                new Size(
                                                        (drawable2.getIntrinsicWidth() < 0
                                                                        || drawable2
                                                                                        .getIntrinsicHeight()
                                                                                < 0)
                                                                ? 9205357640488583168L
                                                                : SizeKt.Size(
                                                                        drawable2
                                                                                .getIntrinsicWidth(),
                                                                        drawable2
                                                                                .getIntrinsicHeight())));
                                    }

                                    @Override // android.graphics.drawable.Drawable.Callback
                                    public final void scheduleDrawable(
                                            Drawable d, Runnable what, long j) {
                                        Intrinsics.checkNotNullParameter(d, "d");
                                        Intrinsics.checkNotNullParameter(what, "what");
                                        ((Handler)
                                                        DrawablePainterKt.MAIN_HANDLER$delegate
                                                                .getValue())
                                                .postAtTime(what, j);
                                    }

                                    @Override // android.graphics.drawable.Drawable.Callback
                                    public final void unscheduleDrawable(
                                            Drawable d, Runnable what) {
                                        Intrinsics.checkNotNullParameter(d, "d");
                                        Intrinsics.checkNotNullParameter(what, "what");
                                        ((Handler)
                                                        DrawablePainterKt.MAIN_HANDLER$delegate
                                                                .getValue())
                                                .removeCallbacks(what);
                                    }
                                };
                            }
                        });
        if (drawable.getIntrinsicWidth() < 0 || drawable.getIntrinsicHeight() < 0) {
            return;
        }
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyAlpha(float f) {
        this.drawable.setAlpha(RangesKt.coerceIn(MathKt.roundToInt(f * 255), 0, 255));
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final boolean applyColorFilter(ColorFilter colorFilter) {
        this.drawable.setColorFilter(colorFilter != null ? colorFilter.nativeColorFilter : null);
        return true;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void applyLayoutDirection(LayoutDirection layoutDirection) {
        int i;
        Intrinsics.checkNotNullParameter(layoutDirection, "layoutDirection");
        Drawable drawable = this.drawable;
        int ordinal = layoutDirection.ordinal();
        if (ordinal != 0) {
            i = 1;
            if (ordinal != 1) {
                throw new NoWhenBranchMatchedException();
            }
        } else {
            i = 0;
        }
        drawable.setLayoutDirection(i);
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo387getIntrinsicSizeNHjbRc() {
        return ((Size) this.drawableIntrinsicSize$delegate.getValue()).packedValue;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onAbandoned() {
        onForgotten();
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
        Intrinsics.checkNotNullParameter(drawScope, "<this>");
        Canvas canvas = drawScope.getDrawContext().getCanvas();
        ((Number) this.drawInvalidateTick$delegate.getValue()).intValue();
        this.drawable.setBounds(
                0,
                0,
                MathKt.roundToInt(Size.m282getWidthimpl(drawScope.mo381getSizeNHjbRc())),
                MathKt.roundToInt(Size.m280getHeightimpl(drawScope.mo381getSizeNHjbRc())));
        try {
            canvas.save();
            this.drawable.draw(AndroidCanvas_androidKt.getNativeCanvas(canvas));
        } finally {
            canvas.restore();
        }
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onForgotten() {
        Object obj = this.drawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).stop();
        }
        this.drawable.setVisible(false, false);
        this.drawable.setCallback(null);
    }

    @Override // androidx.compose.runtime.RememberObserver
    public final void onRemembered() {
        this.drawable.setCallback((Drawable.Callback) this.callback$delegate.getValue());
        this.drawable.setVisible(true, true);
        Object obj = this.drawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).start();
        }
    }
}
