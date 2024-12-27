package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.google.android.material.internal.CheckableImageButton;

import java.util.Arrays;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class IconHelper {
    public static void applyIconTint(
            TextInputLayout textInputLayout,
            CheckableImageButton checkableImageButton,
            ColorStateList colorStateList,
            PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null) {
            drawable = drawable.mutate();
            if (colorStateList == null || !colorStateList.isStateful()) {
                drawable.setTintList(colorStateList);
            } else {
                int[] drawableState = textInputLayout.getDrawableState();
                int[] drawableState2 = checkableImageButton.getDrawableState();
                int length = drawableState.length;
                int[] copyOf =
                        Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
                System.arraycopy(drawableState2, 0, copyOf, length, drawableState2.length);
                drawable.setTintList(
                        ColorStateList.valueOf(
                                colorStateList.getColorForState(
                                        copyOf, colorStateList.getDefaultColor())));
            }
            if (mode != null) {
                drawable.setTintMode(mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public static ImageView.ScaleType convertScaleType(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3
                                        ? i != 5
                                                ? i != 6
                                                        ? ImageView.ScaleType.CENTER
                                                        : ImageView.ScaleType.CENTER_INSIDE
                                                : ImageView.ScaleType.CENTER_CROP
                                        : ImageView.ScaleType.FIT_END
                                : ImageView.ScaleType.FIT_CENTER
                        : ImageView.ScaleType.FIT_START
                : ImageView.ScaleType.FIT_XY;
    }

    public static void refreshIconDrawableState(
            TextInputLayout textInputLayout,
            CheckableImageButton checkableImageButton,
            ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() == null
                || colorStateList == null
                || !colorStateList.isStateful()) {
            return;
        }
        int[] drawableState = textInputLayout.getDrawableState();
        int[] drawableState2 = checkableImageButton.getDrawableState();
        int length = drawableState.length;
        int[] copyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
        System.arraycopy(drawableState2, 0, copyOf, length, drawableState2.length);
        int colorForState =
                colorStateList.getColorForState(copyOf, colorStateList.getDefaultColor());
        Drawable mutate = drawable.mutate();
        mutate.setTintList(ColorStateList.valueOf(colorForState));
        checkableImageButton.setImageDrawable(mutate);
    }

    public static void setIconClickable(
            CheckableImageButton checkableImageButton,
            View.OnLongClickListener onLongClickListener) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean hasOnClickListeners = checkableImageButton.hasOnClickListeners();
        boolean z = onLongClickListener != null;
        boolean z2 = hasOnClickListeners || z;
        checkableImageButton.setFocusable(z2);
        checkableImageButton.setClickable(hasOnClickListeners);
        checkableImageButton.pressable = hasOnClickListeners;
        checkableImageButton.setLongClickable(z);
        checkableImageButton.setImportantForAccessibility(z2 ? 1 : 2);
    }
}
