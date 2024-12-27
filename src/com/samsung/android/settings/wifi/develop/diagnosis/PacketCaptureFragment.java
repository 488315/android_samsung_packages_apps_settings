package com.samsung.android.settings.wifi.develop.diagnosis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import com.android.settings.R;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.OnActivityResultListener;
import com.android.settings.dashboard.DashboardFragment;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class PacketCaptureFragment extends DashboardFragment implements OnActivityResultListener {
    public static final IntentFilter WIFIAP_STATE_CHANGE_FILTER;
    public CheckBoxPreference ctrlCheckBoxPreference;
    public CheckBoxPreference dataCheckBoxPreference;
    public Context mContext;
    public PreferenceScreen mPreferenceScreen;
    public SemWifiManager mSemWifiManager;
    public SwitchPreference mSwitchPreference;
    public WifiStateChangeReceiver mWifiStateChangeReceiver;
    public CheckBoxPreference mgmtCheckBoxPreference;
    public boolean pktCaptureButtonState;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiStateChangeReceiver extends BroadcastReceiver {
        public WifiStateChangeReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m("Updating display config on receiving broadcast ", action, "PacketCaptureSettings", WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED)) {
                if (intent.getIntExtra("wifi_state", 0) == 13) {
                    PacketCaptureFragment.this.mSwitchPreference.setChecked(false);
                }
            } else if (action.equals("com.samsung.android.server.wifi.stopcapture")) {
                PacketCaptureFragment.this.mSwitchPreference.setChecked(false);
            }
        }
    }

    static {
        IntentFilter intentFilter = new IntentFilter();
        WIFIAP_STATE_CHANGE_FILTER = intentFilter;
        intentFilter.addAction(WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED);
        intentFilter.addAction("com.samsung.android.server.wifi.stopcapture");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "PacketCaptureSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.packet_capture_layout;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mSemWifiManager = (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        WifiStateChangeReceiver wifiStateChangeReceiver = new WifiStateChangeReceiver();
        this.mWifiStateChangeReceiver = wifiStateChangeReceiver;
        this.mContext.registerReceiver(wifiStateChangeReceiver, WIFIAP_STATE_CHANGE_FILTER, 2);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mPreferenceScreen = preferenceScreen;
        SwitchPreference switchPreference = (SwitchPreference) preferenceScreen.findPreference("capture_switch");
        this.mSwitchPreference = switchPreference;
        switchPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() { // from class: com.samsung.android.settings.wifi.develop.diagnosis.PacketCaptureFragment.1
            /* JADX WARN: Type inference failed for: r6v12, types: [boolean, int] */
            @Override // androidx.preference.Preference.OnPreferenceChangeListener
            public final boolean onPreferenceChange(Preference preference, Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                PacketCaptureFragment packetCaptureFragment = PacketCaptureFragment.this;
                packetCaptureFragment.pktCaptureButtonState = booleanValue;
                Boolean bool = Boolean.FALSE;
                packetCaptureFragment.mgmtCheckBoxPreference = (CheckBoxPreference) packetCaptureFragment.mPreferenceScreen.findPreference("mgmtframe");
                packetCaptureFragment.dataCheckBoxPreference = (CheckBoxPreference) packetCaptureFragment.mPreferenceScreen.findPreference("dataframe");
                packetCaptureFragment.ctrlCheckBoxPreference = (CheckBoxPreference) packetCaptureFragment.mPreferenceScreen.findPreference("ctrlframe");
                ?? r6 = packetCaptureFragment.mgmtCheckBoxPreference.mChecked;
                int i = r6;
                if (packetCaptureFragment.dataCheckBoxPreference.mChecked) {
                    i = r6 + 2;
                }
                int i2 = i;
                if (packetCaptureFragment.ctrlCheckBoxPreference.mChecked) {
                    i2 = i + 4;
                }
                String str = "Failed due to unknown errors";
                if (packetCaptureFragment.pktCaptureButtonState) {
                    int startCapture = packetCaptureFragment.mSemWifiManager.startCapture(i2);
                    if (startCapture == 0) {
                        bool = Boolean.TRUE;
                        str = "Packet Capture is in progress";
                    } else if (startCapture == 1) {
                        str = "Failed as file system storage is not available.So capture not started";
                    } else if (startCapture == 2) {
                        str = "Failed due to unsupported command.So capture not started";
                    } else if (startCapture == 3) {
                        str = "Failed as the capture is already running";
                    } else if (startCapture == 4) {
                        str = "Capture is not started as Wifi is not turned on or hotspot is turned on";
                    } else if (startCapture == 5) {
                        str = "Capture is not started as device is connected to MLO AP";
                    }
                    Toast.makeText(packetCaptureFragment.mContext, str, 1).show();
                } else {
                    int stopCapture = packetCaptureFragment.mSemWifiManager.stopCapture();
                    if (stopCapture == 0) {
                        bool = Boolean.TRUE;
                        str = "Successfully stopped the capture";
                    } else if (stopCapture == 1) {
                        str = "there is no running capture currently";
                    } else if (stopCapture == 2) {
                        str = "Failed to stop packet capture as input command is not supported";
                    }
                    Toast.makeText(packetCaptureFragment.mContext, str, 1).show();
                }
                return bool.booleanValue();
            }
        });
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mContext.unregisterReceiver(this.mWifiStateChangeReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.pktCaptureButtonState = this.mSemWifiManager.isCaptureRunning() == 1;
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("Button state on resume is "), this.pktCaptureButtonState, "PacketCaptureSettings");
        this.mSwitchPreference.setChecked(this.pktCaptureButtonState);
    }
}
