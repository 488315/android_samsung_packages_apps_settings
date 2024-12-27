package com.samsung.android.settings.wifi.intelligent;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiExcludedNetworkPref extends Preference {
    public Context mContext;
    public WifiConfiguration mWifiConfiguration;

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        WifiConfiguration wifiConfiguration = this.mWifiConfiguration;
        String str = wifiConfiguration.SSID;
        final int i = wifiConfiguration.networkId;
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.network_ssid);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.button_img);
        if (str == null) {
            str = null;
        } else {
            int length = str.length();
            if (length > 1 && str.charAt(0) == '\"') {
                int i2 = length - 1;
                if (str.charAt(i2) == '\"') {
                    str = str.substring(1, i2);
                }
            }
        }
        textView.setText(str);
        imageView.setImageResource(R.drawable.sec_display_ic_minus_mtrl);
        imageView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.intelligent.WifiExcludedNetworkPref.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiExcludedNetworkPref wifiExcludedNetworkPref =
                                WifiExcludedNetworkPref.this;
                        int i3 = i;
                        wifiExcludedNetworkPref.getClass();
                        Log.d(
                                "WifiExcludedNetworkPref",
                                "setSkipInternetCheck(), networkId :  " + i3);
                        ((SemWifiManager)
                                        wifiExcludedNetworkPref.mContext.getSystemService(
                                                WiFiManagerExt.SEM_WIFI_SERVICE))
                                .removeExcludedNetwork(i3);
                        LoggingHelper.insertEventLogging(3120, 3126);
                    }
                });
    }
}
