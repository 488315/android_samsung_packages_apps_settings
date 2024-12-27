package com.samsung.android.settings.datetime;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.picker.widget.SeslTimePicker;
import androidx.picker.widget.SeslTimePickerSpinnerDelegate;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.notification.zen.ZenModeScheduleRuleSettings;

import com.google.android.material.tabs.TabLayout;
import com.sec.ims.configuration.DATA;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class CustomTimePickerDialog extends AlertDialog
        implements SeslTimePicker.OnTimeChangedListener,
                SeslTimePicker.OnEditTextModeChangedListener {
    public final Context mContext;
    public int mEndTime;
    public boolean mIs24HourFormat;
    public int mStartTime;
    public int mTabIndex;
    public final SeslTimePicker mTimePicker;
    public final View mTimePickerDialog;
    public final ZenModeScheduleRuleSettings.AnonymousClass4 timePickerChangeListener;

    public CustomTimePickerDialog(
            Context context,
            int i,
            int i2,
            ZenModeScheduleRuleSettings.AnonymousClass4 anonymousClass4) {
        super(context, R.style.TabCustomTimePickerDialog);
        this.mTimePickerDialog = null;
        this.mContext = context;
        this.mStartTime = i;
        this.mEndTime = i2;
        this.mTabIndex = 0;
        this.timePickerChangeListener = anonymousClass4;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_tab_custom_time_picker_dialog, (ViewGroup) null);
        this.mTimePickerDialog = inflate;
        setView$1(inflate);
        SeslTimePicker seslTimePicker =
                (SeslTimePicker) this.mTimePickerDialog.findViewById(R.id.tab_custom_time_picker);
        this.mTimePicker = seslTimePicker;
        SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = seslTimePicker.mDelegate;
        seslTimePickerSpinnerDelegate.mOnEditTextModeChangedListener = this;
        seslTimePickerSpinnerDelegate.mOnTimeChangedListener = this;
        TabLayout tabLayout =
                (TabLayout) this.mTimePickerDialog.findViewById(R.id.tab_custom_time_picker_tab);
        tabLayout.seslSetSubTabStyle();
        TabLayout.Tab tabAt = tabLayout.getTabAt(this.mTabIndex);
        if (tabAt != null) {
            tabAt.select();
        }
        tabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.datetime.CustomTimePickerDialog.1
                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        CustomTimePickerDialog customTimePickerDialog = CustomTimePickerDialog.this;
                        customTimePickerDialog.mTimePicker.mDelegate.setEditTextMode(false);
                        int i3 = tab.position;
                        customTimePickerDialog.mTabIndex = i3;
                        if (i3 == 0) {
                            customTimePickerDialog.updatePicker(customTimePickerDialog.mStartTime);
                        } else if (i3 == 1) {
                            customTimePickerDialog.updatePicker(customTimePickerDialog.mEndTime);
                        }
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        final int i3 = 0;
        setButton(
                -1,
                context.getResources().getString(R.string.done),
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.datetime.CustomTimePickerDialog$$ExternalSyntheticLambda0
                    public final /* synthetic */ CustomTimePickerDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        int i5 = i3;
                        CustomTimePickerDialog customTimePickerDialog = this.f$0;
                        switch (i5) {
                            case 0:
                                StringBuilder sb = new StringBuilder("save Time : start time = ");
                                sb.append(customTimePickerDialog.mStartTime);
                                sb.append(", end time = ");
                                TooltipPopup$$ExternalSyntheticOutline0.m(
                                        sb,
                                        customTimePickerDialog.mEndTime,
                                        "CustomTimePickerDialog");
                                customTimePickerDialog.mTimePicker.clearFocus();
                                ZenModeScheduleRuleSettings.AnonymousClass4 anonymousClass42 =
                                        customTimePickerDialog.timePickerChangeListener;
                                int i6 = customTimePickerDialog.mStartTime;
                                int i7 = customTimePickerDialog.mEndTime;
                                ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                        ZenModeScheduleRuleSettings.this;
                                if (zenModeScheduleRuleSettings.mSecScheduleTimeType == 1) {
                                    ZenModeConfig.ScheduleInfo scheduleInfo =
                                            zenModeScheduleRuleSettings.mSchedule;
                                    scheduleInfo.startHour = i6 / 60;
                                    scheduleInfo.startMinute = i6 % 60;
                                    scheduleInfo.endHour = i7 / 60;
                                    scheduleInfo.endMinute = i7 % 60;
                                }
                                zenModeScheduleRuleSettings.updateScheduleRule(
                                        zenModeScheduleRuleSettings.mSchedule);
                                SecPreference secPreference =
                                        zenModeScheduleRuleSettings.mSecScheduleStart;
                                if (secPreference != null) {
                                    secPreference.setSummary(
                                            zenModeScheduleRuleSettings
                                                    .updateSecScheduleStartSummary());
                                    break;
                                }
                                break;
                            default:
                                customTimePickerDialog.mTimePicker.clearFocus();
                                break;
                        }
                    }
                });
        final int i4 = 1;
        setButton(
                -2,
                context.getResources().getString(R.string.sec_dnd_schedule_action_cancel),
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.datetime.CustomTimePickerDialog$$ExternalSyntheticLambda0
                    public final /* synthetic */ CustomTimePickerDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i42) {
                        int i5 = i4;
                        CustomTimePickerDialog customTimePickerDialog = this.f$0;
                        switch (i5) {
                            case 0:
                                StringBuilder sb = new StringBuilder("save Time : start time = ");
                                sb.append(customTimePickerDialog.mStartTime);
                                sb.append(", end time = ");
                                TooltipPopup$$ExternalSyntheticOutline0.m(
                                        sb,
                                        customTimePickerDialog.mEndTime,
                                        "CustomTimePickerDialog");
                                customTimePickerDialog.mTimePicker.clearFocus();
                                ZenModeScheduleRuleSettings.AnonymousClass4 anonymousClass42 =
                                        customTimePickerDialog.timePickerChangeListener;
                                int i6 = customTimePickerDialog.mStartTime;
                                int i7 = customTimePickerDialog.mEndTime;
                                ZenModeScheduleRuleSettings zenModeScheduleRuleSettings =
                                        ZenModeScheduleRuleSettings.this;
                                if (zenModeScheduleRuleSettings.mSecScheduleTimeType == 1) {
                                    ZenModeConfig.ScheduleInfo scheduleInfo =
                                            zenModeScheduleRuleSettings.mSchedule;
                                    scheduleInfo.startHour = i6 / 60;
                                    scheduleInfo.startMinute = i6 % 60;
                                    scheduleInfo.endHour = i7 / 60;
                                    scheduleInfo.endMinute = i7 % 60;
                                }
                                zenModeScheduleRuleSettings.updateScheduleRule(
                                        zenModeScheduleRuleSettings.mSchedule);
                                SecPreference secPreference =
                                        zenModeScheduleRuleSettings.mSecScheduleStart;
                                if (secPreference != null) {
                                    secPreference.setSummary(
                                            zenModeScheduleRuleSettings
                                                    .updateSecScheduleStartSummary());
                                    break;
                                }
                                break;
                            default:
                                customTimePickerDialog.mTimePicker.clearFocus();
                                break;
                        }
                    }
                });
        onUpdateHourFormat();
        updateTime(0, this.mStartTime);
        updateTime(1, this.mEndTime);
        updatePicker(this.mTabIndex == 0 ? this.mStartTime : this.mEndTime);
    }

    @Override // androidx.picker.widget.SeslTimePicker.OnTimeChangedListener
    public final void onTimeChanged(int i, int i2) {
        updateTime(this.mTabIndex, (i * 60) + i2);
    }

    public final void onUpdateHourFormat() {
        boolean equals;
        Context context = this.mContext;
        String string = Settings.System.getString(context.getContentResolver(), "time_12_24");
        if (string == null) {
            Locale locale = context.getResources().getConfiguration().getLocales().get(0);
            if (locale == null) {
                locale = Locale.getDefault();
            }
            DateFormat timeInstance = DateFormat.getTimeInstance(1, locale);
            boolean z = timeInstance instanceof SimpleDateFormat;
            String str = DATA.DM_FIELD_INDEX.SIP_T1_TIMER;
            if (z && ((SimpleDateFormat) timeInstance).toPattern().indexOf(72) >= 0) {
                str = DATA.DM_FIELD_INDEX.SIP_TJ_TIMER;
            }
            equals = DATA.DM_FIELD_INDEX.SIP_TJ_TIMER.equals(str);
        } else {
            equals = DATA.DM_FIELD_INDEX.SIP_TJ_TIMER.equals(string);
        }
        this.mIs24HourFormat = equals;
        this.mTimePicker.setIs24HourView(Boolean.valueOf(equals));
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            onUpdateHourFormat();
            View view = this.mTimePickerDialog;
            if (view != null) {
                view.requestLayout();
            }
        }
    }

    public final void updatePicker(int i) {
        this.mTimePicker.setHour(i / 60);
        this.mTimePicker.setMinute(i % 60);
    }

    public final void updateTime(int i, int i2) {
        int i3 = i2 / 60;
        int i4 = i2 % 60;
        boolean z = this.mIs24HourFormat;
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(z ? 11 : 10, i3);
        calendar.set(12, i4);
        TabLayout tabLayout =
                (TabLayout) this.mTimePickerDialog.findViewById(R.id.tab_custom_time_picker_tab);
        if (i == 0) {
            this.mStartTime = i2;
            String timeText = CustomTimePickerUtils.getTimeText(this.mContext, calendar);
            if (!this.mIs24HourFormat) {
                StringBuilder m =
                        PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                                timeText, " ");
                m.append(new SimpleDateFormat("a").format(new Date(calendar.getTimeInMillis())));
                timeText = m.toString();
            }
            TabLayout.Tab tabAt = tabLayout.getTabAt(0);
            tabAt.subText = timeText;
            tabAt.updateView();
            return;
        }
        this.mEndTime = i2;
        String timeText2 = CustomTimePickerUtils.getTimeText(this.mContext, calendar);
        if (!this.mIs24HourFormat) {
            StringBuilder m2 =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(
                            timeText2, " ");
            m2.append(new SimpleDateFormat("a").format(new Date(calendar.getTimeInMillis())));
            timeText2 = m2.toString();
        }
        TabLayout.Tab tabAt2 = tabLayout.getTabAt(1);
        tabAt2.subText = timeText2;
        tabAt2.updateView();
    }
}
