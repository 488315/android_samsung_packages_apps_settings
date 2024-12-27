package com.samsung.android.settings.wifi.develop;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.ServiceManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.settings.R;

import com.samsung.android.settings.general.ResetSettingsPreferenceFragment;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiDevelopmentResetSettingsFragment extends ResetSettingsPreferenceFragment {
    public Context mContext;
    public WifiManager mWifiManager;

    public static void resetDevelopmentOptions(Context context, WifiManager wifiManager) {
        Settings.Global.putInt(
                context.getContentResolver(), "sem_wifi_developer_option_visible", 0);
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        Settings.Global.putInt(
                context.getContentResolver(), "sem_wifi_carrier_network_offload_enabled", 1);
        Settings.Global.putInt(context.getContentResolver(), "sem_wifi_l4s_enabled", 0);
        if (wifiManager.isWifiStandardSupported(8)) {
            Settings.Secure.putInt(context.getContentResolver(), "sec_wifi_7_mode_enabled", 1);
            Settings.Secure.putInt(context.getContentResolver(), "sec_wifi_mlo_link_count", 0);
        }
        Settings.Global.putInt(
                context.getContentResolver(), "sem_wifi_network_rating_scorer_enabled_labs", 0);
        Settings.Secure.putInt(context.getContentResolver(), "sec_wifi_iot_setup_enabled", 0);
        Settings.Global.putInt(context.getContentResolver(), "sec_wifi_developer_rssi_level", 1);
        Settings.Global.putInt(context.getContentResolver(), "sec_wifi_developer_use_filter", 0);
        Settings.Global.putInt(context.getContentResolver(), "sec_wifi_developer_show_band", 0);
        Settings.Global.putInt(context.getContentResolver(), "sec_wifi_developer_sorting_style", 3);
        Settings.Global.putInt(
                context.getContentResolver(), "sec_wifi_developer_max_summary_line", 0);
        Settings.Global.putInt(
                context.getContentResolver(), "sem_wifi_settings_framework_scan_interval", 0);
        IOverlayManager asInterface =
                IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
        try {
            asInterface.setEnabled(
                    "com.samsung.android.wifi.decrease.scan.interval.resources", false, 0);
            asInterface.setEnabled(
                    "com.samsung.android.wifi.increase.scan.interval.resources", false, 0);
        } catch (Exception e) {
            Log.w("SemWifiUtils", "Failed to set RRO ", e);
        }
        Settings.Global.putInt(
                context.getContentResolver(),
                "wifi_switch_to_mobile_data_super_aggressive_mode_on",
                0);
        SemWifiUtils.resetAiConfiguration(context);
        SemWifiManager semWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        if (semWifiManager.isCaptureRunning() == 1) {
            semWifiManager.stopCapture();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final String getResetButtonTitle() {
        return getString(R.string.sec_btn_development_reset_settings);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.sec_development_reset_settings, (ViewGroup) null);
    }

    @Override // com.samsung.android.settings.general.ResetSettingsPreferenceFragment
    public final void onResetButtonClick() {
        resetDevelopmentOptions(this.mContext, this.mWifiManager);
        Toast.makeText(getContext(), R.string.sec_development_reset_settings_completed, 1).show();
        finish();
    }
}
