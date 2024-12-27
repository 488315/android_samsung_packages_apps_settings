package com.android.settings.datetime.timezone;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.datetime.timezone.model.TimeZoneData;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BaseTimeZoneInfoPicker extends BaseTimeZonePicker {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TimeZoneInfoItem implements BaseTimeZoneAdapter.AdapterItem {
        public final long mItemId;
        public final Resources mResources;
        public final String[] mSearchKeys;
        public final DateFormat mTimeFormat;
        public final TimeZoneInfo mTimeZoneInfo;
        public final String mTitle;

        public TimeZoneInfoItem(
                long j, TimeZoneInfo timeZoneInfo, Resources resources, DateFormat dateFormat) {
            this.mItemId = j;
            this.mTimeZoneInfo = timeZoneInfo;
            this.mResources = resources;
            this.mTimeFormat = dateFormat;
            String str = timeZoneInfo.mExemplarLocation;
            if (!TextUtils.isEmpty(str)) {
                if (Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_SHANGHAI_TO_BEIJING_BY_CHINA
                        && str.equals(resources.getText(R.string.city_shanghai))) {
                    str = resources.getText(R.string.city_beijing).toString();
                } else if (Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY
                        && str.equals(resources.getText(R.string.jerusalem))) {
                    str = resources.getText(R.string.tel_aviv).toString();
                }
            }
            str = str == null ? timeZoneInfo.mGenericName : str;
            if (str == null && timeZoneInfo.mTimeZone.inDaylightTime(new Date())) {
                str = timeZoneInfo.mDaylightName;
            }
            str = str == null ? timeZoneInfo.mStandardName : str;
            str = str == null ? String.valueOf(timeZoneInfo.mGmtOffset) : str;
            this.mTitle = str;
            this.mSearchKeys = new String[] {str};
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final String getCurrentTime() {
            return this.mTimeFormat.format(Calendar.getInstance(this.mTimeZoneInfo.mTimeZone));
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
            TimeZoneInfo timeZoneInfo = this.mTimeZoneInfo;
            String str = timeZoneInfo.mGenericName;
            if (str == null) {
                str =
                        timeZoneInfo.mTimeZone.inDaylightTime(new Date())
                                ? timeZoneInfo.mDaylightName
                                : timeZoneInfo.mStandardName;
            }
            String str2 = this.mTitle;
            if (str != null && !str.equals(str2)) {
                return SpannableUtil.getResourcesText(
                        this.mResources,
                        R.string.zone_info_offset_and_name,
                        timeZoneInfo.mGmtOffset,
                        str);
            }
            CharSequence charSequence = timeZoneInfo.mGmtOffset;
            return (charSequence == null || charSequence.toString().equals(str2))
                    ? ApnSettings.MVNO_NONE
                    : charSequence;
        }

        @Override // com.android.settings.datetime.timezone.BaseTimeZoneAdapter.AdapterItem
        public final CharSequence getTitle() {
            return this.mTitle;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ZoneAdapter extends BaseTimeZoneAdapter {}

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePicker
    public final BaseTimeZoneAdapter createAdapter(TimeZoneData timeZoneData) {
        Context context = getContext();
        List allTimeZoneInfos = getAllTimeZoneInfos(timeZoneData);
        BaseTimeZonePicker.OnListItemClickListener onListItemClickListener =
                new BaseTimeZonePicker
                        .OnListItemClickListener() { // from class:
                                                     // com.android.settings.datetime.timezone.BaseTimeZoneInfoPicker$$ExternalSyntheticLambda0
                    @Override // com.android.settings.datetime.timezone.BaseTimeZonePicker.OnListItemClickListener
                    public final void onListItemClick(BaseTimeZoneAdapter.AdapterItem adapterItem) {
                        BaseTimeZoneInfoPicker baseTimeZoneInfoPicker = BaseTimeZoneInfoPicker.this;
                        baseTimeZoneInfoPicker.getClass();
                        baseTimeZoneInfoPicker
                                .getActivity()
                                .setResult(
                                        -1,
                                        baseTimeZoneInfoPicker.prepareResultData(
                                                ((BaseTimeZoneInfoPicker.TimeZoneInfoItem)
                                                                adapterItem)
                                                        .mTimeZoneInfo));
                        baseTimeZoneInfoPicker.getActivity().finish();
                    }
                };
        Locale locale = getLocale();
        CharSequence headerText = getHeaderText();
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat(
                        android.text.format.DateFormat.getTimeFormatString(context), locale);
        ArrayList arrayList = new ArrayList(allTimeZoneInfos.size());
        Resources resources = context.getResources();
        Iterator it = allTimeZoneInfos.iterator();
        long j = 0;
        while (it.hasNext()) {
            arrayList.add(
                    new TimeZoneInfoItem(j, (TimeZoneInfo) it.next(), resources, simpleDateFormat));
            j++;
        }
        return new ZoneAdapter(arrayList, onListItemClickListener, locale, true, headerText);
    }

    public abstract List getAllTimeZoneInfos(TimeZoneData timeZoneData);

    public CharSequence getHeaderText() {
        return null;
    }

    public Intent prepareResultData(TimeZoneInfo timeZoneInfo) {
        return new Intent()
                .putExtra(
                        "com.android.settings.datetime.timezone.result_time_zone_id",
                        timeZoneInfo.mId);
    }
}
