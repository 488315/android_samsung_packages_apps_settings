package com.android.settings.datetime;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.text.format.DateFormat;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.Calendar;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeFormatPreferenceController extends TogglePreferenceController {
    static final String HOURS_12 = "12";
    static final String HOURS_24 = "24";
    private static final String KEY_TIME_FORMAT = "24_hour";
    private final Calendar mDummyDate;
    private boolean mIsFromSUW;
    private UpdateTimeAndDateCallback mUpdateTimeAndDateCallback;

    public TimeFormatPreferenceController(Context context, String str) {
        super(context, str);
        this.mDummyDate = Calendar.getInstance();
    }

    private boolean is24Hour() {
        return DateFormat.is24HourFormat(this.mContext);
    }

    public static void set24Hour(Context context, Boolean bool) {
        Settings.System.putString(
                context.getContentResolver(),
                "time_12_24",
                bool == null ? null : bool.booleanValue() ? "24" : "12");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void timeUpdated(Context context, Boolean bool) {
        Intent intent = new Intent("android.intent.action.TIME_SET");
        intent.addFlags(16777216);
        intent.putExtra(
                "android.intent.extra.TIME_PREF_24_HOUR_FORMAT",
                bool == null ? 2 : bool.booleanValue());
        context.sendBroadcast(intent);
    }

    public static void update24HourFormat(Context context, Boolean bool) {
        set24Hour(context, bool);
        timeUpdated(context, bool);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mIsFromSUW ? 5 : 0;
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
        return R.string.menu_key_system;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Calendar calendar = Calendar.getInstance();
        this.mDummyDate.setTimeZone(calendar.getTimeZone());
        this.mDummyDate.set(calendar.get(1), 11, 31, 13, 0, 0);
        return DateFormat.getTimeFormat(this.mContext).format(this.mDummyDate.getTime());
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
        return is24Hour();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        LoggingHelper.insertEventLogging(38, 4752, z);
        update24HourFormat(this.mContext, Boolean.valueOf(z));
        ((DateTimeSettings) this.mUpdateTimeAndDateCallback).updatePreferenceStates();
        return true;
    }

    public TimeFormatPreferenceController setFromSUW(boolean z) {
        this.mIsFromSUW = z;
        return this;
    }

    public TimeFormatPreferenceController setTimeAndDateCallback(
            UpdateTimeAndDateCallback updateTimeAndDateCallback) {
        this.mUpdateTimeAndDateCallback = updateTimeAndDateCallback;
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(getAvailabilityStatus() == 0);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
