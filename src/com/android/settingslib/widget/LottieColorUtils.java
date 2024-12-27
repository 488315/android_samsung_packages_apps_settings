package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.SimpleLottieValueCallback;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class LottieColorUtils {
    public static final Map DARK_TO_LIGHT_THEME_COLOR_MAP;

    static {
        HashMap hashMap = new HashMap();
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_grey800,
                hashMap,
                ".grey200",
                R.color.settingslib_color_grey400,
                ".grey600");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_grey300,
                hashMap,
                ".grey800",
                R.color.settingslib_color_grey50,
                ".grey900");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_red600,
                hashMap,
                ".red400",
                android.R.color.white,
                ".black");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_blue700,
                hashMap,
                ".blue200",
                R.color.settingslib_color_blue600,
                ".blue400");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_green600,
                hashMap,
                ".green400",
                R.color.settingslib_color_green500,
                ".green200");
        LottieColorUtils$$ExternalSyntheticOutline0.m(
                R.color.settingslib_color_red500,
                hashMap,
                ".red200",
                R.color.settingslib_color_charcoal,
                ".cream");
        DARK_TO_LIGHT_THEME_COLOR_MAP = Collections.unmodifiableMap(hashMap);
    }

    public static void applyDynamicColors(
            Context context, LottieAnimationView lottieAnimationView) {
        if ((context.getResources().getConfiguration().uiMode & 48) == 32) {
            return;
        }
        for (String str : DARK_TO_LIGHT_THEME_COLOR_MAP.keySet()) {
            final int color =
                    context.getColor(((Integer) DARK_TO_LIGHT_THEME_COLOR_MAP.get(str)).intValue());
            lottieAnimationView.lottieDrawable.addValueCallback(
                    new KeyPath("**", str, "**"),
                    LottieProperty.COLOR_FILTER,
                    new LottieAnimationView.AnonymousClass1(
                            new SimpleLottieValueCallback() { // from class:
                                                              // com.android.settingslib.widget.LottieColorUtils$$ExternalSyntheticLambda1
                                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                                public final Object getValue() {
                                    return new PorterDuffColorFilter(
                                            color, PorterDuff.Mode.SRC_ATOP);
                                }
                            }));
        }
    }
}
