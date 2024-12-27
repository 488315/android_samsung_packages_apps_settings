package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecSubHeaderPreference extends Preference {
    public final String headerDesc;

    public SecSubHeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.preferenceCategoryStyle, 0);
        setLayoutResource(com.android.settings.R.layout.sec_subheader_divider_layout);
        this.headerDesc =
                SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                        .m(
                                context,
                                com.android.settings.R.string.preference_category_description,
                                new StringBuilder(", "));
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setAccessibilityHeading(true);
        View view = preferenceViewHolder.itemView;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            String charSequence = textView.getText().toString();
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                            charSequence);
            m.append(this.headerDesc);
            textView.setContentDescription(m.toString());
        }
    }
}
