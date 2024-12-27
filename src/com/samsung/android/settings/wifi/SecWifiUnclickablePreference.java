package com.samsung.android.settings.wifi;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecWifiUnclickablePreference extends SecPreference {
    public int mTitleColor;

    public SecWifiUnclickablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.textView1);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.title);
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        textView.setTextColor(this.mTitleColor);
        textView2.setTextColor(this.mTitleColor);
        textView3.setTextColor(textView3.getTextColors().getDefaultColor());
        if (TextUtils.isEmpty(getSummary())) {
            textView.setText(getTitle());
            return;
        }
        textView2.setText(getTitle());
        textView3.setText(getSummary());
        textView.setVisibility(8);
        textView2.setVisibility(0);
        textView3.setVisibility(0);
    }

    public SecWifiUnclickablePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTitleColor = context.getColor(R.color.sec_wifi_preference_error_color);
        setLayoutResource(R.layout.sec_wifi_preference_unclickable_layout);
    }
}
