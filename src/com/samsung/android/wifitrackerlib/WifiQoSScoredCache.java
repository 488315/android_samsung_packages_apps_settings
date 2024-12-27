package com.samsung.android.wifitrackerlib;

import android.content.Context;

import com.android.wifitrackerlib.BaseWifiTracker;

import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiQoSScoredCache {
    public final Context mContext;
    public final BaseWifiTracker.AnonymousClass8 mListener;
    public final SemWifiManager mSemWifiManager;
    public boolean mUpdated;
    public final Object mLock = new Object();
    public final Map mCache = new HashMap();
    public final LogUtils mLog = new LogUtils();

    public WifiQoSScoredCache(Context context, BaseWifiTracker.AnonymousClass8 anonymousClass8) {
        this.mContext = context;
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mListener = anonymousClass8;
    }
}
