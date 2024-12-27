package com.samsung.android.settings.wifi.details;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.util.SemLog;
import com.sec.ims.settings.ImsProfile;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiHotspot20Fragment extends DashboardFragment {
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public WifiDetailNavigationController mWifiDetailNavigationController;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiHotspot20Fragment$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        if (this.mNetworkDetailsTracker == null) {
            Context context2 = getContext();
            HandlerThread handlerThread =
                    new HandlerThread(
                            "WifiHotspot20Frg{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
            try {
                this.mNetworkDetailsTracker =
                        NetworkDetailsTracker.createNetworkDetailsTracker(
                                new WifiTrackerInjector(context2),
                                getSettingsLifecycle(),
                                context2,
                                (WifiManager) context2.getSystemService(WifiManager.class),
                                (ConnectivityManager)
                                        context2.getSystemService(ConnectivityManager.class),
                                new Handler(Looper.getMainLooper()),
                                this.mWorkerThread.getThreadHandler(),
                                new AnonymousClass1(ZoneOffset.UTC),
                                15000L,
                                10000L,
                                getArguments().getString("key_chosen_passpoint_key"));
            } catch (IllegalArgumentException e) {
                SemLog.d("WifiHotspot20Frg", "IllegalArgumentException " + e.getMessage());
                this.mWorkerThread.quit();
                Context context3 = getContext();
                String string = getArguments().getString("key_chosen_passpoint_friendly_name");
                Intent wifiWarningIntent = WifiWarningDialogController.getWifiWarningIntent();
                wifiWarningIntent.putExtra("req_type", 7);
                wifiWarningIntent.putExtra("friendly_name", string);
                try {
                    context3.startActivity(wifiWarningIntent);
                } catch (ActivityNotFoundException unused) {
                }
                ((SettingsActivity) getActivity()).finishPreferencePanel(null);
            }
        }
        ArrayList arrayList = new ArrayList();
        NetworkDetailsTracker networkDetailsTracker = this.mNetworkDetailsTracker;
        if (networkDetailsTracker != null) {
            WifiEntry wifiEntry = networkDetailsTracker.getWifiEntry();
            WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
            arrayList.add(new WifiPasspointPreferenceController(context, wifiEntry));
            arrayList.add(
                    new WifiAutoConnectPreferenceController(
                            context, wifiEntry, this, wifiManager, "WIFI_261"));
            this.mWifiDetailNavigationController =
                    new WifiDetailNavigationController(
                            wifiEntry,
                            getContext(),
                            this,
                            true,
                            getSettingsLifecycle(),
                            this.mMetricsFeatureProvider,
                            "WIFI_261");
        } else {
            SemLog.d(
                    "WifiHotspot20Frg",
                    "mNetworkDetailsTracker is null, cannot create preference controllers");
        }
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiHotspot20Frg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_hotspot20_fragment;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        WifiDetailNavigationController wifiDetailNavigationController =
                this.mWifiDetailNavigationController;
        if (wifiDetailNavigationController != null) {
            wifiDetailNavigationController.initBottom();
        }
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_261");
    }
}
