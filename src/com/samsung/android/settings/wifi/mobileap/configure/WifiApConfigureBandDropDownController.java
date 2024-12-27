package com.samsung.android.settings.wifi.mobileap.configure;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
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
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApConfigureBandDropDownController extends BasePreferenceController
        implements LifecycleEventObserver, Preference.OnPreferenceChangeListener {
    private static final String BUNDLE_KEY_BAND_DROPDOWN_VALUE = "bundle_key_band_dropdown__value";
    public static final String KEY_PREFERENCE = "band_dropdown_preference";
    private static final String TAG = "WifiApConfigureBandDropDownController";
    private Context mContext;
    private SecDropDownPreference mThisDropDownPreference;
    private WifiApBandConfig mWifiApBandConfig;
    private List<WifiApBandConfig> mWifiApBandConfigList;
    private WifiApEditSettings mWifiApConfigureSettings;

    public WifiApConfigureBandDropDownController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mWifiApBandConfigList = WifiApSoftApUtils.getSupportedBandList(context);
    }

    private String[] getBandDisplayTextArray(List<WifiApBandConfig> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<WifiApBandConfig> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().mDisplayText);
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private String[] getBandDisplayValueArray(List<WifiApBandConfig> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<WifiApBandConfig> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(it.next().mDisplayValueIndex));
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    private void setEntries(String[] strArr) {
        this.mThisDropDownPreference.setEntries(strArr);
    }

    private void setEntryValues(String[] strArr) {
        this.mThisDropDownPreference.mEntryValues = strArr;
    }

    private void setSummaryText(WifiApBandConfig wifiApBandConfig) {
        String sb;
        if (this.mWifiApBandConfigList.size() == 1) {
            sb = wifiApBandConfig.mDisplayText;
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(wifiApBandConfig.mDisplayText);
            sb2.append("\n");
            sb2.append(
                    ((ArrayList) WifiApSoftApUtils.getSupportedBandList(wifiApBandConfig.mContext))
                                            .size()
                                    <= 1
                            ? ApnSettings.MVNO_NONE
                            : wifiApBandConfig.mDisplaySummaryText);
            sb = sb2.toString();
        }
        SpannableString spannableString = new SpannableString(sb);
        int length = getWifiApBandConfig().mDisplayText.length();
        spannableString.setSpan(
                new ForegroundColorSpan(
                        this.mContext.getResources().getColor(R.color.wifi_ap_primary_text_color)),
                0,
                length,
                0);
        if (spannableString.length() > length) {
            spannableString.setSpan(
                    new ForegroundColorSpan(
                            this.mContext
                                    .getResources()
                                    .getColor(R.color.sec_widget_body_text_color)),
                    length + 1,
                    spannableString.length(),
                    0);
        }
        this.mThisDropDownPreference.setSummary(spannableString);
    }

    private void setValueIndex(WifiApBandConfig wifiApBandConfig) {
        this.mWifiApBandConfig = wifiApBandConfig;
        this.mThisDropDownPreference.setValueIndex(wifiApBandConfig.mDisplayValueIndex);
        setSummaryText(wifiApBandConfig);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Log.i(TAG, "displayPreference");
        this.mThisDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference(getPreferenceKey());
        setEntries(getBandDisplayTextArray(this.mWifiApBandConfigList));
        setEntryValues(getBandDisplayValueArray(this.mWifiApBandConfigList));
        setValueIndex(WifiApSoftApUtils.getWifiApBandConfig(this.mContext));
        if (this.mWifiApBandConfigList.size() == 1) {
            this.mThisDropDownPreference.setSelectable(false);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = TAG;
        Log.i(str, "getAvailabilityStatus:");
        if (WifiApFrameworkUtils.isBandSeekBarUxSupported(this.mContext)) {
            Log.i(str, "CONDITIONALLY_UNAVAILABLE:");
            return 2;
        }
        Log.i(str, "AVAILABLE");
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public int[] getBandArray() {
        return this.mWifiApBandConfig.mBandArray;
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

    public WifiApBandConfig getWifiApBandConfig() {
        return this.mWifiApBandConfig;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        WifiApSettingsUtils.hideKeyboard(this.mContext);
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Log.i(TAG, "Band value changed:" + obj);
        WifiApBandConfig wifiApBandConfig =
                (WifiApBandConfig)
                        ((ArrayList) WifiApSoftApUtils.getSupportedBandList(this.mContext))
                                .get(Integer.parseInt(obj.toString()));
        SALogging.insertSALog(
                (long) wifiApBandConfig.mSaLoggingEventId, "TETH_011", "8013", (String) null);
        this.mWifiApBandConfig = wifiApBandConfig;
        setSummaryText(wifiApBandConfig);
        WifiApEditSettings wifiApEditSettings = this.mWifiApConfigureSettings;
        wifiApEditSettings.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings, 4), 10L);
        WifiApEditSettings wifiApEditSettings2 = this.mWifiApConfigureSettings;
        wifiApEditSettings2.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings2, 5), 10L);
        WifiApEditSettings wifiApEditSettings3 = this.mWifiApConfigureSettings;
        wifiApEditSettings3.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings3, 3), 10L);
        WifiApEditSettings wifiApEditSettings4 = this.mWifiApConfigureSettings;
        wifiApEditSettings4.mHandler.postDelayed(
                new WifiApEditSettings$$ExternalSyntheticLambda0(wifiApEditSettings4, 1), 10L);
        return true;
    }

    public void onRestoreInstanceState(Bundle bundle) {
        if (isAvailable() && bundle.containsKey(BUNDLE_KEY_BAND_DROPDOWN_VALUE)) {
            int i = bundle.getInt(BUNDLE_KEY_BAND_DROPDOWN_VALUE);
            MainClearConfirm$$ExternalSyntheticOutline0.m(i, "onRestoreInstanceState: ", TAG);
            WifiApBandConfig wifiApBandConfig =
                    (WifiApBandConfig)
                            ((ArrayList) WifiApSoftApUtils.getSupportedBandList(this.mContext))
                                    .get(i);
            this.mWifiApBandConfig = wifiApBandConfig;
            setValueIndex(wifiApBandConfig);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (isAvailable()) {
            bundle.putInt(BUNDLE_KEY_BAND_DROPDOWN_VALUE, getWifiApBandConfig().mDisplayValueIndex);
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // androidx.lifecycle.LifecycleEventObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {}
}
