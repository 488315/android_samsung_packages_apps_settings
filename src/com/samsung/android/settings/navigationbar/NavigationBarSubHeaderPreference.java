package com.samsung.android.settings.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.widget.SecSubHeaderPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarSubHeaderPreference extends SecSubHeaderPreference {
    public final String headerDesc;

    public NavigationBarSubHeaderPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.headerDesc =
                SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                        .m(
                                context,
                                R.string.preference_category_description,
                                new StringBuilder(", "));
    }

    @Override // com.samsung.android.settings.widget.SecSubHeaderPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.itemView.findViewById(android.R.id.title);
        if (findViewById instanceof TextView) {
            TextView textView = (TextView) findViewById;
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
