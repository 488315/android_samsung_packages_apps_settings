package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settingslib.connectivity.ConnectivitySubsystemsRecoveryManager;
import com.android.settingslib.utils.HandlerInjector;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class InternetResetHelper implements LifecycleObserver {
    public final Context mContext;
    public final HandlerInjector mHandlerInjector;
    public boolean mIsWifiReady;
    public final NetworkMobileProviderController mMobileNetworkController;
    public final RecoveryWorker mRecoveryWorker;
    public final Preference mResettingPreference;
    public final InternetResetHelper$$ExternalSyntheticLambda0 mTimeoutRunnable;
    public final WifiManager mWifiManager;
    public final List mWifiNetworkPreferences;
    public final IntentFilter mWifiStateFilter;
    public final AnonymousClass1 mWifiStateReceiver;
    public final Preference mWifiTogglePreferences;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class RecoveryWorker {
        public static WeakReference sCallback;
        public static RecoveryWorker sInstance;
        public static boolean sIsRecovering;
        public static ConnectivitySubsystemsRecoveryManager sRecoveryManager;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.network.InternetResetHelper$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.settings.network.InternetResetHelper$$ExternalSyntheticLambda0] */
    public InternetResetHelper(
            Context context,
            Lifecycle lifecycle,
            NetworkMobileProviderController networkMobileProviderController,
            Preference preference,
            PreferenceCategory preferenceCategory,
            PreferenceCategory preferenceCategory2,
            PreferenceCategory preferenceCategory3,
            Preference preference2) {
        ArrayList arrayList = new ArrayList();
        this.mWifiNetworkPreferences = arrayList;
        this.mWifiStateReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.settings.network.InternetResetHelper.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        InternetResetHelper.this.updateWifiStateChange();
                    }
                };
        this.mIsWifiReady = true;
        this.mTimeoutRunnable =
                new Runnable() { // from class:
                    // com.android.settings.network.InternetResetHelper$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        InternetResetHelper internetResetHelper = InternetResetHelper.this;
                        internetResetHelper.getClass();
                        Log.w(
                                "InternetResetHelper",
                                "Resume preferences due to connectivity subsystems recovery timed"
                                    + " out.");
                        internetResetHelper.mRecoveryWorker.getClass();
                        InternetResetHelper.RecoveryWorker.sIsRecovering = false;
                        internetResetHelper.mIsWifiReady = true;
                        internetResetHelper.resumePreferences();
                    }
                };
        this.mContext = context;
        this.mMobileNetworkController = networkMobileProviderController;
        this.mWifiTogglePreferences = preference;
        arrayList.add(preferenceCategory);
        arrayList.add(preferenceCategory2);
        arrayList.add(preferenceCategory3);
        this.mResettingPreference = preference2;
        this.mHandlerInjector = new HandlerInjector(context.getMainThreadHandler());
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mWifiStateFilter = new IntentFilter("android.net.wifi.STATE_CHANGE");
        RecoveryWorker.sCallback = new WeakReference(this);
        RecoveryWorker recoveryWorker = RecoveryWorker.sInstance;
        if (recoveryWorker == null) {
            RecoveryWorker.sInstance = new RecoveryWorker();
            Context applicationContext = context.getApplicationContext();
            RecoveryWorker.sRecoveryManager =
                    new ConnectivitySubsystemsRecoveryManager(
                            applicationContext, applicationContext.getMainThreadHandler());
            recoveryWorker = RecoveryWorker.sInstance;
        }
        this.mRecoveryWorker = recoveryWorker;
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        this.mHandlerInjector.mHandler.removeCallbacks(this.mTimeoutRunnable);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        this.mContext.unregisterReceiver(this.mWifiStateReceiver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        this.mContext.registerReceiver(this.mWifiStateReceiver, this.mWifiStateFilter, 2);
    }

    public final void resumePreferences() {
        Preference preference;
        NetworkMobileProviderController networkMobileProviderController;
        this.mRecoveryWorker.getClass();
        boolean z = !RecoveryWorker.sIsRecovering;
        if (z && (networkMobileProviderController = this.mMobileNetworkController) != null) {
            Log.d("InternetResetHelper", "Resume the Mobile Network controller");
            networkMobileProviderController.hidePreference(false, true);
        }
        if (this.mIsWifiReady && (preference = this.mWifiTogglePreferences) != null) {
            Log.d("InternetResetHelper", "Resume the Wi-Fi preferences");
            preference.setVisible(true);
            Iterator it = ((ArrayList) this.mWifiNetworkPreferences).iterator();
            while (it.hasNext()) {
                ((PreferenceCategory) it.next()).setVisible(true);
            }
        }
        if (z && this.mIsWifiReady) {
            this.mHandlerInjector.mHandler.removeCallbacks(this.mTimeoutRunnable);
            Preference preference2 = this.mResettingPreference;
            if (preference2 != null) {
                Log.d("InternetResetHelper", "Resume the Resetting preference");
                preference2.setVisible(false);
            }
        }
    }

    public final void showResettingAndSendTimeoutChecks() {
        Log.d("InternetResetHelper", "Suspend the subsystem preferences");
        NetworkMobileProviderController networkMobileProviderController =
                this.mMobileNetworkController;
        if (networkMobileProviderController != null) {
            networkMobileProviderController.hidePreference(true, true);
        }
        Preference preference = this.mWifiTogglePreferences;
        if (preference != null) {
            preference.setVisible(false);
        }
        Iterator it = ((ArrayList) this.mWifiNetworkPreferences).iterator();
        while (it.hasNext()) {
            PreferenceCategory preferenceCategory = (PreferenceCategory) it.next();
            preferenceCategory.removeAll();
            preferenceCategory.setVisible(false);
        }
        Preference preference2 = this.mResettingPreference;
        if (preference2 != null) {
            preference2.setVisible(true);
        }
        this.mHandlerInjector.mHandler.postDelayed(this.mTimeoutRunnable, 15000L);
    }

    public void updateWifiStateChange() {
        if (this.mIsWifiReady || !this.mWifiManager.isWifiEnabled()) {
            return;
        }
        Log.d("InternetResetHelper", "The Wi-Fi subsystem is done for recovery.");
        this.mIsWifiReady = true;
        resumePreferences();
    }
}
