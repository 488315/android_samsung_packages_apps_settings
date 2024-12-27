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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.RelativeLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.wifitrackerlib.NetworkDetailsTracker;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiTrackerInjector;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.settings.logging.SALogging;
import com.sec.ims.settings.ImsProfile;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiNetworkModifyFragment extends DashboardFragment {
    public AppBarLayout mAppBarLayout;
    public WifiImeHelper mImeHelper;
    public NetworkDetailsTracker mNetworkDetailsTracker;
    public HandlerThread mWorkerThread;
    public boolean mIsSetupwizardFinish = false;
    public final List mControllers = new ArrayList();
    public final WifiNetworkModifyFragment$$ExternalSyntheticLambda0 wifiExpandListener =
            new WifiExpandablePreferenceController
                    .WifiExpandListener() { // from class:
                                            // com.samsung.android.settings.wifi.details.WifiNetworkModifyFragment$$ExternalSyntheticLambda0
                @Override // com.samsung.android.settings.wifi.details.WifiExpandablePreferenceController.WifiExpandListener
                public final void onSpread() {
                    ((ArrayList) WifiNetworkModifyFragment.this.mControllers)
                            .forEach(new WifiNetworkModifyFragment$$ExternalSyntheticLambda1());
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiNetworkModifyFragment$1, reason: invalid class name */
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
                            "WifiNetworkModifyFrg{"
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
                            getArguments().getString("key_modify_wifientry_key"));
        }
        WifiEntry wifiEntry = this.mNetworkDetailsTracker.getWifiEntry();
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        WifiModifyPreferenceController wifiModifyPreferenceController =
                new WifiModifyPreferenceController(
                        wifiEntry,
                        this,
                        context,
                        getActivity(),
                        getSettingsLifecycle(),
                        (WifiManager) context.getSystemService(WifiManager.class),
                        this.mImeHelper,
                        "WIFI_110");
        WifiExpandablePreferenceController wifiExpandablePreferenceController =
                new WifiExpandablePreferenceController(
                        context, this, getSettingsLifecycle(), this.mImeHelper, "WIFI_110");
        wifiExpandablePreferenceController.mExpandListener = this.wifiExpandListener;
        WifiEapPreferenceController wifiEapPreferenceController =
                new WifiEapPreferenceController(
                        context, this, wifiEntry, this.mImeHelper, getSettingsLifecycle());
        wifiModifyPreferenceController.mEapMethodChangeListener =
                wifiEapPreferenceController.mEapMethodChangeListener;
        WifiNetworkConfigPreferenceController wifiNetworkConfigPreferenceController =
                new WifiNetworkConfigPreferenceController(
                        wifiEntry,
                        (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                        context,
                        this,
                        this.mImeHelper,
                        "WIFI_110");
        WifiPrivacyPreferenceController wifiPrivacyPreferenceController =
                new WifiPrivacyPreferenceController(context, wifiEntry, this, "WIFI_110");
        WifiDhcpNameSharingPreferenceController wifiDhcpNameSharingPreferenceController =
                new WifiDhcpNameSharingPreferenceController(
                        context, wifiEntry, this, wifiManager, "WIFI_110");
        ((ArrayList) this.mControllers).add(wifiModifyPreferenceController);
        ((ArrayList) this.mControllers)
                .add(
                        new WifiAutoConnectPreferenceController(
                                context, wifiEntry, this, wifiManager, "WIFI_110"));
        ((ArrayList) this.mControllers).add(wifiExpandablePreferenceController);
        ((ArrayList) this.mControllers).add(wifiEapPreferenceController);
        ((ArrayList) this.mControllers).add(wifiNetworkConfigPreferenceController);
        ((ArrayList) this.mControllers).add(wifiPrivacyPreferenceController);
        ((ArrayList) this.mControllers).add(wifiDhcpNameSharingPreferenceController);
        ((ArrayList) this.mControllers)
                .add(new WifiMeteredPreferenceController(context, wifiEntry, this, "WIFI_110"));
        new SecWifiPreferenceControllerHelper()
                .addValidator(
                        wifiNetworkConfigPreferenceController, wifiModifyPreferenceController);
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiNetworkModifyFrg";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 849;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_network_modify_fragment;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        this.mImeHelper = new WifiImeHelper(context);
        this.mIsSetupwizardFinish =
                Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
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

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = super.onCreateView(layoutInflater, viewGroup, bundle);
        FragmentActivity activity = getActivity();
        View findViewById = activity.findViewById(R.id.button_bar);
        if (findViewById != null) {
            findViewById.setVisibility(0);
            ((RelativeLayout) findViewById)
                    .addView(
                            getLayoutInflater()
                                    .inflate(R.layout.sec_wifi_button_bar, (ViewGroup) null));
        }
        return onCreateView;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        this.mImeHelper.hideSoftKeyboard(getListView());
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_110");
        AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        this.mAppBarLayout = appBarLayout;
        if (appBarLayout != null && appBarLayout.getVisibility() != 0) {
            this.mAppBarLayout.setVisibility(0);
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.action_bar);
        if (toolbar != null) {
            toolbar.setContentInsetsAbsolute(
                    toolbar.getContentInsetStart(),
                    getResources().getDimensionPixelSize(R.dimen.sec_wifi_toolbar_inset_end));
        }
        this.mImeHelper.init(getActivity().getCurrentFocus(), getPreferenceScreen());
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
    }
}
