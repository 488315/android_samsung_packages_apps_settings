package com.google.android.material.badge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.settings.R;

import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.lang.ref.WeakReference;
import java.text.NumberFormat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BadgeDrawable extends Drawable
        implements TextDrawableHelper.TextDrawableDelegate {
    public WeakReference anchorViewRef;
    public final Rect badgeBounds;
    public float badgeCenterX;
    public float badgeCenterY;
    public final WeakReference contextRef;
    public float cornerRadius;
    public WeakReference customBadgeParentRef;
    public float halfBadgeHeight;
    public float halfBadgeWidth;
    public final int maxBadgeNumber;
    public final MaterialShapeDrawable shapeDrawable;
    public final BadgeState state;
    public final TextDrawableHelper textDrawableHelper;

    public BadgeDrawable(Context context, BadgeState.State state) {
        TextAppearance textAppearance;
        WeakReference weakReference = new WeakReference(context);
        this.contextRef = weakReference;
        ThemeEnforcement.checkTheme(
                context, ThemeEnforcement.MATERIAL_CHECK_ATTRS, "Theme.MaterialComponents");
        this.badgeBounds = new Rect();
        TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper;
        textDrawableHelper.textPaint.setTextAlign(Paint.Align.CENTER);
        BadgeState badgeState = new BadgeState(context, state);
        this.state = badgeState;
        boolean hasBadgeContent = hasBadgeContent();
        BadgeState.State state2 = badgeState.currentState;
        MaterialShapeDrawable materialShapeDrawable =
                new MaterialShapeDrawable(
                        ShapeAppearanceModel.builder(
                                        context,
                                        hasBadgeContent
                                                ? state2.badgeWithTextShapeAppearanceResId
                                                        .intValue()
                                                : state2.badgeShapeAppearanceResId.intValue(),
                                        hasBadgeContent()
                                                ? state2.badgeWithTextShapeAppearanceOverlayResId
                                                        .intValue()
                                                : state2.badgeShapeAppearanceOverlayResId
                                                        .intValue(),
                                        new AbsoluteCornerSize(0))
                                .build());
        this.shapeDrawable = materialShapeDrawable;
        onBadgeShapeAppearanceUpdated();
        Context context2 = (Context) weakReference.get();
        if (context2 != null
                && textDrawableHelper.textAppearance
                        != (textAppearance =
                                new TextAppearance(
                                        context2, state2.badgeTextAppearanceResId.intValue()))) {
            textDrawableHelper.setTextAppearance(textAppearance, context2);
            textDrawableHelper.textPaint.setColor(state2.badgeTextColor.intValue());
            invalidateSelf();
            updateCenterAndBounds();
            invalidateSelf();
        }
        int i = state2.maxCharacterCount;
        if (i != -2) {
            this.maxBadgeNumber = ((int) Math.pow(10.0d, i - 1.0d)) - 1;
        } else {
            this.maxBadgeNumber = state2.maxNumber;
        }
        textDrawableHelper.textSizeDirty = true;
        updateCenterAndBounds();
        invalidateSelf();
        textDrawableHelper.textSizeDirty = true;
        onBadgeShapeAppearanceUpdated();
        updateCenterAndBounds();
        invalidateSelf();
        textDrawableHelper.textPaint.setAlpha(getAlpha());
        invalidateSelf();
        ColorStateList valueOf = ColorStateList.valueOf(state2.backgroundColor.intValue());
        if (materialShapeDrawable.drawableState.fillColor != valueOf) {
            materialShapeDrawable.setFillColor(valueOf);
            invalidateSelf();
        }
        textDrawableHelper.textPaint.setColor(state2.badgeTextColor.intValue());
        invalidateSelf();
        WeakReference weakReference2 = this.anchorViewRef;
        if (weakReference2 != null && weakReference2.get() != null) {
            View view = (View) this.anchorViewRef.get();
            WeakReference weakReference3 = this.customBadgeParentRef;
            updateBadgeCoordinates(
                    view, weakReference3 != null ? (FrameLayout) weakReference3.get() : null);
        }
        updateCenterAndBounds();
        setVisible(state2.isVisible.booleanValue(), false);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        String badgeContent;
        if (getBounds().isEmpty() || getAlpha() == 0 || !isVisible()) {
            return;
        }
        this.shapeDrawable.draw(canvas);
        if (!hasBadgeContent() || (badgeContent = getBadgeContent()) == null) {
            return;
        }
        Rect rect = new Rect();
        this.textDrawableHelper.textPaint.getTextBounds(
                badgeContent, 0, badgeContent.length(), rect);
        float exactCenterY = this.badgeCenterY - rect.exactCenterY();
        canvas.drawText(
                badgeContent,
                this.badgeCenterX,
                rect.bottom <= 0 ? (int) exactCenterY : Math.round(exactCenterY),
                this.textDrawableHelper.textPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getAlpha() {
        return this.state.currentState.alpha;
    }

    public final String getBadgeContent() {
        BadgeState.State state = this.state.currentState;
        String str = state.text;
        if (!(str != null)) {
            if (!hasNumber()) {
                return null;
            }
            if (this.maxBadgeNumber == -2 || getNumber() <= this.maxBadgeNumber) {
                return NumberFormat.getInstance(this.state.currentState.numberLocale)
                        .format(getNumber());
            }
            Context context = (Context) this.contextRef.get();
            return context == null
                    ? ApnSettings.MVNO_NONE
                    : String.format(
                            this.state.currentState.numberLocale,
                            context.getString(R.string.mtrl_exceed_max_badge_number_suffix),
                            Integer.valueOf(this.maxBadgeNumber),
                            "+");
        }
        int i = state.maxCharacterCount;
        if (i == -2 || str == null || str.length() <= i) {
            return str;
        }
        Context context2 = (Context) this.contextRef.get();
        if (context2 == null) {
            return ApnSettings.MVNO_NONE;
        }
        return String.format(
                context2.getString(R.string.m3_exceed_max_badge_text_suffix),
                str.substring(0, i - 1),
                "…");
    }

    public final FrameLayout getCustomBadgeParent() {
        WeakReference weakReference = this.customBadgeParentRef;
        if (weakReference != null) {
            return (FrameLayout) weakReference.get();
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return this.badgeBounds.height();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        return this.badgeBounds.width();
    }

    public final int getNumber() {
        int i = this.state.currentState.number;
        if (i != -1) {
            return i;
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public final int getOpacity() {
        return -3;
    }

    public final boolean hasBadgeContent() {
        return this.state.currentState.text != null || hasNumber();
    }

    public final boolean hasNumber() {
        BadgeState.State state = this.state.currentState;
        return state.text == null && state.number != -1;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isStateful() {
        return false;
    }

    public final void onBadgeShapeAppearanceUpdated() {
        Context context = (Context) this.contextRef.get();
        if (context == null) {
            return;
        }
        this.shapeDrawable.setShapeAppearanceModel(
                ShapeAppearanceModel.builder(
                                context,
                                hasBadgeContent()
                                        ? this.state.currentState.badgeWithTextShapeAppearanceResId
                                                .intValue()
                                        : this.state.currentState.badgeShapeAppearanceResId
                                                .intValue(),
                                hasBadgeContent()
                                        ? this.state.currentState
                                                .badgeWithTextShapeAppearanceOverlayResId.intValue()
                                        : this.state.currentState.badgeShapeAppearanceOverlayResId
                                                .intValue(),
                                new AbsoluteCornerSize(0))
                        .build());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable,
              // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }

    @Override // com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final void onTextSizeChange() {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public final void setAlpha(int i) {
        BadgeState badgeState = this.state;
        badgeState.overridingState.alpha = i;
        badgeState.currentState.alpha = i;
        this.textDrawableHelper.textPaint.setAlpha(getAlpha());
        invalidateSelf();
    }

    public final void updateBadgeCoordinates(View view, FrameLayout frameLayout) {
        this.anchorViewRef = new WeakReference(view);
        this.customBadgeParentRef = new WeakReference(frameLayout);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.setClipChildren(false);
        viewGroup.setClipToPadding(false);
        updateCenterAndBounds();
        invalidateSelf();
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0278  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCenterAndBounds() {
        /*
            Method dump skipped, instructions count: 808
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.badge.BadgeDrawable.updateCenterAndBounds():void");
    }

    @Override // android.graphics.drawable.Drawable
    public final void setColorFilter(ColorFilter colorFilter) {}
}
