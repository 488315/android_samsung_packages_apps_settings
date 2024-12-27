package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowInsets;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.SeslContentViewInsetsCallback;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;

import com.google.android.material.appbar.AppBarLayout;
import com.samsung.android.settings.logging.SALogging;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiNetworkAddFragment extends DashboardFragment {
    public AppBarLayout mAppBarLayout;
    public WifiImeHelper mImeHelper;
    public WifiManager mWifiManager;
    public boolean mIsSetupwizardFinish = false;
    public final List mControllers = new ArrayList();
    public final WifiNetworkAddFragment$$ExternalSyntheticLambda0 wifiExpandListener =
            new WifiExpandablePreferenceController
                    .WifiExpandListener() { // from class:
                                            // com.samsung.android.settings.wifi.details.WifiNetworkAddFragment$$ExternalSyntheticLambda0
                @Override // com.samsung.android.settings.wifi.details.WifiExpandablePreferenceController.WifiExpandListener
                public final void onSpread() {
                    ((ArrayList) WifiNetworkAddFragment.this.mControllers)
                            .forEach(new WifiNetworkAddFragment$$ExternalSyntheticLambda2());
                }
            };

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        WifiConnectPreferenceController wifiConnectPreferenceController =
                new WifiConnectPreferenceController(
                        null,
                        false,
                        this,
                        context,
                        getSettingsLifecycle(),
                        (WifiManager) context.getSystemService(WifiManager.class),
                        this.mImeHelper,
                        "WIFI_105");
        WifiExpandablePreferenceController wifiExpandablePreferenceController =
                new WifiExpandablePreferenceController(
                        context, this, getSettingsLifecycle(), this.mImeHelper, "WIFI_105");
        wifiExpandablePreferenceController.mExpandListener = this.wifiExpandListener;
        WifiEapPreferenceController wifiEapPreferenceController =
                new WifiEapPreferenceController(
                        context, this, null, this.mImeHelper, getSettingsLifecycle());
        wifiConnectPreferenceController.mEapMethodChangeListener =
                wifiEapPreferenceController.mEapMethodChangeListener;
        WifiNetworkConfigPreferenceController wifiNetworkConfigPreferenceController =
                new WifiNetworkConfigPreferenceController(
                        null,
                        (ConnectivityManager) context.getSystemService(ConnectivityManager.class),
                        context,
                        this,
                        this.mImeHelper,
                        "WIFI_105");
        WifiPrivacyPreferenceController wifiPrivacyPreferenceController =
                new WifiPrivacyPreferenceController(context, null, this, "WIFI_105");
        WifiDhcpNameSharingPreferenceController wifiDhcpNameSharingPreferenceController =
                new WifiDhcpNameSharingPreferenceController(
                        context, null, this, this.mWifiManager, "WIFI_105");
        ((ArrayList) this.mControllers).add(wifiConnectPreferenceController);
        ((ArrayList) this.mControllers)
                .add(
                        new WifiAutoConnectPreferenceController(
                                context, null, this, this.mWifiManager, "WIFI_105"));
        ((ArrayList) this.mControllers).add(wifiExpandablePreferenceController);
        ((ArrayList) this.mControllers).add(wifiEapPreferenceController);
        ((ArrayList) this.mControllers).add(wifiNetworkConfigPreferenceController);
        ((ArrayList) this.mControllers)
                .add(new WifiHiddenNetworkPreferenceController(context, null, "WIFI_105"));
        ((ArrayList) this.mControllers)
                .add(new WifiMeteredPreferenceController(context, null, this, "WIFI_105"));
        ((ArrayList) this.mControllers).add(wifiPrivacyPreferenceController);
        ((ArrayList) this.mControllers).add(wifiDhcpNameSharingPreferenceController);
        new SecWifiPreferenceControllerHelper()
                .addValidator(
                        wifiNetworkConfigPreferenceController, wifiConnectPreferenceController);
        return this.mControllers;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiAddNetworkFrg";
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
        this.mWifiManager = (WifiManager) context.getSystemService(WifiManager.class);
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
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        SALogging.insertSALog("WIFI_105");
        AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        this.mAppBarLayout = appBarLayout;
        if (appBarLayout != null && appBarLayout.getVisibility() != 0) {
            this.mAppBarLayout.setVisibility(0);
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
        getListView().mDrawLastRoundedCorner = false;
        getListView().seslSetFillBottomEnabled(true);
        getListView()
                .setOnFocusChangeListener(
                        new View
                                .OnFocusChangeListener() { // from class:
                                                           // com.samsung.android.settings.wifi.details.WifiNetworkAddFragment$$ExternalSyntheticLambda1
                            @Override // android.view.View.OnFocusChangeListener
                            public final void onFocusChange(View view2, boolean z) {
                                WifiNetworkAddFragment wifiNetworkAddFragment =
                                        WifiNetworkAddFragment.this;
                                wifiNetworkAddFragment.getClass();
                                if (z) {
                                    AbsAdapter$$ExternalSyntheticOutline0.m(
                                            "onFocusChange : ", "WifiAddNetworkFrg", z);
                                    wifiNetworkAddFragment.mImeHelper.hideSoftKeyboard(view2);
                                }
                            }
                        });
    }
}
