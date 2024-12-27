package com.samsung.android.settings.wifi.develop.diagnosis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.DiagnosisConnectedWifiEntryListAdapter;
import com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.NetworkDiagnosisInformationAdapter;
import com.samsung.android.wifi.SemWifiManager;
import com.samsung.android.wifitrackerlib.SavedScannedTracker;
import com.sec.ims.IMSParameter;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;

import java.net.URL;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NetworkDiagnosisSettings extends SettingsPreferenceFragment
        implements SavedScannedTracker.SavedScannedTrackerCallback {
    public DiagnosisConnectedWifiEntryListAdapter mConnectedAdapter;
    public TextView mConnectedCategory;
    public RecyclerView mConnectedView;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public Button mDadTest;
    public TextView mEmptyView;
    public Button mLaunchConnectivityTest;
    public Button mLaunchDnsTest;
    public SavedScannedTracker mSavedScannedTracker;
    public TextView mStepView;
    public NetworkDiagnosisInformationAdapter mTestInfoAdapter;
    public TextView mTestInfoCategory;
    public RecyclerView mTestInformation;
    public WifiManager mWifiManager;
    public HandlerThread mWorkerThread;
    public final IntentFilter mIntentFilter = new IntentFilter();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings.1
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
                                    "NetworkDiagnosisSettings",
                                    "Network state changed " + networkInfo.getDetailedState());
                            if (networkInfo
                                            .getDetailedState()
                                            .equals(NetworkInfo.DetailedState.CONNECTED)
                                    || networkInfo
                                            .getDetailedState()
                                            .equals(NetworkInfo.DetailedState.DISCONNECTED)) {
                                NetworkDiagnosisSettings.this.mSavedScannedTracker
                                        .getConnectedWifiEntry();
                            }
                            NetworkDiagnosisSettings.this.updateWifiEntries$2();
                            break;
                        case "android.net.wifi.supplicant.STATE_CHANGE":
                            intent.getParcelableExtra("newState");
                            break;
                        case "android.net.wifi.CONFIGURED_NETWORKS_CHANGE":
                            NetworkDiagnosisSettings.this.updateWifiEntries$2();
                            break;
                    }
                }
            };
    public String result = ApnSettings.MVNO_NONE;
    public final AnonymousClass3 mHandler =
            new Handler(
                    Looper
                            .getMainLooper()) { // from class:
                                                // com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings.3
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    NetworkDiagnosisSettings.this.mStepView.append(
                            message.getData().getString("result"));
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends SimpleClock {
        public final long millis() {
            return SystemClock.elapsedRealtime();
        }
    }

    public static String getHttpUrl(String str) {
        String lowerCase = str.toLowerCase();
        if (lowerCase.length() == 0) {
            return ApnSettings.MVNO_NONE;
        }
        if (lowerCase.startsWith("https://")) {
            lowerCase = lowerCase.replace("https://", "http://");
        }
        if (!lowerCase.startsWith("http://")) {
            lowerCase = "http://".concat(lowerCase);
        }
        try {
            new URL(lowerCase).toURI();
            return lowerCase;
        } catch (Exception unused) {
            return ApnSettings.MVNO_NONE;
        }
    }

    public final Network getCurrentWifiNetwork() {
        for (Network network : this.mConnectivityManager.getAllNetworks()) {
            NetworkCapabilities networkCapabilities =
                    this.mConnectivityManager.getNetworkCapabilities(network);
            if (networkCapabilities != null && networkCapabilities.hasTransport(1)) {
                return network;
            }
        }
        return null;
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
                        "NetworkDiagnosisSettings{"
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
        this.mSavedScannedTracker.getConnectedWifiEntry();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View inflate =
                layoutInflater.inflate(
                        R.layout.sec_wifi_developer_network_diagnosis_fragment, viewGroup, false);
        this.mConnectedCategory = (TextView) inflate.findViewById(R.id.connected_network_category);
        this.mConnectedView = (RecyclerView) inflate.findViewById(R.id.connected_list);
        this.mConnectedAdapter = new DiagnosisConnectedWifiEntryListAdapter(getContext());
        this.mTestInfoCategory = (TextView) inflate.findViewById(R.id.test_information_category);
        this.mTestInformation = (RecyclerView) inflate.findViewById(R.id.test_information);
        this.mTestInfoAdapter = new NetworkDiagnosisInformationAdapter(getContext());
        RecyclerView recyclerView = this.mConnectedView;
        DiagnosisConnectedWifiEntryListAdapter diagnosisConnectedWifiEntryListAdapter =
                this.mConnectedAdapter;
        getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(1));
        recyclerView.setAdapter(diagnosisConnectedWifiEntryListAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        recyclerView.semSetRoundedCorners(15);
        recyclerView.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        RecyclerView recyclerView2 = this.mTestInformation;
        NetworkDiagnosisInformationAdapter networkDiagnosisInformationAdapter =
                this.mTestInfoAdapter;
        getContext();
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        recyclerView2.setAdapter(networkDiagnosisInformationAdapter);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        recyclerView2.semSetRoundedCorners(15);
        recyclerView2.semSetRoundedCornerColor(
                15, this.mContext.getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        Button button = (Button) inflate.findViewById(R.id.launch_connectivity_test);
        this.mLaunchConnectivityTest = button;
        final int i = 0;
        button.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NetworkDiagnosisSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    /* JADX WARN: Can't wrap try/catch for region: R(14:65|(18:104|(1:106)|107|(1:109)|110|111|112|68|69|70|71|(2:73|(8:75|(1:77)|78|79|(2:81|(3:83|(1:85)(1:87)|86)(1:88))|89|(2:91|(3:93|(1:95)|96)(1:97))|98)(1:100))|101|79|(0)|89|(0)|98)|67|68|69|70|71|(0)|101|79|(0)|89|(0)|98) */
                    /* JADX WARN: Code restructure failed: missing block: B:103:0x018a, code lost:

                       r0 = "INVALID";
                    */
                    /* JADX WARN: Removed duplicated region for block: B:73:0x0193  */
                    /* JADX WARN: Removed duplicated region for block: B:81:0x01c2  */
                    /* JADX WARN: Removed duplicated region for block: B:91:0x01f4  */
                    @Override // android.view.View.OnClickListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onClick(android.view.View r17) {
                        /*
                            Method dump skipped, instructions count: 579
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
        Button button2 = (Button) inflate.findViewById(R.id.launch_dns_test);
        this.mLaunchDnsTest = button2;
        final int i2 = 1;
        button2.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NetworkDiagnosisSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        /*
                            Method dump skipped, instructions count: 579
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
        Button button3 = (Button) inflate.findViewById(R.id.check_dad);
        this.mDadTest = button3;
        final int i3 = 2;
        button3.setOnClickListener(
                new View.OnClickListener(this) { // from class:
                    // com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0
                    public final /* synthetic */ NetworkDiagnosisSettings f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        /*
                            Method dump skipped, instructions count: 579
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.settings.wifi.develop.diagnosis.NetworkDiagnosisSettings$$ExternalSyntheticLambda0.onClick(android.view.View):void");
                    }
                });
        this.mEmptyView = (TextView) inflate.findViewById(R.id.no_saved_scanned_ap_text);
        TextView textView = (TextView) inflate.findViewById(R.id.step_list);
        this.mStepView = textView;
        textView.setMovementMethod(new ScrollingMovementMethod());
        updateWifiEntries$2();
        return inflate;
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
        this.mStepView.setText(ApnSettings.MVNO_NONE);
        updateWifiEntries$2();
    }

    @Override // com.samsung.android.wifitrackerlib.SavedScannedTracker.SavedScannedTrackerCallback
    public final void onSavedWifiEntriesChanged() {
        updateWifiEntries$2();
    }

    @Override // com.samsung.android.wifitrackerlib.SavedScannedTracker.SavedScannedTrackerCallback
    public final void onSubscriptionWifiEntriesChanged() {
        updateWifiEntries$2();
    }

    @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
    public final void onWifiStateChanged() {
        updateWifiEntries$2();
    }

    public final void setButtonEnabled$1(boolean z) {
        this.mLaunchConnectivityTest.setEnabled(z);
        this.mLaunchConnectivityTest.setAlpha(z ? 1.0f : 0.4f);
        this.mLaunchDnsTest.setEnabled(z);
        this.mLaunchDnsTest.setAlpha(z ? 1.0f : 0.4f);
        this.mDadTest.setEnabled(z);
        this.mDadTest.setAlpha(z ? 1.0f : 0.4f);
    }

    public final void setConnectedVisibility(boolean z) {
        this.mConnectedCategory.setVisibility(z ? 0 : 8);
        this.mTestInfoCategory.setVisibility(z ? 0 : 8);
        this.mConnectedView.setVisibility(z ? 0 : 8);
        this.mTestInformation.setVisibility(z ? 0 : 8);
    }

    public final void updateWifiEntries$2() {
        List savedWifiEntries = this.mSavedScannedTracker.getSavedWifiEntries();
        StringBuilder sb = new StringBuilder("Get new WifiEntries ");
        ArrayList arrayList = (ArrayList) savedWifiEntries;
        sb.append(arrayList.size());
        Log.d("NetworkDiagnosisSettings", sb.toString());
        WifiEntry connectedWifiEntry = this.mSavedScannedTracker.getConnectedWifiEntry();
        if (connectedWifiEntry != null) {
            Log.d("NetworkDiagnosisSettings", "connected network " + connectedWifiEntry.getTitle());
            setConnectedVisibility(true);
            arrayList.remove(connectedWifiEntry);
            DiagnosisConnectedWifiEntryListAdapter diagnosisConnectedWifiEntryListAdapter =
                    this.mConnectedAdapter;
            diagnosisConnectedWifiEntryListAdapter.mWifiEntry = connectedWifiEntry;
            diagnosisConnectedWifiEntryListAdapter.notifyDataSetChanged();
            setButtonEnabled$1(true);
        } else {
            setConnectedVisibility(false);
            setButtonEnabled$1(false);
        }
        this.mEmptyView.setVisibility(connectedWifiEntry != null ? 8 : 0);
        if (connectedWifiEntry == null) {
            return;
        }
        if (connectedWifiEntry.canSignIn()) {
            this.mTestInfoAdapter.infoList[0] = "Captive Portal network. Sign in required";
        }
        int currentStatusMode =
                ((SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                        .getCurrentStatusMode();
        if (currentStatusMode == 0) {
            this.mTestInfoAdapter.infoList[0] =
                    "The current network provides internet connectivity";
            return;
        }
        if (currentStatusMode == 1 || currentStatusMode == 2) {
            this.mTestInfoAdapter.infoList[0] =
                    "The current network doesn't provide internet connectivity";
        } else if (currentStatusMode != 3) {
            this.mTestInfoAdapter.infoList[0] = "Connected";
        } else {
            this.mTestInfoAdapter.infoList[0] =
                    "The current network provides internet connectivity.However, the device is"
                        + " using mobile data because the internet is slow or unstable.";
        }
    }
}
