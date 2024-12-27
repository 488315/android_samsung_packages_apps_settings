package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorSelectorWithWidgetPreference extends SelectorWithWidgetPreference {
    public ColorSelectorWithWidgetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        int resourceId =
                obtainStyledAttributes.getResourceId(
                        32,
                        obtainStyledAttributes.getResourceId(
                                3, R.layout.preference_radio_with_colorchip));
        obtainStyledAttributes.recycle();
        setLayoutResource(resourceId);
    }

    public ColorSelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        int resourceId =
                obtainStyledAttributes.getResourceId(
                        32,
                        obtainStyledAttributes.getResourceId(
                                3, R.layout.preference_radio_with_colorchip));
        obtainStyledAttributes.recycle();
        setLayoutResource(resourceId);
    }

    public ColorSelectorWithWidgetPreference(Context context) {
        this(context, null);
    }
}
