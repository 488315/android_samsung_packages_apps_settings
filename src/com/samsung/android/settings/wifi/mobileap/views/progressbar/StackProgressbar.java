package com.samsung.android.settings.wifi.mobileap.views.progressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.PathInterpolator;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.interpolator.SineInOut90;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class StackProgressbar extends AbsProgressbar {
    public final float[] mGraphWidths;
    public boolean mIsTop10ColorsToBeShown;
    public float mTotalScreenTime;

    public StackProgressbar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mProgress = 0.0f;
        this.mGraphHeight = getResources().getDimension(R.dimen.wifi_ap_progress_bar_height);
        this.mGraphRadius = getResources().getDimension(R.dimen.wifi_ap_progress_bar_radius);
        this.mGraphOutlinePath = new Path();
        Paint paint = new Paint();
        this.mGraphOutlineStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        Paint paint2 = this.mGraphOutlineStrokePaint;
        Paint.Join join = Paint.Join.ROUND;
        paint2.setStrokeJoin(join);
        this.mGraphOutlineStrokePaint.setStrokeWidth(
                getResources().getDimensionPixelSize(R.dimen.home_screen_time_graph_border));
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
        this.mGraphWidths = new float[21];
        for (int i = 0; i < 21; i++) {
            this.mGraphWidths[i] = 0.0f;
        }
        this.mGraphOutlineStrokePaint.setColor(
                getResources()
                        .getColor(
                                R.color.dw_data_outline_light_color_no_theme,
                                getContext().getTheme()));
        this.mBarBgPaint.setColor(
                getResources().getColor(R.color.dw_data_type_5, getContext().getTheme()));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mProgressAnimator = ofFloat;
        SineInOut90 sineInOut90 = new SineInOut90();
        float[] fArr = {0.33f, 0.0f, 0.1f, 1.0f};
        sineInOut90.cubic = new PathInterpolator(fArr[0], fArr[1], fArr[2], fArr[3]);
        ofFloat.setInterpolator(sineInOut90);
        this.mProgressAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.wifi.mobileap.views.progressbar.AbsProgressbar$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        AbsProgressbar absProgressbar = AbsProgressbar.this;
                        absProgressbar.getClass();
                        absProgressbar.mProgress =
                                ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        absProgressbar.invalidate();
                    }
                });
        this.mProgressAnimator.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.settings.wifi.mobileap.views.progressbar.AbsProgressbar.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        AbsProgressbar.this.mProgress = 1.0f;
                    }

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        AbsProgressbar.this.mProgress = 0.0f;
                    }
                });
        boolean z = WifiApSettingsUtils.DBG;
        if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
            this.mProgress = 1.0f;
        } else {
            this.mProgress = 0.0f;
        }
        this.mIsTop10ColorsToBeShown = false;
    }

    @Override // com.samsung.android.settings.wifi.mobileap.views.progressbar.AbsProgressbar,
              // android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        float f;
        float f2;
        int i3;
        int i4;
        float f3;
        float f4;
        this.mGraphOutlinePath.reset();
        Path path = this.mGraphOutlinePath;
        float width = getWidth();
        float f5 = this.mGraphHeight;
        float f6 = this.mGraphRadius;
        path.addRoundRect(0.0f, 0.0f, width, f5, f6, f6, Path.Direction.CCW);
        canvas.clipPath(this.mGraphOutlinePath);
        float width2 = getWidth();
        float f7 = this.mGraphHeight;
        float f8 = this.mGraphRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width2, f7, f8, f8, this.mBarBgPaint);
        for (int i5 = 0; i5 < this.mGraphWidths.length; i5++) {
            int i6 =
                    this.mIsTop10ColorsToBeShown
                            ? getContext()
                                    .getResources()
                                    .getIntArray(R.array.wifi_ap_top_10_progressbar_color_array)[i5]
                            : getContext()
                                    .getResources()
                                    .getIntArray(R.array.wifi_ap_top_3_progressbar_color_array)[i5];
            if (i6 != -1) {
                this.mBarPaint.setColor(i6);
                boolean z = WifiApSettingsUtils.DBG;
                if (TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1) {
                    int width3 = getWidth();
                    if (i5 != 0) {
                        float f9 = 0.0f;
                        for (int i7 = 0; i7 < i5; i7++) {
                            float[] fArr = this.mGraphWidths;
                            if (fArr != null) {
                                float f10 = this.mTotalScreenTime;
                                if (f10 != 0.0f) {
                                    f4 = (fArr[i7] / f10) * this.mMaxGraphWidth;
                                    f9 += f4;
                                }
                            }
                            f4 = 0.0f;
                            f9 += f4;
                        }
                        i3 = Math.round(f9);
                    } else {
                        i3 = 0;
                    }
                    float f11 = width3 - i3;
                    int width4 = getWidth();
                    if (i5 < 21) {
                        float f12 = 0.0f;
                        for (int i8 = 0; i8 <= i5; i8++) {
                            float[] fArr2 = this.mGraphWidths;
                            if (fArr2 != null) {
                                float f13 = this.mTotalScreenTime;
                                if (f13 != 0.0f) {
                                    f3 = (fArr2[i8] / f13) * this.mMaxGraphWidth;
                                    f12 += f3;
                                }
                            }
                            f3 = 0.0f;
                            f12 += f3;
                        }
                        i4 = Math.round(f12);
                    } else {
                        i4 = 0;
                    }
                    canvas.drawRect(f11, 0.0f, width4 - i4, this.mGraphHeight, this.mBarPaint);
                } else {
                    if (i5 != 0) {
                        float f14 = 0.0f;
                        for (int i9 = 0; i9 < i5; i9++) {
                            float[] fArr3 = this.mGraphWidths;
                            if (fArr3 != null) {
                                float f15 = this.mTotalScreenTime;
                                if (f15 != 0.0f) {
                                    f2 = (fArr3[i9] / f15) * this.mMaxGraphWidth;
                                    f14 += f2;
                                }
                            }
                            f2 = 0.0f;
                            f14 += f2;
                        }
                        i = Math.round(f14);
                    } else {
                        i = 0;
                    }
                    float f16 = i;
                    if (i5 < 21) {
                        float f17 = 0.0f;
                        for (int i10 = 0; i10 <= i5; i10++) {
                            float[] fArr4 = this.mGraphWidths;
                            if (fArr4 != null) {
                                float f18 = this.mTotalScreenTime;
                                if (f18 != 0.0f) {
                                    f = (fArr4[i10] / f18) * this.mMaxGraphWidth;
                                    f17 += f;
                                }
                            }
                            f = 0.0f;
                            f17 += f;
                        }
                        i2 = Math.round(f17);
                    } else {
                        i2 = 0;
                    }
                    canvas.drawRect(f16, 0.0f, i2, this.mGraphHeight, this.mBarPaint);
                }
            }
        }
        this.mGraphOutlinePath.reset();
        Path path2 = this.mGraphOutlinePath;
        float f19 = this.mMaxGraphWidth;
        float f20 = this.mGraphHeight;
        float f21 = this.mGraphRadius;
        path2.addRoundRect(0.0f, 0.0f, f19, f20, f21, f21, Path.Direction.CCW);
        float width5 = getWidth();
        float f22 = this.mGraphHeight;
        float f23 = this.mGraphRadius;
        canvas.drawRoundRect(0.0f, 0.0f, width5, f22, f23, f23, this.mGraphOutlineStrokePaint);
        canvas.clipPath(this.mGraphOutlinePath);
        super.onDraw(canvas);
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
