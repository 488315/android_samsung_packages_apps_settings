package com.android.settings.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MessageBubbleBackground extends LinearLayout {
    public final int mSnapWidthPixels;

    public MessageBubbleBackground(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSnapWidthPixels =
                context.getResources()
                        .getDimensionPixelSize(R.dimen.conversation_bubble_width_snap);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        super.onMeasure(
                View.MeasureSpec.makeMeasureSpec(
                        Math.min(
                                        View.MeasureSpec.getSize(i) - paddingRight,
                                        (int)
                                                (Math.ceil(
                                                                (getMeasuredWidth() - paddingRight)
                                                                        / this.mSnapWidthPixels)
                                                        * this.mSnapWidthPixels))
                                + paddingRight,
                        1073741824),
                i2);
    }
}
