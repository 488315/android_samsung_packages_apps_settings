package com.google.android.material.timepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class RadialViewGroup extends ConstraintLayout {
    public final MaterialShapeDrawable background;
    public int radius;
    public final RadialViewGroup$$ExternalSyntheticLambda0 updateLayoutParametersRunnable;

    /* JADX WARN: Type inference failed for: r5v2, types: [com.google.android.material.timepicker.RadialViewGroup$$ExternalSyntheticLambda0] */
    public RadialViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.material_radial_view_group, this);
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        this.background = materialShapeDrawable;
        RelativeCornerSize relativeCornerSize = new RelativeCornerSize(0.5f);
        ShapeAppearanceModel.Builder builder =
                materialShapeDrawable.drawableState.shapeAppearanceModel.toBuilder();
        builder.topLeftCornerSize = relativeCornerSize;
        builder.topRightCornerSize = relativeCornerSize;
        builder.bottomRightCornerSize = relativeCornerSize;
        builder.bottomLeftCornerSize = relativeCornerSize;
        materialShapeDrawable.setShapeAppearanceModel(builder.build());
        this.background.setFillColor(ColorStateList.valueOf(-1));
        MaterialShapeDrawable materialShapeDrawable2 = this.background;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setBackground(materialShapeDrawable2);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.RadialViewGroup, i, 0);
        this.radius = obtainStyledAttributes.getDimensionPixelSize(0, 0);
        this.updateLayoutParametersRunnable =
                new Runnable() { // from class:
                                 // com.google.android.material.timepicker.RadialViewGroup$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        RadialViewGroup.this.updateLayoutParams();
                    }
                };
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        if (view.getId() == -1) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.setId(View.generateViewId());
        }
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.updateLayoutParametersRunnable);
            handler.post(this.updateLayoutParametersRunnable);
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        updateLayoutParams();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.updateLayoutParametersRunnable);
            handler.post(this.updateLayoutParametersRunnable);
        }
    }

    @Override // android.view.View
    public final void setBackgroundColor(int i) {
        this.background.setFillColor(ColorStateList.valueOf(i));
    }

    public abstract void updateLayoutParams();
}
