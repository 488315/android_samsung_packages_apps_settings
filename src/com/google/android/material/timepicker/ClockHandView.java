package com.google.android.material.timepicker;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class ClockHandView extends View {
    public final int animationDuration;
    public final TimeInterpolator animationInterpolator;
    public final float centerDotRadius;
    public boolean changedDuringTouch;
    public int circleRadius;
    public int currentLevel;
    public double degRad;
    public float downX;
    public float downY;
    public boolean isMultiLevel;
    public final List listeners;
    public float originalDeg;
    public final Paint paint;
    public final ValueAnimator rotationAnimator;
    public final int scaledTouchSlop;
    public final RectF selectorBox;
    public final int selectorRadius;
    public final int selectorStrokeWidth;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnRotateListener {}

    public ClockHandView(Context context) {
        this(context, null);
    }

    public final int getLeveledCircleRadius(int i) {
        int i2 = this.circleRadius;
        return i == 2 ? Math.round(i2 * 0.66f) : i2;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        float f = width;
        float leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float cos = (((float) Math.cos(this.degRad)) * leveledCircleRadius) + f;
        float f2 = height;
        float sin = (leveledCircleRadius * ((float) Math.sin(this.degRad))) + f2;
        this.paint.setStrokeWidth(0.0f);
        canvas.drawCircle(cos, sin, this.selectorRadius, this.paint);
        double sin2 = Math.sin(this.degRad);
        double cos2 = Math.cos(this.degRad);
        this.paint.setStrokeWidth(this.selectorStrokeWidth);
        canvas.drawLine(
                f, f2, width + ((int) (cos2 * r7)), height + ((int) (r7 * sin2)), this.paint);
        canvas.drawCircle(f, f2, this.centerDotRadius, this.paint);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.rotationAnimator.isRunning()) {
            return;
        }
        setHandRotation(this.originalDeg);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        int actionMasked = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        boolean z3 = false;
        if (actionMasked == 0) {
            this.changedDuringTouch = false;
            z = true;
            z2 = false;
        } else if (actionMasked == 1 || actionMasked == 2) {
            z2 = this.changedDuringTouch;
            if (this.isMultiLevel) {
                this.currentLevel =
                        ((float)
                                                Math.hypot(
                                                        (double) (x - ((float) (getWidth() / 2))),
                                                        (double) (y - ((float) (getHeight() / 2)))))
                                        <= ((float) getLeveledCircleRadius(2))
                                                + ViewUtils.dpToPx(getContext(), 12)
                                ? 2
                                : 1;
            }
            z = false;
        } else {
            z2 = false;
            z = false;
        }
        boolean z4 = this.changedDuringTouch;
        int degrees = (int) Math.toDegrees(Math.atan2(y - (getHeight() / 2), x - (getWidth() / 2)));
        int i = degrees + 90;
        if (i < 0) {
            i = degrees + 450;
        }
        float f = i;
        boolean z5 = this.originalDeg != f;
        if (!z || !z5) {
            if (z5 || z2) {
                setHandRotation(f);
            }
            this.changedDuringTouch = z4 | z3;
            return true;
        }
        z3 = true;
        this.changedDuringTouch = z4 | z3;
        return true;
    }

    public final void setHandRotation(float f) {
        ValueAnimator valueAnimator = this.rotationAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        float f2 = f % 360.0f;
        this.originalDeg = f2;
        this.degRad = Math.toRadians(f2 - 90.0f);
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        float leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float cos = (((float) Math.cos(this.degRad)) * leveledCircleRadius) + width;
        float sin = (leveledCircleRadius * ((float) Math.sin(this.degRad))) + height;
        RectF rectF = this.selectorBox;
        float f3 = this.selectorRadius;
        rectF.set(cos - f3, sin - f3, cos + f3, sin + f3);
        Iterator it = ((ArrayList) this.listeners).iterator();
        while (it.hasNext()) {
            ClockFaceView clockFaceView = (ClockFaceView) ((OnRotateListener) it.next());
            if (Math.abs(clockFaceView.currentHandRotation - f2) > 0.001f) {
                clockFaceView.currentHandRotation = f2;
                clockFaceView.findIntersectingTextView();
            }
        }
        invalidate();
    }

    public ClockHandView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rotationAnimator = new ValueAnimator();
        this.listeners = new ArrayList();
        Paint paint = new Paint();
        this.paint = paint;
        this.selectorBox = new RectF();
        this.currentLevel = 1;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.ClockHandView, i, 2132084964);
        MotionUtils.resolveThemeDuration(context, R.attr.motionDurationLong2, 200);
        MotionUtils.resolveThemeInterpolator(
                context,
                R.attr.motionEasingEmphasizedInterpolator,
                AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        this.selectorRadius = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        this.selectorStrokeWidth =
                getResources().getDimensionPixelSize(R.dimen.material_clock_hand_stroke_width);
        this.centerDotRadius =
                r3.getDimensionPixelSize(R.dimen.material_clock_hand_center_dot_radius);
        int color = obtainStyledAttributes.getColor(0, 0);
        paint.setAntiAlias(true);
        paint.setColor(color);
        setHandRotation(0.0f);
        ViewConfiguration.get(context).getScaledTouchSlop();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(2);
        obtainStyledAttributes.recycle();
    }
}
