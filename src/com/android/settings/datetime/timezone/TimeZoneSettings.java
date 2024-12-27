package com.android.settings.datetime.timezone;

import android.app.timezonedetector.TimeZoneDetector;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.collection.ArraySet;
import androidx.preference.SecPreferenceCategory;

import com.android.i18n.timezone.CountryTimeZones;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.datetime.timezone.model.FilteredCountryTimeZones;
import com.android.settings.datetime.timezone.model.TimeZoneData;
import com.android.settings.datetime.timezone.model.TimeZoneDataLoader;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;

import com.samsung.android.settings.analyzestorage.data.constant.FileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeZoneSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.time_zone_prefs);
    public boolean mIsHomeCity;
    public Locale mLocale;
    public Intent mPendingZonePickerRequestResult;
    public boolean mSelectByRegion;
    public String mSelectedTimeZoneId;
    public TimeZoneData mTimeZoneData;
    public TimeZoneInfo.Formatter mTimeZoneInfoFormatter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.datetime.timezone.TimeZoneSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final boolean isPageSearchEnabled(Context context) {
            return Settings.Global.getInt(context.getContentResolver(), "auto_time_zone", 1) != 1;
        }
    }

    public static boolean isFixedOffset(String str) {
        return str.startsWith("Etc/GMT") || str.equals("Etc/UTC");
    }

    public static void setPreferenceCategoryVisible(
            SecPreferenceCategory secPreferenceCategory, boolean z) {
        secPreferenceCategory.setVisible(z);
        secPreferenceCategory.setEmptyHeight();
        for (int i = 0; i < secPreferenceCategory.getPreferenceCount(); i++) {
            secPreferenceCategory.getPreference(i).setVisible(z);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        this.mLocale = locale;
        this.mTimeZoneInfoFormatter = new TimeZoneInfo.Formatter(locale, new Date());
        ArrayList arrayList = new ArrayList();
        RegionPreferenceController regionPreferenceController =
                new RegionPreferenceController(context);
        regionPreferenceController.setOnClickListener(
                new TimeZoneSettings$$ExternalSyntheticLambda0(this, 0));
        RegionZonePreferenceController regionZonePreferenceController =
                new RegionZonePreferenceController(context);
        regionZonePreferenceController.setOnClickListener(
                new TimeZoneSettings$$ExternalSyntheticLambda0(this, 1));
        FixedOffsetPreferenceController fixedOffsetPreferenceController =
                new FixedOffsetPreferenceController(context);
        fixedOffsetPreferenceController.setOnClickListener(
                new TimeZoneSettings$$ExternalSyntheticLambda0(this, 2));
        arrayList.add(regionPreferenceController);
        arrayList.add(regionZonePreferenceController);
        arrayList.add(fixedOffsetPreferenceController);
        return arrayList;
    }

    public String findRegionIdForTzId(String str, String str2, String str3) {
        Set set;
        TimeZoneData timeZoneData = this.mTimeZoneData;
        if (str == null) {
            timeZoneData.getClass();
            set = Collections.emptySet();
        } else {
            List<CountryTimeZones> lookupCountryTimeZonesForZoneId =
                    timeZoneData.mCountryZonesFinder.lookupCountryTimeZonesForZoneId(str);
            ArraySet arraySet = new ArraySet(0);
            for (CountryTimeZones countryTimeZones : lookupCountryTimeZonesForZoneId) {
                String readCountryCode = Utils.readCountryCode();
                ArrayList arrayList = new ArrayList();
                if ("HK".equalsIgnoreCase(readCountryCode)
                        || "CN".equalsIgnoreCase(readCountryCode)) {
                    arrayList.add("Asia/Urumqi");
                }
                int size = arrayList.size();
                String[] strArr =
                        size > 0 ? (String[]) arrayList.toArray(new String[size]) : new String[0];
                ArrayList arrayList2 = new ArrayList();
                android.util.ArraySet arraySet2 = new android.util.ArraySet();
                for (CountryTimeZones.TimeZoneMapping timeZoneMapping :
                        countryTimeZones.getTimeZoneMappings()) {
                    if (timeZoneMapping.isShownInPickerAt(TimeUtils.MIN_USE_DATE_OF_TIMEZONE)) {
                        arrayList2.add(timeZoneMapping.getTimeZoneId());
                        arraySet2.addAll(timeZoneMapping.getAlternativeIds());
                    }
                }
                if (strArr != null && strArr.length > 0) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        String str4 = (String) it.next();
                        for (String str5 : strArr) {
                            if (str4.equalsIgnoreCase(str5)) {
                                it.remove();
                            }
                        }
                    }
                }
                List unmodifiableList = Collections.unmodifiableList(arrayList2);
                Set unmodifiableSet = Collections.unmodifiableSet(arraySet2);
                if (unmodifiableList.contains(str) || unmodifiableSet.contains(str)) {
                    String countryIso = countryTimeZones.getCountryIso();
                    arraySet.add(countryIso == null ? null : countryIso.toUpperCase(Locale.US));
                }
            }
            set = arraySet;
        }
        if (set.size() == 0) {
            return null;
        }
        return (str2 == null || !set.contains(str2))
                ? (str3 == null || !set.contains(str3))
                        ? ((String[]) set.toArray(new String[set.size()]))[0]
                        : str3
                : str2;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "TimeZoneSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public int getMetricsCategory() {
        return FileType.SCC;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public int getPreferenceScreenResId() {
        return R.xml.time_zone_prefs;
    }

    @Override // com.android.settings.dashboard.DashboardFragment, androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        String stringExtra;
        if (i2 != -1 || intent == null) {
            return;
        }
        if (i == 1 || i == 2) {
            TimeZoneData timeZoneData = this.mTimeZoneData;
            if (timeZoneData == null) {
                this.mPendingZonePickerRequestResult = intent;
                return;
            } else {
                onZonePickerRequestResult(timeZoneData, intent);
                return;
            }
        }
        if (i != 3
                || (stringExtra =
                                intent.getStringExtra(
                                        "com.android.settings.datetime.timezone.result_time_zone_id"))
                        == null
                || stringExtra.equals(this.mSelectedTimeZoneId)) {
            return;
        }
        this.mSelectedTimeZoneId = stringExtra;
        setDisplayedFixedOffsetTimeZoneInfo(stringExtra);
        saveTimeZone(null, this.mSelectedTimeZoneId);
        setSelectByRegion(false);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setPreferenceCategoryVisible(
                (SecPreferenceCategory) findPreference("time_zone_region_preference_category"),
                false);
        setPreferenceCategoryVisible(
                (SecPreferenceCategory)
                        findPreference("time_zone_fixed_offset_preference_category"),
                false);
        getLoaderManager()
                .initLoader(
                        0,
                        null,
                        new TimeZoneDataLoader.LoaderCreator(
                                getContext(),
                                new TimeZoneSettings$$ExternalSyntheticLambda0(this, 3)));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menu.add(0, 1, 0, R.string.zone_menu_by_region);
        menu.add(0, 2, 0, R.string.zone_menu_by_offset);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 1) {
            startPickerFragment(RegionSearchPicker.class, new Bundle(), 1);
            return true;
        }
        if (itemId != 2) {
            return false;
        }
        startPickerFragment(FixedOffsetPicker.class, new Bundle(), 3);
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        if (this.mIsHomeCity) {
            menu.findItem(1).setVisible(false);
            menu.findItem(2).setVisible(false);
            return;
        }
        menu.findItem(1)
                .setVisible((this.mTimeZoneData == null || this.mSelectByRegion) ? false : true);
        MenuItem findItem = menu.findItem(2);
        if (this.mTimeZoneData != null && this.mSelectByRegion) {
            z = true;
        }
        findItem.setVisible(z);
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (getListView() != null) {
            getListView().mDrawLastRoundedCorner = false;
        }
    }

    public final void onZonePickerRequestResult(TimeZoneData timeZoneData, Intent intent) {
        String stringExtra =
                intent.getStringExtra("com.android.settings.datetime.timezone.result_region_id");
        String stringExtra2 =
                intent.getStringExtra("com.android.settings.datetime.timezone.result_time_zone_id");
        if (Objects.equals(
                        stringExtra,
                        ((RegionPreferenceController) use(RegionPreferenceController.class))
                                .getRegionId())
                && Objects.equals(stringExtra2, this.mSelectedTimeZoneId)) {
            return;
        }
        FilteredCountryTimeZones lookupCountryTimeZones =
                timeZoneData.lookupCountryTimeZones(stringExtra);
        if (lookupCountryTimeZones == null
                || !lookupCountryTimeZones.mPreferredTimeZoneIds.contains(stringExtra2)) {
            Log.e("TimeZoneSettings", "Unknown time zone id is selected: " + stringExtra2);
            return;
        }
        this.mSelectedTimeZoneId = stringExtra2;
        ((RegionPreferenceController) use(RegionPreferenceController.class))
                .setRegionId(stringExtra);
        updatePreferenceStates();
        setDisplayedTimeZoneInfo(stringExtra, this.mSelectedTimeZoneId);
        saveTimeZone(stringExtra, this.mSelectedTimeZoneId);
        setSelectByRegion(true);
    }

    public final void saveTimeZone(String str, String str2) {
        if (this.mIsHomeCity) {
            Settings.System.putString(getContentResolver(), "homecity_timezone", str2);
            return;
        }
        SharedPreferences.Editor edit = getPreferenceManager().getSharedPreferences().edit();
        if (str == null) {
            edit.remove("time_zone_region");
        } else {
            edit.putString("time_zone_region", str);
        }
        edit.apply();
        ((TimeZoneDetector) getActivity().getSystemService(TimeZoneDetector.class))
                .suggestManualTimeZone(
                        TimeZoneDetector.createManualTimeZoneSuggestion(
                                str2, "Settings: Set time zone"));
    }

    public final void setDisplayedFixedOffsetTimeZoneInfo(String str) {
        if (isFixedOffset(str)) {
            FixedOffsetPreferenceController fixedOffsetPreferenceController =
                    (FixedOffsetPreferenceController) use(FixedOffsetPreferenceController.class);
            TimeZoneInfo.Formatter formatter = this.mTimeZoneInfoFormatter;
            formatter.getClass();
            fixedOffsetPreferenceController.setTimeZoneInfo(
                    formatter.format(TimeZone.getFrozenTimeZone(str)));
        } else {
            ((FixedOffsetPreferenceController) use(FixedOffsetPreferenceController.class))
                    .setTimeZoneInfo(null);
        }
        updatePreferenceStates();
    }

    public final void setDisplayedTimeZoneInfo(String str, String str2) {
        TimeZoneInfo format;
        if (str2 == null) {
            format = null;
        } else {
            TimeZoneInfo.Formatter formatter = this.mTimeZoneInfoFormatter;
            formatter.getClass();
            format = formatter.format(TimeZone.getFrozenTimeZone(str2));
        }
        FilteredCountryTimeZones lookupCountryTimeZones =
                this.mTimeZoneData.lookupCountryTimeZones(str);
        ((RegionZonePreferenceController) use(RegionZonePreferenceController.class))
                .setTimeZoneInfo(format);
        RegionZonePreferenceController regionZonePreferenceController =
                (RegionZonePreferenceController) use(RegionZonePreferenceController.class);
        boolean z = true;
        if (format != null
                && (lookupCountryTimeZones == null
                        || lookupCountryTimeZones.mPreferredTimeZoneIds.size() <= 1)) {
            z = false;
        }
        regionZonePreferenceController.setClickable(z);
        ((TimeZoneInfoPreferenceController) use(TimeZoneInfoPreferenceController.class))
                .setTimeZoneInfo(format);
        updatePreferenceStates();
    }

    public final void setSelectByRegion(boolean z) {
        this.mSelectByRegion = z;
        if (!this.mIsHomeCity) {
            setPreferenceCategoryVisible(
                    (SecPreferenceCategory) findPreference("time_zone_region_preference_category"),
                    z);
            setPreferenceCategoryVisible(
                    (SecPreferenceCategory)
                            findPreference("time_zone_fixed_offset_preference_category"),
                    !z);
        }
        String country = this.mLocale.getCountry();
        Locale locale = Locale.US;
        String upperCase = country.toUpperCase(locale);
        if (upperCase.equals("ZG")) {
            upperCase = "MM";
        }
        if (upperCase.equals("DI")) {
            upperCase = "EN";
        }
        if (!this.mTimeZoneData.mRegionIds.contains(upperCase)) {
            upperCase = null;
        }
        ((RegionPreferenceController) use(RegionPreferenceController.class)).setRegionId(upperCase);
        updatePreferenceStates();
        setDisplayedTimeZoneInfo(upperCase, null);
        if (!this.mSelectByRegion) {
            setDisplayedFixedOffsetTimeZoneInfo(this.mSelectedTimeZoneId);
            return;
        }
        String findRegionIdForTzId =
                findRegionIdForTzId(
                        this.mSelectedTimeZoneId,
                        getPreferenceManager()
                                .getSharedPreferences()
                                .getString("time_zone_region", null),
                        this.mLocale.getCountry().toUpperCase(locale));
        if (findRegionIdForTzId != null) {
            ((RegionPreferenceController) use(RegionPreferenceController.class))
                    .setRegionId(findRegionIdForTzId);
            updatePreferenceStates();
            setDisplayedTimeZoneInfo(findRegionIdForTzId, this.mSelectedTimeZoneId);
        } else if (this.mIsHomeCity) {
            startPickerFragment(RegionSearchPicker.class, new Bundle(), 1);
        }
    }

    public void setTimeZoneData(TimeZoneData timeZoneData) {
        this.mTimeZoneData = timeZoneData;
    }

    public final void startPickerFragment(Class cls, Bundle bundle, int i) {
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(getContext());
        String canonicalName = cls.getCanonicalName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = canonicalName;
        launchRequest.mArguments = bundle;
        launchRequest.mSourceMetricsCategory = getMetricsCategory();
        subSettingLauncher.setResultListener(this, i);
        subSettingLauncher.launch();
    }
}
