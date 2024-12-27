package com.android.settings.datetime.timezone.model;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.i18n.timezone.CountryZonesFinder;

import com.samsung.android.settings.Rune;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class TimeZoneData {
    public static WeakReference sCache;
    public final CountryZonesFinder mCountryZonesFinder;
    public final Set mRegionIds;

    public TimeZoneData(CountryZonesFinder countryZonesFinder) {
        this.mCountryZonesFinder = countryZonesFinder;
        List<String> lookupAllCountryIsoCodes = countryZonesFinder.lookupAllCountryIsoCodes();
        HashSet hashSet = new HashSet(lookupAllCountryIsoCodes.size());
        for (String str : lookupAllCountryIsoCodes) {
            if (!Rune.FEATURE_REMOVE_TZ_WESTERN_SAHARA_IN_MOROCCO || !str.equalsIgnoreCase("EH")) {
                hashSet.add(str == null ? null : str.toUpperCase(Locale.US));
            }
        }
        this.mRegionIds = Collections.unmodifiableSet(hashSet);
    }

    public final FilteredCountryTimeZones lookupCountryTimeZones(String str) {
        CountryTimeZones lookupCountryTimeZones =
                str == null ? null : this.mCountryZonesFinder.lookupCountryTimeZones(str);
        if (lookupCountryTimeZones == null) {
            return null;
        }
        return new FilteredCountryTimeZones(lookupCountryTimeZones);
    }
}
