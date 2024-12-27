package com.android.wifitrackerlib;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Debug;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;

import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ScanResultUpdater {
    public final Clock mClock;
    public final long mMaxScanAgeMillis;
    public final SemWifiEntryFilter mSemFilter;
    public int mFilteredNetworkCount = 0;
    public final Map mScanResultsBySsidAndBssid = new ArrayMap();
    public final Object mLock = new Object();
    public final LogUtils mLog = new LogUtils();

    public ScanResultUpdater(Clock clock, long j, Context context) {
        this.mMaxScanAgeMillis = j;
        this.mClock = clock;
        this.mSemFilter = new SemWifiEntryFilter(context);
    }

    public final void evictOldScans() {
        synchronized (this.mLock) {
            ((ArrayMap) this.mScanResultsBySsidAndBssid)
                    .entrySet()
                    .removeIf(
                            new Predicate() { // from class:
                                              // com.android.wifitrackerlib.ScanResultUpdater$$ExternalSyntheticLambda0
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    ScanResultUpdater scanResultUpdater = ScanResultUpdater.this;
                                    return scanResultUpdater.mClock.millis()
                                                    - (((ScanResult) ((Map.Entry) obj).getValue())
                                                                    .timestamp
                                                            / 1000)
                                            > scanResultUpdater.mMaxScanAgeMillis;
                                }
                            });
        }
        this.mFilteredNetworkCount = 0;
    }

    public final List getScanResults(long j) {
        ArrayList arrayList;
        if (j > this.mMaxScanAgeMillis) {
            throw new IllegalArgumentException(
                    "maxScanAgeMillis argument cannot be greater than mMaxScanAgeMillis!");
        }
        synchronized (this.mLock) {
            try {
                arrayList = new ArrayList();
                for (ScanResult scanResult :
                        ((ArrayMap) this.mScanResultsBySsidAndBssid).values()) {
                    if (this.mClock.millis() - (scanResult.timestamp / 1000) <= j) {
                        arrayList.add(scanResult);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final void update(List list) {
        synchronized (this.mLock) {
            try {
                evictOldScans();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                    ScanResult scanResult2 =
                            (ScanResult) ((ArrayMap) this.mScanResultsBySsidAndBssid).get(pair);
                    if (scanResult2 != null && scanResult2.timestamp >= scanResult.timestamp) {}
                    ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void update(List list, WifiInfo wifiInfo) {
        synchronized (this.mLock) {
            try {
                evictOldScans();
                this.mSemFilter.updateRssiFilter();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    if (!TextUtils.isEmpty(scanResult.SSID)) {
                        Pair pair = new Pair(scanResult.SSID, scanResult.BSSID);
                        ScanResult scanResult2 =
                                (ScanResult) ((ArrayMap) this.mScanResultsBySsidAndBssid).get(pair);
                        if (scanResult2 != null && scanResult2.timestamp >= scanResult.timestamp) {}
                        SemWifiEntryFilter semWifiEntryFilter = this.mSemFilter;
                        semWifiEntryFilter.getClass();
                        int i = scanResult.level;
                        if (i >= semWifiEntryFilter.mWeakSignalRssi) {
                            int i2 = scanResult.frequency;
                            if (i2 > 5000) {
                                if (i2 < 6000) {
                                    if (i >= semWifiEntryFilter.mWeakSignalRssi5Ghz) {}
                                }
                            }
                            ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                        }
                        this.mFilteredNetworkCount++;
                        if (wifiInfo != null
                                && TextUtils.equals(wifiInfo.getBSSID(), scanResult.BSSID)) {
                            Log.d(
                                    "WifiTracker.ScanResultUpdater",
                                    "it's weak signal network " + wifiInfo.getSSID());
                            ((ArrayMap) this.mScanResultsBySsidAndBssid).put(pair, scanResult);
                        } else if (Debug.semIsProductDev()) {
                            LogUtils logUtils = this.mLog;
                            String str = "filtered scan item: " + scanResult.toString();
                            if (logUtils.isProductDev) {
                                Log.d(
                                        "WifiTracker.ScanResultUpdater",
                                        logUtils.getPrintableLog(str));
                            }
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
