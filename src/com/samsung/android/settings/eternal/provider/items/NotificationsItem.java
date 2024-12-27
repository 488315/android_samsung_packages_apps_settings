package com.samsung.android.settings.eternal.provider.items;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.lib.episode.Scene;
import com.samsung.android.lib.episode.SceneResult;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.Rune;
import com.samsung.android.util.SemLog;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NotificationsItem implements Recoverable {
    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final boolean followRestoreSkipPolicy(Scene scene) {
        return ("/Settings/Notifications/AdvancedSettings/NotificationHistory"
                                .equals(scene.mSceneKey)
                        || "/Settings/Notifications/AdvancedSettings/ShowAppIcon"
                                .equals(scene.mSceneKey)
                        || "/Settings/Notifications/SortNotifications".equals(scene.mSceneKey))
                ? false
                : true;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final List getTestScenes(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final Scene.Builder getValue(Context context, SourceInfo sourceInfo, String str) {
        boolean z;
        char c;
        Scene.Builder builder = new Scene.Builder(str);
        ContentResolver contentResolver = context.getContentResolver();
        try {
            z = true;
            switch (str.hashCode()) {
                case -2033622162:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/Effect")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -1959767158:
                    if (str.equals("/Settings/Notifications/DndDuration")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1719330724:
                    if (str.equals("/Settings/Notifications/ShowSnoozeOption")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1631554603:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/ShowEvenWhileScreenIsOff")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case -1412813778:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/FloatingIcons")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1353393033:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/ShowAppIcon")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1203253688:
                    if (str.equals(
                            "/Settings/Notifications/LockscreenSettings/ShowContentWhenUnlocked")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -1140176649:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorCustom")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case -1081695185:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/ShowNotificationCategorySettings")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -985085831:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminderSelectable")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -965202059:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedThickness")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -897238618:
                    if (str.equals("/Settings/Notifications/SortNotifications")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -726006815:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorRecent")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case -702259421:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationHistory")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 41247722:
                    if (str.equals(
                            "/Settings/Notifications/AppNotification/FavoriteAppNotifications")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 57980937:
                    if (str.equals(
                            "/Settings/Notifications/GalaxyAI/ChatAssist/SuggestResponses")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 319536809:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowVibration")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 325580352:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorType")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case 452867924:
                    if (str.equals("/Settings/Notifications/NotificationPopUpStyle")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 608520036:
                    if (str.equals("/Settings/Notifications/AppIconBadgeStyle")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 668765695:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/NetworkSpeed")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 729753320:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowReminderTime")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 1057395059:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedDuration")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 1219103237:
                    if (str.equals("/Settings/Notifications/BriefPopupSettings/ColorByKeyword")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case 1268680631:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedTransparency")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case 1492558476:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorIndex")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1738842654:
                    if (str.equals("/Settings/Notifications/AppIconBadgeShowNotifications")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1832439373:
                    if (str.equals("/Settings/Notifications/AppIconBadge")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
        } catch (Exception e) {
            SemLog.e("NotificationsItem", e.getStackTrace()[0].toString());
        }
        switch (c) {
            case 0:
                int intForUser =
                        Settings.Secure.getIntForUser(
                                contentResolver, "notification_badging", 1, -2);
                builder.setValue(Integer.valueOf(intForUser), false);
                if (intForUser != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 1:
                int i = Settings.Secure.getInt(contentResolver, "badge_app_icon_type", 0);
                builder.setValue(Integer.valueOf(i), false);
                if (i != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 2:
                int i2 =
                        Settings.Secure.getInt(
                                contentResolver, "home_show_notification_enabled", 0);
                builder.setValue(Integer.valueOf(i2), false);
                if (i2 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 3:
                int intForUser2 =
                        Settings.Secure.getIntForUser(
                                contentResolver, "show_notification_snooze", 0, -2);
                builder.setValue(Integer.valueOf(intForUser2), false);
                if (intForUser2 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 4:
                int i3 = Settings.Secure.getInt(contentResolver, "zen_duration", 0);
                builder.setValue(Integer.valueOf(i3), false);
                if (i3 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 5:
                int i4 = Settings.Secure.getInt(contentResolver, "notification_history_enabled", 1);
                builder.setValue(Integer.valueOf(i4), false);
                if (i4 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 6:
                int intForUser3 =
                        Settings.System.getIntForUser(
                                contentResolver, "notification_sort_order", 1, -2);
                builder.setValue(Integer.valueOf(intForUser3), false);
                if (intForUser3 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 7:
                int intForUser4 =
                        Settings.Secure.getIntForUser(
                                contentResolver, "notification_bubbles", 1, -2);
                builder.setValue(Integer.valueOf(intForUser4), false);
                if (intForUser4 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\b':
                if (SemCscFeature.getInstance()
                        .getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false)) {
                    int i5 = Settings.System.getInt(contentResolver, "network_speed", 0);
                    builder.setValue(Integer.valueOf(i5), false);
                    if (i5 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case '\t':
                int i6 = Settings.System.getInt(contentResolver, "show_notification_app_icon", 1);
                SemLog.d("NotificationsItem", "B -> KEY_NOTIFICATION_SHOW_APP_ICON: " + i6);
                builder.setValue(Integer.valueOf(i6), false);
                if (i6 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\n':
                int intForUser5 =
                        Settings.Secure.getIntForUser(
                                contentResolver, "show_notification_category_setting", 0, -2);
                SemLog.d(
                        "NotificationsItem",
                        "B -> KEY_NOTIFICATION_SHOW_CATEGORY_SETTING: " + intForUser5);
                builder.setValue(Integer.valueOf(intForUser5), false);
                if (intForUser5 != 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 11:
                int i7 =
                        Settings.Secure.getInt(
                                contentResolver,
                                "notification_panel_show_favorite_app_notifications",
                                0);
                SemLog.d("NotificationsItem", "B-> KEY_SHOW_FAVORTIE_APP_NOTIFICATIONS: " + i7);
                builder.setValue(Integer.valueOf(i7), false);
                builder.setDefault(i7 == 0);
            case '\f':
                int i8 = Settings.System.getInt(contentResolver, "edge_lighting", 1);
                builder.setValue(Integer.valueOf(i8), false);
                if (i8 != 1) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case '\r':
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i9 =
                            Settings.System.getInt(
                                    contentResolver, "edge_lighting_show_condition", 0);
                    builder.setValue(Integer.valueOf(i9), false);
                    if (i9 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 14:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    String string =
                            Settings.System.getString(
                                    contentResolver, "edge_lighting_custom_text_color");
                    builder.setValue(string, true);
                    if (string != null) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 15:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    String string2 =
                            Settings.System.getString(
                                    contentResolver, "edge_lighting_style_type_str");
                    builder.setValue(string2, false);
                    builder.setDefault("preload/noframe".equals(string2));
                }
                return builder;
            case 16:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i10 =
                            Settings.System.getInt(contentResolver, "edge_lighting_color_type", 1);
                    builder.setValue(Integer.valueOf(i10), false);
                    if (i10 != 1) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 17:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i11 =
                            Settings.System.getInt(
                                    contentResolver, "edge_lighting_basic_color_index", 3);
                    builder.setValue(Integer.valueOf(i11), false);
                    if (i11 != 3) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 18:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i12 =
                            Settings.Global.getInt(contentResolver, "edgelighting_custom_color", 0);
                    builder.setValue(Integer.valueOf(i12), false);
                    if (i12 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 19:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    String string3 =
                            Settings.Global.getString(
                                    contentResolver, "edgelighting_recently_used_color");
                    builder.setValue(string3, false);
                    if (string3 != null) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 20:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i13 = Settings.System.getInt(contentResolver, "edge_lighting_thickness", 0);
                    builder.setValue(Integer.valueOf(i13), false);
                    if (i13 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 21:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i14 =
                            Settings.System.getInt(
                                    contentResolver, "edge_lighting_transparency", 0);
                    builder.setValue(Integer.valueOf(i14), false);
                    if (i14 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 22:
                if (SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                    int i15 = Settings.System.getInt(contentResolver, "edge_lighting_duration", 0);
                    builder.setValue(Integer.valueOf(i15), false);
                    if (i15 != 0) {
                        z = false;
                    }
                    builder.setDefault(z);
                }
                return builder;
            case 23:
                int i16 = Settings.System.getInt(contentResolver, "time_key", 180);
                builder.setValue(Integer.valueOf(i16), false);
                SemLog.d("NotificationsItem", "SEM_TIME_KEY: " + i16);
                return builder;
            case 24:
                int i17 =
                        Settings.System.getInt(contentResolver, "notification_reminder_vibrate", 0);
                builder.setValue(Integer.valueOf(i17), false);
                SemLog.d("NotificationsItem", "SEM_NOTIFICATION_REMINDER_VIBRATE: " + i17);
                return builder;
            case 25:
                int i18 =
                        Settings.System.getInt(
                                contentResolver, "notification_reminder_selectable", 0);
                builder.setValue(Integer.valueOf(i18), false);
                SemLog.d("NotificationsItem", "notification_reminder_selectable: " + i18);
                return builder;
            case 26:
                boolean z2 = Rune.NOTIS_LOCKSCREEN_SHOW_CONTENT_WHEN_UNLOCKED_DEFAULT_ON;
                int i19 =
                        Settings.Secure.getInt(
                                contentResolver,
                                "lock_screen_allow_private_notifications_when_unsecure",
                                z2 ? 1 : 0);
                builder.setValue(Integer.valueOf(i19), false);
                if (z2 != i19) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            case 27:
                int i20 = Settings.Global.getInt(contentResolver, "suggestion_responses", 1);
                SemLog.d(
                        "NotificationsItem",
                        "B-> KEY_GALAXY_AI_COVER_SCREEN_SUGGEST_RESPONSES: " + i20);
                builder.setValue(Integer.valueOf(i20), false);
                if (i20 == 0) {
                    z = false;
                }
                builder.setDefault(z);
                return builder;
            default:
                SemLog.d("NotificationsItem", "unknown key : ".concat(str));
                return builder;
        }
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult isOpenable(Context context, String str) {
        return null;
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final SceneResult setValue(
            Context context, String str, Scene scene, SourceInfo sourceInfo) {
        char c;
        ContentResolver contentResolver = context.getContentResolver();
        String value = scene.getValue(null, false);
        SceneResult.Builder builder = new SceneResult.Builder(str);
        builder.mResultType = SceneResult.ResultType.RESULT_OK;
        try {
            switch (str.hashCode()) {
                case -2033622162:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/Effect")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case -1959767158:
                    if (str.equals("/Settings/Notifications/DndDuration")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1719330724:
                    if (str.equals("/Settings/Notifications/ShowSnoozeOption")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1631554603:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/ShowEvenWhileScreenIsOff")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -1412813778:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/FloatingIcons")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1353393033:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/ShowAppIcon")) {
                        c = 20;
                        break;
                    }
                    c = 65535;
                    break;
                case -1203253688:
                    if (str.equals(
                            "/Settings/Notifications/LockscreenSettings/ShowContentWhenUnlocked")) {
                        c = 26;
                        break;
                    }
                    c = 65535;
                    break;
                case -1140176649:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorCustom")) {
                        c = 14;
                        break;
                    }
                    c = 65535;
                    break;
                case -1081695185:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/ShowNotificationCategorySettings")) {
                        c = 21;
                        break;
                    }
                    c = 65535;
                    break;
                case -985085831:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminderSelectable")) {
                        c = 25;
                        break;
                    }
                    c = 65535;
                    break;
                case -965202059:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedThickness")) {
                        c = 16;
                        break;
                    }
                    c = 65535;
                    break;
                case -897238618:
                    if (str.equals("/Settings/Notifications/SortNotifications")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -726006815:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorRecent")) {
                        c = 15;
                        break;
                    }
                    c = 65535;
                    break;
                case -702259421:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationHistory")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 41247722:
                    if (str.equals(
                            "/Settings/Notifications/AppNotification/FavoriteAppNotifications")) {
                        c = 22;
                        break;
                    }
                    c = 65535;
                    break;
                case 57980937:
                    if (str.equals(
                            "/Settings/Notifications/GalaxyAI/ChatAssist/SuggestResponses")) {
                        c = 27;
                        break;
                    }
                    c = 65535;
                    break;
                case 319536809:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowVibration")) {
                        c = 24;
                        break;
                    }
                    c = 65535;
                    break;
                case 325580352:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorType")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 452867924:
                    if (str.equals("/Settings/Notifications/NotificationPopUpStyle")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 608520036:
                    if (str.equals("/Settings/Notifications/AppIconBadgeStyle")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 668765695:
                    if (str.equals("/Settings/Notifications/AdvancedSettings/NetworkSpeed")) {
                        c = 19;
                        break;
                    }
                    c = 65535;
                    break;
                case 729753320:
                    if (str.equals(
                            "/Settings/Notifications/AdvancedSettings/NotificationReminder/ShowReminderTime")) {
                        c = 23;
                        break;
                    }
                    c = 65535;
                    break;
                case 1057395059:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedDuration")) {
                        c = 18;
                        break;
                    }
                    c = 65535;
                    break;
                case 1219103237:
                    if (str.equals("/Settings/Notifications/BriefPopupSettings/ColorByKeyword")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case 1268680631:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/AdvancedTransparency")) {
                        c = 17;
                        break;
                    }
                    c = 65535;
                    break;
                case 1492558476:
                    if (str.equals(
                            "/Settings/Notifications/BriefPopupSettings/EdgeLightingStyle/ColorIndex")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                case 1738842654:
                    if (str.equals("/Settings/Notifications/AppIconBadgeShowNotifications")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1832439373:
                    if (str.equals("/Settings/Notifications/AppIconBadge")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            SceneResult.ResultType resultType = SceneResult.ResultType.RESULT_FAIL;
            switch (c) {
                case 0:
                    SemLog.d(
                            "NotificationsItem", "R -> KEY_NOTIFICATIONS_APP_ICON_BADGE: " + value);
                    Settings.Secure.putIntForUser(
                            contentResolver,
                            "notification_badging",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case 1:
                    Settings.Secure.putInt(
                            contentResolver,
                            "badge_app_icon_type",
                            Integer.valueOf(value).intValue());
                    break;
                case 2:
                    Settings.System.putIntForUser(
                            contentResolver,
                            "notification_sort_order",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case 3:
                    Settings.Secure.putInt(
                            contentResolver,
                            "home_show_notification_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 4:
                    Settings.Secure.putIntForUser(
                            contentResolver,
                            "show_notification_snooze",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case 5:
                    Settings.Secure.putInt(
                            contentResolver, "zen_duration", Integer.valueOf(value).intValue());
                    break;
                case 6:
                    Settings.Secure.putInt(
                            contentResolver,
                            "notification_history_enabled",
                            Integer.valueOf(value).intValue());
                    break;
                case 7:
                    Settings.Secure.putIntForUser(
                            contentResolver,
                            "notification_bubbles",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case '\b':
                    Settings.System.putIntForUser(
                            contentResolver,
                            "edge_lighting",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case '\t':
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_show_condition",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case '\n':
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putStringForUser(
                                contentResolver,
                                "edge_lighting_custom_text_color",
                                String.valueOf(scene.getValue(null, true)),
                                -2);
                        break;
                    }
                case 11:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putStringForUser(
                                contentResolver,
                                "edge_lighting_style_type_str",
                                String.valueOf(value),
                                -2);
                        break;
                    }
                case '\f':
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_color_type",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case '\r':
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_basic_color_index",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case 14:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.Global.putInt(
                                contentResolver,
                                "edgelighting_custom_color",
                                Integer.valueOf(value).intValue());
                        break;
                    }
                case 15:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.Global.putInt(
                                contentResolver,
                                "edgelighting_recently_used_color",
                                Integer.valueOf(value).intValue());
                        break;
                    }
                case 16:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_thickness",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case 17:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_transparency",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case 18:
                    if (!SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_SYSTEMUI_SUPPORT_BRIEF_NOTIFICATION")) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putIntForUser(
                                contentResolver,
                                "edge_lighting_duration",
                                Integer.valueOf(value).intValue(),
                                -2);
                        break;
                    }
                case 19:
                    if (!SemCscFeature.getInstance()
                            .getBoolean(
                                    "CscFeature_Common_SupportZProjectFunctionInGlobal", false)) {
                        builder.mResultType = resultType;
                        builder.mErrorType = SceneResult.ErrorType.NOT_SUPPORTED;
                        break;
                    } else {
                        Settings.System.putInt(
                                contentResolver,
                                "network_speed",
                                Integer.valueOf(value).intValue());
                        break;
                    }
                case 20:
                    SemLog.d("NotificationsItem", "R -> KEY_NOTIFICATION_SHOW_APP_ICON: " + value);
                    Settings.System.putInt(
                            contentResolver,
                            "show_notification_app_icon",
                            Integer.valueOf(value).intValue());
                    break;
                case 21:
                    SemLog.d(
                            "NotificationsItem",
                            "R -> KEY_NOTIFICATION_SHOW_CATEGORY_SETTING: " + value);
                    Settings.Secure.putIntForUser(
                            contentResolver,
                            "show_notification_category_setting",
                            Integer.valueOf(value).intValue(),
                            -2);
                    break;
                case 22:
                    SemLog.d(
                            "NotificationsItem",
                            "R -> KEY_SHOW_FAVORTIE_APP_NOTIFICATIONS: " + value);
                    Settings.Secure.putInt(
                            contentResolver,
                            "notification_panel_show_favorite_app_notifications",
                            Integer.valueOf(value).intValue());
                case 23:
                    Settings.System.putInt(
                            contentResolver, "time_key", Integer.valueOf(value).intValue());
                    SemLog.d("NotificationsItem", "SEM_TIME_KEY: " + value);
                    break;
                case 24:
                    Settings.System.putInt(
                            contentResolver,
                            "notification_reminder_vibrate",
                            Integer.valueOf(value).intValue());
                    SemLog.d("NotificationsItem", "SEM_NOTIFICATION_REMINDER_VIBRATE: " + value);
                    break;
                case 25:
                    Settings.System.putInt(
                            contentResolver,
                            "notification_reminder_selectable",
                            Integer.valueOf(value).intValue());
                    SemLog.d("NotificationsItem", "notification_reminder_selectable: " + value);
                    break;
                case 26:
                    Settings.Secure.putInt(
                            contentResolver,
                            "lock_screen_allow_private_notifications_when_unsecure",
                            Integer.valueOf(value).intValue());
                    break;
                case 27:
                    SemLog.d(
                            "NotificationsItem",
                            "R -> KEY_GALAXY_AI_COVER_SCREEN_SUGGEST_RESPONSES: " + value);
                    Settings.Global.putInt(
                            contentResolver,
                            "suggestion_responses",
                            Integer.valueOf(value).intValue());
                    break;
            }
        } catch (Exception e) {
            SemLog.e("NotificationsItem", e.getStackTrace()[0].toString());
        }
        return builder.build();
    }

    @Override // com.samsung.android.settings.eternal.provider.items.Recoverable
    public final void open(Context context, String str) {}
}
