package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.settings.R;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BottomLabelLayout extends LinearLayout {
    public BottomLabelLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        boolean z = false;
        boolean z2 = true;
        boolean z3 = getOrientation() == 1;
        if (z3 || View.MeasureSpec.getMode(i) != 1073741824) {
            i3 = i;
        } else {
            i3 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
            z = true;
        }
        super.onMeasure(i3, i2);
        if (z3 || (getMeasuredWidthAndState() & EmergencyPhoneWidget.BG_COLOR) != 16777216) {
            z2 = z;
        } else {
            setStacked(true);
        }
        if (z2) {
            super.onMeasure(i, i2);
        }
    }

    public void setStacked(boolean z) {
        setOrientation(z ? 1 : 0);
        setGravity(z ? 8388611 : 80);
        View findViewById = findViewById(R.id.spacer);
        if (findViewById != null) {
            findViewById.setVisibility(z ? 8 : 0);
        }
    }
}
