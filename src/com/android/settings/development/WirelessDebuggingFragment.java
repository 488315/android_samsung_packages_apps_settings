package com.android.settings.development;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.debug.IAdbManager;
import android.debug.PairDevice;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.widget.MainSwitchBarController;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.IMSParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WirelessDebuggingFragment extends DashboardFragment
        implements WirelessDebuggingEnabler.OnEnabledListener, DeveloperOptionAwareMixin {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.adb_wireless_settings);
    public static AdbIpAddressPreferenceController sAdbIpAddressPreferenceController;
    public IAdbManager mAdbManager;
    public int mConnectionPort;
    public Preference mDeviceNamePreference;
    public PreferenceCategory mFooterCategory;
    public IntentFilter mIntentFilter;
    public Preference mIpAddrPreference;
    public FooterPreference mOffMessagePreference;
    public Map mPairedDevicePreferences;
    public PreferenceCategory mPairedDevicesCategory;
    public AdbWirelessDialog mPairingCodeDialog;
    public PreferenceCategory mPairingMethodsCategory;
    public WirelessDebuggingEnabler mWifiDebuggingEnabler;
    public final PairingCodeDialogListener mPairingCodeDialogListener =
            new PairingCodeDialogListener();
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.development.WirelessDebuggingFragment.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if ("com.android.server.adb.WIRELESS_DEBUG_PAIRED_DEVICES".equals(action)) {
                        HashMap hashMap = (HashMap) intent.getSerializableExtra("devices_map");
                        WirelessDebuggingFragment wirelessDebuggingFragment =
                                WirelessDebuggingFragment.this;
                        AdbIpAddressPreferenceController adbIpAddressPreferenceController =
                                WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                        wirelessDebuggingFragment.updatePairedDevicePreferences(hashMap);
                        return;
                    }
                    if ("com.android.server.adb.WIRELESS_DEBUG_STATUS".equals(action)) {
                        int intExtra = intent.getIntExtra(IMSParameter.CALL.STATUS, 5);
                        if (intExtra == 4 || intExtra == 5) {
                            WirelessDebuggingFragment.sAdbIpAddressPreferenceController.updateState(
                                    WirelessDebuggingFragment.this.mIpAddrPreference);
                            return;
                        }
                        return;
                    }
                    if ("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT".equals(action)) {
                        Integer valueOf =
                                Integer.valueOf(intent.getIntExtra(IMSParameter.CALL.STATUS, 0));
                        if (valueOf.equals(3)) {
                            String stringExtra = intent.getStringExtra("pairing_code");
                            AdbWirelessDialog adbWirelessDialog =
                                    WirelessDebuggingFragment.this.mPairingCodeDialog;
                            if (adbWirelessDialog != null) {
                                adbWirelessDialog.mController.mSixDigitCode.setText(stringExtra);
                                return;
                            }
                            return;
                        }
                        if (valueOf.equals(1)) {
                            WirelessDebuggingFragment wirelessDebuggingFragment2 =
                                    WirelessDebuggingFragment.this;
                            AdbIpAddressPreferenceController adbIpAddressPreferenceController2 =
                                    WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                            wirelessDebuggingFragment2.removeDialog(0);
                            WirelessDebuggingFragment.this.mPairingCodeDialog = null;
                            return;
                        }
                        if (valueOf.equals(0)) {
                            WirelessDebuggingFragment wirelessDebuggingFragment3 =
                                    WirelessDebuggingFragment.this;
                            AdbIpAddressPreferenceController adbIpAddressPreferenceController3 =
                                    WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                            wirelessDebuggingFragment3.removeDialog(0);
                            WirelessDebuggingFragment wirelessDebuggingFragment4 =
                                    WirelessDebuggingFragment.this;
                            wirelessDebuggingFragment4.mPairingCodeDialog = null;
                            wirelessDebuggingFragment4.showDialog(2);
                            return;
                        }
                        if (valueOf.equals(4)) {
                            int intExtra2 = intent.getIntExtra("adb_port", 0);
                            Log.i("WirelessDebuggingFrag", "Got pairing code port=" + intExtra2);
                            String str =
                                    WirelessDebuggingFragment.sAdbIpAddressPreferenceController
                                                    .getIpv4Address()
                                            + ":"
                                            + intExtra2;
                            AdbWirelessDialog adbWirelessDialog2 =
                                    WirelessDebuggingFragment.this.mPairingCodeDialog;
                            if (adbWirelessDialog2 != null) {
                                adbWirelessDialog2.mController.mIpAddr.setText(str);
                            }
                        }
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.development.WirelessDebuggingFragment$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PairingCodeDialogListener {
        public PairingCodeDialogListener() {}
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        getActivity();
        Lifecycle settingsLifecycle = getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        AdbIpAddressPreferenceController adbIpAddressPreferenceController =
                new AdbIpAddressPreferenceController(context, settingsLifecycle);
        sAdbIpAddressPreferenceController = adbIpAddressPreferenceController;
        arrayList.add(adbIpAddressPreferenceController);
        return arrayList;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final int getDialogMetricsCategory(int i) {
        return 1832;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "WirelessDebuggingFrag";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1831;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.adb_wireless_settings;
    }

    public final void launchPairedDeviceDetailsFragment(
            AdbPairedDevicePreference adbPairedDevicePreference) {
        adbPairedDevicePreference
                .getExtras()
                .putParcelable("paired_device", adbPairedDevicePreference.mPairedDevice);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        subSettingLauncher.setTitleRes(R.string.adb_wireless_device_details_title, null);
        String name = AdbDeviceDetailsFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = adbPairedDevicePreference.getExtras();
        launchRequest.mSourceMetricsCategory = 1831;
        subSettingLauncher.setResultListener(this, 0);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsActivity settingsActivity = (SettingsActivity) getActivity();
        this.mWifiDebuggingEnabler =
                new WirelessDebuggingEnabler(
                        settingsActivity,
                        new MainSwitchBarController(settingsActivity.mMainSwitch),
                        this,
                        getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 0) {
            if (i == 1 && i2 == -1) {
                if (intent.getIntExtra("request_type_pairing", -1) != 1) {
                    Log.d("WirelessDebuggingFrag", "Successfully paired device");
                    return;
                } else {
                    showDialog(2);
                    return;
                }
            }
            return;
        }
        if (i2 != -1) {
            return;
        }
        Log.i("WirelessDebuggingFrag", "Processing paired device request");
        if (intent.getIntExtra("request_type", -1) != 0) {
            return;
        }
        try {
            this.mAdbManager.unpairDevice(intent.getParcelableExtra("paired_device").guid);
        } catch (RemoteException unused) {
            Log.e("WirelessDebuggingFrag", "Unable to forget the device");
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        ((AdbQrCodePreferenceController) use(AdbQrCodePreferenceController.class))
                .setParentFragment(this);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDeviceNamePreference = findPreference("adb_device_name_pref");
        this.mIpAddrPreference = findPreference("adb_ip_addr_pref");
        this.mPairingMethodsCategory =
                (PreferenceCategory) findPreference("adb_pairing_methods_category");
        findPreference("adb_pair_method_code_pref")
                .setOnPreferenceClickListener(
                        new WirelessDebuggingFragment$$ExternalSyntheticLambda0(this, 0));
        this.mPairedDevicesCategory =
                (PreferenceCategory) findPreference("adb_paired_devices_category");
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) findPreference("adb_wireless_footer_category");
        this.mFooterCategory = preferenceCategory;
        this.mOffMessagePreference = new FooterPreference(preferenceCategory.getContext());
        this.mOffMessagePreference.setTitle(getText(R.string.adb_wireless_list_empty_off));
        this.mFooterCategory.addPreference(this.mOffMessagePreference);
        IntentFilter intentFilter =
                new IntentFilter("com.android.server.adb.WIRELESS_DEBUG_PAIRED_DEVICES");
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("com.android.server.adb.WIRELESS_DEBUG_STATUS");
        this.mIntentFilter.addAction("com.android.server.adb.WIRELESS_DEBUG_PAIRING_RESULT");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        AdbWirelessDialog adbWirelessDialog =
                new AdbWirelessDialog(
                        getActivity(), i == 0 ? this.mPairingCodeDialogListener : null, i);
        if (i != 0) {
            return adbWirelessDialog;
        }
        this.mPairingCodeDialog = adbWirelessDialog;
        try {
            this.mAdbManager.enablePairingByPairingCode();
            return adbWirelessDialog;
        } catch (RemoteException unused) {
            Log.e("WirelessDebuggingFrag", "Unable to enable pairing");
            this.mPairingCodeDialog = null;
            return new AdbWirelessDialog(getActivity(), null, 2);
        }
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        WirelessDebuggingEnabler wirelessDebuggingEnabler = this.mWifiDebuggingEnabler;
        boolean z = wirelessDebuggingEnabler.mListeningToOnSwitchChange;
        SwitchWidgetController switchWidgetController = wirelessDebuggingEnabler.mSwitchWidget;
        if (z) {
            switchWidgetController.stopListening();
            wirelessDebuggingEnabler.mListeningToOnSwitchChange = false;
        }
        switchWidgetController.teardownView();
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        removeDialog(0);
        getActivity().unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mReceiver, this.mIntentFilter, 2);
    }

    public final void updatePairedDevicePreferences(final Map map) {
        if (map == null) {
            this.mPairedDevicesCategory.removeAll();
            return;
        }
        if (this.mPairedDevicePreferences == null) {
            this.mPairedDevicePreferences = new HashMap();
        }
        if (((HashMap) this.mPairedDevicePreferences).isEmpty()) {
            for (Map.Entry entry : map.entrySet()) {
                AdbPairedDevicePreference adbPairedDevicePreference =
                        new AdbPairedDevicePreference(
                                (PairDevice) entry.getValue(),
                                this.mPairedDevicesCategory.getContext());
                ((HashMap) this.mPairedDevicePreferences)
                        .put((String) entry.getKey(), adbPairedDevicePreference);
                adbPairedDevicePreference.setOnPreferenceClickListener(
                        new WirelessDebuggingFragment$$ExternalSyntheticLambda0(this, 1));
                this.mPairedDevicesCategory.addPreference(adbPairedDevicePreference);
            }
            return;
        }
        ((HashMap) this.mPairedDevicePreferences)
                .entrySet()
                .removeIf(
                        new Predicate() { // from class:
                                          // com.android.settings.development.WirelessDebuggingFragment$$ExternalSyntheticLambda2
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                WirelessDebuggingFragment wirelessDebuggingFragment =
                                        WirelessDebuggingFragment.this;
                                Map map2 = map;
                                Map.Entry entry2 = (Map.Entry) obj;
                                AdbIpAddressPreferenceController adbIpAddressPreferenceController =
                                        WirelessDebuggingFragment.sAdbIpAddressPreferenceController;
                                wirelessDebuggingFragment.getClass();
                                if (map2.get(entry2.getKey()) == null) {
                                    wirelessDebuggingFragment.mPairedDevicesCategory
                                            .removePreference((Preference) entry2.getValue());
                                    return true;
                                }
                                AdbPairedDevicePreference adbPairedDevicePreference2 =
                                        (AdbPairedDevicePreference) entry2.getValue();
                                PairDevice pairDevice = (PairDevice) map2.get(entry2.getKey());
                                adbPairedDevicePreference2.mPairedDevice = pairDevice;
                                adbPairedDevicePreference2.setTitle(pairDevice.name);
                                adbPairedDevicePreference2.setSummary(
                                        pairDevice.connected
                                                ? adbPairedDevicePreference2
                                                        .getContext()
                                                        .getText(
                                                                R.string
                                                                        .adb_wireless_device_connected_summary)
                                                : ApnSettings.MVNO_NONE);
                                return false;
                            }
                        });
        for (Map.Entry entry2 : map.entrySet()) {
            if (((HashMap) this.mPairedDevicePreferences).get(entry2.getKey()) == null) {
                AdbPairedDevicePreference adbPairedDevicePreference2 =
                        new AdbPairedDevicePreference(
                                (PairDevice) entry2.getValue(),
                                this.mPairedDevicesCategory.getContext());
                ((HashMap) this.mPairedDevicePreferences)
                        .put((String) entry2.getKey(), adbPairedDevicePreference2);
                adbPairedDevicePreference2.setOnPreferenceClickListener(
                        new WirelessDebuggingFragment$$ExternalSyntheticLambda0(this, 2));
                this.mPairedDevicesCategory.addPreference(adbPairedDevicePreference2);
            }
        }
    }
}
