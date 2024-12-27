package com.android.settings.biometrics.face;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FaceSquareTextureView extends TextureView {
    public FaceSquareTextureView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (size < size2) {
            setMeasuredDimension(size, size);
        } else {
            setMeasuredDimension(size2, size2);
        }
    }

    public FaceSquareTextureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FaceSquareTextureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
