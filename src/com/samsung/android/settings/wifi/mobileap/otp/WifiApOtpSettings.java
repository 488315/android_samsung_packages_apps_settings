package com.samsung.android.settings.wifi.mobileap.otp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.SoftApConfiguration;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamMediaService$4$$ExternalSyntheticOutline0;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settingslib.widget.LayoutPreference;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarMenu;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.WifiApQrCodeFragment;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApMobileDataSharedTodayPreferenceController;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApOtpSettings extends DashboardFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FragmentActivity mContext;
    public LayoutPreference mHotspotDescriptionLayoutPreference;
    public WifiApOtpSettingsBottomView mSettingsBottomView;
    public SettingsMainSwitchBar mSettingsMainSwitchBar;
    public boolean mSwitchStateChangedAfterApEnabled = false;
    public WifiApOtpSettingsPasswordPreferenceController
            mWifiApOtpSettingsPasswordPreferenceController;
    public WifiApStateChangeReceiver mWifiApStateChangeReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiApStateChangeReceiver extends BroadcastReceiver {
        public WifiApStateChangeReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = WifiApOtpSettings.$r8$clinit;
            if (AudioStreamMediaService$4$$ExternalSyntheticOutline0.m(
                    "Broadcast Received: ",
                    action,
                    "WifiApOtpSettings",
                    WifiApMobileDataSharedTodayPreferenceController.ACTION_WIFI_AP_STATE_CHANGED)) {
                int intExtra = intent.getIntExtra("wifi_state", 14);
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                        intExtra, "onReceive:  Wi-Fi AP State: ", "WifiApOtpSettings");
                WifiApOtpSettings wifiApOtpSettings = WifiApOtpSettings.this;
                if (!wifiApOtpSettings.mSwitchStateChangedAfterApEnabled) {
                    if (intExtra != 13 && intExtra != 11) {
                        wifiApOtpSettings.mSettingsMainSwitchBar.setEnabled(false);
                        return;
                    } else {
                        wifiApOtpSettings.mSettingsMainSwitchBar.setEnabled(true);
                        WifiApOtpSettings.this.updatePreferences$1();
                        return;
                    }
                }
                if (intExtra == 13) {
                    wifiApOtpSettings.mSwitchStateChangedAfterApEnabled = false;
                    wifiApOtpSettings.mSettingsMainSwitchBar.setEnabled(true);
                    WifiApOtpSettings.this.updatePreferences$1();
                } else if (intExtra == 11) {
                    wifiApOtpSettings.updatePreferences$1();
                }
            }
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WifiApOtpSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_wifi_ap_one_time_password_settings;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSettingsMainSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.show();
        WifiApOtpSettingsBottomView wifiApOtpSettingsBottomView = new WifiApOtpSettingsBottomView();
        Log.i("WifiApOtpSettingsBottomView", "WifiApQrInfoBottomView() - Start");
        Context context = getContext();
        wifiApOtpSettingsBottomView.mContext = context;
        Activity activity = (Activity) context;
        WifiApSettingsUtils.removeAlphaColor(context);
        Log.i("WifiApOtpSettingsBottomView", "initBottomBar() - Start");
        RelativeLayout relativeLayout = (RelativeLayout) activity.findViewById(R.id.button_bar);
        wifiApOtpSettingsBottomView.mButtonBar = relativeLayout;
        if (relativeLayout != null) {
            relativeLayout.setVisibility(0);
            wifiApOtpSettingsBottomView.mButtonBar.removeAllViews();
            wifiApOtpSettingsBottomView.mButtonBar.addView(
                    (BottomNavigationView)
                            ((LayoutInflater) activity.getSystemService("layout_inflater"))
                                    .inflate(
                                            R.layout.sec_wifi_ap_guest_password_bottom_layout,
                                            (ViewGroup) wifiApOtpSettingsBottomView.mButtonBar,
                                            false));
        }
        Log.i("WifiApOtpSettingsBottomView", "initBottomNavigation() - Start");
        BottomNavigationView bottomNavigationView =
                (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);
        wifiApOtpSettingsBottomView.mBottomNavigationView = bottomNavigationView;
        NavigationBarMenu navigationBarMenu = bottomNavigationView.menu;
        navigationBarMenu.findItem(R.id.qr_code_button);
        navigationBarMenu.findItem(R.id.info_button);
        wifiApOtpSettingsBottomView.mBottomNavigationView.selectedListener =
                new BottomNavigationView
                        .OnNavigationItemSelectedListener() { // from class:
                                                              // com.samsung.android.settings.wifi.mobileap.otp.WifiApOtpSettingsBottomView.1
                    public AnonymousClass1() {}

                    @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
                    public final boolean onNavigationItemSelected(MenuItem menuItem) {
                        SoftApConfiguration softApConfiguration;
                        int itemId = menuItem.getItemId();
                        WifiApOtpSettingsBottomView wifiApOtpSettingsBottomView2 =
                                WifiApOtpSettingsBottomView.this;
                        if (itemId == R.id.qr_code_button) {
                            Log.i("WifiApOtpSettingsBottomView", "QR Button Clicked");
                            SALogging.insertSALog("TETH_015", "8089");
                            wifiApOtpSettingsBottomView2.getClass();
                            Log.i("WifiApOtpSettingsBottomView", "launchQrCodeActivity() - Start");
                            SoftApConfiguration softApConfiguration2 =
                                    wifiApOtpSettingsBottomView2.mSemWifiManager
                                            .getSoftApConfiguration();
                            int securityType = softApConfiguration2.getSecurityType();
                            MainClearConfirm$$ExternalSyntheticOutline0.m(
                                    securityType,
                                    "launchQrCodeActivity buildNewConfig() - securityType",
                                    "WifiApOtpSettingsBottomView");
                            if (securityType == 1) {
                                SoftApConfiguration.Builder builder =
                                        new SoftApConfiguration.Builder(softApConfiguration2);
                                builder.setPassphrase(
                                        wifiApOtpSettingsBottomView2.mSemWifiManager
                                                .getWifiApGuestPassword(),
                                        1);
                                softApConfiguration = builder.build();
                            } else {
                                softApConfiguration = null;
                            }
                            if (softApConfiguration != null) {
                                Bundle bundle2 = new Bundle();
                                bundle2.putParcelable("key_config", softApConfiguration);
                                SubSettingLauncher subSettingLauncher =
                                        new SubSettingLauncher(
                                                wifiApOtpSettingsBottomView2.mContext);
                                SubSettingLauncher.LaunchRequest launchRequest =
                                        subSettingLauncher.mLaunchRequest;
                                launchRequest.mExtras = bundle2;
                                launchRequest.mArguments = bundle2;
                                launchRequest.mSourceMetricsCategory = 3400;
                                launchRequest.mDestinationName =
                                        WifiApQrCodeFragment.class.getCanonicalName();
                                subSettingLauncher.launch();
                            } else {
                                Toast.makeText(
                                                wifiApOtpSettingsBottomView2.mContext,
                                                "MHS Security type is not WPA2, please set to Wpa2",
                                                1)
                                        .show();
                            }
                        } else if (itemId == R.id.info_button) {
                            Log.i("WifiApOtpSettingsBottomView", "Reset Button Clicked");
                            SALogging.insertSALog("TETH_015", "8090");
                            if (WifiApFrameworkUtils.isOtpPasswordEnabled(
                                            wifiApOtpSettingsBottomView2.mContext)
                                    && WifiApFrameworkUtils.isWifiApStateEnabled(
                                            wifiApOtpSettingsBottomView2.mContext)) {
                                Context context2 = wifiApOtpSettingsBottomView2.mContext;
                                Log.i("WifiApFrameworkUtils", "setOtpPassword: ");
                                WifiApFrameworkUtils.getSemWifiManager(context2)
                                        .setWifiApGuestPassword("abcdefgh");
                                WifiApFrameworkUtils.resetSoftAp(
                                        wifiApOtpSettingsBottomView2.mContext);
                                if (Utils.isTalkBackEnabled(
                                        wifiApOtpSettingsBottomView2.mContext)) {
                                    Toast.makeText(
                                                    wifiApOtpSettingsBottomView2.mContext,
                                                    R.string.wifi_ap_password_reset,
                                                    0)
                                            .show();
                                }
                            }
                        }
                        return true;
                    }
                };
        wifiApOtpSettingsBottomView.mButtonBar.setVisibility(0);
        wifiApOtpSettingsBottomView.mSemWifiManager =
                (SemWifiManager) context.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mSettingsBottomView = wifiApOtpSettingsBottomView;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mWifiApOtpSettingsPasswordPreferenceController =
                (WifiApOtpSettingsPasswordPreferenceController)
                        use(WifiApOtpSettingsPasswordPreferenceController.class);
        this.mWifiApStateChangeReceiver = new WifiApStateChangeReceiver();
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("onSwitchChanged: ", "WifiApOtpSettings", z);
        SALogging.insertSALog(z ? 1L : 0L, "TETH_015", "8088", (String) null);
        if (WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext) != z) {
            WifiApFrameworkUtils.setOtpPasswordEnabled(this.mContext, z);
            if (WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)) {
                FragmentActivity fragmentActivity = this.mContext;
                Log.i("WifiApFrameworkUtils", "setOtpPassword: ");
                WifiApFrameworkUtils.getSemWifiManager(fragmentActivity)
                        .setWifiApGuestPassword("abcdefgh");
                this.mSettingsMainSwitchBar.setEnabled(false);
                this.mSwitchStateChangedAfterApEnabled = true;
            }
            FragmentActivity fragmentActivity2 = this.mContext;
            Log.i("WifiApFrameworkUtils", "Resetting Hotspot  Without Delay");
            WifiApFrameworkUtils.getSemWifiManager(fragmentActivity2).resetSoftAp((Message) null);
        }
        updatePreferences$1();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mContext = activity;
        if (!WifiApFeatureUtils.isOneTimePasswordSupported(activity)
                || WifiApSoftApUtils.getSecurityType(this.mContext) != 1) {
            Log.e("WifiApOtpSettings", "Conditions not met to open screen.");
            finish();
        }
        LayoutPreference layoutPreference =
                (LayoutPreference)
                        getPreferenceScreen().findPreference("otp_description_layout_preference");
        this.mHotspotDescriptionLayoutPreference = layoutPreference;
        layoutPreference.seslSetSubheaderRoundedBackground(0);
        ((TextView) this.mHotspotDescriptionLayoutPreference.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_ap_you_can_share_this_password_description);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSettingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.hide();
        }
        super.onDestroyView();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Log.i("WifiApOtpSettings", "Navigate Up clicked");
            SALogging.insertSALog("TETH_015", "8101");
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        this.mSettingsMainSwitchBar.setChecked(
                WifiApFrameworkUtils.isOtpPasswordEnabled(this.mContext));
        if (WifiApFrameworkUtils.isWifiApStateEnabled(this.mContext)
                || WifiApFrameworkUtils.getSemWifiManager(this.mContext).getWifiApState() == 11) {
            this.mSettingsMainSwitchBar.setEnabled(true);
        } else {
            this.mSettingsMainSwitchBar.setEnabled(false);
        }
        this.mSettingsBottomView.update();
        updatePreferences$1();
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        Log.i("WifiApOtpSettings", "onStart");
        super.onStart();
        this.mSettingsMainSwitchBar.addOnSwitchChangeListener(this);
        this.mContext.registerReceiver(
                this.mWifiApStateChangeReceiver,
                new IntentFilter(
                        WifiApMobileDataSharedTodayPreferenceController
                                .ACTION_WIFI_AP_STATE_CHANGED),
                2);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        Log.i("WifiApOtpSettings", "onStop");
        this.mSettingsMainSwitchBar.removeOnSwitchChangeListener(this);
        this.mContext.unregisterReceiver(this.mWifiApStateChangeReceiver);
        super.onStop();
    }

    public final void updatePreferences$1() {
        this.mWifiApOtpSettingsPasswordPreferenceController.updateState(getPreferenceScreen());
        this.mSettingsBottomView.update();
    }
}
