package com.android.settings.gestures;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import com.android.internal.accessibility.AccessibilityShortcutController;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OneHandedSettingsUtils {
    public static int sCurrentUserId;
    public final Context mContext;
    public final SettingsObserver mSettingsObserver;
    public static final String ONE_HANDED_MODE_TARGET_NAME =
            AccessibilityShortcutController.ONE_HANDED_COMPONENT_NAME.getShortClassName();
    public static final Uri ONE_HANDED_MODE_ENABLED_URI =
            Settings.Secure.getUriFor("one_handed_mode_enabled");
    public static final Uri SHOW_NOTIFICATION_ENABLED_URI =
            Settings.Secure.getUriFor("swipe_bottom_to_notification_enabled");
    public static final Uri SOFTWARE_SHORTCUT_ENABLED_URI =
            Settings.Secure.getUriFor("accessibility_button_targets");
    public static final Uri HARDWARE_SHORTCUT_ENABLED_URI =
            Settings.Secure.getUriFor("accessibility_shortcut_target_service");
    public static final Uri QS_SHORTCUT_ENABLED_URI =
            Settings.Secure.getUriFor("accessibility_qs_targets");

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum OneHandedTimeout {
        /* JADX INFO: Fake field, exist only in values array */
        NEVER(0),
        /* JADX INFO: Fake field, exist only in values array */
        SHORT(4),
        MEDIUM(8),
        /* JADX INFO: Fake field, exist only in values array */
        LONG(12);

        private final int mValue;

        OneHandedTimeout(int i) {
            this.mValue = i;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public TogglesCallback mCallback;

        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            TogglesCallback togglesCallback = this.mCallback;
            if (togglesCallback != null) {
                togglesCallback.onChange(uri);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TogglesCallback {
        void onChange(Uri uri);
    }

    public OneHandedSettingsUtils(Context context) {
        this.mContext = context;
        sCurrentUserId = UserHandle.myUserId();
        this.mSettingsObserver = new SettingsObserver(new Handler(Looper.getMainLooper()));
    }

    public static boolean canEnableController(Context context) {
        if (!isOneHandedModeEnabled(context)
                || Settings.Secure.getIntForUser(
                                context.getContentResolver(), "navigation_mode", 2, sCurrentUserId)
                        == 0) {
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            context.getContentResolver(),
                            "accessibility_button_targets",
                            sCurrentUserId);
            boolean isEmpty = TextUtils.isEmpty(stringForUser);
            String str = ONE_HANDED_MODE_TARGET_NAME;
            if (isEmpty || !stringForUser.contains(str)) {
                String stringForUser2 =
                        Settings.Secure.getStringForUser(
                                context.getContentResolver(),
                                "accessibility_shortcut_target_service",
                                sCurrentUserId);
                if (TextUtils.isEmpty(stringForUser2) || !stringForUser2.contains(str)) {
                    String stringForUser3 =
                            Settings.Secure.getStringForUser(
                                    context.getContentResolver(),
                                    "accessibility_qs_targets",
                                    sCurrentUserId);
                    if (TextUtils.isEmpty(stringForUser3) || !stringForUser3.contains(str)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isOneHandedModeEnabled(Context context) {
        return Settings.Secure.getIntForUser(
                        context.getContentResolver(), "one_handed_mode_enabled", 0, sCurrentUserId)
                == 1;
    }

    public static boolean isSupportOneHandedMode() {
        return SystemProperties.getBoolean("ro.support_one_handed_mode", false);
    }

    public static boolean isSwipeDownNotificationEnabled(Context context) {
        return Settings.Secure.getIntForUser(
                        context.getContentResolver(),
                        "swipe_bottom_to_notification_enabled",
                        0,
                        sCurrentUserId)
                == 1;
    }

    public final void registerToggleAwareObserver(TogglesCallback togglesCallback) {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        ContentResolver contentResolver = OneHandedSettingsUtils.this.mContext.getContentResolver();
        contentResolver.registerContentObserver(
                ONE_HANDED_MODE_ENABLED_URI, true, settingsObserver);
        contentResolver.registerContentObserver(
                SHOW_NOTIFICATION_ENABLED_URI, true, settingsObserver);
        contentResolver.registerContentObserver(
                SOFTWARE_SHORTCUT_ENABLED_URI, true, settingsObserver);
        contentResolver.registerContentObserver(
                HARDWARE_SHORTCUT_ENABLED_URI, true, settingsObserver);
        contentResolver.registerContentObserver(QS_SHORTCUT_ENABLED_URI, true, settingsObserver);
        settingsObserver.mCallback = togglesCallback;
    }

    public boolean setNavigationBarMode(Context context, String str) {
        return Settings.Secure.putStringForUser(
                context.getContentResolver(), "navigation_mode", str, UserHandle.myUserId());
    }

    public void setShortcutEnabled(Context context, boolean z) {
        Settings.Secure.putStringForUser(
                context.getContentResolver(),
                "accessibility_button_targets",
                z ? ONE_HANDED_MODE_TARGET_NAME : ApnSettings.MVNO_NONE,
                sCurrentUserId);
    }

    public final void unregisterToggleAwareObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }
}
