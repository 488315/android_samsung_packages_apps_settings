package com.samsung.android.settings.accessibility.dexterity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchAndHoldView extends FrameLayout {
    public final int circleHeight;
    public final int circleWidth;
    public final ImageView mCircleImageView;
    public final Drawable mHoldStandBy;
    public final Drawable mTapConfirm;
    public float mXCoordinate;
    public float mYCoordinate;

    public TouchAndHoldView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int dimensionPixelSize =
                context.getResources().getDimensionPixelSize(R.dimen.touch_circle_cue_size);
        this.circleWidth = dimensionPixelSize;
        int dimensionPixelSize2 =
                context.getResources().getDimensionPixelSize(R.dimen.touch_circle_cue_size);
        this.circleHeight = dimensionPixelSize2;
        this.mTapConfirm = context.getDrawable(R.drawable.circle_fail);
        this.mHoldStandBy = context.getDrawable(R.drawable.circle_hold);
        ImageView imageView = new ImageView(context);
        this.mCircleImageView = imageView;
        imageView.setImageDrawable(context.getDrawable(R.drawable.circle_stand_by));
        addView(imageView, dimensionPixelSize, dimensionPixelSize2);
    }

    public final FrameLayout.LayoutParams getCircleImageViewLayoutParams() {
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) this.mCircleImageView.getLayoutParams();
        layoutParams.gravity = 0;
        float f = this.mXCoordinate;
        int i = this.circleWidth;
        layoutParams.leftMargin = (int) (f - (i / 2.0f));
        float f2 = this.mYCoordinate;
        int i2 = this.circleHeight;
        layoutParams.topMargin = (int) (f2 - (i2 / 2.0f));
        layoutParams.width = i;
        layoutParams.height = i2;
        return layoutParams;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new FrameLayout.LayoutParams(-2, -2, 17);
    }
}
