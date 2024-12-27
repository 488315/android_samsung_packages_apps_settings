package com.google.android.material.appbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;
import android.view.WindowMetrics;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SeslAppBarHelper$Companion {
    public static float getAppBarProPortion(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Configuration configuration = context.getResources().getConfiguration();
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.view.WindowManager");
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        WindowMetrics currentWindowMetrics =
                ((WindowManager) systemService).getCurrentWindowMetrics();
        Intrinsics.checkNotNullExpressionValue(currentWindowMetrics, "wm.currentWindowMetrics");
        int height = currentWindowMetrics.getBounds().height();
        float deriveDimension = TypedValue.deriveDimension(1, height, displayMetrics);
        Log.d(
                "SeslAppBarHelper",
                "fullWindowHeight(dp)="
                        + deriveDimension
                        + ", fullWindowHeight(px)="
                        + height
                        + ", screenHeightDp="
                        + context.getResources().getConfiguration().screenHeightDp);
        StringBuilder sb = new StringBuilder("orientation=");
        sb.append(configuration.orientation);
        sb.append(", fullWindowHeightDp=");
        sb.append(deriveDimension);
        Log.d("SeslAppBarHelper", sb.toString());
        if (configuration.orientation != 2) {
            if (deriveDimension < 639.0f) {
                return 0.0f;
            }
            if (deriveDimension < 696.0f) {
                return 0.48f;
            }
            if (deriveDimension < 780.0f) {
                return 0.43f;
            }
            return deriveDimension < 960.0f ? 0.38f : 0.305f;
        }
        if (deriveDimension < 580.0f) {
            return 0.0f;
        }
        if (deriveDimension < 640.0f) {
            return 0.51f;
        }
        if (deriveDimension < 670.0f) {
            return 0.475f;
        }
        if (deriveDimension < 710.0f) {
            return 0.45f;
        }
        if (deriveDimension < 750.0f) {
            return 0.425f;
        }
        if (deriveDimension < 800.0f) {
            return 0.4f;
        }
        return deriveDimension < 1080.0f ? 0.37f : 0.27f;
    }
}
