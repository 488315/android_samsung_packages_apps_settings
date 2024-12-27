package com.google.android.material.resources;

import android.content.res.Configuration;
import android.graphics.Typeface;

import androidx.core.math.MathUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class TypefaceUtils {
    public static Typeface maybeCopyWithFontWeightAdjustment(
            Configuration configuration, Typeface typeface) {
        int i = configuration.fontWeightAdjustment;
        if (i == Integer.MAX_VALUE || i == 0 || typeface == null) {
            return null;
        }
        return Typeface.create(
                typeface,
                MathUtils.clamp(typeface.getWeight() + configuration.fontWeightAdjustment, 1, 1000),
                typeface.isItalic());
    }
}
