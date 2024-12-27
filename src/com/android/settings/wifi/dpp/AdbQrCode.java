package com.android.settings.wifi.dpp;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbQrCode {
    public final WifiNetworkConfig mAdbConfig;
    public final String mPublicKey;
    public final String mScheme;

    public AdbQrCode(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Empty QR code");
        }
        if (str.startsWith("DPP:")) {
            this.mScheme = "DPP";
            List keyValueList = getKeyValueList(str, "DPP:");
            String valueOrNull = getValueOrNull("K:", keyValueList);
            if (TextUtils.isEmpty(valueOrNull)) {
                throw new IllegalArgumentException("Invalid format");
            }
            this.mPublicKey = valueOrNull;
            getValueOrNull("I:", keyValueList);
        } else {
            if (!str.startsWith("WIFI:")) {
                throw new IllegalArgumentException("Invalid scheme");
            }
            this.mScheme = "WIFI";
            List keyValueList2 = getKeyValueList(str, "WIFI:");
            WifiNetworkConfig validConfigOrNull =
                    WifiNetworkConfig.getValidConfigOrNull(
                            removeBackSlash(getValueOrNull("T:", keyValueList2)),
                            removeBackSlash(getValueOrNull("S:", keyValueList2)),
                            removeBackSlash(getValueOrNull("P:", keyValueList2)),
                            "true".equalsIgnoreCase(getValueOrNull("H:", keyValueList2)),
                            -1,
                            false);
            this.mAdbConfig = validConfigOrNull;
            if (validConfigOrNull == null) {
                throw new IllegalArgumentException("Invalid format");
            }
        }
        if (!"WIFI".equals(this.mScheme)) {
            throw new IllegalArgumentException("DPP format not supported for ADB QR code");
        }
        WifiNetworkConfig wifiNetworkConfig = this.mAdbConfig;
        WifiNetworkConfig wifiNetworkConfig2 =
                wifiNetworkConfig == null ? null : new WifiNetworkConfig(wifiNetworkConfig);
        this.mAdbConfig = wifiNetworkConfig2;
        if (!"ADB".equals(wifiNetworkConfig2.mSecurity)) {
            throw new IllegalArgumentException("Invalid security type");
        }
        if (TextUtils.isEmpty(this.mAdbConfig.mSsid)) {
            throw new IllegalArgumentException("Empty service name");
        }
        if (TextUtils.isEmpty(this.mAdbConfig.mPreSharedKey)) {
            throw new IllegalArgumentException("Empty password");
        }
    }

    public static List getKeyValueList(String str, String str2) {
        return Arrays.asList(str.substring(str2.length()).split("(?<!\\\\)" + Pattern.quote(";")));
    }

    public static String getValueOrNull(String str, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String stripLeading = ((String) it.next()).stripLeading();
            if (stripLeading.startsWith(str)) {
                return stripLeading.substring(str.length());
            }
        }
        return null;
    }

    public String getPublicKey() {
        return this.mPublicKey;
    }

    public String removeBackSlash(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (char c : str.toCharArray()) {
            if (c != '\\') {
                sb.append(c);
            } else if (z) {
                sb.append(c);
            } else {
                z = true;
            }
            z = false;
        }
        return sb.toString();
    }
}
