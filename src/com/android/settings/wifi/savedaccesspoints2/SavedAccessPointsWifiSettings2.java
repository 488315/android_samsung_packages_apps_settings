package com.android.settings.wifi.savedaccesspoints2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.wifitrackerlib.SavedNetworkTracker;

import java.time.ZoneOffset;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SavedAccessPointsWifiSettings2 extends DashboardFragment
        implements SavedNetworkTracker.SavedNetworkTrackerCallback {
    static final String TAG = "SavedAccessPoints2";
    SavedNetworkTracker mSavedNetworkTracker;
    HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.savedaccesspoints2.SavedAccessPointsWifiSettings2$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return TAG;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 106;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_display_saved_access_points2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((SavedAccessPointsPreferenceController2) use(SavedAccessPointsPreferenceController2.class))
                .setHost(this);
        ((SubscribedAccessPointsPreferenceController2)
                        use(SubscribedAccessPointsPreferenceController2.class))
                .setHost(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "SavedAccessPoints2{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        this.mSavedNetworkTracker =
                new SavedNetworkTracker(
                        getSettingsLifecycle(),
                        context,
                        (WifiManager) context.getSystemService(WifiManager.class),
                        (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                        new Handler(Looper.getMainLooper()),
                        this.mWorkerThread.getThreadHandler(),
                        new AnonymousClass1(ZoneOffset.UTC),
                        this);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.android.wifitrackerlib.SavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        ArrayList arrayList;
        if (isFinishingOrDestroyed()) {
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SavedAccessPointsPreferenceController2 savedAccessPointsPreferenceController2 =
                (SavedAccessPointsPreferenceController2)
                        use(SavedAccessPointsPreferenceController2.class);
        SavedNetworkTracker savedNetworkTracker = this.mSavedNetworkTracker;
        synchronized (savedNetworkTracker.mLock) {
            arrayList = new ArrayList(savedNetworkTracker.mSavedWifiEntries);
        }
        savedAccessPointsPreferenceController2.displayPreference(preferenceScreen, arrayList);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        onSavedWifiEntriesChanged();
        onSubscriptionWifiEntriesChanged();
    }

    @Override // com.android.wifitrackerlib.SavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSubscriptionWifiEntriesChanged() {
        ArrayList arrayList;
        if (isFinishingOrDestroyed()) {
            return;
        }
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SubscribedAccessPointsPreferenceController2 subscribedAccessPointsPreferenceController2 =
                (SubscribedAccessPointsPreferenceController2)
                        use(SubscribedAccessPointsPreferenceController2.class);
        SavedNetworkTracker savedNetworkTracker = this.mSavedNetworkTracker;
        synchronized (savedNetworkTracker.mLock) {
            arrayList = new ArrayList(savedNetworkTracker.mSubscriptionWifiEntries);
        }
        subscribedAccessPointsPreferenceController2.displayPreference(preferenceScreen, arrayList);
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {}
}
