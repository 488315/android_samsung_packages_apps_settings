package com.samsung.android.settings.wifi.develop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.develop.savednetwork.AllWifiEntryCategory;
import com.samsung.android.settings.wifi.develop.savednetwork.SavedWifiEntryCategoryOption;
import com.samsung.android.settings.wifi.develop.savednetwork.UnusedWifiEntryCategory;
import com.samsung.android.settings.wifi.develop.savednetwork.WeakWifiEntryCategory;
import com.samsung.android.wifitrackerlib.SavedNetworkFilter;
import com.samsung.android.wifitrackerlib.SemSavedNetworkTracker;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.samsung.android.wifitrackerlib.labs.SemNetworkPredicate;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiConnectivityLabsFragment extends DashboardFragment
        implements SemSavedNetworkTracker.SavedNetworkTrackerCallback {
    public static final IntentFilter intentFilter;
    public Context mContext;
    public SemSavedNetworkTracker mSavedNetworkTracker;
    public HandlerThread mWorkerThread;
    public int mWifiState = -1;
    public final Handler connectionStatusUpdateHandler = new Handler();
    public final AnonymousClass1 connectionStatusUpdateRunnable =
            new Runnable() { // from class:
                             // com.samsung.android.settings.wifi.develop.WifiConnectivityLabsFragment.1
                @Override // java.lang.Runnable
                public final void run() {
                    WifiConnectivityLabsFragment wifiConnectivityLabsFragment =
                            WifiConnectivityLabsFragment.this;
                    IntentFilter intentFilter2 = WifiConnectivityLabsFragment.intentFilter;
                    wifiConnectivityLabsFragment.updateConnectionStatusPreference(false);
                    WifiConnectivityLabsFragment wifiConnectivityLabsFragment2 =
                            WifiConnectivityLabsFragment.this;
                    wifiConnectivityLabsFragment2.getClass();
                    Log.d("WifiConnectivityLabsFragment", "updateWeeklyReportPreference");
                    WifiLabsWeeklyReportPreference wifiLabsWeeklyReportPreference =
                            (WifiLabsWeeklyReportPreference)
                                    wifiConnectivityLabsFragment2.findPreference(
                                            "weekly_report_preference");
                    if (wifiLabsWeeklyReportPreference != null) {
                        wifiLabsWeeklyReportPreference.updateData(false);
                    }
                }
            };
    public final AnonymousClass2 broadcastReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.develop.WifiConnectivityLabsFragment.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    WifiConnectivityLabsFragment wifiConnectivityLabsFragment =
                            WifiConnectivityLabsFragment.this;
                    wifiConnectivityLabsFragment.connectionStatusUpdateHandler.postDelayed(
                            wifiConnectivityLabsFragment.connectionStatusUpdateRunnable, 200L);
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.WifiConnectivityLabsFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    static {
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter = intentFilter2;
        intentFilter2.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter2.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        intentFilter2.addAction("android.intent.action.TIME_TICK");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiConnectivityLabsFragment";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_connectivity_labs_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((WifiConnectivityIoTSetupController) use(WifiConnectivityIoTSetupController.class))
                .setHost(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiConnectivityLabsFragment{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass3 = new AnonymousClass3(ZoneOffset.UTC);
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        Context context = this.mContext;
        this.mSavedNetworkTracker =
                new SemSavedNetworkTracker(
                        settingsLifecycle,
                        context,
                        (WifiManager) context.getSystemService(WifiManager.class),
                        (ConnectivityManager)
                                this.mContext.getSystemService(ConnectivityManager.class),
                        new Handler(Looper.getMainLooper()),
                        this.mWorkerThread.getThreadHandler(),
                        anonymousClass3,
                        this,
                        SavedNetworkFilter.LABS_COMPARATOR);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        HashMap hashMap = new HashMap();
        Context context = this.mContext;
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        hashMap.put(
                "labs_filter_level",
                String.valueOf(
                        Settings.Global.getInt(
                                context.getContentResolver(), "sec_wifi_developer_rssi_level", 1)));
        SALogging.insertSALog("WIFI_LABS", "2001", hashMap, 0);
        HashMap hashMap2 = new HashMap();
        hashMap2.put(
                "labs_sorting_style",
                String.valueOf(SemWifiUtils.getDeveloperSortingStyle(this.mContext)));
        SALogging.insertSALog("WIFI_LABS", "2002", hashMap2, 0);
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getContext().unregisterReceiver(this.broadcastReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        if (!WifiDevelopmentSettingsEnabler.isWifiDevelopmentSettingsEnabled(this.mContext)) {
            finish();
        }
        getContext().registerReceiver(this.broadcastReceiver, intentFilter);
        if (this.mWifiState != -1) {
            updateConnectionStatusPreference(true);
            updateSavedNetworksPreference();
        }
    }

    @Override // com.samsung.android.wifitrackerlib.SemSavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        if (isFinishingOrDestroyed()) {
            return;
        }
        updateSavedNetworksPreference();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        onSavedWifiEntriesChanged();
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x005b, code lost:

       if (r2 == 2) goto L4;
    */
    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateConnectionStatusPreference(boolean r8) {
        /*
            r7 = this;
            java.lang.String r0 = "WifiConnectivityLabsFragment"
            r1 = 1
            if (r8 == 0) goto L7
        L5:
            r5 = r1
            goto L5e
        L7:
            android.content.Context r8 = r7.getContext()
            if (r8 != 0) goto Le
            return
        Le:
            java.lang.String r2 = "sem_wifi"
            java.lang.Object r8 = r8.getSystemService(r2)
            com.samsung.android.wifi.SemWifiManager r8 = (com.samsung.android.wifi.SemWifiManager) r8
            if (r8 != 0) goto L1a
            return
        L1a:
            java.lang.String r8 = r8.getCurrentStateAndEnterTime()
            boolean r2 = android.text.TextUtils.isEmpty(r8)
            r3 = -1
            r4 = 2
            r5 = 0
            if (r2 != 0) goto L4a
            java.lang.String r2 = " "
            java.lang.String[] r8 = r8.split(r2)
            int r2 = r8.length
            if (r2 < r4) goto L4a
            r2 = r8[r5]
            java.lang.String r6 = "Connected"
            boolean r2 = android.text.TextUtils.equals(r2, r6)
            if (r2 == 0) goto L3c
            r8 = r4
            goto L4b
        L3c:
            r8 = r8[r5]
            java.lang.String r2 = "Enabled"
            boolean r8 = android.text.TextUtils.equals(r8, r2)
            if (r8 == 0) goto L48
            r8 = r1
            goto L4b
        L48:
            r8 = r5
            goto L4b
        L4a:
            r8 = r3
        L4b:
            int r2 = r7.mWifiState
            if (r2 == r8) goto L5b
            if (r2 == r3) goto L53
            r5 = r1
            goto L58
        L53:
            java.lang.String r2 = "Do not refreshPreferences if state is unknown."
            android.util.Log.d(r0, r2)
        L58:
            r7.mWifiState = r8
            goto L5e
        L5b:
            if (r2 != r4) goto L5e
            goto L5
        L5e:
            if (r5 == 0) goto L73
            java.lang.String r8 = "updateConnectionStatusPreference"
            android.util.Log.d(r0, r8)
            java.lang.String r8 = "connection_status_preference"
            androidx.preference.Preference r7 = r7.findPreference(r8)
            com.samsung.android.settings.wifi.develop.WifiLabsConnectionStatusPreference r7 = (com.samsung.android.settings.wifi.develop.WifiLabsConnectionStatusPreference) r7
            if (r7 == 0) goto L73
            r7.updatePreference(r1)
        L73:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.WifiConnectivityLabsFragment.updateConnectionStatusPreference(boolean):void");
    }

    public final void updateSavedNetworksPreference() {
        int[] iArr = new int[3];
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        int i = 0;
        while (i <= 2) {
            List savedWifiEntries = this.mSavedNetworkTracker.getSavedWifiEntries();
            SavedWifiEntryCategoryOption unusedWifiEntryCategory =
                    i == 1
                            ? new UnusedWifiEntryCategory()
                            : i == 2
                                    ? new WeakWifiEntryCategory(this.mContext)
                                    : new AllWifiEntryCategory();
            ArrayList arrayList = new ArrayList();
            if (i == 1) {
                arrayList.add(SemNetworkPredicate.LONG_UNUSED_NETWORK_PREDICATE);
            } else if (i != 2) {
                arrayList.add(SemNetworkPredicate.ALL_PREDICATE);
            } else {
                arrayList.add(SemNetworkPredicate.OPEN_NETWORK_PREDICATE);
                arrayList.add(SemNetworkPredicate.WEP_NETWORK_PREDICATE);
            }
            Iterator it = ((ArrayList) savedWifiEntries).iterator();
            int i2 = 0;
            while (it.hasNext()) {
                WifiEntry wifiEntry = (WifiEntry) it.next();
                int i3 = 0;
                while (true) {
                    unusedWifiEntryCategory.getCategoryTitles();
                    if (i3 < ((ArrayList) unusedWifiEntryCategory.getCategoryTitles()).size()) {
                        if (((SemNetworkPredicate.AllNetworkPredicate) arrayList.get(i3))
                                .matches(this.mContext, wifiEntry.getWifiConfiguration())) {
                            i2++;
                        }
                        i3++;
                    }
                }
            }
            iArr[i] = i2;
            i++;
        }
        Log.d(
                "WifiConnectivityLabsFragment",
                "updateSavedNetworksPreference " + iArr[0] + " " + iArr[1] + " " + iArr[2]);
        WifiLabsSavedNetworksPreference wifiLabsSavedNetworksPreference =
                (WifiLabsSavedNetworksPreference) findPreference("saved_networks_preference");
        if (wifiLabsSavedNetworksPreference != null) {
            wifiLabsSavedNetworksPreference.updatePreference(true, iArr);
        }
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {}
}
