package com.samsung.android.settings.wifi.develop.savednetwork;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.settings.wifi.develop.details.WifiNetworkDetailsFragmentForLabs;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SavedWifiEntryPreference extends Preference
        implements Preference.OnPreferenceClickListener {
    public final Context mContext;
    public TextView mLastConnected;
    public ImageView mSecurityPoint;
    public TextView mSecuritySummary;
    public TextView mTitle;
    public final WifiLabsSecurityType mType;
    public final WifiEntry mWifiEntry;

    public SavedWifiEntryPreference(Context context, WifiEntry wifiEntry) {
        super(context);
        this.mContext = context;
        this.mWifiEntry = wifiEntry;
        this.mType = new WifiLabsSecurityType(wifiEntry);
        setLayoutResource(R.layout.sec_wifi_saved_wifi_entry_preference);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        String str;
        String string;
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedBelow = true;
        preferenceViewHolder.mDividerAllowedAbove = true;
        this.mTitle = (TextView) preferenceViewHolder.findViewById(R.id.entry_title);
        this.mSecurityPoint = (ImageView) preferenceViewHolder.findViewById(R.id.security_icon);
        this.mSecuritySummary = (TextView) preferenceViewHolder.findViewById(R.id.security_summary);
        this.mLastConnected =
                (TextView) preferenceViewHolder.findViewById(R.id.security_last_connected);
        this.mTitle.setText(this.mWifiEntry.getTitle());
        ImageView imageView = this.mSecurityPoint;
        GradientDrawable gradientDrawable =
                (GradientDrawable) this.mContext.getDrawable(R.drawable.sec_wifi_labs_open_point);
        Context context = this.mContext;
        switch (this.mType.mType.$r8$classId) {
            case 0:
                i = R.color.sec_wifi_labs_network_info_security_undefined_color;
                break;
            case 1:
                i = R.color.sec_wifi_labs_network_info_security_eap_color;
                break;
            case 2:
                i = R.color.sec_wifi_labs_network_info_security_open_color;
                break;
            case 3:
                i = R.color.sec_wifi_labs_network_info_security_owe_color;
                break;
            case 4:
                i = R.color.sec_wifi_labs_network_info_security_sae_color;
                break;
            case 5:
                i = R.color.sec_wifi_labs_network_info_security_wep_color;
                break;
            default:
                i = R.color.sec_wifi_labs_network_info_security_wpa_color;
                break;
        }
        gradientDrawable.setColor(context.getColor(i));
        imageView.setImageDrawable(gradientDrawable);
        TextView textView = this.mSecuritySummary;
        switch (this.mType.mType.$r8$classId) {
            case 0:
                str = "Undefined";
                break;
            case 1:
                str = "EAP";
                break;
            case 2:
                str = WifiPolicy.SECURITY_TYPE_OPEN;
                break;
            case 3:
                str = "OWE";
                break;
            case 4:
                str = "WPA3";
                break;
            case 5:
                str = "WEP";
                break;
            default:
                str = "WPA2/3";
                break;
        }
        textView.setText(str);
        Context context2 = this.mContext;
        WifiEntry wifiEntry = this.mWifiEntry;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        if (wifiEntry.getWifiConfiguration() == null) {
            string = context2.getString(R.string.sec_wifi_network_information_never_connected);
        } else {
            SemWifiManager semWifiManager =
                    (SemWifiManager) context2.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            long currentTimeMillis = System.currentTimeMillis();
            String key = wifiEntry.getWifiConfiguration().getKey();
            Map networkLastUpdatedTimeMap = semWifiManager.getNetworkLastUpdatedTimeMap();
            if (networkLastUpdatedTimeMap.containsKey(key)) {
                if (((Long) networkLastUpdatedTimeMap.get(key)).longValue() == -1) {
                    string =
                            context2.getString(
                                    R.string.sec_wifi_network_information_never_connected);
                } else if (networkLastUpdatedTimeMap.containsKey(key)
                        && currentTimeMillis
                                        - ((Long) networkLastUpdatedTimeMap.get(key)).longValue()
                                > 15552000000L) {
                    string =
                            context2.getString(
                                    R.string.sec_wifi_network_information_last_connected,
                                    Integer.valueOf(
                                            (int)
                                                    ((currentTimeMillis
                                                                    - ((Long)
                                                                                    networkLastUpdatedTimeMap
                                                                                            .get(
                                                                                                    key))
                                                                            .longValue())
                                                            / 2592000000L)));
                }
            }
            string = ApnSettings.MVNO_NONE;
        }
        if (TextUtils.isEmpty(string)) {
            this.mLastConnected.setVisibility(8);
        } else {
            this.mLastConnected.setVisibility(0);
            this.mLastConnected.setText(string);
        }
        ((LinearLayout) preferenceViewHolder.findViewById(R.id.saved_wifi_entry))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.wifi.develop.savednetwork.SavedWifiEntryPreference.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SavedWifiEntryPreference savedWifiEntryPreference =
                                        SavedWifiEntryPreference.this;
                                WifiEntry wifiEntry2 = savedWifiEntryPreference.mWifiEntry;
                                savedWifiEntryPreference.getClass();
                                Log.d("SavedWifiEntryPreference", "launchNetworkDetailsFragment");
                                WifiConfiguration wifiConfiguration =
                                        wifiEntry2.getWifiConfiguration();
                                if (WifiUtils.isNetworkLockedDown(
                                                savedWifiEntryPreference.mContext,
                                                wifiConfiguration)
                                        && wifiEntry2.getConnectedInfo() != null) {
                                    Context context3 = savedWifiEntryPreference.mContext;
                                    RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                                            context3,
                                            RestrictedLockUtilsInternal.getDeviceOwner(context3));
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("key_chosen_wifientry_key", wifiEntry2.getKey());
                                bundle.putParcelable(
                                        "key_chosen_wifientry_config", wifiConfiguration);
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(savedWifiEntryPreference.mContext);
                                String title = savedWifiEntryPreference.mWifiEntry.getTitle();
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mTitle = title;
                                launchRequest.mDestinationName =
                                        WifiNetworkDetailsFragmentForLabs.class.getName();
                                launchRequest.mArguments = bundle;
                                launchRequest.mSourceMetricsCategory = 0;
                                subSettingLauncher.launch();
                            }
                        });
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        return true;
    }
}
