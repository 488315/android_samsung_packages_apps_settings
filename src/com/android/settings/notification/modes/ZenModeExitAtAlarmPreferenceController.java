package com.android.settings.notification.modes;

import android.service.notification.ZenModeConfig;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeExitAtAlarmPreferenceController
        extends AbstractZenModePreferenceController
        implements Preference.OnPreferenceChangeListener {
    public ZenModeConfig.ScheduleInfo mSchedule;

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        if (scheduleInfo.exitAtAlarm == booleanValue) {
            return false;
        }
        scheduleInfo.exitAtAlarm = booleanValue;
        return saveMode(
                new Function() { // from class:
                                 // com.android.settings.notification.modes.ZenModeExitAtAlarmPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        ZenModeExitAtAlarmPreferenceController
                                zenModeExitAtAlarmPreferenceController =
                                        ZenModeExitAtAlarmPreferenceController.this;
                        ZenMode zenMode = (ZenMode) obj2;
                        zenModeExitAtAlarmPreferenceController.getClass();
                        zenMode.mRule.setConditionId(
                                ZenModeConfig.toScheduleConditionId(
                                        zenModeExitAtAlarmPreferenceController.mSchedule));
                        return zenMode;
                    }
                });
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        ZenModeConfig.ScheduleInfo tryParseScheduleConditionId =
                ZenModeConfig.tryParseScheduleConditionId(zenMode.mRule.getConditionId());
        this.mSchedule = tryParseScheduleConditionId;
        ((TwoStatePreference) preference).setChecked(tryParseScheduleConditionId.exitAtAlarm);
    }
}
