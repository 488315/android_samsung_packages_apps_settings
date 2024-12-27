package com.google.android.material.theme.overlay;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.view.ContextThemeWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialThemeOverlay {
    public static final int[] ANDROID_THEME_OVERLAY_ATTRS = {
        R.attr.theme, com.android.settings.R.attr.theme
    };
    public static final int[] MATERIAL_THEME_OVERLAY_ATTR = {
        com.android.settings.R.attr.materialThemeOverlay
    };

    public static Context wrap(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, MATERIAL_THEME_OVERLAY_ATTR, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        boolean z =
                (context instanceof ContextThemeWrapper)
                        && ((ContextThemeWrapper) context).mThemeResource == resourceId;
        if (resourceId == 0 || z) {
            return context;
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, resourceId);
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, ANDROID_THEME_OVERLAY_ATTRS);
        int resourceId2 = obtainStyledAttributes2.getResourceId(0, 0);
        int resourceId3 = obtainStyledAttributes2.getResourceId(1, 0);
        obtainStyledAttributes2.recycle();
        if (resourceId2 == 0) {
            resourceId2 = resourceId3;
        }
        if (resourceId2 != 0) {
            contextThemeWrapper.getTheme().applyStyle(resourceId2, true);
        }
        return contextThemeWrapper;
    }
}
