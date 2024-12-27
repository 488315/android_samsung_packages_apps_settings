package com.samsung.android.settings.navigationbar;

import android.content.Context;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.Utils;

import com.sec.ims.configuration.DATA;

import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class NavigationBarGestureSettingsUtil {
    public static final Map previewAnimations =
            MapsKt__MapsKt.mapOf(
                    new Pair("0000", Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_nobar)),
                    new Pair("0001", Integer.valueOf(R.raw.navigationbar_swipe_from_bottom)),
                    new Pair(
                            "0010",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_rtl_nobar)),
                    new Pair("0011", Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_rtl)),
                    new Pair(
                            "0100",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_reverse_nobar)),
                    new Pair(
                            "0101", Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_reverse)),
                    new Pair(
                            "0110",
                            Integer.valueOf(
                                    R.raw.navigationbar_swipe_from_bottom_reverse_rtl_nobar)),
                    new Pair(
                            "0111",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_bottom_reverse_rtl)),
                    new Pair(
                            "1x00",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_side_and_bottom_nobar)),
                    new Pair(
                            "1x01",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_side_and_bottom)),
                    new Pair(
                            "1x10",
                            Integer.valueOf(
                                    R.raw.navigationbar_swipe_from_side_and_bottom_rtl_nobar)),
                    new Pair(
                            "1x11",
                            Integer.valueOf(R.raw.navigationbar_swipe_from_side_and_bottom_rtl)));

    public static final int getPreviewAnimationResId(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (i == -1) {
            i =
                    Settings.Global.getInt(
                            context.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
        }
        String str = DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        String str2 =
                i == 1
                        ? "x"
                        : Settings.Global.getInt(
                                                context.getContentResolver(),
                                                "navigationbar_key_order",
                                                0)
                                        == 1
                                ? "1"
                                : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        String str3 = Utils.isRTL(context) ? "1" : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
        if (NavigationBarSettingsUtil.isGestureHintOn(context)) {
            str = "1";
        }
        return ((Number)
                        previewAnimations.getOrDefault(
                                i + str2 + str3 + str,
                                Integer.valueOf(R.raw.navigationbar_swipe_from_side_and_bottom)))
                .intValue();
    }
}
