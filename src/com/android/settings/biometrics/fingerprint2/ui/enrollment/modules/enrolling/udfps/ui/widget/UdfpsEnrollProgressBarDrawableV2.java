package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.R$styleable;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001e\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007R"
                + " \u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\u0012\n"
                + "\u0004\b\t\u0010\n"
                + "\u0012\u0004\b\r"
                + "\u0010\u000e\u001a\u0004\b\u000b\u0010\f¨\u0006\u000f"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/udfps/ui/widget/UdfpsEnrollProgressBarDrawableV2;",
            "Landroid/graphics/drawable/Drawable;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "Landroid/graphics/Paint;",
            "fillPaint",
            "Landroid/graphics/Paint;",
            "getFillPaint",
            "()Landroid/graphics/Paint;",
            "getFillPaint$annotations",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class UdfpsEnrollProgressBarDrawableV2 extends Drawable {
    public final AnonymousClass3 backgroundColorUpdateListener;
    public final Paint backgroundPaint;
    public final Drawable checkMarkDrawable;
    public final int enrollProgressColor;
    public final int enrollProgressHelp;
    public final int enrollProgressHelpWithTalkback;
    public final AnonymousClass3 fillColorUpdateListener;
    public final Paint fillPaint;
    public boolean isAccessibilityEnabled;
    public final int movingTargetFill;
    public Function0 onFinishedCompletionAnimation;
    public float progress;
    public final int progressBarRadius;
    public final AnonymousClass3 progressUpdateListener;
    public final Rect sensorRect;

    static {
        new DecelerateInterpolator();
    }

    public UdfpsEnrollProgressBarDrawableV2(Context context, AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.sensorRect = new Rect();
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.BiometricsEnrollView,
                        R.attr.biometricsEnrollStyle,
                        R.style.BiometricsEnrollStyle);
        Intrinsics.checkNotNullExpressionValue(
                obtainStyledAttributes, "obtainStyledAttributes(...)");
        int color = obtainStyledAttributes.getColor(5, 0);
        obtainStyledAttributes.getColor(6, 0);
        int color2 = obtainStyledAttributes.getColor(1, 0);
        obtainStyledAttributes.getColor(2, 0);
        obtainStyledAttributes.getColor(3, 0);
        obtainStyledAttributes.recycle();
        float f = (context.getResources().getDisplayMetrics().densityDpi / 160) * 12.0f;
        Paint paint = new Paint();
        paint.setStrokeWidth(f);
        paint.setColor(color);
        paint.setAntiAlias(true);
        Paint.Style style = Paint.Style.STROKE;
        paint.setStyle(style);
        Paint.Cap cap = Paint.Cap.ROUND;
        paint.setStrokeCap(cap);
        this.backgroundPaint = paint;
        Drawable drawable = context.getDrawable(R.drawable.udfps_enroll_checkmark);
        Intrinsics.checkNotNull(drawable);
        this.checkMarkDrawable = drawable;
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(f);
        paint2.setColor(color2);
        paint2.setAntiAlias(true);
        paint2.setStyle(style);
        paint2.setStrokeCap(cap);
        this.fillPaint = paint2;
        this.progressBarRadius =
                context.getResources().getInteger(R.integer.config_udfpsEnrollProgressBar);
        final int i = 0;
        new ValueAnimator.AnimatorUpdateListener(
                this) { // from class:
                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollProgressBarDrawableV2.3
            public final /* synthetic */ UdfpsEnrollProgressBarDrawableV2 this$0;

            {
                this.this$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator animation) {
                switch (i) {
                    case 0:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UdfpsEnrollProgressBarDrawableV2 udfpsEnrollProgressBarDrawableV2 =
                                this.this$0;
                        Object animatedValue = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue, "null cannot be cast to non-null type kotlin.Float");
                        udfpsEnrollProgressBarDrawableV2.progress =
                                ((Float) animatedValue).floatValue();
                        this.this$0.invalidateSelf();
                        break;
                    case 1:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint3 = this.this$0.fillPaint;
                        Object animatedValue2 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue2, "null cannot be cast to non-null type kotlin.Int");
                        paint3.setColor(((Integer) animatedValue2).intValue());
                        this.this$0.invalidateSelf();
                        break;
                    default:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint4 = this.this$0.backgroundPaint;
                        Object animatedValue3 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue3, "null cannot be cast to non-null type kotlin.Int");
                        paint4.setColor(((Integer) animatedValue3).intValue());
                        this.this$0.invalidateSelf();
                        break;
                }
            }
        };
        final int i2 = 1;
        new ValueAnimator.AnimatorUpdateListener(
                this) { // from class:
                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollProgressBarDrawableV2.3
            public final /* synthetic */ UdfpsEnrollProgressBarDrawableV2 this$0;

            {
                this.this$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator animation) {
                switch (i2) {
                    case 0:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UdfpsEnrollProgressBarDrawableV2 udfpsEnrollProgressBarDrawableV2 =
                                this.this$0;
                        Object animatedValue = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue, "null cannot be cast to non-null type kotlin.Float");
                        udfpsEnrollProgressBarDrawableV2.progress =
                                ((Float) animatedValue).floatValue();
                        this.this$0.invalidateSelf();
                        break;
                    case 1:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint3 = this.this$0.fillPaint;
                        Object animatedValue2 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue2, "null cannot be cast to non-null type kotlin.Int");
                        paint3.setColor(((Integer) animatedValue2).intValue());
                        this.this$0.invalidateSelf();
                        break;
                    default:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint4 = this.this$0.backgroundPaint;
                        Object animatedValue3 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue3, "null cannot be cast to non-null type kotlin.Int");
                        paint4.setColor(((Integer) animatedValue3).intValue());
                        this.this$0.invalidateSelf();
                        break;
                }
            }
        };
        final int i3 = 2;
        new ValueAnimator.AnimatorUpdateListener(
                this) { // from class:
                        // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollProgressBarDrawableV2.3
            public final /* synthetic */ UdfpsEnrollProgressBarDrawableV2 this$0;

            {
                this.this$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator animation) {
                switch (i3) {
                    case 0:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        UdfpsEnrollProgressBarDrawableV2 udfpsEnrollProgressBarDrawableV2 =
                                this.this$0;
                        Object animatedValue = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue, "null cannot be cast to non-null type kotlin.Float");
                        udfpsEnrollProgressBarDrawableV2.progress =
                                ((Float) animatedValue).floatValue();
                        this.this$0.invalidateSelf();
                        break;
                    case 1:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint3 = this.this$0.fillPaint;
                        Object animatedValue2 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue2, "null cannot be cast to non-null type kotlin.Int");
                        paint3.setColor(((Integer) animatedValue2).intValue());
                        this.this$0.invalidateSelf();
                        break;
                    default:
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        Paint paint4 = this.this$0.backgroundPaint;
                        Object animatedValue3 = animation.getAnimatedValue();
                        Intrinsics.checkNotNull(
                                animatedValue3, "null cannot be cast to non-null type kotlin.Int");
                        paint4.setColor(((Integer) animatedValue3).intValue());
                        this.this$0.invalidateSelf();
                        break;
                }
            }
        };
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        canvas.save();
        Rect rect = new Rect(this.sensorRect);
        int i = -this.progressBarRadius;
        rect.inset(i, i, i, i);
        canvas.rotate(0 - 90.0f, rect.centerX(), rect.centerY());
        if (this.progress < 1.0f) {
            canvas.drawArc(new RectF(rect), 0.0f, 360.0f, false, this.backgroundPaint);
        }
        if (this.progress > 0.0f) {
            canvas.drawArc(new RectF(rect), 0.0f, this.progress * 360.0f, false, this.fillPaint);
        }
        canvas.restore();
        this.checkMarkDrawable.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return 0;
    }

    @VisibleForTesting
    public static /* synthetic */ void getFillPaint$annotations() {}

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {}

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
