package com.samsung.android.settings.wifi.mobileap.configure;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.network.apn.ApnPreference$$ExternalSyntheticOutline0;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings$$ExternalSyntheticLambda0;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApBandConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureSecurityDropDownController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String BUNDLE_KEY_SECURITY_DROPDOWN_VALUE =
            "bundle_key_security_dropdown_value";
    private static final int DIALOG_OPEN_SECURITY_WARNING = 1;
    public static final String KEY_PREFERENCE = "security_dropdown_preference";
    private static final String TAG = "WifiApConfigureSecurityDropDownController";
    private Context mContext;
    private AlertDialog mDialog;
    private boolean mIs6GhzBandSelectedPreviouslyFlag;
    private int mNewChangedSecurityTypeByTouch;
    private boolean mOpenSecurityWarningDialogIsRequired;
    private int mPreviousSecurityType;
    private String[] mSecurityEntries;
    private SecDropDownPreference mThisDropDownPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureSecurityDropDownController(Context context, String str) {
        super(context, str);
        this.mPreviousSecurityType = 0;
        this.mNewChangedSecurityTypeByTouch = -1;
        this.mSecurityEntries = null;
        this.mOpenSecurityWarningDialogIsRequired = false;
        this.mIs6GhzBandSelectedPreviouslyFlag = false;
        this.mContext = context;
        Log.i(TAG, "WifiApConfigureSecurityDropdownController");
        this.mIs6GhzBandSelectedPreviouslyFlag =
                WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisBand6Ghz();
    }

    private void setSecurityTypeEntryArrays(boolean z) {
        String str = TAG;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "setSecurityEntryArray() - is6GhzBandCurrentlySelected : ", str, z);
        if (!WifiApFrameworkUtils.getSemWifiManager(this.mContext).isWifiApWpa3Supported()) {
            Log.i(str, "setSecurityEntryArray() : open/EnhancedOpen/wpa2");
            this.mSecurityEntries =
                    this.mContext
                            .getResources()
                            .getStringArray(R.array.wifi_ap_owe_security_type_entries);
        } else if (WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                        .supportWifiAp6GBasedOnCountry()
                && z) {
            Log.i(str, "setSecurityEntryArray() : EnhancedOpen/wpa3");
            this.mSecurityEntries =
                    this.mContext
                            .getResources()
                            .getStringArray(R.array.wifi_ap_6ghz_owe_wpa3_security_type_entries);
        } else {
            Log.i(str, "setSecurityEntryArray() : open/EnhancedOpen/wpa2/wpa2_wpa3/wpa3");
            this.mSecurityEntries =
                    this.mContext
                            .getResources()
                            .getStringArray(R.array.wifi_ap_owe_wpa3_security_type_entries);
        }
        this.mThisDropDownPreference.setEntries(this.mSecurityEntries);
        this.mThisDropDownPreference.mEntryValues = this.mSecurityEntries;
    }

    private void setSummary(CharSequence charSequence) {
        this.mThisDropDownPreference.setSummary(charSequence);
    }

    private void showDialog(int i) {
        String str = TAG;
        Log.d(str, "showDialog() - dialogId : " + i);
        Context context = this.mContext;
        boolean z = WifiApSettingsUtils.DBG;
        final int i2 = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(context, 0);
        final int i3 = 1;
        if (i != 1) {
            return;
        }
        Log.i(str, "showDialog() - DIALOG_OPEN_SECURITY_WARNING is being shown");
        builder.setMessage(R.string.wifi_ap_none_security_warning)
                .setPositiveButton(
                        R.string.common_continue,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSecurityDropDownController.1
                            public final /* synthetic */ WifiApConfigureSecurityDropDownController
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i3) {
                                    case 0:
                                        Log.i(
                                                WifiApConfigureSecurityDropDownController.TAG,
                                                "showDialog() - DIALOG_OPEN_SECURITY_WARNING's"
                                                    + " PositiveButton clicked");
                                        WifiApConfigureSecurityDropDownController
                                                wifiApConfigureSecurityDropDownController =
                                                        this.this$0;
                                        wifiApConfigureSecurityDropDownController.setValueIndex(
                                                wifiApConfigureSecurityDropDownController
                                                        .mThisDropDownPreference.findIndexOfValue(
                                                        WifiApSoftApUtils.getSecurityTypeString(
                                                                this.this$0.mContext,
                                                                this.this$0
                                                                        .mPreviousSecurityType)));
                                        break;
                                    default:
                                        Log.i(
                                                WifiApConfigureSecurityDropDownController.TAG,
                                                "showDialog() - DIALOG_OPEN_SECURITY_WARNING's"
                                                    + " PositiveButton clicked");
                                        this.this$0.mOpenSecurityWarningDialogIsRequired = false;
                                        break;
                                }
                            }
                        })
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener(this) { // from class:
                            // com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureSecurityDropDownController.1
                            public final /* synthetic */ WifiApConfigureSecurityDropDownController
                                    this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.content.DialogInterface.OnClickListener
                            public final void onClick(DialogInterface dialogInterface, int i4) {
                                switch (i2) {
                                    case 0:
                                        Log.i(
                                                WifiApConfigureSecurityDropDownController.TAG,
                                                "showDialog() - DIALOG_OPEN_SECURITY_WARNING's"
                                                    + " PositiveButton clicked");
                                        WifiApConfigureSecurityDropDownController
                                                wifiApConfigureSecurityDropDownController =
                                                        this.this$0;
                                        wifiApConfigureSecurityDropDownController.setValueIndex(
                                                wifiApConfigureSecurityDropDownController
                                                        .mThisDropDownPreference.findIndexOfValue(
                                                        WifiApSoftApUtils.getSecurityTypeString(
                                                                this.this$0.mContext,
                                                                this.this$0
                                                                        .mPreviousSecurityType)));
                                        break;
                                    default:
                                        Log.i(
                                                WifiApConfigureSecurityDropDownController.TAG,
                                                "showDialog() - DIALOG_OPEN_SECURITY_WARNING's"
                                                    + " PositiveButton clicked");
                                        this.this$0.mOpenSecurityWarningDialogIsRequired = false;
                                        break;
                                }
                            }
                        });
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference");
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisDropDownPreference = secDropDownPreference;
        secDropDownPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.wifi_ap_primary_text_color, this.mContext.getTheme()));
        setSecurityTypeEntryArrays(
                WifiApSoftApUtils.getWifiApBandConfig(this.mContext).isThisBand6Ghz());
        SecDropDownPreference secDropDownPreference2 = this.mThisDropDownPreference;
        Context context = this.mContext;
        setValueIndex(
                secDropDownPreference2.findIndexOfValue(
                        WifiApSoftApUtils.getSecurityTypeString(
                                context,
                                WifiApSoftApUtils.getSoftApConfiguration(context)
                                        .getSecurityType())));
        this.mOpenSecurityWarningDialogIsRequired =
                WifiApSoftApUtils.getSecurityType(this.mContext) != 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Log.i(TAG, "getAvailabilityStatus()");
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int getDropDownValue() {
        return WifiApSoftApUtils.getSecurityType(
                this.mContext, this.mThisDropDownPreference.mValue);
    }

    public int getEntryValuesSize() {
        int i = 0;
        for (CharSequence charSequence : this.mThisDropDownPreference.mEntryValues) {
            i++;
        }
        return i;
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
        Log.i(TAG, "handlePreferenceTreeClick - Triggered");
        WifiApSettingsUtils.hideKeyboard(this.mContext);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isAnyOpenSecurityTypeSelected() {
        return isOpenSecurityTypeSelected() || isOpenEnhancedSecurityTypeSelected();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isOpenEnhancedSecurityTypeSelected() {
        boolean z = getDropDownValue() == 5;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "is currently owe security type selected: ", TAG, z);
        return z;
    }

    public boolean isOpenSecurityTypeSelected() {
        boolean z = getDropDownValue() == 0;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "is currently open security type selected: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    public boolean isWpa3SecurityTypeSelected() {
        boolean z = getDropDownValue() == 3;
        AbsAdapter$$ExternalSyntheticOutline0.m(
                "is currently Wpa3 security type selected: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int securityType = WifiApSoftApUtils.getSecurityType(this.mContext, obj.toString());
        int dropDownValue = getDropDownValue();
        int findIndexOfValue = this.mThisDropDownPreference.findIndexOfValue(obj.toString());
        this.mPreviousSecurityType = dropDownValue;
        this.mNewChangedSecurityTypeByTouch = securityType;
        String str = TAG;
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "OnPreferenceChange - OldValue: ",
                        ", NewValue: ",
                        dropDownValue,
                        securityType,
                        " (Index: ");
        m.append(findIndexOfValue);
        m.append(")");
        Log.i(str, m.toString());
        setSummary(this.mThisDropDownPreference.mEntries[findIndexOfValue]);
        SALogging.insertSALog(securityType, "TETH_011", "8011", (String) null);
        if (("TMO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                        || "NEWCO".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP))
                && securityType == 0) {
            Context context = this.mContext;
            ApnPreference$$ExternalSyntheticOutline0.m(
                    context, R.string.wifi_open_security_warning, context, 0);
        }
        if ("VZW".equalsIgnoreCase(Utils.CONFIGOPBRANDINGFORMOBILEAP)
                && securityType == 0
                && this.mOpenSecurityWarningDialogIsRequired) {
            Log.i(str, "SECURITY_TYPE_OPEN is selected for vzw");
            showDialog(1);
        }
        if (!this.mWifiApConfigureSettings.getBandPreferenceConfig().isThisBand6Ghz()) {
            if (securityType == 3) {
                Context context2 = this.mContext;
                Preference$$ExternalSyntheticOutline0.m(
                        context2,
                        WifiApUtils.getStringID(R.string.wifi_ap_security_warning),
                        new Object[] {"WPA3"},
                        context2,
                        0);
            } else if (securityType == 5) {
                Context context3 = this.mContext;
                Preference$$ExternalSyntheticOutline0.m(
                        context3,
                        WifiApUtils.getStringID(R.string.wifi_ap_security_warning),
                        new Object[] {"Enhanced Open"},
                        context3,
                        0);
            }
        }
        WifiApEditSettings wifiApEditSettings = this.mWifiApConfigureSettings;
        wifiApEditSettings.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings, 2), 10L);
        WifiApEditSettings wifiApEditSettings2 = this.mWifiApConfigureSettings;
        wifiApEditSettings2.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings2, 0), 10L);
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_SECURITY_DROPDOWN_VALUE)) {
            String string = bundle.getString(BUNDLE_KEY_SECURITY_DROPDOWN_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", string, TAG);
            setValueIndex(this.mThisDropDownPreference.findIndexOfValue(string));
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putString(
                    BUNDLE_KEY_SECURITY_DROPDOWN_VALUE, this.mThisDropDownPreference.mValue);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        if (isAvailable()) {
            this.mWifiApConfigureSettings = wifiApEditSettings;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void setValueIndex(int i) {
        setSummary(null);
        if (i < 0 || i >= getEntryValuesSize()) {
            String str = TAG;
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i, "Setting Value Index Error: ", ", getEntryValuesSize:");
            m.append(getEntryValuesSize());
            m.append(", (Resetting index to 0)");
            Log.e(str, m.toString());
            setValueIndex(0);
            return;
        }
        String str2 = TAG;
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Setting Value Index: ", str2);
        setSummary(this.mThisDropDownPreference.mEntries[i]);
        this.mThisDropDownPreference.setValueIndex(i);
        WifiApEditSettings wifiApEditSettings = this.mWifiApConfigureSettings;
        if (wifiApEditSettings == null) {
            Log.d(str2, "mWifiApConfigureSettings is null");
            return;
        }
        wifiApEditSettings.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings, 2), 10L);
        WifiApEditSettings wifiApEditSettings2 = this.mWifiApConfigureSettings;
        wifiApEditSettings2.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings2, 0), 10L);
    }

    public void updateState() {
        if (isAvailable()) {
            WifiApBandConfig bandPreferenceConfig =
                    this.mWifiApConfigureSettings.getBandPreferenceConfig();
            boolean isThisBand6Ghz = bandPreferenceConfig.isThisBand6Ghz();
            String str = TAG;
            Log.i(
                    str,
                    "Updating state - isOpenEnhancedSecurityTypeSupported : true,"
                        + " wifiApBandConfig(is6Ghz) : "
                            + isThisBand6Ghz);
            int dropDownValue = getDropDownValue();
            setSecurityTypeEntryArrays(bandPreferenceConfig.isThisBand6Ghz());
            if (isThisBand6Ghz) {
                this.mIs6GhzBandSelectedPreviouslyFlag = true;
                if (isOpenSecurityTypeSelected() || isOpenEnhancedSecurityTypeSelected()) {
                    setValueIndex(
                            this.mThisDropDownPreference.findIndexOfValue(
                                    WifiApSoftApUtils.getSecurityTypeString(this.mContext, 5)));
                } else if (!isWpa3SecurityTypeSelected()) {
                    setValueIndex(
                            this.mThisDropDownPreference.findIndexOfValue(
                                    WifiApSoftApUtils.getSecurityTypeString(this.mContext, 3)));
                }
            } else if (this.mIs6GhzBandSelectedPreviouslyFlag) {
                int i = this.mNewChangedSecurityTypeByTouch;
                if (i == -1) {
                    Log.i(str, "User didn't touch security Type, retaining previous saved Type");
                    setValueIndex(
                            this.mThisDropDownPreference.findIndexOfValue(
                                    WifiApSoftApUtils.getSecurityTypeString(
                                            this.mContext,
                                            Settings.Secure.getInt(
                                                    this.mContext.getContentResolver(),
                                                    "wifi_ap_security_type",
                                                    WifiApSoftApUtils.getSecurityType(
                                                            this.mContext)))));
                } else {
                    setValueIndex(
                            this.mThisDropDownPreference.findIndexOfValue(
                                    WifiApSoftApUtils.getSecurityTypeString(this.mContext, i)));
                }
                this.mIs6GhzBandSelectedPreviouslyFlag = false;
            }
            TooltipPopup$$ExternalSyntheticOutline0.m(
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "updateState - OldValue: ",
                            ", NewValue: ",
                            dropDownValue,
                            getDropDownValue(),
                            ", mNewChangedSecurityTypeByTouch: "),
                    this.mNewChangedSecurityTypeByTouch,
                    str);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
