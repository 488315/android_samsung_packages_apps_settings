package com.android.wifitrackerlib;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TransportInfo;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.MloLink;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.text.format.DateUtils;

import androidx.core.os.BuildCompat;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import com.sec.ims.settings.ImsProfile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class Utils {
    public static final List defaultSsidList =
            Arrays.asList(
                    "linksys",
                    "netgear",
                    "dlink",
                    "wireless",
                    "2wire",
                    "iptime",
                    "iptime_5g",
                    "sm_base_17f_5g-1");

    public static String bandToBandString(Context context, int i) {
        return i != 1
                ? i != 2
                        ? i != 8
                                ? context.getResources()
                                        .getString(R.string.wifitrackerlib_wifi_band_unknown)
                                : context.getResources()
                                        .getString(R.string.wifitrackerlib_wifi_band_6_ghz)
                        : context.getResources().getString(R.string.wifitrackerlib_wifi_band_5_ghz)
                : context.getResources().getString(R.string.wifitrackerlib_wifi_band_24_ghz);
    }

    public static String getAppLabel(Context context, String str) {
        try {
            return context.getPackageManager()
                    .getApplicationInfo(str, 0)
                    .loadLabel(context.getPackageManager())
                    .toString();
        } catch (PackageManager.NameNotFoundException unused) {
            return ApnSettings.MVNO_NONE;
        }
    }

    public static String getAutoConnectDescription(Context context, WifiEntry wifiEntry) {
        if (context == null || wifiEntry == null || !wifiEntry.canSetAutoJoinEnabled()) {
            return ApnSettings.MVNO_NONE;
        }
        if (!SemWifiEntryFlags.isWepAllowed(context) && wifiEntry.getSecurity$1() == 1) {
            return context.getString(R.string.wifi_wep_networks_blocked_summary);
        }
        if (SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin == -1) {
            SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin =
                    Settings.Secure.getInt(
                            context.getContentResolver(),
                            "rampart_blocked_unsecure_wifi_autojoin",
                            0);
        }
        return (SemWifiEntryFlags.isBlockedUnSecureWifiAutoJoin == 1
                        && (wifiEntry.getSecurity$1() == 0
                                || wifiEntry.getSecurity$1() == 1
                                || wifiEntry.getSecurity$1() == 4))
                ? context.getString(R.string.wifi_auto_blocker_blocked_summary)
                : wifiEntry.isAutoJoinEnabled()
                        ? ApnSettings.MVNO_NONE
                        : context.getString(R.string.wifi_auto_reconnect_disabled);
    }

    public static String getBandString(Context context, int i) {
        return i == 0
                ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_24_ghz)
                : i == 1
                        ? context.getResources().getString(R.string.wifitrackerlib_wifi_band_5_ghz)
                        : i == 2
                                ? context.getResources()
                                        .getString(R.string.wifitrackerlib_wifi_band_6_ghz)
                                : i == 3
                                        ? context.getResources()
                                                .getString(R.string.wifitrackerlib_wifi_band_60_ghz)
                                        : context.getResources()
                                                .getString(
                                                        R.string.wifitrackerlib_wifi_band_unknown);
    }

    public static ScanResult getBestScanResultByLevel(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return (ScanResult)
                Collections.max(
                        list, Comparator.comparingInt(new Utils$$ExternalSyntheticLambda0()));
    }

    public static String getCarrierNetworkOffloadDescription(
            Context context, WifiEntry wifiEntry, WifiManager wifiManager) {
        WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
        return (context == null
                        || wifiConfiguration == null
                        || !wifiEntry.mSemFlags.isCarrierNetwork
                        || wifiManager.isCarrierNetworkOffloadEnabled(
                                wifiConfiguration.subscriptionId, false))
                ? ApnSettings.MVNO_NONE
                : context.getString(R.string.wifi_auto_reconnect_disabled);
    }

    public static WifiEntry.CertificateInfo getCertificateInfo(
            WifiEnterpriseConfig wifiEnterpriseConfig) {
        if (wifiEnterpriseConfig.isEapMethodServerCertUsed()
                && wifiEnterpriseConfig.hasCaCertificate()) {
            WifiEntry.CertificateInfo certificateInfo = new WifiEntry.CertificateInfo();
            certificateInfo.domain = wifiEnterpriseConfig.getDomainSuffixMatch();
            String[] caCertificateAliases = wifiEnterpriseConfig.getCaCertificateAliases();
            certificateInfo.caCertificateAliases = caCertificateAliases;
            if (caCertificateAliases != null) {
                if (caCertificateAliases.length == 1
                        && caCertificateAliases[0].startsWith("hash://server/sha256/")) {
                    certificateInfo.validationMethod = 3;
                    certificateInfo.caCertificateAliases = null;
                } else {
                    certificateInfo.validationMethod = 1;
                }
                return certificateInfo;
            }
            if (wifiEnterpriseConfig.getCaPath() != null) {
                certificateInfo.validationMethod = 2;
                return certificateInfo;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:

       if (r14 == null) goto L20;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getConnectedDescription(
            android.content.Context r11,
            android.net.wifi.WifiInfo r12,
            android.net.wifi.WifiConfiguration r13,
            android.net.NetworkCapabilities r14) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.Utils.getConnectedDescription(android.content.Context,"
                    + " android.net.wifi.WifiInfo, android.net.wifi.WifiConfiguration,"
                    + " android.net.NetworkCapabilities):java.lang.String");
    }

    public static String getConnectingDescription(Context context, NetworkInfo networkInfo) {
        NetworkInfo.DetailedState detailedState;
        if (context == null
                || networkInfo == null
                || (detailedState = networkInfo.getDetailedState()) == null) {
            return ApnSettings.MVNO_NONE;
        }
        String[] stringArray =
                context.getResources().getStringArray(R.array.wifitrackerlib_wifi_status);
        int ordinal = detailedState.ordinal();
        return ordinal >= stringArray.length ? ApnSettings.MVNO_NONE : stringArray[ordinal];
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0107, code lost:

       if (r8 != 11) goto L146;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getDisconnectedDescription(
            android.content.Context r6,
            android.net.wifi.WifiConfiguration r7,
            com.samsung.android.wifitrackerlib.SemWifiEntryFlags r8) {
        /*
            Method dump skipped, instructions count: 516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.Utils.getDisconnectedDescription(android.content.Context,"
                    + " android.net.wifi.WifiConfiguration,"
                    + " com.samsung.android.wifitrackerlib.SemWifiEntryFlags):java.lang.String");
    }

    public static InetAddress getNetworkPart(InetAddress inetAddress, int i) {
        byte[] address = inetAddress.getAddress();
        if (i < 0 || i > address.length * 8) {
            throw new IllegalArgumentException(
                    "IP address with " + address.length + " bytes has invalid prefix length " + i);
        }
        int i2 = i / 8;
        byte b = (byte) (255 << (8 - (i % 8)));
        if (i2 < address.length) {
            address[i2] = (byte) (b & address[i2]);
        }
        while (true) {
            i2++;
            if (i2 >= address.length) {
                try {
                    return InetAddress.getByAddress(address);
                } catch (UnknownHostException e) {
                    throw new IllegalArgumentException("getNetworkPart error - " + e.toString());
                }
            }
            address[i2] = 0;
        }
    }

    public static String getNetworkSelectionDescription(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration == null) {
            return ApnSettings.MVNO_NONE;
        }
        StringBuilder sb = new StringBuilder();
        WifiConfiguration.NetworkSelectionStatus networkSelectionStatus =
                wifiConfiguration.getNetworkSelectionStatus();
        if (networkSelectionStatus.getNetworkSelectionStatus() != 0) {
            sb.append(" (" + networkSelectionStatus.getNetworkStatusString());
            if (networkSelectionStatus.getDisableTime() > 0) {
                sb.append(
                        " "
                                + DateUtils.formatElapsedTime(
                                        (System.currentTimeMillis()
                                                        - networkSelectionStatus.getDisableTime())
                                                / 1000));
            }
            sb.append(")");
        }
        int maxNetworkSelectionDisableReason =
                WifiConfiguration.NetworkSelectionStatus.getMaxNetworkSelectionDisableReason();
        for (int i = 0; i <= maxNetworkSelectionDisableReason; i++) {
            int disableReasonCounter = networkSelectionStatus.getDisableReasonCounter(i);
            if (disableReasonCounter != 0) {
                sb.append(" ");
                sb.append(
                        WifiConfiguration.NetworkSelectionStatus
                                .getNetworkSelectionDisableReasonString(i));
                sb.append("=");
                sb.append(disableReasonCounter);
            }
        }
        return sb.toString();
    }

    public static String getSecurityString(Context context, List list, boolean z) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.size() == 0) {
            return z
                    ? ApnSettings.MVNO_NONE
                    : context.getString(R.string.wifitrackerlib_wifi_security_none);
        }
        if (arrayList.size() == 1) {
            int intValue = ((Integer) arrayList.get(0)).intValue();
            if (intValue == 9) {
                return z
                        ? context.getString(R.string.wifitrackerlib_wifi_security_short_eap_wpa3)
                        : context.getString(R.string.wifitrackerlib_wifi_security_eap_wpa3);
            }
            switch (intValue) {
                case 0:
                    return z
                            ? ApnSettings.MVNO_NONE
                            : context.getString(R.string.wifitrackerlib_wifi_security_none);
                case 1:
                    return context.getString(R.string.wifitrackerlib_wifi_security_wep);
                case 2:
                    return z
                            ? context.getString(
                                    R.string.wifitrackerlib_wifi_security_short_wpa_wpa2)
                            : context.getString(R.string.wifitrackerlib_wifi_security_wpa_wpa2);
                case 3:
                    return z
                            ? context.getString(
                                    R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2)
                            : context.getString(R.string.wifitrackerlib_wifi_security_eap_wpa_wpa2);
                case 4:
                    return z
                            ? context.getString(R.string.wifitrackerlib_wifi_security_short_sae)
                            : context.getString(R.string.wifitrackerlib_wifi_security_sae);
                case 5:
                    return z
                            ? context.getString(
                                    R.string.wifitrackerlib_wifi_security_short_eap_suiteb)
                            : context.getString(R.string.wifitrackerlib_wifi_security_eap_suiteb);
                case 6:
                    return z
                            ? context.getString(R.string.wifitrackerlib_wifi_security_short_owe)
                            : context.getString(R.string.wifitrackerlib_wifi_security_owe);
            }
        }
        if (arrayList.size() == 2) {
            if (arrayList.contains(0) && arrayList.contains(6)) {
                StringJoiner stringJoiner = new StringJoiner("/");
                stringJoiner.add(context.getString(R.string.wifitrackerlib_wifi_security_none));
                stringJoiner.add(
                        z
                                ? context.getString(R.string.wifitrackerlib_wifi_security_short_owe)
                                : context.getString(R.string.wifitrackerlib_wifi_security_owe));
                return stringJoiner.toString();
            }
            if (arrayList.contains(2) && arrayList.contains(4)) {
                return z
                        ? context.getString(
                                R.string.wifitrackerlib_wifi_security_short_wpa_wpa2_wpa3)
                        : context.getString(R.string.wifitrackerlib_wifi_security_wpa_wpa2_wpa3);
            }
            if (arrayList.contains(3) && arrayList.contains(9)) {
                return z
                        ? context.getString(
                                R.string.wifitrackerlib_wifi_security_short_eap_wpa_wpa2_wpa3)
                        : context.getString(
                                R.string.wifitrackerlib_wifi_security_eap_wpa_wpa2_wpa3);
            }
        }
        return z
                ? ApnSettings.MVNO_NONE
                : context.getString(R.string.wifitrackerlib_wifi_security_none);
    }

    public static List getSecurityTypesFromScanResult(ScanResult scanResult) {
        ArrayList arrayList = new ArrayList();
        for (int i : scanResult.getSecurityTypes()) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public static List getSecurityTypesFromWifiConfiguration(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(14)) {
            return Arrays.asList(8);
        }
        if (wifiConfiguration.allowedKeyManagement.get(13)) {
            return Arrays.asList(7);
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return Arrays.asList(5);
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return Arrays.asList(6);
        }
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return Arrays.asList(4);
        }
        if (wifiConfiguration.allowedKeyManagement.get(4)) {
            return Arrays.asList(2);
        }
        if (wifiConfiguration.allowedKeyManagement.get(2)) {
            return (wifiConfiguration.requirePmf
                            && !wifiConfiguration.allowedPairwiseCiphers.get(1)
                            && wifiConfiguration.allowedProtocols.get(1))
                    ? Arrays.asList(9)
                    : Arrays.asList(3, 9);
        }
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return Arrays.asList(2);
        }
        if (wifiConfiguration.allowedKeyManagement.get(0) && wifiConfiguration.wepKeys != null) {
            int i = 0;
            while (true) {
                String[] strArr = wifiConfiguration.wepKeys;
                if (i >= strArr.length) {
                    break;
                }
                if (strArr[i] != null) {
                    return Arrays.asList(1);
                }
                i++;
            }
        }
        return Arrays.asList(0);
    }

    public static int getSingleSecurityTypeFromMultipleSecurityTypes(List list) {
        if (list.size() == 0) {
            return -1;
        }
        if (list.size() == 1) {
            return ((Integer) list.get(0)).intValue();
        }
        if (list.size() == 2) {
            if (list.contains(0)) {
                return 0;
            }
            if (list.contains(2)) {
                return 2;
            }
            if (list.contains(3)) {
                return 3;
            }
        }
        return ((Integer) list.get(0)).intValue();
    }

    public static String getSpeedString(Context context, WifiInfo wifiInfo, boolean z) {
        if (wifiInfo == null) {
            return ApnSettings.MVNO_NONE;
        }
        int txLinkSpeedMbps = z ? wifiInfo.getTxLinkSpeedMbps() : wifiInfo.getRxLinkSpeedMbps();
        if (txLinkSpeedMbps <= 0) {
            return ApnSettings.MVNO_NONE;
        }
        int i = BuildCompat.$r8$clinit;
        List<MloLink> list =
                (List)
                        wifiInfo.getAssociatedMloLinks().stream()
                                .filter(new Utils$$ExternalSyntheticLambda1(1))
                                .collect(Collectors.toList());
        if (list.size() <= 1) {
            return context.getString(
                    R.string.wifitrackerlib_link_speed_mbps, Integer.valueOf(txLinkSpeedMbps));
        }
        StringJoiner stringJoiner =
                new StringJoiner(context.getString(R.string.wifitrackerlib_multiband_separator));
        for (MloLink mloLink : list) {
            int txLinkSpeedMbps2 = z ? mloLink.getTxLinkSpeedMbps() : mloLink.getRxLinkSpeedMbps();
            if (txLinkSpeedMbps2 > 0) {
                stringJoiner.add(
                        context.getString(
                                R.string.wifitrackerlib_link_speed_on_band,
                                context.getString(
                                        R.string.wifitrackerlib_link_speed_mbps,
                                        Integer.valueOf(txLinkSpeedMbps2)),
                                bandToBandString(context, mloLink.getBand())));
            }
        }
        return stringJoiner.toString();
    }

    public static String getStandardString(Context context, int i) {
        if (i == 1) {
            return context.getString(R.string.wifitrackerlib_wifi_standard_legacy);
        }
        switch (i) {
            case 4:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11n);
            case 5:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ac);
            case 6:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ax);
            case 7:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11ad);
            case 8:
                return context.getString(R.string.wifitrackerlib_wifi_standard_11be);
            default:
                return context.getString(R.string.wifitrackerlib_wifi_standard_unknown);
        }
    }

    public static int getSubIdForConfig(Context context, WifiConfiguration wifiConfiguration) {
        SubscriptionManager subscriptionManager;
        int i = -1;
        if (wifiConfiguration.carrierId == -1
                || (subscriptionManager =
                                (SubscriptionManager)
                                        context.getSystemService("telephony_subscription_service"))
                        == null) {
            return -1;
        }
        List<SubscriptionInfo> activeSubscriptionInfoList =
                subscriptionManager.getActiveSubscriptionInfoList();
        if (activeSubscriptionInfoList != null && !activeSubscriptionInfoList.isEmpty()) {
            int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
            for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                if (subscriptionInfo.getCarrierId() == wifiConfiguration.carrierId
                        && (i = subscriptionInfo.getSubscriptionId())
                                == defaultDataSubscriptionId) {
                    break;
                }
            }
        }
        return i;
    }

    public static String getVerboseLoggingDescription(
            WifiEntry wifiEntry, SemWifiEntryFlags semWifiEntryFlags) {
        String stringJoiner;
        if (!BaseWifiTracker.sVerboseLogging || wifiEntry == null) {
            return ApnSettings.MVNO_NONE;
        }
        StringJoiner stringJoiner2 = new StringJoiner(" ");
        StringBuilder sb = new StringBuilder();
        int i = semWifiEntryFlags.wifiStandard;
        if (i != 0) {
            sb.append(i);
            if (semWifiEntryFlags.has6EStandard) {
                sb.append(ImsProfile.TIMER_NAME_E);
            }
            sb.append(ImsProfile.TIMER_NAME_G);
        }
        if (semWifiEntryFlags.staCount >= 0) {
            sb.append(" STAs:");
            sb.append(semWifiEntryFlags.staCount);
        }
        if (semWifiEntryFlags.passpointConfiguration != null) {
            sb.append(" hs20");
        }
        if (semWifiEntryFlags.isSamsungMobileHotspot) {
            sb.append(" sec-mhs");
        }
        if (semWifiEntryFlags.isCarrierNetwork) {
            sb.append(" carrier");
        }
        if (semWifiEntryFlags.isOpenRoamingNetwork) {
            sb.append(" oauth");
        }
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(sb2)) {
            stringJoiner2.add(sb2);
        }
        synchronized (wifiEntry) {
            try {
                StringJoiner stringJoiner3 = new StringJoiner(" ");
                if (wifiEntry.getConnectedState() == 2 && wifiEntry.mWifiInfo != null) {
                    stringJoiner3.add("f = " + wifiEntry.mWifiInfo.getFrequency());
                    String bssid = wifiEntry.mWifiInfo.getBSSID();
                    if (bssid != null) {
                        stringJoiner3.add(bssid);
                    }
                    stringJoiner3.add("standard = " + wifiEntry.getStandardString());
                    stringJoiner3.add("rssi = " + wifiEntry.mWifiInfo.getRssi());
                    stringJoiner3.add("score = " + wifiEntry.mWifiInfo.getScore());
                    stringJoiner3.add(
                            String.format(
                                    " tx=%.1f,",
                                    Double.valueOf(
                                            wifiEntry.mWifiInfo
                                                    .getSuccessfulTxPacketsPerSecond())));
                    stringJoiner3.add(
                            String.format(
                                    "%.1f,",
                                    Double.valueOf(
                                            wifiEntry.mWifiInfo.getRetriedTxPacketsPerSecond())));
                    stringJoiner3.add(
                            String.format(
                                    "%.1f ",
                                    Double.valueOf(
                                            wifiEntry.mWifiInfo.getLostTxPacketsPerSecond())));
                    stringJoiner3.add(
                            String.format(
                                    "rx=%.1f",
                                    Double.valueOf(
                                            wifiEntry.mWifiInfo
                                                    .getSuccessfulRxPacketsPerSecond())));
                    int i2 = BuildCompat.$r8$clinit;
                    if (wifiEntry.mWifiInfo.getApMldMacAddress() != null) {
                        stringJoiner3.add("mldMac = " + wifiEntry.mWifiInfo.getApMldMacAddress());
                        stringJoiner3.add("linkId = " + wifiEntry.mWifiInfo.getApMloLinkId());
                        stringJoiner3.add(
                                "affLinks = "
                                        + Arrays.toString(
                                                wifiEntry
                                                        .mWifiInfo
                                                        .getAffiliatedMloLinks()
                                                        .toArray()));
                    }
                }
                stringJoiner = stringJoiner3.toString();
            } catch (Throwable th) {
                throw th;
            }
        }
        if (!TextUtils.isEmpty(stringJoiner)) {
            stringJoiner2.add(stringJoiner);
        }
        StringBuilder sb3 = new StringBuilder();
        if (wifiEntry.getConnectedState() == 2) {
            sb3.append("hasInternet:");
            sb3.append(wifiEntry.hasInternetAccess());
            sb3.append(", isDefaultNetwork:");
            sb3.append(wifiEntry.isDefaultNetwork());
            sb3.append(", isLowQuality:");
            sb3.append(wifiEntry.isLowQuality());
        }
        String sb4 = sb3.toString();
        if (!TextUtils.isEmpty(sb4)) {
            stringJoiner2.add(sb4);
        }
        String scanResultDescription = wifiEntry.getScanResultDescription();
        if (!TextUtils.isEmpty(scanResultDescription)) {
            stringJoiner2.add(scanResultDescription);
        }
        String networkSelectionDescription = wifiEntry.getNetworkSelectionDescription();
        if (!TextUtils.isEmpty(networkSelectionDescription)) {
            stringJoiner2.add(networkSelectionDescription);
        }
        return stringJoiner2.toString();
    }

    public static String getWarningDescription(Context context, WifiEntry wifiEntry) {
        if (context != null && wifiEntry != null) {
            if (wifiEntry.getSecurity$1() == 0) {
                return context.getString(R.string.wifi_open_warning_summary);
            }
            if (wifiEntry.getSecurity$1() == 1) {
                return context.getString(R.string.wifi_wep_warning_summary);
            }
            if (defaultSsidList.contains(wifiEntry.getTitle().toLowerCase())) {
                return context.getString(R.string.wifi_default_ssid_warning_summary);
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    public static WifiInfo getWifiInfo(NetworkCapabilities networkCapabilities) {
        TransportInfo transportInfo = networkCapabilities.getTransportInfo();
        if (transportInfo instanceof WifiInfo) {
            return (WifiInfo) transportInfo;
        }
        VcnTransportInfo transportInfo2 = networkCapabilities.getTransportInfo();
        if (transportInfo2 instanceof VcnTransportInfo) {
            return transportInfo2.getWifiInfo();
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0046, code lost:

       if (((android.content.ComponentName) r3.second).getPackageName().equals(r6) != false) goto L36;
    */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0071 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isDeviceOrProfileOwner(
            android.content.Context r4, int r5, java.lang.String r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L4
            goto L49
        L4:
            java.lang.Class<android.app.admin.DevicePolicyManager> r1 = android.app.admin.DevicePolicyManager.class
            java.lang.Object r1 = r4.getSystemService(r1)
            android.app.admin.DevicePolicyManager r1 = (android.app.admin.DevicePolicyManager) r1
            if (r1 != 0) goto L10
        Le:
            r3 = r0
            goto L29
        L10:
            android.os.UserHandle r2 = r1.getDeviceOwnerUser()     // Catch: java.lang.Exception -> L73
            android.content.ComponentName r1 = r1.getDeviceOwnerComponentOnAnyUser()     // Catch: java.lang.Exception -> L73
            if (r2 == 0) goto Le
            if (r1 != 0) goto L1d
            goto Le
        L1d:
            java.lang.String r3 = r1.getPackageName()
            if (r3 != 0) goto L24
            goto Le
        L24:
            android.util.Pair r3 = new android.util.Pair
            r3.<init>(r2, r1)
        L29:
            if (r3 != 0) goto L2c
            goto L49
        L2c:
            java.lang.Object r1 = r3.first
            android.os.UserHandle r1 = (android.os.UserHandle) r1
            android.os.UserHandle r2 = android.os.UserHandle.getUserHandleForUid(r5)
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L49
            java.lang.Object r1 = r3.second
            android.content.ComponentName r1 = (android.content.ComponentName) r1
            java.lang.String r1 = r1.getPackageName()
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L49
            goto L71
        L49:
            r1 = 0
            if (r6 != 0) goto L4e
        L4c:
            r4 = r1
            goto L6f
        L4e:
            java.lang.String r2 = r4.getPackageName()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.os.UserHandle r5 = android.os.UserHandle.getUserHandleForUid(r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            android.content.Context r4 = r4.createPackageContextAsUser(r2, r1, r5)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L5b
            goto L5c
        L5b:
            r4 = r0
        L5c:
            if (r4 != 0) goto L5f
            goto L68
        L5f:
            java.lang.Class<android.app.admin.DevicePolicyManager> r5 = android.app.admin.DevicePolicyManager.class
            java.lang.Object r4 = r4.getSystemService(r5)
            r0 = r4
            android.app.admin.DevicePolicyManager r0 = (android.app.admin.DevicePolicyManager) r0
        L68:
            if (r0 != 0) goto L6b
            goto L4c
        L6b:
            boolean r4 = r0.isProfileOwnerApp(r6)
        L6f:
            if (r4 == 0) goto L72
        L71:
            r1 = 1
        L72:
            return r1
        L73:
            r4 = move-exception
            java.lang.RuntimeException r5 = new java.lang.RuntimeException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r0 = "getDeviceOwner error - "
            r6.<init>(r0)
            java.lang.String r4 = r4.toString()
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.<init>(r4)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.wifitrackerlib.Utils.isDeviceOrProfileOwner(android.content.Context,"
                    + " int, java.lang.String):boolean");
    }

    public static boolean shouldSetHiddenSsid(String str, WifiManager wifiManager) {
        List<WifiConfiguration> configuredNetworks;
        if (TextUtils.isEmpty(str)
                || wifiManager == null
                || (configuredNetworks = wifiManager.getConfiguredNetworks()) == null) {
            return false;
        }
        for (WifiConfiguration wifiConfiguration : configuredNetworks) {
            if (wifiConfiguration.hiddenSSID && str.equals(wifiConfiguration.SSID)) {
                return true;
            }
        }
        return false;
    }

    public static String wifiInfoToBandString(final Context context, WifiInfo wifiInfo) {
        int i = 0;
        int i2 = BuildCompat.$r8$clinit;
        final StringJoiner stringJoiner =
                new StringJoiner(
                        context.getResources()
                                .getString(R.string.wifitrackerlib_multiband_separator));
        wifiInfo.getAssociatedMloLinks().stream()
                .filter(new Utils$$ExternalSyntheticLambda1(0))
                .map(new Utils$$ExternalSyntheticLambda2())
                .distinct()
                .sorted()
                .forEach(
                        new Consumer() { // from class:
                                         // com.android.wifitrackerlib.Utils$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                stringJoiner.add(
                                        Utils.bandToBandString(
                                                context, ((Integer) obj).intValue()));
                            }
                        });
        if (stringJoiner.length() != 0) {
            return stringJoiner.toString();
        }
        int frequency = wifiInfo.getFrequency();
        if (frequency >= 2400 && frequency < 2500) {
            i = 1;
        } else if (frequency >= 4900 && frequency < 5900) {
            i = 2;
        } else if (frequency >= 5925 && frequency < 7125) {
            i = 8;
        }
        return bandToBandString(context, i);
    }
}
