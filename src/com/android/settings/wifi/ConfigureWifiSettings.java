package com.android.settings.wifi;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.UserManager;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.wifi.p2p.WifiP2pPreferenceController;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.sec.ims.settings.ImsProfile;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfigureWifiSettings extends DashboardFragment {
    static final String KEY_INSTALL_CREDENTIALS = "install_credentials";
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.wifi_configure_settings);
    public WifiWakeupPreferenceController mWifiWakeupPreferenceController;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.wifi.ConfigureWifiSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            if (ConfigureWifiSettings.isGuestUser(context)) {
                return false;
            }
            return context.getResources().getBoolean(R.bool.config_show_wifi_settings);
        }
    }

    public static boolean isGuestUser(Context context) {
        UserManager userManager;
        if (context == null
                || (userManager = (UserManager) context.getSystemService(UserManager.class))
                        == null) {
            return false;
        }
        return userManager.isGuestUser();
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        if (isGuestUser(context)) {
            return null;
        }
        WifiManager wifiManager = (WifiManager) getSystemService(WifiManager.class);
        ArrayList arrayList = new ArrayList();
        arrayList.add(
                new WifiP2pPreferenceController(context, getSettingsLifecycle(), wifiManager));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConfigureWifiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.XLSB;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.wifi_configure_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        WifiWakeupPreferenceController wifiWakeupPreferenceController =
                this.mWifiWakeupPreferenceController;
        if (wifiWakeupPreferenceController == null || i != 600) {
            super.onActivityResult(i, i2, intent);
        } else {
            wifiWakeupPreferenceController.onActivityResult(i, i2);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (isGuestUser(context)) {
            return;
        }
        WifiWakeupPreferenceController wifiWakeupPreferenceController =
                (WifiWakeupPreferenceController) use(WifiWakeupPreferenceController.class);
        this.mWifiWakeupPreferenceController = wifiWakeupPreferenceController;
        wifiWakeupPreferenceController.setFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getActivity().setTitle(R.string.network_and_internet_preferences_title);
        if (isGuestUser(getContext())) {
            return;
        }
        Preference findPreference = findPreference(KEY_INSTALL_CREDENTIALS);
        if (findPreference != null) {
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.wifi.ConfigureWifiSettings$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            BaseSearchIndexProvider baseSearchIndexProvider =
                                    ConfigureWifiSettings.SEARCH_INDEX_DATA_PROVIDER;
                            ConfigureWifiSettings configureWifiSettings =
                                    ConfigureWifiSettings.this;
                            configureWifiSettings.getClass();
                            Intent intent = new Intent("android.credentials.INSTALL");
                            intent.setFlags(268435456);
                            intent.setComponent(
                                    new ComponentName(
                                            "com.android.certinstaller",
                                            "com.android.certinstaller.CertInstallerMain"));
                            intent.putExtra("certificate_install_usage", ImsProfile.PDN_WIFI);
                            configureWifiSettings.getContext().startActivity(intent);
                            return true;
                        }
                    });
        } else {
            Log.d("ConfigureWifiSettings", "Can not find the preference.");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (isGuestUser(getContext())) {
            Log.w(
                    "ConfigureWifiSettings",
                    "Displays the restricted UI because the user is a guest.");
            EventLog.writeEvent(1397638484, "231987122", -1, "User is a guest");
            TextView textView = (TextView) getActivity().findViewById(android.R.id.empty);
            if (textView != null) {
                textView.setVisibility(0);
                textView.setText(R.string.wifi_empty_list_user_restricted);
            }
            getPreferenceScreen().removeAll();
        }
    }
}
