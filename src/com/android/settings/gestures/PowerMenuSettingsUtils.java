package com.android.settings.gestures;

import android.R;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PowerMenuSettingsUtils {
    public static final Uri POWER_BUTTON_LONG_PRESS_URI =
            Settings.Global.getUriFor("power_button_long_press");
    public final Context mContext;
    public final SettingsObserver mSettingsObserver =
            new SettingsObserver(new Handler(Looper.getMainLooper()));

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingsObserver extends ContentObserver {
        public SettingsStateCallback mCallback;

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            SettingsStateCallback settingsStateCallback = this.mCallback;
            if (settingsStateCallback != null) {
                settingsStateCallback.onChange(uri);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface SettingsStateCallback {
        void onChange(Uri uri);
    }

    public PowerMenuSettingsUtils(Context context) {
        this.mContext = context;
    }

    public static boolean isLongPressPowerForAssistantEnabled(Context context) {
        return Settings.Global.getInt(
                        context.getContentResolver(),
                        "power_button_long_press",
                        context.getResources()
                                .getInteger(R.integer.config_networkPolicyDefaultWarning))
                == 5;
    }

    public static boolean isLongPressPowerSettingAvailable(Context context) {
        if (!context.getResources().getBoolean(R.bool.config_multiuserVisibleBackgroundUsers)) {
            return false;
        }
        int integer =
                context.getResources().getInteger(R.integer.config_networkPolicyDefaultWarning);
        return integer == 1 || integer == 5;
    }

    public final void registerObserver(SettingsStateCallback settingsStateCallback) {
        SettingsObserver settingsObserver = this.mSettingsObserver;
        settingsObserver.mCallback = settingsStateCallback;
        this.mContext
                .getContentResolver()
                .registerContentObserver(POWER_BUTTON_LONG_PRESS_URI, true, settingsObserver);
    }

    public final void unregisterObserver() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }
}
