package com.samsung.android.settings.bixby.target;

import android.app.KeyguardManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Utils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.bixby.target.actions.Action;
import com.samsung.android.settings.bixby.utils.BixbyUtils;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class WifiSettingsAction extends Action {
    public final KeyguardManager mKeyguardManager;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    public WifiSettingsAction(Context context, Bundle bundle) {
        super(context, bundle);
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mKeyguardManager = (KeyguardManager) context.getSystemService("keyguard");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doDisableAction() {
        this.mBundle.putString("value", "false");
        Bundle doSetAction = doSetAction();
        return TextUtils.equals(doSetAction.getString("result"), "fail")
                ? doSetAction
                : doGotoAction();
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGetAction() {
        return Action.createResult(this.mWifiManager.isWifiEnabled() ? "true" : "false");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doGotoAction() {
        String str;
        WifiConfiguration wifiConfiguration;
        String string = this.mBundle.getString("target");
        Log.d("WifiSettingsAction", "startWifiSettings, target:" + string);
        Intent intent = new Intent();
        if (!"wifiQrcode".equals(string)) {
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.android.settings.Settings$WifiSettingsActivity");
        } else {
            if (this.mWifiManager.getCurrentNetwork() == null) {
                Log.d("WifiSettingsAction", "getCurrentNetwork is not valid");
                return Action.createResult("disconnected");
            }
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            if (connectionInfo == null) {
                Log.d("WifiSettingsAction", "wifiInfo is null");
                return Action.createResult("fail");
            }
            int networkId = connectionInfo.getNetworkId();
            List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
            if (configuredNetworks != null) {
                Iterator<WifiConfiguration> it = configuredNetworks.iterator();
                while (it.hasNext()) {
                    wifiConfiguration = it.next();
                    if (networkId == wifiConfiguration.networkId) {
                        break;
                    }
                }
            }
            wifiConfiguration = null;
            if (wifiConfiguration == null) {
                Log.d("WifiSettingsAction", "wifiConfiguration is null");
                return Action.createResult("fail");
            }
            intent.putExtras(new Bundle());
            intent.setClassName(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    "com.android.settings.Settings$WifiQrCodeActivity");
        }
        intent.setAction("android.settings.WIFI_SETTINGS");
        intent.setFlags(268468224);
        Utils.setTaskIdToIntent(intent, getTaskId());
        try {
            launchSettings(
                    intent,
                    Utils.isDesktopModeEnabled(this.mContext)
                            ? BixbyUtils.getDeXDisplay(this.mContext)
                            : null);
            str = "success";
        } catch (ActivityNotFoundException unused) {
            Log.e("WifiSettingsAction", "ActivityNotFoundException, Settings$WifiSettingsActivity");
            str = "not_supported_device";
        }
        return Action.createResult(str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004a, code lost:

       if (android.provider.Settings.Secure.getInt(r6.mContext.getContentResolver(), "wifi_ap_wifi_sharing") == 1) goto L20;
    */
    @Override // com.samsung.android.settings.bixby.target.actions.Action
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle doSetAction() {
        /*
            r6 = this;
            java.lang.String r0 = r6.getValue()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "setEnabled, value:"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "WifiSettingsAction"
            android.util.Log.d(r2, r1)
            java.lang.String r1 = "true"
            boolean r1 = r1.equals(r0)
            java.lang.String r3 = "success"
            r4 = 0
            if (r1 == 0) goto L7d
            android.net.wifi.WifiManager r0 = r6.mWifiManager
            boolean r0 = r0.isWifiEnabled()
            if (r0 == 0) goto L34
            java.lang.String r6 = "already_on"
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r6)
            return r6
        L34:
            android.net.wifi.WifiManager r0 = r6.mWifiManager
            boolean r0 = r0.isWifiApEnabled()
            r1 = 1
            if (r0 == 0) goto L73
            android.content.Context r0 = r6.mContext     // Catch: android.provider.Settings.SettingNotFoundException -> L4d
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: android.provider.Settings.SettingNotFoundException -> L4d
            java.lang.String r5 = "wifi_ap_wifi_sharing"
            int r0 = android.provider.Settings.Secure.getInt(r0, r5)     // Catch: android.provider.Settings.SettingNotFoundException -> L4d
            if (r0 != r1) goto L52
            goto L73
        L4d:
            java.lang.String r0 = "isWifiSharingEnabled, SettingNotFoundException"
            android.util.Log.d(r2, r0)
        L52:
            android.app.KeyguardManager r0 = r6.mKeyguardManager
            boolean r0 = r0.isKeyguardLocked()
            if (r0 == 0) goto L61
            java.lang.String r6 = "KeyguardIsLocked"
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r6)
            return r6
        L61:
            com.samsung.android.wifi.SemWifiManager r0 = r6.mSemWifiManager
            r2 = 0
            r0.setWifiApEnabled(r2, r4)
            android.net.wifi.WifiManager r6 = r6.mWifiManager
            r6.setWifiEnabled(r1)
            java.lang.String r6 = "disableWifiAp"
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r6)
            return r6
        L73:
            android.net.wifi.WifiManager r6 = r6.mWifiManager
            r6.setWifiEnabled(r1)
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r3)
            return r6
        L7d:
            java.lang.String r1 = "false"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L9e
            android.net.wifi.WifiManager r0 = r6.mWifiManager
            boolean r0 = r0.isWifiEnabled()
            if (r0 == 0) goto L97
            android.net.wifi.WifiManager r6 = r6.mWifiManager
            r6.setWifiEnabled(r4)
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r3)
            return r6
        L97:
            java.lang.String r6 = "already_off"
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r6)
            return r6
        L9e:
            android.net.wifi.WifiManager r0 = r6.mWifiManager
            boolean r0 = r0.isWifiEnabled()
            if (r0 == 0) goto Lab
            android.net.wifi.WifiManager r6 = r6.mWifiManager
            r6.setWifiEnabled(r4)
        Lab:
            java.lang.String r6 = "fail"
            android.os.Bundle r6 = com.samsung.android.settings.bixby.target.actions.Action.createResult(r6)
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.target.WifiSettingsAction.doSetAction():android.os.Bundle");
    }

    @Override // com.samsung.android.settings.bixby.target.actions.Action
    public final Bundle doSupportAction() {
        Log.d("WifiSettingsAction", "doSupportAction");
        if (!this.mWifiManager.isWifiEnabled()) {
            Log.d("WifiSettingsAction", "wifiInfo is disabled");
            return Action.createResult("disconnected");
        }
        if (this.mWifiManager.getCurrentNetwork() == null) {
            Log.d("WifiSettingsAction", "getCurrentNetwork is not valid");
            return Action.createResult("disconnected");
        }
        WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
        if (connectionInfo == null) {
            Log.d("WifiSettingsAction", "wifiInfo is not valid");
            return Action.createResult("disconnected");
        }
        int currentSecurityType = connectionInfo.getCurrentSecurityType();
        if ((currentSecurityType == 1
                        || currentSecurityType == 2
                        || currentSecurityType == 4
                        || currentSecurityType == 7
                        || currentSecurityType == 6
                        || currentSecurityType == 0)
                && !KnoxUtils.isApplicationRestricted(this.mContext, "wifi_qrcode", "hide")) {
            return Action.createResult("success");
        }
        Log.d("WifiSettingsAction", "is not SecurityType: " + currentSecurityType);
        return Action.createResult("not_supported_device");
    }
}
