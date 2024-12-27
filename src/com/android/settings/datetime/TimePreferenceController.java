package com.android.settings.datetime;

import android.app.time.TimeCapabilitiesAndConfig;
import android.app.time.TimeManager;
import android.app.timedetector.ManualTimeSuggestion;
import android.app.timedetector.TimeDetector;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.picker.app.SeslTimePickerDialog;
import androidx.picker.widget.SeslTimePicker;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.MinorModeUtils;
import com.android.settingslib.RestrictedPreference;

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
public class TimePreferenceController extends BasePreferenceController
        implements SeslTimePickerDialog.OnTimeSetListener {
    public static final int DIALOG_TIMEPICKER = 1;
    private static final String TAG = "TimePreferenceController";
    private TimePreferenceHost mHost;
    private TimeManager mTimeManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TimePreferenceHost extends UpdateTimeAndDateCallback {}

    public TimePreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
    }

    private TimeCapabilitiesAndConfig getTimeCapabilitiesAndConfig() {
        return this.mTimeManager.getTimeCapabilitiesAndConfig();
    }

    public SeslTimePickerDialog buildTimePicker(Context context) {
        Calendar calendar = Calendar.getInstance();
        SeslTimePickerDialog seslTimePickerDialog =
                new SeslTimePickerDialog(
                        context,
                        this,
                        calendar.get(11),
                        calendar.get(12),
                        DateFormat.is24HourFormat(context));
        seslTimePickerDialog.getWindow().setSoftInputMode(16);
        return seslTimePickerDialog;
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
        return DateFormat.getTimeFormat(this.mContext).format(Calendar.getInstance().getTime());
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
        LoggingHelper.insertEventLogging(38, 4752);
        DateTimeSettings dateTimeSettings = (DateTimeSettings) this.mHost;
        dateTimeSettings.removeDialog(1);
        dateTimeSettings.showDialog(1);
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

    @Override // androidx.picker.app.SeslTimePickerDialog.OnTimeSetListener
    public void onTimeSet(SeslTimePicker seslTimePicker, int i, int i2) {
        if (this.mContext != null) {
            setTime(i, i2);
            ((DateTimeSettings) this.mHost).updatePreferenceStates();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setHost(TimePreferenceHost timePreferenceHost) {
        this.mHost = timePreferenceHost;
    }

    public void setTime(int i, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, i);
        calendar.set(12, i2);
        calendar.set(13, 0);
        calendar.set(14, 0);
        long timeInMillis = calendar.getTimeInMillis();
        TimeDetector timeDetector =
                (TimeDetector) this.mContext.getSystemService(TimeDetector.class);
        ManualTimeSuggestion createManualTimeSuggestion =
                TimeDetector.createManualTimeSuggestion(timeInMillis, "Settings: Set time");
        if (timeDetector.suggestManualTime(createManualTimeSuggestion)) {
            return;
        }
        Log.w(TAG, "Unable to set time with suggestion=" + createManualTimeSuggestion);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof RestrictedPreference) {
            preference.setSummary(
                    DateFormat.getTimeFormat(this.mContext)
                            .format(Calendar.getInstance().getTime()));
            if (!((RestrictedPreference) preference).mHelper.mDisabledByAdmin) {
                if (DateTimeUtils.applyEDMDateTimeChangePolicy(this.mContext)) {
                    preference.setEnabled(isEnabled());
                } else {
                    preference.setEnabled(false);
                }
            }
            SecRestrictedPreference secRestrictedPreference = (SecRestrictedPreference) preference;
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
