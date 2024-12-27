package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.settings.R;

import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FillContentLayout extends FrameLayout {
    public final int maxHeight;
    public final int maxWidth;

    public FillContentLayout(Context context) {
        this(context, null);
    }

    public static int getMaxSizeMeasureSpec(int i, int i2, int i3) {
        int max = Math.max(0, i - i2);
        if (i3 >= 0) {
            return View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        }
        if (i3 == -1) {
            return View.MeasureSpec.makeMeasureSpec(max, 1073741824);
        }
        if (i3 == -2) {
            return View.MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE);
        }
        return 0;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(
                FrameLayout.getDefaultSize(getSuggestedMinimumWidth(), i),
                FrameLayout.getDefaultSize(getSuggestedMinimumHeight(), i2));
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            childAt.measure(
                    getMaxSizeMeasureSpec(
                            Math.min(this.maxWidth, measuredWidth),
                            getPaddingRight()
                                    + getPaddingLeft()
                                    + marginLayoutParams.leftMargin
                                    + marginLayoutParams.rightMargin,
                            marginLayoutParams.width),
                    getMaxSizeMeasureSpec(
                            Math.min(this.maxHeight, measuredHeight),
                            getPaddingBottom()
                                    + getPaddingTop()
                                    + marginLayoutParams.topMargin
                                    + marginLayoutParams.bottomMargin,
                            marginLayoutParams.height));
        }
    }

    public FillContentLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.sudFillContentLayoutStyle);
    }

    public FillContentLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (isInEditMode()) {
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.SudFillContentLayout, i, 0);
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_ILLUSTRATION_MAX_HEIGHT;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.maxHeight =
                    (int)
                            PartnerConfigHelper.get(context)
                                    .getDimension(context, partnerConfig, 0.0f);
        } else {
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_ILLUSTRATION_MAX_WIDTH;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            this.maxWidth =
                    (int)
                            PartnerConfigHelper.get(context)
                                    .getDimension(context, partnerConfig2, 0.0f);
        } else {
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        }
        obtainStyledAttributes.recycle();
    }
}
