package com.samsung.android.settings.datausage;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DataUsageCategoryPreference extends SecPreferenceCategory {
    public DataUsageCategoryPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.preference.SecPreferenceCategory, androidx.preference.PreferenceCategory,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextSize(32.0f);
            textView.setTextColor(
                    getContext()
                            .getResources()
                            .getColor(R.color.sec_data_usage_header_title_text_color));
            textView.setTypeface(Typeface.create("sec", 0));
            Utils.setMaxFontScale(getContext(), textView);
        }
    }

    public DataUsageCategoryPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
    }

    public DataUsageCategoryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, android.R.attr.preferenceCategoryStyle);
    }

    public DataUsageCategoryPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.SecPreferenceCategory
    public final void setEmptyHeight() {}
}
