package com.android.settings.datetime.timezone.model;

import android.util.ArraySet;
import android.util.TimeUtils;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.settings.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FilteredCountryTimeZones {
    public final String[] mDisableTimeZoneListsArr;
    public final List mPreferredTimeZoneIds;

    public FilteredCountryTimeZones(CountryTimeZones countryTimeZones) {
        String readCountryCode = Utils.readCountryCode();
        ArrayList arrayList = new ArrayList();
        if ("HK".equalsIgnoreCase(readCountryCode) || "CN".equalsIgnoreCase(readCountryCode)) {
            arrayList.add("Asia/Urumqi");
        }
        int size = arrayList.size();
        this.mDisableTimeZoneListsArr =
                size > 0 ? (String[]) arrayList.toArray(new String[size]) : new String[0];
        ArrayList arrayList2 = new ArrayList();
        ArraySet arraySet = new ArraySet();
        for (CountryTimeZones.TimeZoneMapping timeZoneMapping :
                countryTimeZones.getTimeZoneMappings()) {
            if (timeZoneMapping.isShownInPickerAt(TimeUtils.MIN_USE_DATE_OF_TIMEZONE)) {
                arrayList2.add(timeZoneMapping.getTimeZoneId());
                arraySet.addAll(timeZoneMapping.getAlternativeIds());
            }
        }
        String[] strArr = this.mDisableTimeZoneListsArr;
        if (strArr != null && strArr.length > 0) {
            Iterator it = arrayList2.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                for (String str2 : this.mDisableTimeZoneListsArr) {
                    if (str.equalsIgnoreCase(str2)) {
                        it.remove();
                    }
                }
            }
        }
        this.mPreferredTimeZoneIds = Collections.unmodifiableList(arrayList2);
        Collections.unmodifiableSet(arraySet);
    }
}
