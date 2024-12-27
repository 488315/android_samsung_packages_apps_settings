package com.samsung.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.provider.Settings;
import android.telephony.SubscriptionManager;

import com.samsung.android.wifi.SemWifiConfiguration;
import com.sec.ims.settings.ImsProfile;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SemWifiEntryFlags {
    public static int isBlockedUnSecureWifiAutoJoin = -1;
    public static int isShowBandSummaryOn = -1;
    public static int isWepAllowed = -1;
    public static int isWifiDeveloperOptionOn = -1;
    public static int isWpa3OweSupported = -1;
    public static int isWpa3SaeH2eSupported = -1;
    public static int isWpa3SaeSupported = -1;
    public static int isWpa3SuiteBSupported = -1;
    public boolean has6EStandard;
    public boolean isCaptivePortal;
    public boolean isCarrierNetwork;
    public boolean isH2EOnlyNetwork;
    public boolean isLocked;
    public boolean isOpenRoamingNetwork;
    public boolean isSamsungMobileHotspot;
    public boolean isSupportedWifi7;
    public boolean networkScoringUiEnabled;
    public int networkType;
    public PasspointConfiguration passpointConfiguration;
    public String prevSummaryString;
    public SemWifiConfiguration semConfig;
    public int wifiStandard;
    public int staCount = -1;
    public final Map qosScoredNetworkCache = new HashMap();

    public static boolean isEnhancedOpenSupported(WifiManager wifiManager) {
        if (isWpa3OweSupported == -1 && wifiManager.isWifiEnabled()) {
            isWpa3OweSupported = wifiManager.isEnhancedOpenSupported() ? 1 : 0;
        }
        return isWpa3OweSupported == 1;
    }

    public static boolean isWepAllowed(Context context) {
        if (isWepAllowed == -1) {
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            HandlerThread handlerThread = new HandlerThread("SemWifiUtils.isWepAllowed");
            handlerThread.start();
            Executor handlerExecutor = new HandlerExecutor(new Handler(handlerThread.getLooper()));
            final Object obj = new Object();
            final SemWifiUtils.Mutable mutable = new SemWifiUtils.Mutable(Boolean.FALSE);
            final SemWifiUtils.Mutable mutable2 = new SemWifiUtils.Mutable(Boolean.TRUE);
            ((WifiManager) context.getSystemService(ImsProfile.PDN_WIFI))
                    .queryWepAllowed(
                            handlerExecutor,
                            new Consumer() { // from class:
                                             // com.samsung.android.wifitrackerlib.SemWifiUtils.1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj2) {
                                    Boolean bool = (Boolean) obj2;
                                    synchronized (obj) {
                                        mutable2.value = bool;
                                        mutable.value = Boolean.TRUE;
                                        obj.notify();
                                    }
                                }
                            });
            synchronized (obj) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    long j = 10000 + currentTimeMillis;
                    while (!((Boolean) mutable.value).booleanValue() && currentTimeMillis < j) {
                        try {
                            obj.wait(j - currentTimeMillis);
                            currentTimeMillis = System.currentTimeMillis();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            isWepAllowed = ((Boolean) mutable2.value).booleanValue() ? 1 : 0;
        }
        return isWepAllowed == 1;
    }

    public static boolean isWifiDeveloperOptionOn(Context context) {
        if (isWifiDeveloperOptionOn == -1) {
            SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
            isWifiDeveloperOptionOn =
                    Settings.Global.getInt(
                                            context.getContentResolver(),
                                            "sem_wifi_developer_option_visible",
                                            0)
                                    == 1
                            ? 1
                            : 0;
        }
        return isWifiDeveloperOptionOn == 1;
    }

    public static boolean isWpa3SaeSupported(WifiManager wifiManager) {
        if (isWpa3SaeSupported == -1 && wifiManager.isWifiEnabled()) {
            isWpa3SaeSupported = wifiManager.isWpa3SaeSupported() ? 1 : 0;
        }
        return isWpa3SaeSupported == 1;
    }

    public final int getSemDisableReason() {
        SemWifiConfiguration semWifiConfiguration = this.semConfig;
        if (semWifiConfiguration != null) {
            return semWifiConfiguration.networkDisableReason;
        }
        return 0;
    }
}
