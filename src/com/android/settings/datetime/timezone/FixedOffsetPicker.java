package com.android.settings.datetime.timezone;

import android.icu.util.TimeZone;

import com.android.settings.R;
import com.android.settings.datetime.timezone.model.TimeZoneData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class FixedOffsetPicker extends BaseTimeZoneInfoPicker {
    public FixedOffsetPicker() {
        super(
                R.string.date_time_select_fixed_offset_time_zones,
                R.string.search_settings,
                false,
                false);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZoneInfoPicker
    public final List getAllTimeZoneInfos(TimeZoneData timeZoneData) {
        TimeZoneInfo.Formatter formatter = new TimeZoneInfo.Formatter(getLocale(), new Date());
        ArrayList arrayList = new ArrayList();
        arrayList.add(formatter.format(TimeZone.getFrozenTimeZone("Etc/UTC")));
        for (int i = 12; i >= -14; i--) {
            if (i != 0) {
                arrayList.add(
                        formatter.format(
                                TimeZone.getFrozenTimeZone(
                                        String.format(
                                                Locale.US, "Etc/GMT%+d", Integer.valueOf(i)))));
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1357;
    }
}
