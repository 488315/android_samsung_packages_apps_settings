package com.samsung.android.settings.wifi.mobileap.datamodels;

import android.content.Context;
import android.net.MacAddress;
import android.net.wifi.SoftApConfiguration;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApConfiguration {
    public int mBroadcastChannel;
    public boolean mIsHiddenNetworkEnabled;
    public int mMacRandomizationSettingsType;
    public String mPassphrase;
    public int mSecurityType;
    public int[] mSoftApBandArray;
    public String mSsid;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration$1, reason: invalid class name */
    public final class AnonymousClass1 extends SparseIntArray {}

    public WifiApConfiguration(Context context) {
        SemWifiManager semWifiManager = WifiApFrameworkUtils.getSemWifiManager(context);
        update(context, semWifiManager.getSoftApConfiguration(), semWifiManager.getSoftApBands());
    }

    public final SoftApConfiguration getSoftApConfiguration(Context context) {
        boolean z;
        SoftApConfiguration softApConfiguration =
                WifiApFrameworkUtils.getSemWifiManager(context).getSoftApConfiguration();
        SoftApConfiguration.Builder builder = new SoftApConfiguration.Builder(softApConfiguration);
        String passphrase = softApConfiguration.getPassphrase();
        String str = this.mSsid;
        if (str == null || !str.equals(passphrase)) {
            Log.d("WifiApConfiguration", "isSsidChanged() - SSID changed.");
            z = true;
        } else {
            Log.i("WifiApConfiguration", "isSsidChanged() - SSID not changed.");
            z = false;
        }
        if (z) {
            if (TextUtils.isEmpty(this.mSsid)) {
                Log.i("WifiApConfiguration", "Network name is empty.");
            } else if (this.mSsid.getBytes().length < 1 || this.mSsid.getBytes().length > 32) {
                Log.i("WifiApConfiguration", "Network name length error.");
            } else if (this.mSsid.equals("\tUSER#DEFINED#PWD#\n")) {
                Log.e("WifiApConfiguration", "Network name error.");
            } else {
                builder.setSsid(this.mSsid);
            }
        }
        boolean z2 = this.mSecurityType == 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "Is Open Security Type: ", "WifiApConfiguration", z2);
        if (z2) {
            builder.setPassphrase((String) null, 0);
        } else {
            boolean z3 = this.mSecurityType == 5;
            AbsAdapter$$ExternalSyntheticOutline0.m(
                    "Is Open Enhanced Security Type: ", "WifiApConfiguration", z3);
            if (z3) {
                builder.setPassphrase((String) null, 5);
            } else if (!TextUtils.isEmpty(this.mPassphrase)
                    && this.mPassphrase.length() >= 8
                    && this.mPassphrase.getBytes().length >= 8
                    && this.mPassphrase.getBytes().length <= 63
                    && !this.mPassphrase.equals("\tUSER#DEFINED#PWD#\n")) {
                builder.setPassphrase(this.mPassphrase, this.mSecurityType);
            }
        }
        int[] iArr = this.mSoftApBandArray;
        if (iArr.length == 1 && (iArr[0] & 4) != 0) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1();
            anonymousClass1.put(4, 0);
            builder.setChannels(anonymousClass1);
        } else if (!WifiApFrameworkUtils.getSemWifiManager(context).supportWifiAp5GBasedOnCountry()
                || this.mSoftApBandArray.length <= 1) {
            int[] iArr2 = this.mSoftApBandArray;
            if (iArr2.length == 1 && (iArr2[0] & 2) != 0) {
                AnonymousClass1 anonymousClass12 = new AnonymousClass1();
                anonymousClass12.put(2, 149);
                builder.setChannels(anonymousClass12);
            } else if (iArr2.length == 1 && (iArr2[0] & 1) != 0) {
                if (this.mBroadcastChannel != WifiApSoftApUtils.getBroadcastChannel(context)) {
                    Log.d(
                            "WifiApConfiguration",
                            "isBroadcastChannelChanged() - Broadcast Channel changed.");
                    AnonymousClass1 anonymousClass13 = new AnonymousClass1();
                    anonymousClass13.put(1, this.mBroadcastChannel);
                    builder.setChannels(anonymousClass13);
                } else {
                    Log.i(
                            "WifiApConfiguration",
                            "isBroadcastChannelChanged() - Broadcast Channel not changed.");
                }
            }
        } else {
            AnonymousClass1 anonymousClass14 = new AnonymousClass1();
            anonymousClass14.put(1, 0);
            anonymousClass14.put(2, 149);
            builder.setChannels(anonymousClass14);
        }
        if (this.mIsHiddenNetworkEnabled != softApConfiguration.isHiddenSsid()) {
            Log.d("WifiApConfiguration", "isHiddenNetworkChanged() - Hidden Network changed.");
        } else {
            Log.i("WifiApConfiguration", "isHiddenNetworkChanged() - Hidden Network not changed.");
        }
        builder.setHiddenSsid(this.mIsHiddenNetworkEnabled);
        if (this.mMacRandomizationSettingsType
                != softApConfiguration.getMacRandomizationSetting()) {
            Log.d(
                    "WifiApConfiguration",
                    "isMacRandomizationSettingTypeChanged() - Mac Randomization Setting Type"
                        + " changed.");
            if (this.mMacRandomizationSettingsType == 0) {
                builder.setMacRandomizationSetting(0);
            } else {
                builder.setMacRandomizationSetting(1);
                builder.setBssid((MacAddress) null);
            }
        } else {
            Log.i(
                    "WifiApConfiguration",
                    "isMacRandomizationSettingTypeChanged() - Mac Randomization Setting Type not"
                        + " changed.");
        }
        return builder.build();
    }

    public final boolean isDefaultPassphraseSet() {
        String str = this.mPassphrase;
        if (str == null || this.mSecurityType == 0 || !str.equals("\tUSER#DEFINED#PWD#\n")) {
            return false;
        }
        Log.i("WifiApConfiguration", "Default password is set");
        return true;
    }

    public final void update(Context context, SoftApConfiguration softApConfiguration, int[] iArr) {
        this.mSoftApBandArray = iArr;
        this.mSsid = softApConfiguration.getSsid();
        this.mPassphrase = softApConfiguration.getPassphrase();
        this.mSecurityType = softApConfiguration.getSecurityType();
        this.mMacRandomizationSettingsType = softApConfiguration.getMacRandomizationSetting();
        this.mIsHiddenNetworkEnabled = softApConfiguration.isHiddenSsid();
        this.mBroadcastChannel = WifiApSoftApUtils.getBroadcastChannel(context);
        WifiApSoftApUtils.isSupportWifi6StandardEnabled(context);
        Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_pmf_checked", 0);
        Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_powersave_mode_checked", 0);
        WifiApSoftApUtils.isWifiSharingEnabled(context);
        WifiApFrameworkUtils.getWifiApMaxClientDefault();
        Log.i("WifiApConfiguration", "wifiApConfiguration updated");
    }
}
