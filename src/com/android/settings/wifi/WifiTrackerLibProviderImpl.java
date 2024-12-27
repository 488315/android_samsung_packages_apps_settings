package com.android.settings.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;

import androidx.lifecycle.Lifecycle;

import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.android.wifitrackerlib.WifiTrackerInjector;

import java.time.Clock;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiTrackerLibProviderImpl {
    public static NetworkDetailsTracker createNetworkDetailsTracker(
            Lifecycle lifecycle,
            Context context,
            Handler handler,
            Handler handler2,
            Clock clock,
            String str) {
        return NetworkDetailsTracker.createNetworkDetailsTracker(
                new WifiTrackerInjector(context),
                lifecycle,
                context,
                (WifiManager) context.getSystemService(WifiManager.class),
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                handler,
                handler2,
                clock,
                15000L,
                10000L,
                str);
    }

    public static WifiPickerTracker createWifiPickerTracker(
            Lifecycle lifecycle,
            Context context,
            Handler handler,
            Handler handler2,
            Clock clock,
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        return new WifiPickerTracker(
                new WifiTrackerInjector(context),
                lifecycle,
                context,
                (WifiManager) context.getSystemService(WifiManager.class),
                (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                handler,
                handler2,
                clock,
                15000L,
                10000L,
                wifiPickerTrackerCallback,
                null,
                false);
    }
}
