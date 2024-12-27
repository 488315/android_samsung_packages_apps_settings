package com.samsung.android.settings.wifi.develop.details;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;

import com.sec.ims.settings.ImsProfile;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiNetworkDetailsFragmentForLabs extends DashboardFragment {
    public final List mControllers = new ArrayList();
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public WifiEntry mWifiEntry;
    public HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.details.WifiNetworkDetailsFragmentForLabs$1, reason: invalid class name */
    public final class AnonymousClass1 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        if (this.mNetworkDetailsTracker == null) {
            Context context2 = getContext();
            HandlerThread handlerThread =
                    new HandlerThread(
                            "WifiNetworkDetailsFragmentForLabs{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
            SimpleClock anonymousClass1 = new AnonymousClass1(ZoneOffset.UTC);
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            WifiTrackerLibProviderImpl wifiTrackerLibProvider =
                    featureFactoryImpl.getWifiTrackerLibProvider();
            Lifecycle settingsLifecycle = getSettingsLifecycle();
            Handler handler = new Handler(Looper.getMainLooper());
            Handler threadHandler = this.mWorkerThread.getThreadHandler();
            String string = getArguments().getString("key_chosen_wifientry_key");
            wifiTrackerLibProvider.getClass();
            this.mNetworkDetailsTracker =
                    WifiTrackerLibProviderImpl.createNetworkDetailsTracker(
                            settingsLifecycle,
                            context2,
                            handler,
                            threadHandler,
                            anonymousClass1,
                            string);
        }
        Log.d("WifiNetworkDetailsFragmentForLabs", "setupForceWifiEntry");
        if (wifiManager.getConnectionInfo() != null) {
            WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
            this.mWifiEntry = wifiEntry;
            wifiEntry.forceUpdateNetworkInfo(
                    wifiManager.getConnectionInfo(),
                    connectivityManager.getNetworkInfo(wifiManager.getCurrentNetwork()));
        }
        ((ArrayList) this.mControllers)
                .add(new WifiNetworkDetailPreferenceController(context, this, this.mWifiEntry));
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiNetworkDetailsFragmentForLabs";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_network_details_fragment_for_labs;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        if (activity instanceof SettingsBaseActivity) {
            ((SettingsBaseActivity) activity).disableExtendedAppBar();
        }
        activity.setTitle((CharSequence) null);
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mWorkerThread.quit();
        super.onDestroy();
    }
}
