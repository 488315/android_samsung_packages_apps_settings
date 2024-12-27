package com.samsung.android.settings.wifi.mobileap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApConfiguration;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import com.samsung.android.wifi.SemWifiApRestoreHelper;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApDashboardNetworkNameController extends BasePreferenceController {
    private static final int DIALOG_RESTORE_SSID = 4;
    private static final String TAG = "WifiApDashboardNetworkNameController";
    private AlertDialog mConfigDialogCreate;
    private Context mContext;
    private AlertDialog mRestoreDialog;
    private WifiApPreference mThisPreference;
    private WifiApSettings mWifiApSettings;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController$6, reason: invalid class name */
    public final class AnonymousClass6 implements DialogInterface.OnCancelListener {
        @Override // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            dialogInterface.dismiss();
        }
    }

    public WifiApDashboardNetworkNameController(Context context, String str) {
        super(context, str);
        this.mConfigDialogCreate = null;
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendConfigChangedBroadcastToSmartTethering() {
        Log.d(TAG, "sendConfigChangedBroadcastToSmartTethering() - Sending Broadcast");
        if (Utils.SPF_SupportMobileApEnhanced) {
            Intent intent = new Intent();
            intent.setAction("com.samsung.android.server.wifi.softap.smarttethering.ssid_changed");
            this.mContext.sendBroadcast(intent, "android.permission.OVERRIDE_WIFI_CONFIG");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRestoreDialog(int i) {
        String str = TAG;
        Log.d(str, "showDialog() - dialogId : " + i);
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        final int i2 = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        if (i != 4) {
            return;
        }
        Log.i(str, "showDialog() - DIALOG_RESTORE_SSID is being shown");
        String ssid = SemWifiApRestoreHelper.getSSID(this.mContext);
        String password = SemWifiApRestoreHelper.getPassword(this.mContext);
        StringBuilder sb = new StringBuilder();
        sb.append(
                this.mContext
                        .getResources()
                        .getString(R.string.wifi_ap_network_name_to_be_restored));
        sb.append(" ");
        sb.append(ssid);
        sb.append("\n");
        sb.append(this.mContext.getResources().getString(R.string.wifi_ap_password_to_be_restored));
        sb.append(" ");
        if (SemWifiApRestoreHelper.getSecurityType(this.mContext) == 0) {
            sb.append(this.mContext.getResources().getString(R.string.wifi_security_none));
        } else {
            sb.append(password);
        }
        AlertDialog.Builder message =
                builder.setTitle(R.string.wifi_ap_restore_network_name).setMessage(sb);
        final int i3 = 1;
        message.setPositiveButton(
                        R.string.wifi_ap_restore_button,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController.2
                            public final /* synthetic */ WifiApDashboardNetworkNameController
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i3) {
                                    case 0:
                                        Log.i(
                                                WifiApDashboardNetworkNameController.TAG,
                                                "showDialog() - DIALOG_RESTORE_SSID's"
                                                    + " NegativeButton CANCEL clicked");
                                        SALogging.insertSALog("TETH_010", "8036");
                                        this.this$0.setCurrentConfigToContentProvider();
                                        this.this$0.mWifiApSettings.refreshPreferenceStates$1();
                                        break;
                                    default:
                                        Log.i(
                                                WifiApDashboardNetworkNameController.TAG,
                                                "showDialog() - DIALOG_RESTORE_SSID's"
                                                    + " PositiveButton RESTORE clicked");
                                        SALogging.insertSALog("TETH_010", "8037");
                                        this.this$0.setContentProviderConfigToFramework();
                                        this.this$0.mWifiApSettings.refreshPreferenceStates$1();
                                        break;
                                }
                            }
                        })
                .setNegativeButton(
                        R.string.wifi_cancel,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController.2
                            public final /* synthetic */ WifiApDashboardNetworkNameController
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i2) {
                                    case 0:
                                        Log.i(
                                                WifiApDashboardNetworkNameController.TAG,
                                                "showDialog() - DIALOG_RESTORE_SSID's"
                                                    + " NegativeButton CANCEL clicked");
                                        SALogging.insertSALog("TETH_010", "8036");
                                        this.this$0.setCurrentConfigToContentProvider();
                                        this.this$0.mWifiApSettings.refreshPreferenceStates$1();
                                        break;
                                    default:
                                        Log.i(
                                                WifiApDashboardNetworkNameController.TAG,
                                                "showDialog() - DIALOG_RESTORE_SSID's"
                                                    + " PositiveButton RESTORE clicked");
                                        SALogging.insertSALog("TETH_010", "8037");
                                        this.this$0.setContentProviderConfigToFramework();
                                        this.this$0.mWifiApSettings.refreshPreferenceStates$1();
                                        break;
                                }
                            }
                        });
        AlertDialog create = builder.create();
        this.mRestoreDialog = create;
        create.show();
    }

    public boolean checkIfHelpIconForRestoringConfigNeeded() {
        Context context = this.mContext;
        EnterpriseDeviceManager enterpriseDeviceManager =
                context != null ? EnterpriseDeviceManager.getInstance(context) : null;
        boolean isWifiApSettingUserModificationAllowed =
                (enterpriseDeviceManager == null || enterpriseDeviceManager.getWifiPolicy() == null)
                        ? true
                        : enterpriseDeviceManager
                                .getWifiPolicy()
                                .isWifiApSettingUserModificationAllowed();
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "WifiApSettingsAllowedToChange:",
                "WifiApFeatureUtils",
                isWifiApSettingUserModificationAllowed);
        String str = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "checkIfHelpIconForRestoringConfigNeeded isWifiApSettingUserModificationAllowed:",
                str,
                isWifiApSettingUserModificationAllowed);
        if (!isWifiApSettingUserModificationAllowed) {
            return false;
        }
        String ssid = SemWifiApRestoreHelper.getSSID(this.mContext);
        WifiApConfiguration wifiApConfiguration = this.mWifiApSettings.getWifiApConfiguration();
        if (ssid.isEmpty() || ssid.equals(wifiApConfiguration.mSsid)) {
            Log.i(str, "HelpIconForRestoring not needed()");
            return false;
        }
        Utils$$ExternalSyntheticOutline0.m(
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "HelpIconForRestoring needed() - mContentProviderSSID: ", ssid, ", SSID: "),
                wifiApConfiguration.mSsid,
                str);
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        WifiApPreference wifiApPreference =
                (WifiApPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisPreference = wifiApPreference;
        wifiApPreference.mPreferenceType = 1;
        wifiApPreference.notifyChanged();
        WifiApPreference wifiApPreference2 = this.mThisPreference;
        wifiApPreference2.mSecondaryIconClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        WifiApDashboardNetworkNameController.this.showRestoreDialog(4);
                    }
                };
        wifiApPreference2.notifyChanged();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SALogging.insertSALog("TETH_010", "8008");
        if ((WifiApSettingsUtils.isCarrierTMO() || WifiApSettingsUtils.isCarrierNEWCO())
                && this.mWifiApSettings.getWifiApConfiguration().isDefaultPassphraseSet()) {
            this.mWifiApSettings.launchWifiApEditSettingsActivity(
                    R.string.wifi_ap_first_time_configuration, "intent_value_focus_network_name");
        } else {
            this.mWifiApSettings.launchWifiApEditSettingsActivity(
                    R.string.wifi_ap_menu_configure_hotspot, "intent_value_focus_network_name");
        }
        return super.handlePreferenceTreeClick(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setContentProviderConfigToFramework() {
        if (this.mContext == null) {
            Log.e(TAG, "Context is null");
            return;
        }
        Log.i(TAG, "updatePreviousConfigurationFromContentProvider()");
        String ssid = SemWifiApRestoreHelper.getSSID(this.mContext);
        String password = SemWifiApRestoreHelper.getPassword(this.mContext);
        int securityType = SemWifiApRestoreHelper.getSecurityType(this.mContext);
        WifiApConfiguration wifiApConfiguration = this.mWifiApSettings.getWifiApConfiguration();
        String str = wifiApConfiguration.mSsid;
        int[] iArr = wifiApConfiguration.mSoftApBandArray;
        int i = wifiApConfiguration.mBroadcastChannel;
        int i2 = wifiApConfiguration.mMacRandomizationSettingsType;
        boolean z = wifiApConfiguration.mIsHiddenNetworkEnabled;
        if (TextUtils.isEmpty(ssid)) {
            throw new IllegalStateException("Ssid cannot be empty");
        }
        WifiApConfiguration wifiApConfiguration2 = new WifiApConfiguration();
        wifiApConfiguration2.mSsid = ssid;
        wifiApConfiguration2.mPassphrase = password;
        wifiApConfiguration2.mSecurityType = securityType;
        wifiApConfiguration2.mSoftApBandArray = iArr;
        wifiApConfiguration2.mBroadcastChannel = i;
        wifiApConfiguration2.mMacRandomizationSettingsType = i2;
        wifiApConfiguration2.mIsHiddenNetworkEnabled = z;
        updateConfigurationToFramework(wifiApConfiguration2);
    }

    public void setCurrentConfigToContentProvider() {
        String str = TAG;
        Log.i(str, "updateCurrentConfigToContentProvider() - Start");
        Context context = this.mContext;
        if (context == null) {
            Log.e(str, "Context is null");
        } else {
            SemWifiApRestoreHelper.setCurrentApConfiguration(context);
        }
    }

    public void setHost(WifiApSettings wifiApSettings) {
        this.mWifiApSettings = wifiApSettings;
        if (this.mContext == null) {
            Log.i(TAG, "Setting context from wifiApSettings");
            this.mContext = wifiApSettings.getContext();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void updateConfigurationToFramework(final WifiApConfiguration wifiApConfiguration) {
        final boolean z;
        String str;
        Log.i(TAG, "updateConfigurationToFramework() - Start");
        WifiApConfiguration wifiApConfiguration2 = this.mWifiApSettings.getWifiApConfiguration();
        wifiApConfiguration2.getClass();
        if (wifiApConfiguration == null) {
            Log.d(
                    "WifiApConfiguration",
                    "isSoftApConfigurationChanged() - WifiConfiguration to be compared is NULL.");
            z = false;
        } else {
            String str2 = wifiApConfiguration.mSsid;
            String str3 = wifiApConfiguration2.mSsid;
            if (str3 == null || !str3.equals(str2)) {
                Log.d("WifiApConfiguration", "isSsidChanged() - SSID changed.");
            } else {
                Log.i("WifiApConfiguration", "isSsidChanged() - SSID not changed.");
                String str4 = wifiApConfiguration.mPassphrase;
                String str5 = wifiApConfiguration2.mPassphrase;
                if (str5 != null && str5.length() != 0 && (str4 == null || str4.length() == 0)) {
                    Log.d(
                            "WifiApConfiguration",
                            "isPassphraseChanged() - Password changed to OPEN.");
                }
                if (str4 == null
                        || str4.length() == 0
                        || !((str = wifiApConfiguration2.mPassphrase) == null
                                || str.length() == 0)) {
                    String str6 = wifiApConfiguration2.mPassphrase;
                    if (str6 == null || str6.equals(str4)) {
                        Log.i(
                                "WifiApConfiguration",
                                "isPassphraseChanged() - Passphrase not changed.");
                    } else {
                        Log.d("WifiApConfiguration", "isPassphraseChanged() - Passphrase changed.");
                    }
                } else {
                    Log.d(
                            "WifiApConfiguration",
                            "isPassphraseChanged() - Password changed from OPEN.");
                }
            }
            z = true;
        }
        if ("VZW".equals(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                && WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            Context context = this.mContext;
            boolean z2 = WifiApSettingsUtils.DBG;
            AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
            builder.setTitle(R.string.wifi_ap_wifi_save);
            builder.setMessage(R.string.wifi_ap_wifi_config_summary);
            builder.setCancelable(true);
            builder.setPositiveButton(
                    R.string.common_continue,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.mobileap.WifiApDashboardNetworkNameController.4
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            WifiApSoftApUtils.setSoftApConfiguration(
                                    WifiApDashboardNetworkNameController.this.mContext,
                                    wifiApConfiguration.getSoftApConfiguration(
                                            WifiApDashboardNetworkNameController.this.mContext));
                            if (z) {
                                WifiApDashboardNetworkNameController.this
                                        .sendConfigChangedBroadcastToSmartTethering();
                            }
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException e) {
                                Log.i(
                                        WifiApDashboardNetworkNameController.TAG,
                                        "Error InterruptedException " + e);
                                Thread.currentThread().interrupt();
                            }
                            WifiApDashboardNetworkNameController.this.mWifiApSettings
                                    .updateWifiApConfiguration();
                            dialogInterface.dismiss();
                            WifiApFrameworkUtils.resetSoftAp(
                                    WifiApDashboardNetworkNameController.this.mContext);
                            WifiApDashboardNetworkNameController.this.mWifiApSettings
                                    .refreshPreferenceStates$1();
                        }
                    });
            builder.setNegativeButton(R.string.cancel, new AnonymousClass5());
            builder.setOnCancelListener(new AnonymousClass6());
            AlertDialog create = builder.create();
            this.mConfigDialogCreate = create;
            create.show();
            return;
        }
        Context context2 = this.mContext;
        WifiApSoftApUtils.setSoftApConfiguration(
                context2, wifiApConfiguration.getSoftApConfiguration(context2));
        if (z) {
            sendConfigChangedBroadcastToSmartTethering();
        }
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            Log.i(TAG, "Error InterruptedException " + e);
            Thread.currentThread().interrupt();
        }
        this.mWifiApSettings.updateWifiApConfiguration();
        this.mWifiApSettings.refreshPreferenceStates$1();
        if (WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            WifiApFrameworkUtils.resetSoftAp(this.mContext);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        this.mThisPreference.setSummary(this.mWifiApSettings.getWifiApConfiguration().mSsid);
        if (!checkIfHelpIconForRestoringConfigNeeded()) {
            this.mThisPreference.setSecondaryIcon(null);
        } else {
            WifiApPreference wifiApPreference = this.mThisPreference;
            wifiApPreference.setSecondaryIcon(
                    wifiApPreference.mContext.getDrawable(R.drawable.ic_wifi_ap_tips));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
