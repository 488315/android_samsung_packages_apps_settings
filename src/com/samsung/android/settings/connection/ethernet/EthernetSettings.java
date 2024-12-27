package com.samsung.android.settings.connection.ethernet;

import android.app.Dialog;
import android.database.ContentObserver;
import android.net.EthernetManager;
import android.net.IpConfiguration;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.secutil.Log;
import android.widget.Toast;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EthernetSettings extends SettingsPreferenceFragment {
    public SecPreference mEthConfigPref;
    public final AnonymousClass1 mEthDeviceStateReceiver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.connection.ethernet.EthernetSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    int intForUser =
                            Settings.System.getIntForUser(
                                    EthernetSettings.this.getContentResolver(),
                                    "eth_device_conn",
                                    0,
                                    0);
                    Log.d(
                            "EthernetSettings",
                            " mEthDeviceStateReceiver - ethernet_conn_state :" + intForUser);
                    if (intForUser != 2) {
                        EthernetSettings.this.removeDialog(1);
                        EthernetSettings ethernetSettings = EthernetSettings.this;
                        ethernetSettings.getClass();
                        ethernetSettings.finishFragment();
                    }
                }
            };
    public EthernetEnabler mEthEnabler;
    public EthernetManager mEthManager;
    public EthernetListener mEthernetListener;
    public Handler mHandler;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EthernetListener implements EthernetManager.InterfaceStateListener {
        public final void onInterfaceStateChanged(
                String str, int i, int i2, IpConfiguration ipConfiguration) {
            Log.d(
                    "EthernetSettings",
                    " EthernetListener state " + i + " configuration : " + ipConfiguration);
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 42;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.sec_ethernet_settings);
        this.mEthConfigPref = (SecPreference) getPreferenceScreen().findPreference("eth_config");
        this.mEthManager = (EthernetManager) getSystemService("ethernet");
        this.mEthernetListener = new EthernetListener();
        this.mHandler = new Handler();
        FragmentActivity activity = getActivity();
        EthernetManager ethernetManager = (EthernetManager) getSystemService(EthernetManager.class);
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) findPreference("toggle_eth");
        this.mEthEnabler = new EthernetEnabler(activity, ethernetManager, secSwitchPreference);
        new EthernetConfigDialog(getActivity(), this.mEthEnabler);
        this.mEthEnabler.getClass();
        this.mEthEnabler.getClass();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.DialogCreatable
    public final Dialog onCreateDialog(int i) {
        if (i == 1) {
            return new EthernetConfigDialog(getActivity(), this.mEthEnabler);
        }
        return null;
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        EthernetEnabler ethernetEnabler = this.mEthEnabler;
        ethernetEnabler.mHandler.removeCallbacks(ethernetEnabler.summaryUpdater);
        this.mEthernetListener = null;
        this.mEthManager = null;
        this.mHandler = null;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        EthernetEnabler ethernetEnabler = this.mEthEnabler;
        ethernetEnabler.mStartMode = 3;
        ethernetEnabler
                .mContext
                .getContentResolver()
                .unregisterContentObserver(ethernetEnabler.mEthDeviceStateReceiver);
        ethernetEnabler.mEthCheckBoxPref.setOnPreferenceChangeListener(null);
        getContentResolver().unregisterContentObserver(this.mEthDeviceStateReceiver);
        EthernetManager ethernetManager = this.mEthManager;
        if (ethernetManager != null) {
            ethernetManager.removeInterfaceStateListener(this.mEthernetListener);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        super.onPreferenceTreeClick(preference);
        if (preference != this.mEthConfigPref) {
            return false;
        }
        if (this.mEthEnabler.isEthernetConnected) {
            Toast.makeText(
                            getActivity().getApplicationContext(),
                            R.string.sec_ethernet_disable_toast_message,
                            1)
                    .show();
        }
        showDialog(1);
        return false;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        EthernetEnabler ethernetEnabler = this.mEthEnabler;
        if (ethernetEnabler.mStartMode != 1) {
            ethernetEnabler.mStartMode = 2;
        }
        ethernetEnabler
                .mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("ethernet_state"),
                        false,
                        ethernetEnabler.mEthDeviceStateReceiver,
                        0);
        ethernetEnabler.mEthCheckBoxPref.setOnPreferenceChangeListener(ethernetEnabler);
        String stringForUser =
                Settings.System.getStringForUser(
                        ethernetEnabler.mContext.getContentResolver(), "ethernet_state", 0);
        ethernetEnabler.isEthernetConnected =
                EthernetEnabler.getEthernetConnectedState(stringForUser);
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "resume state :", stringForUser, "SettingsEthEnabler");
        ethernetEnabler.mHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.android.settings.connection.ethernet.EthernetEnabler.1
                    public final /* synthetic */ String val$ethernetStatus;

                    public AnonymousClass1(String stringForUser2) {
                        r2 = stringForUser2;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        Utils$$ExternalSyntheticOutline0.m(
                                new StringBuilder("updateSwitch "), r2, "SettingsEthEnabler");
                        String str = r2;
                        if (str != null && str.equals("Connected")) {
                            EthernetEnabler.this.mEthCheckBoxPref.setChecked(true);
                            EthernetEnabler.this.mEthCheckBoxPref.setSummary(
                                    R.string.switch_on_text);
                            SecSwitchPreference secSwitchPreference =
                                    EthernetEnabler.this.mEthCheckBoxPref;
                            secSwitchPreference.getClass();
                            SecPreferenceUtils.applySummaryColor(secSwitchPreference, true);
                            EthernetEnabler ethernetEnabler2 = EthernetEnabler.this;
                            ethernetEnabler2.mHandler.removeCallbacks(
                                    ethernetEnabler2.summaryUpdater);
                            EthernetEnabler.this.mEthCheckBoxPref.setEnabled(true);
                            return;
                        }
                        String str2 = r2;
                        if (str2 == null || str2.equals("Disconnected")) {
                            EthernetEnabler.this.mEthCheckBoxPref.setChecked(false);
                            EthernetEnabler.this.mEthCheckBoxPref.setSummary(
                                    R.string.switch_off_text);
                            SecSwitchPreference secSwitchPreference2 =
                                    EthernetEnabler.this.mEthCheckBoxPref;
                            secSwitchPreference2.getClass();
                            SecPreferenceUtils.applySummaryColor(secSwitchPreference2, false);
                        }
                    }
                });
        int intForUser =
                Settings.System.getIntForUser(getContentResolver(), "eth_device_conn", 0, 0);
        Log.d("EthernetSettings", " mEthDeviceStateReceiver - ethernet_conn_state :" + intForUser);
        if (intForUser != 2) {
            finish();
        }
        if (this.mEthManager != null) {
            Log.i("EthernetSettings", "onStart addInterfaceStateListener");
            this.mEthManager.addInterfaceStateListener(
                    new Executor() { // from class:
                                     // com.samsung.android.settings.connection.ethernet.EthernetSettings$$ExternalSyntheticLambda0
                        @Override // java.util.concurrent.Executor
                        public final void execute(Runnable runnable) {
                            EthernetSettings.this.mHandler.post(runnable);
                        }
                    },
                    this.mEthernetListener);
        }
        getContentResolver()
                .registerContentObserver(
                        Settings.System.getUriFor("eth_device_conn"),
                        false,
                        this.mEthDeviceStateReceiver,
                        0);
    }
}
