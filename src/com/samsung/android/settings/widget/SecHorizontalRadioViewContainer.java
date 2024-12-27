package com.samsung.android.settings.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecHorizontalRadioViewContainer extends LinearLayout {
    public Boolean mIsDividerEnabled;

    public SecHorizontalRadioViewContainer(Context context) {
        super(context);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mIsDividerEnabled.booleanValue()) {
            Drawable drawable = getContext().getDrawable(R.drawable.sec_divider_verticial);
            int round =
                    Math.round(
                            getContext()
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .sec_widget_multi_btn_preference_divider_margin_top));
            int height =
                    (getHeight() - round)
                            - Math.round(
                                    getContext()
                                            .getResources()
                                            .getDimension(
                                                    R.dimen
                                                            .sec_widget_multi_btn_preference_divider_margin_bottom));
            int round2 =
                    Math.round(
                            getContext()
                                    .getResources()
                                    .getDimension(
                                            R.dimen.sec_widget_preference_vertical_divider_width));
            int childCount = getChildCount() - 1;
            int i = 0;
            while (i < childCount) {
                drawable.setBounds(0, 0, round2, height);
                canvas.save();
                i++;
                canvas.translate(Math.round((getWidth() / getChildCount()) * i), round);
                drawable.draw(canvas);
                canvas.restore();
            }
        }
    }

    public SecHorizontalRadioViewContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SecHorizontalRadioViewContainer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
