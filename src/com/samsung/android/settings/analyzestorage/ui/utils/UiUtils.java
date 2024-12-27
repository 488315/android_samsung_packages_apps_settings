package com.samsung.android.settings.analyzestorage.ui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.FeatureManager$UiFeature;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class UiUtils {
    public static long lastClickTime = 0;
    public static int prevId = Integer.MIN_VALUE;
    public static int prevPosition = Integer.MIN_VALUE;

    public static final int getDimenSize(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (i == -1) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(i);
    }

    public static final void initStatusBar(Activity activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (FeatureManager$UiFeature.isDefaultTheme(activity)) {
            int color = activity.getColor(R.color.as_list_background_color);
            activity.getWindow().getDecorView().setBackgroundColor(color);
            activity.getWindow().setStatusBarColor(color);
            activity.getWindow().setNavigationBarColor(color);
            return;
        }
        int color2 =
                activity.getColor(R.color.sec_display_setting_seek_bar_controller_background_color);
        if (!FeatureManager$UiFeature.isDefaultTheme(activity)) {
            boolean z = Color.blue(color2) + (Color.green(color2) + Color.red(color2)) >= 552;
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(
                            z ? systemUiVisibility | 8208 : systemUiVisibility & (-8209));
        }
        activity.getWindow().setStatusBarColor(color2);
        activity.getWindow().setNavigationBarColor(color2);
    }

    public static boolean isValidClick$default(int i) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (prevId == i && prevPosition == -1) {
            long j = elapsedRealtime - lastClickTime;
            if (j < 400) {
                Log.d("UiUtils", "invalid click - " + j);
                return false;
            }
        } else {
            Log.d("UiUtils", "isValidClick()] View is different");
        }
        lastClickTime = elapsedRealtime;
        prevId = i;
        prevPosition = -1;
        return true;
    }

    public static final void setAccessibilityForWidget(
            String contentDescription, View view, String str) {
        Intrinsics.checkNotNullParameter(contentDescription, "contentDescription");
        Intrinsics.checkNotNullParameter(view, "view");
        view.setContentDescription(contentDescription);
        ViewCompat.setAccessibilityDelegate(view, new RoleDescriptionAccessibilityDelegate(str));
    }

    public static final void setFlexibleSpacing(View view) {
        Context context = view.getContext();
        Configuration configuration = context.getResources().getConfiguration();
        Intrinsics.checkNotNull(configuration);
        int i = configuration.screenWidthDp;
        if (i > 588 && i < 960) {
            i = (int) (i * (configuration.screenHeightDp < 411 ? 1.0f : 0.86f));
        } else if (i >= 960) {
            i = 840;
        }
        float f = context.getResources().getDisplayMetrics().density;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (i * f);
        view.setLayoutParams(layoutParams);
    }

    public static void updatePadding(View view, int i, int i2, int i3) {
        Intrinsics.checkNotNullParameter(view, "view");
        Context context = view.getContext();
        Intrinsics.checkNotNull(context);
        view.setPaddingRelative(
                getDimenSize(context, R.dimen.as_viewpager_padding_horizontal),
                getDimenSize(context, i),
                getDimenSize(context, i2),
                getDimenSize(context, i3));
    }
}
