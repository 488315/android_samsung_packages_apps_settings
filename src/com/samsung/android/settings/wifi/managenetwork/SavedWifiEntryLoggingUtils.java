package com.samsung.android.settings.wifi.managenetwork;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SavedWifiEntryLoggingUtils {
    public static final int LOGGING_SECURITY_TYPE_COUNT = 7;
    public static final int LOGGING_SEMWIFICONFIG_TYPE = 3;
    public static final int LOGGING_TYPE_CAPTIVE_PORTAL = 0;
    public static final int LOGGING_TYPE_EAP = 5;
    public static final int LOGGING_TYPE_LOCK_DOWN = 1;
    public static final int LOGGING_TYPE_NO_INTERNET = 2;
    public static final int LOGGING_TYPE_OPEN = 0;
    public static final int LOGGING_TYPE_OWE_ONLY = 1;
    public static final int LOGGING_TYPE_PSK = 3;
    public static final int LOGGING_TYPE_SAE_ONLY = 4;
    public static final int LOGGING_TYPE_WAPI = 6;
    public static final int LOGGING_TYPE_WEP = 2;
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryLoggingUtils$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            SemWifiManager semWifiManager =
                    (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            List configuredNetworks2 = semWifiManager.getConfiguredNetworks();
            int[] securityTypeForStatusLogging =
                    SavedWifiEntryLoggingUtils.getSecurityTypeForStatusLogging(configuredNetworks);
            int[] semConfigTypeForStatusLogging =
                    SavedWifiEntryLoggingUtils.getSemConfigTypeForStatusLogging(
                            configuredNetworks2);
            ArrayList arrayList = new ArrayList();
            String valueOf = String.valueOf(configuredNetworks.size());
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf;
            statusData.mStatusKey = "12501";
            arrayList.add(statusData);
            String valueOf2 = String.valueOf(securityTypeForStatusLogging[0]);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf2;
            statusData2.mStatusKey = "12502";
            arrayList.add(statusData2);
            String valueOf3 = String.valueOf(securityTypeForStatusLogging[1]);
            StatusData statusData3 = new StatusData();
            statusData3.mStatusValue = valueOf3;
            statusData3.mStatusKey = "12503";
            arrayList.add(statusData3);
            String valueOf4 = String.valueOf(securityTypeForStatusLogging[2]);
            StatusData statusData4 = new StatusData();
            statusData4.mStatusValue = valueOf4;
            statusData4.mStatusKey = "12504";
            arrayList.add(statusData4);
            String valueOf5 = String.valueOf(securityTypeForStatusLogging[3]);
            StatusData statusData5 = new StatusData();
            statusData5.mStatusValue = valueOf5;
            statusData5.mStatusKey = "12505";
            arrayList.add(statusData5);
            String valueOf6 = String.valueOf(securityTypeForStatusLogging[4]);
            StatusData statusData6 = new StatusData();
            statusData6.mStatusValue = valueOf6;
            statusData6.mStatusKey = "12506";
            arrayList.add(statusData6);
            String valueOf7 = String.valueOf(securityTypeForStatusLogging[5]);
            StatusData statusData7 = new StatusData();
            statusData7.mStatusValue = valueOf7;
            statusData7.mStatusKey = "12507";
            arrayList.add(statusData7);
            String valueOf8 = String.valueOf(securityTypeForStatusLogging[6]);
            StatusData statusData8 = new StatusData();
            statusData8.mStatusValue = valueOf8;
            statusData8.mStatusKey = "12508";
            arrayList.add(statusData8);
            String valueOf9 = String.valueOf(semConfigTypeForStatusLogging[0]);
            StatusData statusData9 = new StatusData();
            statusData9.mStatusValue = valueOf9;
            statusData9.mStatusKey = "12509";
            arrayList.add(statusData9);
            String valueOf10 = String.valueOf(semConfigTypeForStatusLogging[1]);
            StatusData statusData10 = new StatusData();
            statusData10.mStatusValue = valueOf10;
            statusData10.mStatusKey = "12510";
            arrayList.add(statusData10);
            String valueOf11 = String.valueOf(semConfigTypeForStatusLogging[2]);
            StatusData statusData11 = new StatusData();
            statusData11.mStatusValue = valueOf11;
            statusData11.mStatusKey = "12511";
            arrayList.add(statusData11);
            return arrayList;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:51:0x00e3 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00dc A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int[] getSecurityTypeForStatusLogging(
            java.util.List<android.net.wifi.WifiConfiguration> r11) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.managenetwork.SavedWifiEntryLoggingUtils.getSecurityTypeForStatusLogging(java.util.List):int[]");
    }

    public static int[] getSemConfigTypeForStatusLogging(List<SemWifiConfiguration> list) {
        int[] array =
                IntStream.range(0, 3)
                        .map(new SavedWifiEntryLoggingUtils$$ExternalSyntheticLambda0(1))
                        .toArray();
        for (SemWifiConfiguration semWifiConfiguration : list) {
            if (semWifiConfiguration.isCaptivePortal()) {
                array[0] = array[0] + 1;
            } else if (semWifiConfiguration.isLockDown()) {
                array[1] = array[1] + 1;
            } else if (semWifiConfiguration.isNoInternetAccessExpected) {
                array[2] = array[2] + 1;
            }
        }
        return array;
    }

    private static boolean isEapNetwork(WifiConfiguration wifiConfiguration) {
        int authType = wifiConfiguration.getAuthType();
        return authType == 2 || authType == 3;
    }

    private static boolean isOpenNetwork(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.getAuthType() == 0;
    }

    private static boolean isOweNetwork(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.getAuthType() == 9;
    }

    private static boolean isPskNetwork(WifiConfiguration wifiConfiguration) {
        int authType = wifiConfiguration.getAuthType();
        return authType == 1 || authType == 4;
    }

    private static boolean isSaeNetwork(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.getAuthType() == 8;
    }

    private static boolean isWapiNetwork(WifiConfiguration wifiConfiguration) {
        int authType = wifiConfiguration.getAuthType();
        return authType == 13 || authType == 14;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getSecurityTypeForStatusLogging$0(int i) {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$getSemConfigTypeForStatusLogging$1(int i) {
        return 0;
    }
}
