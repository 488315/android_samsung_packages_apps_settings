package com.samsung.android.settings.wifi.intelligent;

import android.R;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SnsRadioButtonPreference extends CheckBoxPreference {
    public WifiSmartNetworkSwitchEnabler mListener;

    public SnsRadioButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkBoxPreferenceStyle);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView != null) {
            textView.setSingleLine(false);
        }
        if (textView2 != null) {
            textView2.setBreakStrategy(0);
        }
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        WifiSmartNetworkSwitchEnabler wifiSmartNetworkSwitchEnabler = this.mListener;
        if (wifiSmartNetworkSwitchEnabler != null) {
            if (this == wifiSmartNetworkSwitchEnabler.mAgg) {
                Log.d("WifiSmartNetworkSwitchEnabler", "Agg on pressed");
                wifiSmartNetworkSwitchEnabler.mAgg.setChecked(true);
                wifiSmartNetworkSwitchEnabler.mNormal.setChecked(false);
                Settings.Global.putInt(
                        wifiSmartNetworkSwitchEnabler.mContext.getContentResolver(),
                        "wifi_watchdog_poor_network_aggressive_mode_on",
                        1);
                return;
            }
            if (this == wifiSmartNetworkSwitchEnabler.mNormal) {
                Log.d("WifiSmartNetworkSwitchEnabler", "Agg off pressed");
                wifiSmartNetworkSwitchEnabler.mNormal.setChecked(true);
                wifiSmartNetworkSwitchEnabler.mAgg.setChecked(false);
                Settings.Global.putInt(
                        wifiSmartNetworkSwitchEnabler.mContext.getContentResolver(),
                        "wifi_watchdog_poor_network_aggressive_mode_on",
                        0);
            }
        }
    }

    public SnsRadioButtonPreference(Context context) {
        this(context, null);
    }

    public SnsRadioButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mListener = null;
        setLayoutResource(com.android.settings.R.layout.sec_sns_radio_button_preference_row);
        setWidgetLayoutResource(com.android.settings.R.layout.preference_widget_radiobutton);
    }
}