package com.android.settings.notification.modes;

import android.app.Flags;
import android.content.Context;
import android.service.notification.SystemZenRules;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.widget.LayoutPreference;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Calendar;
import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenModeSetSchedulePreferenceController
        extends AbstractZenModePreferenceController {
    public final ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2 mEndSetter;
    public final SimpleDateFormat mLongDayFormat;
    public final Fragment mParent;
    public ZenModeConfig.ScheduleInfo mSchedule;
    public final SimpleDateFormat mShortDayFormat;
    public final ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2 mStartSetter;

    public static /* synthetic */ void $r8$lambda$M0azf3lzoHeZKY5fU0IIP8x2vgs(
            ZenModeSetSchedulePreferenceController zenModeSetSchedulePreferenceController,
            int i,
            int i2,
            ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2
                    zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2) {
        zenModeSetSchedulePreferenceController.getClass();
        new ZenModeTimePickerFragment(
                        zenModeSetSchedulePreferenceController.mContext,
                        i,
                        i2,
                        zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2)
                .show(
                        zenModeSetSchedulePreferenceController.mParent.getParentFragmentManager(),
                        "ZenModeSetSchedulePreferenceController");
    }

    public static void $r8$lambda$i0KwHfjRt6iBF4iCBfHvJ9uWSes(
            ZenModeSetSchedulePreferenceController zenModeSetSchedulePreferenceController,
            ZenModeConfig.ScheduleInfo scheduleInfo,
            ZenMode zenMode) {
        zenModeSetSchedulePreferenceController.getClass();
        zenMode.mRule.setConditionId(ZenModeConfig.toScheduleConditionId(scheduleInfo));
        if (Flags.modesApi() && Flags.modesUi()) {
            zenMode.mRule.setTriggerDescription(
                    SystemZenRules.getTriggerDescriptionForScheduleTime(
                            zenModeSetSchedulePreferenceController.mContext, scheduleInfo));
        }
    }

    public ZenModeSetSchedulePreferenceController(
            Context context, Fragment fragment, ZenModesBackend zenModesBackend) {
        super(context, "schedule", zenModesBackend);
        this.mShortDayFormat = new SimpleDateFormat("EEEEE");
        this.mLongDayFormat = new SimpleDateFormat("EEEE");
        this.mStartSetter =
                new ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2(this, 0);
        this.mEndSetter =
                new ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2(this, 1);
        this.mParent = fragment;
    }

    public static boolean updateScheduleDays(
            ZenModeConfig.ScheduleInfo scheduleInfo, int i, boolean z) {
        ArraySet arraySet = new ArraySet();
        if (scheduleInfo.days != null) {
            int i2 = 0;
            while (true) {
                int[] iArr = scheduleInfo.days;
                if (i2 >= iArr.length) {
                    break;
                }
                arraySet.add(Integer.valueOf(iArr[i2]));
                i2++;
            }
        }
        if (arraySet.contains(Integer.valueOf(i)) == z) {
            return false;
        }
        if (z) {
            arraySet.add(Integer.valueOf(i));
        } else {
            arraySet.remove(Integer.valueOf(i));
        }
        int[] iArr2 = new int[arraySet.size()];
        for (int i3 = 0; i3 < arraySet.size(); i3++) {
            iArr2[i3] = ((Integer) arraySet.valueAt(i3)).intValue();
        }
        Arrays.sort(iArr2);
        scheduleInfo.days = iArr2;
        return true;
    }

    public void setupDayToggles(
            ViewGroup viewGroup, final ZenModeConfig.ScheduleInfo scheduleInfo, Calendar calendar) {
        int i;
        boolean z;
        int[] iArr = new int[7];
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        for (int i2 = 0; i2 < 7; i2++) {
            if (firstDayOfWeek > 7) {
                firstDayOfWeek = 1;
            }
            iArr[i2] = firstDayOfWeek;
            firstDayOfWeek++;
        }
        for (int i3 = 0; i3 < 7; i3++) {
            switch (i3) {
                case 0:
                    i = R.id.day0;
                    break;
                case 1:
                    i = R.id.day1;
                    break;
                case 2:
                    i = R.id.day2;
                    break;
                case 3:
                    i = R.id.day3;
                    break;
                case 4:
                    i = R.id.day4;
                    break;
                case 5:
                    i = R.id.day5;
                    break;
                case 6:
                    i = R.id.day6;
                    break;
                default:
                    i = 0;
                    break;
            }
            ToggleButton toggleButton = (ToggleButton) viewGroup.findViewById(i);
            if (toggleButton != null) {
                final int i4 = iArr[i3];
                calendar.set(7, i4);
                if (scheduleInfo.days != null) {
                    int i5 = 0;
                    while (true) {
                        int[] iArr2 = scheduleInfo.days;
                        if (i5 < iArr2.length) {
                            if (iArr2[i5] == i4) {
                                z = true;
                            } else {
                                i5++;
                            }
                        }
                    }
                }
                z = false;
                toggleButton.setTextOn(this.mShortDayFormat.format(calendar.getTime()));
                toggleButton.setTextOff(this.mShortDayFormat.format(calendar.getTime()));
                toggleButton.setContentDescription(this.mLongDayFormat.format(calendar.getTime()));
                toggleButton.setChecked(z);
                toggleButton.setOnCheckedChangeListener(
                        new CompoundButton
                                .OnCheckedChangeListener() { // from class:
                                                             // com.android.settings.notification.modes.ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda0
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(
                                    CompoundButton compoundButton, boolean z2) {
                                ZenModeSetSchedulePreferenceController
                                        zenModeSetSchedulePreferenceController =
                                                ZenModeSetSchedulePreferenceController.this;
                                ZenModeConfig.ScheduleInfo scheduleInfo2 = scheduleInfo;
                                int i6 = i4;
                                zenModeSetSchedulePreferenceController.getClass();
                                if (ZenModeSetSchedulePreferenceController.updateScheduleDays(
                                        scheduleInfo2, i6, z2)) {
                                    zenModeSetSchedulePreferenceController.saveMode(
                                            zenModeSetSchedulePreferenceController
                                                    .updateScheduleMode(scheduleInfo2));
                                }
                            }
                        });
                toggleButton.setVerticalScrollBarEnabled(false);
                toggleButton.setHorizontalScrollBarEnabled(false);
            }
        }
    }

    public Function<ZenMode, ZenMode> updateScheduleMode(
            final ZenModeConfig.ScheduleInfo scheduleInfo) {
        return new Function() { // from class:
                                // com.android.settings.notification.modes.ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                ZenMode zenMode = (ZenMode) obj;
                ZenModeSetSchedulePreferenceController.$r8$lambda$i0KwHfjRt6iBF4iCBfHvJ9uWSes(
                        ZenModeSetSchedulePreferenceController.this, scheduleInfo, zenMode);
                return zenMode;
            }
        };
    }

    @Override // com.android.settings.notification.modes.AbstractZenModePreferenceController
    public final void updateState(Preference preference, ZenMode zenMode) {
        this.mSchedule = ZenModeConfig.tryParseScheduleConditionId(zenMode.mRule.getConditionId());
        LayoutPreference layoutPreference = (LayoutPreference) preference;
        TextView textView = (TextView) layoutPreference.mRootView.findViewById(R.id.start_time);
        ZenModeConfig.ScheduleInfo scheduleInfo = this.mSchedule;
        int i = scheduleInfo.startHour;
        int i2 = scheduleInfo.startMinute;
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, i);
        calendar.set(12, i2);
        textView.setText(DateFormat.getTimeFormat(this.mContext).format(calendar.getTime()));
        ZenModeConfig.ScheduleInfo scheduleInfo2 = this.mSchedule;
        final int i3 = scheduleInfo2.startHour;
        final int i4 = scheduleInfo2.startMinute;
        final ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2
                zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2 =
                        this.mStartSetter;
        textView.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.modes.ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenModeSetSchedulePreferenceController
                                .$r8$lambda$M0azf3lzoHeZKY5fU0IIP8x2vgs(
                                        ZenModeSetSchedulePreferenceController.this,
                                        i3,
                                        i4,
                                        zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2);
                    }
                });
        TextView textView2 = (TextView) layoutPreference.mRootView.findViewById(R.id.end_time);
        ZenModeConfig.ScheduleInfo scheduleInfo3 = this.mSchedule;
        int i5 = scheduleInfo3.endHour;
        int i6 = scheduleInfo3.endMinute;
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(11, i5);
        calendar2.set(12, i6);
        textView2.setText(DateFormat.getTimeFormat(this.mContext).format(calendar2.getTime()));
        ZenModeConfig.ScheduleInfo scheduleInfo4 = this.mSchedule;
        final int i7 = scheduleInfo4.endHour;
        final int i8 = scheduleInfo4.endMinute;
        final ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda2
                zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda22 = this.mEndSetter;
        textView2.setOnClickListener(
                new View
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.modes.ZenModeSetSchedulePreferenceController$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        ZenModeSetSchedulePreferenceController
                                .$r8$lambda$M0azf3lzoHeZKY5fU0IIP8x2vgs(
                                        ZenModeSetSchedulePreferenceController.this,
                                        i7,
                                        i8,
                                        zenModeSetSchedulePreferenceController$$ExternalSyntheticLambda22);
                    }
                });
        TextView textView3 =
                (TextView) layoutPreference.mRootView.findViewById(R.id.schedule_duration);
        ZenModeConfig.ScheduleInfo scheduleInfo5 = this.mSchedule;
        Duration ofMinutes =
                (scheduleInfo5.startHour * 60) + scheduleInfo5.startMinute
                                >= (scheduleInfo5.endHour * 60) + scheduleInfo5.endMinute
                        ? Duration.ofMinutes((r2 + 1440) - r1)
                        : Duration.ofMinutes(r2 - r1);
        int hoursPart = ofMinutes.toHoursPart();
        textView3.setText(
                this.mContext.getString(
                        R.string.zen_mode_schedule_duration,
                        Integer.valueOf(hoursPart),
                        Integer.valueOf(ofMinutes.minusHours(hoursPart).toMinutesPart())));
        setupDayToggles(
                (ViewGroup) layoutPreference.mRootView.findViewById(R.id.days_of_week_container),
                this.mSchedule,
                Calendar.getInstance());
    }
}
