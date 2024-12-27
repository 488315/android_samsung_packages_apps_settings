package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageButton;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilitySelectorWithWidgetPreference extends SelectorWithWidgetPreference {
    public int dividerStartOffset;
    public Drawable extraImageDrawable;
    public CharSequence extraWidgetContentDescription;
    public boolean isExtraWidgetEnabled;

    public AccessibilitySelectorWithWidgetPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isExtraWidgetEnabled = true;
        this.extraWidgetContentDescription = null;
        this.extraImageDrawable = null;
        this.dividerStartOffset = 0;
        init(context, attributeSet);
    }

    public final void init(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        int resourceId =
                obtainStyledAttributes.getResourceId(
                        32,
                        obtainStyledAttributes.getResourceId(
                                3, R.layout.accessibility_preference_selector_with_widget));
        obtainStyledAttributes.recycle();
        setLayoutResource(resourceId);
        Resources resources = context.getResources();
        this.dividerStartOffset =
                resources.getDimensionPixelSize(R.dimen.controller_item_area)
                        - resources.getDimensionPixelSize(R.dimen.list_divider_padding);
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference,
              // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton =
                (ImageButton) preferenceViewHolder.findViewById(R.id.selector_extra_widget);
        if (imageButton != null) {
            imageButton.setEnabled(isEnabled() && this.isExtraWidgetEnabled);
            imageButton.setContentDescription(this.extraWidgetContentDescription);
            Drawable drawable = this.extraImageDrawable;
            if (drawable != null) {
                imageButton.setImageDrawable(drawable);
            }
        }
        preferenceViewHolder.mDividerStartOffset = this.dividerStartOffset;
    }

    public AccessibilitySelectorWithWidgetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isExtraWidgetEnabled = true;
        this.extraWidgetContentDescription = null;
        this.extraImageDrawable = null;
        this.dividerStartOffset = 0;
        init(context, attributeSet);
    }

    public AccessibilitySelectorWithWidgetPreference(Context context) {
        this(context, null);
    }
}
