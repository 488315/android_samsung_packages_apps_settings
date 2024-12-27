package com.google.android.material.chip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;

import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.Preference;

import com.android.settings.R;

import com.google.android.material.internal.CheckableGroup;
import com.google.android.material.internal.MaterialCheckable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SeslChipGroup extends ViewGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CheckableGroup checkableGroup;
    public final int chipSpacingHorizontal;
    public final int chipSpacingVertical;
    public final int defaultCheckedId;
    public int itemSpacing;
    public int lineSpacing;
    public int mChipMaxWidth;
    public final boolean mDynamicChipTextTruncation;
    public int mEmptyContainerHeight;
    public final LayoutTransition mLayoutTransition;
    public int mRowCount;
    public final ChipGroup$PassThroughHierarchyChangeListener passThroughListener;
    public boolean singleLine;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.chip.SeslChipGroup$3, reason: invalid class name */
    public final class AnonymousClass3 extends AnimatorListenerAdapter {
        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            View view = (View) ((SeslValueAnimator) animator).mTargetView.get();
            if (view == null) {
                return;
            }
            view.setAlpha(1.0f);
        }
    }

    public SeslChipGroup(Context context) {
        this(context, null);
    }

    public final void addRemoveAnim() {
        if (!isLineAddedOrRemoved()) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            layoutParams.height = -2;
            this.mEmptyContainerHeight = 0;
            setLayoutParams(layoutParams);
            return;
        }
        final int height = getHeight();
        final int internalHeight = getInternalHeight(getWidth()) - height;
        if (Math.abs(internalHeight)
                < getContext().getResources().getDimension(R.dimen.chip_height)) {
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setDuration(
                getContext().getResources().getInteger(R.integer.sesl_chip_default_anim_duration));
        ofFloat.setInterpolator(
                AnimationUtils.loadInterpolator(
                        getContext(), R.interpolator.sesl_chip_default_interpolator));
        ofFloat.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.google.android.material.chip.SeslChipGroup.1
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        ViewGroup.LayoutParams layoutParams2 = SeslChipGroup.this.getLayoutParams();
                        layoutParams2.height = -2;
                        SeslChipGroup seslChipGroup = SeslChipGroup.this;
                        seslChipGroup.mEmptyContainerHeight = 0;
                        seslChipGroup.setLayoutParams(layoutParams2);
                    }
                });
        ofFloat.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.google.android.material.chip.SeslChipGroup$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SeslChipGroup seslChipGroup = SeslChipGroup.this;
                        int i = height;
                        int i2 = internalHeight;
                        int i3 = SeslChipGroup.$r8$clinit;
                        ViewGroup.LayoutParams layoutParams2 = seslChipGroup.getLayoutParams();
                        int floatValue =
                                i
                                        + ((int)
                                                (((Float) valueAnimator.getAnimatedValue())
                                                                .floatValue()
                                                        * i2));
                        layoutParams2.height = floatValue;
                        seslChipGroup.mEmptyContainerHeight = floatValue;
                        seslChipGroup.setLayoutParams(layoutParams2);
                    }
                });
        ofFloat.start();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            setLayoutTransition(this.mLayoutTransition);
        } else {
            setLayoutTransition(null);
        }
        super.addView(view, i, layoutParams);
        if (isLineAddedOrRemoved()) {
            setLayoutTransition(null);
        }
        addRemoveAnim();
        if (view instanceof Chip) {
            Chip chip = (Chip) view;
            if (this.mDynamicChipTextTruncation) {
                int i2 = this.mChipMaxWidth;
                if (i2 > 0) {
                    chip.setMaxWidth(i2);
                }
                chip.setEllipsize(TextUtils.TruncateAt.END);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams)
                && (layoutParams instanceof ChipGroup$LayoutParams);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ChipGroup$LayoutParams(-2, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ChipGroup$LayoutParams(getContext(), attributeSet);
    }

    public final int getInternalHeight(float f) {
        int i;
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int i2 = this.chipSpacingHorizontal;
        int width = getChildAt(0).getWidth() + paddingStart + paddingEnd + i2;
        int i3 = 1;
        for (int i4 = 1; i4 < childCount; i4++) {
            int intrinsicWidth = ((Chip) getChildAt(i4)).chipDrawable.getIntrinsicWidth();
            if (width + intrinsicWidth < f) {
                i = intrinsicWidth + i2 + width;
            } else {
                i = intrinsicWidth + i2 + paddingStart + paddingEnd;
                i3++;
            }
            width = i;
        }
        int i5 = this.chipSpacingVertical;
        return (getPaddingTop() + (getPaddingBottom() + ((getChildAt(0).getHeight() + i5) * i3)))
                - i5;
    }

    public final boolean isLineAddedOrRemoved() {
        boolean z;
        return getHeight() != getInternalHeight((float) getWidth())
                && (!(z = this.singleLine) || (z && getChildCount() == 0));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        int i = this.defaultCheckedId;
        if (i != -1) {
            CheckableGroup checkableGroup = this.checkableGroup;
            MaterialCheckable materialCheckable =
                    (MaterialCheckable)
                            ((HashMap) checkableGroup.checkables).get(Integer.valueOf(i));
            if (materialCheckable != null && checkableGroup.checkInternal(materialCheckable)) {
                checkableGroup.onCheckedStateChanged();
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        int i;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.singleLine) {
            i = 0;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                if ((getChildAt(i2) instanceof Chip) && getChildAt(i2).getVisibility() == 0) {
                    i++;
                }
            }
        } else {
            i = -1;
        }
        accessibilityNodeInfo.setCollectionInfo(
                (AccessibilityNodeInfo.CollectionInfo)
                        AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                                        this.mRowCount,
                                        i,
                                        this.checkableGroup.singleSelection ? 1 : 2)
                                .mInfo);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = 1;
        if (getChildCount() == 0) {
            this.mRowCount = 0;
            return;
        }
        this.mRowCount = 1;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z2 = getLayoutDirection() == 1;
        int paddingRight = z2 ? getPaddingRight() : getPaddingLeft();
        int paddingLeft = z2 ? getPaddingLeft() : getPaddingRight();
        int paddingTop = getPaddingTop();
        int i9 = this.lineSpacing;
        int i10 = this.itemSpacing;
        int i11 = i3 - i;
        int i12 = i11 - paddingLeft;
        if (!z2) {
            i11 = i12;
        }
        int i13 = paddingRight;
        int i14 = paddingTop;
        for (int i15 = 0; i15 < getChildCount(); i15 += i8) {
            View childAt = getChildAt(i15);
            if (childAt.getVisibility() == 8) {
                childAt.setTag(R.id.row_index_key, -1);
            } else {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams =
                            (ViewGroup.MarginLayoutParams) layoutParams;
                    i6 = marginLayoutParams.getMarginStart();
                    i5 = marginLayoutParams.getMarginEnd();
                } else {
                    i5 = 0;
                    i6 = 0;
                }
                int measuredWidth = childAt.getMeasuredWidth() + i13 + i6;
                if (this.singleLine || measuredWidth <= i12) {
                    i7 = 1;
                } else {
                    i14 = paddingTop + i9;
                    i7 = 1;
                    this.mRowCount++;
                    i13 = paddingRight;
                }
                childAt.setTag(R.id.row_index_key, Integer.valueOf(this.mRowCount - i7));
                int i16 = i13 + i6;
                int measuredWidth2 = childAt.getMeasuredWidth() + i16;
                paddingTop = childAt.getMeasuredHeight() + i14;
                if (z2) {
                    childAt.layout(i11 - measuredWidth2, i14, (i11 - i13) - i6, paddingTop);
                } else {
                    childAt.layout(i16, i14, measuredWidth2, paddingTop);
                }
                i13 += childAt.getMeasuredWidth() + i6 + i5 + i10;
                i8 = 1;
            }
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        onMeasure$com$google$android$material$internal$FlowLayout(i, i2);
        if (getChildCount() <= 0) {
            setMeasuredDimension(getWidth(), this.mEmptyContainerHeight);
        }
    }

    public final void onMeasure$com$google$android$material$internal$FlowLayout(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i7 =
                (mode == Integer.MIN_VALUE || mode == 1073741824) ? size : Preference.DEFAULT_ORDER;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = i7 - getPaddingRight();
        int i8 = paddingTop;
        int i9 = 0;
        for (int i10 = 0; i10 < getChildCount(); i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams =
                            (ViewGroup.MarginLayoutParams) layoutParams;
                    i5 = marginLayoutParams.leftMargin;
                    i4 = marginLayoutParams.rightMargin;
                } else {
                    i4 = 0;
                    i5 = 0;
                }
                int i11 = paddingLeft;
                if (childAt.getMeasuredWidth() + paddingLeft + i5 <= paddingRight
                        || this.singleLine) {
                    i6 = i11;
                } else {
                    i6 = getPaddingLeft();
                    i8 = this.lineSpacing + paddingTop;
                }
                int measuredWidth = childAt.getMeasuredWidth() + i6 + i5;
                int measuredHeight = childAt.getMeasuredHeight() + i8;
                if (measuredWidth > i9) {
                    i9 = measuredWidth;
                }
                int measuredWidth2 = childAt.getMeasuredWidth() + i5 + i4 + this.itemSpacing + i6;
                if (i10 == getChildCount() - 1) {
                    i9 += i4;
                }
                paddingLeft = measuredWidth2;
                paddingTop = measuredHeight;
            }
        }
        int paddingRight2 = getPaddingRight() + i9;
        int paddingBottom = getPaddingBottom() + paddingTop;
        if (mode != Integer.MIN_VALUE) {
            i3 = 1073741824;
            if (mode != 1073741824) {
                size = paddingRight2;
            }
        } else {
            i3 = 1073741824;
            size = Math.min(paddingRight2, size);
        }
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(paddingBottom, size2);
        } else if (mode2 != i3) {
            size2 = paddingBottom;
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup
    public final void removeAllViews() {
        setStaticHeight();
        super.removeAllViews();
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeAllViewsInLayout() {
        setStaticHeight();
        super.removeAllViewsInLayout();
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        if (getChildCount() > 1) {
            setLayoutTransition(this.mLayoutTransition);
        } else {
            setLayoutTransition(null);
        }
        setStaticHeight();
        super.removeView(view);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewAt(int i) {
        setStaticHeight();
        super.removeViewAt(i);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewInLayout(View view) {
        setStaticHeight();
        super.removeViewInLayout(view);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViews(int i, int i2) {
        setStaticHeight();
        super.removeViews(i, i2);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void removeViewsInLayout(int i, int i2) {
        setStaticHeight();
        super.removeViewsInLayout(i, i2);
        addRemoveAnim();
    }

    @Override // android.view.ViewGroup
    public final void setOnHierarchyChangeListener(
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.passThroughListener.onHierarchyChangeListener = onHierarchyChangeListener;
    }

    public final void setStaticHeight() {
        this.mEmptyContainerHeight = getHeight();
    }

    public SeslChipGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.chipGroupStyle);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SeslValueAnimator extends ValueAnimator {
        public ArrayList mSeslListeners;
        public ArrayList mSeslUpdateListeners;
        public WeakReference mTargetView;
        public float[] mValues;

        public static SeslValueAnimator ofFloat(float... fArr) {
            SeslValueAnimator seslValueAnimator = new SeslValueAnimator();
            seslValueAnimator.setFloatValues(fArr);
            seslValueAnimator.mValues = fArr;
            seslValueAnimator.mSeslUpdateListeners = new ArrayList();
            seslValueAnimator.mSeslListeners = new ArrayList();
            return seslValueAnimator;
        }

        @Override // android.animation.Animator
        public final void addListener(Animator.AnimatorListener animatorListener) {
            super.addListener(animatorListener);
            this.mSeslListeners.add(animatorListener);
        }

        @Override // android.animation.ValueAnimator
        public final void addUpdateListener(
                ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            super.addUpdateListener(animatorUpdateListener);
            this.mSeslUpdateListeners.add(animatorUpdateListener);
        }

        @Override // android.animation.Animator
        public final void setTarget(Object obj) {
            this.mTargetView = new WeakReference((View) obj);
            super.setTarget(obj);
        }

        @Override // android.animation.ValueAnimator, android.animation.Animator
        public final SeslValueAnimator clone() {
            SeslValueAnimator ofFloat = ofFloat(this.mValues);
            ArrayList arrayList = this.mSeslUpdateListeners;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ofFloat.addUpdateListener((ValueAnimator.AnimatorUpdateListener) it.next());
                }
            }
            ArrayList arrayList2 = this.mSeslListeners;
            if (arrayList2 != null) {
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    ofFloat.addListener((Animator.AnimatorListener) it2.next());
                }
            }
            return ofFloat;
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SeslChipGroup(android.content.Context r11, android.util.AttributeSet r12, int r13) {
        /*
            Method dump skipped, instructions count: 404
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.chip.SeslChipGroup.<init>(android.content.Context,"
                    + " android.util.AttributeSet, int):void");
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new ChipGroup$LayoutParams(layoutParams);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.chip.SeslChipGroup$2, reason: invalid class name */
    public final class AnonymousClass2 implements LayoutTransition.TransitionListener {
        @Override // android.animation.LayoutTransition.TransitionListener
        public final void endTransition(
                LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {}

        @Override // android.animation.LayoutTransition.TransitionListener
        public final void startTransition(
                LayoutTransition layoutTransition, ViewGroup viewGroup, View view, int i) {}
    }
}
