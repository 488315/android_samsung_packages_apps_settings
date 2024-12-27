package com.google.android.material.appbar;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.preference.Preference;

import java.lang.ref.WeakReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class HeaderBehavior extends ViewOffsetBehavior {
    public int activePointerId;
    public FlingRunnable flingRunnable;
    public boolean isBeingDragged;
    public int lastMotionY;
    public boolean mHasNoSnapFlag;
    public int mLastInterceptTouchEvent;
    public int mLastTouchEvent;
    public OverScroller scroller;
    public int touchSlop;
    public VelocityTracker velocityTracker;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FlingRunnable implements Runnable {
        public final View layout;
        public final CoordinatorLayout parent;

        public FlingRunnable(CoordinatorLayout coordinatorLayout, View view) {
            this.parent = coordinatorLayout;
            this.layout = view;
        }

        @Override // java.lang.Runnable
        public final void run() {
            OverScroller overScroller;
            if (this.layout == null || (overScroller = HeaderBehavior.this.scroller) == null) {
                return;
            }
            if (overScroller.computeScrollOffset()) {
                HeaderBehavior headerBehavior = HeaderBehavior.this;
                headerBehavior.setHeaderTopBottomOffset(
                        this.parent, this.layout, headerBehavior.scroller.getCurrY());
                this.layout.postOnAnimation(this);
                return;
            }
            HeaderBehavior headerBehavior2 = HeaderBehavior.this;
            View view = this.layout;
            AppBarLayout.BaseBehavior baseBehavior = (AppBarLayout.BaseBehavior) headerBehavior2;
            baseBehavior.getClass();
            OverScroller overScroller2 = baseBehavior.scroller;
            if (overScroller2 != null) {
                overScroller2.forceFinished(true);
            }
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(
            CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        boolean z;
        View view2;
        int findPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop =
                    ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        this.mLastInterceptTouchEvent = motionEvent.getAction();
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            int i = this.activePointerId;
            if (i == -1 || (findPointerIndex = motionEvent.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            AppBarLayout.BaseBehavior baseBehavior = (AppBarLayout.BaseBehavior) this;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            AppBarLayout.BaseBehavior.BaseDragCallback baseDragCallback =
                    baseBehavior.onDragCallback;
            if (baseDragCallback != null) {
                z = baseDragCallback.canDrag(appBarLayout);
            } else {
                WeakReference weakReference = baseBehavior.lastNestedScrollingChildRef;
                z =
                        weakReference == null
                                || !((view2 = (View) weakReference.get()) == null
                                        || !view2.isShown()
                                        || view2.canScrollVertically(-1));
            }
            boolean z2 = z && coordinatorLayout.isPointInChildBounds(view, x, y2);
            this.isBeingDragged = z2;
            if (z2) {
                this.lastMotionY = y2;
                this.activePointerId = motionEvent.getPointerId(0);
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        return false;
    }

    public abstract int setHeaderTopBottomOffset(
            CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3);

    public final void setHeaderTopBottomOffset(
            CoordinatorLayout coordinatorLayout, View view, int i) {
        setHeaderTopBottomOffset(
                coordinatorLayout, view, i, Integer.MIN_VALUE, Preference.DEFAULT_ORDER);
    }
}
