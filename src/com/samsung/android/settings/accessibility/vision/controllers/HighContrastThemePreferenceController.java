package com.samsung.android.settings.accessibility.vision.controllers;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.PkgUtils;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.voiceinput.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class HighContrastThemePreferenceController extends BasePreferenceController
        implements A11yStatusLoggingProvider {
    static final String HIGH_CONTRAST_THEME_BLUE = "com.samsung.High_contrast_theme_II";
    static final String HIGH_CONTRAST_THEME_YELLOW = "com.samsung.Dream_A11y_lowvision";
    static final String HIGH_CONTRAST_THEME_YELLOW_LATEST = "com.samsung.High_contrast_theme_I";

    public HighContrastThemePreferenceController(Context context, String str) {
        super(context, str);
    }

    public static String getHighContrastTheme(Context context) {
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        String string =
                Settings.System.getString(
                        context.getContentResolver(), "current_sec_active_themepackage");
        return string != null
                ? (string.equals(HIGH_CONTRAST_THEME_YELLOW)
                                || string.equals(HIGH_CONTRAST_THEME_YELLOW_LATEST))
                        ? "T1"
                        : string.equals(HIGH_CONTRAST_THEME_BLUE) ? "T2" : "None"
                : "None";
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!WizardManagerHelper.isUserSetupComplete(this.mContext)) {
            return 4;
        }
        Context context = this.mContext;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        if (ActivityManager.semGetCurrentUser() == 0
                ? Utils.hasPackage(context, "com.samsung.android.themestore")
                : false) {
            return 0;
        }
        return (Rune.isChinaModel()
                        && !Utils.isTablet()
                        && PkgUtils.hasPackage(this.mContext, Constants.GALAXY_STORE_PACKAGE_NAME))
                ? 0
                : 3;
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        return Map.of("A11YS3010", getHighContrastTheme(this.mContext));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Context context = this.mContext;
        HashSet hashSet = SecAccessibilityUtils.excludeDeviceNameSet;
        String string =
                Settings.System.getString(
                        context.getContentResolver(), "current_sec_active_themepackage");
        return string != null
                ? (string.equals(HIGH_CONTRAST_THEME_YELLOW)
                                || string.equals(HIGH_CONTRAST_THEME_YELLOW_LATEST))
                        ? this.mContext.getString(R.string.color_yellow)
                        : string.equals(HIGH_CONTRAST_THEME_BLUE)
                                ? this.mContext.getString(R.string.color_blue)
                                : this.mContext.getString(R.string.app_list_preference_none)
                : this.mContext.getString(R.string.app_list_preference_none);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!preference.getKey().equals(getPreferenceKey())
                || !SecAccessibilityUtils.isDesktopDualModeMonitorDisplay(this.mContext)) {
            return false;
        }
        String string = this.mContext.getString(R.string.high_contrast_theme_title);
        Context context = this.mContext;
        Preference$$ExternalSyntheticOutline0.m(
                context, R.string.biometircs_dex_cant_use_toast, new Object[] {string}, context, 0);
        return true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
