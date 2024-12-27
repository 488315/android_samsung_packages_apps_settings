package com.android.settings.fuelgauge.batteryusage;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.AppPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerGaugePreference extends AppPreference {
    public BatteryDiffEntry mBatteryDiffEntry;
    public final CharSequence mContentDescription;
    public CharSequence mProgress;
    public CharSequence mProgressContentDescription;
    public final int mTitleColorNormal;

    public PowerGaugePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWidgetLayoutResource(R.layout.preference_widget_summary);
        this.mContentDescription = null;
        this.mTitleColorNormal =
                Utils.getColorAttrDefaultColor(context, android.R.attr.textColorPrimary);
    }

    public static void setViewAlpha(View view, float f) {
        if (!(view instanceof ViewGroup)) {
            view.setAlpha(f);
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            setViewAlpha(viewGroup.getChildAt(childCount), f);
        }
    }

    @Override // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        com.android.settings.Utils.isNightMode(getContext());
        setViewAlpha(preferenceViewHolder.itemView, isSelectable() ? 1.0f : 0.65f);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.widget_summary);
        textView.setText(this.mProgress);
        if (!TextUtils.isEmpty(this.mProgressContentDescription)) {
            textView.setContentDescription(this.mProgressContentDescription);
        }
        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        if (this.mContentDescription != null) {
            ((TextView) preferenceViewHolder.findViewById(android.R.id.title))
                    .setContentDescription(this.mContentDescription);
        }
        if (isSelectable()) {
            return;
        }
        ((TextView) preferenceViewHolder.findViewById(android.R.id.title))
                .setTextColor(this.mTitleColorNormal);
        ((TextView) preferenceViewHolder.findViewById(android.R.id.summary))
                .setTextColor(this.mTitleColorNormal);
        textView.setTextColor(this.mTitleColorNormal);
    }
}
