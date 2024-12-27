package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContainedButton extends AppCompatButton implements LimitedScale {
    public ContainedButton(Context context) {
        this(context, null);
    }

    public ContainedButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public ContainedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTextSize(1, LimitedScale.calculateSize(getTextSize(), 5, context));
    }
}
