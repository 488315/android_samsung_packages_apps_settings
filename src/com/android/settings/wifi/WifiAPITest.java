package com.android.settings.wifi;

import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiAPITest extends SettingsPreferenceFragment
        implements Preference.OnPreferenceClickListener {
    public Preference mWifiDisableNetwork;
    public Preference mWifiDisconnect;
    public Preference mWifiEnableNetwork;
    public WifiManager mWifiManager;
    public int netid;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 89;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mWifiManager = (WifiManager) getSystemService(ImsProfile.PDN_WIFI);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat
    public final void onCreatePreferences(Bundle bundle, String str) {
        Context context = getContext();
        if (context == null) {
            return;
        }
        addPreferencesFromResource(R.xml.wifi_api_test);
        boolean isChangeWifiStateAllowed =
                WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(context);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Preference findPreference = preferenceScreen.findPreference("disconnect");
        this.mWifiDisconnect = findPreference;
        if (findPreference != null) {
            findPreference.setEnabled(isChangeWifiStateAllowed);
            if (isChangeWifiStateAllowed) {
                this.mWifiDisconnect.setOnPreferenceClickListener(this);
            }
        }
        Preference findPreference2 = preferenceScreen.findPreference("disable_network");
        this.mWifiDisableNetwork = findPreference2;
        if (findPreference2 != null) {
            findPreference2.setEnabled(isChangeWifiStateAllowed);
            if (isChangeWifiStateAllowed) {
                this.mWifiDisableNetwork.setOnPreferenceClickListener(this);
            }
        }
        Preference findPreference3 = preferenceScreen.findPreference("enable_network");
        this.mWifiEnableNetwork = findPreference3;
        if (findPreference3 != null) {
            findPreference3.setEnabled(isChangeWifiStateAllowed);
            if (isChangeWifiStateAllowed) {
                this.mWifiEnableNetwork.setOnPreferenceClickListener(this);
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (preference == this.mWifiDisconnect) {
            this.mWifiManager.disconnect();
            return true;
        }
        if (preference == this.mWifiDisableNetwork) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = "Input";
            alertParams.mMessage = "Enter Network ID";
            final EditText editText = new EditText(getPrefContext());
            builder.setView(editText);
            final int i = 0;
            builder.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener(
                            this) { // from class: com.android.settings.wifi.WifiAPITest.1
                        public final /* synthetic */ WifiAPITest this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i2) {
                            switch (i) {
                                case 0:
                                    Editable text = editText.getText();
                                    try {
                                        this.this$0.netid = Integer.parseInt(text.toString());
                                        WifiAPITest wifiAPITest = this.this$0;
                                        wifiAPITest.mWifiManager.disableNetwork(wifiAPITest.netid);
                                        break;
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                default:
                                    Editable text2 = editText.getText();
                                    try {
                                        this.this$0.netid = Integer.parseInt(text2.toString());
                                        WifiAPITest wifiAPITest2 = this.this$0;
                                        wifiAPITest2.mWifiManager.enableNetwork(
                                                wifiAPITest2.netid, false);
                                        break;
                                    } catch (NumberFormatException unused) {
                                        return;
                                    }
                            }
                        }
                    });
            final int i2 = 0;
            builder.setNegativeButton(
                    "Cancel",
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.wifi.WifiAPITest.2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            int i4 = i2;
                        }

                        private final void onClick$com$android$settings$wifi$WifiAPITest$2(
                                DialogInterface dialogInterface, int i3) {}

                        private final void onClick$com$android$settings$wifi$WifiAPITest$4(
                                DialogInterface dialogInterface, int i3) {}
                    });
            builder.show();
            return true;
        }
        if (preference != this.mWifiEnableNetwork) {
            return true;
        }
        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        AlertController.AlertParams alertParams2 = builder2.P;
        alertParams2.mTitle = "Input";
        alertParams2.mMessage = "Enter Network ID";
        final EditText editText2 = new EditText(getPrefContext());
        builder2.setView(editText2);
        final int i3 = 1;
        builder2.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener(
                        this) { // from class: com.android.settings.wifi.WifiAPITest.1
                    public final /* synthetic */ WifiAPITest this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i22) {
                        switch (i3) {
                            case 0:
                                Editable text = editText2.getText();
                                try {
                                    this.this$0.netid = Integer.parseInt(text.toString());
                                    WifiAPITest wifiAPITest = this.this$0;
                                    wifiAPITest.mWifiManager.disableNetwork(wifiAPITest.netid);
                                    break;
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                    return;
                                }
                            default:
                                Editable text2 = editText2.getText();
                                try {
                                    this.this$0.netid = Integer.parseInt(text2.toString());
                                    WifiAPITest wifiAPITest2 = this.this$0;
                                    wifiAPITest2.mWifiManager.enableNetwork(
                                            wifiAPITest2.netid, false);
                                    break;
                                } catch (NumberFormatException unused) {
                                    return;
                                }
                        }
                    }
                });
        final int i4 = 1;
        builder2.setNegativeButton(
                "Cancel",
                new DialogInterface
                        .OnClickListener() { // from class: com.android.settings.wifi.WifiAPITest.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i32) {
                        int i42 = i4;
                    }

                    private final void onClick$com$android$settings$wifi$WifiAPITest$2(
                            DialogInterface dialogInterface, int i32) {}

                    private final void onClick$com$android$settings$wifi$WifiAPITest$4(
                            DialogInterface dialogInterface, int i32) {}
                });
        builder2.show();
        return true;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat,
              // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        super.onPreferenceTreeClick(preference);
        return false;
    }
}
