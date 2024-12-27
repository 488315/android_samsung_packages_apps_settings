package com.android.settings.datetime.timezone;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.text.LocaleDisplayNames;

import com.android.settings.R;
import com.android.settingslib.datetime.ZoneGetter;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RegionPreferenceController extends BaseTimeZonePreferenceController {
    private static final String PREFERENCE_KEY = "region";
    private final Locale mLocale;
    private final LocaleDisplayNames mLocaleDisplayNames;
    private String mRegionId;

    public RegionPreferenceController(Context context) {
        super(context, PREFERENCE_KEY);
        this.mRegionId = ApnSettings.MVNO_NONE;
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        this.mLocale = locale;
        this.mLocaleDisplayNames = LocaleDisplayNames.getInstance(locale);
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

    public String getRegionId() {
        return this.mRegionId;
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
        String regionId = getRegionId();
        return regionId == null
                ? this.mLocaleDisplayNames.regionDisplayName(this.mRegionId)
                : (Rune.isDisableIsraelCountry() && regionId.equalsIgnoreCase("IL"))
                        ? this.mContext.getString(R.string.jerusalem)
                        : regionId.equalsIgnoreCase("TR")
                                ? this.mContext.getString(R.string.turkey)
                                : (Rune.FEATURE_NATIONAL_NAME_CHINA
                                                && regionId.equalsIgnoreCase("CN"))
                                        ? this.mContext.getString(R.string.china)
                                        : ZoneGetter.capitalizeForStandaloneDisplay(
                                                this.mLocale,
                                                this.mLocaleDisplayNames.regionDisplayName(
                                                        this.mRegionId));
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

    public void setRegionId(String str) {
        this.mRegionId = str;
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return super.setValue(controlValue);
    }

    @Override // com.android.settings.datetime.timezone.BaseTimeZonePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
