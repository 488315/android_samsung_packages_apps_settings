package com.samsung.android.settings.vpn.bixby;

import android.content.Context;
import android.net.VpnManager;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.LegacyVpnProfileStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.net.LegacyVpnInfo;
import com.android.internal.net.VpnProfile;
import com.android.settings.R;
import com.android.settings.vpn2.VpnUtils;

import java.util.Comparator;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class VpnHelper {
    public final Context mContext;
    public final VpnManager mService;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.vpn.bixby.VpnHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            VpnProfile vpnProfile = (VpnProfile) obj2;
            String str = ((VpnProfile) obj).name;
            if (str == null) {
                return vpnProfile.name == null ? 0 : -1;
            }
            String str2 = vpnProfile.name;
            if (str2 == null) {
                return 1;
            }
            return str.compareTo(str2);
        }
    }

    public VpnHelper(Context context) {
        this.mContext = context;
        this.mService = (VpnManager) context.getSystemService(VpnManager.class);
    }

    public static VpnProfile findVpnProfile(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] list = LegacyVpnProfileStore.list("VPN_");
        int length = list.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str2 = list[i];
            if (Objects.equals(str2, str)) {
                VpnProfile decode =
                        VpnProfile.decode(str2, LegacyVpnProfileStore.get("VPN_" + str2));
                if (decode != null) {
                    return decode;
                }
            } else {
                i++;
            }
        }
        return null;
    }

    public final boolean connect(VpnProfile vpnProfile, boolean z) {
        disconnect(vpnProfile);
        if (z) {
            if (vpnProfile.isValidLockdownProfile()) {
                this.mService.setAlwaysOnVpnPackageForUser(
                        UserHandle.myUserId(), null, false, null);
                Context context = this.mContext;
                LegacyVpnProfileStore.put("LOCKDOWN_VPN", vpnProfile.key.getBytes());
                VpnUtils.getVpnManager(context).updateLockdownVpn();
            } else {
                Toast.makeText(this.mContext, R.string.vpn_lockdown_config_error, 1).show();
            }
        } else if (vpnProfile.key.equals(VpnUtils.getLockdownVpn())) {
            VpnUtils.clearLockdownVpn(this.mContext);
        }
        if (vpnProfile.key.equals(VpnUtils.getLockdownVpn())) {
            return true;
        }
        this.mService.setAlwaysOnVpnPackageForUser(UserHandle.myUserId(), null, false, null);
        VpnUtils.clearLockdownVpn(this.mContext);
        try {
            this.mService.startLegacyVpn(vpnProfile);
            return true;
        } catch (IllegalStateException e) {
            Log.e("VpnHelper", "Failed to connect", e);
            return false;
        }
    }

    public final boolean disconnect(VpnProfile vpnProfile) {
        try {
            LegacyVpnInfo legacyVpnInfo = this.mService.getLegacyVpnInfo(UserHandle.myUserId());
            if (legacyVpnInfo != null
                    && vpnProfile.key.equals(legacyVpnInfo.key)
                    && legacyVpnInfo.state == 3) {
                return VpnUtils.disconnectLegacyVpn(this.mContext);
            }
            return true;
        } catch (RemoteException e) {
            Log.e("VpnHelper", "Failed to disconnect", e);
            return false;
        }
    }
}
