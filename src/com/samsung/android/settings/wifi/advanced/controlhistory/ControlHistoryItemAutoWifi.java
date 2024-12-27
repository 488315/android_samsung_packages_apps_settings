package com.samsung.android.settings.wifi.advanced.controlhistory;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.android.settings.R;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ControlHistoryItemAutoWifi extends AbstractControlHistoryItem {
    public final PackageManager mPackageManager;

    public ControlHistoryItemAutoWifi(Context context, String str, int i, long j) {
        super(context, str, i, j);
        this.mPackageManager = context.getPackageManager();
    }

    @Override // com.samsung.android.settings.wifi.advanced.controlhistory.AbstractControlHistoryItem
    public final Drawable getIcon() {
        try {
            return this.mPackageManager.getApplicationIcon(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @Override // com.samsung.android.settings.wifi.advanced.controlhistory.AbstractControlHistoryItem
    public final String getTitle() {
        return this.mContext.getString(R.string.wifi_autowifi_title);
    }
}
