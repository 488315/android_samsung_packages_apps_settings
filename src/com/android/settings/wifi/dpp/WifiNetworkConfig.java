package com.android.settings.wifi.dpp;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiNetworkConfig {
    public final boolean mHiddenSsid;
    public final boolean mIsHotspot;
    public final int mNetworkId;
    public final String mPreSharedKey;
    public final String mSecurity;
    public final String mSsid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Retriever {}

    public WifiNetworkConfig(String str, String str2, String str3, boolean z, int i, boolean z2) {
        this.mSecurity = str;
        this.mSsid = str2;
        this.mPreSharedKey = str3;
        this.mHiddenSsid = z;
        this.mNetworkId = i;
        this.mIsHotspot = z2;
    }

    public static String addQuotationIfNeeded(String str) {
        return TextUtils.isEmpty(str)
                ? ApnSettings.MVNO_NONE
                : (str.length() >= 2 && str.startsWith("\"") && str.endsWith("\""))
                        ? str
                        : ComposerKt$$ExternalSyntheticOutline0.m("\"", str, "\"");
    }

    public static String escapeSpecialCharacters(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == ',' || charAt == ';' || charAt == ':') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static WifiNetworkConfig getValidConfigOrNull(Intent intent) {
        return getValidConfigOrNull(
                intent.getStringExtra("security"),
                intent.getStringExtra("ssid"),
                intent.getStringExtra("preSharedKey"),
                intent.getBooleanExtra("hiddenSsid", false),
                intent.getIntExtra("networkId", -1),
                intent.getBooleanExtra("isHotspot", false));
    }

    public static boolean isValidConfig(WifiNetworkConfig wifiNetworkConfig) {
        if (wifiNetworkConfig == null) {
            return false;
        }
        return isValidConfig(
                wifiNetworkConfig.mSecurity,
                wifiNetworkConfig.mSsid,
                wifiNetworkConfig.mPreSharedKey,
                wifiNetworkConfig.mHiddenSsid);
    }

    public final WifiConfiguration getBasicWifiConfiguration() {
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = addQuotationIfNeeded(this.mSsid);
        wifiConfiguration.hiddenSSID = this.mHiddenSsid;
        wifiConfiguration.networkId = this.mNetworkId;
        return wifiConfiguration;
    }

    public final String getQrCode() {
        StringBuilder sb = new StringBuilder("WIFI:S:");
        sb.append(escapeSpecialCharacters(this.mSsid));
        sb.append(";T:");
        String str = this.mSecurity;
        boolean isEmpty = TextUtils.isEmpty(str);
        String str2 = ApnSettings.MVNO_NONE;
        if (isEmpty) {
            str = ApnSettings.MVNO_NONE;
        }
        sb.append(str);
        sb.append(";P:");
        String str3 = this.mPreSharedKey;
        if (!TextUtils.isEmpty(str3)) {
            str2 = escapeSpecialCharacters(str3);
        }
        sb.append(str2);
        sb.append(";H:");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.mHiddenSsid, ";;");
    }

    public static boolean isValidConfig(String str, String str2, String str3, boolean z) {
        if (TextUtils.isEmpty(str) || "nopass".equals(str) || !TextUtils.isEmpty(str3)) {
            return z || !TextUtils.isEmpty(str2);
        }
        return false;
    }

    public WifiNetworkConfig(WifiNetworkConfig wifiNetworkConfig) {
        this.mSecurity = wifiNetworkConfig.mSecurity;
        this.mSsid = wifiNetworkConfig.mSsid;
        this.mPreSharedKey = wifiNetworkConfig.mPreSharedKey;
        this.mHiddenSsid = wifiNetworkConfig.mHiddenSsid;
        this.mNetworkId = wifiNetworkConfig.mNetworkId;
        this.mIsHotspot = wifiNetworkConfig.mIsHotspot;
    }

    public static WifiNetworkConfig getValidConfigOrNull(
            String str, String str2, String str3, boolean z, int i, boolean z2) {
        if (isValidConfig(str, str2, str3, z)) {
            return new WifiNetworkConfig(str, str2, str3, z, i, z2);
        }
        return null;
    }
}
