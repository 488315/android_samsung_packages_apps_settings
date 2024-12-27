package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.R$styleable;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LimitedScaleTextView extends TextView implements LimitedScale {
    public LimitedScaleTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        float textSize = getTextSize();
        ArrayList arrayList = new ArrayList();
        float f = context.getResources().getConfiguration().fontScale;
        for (String str :
                context.getResources().getStringArray(R.array.sec_entryvalues_8_step_font_size)) {
            arrayList.add(Float.valueOf(str));
        }
        int indexOf = arrayList.indexOf(Float.valueOf(f)) + 1;
        TypedArray obtainStyledAttributes =
                context.getTheme()
                        .obtainStyledAttributes(attributeSet, R$styleable.LimitedScale, 0, 0);
        for (int i = 0; i < obtainStyledAttributes.getIndexCount(); i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == 0) {
                indexOf = obtainStyledAttributes.getInt(index, indexOf);
                if (indexOf < 1) {
                    indexOf = 1;
                } else if (indexOf > 8) {
                    indexOf = 8;
                }
            }
        }
        obtainStyledAttributes.recycle();
        setTextSize(1, LimitedScale.calculateSize(textSize, indexOf, context));
    }
}
