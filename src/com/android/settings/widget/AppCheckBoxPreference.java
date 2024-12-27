package com.android.settings.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppCheckBoxPreference extends CheckBoxPreference {
    public AppCheckBoxPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.preference_app);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.findViewById(R.id.summary_container);
        if (linearLayout != null) {
            linearLayout.setVisibility(TextUtils.isEmpty(getSummary()) ? 8 : 0);
        }
    }

    public AppCheckBoxPreference(Context context) {
        super(context, null);
        setLayoutResource(R.layout.preference_app);
    }
}
