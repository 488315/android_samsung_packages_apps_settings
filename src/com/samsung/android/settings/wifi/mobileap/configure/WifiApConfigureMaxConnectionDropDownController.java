package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
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
import com.samsung.android.settings.wifi.mobileap.WifiApBigDataLogger;
import com.samsung.android.settings.wifi.mobileap.WifiApEditSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureMaxConnectionDropDownController extends BasePreferenceController
        implements LifecycleEventObserver, Preference.OnPreferenceChangeListener {
    private static final String BUNDLE_KEY_MAX_CONNECTION_DROPDOWN_VALUE =
            "bundle_key_max_connection_dropdown_value";
    public static final String KEY_PREFERENCE = "max_connection_dropdown_preference";
    private static final String TAG = "WifiApConfigureMaxConnectionDropDownController";
    private Context mContext;
    private DropDownPreference mThisDropDownPreference;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureMaxConnectionDropDownController(Context context, String str) {
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
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mThisDropDownPreference = dropDownPreference;
        dropDownPreference.seslSetSummaryColor(
                this.mContext
                        .getResources()
                        .getColor(R.color.wifi_ap_primary_text_color, this.mContext.getTheme()));
        int wifiApMaxClientDefault = WifiApFrameworkUtils.getWifiApMaxClientDefault();
        if (wifiApMaxClientDefault <= 0) {
            Log.e(TAG, "Max Connection ZERO");
            return;
        }
        CharSequence[] charSequenceArr = new CharSequence[wifiApMaxClientDefault];
        CharSequence[] charSequenceArr2 = new CharSequence[wifiApMaxClientDefault];
        int i = 0;
        while (i < wifiApMaxClientDefault) {
            int i2 = i + 1;
            charSequenceArr[i] = Integer.valueOf(i2).toString();
            charSequenceArr2[i] = Integer.valueOf(i2).toString();
            i = i2;
        }
        this.mThisDropDownPreference.setEntries(charSequenceArr);
        this.mThisDropDownPreference.mEntryValues = charSequenceArr2;
        setValueIndex(WifiApFrameworkUtils.getWifiApMaxClientFromFramework(this.mContext) - 1);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 3;
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
            int wifiApMaxClientFromFramework =
                    WifiApFrameworkUtils.getWifiApMaxClientFromFramework(this.mContext);
            int dropDownValue = getDropDownValue();
            DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                    "Values, Old: ", ", New: ", wifiApMaxClientFromFramework, dropDownValue, TAG);
            if (wifiApMaxClientFromFramework != dropDownValue) {
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
        Log.i(TAG, "onPreferenceChange New value :" + parseInt);
        setSummary(this.mThisDropDownPreference.mEntries[parseInt - 1]);
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_MAX_CONNECTION_DROPDOWN_VALUE)) {
            String string = bundle.getString(BUNDLE_KEY_MAX_CONNECTION_DROPDOWN_VALUE);
            AbsAdapter$$ExternalSyntheticOutline0.m("onRestoreInstanceState: ", string, TAG);
            setValueIndex(this.mThisDropDownPreference.findIndexOfValue(string));
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putString(
                    BUNDLE_KEY_MAX_CONNECTION_DROPDOWN_VALUE, this.mThisDropDownPreference.mValue);
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
            WifiApFrameworkUtils.getSemWifiManager(this.mContext)
                    .setWifiApMaxClientToFramework(dropDownValue);
            new WifiApBigDataLogger(this.mContext).insertMHSBigData(3, dropDownValue);
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
