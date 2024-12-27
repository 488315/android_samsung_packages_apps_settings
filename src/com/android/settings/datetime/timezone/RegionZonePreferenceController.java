package com.android.settings.datetime.timezone;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;

import androidx.preference.Preference;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RegionZonePreferenceController extends BaseTimeZonePreferenceController {
    private static final String PREFERENCE_KEY = "region_zone";
    private static final String TZID_LAAYOUNE = "Africa/El_Aaiun";
    private boolean mIsClickable;
    private TimeZoneInfo mTimeZoneInfo;

    public RegionZonePreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        TimeZoneInfo timeZoneInfo;
        TimeZoneInfo timeZoneInfo2;
        if (Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_SHANGHAI_TO_BEIJING_BY_CHINA
                && (timeZoneInfo2 = this.mTimeZoneInfo) != null
                && timeZoneInfo2.mExemplarLocation.equals(
                        this.mContext.getString(R.string.city_shanghai))) {
            return SpannableUtil.getResourcesText(
                    this.mContext.getResources(),
                    R.string.zone_info_exemplar_location_and_offset,
                    this.mContext.getString(R.string.city_beijing),
                    this.mTimeZoneInfo.mGmtOffset);
        }
        if (Rune.SUPPORT_TEXT_REQUEST_TIMEZONE_JERUSALEM_TO_TELAVIV_BY_TURKEY
                && (timeZoneInfo = this.mTimeZoneInfo) != null
                && timeZoneInfo.mExemplarLocation.equals(
                        this.mContext.getString(R.string.jerusalem))) {
            return SpannableUtil.getResourcesText(
                    this.mContext.getResources(),
                    R.string.zone_info_exemplar_location_and_offset,
                    this.mContext.getString(R.string.tel_aviv),
                    this.mTimeZoneInfo.mGmtOffset);
        }
        TimeZoneInfo timeZoneInfo3 = this.mTimeZoneInfo;
        if (timeZoneInfo3 != null && timeZoneInfo3.mId.equals(TZID_LAAYOUNE)) {
            return this.mTimeZoneInfo.mGmtOffset;
        }
        if (this.mTimeZoneInfo == null) {
            return ApnSettings.MVNO_NONE;
        }
        Resources resources = this.mContext.getResources();
        TimeZoneInfo timeZoneInfo4 = this.mTimeZoneInfo;
        return SpannableUtil.getResourcesText(
                resources,
                R.string.zone_info_exemplar_location_and_offset,
                timeZoneInfo4.mExemplarLocation,
                timeZoneInfo4.mGmtOffset);
    }

    public TimeZoneInfo getTimeZoneInfo() {
        return this.mTimeZoneInfo;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return super.getValue();
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    public boolean isClickable() {
        return this.mIsClickable;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    public void setClickable(boolean z) {
        this.mIsClickable = z;
    }

    public void setTimeZoneInfo(TimeZoneInfo timeZoneInfo) {
        this.mTimeZoneInfo = timeZoneInfo;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        preference.setEnabled(isClickable());
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
