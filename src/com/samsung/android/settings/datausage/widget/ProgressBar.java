package com.samsung.android.settings.datausage.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.settings.R;
import com.android.settings.R$styleable;

import com.samsung.android.settings.wifi.mobileap.clients.report.chart.ViewUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProgressBar extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAlphaValue;
    public final Paint mAnimBarPaint;
    public final float mAnimBarPosition;
    public final float mAnimBarRadius;
    public final float mAnimBarWidth;
    public int mAnimType;
    public int mBackgroundColor;
    public final Paint mBackgroundPaint;
    public int mBackgroundStrokeColor;
    public final Path mBgPath;
    public final long[] mGradientColorArray;
    public final boolean mGradientTopCoat;
    public final boolean mIsRTL;
    public final boolean mIsReverse;
    public final boolean mIsVerticalProgress;
    public float mMaxHeight;
    public float mMaxWidth;
    public final float mOutlineRadius;
    public final Paint mOutlineStrokePaint;
    public final float mOutlineStrokeSize;
    public ValueAnimator mProgressAnim;
    public final Paint mProgressBarPaint;
    public final int mProgressColor;
    public float mProgressSize;
    public final Path mRadiusSubPath;
    public float mScore;
    public final RectF mStrokeRect;
    public final Paint mTopGradientPaint;

    public ProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, 0, i);
        this.mStrokeRect = new RectF();
        this.mIsRTL = false;
        this.mGradientTopCoat = false;
        Log.d("BaseProgressBar", "ProgressBar");
        this.mIsRTL = ViewUtils.isRTL();
        try {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.SecBaseProgressBar, 0, i);
            try {
                this.mOutlineRadius =
                        obtainStyledAttributes.getDimension(
                                2,
                                getResources()
                                        .getDimension(
                                                R.dimen.sec_second_depth_progress_bar_radius));
                this.mOutlineStrokeSize =
                        obtainStyledAttributes.getDimension(
                                4,
                                getResources().getDimension(R.dimen.sec_progress_bar_stroke_width));
                this.mBackgroundStrokeColor =
                        obtainStyledAttributes.getColor(
                                3,
                                getResources()
                                        .getColor(
                                                R.color.sec_round_corner_progress_bar_stroke_color,
                                                getContext().getTheme()));
                this.mBackgroundColor =
                        obtainStyledAttributes.getColor(
                                0,
                                getResources()
                                        .getColor(
                                                R.color
                                                        .sec_round_corner_progress_bar_background_color_theme,
                                                getContext().getTheme()));
                this.mProgressColor =
                        obtainStyledAttributes.getColor(
                                1,
                                getResources()
                                        .getColor(
                                                R.color
                                                        .sec_round_corner_progress_bar_anim_color_theme,
                                                getContext().getTheme()));
                this.mIsVerticalProgress = obtainStyledAttributes.getBoolean(5, false);
                this.mGradientTopCoat = obtainStyledAttributes.getBoolean(6, false);
                obtainStyledAttributes.close();
            } finally {
            }
        } catch (Exception e) {
            Log.e("BaseProgressBar", "Error obtaining styled attributes", e);
        }
        Log.d(
                "BaseProgressBar",
                "initView. " + this.mOutlineRadius + ", " + this.mOutlineStrokeSize);
        this.mBgPath = new Path();
        Paint paint = new Paint();
        this.mOutlineStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mOutlineStrokePaint.setAntiAlias(true);
        this.mOutlineStrokePaint.setStrokeWidth(this.mOutlineStrokeSize);
        this.mOutlineStrokePaint.setColor(this.mBackgroundStrokeColor);
        Paint paint2 = new Paint();
        this.mBackgroundPaint = paint2;
        Paint.Style style = Paint.Style.FILL_AND_STROKE;
        paint2.setStyle(style);
        this.mBackgroundPaint.setAntiAlias(true);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        Paint paint3 = new Paint();
        this.mProgressBarPaint = paint3;
        paint3.setStyle(style);
        this.mProgressBarPaint.setAntiAlias(true);
        this.mRadiusSubPath = new Path();
        this.mProgressBarPaint.setColor(this.mProgressColor);
        if (this.mGradientTopCoat) {
            Paint paint4 = new Paint();
            this.mTopGradientPaint = paint4;
            paint4.setStyle(style);
            this.mTopGradientPaint.setAntiAlias(true);
            this.mGradientColorArray =
                    new long[] {
                        Color.pack(
                                getContext().getColor(R.color.sec_graph_top_gradient_color_start)),
                        Color.pack(getContext().getColor(R.color.sec_graph_top_gradient_color_end))
                    };
        }
        this.mAlphaValue = 255;
        this.mAnimBarPosition = 0.0f;
        this.mAnimType = 0;
        this.mAnimBarWidth = getResources().getDimension(R.dimen.sec_progress_anim_bar_width);
        this.mAnimBarRadius = getResources().getDimension(R.dimen.sec_progress_anim_bar_radius);
        Paint paint5 = new Paint();
        this.mAnimBarPaint = paint5;
        paint5.setShader(
                new LinearGradient(
                        0.0f,
                        0.0f,
                        getWidth(),
                        0.0f,
                        getResources().getColor(R.color.sec_data_usage_blue_color_alpha),
                        getResources().getColor(R.color.sec_data_usage_blue_color),
                        Shader.TileMode.CLAMP));
        paint5.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAnimType = 0;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        onDraw$com$samsung$android$settings$datausage$widget$SecBaseProgressBar(canvas);
        if (this.mAnimType == 1) {
            if (this.mIsVerticalProgress) {
                float f = this.mMaxWidth;
                float f2 = this.mAnimBarWidth;
                float f3 = this.mAnimBarPosition;
                RectF rectF = new RectF(f - f2, f3 + f2, f2, f3);
                float f4 = this.mAnimBarRadius;
                canvas.drawRoundRect(rectF, f4, f4, this.mAnimBarPaint);
            } else {
                float f5 = this.mAnimBarPosition;
                float f6 = this.mAnimBarWidth;
                RectF rectF2 = new RectF(f5, f6, f5 + f6, this.mMaxHeight - f6);
                float f7 = this.mAnimBarRadius;
                canvas.drawRoundRect(rectF2, f7, f7, this.mAnimBarPaint);
            }
            this.mAnimBarPaint.setAlpha(this.mAlphaValue);
        }
    }

    public final void onDraw$com$samsung$android$settings$datausage$widget$SecBaseProgressBar(
            Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        this.mBgPath.reset();
        float f = this.mOutlineStrokeSize;
        Path path = this.mBgPath;
        float f2 = this.mMaxWidth - f;
        float f3 = this.mMaxHeight - f;
        float f4 = this.mOutlineRadius;
        Path.Direction direction = Path.Direction.CCW;
        path.addRoundRect(f, f, f2, f3, f4, f4, direction);
        canvas.clipPath(this.mBgPath);
        canvas.drawPaint(this.mBackgroundPaint);
        if (this.mIsVerticalProgress) {
            canvas.drawRect(
                    0.0f,
                    0.0f,
                    this.mMaxWidth,
                    this.mMaxHeight - this.mProgressSize,
                    this.mBackgroundPaint);
            float f5 = this.mMaxHeight;
            canvas.drawRect(
                    0.0f, f5 - this.mProgressSize, this.mMaxWidth, f5, this.mProgressBarPaint);
        } else {
            if (this.mAnimType == 0) {
                float f6 = this.mProgressSize;
                float f7 = this.mMaxHeight;
                if (f6 < f7) {
                    this.mProgressSize = f7;
                }
            }
            this.mRadiusSubPath.reset();
            if (this.mIsRTL) {
                Path path2 = this.mRadiusSubPath;
                float f8 = this.mMaxWidth;
                RectF rectF = new RectF(f8 - this.mProgressSize, 0.0f, f8, this.mMaxHeight);
                float f9 = this.mMaxHeight;
                path2.addRoundRect(rectF, f9, f9, direction);
                canvas.clipPath(this.mRadiusSubPath);
                float f10 = this.mMaxWidth;
                canvas.drawRect(
                        f10 - this.mProgressSize,
                        0.0f,
                        f10,
                        this.mMaxHeight,
                        this.mProgressBarPaint);
                if (this.mGradientTopCoat) {
                    float f11 = this.mMaxWidth;
                    canvas.drawRect(
                            f11 - this.mProgressSize,
                            0.0f,
                            f11,
                            this.mMaxHeight,
                            this.mTopGradientPaint);
                }
            } else {
                Path path3 = this.mRadiusSubPath;
                RectF rectF2 = new RectF(0.0f, 0.0f, this.mProgressSize, this.mMaxHeight);
                float f12 = this.mMaxHeight;
                path3.addRoundRect(rectF2, f12, f12, direction);
                canvas.clipPath(this.mRadiusSubPath);
                canvas.drawRect(
                        0.0f, 0.0f, this.mProgressSize, this.mMaxHeight, this.mProgressBarPaint);
                if (this.mGradientTopCoat) {
                    canvas.drawRect(
                            0.0f,
                            0.0f,
                            this.mProgressSize,
                            this.mMaxHeight,
                            this.mTopGradientPaint);
                }
            }
        }
        canvas.restore();
        float f13 = this.mOutlineStrokeSize;
        if (f13 != 0.0f) {
            float f14 = f13 / 2.0f;
            this.mStrokeRect.set(f14, f14, this.mMaxWidth - f14, this.mMaxHeight - f14);
            RectF rectF3 = this.mStrokeRect;
            float f15 = this.mOutlineRadius;
            canvas.drawRoundRect(rectF3, f15, f15, this.mOutlineStrokePaint);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        onMeasure$com$samsung$android$settings$datausage$widget$SecBaseProgressBar(i, i2);
    }

    public final void onMeasure$com$samsung$android$settings$datausage$widget$SecBaseProgressBar(
            int i, int i2) {
        this.mMaxHeight = View.MeasureSpec.getSize(i2);
        float size = View.MeasureSpec.getSize(i);
        this.mMaxWidth = size;
        if (size > 0.0f && this.mMaxHeight > 0.0f) {
            setProgress(this.mScore);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        int i2 = this.mBackgroundStrokeColor;
        this.mBackgroundColor = i;
        this.mBackgroundStrokeColor = i2;
        this.mBackgroundPaint.setColor(i);
        this.mOutlineStrokePaint.setColor(i2);
        invalidate();
    }

    public final void setProgress(float f) {
        this.mScore = f;
        if (f == 0.0f) {
            this.mProgressSize = 0.0f;
        } else {
            float f2 = 100.0f / f;
            if (this.mIsVerticalProgress) {
                this.mProgressSize = this.mMaxHeight / f2;
            } else {
                this.mProgressSize = this.mMaxWidth / f2;
            }
            if (this.mGradientTopCoat) {
                this.mTopGradientPaint.setShader(
                        new LinearGradient(
                                0.0f,
                                0.0f,
                                this.mProgressSize,
                                0.0f,
                                this.mGradientColorArray,
                                new float[] {0.0f, 1.0f},
                                Shader.TileMode.CLAMP));
            }
        }
        invalidate();
    }

    public ProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
