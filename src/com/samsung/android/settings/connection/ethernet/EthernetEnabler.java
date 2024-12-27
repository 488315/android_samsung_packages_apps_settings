package com.samsung.android.settings.connection.ethernet;

import android.content.Context;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.EthernetManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreference;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EthernetEnabler implements Preference.OnPreferenceChangeListener {
    public final List DevList;
    public boolean isEthernetConnected;
    public final Context mContext;
    public final SecSwitchPreference mEthCheckBoxPref;
    public final EthernetManager mEthManager;
    public int mStartMode;
    public final Handler mHandler = new Handler();
    public final AnonymousClass2 summaryUpdater =
            new Runnable() { // from class:
                             // com.samsung.android.settings.connection.ethernet.EthernetEnabler.2
                @Override // java.lang.Runnable
                public final void run() {
                    ActionBarContextView$$ExternalSyntheticOutline0.m(
                            new StringBuilder(" summaryUpdater - isEthernetConnected :"),
                            EthernetEnabler.this.isEthernetConnected,
                            "SettingsEthEnabler");
                    EthernetEnabler ethernetEnabler = EthernetEnabler.this;
                    boolean z = ethernetEnabler.isEthernetConnected;
                    ethernetEnabler.mEthCheckBoxPref.setSummary(
                            z ? R.string.switch_on_text : R.string.sec_eth_toggle_summary_on);
                    SecSwitchPreference secSwitchPreference = EthernetEnabler.this.mEthCheckBoxPref;
                    secSwitchPreference.getClass();
                    SecPreferenceUtils.applySummaryColor(secSwitchPreference, true);
                    EthernetEnabler.this.mEthCheckBoxPref.setChecked(z);
                    EthernetEnabler.this.mEthCheckBoxPref.setEnabled(true);
                }
            };
    public final AnonymousClass3 mEthDeviceStateReceiver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.connection.ethernet.EthernetEnabler.3
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    String stringForUser =
                            Settings.System.getStringForUser(
                                    EthernetEnabler.this.mContext.getContentResolver(),
                                    "ethernet_state",
                                    0);
                    EthernetEnabler ethernetEnabler = EthernetEnabler.this;
                    ethernetEnabler.getClass();
                    ethernetEnabler.isEthernetConnected =
                            EthernetEnabler.getEthernetConnectedState(stringForUser);
                    if (stringForUser.equals("Connected")) {
                        EthernetEnabler.this.postEnableTaskFinishedUIUpdate(true);
                    } else {
                        EthernetEnabler.this.postEnableTaskFinishedUIUpdate(false);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EthernetEnableTask extends AsyncTask {
        public EthernetEnableTask() {}

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            String[] strArr = (String[]) objArr;
            String str = strArr[0];
            String str2 = strArr[1];
            Log.d("SettingsEthEnabler", "doInBackground : enable" + str + " enabling" + str2);
            if (!str2.equals("enabling")) {
                if (str.equals("true")) {
                    EthernetEnabler.this.mEthManager.setEthernetEnabled(true);
                    Settings.System.putIntForUser(
                            EthernetEnabler.this.mContext.getContentResolver(),
                            "eth_disabled",
                            0,
                            0);
                } else {
                    EthernetEnabler.this.mEthManager.setEthernetEnabled(false);
                    Settings.System.putIntForUser(
                            EthernetEnabler.this.mContext.getContentResolver(),
                            "eth_disabled",
                            1,
                            0);
                }
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            return str;
        }

        @Override // android.os.AsyncTask
        public final void onPostExecute(Object obj) {
            if (((String) obj).equals("true")) {
                EthernetEnabler.this.postEnableTaskFinishedUIUpdate(true);
            } else {
                EthernetEnabler.this.postEnableTaskFinishedUIUpdate(false);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.settings.connection.ethernet.EthernetEnabler$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.settings.connection.ethernet.EthernetEnabler$3] */
    public EthernetEnabler(
            FragmentActivity fragmentActivity,
            EthernetManager ethernetManager,
            SecSwitchPreference secSwitchPreference) {
        this.mStartMode = 3;
        this.mContext = fragmentActivity;
        this.mEthCheckBoxPref = secSwitchPreference;
        this.mEthManager = ethernetManager;
        List interfaceList = ethernetManager.getInterfaceList();
        this.DevList = interfaceList;
        String[] strArr = new String[interfaceList.size()];
        for (int i = 0; i < this.DevList.size(); i++) {
            strArr[i] = (String) this.DevList.get(i);
        }
        secSwitchPreference.getSummary();
        secSwitchPreference.setPersistent();
        new IntentFilter().addAction("samsung.net.ethernet.ETH_STATE_CHANGED");
        this.mStartMode = 1;
    }

    public static boolean getEthernetConnectedState(String str) {
        DialogFragment$$ExternalSyntheticOutline0.m(
                " getEthernetConnectedState - ethernet_state :", str, "SettingsEthEnabler");
        return str != null && str.equals("Connected");
    }

    public static void setCheckBox(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "setCheckBox  req state ", "SettingsEthEnabler");
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        StringBuilder sb = new StringBuilder("onPreferenceChange : ");
        Boolean bool = (Boolean) obj;
        sb.append(bool);
        Log.i("SettingsEthEnabler", sb.toString());
        boolean booleanValue = bool.booleanValue();
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                RowView$$ExternalSyntheticOutline0.m(
                        "setEthEnabled enable ", " isEthernetConnected :", booleanValue),
                this.isEthernetConnected,
                "SettingsEthEnabler");
        if (booleanValue && this.isEthernetConnected) {
            postEnableTaskFinishedUIUpdate(booleanValue);
        } else {
            this.mEthCheckBoxPref.setEnabled(false);
            new EthernetEnableTask()
                    .execute(ApnSettings.MVNO_NONE + booleanValue, ApnSettings.MVNO_NONE);
        }
        return false;
    }

    public final void postEnableTaskFinishedUIUpdate(boolean z) {
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(
                "postEnableTaskFinishedUIUpdate : ", "SettingsEthEnabler", z);
        int i = z ? R.string.sec_connect_starting : R.string.sec_eth_toggle_summary_on;
        SecSwitchPreference secSwitchPreference = this.mEthCheckBoxPref;
        secSwitchPreference.setSummary(i);
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("postEnableTaskFinishedUIUpdate isEthernetConnected: "),
                this.isEthernetConnected,
                "SettingsEthEnabler");
        boolean z2 = this.isEthernetConnected;
        secSwitchPreference.setChecked(z2);
        Log.d("SettingsEthEnabler", "postEnableTaskFinishedUIUpdate newstate: " + z2);
        if (z || z2) {
            Log.i("SettingsEthEnabler", "handleEthSummaryIfConnectionFailsLanNotConnected called");
            this.mHandler.postDelayed(this.summaryUpdater, 3000L);
        } else {
            secSwitchPreference.setSummary(R.string.sec_eth_toggle_summary_on);
            secSwitchPreference.setEnabled(true);
        }
    }
}
