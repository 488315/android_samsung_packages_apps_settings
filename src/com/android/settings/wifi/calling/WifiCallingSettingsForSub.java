package com.android.settings.wifi.calling;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ProvisioningManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.ims.WifiCallingQueryImsState;
import com.android.settings.widget.SettingsMainSwitchBar;
import com.android.settings.widget.SettingsMainSwitchPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WifiCallingSettingsForSub extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener, Preference.OnPreferenceChangeListener {

    @VisibleForTesting static final int REQUEST_CHECK_WFC_DISCLAIMER = 2;

    @VisibleForTesting static final int REQUEST_CHECK_WFC_EMERGENCY_ADDRESS = 1;
    public ListWithEntrySummaryPreference mButtonWfcMode;
    public ListWithEntrySummaryPreference mButtonWfcRoamingMode;
    public ImsMmTelManager mImsMmTelManager;
    public IntentFilter mIntentFilter;
    public ProvisioningManager mProvisioningManager;
    public SettingsMainSwitchPreference mSwitchBar;
    public PhoneTelephonyCallback mTelephonyCallback;
    public TelephonyManager mTelephonyManager;
    public Preference mUpdateAddress;
    public boolean mEditableWfcMode = true;
    public boolean mEditableWfcRoamingMode = true;
    public boolean mUseWfcHomeModeForRoaming = false;
    public boolean mOverrideWfcRoamingModeWhileUsingNtn = false;
    public int mSubId = -1;
    public final WifiCallingSettingsForSub$$ExternalSyntheticLambda0 mUpdateAddressListener =
            new Preference
                    .OnPreferenceClickListener() { // from class:
                                                   // com.android.settings.wifi.calling.WifiCallingSettingsForSub$$ExternalSyntheticLambda0
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final boolean onPreferenceClick(Preference preference) {
                    WifiCallingSettingsForSub wifiCallingSettingsForSub =
                            WifiCallingSettingsForSub.this;
                    Intent carrierActivityIntent =
                            wifiCallingSettingsForSub.getCarrierActivityIntent();
                    if (carrierActivityIntent != null) {
                        carrierActivityIntent.putExtra("EXTRA_LAUNCH_CARRIER_APP", 1);
                        wifiCallingSettingsForSub.startActivity(carrierActivityIntent);
                    }
                    return true;
                }
            };
    public final AnonymousClass1 mProvisioningCallback =
            new ProvisioningManager
                    .Callback() { // from class:
                                  // com.android.settings.wifi.calling.WifiCallingSettingsForSub.1
                public final void onProvisioningIntChanged(int i, int i2) {
                    if (i == 28 || i == 10) {
                        WifiCallingSettingsForSub.this.updateBody();
                    }
                }
            };
    public final AnonymousClass2 mIntentReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.android.settings.wifi.calling.WifiCallingSettingsForSub.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    if (intent.getAction()
                            .equals("android.telephony.ims.action.WFC_IMS_REGISTRATION_ERROR")) {
                        setResultCode(0);
                        WifiCallingSettingsForSub.this.showAlert(intent);
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PhoneTelephonyCallback extends TelephonyCallback
            implements TelephonyCallback.CallStateListener {
        public PhoneTelephonyCallback() {}

        /* JADX WARN: Code restructure failed: missing block: B:32:0x00b8, code lost:

           if (((r8 != null && r8.isUsingNonTerrestrialNetwork()) ? r7.mOverrideWfcRoamingModeWhileUsingNtn : false) == false) goto L48;
        */
        @Override // android.telephony.TelephonyCallback.CallStateListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onCallStateChanged(int r8) {
            /*
                r7 = this;
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r8 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                androidx.fragment.app.FragmentActivity r8 = r8.getActivity()
                com.android.settings.SettingsActivity r8 = (com.android.settings.SettingsActivity) r8
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r0 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                androidx.preference.PreferenceScreen r0 = r0.getPreferenceScreen()
                java.lang.String r1 = "wifi_calling_switch_bar"
                androidx.preference.Preference r0 = r0.findPreference(r1)
                com.android.settings.widget.SettingsMainSwitchPreference r0 = (com.android.settings.widget.SettingsMainSwitchPreference) r0
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L52
                boolean r3 = r0.mChecked
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r4 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                int r5 = r4.mSubId
                android.telephony.TelephonyManager r4 = r4.getTelephonyManagerForSub(r5)
                int r4 = r4.getCallStateForSubscription()
                if (r4 != 0) goto L2c
                r4 = r1
                goto L2d
            L2c:
                r4 = r2
            L2d:
                if (r3 != 0) goto L34
                if (r4 == 0) goto L32
                goto L34
            L32:
                r5 = r1
                goto L40
            L34:
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r5 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                int r6 = r5.mSubId
                com.android.settings.network.ims.WifiCallingQueryImsState r5 = r5.queryImsState(r6)
                boolean r5 = r5.isAllowUserControl()
            L40:
                if (r3 == 0) goto L46
                if (r5 == 0) goto L46
                r3 = r1
                goto L47
            L46:
                r3 = r2
            L47:
                if (r4 == 0) goto L4d
                if (r5 == 0) goto L4d
                r5 = r1
                goto L4e
            L4d:
                r5 = r2
            L4e:
                r0.setEnabled(r5)
                goto L54
            L52:
                r3 = r2
                r4 = r3
            L54:
                if (r3 == 0) goto L7c
                if (r4 == 0) goto L7c
                java.lang.String r0 = "carrier_config"
                java.lang.Object r8 = r8.getSystemService(r0)
                android.telephony.CarrierConfigManager r8 = (android.telephony.CarrierConfigManager) r8
                if (r8 == 0) goto L79
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r0 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                int r0 = r0.mSubId
                android.os.PersistableBundle r8 = r8.getConfigForSubId(r0)
                if (r8 == 0) goto L79
                java.lang.String r0 = "editable_wfc_mode_bool"
                boolean r0 = r8.getBoolean(r0)
                java.lang.String r3 = "editable_wfc_roaming_mode_bool"
                boolean r8 = r8.getBoolean(r3)
                goto L7e
            L79:
                r0 = r1
                r8 = r2
                goto L7e
            L7c:
                r8 = r2
                r0 = r8
            L7e:
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r3 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                androidx.preference.PreferenceScreen r3 = r3.getPreferenceScreen()
                java.lang.String r4 = "wifi_calling_mode"
                androidx.preference.Preference r3 = r3.findPreference(r4)
                if (r3 == 0) goto L8f
                r3.setEnabled(r0)
            L8f:
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r0 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                androidx.preference.PreferenceScreen r0 = r0.getPreferenceScreen()
                java.lang.String r3 = "wifi_calling_roaming_mode"
                androidx.preference.Preference r0 = r0.findPreference(r3)
                if (r0 == 0) goto Lbf
                if (r8 == 0) goto Lbb
                com.android.settings.wifi.calling.WifiCallingSettingsForSub r7 = com.android.settings.wifi.calling.WifiCallingSettingsForSub.this
                int r8 = r7.mSubId
                android.telephony.TelephonyManager r8 = r7.getTelephonyManagerForSub(r8)
                android.telephony.ServiceState r8 = r8.getServiceState()
                if (r8 != 0) goto Laf
            Lad:
                r7 = r2
                goto Lb8
            Laf:
                boolean r8 = r8.isUsingNonTerrestrialNetwork()
                if (r8 != 0) goto Lb6
                goto Lad
            Lb6:
                boolean r7 = r7.mOverrideWfcRoamingModeWhileUsingNtn
            Lb8:
                if (r7 != 0) goto Lbb
                goto Lbc
            Lbb:
                r1 = r2
            Lbc:
                r0.setEnabled(r1)
            Lbf:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.wifi.calling.WifiCallingSettingsForSub.PhoneTelephonyCallback.onCallStateChanged(int):void");
        }
    }

    public final Intent getCarrierActivityIntent() {
        PersistableBundle configForSubId;
        ComponentName unflattenFromString;
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) getActivity().getSystemService(CarrierConfigManager.class);
        if (carrierConfigManager == null
                || (configForSubId = carrierConfigManager.getConfigForSubId(this.mSubId)) == null) {
            return null;
        }
        String string = configForSubId.getString("wfc_emergency_address_carrier_app_string");
        if (TextUtils.isEmpty(string)
                || (unflattenFromString = ComponentName.unflattenFromString(string)) == null) {
            return null;
        }
        Intent intent = new Intent();
        intent.setComponent(unflattenFromString);
        intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", this.mSubId);
        return intent;
    }

    @VisibleForTesting
    public ImsMmTelManager getImsMmTelManager() {
        if (SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            return ImsMmTelManager.createForSubscriptionId(this.mSubId);
        }
        return null;
    }

    @VisibleForTesting
    public ProvisioningManager getImsProvisioningManager() {
        if (SubscriptionManager.isValidSubscriptionId(this.mSubId)) {
            return ProvisioningManager.createForSubscriptionId(this.mSubId);
        }
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1230;
    }

    @VisibleForTesting
    public Resources getResourcesForSubId() {
        return SubscriptionManager.getResourcesForSubId(getContext(), this.mSubId);
    }

    @VisibleForTesting
    public TelephonyManager getTelephonyManagerForSub(int i) {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager =
                    (TelephonyManager) getContext().getSystemService(TelephonyManager.class);
        }
        return this.mTelephonyManager.createForSubscriptionId(i);
    }

    public final CharSequence getWfcModeSummary(int i) {
        int i2;
        if (queryImsState(this.mSubId).isEnabledByUser()) {
            if (i == 0) {
                i2 = 17043507;
            } else if (i == 1) {
                i2 = 17043506;
            } else if (i != 2) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Unexpected WFC mode value: ", "WifiCallingForSub");
            } else {
                i2 = 17043508;
            }
            return getResourcesForSubId().getString(i2);
        }
        i2 = 17043585;
        return getResourcesForSubId().getString(i2);
    }

    @VisibleForTesting
    public boolean isWfcProvisionedOnDevice() {
        return queryImsState(this.mSubId).isWifiCallingProvisioned();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                "WFC activity request = ", " result = ", i, i2, "WifiCallingForSub");
        if (i == 1) {
            if (i2 == -1) {
                updateWfcMode(true);
            }
        } else {
            if (i != 2) {
                WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                        .m(i, "Unexpected request: ", "WifiCallingForSub");
                return;
            }
            if (i2 == -1) {
                Intent carrierActivityIntent = getCarrierActivityIntent();
                if (carrierActivityIntent == null) {
                    updateWfcMode(true);
                } else {
                    carrierActivityIntent.putExtra("EXTRA_LAUNCH_CARRIER_APP", 0);
                    startActivityForResult(carrierActivityIntent, 1);
                }
            }
        }
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Log.d("WifiCallingForSub", "onSwitchChanged(" + z + ")");
        if (!z) {
            updateWfcMode(false);
            return;
        }
        FragmentActivity activity = getActivity();
        Bundle bundle = new Bundle();
        bundle.putInt("EXTRA_SUB_ID", this.mSubId);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
        String name = WifiCallingDisclaimerFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.wifi_calling_settings_title, null);
        launchRequest.mSourceMetricsCategory = 1230;
        subSettingLauncher.setResultListener(this, 2);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.wifi_calling_settings);
        if (getArguments() != null && getArguments().containsKey("subId")) {
            this.mSubId = getArguments().getInt("subId");
        } else if (bundle != null) {
            this.mSubId = bundle.getInt("subId", -1);
        }
        this.mProvisioningManager = getImsProvisioningManager();
        this.mImsMmTelManager = getImsMmTelManager();
        this.mSwitchBar = (SettingsMainSwitchPreference) findPreference("wifi_calling_switch_bar");
        ListWithEntrySummaryPreference listWithEntrySummaryPreference =
                (ListWithEntrySummaryPreference) findPreference("wifi_calling_mode");
        this.mButtonWfcMode = listWithEntrySummaryPreference;
        listWithEntrySummaryPreference.setOnPreferenceChangeListener(this);
        ListWithEntrySummaryPreference listWithEntrySummaryPreference2 =
                (ListWithEntrySummaryPreference) findPreference("wifi_calling_roaming_mode");
        this.mButtonWfcRoamingMode = listWithEntrySummaryPreference2;
        listWithEntrySummaryPreference2.setOnPreferenceChangeListener(this);
        Preference findPreference = findPreference("emergency_address_key");
        this.mUpdateAddress = findPreference;
        findPreference.setOnPreferenceClickListener(this.mUpdateAddressListener);
        IntentFilter intentFilter = new IntentFilter();
        this.mIntentFilter = intentFilter;
        intentFilter.addAction("android.telephony.ims.action.WFC_IMS_REGISTRATION_ERROR");
        updateDescriptionForOptions(
                List.of(this.mButtonWfcMode, this.mButtonWfcRoamingMode, this.mUpdateAddress));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        R.layout.wifi_calling_settings_preferences, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup) inflate.findViewById(android.R.id.tabcontent);
        Utils.prepareCustomPreferencesList(viewGroup, inflate, viewGroup2);
        viewGroup2.addView(super.onCreateView(layoutInflater, viewGroup2, bundle));
        return inflate;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        FragmentActivity activity = getActivity();
        if (this.mTelephonyCallback != null) {
            getTelephonyManagerForSub(this.mSubId)
                    .unregisterTelephonyCallback(this.mTelephonyCallback);
            this.mTelephonyCallback = null;
            SettingsMainSwitchPreference settingsMainSwitchPreference = this.mSwitchBar;
            ((ArrayList) settingsMainSwitchPreference.mSwitchChangeListeners).remove(this);
            SettingsMainSwitchBar settingsMainSwitchBar =
                    settingsMainSwitchPreference.mMainSwitchBar;
            if (settingsMainSwitchBar != null) {
                settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            }
        }
        activity.unregisterReceiver(this.mIntentReceiver);
        unregisterProvisioningChangedCallback();
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int i = -1;
        boolean z = false;
        if (preference == this.mButtonWfcMode) {
            Log.d("WifiCallingForSub", "onPreferenceChange mButtonWfcMode " + obj);
            String str = (String) obj;
            this.mButtonWfcMode.setValue(str);
            int intValue = Integer.valueOf(str).intValue();
            try {
                i = this.mImsMmTelManager.getVoWiFiModeSetting();
            } catch (IllegalArgumentException e) {
                Log.e("WifiCallingForSub", "onPreferenceChange: Exception", e);
                z = true;
            }
            if (intValue != i && !z) {
                try {
                    this.mImsMmTelManager.setVoWiFiModeSetting(intValue);
                    this.mButtonWfcMode.setSummary(getWfcModeSummary(intValue));
                    this.mMetricsFeatureProvider.action(getActivity(), 1230, intValue);
                    if (this.mUseWfcHomeModeForRoaming) {
                        this.mImsMmTelManager.setVoWiFiRoamingModeSetting(intValue);
                    }
                } catch (IllegalArgumentException e2) {
                    Log.e("WifiCallingForSub", "onPreferenceChange: Exception", e2);
                }
            }
        } else {
            ListWithEntrySummaryPreference listWithEntrySummaryPreference =
                    this.mButtonWfcRoamingMode;
            if (preference == listWithEntrySummaryPreference) {
                String str2 = (String) obj;
                listWithEntrySummaryPreference.setValue(str2);
                int intValue2 = Integer.valueOf(str2).intValue();
                try {
                    i = this.mImsMmTelManager.getVoWiFiRoamingModeSetting();
                } catch (IllegalArgumentException e3) {
                    Log.e("WifiCallingForSub", "updateWfcMode: Exception", e3);
                    z = true;
                }
                if (intValue2 != i && !z) {
                    try {
                        this.mImsMmTelManager.setVoWiFiRoamingModeSetting(intValue2);
                        this.mMetricsFeatureProvider.action(getActivity(), 1230, intValue2);
                    } catch (IllegalArgumentException e4) {
                        Log.e("WifiCallingForSub", "onPreferenceChange: Exception", e4);
                    }
                }
            }
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        updateBody();
        FragmentActivity activity = getActivity();
        if (this.mTelephonyCallback == null
                && queryImsState(this.mSubId).isWifiCallingSupported()) {
            this.mTelephonyCallback = new PhoneTelephonyCallback();
            getTelephonyManagerForSub(this.mSubId)
                    .registerTelephonyCallback(activity.getMainExecutor(), this.mTelephonyCallback);
            this.mSwitchBar.addOnSwitchChangeListener(this);
        }
        activity.registerReceiver(this.mIntentReceiver, this.mIntentFilter, 2);
        Intent intent = getActivity().getIntent();
        if (intent.getBooleanExtra("alertShow", false)) {
            showAlert(intent);
        }
        registerProvisioningChangedCallback();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("subId", this.mSubId);
        super.onSaveInstanceState(bundle);
    }

    @VisibleForTesting
    public WifiCallingQueryImsState queryImsState(int i) {
        return new WifiCallingQueryImsState(getContext(), i);
    }

    @VisibleForTesting
    public void registerProvisioningChangedCallback() {
        ProvisioningManager provisioningManager = this.mProvisioningManager;
        if (provisioningManager == null) {
            return;
        }
        try {
            provisioningManager.registerProvisioningChangedCallback(
                    getContext().getMainExecutor(), this.mProvisioningCallback);
        } catch (Exception unused) {
            Log.w(
                    "WifiCallingForSub",
                    "onResume: Unable to register callback for provisioning changes.");
        }
    }

    @VisibleForTesting
    public void showAlert(Intent intent) {
        FragmentActivity activity = getActivity();
        CharSequence charSequenceExtra =
                intent.getCharSequenceExtra(
                        "android.telephony.ims.extra.WFC_REGISTRATION_FAILURE_TITLE");
        CharSequence charSequenceExtra2 =
                intent.getCharSequenceExtra(
                        "android.telephony.ims.extra.WFC_REGISTRATION_FAILURE_MESSAGE");
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mMessage = charSequenceExtra2;
        alertParams.mTitle = charSequenceExtra;
        alertParams.mIconId = android.R.drawable.ic_dialog_alert;
        builder.setPositiveButton(android.R.string.ok, (DialogInterface.OnClickListener) null);
        builder.create().show();
    }

    @VisibleForTesting
    public void unregisterProvisioningChangedCallback() {
        ProvisioningManager provisioningManager = this.mProvisioningManager;
        if (provisioningManager == null) {
            return;
        }
        provisioningManager.unregisterProvisioningChangedCallback(this.mProvisioningCallback);
    }

    public final void updateBody() {
        boolean z;
        int i;
        boolean z2;
        PersistableBundle configForSubId;
        if (!isWfcProvisionedOnDevice()) {
            finish();
            return;
        }
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) getSystemService("carrier_config");
        boolean z3 = false;
        if (carrierConfigManager == null
                || (configForSubId = carrierConfigManager.getConfigForSubId(this.mSubId)) == null) {
            z = true;
        } else {
            this.mEditableWfcMode = configForSubId.getBoolean("editable_wfc_mode_bool");
            this.mEditableWfcRoamingMode =
                    configForSubId.getBoolean("editable_wfc_roaming_mode_bool");
            this.mUseWfcHomeModeForRoaming =
                    configForSubId.getBoolean(
                            "use_wfc_home_network_mode_in_roaming_network_bool", false);
            z = configForSubId.getBoolean("carrier_wfc_supports_wifi_only_bool", true);
            this.mOverrideWfcRoamingModeWhileUsingNtn =
                    configForSubId.getBoolean(
                            "override_wfc_roaming_mode_while_using_ntn_bool", true);
        }
        Resources resourcesForSubId = getResourcesForSubId();
        this.mButtonWfcMode.setTitle(resourcesForSubId.getString(R.string.wifi_calling_mode_title));
        this.mButtonWfcMode.mDialogTitle =
                resourcesForSubId.getString(R.string.wifi_calling_mode_dialog_title);
        this.mButtonWfcRoamingMode.setTitle(
                resourcesForSubId.getString(R.string.wifi_calling_roaming_mode_title));
        this.mButtonWfcRoamingMode.mDialogTitle =
                resourcesForSubId.getString(R.string.wifi_calling_roaming_mode_dialog_title);
        if (z) {
            this.mButtonWfcMode.mEntries =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_choices);
            this.mButtonWfcMode.mEntryValues =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_values);
            this.mButtonWfcMode.mSummaries =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_summaries);
            this.mButtonWfcRoamingMode.mEntries =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_choices_v2);
            this.mButtonWfcRoamingMode.mEntryValues =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_values);
            this.mButtonWfcRoamingMode.mSummaries =
                    resourcesForSubId.getStringArray(R.array.wifi_calling_mode_summaries);
        } else {
            this.mButtonWfcMode.mEntries =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_choices_without_wifi_only);
            this.mButtonWfcMode.mEntryValues =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_values_without_wifi_only);
            this.mButtonWfcMode.mSummaries =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_summaries_without_wifi_only);
            this.mButtonWfcRoamingMode.mEntries =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_choices_v2_without_wifi_only);
            this.mButtonWfcRoamingMode.mEntryValues =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_values_without_wifi_only);
            this.mButtonWfcRoamingMode.mSummaries =
                    resourcesForSubId.getStringArray(
                            R.array.wifi_calling_mode_summaries_without_wifi_only);
        }
        WifiCallingQueryImsState queryImsState = queryImsState(this.mSubId);
        boolean z4 = queryImsState.isEnabledByUser() && queryImsState.isAllowUserControl();
        this.mSwitchBar.setChecked(z4);
        int i2 = -1;
        try {
            i = this.mImsMmTelManager.getVoWiFiModeSetting();
            try {
                i2 = this.mImsMmTelManager.getVoWiFiRoamingModeSetting();
                z2 = false;
            } catch (IllegalArgumentException e) {
                e = e;
                Log.e("WifiCallingForSub", "getResourceIdForWfcMode: Exception", e);
                z2 = true;
                this.mButtonWfcMode.setValue(Integer.toString(i));
                this.mButtonWfcRoamingMode.setValue(Integer.toString(i2));
                if (z4) {
                    z3 = true;
                }
                updateButtonWfcMode(i, z3);
            }
        } catch (IllegalArgumentException e2) {
            e = e2;
            i = -1;
        }
        this.mButtonWfcMode.setValue(Integer.toString(i));
        this.mButtonWfcRoamingMode.setValue(Integer.toString(i2));
        if (z4 && !z2) {
            z3 = true;
        }
        updateButtonWfcMode(i, z3);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateButtonWfcMode(int r4, boolean r5) {
        /*
            r3 = this;
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r0 = r3.mButtonWfcMode
            java.lang.CharSequence r4 = r3.getWfcModeSummary(r4)
            r0.setSummary(r4)
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r4 = r3.mButtonWfcMode
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L15
            boolean r2 = r3.mEditableWfcMode
            if (r2 == 0) goto L15
            r2 = r0
            goto L16
        L15:
            r2 = r1
        L16:
            r4.setEnabled(r2)
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r4 = r3.mButtonWfcRoamingMode
            if (r5 == 0) goto L3c
            boolean r2 = r3.mEditableWfcRoamingMode
            if (r2 == 0) goto L3c
            int r2 = r3.mSubId
            android.telephony.TelephonyManager r2 = r3.getTelephonyManagerForSub(r2)
            android.telephony.ServiceState r2 = r2.getServiceState()
            if (r2 != 0) goto L2f
        L2d:
            r2 = r1
            goto L38
        L2f:
            boolean r2 = r2.isUsingNonTerrestrialNetwork()
            if (r2 != 0) goto L36
            goto L2d
        L36:
            boolean r2 = r3.mOverrideWfcRoamingModeWhileUsingNtn
        L38:
            if (r2 != 0) goto L3c
            r2 = r0
            goto L3d
        L3c:
            r2 = r1
        L3d:
            r4.setEnabled(r2)
            r3.getPreferenceScreen()
            android.content.Intent r4 = r3.getCarrierActivityIntent()
            if (r4 == 0) goto L4b
            r4 = r0
            goto L4c
        L4b:
            r4 = r1
        L4c:
            if (r5 == 0) goto L6a
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r5 = r3.mButtonWfcMode
            boolean r2 = r3.mEditableWfcMode
            r5.setVisible(r2)
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r5 = r3.mButtonWfcRoamingMode
            boolean r2 = r3.mEditableWfcRoamingMode
            if (r2 == 0) goto L60
            boolean r2 = r3.mUseWfcHomeModeForRoaming
            if (r2 != 0) goto L60
            goto L61
        L60:
            r0 = r1
        L61:
            r5.setVisible(r0)
            androidx.preference.Preference r5 = r3.mUpdateAddress
            r5.setVisible(r4)
            goto L79
        L6a:
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r4 = r3.mButtonWfcMode
            r4.setVisible(r1)
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r4 = r3.mButtonWfcRoamingMode
            r4.setVisible(r1)
            androidx.preference.Preference r4 = r3.mUpdateAddress
            r4.setVisible(r1)
        L79:
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r4 = r3.mButtonWfcMode
            com.android.settings.wifi.calling.ListWithEntrySummaryPreference r5 = r3.mButtonWfcRoamingMode
            androidx.preference.Preference r0 = r3.mUpdateAddress
            java.util.List r4 = java.util.List.of(r4, r5, r0)
            r3.updateDescriptionForOptions(r4)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.calling.WifiCallingSettingsForSub.updateButtonWfcMode(int,"
                    + " boolean):void");
    }

    public final void updateDescriptionForOptions(List list) {
        LinkifyDescriptionPreference linkifyDescriptionPreference =
                (LinkifyDescriptionPreference) findPreference("no_options_description");
        if (linkifyDescriptionPreference == null) {
            return;
        }
        boolean anyMatch =
                list.stream().anyMatch(new WifiCallingSettingsForSub$$ExternalSyntheticLambda1());
        if (!anyMatch) {
            Resources resourcesForSubId = getResourcesForSubId();
            linkifyDescriptionPreference.setSummary(
                    resourcesForSubId.getString(
                            R.string.wifi_calling_off_explanation,
                            resourcesForSubId.getString(R.string.wifi_calling_off_explanation_2)));
        }
        linkifyDescriptionPreference.setVisible(!anyMatch);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWfcMode(boolean r9) {
        /*
            r8 = this;
            java.lang.String r0 = "updateWfcMode: Exception"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "updateWfcMode("
            r1.<init>(r2)
            r1.append(r9)
            java.lang.String r2 = ")"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            java.lang.String r2 = "WifiCallingForSub"
            android.util.Log.i(r2, r1)
            r1 = 1
            r3 = 0
            android.telephony.ims.ImsMmTelManager r4 = r8.mImsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L23
            r4.setVoWiFiSettingEnabled(r9)     // Catch: java.lang.IllegalArgumentException -> L23
            r4 = r3
            goto L28
        L23:
            r4 = move-exception
            android.util.Log.e(r2, r0, r4)
            r4 = r1
        L28:
            r5 = -1
            if (r4 != 0) goto L40
            android.telephony.ims.ImsMmTelManager r6 = r8.mImsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L39
            int r6 = r6.getVoWiFiModeSetting()     // Catch: java.lang.IllegalArgumentException -> L39
            android.telephony.ims.ImsMmTelManager r7 = r8.mImsMmTelManager     // Catch: java.lang.IllegalArgumentException -> L37
            r7.getVoWiFiRoamingModeSetting()     // Catch: java.lang.IllegalArgumentException -> L37
            goto L41
        L37:
            r4 = move-exception
            goto L3b
        L39:
            r4 = move-exception
            r6 = r5
        L3b:
            android.util.Log.e(r2, r0, r4)
            r4 = r1
            goto L41
        L40:
            r6 = r5
        L41:
            if (r9 == 0) goto L46
            if (r4 != 0) goto L46
            goto L47
        L46:
            r1 = r3
        L47:
            r8.updateButtonWfcMode(r6, r1)
            r0 = 1230(0x4ce, float:1.724E-42)
            if (r9 == 0) goto L58
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r9 = r8.mMetricsFeatureProvider
            androidx.fragment.app.FragmentActivity r8 = r8.getActivity()
            r9.action(r8, r0, r6)
            goto L61
        L58:
            com.android.settingslib.core.instrumentation.MetricsFeatureProvider r9 = r8.mMetricsFeatureProvider
            androidx.fragment.app.FragmentActivity r8 = r8.getActivity()
            r9.action(r8, r0, r5)
        L61:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.wifi.calling.WifiCallingSettingsForSub.updateWfcMode(boolean):void");
    }
}
