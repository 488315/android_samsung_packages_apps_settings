package com.android.settings.accessibility;

import android.R;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class HtmlTextPreference extends StaticTextPreference {
    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.summary);
        if (textView == null || TextUtils.isEmpty(getSummary())) {
            return;
        }
        textView.setText(Html.fromHtml(getSummary().toString(), 0, null, null));
    }
}
