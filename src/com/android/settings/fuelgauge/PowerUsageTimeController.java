package com.android.settings.fuelgauge;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.utils.StringUtil;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PowerUsageTimeController extends BasePreferenceController {
    private static final String KEY_BACKGROUND_TIME_PREF = "battery_usage_background_time";
    private static final String KEY_POWER_USAGE_TIME = "battery_usage_time_category";
    private static final String KEY_SCREEN_TIME_PREF = "battery_usage_screen_time";
    private static final String TAG = "PowerUsageTimeController";

    @VisibleForTesting PowerUsageTimePreference mBackgroundTimePreference;

    @VisibleForTesting PreferenceCategory mPowerUsageTimeCategory;

    @VisibleForTesting PowerUsageTimePreference mScreenTimePreference;

    public PowerUsageTimeController(Context context) {
        super(context, KEY_POWER_USAGE_TIME);
    }

    private CharSequence getPowerUsageTimeInfo(long j) {
        return j < 60000
                ? this.mContext.getString(R.string.power_usage_time_less_than_one_minute)
                : ((SpannableStringBuilder)
                                StringUtil.formatElapsedTime(this.mContext, j, false, false))
                        .toString()
                        .replaceAll(",", ApnSettings.MVNO_NONE);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPowerUsageTimeCategory =
                (PreferenceCategory) preferenceScreen.findPreference(KEY_POWER_USAGE_TIME);
        this.mScreenTimePreference =
                (PowerUsageTimePreference) preferenceScreen.findPreference(KEY_SCREEN_TIME_PREF);
        this.mBackgroundTimePreference =
                (PowerUsageTimePreference)
                        preferenceScreen.findPreference(KEY_BACKGROUND_TIME_PREF);
        this.mPowerUsageTimeCategory.setVisible(false);
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

    public void handleScreenTimeUpdated(String str, long j, long j2, String str2, String str3) {
        boolean showTimePreference =
                showTimePreference(
                        this.mScreenTimePreference,
                        R.string.power_usage_detail_screen_time,
                        j,
                        str2,
                        str3);
        boolean showTimePreference2 =
                showTimePreference(
                        this.mBackgroundTimePreference,
                        R.string.power_usage_detail_background_time,
                        j2,
                        str2,
                        str3);
        if (showTimePreference || showTimePreference2) {
            showCategoryTitle(str);
        }
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @VisibleForTesting
    public void showCategoryTitle(String str) {
        this.mPowerUsageTimeCategory.setTitle(
                str == null
                        ? this.mContext.getString(R.string.battery_app_usage)
                        : this.mContext.getString(R.string.battery_app_usage_for, str));
        this.mPowerUsageTimeCategory.setVisible(true);
    }

    public boolean showTimePreference(
            PowerUsageTimePreference powerUsageTimePreference,
            int i,
            long j,
            String str,
            String str2) {
        if (powerUsageTimePreference == null) {
            return false;
        }
        if (j == 0 && !TextUtils.equals(str, powerUsageTimePreference.getKey())) {
            return false;
        }
        String string = this.mContext.getString(i);
        if (!TextUtils.equals(powerUsageTimePreference.mTimeTitle, string)) {
            powerUsageTimePreference.mTimeTitle = string;
            powerUsageTimePreference.notifyChanged();
        }
        CharSequence powerUsageTimeInfo = getPowerUsageTimeInfo(j);
        if (!TextUtils.equals(powerUsageTimePreference.mTimeSummary, powerUsageTimeInfo)) {
            powerUsageTimePreference.mTimeSummary = powerUsageTimeInfo;
            powerUsageTimePreference.notifyChanged();
        }
        if (TextUtils.equals(str, powerUsageTimePreference.getKey())
                && !TextUtils.equals(powerUsageTimePreference.mAnomalyHintText, str2)) {
            powerUsageTimePreference.mAnomalyHintText = str2;
            powerUsageTimePreference.notifyChanged();
        }
        powerUsageTimePreference.setVisible(true);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
