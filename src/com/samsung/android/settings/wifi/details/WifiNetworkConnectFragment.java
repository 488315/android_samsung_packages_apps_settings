package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SimpleClock;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiNetworkConnectFragment extends DashboardFragment {
    public AppBarLayout mAppBarLayout;
    public String mBssid;
    public int mDisableReason;
    public WifiImeHelper mImeHelper;
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public SemWifiManager mSemWifiManager;
    public WifiConnectPreferenceController mWifiConnectPreferenceController;
    public WifiManager mWifiManager;
    public HandlerThread mWorkerThread;
    public final List mControllers = new ArrayList();
    public boolean mIsSetupwizardFinish = false;
    public boolean mIsRetryDialog = false;
    public final WifiNetworkConnectFragment$$ExternalSyntheticLambda0 wifiExpandListener =
            new WifiExpandablePreferenceController
                    .WifiExpandListener() { // from class:
                                            // com.samsung.android.settings.wifi.details.WifiNetworkConnectFragment$$ExternalSyntheticLambda0
                @Override // com.samsung.android.settings.wifi.details.WifiExpandablePreferenceController.WifiExpandListener
                public final void onSpread() {
                    ((ArrayList) WifiNetworkConnectFragment.this.mControllers)
                            .forEach(new WifiNetworkConnectFragment$$ExternalSyntheticLambda2());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiNetworkConnectFragment$1, reason: invalid class name */
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
                            "WifiNetworkConnectFrg{"
                                    + Integer.toHexString(System.identityHashCode(this))
                                    + "}",
                            10);
            this.mWorkerThread = handlerThread;
            handlerThread.start();
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
                            getArguments().getString("key_connect_wifientry_key"));
        }
        WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
        WifiConnectPreferenceController wifiConnectPreferenceController =
                new WifiConnectPreferenceController(
                        wifiEntry,
                        this.mIsRetryDialog,
                        this,
                        context,
                        getSettingsLifecycle(),
                        this.mWifiManager,
                        this.mImeHelper,
                        "WIFI_107");
        this.mWifiConnectPreferenceController = wifiConnectPreferenceController;
        wifiConnectPreferenceController.mDisableReason = this.mDisableReason;
        String str = this.mBssid;
        if (TextUtils.isEmpty(wifiConnectPreferenceController.mBssid)) {
            wifiConnectPreferenceController.mBssid = str;
        } else {
            Log.d("WifiConnectPrefController", "BSSID is already set. ignore it");
        }
        ((ArrayList) this.mControllers).add(this.mWifiConnectPreferenceController);
        ((ArrayList) this.mControllers)
                .add(
                        new WifiAutoConnectPreferenceController(
                                context, wifiEntry, this, this.mWifiManager, "WIFI_107"));
        WifiExpandablePreferenceController wifiExpandablePreferenceController =
                new WifiExpandablePreferenceController(
                        context, this, getSettingsLifecycle(), this.mImeHelper, "WIFI_107");
        wifiExpandablePreferenceController.mExpandListener = this.wifiExpandListener;
        ((ArrayList) this.mControllers).add(wifiExpandablePreferenceController);
        WifiEapPreferenceController wifiEapPreferenceController =
                new WifiEapPreferenceController(
                        context, this, wifiEntry, this.mImeHelper, getSettingsLifecycle());
        this.mWifiConnectPreferenceController.mEapMethodChangeListener =
                wifiEapPreferenceController.mEapMethodChangeListener;
        ((ArrayList) this.mControllers).add(wifiEapPreferenceController);
        WifiNetworkConfigPreferenceController wifiNetworkConfigPreferenceController =
                new WifiNetworkConfigPreferenceController(
                        wifiEntry,
                        (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                        context,
                        this,
                        this.mImeHelper,
                        "WIFI_107");
        ((ArrayList) this.mControllers).add(wifiNetworkConfigPreferenceController);
        ((ArrayList) this.mControllers)
                .add(new WifiHiddenNetworkPreferenceController(context, wifiEntry, "WIFI_107"));
        ((ArrayList) this.mControllers)
                .add(new WifiMeteredPreferenceController(context, wifiEntry, this, "WIFI_107"));
        ((ArrayList) this.mControllers)
                .add(new WifiPrivacyPreferenceController(context, wifiEntry, this, "WIFI_107"));
        ((ArrayList) this.mControllers)
                .add(
                        new WifiDhcpNameSharingPreferenceController(
                                context, wifiEntry, this, this.mWifiManager, "WIFI_107"));
        new SecWifiPreferenceControllerHelper()
                .addValidator(
                        wifiNetworkConfigPreferenceController,
                        this.mWifiConnectPreferenceController);
        DialogFragment$$ExternalSyntheticOutline0.m(
                "Launch ConnectFragment ",
                wifiEntry != null ? wifiEntry.getTitle() : ApnSettings.MVNO_NONE,
                "WifiNetworkConnectFrg");
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiNetworkConnectFrg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 849;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_add_network_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mIsRetryDialog = arguments.getBoolean("DialogForRetry", false);
            this.mDisableReason = arguments.getInt("disableReason", 0);
            this.mBssid = arguments.getString("bssid");
        }
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
        this.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mIsSetupwizardFinish =
                Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        this.mImeHelper = new WifiImeHelper(context);
        super.onAttach(context);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (this.mIsSetupwizardFinish || supportActionBar == null) {
            return;
        }
        supportActionBar.setDisplayHomeAsUpEnabled(false);
        supportActionBar.setHomeButtonEnabled();
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mImeHelper.hideSoftKeyboard(getListView());
        this.mSemWifiManager.setWifiSettingsForegroundState(0);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mSemWifiManager.setWifiSettingsForegroundState(0);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_107");
        AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        this.mAppBarLayout = appBarLayout;
        if (appBarLayout != null && appBarLayout.getVisibility() != 0) {
            this.mAppBarLayout.setVisibility(0);
        }
        this.mImeHelper.init(getActivity().getCurrentFocus(), getPreferenceScreen());
        this.mSemWifiManager.setWifiSettingsForegroundState(1);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getActivity().getWindow().setDecorFitsSystemWindows(false);
        View findViewById = getActivity().findViewById(android.R.id.content);
        SeslContentViewInsetsCallback seslContentViewInsetsCallback =
                new SeslContentViewInsetsCallback(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(),
                        WindowInsets.Type.ime());
        findViewById.setWindowInsetsAnimationCallback(seslContentViewInsetsCallback);
        findViewById.setOnApplyWindowInsetsListener(seslContentViewInsetsCallback);
        getListView().mDrawLastRoundedCorner = false;
        getListView().seslSetFillBottomEnabled(true);
        getListView()
                .setOnFocusChangeListener(
                        new View
                                .OnFocusChangeListener() { // from class:
                                                           // com.samsung.android.settings.wifi.details.WifiNetworkConnectFragment$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnFocusChangeListener
                            public final void onFocusChange(View view2, boolean z) {
                                WifiNetworkConnectFragment wifiNetworkConnectFragment =
                                        WifiNetworkConnectFragment.this;
                                wifiNetworkConnectFragment.getClass();
                                if (z) {
                                    AbsAdapter$$ExternalSyntheticOutline0.m(
                                            "onFocusChange : ", "WifiNetworkConnectFrg", z);
                                    wifiNetworkConnectFragment.mImeHelper.hideSoftKeyboard(view2);
                                }
                            }
                        });
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar);
        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(
                    toolbar.getContentInsetStart(),
                    getResources().getDimensionPixelSize(R.dimen.sec_wifi_toolbar_inset_end));
        }
        WifiConnectPreferenceController wifiConnectPreferenceController =
                this.mWifiConnectPreferenceController;
        if (wifiConnectPreferenceController != null) {
            wifiConnectPreferenceController.mRecyclerView = getListView();
        }
    }
}
