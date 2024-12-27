package com.android.settings.wifi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PersistableBundle;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.network.CarrierConfigCache;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import com.android.wifitrackerlib.WifiTrackerInjector;

import java.time.ZoneOffset;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiPickerTrackerHelper implements LifecycleObserver {
    public static final AnonymousClass1 ELAPSED_REALTIME_CLOCK =
            new AnonymousClass1(ZoneOffset.UTC);
    public final CarrierConfigCache mCarrierConfigCache;
    public WifiPickerTracker mWifiPickerTracker;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.WifiPickerTrackerHelper$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    public WifiPickerTrackerHelper(
            Lifecycle lifecycle,
            Context context,
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback) {
        if (lifecycle == null) {
            throw new IllegalArgumentException("lifecycle must be non-null.");
        }
        lifecycle.addObserver(this);
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiPickerTrackerHelper{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                featureFactoryImpl.getWifiTrackerLibProvider();
        Handler handler = new Handler(Looper.getMainLooper());
        Handler threadHandler = this.mWorkerThread.getThreadHandler();
        wifiTrackerLibProvider.getClass();
        this.mWifiPickerTracker =
                WifiTrackerLibProviderImpl.createWifiPickerTracker(
                        lifecycle,
                        context,
                        handler,
                        threadHandler,
                        ELAPSED_REALTIME_CLOCK,
                        wifiPickerTrackerCallback);
        this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
    }

    public final boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = this.mWifiPickerTracker.getMergedCarrierEntry();
        return mergedCarrierEntry != null && mergedCarrierEntry.isDefaultNetwork();
    }

    public final boolean isCarrierNetworkProvisionEnabled(int i) {
        this.mCarrierConfigCache.getClass();
        PersistableBundle configForSubId = CarrierConfigCache.getConfigForSubId(i);
        if (configForSubId == null) {
            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                    .m(i, "Could not get carrier config, subId:", "WifiPickerTrackerHelper");
            return false;
        }
        boolean z = configForSubId.getBoolean("carrier_provisions_wifi_merged_networks_bool");
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "isCarrierNetworkProvisionEnabled:", "WifiPickerTrackerHelper", z);
        return z;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mWorkerThread.quit();
    }

    public final void setCarrierNetworkEnabled(boolean z) {
        MergedCarrierEntry mergedCarrierEntry = this.mWifiPickerTracker.getMergedCarrierEntry();
        if (mergedCarrierEntry == null) {
            Log.e(
                    "WifiPickerTrackerHelper",
                    "Unable to get MergedCarrierEntry to set enabled status");
            return;
        }
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "setCarrierNetworkEnabled:", "WifiPickerTrackerHelper", z);
        mergedCarrierEntry.mWifiManager.setCarrierNetworkOffloadEnabled(
                mergedCarrierEntry.mSubscriptionId, true, z);
        if (z) {
            return;
        }
        mergedCarrierEntry.mWifiManager.stopRestrictingAutoJoinToSubscriptionId();
        mergedCarrierEntry.mWifiManager.startScan();
    }

    @VisibleForTesting
    public void setWifiPickerTracker(WifiPickerTracker wifiPickerTracker) {
        this.mWifiPickerTracker = wifiPickerTracker;
    }

    @VisibleForTesting
    public void setWorkerThread(HandlerThread handlerThread) {
        this.mWorkerThread = handlerThread;
    }

    public WifiPickerTrackerHelper(
            com.android.settingslib.core.lifecycle.Lifecycle lifecycle,
            Context context,
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback,
            WifiPickerTracker.SemWifiPickerTrackerCallback semWifiPickerTrackerCallback,
            long j) {
        if (lifecycle != null) {
            lifecycle.addObserver(this);
            HandlerThread handlerThread =
                    new HandlerThread(
                            "WifiPickerTrackerHelper{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl != null) {
                WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                        featureFactoryImpl.getWifiTrackerLibProvider();
                Handler handler = new Handler(Looper.getMainLooper());
                Handler threadHandler = this.mWorkerThread.getThreadHandler();
                wifiTrackerLibProvider.getClass();
                this.mWifiPickerTracker =
                        new WifiPickerTracker(
                                new WifiTrackerInjector(context),
                                lifecycle,
                                context,
                                (WifiManager) context.getSystemService(WifiManager.class),
                                (ConnectivityManager)
                                        context.getSystemService(ConnectivityManager.class),
                                handler,
                                threadHandler,
                                ELAPSED_REALTIME_CLOCK,
                                15000L,
                                j,
                                wifiPickerTrackerCallback,
                                semWifiPickerTrackerCallback,
                                true);
                this.mCarrierConfigCache = CarrierConfigCache.getInstance(context);
                return;
            }
            throw new UnsupportedOperationException("No feature factory configured");
        }
        throw new IllegalArgumentException("lifecycle must be non-null.");
    }
}
