package com.samsung.android.settings.wifi.intelligent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.glance.session.SessionManagerImpl$scope$1$$ExternalSyntheticOutline0;
import androidx.preference.SecPreferenceCategory;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.accounts.ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiExceptionNetworkSettings extends SettingsPreferenceFragment {
    public FragmentActivity mContext;
    public SecPreferenceCategory mExceptionList;
    public final AnonymousClass1 mMobileDataObserver = new ContentObserver(new Handler()) { // from class: com.samsung.android.settings.wifi.intelligent.WifiExceptionNetworkSettings.1
        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (Utils.updateSmartNetworkSwitchAvailability(WifiExceptionNetworkSettings.this.getActivity()) == 4) {
                WifiExceptionNetworkSettings.m1347$$Nest$mfinishWifiExceptionSettings(WifiExceptionNetworkSettings.this);
            }
        }
    };
    public final AnonymousClass3 mOnHierarchyChangeListener = new AnonymousClass3();
    public SemWifiManager mSemWifiManager;
    public AnonymousClass2 mWcmConfigChangeListener;
    public WifiWcmUnclickablePreference mWcmExceptionDesc;
    public WifiManager mWifiManager;

    /* renamed from: -$$Nest$mfinishWifiExceptionSettings, reason: not valid java name */
    public static void m1347$$Nest$mfinishWifiExceptionSettings(WifiExceptionNetworkSettings wifiExceptionNetworkSettings) {
        wifiExceptionNetworkSettings.getClass();
        if (Utils.isTablet() && wifiExceptionNetworkSettings.getFragmentManager() != null && wifiExceptionNetworkSettings.getFragmentManager().getBackStackEntryCount() > 0) {
            wifiExceptionNetworkSettings.getFragmentManager().popBackStack();
        } else if (wifiExceptionNetworkSettings.getActivity() != null) {
            wifiExceptionNetworkSettings.getActivity().finish();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 849;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mContext = getActivity();
        getListView().setImportantForAccessibility(2);
        updateExcludedNetworks();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [android.content.BroadcastReceiver, com.samsung.android.settings.wifi.intelligent.WifiExceptionNetworkSettings$2] */
    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        setHasOptionsMenu(false);
        setActionBarTitle$2();
        addPreferencesFromResource(R.xml.wifi_excluded_networks_settings);
        this.mWifiManager = (WifiManager) getActivity().getSystemService(ImsProfile.PDN_WIFI);
        this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        this.mWcmExceptionDesc = (WifiWcmUnclickablePreference) findPreference("wcm_exception_desc");
        this.mExceptionList = (SecPreferenceCategory) findPreference("wcm_exception_list");
        IntentFilter m = ManagedProfileQuietModeEnabler$$ExternalSyntheticOutline0.m("com.sec.android.WIFI_WCM_CONFIGURATION_CHANGED", "android.net.wifi.WIFI_STATE_CHANGED");
        ?? r0 = new BroadcastReceiver() { // from class: com.samsung.android.settings.wifi.intelligent.WifiExceptionNetworkSettings.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("com.sec.android.WIFI_WCM_CONFIGURATION_CHANGED")) {
                    Log.d("WifiExceptionNetworkSettings", "WIFI_WCM_CONFIGURATION_CHANGED");
                    WifiExceptionNetworkSettings.this.updateExcludedNetworks();
                } else if (action.equals("android.net.wifi.WIFI_STATE_CHANGED") && intent.getIntExtra("wifi_state", 4) == 1) {
                    WifiExceptionNetworkSettings.m1347$$Nest$mfinishWifiExceptionSettings(WifiExceptionNetworkSettings.this);
                }
            }
        };
        this.mWcmConfigChangeListener = r0;
        this.mContext.registerReceiver(r0, m, 2);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mContext.unregisterReceiver(this.mWcmConfigChangeListener);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        getActivity().getContentResolver().unregisterContentObserver(this.mMobileDataObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        setActionBarTitle$2();
        updateExcludedNetworks();
        getActivity().getContentResolver().registerContentObserver(Settings.Global.getUriFor("mobile_data"), false, this.mMobileDataObserver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        LoggingHelper.insertFlowLogging(3120);
    }

    public final void setActionBarTitle$2() {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(getResources().getString(R.string.wifi_smart_network_switch_excluded_networks));
        }
        getActivity().setTitle(getResources().getString(R.string.wifi_smart_network_switch_excluded_networks));
        ViewGroup viewGroup = (ViewGroup) getActivity().getWindow().getDecorView().findViewById(getResources().getIdentifier("action_bar", "id", RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME));
        if (viewGroup != null) {
            viewGroup.setOnHierarchyChangeListener(this.mOnHierarchyChangeListener);
        }
    }

    public final void updateExcludedNetworks() {
        this.mWcmExceptionDesc.setLayoutResource(R.layout.wifi_excluded_networks_settings_exception_list);
        this.mWcmExceptionDesc.setTitle(R.string.wifi_smart_network_switch_excluded_networks_body);
        this.mExceptionList.setVisible(true);
        this.mExceptionList.removeAll();
        List<WifiConfiguration> configuredNetworks = this.mWifiManager.getConfiguredNetworks();
        List<SemWifiConfiguration> configuredNetworks2 = this.mSemWifiManager.getConfiguredNetworks();
        if (configuredNetworks != null && configuredNetworks2 != null) {
            try {
                for (SemWifiConfiguration semWifiConfiguration : configuredNetworks2) {
                    for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                        if (semWifiConfiguration != null && wifiConfiguration != null && semWifiConfiguration.isNoInternetAccessExpected && semWifiConfiguration.configKey.equals(wifiConfiguration.getKey()) && wifiConfiguration.SSID != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("config SSID : ");
                            String str = wifiConfiguration.SSID;
                            if (str == null) {
                                str = null;
                            } else {
                                int length = str.length();
                                if (length > 1 && str.charAt(0) == '\"') {
                                    int i = length - 1;
                                    if (str.charAt(i) == '\"') {
                                        str = str.substring(1, i);
                                    }
                                }
                            }
                            sb.append(str);
                            Log.d("WifiExceptionNetworkSettings", sb.toString());
                            FragmentActivity fragmentActivity = this.mContext;
                            WifiExcludedNetworkPref wifiExcludedNetworkPref = new WifiExcludedNetworkPref(fragmentActivity);
                            wifiExcludedNetworkPref.mContext = fragmentActivity;
                            wifiExcludedNetworkPref.mWifiConfiguration = wifiConfiguration;
                            wifiExcludedNetworkPref.setLayoutResource(R.layout.sec_preference_wifi_excluded_network);
                            this.mExceptionList.addPreference(wifiExcludedNetworkPref);
                        }
                    }
                }
            } catch (Exception e) {
                SessionManagerImpl$scope$1$$ExternalSyntheticOutline0.m("Error calling configuredNetworks ", e, "WifiExceptionNetworkSettings");
            }
        }
        if (this.mExceptionList.getPreferenceCount() == 0) {
            Log.d("WifiExceptionNetworkSettings", "No Item");
            this.mExceptionList.setVisible(false);
            getPreferenceScreen().removeAll();
            TextView textView = (TextView) getActivity().findViewById(android.R.id.empty);
            textView.setText(R.string.wifi_smart_network_switch_excluded_noitem);
            textView.setTextAppearance(R.style.no_item_text_style);
            setEmptyView(textView);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.intelligent.WifiExceptionNetworkSettings$3, reason: invalid class name */
    public final class AnonymousClass3 implements ViewGroup.OnHierarchyChangeListener {
        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewAdded(View view, View view2) {
            Log.d("WifiExceptionNetworkSettings", "OnHierarchyChangeListener, parent: " + view);
            View childAt = ((ViewGroup) view).getChildAt(1);
            Log.d("WifiExceptionNetworkSettings", "OnHierarchyChangeListener, titleView: " + childAt);
            if (childAt instanceof TextView) {
                Log.d("WifiExceptionNetworkSettings", "OnHierarchyChangeListener, set the setallcaps to the false.");
                ((TextView) childAt).setAllCaps(true);
            }
        }

        @Override // android.view.ViewGroup.OnHierarchyChangeListener
        public final void onChildViewRemoved(View view, View view2) {
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment, androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
    }
}
