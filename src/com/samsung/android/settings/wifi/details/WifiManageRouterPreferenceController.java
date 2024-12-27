package com.samsung.android.settings.wifi.details;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiManageRouterPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin,
                LifecycleObserver,
                Preference.OnPreferenceClickListener {
    public static final int[] mIgnorableNetworkMasks = {2861248};
    public final ConnectivityManager mConnectivityManager;
    public final String mGateway;
    public int mHttpResponse;
    public Preference mManageRouterPref;
    public final String mSAScreenId;
    public final WifiEntry mWifiEntry;

    public WifiManageRouterPreferenceController(
            WifiEntry wifiEntry, ConnectivityManager connectivityManager, Context context) {
        super(context);
        WifiConfiguration wifiConfiguration;
        this.mGateway = null;
        this.mWifiEntry = wifiEntry;
        WifiManager wifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mConnectivityManager = connectivityManager;
        this.mSAScreenId = "WIFI_106";
        if (!Utils.isDeviceProvisioned(this.mContext)) {
            Log.d("WifiManageRouterPrefCtrl", "Go to Webpage: Setup Wizard");
            return;
        }
        if (wifiEntry == null || wifiEntry.getSecurity$1() == 3) {
            return;
        }
        SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
        if (semWifiEntryFlags.isCarrierNetwork
                || semWifiEntryFlags.isLocked
                || (wifiConfiguration = wifiEntry.getWifiConfiguration()) == null
                || !WifiDevicePolicyManager.canModifyNetwork(this.mContext, wifiConfiguration)
                || WifiUtils.isNetworkLockedDown(this.mContext, wifiConfiguration)
                || semWifiEntryFlags.isCaptivePortal
                || wifiEntry.semIsMobileHotspot()) {
            return;
        }
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int i = mIgnorableNetworkMasks[0];
        if (connectionInfo != null && (connectionInfo.getIpAddress() & 16777215) == i) {
            Log.d("WifiManageRouterPrefCtrl", "Go to Webpage: Masked Android Hotspot");
            return;
        }
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo != null) {
            Preference$$ExternalSyntheticOutline0.m(
                    new StringBuilder("dhcpInfo.gateway : "),
                    dhcpInfo.gateway,
                    "WifiManageRouterPrefCtrl");
            if (dhcpInfo.gateway != 0) {
                this.mGateway = "http://" + Formatter.formatIpAddress(dhcpInfo.gateway);
            }
        }
        CheckManageRouterTask checkManageRouterTask = new CheckManageRouterTask();
        checkManageRouterTask.mResponse = 0;
        checkManageRouterTask.mCallback = this;
        String str = this.mGateway;
        if (str != null) {
            checkManageRouterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.d(
                "WifiManageRouterPrefCtrl",
                "displayPreference mHttpResponse is " + this.mHttpResponse);
        Preference findPreference = preferenceScreen.findPreference("manage_router");
        this.mManageRouterPref = findPreference;
        findPreference.setOnPreferenceClickListener(this);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "manage_router";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        WifiEntry wifiEntry;
        NetworkInfo activeNetworkInfo;
        int i = this.mHttpResponse;
        return (i == 200 || i == 401)
                && (wifiEntry = this.mWifiEntry) != null
                && wifiEntry.getConnectedInfo() != null
                && (activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo()) != null
                && activeNetworkInfo.getType() == 1
                && activeNetworkInfo.isConnected();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        SALogging.insertSALog(this.mSAScreenId, "1024");
        try {
            this.mContext.startActivity(
                    new Intent("android.intent.action.VIEW", Uri.parse(this.mGateway)));
            return true;
        } catch (ActivityNotFoundException e) {
            Log.d("WifiManageRouterPrefCtrl", "Go to Webpage: Activity not found");
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            Log.d("WifiManageRouterPrefCtrl", "Go to Webpage: " + e2.getLocalizedMessage());
            e2.printStackTrace();
            return false;
        }
    }
}
