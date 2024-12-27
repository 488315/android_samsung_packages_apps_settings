package com.android.settings.regionalpreferences;

import android.content.Context;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class RegionalPreferencesDataUtils {
    public static String dayConverter(Context context, String str) {
        str.getClass();
        switch (str) {
            case "fri":
                return context.getString(R.string.friday_first_day_of_week);
            case "mon":
                return context.getString(R.string.monday_first_day_of_week);
            case "sat":
                return context.getString(R.string.saturday_first_day_of_week);
            case "sun":
                return context.getString(R.string.sunday_first_day_of_week);
            case "thu":
                return context.getString(R.string.thursday_first_day_of_week);
            case "tue":
                return context.getString(R.string.tuesday_first_day_of_week);
            case "wed":
                return context.getString(R.string.wednesday_first_day_of_week);
            default:
                return context.getString(R.string.default_string_of_regional_preference);
        }
    }
}
