package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.settings.R;

import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class DescriptionMixin implements Mixin {
    public final TemplateLayout templateLayout;

    public DescriptionMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        TextView textView;
        TextView textView2;
        this.templateLayout = templateLayout;
        TypedArray obtainStyledAttributes =
                templateLayout
                        .getContext()
                        .obtainStyledAttributes(
                                attributeSet, R$styleable.SudDescriptionMixin, i, 0);
        CharSequence text = obtainStyledAttributes.getText(0);
        if (text != null && (textView2 = getTextView()) != null) {
            textView2.setText(text);
            TextView textView3 = getTextView();
            if (textView3 != null) {
                textView3.setVisibility(0);
            }
        }
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(1);
        if (colorStateList != null && (textView = getTextView()) != null) {
            textView.setTextColor(colorStateList);
        }
        obtainStyledAttributes.recycle();
    }

    public final TextView getTextView() {
        return (TextView) this.templateLayout.findManagedViewById(R.id.sud_layout_subtitle);
    }
}
