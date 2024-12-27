package com.android.settings.wifi;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.android.settings.R;

import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiConfigInfo extends Activity {
    public TextView mConfigList;
    public WifiManager mWifiManager;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
        setContentView(R.layout.wifi_config_info);
        this.mConfigList = (TextView) findViewById(R.id.config_list);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (!this.mWifiManager.isWifiEnabled()) {
            this.mConfigList.setText(R.string.wifi_state_disabled);
            return;
        }
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        StringBuffer stringBuffer = new StringBuffer();
        for (int size = configuredNetworks.size() - 1; size >= 0; size--) {
            stringBuffer.append(configuredNetworks.get(size));
        }
        this.mConfigList.setText(stringBuffer);
    }
}
