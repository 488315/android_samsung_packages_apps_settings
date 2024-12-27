package com.android.settings.datetime.timezone;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;

import androidx.picker.widget.SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.time.Instant;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeZoneInfoPreferenceController extends BasePreferenceController {
    Date mDate;
    private final DateFormat mDateFormat;
    private TimeZoneInfo mTimeZoneInfo;

    public TimeZoneInfoPreferenceController(Context context, String str) {
        super(context, str);
        DateFormat dateInstance = DateFormat.getDateInstance(1);
        this.mDateFormat = dateInstance;
        dateInstance.setContext(DisplayContext.CAPITALIZATION_NONE);
        this.mDate = new Date();
    }

    private CharSequence createFooterString(CharSequence charSequence, String str, String str2) {
        Resources resources = this.mContext.getResources();
        Locale locale = resources.getConfiguration().getLocales().get(0);
        return SpannableUtil.titleCaseSentences(
                locale,
                SpannableUtil.getResourcesText(
                        resources,
                        R.string.zone_info_footer_first_sentence,
                        charSequence,
                        SpannableUtil.titleCaseSentences(
                                locale,
                                SpannableUtil.getResourcesText(
                                        resources,
                                        R.string.zone_info_footer_second_sentence,
                                        str,
                                        str2))));
    }

    private ZoneOffsetTransition findNextDstTransition(TimeZoneInfo timeZoneInfo) {
        ZoneOffsetTransition nextTransition;
        TimeZone timeZone = timeZoneInfo.mTimeZone;
        String id = timeZone.getID();
        String canonicalID = TimeZone.getCanonicalID(id);
        if (canonicalID != null) {
            id = canonicalID;
        }
        ZoneRules rules = java.util.TimeZone.getTimeZone(id).toZoneId().getRules();
        Instant instant = this.mDate.toInstant();
        while (true) {
            nextTransition = rules.nextTransition(instant);
            if (nextTransition == null) {
                break;
            }
            Instant instant2 = nextTransition.getInstant();
            if (getDSTSavings(timeZone, instant) != getDSTSavings(timeZone, instant2)) {
                break;
            }
            instant = instant2;
        }
        return nextTransition;
    }

    private CharSequence formatInfo(TimeZoneInfo timeZoneInfo) {
        CharSequence formatOffsetAndName = formatOffsetAndName(timeZoneInfo);
        TimeZone timeZone = timeZoneInfo.mTimeZone;
        ZoneOffsetTransition findNextDstTransition =
                timeZone.observesDaylightTime() ? findNextDstTransition(timeZoneInfo) : null;
        if (findNextDstTransition == null || !timeZone.observesDaylightTime()) {
            return SpannableUtil.getResourcesText(
                    this.mContext.getResources(),
                    R.string.zone_info_footer_no_dst,
                    formatOffsetAndName);
        }
        boolean z = getDSTSavings(timeZone, findNextDstTransition.getInstant()) != 0;
        String str =
                (Rune.isDisableIsraelCountry() && timeZoneInfo.mId.equals("Asia/Jerusalem"))
                        ? null
                        : z ? timeZoneInfo.mDaylightName : timeZoneInfo.mStandardName;
        if (str == null) {
            str =
                    z
                            ? this.mContext.getString(R.string.zone_time_type_dst)
                            : this.mContext.getString(R.string.zone_time_type_standard);
        }
        Calendar calendar = Calendar.getInstance(timeZone);
        calendar.setTimeInMillis(findNextDstTransition.getInstant().toEpochMilli());
        return createFooterString(formatOffsetAndName, str, this.mDateFormat.format(calendar));
    }

    private CharSequence formatOffsetAndName(TimeZoneInfo timeZoneInfo) {
        String str = timeZoneInfo.mGenericName;
        boolean isDisableIsraelCountry = Rune.isDisableIsraelCountry();
        String str2 = timeZoneInfo.mId;
        if (isDisableIsraelCountry && str2.equals("Asia/Jerusalem")) {
            if (Utils.isRTL(this.mContext)) {
                StringBuilder sb = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                        this.mContext, R.string.time_picker_title, sb, " ");
                str =
                        SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                .m(this.mContext, R.string.jerusalem, sb);
            } else {
                StringBuilder sb2 = new StringBuilder();
                TimeZoneInfoPreferenceController$$ExternalSyntheticOutline0.m(
                        this.mContext, R.string.jerusalem, sb2, " ");
                str =
                        SeslSpinningDatePickerSpinnerDelegate$AccessibilityNodeProviderImpl$$ExternalSyntheticOutline0
                                .m(this.mContext, R.string.time_picker_title, sb2);
            }
        }
        if (str == null) {
            str =
                    timeZoneInfo.mTimeZone.inDaylightTime(this.mDate)
                            ? (Rune.isDisableIsraelCountry() && str2.equals("Asia/Jerusalem"))
                                    ? this.mContext.getString(R.string.zone_time_type_dst)
                                    : timeZoneInfo.mDaylightName
                            : timeZoneInfo.mStandardName;
        }
        return str == null
                ? timeZoneInfo.mGmtOffset.toString()
                : SpannableUtil.getResourcesText(
                        this.mContext.getResources(),
                        R.string.zone_info_offset_and_name,
                        timeZoneInfo.mGmtOffset,
                        str);
    }

    private static int getDSTSavings(TimeZone timeZone, Instant instant) {
        int[] iArr = new int[2];
        timeZone.getOffset(instant.toEpochMilli(), false, iArr);
        return iArr[1];
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return this.mTimeZoneInfo != null ? 1 : 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        TimeZoneInfo timeZoneInfo = this.mTimeZoneInfo;
        return timeZoneInfo == null ? ApnSettings.MVNO_NONE : formatInfo(timeZoneInfo);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setTimeZoneInfo(TimeZoneInfo timeZoneInfo) {
        this.mTimeZoneInfo = timeZoneInfo;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        preference.setTitle(getSummary());
        preference.setVisible(this.mTimeZoneInfo != null);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
