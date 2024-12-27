package com.android.settings.display.darkmode;

import android.app.TimePickerDialog;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.preference.Preference;

import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DarkModeCustomPreferenceController extends BasePreferenceController {
    private static final String END_TIME_KEY = "dark_theme_end_time";
    private static final String START_TIME_KEY = "dark_theme_start_time";
    private TimeFormatter mFormat;
    private DarkModeSettingsFragment mFragmet;
    private final UiModeManager mUiModeManager;

    public DarkModeCustomPreferenceController(Context context, String str) {
        super(context, str);
        this.mFormat = new TimeFormatter(this.mContext);
        this.mUiModeManager = (UiModeManager) context.getSystemService(UiModeManager.class);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lambda$getDialog$0(TimePicker timePicker, int i, int i2) {
        LocalTime of = LocalTime.of(i, i2);
        if (TextUtils.equals(getPreferenceKey(), START_TIME_KEY)) {
            this.mUiModeManager.setCustomNightModeStart(of);
        } else {
            this.mUiModeManager.setCustomNightModeEnd(of);
        }
        DarkModeSettingsFragment darkModeSettingsFragment = this.mFragmet;
        if (darkModeSettingsFragment != null) {
            darkModeSettingsFragment.updatePreferenceStates();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (this.mUiModeManager.getNightMode() == 3
                        && this.mUiModeManager.getNightModeCustomType() == 0)
                ? 0
                : 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public TimePickerDialog getDialog() {
        LocalTime customNightModeStart =
                TextUtils.equals(getPreferenceKey(), START_TIME_KEY)
                        ? this.mUiModeManager.getCustomNightModeStart()
                        : this.mUiModeManager.getCustomNightModeEnd();
        return new TimePickerDialog(
                this.mContext,
                new TimePickerDialog
                        .OnTimeSetListener() { // from class:
                                               // com.android.settings.display.darkmode.DarkModeCustomPreferenceController$$ExternalSyntheticLambda0
                    @Override // android.app.TimePickerDialog.OnTimeSetListener
                    public final void onTimeSet(TimePicker timePicker, int i, int i2) {
                        DarkModeCustomPreferenceController.this.lambda$getDialog$0(
                                timePicker, i, i2);
                    }
                },
                customNightModeStart.getHour(),
                customNightModeStart.getMinute(),
                DateFormat.is24HourFormat(this.mFormat.mContext));
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        preference.setSummary(
                this.mFormat.of(
                        TextUtils.equals(getPreferenceKey(), START_TIME_KEY)
                                ? this.mUiModeManager.getCustomNightModeStart()
                                : this.mUiModeManager.getCustomNightModeEnd()));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public DarkModeCustomPreferenceController(
            Context context, String str, DarkModeSettingsFragment darkModeSettingsFragment) {
        this(context, str);
        this.mFragmet = darkModeSettingsFragment;
    }

    public DarkModeCustomPreferenceController(
            Context context,
            String str,
            DarkModeSettingsFragment darkModeSettingsFragment,
            TimeFormatter timeFormatter) {
        this(context, str, darkModeSettingsFragment);
        this.mFormat = timeFormatter;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
