package com.samsung.android.settings.wifi.develop.weeklyreport.progressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WeeklyReportProgressbar extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Paint mBarBgPaint;
    public final Paint mBarPaint;
    public final float mGraphHeight;
    public final Path mGraphOutlinePath;
    public final Paint mGraphOutlineStrokePaint;
    public final float mGraphRadius;
    public final float[] mGraphWidths;
    public boolean mIsBandColor;
    public float mMaxGraphWidth;
    public float mProgress;
    public final ValueAnimator mProgressAnimator;
    public float mTotalScreenTime;

    public WeeklyReportProgressbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgress = 0.0f;
        this.mGraphHeight =
                getResources().getDimension(R.dimen.wifi_weekly_report_progress_bar_height);
        this.mGraphRadius =
                getResources().getDimension(R.dimen.wifi_weekly_report_progress_bar_radius);
        this.mGraphOutlinePath = new Path();
        Paint paint = new Paint();
        this.mGraphOutlineStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        Paint paint2 = this.mGraphOutlineStrokePaint;
        Paint.Join join = Paint.Join.ROUND;
        paint2.setStrokeJoin(join);
        this.mGraphOutlineStrokePaint.setStrokeWidth(
                getResources()
                        .getDimensionPixelSize(
                                R.dimen.wifi_weekly_report_home_screen_time_graph_border));
        this.mGraphOutlineStrokePaint.setAntiAlias(true);
        Paint paint3 = new Paint();
        this.mBarBgPaint = paint3;
        Paint.Style style = Paint.Style.FILL;
        paint3.setStyle(style);
        this.mBarBgPaint.setStrokeJoin(join);
        this.mBarBgPaint.setAntiAlias(true);
        Paint paint4 = new Paint();
        this.mBarPaint = paint4;
        paint4.setStyle(style);
        this.mBarPaint.setStrokeJoin(join);
        this.mBarPaint.setAntiAlias(true);
        this.mTotalScreenTime = 0.0f;
        this.mGraphWidths = new float[4];
        for (int i = 0; i < 4; i++) {
            this.mGraphWidths[i] = 0.0f;
        }
        this.mGraphOutlineStrokePaint.setColor(
                getResources()
                        .getColor(
                                R.color.sec_wifi_labs_weekly_report_progressbar_data_outline_color,
                                getContext().getTheme()));
        this.mBarBgPaint.setColor(
                getResources()
                        .getColor(
                                R.color.sec_wifi_labs_weekly_report_progressbar_data_type_color,
                                getContext().getTheme()));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mProgressAnimator = ofFloat;
        SineInOut90 sineInOut90 = new SineInOut90();
        sineInOut90.mCubic = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        ofFloat.setInterpolator(sineInOut90);
        this.mProgressAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.wifi.develop.weeklyreport.progressbar.WeeklyReportAbsProgressbar$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        WeeklyReportProgressbar weeklyReportProgressbar =
                                WeeklyReportProgressbar.this;
                        int i2 = WeeklyReportProgressbar.$r8$clinit;
                        weeklyReportProgressbar.getClass();
                        weeklyReportProgressbar.mProgress =
                                ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        weeklyReportProgressbar.invalidate();
                    }
                });
        this.mProgressAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.wifi.develop.weeklyreport.progressbar.WeeklyReportAbsProgressbar$1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        WeeklyReportProgressbar.this.mProgress = 1.0f;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        WeeklyReportProgressbar.this.mProgress = 0.0f;
                    }
                });
        this.mIsBandColor = true;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        float f;
        float f2;
        this.mGraphOutlinePath.reset();
        Path path = this.mGraphOutlinePath;
        float width = getWidth();
        float f3 = this.mGraphHeight;
        float f4 = this.mGraphRadius;
        path.addRoundRect(0.0f, 0.0f, width, f3, f4, f4, Path.Direction.CCW);
        canvas.clipPath(this.mGraphOutlinePath);
        float width2 = getWidth();
        float f5 = this.mGraphHeight;
        float f6 = this.mGraphRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width2, f5, f6, f6, this.mBarBgPaint);
        for (int i3 = 0; i3 < this.mGraphWidths.length; i3++) {
            int i4 =
                    this.mIsBandColor
                            ? getContext()
                                    .getResources()
                                    .getIntArray(R.array.wifi_progressbar_band_color_array)[i3]
                            : getContext()
                                    .getResources()
                                    .getIntArray(R.array.wifi_progressbar_standard_color_array)[i3];
            if (i4 != -1) {
                this.mBarPaint.setColor(i4);
                if (i3 != 0) {
                    float f7 = 0.0f;
                    for (int i5 = 0; i5 < i3; i5++) {
                        float[] fArr = this.mGraphWidths;
                        if (fArr != null) {
                            float f8 = this.mTotalScreenTime;
                            if (f8 != 0.0f) {
                                f2 = (fArr[i5] / f8) * this.mMaxGraphWidth;
                                f7 += f2;
                            }
                        }
                        f2 = 0.0f;
                        f7 += f2;
                    }
                    i = Math.round(f7);
                } else {
                    i = 0;
                }
                float f9 = i;
                if (i3 < 4) {
                    float f10 = 0.0f;
                    for (int i6 = 0; i6 <= i3; i6++) {
                        float[] fArr2 = this.mGraphWidths;
                        if (fArr2 != null) {
                            float f11 = this.mTotalScreenTime;
                            if (f11 != 0.0f) {
                                f = (fArr2[i6] / f11) * this.mMaxGraphWidth;
                                f10 += f;
                            }
                        }
                        f = 0.0f;
                        f10 += f;
                    }
                    i2 = Math.round(f10);
                } else {
                    i2 = 0;
                }
                canvas.drawRect(f9, 0.0f, i2, this.mGraphHeight, this.mBarPaint);
            }
        }
        this.mGraphOutlinePath.reset();
        Path path2 = this.mGraphOutlinePath;
        float f12 = this.mMaxGraphWidth;
        float f13 = this.mGraphHeight;
        float f14 = this.mGraphRadius;
        path2.addRoundRect(0.0f, 0.0f, f12, f13, f14, f14, Path.Direction.CCW);
        float width3 = getWidth();
        float f15 = this.mGraphHeight;
        float f16 = this.mGraphRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width3, f15, f16, f16, this.mGraphOutlineStrokePaint);
        canvas.clipPath(this.mGraphOutlinePath);
        onDraw$com$samsung$android$settings$wifi$develop$weeklyreport$progressbar$WeeklyReportAbsProgressbar(
                canvas);
    }

    public final void
            onDraw$com$samsung$android$settings$wifi$develop$weeklyreport$progressbar$WeeklyReportAbsProgressbar(
                    Canvas canvas) {
        this.mMaxGraphWidth = (int) (getWidth() * this.mProgress);
    }

    public final void setProgressbar(float[] fArr, float f, boolean z) {
        this.mTotalScreenTime = f;
        float f2 = 0.0f;
        for (int i = 0; i < fArr.length; i++) {
            this.mGraphWidths[i] = fArr[i];
            f2 += fArr[i];
        }
        if (!z) {
            invalidate();
        } else {
            this.mProgressAnimator.setDuration((long) ((f2 / this.mTotalScreenTime) * 2000.0f));
            this.mProgressAnimator.start();
        }
    }
}
