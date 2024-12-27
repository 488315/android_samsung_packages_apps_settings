package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiExpandablePreference extends Preference {
    public final Context mContext;

    public WifiExpandablePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_expandable_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ((ImageView) preferenceViewHolder.findViewById(R.id.arrow))
                .setColorFilter(this.mContext.getColor(R.color.sec_wifi_arrow_icon_color));
        preferenceViewHolder.mDividerAllowedBelow = false;
    }
}
