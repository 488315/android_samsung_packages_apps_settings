package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.RotationUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;

import com.android.settings.R;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class UdfpsUtils {
    public static Point getPortraitTouch(
            int i, MotionEvent motionEvent, UdfpsOverlayParams udfpsOverlayParams) {
        Point point = new Point((int) motionEvent.getRawX(i), (int) motionEvent.getRawY(i));
        int i2 = udfpsOverlayParams.rotation;
        if (i2 == 1 || i2 == 3) {
            RotationUtils.rotatePoint(
                    point,
                    RotationUtils.deltaRotation(i2, 0),
                    udfpsOverlayParams.logicalDisplayWidth,
                    udfpsOverlayParams.logicalDisplayHeight);
        }
        return point;
    }

    public static float getScaleFactor(DisplayInfo displayInfo) {
        Display.Mode maximumResolutionDisplayMode =
                DisplayUtils.getMaximumResolutionDisplayMode(displayInfo.supportedModes);
        float physicalPixelDisplaySizeRatio =
                DisplayUtils.getPhysicalPixelDisplaySizeRatio(
                        maximumResolutionDisplayMode.getPhysicalWidth(),
                        maximumResolutionDisplayMode.getPhysicalHeight(),
                        displayInfo.getNaturalWidth(),
                        displayInfo.getNaturalHeight());
        if (physicalPixelDisplaySizeRatio == Float.POSITIVE_INFINITY) {
            return 1.0f;
        }
        return physicalPixelDisplaySizeRatio;
    }

    public static String onTouchOutsideOfSensorArea(
            boolean z, Context context, int i, int i2, UdfpsOverlayParams udfpsOverlayParams) {
        if (!z) {
            return null;
        }
        Resources resources = context.getResources();
        String[] strArr = {
            resources.getString(R.string.udfps_accessibility_touch_hints_left),
            resources.getString(R.string.udfps_accessibility_touch_hints_down),
            resources.getString(R.string.udfps_accessibility_touch_hints_right),
            resources.getString(R.string.udfps_accessibility_touch_hints_up)
        };
        float centerX = udfpsOverlayParams.sensorBounds.centerX() / udfpsOverlayParams.scaleFactor;
        double atan2 =
                Math.atan2((udfpsOverlayParams.sensorBounds.centerY() / r7) - i2, i - centerX);
        if (atan2 < 0.0d) {
            atan2 += 6.283185307179586d;
        }
        double d = 360.0d / 4;
        int degrees = ((int) ((((d / 2.0d) + Math.toDegrees(atan2)) % 360.0d) / d)) % 4;
        int i3 = udfpsOverlayParams.rotation;
        if (i3 == 1) {
            degrees = (degrees + 1) % 4;
        }
        if (i3 == 3) {
            degrees = (degrees + 3) % 4;
        }
        String str = strArr[degrees];
        Log.v("UdfpsUtils", "Announcing touch outside : $theStr");
        return str;
    }
}
