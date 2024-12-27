package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewStub;
import android.widget.ProgressBar;

import com.android.settings.R;

import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ProgressBarMixin implements Mixin {
    public ColorStateList color;
    public final TemplateLayout templateLayout;
    public final boolean useBottomProgressBar;

    public ProgressBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
        boolean z = false;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    templateLayout
                            .getContext()
                            .obtainStyledAttributes(
                                    attributeSet, R$styleable.SudProgressBarMixin, i, 0);
            boolean z2 =
                    obtainStyledAttributes.hasValue(0)
                            ? obtainStyledAttributes.getBoolean(0, false)
                            : false;
            obtainStyledAttributes.recycle();
            setShown(false);
            z = z2;
        }
        this.useBottomProgressBar = z;
    }

    public final ProgressBar peekProgressBar() {
        return (ProgressBar)
                this.templateLayout.findManagedViewById(
                        this.useBottomProgressBar
                                ? R.id.sud_glif_progress_bar
                                : R.id.sud_layout_progress);
    }

    public final void setShown(boolean z) {
        boolean z2 = this.useBottomProgressBar;
        if (!z) {
            ProgressBar peekProgressBar = peekProgressBar();
            if (peekProgressBar != null) {
                peekProgressBar.setVisibility(z2 ? 4 : 8);
                return;
            }
            return;
        }
        if (peekProgressBar() == null && !z2) {
            ViewStub viewStub =
                    (ViewStub)
                            this.templateLayout.findManagedViewById(R.id.sud_layout_progress_stub);
            if (viewStub != null) {
                viewStub.inflate();
            }
            ColorStateList colorStateList = this.color;
            this.color = colorStateList;
            ProgressBar peekProgressBar2 = peekProgressBar();
            if (peekProgressBar2 != null) {
                peekProgressBar2.setIndeterminateTintList(colorStateList);
                peekProgressBar2.setProgressBackgroundTintList(colorStateList);
            }
        }
        ProgressBar peekProgressBar3 = peekProgressBar();
        if (peekProgressBar3 != null) {
            peekProgressBar3.setVisibility(0);
        }
    }
}
