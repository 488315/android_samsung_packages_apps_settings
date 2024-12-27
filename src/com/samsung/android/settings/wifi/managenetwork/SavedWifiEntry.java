package com.samsung.android.settings.wifi.managenetwork;

import android.content.Context;

import com.android.settings.wifi.WifiUtils;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SavedWifiEntry {
    public final Context mContext;
    public boolean mIsChecked;
    public Boolean mIsRemovable;
    public final WifiEntry mWifiEntry;

    public SavedWifiEntry(Context context, WifiEntry wifiEntry) {
        this.mContext = context;
        this.mWifiEntry = wifiEntry;
    }

    public final Boolean isRemovableNetwork() {
        Boolean bool = this.mIsRemovable;
        if (bool != null) {
            return bool;
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        Boolean valueOf =
                Boolean.valueOf(
                        (wifiEntry == null
                                        || !wifiEntry.canForget()
                                        || WifiUtils.isBlockedByEnterprise(
                                                this.mContext, wifiEntry.getSsid())
                                        || !WifiDevicePolicyManager.canModifyNetwork(
                                                this.mContext, wifiEntry.getWifiConfiguration())
                                        || wifiEntry.semIsEphemeral()
                                        || wifiEntry.mSemFlags.isLocked
                                        || Rune.isShopDemo(this.mContext))
                                ? false
                                : true);
        this.mIsRemovable = valueOf;
        return valueOf;
    }
}
