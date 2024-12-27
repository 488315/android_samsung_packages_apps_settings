package com.samsung.android.settings.notification;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecDropDownPreference;
import androidx.preference.SecPreference;
import androidx.preference.SecSwitchPreferenceScreen;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.notification.AssistantReminderPreferenceController;
import com.android.settings.notification.EmergencyBroadcastPreferenceController;
import com.android.settings.notification.NotificationBackend;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.notification.reminder.NotificationUtils;
import com.samsung.android.settings.notification.reminder.ReminderAppListInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ConfigureNotificationMoreSettings extends DashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new AnonymousClass1(R.xml.sec_configure_notification_more_settings);
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.ConfigureNotificationMoreSettings$1, reason: invalid class name */
    public final class AnonymousClass1 extends BaseSearchIndexProvider {
        @Override // com.android.settings.search.BaseSearchIndexProvider,
                  // com.android.settingslib.search.Indexable$SearchIndexProvider
        public final List getNonIndexableKeys(Context context) {
            List nonIndexableKeys = super.getNonIndexableKeys(context);
            if (!Rune.supportLedIndicator() || SemPersonaManager.isKnoxId(UserHandle.myUserId())) {
                ((ArrayList) nonIndexableKeys).add("key_simple_led_indicator_settings");
            }
            if (!SemCscFeature.getInstance()
                    .getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false)) {
                ((ArrayList) nonIndexableKeys).add("network_speed");
            }
            if (context.getResources()
                    .getBoolean(android.R.bool.config_displayWhiteBalanceAvailable)) {
                ((ArrayList) nonIndexableKeys).add("app_and_notif_cell_broadcast_settings");
            }
            return nonIndexableKeys;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.notification.ConfigureNotificationMoreSettings$2, reason: invalid class name */
    public final class AnonymousClass2 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            ContentResolver contentResolver = context.getContentResolver();
            new NotificationBackend();
            Settings.System.putInt(contentResolver, "simple_status_bar", 1);
            Settings.System.putInt(contentResolver, "display_battery_percentage", -1);
            Settings.System.putInt(contentResolver, "status_bar_show_network_information", 1);
            Settings.System.putInt(contentResolver, "network_speed", 0);
            Settings.System.putInt(contentResolver, "status_bar_show_date", 1);
            Settings.Secure.putInt(contentResolver, "notification_history_enabled", 1);
            Settings.System.putIntForUser(contentResolver, "notification_sort_order", 1, -2);
            Settings.Global.putInt(contentResolver, "notification_bubbles", 1);
            Settings.System.putInt(contentResolver, "noti_auto_more_grouping", 0);
            Settings.System.putInt(contentResolver, "show_notification_app_icon", 1);
            NotificationBackend.setNotificationAssistantGranted(
                    NotificationBackend.getDefaultNotificationAssistant());
            Settings.Secure.putIntForUser(contentResolver, "show_notification_snooze", 0, -2);
            Settings.Secure.putInt(contentResolver, "notification_badging", 1);
            Settings.Secure.putInt(contentResolver, "badge_app_icon_type", 0);
            Settings.Secure.putInt(contentResolver, "home_show_notification_enabled", 0);
            Settings.Secure.putInt(context.getContentResolver(), "brightness_on_top", 1);
            Settings.Secure.putInt(
                    context.getContentResolver(), "qspanel_media_quickcontrol_bar_available", 1);
            Settings.Secure.putInt(
                    context.getContentResolver(),
                    "qspanel_media_quickcontrol_bar_available_on_top",
                    -1);
            Settings.Secure.putInt(
                    context.getContentResolver(), "multi_sim_bar_show_on_qspanel", 1);
            Settings.Global.putString(
                    context.getContentResolver(),
                    "edgelighting_recently_used_color",
                    ApnSettings.MVNO_NONE);
            Settings.System.putStringForUser(
                    context.getContentResolver(),
                    "edge_lighting_custom_text_color",
                    ApnSettings.MVNO_NONE,
                    -2);
            if (Rune.supportLedIndicator()) {
                Settings.System.putInt(context.getContentResolver(), "led_indicator_charing", 1);
                Settings.System.putInt(
                        context.getContentResolver(), "led_indicator_low_battery", 1);
                Settings.System.putInt(
                        context.getContentResolver(), "led_indicator_missed_event", 1);
                Settings.System.putInt(
                        context.getContentResolver(), "led_indicator_voice_recording", 1);
            }
            BaseSearchIndexProvider baseSearchIndexProvider =
                    ConfigureNotificationMoreSettings.SEARCH_INDEX_DATA_PROVIDER;
            new NotificationBackend();
            int i = NotificationUtils.mInstalledAppCountInWhiteList;
            ArrayList arrayList = new ArrayList();
            ArrayList appList = NotificationUtils.getAppList(context);
            if (appList.size() > 0) {
                Iterator it = appList.iterator();
                while (it.hasNext()) {
                    ReminderAppListInfo reminderAppListInfo = (ReminderAppListInfo) it.next();
                    if (reminderAppListInfo != null && reminderAppListInfo.isSelected) {
                        arrayList.add(reminderAppListInfo.packageName);
                    }
                }
            }
            if (!arrayList.isEmpty()) {
                try {
                    NotificationBackend.sINM.setReminderEnabled(
                            UserHandle.myUserId(), false, arrayList);
                } catch (Exception e) {
                    Log.w("NotificationBackend", "Error calling NoMan", e);
                }
            }
            Settings.System.putInt(
                    context.getContentResolver(), "notification_reminder_selectable", 0);
            Settings.System.putInt(
                    context.getContentResolver(), "notification_reminder_vibrate", 0);
            Settings.System.putInt(context.getContentResolver(), "time_key", 180);
            Settings.Secure.putIntForUser(
                    context.getContentResolver(), "show_notification_category_setting", 0, -2);
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    public final List createPreferenceControllers(Context context) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.getApplication();
        }
        getSettingsLifecycle();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new EmergencyBroadcastPreferenceController(context));
        arrayList.add(
                new LedIndicatorPreferenceController(context, "key_simple_led_indicator_settings"));
        arrayList.add(new FloatingIconsNotificationPreferenceController(context, "floating_icons"));
        arrayList.add(new NotificationHistoryPreferenceController(context, "notification_history"));
        arrayList.add(
                new AssistantReminderPreferenceController(context, "asst_notification_reminder"));
        arrayList.add(
                new ShowNotificationAppIconPreferenceController(
                        context, "show_notification_app_icon"));
        arrayList.add(
                new ShowNotificationCategoryPreferenceController(
                        context, "show_notification_category_setting"));
        arrayList.add(new NotificationSortController(context, "key_notification_sort"));
        arrayList.add(new SecFilterNotificationController(context, "auto_grouping"));
        return arrayList;
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "ConfigNotiSettings";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return FileType.SHOW;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_configure_notification_more_settings;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        Bundle arguments = getArguments();
        PreferenceCategory preferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference("status_bar_settings");
        PreferenceCategory preferenceCategory2 =
                (PreferenceCategory) preferenceScreen.findPreference("emergency_alerts_category");
        Preference findPreference = preferenceScreen.findPreference("manage_conversations");
        Preference findPreference2 = preferenceScreen.findPreference("notification_history");
        SecPreference secPreference =
                (SecPreference) preferenceScreen.findPreference("floating_icons");
        SwitchPreference switchPreference =
                (SwitchPreference)
                        preferenceScreen.findPreference("asst_capabilities_actions_replies");
        SecSwitchPreferenceScreen secSwitchPreferenceScreen =
                (SecSwitchPreferenceScreen)
                        preferenceScreen.findPreference("asst_notification_reminder");
        SwitchPreferenceCompat switchPreferenceCompat =
                (SwitchPreferenceCompat)
                        preferenceScreen.findPreference("key_simple_led_indicator_settings");
        SecDropDownPreference secDropDownPreference =
                (SecDropDownPreference) preferenceScreen.findPreference("key_notification_sort");
        SecSwitchPreferenceScreen secSwitchPreferenceScreen2 =
                (SecSwitchPreferenceScreen) preferenceScreen.findPreference("auto_grouping");
        if (arguments != null && SemPersonaManager.isSecureFolderId(UserHandle.myUserId())) {
            if (preferenceCategory != null) {
                preferenceScreen.removePreference(preferenceCategory);
            }
            if (preferenceCategory2 != null) {
                preferenceScreen.removePreference(preferenceCategory2);
            }
            if (findPreference != null) {
                preferenceScreen.removePreference(findPreference);
            }
            if (findPreference2 != null) {
                preferenceScreen.removePreference(findPreference2);
            }
            if (secPreference != null) {
                preferenceScreen.removePreference(secPreference);
            }
            if (switchPreference != null) {
                preferenceScreen.removePreference(switchPreference);
            }
            if (secSwitchPreferenceScreen != null) {
                preferenceScreen.removePreference(secSwitchPreferenceScreen);
            }
            if (switchPreferenceCompat != null) {
                preferenceScreen.removePreference(switchPreferenceCompat);
            }
            if (secDropDownPreference != null) {
                preferenceScreen.removePreference(secDropDownPreference);
            }
            if (secSwitchPreferenceScreen2 != null) {
                preferenceScreen.removePreference(secSwitchPreferenceScreen2);
            }
        }
        if (!getResources().getBoolean(android.R.bool.config_displayWhiteBalanceAvailable)
                || preferenceCategory2 == null) {
            return;
        }
        preferenceScreen.removePreference(preferenceCategory2);
    }
}
