package com.google.android.material.card;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.cardview.R$styleable;
import androidx.cardview.widget.RoundRectDrawable;

import com.android.settings.R;

import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.CutCornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RoundedCornerTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialCardViewHelper {
    public static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    public final MaterialShapeDrawable bgDrawable;
    public boolean checkable;
    public Drawable checkedIcon;
    public int checkedIconGravity;
    public int checkedIconMargin;
    public int checkedIconSize;
    public ColorStateList checkedIconTint;
    public LayerDrawable clickableForegroundDrawable;
    public Drawable fgDrawable;
    public final MaterialShapeDrawable foregroundContentDrawable;
    public MaterialShapeDrawable foregroundShapeDrawable;
    public ValueAnimator iconAnimator;
    public final TimeInterpolator iconFadeAnimInterpolator;
    public final int iconFadeInAnimDuration;
    public final int iconFadeOutAnimDuration;
    public final MaterialCardView materialCardView;
    public ColorStateList rippleColor;
    public Drawable rippleDrawable;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorStateList strokeColor;
    public int strokeWidth;
    public final Rect userContentPadding = new Rect();
    public boolean isBackgroundOverwritten = false;
    public float checkedAnimationProgress = 0.0f;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.card.MaterialCardViewHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends InsetDrawable {
        @Override // android.graphics.drawable.Drawable
        public final int getMinimumHeight() {
            return -1;
        }

        @Override // android.graphics.drawable.Drawable
        public final int getMinimumWidth() {
            return -1;
        }

        @Override // android.graphics.drawable.InsetDrawable,
                  // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
        public final boolean getPadding(Rect rect) {
            return false;
        }
    }

    public MaterialCardViewHelper(
            MaterialCardView materialCardView, AttributeSet attributeSet, int i) {
        this.materialCardView = materialCardView;
        MaterialShapeDrawable materialShapeDrawable =
                new MaterialShapeDrawable(
                        materialCardView.getContext(), attributeSet, i, 2132084883);
        this.bgDrawable = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(materialCardView.getContext());
        materialShapeDrawable.setShadowColor();
        ShapeAppearanceModel.Builder builder =
                materialShapeDrawable.drawableState.shapeAppearanceModel.toBuilder();
        TypedArray obtainStyledAttributes =
                materialCardView
                        .getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.CardView, i, R.style.CardView);
        if (obtainStyledAttributes.hasValue(3)) {
            float dimension = obtainStyledAttributes.getDimension(3, 0.0f);
            builder.topLeftCornerSize = new AbsoluteCornerSize(dimension);
            builder.topRightCornerSize = new AbsoluteCornerSize(dimension);
            builder.bottomRightCornerSize = new AbsoluteCornerSize(dimension);
            builder.bottomLeftCornerSize = new AbsoluteCornerSize(dimension);
        }
        this.foregroundContentDrawable = new MaterialShapeDrawable();
        setShapeAppearanceModel(builder.build());
        this.iconFadeAnimInterpolator =
                MotionUtils.resolveThemeInterpolator(
                        materialCardView.getContext(),
                        R.attr.motionEasingLinearInterpolator,
                        AnimationUtils.LINEAR_INTERPOLATOR);
        this.iconFadeInAnimDuration =
                MotionUtils.resolveThemeDuration(
                        materialCardView.getContext(), R.attr.motionDurationShort2, 300);
        this.iconFadeOutAnimDuration =
                MotionUtils.resolveThemeDuration(
                        materialCardView.getContext(), R.attr.motionDurationShort1, 300);
        obtainStyledAttributes.recycle();
    }

    public static float calculateCornerPaddingForCornerTreatment(
            CornerTreatment cornerTreatment, float f) {
        if (cornerTreatment instanceof RoundedCornerTreatment) {
            return (float) ((1.0d - COS_45) * f);
        }
        if (cornerTreatment instanceof CutCornerTreatment) {
            return f / 2.0f;
        }
        return 0.0f;
    }

    public final float calculateActualCornerPadding() {
        CornerTreatment cornerTreatment = this.shapeAppearanceModel.topLeftCorner;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        return Math.max(
                Math.max(
                        calculateCornerPaddingForCornerTreatment(
                                cornerTreatment,
                                materialShapeDrawable.getTopLeftCornerResolvedSize()),
                        calculateCornerPaddingForCornerTreatment(
                                this.shapeAppearanceModel.topRightCorner,
                                materialShapeDrawable.drawableState.shapeAppearanceModel
                                        .topRightCornerSize.getCornerSize(
                                        materialShapeDrawable.getBoundsAsRectF$1()))),
                Math.max(
                        calculateCornerPaddingForCornerTreatment(
                                this.shapeAppearanceModel.bottomRightCorner,
                                materialShapeDrawable.drawableState.shapeAppearanceModel
                                        .bottomRightCornerSize.getCornerSize(
                                        materialShapeDrawable.getBoundsAsRectF$1())),
                        calculateCornerPaddingForCornerTreatment(
                                this.shapeAppearanceModel.bottomLeftCorner,
                                materialShapeDrawable.drawableState.shapeAppearanceModel
                                        .bottomLeftCornerSize.getCornerSize(
                                        materialShapeDrawable.getBoundsAsRectF$1()))));
    }

    public final Drawable getClickableForeground() {
        if (this.rippleDrawable == null) {
            this.foregroundShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.rippleDrawable =
                    new RippleDrawable(this.rippleColor, null, this.foregroundShapeDrawable);
        }
        if (this.clickableForegroundDrawable == null) {
            LayerDrawable layerDrawable =
                    new LayerDrawable(
                            new Drawable[] {
                                this.rippleDrawable,
                                this.foregroundContentDrawable,
                                this.checkedIcon
                            });
            this.clickableForegroundDrawable = layerDrawable;
            layerDrawable.setId(2, R.id.mtrl_card_checked_layer_id);
        }
        return this.clickableForegroundDrawable;
    }

    public final AnonymousClass1 insetDrawable(Drawable drawable) {
        int i;
        int i2;
        if (this.materialCardView.mCompatPadding) {
            int ceil =
                    (int)
                            Math.ceil(
                                    (((RoundRectDrawable) r0.mCardViewDelegate.mCardBackground)
                                                            .mPadding
                                                    * 1.5f)
                                            + (shouldAddCornerPaddingOutsideCardBackground()
                                                    ? calculateActualCornerPadding()
                                                    : 0.0f));
            i =
                    (int)
                            Math.ceil(
                                    ((RoundRectDrawable) r0.mCardViewDelegate.mCardBackground)
                                                    .mPadding
                                            + (shouldAddCornerPaddingOutsideCardBackground()
                                                    ? calculateActualCornerPadding()
                                                    : 0.0f));
            i2 = ceil;
        } else {
            i = 0;
            i2 = 0;
        }
        return new AnonymousClass1(drawable, i, i2, i, i2);
    }

    public final void setChecked(boolean z, boolean z2) {
        Drawable drawable = this.checkedIcon;
        if (drawable != null) {
            if (!z2) {
                drawable.setAlpha(z ? 255 : 0);
                this.checkedAnimationProgress = z ? 1.0f : 0.0f;
                return;
            }
            float f = z ? 1.0f : 0.0f;
            float f2 = z ? 1.0f - this.checkedAnimationProgress : this.checkedAnimationProgress;
            ValueAnimator valueAnimator = this.iconAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.iconAnimator = null;
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.checkedAnimationProgress, f);
            this.iconAnimator = ofFloat;
            ofFloat.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.google.android.material.card.MaterialCardViewHelper$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            MaterialCardViewHelper materialCardViewHelper =
                                    MaterialCardViewHelper.this;
                            materialCardViewHelper.getClass();
                            float floatValue =
                                    ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            materialCardViewHelper.checkedIcon.setAlpha(
                                    (int) (255.0f * floatValue));
                            materialCardViewHelper.checkedAnimationProgress = floatValue;
                        }
                    });
            this.iconAnimator.setInterpolator(this.iconFadeAnimInterpolator);
            this.iconAnimator.setDuration(
                    (long) ((z ? this.iconFadeInAnimDuration : this.iconFadeOutAnimDuration) * f2));
            this.iconAnimator.start();
        }
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.shapeAppearanceModel = shapeAppearanceModel;
        MaterialShapeDrawable materialShapeDrawable = this.bgDrawable;
        materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel);
        materialShapeDrawable.shadowBitmapDrawingEnable = !materialShapeDrawable.isRoundRect();
        MaterialShapeDrawable materialShapeDrawable2 = this.foregroundContentDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel);
        }
        MaterialShapeDrawable materialShapeDrawable3 = this.foregroundShapeDrawable;
        if (materialShapeDrawable3 != null) {
            materialShapeDrawable3.setShapeAppearanceModel(shapeAppearanceModel);
        }
    }

    public final boolean shouldAddCornerPaddingOutsideCardBackground() {
        MaterialCardView materialCardView = this.materialCardView;
        return materialCardView.mPreventCornerOverlap
                && this.bgDrawable.isRoundRect()
                && materialCardView.mCompatPadding;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v5, types: [android.view.View] */
    public final boolean shouldUseClickableForeground() {
        MaterialCardView materialCardView = this.materialCardView;
        boolean isClickable = materialCardView.isClickable();
        MaterialCardView materialCardView2 = materialCardView;
        if (isClickable) {
            return true;
        }
        while (materialCardView2.isDuplicateParentStateEnabled()
                && (materialCardView2.getParent() instanceof View)) {
            materialCardView2 = (View) materialCardView2.getParent();
        }
        return materialCardView2.isClickable();
    }

    public final void updateClickable() {
        Drawable drawable = this.fgDrawable;
        Drawable clickableForeground =
                shouldUseClickableForeground()
                        ? getClickableForeground()
                        : this.foregroundContentDrawable;
        this.fgDrawable = clickableForeground;
        if (drawable != clickableForeground) {
            MaterialCardView materialCardView = this.materialCardView;
            if (materialCardView.getForeground() instanceof InsetDrawable) {
                ((InsetDrawable) materialCardView.getForeground()).setDrawable(clickableForeground);
            } else {
                materialCardView.setForeground(insetDrawable(clickableForeground));
            }
        }
    }
}
