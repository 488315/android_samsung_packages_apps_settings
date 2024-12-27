package com.android.settings.accessibility;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FlashNotificationsUtil {
    public static final int DEFAULT_SCREEN_FLASH_COLOR =
            ScreenFlashNotificationColor.YELLOW.mColorInt;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ScreenColorNotFoundException extends Exception {}

    public static String getColorDescriptionText(Context context, int i) {
        try {
            return context.getString(getScreenColor(i).mStringRes);
        } catch (ScreenColorNotFoundException unused) {
            return ApnSettings.MVNO_NONE;
        }
    }

    public static int getFlashNotificationsState(Context context) {
        if (context == null) {
            return 0;
        }
        return ((isTorchAvailable(context)
                                && (Settings.System.getInt(
                                                context.getContentResolver(),
                                                "camera_flash_notification",
                                                0)
                                        != 0))
                        ? 1
                        : 0)
                | (Settings.System.getInt(
                                        context.getContentResolver(),
                                        "screen_flash_notification",
                                        0)
                                != 0
                        ? 2
                        : 0);
    }

    public static ScreenFlashNotificationColor getScreenColor(int i) {
        int i2 = i | EmergencyPhoneWidget.BG_COLOR;
        for (ScreenFlashNotificationColor screenFlashNotificationColor :
                ScreenFlashNotificationColor.values()) {
            if (i2 == screenFlashNotificationColor.mOpaqueColorInt) {
                return screenFlashNotificationColor;
            }
        }
        throw new ScreenColorNotFoundException();
    }

    public static boolean isTorchAvailable(Context context) {
        Integer num;
        CameraManager cameraManager = (CameraManager) context.getSystemService(CameraManager.class);
        if (cameraManager == null) {
            return false;
        }
        try {
            for (String str : cameraManager.getCameraIdList()) {
                CameraCharacteristics cameraCharacteristics =
                        cameraManager.getCameraCharacteristics(str);
                Boolean bool =
                        (Boolean)
                                cameraCharacteristics.get(
                                        CameraCharacteristics.FLASH_INFO_AVAILABLE);
                if (bool != null
                        && (num =
                                        (Integer)
                                                cameraCharacteristics.get(
                                                        CameraCharacteristics.LENS_FACING))
                                != null
                        && bool.booleanValue()
                        && num.intValue() == 1) {
                    return true;
                }
            }
        } catch (CameraAccessException unused) {
            Log.w(
                    "FlashNotificationsUtil",
                    "Failed to get valid camera for camera flash notification.");
        }
        return false;
    }
}
