package com.android.settings.notification.modes;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeTimePickerFragment extends InstrumentedDialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    public final Context mContext;
    public final int mHour;
    public final int mMinute;
    public final ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2 mTimeSetter;

    public ZenModeTimePickerFragment(
            Context context,
            int i,
            int i2,
            ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2
                    zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2) {
        this.mContext = context;
        this.mHour = i;
        this.mMinute = i2;
        this.mTimeSetter = zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 556;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Context context = this.mContext;
        return new TimePickerDialog(
                context, this, this.mHour, this.mMinute, DateFormat.is24HourFormat(context));
    }

    @Override // android.app.TimePickerDialog.OnTimeSetListener
    public final void onTimeSet(TimePicker timePicker, int i, int i2) {
        ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2
                zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2 = this.mTimeSetter;
        switch (zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2.$r8$classId) {
            case 0:
                ZenModeSetSchedulePreferenceController zenModeSetSchedulePreferenceController =
                        zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2.f$0;
                zenModeSetSchedulePreferenceController.getClass();
                if (ZenModeConfig.isValidHour(i) && ZenModeConfig.isValidMinute(i2)) {
                    ZenModeConfig.ScheduleInfo scheduleInfo =
                            zenModeSetSchedulePreferenceController.mSchedule;
                    if (i != scheduleInfo.startHour || i2 != scheduleInfo.startMinute) {
                        scheduleInfo.startHour = i;
                        scheduleInfo.startMinute = i2;
                        zenModeSetSchedulePreferenceController.saveMode(
                                zenModeSetSchedulePreferenceController.updateScheduleMode(
                                        scheduleInfo));
                        break;
                    }
                }
                break;
            default:
                ZenModeSetSchedulePreferenceController zenModeSetSchedulePreferenceController2 =
                        zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2.f$0;
                zenModeSetSchedulePreferenceController2.getClass();
                if (ZenModeConfig.isValidHour(i) && ZenModeConfig.isValidMinute(i2)) {
                    ZenModeConfig.ScheduleInfo scheduleInfo2 =
                            zenModeSetSchedulePreferenceController2.mSchedule;
                    if (i != scheduleInfo2.endHour || i2 != scheduleInfo2.endMinute) {
                        scheduleInfo2.endHour = i;
                        scheduleInfo2.endMinute = i2;
                        zenModeSetSchedulePreferenceController2.saveMode(
                                zenModeSetSchedulePreferenceController2.updateScheduleMode(
                                        scheduleInfo2));
                        break;
                    }
                }
                break;
        }
    }
}
