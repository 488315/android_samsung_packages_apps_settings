package com.android.settingslib.graph;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.PathParser;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;

import com.android.settingslib.Utils;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ThemedBatteryDrawable extends Drawable {
    public int batteryLevel;
    public final Path boltPath;
    public boolean charging;
    public final int[] colorLevels;
    public final Context context;
    public final int criticalLevel;
    public final boolean dualTone;
    public final Paint dualToneBackgroundFill;
    public final Paint errorPaint;
    public final Path errorPerimeterPath;
    public final int fillColor;
    public final Paint fillColorStrokePaint;
    public final Paint fillColorStrokeProtection;
    public final Path fillMask;
    public final Paint fillPaint;
    public final RectF fillRect;
    public final Function0 invalidateRunnable;
    public boolean invertFillIcon;
    public int levelColor;
    public final Path levelPath;
    public final RectF levelRect;
    public final Path perimeterPath;
    public final Path plusPath;
    public boolean powerSaveEnabled;
    public final Matrix scaleMatrix;
    public final Path scaledBolt;
    public final Path scaledErrorPerimeter;
    public final Path scaledFill;
    public final Path scaledPerimeter;
    public final Path scaledPlus;
    public final Path unifiedPath;

    public ThemedBatteryDrawable(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.perimeterPath = new Path();
        this.scaledPerimeter = new Path();
        this.errorPerimeterPath = new Path();
        this.scaledErrorPerimeter = new Path();
        this.fillMask = new Path();
        this.scaledFill = new Path();
        this.fillRect = new RectF();
        this.levelRect = new RectF();
        this.levelPath = new Path();
        this.scaleMatrix = new Matrix();
        new Rect();
        this.unifiedPath = new Path();
        this.boltPath = new Path();
        this.scaledBolt = new Path();
        this.plusPath = new Path();
        this.scaledPlus = new Path();
        this.fillColor = -65281;
        this.levelColor = -65281;
        this.invalidateRunnable =
                new Function0() { // from class:
                                  // com.android.settingslib.graph.ThemedBatteryDrawable$invalidateRunnable$1
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        ThemedBatteryDrawable.this.invalidateSelf();
                        return Unit.INSTANCE;
                    }
                };
        this.criticalLevel = context.getResources().getInteger(R.integer.config_defaultRefreshRate);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(255);
        paint.setDither(true);
        paint.setStrokeWidth(5.0f);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        BlendMode blendMode = BlendMode.SRC;
        paint.setBlendMode(blendMode);
        paint.setStrokeMiter(5.0f);
        Paint.Join join = Paint.Join.ROUND;
        paint.setStrokeJoin(join);
        this.fillColorStrokePaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setDither(true);
        paint2.setStrokeWidth(5.0f);
        paint2.setStyle(style);
        paint2.setBlendMode(BlendMode.CLEAR);
        paint2.setStrokeMiter(5.0f);
        paint2.setStrokeJoin(join);
        this.fillColorStrokeProtection = paint2;
        Paint paint3 = new Paint(1);
        paint3.setColor(i);
        paint3.setAlpha(255);
        paint3.setDither(true);
        paint3.setStrokeWidth(0.0f);
        Paint.Style style2 = Paint.Style.FILL_AND_STROKE;
        paint3.setStyle(style2);
        this.fillPaint = paint3;
        Paint paint4 = new Paint(1);
        paint4.setColor(
                context.getResources()
                        .getColorStateList(
                                com.android.settings.R.color.batterymeter_saver_color,
                                context.getTheme())
                        .getDefaultColor());
        paint4.setAlpha(255);
        paint4.setDither(true);
        paint4.setStrokeWidth(0.0f);
        paint4.setStyle(style2);
        paint4.setBlendMode(blendMode);
        this.errorPaint = paint4;
        Paint paint5 = new Paint(1);
        paint5.setColor(i);
        paint5.setAlpha(85);
        paint5.setDither(true);
        paint5.setStrokeWidth(0.0f);
        paint5.setStyle(style2);
        this.dualToneBackgroundFill = paint5;
        float f = context.getResources().getDisplayMetrics().density;
        Resources resources = context.getResources();
        TypedArray obtainTypedArray =
                resources.obtainTypedArray(com.android.settings.R.array.batterymeter_color_levels);
        Intrinsics.checkNotNullExpressionValue(obtainTypedArray, "obtainTypedArray(...)");
        TypedArray obtainTypedArray2 =
                resources.obtainTypedArray(com.android.settings.R.array.batterymeter_color_values);
        Intrinsics.checkNotNullExpressionValue(obtainTypedArray2, "obtainTypedArray(...)");
        int length = obtainTypedArray.length();
        this.colorLevels = new int[length * 2];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            this.colorLevels[i3] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                this.colorLevels[i3 + 1] =
                        Utils.getColorAttrDefaultColor(
                                this.context, obtainTypedArray2.getThemeAttributeId(i2, 0));
            } else {
                this.colorLevels[i3 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        String string =
                this.context.getResources().getString(R.string.content_description_collapsed);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        this.perimeterPath.set(PathParser.createPathFromPathData(string));
        this.perimeterPath.computeBounds(new RectF(), true);
        String string2 =
                this.context.getResources().getString(R.string.contentServiceSyncNotificationTitle);
        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
        this.errorPerimeterPath.set(PathParser.createPathFromPathData(string2));
        this.errorPerimeterPath.computeBounds(new RectF(), true);
        String string3 =
                this.context
                        .getResources()
                        .getString(R.string.contentServiceTooManyDeletesNotificationDesc);
        Intrinsics.checkNotNullExpressionValue(string3, "getString(...)");
        this.fillMask.set(PathParser.createPathFromPathData(string3));
        this.fillMask.computeBounds(this.fillRect, true);
        String string4 = this.context.getResources().getString(R.string.contentServiceSync);
        Intrinsics.checkNotNullExpressionValue(string4, "getString(...)");
        this.boltPath.set(PathParser.createPathFromPathData(string4));
        String string5 =
                this.context.getResources().getString(R.string.content_description_expanded);
        Intrinsics.checkNotNullExpressionValue(string5, "getString(...)");
        this.plusPath.set(PathParser.createPathFromPathData(string5));
        this.dualTone =
                this.context
                        .getResources()
                        .getBoolean(R.bool.config_bg_current_drain_high_threshold_by_bg_location);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas c) {
        float m;
        Intrinsics.checkNotNullParameter(c, "c");
        c.saveLayer(null, null);
        this.unifiedPath.reset();
        this.levelPath.reset();
        this.levelRect.set(this.fillRect);
        int i = this.batteryLevel;
        float f = i / 100.0f;
        if (i >= 95) {
            m = this.fillRect.top;
        } else {
            RectF rectF = this.fillRect;
            m = AndroidFlingSpline$$ExternalSyntheticOutline0.m(1, f, rectF.height(), rectF.top);
        }
        this.levelRect.top = (float) Math.floor(m);
        this.levelPath.addRect(this.levelRect, Path.Direction.CCW);
        this.unifiedPath.addPath(this.scaledPerimeter);
        if (!this.dualTone) {
            this.unifiedPath.op(this.levelPath, Path.Op.UNION);
        }
        this.fillPaint.setColor(this.levelColor);
        if (this.charging) {
            this.unifiedPath.op(this.scaledBolt, Path.Op.DIFFERENCE);
            if (!this.invertFillIcon) {
                c.drawPath(this.scaledBolt, this.fillPaint);
            }
        }
        if (this.dualTone) {
            c.drawPath(this.unifiedPath, this.dualToneBackgroundFill);
            c.save();
            c.clipRect(
                    0.0f,
                    getBounds().bottom - (getBounds().height() * f),
                    getBounds().right,
                    getBounds().bottom);
            c.drawPath(this.unifiedPath, this.fillPaint);
            c.restore();
        } else {
            this.fillPaint.setColor(this.fillColor);
            c.drawPath(this.unifiedPath, this.fillPaint);
            this.fillPaint.setColor(this.levelColor);
            if (this.batteryLevel <= 20 && !this.charging) {
                c.save();
                c.clipPath(this.scaledFill);
                c.drawPath(this.levelPath, this.fillPaint);
                c.restore();
            }
        }
        if (this.charging) {
            c.clipOutPath(this.scaledBolt);
            if (this.invertFillIcon) {
                c.drawPath(this.scaledBolt, this.fillColorStrokePaint);
            } else {
                c.drawPath(this.scaledBolt, this.fillColorStrokeProtection);
            }
        } else if (this.powerSaveEnabled) {
            c.drawPath(this.levelPath, this.errorPaint);
            this.fillPaint.setColor(this.fillColor);
            c.drawPath(this.scaledPlus, this.fillPaint);
        }
        c.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect bounds) {
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        super.onBoundsChange(bounds);
        Rect bounds2 = getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds2, "getBounds(...)");
        if (bounds2.isEmpty()) {
            this.scaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.scaleMatrix.setScale(bounds2.right / 12.0f, bounds2.bottom / 20.0f);
        }
        this.perimeterPath.transform(this.scaleMatrix, this.scaledPerimeter);
        this.errorPerimeterPath.transform(this.scaleMatrix, this.scaledErrorPerimeter);
        this.fillMask.transform(this.scaleMatrix, this.scaledFill);
        this.scaledFill.computeBounds(this.fillRect, true);
        this.boltPath.transform(this.scaleMatrix, this.scaledBolt);
        this.plusPath.transform(this.scaleMatrix, this.scaledPlus);
        float max = Math.max((bounds2.right / 12.0f) * 3.0f, 6.0f);
        this.fillColorStrokePaint.setStrokeWidth(max);
        this.fillColorStrokeProtection.setStrokeWidth(max);
    }

    public final void setBatteryLevel(int i) {
        int i2;
        int i3 = 0;
        this.invertFillIcon = i >= 67 ? true : i <= 33 ? false : this.invertFillIcon;
        this.batteryLevel = i;
        if (!this.charging && !this.powerSaveEnabled) {
            i2 = 0;
            while (true) {
                int[] iArr = this.colorLevels;
                if (i3 >= iArr.length) {
                    break;
                }
                int i4 = iArr[i3];
                int i5 = iArr[i3 + 1];
                if (i <= i4) {
                    i2 = i3 == iArr.length + (-2) ? this.fillColor : i5;
                } else {
                    i3 += 2;
                    i2 = i5;
                }
            }
        } else {
            i2 = this.fillColor;
        }
        this.levelColor = i2;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        this.fillPaint.setColorFilter(colorFilter);
        this.fillColorStrokePaint.setColorFilter(colorFilter);
        this.dualToneBackgroundFill.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}
}
