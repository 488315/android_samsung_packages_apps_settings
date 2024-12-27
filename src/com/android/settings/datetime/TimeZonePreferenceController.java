package com.android.settings.datetime;

import android.app.time.TimeManager;
import android.app.time.TimeZoneCapabilitiesAndConfig;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.TimeZoneFormat;
import android.icu.text.TimeZoneNames;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.MinorModeUtils;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.datetime.ZoneGetter;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.datetime.DateTimeUtils;
import com.samsung.android.settings.widget.SecRestrictedPreference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TimeZonePreferenceController extends BasePreferenceController {
    private static final String KEY_TIMEZONE = "timezone";
    private final TimeManager mTimeManager;

    public TimeZonePreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeManager = (TimeManager) context.getSystemService(TimeManager.class);
    }

    private TimeZoneCapabilitiesAndConfig getTimeZoneCapabilitiesAndConfig() {
        return this.mTimeManager.getTimeZoneCapabilitiesAndConfig();
    }

    private boolean shouldEnableManualTimeZoneSelection() {
        return this.mTimeManager
                        .getTimeZoneCapabilitiesAndConfig()
                        .getCapabilities()
                        .getSetManualTimeZoneCapability()
                == 40;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return !isAutoTimeZoneEnabled() ? 0 : 5;
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
        return getTimeZoneOffsetAndName();
    }

    public CharSequence getTimeZoneOffsetAndName() {
        String capitalizeForStandaloneDisplay;
        Calendar calendar = Calendar.getInstance();
        Context context = this.mContext;
        TimeZone timeZone = calendar.getTimeZone();
        Date time = calendar.getTime();
        Locale locale = context.getResources().getConfiguration().locale;
        CharSequence gmtOffsetText =
                ZoneGetter.getGmtOffsetText(
                        TimeZoneFormat.getInstance(locale), locale, timeZone, time);
        TimeZoneNames timeZoneNames = TimeZoneNames.getInstance(locale);
        if (timeZone.getID().equals("Asia/Jerusalem")
                && SemCscFeature.getInstance().getBoolean("CscFeature_Setting_DisableIsraelCountry")
                && !"TR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
            Log.d("ZoneGetter", "for MEA timeZoneId = " + timeZone.getID());
            capitalizeForStandaloneDisplay =
                    timeZoneNames.getExemplarLocationName(timeZone.getID());
            if (!TextUtils.isEmpty(capitalizeForStandaloneDisplay)) {
                DialogFragment$$ExternalSyntheticOutline0.m(
                        "for MEA zoneNameString = ", capitalizeForStandaloneDisplay, "ZoneGetter");
            }
        } else {
            TimeZoneNames.NameType nameType =
                    timeZone.inDaylightTime(time)
                            ? TimeZoneNames.NameType.LONG_DAYLIGHT
                            : TimeZoneNames.NameType.LONG_STANDARD;
            String id = timeZone.getID();
            String canonicalID = android.icu.util.TimeZone.getCanonicalID(id);
            if (canonicalID != null) {
                id = canonicalID;
            }
            capitalizeForStandaloneDisplay =
                    ZoneGetter.capitalizeForStandaloneDisplay(
                            locale,
                            ZoneGetter.capitalizeForStandaloneDisplay(
                                    locale,
                                    timeZoneNames.getDisplayName(id, nameType, time.getTime())));
        }
        return capitalizeForStandaloneDisplay == null
                ? gmtOffsetText
                : TextUtils.concat(gmtOffsetText, " ", capitalizeForStandaloneDisplay);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), KEY_TIMEZONE)
                || !MinorModeUtils.isCHNMinorModeRestrictDateTimeModify(this.mContext)) {
            return false;
        }
        Toast.makeText(this.mContext, R.string.child_account_can_not_change_time_toast, 0).show();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public boolean isAutoTimeZoneEnabled() {
        return getTimeZoneCapabilitiesAndConfig().getConfiguration().isAutoDetectionEnabled();
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof RestrictedPreference) {
            preference.setSummary(getTimeZoneOffsetAndName());
            SecRestrictedPreference secRestrictedPreference = (SecRestrictedPreference) preference;
            if (!secRestrictedPreference.mHelper.mDisabledByAdmin) {
                if (DateTimeUtils.applyEDMDateTimeChangePolicy(this.mContext)) {
                    preference.setEnabled(shouldEnableManualTimeZoneSelection());
                } else {
                    preference.setEnabled(false);
                }
            }
            secRestrictedPreference.getClass();
            SecPreferenceUtils.applySummaryColor(secRestrictedPreference, true);
            preference.setVisible(!isAutoTimeZoneEnabled());
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
