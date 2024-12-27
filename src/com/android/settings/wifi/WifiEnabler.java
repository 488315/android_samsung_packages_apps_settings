package com.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.widget.SwitchWidgetController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;

import com.samsung.android.feature.SemGateConfig;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.RestrictionUtils;
import com.samsung.android.settings.wifi.WifiDevicePolicyManager;
import com.samsung.android.settings.wifi.WifiGateMessageManager;
import com.samsung.android.settingslib.wifi.WifiWarningDialogController;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.IMSParameter;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WifiEnabler
        implements SwitchWidgetController.OnSwitchChangeListener,
                Preference.OnPreferenceChangeListener,
                CompoundButton.OnCheckedChangeListener {
    public final AtomicBoolean mConnected;
    public final AnonymousClass2 mContentObserver;
    public Context mContext;
    public int mCurrentWifiState;
    public boolean mDisabledByAdmin;
    public final WifiGateMessageManager mGateMessageManager;
    public final IntentFilter mIntentFilter;
    public boolean mIsConnectedState;
    public final boolean mIsGuestUser;
    public boolean mIsWifiBlocked;
    public boolean mIsWifiStateBlocked;
    public WifiSettings.AnonymousClass6 mListener;
    public boolean mListeningToOnSwitchChange;
    public final Handler mMainHandler;
    public final MetricsFeatureProvider mMetricsFeatureProvider;
    public final AnonymousClass1 mReceiver;
    public boolean mRegisteredReceiver;
    public final RestrictionUtils mRestrictionUtils;
    public boolean mStateMachineEvent;
    public final SecSwitchPreferenceScreen mSwitchPreference;
    public final Switch mSwitchView;
    public final SwitchWidgetController mSwitchWidget;
    public final TextView mTextView;
    public final WifiWarningDialogController mWarningDialogController;
    public final WifiManager mWifiManager;

    /* renamed from: -$$Nest$mhandleStateChanged, reason: not valid java name */
    public static void m1024$$Nest$mhandleStateChanged(
            WifiEnabler wifiEnabler, NetworkInfo.DetailedState detailedState) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen;
        wifiEnabler.getClass();
        Log.d("WifiEnabler", "handleStateChanged " + detailedState);
        if (detailedState == null
                || (secSwitchPreferenceScreen = wifiEnabler.mSwitchPreference) == null
                || !secSwitchPreferenceScreen.mChecked) {
            return;
        }
        NetworkInfo.DetailedState detailedState2 = NetworkInfo.DetailedState.CONNECTED;
        String str = ApnSettings.MVNO_NONE;
        if (detailedState != detailedState2) {
            if (detailedState == NetworkInfo.DetailedState.DISCONNECTED) {
                secSwitchPreferenceScreen.setSummary(ApnSettings.MVNO_NONE);
                wifiEnabler.mIsConnectedState = false;
                return;
            }
            return;
        }
        android.net.wifi.WifiInfo connectionInfo = wifiEnabler.mWifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            wifiEnabler.mIsConnectedState = true;
            if (connectionInfo.isPasspointAp()) {
                str = connectionInfo.getPasspointProviderFriendlyName();
            } else if (!SemWifiUtils.removeDoubleQuotes(connectionInfo.getSSID())
                    .equals("<unknown ssid>")) {
                str = SemWifiUtils.removeDoubleQuotes(connectionInfo.getSSID());
            }
            wifiEnabler.mSwitchPreference.setSummary(str);
        }
    }

    public WifiEnabler(
            Context context,
            SwitchWidgetController switchWidgetController,
            MetricsFeatureProvider metricsFeatureProvider) {
        this(context, switchWidgetController, metricsFeatureProvider, null, null, null, null);
    }

    public final void handleWifiStateChanged(int i) {
        if (i == 0 || i == 2) {
            setSwitchBarEnabled(false, true);
        } else if (i != 3) {
            setSwitchBarChecked$2(false);
            setSwitchBarEnabled(true, true);
        } else {
            setSwitchBarChecked$2(true);
            setSwitchBarEnabled(true, true);
        }
    }

    public boolean maybeEnforceRestrictions() {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                this.mRestrictionUtils == null
                        ? null
                        : RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                this.mContext, UserHandle.myUserId(), "no_config_wifi");
        SwitchWidgetController switchWidgetController = this.mSwitchWidget;
        if (switchWidgetController != null) {
            switchWidgetController.setDisabledByAdmin(checkIfRestrictionEnforced);
            if (checkIfRestrictionEnforced != null) {
                Log.i("WifiEnabler", "maybeEnforceRestrictions: restricted by enforced admin");
            }
        }
        return checkIfRestrictionEnforced != null;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        onSwitchChanged(z);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        SALogging.insertSALog(
                booleanValue ? 1L : 0L,
                String.valueOf(
                        VolteConstants.ErrorCode.SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED),
                "0102");
        return onSwitchChanged(booleanValue);
    }

    public final boolean onSwitchChanged(boolean z) {
        String string;
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m("onSwitchChanged : ", "WifiEnabler", z);
        int i = this.mCurrentWifiState;
        if ((i != 3 || z) && !((i == 1 || i == 4) && z)) {
            return false;
        }
        if (this.mStateMachineEvent) {
            return true;
        }
        if (this.mIsWifiBlocked && !this.mIsWifiStateBlocked) {
            return false;
        }
        if (this.mIsWifiStateBlocked) {
            Toast.makeText(this.mContext, R.string.mdm_policy_not_allow_wifi, 0).show();
            return false;
        }
        if (z) {
            Context context = this.mContext;
            if (WirelessUtils.isAirplaneModeOn(context)
                    && ((string =
                                            Settings.Global.getString(
                                                    context.getContentResolver(),
                                                    "airplane_mode_toggleable_radios"))
                                    == null
                            || !string.contains(ImsProfile.PDN_WIFI))) {
                Toast.makeText(this.mContext, R.string.wifi_in_airplane_mode, 0).show();
                setSwitchBarChecked$2(false);
                return false;
            }
        }
        MetricsFeatureProvider metricsFeatureProvider = this.mMetricsFeatureProvider;
        if (metricsFeatureProvider != null) {
            metricsFeatureProvider.action(this.mContext, z ? 139 : 138, this.mConnected.get());
        }
        if (!z && Utils.SUPPORT_MOBILEAP_WIFISHARINGLITE) {
            Settings.System.putInt(
                    this.mContext.getContentResolver(), "wifi_sharing_lite_popup_status", 0);
        }
        setSwitchBarEnabled(false, true);
        if (z && this.mWarningDialogController.isNeedToConfirmApDisable()) {
            setSwitchBarChecked$2(!z);
            setSwitchBarEnabled(true, true);
            return false;
        }
        if (this.mListener != null) {
            this.mMainHandler.post(new WifiEnabler$$ExternalSyntheticLambda0(this, z, 1));
        }
        new Handler().postDelayed(new WifiEnabler$$ExternalSyntheticLambda0(this, z, 0), 200L);
        return true;
    }

    @Override // com.android.settings.widget.SwitchWidgetController.OnSwitchChangeListener
    public final boolean onSwitchToggled(boolean z) {
        if (this.mStateMachineEvent) {
            return true;
        }
        if (this.mSwitchWidget != null) {
            SALogging.insertSALog(z ? 1L : 0L, "WIFI_100", "0102", (String) null);
        }
        return onSwitchChanged(z);
    }

    public final void pause() {
        Log.d("WifiEnabler", "pause");
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mSwitchPreference;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setOnPreferenceChangeListener(null);
        }
        Switch r0 = this.mSwitchView;
        if (r0 != null) {
            r0.setOnCheckedChangeListener(null);
        }
        this.mContext.getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    public final void resume() {
        boolean z;
        boolean z2;
        Log.d("WifiEnabler", "resume");
        Uri uriFor = Settings.Global.getUriFor("satellite_mode_enabled");
        if (uriFor == null) {
            Log.d("WifiEnabler", "satellite mode key does not exist in Settings");
        } else {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uriFor, false, this.mContentObserver);
        }
        if (WifiDevicePolicyManager.getCursorValuePositive(
                this.mContext,
                "content://com.sec.knox.provider/RestrictionPolicy4",
                "isWifiEnabled",
                null,
                "false")) {
            Log.i("WifiDevicePolicyManager", "isAllowedWifiEnabled false");
            z = false;
        } else {
            z = true;
        }
        this.mIsWifiBlocked = !z;
        if (WifiDevicePolicyManager.getCursorValuePositive(
                this.mContext,
                "content://com.sec.knox.provider2/WifiPolicy",
                "isWifiStateChangeAllowed",
                null,
                "false")) {
            Log.i("WifiDevicePolicyManager", "isAllowedWifiStateChange false");
            z2 = false;
        } else {
            z2 = true;
        }
        boolean z3 = !z2;
        this.mIsWifiStateBlocked = z3;
        if (this.mIsWifiBlocked || z3) {
            StringBuilder sb = new StringBuilder("isWifiBlocked: restricted by MDM ");
            sb.append(this.mIsWifiBlocked);
            sb.append(" ");
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    sb, this.mIsWifiStateBlocked, "WifiEnabler");
        }
        if (!this.mIsWifiStateBlocked && (maybeEnforceRestrictions() || this.mIsGuestUser)) {
            Log.i("WifiEnabler", "isWifiBlocked: restricted by enforced admin");
            this.mIsWifiStateBlocked = true;
        }
        if (!WifiEnterpriseRestrictionUtils.isChangeWifiStateAllowed(this.mContext)) {
            this.mIsWifiBlocked = true;
        }
        if (this.mIsWifiBlocked) {
            setSwitchBarEnabled(false, true);
        } else if (this.mIsWifiStateBlocked) {
            setSwitchBarEnabled(false, false);
            setSwitchBarChecked$2(this.mCurrentWifiState == 3);
        }
        if (this.mIsWifiBlocked) {
            handleWifiStateChanged(this.mCurrentWifiState);
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mSwitchPreference;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setOnPreferenceChangeListener(this);
        }
        Switch r0 = this.mSwitchView;
        if (r0 != null) {
            r0.setOnCheckedChangeListener(this);
        }
    }

    public final void setSwitchBarChecked$2(boolean z) {
        String str;
        android.net.wifi.WifiInfo connectionInfo;
        this.mStateMachineEvent = true;
        SwitchWidgetController switchWidgetController = this.mSwitchWidget;
        if (switchWidgetController != null && switchWidgetController.isChecked() != z) {
            this.mSwitchWidget.setChecked(z);
        }
        SecSwitchPreferenceScreen secSwitchPreferenceScreen = this.mSwitchPreference;
        if (secSwitchPreferenceScreen != null) {
            secSwitchPreferenceScreen.setChecked(z);
            SecSwitchPreferenceScreen secSwitchPreferenceScreen2 = this.mSwitchPreference;
            secSwitchPreferenceScreen2.getClass();
            SecPreferenceUtils.applySummaryColor(secSwitchPreferenceScreen2, z);
            if (this.mIsConnectedState
                    && z
                    && (connectionInfo = this.mWifiManager.getConnectionInfo()) != null) {
                if (connectionInfo.isPasspointAp()) {
                    str = connectionInfo.getPasspointProviderFriendlyName();
                } else if (!SemWifiUtils.removeDoubleQuotes(connectionInfo.getSSID())
                        .equals("<unknown ssid>")) {
                    str = SemWifiUtils.removeDoubleQuotes(connectionInfo.getSSID());
                }
                this.mSwitchPreference.setSummary(str);
            }
            str = null;
            this.mSwitchPreference.setSummary(str);
        }
        Switch r0 = this.mSwitchView;
        if (r0 != null) {
            r0.setChecked(z);
        }
        this.mStateMachineEvent = false;
    }

    public final void setSwitchBarEnabled(boolean z, boolean z2) {
        SecSwitchPreferenceScreen secSwitchPreferenceScreen;
        if (this.mIsWifiStateBlocked
                || this.mIsWifiBlocked
                || SemWifiUtils.isSatelliteModeOn(this.mContext)) {
            z = false;
        }
        SwitchWidgetController switchWidgetController = this.mSwitchWidget;
        if (switchWidgetController != null) {
            switchWidgetController.setEnabled(z);
        }
        if (z2 && (secSwitchPreferenceScreen = this.mSwitchPreference) != null) {
            if (this.mDisabledByAdmin) {
                boolean z3 =
                        RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                        this.mContext, UserHandle.myUserId(), "no_config_wifi")
                                != null;
                if (this.mDisabledByAdmin != z3) {
                    this.mDisabledByAdmin = z3;
                }
            } else {
                secSwitchPreferenceScreen.setEnabled(z);
            }
        }
        Switch r5 = this.mSwitchView;
        if (r5 != null) {
            r5.setEnabled(z);
        }
        TextView textView = this.mTextView;
        if (textView != null) {
            textView.setEnabled(z);
        }
    }

    public final void stop() {
        Log.d("WifiEnabler", "stop");
        if (this.mRegisteredReceiver) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mRegisteredReceiver = false;
        }
        if (this.mListeningToOnSwitchChange) {
            this.mSwitchWidget.stopListening();
            this.mListeningToOnSwitchChange = false;
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.settings.wifi.WifiEnabler$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settings.wifi.WifiEnabler$2] */
    public WifiEnabler(
            Context context,
            SwitchWidgetController switchWidgetController,
            MetricsFeatureProvider metricsFeatureProvider,
            SecSwitchPreferenceScreen secSwitchPreferenceScreen,
            Switch r8,
            TextView textView,
            ConnectivityManager connectivityManager) {
        this.mListeningToOnSwitchChange = false;
        this.mConnected = new AtomicBoolean(false);
        this.mCurrentWifiState = 4;
        this.mRegisteredReceiver = false;
        this.mIsConnectedState = false;
        this.mIsWifiBlocked = false;
        this.mIsWifiStateBlocked = false;
        this.mReceiver =
                new BroadcastReceiver() { // from class: com.android.settings.wifi.WifiEnabler.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        NetworkInfo.DetailedState detailedState;
                        String action = intent.getAction();
                        if (!"android.net.wifi.WIFI_STATE_CHANGED".equals(action)) {
                            if ("android.net.wifi.supplicant.STATE_CHANGE".equals(action)) {
                                if (WifiEnabler.this.mConnected.get()) {
                                    return;
                                }
                                WifiEnabler.m1024$$Nest$mhandleStateChanged(
                                        WifiEnabler.this,
                                        android.net.wifi.WifiInfo.getDetailedStateOf(
                                                (SupplicantState)
                                                        intent.getParcelableExtra("newState")));
                                return;
                            } else {
                                if ("android.net.wifi.STATE_CHANGE".equals(action)) {
                                    NetworkInfo networkInfo =
                                            (NetworkInfo)
                                                    intent.getParcelableExtra(
                                                            IMSParameter.GENERAL.NETWORK_INFO);
                                    WifiEnabler.this.mConnected.set(networkInfo.isConnected());
                                    WifiEnabler.m1024$$Nest$mhandleStateChanged(
                                            WifiEnabler.this, networkInfo.getDetailedState());
                                    WifiGateMessageManager wifiGateMessageManager =
                                            WifiEnabler.this.mGateMessageManager;
                                    if (wifiGateMessageManager == null
                                            || (detailedState = networkInfo.getDetailedState())
                                                    == wifiGateMessageManager.mPreviousState) {
                                        return;
                                    }
                                    if (detailedState == NetworkInfo.DetailedState.CONNECTED) {
                                        Log.i("GATE", "<GATE-M> WIFI_STATUS_CONNECTED </GATE-M>");
                                    } else if (detailedState
                                            == NetworkInfo.DetailedState.DISCONNECTED) {
                                        Log.i(
                                                "GATE",
                                                "<GATE-M> WIFI_STATUS_DISCONNECTED </GATE-M>");
                                    }
                                    wifiGateMessageManager.mPreviousState = detailedState;
                                    return;
                                }
                                return;
                            }
                        }
                        WifiEnabler wifiEnabler = WifiEnabler.this;
                        if (wifiEnabler.mIsWifiBlocked) {
                            return;
                        }
                        wifiEnabler.mCurrentWifiState = wifiEnabler.mWifiManager.getWifiState();
                        WifiEnabler wifiEnabler2 = WifiEnabler.this;
                        wifiEnabler2.handleWifiStateChanged(
                                wifiEnabler2.mWifiManager.getWifiState());
                        WifiEnabler wifiEnabler3 = WifiEnabler.this;
                        WifiGateMessageManager wifiGateMessageManager2 =
                                wifiEnabler3.mGateMessageManager;
                        if (wifiGateMessageManager2 != null) {
                            int i = wifiEnabler3.mCurrentWifiState;
                            wifiGateMessageManager2.getClass();
                            if (i == 0) {
                                Log.i("GATE", "<GATE-M> WIFI_DISABLING </GATE-M>");
                            } else if (i == 1) {
                                Log.i("GATE", "<GATE-M> WIFI_OFF </GATE-M>");
                            } else {
                                if (i != 3) {
                                    return;
                                }
                                Log.i("GATE", "<GATE-M> WIFI_ON </GATE-M>");
                            }
                        }
                    }
                };
        this.mContentObserver =
                new ContentObserver(
                        new Handler()) { // from class: com.android.settings.wifi.WifiEnabler.2
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        WifiEnabler.this.setSwitchBarEnabled(
                                !SemWifiUtils.isSatelliteModeOn(r1.mContext), true);
                    }
                };
        this.mContext = context;
        this.mSwitchWidget = switchWidgetController;
        if (switchWidgetController != null) {
            switchWidgetController.mListener = this;
        }
        this.mMetricsFeatureProvider = metricsFeatureProvider;
        WifiManager wifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mWifiManager = wifiManager;
        this.mSwitchPreference = secSwitchPreferenceScreen;
        this.mSwitchView = r8;
        this.mTextView = textView;
        this.mWarningDialogController = new WifiWarningDialogController(this.mContext);
        boolean z =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                                this.mContext, UserHandle.myUserId(), "no_config_wifi")
                        != null;
        if (this.mDisabledByAdmin != z) {
            this.mDisabledByAdmin = z;
        }
        if (this.mCurrentWifiState == 4) {
            this.mCurrentWifiState = wifiManager.getWifiState();
        }
        int i = this.mCurrentWifiState;
        if (!this.mIsWifiBlocked) {
            if (i == 3) {
                setSwitchBarChecked$2(true);
            } else if (i == 2) {
                setSwitchBarChecked$2(true);
                setSwitchBarEnabled(false, true);
            } else {
                setSwitchBarChecked$2(false);
                if (i == 0) {
                    setSwitchBarEnabled(false, true);
                }
            }
        }
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED");
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        this.mRestrictionUtils = new RestrictionUtils();
        if (SemGateConfig.isGateEnabled()) {
            WifiGateMessageManager wifiGateMessageManager = new WifiGateMessageManager();
            wifiGateMessageManager.mPreviousState = null;
            this.mGateMessageManager = wifiGateMessageManager;
        }
        this.mMainHandler = new Handler(this.mContext.getMainLooper());
        SubscriptionManager subscriptionManager = SemWifiUtils.mSubscriptionManager;
        this.mIsGuestUser = ((UserManager) context.getSystemService("user")).isGuestUser();
        handleWifiStateChanged(this.mCurrentWifiState);
        if (switchWidgetController != null) {
            if (!this.mListeningToOnSwitchChange) {
                switchWidgetController.startListening();
                this.mListeningToOnSwitchChange = true;
            }
            switchWidgetController.setupView();
        }
    }
}
