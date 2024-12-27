package com.samsung.android.settings.accessibility.vision.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ColorAdjustmentResetRadioPreference extends SelectorWithWidgetPreference {
    public View.OnClickListener mResetBtnClickListener;

    public ColorAdjustmentResetRadioPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        int resourceId =
                obtainStyledAttributes.getResourceId(
                        32,
                        obtainStyledAttributes.getResourceId(
                                3, R.layout.color_adjustment_radio_preference_layout));
        obtainStyledAttributes.recycle();
        setLayoutResource(resourceId);
        this.mResetBtnClickListener = null;
    }

    @Override // com.android.settingslib.widget.SelectorWithWidgetPreference,
              // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.itemView.findViewById(R.id.reset_button);
        findViewById.setVisibility(this.mResetBtnClickListener != null ? 0 : 8);
        findViewById.setOnClickListener(this.mResetBtnClickListener);
    }

    public ColorAdjustmentResetRadioPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference);
        int resourceId =
                obtainStyledAttributes.getResourceId(
                        32,
                        obtainStyledAttributes.getResourceId(
                                3, R.layout.color_adjustment_radio_preference_layout));
        obtainStyledAttributes.recycle();
        setLayoutResource(resourceId);
        this.mResetBtnClickListener = null;
    }
}
