package com.samsung.android.settings.bixby.target;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;

import com.android.settings.Utils;
import com.android.settings.datausage.DataSaverBackend;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.connection.ConnectionsUtils;
import com.samsung.android.wifi.SemWifiApCust;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class HotspotAction extends Action {
    public SemWifiManager mSemWifiManager;
    public UserManager mUserManager;
    public WifiManager mWifiManager;

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        return Action.createResult(this.mWifiManager.isWifiApEnabled() ? "true" : "false");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$WifiApSettingsActivity");
        intent.setAction("android.settings.WIFI_AP_SETTINGS");
        intent.setFlags(268468224);
        Utils.setTaskIdToIntent(intent, getTaskId());
        try {
            launchSettings(intent, null);
            str = "success";
        } catch (ActivityNotFoundException unused) {
            Log.e(
                    "HotspotAction",
                    "ActivityNotFoundException :: Can not found WifiApSettingsActivity");
            str = "not_supported_device";
        }
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSetAction() {
        String value = getValue();
        Log.d("HotspotAction", "doSetAction, value:" + value);
        int i = 0;
        String str = "success";
        if ("true".equals(value)) {
            if (ConnectionsUtils.isAirplaneModeEnabled(this.mContext)) {
                str = "hotspot_not_support_airplanemode";
            } else if (new DataSaverBackend(this.mContext).mPolicyManager.getRestrictBackground()) {
                str = "hotspot_not_support_datasaver";
            } else if (this.mWifiManager.isWifiApEnabled()) {
                str = "already_on";
            } else if (this.mWifiManager.isWifiEnabled()
                    && !this.mSemWifiManager.isWifiSharingEnabled()) {
                str = "hotspot_already_wifion";
            } else {
                if (!SemWifiApCust.isProvisioningNeeded()) {
                    this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, true);
                    Log.i("HotspotAction", "doSetAction true, no need provisioning.");
                    return Action.createResult("success");
                }
                Utils.USE_BIXBY = true;
                Context context = this.mContext;
                Log.i("HotspotAction", "startHotspotProvisioningRequest");
                Intent intent = new Intent();
                intent.setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.samsung.android.settings.wifi.mobileap.WifiApWarning");
                intent.setFlags(268435456);
                intent.setAction("com.samsung.android.settings.wifi.mobileap.wifiapwarning");
                intent.putExtra("wifiap_warning_dialog_type", 6);
                Utils.setTaskIdToIntent(intent, getTaskId());
                context.startActivity(intent);
                while (true) {
                    if (!this.mWifiManager.isWifiApEnabled()) {
                        if (!Utils.USE_BIXBY) {
                            str = "hotspot_not_provision";
                            break;
                        }
                        int i2 = i + 1;
                        if (i >= 120) {
                            Log.i("HotspotAction", "Mobile Hotspot Time out");
                            str = "fail";
                            break;
                        }
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                        i = i2;
                    } else {
                        break;
                    }
                }
            }
        } else if (this.mWifiManager.isWifiApEnabled()) {
            this.mSemWifiManager.setWifiApEnabled((SoftApConfiguration) null, false);
        } else {
            str = "already_off";
        }
        Log.i("HotspotAction", "doSetAction result = ".concat(str));
        return Action.createResult(str);
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        if (com.android.settingslib.Utils.isWifiOnly(this.mContext)
                || !this.mUserManager.isAdminUser()) {
            Log.i("HotspotAction", "Tethering not supported");
            return Action.createResult("false");
        }
        if (Utils.getEnterprisePolicyEnabled(
                        this.mContext,
                        "content://com.sec.knox.provider/RestrictionPolicy4",
                        "isWifiTetheringEnabled")
                != 0) {
            return Action.createResult("true");
        }
        Log.i("HotspotAction", "MHS is disabled due to Knox MDM Restriction Policy");
        return Action.createResult("restrict_by_knox");
    }
}
