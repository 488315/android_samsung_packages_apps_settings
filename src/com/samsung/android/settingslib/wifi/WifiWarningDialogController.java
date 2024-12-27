package com.samsung.android.settingslib.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiWarningDialogController {
    public final Context mContext;
    public final SemWifiManager mSemWifiManager;
    public final WifiManager mWifiManager;

    public WifiWarningDialogController(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    public static Intent getWifiWarningIntent() {
        Intent intent = new Intent();
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.samsung.android.settings.wifi.WifiWarning");
        intent.setFlags(343932928);
        return intent;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x0071, code lost:

       if (r1 == false) goto L27;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isNeedToConfirmApDisable() {
        /*
            r10 = this;
            android.content.Context r0 = r10.mContext
            android.content.ContentResolver r0 = r0.getContentResolver()
            java.lang.String r1 = "wifi_sharing_lite_popup_status"
            r2 = 0
            int r0 = android.provider.Settings.System.getInt(r0, r1, r2)
            boolean r1 = r10.isWifiApEnabled()
            java.lang.String r3 = "wifiwarning"
            r4 = 1
            if (r1 == 0) goto L31
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            int[] r1 = r1.getSoftApBands()
            if (r1 == 0) goto L31
            int r1 = r1.length
            if (r1 <= r4) goto L31
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            boolean r1 = r1.isWifiApEnabledWithDualBand()
            if (r1 != 0) goto L31
            java.lang.String r10 = " configured dualAp, but single softap interface is running"
            android.util.Log.w(r3, r10)
            return r2
        L31:
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            boolean r1 = r1.isWifiSharingEnabled()
            java.lang.String r5 = "statusbar"
            java.lang.String r6 = "extra_type"
            java.lang.String r7 = "req_type"
            if (r1 != 0) goto L46
            boolean r1 = r10.isWifiApEnabled()
            if (r1 != 0) goto L73
        L46:
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            boolean r1 = r1.isWifiApEnabledWithDualBand()
            if (r1 == 0) goto L90
            boolean r1 = com.samsung.android.wifi.SemWifiApCust.DBG
            if (r1 == 0) goto L70
            java.lang.String r1 = "vendor.wifiap.debug.BridgedApSta"
            int r1 = android.os.SystemProperties.getInt(r1, r2)
            if (r1 != r4) goto L5d
            r1 = r4
            goto L5e
        L5d:
            r1 = r2
        L5e:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = " supportBridgedApStaConcurrency() "
            r8.<init>(r9)
            r8.append(r1)
            java.lang.String r8 = r8.toString()
            android.util.Log.w(r3, r8)
            goto L71
        L70:
            r1 = r2
        L71:
            if (r1 != 0) goto L90
        L73:
            android.content.Context r0 = r10.mContext
            java.lang.Object r0 = r0.getSystemService(r5)
            android.app.StatusBarManager r0 = (android.app.StatusBarManager) r0
            if (r0 == 0) goto L80
            r0.collapsePanels()
        L80:
            android.content.Intent r0 = getWifiWarningIntent()
            r0.putExtra(r7, r2)
            r0.putExtra(r6, r4)
            android.content.Context r10 = r10.mContext     // Catch: android.content.ActivityNotFoundException -> L8f
            r10.startActivity(r0)     // Catch: android.content.ActivityNotFoundException -> L8f
        L8f:
            return r4
        L90:
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            boolean r1 = r1.isWifiSharingLiteSupported()
            if (r1 == 0) goto Lc6
            com.samsung.android.wifi.SemWifiManager r1 = r10.mSemWifiManager
            boolean r1 = r1.isWifiSharingEnabled()
            if (r1 == 0) goto Lc6
            boolean r1 = r10.isWifiApEnabled()
            if (r1 == 0) goto Lc6
            if (r0 != 0) goto Lc6
            android.content.Context r0 = r10.mContext
            java.lang.Object r0 = r0.getSystemService(r5)
            android.app.StatusBarManager r0 = (android.app.StatusBarManager) r0
            if (r0 == 0) goto Lb5
            r0.collapsePanels()
        Lb5:
            android.content.Intent r0 = getWifiWarningIntent()
            r0.putExtra(r7, r2)
            r1 = 5
            r0.putExtra(r6, r1)
            android.content.Context r10 = r10.mContext     // Catch: android.content.ActivityNotFoundException -> Lc5
            r10.startActivity(r0)     // Catch: android.content.ActivityNotFoundException -> Lc5
        Lc5:
            return r4
        Lc6:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settingslib.wifi.WifiWarningDialogController.isNeedToConfirmApDisable():boolean");
    }

    public final boolean isWifiApEnabled() {
        return this.mWifiManager.getWifiApState() == 13 || this.mWifiManager.getWifiApState() == 12;
    }
}
