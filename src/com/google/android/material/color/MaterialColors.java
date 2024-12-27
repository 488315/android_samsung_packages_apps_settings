package com.google.android.material.color;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;

import androidx.core.graphics.ColorUtils;

import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialColors {
    public static int getColor(View view, int i) {
        Context context = view.getContext();
        TypedValue resolveTypedValueOrThrow =
                MaterialAttributes.resolveTypedValueOrThrow(
                        view.getContext(), i, view.getClass().getCanonicalName());
        int i2 = resolveTypedValueOrThrow.resourceId;
        return i2 != 0 ? context.getColor(i2) : resolveTypedValueOrThrow.data;
    }

    public static boolean isColorLight(int i) {
        return i != 0 && ColorUtils.calculateLuminance(i) > 0.5d;
    }

    public static int layer(int i, int i2, float f) {
        return ColorUtils.compositeColors(
                ColorUtils.setAlphaComponent(i2, Math.round(Color.alpha(i2) * f)), i);
    }
}
