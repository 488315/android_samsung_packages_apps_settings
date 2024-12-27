package com.android.settings.wifi.slice;

import android.content.Context;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.wifitrackerlib.HotspotNetworkEntry;
import com.android.wifitrackerlib.WifiEntry;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiSliceItem {
    public static final int[] WIFI_CONNECTION_STRENGTH = {
        R.string.accessibility_no_wifi,
        R.string.accessibility_wifi_one_bar,
        R.string.accessibility_wifi_two_bars,
        R.string.accessibility_wifi_three_bars,
        R.string.accessibility_wifi_signal_full
    };
    public final int mConnectedState;
    public final Context mContext;
    public final int mInstantHotspotDeviceType;
    public final boolean mIsInstantHotspotNetwork;
    public final boolean mIsWifi6ENetwork;
    public final boolean mIsWifi6Network;
    public final boolean mIsWifi7Network;
    public final String mKey;
    public final int mLevel;
    public final int mSecurity;
    public final boolean mShouldEditBeforeConnect;
    public final boolean mShouldShowXLevelIcon;
    public final String mSummary;
    public final String mTitle;

    public WifiSliceItem(Context context, WifiEntry wifiEntry) {
        this.mContext = context;
        this.mKey = wifiEntry.getKey();
        this.mTitle = wifiEntry.getTitle();
        this.mSecurity = wifiEntry.getSecurity$1();
        this.mConnectedState = wifiEntry.getConnectedState();
        this.mLevel = wifiEntry.getLevel();
        this.mShouldShowXLevelIcon = wifiEntry.shouldShowXLevelIcon();
        this.mShouldEditBeforeConnect = wifiEntry.shouldEditBeforeConnect();
        wifiEntry.hasInternetAccess();
        this.mSummary = wifiEntry.getSummary(false);
        boolean z = wifiEntry instanceof HotspotNetworkEntry;
        this.mIsInstantHotspotNetwork = z;
        if (z) {
            this.mInstantHotspotDeviceType = ((HotspotNetworkEntry) wifiEntry).getDeviceType();
        }
        this.mIsWifi6ENetwork = wifiEntry.semIsWifi6ENetwork();
        this.mIsWifi6Network = wifiEntry.semIsWifi6Network();
        this.mIsWifi7Network = wifiEntry.semIsWifi7Network();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof WifiSliceItem)) {
            return false;
        }
        WifiSliceItem wifiSliceItem = (WifiSliceItem) obj;
        return TextUtils.equals(this.mKey, wifiSliceItem.mKey)
                && this.mConnectedState == wifiSliceItem.mConnectedState
                && this.mLevel == wifiSliceItem.mLevel
                && this.mShouldShowXLevelIcon == wifiSliceItem.mShouldShowXLevelIcon
                && TextUtils.equals(this.mSummary, wifiSliceItem.mSummary)
                && this.mIsInstantHotspotNetwork == wifiSliceItem.mIsInstantHotspotNetwork
                && this.mInstantHotspotDeviceType == wifiSliceItem.mInstantHotspotDeviceType;
    }
}
