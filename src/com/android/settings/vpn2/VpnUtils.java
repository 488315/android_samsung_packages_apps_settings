package com.android.settings.vpn2;

import android.content.Context;
import android.net.VpnManager;
import android.provider.Settings;
import android.security.LegacyVpnProfileStore;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class VpnUtils {
    public static void clearLockdownVpn(Context context) {
        LegacyVpnProfileStore.remove("LOCKDOWN_VPN");
        getVpnManager(context).updateLockdownVpn();
    }

    public static boolean disconnectLegacyVpn(Context context) {
        int userId = context.getUserId();
        if (getVpnManager(context).getLegacyVpnInfo(userId) == null) {
            return false;
        }
        clearLockdownVpn(context);
        getVpnManager(context).prepareVpn(null, "[Legacy VPN]", userId);
        return true;
    }

    public static String getLockdownVpn() {
        byte[] bArr = LegacyVpnProfileStore.get("LOCKDOWN_VPN");
        if (bArr == null) {
            return null;
        }
        return new String(bArr);
    }

    public static VpnManager getVpnManager(Context context) {
        return (VpnManager) context.getSystemService(VpnManager.class);
    }

    public static boolean isAnyLockdownActive(Context context) {
        int userId = context.getUserId();
        if (getLockdownVpn() != null) {
            return true;
        }
        return (getVpnManager(context).getAlwaysOnVpnPackageForUser(userId) == null
                        || Settings.Secure.getIntForUser(
                                        context.getContentResolver(),
                                        "always_on_vpn_lockdown",
                                        0,
                                        userId)
                                == 0)
                ? false
                : true;
    }
}
