package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.WifiApBigDataLogger;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSoftApUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureTurnOffHotspotTimerDropDownController extends BasePreferenceController
        implements LifecycleEventObserver, Preference.OnPreferenceChangeListener {
    private static final String BUNDLE_KEY_TURN_OFF_HOTSPOT_TIMER_DROPDOWN_VALUE =
            "bundle_key_turn_off_hotspot_timer_dropdown_value";
    public static final String KEY_PREFERENCE = "turn_off_hotspot_timer_dropdown_preference";
    private static final String TAG = "WifiApConfigureTurnOffHotspotTimerDropDownController";
    private Context mContext;
    private DropDownPreference mThisDropDownPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureTurnOffHotspotTimerDropDownController(Context context, String str) {
        super(context, str);
        this.mContext = context;
    }

    private int findIndexOfValue(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "Finding Index Of Value: ", TAG);
        return this.mThisDropDownPreference.findIndexOfValue(Integer.toString(i));
    }

    private void setSummary(CharSequence charSequence) {
        this.mThisDropDownPreference.setSummary(charSequence);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
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
                        .getStringArray(R.array.wifi_ap_turn_off_hotspot_timer_entries));
        this.mThisDropDownPreference.mEntryValues =
                this.mContext
                        .getResources()
                        .getStringArray(R.array.wifi_ap_turn_off_hotspot_timer_entry_values);
        setValueIndex(
                findIndexOfValue(WifiApSoftApUtils.getTurnOffHotspotTimerInMinutes(this.mContext)));
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
        boolean z;
        if (isAvailable()) {
            int turnOffHotspotTimerInMinutes =
                    WifiApSoftApUtils.getTurnOffHotspotTimerInMinutes(this.mContext);
            int dropDownValue = getDropDownValue();
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "Values, Old: ", ", New: ", turnOffHotspotTimerInMinutes, dropDownValue, TAG);
            if (turnOffHotspotTimerInMinutes != dropDownValue) {
                z = true;
                AbsAdapter$$ExternalSyntheticOutline0.m("Is value modified: ", TAG, z);
                return z;
            }
        }
        z = false;
        AbsAdapter$$ExternalSyntheticOutline0.m("Is value modified: ", TAG, z);
        return z;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        int parseInt = Integer.parseInt(obj.toString());
        int dropDownValue = getDropDownValue();
        int findIndexOfValue = this.mThisDropDownPreference.findIndexOfValue(obj.toString());
        String str = TAG;
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "OnPreferenceChange - OldValue: ",
                        ", NewValue: ",
                        dropDownValue,
                        parseInt,
                        " (Index: ");
        m.append(findIndexOfValue);
        m.append(")");
        Log.i(str, m.toString());
        setSummary(this.mThisDropDownPreference.mEntries[findIndexOfValue]);
        SALogging.insertSALog(findIndexOfValue, "TETH_012", "8017");
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_TURN_OFF_HOTSPOT_TIMER_DROPDOWN_VALUE)) {
            String string = bundle.getString(BUNDLE_KEY_TURN_OFF_HOTSPOT_TIMER_DROPDOWN_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", string, TAG);
            setValueIndex(this.mThisDropDownPreference.findIndexOfValue(string));
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putString(
                    BUNDLE_KEY_TURN_OFF_HOTSPOT_TIMER_DROPDOWN_VALUE,
                    this.mThisDropDownPreference.mValue);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void saveValue() {
        if (isValueModified()) {
            int dropDownValue = getDropDownValue();
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    dropDownValue, "Updating new value: ", TAG);
            WifiApSoftApUtils.setTurnOffHotspotTimerInMinutes(this.mContext, dropDownValue);
            new WifiApBigDataLogger(this.mContext).insertMHSBigData(7, dropDownValue);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
