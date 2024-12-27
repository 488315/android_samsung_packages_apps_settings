package com.google.android.material.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MaterialTextView extends AppCompatTextView {
    public static int readFirstAvailableDimension(
            Context context, TypedArray typedArray, int... iArr) {
        int i = -1;
        for (int i2 = 0; i2 < iArr.length && i < 0; i2++) {
            int i3 = iArr[i2];
            TypedValue typedValue = new TypedValue();
            if (typedArray.getValue(i3, typedValue) && typedValue.type == 2) {
                TypedArray obtainStyledAttributes =
                        context.getTheme().obtainStyledAttributes(new int[] {typedValue.data});
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
                obtainStyledAttributes.recycle();
                i = dimensionPixelSize;
            } else {
                i = typedArray.getDimensionPixelSize(i3, -1);
            }
        }
        return i;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public final void setTextAppearance(Context context, int i) {
        super.setTextAppearance(context, i);
        if (MaterialAttributes.resolveBoolean(
                context, R.attr.textAppearanceLineHeightEnabled, true)) {
            TypedArray obtainStyledAttributes =
                    context.getTheme()
                            .obtainStyledAttributes(i, R$styleable.MaterialTextAppearance);
            int readFirstAvailableDimension =
                    readFirstAvailableDimension(getContext(), obtainStyledAttributes, 1, 2);
            obtainStyledAttributes.recycle();
            if (readFirstAvailableDimension >= 0) {
                TextViewCompat.setLineHeight(readFirstAvailableDimension, this);
            }
        }
    }
}
