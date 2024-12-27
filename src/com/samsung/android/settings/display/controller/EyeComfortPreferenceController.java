package com.samsung.android.settings.display.controller;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.AirplaneModeEnabler$$ExternalSyntheticOutline0;
import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.preferences.DisplayDisabledAppearanceSwitchPreference;
import com.samsung.android.settings.eyecomfort.EyeComfortSettings;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EyeComfortPreferenceController extends TogglePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    private static final int DEFAULT_OFF = 0;
    private static final int DEFAULT_ON = 1;
    private static final String TAG = "EyeComfortPreferenceController";
    private ContentObserver mContentObserver;
    private DisplayDisabledAppearanceSwitchPreference mPreference;
    private static final Uri BLUE_LIGHT_FILTER_URI = Settings.System.getUriFor("blue_light_filter");
    private static final Uri BLUE_LIGHT_FILTER_TYPE_URI =
            Settings.System.getUriFor("blue_light_filter_type");
    private static final Uri BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI =
            Settings.System.getUriFor("blue_light_filter_adaptive_mode");
    private static final Uri GREY_SCALE_URI = Settings.System.getUriFor("greyscale_mode");
    private static final Uri HIGH_CONTRAST_URI = Settings.System.getUriFor("high_contrast");
    private static final Uri COLOR_BLIND_URI = Settings.System.getUriFor("color_blind");
    private static final Uri COLOR_LENDS_URI = Settings.Secure.getUriFor("color_lens_switch");
    private static final Uri LOCATION_ALLOWED_URI =
            Settings.Secure.getUriFor("location_providers_allowed");

    public EyeComfortPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.display.controller.EyeComfortPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        EyeComfortPreferenceController eyeComfortPreferenceController =
                                EyeComfortPreferenceController.this;
                        eyeComfortPreferenceController.updateState(
                                eyeComfortPreferenceController.mPreference);
                    }
                };
    }

    private ContentResolver getContentResolver() {
        return this.mContext.getContentResolver();
    }

    private void showLocationOnDialog() {
        Intent action = new Intent().setAction("com.samsung.settings.EyeComfortLocationDialog");
        if (!(this.mContext instanceof Activity)) {
            action.addFlags(402653184);
        }
        this.mContext.startActivity(action);
    }

    private void updateEyeComfortState(Preference preference) {
        if (preference instanceof DisplayDisabledAppearanceSwitchPreference) {
            preference.setTitle(R.string.sec_eye_comfort_title);
            boolean z =
                    Rune.isSamsungDexMode(this.mContext) && Utils.isDesktopDualMode(this.mContext);
            boolean z2 =
                    (SecDisplayUtils.isAccessibilityVisionEnabled(this.mContext) || z)
                            ? false
                            : true;
            DisplayDisabledAppearanceSwitchPreference displayDisabledAppearanceSwitchPreference =
                    (DisplayDisabledAppearanceSwitchPreference) preference;
            displayDisabledAppearanceSwitchPreference.mIsEnabled = z2;
            displayDisabledAppearanceSwitchPreference.notifyChanged();
            preference.setFragment(z2 ? EyeComfortSettings.class.getCanonicalName() : null);
            String accessibilityVisionMessage =
                    SecDisplayUtils.getAccessibilityVisionMessage(this.mContext);
            if (z) {
                preference.setEnabled(false);
            } else {
                if (TextUtils.isEmpty(accessibilityVisionMessage)) {
                    return;
                }
                Context context = this.mContext;
                displayDisabledAppearanceSwitchPreference.mMsg =
                        context.getString(
                                R.string.sec_eye_comfort_disable_reason,
                                accessibilityVisionMessage,
                                context.getString(R.string.sec_eye_comfort_title));
            }
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        DisplayDisabledAppearanceSwitchPreference displayDisabledAppearanceSwitchPreference;
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (DisplayDisabledAppearanceSwitchPreference)
                        preferenceScreen.findPreference(getPreferenceKey());
        if (getAvailabilityStatus() != 5
                || (displayDisabledAppearanceSwitchPreference = this.mPreference) == null) {
            return;
        }
        displayDisabledAppearanceSwitchPreference.setEnabled(true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        registerStateCode();
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return (SecDisplayUtils.isAccessibilityVisionEnabled(this.mContext)
                        || (Rune.isSamsungDexMode(this.mContext)
                                && Utils.isDesktopDualMode(this.mContext)))
                ? 5
                : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public Intent getLaunchIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setClassName(
                KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                "com.android.settings.Settings$EyeComfortSettingsActivity");
        intent.addFlags(268468224);
        return intent;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        if (Settings.System.getInt(getContentResolver(), "blue_light_filter", 0) != 0) {
            int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
            if (Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0)
                    != 0) {
                if (i == 1) {
                    long j =
                            Settings.System.getLong(
                                    getContentResolver(), "blue_light_filter_on_time", 1140L);
                    long j2 =
                            Settings.System.getLong(
                                    getContentResolver(), "blue_light_filter_off_time", 420L);
                    return this.mContext
                            .getResources()
                            .getString(
                                    R.string.sec_data_usage_summary_preference_cycle_label,
                                    SecDisplayUtils.getStringFromMillis(this.mContext, j),
                                    j >= j2
                                            ? this.mContext
                                                    .getResources()
                                                    .getString(
                                                            R.string
                                                                    .sec_blue_light_filter_off_time_next_day_summary_format,
                                                            SecDisplayUtils.getStringFromMillis(
                                                                    this.mContext, j2))
                                            : SecDisplayUtils.getStringFromMillis(
                                                    this.mContext, j2));
                }
                if (i == 2) {
                    return this.mContext.getString(
                            R.string.sec_eye_comfort_schedule_sunset_to_sunrise);
                }
                if (i == 0) {
                    return this.mContext.getString(R.string.sec_eye_comfort_schedule_always_on);
                }
            } else if (Rune.supportBlueLightFilterAdaptiveMode()
                    && Settings.System.getInt(
                                    getContentResolver(), "blue_light_filter_adaptive_mode", 0)
                            != 0) {
                return this.mContext.getString(R.string.sec_eye_comfort_adaptive);
            }
        }
        return ApnSettings.MVNO_NONE;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return Settings.System.getInt(getContentResolver(), "blue_light_filter", 0) != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        getContentResolver()
                .registerContentObserver(BLUE_LIGHT_FILTER_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(BLUE_LIGHT_FILTER_TYPE_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(
                        BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(GREY_SCALE_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(HIGH_CONTRAST_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(COLOR_BLIND_URI, false, this.mContentObserver);
        getContentResolver().registerContentObserver(COLOR_LENDS_URI, false, this.mContentObserver);
        getContentResolver()
                .registerContentObserver(LOCATION_ALLOWED_URI, false, this.mContentObserver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        getContentResolver().unregisterContentObserver(this.mContentObserver);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void refreshSummary(Preference preference) {
        super.refreshSummary(preference);
        if (preference instanceof DisplayDisabledAppearanceSwitchPreference) {
            DisplayDisabledAppearanceSwitchPreference displayDisabledAppearanceSwitchPreference =
                    (DisplayDisabledAppearanceSwitchPreference) preference;
            displayDisabledAppearanceSwitchPreference.getClass();
            SecPreferenceUtils.applySummaryColor(displayDisabledAppearanceSwitchPreference, true);
        }
    }

    public void registerStateCode() {
        boolean z =
                Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0) != 0;
        int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
        boolean z2 = Settings.Secure.getInt(getContentResolver(), "location_mode", 0) != 0;
        boolean z3 = Settings.System.getInt(getContentResolver(), "greyscale_mode", 0) != 0;
        boolean z4 = Settings.System.getInt(getContentResolver(), "high_contrast", 0) != 0;
        boolean z5 = Settings.System.getInt(getContentResolver(), "color_blind", 0) != 0;
        boolean z6 = Settings.Secure.getInt(getContentResolver(), "color_lens_switch", 0) != 0;
        if (!z2 && z && i == 2) {
            setStatusCode("disabled_by_ecs_dialog_location_off");
            return;
        }
        if (z3) {
            setStatusCode("disabled_by_ecs_toast_accessibility_grey_scale");
            return;
        }
        if (z4) {
            setStatusCode("disabled_by_ecs_toast_accessibility_negative_color");
        } else if (z5) {
            setStatusCode("disabled_by_ecs_toast_accessibility_color_adjustment");
        } else if (z6) {
            setStatusCode("disabled_by_ecs_toast_accessibility_color_lens");
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        byte b =
                Settings.System.getInt(getContentResolver(), "blue_light_filter_scheduled", 0) != 0;
        int i = Settings.System.getInt(getContentResolver(), "blue_light_filter_type", 0);
        if (Settings.Secure.getInt(getContentResolver(), "location_mode", 0) == 0
                && i == 2
                && b == true
                && z) {
            showLocationOnDialog();
            return false;
        }
        AirplaneModeEnabler$$ExternalSyntheticOutline0.m(" checked state ", TAG, z);
        Settings.System.putInt(getContentResolver(), "blue_light_filter", z ? 1 : 0);
        LoggingHelper.insertEventLogging(46, 4221, z ? 1L : 0L);
        Intent intent = new Intent();
        DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                "com.samsung.android.bluelightfilter",
                "com.samsung.android.bluelightfilter.BlueLightFilterService",
                intent);
        intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", z ? 24 : 25);
        this.mContext.startService(intent);
        refreshSummary(this.mPreference);
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (preference == null) {
            return;
        }
        updateEyeComfortState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
