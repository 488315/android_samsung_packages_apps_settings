package com.samsung.android.settings.wifi.mobileap.configure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.Utils$$ExternalSyntheticOutline0;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;
import com.sec.ims.settings.ImsProfile;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureWifiSharingController extends TogglePreferenceController
        implements LifecycleEventObserver {
    private static final String BUNDLE_KEY_WIFI_SHARING_SWITCH_VALUE =
            "bundle_key_wifi_sharing_switch_value";
    public static final String KEY_PREFERENCE = "wifi_sharing";
    private static final String PREFERENCENAME = "WIFI_AP_SHARED_PREFS";
    private static final String TAG = "WifiApConfigureWifiSharingController";
    private Context mContext;
    private int mDualBandSelectedPreviouslyFlag;
    private SecSwitchPreference mThisSwitchPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureWifiSharingController(Context context, String str) {
        super(context, str);
        this.mDualBandSelectedPreviouslyFlag = 0;
        this.mContext = context;
    }

    private void checkWifiMobileApStatus() {
        int wifiState =
                ((WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI)).getWifiState();
        if ((wifiState == 2 || wifiState == 3)
                && WifiApFrameworkUtils.isWifiApStateEnablingOrEnabled(this.mContext)) {
            Log.i(TAG, "Wifi Sharing disabled, so Disable Wifi when both were ON");
            ((WifiManager) this.mContext.getSystemService(ImsProfile.PDN_WIFI))
                    .setWifiEnabled(false);
            Settings.Secure.putInt(this.mContext.getContentResolver(), "wifi_saved_state", 1);
        }
    }

    private int getIntFromSharedPreference(Context context, String str) {
        String str2 = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "getIntFromSharedPreference() - strKey: ", str, str2);
        int i = context.getSharedPreferences(PREFERENCENAME, 0).getInt(str, 0);
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "getIntFromSharedPreference - ", str, " : ");
        m.append(String.valueOf(i));
        Log.i(str2, m.toString());
        return i;
    }

    private void insertMHSWifiSharingLogging(boolean z) {
        if (z) {
            WifiApFrameworkUtils.getSemWifiManager(this.mContext).reportMHSBigData("MHWS", "ON");
        } else {
            WifiApFrameworkUtils.getSemWifiManager(this.mContext).reportMHSBigData("MHWS", "OFF");
            checkWifiMobileApStatus();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showWifiLiteWarningDialogBox$0(
            DialogInterface dialogInterface, int i) {
        this.mThisSwitchPreference.setChecked(false);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showWifiLiteWarningDialogBox$1(
            DialogInterface dialogInterface, int i) {
        this.mThisSwitchPreference.setChecked(true);
        dialogInterface.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showWifiLiteWarningDialogBox$2(
            DialogInterface dialogInterface) {
        this.mThisSwitchPreference.setChecked(true);
    }

    private void putIntSharedPreference(Context context, String str, int i) {
        String str2 = TAG;
        Log.i(str2, "putIntSharedPreference() - strKey: " + str + ", bValue: " + i);
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCENAME, 0).edit();
        edit.putInt(str, i);
        edit.commit();
        StringBuilder m =
                ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                        "putIntSharedPreference - ", str, " : ");
        m.append(String.valueOf(i));
        Log.i(str2, m.toString());
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference");
        SecSwitchPreference secSwitchPreference =
                (SecSwitchPreference) preferenceScreen.findPreference(KEY_PREFERENCE);
        this.mThisSwitchPreference = secSwitchPreference;
        secSwitchPreference.setTitle(R.string.wifi_ap__wifi_sharing);
        this.mThisSwitchPreference.setSummary(
                WifiApUtils.getString(this.mContext, R.string.wifi_ap_wifi_sharing_auto_hotspot));
        this.mThisSwitchPreference.setChecked(
                WifiApSoftApUtils.isWifiSharingEnabled(this.mContext));
        updateState(
                WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisDualBand2GhzAnd5Ghz());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (WifiApFrameworkUtils.getSemWifiManager(this.mContext).isWifiSharingSupported()
                        || WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                                .isWifiSharingLiteSupported())
                ? 0
                : 3;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        Log.i(TAG, "isChecked");
        return this.mThisSwitchPreference.isChecked();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    public boolean isWifiSharingSwitchStateModified() {
        boolean z;
        if (isAvailable()) {
            boolean isWifiSharingEnabled = WifiApSoftApUtils.isWifiSharingEnabled(this.mContext);
            boolean threadEnabled = getThreadEnabled();
            Log.i(
                    TAG,
                    "Wifi Sharing States Old: " + isWifiSharingEnabled + ", new: " + threadEnabled);
            if (isWifiSharingEnabled != threadEnabled) {
                z = true;
                AbsAdapter$$ExternalSyntheticOutline0.m("Is Wifi Sharing State modified: ", TAG, z);
                return z;
            }
        }
        z = false;
        AbsAdapter$$ExternalSyntheticOutline0.m("Is Wifi Sharing State modified: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_WIFI_SHARING_SWITCH_VALUE)) {
            boolean z = bundle.getBoolean(BUNDLE_KEY_WIFI_SHARING_SWITCH_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", TAG, z);
            this.mThisSwitchPreference.setChecked(z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putBoolean(
                    BUNDLE_KEY_WIFI_SHARING_SWITCH_VALUE, this.mThisSwitchPreference.isChecked());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void saveWifiSharingSwitchState() {
        if (isWifiSharingSwitchStateModified()) {
            Log.i(TAG, "Updating new Wifi Sharing state: " + getThreadEnabled());
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "wifi_ap_wifi_sharing",
                    getThreadEnabled() ? 1 : 0);
            insertMHSWifiSharingLogging(getThreadEnabled());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        AbsAdapter$$ExternalSyntheticOutline0.m("setChecked: ", TAG, z);
        SALogging.insertSALog(z ? 1L : 0L, "TETH_012", "8025", (String) null);
        if (z
                && WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                        .isWifiSharingLiteSupported()) {
            showWifiLiteWarningDialogBox();
            return false;
        }
        WifiApEditSettings wifiApEditSettings = this.mWifiApConfigureSettings;
        wifiApEditSettings.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings, 3), 10L);
        return true;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApConfigureSettings = wifiApEditSettings;
    }

    public void showWifiLiteWarningDialogBox() {
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        final int i = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        final int i2 = 1;
        builder.setTitle(R.string.wifi_ap_wifi_sharing_title)
                .setMessage(
                        this.mContext.getResources().getString(R.string.wifi_ap_wifi_sharing_text)
                                + "\n\n"
                                + this.mContext
                                        .getResources()
                                        .getString(
                                                R.string.wifi_ap_wifi_sharinglite_wifiwarning_text))
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureWifiSharingController$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiApConfigureWifiSharingController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                int i4 = i;
                                WifiApConfigureWifiSharingController
                                        wifiApConfigureWifiSharingController = this.f$0;
                                switch (i4) {
                                    case 0:
                                        wifiApConfigureWifiSharingController
                                                .lambda$showWifiLiteWarningDialogBox$0(
                                                        dialogInterface, i3);
                                        break;
                                    default:
                                        wifiApConfigureWifiSharingController
                                                .lambda$showWifiLiteWarningDialogBox$1(
                                                        dialogInterface, i3);
                                        break;
                                }
                            }
                        })
                .setPositiveButton(
                        R.string.dlg_ok,
                        new DialogInterface.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureWifiSharingController$$ExternalSyntheticLambda0
                            public final /* synthetic */ WifiApConfigureWifiSharingController f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i3) {
                                int i4 = i2;
                                WifiApConfigureWifiSharingController
                                        wifiApConfigureWifiSharingController = this.f$0;
                                switch (i4) {
                                    case 0:
                                        wifiApConfigureWifiSharingController
                                                .lambda$showWifiLiteWarningDialogBox$0(
                                                        dialogInterface, i3);
                                        break;
                                    default:
                                        wifiApConfigureWifiSharingController
                                                .lambda$showWifiLiteWarningDialogBox$1(
                                                        dialogInterface, i3);
                                        break;
                                }
                            }
                        })
                .setOnCancelListener(
                        new DialogInterface
                                .OnCancelListener() { // from class:
                                                      // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureWifiSharingController$$ExternalSyntheticLambda2
                            @Override // android.content.DialogInterface.OnCancelListener
                            public final void onCancel(DialogInterface dialogInterface) {
                                WifiApConfigureWifiSharingController.this
                                        .lambda$showWifiLiteWarningDialogBox$2(dialogInterface);
                            }
                        })
                .create();
        builder.show();
    }

    public void updateState() {
        if (isAvailable()) {
            updateState(
                    this.mWifiApConfigureSettings
                            .getBandPreferenceConfig()
                            .isThisDualBand2GhzAnd5Ghz());
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    private void updateState(boolean z) {
        boolean isWifiSharingLiteSupported =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).isWifiSharingLiteSupported();
        boolean isWifiSharingSupported =
                WifiApFrameworkUtils.getSemWifiManager(this.mContext).isWifiSharingSupported();
        boolean isWifiSharingEnabled = WifiApSoftApUtils.isWifiSharingEnabled(this.mContext);
        if (Utils.supportBridgedApStaConcurrency()) {
            z = false;
        }
        String str = TAG;
        Log.i(
                str,
                "Updating state: wifiApBandConfig(isDualBand): "
                        + z
                        + " wifiSharing State :"
                        + isWifiSharingEnabled);
        if (z) {
            this.mThisSwitchPreference.setChecked(false);
            this.mThisSwitchPreference.setEnabled(false);
            if (isWifiSharingEnabled) {
                putIntSharedPreference(this.mContext, "DUAL_BAND_PREV_WIFI_SHARING_STATE", 1);
                return;
            }
            return;
        }
        boolean isAutoHotspotSetOn = WifiApFrameworkUtils.isAutoHotspotSetOn(this.mContext);
        this.mDualBandSelectedPreviouslyFlag =
                getIntFromSharedPreference(this.mContext, "DUAL_BAND_PREV_WIFI_SHARING_STATE");
        StringBuilder m =
                Utils$$ExternalSyntheticOutline0.m(
                        "Updating state: isAutoHotspotEnabled: ",
                        isAutoHotspotSetOn,
                        ",wifiSharinglite:",
                        isWifiSharingLiteSupported,
                        ",wifisharing:");
        m.append(isWifiSharingSupported);
        m.append(" ,mDualBandPrevFlag ");
        TooltipPopup$$ExternalSyntheticOutline0.m(m, this.mDualBandSelectedPreviouslyFlag, str);
        if (!isAutoHotspotSetOn || isWifiSharingLiteSupported) {
            this.mThisSwitchPreference.setChecked(isWifiSharingEnabled);
        } else if (isWifiSharingSupported && isAutoHotspotSetOn) {
            this.mThisSwitchPreference.setChecked(true);
        }
        if (isAutoHotspotSetOn && !isWifiSharingLiteSupported) {
            this.mThisSwitchPreference.setEnabled(!isAutoHotspotSetOn);
        } else {
            this.mThisSwitchPreference.setEnabled(true);
        }
        if (this.mDualBandSelectedPreviouslyFlag == 1) {
            this.mThisSwitchPreference.setChecked(true);
            this.mThisSwitchPreference.setEnabled(true);
            saveWifiSharingSwitchState();
            putIntSharedPreference(this.mContext, "DUAL_BAND_PREV_WIFI_SHARING_STATE", 0);
        }
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
