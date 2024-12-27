package com.google.android.material.resources;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialResources {
    public static ColorStateList getColorStateList(Context context, TypedArray typedArray, int i) {
        int resourceId;
        ColorStateList colorStateList;
        return (!typedArray.hasValue(i)
                        || (resourceId = typedArray.getResourceId(i, 0)) == 0
                        || (colorStateList = ContextCompat.getColorStateList(context, resourceId))
                                == null)
                ? typedArray.getColorStateList(i)
                : colorStateList;
    }

    public static Drawable getDrawable(Context context, TypedArray typedArray, int i) {
        int resourceId;
        Drawable drawable;
        return (!typedArray.hasValue(i)
                        || (resourceId = typedArray.getResourceId(i, 0)) == 0
                        || (drawable = AppCompatResources.getDrawable(context, resourceId)) == null)
                ? typedArray.getDrawable(i)
                : drawable;
    }

    public static boolean isFontScaleAtLeast1_3(Context context) {
        return context.getResources().getConfiguration().fontScale >= 1.3f;
    }

    public static ColorStateList getColorStateList(
            Context context, TintTypedArray tintTypedArray, int i) {
        int resourceId;
        ColorStateList colorStateList;
        return (!tintTypedArray.mWrapped.hasValue(i)
                        || (resourceId = tintTypedArray.mWrapped.getResourceId(i, 0)) == 0
                        || (colorStateList = ContextCompat.getColorStateList(context, resourceId))
                                == null)
                ? tintTypedArray.getColorStateList(i)
                : colorStateList;
    }
}
