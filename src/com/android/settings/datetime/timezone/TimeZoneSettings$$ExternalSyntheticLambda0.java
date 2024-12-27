package com.android.settings.datetime.timezone;

import android.content.Intent;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.datetime.timezone.model.TimeZoneData;
import com.android.settings.datetime.timezone.model.TimeZoneDataLoader;
import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class TimeZoneSettings$$ExternalSyntheticLambda0
        implements OnPreferenceClickListener, TimeZoneDataLoader.OnDataReadyCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TimeZoneSettings f$0;

    public /* synthetic */ TimeZoneSettings$$ExternalSyntheticLambda0(
            TimeZoneSettings timeZoneSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = timeZoneSettings;
    }

    @Override // com.android.settings.datetime.timezone.OnPreferenceClickListener
    public void onClick() {
        TimeZoneSettings timeZoneSettings = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                BaseSearchIndexProvider baseSearchIndexProvider =
                        TimeZoneSettings.SEARCH_INDEX_DATA_PROVIDER;
                timeZoneSettings.getClass();
                timeZoneSettings.startPickerFragment(RegionSearchPicker.class, new Bundle(), 1);
                break;
            case 1:
                BaseSearchIndexProvider baseSearchIndexProvider2 =
                        TimeZoneSettings.SEARCH_INDEX_DATA_PROVIDER;
                timeZoneSettings.getClass();
                Bundle bundle = new Bundle();
                bundle.putString(
                        "com.android.settings.datetime.timezone.region_id",
                        ((RegionPreferenceController)
                                        timeZoneSettings.use(RegionPreferenceController.class))
                                .getRegionId());
                timeZoneSettings.startPickerFragment(RegionZonePicker.class, bundle, 2);
                break;
            default:
                BaseSearchIndexProvider baseSearchIndexProvider3 =
                        TimeZoneSettings.SEARCH_INDEX_DATA_PROVIDER;
                timeZoneSettings.getClass();
                timeZoneSettings.startPickerFragment(FixedOffsetPicker.class, new Bundle(), 3);
                break;
        }
    }

    @Override // com.android.settings.datetime.timezone.model.TimeZoneDataLoader.OnDataReadyCallback
    public void onTimeZoneDataReady(TimeZoneData timeZoneData) {
        TimeZoneSettings timeZoneSettings = this.f$0;
        if (timeZoneSettings.mTimeZoneData != null || timeZoneData == null) {
            return;
        }
        timeZoneSettings.mTimeZoneData = timeZoneData;
        if (timeZoneSettings.mIsHomeCity) {
            String string =
                    Settings.System.getString(
                            timeZoneSettings.getContentResolver(), "homecity_timezone");
            if (!TextUtils.isEmpty(string) && !TimeZoneSettings.isFixedOffset(string)) {
                timeZoneSettings.mSelectedTimeZoneId = string;
            }
            Log.i(
                    "TimeZoneSettings",
                    "setupForCurrentTimeZone > HOMECITY_TIMEZONE : "
                            .concat(String.valueOf(timeZoneSettings.mSelectedTimeZoneId)));
            if (timeZoneSettings.mSelectedTimeZoneId == null) {
                timeZoneSettings.mSelectedTimeZoneId = TimeZone.getDefault().getID();
            }
            timeZoneSettings.setSelectByRegion(true);
        } else {
            timeZoneSettings.mSelectedTimeZoneId = TimeZone.getDefault().getID();
            timeZoneSettings.setSelectByRegion(!TimeZoneSettings.isFixedOffset(r0));
        }
        timeZoneSettings.getActivity().invalidateOptionsMenu();
        Intent intent = timeZoneSettings.mPendingZonePickerRequestResult;
        if (intent != null) {
            timeZoneSettings.onZonePickerRequestResult(timeZoneData, intent);
            timeZoneSettings.mPendingZonePickerRequestResult = null;
        }
    }
}
