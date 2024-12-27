package com.samsung.android.settings.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;
import android.os.Debug;
import android.util.Log;

import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.wifitrackerlib.WifiEntry;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiRetryPopupController {
    public static final boolean DBG = Debug.semIsProductDev();
    public final Context mContext;
    public int mDisableReason;
    public final WifiManager mWifiManager;

    public WifiRetryPopupController(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
    }

    public static boolean isSecurityTypeSupportRetry(WifiEntry wifiEntry) {
        WifiConfiguration wifiConfiguration;
        WifiEnterpriseConfig wifiEnterpriseConfig;
        if (wifiEntry == null || !wifiEntry.isSaved()) {
            return false;
        }
        int security$1 = wifiEntry.getSecurity$1();
        if (security$1 == 1 || security$1 == 2 || security$1 == 5) {
            return true;
        }
        if ((security$1 != 3 && security$1 != 7)
                || (wifiConfiguration = wifiEntry.getWifiConfiguration()) == null
                || (wifiEnterpriseConfig = wifiConfiguration.enterpriseConfig) == null) {
            return false;
        }
        int eapMethod = wifiEnterpriseConfig.getEapMethod();
        return eapMethod == 0 || eapMethod == 2 || eapMethod == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0015, code lost:

       if (r7 != 5) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x005b, code lost:

       return false;
    */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:

       if (com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r0.preSharedKey).length() <= 64) goto L27;
    */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0057, code lost:

       r6.mDisableReason = -1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005a, code lost:

       return true;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0055, code lost:

       if (r7 <= 64) goto L27;
    */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean checkToShowRetryForIncorrectPasswordLength(
            com.android.wifitrackerlib.WifiEntry r7) {
        /*
            r6 = this;
            android.net.wifi.WifiConfiguration r0 = r7.getWifiConfiguration()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            int r7 = r7.getSecurity$1()
            r2 = 64
            r3 = 1
            if (r7 == r3) goto L25
            r4 = 2
            if (r7 == r4) goto L18
            r4 = 5
            if (r7 == r4) goto L18
            goto L5b
        L18:
            java.lang.String r7 = r0.preSharedKey
            java.lang.String r7 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r7)
            int r7 = r7.length()
            if (r7 <= r2) goto L5b
            goto L57
        L25:
            android.telephony.SubscriptionManager r7 = com.samsung.android.wifitrackerlib.SemWifiUtils.mSubscriptionManager
            int r7 = r0.wepTxKeyIndex
            if (r7 < 0) goto L33
            java.lang.String[] r4 = r0.wepKeys
            int r5 = r4.length
            if (r7 >= r5) goto L33
            r7 = r4[r7]
            goto L34
        L33:
            r7 = 0
        L34:
            java.lang.String r7 = com.samsung.android.wifitrackerlib.SemWifiUtils.removeDoubleQuotes(r7)
            int r7 = r7.length()
            if (r7 != 0) goto L55
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "WEP key is abnormal - "
            r4.<init>(r5)
            java.lang.String r0 = r0.getProfileKey()
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            java.lang.String r4 = "WifiSettings.retry"
            android.util.Log.d(r4, r0)
        L55:
            if (r7 <= r2) goto L5b
        L57:
            r7 = -1
            r6.mDisableReason = r7
            return r3
        L5b:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.WifiRetryPopupController.checkToShowRetryForIncorrectPasswordLength(com.android.wifitrackerlib.WifiEntry):boolean");
    }

    public final boolean isAllowToShowDialogForRetry(WifiConfiguration wifiConfiguration) {
        Context context = this.mContext;
        if (context == null) {
            Log.e("WifiSettings.retry", "not allowed to show retry - mContext is NULL");
            return false;
        }
        if (WifiUtils.isNetworkLockedDown(context, wifiConfiguration)) {
            Context context2 = this.mContext;
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    context2, RestrictedLockUtilsInternal.getDeviceOwner(context2));
            Log.e("WifiSettings.retry", "not allowed to show retry - isNetworkLockedDown");
            return false;
        }
        if (WifiDevicePolicyManager.isAllowedToShowRetryDialog(this.mContext)) {
            return true;
        }
        Log.e("WifiSettings.retry", "not allowed to show retry - WifiDevicePolicyManager");
        return false;
    }
}
