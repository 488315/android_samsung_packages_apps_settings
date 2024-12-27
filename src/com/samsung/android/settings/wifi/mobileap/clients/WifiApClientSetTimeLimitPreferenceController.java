package com.samsung.android.settings.wifi.mobileap.clients;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.picker.app.SeslTimePickerDialog;
import androidx.picker.widget.SeslTimePicker;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApConnectedDeviceUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApDateUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.views.WifiApPreference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApClientSetTimeLimitPreferenceController extends BasePreferenceController {
    private static final String TAG = "WifiApClientSetTimeLimitPreferenceController";
    private long mConsumedTimeTodayTotal;
    private String mMacAddress;
    private SeslTimePickerDialog mSeslTimePickerDialog;
    private long mSetTimeLimitInMillis;
    private WifiApPreference mThisPreference;
    private WifiApClientSetTimeLimitPicker mTimePickerDialog;

    public WifiApClientSetTimeLimitPreferenceController(Context context, String str) {
        super(context, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$showChangeTimeDialog$0(SeslTimePicker seslTimePicker, int i, int i2) {
        SALogging.insertSALog(1L, "TETH_014", "8087");
        long millis = TimeUnit.MINUTES.toMillis((i * 60) + i2);
        long wifiApClientUsedTimeToday = WifiApConnectedDeviceUtils.getWifiApClientUsedTimeToday(this.mContext, this.mMacAddress);
        this.mConsumedTimeTodayTotal = wifiApClientUsedTimeToday;
        if (millis < wifiApClientUsedTimeToday) {
            return;
        }
        WifiApConnectedDeviceUtils.setWifiApClientTimeLimit(this.mContext, this.mMacAddress, millis);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChangeTimeDialog$1(DialogInterface dialogInterface) {
        this.mTimePickerDialog.dismiss();
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda0] */
    private void showChangeTimeDialog() {
        int minutes;
        int hours;
        ?? r3 = new SeslTimePickerDialog.OnTimeSetListener() { // from class: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda0
            @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
            public final void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
                WifiApClientSetTimeLimitPreferenceController.this.lambda$showChangeTimeDialog$0(seslTimePicker, i, i2);
            }
        };
        WifiApClientSetTimeLimitPicker wifiApClientSetTimeLimitPicker = this.mTimePickerDialog;
        if (wifiApClientSetTimeLimitPicker != null && wifiApClientSetTimeLimitPicker.isShowing()) {
            this.mTimePickerDialog.dismiss();
        }
        long j = this.mSetTimeLimitInMillis;
        if (j > 0) {
            TimeUnit timeUnit = TimeUnit.MILLISECONDS;
            minutes = ((int) timeUnit.toMinutes(j)) % 60;
            hours = (int) timeUnit.toHours(this.mSetTimeLimitInMillis);
        } else {
            long wifiApClientUsedTimeToday = WifiApConnectedDeviceUtils.getWifiApClientUsedTimeToday(this.mContext, this.mMacAddress);
            TimeUnit timeUnit2 = TimeUnit.MILLISECONDS;
            minutes = ((int) timeUnit2.toMinutes(wifiApClientUsedTimeToday)) % 60;
            hours = (int) timeUnit2.toHours(wifiApClientUsedTimeToday);
        }
        WifiApClientSetTimeLimitPicker wifiApClientSetTimeLimitPicker2 = new WifiApClientSetTimeLimitPicker(this.mContext, this.mMacAddress, r3, hours, minutes);
        this.mTimePickerDialog = wifiApClientSetTimeLimitPicker2;
        wifiApClientSetTimeLimitPicker2.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPreferenceController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                WifiApClientSetTimeLimitPreferenceController.this.lambda$showChangeTimeDialog$1(dialogInterface);
            }
        });
        this.mTimePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.samsung.android.settings.wifi.mobileap.clients.WifiApClientSetTimeLimitPreferenceController.1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                WifiApClientSetTimeLimitPreferenceController wifiApClientSetTimeLimitPreferenceController = WifiApClientSetTimeLimitPreferenceController.this;
                wifiApClientSetTimeLimitPreferenceController.updateState(wifiApClientSetTimeLimitPreferenceController.mThisPreference);
            }
        });
        this.mTimePickerDialog.show();
    }

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mThisPreference = (WifiApPreference) preferenceScreen.findPreference(getPreferenceKey());
        this.mSetTimeLimitInMillis = WifiApConnectedDeviceUtils.getWifiApClientSetTimeLimit(this.mContext, this.mMacAddress);
        this.mConsumedTimeTodayTotal = WifiApConnectedDeviceUtils.getWifiApClientUsedTimeToday(this.mContext, this.mMacAddress);
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

    @Override // com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        String str = TAG;
        Log.i(str, "handlePreferenceTreeClick - Triggered");
        SALogging.insertSALog("TETH_014", "8084");
        SeslTimePickerDialog seslTimePickerDialog = this.mSeslTimePickerDialog;
        if (seslTimePickerDialog == null || !seslTimePickerDialog.isShowing()) {
            showChangeTimeDialog();
            return true;
        }
        Log.d(str, "Dialog is showing, ignore");
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
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
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
        this.mSetTimeLimitInMillis = WifiApConnectedDeviceUtils.getWifiApClientSetTimeLimit(this.mContext, this.mMacAddress);
        this.mConsumedTimeTodayTotal = WifiApConnectedDeviceUtils.getWifiApClientUsedTimeToday(this.mContext, this.mMacAddress);
        if (this.mSetTimeLimitInMillis <= 0) {
            WifiApPreference wifiApPreference = this.mThisPreference;
            if (!TextUtils.equals(wifiApPreference.mSummary2Text, null)) {
                wifiApPreference.mSummary2Text = null;
                wifiApPreference.notifyChanged();
            }
            this.mThisPreference.setSummary(R.string.none);
            WifiApPreference wifiApPreference2 = this.mThisPreference;
            wifiApPreference2.mProgressBarVisibility = false;
            wifiApPreference2.notifyChanged();
            WifiApPreference wifiApPreference3 = this.mThisPreference;
            wifiApPreference3.mProgress = 0;
            wifiApPreference3.notifyChanged();
            return;
        }
        WifiApPreference wifiApPreference4 = this.mThisPreference;
        wifiApPreference4.mProgressBarVisibility = true;
        wifiApPreference4.notifyChanged();
        if (WifiApConnectedDeviceUtils.getWifiApClientDetails(this.mContext, this.mMacAddress).isClientDataPausedByUser()) {
            WifiApPreference wifiApPreference5 = this.mThisPreference;
            wifiApPreference5.mProgressColor = this.mContext.getColor(R.color.dw_data_type_1_disabled);
            wifiApPreference5.notifyChanged();
        } else {
            WifiApPreference wifiApPreference6 = this.mThisPreference;
            wifiApPreference6.mProgressColor = this.mContext.getColor(R.color.dw_data_type_1);
            wifiApPreference6.notifyChanged();
        }
        double d = (this.mConsumedTimeTodayTotal * 100) / this.mSetTimeLimitInMillis;
        WifiApPreference wifiApPreference7 = this.mThisPreference;
        wifiApPreference7.mProgress = (int) d;
        wifiApPreference7.notifyChanged();
        int convertTimeToMinutes = WifiApDateUtils.convertTimeToMinutes(this.mSetTimeLimitInMillis);
        int convertTimeToMinutes2 = WifiApDateUtils.convertTimeToMinutes(this.mConsumedTimeTodayTotal);
        String convertTimeToLocale = WifiApDateUtils.convertTimeToLocale(this.mContext, convertTimeToMinutes);
        String convertTimeToLocale2 = WifiApDateUtils.convertTimeToLocale(this.mContext, convertTimeToMinutes2);
        this.mThisPreference.setSummary(WifiApDateUtils.convertTimeToLocaleWithRemainingText(this.mContext, convertTimeToMinutes - convertTimeToMinutes2));
        WifiApPreference wifiApPreference8 = this.mThisPreference;
        String m = AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(convertTimeToLocale2, "/", convertTimeToLocale);
        if (!TextUtils.equals(wifiApPreference8.mSummary2Text, m)) {
            wifiApPreference8.mSummary2Text = m;
            wifiApPreference8.notifyChanged();
        }
        WifiApPreference wifiApPreference9 = this.mThisPreference;
        wifiApPreference9.mSummaryMaxLines = 1;
        wifiApPreference9.mSummary2MaxLines = 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
    }
}
