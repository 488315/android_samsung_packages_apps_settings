package com.samsung.android.settings.display.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.picker.widget.SeslTimePicker;
import androidx.picker.widget.SeslTimePickerSpinnerDelegate;

import com.android.settings.R;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.google.android.material.tabs.TabLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.display.SecDarkModeSettingsFragment;
import com.samsung.android.settings.logging.LoggingHelper;
import com.sec.ims.configuration.DATA;
import com.sec.ims.settings.ImsProfile;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecDarkModeTimePickerDialog extends AlertDialog
        implements SeslTimePicker.OnTimeChangedListener,
                SeslTimePicker.OnEditTextModeChangedListener {
    public final Context mContext;
    public boolean mIs24HourFormat;
    public final SecStartEndTabLayout mTabLayout;
    public final SeslTimePicker mTimePicker;
    public List mTimePickerChangeListeners;
    public final View mTimePickerDialog;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.display.widget.SecDarkModeTimePickerDialog$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TimePickerChangeListener {}

    public SecDarkModeTimePickerDialog(Context context, int i, int i2) {
        super(context, R.style.SecDarkModeTimePickerDialog);
        this.mTimePickerDialog = null;
        this.mContext = context;
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.sec_dark_mode_time_picker_dialog, (ViewGroup) null);
        this.mTimePickerDialog = inflate;
        setView$1(inflate);
        SeslTimePicker seslTimePicker =
                (SeslTimePicker)
                        this.mTimePickerDialog.findViewById(R.id.sec_dark_mode_time_picker);
        this.mTimePicker = seslTimePicker;
        SeslTimePickerSpinnerDelegate seslTimePickerSpinnerDelegate = seslTimePicker.mDelegate;
        seslTimePickerSpinnerDelegate.mOnEditTextModeChangedListener = this;
        seslTimePickerSpinnerDelegate.mOnTimeChangedListener = this;
        this.mTabLayout =
                (SecStartEndTabLayout)
                        this.mTimePickerDialog.findViewById(R.id.sec_dark_mode_time_picker_tab);
        final int i3 = 0;
        setButton(
                -1,
                context.getResources().getString(R.string.sec_eye_comfort_time_picker_dialog_done),
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.widget.SecDarkModeTimePickerDialog$$ExternalSyntheticLambda1
                    public final /* synthetic */ SecDarkModeTimePickerDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i4) {
                        int i5 = i3;
                        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog = this.f$0;
                        switch (i5) {
                            case 0:
                                secDarkModeTimePickerDialog.mTimePicker.clearFocus();
                                int[] iArr = secDarkModeTimePickerDialog.mTabLayout.mTimes;
                                int i6 = iArr[0];
                                int i7 = iArr[1];
                                DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                                        "save Time : start time = ",
                                        ", end time = ",
                                        i6,
                                        i7,
                                        "SecDarkModeTimePickerDialog");
                                Settings.System.putLong(
                                        secDarkModeTimePickerDialog.mContext.getContentResolver(),
                                        "display_night_theme_on_time",
                                        i6);
                                Settings.System.putLong(
                                        secDarkModeTimePickerDialog.mContext.getContentResolver(),
                                        "display_night_theme_off_time",
                                        i7);
                                secDarkModeTimePickerDialog.onTimeSet(0, i6 / 60, i6 % 60);
                                secDarkModeTimePickerDialog.onTimeSet(1, i7 / 60, i7 % 60);
                                break;
                            default:
                                secDarkModeTimePickerDialog.mTimePicker.clearFocus();
                                break;
                        }
                    }
                });
        final int i4 = 1;
        setButton(
                -2,
                context.getResources().getString(R.string.schedule_delete_dialog_cancel),
                new DialogInterface.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.display.widget.SecDarkModeTimePickerDialog$$ExternalSyntheticLambda1
                    public final /* synthetic */ SecDarkModeTimePickerDialog f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i42) {
                        int i5 = i4;
                        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog = this.f$0;
                        switch (i5) {
                            case 0:
                                secDarkModeTimePickerDialog.mTimePicker.clearFocus();
                                int[] iArr = secDarkModeTimePickerDialog.mTabLayout.mTimes;
                                int i6 = iArr[0];
                                int i7 = iArr[1];
                                DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                                        "save Time : start time = ",
                                        ", end time = ",
                                        i6,
                                        i7,
                                        "SecDarkModeTimePickerDialog");
                                Settings.System.putLong(
                                        secDarkModeTimePickerDialog.mContext.getContentResolver(),
                                        "display_night_theme_on_time",
                                        i6);
                                Settings.System.putLong(
                                        secDarkModeTimePickerDialog.mContext.getContentResolver(),
                                        "display_night_theme_off_time",
                                        i7);
                                secDarkModeTimePickerDialog.onTimeSet(0, i6 / 60, i6 % 60);
                                secDarkModeTimePickerDialog.onTimeSet(1, i7 / 60, i7 % 60);
                                break;
                            default:
                                secDarkModeTimePickerDialog.mTimePicker.clearFocus();
                                break;
                        }
                    }
                });
        onUpdateHourFormat$1();
        final SecStartEndTabLayout secStartEndTabLayout = this.mTabLayout;
        final AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        Function function =
                new Function() { // from class:
                                 // com.samsung.android.settings.display.widget.SecDarkModeTimePickerDialog$$ExternalSyntheticLambda0
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        int intValue = ((Integer) obj).intValue();
                        int i5 = intValue / 60;
                        int i6 = intValue % 60;
                        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog =
                                SecDarkModeTimePickerDialog.this;
                        boolean z = secDarkModeTimePickerDialog.mIs24HourFormat;
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(z ? 11 : 10, i5);
                        calendar.set(12, i6);
                        Context context2 = secDarkModeTimePickerDialog.getContext();
                        boolean z2 = secDarkModeTimePickerDialog.mIs24HourFormat;
                        String pattern =
                                ((SimpleDateFormat) DateFormat.getTimeFormat(context2)).toPattern();
                        if (z2) {
                            pattern =
                                    pattern.replace("a", ApnSettings.MVNO_NONE)
                                            .replace("h", ImsProfile.TIMER_NAME_H)
                                            .trim();
                        }
                        Locale locale =
                                context2.getResources().getConfiguration().getLocales().get(0);
                        if (locale == null) {
                            locale = Locale.getDefault();
                        }
                        return new SimpleDateFormat(pattern, locale)
                                .format(new Date(calendar.getTimeInMillis()));
                    }
                };
        secStartEndTabLayout.getClass();
        secStartEndTabLayout.addOnTabSelectedListener$1(
                new TabLayout
                        .OnTabSelectedListener() { // from class:
                                                   // com.samsung.android.settings.display.widget.SecStartEndTabLayout.1
                    public final /* synthetic */ SecDarkModeTimePickerDialog.AnonymousClass1
                            val$listener;

                    public AnonymousClass1(
                            final SecDarkModeTimePickerDialog.AnonymousClass1 anonymousClass12) {
                        r2 = anonymousClass12;
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabSelected(TabLayout.Tab tab) {
                        SecDarkModeTimePickerDialog.AnonymousClass1 anonymousClass12 = r2;
                        SecDarkModeTimePickerDialog.this.mTimePicker.mDelegate.setEditTextMode(
                                false);
                        int i5 = tab.position;
                        SecStartEndTabLayout secStartEndTabLayout2 = SecStartEndTabLayout.this;
                        secStartEndTabLayout2.mTabIndex = i5;
                        int i6 = secStartEndTabLayout2.mTimes[i5];
                        SecDarkModeTimePickerDialog secDarkModeTimePickerDialog =
                                SecDarkModeTimePickerDialog.this;
                        secDarkModeTimePickerDialog.mTimePicker.setHour(i6 / 60);
                        secDarkModeTimePickerDialog.mTimePicker.setMinute(i6 % 60);
                    }

                    @Override // com.google.android.material.tabs.TabLayout.OnTabSelectedListener
                    public final void onTabUnselected(TabLayout.Tab tab) {}
                });
        int[] iArr = secStartEndTabLayout.mTimes;
        iArr[0] = i;
        iArr[1] = i2;
        secStartEndTabLayout.mTimeFormatter = function;
        IntStream.range(0, iArr.length)
                .forEach(new SecStartEndTabLayout$$ExternalSyntheticLambda1(secStartEndTabLayout));
        TabLayout.Tab tabAt = secStartEndTabLayout.getTabAt(secStartEndTabLayout.mTabIndex);
        Objects.requireNonNull(tabAt);
        TabLayout tabLayout = tabAt.parent;
        if (tabLayout == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        int selectedTabPosition = tabLayout.getSelectedTabPosition();
        if (selectedTabPosition == -1 || selectedTabPosition != tabAt.position) {
            tabAt.select();
        } else {
            int i5 = secStartEndTabLayout.mTimes[secStartEndTabLayout.mTabIndex];
            this.mTimePicker.setHour(i5 / 60);
            this.mTimePicker.setMinute(i5 % 60);
        }
        TabLayout.Tab tabAt2 = this.mTabLayout.getTabAt(0);
        Objects.requireNonNull(tabAt2);
        tabAt2.select();
    }

    @Override // androidx.picker.widget.SeslTimePicker.OnTimeChangedListener
    public final void onTimeChanged(int i, int i2) {
        SecStartEndTabLayout secStartEndTabLayout = this.mTabLayout;
        secStartEndTabLayout.updateTime(secStartEndTabLayout.mTabIndex, (i * 60) + i2);
    }

    public final void onTimeSet(int i, int i2, int i3) {
        List list = this.mTimePickerChangeListeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i4 = 0; i4 < size; i4++) {
                TimePickerChangeListener timePickerChangeListener =
                        (TimePickerChangeListener)
                                ((ArrayList) this.mTimePickerChangeListeners).get(i4);
                if (timePickerChangeListener != null) {
                    SecDarkModeSettingsFragment secDarkModeSettingsFragment =
                            (SecDarkModeSettingsFragment) timePickerChangeListener;
                    if (secDarkModeSettingsFragment.getActivity() != null) {
                        LocalTime of = LocalTime.of(i2, i3);
                        if (i == 0) {
                            secDarkModeSettingsFragment.mUiModeManager.setCustomNightModeStart(of);
                            Settings.System.putLong(
                                    secDarkModeSettingsFragment.getContentResolver(),
                                    "display_night_theme_on_time",
                                    of.getMinute() + (of.getHour() * 60));
                            LoggingHelper.insertEventLogging(
                                    7449, 7494, of.getMinute() + (of.getHour() * 60));
                        } else if (i == 1) {
                            secDarkModeSettingsFragment.mUiModeManager.setCustomNightModeEnd(of);
                            Settings.System.putLong(
                                    secDarkModeSettingsFragment.getContentResolver(),
                                    "display_night_theme_off_time",
                                    of.getMinute() + (of.getHour() * 60));
                            LoggingHelper.insertEventLogging(
                                    7449, 7495, of.getMinute() + (of.getHour() * 60));
                        }
                        secDarkModeSettingsFragment.updateDarkModeSchdeuledTimePreferences(true);
                    }
                }
            }
        }
    }

    public final void onUpdateHourFormat$1() {
        boolean equals;
        Context context = this.mContext;
        String string = Settings.System.getString(context.getContentResolver(), "time_12_24");
        if (string == null) {
            Locale locale = context.getResources().getConfiguration().getLocales().get(0);
            if (locale == null) {
                locale = Locale.getDefault();
            }
            java.text.DateFormat timeInstance = java.text.DateFormat.getTimeInstance(1, locale);
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
        SecStartEndTabLayout secStartEndTabLayout = this.mTabLayout;
        IntStream.range(0, secStartEndTabLayout.mTimes.length)
                .forEach(new SecStartEndTabLayout$$ExternalSyntheticLambda1(secStartEndTabLayout));
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            onUpdateHourFormat$1();
            View view = this.mTimePickerDialog;
            if (view != null) {
                view.requestLayout();
            }
        }
    }
}
