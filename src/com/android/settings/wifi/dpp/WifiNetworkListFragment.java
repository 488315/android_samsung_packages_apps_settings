package com.android.settings.wifi.dpp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.wifi.AddNetworkFragment;
import com.android.settings.wifi.WifiEntryPreference;
import com.android.wifitrackerlib.SavedNetworkTracker;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.wifi.WifiPolicy;

import java.time.Duration;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiNetworkListFragment extends SettingsPreferenceFragment
        implements SavedNetworkTracker.SavedNetworkTrackerCallback,
                Preference.OnPreferenceClickListener {
    static final int ADD_NETWORK_REQUEST = 1;
    static final String WIFI_CONFIG_KEY = "wifi_config_key";
    Preference mAddPreference;
    OnChooseNetworkListener mOnChooseNetworkListener;
    PreferenceCategory mPreferenceGroup;
    public AnonymousClass1 mSaveListener;
    SavedNetworkTracker mSavedNetworkTracker;
    WifiManager mWifiManager;
    HandlerThread mWorkerThread;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.dpp.WifiNetworkListFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DisableUnreachableWifiEntryPreference extends WifiEntryPreference {
        @Override // com.android.settings.wifi.WifiEntryPreference,
                  // com.android.wifitrackerlib.WifiEntry.WifiEntryCallback
        public final void onUpdated() {
            refresh();
            setEnabled(this.mWifiEntry.getLevel() != -1);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnChooseNetworkListener {}

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1595;
    }

    /* JADX WARN: Type inference failed for: r10v4, types: [com.android.settings.wifi.dpp.WifiNetworkListFragment$1] */
    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Context context = getContext();
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mSaveListener =
                new WifiManager
                        .ActionListener() { // from class:
                                            // com.android.settings.wifi.dpp.WifiNetworkListFragment.1
                    public final void onFailure(int i) {
                        FragmentActivity activity = WifiNetworkListFragment.this.getActivity();
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        Toast.makeText(activity, R.string.wifi_failed_save_message, 0).show();
                    }

                    public final void onSuccess() {}
                };
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiNetworkListFragment{"
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
                        new AnonymousClass2(ZoneOffset.UTC),
                        this);
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        WifiConfiguration wifiConfiguration;
        super.onActivityResult(i, i2, intent);
        if (i == 1
                && i2 == -1
                && (wifiConfiguration =
                                (WifiConfiguration) intent.getParcelableExtra(WIFI_CONFIG_KEY))
                        != null) {
            this.mWifiManager.save(wifiConfiguration, this.mSaveListener);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnChooseNetworkListener)) {
            throw new IllegalArgumentException("Invalid context type");
        }
        this.mOnChooseNetworkListener = (OnChooseNetworkListener) context;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        addPreferencesFromResource(R.xml.wifi_dpp_network_list);
        this.mPreferenceGroup = (PreferenceCategory) findPreference("access_points");
        Preference preference = new Preference(getPrefContext());
        this.mAddPreference = preference;
        preference.setIcon(R.drawable.ic_add_24dp);
        this.mAddPreference.setTitle(R.string.wifi_add_network);
        this.mAddPreference.setOnPreferenceClickListener(this);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        this.mWorkerThread.quit();
        super.onDestroyView();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onDetach() {
        this.mOnChooseNetworkListener = null;
        super.onDetach();
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (preference instanceof WifiEntryPreference) {
            WifiEntry wifiEntry = ((WifiEntryPreference) preference).mWifiEntry;
            WifiConfiguration wifiConfiguration = wifiEntry.getWifiConfiguration();
            if (wifiConfiguration == null) {
                throw new IllegalArgumentException("Invalid access point");
            }
            Duration duration = WifiDppUtils.VIBRATE_DURATION_QR_CODE_RECOGNITION;
            int security$1 = wifiEntry.getSecurity$1();
            WifiNetworkConfig validConfigOrNull =
                    WifiNetworkConfig.getValidConfigOrNull(
                            security$1 != 1
                                    ? security$1 != 2
                                            ? security$1 != 5
                                                    ? "nopass"
                                                    : WifiPolicy.SECURITY_TYPE_SAE
                                            : "WPA"
                                    : "WEP",
                            wifiConfiguration.getPrintableSsid(),
                            wifiConfiguration.preSharedKey,
                            wifiConfiguration.hiddenSSID,
                            wifiConfiguration.networkId,
                            false);
            OnChooseNetworkListener onChooseNetworkListener = this.mOnChooseNetworkListener;
            if (onChooseNetworkListener != null) {
                WifiDppConfiguratorActivity wifiDppConfiguratorActivity =
                        (WifiDppConfiguratorActivity) onChooseNetworkListener;
                wifiDppConfiguratorActivity.mWifiNetworkConfig =
                        new WifiNetworkConfig(validConfigOrNull);
                wifiDppConfiguratorActivity.showAddDeviceFragment(true);
            }
        } else {
            if (preference != this.mAddPreference) {
                return onPreferenceTreeClick(preference);
            }
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
            subSettingLauncher.setTitleRes(R.string.wifi_add_network, null);
            String name = AddNetworkFragment.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            launchRequest.mSourceMetricsCategory = 1595;
            subSettingLauncher.setResultListener(this, 1);
            subSettingLauncher.launch();
        }
        return true;
    }

    @Override // com.android.wifitrackerlib.SavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        ArrayList arrayList;
        SavedNetworkTracker savedNetworkTracker = this.mSavedNetworkTracker;
        synchronized (savedNetworkTracker.mLock) {
            arrayList = new ArrayList(savedNetworkTracker.mSavedWifiEntries);
        }
        List<WifiEntry> list =
                (List)
                        arrayList.stream()
                                .filter(
                                        new Predicate() { // from class:
                                                          // com.android.settings.wifi.dpp.WifiNetworkListFragment$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                WifiNetworkListFragment.this.getClass();
                                                int security$1 = ((WifiEntry) obj).getSecurity$1();
                                                return security$1 == 2 || security$1 == 5;
                                            }
                                        })
                                .collect(Collectors.toList());
        this.mPreferenceGroup.removeAll();
        int i = 0;
        for (WifiEntry wifiEntry : list) {
            DisableUnreachableWifiEntryPreference disableUnreachableWifiEntryPreference =
                    new DisableUnreachableWifiEntryPreference(getContext(), wifiEntry);
            disableUnreachableWifiEntryPreference.setOnPreferenceClickListener(this);
            disableUnreachableWifiEntryPreference.setEnabled(wifiEntry.getLevel() != -1);
            disableUnreachableWifiEntryPreference.setOrder(i);
            this.mPreferenceGroup.addPreference(disableUnreachableWifiEntryPreference);
            i++;
        }
        this.mAddPreference.setOrder(i);
        this.mPreferenceGroup.addPreference(this.mAddPreference);
    }

    @Override // com.android.wifitrackerlib.SavedNetworkTracker.SavedNetworkTrackerCallback
    public final void onSubscriptionWifiEntriesChanged() {}

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {}
}
