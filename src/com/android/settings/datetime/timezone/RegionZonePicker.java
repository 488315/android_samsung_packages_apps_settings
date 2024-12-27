package com.android.settings.datetime.timezone;

import android.content.Intent;
import android.icu.text.Collator;
import android.icu.text.LocaleDisplayNames;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;

import com.android.settings.R;
import com.android.settings.datetime.timezone.model.FilteredCountryTimeZones;
import com.android.settings.datetime.timezone.model.TimeZoneData;
import com.android.settingslib.datetime.ZoneGetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RegionZonePicker extends BaseTimeZoneInfoPicker {
    public String mRegionName;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class TimeZoneInfoComparator implements Comparator<TimeZoneInfo> {
        public final Collator mCollator;
        public final Date mNow;

        public TimeZoneInfoComparator(Collator collator, Date date) {
            this.mCollator = collator;
            this.mNow = date;
        }

        @Override // java.util.Comparator
        public final int compare(TimeZoneInfo timeZoneInfo, TimeZoneInfo timeZoneInfo2) {
            String str;
            String str2;
            TimeZoneInfo timeZoneInfo3 = timeZoneInfo;
            TimeZoneInfo timeZoneInfo4 = timeZoneInfo2;
            int compare =
                    Integer.compare(
                            timeZoneInfo3.mTimeZone.getOffset(this.mNow.getTime()),
                            timeZoneInfo4.mTimeZone.getOffset(this.mNow.getTime()));
            if (compare == 0) {
                compare =
                        Integer.compare(
                                timeZoneInfo3.mTimeZone.getRawOffset(),
                                timeZoneInfo4.mTimeZone.getRawOffset());
            }
            if (compare == 0) {
                compare =
                        this.mCollator.compare(
                                timeZoneInfo3.mExemplarLocation, timeZoneInfo4.mExemplarLocation);
            }
            return (compare != 0
                            || (str = timeZoneInfo3.mGenericName) == null
                            || (str2 = timeZoneInfo4.mGenericName) == null)
                    ? compare
                    : this.mCollator.compare(str, str2);
        }
    }

    public RegionZonePicker() {
        super(R.string.date_time_set_timezone_title, R.string.search_time_zones, true, false);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZoneInfoPicker
    public final List getAllTimeZoneInfos(TimeZoneData timeZoneData) {
        if (getArguments() == null) {
            Log.e("RegionZoneSearchPicker", "getArguments() == null");
            getActivity().finish();
            return Collections.emptyList();
        }
        String string =
                getArguments().getString("com.android.settings.datetime.timezone.region_id");
        FilteredCountryTimeZones lookupCountryTimeZones =
                timeZoneData.lookupCountryTimeZones(string);
        if (lookupCountryTimeZones == null) {
            Log.e("RegionZoneSearchPicker", "region id is not valid: " + string);
            getActivity().finish();
            return Collections.emptyList();
        }
        List list = lookupCountryTimeZones.mPreferredTimeZoneIds;
        TimeZoneInfo.Formatter formatter = new TimeZoneInfo.Formatter(getLocale(), new Date());
        TreeSet treeSet =
                new TreeSet(
                        new TimeZoneInfoComparator(Collator.getInstance(getLocale()), new Date()));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            TimeZone frozenTimeZone = TimeZone.getFrozenTimeZone((String) it.next());
            if (!frozenTimeZone.getID().equals("Etc/Unknown")) {
                treeSet.add(formatter.format(frozenTimeZone));
            }
        }
        return Collections.unmodifiableList(new ArrayList(treeSet));
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZoneInfoPicker
    public final CharSequence getHeaderText() {
        return this.mRegionName;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1356;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePicker,
              // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LocaleDisplayNames localeDisplayNames = LocaleDisplayNames.getInstance(getLocale());
        String string =
                getArguments() == null
                        ? null
                        : getArguments()
                                .getString("com.android.settings.datetime.timezone.region_id");
        this.mRegionName =
                string != null
                        ? ZoneGetter.capitalizeForStandaloneDisplay(
                                null, localeDisplayNames.regionDisplayName(string))
                        : null;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZoneInfoPicker
    public final Intent prepareResultData(TimeZoneInfo timeZoneInfo) {
        Intent prepareResultData = super.prepareResultData(timeZoneInfo);
        prepareResultData.putExtra(
                "com.android.settings.datetime.timezone.result_region_id",
                getArguments().getString("com.android.settings.datetime.timezone.region_id"));
        return prepareResultData;
    }
}
