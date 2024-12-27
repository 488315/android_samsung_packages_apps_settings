package com.android.settings.wifi;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import com.android.internal.app.AlertActivity;
import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.wifi.SemWifiConfiguration;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;
import com.sec.ims.extensions.WiFiManagerExt;
import com.sec.ims.settings.ImsProfile;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiNoInternetDialog extends AlertActivity {
    public String mAction;
    public ConnectivityManager mCM;
    public Context mContext;
    public int mDialogTheme;
    public Network mNetwork;
    public AnonymousClass2 mNetworkCallback;
    public String mNetworkName;
    public SemWifiManager mSemWifiManager;
    public AlertDialog mWifiNoInternetDialog;
    public final AnonymousClass5 mWifiNoInternetDialogListener;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.wifi.WifiNoInternetDialog$5] */
    public WifiNoInternetDialog() {
        new BroadcastReceiver() { // from class: com.android.settings.wifi.WifiNoInternetDialog.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                if ("android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("wifi_state", 4);
                    if (intExtra == 1 || intExtra == 0) {
                        AlertDialog alertDialog = WifiNoInternetDialog.this.mWifiNoInternetDialog;
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                        WifiNoInternetDialog.this.finish();
                        return;
                    }
                    return;
                }
                if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(IMSParameter.GENERAL.NETWORK_INFO);
                    if (networkInfo.getDetailedState().equals(NetworkInfo.DetailedState.DISCONNECTED)) {
                        Log.d("WifiNoInternetDialog", "Network state changed " + networkInfo.getDetailedState());
                        AlertDialog alertDialog2 = WifiNoInternetDialog.this.mWifiNoInternetDialog;
                        if (alertDialog2 != null) {
                            alertDialog2.dismiss();
                        }
                        WifiNoInternetDialog.this.finish();
                    }
                }
            }
        };
        this.mWifiNoInternetDialogListener = new View.OnClickListener() { // from class: com.android.settings.wifi.WifiNoInternetDialog.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                String str;
                WifiConfiguration wifiConfiguration;
                List configuredNetworks;
                if (WifiNoInternetDialog.this.mWifiNoInternetDialog == null) {
                    return;
                }
                boolean z = view.getId() == R.id.keep_btn || view.getId() == R.id.switch_btn;
                String str2 = WifiNoInternetDialog.this.mAction;
                if (str2 == null) {
                    return;
                }
                String str3 = "LOST_INTERNET";
                if (str2.contains("PROMPT_UNVALIDATED") || WifiNoInternetDialog.this.mAction.contains("PROMPT_PARTIAL_CONNECTIVITY") || WifiNoInternetDialog.this.mAction.contains("PROMPT_LOST_VALIDATION")) {
                    if ("android.net.action.PROMPT_UNVALIDATED".equals(WifiNoInternetDialog.this.mAction)) {
                        str3 = "NO_INTERNET";
                    } else if ("android.net.action.PROMPT_PARTIAL_CONNECTIVITY".equals(WifiNoInternetDialog.this.mAction)) {
                        str3 = "PARTIAL_CONNECTIVITY";
                    }
                    if (view.getId() != R.id.keep_btn && view.getId() != R.id.connect_btn) {
                        r1 = false;
                    }
                    str = r1 ? "Connect" : "Ignore";
                    WifiNoInternetDialog wifiNoInternetDialog = WifiNoInternetDialog.this;
                    if (wifiNoInternetDialog.mSemWifiManager != null) {
                        SemWifiManager semWifiManager = (SemWifiManager) wifiNoInternetDialog.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
                        WifiManager wifiManager = (WifiManager) wifiNoInternetDialog.mContext.getSystemService(ImsProfile.PDN_WIFI);
                        android.net.wifi.WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                        List<WifiConfiguration> configuredNetworks2 = wifiManager.getConfiguredNetworks();
                        SemWifiConfiguration semWifiConfiguration = null;
                        if (configuredNetworks2 != null && connectionInfo != null) {
                            Iterator<WifiConfiguration> it = configuredNetworks2.iterator();
                            while (it.hasNext()) {
                                wifiConfiguration = it.next();
                                if (wifiConfiguration.networkId == connectionInfo.getNetworkId()) {
                                    break;
                                }
                            }
                        }
                        wifiConfiguration = null;
                        if (wifiConfiguration != null && (configuredNetworks = semWifiManager.getConfiguredNetworks()) != null) {
                            String key = wifiConfiguration.getKey();
                            Iterator it2 = configuredNetworks.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    break;
                                }
                                SemWifiConfiguration semWifiConfiguration2 = (SemWifiConfiguration) it2.next();
                                if (semWifiConfiguration2.configKey.equals(key)) {
                                    semWifiConfiguration = semWifiConfiguration2;
                                    break;
                                }
                            }
                        }
                        if (semWifiConfiguration != null && semWifiConfiguration.isNoInternetAccessExpected && !z) {
                            semWifiConfiguration.isNoInternetAccessExpected = false;
                            WifiNoInternetDialog.this.mSemWifiManager.addOrUpdateNetwork(semWifiConfiguration);
                        }
                        WifiNoInternetDialog.this.sendResultToWCM(r1 ? z ? 2 : 3 : 4, r1, z);
                    }
                } else {
                    r1 = view.getId() == R.id.keep_btn;
                    str = r1 ? "Switch away" : "Get stuck";
                    if (z) {
                        Settings.Global.putString(((AlertActivity) WifiNoInternetDialog.this).mAlertParams.mContext.getContentResolver(), "network_avoid_bad_wifi", r1 ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                    } else if (r1) {
                        WifiNoInternetDialog wifiNoInternetDialog2 = WifiNoInternetDialog.this;
                        wifiNoInternetDialog2.mCM.setAvoidUnvalidated(wifiNoInternetDialog2.mNetwork);
                    }
                }
                StringBuilder sb = new StringBuilder();
                sb.append(str3);
                sb.append(": ");
                sb.append(str);
                sb.append(" network=");
                sb.append(WifiNoInternetDialog.this.mNetwork);
                MainClearConfirm$$ExternalSyntheticOutline0.m(sb, z ? " and remember" : ApnSettings.MVNO_NONE, "WifiNoInternetDialog");
                WifiNoInternetDialog.this.finish();
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v15, types: [android.net.ConnectivityManager$NetworkCallback, com.android.settings.wifi.WifiNoInternetDialog$2] */
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Log.d("WifiNoInternetDialog", "onCreate");
        Intent intent = getIntent();
        if (Utils.isDeviceProvisioned(getApplicationContext())) {
            z = false;
        } else {
            z = getIntent().getBooleanExtra("is_network_required_by_kme", false) || getIntent().getBooleanExtra("is_network_required", false);
            AirplaneModeEnabler$$ExternalSyntheticOutline0.m("isNetworkRequired: ", "WifiNoInternetDialog", z);
        }
        if (z && !"FINISH".equalsIgnoreCase(SystemProperties.get("persist.sys.setupwizard", "NOTSET"))) {
            Log.d("WifiNoInternetDialog", "FRP activated");
            finish();
            return;
        }
        if (intent == null || intent.getAction() == null || !("android.net.action.PROMPT_UNVALIDATED".equals(intent.getAction()) || "android.net.action.PROMPT_LOST_VALIDATION".equals(intent.getAction()) || "android.net.action.PROMPT_PARTIAL_CONNECTIVITY".equals(intent.getAction()))) {
            Log.e("WifiNoInternetDialog", "Unexpected intent " + intent + ", exiting");
            finish();
            return;
        }
        Network network = (Network) intent.getParcelableExtra("android.net.extra.NETWORK");
        this.mNetwork = network;
        if (network == null) {
            Log.e("WifiNoInternetDialog", "Can't determine network from '" + intent.getData() + "' , exiting");
            finish();
            return;
        }
        this.mAction = intent.getAction();
        this.mCM = (ConnectivityManager) getSystemService("connectivity");
        NetworkRequest build = new NetworkRequest.Builder().clearCapabilities().build();
        ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.settings.wifi.WifiNoInternetDialog.2
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network2, NetworkCapabilities networkCapabilities) {
                if (WifiNoInternetDialog.this.mNetwork.equals(network2) && networkCapabilities.hasCapability(16)) {
                    Log.d("WifiNoInternetDialog", "Network " + WifiNoInternetDialog.this.mNetwork + " validated");
                    WifiNoInternetDialog.this.finish();
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network2) {
                if (WifiNoInternetDialog.this.mNetwork.equals(network2)) {
                    Log.d("WifiNoInternetDialog", "Network " + WifiNoInternetDialog.this.mNetwork + " disconnected");
                    WifiNoInternetDialog.this.finish();
                }
            }
        };
        this.mNetworkCallback = r1;
        this.mCM.registerNetworkCallback(build, (ConnectivityManager.NetworkCallback) r1);
        NetworkInfo networkInfo = this.mCM.getNetworkInfo(this.mNetwork);
        NetworkCapabilities networkCapabilities = this.mCM.getNetworkCapabilities(this.mNetwork);
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting() || networkCapabilities == null) {
            Log.d("WifiNoInternetDialog", "Network " + this.mNetwork + " is not connected: " + networkInfo);
            finish();
            return;
        }
        this.mContext = getApplicationContext();
        if (networkCapabilities.hasCapability(24) && com.android.settingslib.Utils.isWifiOnly(this.mContext) && !Utils.isFrpChallengeRequired(this.mContext) && !"FINISH".equalsIgnoreCase(SystemProperties.get("persist.sys.setupwizard", "NOTSET"))) {
            Log.e("WifiNoInternetDialog", "Skip NoInternet dialog in case of partial connectivity during setup wizard");
            this.mCM.setAcceptPartialConnectivity(this.mNetwork, true, false);
            this.mCM.reportNetworkConnectivity(this.mNetwork, true);
            finish();
            return;
        }
        this.mSemWifiManager = (SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE);
        String ssid = networkCapabilities.getSsid();
        this.mNetworkName = ssid;
        if (ssid != null) {
            this.mNetworkName = android.net.wifi.WifiInfo.sanitizeSsid(ssid);
        }
        getWindow().setLayout(0, 0);
        setTheme(R.style.SettingsTheme);
        boolean equalsIgnoreCase = "vzw".equalsIgnoreCase(SemCscFeature.getInstance().getString("SalesCode"));
        View inflate = equalsIgnoreCase ? View.inflate(this, R.layout.sec_wifi_sns_exception_dialog_vzw, null) : View.inflate(this, R.layout.sec_wifi_sns_exception_dialog, null);
        TextView textView = (TextView) inflate.findViewById(R.id.wifi_sns_exception_dialog_text);
        TextView textView2 = (TextView) inflate.findViewById(R.id.wifi_sns_exception_dialog_warning);
        Button button = (Button) inflate.findViewById(R.id.connect_btn);
        Button button2 = (Button) inflate.findViewById(R.id.switch_btn);
        Button button3 = (Button) inflate.findViewById(R.id.keep_btn);
        Context context = this.mContext;
        String str = this.mNetworkName;
        String string = context.getString(R.string.wifi_switch_to_mobile_data_exception_alert_with_no_sim, str, str);
        if (Utils.isTablet()) {
            Context context2 = this.mContext;
            String str2 = this.mNetworkName;
            string = context2.getString(R.string.wifi_switch_to_mobile_data_exception_alert_tablet, str2, str2);
        }
        int updateSmartNetworkSwitchAvailability = Utils.updateSmartNetworkSwitchAvailability(this.mContext);
        if (updateSmartNetworkSwitchAvailability != 2 && updateSmartNetworkSwitchAvailability != 3 && updateSmartNetworkSwitchAvailability != 4) {
            string = SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.wifi_switch_to_mobile_data_exception_alert_detail_intelligent, PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string, "\n\n"));
        }
        textView.setText(string);
        textView2.setText(this.mContext.getString(R.string.wifi_switch_to_mobile_data_exception_warning_detail));
        if ((getResources().getConfiguration().uiMode & 48) == 32) {
            this.mDialogTheme = 4;
        } else {
            this.mDialogTheme = 5;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, this.mDialogTheme);
        builder.setTitle(R.string.wifi_switch_to_mobile_data_exception_title);
        if (equalsIgnoreCase) {
            button.setText(R.string.wifi_switch_to_mobile_data_exception_connect_once);
            button2.setText(R.string.wifi_sns_exception_allow_switch);
            button3.setText(R.string.wifi_sns_exception_stay_connected);
        } else {
            button.setText(R.string.wifi_switch_to_mobile_data_exception_connect_once);
            button2.setText(R.string.wifi_switch_to_mobile_data_exception_disconnect);
            button3.setText(R.string.wifi_switch_to_mobile_data_exception_always_connect);
        }
        button.setOnClickListener(this.mWifiNoInternetDialogListener);
        button2.setOnClickListener(this.mWifiNoInternetDialogListener);
        button3.setOnClickListener(this.mWifiNoInternetDialogListener);
        builder.setView(inflate);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() { // from class: com.android.settings.wifi.WifiNoInternetDialog.3
            @Override // android.content.DialogInterface.OnKeyListener
            public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i != 4 || keyEvent.getAction() != 1 || keyEvent.isCanceled()) {
                    return false;
                }
                WifiNoInternetDialog.this.sendResultToWCM(0, false, false);
                Settings.Secure.putInt(WifiNoInternetDialog.this.mContext.getContentResolver(), "wifi_sns_dialog_for_starting_settings", 0);
                AlertDialog alertDialog = WifiNoInternetDialog.this.mWifiNoInternetDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                WifiNoInternetDialog.this.finish();
                return true;
            }
        });
        AlertDialog create = builder.create();
        this.mWifiNoInternetDialog = create;
        if (create != null) {
            create.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, new OnBackInvokedCallback() { // from class: com.android.settings.wifi.WifiNoInternetDialog.4
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    WifiNoInternetDialog.this.sendResultToWCM(0, false, false);
                    Settings.Secure.putInt(WifiNoInternetDialog.this.mContext.getContentResolver(), "wifi_sns_dialog_for_starting_settings", 0);
                    WifiNoInternetDialog.this.mWifiNoInternetDialog.dismiss();
                    WifiNoInternetDialog.this.finish();
                }
            });
            this.mWifiNoInternetDialog.setCanceledOnTouchOutside(false);
            this.mWifiNoInternetDialog.show();
        }
        setupAlert();
        this.mContext.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }

    public final void onDestroy() {
        AnonymousClass2 anonymousClass2 = this.mNetworkCallback;
        if (anonymousClass2 != null) {
            this.mCM.unregisterNetworkCallback(anonymousClass2);
            this.mNetworkCallback = null;
        }
        super.onDestroy();
    }

    public final void onUserLeaveHint() {
        super.onUserLeaveHint();
        sendResultToWCM(0, false, false);
        Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_sns_dialog_for_starting_settings", 0);
        finish();
    }

    public final void sendResultToWCM(int i, boolean z, boolean z2) {
        this.mSemWifiManager.setKeepConnectionBigData(i);
        if (i != 0) {
            this.mCM.setAcceptUnvalidated(this.mNetwork, z, false);
        }
        this.mSemWifiManager.setKeepConnection(z, z2);
    }
}
