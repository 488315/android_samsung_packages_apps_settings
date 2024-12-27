package com.google.android.material.shape;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewParent;

import androidx.core.view.ViewCompat;

import com.google.android.material.elevation.ElevationOverlayProvider;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class MaterialShapeUtils {
    public static CornerTreatment createCornerTreatment(int i) {
        return i != 0
                ? i != 1 ? new RoundedCornerTreatment() : new CutCornerTreatment()
                : new RoundedCornerTreatment();
    }

    public static void setElevation(View view, float f) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) background).setElevation(f);
        }
    }

    public static void setParentAbsoluteElevation(View view) {
        Drawable background = view.getBackground();
        if (background instanceof MaterialShapeDrawable) {
            setParentAbsoluteElevation(view, (MaterialShapeDrawable) background);
        }
    }

    public static void setParentAbsoluteElevation(
            View view, MaterialShapeDrawable materialShapeDrawable) {
        ElevationOverlayProvider elevationOverlayProvider =
                materialShapeDrawable.drawableState.elevationOverlayProvider;
        if (elevationOverlayProvider == null || !elevationOverlayProvider.elevationOverlayEnabled) {
            return;
        }
        float f = 0.0f;
        for (ViewParent parent = view.getParent();
                parent instanceof View;
                parent = parent.getParent()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            f += ViewCompat.Api21Impl.getElevation((View) parent);
        }
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                materialShapeDrawable.drawableState;
        if (materialShapeDrawableState.parentAbsoluteElevation != f) {
            materialShapeDrawableState.parentAbsoluteElevation = f;
            materialShapeDrawable.updateZ();
        }
    }
}
