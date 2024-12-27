package com.android.settings.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.View;

import com.android.settings.R;
import com.android.settings.fuelgauge.BatteryUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsageGraph extends View {
    public final int mBottomDividerTint;
    public final int mCornerRadius;
    public final Drawable mDivider;
    public final int mDividerSize;
    public final Paint mFillPaint;
    public final Paint mLinePaint;
    public final SparseIntArray mLocalPaths;
    public final SparseIntArray mLocalProjectedPaths;
    public float mMaxX;
    public float mMaxY;
    public float mMiddleDividerLoc;
    public int mMiddleDividerTint;
    public final Path mPath;
    public final SparseIntArray mPaths;
    public final SparseIntArray mProjectedPaths;
    public final Drawable mTintedDivider;
    public int mTopDividerTint;

    public UsageGraph(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPath = new Path();
        this.mPaths = new SparseIntArray();
        this.mLocalPaths = new SparseIntArray();
        this.mProjectedPaths = new SparseIntArray();
        this.mLocalProjectedPaths = new SparseIntArray();
        this.mMaxX = 100.0f;
        this.mMaxY = 100.0f;
        this.mMiddleDividerLoc = 0.5f;
        this.mMiddleDividerTint = -1;
        this.mTopDividerTint = -1;
        Resources resources = context.getResources();
        Paint paint = new Paint();
        this.mLinePaint = paint;
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setAntiAlias(true);
        int dimensionPixelSize =
                resources.getDimensionPixelSize(R.dimen.usage_graph_line_corner_radius);
        this.mCornerRadius = dimensionPixelSize;
        paint.setPathEffect(new CornerPathEffect(dimensionPixelSize));
        paint.setStrokeWidth(resources.getDimensionPixelSize(R.dimen.usage_graph_line_width));
        Paint paint2 = new Paint(paint);
        this.mFillPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(resources.getColor(R.color.usage_graph_dots));
        Paint paint3 = new Paint(paint);
        paint3.setStyle(style);
        float dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.usage_graph_dot_size);
        float dimensionPixelSize3 =
                resources.getDimensionPixelSize(R.dimen.usage_graph_dot_interval);
        paint3.setStrokeWidth(3.0f * dimensionPixelSize2);
        paint3.setPathEffect(
                new DashPathEffect(new float[] {dimensionPixelSize2, dimensionPixelSize3}, 0.0f));
        paint3.setColor(context.getColor(R.color.usage_graph_dots));
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.listDivider, typedValue, true);
        this.mDivider = context.getDrawable(typedValue.resourceId);
        this.mTintedDivider = context.getDrawable(R.drawable.tw_list_divider_material);
        this.mBottomDividerTint =
                resources.getColor(R.color.sec_data_usage_graph_bottom_line_color);
        this.mDividerSize = resources.getDimensionPixelSize(R.dimen.usage_graph_divider_size);
    }

    public void calculateLocalPaths(SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        long currentTimeMillis = System.currentTimeMillis();
        if (getWidth() == 0) {
            return;
        }
        sparseIntArray2.clear();
        int i = -1;
        boolean z = false;
        int i2 = 0;
        for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
            int keyAt = sparseIntArray.keyAt(i3);
            int valueAt = sparseIntArray.valueAt(i3);
            if (valueAt != -1) {
                i2 = (int) ((keyAt / this.mMaxX) * getWidth());
                i = (int) ((1.0f - (valueAt / this.mMaxY)) * getHeight());
                if (sparseIntArray2.size() > 0) {
                    int keyAt2 = sparseIntArray2.keyAt(sparseIntArray2.size() - 1);
                    int valueAt2 = sparseIntArray2.valueAt(sparseIntArray2.size() - 1);
                    if (valueAt2 != -1
                            && Math.abs(i2 - keyAt2) < this.mCornerRadius
                            && Math.abs(i - valueAt2) < this.mCornerRadius) {
                        z = true;
                    }
                }
                sparseIntArray2.put(i2, i);
                z = false;
            } else if (i3 == 1) {
                sparseIntArray2.put(
                        ((int) (((keyAt + 1) / this.mMaxX) * getWidth())) - 1,
                        (int) ((1.0f - (0.0f / this.mMaxY)) * getHeight()));
            } else {
                if (i3 == sparseIntArray.size() - 1 && z) {
                    sparseIntArray2.put(i2, i);
                }
                sparseIntArray2.put(i2 + 1, -1);
                z = false;
            }
        }
        BatteryUtils.logRuntime(currentTimeMillis, "UsageGraph", "calculateLocalPaths");
    }

    public final void drawDivider(int i, Canvas canvas, int i2) {
        Drawable drawable = this.mDivider;
        if (i2 != -1) {
            this.mTintedDivider.setTint(i2);
            drawable = this.mTintedDivider;
        }
        drawable.setBounds(0, i, canvas.getWidth(), this.mDividerSize + i);
        drawable.draw(canvas);
    }

    public void drawFilledPath(Canvas canvas, SparseIntArray sparseIntArray, Paint paint) {
        if (sparseIntArray.size() == 0) {
            return;
        }
        this.mPath.reset();
        float keyAt = sparseIntArray.keyAt(0);
        this.mPath.moveTo(sparseIntArray.keyAt(0), sparseIntArray.valueAt(0));
        int i = 1;
        while (i < sparseIntArray.size()) {
            int keyAt2 = sparseIntArray.keyAt(i);
            int valueAt = sparseIntArray.valueAt(i);
            if (valueAt == -1) {
                this.mPath.lineTo(sparseIntArray.keyAt(i - 1), getHeight());
                this.mPath.lineTo(keyAt, getHeight());
                this.mPath.close();
                i++;
                if (i < sparseIntArray.size()) {
                    keyAt = sparseIntArray.keyAt(i);
                    this.mPath.moveTo(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
                }
            } else {
                this.mPath.lineTo(keyAt2, valueAt);
            }
            i++;
        }
        canvas.drawPath(this.mPath, paint);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mMiddleDividerLoc != 0.0f) {
            drawDivider(0, canvas, this.mTopDividerTint);
        }
        drawDivider(
                (int) ((canvas.getHeight() - this.mDividerSize) * this.mMiddleDividerLoc),
                canvas,
                this.mMiddleDividerTint);
        drawDivider(canvas.getHeight() - this.mDividerSize, canvas, this.mBottomDividerTint);
        if (this.mLocalPaths.size() == 0 && this.mLocalProjectedPaths.size() == 0) {
            return;
        }
        canvas.save();
        if (getLayoutDirection() == 1) {
            canvas.scale(-1.0f, 1.0f, canvas.getWidth() * 0.5f, 0.0f);
        }
        drawFilledPath(canvas, this.mLocalPaths, this.mFillPaint);
        canvas.restore();
        BatteryUtils.logRuntime(currentTimeMillis, "UsageGraph", "onDraw");
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        long currentTimeMillis = System.currentTimeMillis();
        super.onSizeChanged(i, i2, i3, i4);
        updateGradient();
        calculateLocalPaths(this.mPaths, this.mLocalPaths);
        calculateLocalPaths(this.mProjectedPaths, this.mLocalProjectedPaths);
        BatteryUtils.logRuntime(currentTimeMillis, "UsageGraph", "onSizeChanged");
    }

    public void setAccentColor(int i) {
        this.mLinePaint.setColor(i);
        updateGradient();
        postInvalidate();
    }

    public void setDividerLoc(int i) {
        this.mMiddleDividerLoc = 1.0f - (i / this.mMaxY);
    }

    public final void updateGradient() {
        this.mFillPaint.setShader(
                new LinearGradient(
                        0.0f,
                        0.0f,
                        0.0f,
                        getHeight(),
                        getContext().getResources().getColor(R.color.sec_data_usage_blue_color),
                        getContext()
                                .getResources()
                                .getColor(R.color.sec_data_usage_blue_color_alpha),
                        Shader.TileMode.CLAMP));
    }
}
