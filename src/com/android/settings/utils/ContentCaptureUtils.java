package com.android.settings.utils;

import android.content.ComponentName;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.view.contentcapture.ContentCaptureManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ContentCaptureUtils {
    public static final int MY_USER_ID = UserHandle.myUserId();

    public static ComponentName getServiceSettingsComponentName() {
        try {
            return ContentCaptureManager.getServiceSettingsComponentName();
        } catch (RuntimeException e) {
            Log.w("ContentCaptureUtils", "Could not get service settings: " + e);
            return null;
        }
    }

    public static boolean isEnabledForUser(Context context) {
        return Settings.Secure.getIntForUser(
                        context.getContentResolver(), "content_capture_enabled", 1, MY_USER_ID)
                == 1;
    }
}
