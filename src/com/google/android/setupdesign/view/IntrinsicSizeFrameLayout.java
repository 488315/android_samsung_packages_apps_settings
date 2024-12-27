package com.google.android.setupdesign.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class IntrinsicSizeFrameLayout extends FrameLayout {
    public int intrinsicHeight;
    public int intrinsicWidth;
    public Object lastInsets;
    public final Rect windowVisibleDisplayRect;

    public IntrinsicSizeFrameLayout(Context context) {
        super(context);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        init(context, null, 0);
    }

    public final int getIntrinsicMeasureSpec(int i, int i2) {
        if (i2 <= 0) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        return mode == 0
                ? View.MeasureSpec.makeMeasureSpec(this.intrinsicHeight, 1073741824)
                : mode == Integer.MIN_VALUE
                        ? View.MeasureSpec.makeMeasureSpec(
                                Math.min(View.MeasureSpec.getSize(i), this.intrinsicHeight),
                                1073741824)
                        : i;
    }

    public final void init(Context context, AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.SudIntrinsicSizeFrameLayout, i, 0);
        this.intrinsicHeight = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.intrinsicWidth = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_CARD_VIEW_INTRINSIC_HEIGHT;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.intrinsicHeight =
                    (int)
                            PartnerConfigHelper.get(context)
                                    .getDimension(context, partnerConfig, 0.0f);
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_CARD_VIEW_INTRINSIC_WIDTH;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            this.intrinsicWidth =
                    (int)
                            PartnerConfigHelper.get(context)
                                    .getDimension(context, partnerConfig2, 0.0f);
        }
    }

    public boolean isWindowSizeSmallerThanDisplaySize() {
        this.windowVisibleDisplayRect.set(
                ((WindowManager) getContext().getSystemService(WindowManager.class))
                        .getCurrentWindowMetrics()
                        .getBounds());
        Display display = getDisplay();
        if (display != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            if (this.windowVisibleDisplayRect.width() > 0
                    && this.windowVisibleDisplayRect.width() < displayMetrics.widthPixels) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.lastInsets = windowInsets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.lastInsets == null) {
            requestApplyInsets();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int intrinsicMeasureSpec;
        if (isWindowSizeSmallerThanDisplaySize()) {
            getWindowVisibleDisplayFrame(this.windowVisibleDisplayRect);
            intrinsicMeasureSpec =
                    View.MeasureSpec.makeMeasureSpec(
                            this.windowVisibleDisplayRect.width(), 1073741824);
        } else {
            intrinsicMeasureSpec = getIntrinsicMeasureSpec(i, this.intrinsicWidth);
        }
        super.onMeasure(intrinsicMeasureSpec, getIntrinsicMeasureSpec(i2, this.intrinsicHeight));
    }

    @Override // android.view.View
    public final void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (this.intrinsicHeight == 0 && this.intrinsicWidth == 0) {
            layoutParams.width = -1;
            layoutParams.height = -1;
        }
        super.setLayoutParams(layoutParams);
    }

    public IntrinsicSizeFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        init(context, attributeSet, 0);
    }

    @TargetApi(11)
    public IntrinsicSizeFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        init(context, attributeSet, i);
    }
}
