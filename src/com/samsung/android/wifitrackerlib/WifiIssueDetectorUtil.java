package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.os.Bundle;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiIssueDetectorUtil {
    public final String mNameOfUid;
    public final SemWifiManager mSemWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ReportUtil {
        public static Bundle getReportDataForWifiManagerApi(
                int i, String str, String str2, String str3) {
            Bundle bundle = new Bundle();
            bundle.putInt("netid", i);
            bundle.putString("apiName", str);
            bundle.putString("callUid", str2);
            bundle.putString("callBy", str3);
            return bundle;
        }

        public static Bundle getReportDataForWifiManagerConnectApi(
                int i, boolean z, int i2, String str, String str2) {
            Bundle bundle = new Bundle();
            bundle.putInt("netid", i);
            bundle.putString("ssid", str);
            bundle.putString("apiName", "connect");
            bundle.putString("callUid", str2);
            bundle.putInt("hasPassword", z ? 1 : 0);
            bundle.putInt("isPasspoint", i2);
            return bundle;
        }
    }

    public WifiIssueDetectorUtil(Context context) {
        context.getPackageName();
        this.mNameOfUid = context.getPackageManager().getNameForUid(context.getUserId());
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0032, code lost:

       if (r2.length() > 2) goto L18;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0040, code lost:

       if (r2.length() > 2) goto L18;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportConnectNetwork(android.net.wifi.WifiConfiguration r8) {
        /*
            r7 = this;
            if (r8 == 0) goto L54
            int r0 = r8.networkId
            java.lang.String r1 = r8.getPrintableSsid()
            java.util.BitSet r2 = r8.allowedKeyManagement
            r3 = 1
            boolean r2 = r2.get(r3)
            r4 = 0
            r5 = 2
            if (r2 != 0) goto L38
            java.util.BitSet r2 = r8.allowedKeyManagement
            r6 = 8
            boolean r2 = r2.get(r6)
            if (r2 == 0) goto L1e
            goto L38
        L1e:
            java.util.BitSet r2 = r8.allowedKeyManagement
            boolean r2 = r2.get(r4)
            if (r2 == 0) goto L43
            java.lang.String[] r2 = r8.wepKeys
            if (r2 == 0) goto L43
            r2 = r2[r4]
            if (r2 == 0) goto L43
            int r2 = r2.length()
            if (r2 <= r5) goto L35
            goto L36
        L35:
            r3 = r4
        L36:
            r4 = r3
            goto L43
        L38:
            java.lang.String r2 = r8.preSharedKey
            if (r2 == 0) goto L35
            int r2 = r2.length()
            if (r2 <= r5) goto L35
            goto L36
        L43:
            boolean r8 = r8.isPasspoint()
            com.samsung.android.wifi.SemWifiManager r2 = r7.mSemWifiManager
            java.lang.String r7 = r7.mNameOfUid
            android.os.Bundle r7 = com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil.ReportUtil.getReportDataForWifiManagerConnectApi(r0, r4, r8, r1, r7)
            r8 = 103(0x67, float:1.44E-43)
            r2.reportIssue(r8, r7)
        L54:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.wifitrackerlib.WifiIssueDetectorUtil.reportConnectNetwork(android.net.wifi.WifiConfiguration):void");
    }
}
