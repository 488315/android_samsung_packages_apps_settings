package com.android.settings.datetime.timezone;

import android.content.Intent;
import android.icu.text.Collator;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.datetime.timezone.model.FilteredCountryTimeZones;
import com.android.settings.datetime.timezone.model.TimeZoneData;
import com.android.settingslib.datetime.ZoneGetter;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RegionSearchPicker extends BaseTimeZonePicker {
    public TimeZoneData mTimeZoneData;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RegionInfoComparator implements Comparator {
        public final Collator mCollator;

        public RegionInfoComparator(Collator collator) {
            this.mCollator = collator;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return this.mCollator.compare(
                    (Object) ((RegionItem) obj).mName, (Object) ((RegionItem) obj2).mName);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RegionItem implements BaseTimeZoneAdapter.AdapterItem {
        public final String mId;
        public final long mItemId;
        public final String mName;
        public final String[] mSearchKeys;

        public RegionItem(long j, String str, String str2) {
            this.mId = str;
            this.mName = str2;
            this.mItemId = j;
            this.mSearchKeys = new String[] {str, str2};
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final String getCurrentTime() {
            return null;
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final long getItemId() {
            return this.mItemId;
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final String[] getSearchKeys() {
            return this.mSearchKeys;
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final CharSequence getSummary() {
            return null;
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final CharSequence getTitle() {
            return this.mName;
        }
    }

    public RegionSearchPicker() {
        super(R.string.date_time_select_region, R.string.date_time_search_region, true, true);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePicker
    public final BaseTimeZoneAdapter createAdapter(TimeZoneData timeZoneData) {
        this.mTimeZoneData = timeZoneData;
        Set<String> set = timeZoneData.mRegionIds;
        TreeSet treeSet = new TreeSet(new RegionInfoComparator(Collator.getInstance(getLocale())));
        LocaleDisplayNames localeDisplayNames = LocaleDisplayNames.getInstance(getLocale());
        long j = 0;
        for (String str : set) {
            String capitalizeForStandaloneDisplay =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            null, localeDisplayNames.regionDisplayName(str));
            if (Rune.isDisableIsraelCountry() && str.equalsIgnoreCase("IL")) {
                capitalizeForStandaloneDisplay = getContext().getString(R.string.jerusalem);
            } else if (str.equalsIgnoreCase("TR")) {
                capitalizeForStandaloneDisplay = getContext().getString(R.string.turkey);
            } else if (Rune.FEATURE_NATIONAL_NAME_CHINA && str.equalsIgnoreCase("CN")) {
                capitalizeForStandaloneDisplay = getContext().getString(R.string.china);
            }
            treeSet.add(new RegionItem(j, str, capitalizeForStandaloneDisplay));
            j = 1 + j;
        }
        return new BaseTimeZoneAdapter(
                new ArrayList(treeSet),
                new BaseTimeZonePicker.OnListItemClickListener() { // from class:
                    // com.android.settings.datetime.timezone.RegionSearchPicker$$ExternalSyntheticLambda0
                    @Override // com.android.settings.datetime.timezone.BaseTimeZonePicker.OnListItemClickListener
                    public final void onListItemClick(BaseTimeZoneAdapter.AdapterItem adapterItem) {
                        RegionSearchPicker regionSearchPicker = RegionSearchPicker.this;
                        regionSearchPicker.getClass();
                        String str2 = ((RegionSearchPicker.RegionItem) adapterItem).mId;
                        FilteredCountryTimeZones lookupCountryTimeZones =
                                regionSearchPicker.mTimeZoneData.lookupCountryTimeZones(str2);
                        FragmentActivity activity = regionSearchPicker.getActivity();
                        LoggingHelper.insertEventLogging(4753, 8017);
                        if (lookupCountryTimeZones == null
                                || lookupCountryTimeZones.mPreferredTimeZoneIds.isEmpty()) {
                            Log.e("RegionSearchPicker", "Region has no time zones: " + str2);
                            activity.setResult(0);
                            activity.finish();
                            return;
                        }
                        List list = lookupCountryTimeZones.mPreferredTimeZoneIds;
                        if (list.size() == 1) {
                            regionSearchPicker
                                    .getActivity()
                                    .setResult(
                                            -1,
                                            new Intent()
                                                    .putExtra(
                                                            "com.android.settings.datetime.timezone.result_region_id",
                                                            str2)
                                                    .putExtra(
                                                            "com.android.settings.datetime.timezone.result_time_zone_id",
                                                            (String) list.get(0)));
                            regionSearchPicker.getActivity().finish();
                            return;
                        }
                        Bundle m =
                                AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                        "com.android.settings.datetime.timezone.region_id", str2);
                        SubSettingLauncher subSettingLauncher =
                                new SubSettingLauncher(regionSearchPicker.getContext());
                        String canonicalName = RegionZonePicker.class.getCanonicalName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = canonicalName;
                        launchRequest.mArguments = m;
                        launchRequest.mSourceMetricsCategory = 1355;
                        subSettingLauncher.setResultListener(regionSearchPicker, 1);
                        subSettingLauncher.launch();
                    }
                },
                getLocale(),
                false,
                null);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1355;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 1) {
            if (i2 == -1) {
                getActivity().setResult(-1, intent);
            }
            getActivity().finish();
        }
    }
}
