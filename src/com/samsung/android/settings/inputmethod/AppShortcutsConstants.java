package com.samsung.android.settings.inputmethod;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AppShortcutsConstants {
    public static final List SETTINGS_LIST =
            new ArrayList(
                    Arrays.asList(
                            "app_shortcuts_command_a",
                            "app_shortcuts_command_b",
                            "app_shortcuts_command_c",
                            "app_shortcuts_command_d",
                            "app_shortcuts_command_e",
                            "app_shortcuts_command_f",
                            "app_shortcuts_command_h",
                            "app_shortcuts_command_i",
                            "app_shortcuts_command_j",
                            "app_shortcuts_command_k",
                            "app_shortcuts_command_m",
                            "app_shortcuts_command_p",
                            "app_shortcuts_command_r",
                            "app_shortcuts_command_s",
                            "app_shortcuts_command_z"));
    public static final List DEFAULT_SETTINGS_LIST =
            new ArrayList(
                    Arrays.asList(
                            "android.app.role.ASSISTANT",
                            "android.app.role.BROWSER",
                            "android.intent.category.APP_CONTACTS",
                            "android.app.role.HOME",
                            "android.intent.category.APP_EMAIL",
                            "com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity",
                            "android.app.role.HOME",
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                            "com.samsung.android.app.tips",
                            "android.intent.category.APP_CALENDAR",
                            "android.intent.category.APP_MAPS",
                            "android.intent.category.APP_MUSIC",
                            "com.sec.android.app.myfiles",
                            "android.app.role.SMS",
                            KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG));
}
