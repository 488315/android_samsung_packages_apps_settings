package com.android.settings.display;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;

import com.android.settings.R;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NightDisplayTimeFormatter {
    public final DateFormat mTimeFormatter;

    public NightDisplayTimeFormatter(Context context) {
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(context);
        this.mTimeFormatter = timeFormat;
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public final String getAutoModeSummary(
            Context context, ColorDisplayManager colorDisplayManager) {
        boolean isNightDisplayActivated = colorDisplayManager.isNightDisplayActivated();
        int nightDisplayAutoMode = colorDisplayManager.getNightDisplayAutoMode();
        if (nightDisplayAutoMode == 1) {
            return isNightDisplayActivated
                    ? context.getString(
                            R.string.night_display_summary_on_auto_mode_custom,
                            getFormattedTimeString(
                                    colorDisplayManager.getNightDisplayCustomEndTime()))
                    : context.getString(
                            R.string.night_display_summary_off_auto_mode_custom,
                            getFormattedTimeString(
                                    colorDisplayManager.getNightDisplayCustomStartTime()));
        }
        if (nightDisplayAutoMode == 2) {
            return context.getString(
                    isNightDisplayActivated
                            ? R.string.night_display_summary_on_auto_mode_twilight
                            : R.string.night_display_summary_off_auto_mode_twilight);
        }
        return context.getString(
                isNightDisplayActivated
                        ? R.string.night_display_summary_on_auto_mode_never
                        : R.string.night_display_summary_off_auto_mode_never);
    }

    public final String getFormattedTimeString(LocalTime localTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(this.mTimeFormatter.getTimeZone());
        calendar.set(11, localTime.getHour());
        calendar.set(12, localTime.getMinute());
        calendar.set(13, 0);
        calendar.set(14, 0);
        return this.mTimeFormatter.format(calendar.getTime());
    }
}
