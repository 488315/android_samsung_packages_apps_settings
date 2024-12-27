package com.samsung.android.settings.wifi.develop.diagnosis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
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
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisAvailableWifiEntryListAdapter;
import com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisConnectedWifiEntryListAdapter;
import com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisStepLogListAdapter;
import com.samsung.android.settings.wifi.develop.diagnosis.utils.SupplicantStateMessage;
import com.samsung.android.settings.wifi.develop.history.model.LogRepository;
import com.samsung.android.settings.wifi.managenetwork.SavedWifiEntrySettings;
import com.samsung.android.wifitrackerlib.SavedScannedTracker;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiDiagnosisSettings extends SettingsPreferenceFragment
        implements SavedScannedTracker.SavedScannedTrackerCallback {
    public DiagnosisAvailableWifiEntryListAdapter mAvailableAdapter;
    public TextView mAvailableCategory;
    public RecyclerView mAvailableView;
    public DiagnosisConnectedWifiEntryListAdapter mConnectedAdapter;
    public TextView mConnectedCategory;
    public RecyclerView mConnectedView;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public TextView mEmptyView;
    public Button mGoToManageNetwork;
    public LogRepository mLogRepository;
    public SavedScannedTracker mSavedScannedTracker;
    public WifiEntry mStepEntry;
    public DiagnosisStepLogListAdapter mStepLogAdapter;
    public RecyclerView mStepLogView;
    public WifiManager mWifiManager;
    public HandlerThread mWorkerThread;
    public final IntentFilter mIntentFilter = new IntentFilter();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.develop.diagnosis.WifiDiagnosisSettings.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    action.getClass();
                    switch (action) {
                        case "android.net.wifi.STATE_CHANGE":
                            NetworkInfo networkInfo =
                                    (NetworkInfo)
                                            intent.getParcelableExtra(
                                                    IMSParameter.GENERAL.NETWORK_INFO);
                            Log.d(
                                    "WifiDiagnosisSettings",
                                    "Network state changed " + networkInfo.getDetailedState());
                            if (networkInfo
                                            .getDetailedState()
                                            .equals(NetworkInfo.DetailedState.CONNECTED)
                                    || networkInfo
                                            .getDetailedState()
                                            .equals(NetworkInfo.DetailedState.DISCONNECTED)) {
                                WifiDiagnosisSettings.this.updateStepLog();
                            }
                            WifiDiagnosisSettings.this.updateWifiEntries$3();
                            break;
                        case "android.net.wifi.supplicant.STATE_CHANGE":
                            intent.getParcelableExtra("newState");
                            WifiDiagnosisSettings wifiDiagnosisSettings =
                                    WifiDiagnosisSettings.this;
                            final SupplicantState supplicantState =
                                    (SupplicantState) intent.getParcelableExtra("newState");
                            intent.hasExtra("supplicantError");
                            wifiDiagnosisSettings.getClass();
                            Log.d(
                                    "WifiDiagnosisSettings",
                                    "[updateStepList] state : " + supplicantState);
                            ((SupplicantStateMessage)
                                            Stream.of((Object[]) SupplicantStateMessage.values())
                                                    .filter(
                                                            new Predicate() { // from class:
                                                                              // com.samsung.android.settings.wifi.develop.diagnosis.utils.SupplicantStateMessage$$ExternalSyntheticLambda0
                                                                @Override // java.util.function.Predicate
                                                                public final boolean test(
                                                                        Object obj) {
                                                                    boolean equals;
                                                                    equals =
                                                                            ((SupplicantStateMessage)
                                                                                            obj)
                                                                                            .mSupplicantState
                                                                                            .equals(
                                                                                                    supplicantState);
                                                                    return equals;
                                                                }
                                                            })
                                                    .findAny()
                                                    .orElse(SupplicantStateMessage.INVALID))
                                    .getClass();
                            break;
                        case "android.net.wifi.CONFIGURED_NETWORKS_CHANGE":
                            WifiDiagnosisSettings.this.updateWifiEntries$3();
                            break;
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.diagnosis.WifiDiagnosisSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        HandlerThread handlerThread =
                new HandlerThread(
                        "WifiDiagnosisSettings{"
                                + Integer.toHexString(System.identityHashCode(this))
                                + "}",
                        10);
        this.mWorkerThread = handlerThread;
        handlerThread.start();
        SimpleClock anonymousClass2 = new AnonymousClass2(ZoneOffset.UTC);
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        this.mConnectivityManager =
                (ConnectivityManager) getActivity().getSystemService(ConnectivityManager.class);
        this.mSavedScannedTracker =
                new SavedScannedTracker(
                        getSettingsLifecycle(),
                        this.mContext,
                        this.mWifiManager,
                        this.mConnectivityManager,
                        new Handler(Looper.getMainLooper()),
                        this.mWorkerThread.getThreadHandler(),
                        anonymousClass2,
                        this);
        this.mIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.mIntentFilter.addAction("android.net.wifi.CONFIGURED_NETWORKS_CHANGE");
        this.mIntentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        this.mLogRepository = LogRepository.LazyHolder.INSTANCE;
        this.mStepEntry = this.mSavedScannedTracker.getConnectedWifiEntry();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_developer_wifi_diagnosis_fragment,
                        (ViewGroup) onCreateView);
        List savedWifiEntries = this.mSavedScannedTracker.getSavedWifiEntries();
        this.mAvailableCategory = (TextView) inflate.findViewById(R.id.available_network_category);
        this.mAvailableAdapter =
                new DiagnosisAvailableWifiEntryListAdapter(getContext(), savedWifiEntries);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.available_list);
        this.mAvailableView = recyclerView;
        DiagnosisAvailableWifiEntryListAdapter diagnosisAvailableWifiEntryListAdapter =
                this.mAvailableAdapter;
        diagnosisAvailableWifiEntryListAdapter.mListener = this;
        setupEntryRecyclerView(recyclerView, diagnosisAvailableWifiEntryListAdapter);
        this.mConnectedCategory = (TextView) inflate.findViewById(R.id.connected_network_category);
        this.mConnectedView = (RecyclerView) inflate.findViewById(R.id.connected_list);
        DiagnosisConnectedWifiEntryListAdapter diagnosisConnectedWifiEntryListAdapter =
                new DiagnosisConnectedWifiEntryListAdapter(getContext());
        this.mConnectedAdapter = diagnosisConnectedWifiEntryListAdapter;
        setupEntryRecyclerView(this.mConnectedView, diagnosisConnectedWifiEntryListAdapter);
        this.mStepLogView = (RecyclerView) inflate.findViewById(R.id.step_log_list);
        DiagnosisStepLogListAdapter diagnosisStepLogListAdapter =
                new DiagnosisStepLogListAdapter(getContext());
        this.mStepLogAdapter = diagnosisStepLogListAdapter;
        RecyclerView recyclerView2 = this.mStepLogView;
        getContext();
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        recyclerView2.setAdapter(diagnosisStepLogListAdapter);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        recyclerView2.semSetRoundedCorners(15);
        recyclerView2.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mEmptyView = (TextView) inflate.findViewById(R.id.no_saved_scanned_ap_text);
        Button button = (Button) inflate.findViewById(R.id.go_to_manage_network);
        this.mGoToManageNetwork = button;
        button.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.develop.diagnosis.WifiDiagnosisSettings$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiDiagnosisSettings wifiDiagnosisSettings = WifiDiagnosisSettings.this;
                        wifiDiagnosisSettings.getClass();
                        Bundle bundle2 = new Bundle();
                        bundle2.putBoolean("development_mode", true);
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(wifiDiagnosisSettings.mContext);
                        String canonicalName = SavedWifiEntrySettings.class.getCanonicalName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = canonicalName;
                        launchRequest.mArguments = bundle2;
                        launchRequest.mSourceMetricsCategory = 0;
                        subSettingLauncher.launch();
                    }
                });
        updateWifiEntries$3();
        return onCreateView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mWorkerThread.quit();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mReceiver, this.mIntentFilter);
        updateWifiEntries$3();
    }

    @Override // com.samsung.android.wifitrackerlib.SavedScannedTracker.SavedScannedTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        updateWifiEntries$3();
    }

    @Override // com.samsung.android.wifitrackerlib.SavedScannedTracker.SavedScannedTrackerCallback
    public final void onSubscriptionWifiEntriesChanged() {
        updateWifiEntries$3();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        int wifiState = this.mSavedScannedTracker.getWifiState();
        if (wifiState == 0 || wifiState == 1 || wifiState == 2) {
            setButtonEnabled$2(false);
        } else {
            if (wifiState != 3) {
                return;
            }
            setButtonEnabled$2(true);
        }
    }

    public final void setButtonEnabled$2(boolean z) {
        this.mGoToManageNetwork.setEnabled(z);
        this.mGoToManageNetwork.setAlpha(z ? 1.0f : 0.4f);
    }

    public final void setupEntryRecyclerView(
            RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        recyclerView.semSetRoundedCorners(15);
        recyclerView.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
    }

    public final void updateStepLog() {
        this.mLogRepository.updateRouterHistoryLog(this.mContext);
        WifiEntry wifiEntry = this.mStepEntry;
        if (wifiEntry == null) {
            Log.d("WifiDiagnosisSettings", "[updateStepLog] mStepEntry is null");
            return;
        }
        String ssid = wifiEntry.getSsid();
        DialogFragment$$ExternalSyntheticOutline0.m(
                "[updateStepLog] SSID : ", ssid, "WifiDiagnosisSettings");
        LogRepository logRepository = this.mLogRepository;
        String[] strArr =
                (String[])
                        logRepository
                                .mLogBySsid
                                .keySet()
                                .toArray(new String[logRepository.mLogBySsid.size()]);
        int length = strArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            String str = strArr[i];
            if (str.contains(ssid)) {
                ssid = str;
                break;
            }
            i++;
        }
        DialogFragment$$ExternalSyntheticOutline0.m(
                "[updateStepLog] SSID + key : ", ssid, "WifiDiagnosisSettings");
        ArrayList arrayList = (ArrayList) this.mLogRepository.mLogBySsid.get(ssid);
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        if (!arrayList.isEmpty()) {
            DiagnosisStepLogListAdapter diagnosisStepLogListAdapter = this.mStepLogAdapter;
            diagnosisStepLogListAdapter.mLogs = arrayList;
            diagnosisStepLogListAdapter.notifyDataSetChanged();
        }
        this.mStepLogView.scrollToPosition(this.mStepLogAdapter.getItemCount() - 1);
    }

    public final void updateWifiEntries$3() {
        List savedWifiEntries = this.mSavedScannedTracker.getSavedWifiEntries();
        StringBuilder sb = new StringBuilder("Get new WifiEntries ");
        ArrayList arrayList = (ArrayList) savedWifiEntries;
        sb.append(arrayList.size());
        Log.d("WifiDiagnosisSettings", sb.toString());
        setButtonEnabled$2(arrayList.size() != 0);
        WifiEntry connectedWifiEntry = this.mSavedScannedTracker.getConnectedWifiEntry();
        if (connectedWifiEntry != null) {
            Log.d("WifiDiagnosisSettings", "connected network " + connectedWifiEntry.getTitle());
            this.mConnectedCategory.setVisibility(0);
            this.mConnectedView.setVisibility(0);
            arrayList.remove(connectedWifiEntry);
            DiagnosisConnectedWifiEntryListAdapter diagnosisConnectedWifiEntryListAdapter =
                    this.mConnectedAdapter;
            diagnosisConnectedWifiEntryListAdapter.mWifiEntry = connectedWifiEntry;
            diagnosisConnectedWifiEntryListAdapter.notifyDataSetChanged();
        } else {
            this.mConnectedCategory.setVisibility(8);
            this.mConnectedView.setVisibility(8);
        }
        this.mAvailableAdapter.updateAllAccessPoints(savedWifiEntries);
        boolean z = this.mAvailableAdapter.getItemCount() > 0 || connectedWifiEntry != null;
        if (this.mWifiManager.isWifiEnabled()) {
            this.mEmptyView.setText(R.string.wifi_no_saved_network);
        } else {
            this.mEmptyView.setText(R.string.wifi_empty_list_wifi_off);
        }
        this.mEmptyView.setVisibility(z ? 8 : 0);
        boolean z2 = this.mAvailableAdapter.getItemCount() > 0;
        this.mAvailableCategory.setVisibility(z2 ? 0 : 8);
        this.mAvailableView.setVisibility(z2 ? 0 : 8);
    }
}
