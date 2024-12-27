package com.android.settings.development;

import android.content.Context;
import android.debug.IAdbManager;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.os.Build;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AdbIpAddressPreferenceController
        extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {
        "android.net.conn.CONNECTIVITY_CHANGE",
        "android.net.wifi.LINK_CONFIGURATION_CHANGED",
        "android.net.wifi.STATE_CHANGE"
    };
    public Preference mAdbIpAddrPref;
    public final IAdbManager mAdbManager;
    public final ConnectivityManager mCM;

    public AdbIpAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
        this.mCM = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mAdbManager = IAdbManager.Stub.asInterface(ServiceManager.getService("adb"));
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mAdbIpAddrPref = preferenceScreen.findPreference("adb_ip_addr_pref");
        updateConnectivity();
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    public final String getIpv4Address() {
        if (Build.IS_ARC) {
            String[] split =
                    SystemProperties.get("vendor.arc.net.ipv4.host_wifi_address").split(",");
            if (split.length > 0) {
                return split[0];
            }
        }
        ConnectivityManager connectivityManager = this.mCM;
        LinkProperties linkProperties =
                connectivityManager.getLinkProperties(connectivityManager.getActiveNetwork());
        if (linkProperties == null) {
            return null;
        }
        Iterator it = linkProperties.getAllLinkAddresses().iterator();
        if (!it.hasNext()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            InetAddress address = ((LinkAddress) it.next()).getAddress();
            if (address instanceof Inet4Address) {
                sb.append(address.getHostAddress());
                break;
            }
        }
        return sb.toString();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "adb_ip_addr_pref";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        int i;
        String ipv4Address = getIpv4Address();
        if (ipv4Address == null) {
            this.mAdbIpAddrPref.setSummary(R.string.status_unavailable);
            return;
        }
        try {
            i = this.mAdbManager.getAdbWirelessPort();
        } catch (RemoteException unused) {
            Log.e("AdbIpAddrPrefCtrl", "Unable to get the adbwifi port");
            i = 0;
        }
        if (i <= 0) {
            this.mAdbIpAddrPref.setSummary(R.string.status_unavailable);
        } else {
            ipv4Address = ipv4Address + ":" + i;
        }
        this.mAdbIpAddrPref.setSummary(ipv4Address);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        super.updateState(preference);
        updateConnectivity();
    }
}
