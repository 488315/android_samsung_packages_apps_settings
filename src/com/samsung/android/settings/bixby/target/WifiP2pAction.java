package com.samsung.android.settings.bixby.target;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WifiP2pAction extends Action {
    public WifiManager mWifiManager;

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        WifiManager wifiManager;
        Log.d("WifiP2pAction", "gotoWifiP2pSetting()");
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$WifiP2pSettingsActivity");
        intent.setFlags(268435456);
        intent.putExtra("EXTRA_TRIGGER_APP", "bixby");
        try {
            this.mContext.startActivity(intent);
            wifiManager = this.mWifiManager;
        } catch (ActivityNotFoundException unused) {
            Log.e("WifiP2pAction", "ActivityNotFoundException :: Can not found WifiP2pSettings");
            str = "not_supported_device";
        }
        if (wifiManager != null) {
            int wifiApState = wifiManager.getWifiApState();
            if (wifiApState != 13) {
                if (wifiApState == 12) {}
            }
            str = "wifip2p_error_hotspot_enabled";
            return Action.createResult(str);
        }
        Log.e("WifiP2pAction", "mWifiManager is null, so hotspot checking is fail");
        str = "success";
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        if (Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy4",
                        "isWifiDirectAllowed")
                != 0) {
            return Action.createResult("true");
        }
        Log.i("WifiP2pAction", "WiFi Direct is disabled due to Knox MDM Restriction Policy");
        return Action.createResult("restrict_by_knox");
    }
}
