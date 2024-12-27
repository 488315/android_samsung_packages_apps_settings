package com.google.android.material.timepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.resources.MaterialResources;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class ClockFaceView extends RadialViewGroup implements ClockHandView.OnRotateListener {
    public final int clockHandPadding;
    public final ClockHandView clockHandView;
    public final int clockSize;
    public float currentHandRotation;
    public final int[] gradientColors;
    public final float[] gradientPositions;
    public final int minimumHeight;
    public final int minimumWidth;
    public final RectF scratch;
    public final Rect scratchLineBounds;
    public final ColorStateList textColor;
    public final SparseArray textViewPool;
    public final Rect textViewRect;
    public final AnonymousClass2 valueAccessibilityDelegate;
    public final String[] values;

    public ClockFaceView(Context context) {
        this(context, null);
    }

    public final void findIntersectingTextView() {
        RectF rectF = this.clockHandView.selectorBox;
        float f = Float.MAX_VALUE;
        TextView textView = null;
        for (int i = 0; i < this.textViewPool.size(); i++) {
            TextView textView2 = (TextView) this.textViewPool.get(i);
            if (textView2 != null) {
                textView2.getHitRect(this.textViewRect);
                this.scratch.set(this.textViewRect);
                this.scratch.union(rectF);
                float height = this.scratch.height() * this.scratch.width();
                if (height < f) {
                    textView = textView2;
                    f = height;
                }
            }
        }
        for (int i2 = 0; i2 < this.textViewPool.size(); i2++) {
            TextView textView3 = (TextView) this.textViewPool.get(i2);
            if (textView3 != null) {
                textView3.setSelected(textView3 == textView);
                textView3.getHitRect(this.textViewRect);
                this.scratch.set(this.textViewRect);
                textView3.getLineBounds(0, this.scratchLineBounds);
                RectF rectF2 = this.scratch;
                Rect rect = this.scratchLineBounds;
                rectF2.inset(rect.left, rect.top);
                textView3
                        .getPaint()
                        .setShader(
                                !RectF.intersects(rectF, this.scratch)
                                        ? null
                                        : new RadialGradient(
                                                rectF.centerX() - this.scratch.left,
                                                rectF.centerY() - this.scratch.top,
                                                0.5f * rectF.width(),
                                                this.gradientColors,
                                                this.gradientPositions,
                                                Shader.TileMode.CLAMP));
                textView3.invalidate();
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(
                (AccessibilityNodeInfo.CollectionInfo)
                        AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        1, this.values.length, 1)
                                .mInfo);
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.ViewGroup,
              // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        findIntersectingTextView();
    }

    @Override // androidx.constraintlayout.widget.ConstraintLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int max =
                (int)
                        (this.clockSize
                                / Math.max(
                                        Math.max(
                                                this.minimumHeight / displayMetrics.heightPixels,
                                                this.minimumWidth / displayMetrics.widthPixels),
                                        1.0f));
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(max, 1073741824);
        setMeasuredDimension(max, max);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    @Override // com.google.android.material.timepicker.RadialViewGroup
    public final void updateLayoutParams() {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this);
        HashMap hashMap = new HashMap();
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getId() != R.id.circle_center && !"skip".equals(childAt.getTag())) {
                int i2 = (Integer) childAt.getTag(R.id.material_clock_level);
                if (i2 == null) {
                    i2 = 1;
                }
                if (!hashMap.containsKey(i2)) {
                    hashMap.put(i2, new ArrayList());
                }
                ((List) hashMap.get(i2)).add(childAt);
            }
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            List list = (List) entry.getValue();
            int round =
                    ((Integer) entry.getKey()).intValue() == 2
                            ? Math.round(this.radius * 0.66f)
                            : this.radius;
            Iterator it = list.iterator();
            float f = 0.0f;
            while (it.hasNext()) {
                ConstraintSet.Layout layout = constraintSet.get(((View) it.next()).getId()).layout;
                layout.circleConstraint = R.id.circle_center;
                layout.circleRadius = round;
                layout.circleAngle = f;
                f += 360.0f / list.size();
            }
        }
        constraintSet.applyTo(this);
        for (int i3 = 0; i3 < this.textViewPool.size(); i3++) {
            ((TextView) this.textViewPool.get(i3)).setVisibility(0);
        }
    }

    public ClockFaceView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialClockStyle);
    }

    /* JADX WARN: Type inference failed for: r10v3, types: [com.google.android.material.timepicker.ClockFaceView$2] */
    @SuppressLint({"ClickableViewAccessibility"})
    public ClockFaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textViewRect = new Rect();
        this.scratch = new RectF();
        this.scratchLineBounds = new Rect();
        SparseArray sparseArray = new SparseArray();
        this.textViewPool = sparseArray;
        this.gradientPositions = new float[] {0.0f, 0.9f, 1.0f};
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.ClockFaceView, i, 2132084964);
        Resources resources = getResources();
        ColorStateList colorStateList =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 1);
        this.textColor = colorStateList;
        LayoutInflater.from(context)
                .inflate(R.layout.material_clockface_view, (ViewGroup) this, true);
        ClockHandView clockHandView = (ClockHandView) findViewById(R.id.material_clock_hand);
        this.clockHandView = clockHandView;
        this.clockHandPadding =
                resources.getDimensionPixelSize(R.dimen.material_clock_hand_padding);
        int colorForState =
                colorStateList.getColorForState(
                        new int[] {android.R.attr.state_selected},
                        colorStateList.getDefaultColor());
        this.gradientColors =
                new int[] {colorForState, colorForState, colorStateList.getDefaultColor()};
        ((ArrayList) clockHandView.listeners).add(this);
        int defaultColor =
                ResourcesCompat.getColorStateList(
                                context.getResources(),
                                R.color.material_timepicker_clockface,
                                context.getTheme())
                        .getDefaultColor();
        ColorStateList colorStateList2 =
                MaterialResources.getColorStateList(context, obtainStyledAttributes, 0);
        setBackgroundColor(
                colorStateList2 != null ? colorStateList2.getDefaultColor() : defaultColor);
        getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.google.android.material.timepicker.ClockFaceView.1
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public final boolean onPreDraw() {
                                if (!ClockFaceView.this.isShown()) {
                                    return true;
                                }
                                ClockFaceView.this
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                int height = ClockFaceView.this.getHeight() / 2;
                                ClockFaceView clockFaceView = ClockFaceView.this;
                                int i2 =
                                        (height - clockFaceView.clockHandView.selectorRadius)
                                                - clockFaceView.clockHandPadding;
                                if (i2 != clockFaceView.radius) {
                                    clockFaceView.radius = i2;
                                    clockFaceView.updateLayoutParams();
                                    ClockHandView clockHandView2 = clockFaceView.clockHandView;
                                    clockHandView2.circleRadius = clockFaceView.radius;
                                    clockHandView2.invalidate();
                                }
                                return true;
                            }
                        });
        setFocusable(true);
        obtainStyledAttributes.recycle();
        this.valueAccessibilityDelegate =
                new AccessibilityDelegateCompat() { // from class:
                                                    // com.google.android.material.timepicker.ClockFaceView.2
                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                                view, accessibilityNodeInfoCompat.mInfo);
                        int intValue =
                                ((Integer) view.getTag(R.id.material_value_index)).intValue();
                        if (intValue > 0) {
                            accessibilityNodeInfoCompat.mInfo.setTraversalAfter(
                                    (View) ClockFaceView.this.textViewPool.get(intValue - 1));
                        }
                        accessibilityNodeInfoCompat.setCollectionItemInfo(
                                AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        view.isSelected(), 0, 1, intValue, 1));
                        accessibilityNodeInfoCompat.setClickable(true);
                        accessibilityNodeInfoCompat.addAction(
                                AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
                    }

                    @Override // androidx.core.view.AccessibilityDelegateCompat
                    public final boolean performAccessibilityAction(
                            View view, int i2, Bundle bundle) {
                        if (i2 != 16) {
                            return super.performAccessibilityAction(view, i2, bundle);
                        }
                        long uptimeMillis = SystemClock.uptimeMillis();
                        ClockFaceView clockFaceView = ClockFaceView.this;
                        view.getHitRect(clockFaceView.textViewRect);
                        float centerX = clockFaceView.textViewRect.centerX();
                        float centerY = clockFaceView.textViewRect.centerY();
                        clockFaceView.clockHandView.onTouchEvent(
                                MotionEvent.obtain(
                                        uptimeMillis, uptimeMillis, 0, centerX, centerY, 0));
                        clockFaceView.clockHandView.onTouchEvent(
                                MotionEvent.obtain(
                                        uptimeMillis, uptimeMillis, 1, centerX, centerY, 0));
                        return true;
                    }
                };
        String[] strArr = new String[12];
        Arrays.fill(strArr, ApnSettings.MVNO_NONE);
        this.values = strArr;
        LayoutInflater from = LayoutInflater.from(getContext());
        int size = sparseArray.size();
        boolean z = false;
        for (int i2 = 0; i2 < Math.max(this.values.length, size); i2++) {
            TextView textView = (TextView) this.textViewPool.get(i2);
            if (i2 >= this.values.length) {
                removeView(textView);
                this.textViewPool.remove(i2);
            } else {
                if (textView == null) {
                    textView =
                            (TextView)
                                    from.inflate(
                                            R.layout.material_clockface_textview,
                                            (ViewGroup) this,
                                            false);
                    this.textViewPool.put(i2, textView);
                    addView(textView);
                }
                textView.setText(this.values[i2]);
                textView.setTag(R.id.material_value_index, Integer.valueOf(i2));
                int i3 = (i2 / 12) + 1;
                textView.setTag(R.id.material_clock_level, Integer.valueOf(i3));
                z = i3 > 1 ? true : z;
                ViewCompat.setAccessibilityDelegate(textView, this.valueAccessibilityDelegate);
                textView.setTextColor(this.textColor);
            }
        }
        ClockHandView clockHandView2 = this.clockHandView;
        if (clockHandView2.isMultiLevel && !z) {
            clockHandView2.currentLevel = 1;
        }
        clockHandView2.isMultiLevel = z;
        clockHandView2.invalidate();
        this.minimumHeight =
                resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_height);
        this.minimumWidth =
                resources.getDimensionPixelSize(R.dimen.material_time_picker_minimum_screen_width);
        this.clockSize = resources.getDimensionPixelSize(R.dimen.material_clock_size);
    }
}
