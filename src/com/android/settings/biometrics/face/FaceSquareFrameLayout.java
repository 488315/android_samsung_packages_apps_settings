package com.android.settings.biometrics.face;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSquareFrameLayout extends FrameLayout {
    public FaceSquareFrameLayout(Context context) {
        super(context);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        setMeasuredDimension(size, size);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
            getChildAt(i3).measure(makeMeasureSpec, makeMeasureSpec);
        }
    }

    public FaceSquareFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FaceSquareFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public FaceSquareFrameLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
