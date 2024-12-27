package com.samsung.android.settings.wifi.advanced;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiHistoryNoItemsPreference extends Preference {
    public WifiHistoryNoItemsPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.sec_preference_wifi_no_item);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        preferenceViewHolder.mDividerAllowedBelow = false;
        preferenceViewHolder.mDividerAllowedAbove = false;
    }

    public WifiHistoryNoItemsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
