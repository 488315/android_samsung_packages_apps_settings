package com.android.settings.applications.manageapplications;

import com.android.settings.R;
import com.android.settings.applications.AppStateAlarmsAndRemindersBridge;
import com.android.settings.applications.AppStateAppBatteryUsageBridge;
import com.android.settings.applications.AppStateClonedAppsBridge;
import com.android.settings.applications.AppStateInstallAppsBridge;
import com.android.settings.applications.AppStateLocaleBridge;
import com.android.settings.applications.AppStateLongBackgroundTasksBridge;
import com.android.settings.applications.AppStateManageExternalStorageBridge;
import com.android.settings.applications.AppStateMediaManagementAppsBridge;
import com.android.settings.applications.AppStateNotificationBridge;
import com.android.settings.applications.AppStateOverlayBridge;
import com.android.settings.applications.AppStatePowerBridge;
import com.android.settings.applications.AppStateTurnScreenOnBridge;
import com.android.settings.applications.AppStateUsageBridge;
import com.android.settings.applications.AppStateWriteSettingsBridge;
import com.android.settings.nfc.AppStateNfcTagAppsBridge;
import com.android.settings.wifi.AppStateChangeWifiStateBridge;
import com.android.settingslib.applications.ApplicationsState;

import com.samsung.android.settings.applications.AppStateManageFullScreenIntentsBridge;
import com.samsung.android.settings.applications.AppStateMediaRoutingAppsBridge;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppFilterRegistry {
    public static AppFilterRegistry sRegistry;
    public final AppFilterItem[] mFilters;

    public AppFilterRegistry() {
        AppFilterItem[] appFilterItemArr = new AppFilterItem[34];
        this.mFilters = appFilterItemArr;
        ApplicationsState.AnonymousClass30 anonymousClass30 =
                AppStatePowerBridge.FILTER_POWER_ALLOWLISTED;
        ApplicationsState.AnonymousClass15 anonymousClass15 = ApplicationsState.FILTER_ALL_ENABLED;
        appFilterItemArr[0] =
                new AppFilterItem(
                        new ApplicationsState.AnonymousClass30(anonymousClass30, anonymousClass15),
                        0,
                        R.string.high_power_filter_on);
        appFilterItemArr[1] =
                new AppFilterItem(
                        new ApplicationsState.AnonymousClass30(
                                ApplicationsState.FILTER_WITHOUT_DISABLED_UNTIL_USED,
                                anonymousClass15),
                        1,
                        R.string.filter_all_apps);
        appFilterItemArr[4] =
                new AppFilterItem(ApplicationsState.FILTER_EVERYTHING, 4, R.string.filter_all_apps);
        appFilterItemArr[31] =
                new AppFilterItem(
                        ApplicationsState.FILTER_INSTALLED_APP_BY_USER,
                        31,
                        R.string.sec_installed_by_user);
        appFilterItemArr[5] = new AppFilterItem(anonymousClass15, 5, R.string.filter_enabled_apps);
        appFilterItemArr[7] =
                new AppFilterItem(
                        ApplicationsState.FILTER_DISABLED, 7, R.string.filter_apps_disabled);
        appFilterItemArr[6] =
                new AppFilterItem(
                        ApplicationsState.FILTER_INSTANT, 6, R.string.filter_instant_apps);
        appFilterItemArr[2] =
                new AppFilterItem(
                        AppStateNotificationBridge.FILTER_APP_NOTIFICATION_RECENCY,
                        2,
                        R.string.sort_order_recent_notification);
        appFilterItemArr[3] =
                new AppFilterItem(
                        AppStateNotificationBridge.FILTER_APP_NOTIFICATION_FREQUENCY,
                        3,
                        R.string.sort_order_frequent_notification);
        appFilterItemArr[8] =
                new AppFilterItem(ApplicationsState.FILTER_PERSONAL, 8, R.string.category_personal);
        appFilterItemArr[9] =
                new AppFilterItem(ApplicationsState.FILTER_WORK, 9, R.string.category_work);
        appFilterItemArr[10] =
                new AppFilterItem(
                        AppStateUsageBridge.FILTER_APP_USAGE, 10, R.string.filter_all_apps);
        appFilterItemArr[11] =
                new AppFilterItem(
                        AppStateOverlayBridge.FILTER_SYSTEM_ALERT_WINDOW,
                        11,
                        R.string.filter_overlay_apps);
        appFilterItemArr[33] =
                new AppFilterItem(
                        AppStateMediaRoutingAppsBridge.FILTER_MEDIA_ROUTING,
                        33,
                        R.string.media_routing_control_title);
        appFilterItemArr[32] =
                new AppFilterItem(
                        AppStateManageFullScreenIntentsBridge.FILTER_USE_FULL_SCREEN_INTENT,
                        32,
                        R.string.sec_full_screen_intent_title);
        appFilterItemArr[12] =
                new AppFilterItem(
                        AppStateWriteSettingsBridge.FILTER_WRITE_SETTINGS,
                        12,
                        R.string.filter_write_settings_apps);
        appFilterItemArr[13] =
                new AppFilterItem(
                        AppStateInstallAppsBridge.FILTER_APP_SOURCES,
                        13,
                        R.string.filter_install_sources_apps);
        appFilterItemArr[15] =
                new AppFilterItem(
                        AppStateChangeWifiStateBridge.FILTER_CHANGE_WIFI_STATE,
                        15,
                        R.string.filter_write_settings_apps);
        appFilterItemArr[16] =
                new AppFilterItem(
                        AppStateNotificationBridge.FILTER_APP_NOTIFICATION_BLOCKED,
                        16,
                        R.string.filter_notif_blocked_apps);
        appFilterItemArr[17] =
                new AppFilterItem(
                        AppStateManageExternalStorageBridge.FILTER_MANAGE_EXTERNAL_STORAGE,
                        17,
                        R.string.filter_manage_external_storage);
        appFilterItemArr[18] =
                new AppFilterItem(
                        AppStateAlarmsAndRemindersBridge.FILTER_CLOCK_APPS,
                        18,
                        R.string.alarms_and_reminders_title);
        appFilterItemArr[19] =
                new AppFilterItem(
                        AppStateMediaManagementAppsBridge.FILTER_MEDIA_MANAGEMENT_APPS,
                        19,
                        R.string.media_management_apps_title);
        appFilterItemArr[20] =
                new AppFilterItem(
                        AppStateLocaleBridge.FILTER_APPS_LOCALE,
                        20,
                        R.string.app_locale_picker_title);
        appFilterItemArr[21] =
                new AppFilterItem(
                        AppStateAppBatteryUsageBridge.FILTER_BATTERY_UNRESTRICTED_APPS,
                        21,
                        R.string.filter_battery_unrestricted_title);
        appFilterItemArr[22] =
                new AppFilterItem(
                        AppStateAppBatteryUsageBridge.FILTER_BATTERY_OPTIMIZED_APPS,
                        22,
                        R.string.filter_battery_optimized_title);
        appFilterItemArr[23] =
                new AppFilterItem(
                        AppStateAppBatteryUsageBridge.FILTER_BATTERY_RESTRICTED_APPS,
                        23,
                        R.string.filter_battery_restricted_title);
        appFilterItemArr[24] =
                new AppFilterItem(
                        AppStateLongBackgroundTasksBridge.FILTER_LONG_JOBS_APPS,
                        24,
                        R.string.long_background_tasks_title);
        appFilterItemArr[25] =
                new AppFilterItem(
                        AppStateClonedAppsBridge.FILTER_APPS_CLONE,
                        25,
                        R.string.cloned_apps_dashboard_title);
        appFilterItemArr[26] =
                new AppFilterItem(
                        AppStateNfcTagAppsBridge.FILTER_APPS_NFC_TAG,
                        26,
                        R.string.change_nfc_tag_apps_title);
        appFilterItemArr[27] =
                new AppFilterItem(
                        AppStateTurnScreenOnBridge.FILTER_TURN_SCREEN_ON_APPS,
                        27,
                        R.string.turn_screen_on_title);
        appFilterItemArr[28] =
                new AppFilterItem(
                        AppStateNotificationBridge.FILTER_APP_NOTI_ALL,
                        28,
                        R.string.filter_all_apps);
        appFilterItemArr[29] =
                new AppFilterItem(
                        ApplicationsState.FILTER_APP_SETTING, 29, R.string.filter_all_apps);
        appFilterItemArr[30] =
                new AppFilterItem(
                        ApplicationsState.FILTER_MANAGE_UNKNOWN_SOURCE_APPS,
                        30,
                        R.string.sec_manage_unknown_app);
    }

    public static int getDefaultFilterType(int i) {
        if (i == 1) {
            return 28;
        }
        if (i == 101) {
            return 29;
        }
        switch (i) {
            case 4:
                return 10;
            case 5:
                return 0;
            case 6:
                return 11;
            case 7:
                return 12;
            case 8:
                return 13;
            default:
                switch (i) {
                    case 10:
                        return 15;
                    case 11:
                        return 17;
                    case 12:
                        return 18;
                    case 13:
                        return 19;
                    case 14:
                        return 20;
                    case 15:
                        return 22;
                    case 16:
                        return 24;
                    case 17:
                        return 25;
                    case 18:
                        return 26;
                    case 19:
                        return 27;
                    default:
                        switch (i) {
                            case 103:
                                return 30;
                            case 104:
                                return 32;
                            case 105:
                                return 33;
                            default:
                                return 4;
                        }
                }
        }
    }

    public static AppFilterRegistry getInstance() {
        if (sRegistry == null) {
            sRegistry = new AppFilterRegistry();
        }
        return sRegistry;
    }
}
