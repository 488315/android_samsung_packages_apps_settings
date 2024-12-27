package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.wifi.mobileap.WifiApBigDataLogger;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureBroadcastChannelDropDownController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {
    private static final String BUNDLE_KEY_BROADCAST_CHANNEL_DROPDOWN_VALUE =
            "bundle_key_broadcast_channel_dropdown_value";
    public static final String KEY_PREFERENCE = "broadcast_channel_dropdown_preference";
    private static final String TAG = "WifiApConfigureBroadcastChannelDropDownController";
    private Context mContext;
    private DropDownPreference mThisDropDownPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureBroadcastChannelDropDownController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private void setSummary(CharSequence charSequence) {
        this.mThisDropDownPreference.setSummary(charSequence);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference()");
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisDropDownPreference = dropDownPreference;
        dropDownPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.wifi_ap_primary_text_color, this.mContext.getTheme()));
        this.mThisDropDownPreference.setEntries(
                this.mContext
                        .getResources()
                        .getStringArray(R.array.wifi_ap_broadcast_channel_entries));
        this.mThisDropDownPreference.mEntryValues =
                this.mContext
                        .getResources()
                        .getStringArray(R.array.wifi_ap_broadcast_channel_entry_values);
        setValueIndex(WifiApSoftApUtils.getBroadcastChannel(this.mContext));
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

    public int getDropDownValue() {
        return Integer.parseInt(this.mThisDropDownPreference.mValue);
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

    public boolean isValueModified() {
        if (isAvailable()) {
            int broadcastChannel = WifiApSoftApUtils.getBroadcastChannel(this.mContext);
            int dropDownValue = getDropDownValue();
            r1 = broadcastChannel != dropDownValue;
            SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(
                    RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                            "Values, Old: ",
                            ", New: ",
                            broadcastChannel,
                            dropDownValue,
                            "Is value modified: "),
                    r1,
                    TAG);
        }
        return r1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt(obj.toString());
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                parseInt, "onPreferenceChange New value :", TAG);
        setSummary(this.mThisDropDownPreference.mEntries[parseInt]);
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_BROADCAST_CHANNEL_DROPDOWN_VALUE)) {
            String string = bundle.getString(BUNDLE_KEY_BROADCAST_CHANNEL_DROPDOWN_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", string, TAG);
            setValueIndex(this.mThisDropDownPreference.findIndexOfValue(string));
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putString(
                    BUNDLE_KEY_BROADCAST_CHANNEL_DROPDOWN_VALUE,
                    this.mThisDropDownPreference.mValue);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void saveValue() {
        if (this.mWifiApConfigureSettings.getBandPreferenceConfig().isThisBand2Ghz()) {
            int broadcastChannel = WifiApSoftApUtils.getBroadcastChannel(this.mContext);
            if (isValueModified()) {
                broadcastChannel = getDropDownValue();
            }
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    broadcastChannel, "Updating new value: ", TAG);
            Context context = this.mContext;
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    broadcastChannel, "Setting Broadcast Channel): ", "WifiApSoftApUtils");
            Settings.Secure.putInt(
                    context.getContentResolver(),
                    "wifi_ap_last_2g_channel",
                    (broadcastChannel < 0 || broadcastChannel > 11) ? 0 : broadcastChannel);
            new WifiApBigDataLogger(this.mContext).insertMHSBigData(4, broadcastChannel);
        }
    }

    public void setHost(WifiApEditSettings wifiApEditSettings) {
        this.mWifiApConfigureSettings = wifiApEditSettings;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    public void setValueIndex(int i) {
        this.mThisDropDownPreference.setValueIndex(i);
        setSummary(this.mThisDropDownPreference.mEntries[i]);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        WifiApEditSettings wifiApEditSettings;
        String str = TAG;
        Log.i(str, "updateState() -" + preference.getKey());
        super.updateState(preference);
        if (!isAvailable() || (wifiApEditSettings = this.mWifiApConfigureSettings) == null) {
            return;
        }
        boolean isThisBand2Ghz = wifiApEditSettings.getBandPreferenceConfig().isThisBand2Ghz();
        WifiApEditSettings wifiApEditSettings2 = this.mWifiApConfigureSettings;
        boolean isWifiSharingEnabled =
                WifiApSoftApUtils.isWifiSharingEnabled(wifiApEditSettings2.mContext);
        WifiApConfigureWifiSharingController wifiApConfigureWifiSharingController =
                wifiApEditSettings2.mWifiSharingController;
        if (wifiApConfigureWifiSharingController != null) {
            isWifiSharingEnabled = wifiApConfigureWifiSharingController.getThreadEnabled();
        } else {
            Log.d("WifiApEditSettings", "mWifiSharingController is null");
        }
        Log.i(
                str,
                "Updating state: isWifiSharingEnabled: "
                        + isWifiSharingEnabled
                        + ", wifiApBandConfig(is2Ghz) : "
                        + isThisBand2Ghz);
        this.mThisDropDownPreference.setVisible(!isWifiSharingEnabled && isThisBand2Ghz);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
