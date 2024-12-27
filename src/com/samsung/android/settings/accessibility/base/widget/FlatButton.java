package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.TypedArrayUtils;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FlatButton extends AppCompatButton implements LimitedScale {
    public FlatButton(Context context) {
        this(context, null);
    }

    public FlatButton(Context context, AttributeSet attributeSet) {
        super(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(context, R.attr.flatButtonStyle, R.attr.buttonStyle));
        setTextSize(1, LimitedScale.calculateSize(getTextSize(), 5, context));
    }
}
