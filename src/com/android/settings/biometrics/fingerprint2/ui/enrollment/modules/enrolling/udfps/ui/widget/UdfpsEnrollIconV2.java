package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.PathParser;

import com.android.settings.R;
import com.android.settings.R$styleable;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0016\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u001b\b\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/udfps/ui/widget/UdfpsEnrollIconV2;",
            "Landroid/graphics/drawable/Drawable;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class UdfpsEnrollIconV2 extends Drawable {
    public int alpha;
    public final float currentScale;
    public final ShapeDrawable fingerprintDrawable;
    public final Drawable movingTargetFpIcon;
    public final float sensorLeftOffset;
    public final Paint sensorOutlinePaint;
    public final Rect sensorRectBounds;
    public final float sensorTopOffset;

    public UdfpsEnrollIconV2(Context context, AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.currentScale = 1.0f;
        this.sensorRectBounds = new Rect();
        String string = context.getResources().getString(R.string.config_udfpsIcon);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        ShapeDrawable shapeDrawable =
                new ShapeDrawable(
                        new PathShape(PathParser.createPathFromPathData(string), 72.0f, 72.0f));
        shapeDrawable.mutate();
        shapeDrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable.getPaint().setStrokeCap(Paint.Cap.ROUND);
        shapeDrawable.getPaint().setStrokeWidth(3.0f);
        this.fingerprintDrawable = shapeDrawable;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.BiometricsEnrollView,
                        R.attr.biometricsEnrollStyle,
                        R.style.BiometricsEnrollStyle);
        int color = obtainStyledAttributes.getColor(0, 0);
        int color2 = obtainStyledAttributes.getColor(5, 0);
        obtainStyledAttributes.recycle();
        Paint paint = new Paint(0);
        paint.setAntiAlias(true);
        paint.setColor(color2);
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        this.sensorOutlinePaint = paint;
        Paint paint2 = new Paint(0);
        paint2.setAntiAlias(true);
        paint2.setColor(color2);
        paint2.setStyle(style);
        Drawable drawable =
                context.getResources().getDrawable(R.drawable.ic_enrollment_fingerprint, null);
        drawable.setTint(color);
        drawable.mutate();
        this.movingTargetFpIcon = drawable;
        shapeDrawable.setTint(color);
        this.alpha = 255;
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        this.movingTargetFpIcon.setAlpha(this.alpha);
        this.fingerprintDrawable.setAlpha(this.alpha);
        this.sensorOutlinePaint.setAlpha(this.alpha);
        Rect rect = this.sensorRectBounds;
        float f = rect.left + 0.0f;
        float f2 = rect.top + 0.0f;
        RectF rectF =
                new RectF(
                        f,
                        f2,
                        this.sensorRectBounds.width() + f,
                        this.sensorRectBounds.height() + f2);
        float f3 = this.currentScale;
        canvas.scale(f3, f3, rectF.centerX(), rectF.centerY());
        canvas.drawOval(rectF, this.sensorOutlinePaint);
        ShapeDrawable shapeDrawable = this.fingerprintDrawable;
        Rect rect2 = new Rect();
        rectF.roundOut(rect2);
        shapeDrawable.setBounds(rect2);
        this.fingerprintDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.alpha;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        this.alpha = i;
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
