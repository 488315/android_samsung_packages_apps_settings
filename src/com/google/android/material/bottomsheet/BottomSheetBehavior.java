package com.google.android.material.bottomsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.activity.BackEventCompat;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MaterialBackHandler;
import com.google.android.material.motion.MaterialBottomContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior
        implements MaterialBackHandler {
    static final int DEFAULT_SIGNIFICANT_VEL_THRESHOLD = 500;
    static final int VIEW_INDEX_ACCESSIBILITY_DELEGATE_VIEW = 1;
    public int activePointerId;
    public final ColorStateList backgroundTint;
    public MaterialBottomContainerBackHelper bottomContainerBackHelper;
    public final ArrayList callbacks;
    public int childHeight;
    public int collapsedOffset;
    public final AnonymousClass5 dragCallback;
    public final boolean draggable;
    public final float elevation;
    final SparseIntArray expandHalfwayActionIds;
    public boolean expandedCornersRemoved;
    public final int expandedOffset;
    public boolean fitToContents;
    public int fitToContentsOffset;
    public int gestureInsetBottom;
    public final boolean gestureInsetBottomIgnored;
    public int halfExpandedOffset;
    public final float halfExpandedRatio;
    public final float hideFriction;
    public boolean hideable;
    public boolean ignoreEvents;
    public Map importantForAccessibilityMap;
    public int initialY;
    public int insetBottom;
    public int insetTop;
    public ValueAnimator interpolatorAnimator;
    public int lastNestedScrollDy;
    public final boolean marginLeftSystemWindowInsets;
    public final boolean marginRightSystemWindowInsets;
    public final boolean marginTopSystemWindowInsets;
    public final MaterialShapeDrawable materialShapeDrawable;
    public int maxHeight;
    public int maxWidth;
    public final float maximumVelocity;
    public boolean nestedScrolled;
    public WeakReference nestedScrollingChildRef;
    public final boolean paddingBottomSystemWindowInsets;
    public final boolean paddingLeftSystemWindowInsets;
    public final boolean paddingRightSystemWindowInsets;
    public final boolean paddingTopSystemWindowInsets;
    public int parentHeight;
    public int parentWidth;
    public int peekHeight;
    public boolean peekHeightAuto;
    public final int peekHeightGestureInsetBuffer;
    public int peekHeightMin;
    public final int saveFlags;
    public final ShapeAppearanceModel shapeAppearanceModelDefault;
    public final boolean shouldRemoveExpandedCorners;
    public final int significantVelocityThreshold;
    public boolean skipCollapsed;
    public int state;
    public final StateSettlingTracker stateSettlingTracker;
    public boolean touchingScrollingChild;
    public VelocityTracker velocityTracker;
    public ViewDragHelper viewDragHelper;
    public WeakReference viewRef;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.bottomsheet.BottomSheetBehavior$4, reason: invalid class name */
    public final class AnonymousClass4 {
        public final /* synthetic */ boolean val$shouldHandleGestureInsets;

        public AnonymousClass4(boolean z) {
            this.val$shouldHandleGestureInsets = z;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class StateSettlingTracker {
        public final AnonymousClass1 continueSettlingRunnable =
                new Runnable() { // from class:
                                 // com.google.android.material.bottomsheet.BottomSheetBehavior.StateSettlingTracker.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        StateSettlingTracker stateSettlingTracker = StateSettlingTracker.this;
                        stateSettlingTracker.isContinueSettlingRunnablePosted = false;
                        ViewDragHelper viewDragHelper = BottomSheetBehavior.this.viewDragHelper;
                        if (viewDragHelper != null && viewDragHelper.continueSettling()) {
                            StateSettlingTracker stateSettlingTracker2 = StateSettlingTracker.this;
                            stateSettlingTracker2.continueSettlingToState(
                                    stateSettlingTracker2.targetState);
                            return;
                        }
                        StateSettlingTracker stateSettlingTracker3 = StateSettlingTracker.this;
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        if (bottomSheetBehavior.state == 2) {
                            bottomSheetBehavior.setStateInternal(stateSettlingTracker3.targetState);
                        }
                    }
                };
        public boolean isContinueSettlingRunnablePosted;
        public int targetState;

        /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$StateSettlingTracker$1] */
        public StateSettlingTracker() {}

        public final void continueSettlingToState(int i) {
            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
            WeakReference weakReference = bottomSheetBehavior.viewRef;
            if (weakReference == null || weakReference.get() == null) {
                return;
            }
            this.targetState = i;
            if (this.isContinueSettlingRunnablePosted) {
                return;
            }
            View view = (View) bottomSheetBehavior.viewRef.get();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.postOnAnimation(this.continueSettlingRunnable);
            this.isContinueSettlingRunnablePosted = true;
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$5] */
    public BottomSheetBehavior() {
        this.saveFlags = 0;
        this.fitToContents = true;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback =
                new ViewDragHelper.Callback() { // from class:
                    // com.google.android.material.bottomsheet.BottomSheetBehavior.5
                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int clampViewPositionHorizontal(View view, int i) {
                        return view.getLeft();
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int clampViewPositionVertical(View view, int i) {
                        return MathUtils.clamp(
                                i,
                                BottomSheetBehavior.this.getExpandedOffset(),
                                getViewVerticalDragRange());
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int getViewVerticalDragRange() {
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        return bottomSheetBehavior.hideable
                                ? bottomSheetBehavior.parentHeight
                                : bottomSheetBehavior.collapsedOffset;
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final void onViewDragStateChanged(int i) {
                        if (i == 1) {
                            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                            if (bottomSheetBehavior.draggable) {
                                bottomSheetBehavior.setStateInternal(1);
                            }
                        }
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final void onViewPositionChanged(View view, int i, int i2) {
                        BottomSheetBehavior.this.dispatchOnSlide(i2);
                    }

                    /* JADX WARN: Code restructure failed: missing block: B:10:0x0019, code lost:

                       if (r6 > r4.halfExpandedOffset) goto L50;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:25:0x0069, code lost:

                       if (java.lang.Math.abs(r5.getTop() - r4.getExpandedOffset()) < java.lang.Math.abs(r5.getTop() - r4.halfExpandedOffset)) goto L6;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:34:0x0097, code lost:

                       if (java.lang.Math.abs(r6 - r4.halfExpandedOffset) < java.lang.Math.abs(r6 - r4.collapsedOffset)) goto L50;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b1, code lost:

                       if (java.lang.Math.abs(r6 - r4.fitToContentsOffset) < java.lang.Math.abs(r6 - r4.collapsedOffset)) goto L6;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c1, code lost:

                       if (r6 < java.lang.Math.abs(r6 - r4.collapsedOffset)) goto L6;
                    */
                    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d2, code lost:

                       if (java.lang.Math.abs(r6 - r7) < java.lang.Math.abs(r6 - r4.collapsedOffset)) goto L50;
                    */
                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onViewReleased(android.view.View r5, float r6, float r7) {
                        /*
                            r4 = this;
                            r0 = 0
                            int r1 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                            r2 = 6
                            r3 = 3
                            com.google.android.material.bottomsheet.BottomSheetBehavior r4 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                            if (r1 >= 0) goto L1d
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L10
                        Ld:
                            r2 = r3
                            goto Ld4
                        L10:
                            int r6 = r5.getTop()
                            java.lang.System.currentTimeMillis()
                            int r7 = r4.halfExpandedOffset
                            if (r6 <= r7) goto Ld
                            goto Ld4
                        L1d:
                            boolean r1 = r4.hideable
                            if (r1 == 0) goto L6c
                            boolean r1 = r4.shouldHide(r5, r7)
                            if (r1 == 0) goto L6c
                            float r6 = java.lang.Math.abs(r6)
                            float r0 = java.lang.Math.abs(r7)
                            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                            if (r6 >= 0) goto L3a
                            int r6 = r4.significantVelocityThreshold
                            float r6 = (float) r6
                            int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                            if (r6 > 0) goto L49
                        L3a:
                            int r6 = r5.getTop()
                            int r7 = r4.parentHeight
                            int r0 = r4.getExpandedOffset()
                            int r0 = r0 + r7
                            int r0 = r0 / 2
                            if (r6 <= r0) goto L4c
                        L49:
                            r2 = 5
                            goto Ld4
                        L4c:
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L51
                            goto Ld
                        L51:
                            int r6 = r5.getTop()
                            int r7 = r4.getExpandedOffset()
                            int r6 = r6 - r7
                            int r6 = java.lang.Math.abs(r6)
                            int r7 = r5.getTop()
                            int r0 = r4.halfExpandedOffset
                            int r7 = r7 - r0
                            int r7 = java.lang.Math.abs(r7)
                            if (r6 >= r7) goto Ld4
                            goto Ld
                        L6c:
                            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                            r1 = 4
                            if (r0 == 0) goto L9a
                            float r6 = java.lang.Math.abs(r6)
                            float r7 = java.lang.Math.abs(r7)
                            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                            if (r6 <= 0) goto L7e
                            goto L9a
                        L7e:
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L84
                        L82:
                            r2 = r1
                            goto Ld4
                        L84:
                            int r6 = r5.getTop()
                            int r7 = r4.halfExpandedOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                            goto Ld4
                        L9a:
                            int r6 = r5.getTop()
                            boolean r7 = r4.fitToContents
                            if (r7 == 0) goto Lb5
                            int r7 = r4.fitToContentsOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                            goto Ld
                        Lb5:
                            int r7 = r4.halfExpandedOffset
                            if (r6 >= r7) goto Lc5
                            int r7 = r4.collapsedOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            if (r6 >= r7) goto Ld4
                            goto Ld
                        Lc5:
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                        Ld4:
                            r4.getClass()
                            r6 = 1
                            r4.startSettling(r5, r2, r6)
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass5.onViewReleased(android.view.View,"
                                    + " float, float):void");
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final boolean tryCaptureView(View view, int i) {
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        int i2 = bottomSheetBehavior.state;
                        if (i2 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                            return false;
                        }
                        if (i2 == 3 && bottomSheetBehavior.activePointerId == i) {
                            WeakReference weakReference =
                                    bottomSheetBehavior.nestedScrollingChildRef;
                            View view2 = weakReference != null ? (View) weakReference.get() : null;
                            if (view2 != null && view2.canScrollVertically(-1)) {
                                return false;
                            }
                        }
                        System.currentTimeMillis();
                        WeakReference weakReference2 = bottomSheetBehavior.viewRef;
                        return weakReference2 != null && weakReference2.get() == view;
                    }
                };
    }

    public static BottomSheetBehavior from(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior =
                ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior;
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    public static int getChildMeasureSpec(int i, int i2, int i3, int i4) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, i2, i4);
        if (i3 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), 1073741824);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
    }

    public final void calculateCollapsedOffset() {
        int calculatePeekHeight = calculatePeekHeight();
        if (this.fitToContents) {
            this.collapsedOffset =
                    Math.max(this.parentHeight - calculatePeekHeight, this.fitToContentsOffset);
        } else {
            this.collapsedOffset = this.parentHeight - calculatePeekHeight;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final float calculateInterpolationWithCornersRemoved() {
        /*
            r5 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r5.materialShapeDrawable
            r1 = 0
            if (r0 == 0) goto L6b
            java.lang.ref.WeakReference r0 = r5.viewRef
            if (r0 == 0) goto L6b
            java.lang.Object r0 = r0.get()
            if (r0 == 0) goto L6b
            java.lang.ref.WeakReference r0 = r5.viewRef
            java.lang.Object r0 = r0.get()
            android.view.View r0 = (android.view.View) r0
            boolean r2 = r5.isAtTopOfScreen()
            if (r2 == 0) goto L6b
            android.view.WindowInsets r0 = r0.getRootWindowInsets()
            if (r0 == 0) goto L6b
            com.google.android.material.shape.MaterialShapeDrawable r2 = r5.materialShapeDrawable
            float r2 = r2.getTopLeftCornerResolvedSize()
            r3 = 0
            android.view.RoundedCorner r3 = r0.getRoundedCorner(r3)
            if (r3 == 0) goto L3f
            int r3 = r3.getRadius()
            float r3 = (float) r3
            int r4 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r4 <= 0) goto L3f
            int r4 = (r2 > r1 ? 1 : (r2 == r1 ? 0 : -1))
            if (r4 <= 0) goto L3f
            float r3 = r3 / r2
            goto L40
        L3f:
            r3 = r1
        L40:
            com.google.android.material.shape.MaterialShapeDrawable r5 = r5.materialShapeDrawable
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r2 = r5.drawableState
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.shapeAppearanceModel
            com.google.android.material.shape.CornerSize r2 = r2.topRightCornerSize
            android.graphics.RectF r5 = r5.getBoundsAsRectF$1()
            float r5 = r2.getCornerSize(r5)
            r2 = 1
            android.view.RoundedCorner r0 = r0.getRoundedCorner(r2)
            if (r0 == 0) goto L66
            int r0 = r0.getRadius()
            float r0 = (float) r0
            int r2 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r2 <= 0) goto L66
            int r2 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r2 <= 0) goto L66
            float r1 = r0 / r5
        L66:
            float r5 = java.lang.Math.max(r3, r1)
            return r5
        L6b:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.bottomsheet.BottomSheetBehavior.calculateInterpolationWithCornersRemoved():float");
    }

    public final int calculatePeekHeight() {
        int i;
        int i2;
        int i3;
        if (this.peekHeightAuto) {
            i =
                    Math.min(
                            Math.max(
                                    this.peekHeightMin,
                                    this.parentHeight - ((this.parentWidth * 9) / 16)),
                            this.childHeight);
            i2 = this.insetBottom;
        } else {
            if (!this.gestureInsetBottomIgnored
                    && !this.paddingBottomSystemWindowInsets
                    && (i3 = this.gestureInsetBottom) > 0) {
                return Math.max(this.peekHeight, i3 + this.peekHeightGestureInsetBuffer);
            }
            i = this.peekHeight;
            i2 = this.insetBottom;
        }
        return i + i2;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void cancelBackProgress() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper =
                this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        if (materialBottomContainerBackHelper.backEvent == null) {
            Log.w(
                    "MaterialBackHelper",
                    "Must call startBackProgress() and updateBackProgress() before"
                        + " cancelBackProgress()");
        }
        BackEventCompat backEventCompat = materialBottomContainerBackHelper.backEvent;
        materialBottomContainerBackHelper.backEvent = null;
        if (backEventCompat == null) {
            return;
        }
        Animator createResetScaleAnimator =
                materialBottomContainerBackHelper.createResetScaleAnimator();
        createResetScaleAnimator.setDuration(materialBottomContainerBackHelper.cancelDuration);
        createResetScaleAnimator.start();
    }

    public void disableShapeAnimations() {
        this.interpolatorAnimator = null;
    }

    public final void dispatchOnSlide(int i) {
        View view = (View) this.viewRef.get();
        if (view == null || this.callbacks.isEmpty()) {
            return;
        }
        int i2 = this.collapsedOffset;
        if (i <= i2 && i2 != getExpandedOffset()) {
            getExpandedOffset();
        }
        for (int i3 = 0; i3 < this.callbacks.size(); i3++) {
            ((BottomSheetCallback) this.callbacks.get(i3)).onSlide(view);
        }
    }

    public View findScrollingChild(View view) {
        if (view.getVisibility() != 0) {
            return null;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api21Impl.isNestedScrollingEnabled(view)) {
            return view;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View findScrollingChild = findScrollingChild(viewGroup.getChildAt(i));
                if (findScrollingChild != null) {
                    return findScrollingChild;
                }
            }
        }
        return null;
    }

    public MaterialBottomContainerBackHelper getBackHelper() {
        return this.bottomContainerBackHelper;
    }

    public final int getExpandedOffset() {
        if (this.fitToContents) {
            return this.fitToContentsOffset;
        }
        return Math.max(this.expandedOffset, this.paddingTopSystemWindowInsets ? 0 : this.insetTop);
    }

    public int getPeekHeightMin() {
        return this.peekHeightMin;
    }

    public final int getTopOffsetForState(int i) {
        if (i == 3) {
            return getExpandedOffset();
        }
        if (i == 4) {
            return this.collapsedOffset;
        }
        if (i == 5) {
            return this.parentHeight;
        }
        if (i == 6) {
            return this.halfExpandedOffset;
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                        i, "Invalid state to get top offset: "));
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void handleBackInvoked() {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper =
                this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        BackEventCompat backEventCompat = materialBottomContainerBackHelper.backEvent;
        materialBottomContainerBackHelper.backEvent = null;
        if (backEventCompat == null) {
            setState$1(this.hideable ? 5 : 4);
            return;
        }
        boolean z = this.hideable;
        int i = materialBottomContainerBackHelper.hideDurationMin;
        int i2 = materialBottomContainerBackHelper.hideDurationMax;
        float f = backEventCompat.progress;
        if (!z) {
            Animator createResetScaleAnimator =
                    materialBottomContainerBackHelper.createResetScaleAnimator();
            createResetScaleAnimator.setDuration(AnimationUtils.lerp(i2, i, f));
            createResetScaleAnimator.start();
            setState$1(4);
            return;
        }
        AnimatorListenerAdapter animatorListenerAdapter =
                new AnimatorListenerAdapter() { // from class:
                                                // com.google.android.material.bottomsheet.BottomSheetBehavior.2
                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        BottomSheetBehavior.this.setStateInternal(5);
                        WeakReference weakReference = BottomSheetBehavior.this.viewRef;
                        if (weakReference == null || weakReference.get() == null) {
                            return;
                        }
                        ((View) BottomSheetBehavior.this.viewRef.get()).requestLayout();
                    }
                };
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat(
                        materialBottomContainerBackHelper.view,
                        (Property<View, Float>) View.TRANSLATION_Y,
                        materialBottomContainerBackHelper.view.getScaleY()
                                * materialBottomContainerBackHelper.view.getHeight());
        ofFloat.setInterpolator(new FastOutSlowInInterpolator());
        ofFloat.setDuration(AnimationUtils.lerp(i2, i, f));
        ofFloat.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.google.android.material.motion.MaterialBottomContainerBackHelper.1
                    public AnonymousClass1() {}

                    @Override // android.animation.AnimatorListenerAdapter,
                              // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        MaterialBottomContainerBackHelper.this.view.setTranslationY(0.0f);
                        MaterialBottomContainerBackHelper.this.updateBackProgress(0.0f);
                    }
                });
        ofFloat.addListener(animatorListenerAdapter);
        ofFloat.start();
    }

    public final boolean isAtTopOfScreen() {
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            return false;
        }
        int[] iArr = new int[2];
        ((View) this.viewRef.get()).getLocationOnScreen(iArr);
        return iArr[1] == 0;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onDetachedFromLayoutParams() {
        this.viewRef = null;
        this.viewDragHelper = null;
        this.bottomContainerBackHelper = null;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onInterceptTouchEvent(
            CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int i;
        ViewDragHelper viewDragHelper;
        if (!view.isShown() || !this.draggable) {
            this.ignoreEvents = true;
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            reset$1$1();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            int x = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            if (this.state != 2) {
                WeakReference weakReference = this.nestedScrollingChildRef;
                View view2 = weakReference != null ? (View) weakReference.get() : null;
                if (view2 != null
                        && coordinatorLayout.isPointInChildBounds(view2, x, this.initialY)) {
                    this.activePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                    this.touchingScrollingChild = true;
                }
            }
            this.ignoreEvents =
                    this.activePointerId == -1
                            && !coordinatorLayout.isPointInChildBounds(view, x, this.initialY);
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.touchingScrollingChild = false;
            this.activePointerId = -1;
            if (this.ignoreEvents) {
                this.ignoreEvents = false;
                return false;
            }
        }
        if (!this.ignoreEvents
                && (viewDragHelper = this.viewDragHelper) != null
                && viewDragHelper.shouldInterceptTouchEvent(motionEvent)) {
            return true;
        }
        WeakReference weakReference2 = this.nestedScrollingChildRef;
        View view3 = weakReference2 != null ? (View) weakReference2.get() : null;
        return (actionMasked != 2
                        || view3 == null
                        || this.ignoreEvents
                        || this.state == 1
                        || coordinatorLayout.isPointInChildBounds(
                                view3, (int) motionEvent.getX(), (int) motionEvent.getY())
                        || this.viewDragHelper == null
                        || (i = this.initialY) == -1
                        || Math.abs(((float) i) - motionEvent.getY())
                                <= ((float) this.viewDragHelper.mTouchSlop))
                ? false
                : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.peekHeightMin =
                    coordinatorLayout
                            .getResources()
                            .getDimensionPixelSize(R.dimen.design_bottom_sheet_peek_height_min);
            boolean z = (this.gestureInsetBottomIgnored || this.peekHeightAuto) ? false : true;
            if (this.paddingBottomSystemWindowInsets
                    || this.paddingLeftSystemWindowInsets
                    || this.paddingRightSystemWindowInsets
                    || this.marginLeftSystemWindowInsets
                    || this.marginRightSystemWindowInsets
                    || this.marginTopSystemWindowInsets
                    || z) {
                final AnonymousClass4 anonymousClass4 = new AnonymousClass4(z);
                int paddingStart = view.getPaddingStart();
                view.getPaddingTop();
                int paddingEnd = view.getPaddingEnd();
                int paddingBottom = view.getPaddingBottom();
                final ViewUtils.RelativePadding relativePadding = new ViewUtils.RelativePadding();
                relativePadding.start = paddingStart;
                relativePadding.end = paddingEnd;
                relativePadding.bottom = paddingBottom;
                ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(
                        view,
                        new OnApplyWindowInsetsListener() { // from class:
                            // com.google.android.material.internal.ViewUtils.2
                            /* JADX WARN: Removed duplicated region for block: B:31:0x0082  */
                            /* JADX WARN: Removed duplicated region for block: B:34:0x0090  */
                            @Override // androidx.core.view.OnApplyWindowInsetsListener
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final androidx.core.view.WindowInsetsCompat onApplyWindowInsets(
                                    android.view.View r14,
                                    androidx.core.view.WindowInsetsCompat r15) {
                                /*
                                    r13 = this;
                                    com.google.android.material.internal.ViewUtils$RelativePadding r0 = r2
                                    int r1 = r0.start
                                    com.google.android.material.bottomsheet.BottomSheetBehavior$4 r13 = com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass4.this
                                    r13.getClass()
                                    androidx.core.view.WindowInsetsCompat$Impl r2 = r15.mImpl
                                    r3 = 7
                                    androidx.core.graphics.Insets r3 = r2.getInsets(r3)
                                    r4 = 32
                                    androidx.core.graphics.Insets r2 = r2.getInsets(r4)
                                    int r4 = r3.top
                                    com.google.android.material.bottomsheet.BottomSheetBehavior r5 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                                    r5.insetTop = r4
                                    boolean r4 = com.google.android.material.internal.ViewUtils.isLayoutRtl(r14)
                                    int r6 = r14.getPaddingBottom()
                                    int r7 = r14.getPaddingLeft()
                                    int r8 = r14.getPaddingRight()
                                    boolean r9 = r5.paddingBottomSystemWindowInsets
                                    if (r9 == 0) goto L39
                                    int r6 = r15.getSystemWindowInsetBottom()
                                    r5.insetBottom = r6
                                    int r10 = r0.bottom
                                    int r6 = r6 + r10
                                L39:
                                    int r0 = r0.end
                                    boolean r10 = r5.paddingLeftSystemWindowInsets
                                    int r11 = r3.left
                                    if (r10 == 0) goto L47
                                    if (r4 == 0) goto L45
                                    r7 = r0
                                    goto L46
                                L45:
                                    r7 = r1
                                L46:
                                    int r7 = r7 + r11
                                L47:
                                    boolean r10 = r5.paddingRightSystemWindowInsets
                                    int r12 = r3.right
                                    if (r10 == 0) goto L53
                                    if (r4 == 0) goto L50
                                    goto L51
                                L50:
                                    r1 = r0
                                L51:
                                    int r8 = r1 + r12
                                L53:
                                    android.view.ViewGroup$LayoutParams r0 = r14.getLayoutParams()
                                    android.view.ViewGroup$MarginLayoutParams r0 = (android.view.ViewGroup.MarginLayoutParams) r0
                                    boolean r1 = r5.marginLeftSystemWindowInsets
                                    r4 = 1
                                    if (r1 == 0) goto L66
                                    int r1 = r0.leftMargin
                                    if (r1 == r11) goto L66
                                    r0.leftMargin = r11
                                    r1 = r4
                                    goto L67
                                L66:
                                    r1 = 0
                                L67:
                                    boolean r10 = r5.marginRightSystemWindowInsets
                                    if (r10 == 0) goto L72
                                    int r10 = r0.rightMargin
                                    if (r10 == r12) goto L72
                                    r0.rightMargin = r12
                                    r1 = r4
                                L72:
                                    boolean r10 = r5.marginTopSystemWindowInsets
                                    if (r10 == 0) goto L7f
                                    int r10 = r0.topMargin
                                    int r3 = r3.top
                                    if (r10 == r3) goto L7f
                                    r0.topMargin = r3
                                    goto L80
                                L7f:
                                    r4 = r1
                                L80:
                                    if (r4 == 0) goto L85
                                    r14.setLayoutParams(r0)
                                L85:
                                    int r0 = r14.getPaddingTop()
                                    r14.setPadding(r7, r0, r8, r6)
                                    boolean r13 = r13.val$shouldHandleGestureInsets
                                    if (r13 == 0) goto L94
                                    int r14 = r2.bottom
                                    r5.gestureInsetBottom = r14
                                L94:
                                    if (r9 != 0) goto L98
                                    if (r13 == 0) goto L9b
                                L98:
                                    r5.updatePeekHeight()
                                L9b:
                                    return r15
                                */
                                throw new UnsupportedOperationException(
                                        "Method not decompiled:"
                                            + " com.google.android.material.internal.ViewUtils.AnonymousClass2.onApplyWindowInsets(android.view.View,"
                                            + " androidx.core.view.WindowInsetsCompat):androidx.core.view.WindowInsetsCompat");
                            }
                        });
                if (view.isAttachedToWindow()) {
                    ViewCompat.Api20Impl.requestApplyInsets(view);
                } else {
                    view.addOnAttachStateChangeListener(new ViewUtils.AnonymousClass3());
                }
            }
            view.setWindowInsetsAnimationCallback(
                    new WindowInsetsAnimationCompat.Impl30.ProxyCallback(
                            new InsetsAnimationCallback(view)));
            this.viewRef = new WeakReference(view);
            this.bottomContainerBackHelper = new MaterialBottomContainerBackHelper(view);
            if (materialShapeDrawable != null) {
                view.setBackground(materialShapeDrawable);
                float f = this.elevation;
                if (f == -1.0f) {
                    f = ViewCompat.Api21Impl.getElevation(view);
                }
                materialShapeDrawable.setElevation(f);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.Api21Impl.setBackgroundTintList(view, colorStateList);
                }
            }
            updateAccessibilityActions$1();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
        }
        if (this.viewDragHelper == null) {
            this.viewDragHelper =
                    new ViewDragHelper(
                            coordinatorLayout.getContext(), coordinatorLayout, this.dragCallback);
        }
        int top = view.getTop();
        coordinatorLayout.onLayoutChild(view, i);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentHeight = coordinatorLayout.getHeight();
        int height = view.getHeight();
        this.childHeight = height;
        int i2 = this.parentHeight;
        int i3 = i2 - height;
        int i4 = this.insetTop;
        if (i3 < i4) {
            if (this.paddingTopSystemWindowInsets) {
                int i5 = this.maxHeight;
                if (i5 != -1) {
                    i2 = Math.min(i2, i5);
                }
                this.childHeight = i2;
            } else {
                int i6 = i2 - i4;
                int i7 = this.maxHeight;
                if (i7 != -1) {
                    i6 = Math.min(i6, i7);
                }
                this.childHeight = i6;
            }
        }
        this.fitToContentsOffset = Math.max(0, this.parentHeight - this.childHeight);
        this.halfExpandedOffset = (int) ((1.0f - this.halfExpandedRatio) * this.parentHeight);
        calculateCollapsedOffset();
        int i8 = this.state;
        if (i8 == 3) {
            view.offsetTopAndBottom(getExpandedOffset());
        } else if (i8 == 6) {
            view.offsetTopAndBottom(this.halfExpandedOffset);
        } else if (this.hideable && i8 == 5) {
            view.offsetTopAndBottom(this.parentHeight);
        } else if (i8 == 4) {
            view.offsetTopAndBottom(this.collapsedOffset);
        } else if (i8 == 1 || i8 == 2) {
            view.offsetTopAndBottom(top - view.getTop());
        }
        updateDrawableForTargetState(this.state, false);
        this.nestedScrollingChildRef = new WeakReference(findScrollingChild(view));
        for (int i9 = 0; i9 < this.callbacks.size(); i9++) {
            ((BottomSheetCallback) this.callbacks.get(i9)).onLayout(view);
        }
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onMeasureChild(
            CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(
                getChildMeasureSpec(
                        i,
                        coordinatorLayout.getPaddingRight()
                                + coordinatorLayout.getPaddingLeft()
                                + marginLayoutParams.leftMargin
                                + marginLayoutParams.rightMargin
                                + i2,
                        this.maxWidth,
                        marginLayoutParams.width),
                getChildMeasureSpec(
                        i3,
                        coordinatorLayout.getPaddingBottom()
                                + coordinatorLayout.getPaddingTop()
                                + marginLayoutParams.topMargin
                                + marginLayoutParams.bottomMargin,
                        this.maxHeight,
                        marginLayoutParams.height));
        return true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onNestedPreFling(View view, View view2, float f) {
        WeakReference weakReference = this.nestedScrollingChildRef;
        return (weakReference == null || view2 != weakReference.get() || this.state == 3)
                ? false
                : true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedPreScroll(
            CoordinatorLayout coordinatorLayout,
            View view,
            View view2,
            int i,
            int i2,
            int[] iArr,
            int i3) {
        boolean z = this.draggable;
        if (i3 == 1) {
            return;
        }
        WeakReference weakReference = this.nestedScrollingChildRef;
        if (view2 != (weakReference != null ? (View) weakReference.get() : null)) {
            return;
        }
        int top = view.getTop();
        int i4 = top - i2;
        if (i2 > 0) {
            if (i4 < getExpandedOffset()) {
                int expandedOffset = top - getExpandedOffset();
                iArr[1] = expandedOffset;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-expandedOffset);
                setStateInternal(3);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !view2.canScrollVertically(-1)) {
            int i5 = this.collapsedOffset;
            if (i4 > i5 && !this.hideable) {
                int i6 = top - i5;
                iArr[1] = i6;
                WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i6);
                setStateInternal(4);
            } else {
                if (!z) {
                    return;
                }
                iArr[1] = i2;
                WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(-i2);
                setStateInternal(1);
            }
        }
        dispatchOnSlide(view.getTop());
        this.lastNestedScrollDy = i2;
        this.nestedScrolled = true;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onRestoreInstanceState(View view, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        int i = this.saveFlags;
        if (i != 0) {
            if (i == -1 || (i & 1) == 1) {
                this.peekHeight = savedState.peekHeight;
            }
            if (i == -1 || (i & 2) == 2) {
                this.fitToContents = savedState.fitToContents;
            }
            if (i == -1 || (i & 4) == 4) {
                this.hideable = savedState.hideable;
            }
            if (i == -1 || (i & 8) == 8) {
                this.skipCollapsed = savedState.skipCollapsed;
            }
        }
        int i2 = savedState.state;
        if (i2 == 1 || i2 == 2) {
            this.state = 4;
        } else {
            this.state = i2;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final Parcelable onSaveInstanceState(View view) {
        return new SavedState(View.BaseSavedState.EMPTY_STATE, this);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onStartNestedScroll(
            CoordinatorLayout coordinatorLayout, View view, View view2, View view3, int i, int i2) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (i & 2) != 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:

       if (r4.getTop() <= r2.halfExpandedOffset) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0071, code lost:

       if (java.lang.Math.abs(r3 - r2.fitToContentsOffset) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0080, code lost:

       if (r3 < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0090, code lost:

       if (java.lang.Math.abs(r3 - r1) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L50;
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00ac, code lost:

       if (java.lang.Math.abs(r3 - r2.halfExpandedOffset) < java.lang.Math.abs(r3 - r2.collapsedOffset)) goto L50;
    */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onStopNestedScroll(
            androidx.coordinatorlayout.widget.CoordinatorLayout r3,
            android.view.View r4,
            android.view.View r5,
            int r6) {
        /*
            r2 = this;
            int r3 = r4.getTop()
            int r6 = r2.getExpandedOffset()
            r0 = 3
            if (r3 != r6) goto Lf
            r2.setStateInternal(r0)
            return
        Lf:
            java.lang.ref.WeakReference r3 = r2.nestedScrollingChildRef
            if (r3 == 0) goto Lb5
            java.lang.Object r3 = r3.get()
            if (r5 != r3) goto Lb5
            boolean r3 = r2.nestedScrolled
            if (r3 != 0) goto L1f
            goto Lb5
        L1f:
            int r3 = r2.lastNestedScrollDy
            r5 = 6
            if (r3 <= 0) goto L34
            boolean r3 = r2.fitToContents
            if (r3 == 0) goto L2a
            goto Laf
        L2a:
            int r3 = r4.getTop()
            int r6 = r2.halfExpandedOffset
            if (r3 <= r6) goto Laf
            goto Lae
        L34:
            boolean r3 = r2.hideable
            if (r3 == 0) goto L55
            android.view.VelocityTracker r3 = r2.velocityTracker
            if (r3 != 0) goto L3e
            r3 = 0
            goto L4d
        L3e:
            r6 = 1000(0x3e8, float:1.401E-42)
            float r1 = r2.maximumVelocity
            r3.computeCurrentVelocity(r6, r1)
            android.view.VelocityTracker r3 = r2.velocityTracker
            int r6 = r2.activePointerId
            float r3 = r3.getYVelocity(r6)
        L4d:
            boolean r3 = r2.shouldHide(r4, r3)
            if (r3 == 0) goto L55
            r0 = 5
            goto Laf
        L55:
            int r3 = r2.lastNestedScrollDy
            r6 = 4
            if (r3 != 0) goto L93
            int r3 = r4.getTop()
            boolean r1 = r2.fitToContents
            if (r1 == 0) goto L74
            int r5 = r2.fitToContentsOffset
            int r5 = r3 - r5
            int r5 = java.lang.Math.abs(r5)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r5 >= r3) goto L97
            goto Laf
        L74:
            int r1 = r2.halfExpandedOffset
            if (r3 >= r1) goto L83
            int r6 = r2.collapsedOffset
            int r6 = r3 - r6
            int r6 = java.lang.Math.abs(r6)
            if (r3 >= r6) goto Lae
            goto Laf
        L83:
            int r0 = r3 - r1
            int r0 = java.lang.Math.abs(r0)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r0 >= r3) goto L97
            goto Lae
        L93:
            boolean r3 = r2.fitToContents
            if (r3 == 0) goto L99
        L97:
            r0 = r6
            goto Laf
        L99:
            int r3 = r4.getTop()
            int r0 = r2.halfExpandedOffset
            int r0 = r3 - r0
            int r0 = java.lang.Math.abs(r0)
            int r1 = r2.collapsedOffset
            int r3 = r3 - r1
            int r3 = java.lang.Math.abs(r3)
            if (r0 >= r3) goto L97
        Lae:
            r0 = r5
        Laf:
            r3 = 0
            r2.startSettling(r4, r0, r3)
            r2.nestedScrolled = r3
        Lb5:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.bottomsheet.BottomSheetBehavior.onStopNestedScroll(androidx.coordinatorlayout.widget.CoordinatorLayout,"
                    + " android.view.View, android.view.View, int):void");
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final boolean onTouchEvent(
            CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        int i = this.state;
        if (i == 1 && actionMasked == 0) {
            return true;
        }
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper != null && (this.draggable || i == 1)) {
            viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            reset$1$1();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (this.viewDragHelper != null
                && ((this.draggable || this.state == 1)
                        && actionMasked == 2
                        && !this.ignoreEvents)) {
            float abs = Math.abs(this.initialY - motionEvent.getY());
            ViewDragHelper viewDragHelper2 = this.viewDragHelper;
            if (abs > viewDragHelper2.mTouchSlop) {
                viewDragHelper2.captureChildView(
                        view, motionEvent.getPointerId(motionEvent.getActionIndex()));
            }
        }
        return !this.ignoreEvents;
    }

    public final void reset$1$1() {
        this.activePointerId = -1;
        this.initialY = -1;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.velocityTracker = null;
        }
    }

    public final void setHideable(boolean z) {
        if (this.hideable != z) {
            this.hideable = z;
            if (!z && this.state == 5) {
                setState$1(4);
            }
            updateAccessibilityActions$1();
        }
    }

    public final void setPeekHeight(int i) {
        if (i == -1) {
            if (this.peekHeightAuto) {
                return;
            } else {
                this.peekHeightAuto = true;
            }
        } else {
            if (!this.peekHeightAuto && this.peekHeight == i) {
                return;
            }
            this.peekHeightAuto = false;
            this.peekHeight = Math.max(0, i);
        }
        updatePeekHeight();
    }

    public final void setState$1(int i) {
        if (i == 1 || i == 2) {
            throw new IllegalArgumentException(
                    ComponentActivity$1$$ExternalSyntheticOutline0.m(
                            new StringBuilder("STATE_"),
                            i == 1 ? "DRAGGING" : "SETTLING",
                            " should not be set externally."));
        }
        if (!this.hideable && i == 5) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "Cannot set state: ", "BottomSheetBehavior");
            return;
        }
        final int i2 =
                (i == 6
                                && this.fitToContents
                                && getTopOffsetForState(i) <= this.fitToContentsOffset)
                        ? 3
                        : i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            setStateInternal(i);
            return;
        }
        final View view = (View) this.viewRef.get();
        Runnable runnable =
                new Runnable() { // from class:
                                 // com.google.android.material.bottomsheet.BottomSheetBehavior.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BottomSheetBehavior.this.startSettling(view, i2, false);
                    }
                };
        ViewParent parent = view.getParent();
        if (parent != null && parent.isLayoutRequested()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (view.isAttachedToWindow()) {
                view.post(runnable);
                return;
            }
        }
        runnable.run();
    }

    public final void setStateInternal(int i) {
        View view;
        if (this.state == i) {
            return;
        }
        this.state = i;
        if (i != 4 && i != 3 && i != 6) {
            boolean z = this.hideable;
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        if (i == 3) {
            updateImportantForAccessibility(true);
        } else if (i == 6 || i == 5 || i == 4) {
            updateImportantForAccessibility(false);
        }
        updateDrawableForTargetState(i, true);
        for (int i2 = 0; i2 < this.callbacks.size(); i2++) {
            ((BottomSheetCallback) this.callbacks.get(i2)).onStateChanged(view, i);
        }
        updateAccessibilityActions$1();
    }

    public final boolean shouldHide(View view, float f) {
        if (this.skipCollapsed) {
            return true;
        }
        if (view.getTop() < this.collapsedOffset) {
            return false;
        }
        return Math.abs(
                                ((f * this.hideFriction) + ((float) view.getTop()))
                                        - ((float) this.collapsedOffset))
                        / ((float) calculatePeekHeight())
                > 0.5f;
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void startBackProgress(BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper =
                this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        materialBottomContainerBackHelper.backEvent = backEventCompat;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0030, code lost:

       if (r3 != false) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0012, code lost:

       if (r1.settleCapturedViewAt(r3.getLeft(), r0) != false) goto L16;
    */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0032, code lost:

       setStateInternal(2);
       updateDrawableForTargetState(r4, true);
       r2.stateSettlingTracker.continueSettlingToState(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:7:?, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startSettling(android.view.View r3, int r4, boolean r5) {
        /*
            r2 = this;
            int r0 = r2.getTopOffsetForState(r4)
            androidx.customview.widget.ViewDragHelper r1 = r2.viewDragHelper
            if (r1 == 0) goto L40
            if (r5 == 0) goto L15
            int r3 = r3.getLeft()
            boolean r3 = r1.settleCapturedViewAt(r3, r0)
            if (r3 == 0) goto L40
            goto L32
        L15:
            int r5 = r3.getLeft()
            r1.mCapturedView = r3
            r3 = -1
            r1.mActivePointerId = r3
            r3 = 0
            boolean r3 = r1.forceSettleCapturedViewAt(r5, r0, r3, r3)
            if (r3 != 0) goto L30
            int r5 = r1.mDragState
            if (r5 != 0) goto L30
            android.view.View r5 = r1.mCapturedView
            if (r5 == 0) goto L30
            r5 = 0
            r1.mCapturedView = r5
        L30:
            if (r3 == 0) goto L40
        L32:
            r3 = 2
            r2.setStateInternal(r3)
            r3 = 1
            r2.updateDrawableForTargetState(r4, r3)
            com.google.android.material.bottomsheet.BottomSheetBehavior$StateSettlingTracker r2 = r2.stateSettlingTracker
            r2.continueSettlingToState(r4)
            goto L43
        L40:
            r2.setStateInternal(r4)
        L43:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.bottomsheet.BottomSheetBehavior.startSettling(android.view.View,"
                    + " int, boolean):void");
    }

    public final void updateAccessibilityActions$1() {
        View view;
        int i;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null) {
            return;
        }
        ViewCompat.removeActionWithId(
                view, NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 262144);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        ViewCompat.removeActionWithId(view, 1048576);
        ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
        int i2 = this.expandHalfwayActionIds.get(0, -1);
        if (i2 != -1) {
            ViewCompat.removeActionWithId(view, i2);
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            this.expandHalfwayActionIds.delete(0);
        }
        if (!this.fitToContents && this.state != 6) {
            SparseIntArray sparseIntArray = this.expandHalfwayActionIds;
            String string =
                    view.getResources().getString(R.string.bottomsheet_action_expand_halfway);
            AccessibilityViewCommand accessibilityViewCommand =
                    new AccessibilityViewCommand() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view2) {
                            BottomSheetBehavior.this.setState$1(r2);
                            return true;
                        }
                    };
            List actionList = ViewCompat.getActionList(view);
            int i3 = 0;
            while (true) {
                if (i3 >= actionList.size()) {
                    int i4 = -1;
                    for (int i5 = 0; i5 < 32 && i4 == -1; i5++) {
                        int i6 = ViewCompat.ACCESSIBILITY_ACTIONS_RESOURCE_IDS[i5];
                        boolean z = true;
                        for (int i7 = 0; i7 < actionList.size(); i7++) {
                            z &=
                                    ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)
                                                            actionList.get(i7))
                                                    .getId()
                                            != i6;
                        }
                        if (z) {
                            i4 = i6;
                        }
                    }
                    i = i4;
                } else {
                    if (TextUtils.equals(
                            string,
                            ((AccessibilityNodeInfo.AccessibilityAction)
                                            ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)
                                                            actionList.get(i3))
                                                    .mAction)
                                    .getLabel())) {
                        i =
                                ((AccessibilityNodeInfoCompat.AccessibilityActionCompat)
                                                actionList.get(i3))
                                        .getId();
                        break;
                    }
                    i3++;
                }
            }
            if (i != -1) {
                AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat =
                        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                null, i, string, accessibilityViewCommand, null);
                View.AccessibilityDelegate accessibilityDelegate =
                        ViewCompat.Api29Impl.getAccessibilityDelegate(view);
                AccessibilityDelegateCompat accessibilityDelegateCompat =
                        accessibilityDelegate == null
                                ? null
                                : accessibilityDelegate
                                                instanceof
                                                AccessibilityDelegateCompat
                                                        .AccessibilityDelegateAdapter
                                        ? ((AccessibilityDelegateCompat
                                                                .AccessibilityDelegateAdapter)
                                                        accessibilityDelegate)
                                                .mCompat
                                        : new AccessibilityDelegateCompat(accessibilityDelegate);
                if (accessibilityDelegateCompat == null) {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat();
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
                ViewCompat.removeActionWithId(view, accessibilityActionCompat.getId());
                ViewCompat.getActionList(view).add(accessibilityActionCompat);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            }
            sparseIntArray.put(0, i);
        }
        if (this.hideable) {
            final int i8 = 5;
            if (this.state != 5) {
                ViewCompat.replaceAccessibilityAction(
                        view,
                        AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS,
                        new AccessibilityViewCommand() { // from class:
                                                         // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                            public final boolean perform(View view2) {
                                BottomSheetBehavior.this.setState$1(i8);
                                return true;
                            }
                        });
            }
        }
        int i9 = this.state;
        final int i10 = 4;
        final int i11 = 3;
        if (i9 == 3) {
            r4 = this.fitToContents ? 4 : 6;
            ViewCompat.replaceAccessibilityAction(
                    view,
                    AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE,
                    new AccessibilityViewCommand() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view2) {
                            BottomSheetBehavior.this.setState$1(r2);
                            return true;
                        }
                    });
        } else if (i9 == 4) {
            r4 = this.fitToContents ? 3 : 6;
            ViewCompat.replaceAccessibilityAction(
                    view,
                    AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND,
                    new AccessibilityViewCommand() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view2) {
                            BottomSheetBehavior.this.setState$1(r2);
                            return true;
                        }
                    });
        } else {
            if (i9 != 6) {
                return;
            }
            ViewCompat.replaceAccessibilityAction(
                    view,
                    AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_COLLAPSE,
                    new AccessibilityViewCommand() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view2) {
                            BottomSheetBehavior.this.setState$1(i10);
                            return true;
                        }
                    });
            ViewCompat.replaceAccessibilityAction(
                    view,
                    AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND,
                    new AccessibilityViewCommand() { // from class:
                                                     // com.google.android.material.bottomsheet.BottomSheetBehavior.6
                        @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                        public final boolean perform(View view2) {
                            BottomSheetBehavior.this.setState$1(i11);
                            return true;
                        }
                    });
        }
    }

    @Override // com.google.android.material.motion.MaterialBackHandler
    public final void updateBackProgress(BackEventCompat backEventCompat) {
        MaterialBottomContainerBackHelper materialBottomContainerBackHelper =
                this.bottomContainerBackHelper;
        if (materialBottomContainerBackHelper == null) {
            return;
        }
        if (materialBottomContainerBackHelper.backEvent == null) {
            Log.w(
                    "MaterialBackHelper",
                    "Must call startBackProgress() before updateBackProgress()");
        }
        BackEventCompat backEventCompat2 = materialBottomContainerBackHelper.backEvent;
        materialBottomContainerBackHelper.backEvent = backEventCompat;
        if (backEventCompat2 == null) {
            return;
        }
        materialBottomContainerBackHelper.updateBackProgress(backEventCompat.progress);
    }

    public final void updateDrawableForTargetState(int i, boolean z) {
        ValueAnimator valueAnimator;
        MaterialShapeDrawable materialShapeDrawable = this.materialShapeDrawable;
        if (i == 2) {
            return;
        }
        boolean z2 = this.state == 3 && (this.shouldRemoveExpandedCorners || isAtTopOfScreen());
        if (this.expandedCornersRemoved == z2 || materialShapeDrawable == null) {
            return;
        }
        this.expandedCornersRemoved = z2;
        if (z && (valueAnimator = this.interpolatorAnimator) != null) {
            if (valueAnimator.isRunning()) {
                this.interpolatorAnimator.reverse();
                return;
            } else {
                this.interpolatorAnimator.setFloatValues(
                        materialShapeDrawable.drawableState.interpolation,
                        z2 ? calculateInterpolationWithCornersRemoved() : 1.0f);
                this.interpolatorAnimator.start();
                return;
            }
        }
        ValueAnimator valueAnimator2 = this.interpolatorAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.interpolatorAnimator.cancel();
        }
        float calculateInterpolationWithCornersRemoved =
                this.expandedCornersRemoved ? calculateInterpolationWithCornersRemoved() : 1.0f;
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState =
                materialShapeDrawable.drawableState;
        if (materialShapeDrawableState.interpolation != calculateInterpolationWithCornersRemoved) {
            materialShapeDrawableState.interpolation = calculateInterpolationWithCornersRemoved;
            materialShapeDrawable.pathDirty = true;
            materialShapeDrawable.invalidateSelf();
        }
    }

    public final void updateImportantForAccessibility(boolean z) {
        WeakReference weakReference = this.viewRef;
        if (weakReference == null) {
            return;
        }
        ViewParent parent = ((View) weakReference.get()).getParent();
        if (parent instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) parent;
            int childCount = coordinatorLayout.getChildCount();
            if (z) {
                if (this.importantForAccessibilityMap != null) {
                    return;
                } else {
                    this.importantForAccessibilityMap = new HashMap(childCount);
                }
            }
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if (childAt != this.viewRef.get() && z) {
                    ((HashMap) this.importantForAccessibilityMap)
                            .put(childAt, Integer.valueOf(childAt.getImportantForAccessibility()));
                }
            }
            if (z) {
                return;
            }
            this.importantForAccessibilityMap = null;
        }
    }

    public final void updatePeekHeight() {
        View view;
        if (this.viewRef != null) {
            calculateCollapsedOffset();
            if (this.state != 4 || (view = (View) this.viewRef.get()) == null) {
                return;
            }
            view.requestLayout();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
        public final boolean fitToContents;
        public final boolean hideable;
        public final int peekHeight;
        public final boolean skipCollapsed;
        public final int state;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.android.material.bottomsheet.BottomSheetBehavior$SavedState$1, reason: invalid class name */
        public final class AnonymousClass1 implements Parcelable.ClassLoaderCreator {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
            this.peekHeight = parcel.readInt();
            this.fitToContents = parcel.readInt() == 1;
            this.hideable = parcel.readInt() == 1;
            this.skipCollapsed = parcel.readInt() == 1;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
            parcel.writeInt(this.peekHeight);
            parcel.writeInt(this.fitToContents ? 1 : 0);
            parcel.writeInt(this.hideable ? 1 : 0);
            parcel.writeInt(this.skipCollapsed ? 1 : 0);
        }

        public SavedState(Parcelable parcelable, BottomSheetBehavior bottomSheetBehavior) {
            super(parcelable);
            this.state = bottomSheetBehavior.state;
            this.peekHeight = bottomSheetBehavior.peekHeight;
            this.fitToContents = bottomSheetBehavior.fitToContents;
            this.hideable = bottomSheetBehavior.hideable;
            this.skipCollapsed = bottomSheetBehavior.skipCollapsed;
        }
    }

    /* JADX WARN: Type inference failed for: r7v3, types: [com.google.android.material.bottomsheet.BottomSheetBehavior$5] */
    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        int i;
        this.saveFlags = 0;
        this.fitToContents = true;
        this.maxWidth = -1;
        this.maxHeight = -1;
        this.stateSettlingTracker = new StateSettlingTracker();
        this.halfExpandedRatio = 0.5f;
        this.elevation = -1.0f;
        this.draggable = true;
        this.state = 4;
        this.hideFriction = 0.1f;
        this.callbacks = new ArrayList();
        this.initialY = -1;
        this.expandHalfwayActionIds = new SparseIntArray();
        this.dragCallback =
                new ViewDragHelper.Callback() { // from class:
                    // com.google.android.material.bottomsheet.BottomSheetBehavior.5
                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int clampViewPositionHorizontal(View view, int i2) {
                        return view.getLeft();
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int clampViewPositionVertical(View view, int i2) {
                        return MathUtils.clamp(
                                i2,
                                BottomSheetBehavior.this.getExpandedOffset(),
                                getViewVerticalDragRange());
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final int getViewVerticalDragRange() {
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        return bottomSheetBehavior.hideable
                                ? bottomSheetBehavior.parentHeight
                                : bottomSheetBehavior.collapsedOffset;
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final void onViewDragStateChanged(int i2) {
                        if (i2 == 1) {
                            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                            if (bottomSheetBehavior.draggable) {
                                bottomSheetBehavior.setStateInternal(1);
                            }
                        }
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final void onViewPositionChanged(View view, int i2, int i22) {
                        BottomSheetBehavior.this.dispatchOnSlide(i22);
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final void onViewReleased(View view, float f, float f2) {
                        /*
                            this = this;
                            r0 = 0
                            int r1 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                            r2 = 6
                            r3 = 3
                            com.google.android.material.bottomsheet.BottomSheetBehavior r4 = com.google.android.material.bottomsheet.BottomSheetBehavior.this
                            if (r1 >= 0) goto L1d
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L10
                        Ld:
                            r2 = r3
                            goto Ld4
                        L10:
                            int r6 = r5.getTop()
                            java.lang.System.currentTimeMillis()
                            int r7 = r4.halfExpandedOffset
                            if (r6 <= r7) goto Ld
                            goto Ld4
                        L1d:
                            boolean r1 = r4.hideable
                            if (r1 == 0) goto L6c
                            boolean r1 = r4.shouldHide(r5, r7)
                            if (r1 == 0) goto L6c
                            float r6 = java.lang.Math.abs(r6)
                            float r0 = java.lang.Math.abs(r7)
                            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                            if (r6 >= 0) goto L3a
                            int r6 = r4.significantVelocityThreshold
                            float r6 = (float) r6
                            int r6 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
                            if (r6 > 0) goto L49
                        L3a:
                            int r6 = r5.getTop()
                            int r7 = r4.parentHeight
                            int r0 = r4.getExpandedOffset()
                            int r0 = r0 + r7
                            int r0 = r0 / 2
                            if (r6 <= r0) goto L4c
                        L49:
                            r2 = 5
                            goto Ld4
                        L4c:
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L51
                            goto Ld
                        L51:
                            int r6 = r5.getTop()
                            int r7 = r4.getExpandedOffset()
                            int r6 = r6 - r7
                            int r6 = java.lang.Math.abs(r6)
                            int r7 = r5.getTop()
                            int r0 = r4.halfExpandedOffset
                            int r7 = r7 - r0
                            int r7 = java.lang.Math.abs(r7)
                            if (r6 >= r7) goto Ld4
                            goto Ld
                        L6c:
                            int r0 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
                            r1 = 4
                            if (r0 == 0) goto L9a
                            float r6 = java.lang.Math.abs(r6)
                            float r7 = java.lang.Math.abs(r7)
                            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
                            if (r6 <= 0) goto L7e
                            goto L9a
                        L7e:
                            boolean r6 = r4.fitToContents
                            if (r6 == 0) goto L84
                        L82:
                            r2 = r1
                            goto Ld4
                        L84:
                            int r6 = r5.getTop()
                            int r7 = r4.halfExpandedOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                            goto Ld4
                        L9a:
                            int r6 = r5.getTop()
                            boolean r7 = r4.fitToContents
                            if (r7 == 0) goto Lb5
                            int r7 = r4.fitToContentsOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                            goto Ld
                        Lb5:
                            int r7 = r4.halfExpandedOffset
                            if (r6 >= r7) goto Lc5
                            int r7 = r4.collapsedOffset
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            if (r6 >= r7) goto Ld4
                            goto Ld
                        Lc5:
                            int r7 = r6 - r7
                            int r7 = java.lang.Math.abs(r7)
                            int r0 = r4.collapsedOffset
                            int r6 = r6 - r0
                            int r6 = java.lang.Math.abs(r6)
                            if (r7 >= r6) goto L82
                        Ld4:
                            r4.getClass()
                            r6 = 1
                            r4.startSettling(r5, r2, r6)
                            return
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.google.android.material.bottomsheet.BottomSheetBehavior.AnonymousClass5.onViewReleased(android.view.View,"
                                    + " float, float):void");
                    }

                    @Override // androidx.customview.widget.ViewDragHelper.Callback
                    public final boolean tryCaptureView(View view, int i2) {
                        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.this;
                        int i22 = bottomSheetBehavior.state;
                        if (i22 == 1 || bottomSheetBehavior.touchingScrollingChild) {
                            return false;
                        }
                        if (i22 == 3 && bottomSheetBehavior.activePointerId == i2) {
                            WeakReference weakReference =
                                    bottomSheetBehavior.nestedScrollingChildRef;
                            View view2 = weakReference != null ? (View) weakReference.get() : null;
                            if (view2 != null && view2.canScrollVertically(-1)) {
                                return false;
                            }
                        }
                        System.currentTimeMillis();
                        WeakReference weakReference2 = bottomSheetBehavior.viewRef;
                        return weakReference2 != null && weakReference2.get() == view;
                    }
                };
        this.peekHeightGestureInsetBuffer =
                context.getResources().getDimensionPixelSize(R.dimen.mtrl_min_touch_target_size);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, R$styleable.BottomSheetBehavior_Layout);
        if (obtainStyledAttributes.hasValue(3)) {
            this.backgroundTint =
                    MaterialResources.getColorStateList(context, obtainStyledAttributes, 3);
        }
        if (obtainStyledAttributes.hasValue(21)) {
            this.shapeAppearanceModelDefault =
                    ShapeAppearanceModel.builder(
                                    context, attributeSet, R.attr.bottomSheetStyle, 2132084667)
                            .build();
        }
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModelDefault;
        if (shapeAppearanceModel != null) {
            MaterialShapeDrawable materialShapeDrawable =
                    new MaterialShapeDrawable(shapeAppearanceModel);
            this.materialShapeDrawable = materialShapeDrawable;
            materialShapeDrawable.initializeElevationOverlay(context);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
            } else {
                TypedValue typedValue = new TypedValue();
                context.getTheme()
                        .resolveAttribute(android.R.attr.colorBackground, typedValue, true);
                this.materialShapeDrawable.setTint(typedValue.data);
            }
        }
        ValueAnimator ofFloat =
                ValueAnimator.ofFloat(calculateInterpolationWithCornersRemoved(), 1.0f);
        this.interpolatorAnimator = ofFloat;
        ofFloat.setDuration(500L);
        this.interpolatorAnimator.addUpdateListener(
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.google.android.material.bottomsheet.BottomSheetBehavior.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        MaterialShapeDrawable materialShapeDrawable2 =
                                BottomSheetBehavior.this.materialShapeDrawable;
                        if (materialShapeDrawable2 != null) {
                            MaterialShapeDrawable.MaterialShapeDrawableState
                                    materialShapeDrawableState =
                                            materialShapeDrawable2.drawableState;
                            if (materialShapeDrawableState.interpolation != floatValue) {
                                materialShapeDrawableState.interpolation = floatValue;
                                materialShapeDrawable2.pathDirty = true;
                                materialShapeDrawable2.invalidateSelf();
                            }
                        }
                    }
                });
        this.elevation = obtainStyledAttributes.getDimension(2, -1.0f);
        if (obtainStyledAttributes.hasValue(0)) {
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        }
        if (obtainStyledAttributes.hasValue(1)) {
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(1, -1);
        }
        TypedValue peekValue = obtainStyledAttributes.peekValue(9);
        if (peekValue != null && (i = peekValue.data) == -1) {
            setPeekHeight(i);
        } else {
            setPeekHeight(obtainStyledAttributes.getDimensionPixelSize(9, -1));
        }
        setHideable(obtainStyledAttributes.getBoolean(8, false));
        this.gestureInsetBottomIgnored = obtainStyledAttributes.getBoolean(13, false);
        boolean z = obtainStyledAttributes.getBoolean(6, true);
        if (this.fitToContents != z) {
            this.fitToContents = z;
            if (this.viewRef != null) {
                calculateCollapsedOffset();
            }
            setStateInternal((this.fitToContents && this.state == 6) ? 3 : this.state);
            updateDrawableForTargetState(this.state, true);
            updateAccessibilityActions$1();
        }
        this.skipCollapsed = obtainStyledAttributes.getBoolean(12, false);
        this.draggable = obtainStyledAttributes.getBoolean(4, true);
        this.saveFlags = obtainStyledAttributes.getInt(10, 0);
        float f = obtainStyledAttributes.getFloat(7, 0.5f);
        if (f > 0.0f && f < 1.0f) {
            this.halfExpandedRatio = f;
            if (this.viewRef != null) {
                this.halfExpandedOffset = (int) ((1.0f - f) * this.parentHeight);
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(5);
            if (peekValue2 != null && peekValue2.type == 16) {
                int i2 = peekValue2.data;
                if (i2 >= 0) {
                    this.expandedOffset = i2;
                    updateDrawableForTargetState(this.state, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            } else {
                int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(5, 0);
                if (dimensionPixelOffset >= 0) {
                    this.expandedOffset = dimensionPixelOffset;
                    updateDrawableForTargetState(this.state, true);
                } else {
                    throw new IllegalArgumentException("offset must be greater than or equal to 0");
                }
            }
            this.significantVelocityThreshold = obtainStyledAttributes.getInt(11, 500);
            this.paddingBottomSystemWindowInsets = obtainStyledAttributes.getBoolean(17, false);
            this.paddingLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(18, false);
            this.paddingRightSystemWindowInsets = obtainStyledAttributes.getBoolean(19, false);
            this.paddingTopSystemWindowInsets = obtainStyledAttributes.getBoolean(20, true);
            this.marginLeftSystemWindowInsets = obtainStyledAttributes.getBoolean(14, false);
            this.marginRightSystemWindowInsets = obtainStyledAttributes.getBoolean(15, false);
            this.marginTopSystemWindowInsets = obtainStyledAttributes.getBoolean(16, false);
            this.shouldRemoveExpandedCorners = obtainStyledAttributes.getBoolean(23, true);
            obtainStyledAttributes.recycle();
            this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
            return;
        }
        throw new IllegalArgumentException("ratio must be a float value between 0 and 1");
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class BottomSheetCallback {
        public abstract void onSlide(View view);

        public abstract void onStateChanged(View view, int i);

        public void onLayout(View view) {}
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public final void onNestedScroll(
            CoordinatorLayout coordinatorLayout,
            View view,
            View view2,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int[] iArr) {}
}
