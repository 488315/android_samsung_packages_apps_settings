package com.android.settings;

import android.R;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.SearchIndexableData;
import android.provider.Settings;
import android.util.Log;
import android.view.IWindowManager;
import android.view.View;
import android.view.WindowManagerGlobal;

import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.search.SearchIndexableRaw;
import com.android.settingslib.widget.LayoutPreference;

import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.fontutil.FontWriter;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.display.SecDisplayUtils;
import com.samsung.android.settings.display.controller.EasyModePreferenceController;
import com.samsung.android.settings.display.controller.KeyboardBacklightPreferenceController;
import com.samsung.android.settings.display.controller.NavigationBarPreferenceController;
import com.samsung.android.settings.display.controller.NightTimeKeyboardModePreferenceController;
import com.samsung.android.settings.display.controller.OuterScreenAutoLockPreferenceController;
import com.samsung.android.settings.display.controller.SecBrightnessPreferenceController;
import com.samsung.android.settings.display.controller.SecOutDoorModePreferenceController;
import com.samsung.android.settings.display.controller.SecScrollFilterForChromePreferenceController;
import com.samsung.android.settings.display.controller.SecSubBrightnessPreferenceController;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.gts.GtsGroup;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.navigationbar.NavigationBarSettings;
import com.samsung.android.settings.navigationbar.NavigationBarSettingsUtil;
import com.samsung.android.settings.taskbar.TaskBarPreferenceController;
import com.samsung.android.settings.widget.SecRelativeLinkView;
import com.sec.ims.configuration.DATA;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class DisplaySettings extends DashboardFragment {
    public Context mContext;
    public LayoutPreference mDarkModePref;
    public Handler mHandler;
    public View mLayoutSecDarkMode;
    public SecRelativeLinkView mRelativeLinkView;
    public SettingsObserver mSettingsObserver;
    public static final LocalTime DEFAULT_CUSTOM_NIGHT_START_TIME = LocalTime.of(19, 0);
    public static final LocalTime DEFAULT_CUSTOM_NIGHT_END_TIME = LocalTime.of(7, 0);
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass2(R.xml.sec_display_settings);
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass4();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.DisplaySettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final List createPreferenceControllers(Context context) {
            return DisplaySettings.buildPreferenceControllers(context, null);
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider
        public final HashMap getGtsResourceGroup() {
            HashMap hashMap = new HashMap();
            String groupName = GtsGroup.GROUP_KEY_DISPLAY.getGroupName();
            hashMap.put("auto_brightness", groupName);
            hashMap.put("dark_mode", groupName);
            hashMap.put("sec_high_refresh_rate", groupName);
            hashMap.put("sec_font_size_controller", groupName);
            hashMap.put("sec_font_style", groupName);
            hashMap.put("camera_cutout", groupName);
            hashMap.put("full_screen_apps", groupName);
            hashMap.put("front_screen_apps", groupName);
            hashMap.put("navigation_Bar", groupName);
            hashMap.put("screen_mode", groupName);
            hashMap.put("screen_resolution", groupName);
            hashMap.put("screen_timeout", groupName);
            hashMap.put("edge_screen", groupName);
            return hashMap;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            ArrayList arrayList = (ArrayList) nonIndexableKeys;
            arrayList.add("secfrontbrightness");
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            if (context == null) {
                Log.secD("SecDisplayUtils", "getAutoBrightnessSetting() - context is null");
            } else if (Settings.System.getInt(
                                    context.getContentResolver(), "screen_brightness_mode", 0)
                            == 1
                    && (!Rune.isSamsungDexMode(context) || !Utils.isDesktopDualMode(context))) {
                arrayList.add("extra_brightness");
            }
            if (!Rune.supportHighRefreshRate(context, 0) || Utils.isDesktopModeEnabled(context)) {
                arrayList.add("sec_high_refresh_rate");
            }
            return nonIndexableKeys;
        }

        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getRawDataToIndex(Context context) {
            Resources resources = context.getResources();
            ArrayList arrayList = new ArrayList();
            SearchIndexableRaw searchIndexableRaw = new SearchIndexableRaw(context);
            Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            searchIndexableRaw.title = String.valueOf(R.string.sec_screen_zoom_title);
            searchIndexableRaw.screenTitle = resources.getString(R.string.display_settings_title);
            searchIndexableRaw.keywords =
                    resources.getString(R.string.sec_font_size_and_style_keyword);
            ((SearchIndexableData) searchIndexableRaw).key = "sec_screen_size";
            arrayList.add(searchIndexableRaw);
            if (Rune.supportNavigationBarForHardKey()) {
                SearchIndexableRaw searchIndexableRaw2 = new SearchIndexableRaw(context);
                ((SearchIndexableData) searchIndexableRaw2).intentTargetClass =
                        NavigationBarSettings.class.getName();
                searchIndexableRaw2.title =
                        String.valueOf(R.string.navigationbar_gesture_category_name);
                searchIndexableRaw2.screenTitle =
                        resources.getString(R.string.display_settings_title);
                searchIndexableRaw2.keywords = resources.getString(R.string.keywords_navigationbar);
                ((SearchIndexableData) searchIndexableRaw2).key = "navigation_Bar";
                arrayList.add(searchIndexableRaw2);
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.DisplaySettings$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            ArrayList arrayList = new ArrayList();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            int screenResolution = SecDisplayUtils.getScreenResolution(context);
            String valueOf = String.valueOf(7440);
            String valueOf2 = String.valueOf(screenResolution);
            StatusData statusData = new StatusData();
            statusData.mStatusValue = valueOf2;
            statusData.mStatusKey = valueOf;
            arrayList.add(statusData);
            int[] screenZoomInfo = SecDisplayUtils.getScreenZoomInfo(context);
            int i = screenZoomInfo == null ? -1 : screenZoomInfo[1];
            String valueOf3 = String.valueOf(7430);
            String valueOf4 = String.valueOf(i);
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = valueOf4;
            statusData2.mStatusKey = valueOf3;
            arrayList.add(statusData2);
            if (context.getResources().getBoolean(R.bool.config_earcFeatureDisabled_default)) {
                String valueOf5 = String.valueOf(4233);
                boolean isScreenSaverEnabled = SecDisplayUtils.isScreenSaverEnabled(context);
                String str2 = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                String str3 = isScreenSaverEnabled ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                StatusData statusData3 = new StatusData();
                statusData3.mStatusValue = str3;
                statusData3.mStatusKey = valueOf5;
                arrayList.add(statusData3);
                String valueOf6 = String.valueOf(4304);
                if (SecDisplayUtils.isScreenSaverEnabled(context)) {
                    str2 = "1";
                }
                StatusData statusData4 = new StatusData();
                statusData4.mStatusValue = str2;
                statusData4.mStatusKey = valueOf6;
                arrayList.add(statusData4);
            }
            if (Rune.supportHighRefreshRate(context, 1)) {
                String valueOf7 = String.valueOf(10411);
                String valueOf8 = String.valueOf(SecDisplayUtils.getIntRefreshRate(context, 1));
                StatusData statusData5 = new StatusData();
                statusData5.mStatusValue = valueOf8;
                statusData5.mStatusKey = valueOf7;
                arrayList.add(statusData5);
            }
            if (Rune.supportHighRefreshRate(context, 0)) {
                String valueOf9 = String.valueOf(10411);
                String valueOf10 = String.valueOf(SecDisplayUtils.getIntRefreshRate(context, 0));
                StatusData statusData6 = new StatusData();
                statusData6.mStatusValue = valueOf10;
                statusData6.mStatusKey = valueOf9;
                arrayList.add(statusData6);
            }
            if (Settings.System.getInt(context.getContentResolver(), "blue_light_filter", 0) != 0) {
                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                    int i2 =
                            Settings.System.getInt(
                                    context.getContentResolver(),
                                    "blue_light_filter_adaptive_mode",
                                    0);
                    String valueOf11 = String.valueOf(40200);
                    String valueOf12 = String.valueOf(i2);
                    StatusData statusData7 = new StatusData();
                    statusData7.mStatusValue = valueOf12;
                    statusData7.mStatusKey = valueOf11;
                    arrayList.add(statusData7);
                }
                int i3 =
                        Settings.System.getInt(
                                context.getContentResolver(), "blue_light_filter_scheduled", 0);
                String valueOf13 = String.valueOf(7411);
                String valueOf14 = String.valueOf(i3);
                StatusData statusData8 = new StatusData();
                statusData8.mStatusValue = valueOf14;
                statusData8.mStatusKey = valueOf13;
                arrayList.add(statusData8);
                if (i3 == 1) {
                    int i4 =
                            Settings.System.getInt(
                                    context.getContentResolver(), "blue_light_filter_type", 0);
                    String valueOf15 = String.valueOf(4225);
                    String valueOf16 = String.valueOf(i4);
                    StatusData statusData9 = new StatusData();
                    statusData9.mStatusValue = valueOf16;
                    statusData9.mStatusKey = valueOf15;
                    arrayList.add(statusData9);
                }
            }
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.DisplaySettings$4, reason: invalid class name */
    public final class AnonymousClass4 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            ContentResolver contentResolver = context.getContentResolver();
            String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
            Settings.System.putInt(contentResolver, "auto_adjust_touch", 0);
            Settings.System.putInt(contentResolver, "ead_enabled", 0);
            Settings.System.putInt(contentResolver, "notified_ead_camera_use", 0);
            Settings.System.putInt(contentResolver, "notified_ead_camera_use_routine", 0);
            boolean supportWcgModeOnAmoled = Rune.supportWcgModeOnAmoled();
            boolean supportVividPlusMode = Rune.supportVividPlusMode();
            if (Rune.isShopDemo(context)) {
                Settings.System.putInt(contentResolver, "screen_mode_setting", 4);
                Settings.System.putInt(contentResolver, "screen_mode_automatic_setting", 1);
                Log.d("DisplaySettings", "(shop demo reset) set Mdnie ScreenMode : 4");
                if (supportWcgModeOnAmoled && !supportVividPlusMode) {
                    SecDisplayUtils.setDisplayColor(1);
                }
                SemMdnieManager semMdnieManager =
                        (SemMdnieManager) context.getSystemService("mDNIe");
                if (semMdnieManager != null) {
                    semMdnieManager.setScreenMode(4);
                }
            } else {
                int i6 =
                        SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_DEFAULT_SCREEN_MODE");
                Settings.System.putInt(contentResolver, "screen_mode_setting", i6);
                if (i6 == 4) {
                    Settings.System.putInt(contentResolver, "screen_mode_automatic_setting", 1);
                    i = 0;
                } else {
                    i = 0;
                    Settings.System.putInt(contentResolver, "screen_mode_automatic_setting", 0);
                }
                if (supportWcgModeOnAmoled && !supportVividPlusMode) {
                    if (i6 == 3) {
                        SecDisplayUtils.setDisplayColor(i);
                    } else {
                        SecDisplayUtils.setDisplayColor(1);
                    }
                }
            }
            if (Rune.supportPocketMode(context)) {
                Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
                Settings.System.putInt(
                        contentResolver,
                        "screen_off_pocket",
                        (Rune.isChinaModel()
                                        || SemFloatingFeature.getInstance()
                                                        .getInt(
                                                                "SEC_FLOATING_FEATURE_SETTINGS_CONFIG_DEFAULT_POCKET_MODE")
                                                == 1)
                                ? 1
                                : 0);
            }
            int i7 = Settings.System.getInt(contentResolver, "easy_mode_switch", 1) != 1 ? 1 : 0;
            int i8 = i7 != 0 ? 1 : 3;
            Point point = new Point();
            try {
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                windowManagerService.getInitialDisplaySize(0, point);
                int initialDisplayDensity = windowManagerService.getInitialDisplayDensity(0);
                Log.i("DisplaySettings", "resetDisplaySizeSettings() default LCD size : " + point);
                Log.i(
                        "DisplaySettings",
                        "resetDisplaySizeSettings() default density : " + initialDisplayDensity);
                String string = Settings.Global.getString(contentResolver, "display_size_forced");
                if (string == null || ApnSettings.MVNO_NONE.equals(string)) {
                    i2 = point.x;
                } else {
                    String[] split = string.split(",");
                    i2 = (split == null || split.length <= 1) ? 1440 : Integer.parseInt(split[0]);
                }
                if (i7 != 0) {
                    Settings.System.putInt(
                            contentResolver,
                            "preserved_density_standard_mode",
                            initialDisplayDensity);
                }
                if (i8 == 1) {
                    i3 = 3;
                    Log.i("DisplaySettings", "resetDisplaySizeSettings() Clear Resolution");
                    int i9 = point.x;
                    if (i9 < 1440 && (i9 <= 720 || i9 > 1080)) {
                        i4 = 0;
                        SecDisplayUtils.setSelectedScreenResolution(context, i4);
                    }
                    i4 = 1;
                    SecDisplayUtils.setSelectedScreenResolution(context, i4);
                } else if (i8 != 2) {
                    i3 = 3;
                    if (i8 != 3) {
                        Log.i("DisplaySettings", "resetDisplaySizeSettings() clearMode +" + i8);
                    } else {
                        Log.i(
                                "DisplaySettings",
                                "resetDisplaySizeSettings() Clear Resolution & Density ");
                        int i10 = point.x;
                        if (i10 >= 1440) {
                            SecDisplayUtils.applyForcedDisplayDensity(
                                    (int) (i10 * 0.75d),
                                    (int) (point.y * 0.75d),
                                    (int) (initialDisplayDensity * 0.75d));
                        } else {
                            SecDisplayUtils.applyForcedDisplayDensity(
                                    i10, point.y, initialDisplayDensity);
                        }
                    }
                } else {
                    i3 = 3;
                    int i11 = (i2 * initialDisplayDensity) / point.x;
                    Log.i(
                            "DisplaySettings",
                            "resetDisplaySizeSettings() Clear Density density : " + i11);
                    SecDisplayUtils.applyForcedDisplayDensity(-1, -1, i11);
                }
                if (i7 == 0 && windowManagerService.isSafeModeEnabled()) {
                    Settings.Global.putString(
                            contentResolver, "display_size_forced", point.x + "," + point.y);
                    Settings.Secure.putStringForUser(
                            contentResolver,
                            "display_density_forced",
                            Integer.toString(initialDisplayDensity),
                            0);
                    Log.secI(
                            "DisplaySettings",
                            "The display settings have been reset in safe mode.");
                }
                new FontWriter().writeLoc("default#default");
                Settings.Secure.putInt(contentResolver, "sem_sp_edition_flipfont_changed", 0);
                if (i7 != 0) {
                    Settings.Global.putInt(contentResolver, "bold_text", 1);
                    SecDisplayUtils.writeFontWeightDBAllUser(context, 300);
                    Settings.Global.putInt(contentResolver, "STANDARD_BOLD_FONT", 0);
                } else {
                    Settings.Global.putInt(contentResolver, "bold_text", 0);
                    SecDisplayUtils.writeFontWeightDBAllUser(context, 0);
                }
                int i12 =
                        SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_SETTINGS_CONFIG_DEFAULT_FONT_SIZE");
                if (i7 != 0) {
                    Settings.Global.putInt(contentResolver, "STANDARD_FONT_SIZE", i12);
                    if (Build.VERSION.SEM_PLATFORM_INT >= 110500 && Rune.isJapanModel()) {
                        i3 = 5;
                    }
                } else {
                    i3 = i12;
                }
                Settings.Global.putInt(contentResolver, "font_size", i3);
                SecDisplayUtils.writeFontScaleDBAllUser(
                        context, SecDisplayUtils.getFontScale(context, i3));
                if (Rune.isDualFolderType(context) && Rune.isChinaModel()) {
                    i5 = 1;
                    Settings.System.putInt(contentResolver, "key_night_mode", 1);
                } else {
                    i5 = 1;
                }
                if (Rune.supportHighRefreshRate(context, 0)) {
                    SecDisplayUtils.putIntRefreshRate(
                            context, SecDisplayUtils.getHighRefreshRateDefaultValue(context, 0), 0);
                }
                if (Rune.supportHighRefreshRate(context, i5)) {
                    SecDisplayUtils.putIntRefreshRate(
                            context,
                            SecDisplayUtils.getHighRefreshRateDefaultValue(context, i5),
                            i5);
                }
                UiModeManager uiModeManager = (UiModeManager) context.getSystemService("uimode");
                uiModeManager.setNightMode(i5);
                uiModeManager.setCustomNightModeStart(
                        DisplaySettings.DEFAULT_CUSTOM_NIGHT_START_TIME);
                uiModeManager.setCustomNightModeEnd(DisplaySettings.DEFAULT_CUSTOM_NIGHT_END_TIME);
                Settings.System.putInt(contentResolver, "display_night_theme", 0);
                Settings.System.putInt(contentResolver, "display_night_theme_scheduled", 0);
                Settings.System.putInt(contentResolver, "display_night_theme_scheduled_type", 1);
                Settings.System.putLong(contentResolver, "display_night_theme_on_time", 1140L);
                Settings.System.putLong(contentResolver, "display_night_theme_off_time", 420L);
                Log.i("DisplaySettings", "resetBrightnessSettings()");
                Settings.System.putInt(
                        context.getContentResolver(),
                        "screen_brightness",
                        context.getResources()
                                .getInteger(
                                        R.integer
                                                .config_wait_for_datagram_sending_response_timeout_millis));
                if (Rune.SUPPORT_EXTRA_BRIGHT) {
                    Settings.Secure.putInt(
                            context.getContentResolver(), "screen_extra_brightness", 0);
                }
                if (Rune.isShopDemo(context)) {
                    Log.i("DisplaySettings", "reset to retail mode value");
                    Settings.System.putIntForUser(
                            context.getContentResolver(),
                            "screen_brightness",
                            context.getResources()
                                    .getInteger(
                                            R.integer
                                                    .config_wait_for_satellite_enabling_response_timeout_millis),
                            -2);
                    Settings.System.putIntForUser(
                            context.getContentResolver(), "screen_brightness_mode", 0, -2);
                    if (Rune.SUPPORT_LARGE_FRONT_SUBDISPLAY) {
                        Settings.System.putIntForUser(
                                context.getContentResolver(), "sub_screen_brightness_mode", 0, -2);
                    }
                } else {
                    if (Rune.SUPPORT_LARGE_FRONT_SUBDISPLAY) {
                        Settings.System.putInt(
                                context.getContentResolver(), "sub_screen_brightness_mode", 1);
                    }
                    int autoBrightnessDefaultValue =
                            SecDisplayUtils.getAutoBrightnessDefaultValue(context);
                    Log.i("DisplaySettings", "automaticAvailable= " + autoBrightnessDefaultValue);
                    Settings.System.putInt(
                            context.getContentResolver(),
                            "screen_brightness_mode",
                            autoBrightnessDefaultValue);
                }
                Settings.System.putInt(contentResolver, "sec_display_preset_index", 2);
                Settings.System.putInt(contentResolver, "sec_display_temperature_red", 0);
                Settings.System.putInt(contentResolver, "sec_display_temperature_blue", 0);
                Settings.System.putInt(contentResolver, "sec_display_temperature_green", 0);
                if (Rune.supportVividness()) {
                    Settings.System.putInt(contentResolver, "vividness_intensity", 0);
                }
                int i13 = Settings.System.getInt(contentResolver, "screen_off_timeout_backup", 0);
                if (i13 > 0) {
                    Settings.System.putInt(contentResolver, "screen_off_timeout", i13);
                } else {
                    Settings.System.putInt(contentResolver, "screen_off_timeout", 30000);
                }
                Settings.System.putInt(contentResolver, "blue_light_filter", 0);
                Settings.System.putInt(contentResolver, "blue_light_filter_opacity", 5);
                if (Rune.supportBlueLightFilterAdaptiveMode()) {
                    Settings.System.putInt(contentResolver, "blue_light_filter_adaptive_mode", 1);
                    Settings.System.putInt(contentResolver, "blue_light_filter_scheduled", 0);
                } else {
                    Settings.System.putInt(contentResolver, "blue_light_filter_scheduled", 1);
                }
                Settings.System.putInt(contentResolver, "blue_light_filter_type", 0);
                Settings.System.putLong(contentResolver, "blue_light_filter_on_time", 1140L);
                Settings.System.putLong(contentResolver, "blue_light_filter_off_time", 420L);
                if (SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM")
                        > 0) {
                    Settings.System.putInt(contentResolver, "blue_light_filter_night_dim", 0);
                }
                if (Rune.isShopDemo(context)) {
                    Intent intent = new Intent();
                    intent.setComponent(
                            new ComponentName(
                                    "com.samsung.android.bluelightfilter",
                                    "com.samsung.android.bluelightfilter.BlueLightFilterService"));
                    intent.putExtra("BLUE_LIGHT_FILTER_SERVICE_TYPE", 25);
                    context.startService(intent);
                }
                if (SecDisplayUtils.getChargingInfoAlways(context) >= 0) {
                    if (context.getPackageManager()
                            .hasSystemFeature("com.samsung.feature.aod.ramless")) {
                        Log.d(
                                "DisplaySettings",
                                "show charging info default off for ramless models");
                        SecDisplayUtils.setChargingInfoAlways(context, 0);
                    } else {
                        SecDisplayUtils.setChargingInfoAlways(context, 1);
                    }
                }
                Settings.Secure.putInt(
                        contentResolver,
                        "screensaver_enabled",
                        context.getResources()
                                        .getBoolean(R.bool.config_eap_sim_based_auth_supported)
                                ? 1
                                : 0);
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SETTINGS_SUPPORT_DOCK")) {
                    Settings.Secure.putInt(
                            contentResolver,
                            "screensaver_activate_on_dock",
                            context.getResources()
                                            .getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser)
                                    ? 1
                                    : 0);
                    Settings.Secure.putInt(
                            contentResolver,
                            "screensaver_activate_on_sleep",
                            context.getResources().getBoolean(R.bool.config_dreamsSupported)
                                    ? 1
                                    : 0);
                } else {
                    Settings.Secure.putInt(
                            contentResolver,
                            "screensaver_activate_on_dock",
                            context.getResources().getBoolean(R.bool.config_dreamsSupported)
                                    ? 1
                                    : 0);
                    Settings.Secure.putInt(
                            contentResolver,
                            "screensaver_activate_on_sleep",
                            context.getResources()
                                            .getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser)
                                    ? 1
                                    : 0);
                }
                Settings.Secure.putString(
                        contentResolver,
                        "screensaver_components",
                        "com.android.dreams.basic/com.android.dreams.basic.Colors");
                if (Rune.supportNavigationBar()) {
                    ContentResolver contentResolver2 = context.getContentResolver();
                    Log.i("DisplaySettings", "resetNavigationBarSettings");
                    Settings.Global.putInt(
                            contentResolver2,
                            "navigation_bar_gesture_while_hidden",
                            NavigationBarSettingsUtil.isGestureDefault() ? 1 : 0);
                    Settings.Global.putInt(
                            contentResolver2, "navigation_bar_gesture_detail_type", 1);
                    Settings.Global.putInt(contentResolver2, "navigation_bar_gesture_hint", 1);
                    Settings.Global.putInt(
                            contentResolver2, "navigation_bar_back_gesture_sensitivity", 1);
                    Settings.Global.putInt(contentResolver2, "navigationbar_key_order", 0);
                    Settings.Global.putInt(contentResolver2, "navigationbar_key_position", 2);
                    Settings.Global.putInt(
                            contentResolver2, "navigation_bar_block_gestures_with_spen", 0);
                    Settings.Global.putInt(
                            contentResolver2, "navigation_bar_button_to_hide_keyboard", 1);
                    Settings.Global.putFloat(contentResolver2, "bottom_gesture_inset_scale", 1.0f);
                    Settings.Secure.putFloat(
                            contentResolver2, "back_gesture_inset_scale_left", 1.0f);
                    Settings.Secure.putFloat(
                            contentResolver2, "back_gesture_inset_scale_right", 1.0f);
                    Settings.Global.putInt(
                            contentResolver2, "navigationbar_switch_apps_when_hint_hidden", 0);
                    Settings.Secure.putInt(contentResolver2, "assist_touch_gesture_enabled", 1);
                    Settings.Secure.putInt(contentResolver2, "assist_long_press_home_enabled", 1);
                    if (NavigationBarSettingsUtil.isSupportSearcle()) {
                        Settings.Secure.putInt(contentResolver2, "touch_and_hold_to_search", 1);
                    }
                }
                if (Rune.supportTaskBar(context)) {
                    Settings.Global.putInt(
                            context.getContentResolver(),
                            TaskBarPreferenceController.KEY_TASK_BAR_SETTING,
                            i7 ^ 1);
                    Settings.Global.putInt(
                            context.getContentResolver(), "taskbar_max_recent_count", 2);
                    Settings.Global.putInt(context.getContentResolver(), "task_bar_type", 0);
                }
            } catch (RemoteException unused) {
                Log.w("DisplaySettings", "windowManager API exception!!!");
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public final Uri LOW_POWER_MODE_ENABLED;
        public final Uri POWER_SAVING_TURN_ON_DARK_MODE_ENABLED;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.LOW_POWER_MODE_ENABLED = Settings.Global.getUriFor("low_power");
            this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED =
                    Settings.Global.getUriFor("pms_settings_dark_mode_enabled");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri.equals(this.LOW_POWER_MODE_ENABLED)
                    || uri.equals(this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED)) {
                DisplaySettings displaySettings = DisplaySettings.this;
                if (displaySettings.mLayoutSecDarkMode != null) {
                    if (Utils.isMediumPowerSavingModeEnabled(displaySettings.mContext)
                            && Settings.Global.getInt(
                                            displaySettings.mContext.getContentResolver(),
                                            "pms_settings_dark_mode_enabled",
                                            -1)
                                    == 1) {
                        DisplaySettings.this.mLayoutSecDarkMode.setVisibility(0);
                    } else {
                        DisplaySettings.this.mLayoutSecDarkMode.setVisibility(8);
                    }
                }
            }
        }

        public final void setListening(boolean z) {
            if (!z) {
                DisplaySettings.this.mContext.getContentResolver().unregisterContentObserver(this);
            } else {
                DisplaySettings.this
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(this.LOW_POWER_MODE_ENABLED, false, this);
                DisplaySettings.this
                        .mContext
                        .getContentResolver()
                        .registerContentObserver(
                                this.POWER_SAVING_TURN_ON_DARK_MODE_ENABLED, false, this);
            }
        }
    }

    public static List buildPreferenceControllers(Context context, Lifecycle lifecycle) {
        ArrayList arrayList = new ArrayList();
        SecBrightnessPreferenceController secBrightnessPreferenceController =
                new SecBrightnessPreferenceController(context, "secbrightness", lifecycle);
        secBrightnessPreferenceController.mContext = context;
        arrayList.add(secBrightnessPreferenceController);
        SecSubBrightnessPreferenceController secSubBrightnessPreferenceController =
                new SecSubBrightnessPreferenceController(context, "secfrontbrightness", lifecycle);
        secSubBrightnessPreferenceController.mContext = context;
        arrayList.add(secSubBrightnessPreferenceController);
        arrayList.add(new SecOutDoorModePreferenceController(context, "outdoor_mode", lifecycle));
        arrayList.add(new NavigationBarPreferenceController(context, lifecycle));
        arrayList.add(
                new OuterScreenAutoLockPreferenceController(
                        context, "sub_lcd_auto_lock", lifecycle));
        arrayList.add(
                new NightTimeKeyboardModePreferenceController(
                        context, "key_night_mode", lifecycle));
        SecScrollFilterForChromePreferenceController secScrollFilterForChromePreferenceController =
                new SecScrollFilterForChromePreferenceController(
                        context, "sem_scroll_filter_for_chrome", lifecycle);
        secScrollFilterForChromePreferenceController.mContext = context;
        arrayList.add(secScrollFilterForChromePreferenceController);
        arrayList.add(new EasyModePreferenceController(context, "easy_mode", lifecycle));
        arrayList.add(
                new KeyboardBacklightPreferenceController(context, "key_backlight", lifecycle));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, getSettingsLifecycle());
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "DisplaySettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        getContext();
        Signature[] signatureArr = SecDisplayUtils.SIGNATURES;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return 46;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_display_settings;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return SemEmergencyManager.isEmergencyMode(context)
                ? "top_level_display_upsm"
                : "top_level_display";
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
    }

    /* JADX WARN: Not initialized variable reg: 10, insn: 0x00cc: MOVE (r9 I:??[OBJECT, ARRAY]) = (r10 I:??[OBJECT, ARRAY]) (LINE:205), block:B:69:0x00cc */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r19) {
        /*
            Method dump skipped, instructions count: 387
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.DisplaySettings.onCreate(android.os.Bundle):void");
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        SettingsObserver settingsObserver = this.mSettingsObserver;
        if (settingsObserver != null) {
            settingsObserver.setListening(false);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0154, code lost:

       if ((r7 != null ? "1".equals(r7.split(",")[0]) : true) == false) goto L49;
    */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0160, code lost:

       if (r9 == false) goto L53;
    */
    /* JADX WARN: Removed duplicated region for block: B:50:0x019d  */
    /* JADX WARN: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume() {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.settings.DisplaySettings.onResume():void");
    }
}
