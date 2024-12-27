package com.android.settings.wifi.factory;

import android.content.Context;
import android.net.TetheringManager;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.android.settings.wifi.repository.SharedConnectivityRepository;
import com.android.settings.wifi.repository.WifiHotspotRepository;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiFeatureProvider {
    public final Context mAppContext;
    public SharedConnectivityRepository mSharedConnectivityRepository;
    public TetheringManager mTetheringManager;
    public WifiHotspotRepository mWifiHotspotRepository;
    public WifiManager mWifiManager;
    public WifiVerboseLogging mWifiVerboseLogging;

    public WifiFeatureProvider(Context context) {
        this.mAppContext = context;
    }

    public final WifiHotspotRepository getWifiHotspotRepository() {
        if (this.mWifiHotspotRepository == null) {
            Context context = this.mAppContext;
            if (this.mWifiManager == null) {
                this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
            }
            WifiManager wifiManager = this.mWifiManager;
            if (this.mTetheringManager == null) {
                this.mTetheringManager =
                        (TetheringManager)
                                this.mAppContext.getSystemService(TetheringManager.class);
                verboseLog(
                        "WifiFeatureProvider", "getTetheringManager():" + this.mTetheringManager);
            }
            this.mWifiHotspotRepository =
                    new WifiHotspotRepository(context, wifiManager, this.mTetheringManager);
            verboseLog(
                    "WifiFeatureProvider",
                    "getWifiHotspotRepository():" + this.mWifiHotspotRepository);
        }
        return this.mWifiHotspotRepository;
    }

    public final void verboseLog(String str, String str2) {
        if (this.mWifiVerboseLogging == null) {
            Context context = this.mAppContext;
            if (this.mWifiManager == null) {
                this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
            }
            this.mWifiVerboseLogging = new WifiVerboseLogging(this.mWifiManager);
        }
        if (this.mWifiVerboseLogging.mIsVerboseLoggingEnabled) {
            Log.v(str, str2);
        }
    }
}
