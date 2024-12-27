package com.samsung.android.settings.wifi.mobileap.otp;

import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiApOtpSettingsBottomView {
    public BottomNavigationView mBottomNavigationView;
    public RelativeLayout mButtonBar;
    public Context mContext;
    public SemWifiManager mSemWifiManager;

    public final void update() {
        Log.i("WifiApOtpSettingsBottomView", "update: ");
        if (WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext)
                && WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)) {
            this.mButtonBar.setVisibility(0);
        } else {
            this.mButtonBar.setVisibility(8);
        }
    }
}
