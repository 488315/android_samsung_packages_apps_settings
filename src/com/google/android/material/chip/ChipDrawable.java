package com.google.android.material.chip;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ChipDrawable extends MaterialShapeDrawable
        implements Drawable.Callback, TextDrawableHelper.TextDrawableDelegate {
    public static final int[] DEFAULT_STATE = {R.attr.state_enabled};
    public static final ShapeDrawable closeIconRippleMask = new ShapeDrawable(new OvalShape());
    public int alpha;
    public boolean checkable;
    public Drawable checkedIcon;
    public ColorStateList checkedIconTint;
    public boolean checkedIconVisible;
    public ColorStateList chipBackgroundColor;
    public float chipCornerRadius;
    public float chipEndPadding;
    public Drawable chipIcon;
    public float chipIconSize;
    public ColorStateList chipIconTint;
    public boolean chipIconVisible;
    public float chipMinHeight;
    public final Paint chipPaint;
    public float chipStartPadding;
    public ColorStateList chipStrokeColor;
    public float chipStrokeWidth;
    public ColorStateList chipSurfaceColor;
    public Drawable closeIcon;
    public float closeIconEndPadding;
    public Drawable closeIconRipple;
    public float closeIconSize;
    public float closeIconStartPadding;
    public int[] closeIconStateSet;
    public ColorStateList closeIconTint;
    public boolean closeIconVisible;
    public ColorFilter colorFilter;
    public ColorStateList compatRippleColor;
    public final Context context;
    public boolean currentChecked;
    public int currentChipBackgroundColor;
    public int currentChipStrokeColor;
    public int currentChipSurfaceColor;
    public int currentCompatRippleColor;
    public int currentCompositeSurfaceBackgroundColor;
    public int currentTextColor;
    public int currentTint;
    public WeakReference delegate;
    public final Paint.FontMetrics fontMetrics;
    public boolean hasChipIconTint;
    public float iconEndPadding;
    public float iconStartPadding;
    public boolean isShapeThemingEnabled;
    public int maxWidth;
    public final PointF pointF;
    public final RectF rectF;
    public ColorStateList rippleColor;
    public final Path shapePath;
    public boolean shouldDrawText;
    public CharSequence text;
    public final TextDrawableHelper textDrawableHelper;
    public float textEndPadding;
    public float textStartPadding;
    public ColorStateList tint;
    public PorterDuffColorFilter tintFilter;
    public PorterDuff.Mode tintMode;
    public TextUtils.TruncateAt truncateAt;
    public boolean useCompatRipple;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Delegate {}

    public ChipDrawable(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 2132084885);
        this.chipCornerRadius = -1.0f;
        this.chipPaint = new Paint(1);
        this.fontMetrics = new Paint.FontMetrics();
        this.rectF = new RectF();
        this.pointF = new PointF();
        this.shapePath = new Path();
        this.alpha = 255;
        this.tintMode = PorterDuff.Mode.SRC_IN;
        this.delegate = new WeakReference(null);
        initializeElevationOverlay(context);
        this.context = context;
        TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper;
        this.text = ApnSettings.MVNO_NONE;
        textDrawableHelper.textPaint.density = context.getResources().getDisplayMetrics().density;
        int[] iArr = DEFAULT_STATE;
        setState(iArr);
        if (!Arrays.equals(this.closeIconStateSet, iArr)) {
            this.closeIconStateSet = iArr;
            if (showsCloseIcon()) {
                onStateChange(getState(), iArr);
            }
        }
        this.shouldDrawText = true;
        closeIconRippleMask.setTint(-1);
    }

    public static void unapplyChildDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
        }
    }

    public final void applyChildDrawable(Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setCallback(this);
        drawable.setLayoutDirection(getLayoutDirection());
        drawable.setLevel(getLevel());
        drawable.setVisible(isVisible(), false);
        if (drawable == this.closeIcon) {
            if (drawable.isStateful()) {
                drawable.setState(this.closeIconStateSet);
            }
            drawable.setTintList(this.closeIconTint);
            return;
        }
        Drawable drawable2 = this.chipIcon;
        if (drawable == drawable2 && this.hasChipIconTint) {
            drawable2.setTintList(this.chipIconTint);
        }
        if (drawable.isStateful()) {
            drawable.setState(getState());
        }
    }

    public final void calculateChipIconBounds(Rect rect, RectF rectF) {
        rectF.setEmpty();
        if (showsChipIcon() || showsCheckedIcon()) {
            float f = this.chipStartPadding + this.iconStartPadding;
            Drawable drawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
            float f2 = this.chipIconSize;
            if (f2 <= 0.0f && drawable != null) {
                f2 = drawable.getIntrinsicWidth();
            }
            if (getLayoutDirection() == 0) {
                float f3 = rect.left + f;
                rectF.left = f3;
                rectF.right = f3 + f2;
            } else {
                float f4 = rect.right - f;
                rectF.right = f4;
                rectF.left = f4 - f2;
            }
            Drawable drawable2 = this.currentChecked ? this.checkedIcon : this.chipIcon;
            float f5 = this.chipIconSize;
            if (f5 <= 0.0f && drawable2 != null) {
                f5 = (float) Math.ceil(ViewUtils.dpToPx(this.context, 24));
                if (drawable2.getIntrinsicHeight() <= f5) {
                    f5 = drawable2.getIntrinsicHeight();
                }
            }
            float exactCenterY = rect.exactCenterY() - (f5 / 2.0f);
            rectF.top = exactCenterY;
            rectF.bottom = exactCenterY + f5;
        }
    }

    public final float calculateChipIconWidth() {
        if (!showsChipIcon() && !showsCheckedIcon()) {
            return 0.0f;
        }
        float f = this.iconStartPadding;
        Drawable drawable = this.currentChecked ? this.checkedIcon : this.chipIcon;
        float f2 = this.chipIconSize;
        if (f2 <= 0.0f && drawable != null) {
            f2 = drawable.getIntrinsicWidth();
        }
        return f2 + f + this.iconEndPadding;
    }

    public final float calculateCloseIconWidth() {
        if (showsCloseIcon()) {
            return this.closeIconStartPadding + this.closeIconSize + this.closeIconEndPadding;
        }
        return 0.0f;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        int i5;
        Rect bounds = getBounds();
        if (bounds.isEmpty() || (i = this.alpha) == 0) {
            return;
        }
        int saveLayerAlpha =
                i < 255
                        ? canvas.saveLayerAlpha(
                                bounds.left, bounds.top, bounds.right, bounds.bottom, i)
                        : 0;
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipSurfaceColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            this.rectF.set(bounds);
            canvas.drawRoundRect(
                    this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (!this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipBackgroundColor);
            this.chipPaint.setStyle(Paint.Style.FILL);
            Paint paint = this.chipPaint;
            ColorFilter colorFilter = this.colorFilter;
            if (colorFilter == null) {
                colorFilter = this.tintFilter;
            }
            paint.setColorFilter(colorFilter);
            this.rectF.set(bounds);
            canvas.drawRoundRect(
                    this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (this.isShapeThemingEnabled) {
            super.draw(canvas);
        }
        if (this.chipStrokeWidth > 0.0f && !this.isShapeThemingEnabled) {
            this.chipPaint.setColor(this.currentChipStrokeColor);
            this.chipPaint.setStyle(Paint.Style.STROKE);
            if (!this.isShapeThemingEnabled) {
                Paint paint2 = this.chipPaint;
                ColorFilter colorFilter2 = this.colorFilter;
                if (colorFilter2 == null) {
                    colorFilter2 = this.tintFilter;
                }
                paint2.setColorFilter(colorFilter2);
            }
            RectF rectF = this.rectF;
            float f2 = bounds.left;
            float f3 = this.chipStrokeWidth / 2.0f;
            rectF.set(f2 + f3, bounds.top + f3, bounds.right - f3, bounds.bottom - f3);
            float f4 = this.chipCornerRadius - (this.chipStrokeWidth / 2.0f);
            canvas.drawRoundRect(this.rectF, f4, f4, this.chipPaint);
        }
        this.chipPaint.setColor(this.currentCompatRippleColor);
        this.chipPaint.setStyle(Paint.Style.FILL);
        this.rectF.set(bounds);
        if (this.isShapeThemingEnabled) {
            RectF rectF2 = new RectF(bounds);
            Path path = this.shapePath;
            ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
            MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                    this.drawableState;
            shapeAppearancePathProvider.calculatePath(
                    materialShapeDrawableState.shapeAppearanceModel,
                    materialShapeDrawableState.interpolation,
                    rectF2,
                    this.pathShadowListener,
                    path);
            drawShape(
                    canvas,
                    this.chipPaint,
                    this.shapePath,
                    this.drawableState.shapeAppearanceModel,
                    getBoundsAsRectF$1());
        } else {
            canvas.drawRoundRect(
                    this.rectF, getChipCornerRadius(), getChipCornerRadius(), this.chipPaint);
        }
        if (showsChipIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF3 = this.rectF;
            float f5 = rectF3.left;
            float f6 = rectF3.top;
            canvas.translate(f5, f6);
            this.chipIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.chipIcon.draw(canvas);
            canvas.translate(-f5, -f6);
        }
        if (showsCheckedIcon()) {
            calculateChipIconBounds(bounds, this.rectF);
            RectF rectF4 = this.rectF;
            float f7 = rectF4.left;
            float f8 = rectF4.top;
            canvas.translate(f7, f8);
            this.checkedIcon.setBounds(0, 0, (int) this.rectF.width(), (int) this.rectF.height());
            this.checkedIcon.draw(canvas);
            canvas.translate(-f7, -f8);
        }
        if (!this.shouldDrawText || this.text == null) {
            i2 = saveLayerAlpha;
            i3 = 0;
            i4 = 255;
        } else {
            PointF pointF = this.pointF;
            pointF.set(0.0f, 0.0f);
            Paint.Align align = Paint.Align.LEFT;
            if (this.text != null) {
                float calculateChipIconWidth =
                        this.chipStartPadding + calculateChipIconWidth() + this.textStartPadding;
                if (getLayoutDirection() == 0) {
                    pointF.x = bounds.left + calculateChipIconWidth;
                } else {
                    pointF.x = bounds.right - calculateChipIconWidth;
                    align = Paint.Align.RIGHT;
                }
                float centerY = bounds.centerY();
                this.textDrawableHelper.textPaint.getFontMetrics(this.fontMetrics);
                Paint.FontMetrics fontMetrics = this.fontMetrics;
                pointF.y = centerY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f);
            }
            RectF rectF5 = this.rectF;
            rectF5.setEmpty();
            if (this.text != null) {
                float calculateChipIconWidth2 = calculateChipIconWidth();
                float calculateCloseIconWidth = calculateCloseIconWidth();
                float f9 = this.chipStartPadding + calculateChipIconWidth2 + this.textStartPadding;
                float f10 = this.chipEndPadding + calculateCloseIconWidth + this.textEndPadding;
                if (getLayoutDirection() == 0) {
                    rectF5.left = bounds.left + f9;
                    rectF5.right = bounds.right - f10;
                } else {
                    rectF5.left = bounds.left + f10;
                    rectF5.right = bounds.right - f9;
                }
                rectF5.top = bounds.top;
                rectF5.bottom = bounds.bottom;
            }
            TextDrawableHelper textDrawableHelper = this.textDrawableHelper;
            if (textDrawableHelper.textAppearance != null) {
                textDrawableHelper.textPaint.drawableState = getState();
                TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
                textDrawableHelper2.textAppearance.updateDrawState(
                        this.context,
                        textDrawableHelper2.textPaint,
                        textDrawableHelper2.fontCallback);
            }
            this.textDrawableHelper.textPaint.setTextAlign(align);
            TextDrawableHelper textDrawableHelper3 = this.textDrawableHelper;
            String charSequence = this.text.toString();
            if (textDrawableHelper3.textSizeDirty) {
                textDrawableHelper3.refreshTextDimens(charSequence);
                f = textDrawableHelper3.textWidth;
            } else {
                f = textDrawableHelper3.textWidth;
            }
            boolean z = Math.round(f) > Math.round(this.rectF.width());
            if (z) {
                i5 = canvas.save();
                canvas.clipRect(this.rectF);
            } else {
                i5 = 0;
            }
            CharSequence charSequence2 = this.text;
            if (z && this.truncateAt != null) {
                charSequence2 =
                        TextUtils.ellipsize(
                                charSequence2,
                                this.textDrawableHelper.textPaint,
                                this.rectF.width(),
                                this.truncateAt);
            }
            CharSequence charSequence3 = charSequence2;
            int length = charSequence3.length();
            PointF pointF2 = this.pointF;
            i2 = saveLayerAlpha;
            i3 = 0;
            i4 = 255;
            canvas.drawText(
                    charSequence3,
                    0,
                    length,
                    pointF2.x,
                    pointF2.y,
                    this.textDrawableHelper.textPaint);
            if (z) {
                canvas.restoreToCount(i5);
            }
        }
        if (showsCloseIcon()) {
            RectF rectF6 = this.rectF;
            rectF6.setEmpty();
            if (showsCloseIcon()) {
                float f11 = this.chipEndPadding + this.closeIconEndPadding;
                if (getLayoutDirection() == 0) {
                    float f12 = bounds.right - f11;
                    rectF6.right = f12;
                    rectF6.left = f12 - this.closeIconSize;
                } else {
                    float f13 = bounds.left + f11;
                    rectF6.left = f13;
                    rectF6.right = f13 + this.closeIconSize;
                }
                float exactCenterY = bounds.exactCenterY();
                float f14 = this.closeIconSize;
                float f15 = exactCenterY - (f14 / 2.0f);
                rectF6.top = f15;
                rectF6.bottom = f15 + f14;
            }
            RectF rectF7 = this.rectF;
            float f16 = rectF7.left;
            float f17 = rectF7.top;
            canvas.translate(f16, f17);
            this.closeIcon.setBounds(i3, i3, (int) this.rectF.width(), (int) this.rectF.height());
            this.closeIconRipple.setBounds(this.closeIcon.getBounds());
            this.closeIconRipple.jumpToCurrentState();
            this.closeIconRipple.draw(canvas);
            canvas.translate(-f16, -f17);
        }
        if (this.alpha < i4) {
            canvas.restoreToCount(i2);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.alpha;
    }

    public final float getChipCornerRadius() {
        return this.isShapeThemingEnabled ? getTopLeftCornerResolvedSize() : this.chipCornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public final ColorFilter getColorFilter() {
        return this.colorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) this.chipMinHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        float f;
        float calculateChipIconWidth =
                calculateChipIconWidth() + this.chipStartPadding + this.textStartPadding;
        TextDrawableHelper textDrawableHelper = this.textDrawableHelper;
        String charSequence = this.text.toString();
        if (textDrawableHelper.textSizeDirty) {
            textDrawableHelper.refreshTextDimens(charSequence);
            f = textDrawableHelper.textWidth;
        } else {
            f = textDrawableHelper.textWidth;
        }
        return Math.min(
                Math.round(
                        calculateCloseIconWidth()
                                + f
                                + calculateChipIconWidth
                                + this.textEndPadding
                                + this.chipEndPadding),
                this.maxWidth);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void getOutline(Outline outline) {
        if (this.isShapeThemingEnabled) {
            super.getOutline(outline);
            return;
        }
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            outline.setRoundRect(
                    0, 0, getIntrinsicWidth(), (int) this.chipMinHeight, this.chipCornerRadius);
        } else {
            outline.setRoundRect(bounds, this.chipCornerRadius);
        }
        outline.setAlpha(this.alpha / 255.0f);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        TextAppearance textAppearance;
        ColorStateList colorStateList;
        return isStateful(this.chipSurfaceColor)
                || isStateful(this.chipBackgroundColor)
                || isStateful(this.chipStrokeColor)
                || (this.useCompatRipple && isStateful(this.compatRippleColor))
                || (!((textAppearance = this.textDrawableHelper.textAppearance) == null
                                || (colorStateList = textAppearance.textColor) == null
                                || !colorStateList.isStateful())
                        || ((this.checkedIconVisible && this.checkedIcon != null && this.checkable)
                                || isStateful(this.chipIcon)
                                || isStateful(this.checkedIcon)
                                || isStateful(this.tint)));
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLayoutDirectionChanged(int i) {
        boolean onLayoutDirectionChanged = super.onLayoutDirectionChanged(i);
        if (showsChipIcon()) {
            onLayoutDirectionChanged |= this.chipIcon.setLayoutDirection(i);
        }
        if (showsCheckedIcon()) {
            onLayoutDirectionChanged |= this.checkedIcon.setLayoutDirection(i);
        }
        if (showsCloseIcon()) {
            onLayoutDirectionChanged |= this.closeIcon.setLayoutDirection(i);
        }
        if (!onLayoutDirectionChanged) {
            return true;
        }
        invalidateSelf();
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean onLevelChange(int i) {
        boolean onLevelChange = super.onLevelChange(i);
        if (showsChipIcon()) {
            onLevelChange |= this.chipIcon.setLevel(i);
        }
        if (showsCheckedIcon()) {
            onLevelChange |= this.checkedIcon.setLevel(i);
        }
        if (showsCloseIcon()) {
            onLevelChange |= this.closeIcon.setLevel(i);
        }
        if (onLevelChange) {
            invalidateSelf();
        }
        return onLevelChange;
    }

    public final void onSizeChange() {
        Delegate delegate = (Delegate) this.delegate.get();
        if (delegate != null) {
            Chip chip = (Chip) delegate;
            chip.ensureAccessibleTouchTarget(chip.minTouchTargetSize);
            chip.requestLayout();
            chip.invalidateOutline();
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable,
              // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final boolean onStateChange(int[] iArr) {
        if (this.isShapeThemingEnabled) {
            super.onStateChange(iArr);
        }
        return onStateChange(iArr, this.closeIconStateSet);
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final void onTextSizeChange() {
        onSizeChange();
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        if (this.alpha != i) {
            this.alpha = i;
            invalidateSelf();
        }
    }

    public final void setCheckedIconVisible(boolean z) {
        if (this.checkedIconVisible != z) {
            boolean showsCheckedIcon = showsCheckedIcon();
            this.checkedIconVisible = z;
            boolean showsCheckedIcon2 = showsCheckedIcon();
            if (showsCheckedIcon != showsCheckedIcon2) {
                if (showsCheckedIcon2) {
                    applyChildDrawable(this.checkedIcon);
                } else {
                    unapplyChildDrawable(this.checkedIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setChipIconVisible(boolean z) {
        if (this.chipIconVisible != z) {
            boolean showsChipIcon = showsChipIcon();
            this.chipIconVisible = z;
            boolean showsChipIcon2 = showsChipIcon();
            if (showsChipIcon != showsChipIcon2) {
                if (showsChipIcon2) {
                    applyChildDrawable(this.chipIcon);
                } else {
                    unapplyChildDrawable(this.chipIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    public final void setCloseIconVisible(boolean z) {
        if (this.closeIconVisible != z) {
            boolean showsCloseIcon = showsCloseIcon();
            this.closeIconVisible = z;
            boolean showsCloseIcon2 = showsCloseIcon();
            if (showsCloseIcon != showsCloseIcon2) {
                if (showsCloseIcon2) {
                    applyChildDrawable(this.closeIcon);
                } else {
                    unapplyChildDrawable(this.closeIcon);
                }
                invalidateSelf();
                onSizeChange();
            }
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {
        if (this.colorFilter != colorFilter) {
            this.colorFilter = colorFilter;
            invalidateSelf();
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void setTintList(ColorStateList colorStateList) {
        if (this.tint != colorStateList) {
            this.tint = colorStateList;
            onStateChange(getState());
        }
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable,
              // android.graphics.drawable.Drawable
    public final void setTintMode(PorterDuff.Mode mode) {
        if (this.tintMode != mode) {
            this.tintMode = mode;
            ColorStateList colorStateList = this.tint;
            this.tintFilter =
                    (colorStateList == null || mode == null)
                            ? null
                            : new PorterDuffColorFilter(
                                    colorStateList.getColorForState(getState(), 0), mode);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (showsChipIcon()) {
            visible |= this.chipIcon.setVisible(z, z2);
        }
        if (showsCheckedIcon()) {
            visible |= this.checkedIcon.setVisible(z, z2);
        }
        if (showsCloseIcon()) {
            visible |= this.closeIcon.setVisible(z, z2);
        }
        if (visible) {
            invalidateSelf();
        }
        return visible;
    }

    public final boolean showsCheckedIcon() {
        return this.checkedIconVisible && this.checkedIcon != null && this.currentChecked;
    }

    public final boolean showsChipIcon() {
        return this.chipIconVisible && this.chipIcon != null;
    }

    public final boolean showsCloseIcon() {
        return this.closeIconVisible && this.closeIcon != null;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0146  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onStateChange(int[] r12, int[] r13) {
        /*
            Method dump skipped, instructions count: 388
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.chip.ChipDrawable.onStateChange(int[],"
                    + " int[]):boolean");
    }

    public static boolean isStateful(ColorStateList colorStateList) {
        return colorStateList != null && colorStateList.isStateful();
    }

    public static boolean isStateful(Drawable drawable) {
        return drawable != null && drawable.isStateful();
    }
}
