package com.google.android.material.elevation;

import android.content.Context;
import android.util.TypedValue;

import com.android.settings.R;

import com.google.android.material.resources.MaterialAttributes;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ElevationOverlayProvider {
    public static final int OVERLAY_ACCENT_COLOR_ALPHA = (int) Math.round(5.1000000000000005d);
    public final int colorSurface;
    public final float displayDensity;
    public final int elevationOverlayAccentColor;
    public final int elevationOverlayColor;
    public final boolean elevationOverlayEnabled;

    public ElevationOverlayProvider(Context context) {
        Integer num;
        Integer num2;
        boolean resolveBoolean =
                MaterialAttributes.resolveBoolean(context, R.attr.elevationOverlayEnabled, false);
        TypedValue resolve = MaterialAttributes.resolve(context, R.attr.elevationOverlayColor);
        Integer num3 = null;
        if (resolve != null) {
            int i = resolve.resourceId;
            num = Integer.valueOf(i != 0 ? context.getColor(i) : resolve.data);
        } else {
            num = null;
        }
        int intValue = num != null ? num.intValue() : 0;
        TypedValue resolve2 =
                MaterialAttributes.resolve(context, R.attr.elevationOverlayAccentColor);
        if (resolve2 != null) {
            int i2 = resolve2.resourceId;
            num2 = Integer.valueOf(i2 != 0 ? context.getColor(i2) : resolve2.data);
        } else {
            num2 = null;
        }
        int intValue2 = num2 != null ? num2.intValue() : 0;
        TypedValue resolve3 = MaterialAttributes.resolve(context, R.attr.colorSurface);
        if (resolve3 != null) {
            int i3 = resolve3.resourceId;
            num3 = Integer.valueOf(i3 != 0 ? context.getColor(i3) : resolve3.data);
        }
        int intValue3 = num3 != null ? num3.intValue() : 0;
        float f = context.getResources().getDisplayMetrics().density;
        this.elevationOverlayEnabled = resolveBoolean;
        this.elevationOverlayColor = intValue;
        this.elevationOverlayAccentColor = intValue2;
        this.colorSurface = intValue3;
        this.displayDensity = f;
    }
}
