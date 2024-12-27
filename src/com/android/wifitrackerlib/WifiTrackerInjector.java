package com.android.wifitrackerlib;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.ArraySet;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.inputmethod.TouchPadGestureSettingsController;
import com.samsung.android.wifi.SemWifiApCust;
import com.sec.ims.settings.ImsProfile;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTrackerInjector {
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final boolean mIsDemoMode;
    public final Set mNoAttributionAnnotationPackages = new ArraySet();
    public final UserManager mUserManager;
    public final WifiManager mWifiManager;

    public WifiTrackerInjector(Context context) {
        this.mContext = context;
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mIsDemoMode = UserManager.isDeviceInDemoMode(context);
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
        this.mDevicePolicyManager =
                (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        for (String str :
                context.getString(R.string.wifitrackerlib_no_attribution_annotation_packages)
                        .split(",")) {
            ((ArraySet) this.mNoAttributionAnnotationPackages).add(str);
        }
    }

    public final boolean isSharedConnectivityFeatureEnabled() {
        int i;
        int i2 = SystemProperties.getInt(TouchPadGestureSettingsController.FIRST_API_LEVEL, -1);
        boolean z =
                !"CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code")) && i2 >= 35;
        if (SemWifiApCust.DBG
                && (i =
                                Settings.Secure.getInt(
                                        this.mContext.getContentResolver(),
                                        "vendor.wifiap.ih.enable",
                                        -1))
                        >= 0) {
            z = i == 1;
        }
        StringBuilder m =
                RowView$$ExternalSyntheticOutline0.m(
                        "Instant Hotspot isDeviceConfigEnabled ", " rollout:", z);
        m.append(
                DeviceConfig.getBoolean(ImsProfile.PDN_WIFI, "shared_connectivity_enabled", false));
        m.append(" ");
        TooltipPopup$$ExternalSyntheticOutline0.m(m, i2, "WifiTrackerInjector");
        return z;
    }
}
