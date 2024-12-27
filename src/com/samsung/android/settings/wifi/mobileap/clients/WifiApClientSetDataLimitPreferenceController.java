package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetDataLimitDialog.AnonymousClass1;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApDataUsageConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientSetDataLimitPreferenceController extends BasePreferenceController {
    private static final String TAG = "WifiApClientSetDataLimitPreferenceController";
    private DialogInterface.OnDismissListener dismissListener;
    private WifiApClientSetDataLimitDialog mClientSetDataLimitDialog;
    private String mMacAddress;
    private WifiApPreference mThisPreference;
    private WifiApClientSettings mWifiApClientSettings;

    public WifiApClientSetDataLimitPreferenceController(Context context, String str) {
        super(context, str);
        this.dismissListener =
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetDataLimitPreferenceController.1
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        WifiApClientSetDataLimitPreferenceController.this.mWifiApClientSettings
                                .refreshConnectedPreferences();
                        WifiApClientSetDataLimitPreferenceController.this
                                        .mClientSetDataLimitDialog =
                                null;
                    }
                };
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mThisPreference =
                (WifiApPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return WifiApFeatureUtils.isMobileDataUsageSupported(this.mContext) ? 0 : 3;
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
        String str = TAG;
        Log.i(str, "handlePreferenceTreeClick - Triggered");
        SALogging.insertSALog("TETH_014", "8083");
        WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog =
                this.mClientSetDataLimitDialog;
        if (wifiApClientSetDataLimitDialog != null && wifiApClientSetDataLimitDialog.isShowing()) {
            Log.d(str, "Dialog is showing, ignore");
            return true;
        }
        String string = this.mContext.getString(R.string.wifi_ap_data_limit_client);
        String string2 = this.mContext.getString(R.string.wifi_ap_client_data_limit_dialog_text);
        Context context = this.mContext;
        WifiApDataUsageConfig wifiApClientDataLimitDetail =
                WifiApConnectedDeviceUtils.getWifiApClientDataLimitDetail(
                        context, this.mMacAddress);
        String str2 = this.mMacAddress;
        DialogInterface.OnClickListener onClickListener =
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetDataLimitPreferenceController.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog2 =
                                (WifiApClientSetDataLimitDialog) dialogInterface;
                        if (i == -1) {
                            SALogging.insertSALog(1L, "TETH_014", "8086", (String) null);
                            WifiApDataUsageConfig inputDataInDataUsageConfig =
                                    wifiApClientSetDataLimitDialog2.getInputDataInDataUsageConfig();
                            if (inputDataInDataUsageConfig.getUsageValueInMB()
                                            <= WifiApConnectedDeviceUtils
                                                    .getWifiApClientMobileDataConsumedDetail(
                                                            ((AbstractPreferenceController)
                                                                            WifiApClientSetDataLimitPreferenceController
                                                                                    .this)
                                                                    .mContext,
                                                            WifiApClientSetDataLimitPreferenceController
                                                                    .this
                                                                    .mMacAddress)
                                                    .getUsageValueInMB()
                                    || inputDataInDataUsageConfig.mUsageValueInBytes
                                            <= WifiApConnectedDeviceUtils
                                                    .getWifiApClientMobileDataConsumedDetail(
                                                            ((AbstractPreferenceController)
                                                                            WifiApClientSetDataLimitPreferenceController
                                                                                    .this)
                                                                    .mContext,
                                                            WifiApClientSetDataLimitPreferenceController
                                                                    .this
                                                                    .mMacAddress)
                                                    .mUsageValueInBytes) {
                                Log.i(
                                        WifiApClientSetDataLimitPreferenceController.TAG,
                                        "Error Settings client data limit : "
                                                + inputDataInDataUsageConfig.getUsageValueInMB()
                                                + ", mac: "
                                                + WifiApSettingsUtils.hideSecondHalfOfString(
                                                        WifiApClientSetDataLimitPreferenceController
                                                                .this
                                                                .mMacAddress));
                                Toast.makeText(
                                                ((AbstractPreferenceController)
                                                                WifiApClientSetDataLimitPreferenceController
                                                                        .this)
                                                        .mContext,
                                                R.string.wifi_ap_data_limit_less_than_amount_used,
                                                0)
                                        .show();
                            } else {
                                String str3 = WifiApClientSetDataLimitPreferenceController.TAG;
                                StringBuilder sb = new StringBuilder("Settings data limit : ");
                                sb.append(inputDataInDataUsageConfig.mUsageValueInBytes);
                                sb.append(" for mac: ");
                                sb.append(
                                        WifiApSettingsUtils.hideSecondHalfOfString(
                                                WifiApClientSetDataLimitPreferenceController.this
                                                        .mMacAddress));
                                Log.i(str3, sb.toString());
                                WifiApFrameworkUtils.getSemWifiManager(
                                                ((AbstractPreferenceController)
                                                                WifiApClientSetDataLimitPreferenceController
                                                                        .this)
                                                        .mContext)
                                        .setWifiApClientMobileDataLimit(
                                                WifiApClientSetDataLimitPreferenceController.this
                                                        .mMacAddress,
                                                inputDataInDataUsageConfig.mUsageValueInBytes);
                            }
                        } else if (i == -2) {
                            SALogging.insertSALog(2L, "TETH_014", "8086", (String) null);
                            Log.i(
                                    WifiApClientSetDataLimitPreferenceController.TAG,
                                    "Settings NO data limit : for mac: "
                                            + WifiApSettingsUtils.hideSecondHalfOfString(
                                                    WifiApClientSetDataLimitPreferenceController
                                                            .this
                                                            .mMacAddress));
                            WifiApFrameworkUtils.getSemWifiManager(
                                            ((AbstractPreferenceController)
                                                            WifiApClientSetDataLimitPreferenceController
                                                                    .this)
                                                    .mContext)
                                    .setWifiApClientMobileDataLimit(
                                            WifiApClientSetDataLimitPreferenceController.this
                                                    .mMacAddress,
                                            0L);
                        } else if (i == -3) {
                            SALogging.insertSALog(0L, "TETH_014", "8086", (String) null);
                        }
                        WifiApClientSetDataLimitPreferenceController
                                wifiApClientSetDataLimitPreferenceController =
                                        WifiApClientSetDataLimitPreferenceController.this;
                        wifiApClientSetDataLimitPreferenceController.updateState(
                                wifiApClientSetDataLimitPreferenceController.mThisPreference);
                    }
                };
        WifiApClientSetDataLimitDialog wifiApClientSetDataLimitDialog2 =
                new WifiApClientSetDataLimitDialog(context, 0);
        wifiApClientSetDataLimitDialog2.mDataLimitEditTextWatcher =
                wifiApClientSetDataLimitDialog2.new AnonymousClass1();
        wifiApClientSetDataLimitDialog2.mContext = context;
        wifiApClientSetDataLimitDialog2.mListener = onClickListener;
        wifiApClientSetDataLimitDialog2.mWifiApDataUsageConfig = wifiApClientDataLimitDetail;
        wifiApClientSetDataLimitDialog2.mMacAddress = str2;
        wifiApClientSetDataLimitDialog2.setTitle(string);
        wifiApClientSetDataLimitDialog2.mDialogMessage = string2;
        this.mClientSetDataLimitDialog = wifiApClientSetDataLimitDialog2;
        wifiApClientSetDataLimitDialog2.setOnDismissListener(this.dismissListener);
        this.mClientSetDataLimitDialog.show();
        return true;
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

    public void setHost(WifiApClientSettings wifiApClientSettings) {
        this.mWifiApClientSettings = wifiApClientSettings;
    }

    public void setMacDetail(String str) {
        this.mMacAddress = str;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        WifiApDataUsageConfig wifiApClientDataLimitDetail =
                WifiApConnectedDeviceUtils.getWifiApClientDataLimitDetail(
                        this.mContext, this.mMacAddress);
        if (wifiApClientDataLimitDetail.mUsageValueInBytes > 0.0d) {
            this.mThisPreference.setSummary(
                    wifiApClientDataLimitDetail.getUsageValueInLocaleString(this.mContext));
        } else {
            this.mThisPreference.setSummary(this.mContext.getString(R.string.none));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
