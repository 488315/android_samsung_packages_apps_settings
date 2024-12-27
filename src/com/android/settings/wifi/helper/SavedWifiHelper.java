package com.android.settings.wifi.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.Lifecycle;

import com.android.wifitrackerlib.SavedNetworkTracker;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda0;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SavedWifiHelper extends WifiTrackerBase {
    public static final Object sInstanceLock = new Object();
    public static Map sTestInstances;
    public final SavedNetworkTracker mSavedNetworkTracker;

    public SavedWifiHelper(
            Context context, Lifecycle lifecycle, SavedNetworkTracker savedNetworkTracker) {
        super(lifecycle, null);
        this.mSavedNetworkTracker =
                savedNetworkTracker == null
                        ? createSavedNetworkTracker(context, lifecycle)
                        : savedNetworkTracker;
    }

    public static void setTestInstance(Context context, SavedWifiHelper savedWifiHelper) {
        synchronized (sInstanceLock) {
            try {
                if (sTestInstances == null) {
                    sTestInstances = new ConcurrentHashMap();
                }
                Log.w("SavedWifiHelper", "Set a test instance by context:" + context);
                sTestInstances.put(context, savedWifiHelper);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public SavedNetworkTracker createSavedNetworkTracker(Context context, Lifecycle lifecycle) {
        return new SavedNetworkTracker(
                lifecycle,
                context.getApplicationContext(),
                (WifiManager) context.getApplicationContext().getSystemService(WifiManager.class),
                (ConnectivityManager)
                        context.getApplicationContext().getSystemService(ConnectivityManager.class),
                new Handler(Looper.getMainLooper()),
                this.mWorkerThread.getThreadHandler(),
                WifiTrackerBase.ELAPSED_REALTIME_CLOCK,
                null);
    }

    @Override // com.android.settings.wifi.helper.WifiTrackerBase
    public final String getTag() {
        return "SavedWifiHelper";
    }

    public final boolean isCertificateInUse(String str) {
        SavedNetworkTracker savedNetworkTracker = this.mSavedNetworkTracker;
        List list =
                (List)
                        savedNetworkTracker.mWifiManager.getNetworkSuggestions().stream()
                                .map(new SavedNetworkTracker$$ExternalSyntheticLambda0(3))
                                .collect(Collectors.toList());
        list.addAll(savedNetworkTracker.mWifiManager.getConfiguredNetworks());
        return list.stream().anyMatch(new SavedNetworkTracker$$ExternalSyntheticLambda1(str, 1));
    }
}
