package com.android.settings.datetime;

import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeManager;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TimeDetector;
import android.app.timedetector.TimeDetectorHelper;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.picker.app.SeslDatePickerDialog;
import androidx.picker.widget.SeslDatePicker;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.MinorModeUtils;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.datetime.DateTimeUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DatePreferenceController extends BasePreferenceController {
    public static final int DIALOG_DATEPICKER = 0;
    private static final String TAG = "DatePreferenceController";
    private DatePreferenceHost mHost;
    private final TimeManager mTimeManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DatePreferenceHost extends UpdateTimeAndDateCallback {}

    public DatePreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
    }

    private TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig() {
        return this.mTimeManager.getTimeCapabilitiesAndConfig();
    }

    public SeslDatePickerDialog buildDatePicker(
            Context context, TimeDetectorHelper timeDetectorHelper) {
        Calendar calendar = Calendar.getInstance();
        SeslDatePickerDialog seslDatePickerDialog =
                new SeslDatePickerDialog(
                        context, this, calendar.get(1), calendar.get(2), calendar.get(5));
        seslDatePickerDialog.getWindow().setSoftInputMode(16);
        SeslDatePicker seslDatePicker = seslDatePickerDialog.mDatePicker;
        calendar.clear();
        calendar.set(timeDetectorHelper.getManualDateSelectionYearMin(), 0, 1);
        seslDatePicker.setMinDate(calendar.getTimeInMillis());
        int manualDateSelectionYearMax = timeDetectorHelper.getManualDateSelectionYearMax();
        calendar.clear();
        calendar.set(manualDateSelectionYearMax, 11, 31);
        seslDatePicker.setMaxDate(calendar.getTimeInMillis());
        return seslDatePickerDialog;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !isAutoTimeEnabled() ? 0 : 5;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return DateFormat.getLongDateFormat(this.mContext).format(Calendar.getInstance().getTime());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(getPreferenceKey(), preference.getKey())) {
            return false;
        }
        if (MinorModeUtils.isCHNMinorModeRestrictDateTimeModify(this.mContext)) {
            Toast.makeText(this.mContext, R.string.child_account_can_not_change_time_toast, 0)
                    .show();
            return false;
        }
        LoggingHelper.insertEventLogging(38, 4751);
        ((DateTimeSettings) this.mHost).showDialog(0);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isAutoTimeEnabled() {
        return getTimeCapabilitiesAndConfig().getConfiguration().isAutoDetectionEnabled();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public boolean isEnabled() {
        return this.mTimeManager
                        .getTimeCapabilitiesAndConfig()
                        .getCapabilities()
                        .getSetManualTimeCapability()
                == 40;
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

    public void onDateSet(SeslDatePicker seslDatePicker, int i, int i2, int i3) {
        setDate(i, i2, i3);
        ((DateTimeSettings) this.mHost).updatePreferenceStates();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setDate(int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, i);
        calendar.set(2, i2);
        calendar.set(5, i3);
        long timeInMillis = calendar.getTimeInMillis();
        TimeDetector timeDetector =
                (TimeDetector) this.mContext.getSystemService(TimeDetector.class);
        ManualTimeSuggestion createManualTimeSuggestion =
                TimeDetector.createManualTimeSuggestion(timeInMillis, "Settings: Set date");
        if (timeDetector.suggestManualTime(createManualTimeSuggestion)) {
            return;
        }
        Log.w(TAG, "Unable to set date with suggestion=" + createManualTimeSuggestion);
    }

    public void setHost(DatePreferenceHost datePreferenceHost) {
        this.mHost = datePreferenceHost;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof SecRestrictedPreference) {
            preference.setSummary(
                    DateFormat.getLongDateFormat(this.mContext)
                            .format(Calendar.getInstance().getTime()));
            SecRestrictedPreference secRestrictedPreference = (SecRestrictedPreference) preference;
            if (!secRestrictedPreference.mHelper.mDisabledByAdmin) {
                if (DateTimeUtils.applyEDMDateTimeChangePolicy(this.mContext)) {
                    preference.setEnabled(isEnabled());
                } else {
                    preference.setEnabled(false);
                }
            }
            secRestrictedPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secRestrictedPreference, true);
            preference.setVisible(isEnabled());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
