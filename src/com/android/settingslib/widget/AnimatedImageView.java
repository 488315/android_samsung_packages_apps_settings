package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AnimatedImageView extends ImageView {
    public boolean mAnimating;
    public AnimatedRotateDrawable mDrawable;

    public AnimatedImageView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateAnimating();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        updateAnimating();
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        updateAnimating();
    }

    public void setAnimating(boolean z) {
        this.mAnimating = z;
        updateAnimating();
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateDrawable();
    }

    @Override // android.widget.ImageView
    public void setImageResource(int i) {
        super.setImageResource(i);
        updateDrawable();
    }

    public final void updateAnimating() {
        if (this.mDrawable != null) {
            if (getVisibility() == 0 && this.mAnimating) {
                this.mDrawable.start();
            } else {
                this.mDrawable.stop();
            }
        }
    }

    public final void updateDrawable() {
        AnimatedRotateDrawable animatedRotateDrawable;
        if (isShown() && (animatedRotateDrawable = this.mDrawable) != null) {
            animatedRotateDrawable.stop();
        }
        AnimatedRotateDrawable drawable = getDrawable();
        if (!(drawable instanceof AnimatedRotateDrawable)) {
            this.mDrawable = null;
            return;
        }
        AnimatedRotateDrawable animatedRotateDrawable2 = drawable;
        this.mDrawable = animatedRotateDrawable2;
        animatedRotateDrawable2.setFramesCount(56);
        this.mDrawable.setFramesDuration(32);
        if (isShown() && this.mAnimating) {
            this.mDrawable.start();
        }
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
