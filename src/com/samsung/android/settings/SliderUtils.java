package com.samsung.android.settings;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.util.MathUtils;

import androidx.appcompat.widget.SeslSeekBar;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SliderUtils {
    public static Locale mCachedLocale;
    public static NumberFormat mPercentFormat;

    public static CharSequence formatStateDescription(Context context, SeslSeekBar seslSeekBar) {
        Locale locale = context.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(mCachedLocale)) {
            mCachedLocale = locale;
            mPercentFormat = NumberFormat.getPercentInstance(locale);
        }
        return mPercentFormat.format(
                MathUtils.saturate(
                        MathUtils.lerpInv(
                                seslSeekBar.getMin(),
                                seslSeekBar.getMax(),
                                seslSeekBar.getProgress())));
    }
}
