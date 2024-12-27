package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class BodyContainedButton extends ContainedButton {
    public BodyContainedButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.bodyButtonStyle);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
            i =
                    View.MeasureSpec.makeMeasureSpec(
                            Math.round(
                                    View.MeasureSpec.getSize(i)
                                            * getResources()
                                                    .getFloat(
                                                            R.dimen
                                                                    .body_contained_button_max_ratio)),
                            View.MeasureSpec.getMode(i));
        }
        super.onMeasure(i, i2);
    }
}
