package com.google.android.material.carousel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView$SmoothScroller$ScrollVectorProvider;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class CarouselLayoutManager extends RecyclerView.LayoutManager
        implements RecyclerView$SmoothScroller$ScrollVectorProvider {
    public final MultiBrowseCarouselStrategy carouselStrategy;
    public final KeylineState currentKeylineState;
    public final DebugItemDecoration debugItemDecoration;
    int maxScroll;
    int minScroll;
    public CarouselOrientationHelper$1 orientationHelper;
    public final View.OnLayoutChangeListener recyclerViewSizeChangeListener;
    int scrollOffset;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DebugItemDecoration extends RecyclerView.ItemDecoration {
        public final List keylines;
        public final Paint linePaint;

        public DebugItemDecoration() {
            Paint paint = new Paint();
            this.linePaint = paint;
            this.keylines = Collections.unmodifiableList(new ArrayList());
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public final void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
            int i;
            int i2;
            int paddingLeft;
            int paddingRight;
            this.linePaint.setStrokeWidth(
                    recyclerView
                            .getResources()
                            .getDimension(R.dimen.m3_carousel_debug_keyline_width));
            for (KeylineState.Keyline keyline : this.keylines) {
                this.linePaint.setColor(ColorUtils.blendARGB(-65281, -16776961, keyline.mask));
                if (((CarouselLayoutManager) recyclerView.getLayoutManager()).isHorizontal()) {
                    CarouselOrientationHelper$1 carouselOrientationHelper$1 =
                            ((CarouselLayoutManager) recyclerView.getLayoutManager())
                                    .orientationHelper;
                    switch (carouselOrientationHelper$1.$r8$classId) {
                        case 0:
                            i = 0;
                            break;
                        default:
                            i =
                                    carouselOrientationHelper$1.val$carouselLayoutManager
                                            .getPaddingTop();
                            break;
                    }
                    float f = i;
                    CarouselOrientationHelper$1 carouselOrientationHelper$12 =
                            ((CarouselLayoutManager) recyclerView.getLayoutManager())
                                    .orientationHelper;
                    switch (carouselOrientationHelper$12.$r8$classId) {
                        case 0:
                            i2 = carouselOrientationHelper$12.val$carouselLayoutManager.mHeight;
                            break;
                        default:
                            CarouselLayoutManager carouselLayoutManager =
                                    carouselOrientationHelper$12.val$carouselLayoutManager;
                            i2 =
                                    carouselLayoutManager.mHeight
                                            - carouselLayoutManager.getPaddingBottom();
                            break;
                    }
                    Paint paint = this.linePaint;
                    float f2 = keyline.locOffset;
                    canvas.drawLine(f2, f, f2, i2, paint);
                } else {
                    CarouselOrientationHelper$1 carouselOrientationHelper$13 =
                            ((CarouselLayoutManager) recyclerView.getLayoutManager())
                                    .orientationHelper;
                    switch (carouselOrientationHelper$13.$r8$classId) {
                        case 0:
                            paddingLeft =
                                    carouselOrientationHelper$13.val$carouselLayoutManager
                                            .getPaddingLeft();
                            break;
                        default:
                            paddingLeft = 0;
                            break;
                    }
                    float f3 = paddingLeft;
                    CarouselOrientationHelper$1 carouselOrientationHelper$14 =
                            ((CarouselLayoutManager) recyclerView.getLayoutManager())
                                    .orientationHelper;
                    switch (carouselOrientationHelper$14.$r8$classId) {
                        case 0:
                            CarouselLayoutManager carouselLayoutManager2 =
                                    carouselOrientationHelper$14.val$carouselLayoutManager;
                            paddingRight =
                                    carouselLayoutManager2.mWidth
                                            - carouselLayoutManager2.getPaddingRight();
                            break;
                        default:
                            paddingRight =
                                    carouselOrientationHelper$14.val$carouselLayoutManager.mWidth;
                            break;
                    }
                    Paint paint2 = this.linePaint;
                    float f4 = keyline.locOffset;
                    canvas.drawLine(f3, f4, paddingRight, f4, paint2);
                }
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeylineRange {
        public final KeylineState.Keyline leftOrTop;
        public final KeylineState.Keyline rightOrBottom;

        public KeylineRange(KeylineState.Keyline keyline, KeylineState.Keyline keyline2) {
            if (keyline.loc > keyline2.loc) {
                throw new IllegalArgumentException();
            }
        }
    }

    public CarouselLayoutManager() {
        new MultiBrowseCarouselStrategy();
        new DebugItemDecoration();
        this.recyclerViewSizeChangeListener =
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        final CarouselLayoutManager carouselLayoutManager =
                                CarouselLayoutManager.this;
                        carouselLayoutManager.getClass();
                        if (i == i5 && i2 == i6 && i3 == i7 && i4 == i8) {
                            return;
                        }
                        view.post(
                                new Runnable() { // from class:
                                                 // com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CarouselLayoutManager.this.refreshKeylineState();
                                    }
                                });
                    }
                };
        requestLayout();
        setOrientation(0);
    }

    public final float calculateChildStartForFill(int i) {
        int i2;
        CarouselOrientationHelper$1 carouselOrientationHelper$1 = this.orientationHelper;
        switch (carouselOrientationHelper$1.$r8$classId) {
            default:
                CarouselLayoutManager carouselLayoutManager =
                        carouselOrientationHelper$1.val$carouselLayoutManager;
                if (carouselLayoutManager.isLayoutRtl()) {
                    i2 = carouselLayoutManager.mWidth;
                    break;
                }
            case 0:
                i2 = 0;
                break;
        }
        float f = i2 - this.scrollOffset;
        float f2 = this.currentKeylineState.itemSize * i;
        return isLayoutRtl() ? f - f2 : f + f2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollHorizontally() {
        return isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean canScrollVertically() {
        return !isHorizontal();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollExtent(RecyclerView.State state) {
        getChildCount();
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView$SmoothScroller$ScrollVectorProvider
    public final PointF computeScrollVectorForPosition(int i) {
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollExtent(RecyclerView.State state) {
        getChildCount();
        return 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int computeVerticalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void getDecoratedBoundsWithMargins(Rect rect, View view) {
        RecyclerView.getDecoratedBoundsWithMarginsInt(rect, view);
        float centerY = rect.centerY();
        if (isHorizontal()) {
            centerY = rect.centerX();
        }
        List list = this.currentKeylineState.keylines;
        float f = Float.MAX_VALUE;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        float f2 = -3.4028235E38f;
        float f3 = Float.MAX_VALUE;
        float f4 = Float.MAX_VALUE;
        for (int i5 = 0; i5 < list.size(); i5++) {
            ((KeylineState.Keyline) list.get(i5)).getClass();
            float abs = Math.abs(0.0f - centerY);
            if (0.0f <= centerY && abs <= f) {
                i = i5;
                f = abs;
            }
            if (0.0f > centerY && abs <= f3) {
                i3 = i5;
                f3 = abs;
            }
            if (0.0f <= f4) {
                i2 = i5;
                f4 = 0.0f;
            }
            if (0.0f > f2) {
                i4 = i5;
                f2 = 0.0f;
            }
        }
        if (i == -1) {
            i = i2;
        }
        if (i3 == -1) {
            i3 = i4;
        }
        KeylineRange keylineRange =
                new KeylineRange(
                        (KeylineState.Keyline) list.get(i), (KeylineState.Keyline) list.get(i3));
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f5 = keyline.maskedItemSize;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        float lerp =
                AnimationUtils.lerp(
                        f5,
                        keyline2.maskedItemSize,
                        keyline.locOffset,
                        keyline2.locOffset,
                        centerY);
        float width = isHorizontal() ? (rect.width() - lerp) / 2.0f : 0.0f;
        float height = isHorizontal() ? 0.0f : (rect.height() - lerp) / 2.0f;
        rect.set(
                (int) (rect.left + width),
                (int) (rect.top + height),
                (int) (rect.right - width),
                (int) (rect.bottom - height));
    }

    public final boolean isHorizontal() {
        return this.orientationHelper.orientation == 0;
    }

    public final boolean isLayoutRtl() {
        return isHorizontal() && getLayoutDirection() == 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onAttachedToWindow(RecyclerView recyclerView) {
        refreshKeylineState();
        recyclerView.addOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onDetachedFromWindow(RecyclerView recyclerView) {
        recyclerView.removeOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x002e, code lost:

       if (r10 == 1) goto L30;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0037, code lost:

       if (isLayoutRtl() != false) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x003a, code lost:

       if (r10 == 1) goto L31;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0043, code lost:

       if (isLayoutRtl() != false) goto L30;
    */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004b  */
    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onFocusSearchFailed(
            android.view.View r7,
            int r8,
            androidx.recyclerview.widget.RecyclerView.Recycler r9,
            androidx.recyclerview.widget.RecyclerView.State r10) {
        /*
            r6 = this;
            int r10 = r6.getChildCount()
            r0 = 0
            if (r10 != 0) goto L8
            return r0
        L8:
            com.google.android.material.carousel.CarouselOrientationHelper$1 r10 = r6.orientationHelper
            int r10 = r10.orientation
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r2 = -1
            r3 = 1
            if (r8 == r3) goto L47
            r4 = 2
            if (r8 == r4) goto L45
            r4 = 17
            if (r8 == r4) goto L3d
            r4 = 33
            if (r8 == r4) goto L3a
            r4 = 66
            if (r8 == r4) goto L31
            r4 = 130(0x82, float:1.82E-43)
            if (r8 == r4) goto L2e
            java.lang.String r10 = "Unknown focus request:"
            java.lang.String r4 = "CarouselLayoutManager"
            androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0.m1m(r8, r10, r4)
        L2c:
            r8 = r1
            goto L48
        L2e:
            if (r10 != r3) goto L2c
            goto L45
        L31:
            if (r10 != 0) goto L2c
            boolean r8 = r6.isLayoutRtl()
            if (r8 == 0) goto L45
            goto L47
        L3a:
            if (r10 != r3) goto L2c
            goto L47
        L3d:
            if (r10 != 0) goto L2c
            boolean r8 = r6.isLayoutRtl()
            if (r8 == 0) goto L47
        L45:
            r8 = r3
            goto L48
        L47:
            r8 = r2
        L48:
            if (r8 != r1) goto L4b
            return r0
        L4b:
            java.lang.String r10 = "All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup."
            r4 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
            r1 = 0
            if (r8 != r2) goto L8e
            int r7 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r7)
            if (r7 != 0) goto L5c
            return r0
        L5c:
            android.view.View r7 = r6.getChildAt(r1)
            int r7 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r7)
            int r7 = r7 - r3
            if (r7 < 0) goto L7d
            int r8 = r6.getItemCount()
            if (r7 < r8) goto L6e
            goto L7d
        L6e:
            r6.calculateChildStartForFill(r7)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = r9.tryGetViewHolderForPositionByDeadline(r7, r4)
            android.view.View r6 = r6.itemView
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>(r10)
            throw r6
        L7d:
            boolean r7 = r6.isLayoutRtl()
            if (r7 == 0) goto L89
            int r7 = r6.getChildCount()
            int r1 = r7 + (-1)
        L89:
            android.view.View r6 = r6.getChildAt(r1)
            goto Ld1
        L8e:
            int r7 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r7)
            int r8 = r6.getItemCount()
            int r8 = r8 - r3
            if (r7 != r8) goto L9a
            return r0
        L9a:
            int r7 = r6.getChildCount()
            int r7 = r7 - r3
            android.view.View r7 = r6.getChildAt(r7)
            int r7 = androidx.recyclerview.widget.RecyclerView.LayoutManager.getPosition(r7)
            int r7 = r7 + r3
            if (r7 < 0) goto Lc0
            int r8 = r6.getItemCount()
            if (r7 < r8) goto Lb1
            goto Lc0
        Lb1:
            r6.calculateChildStartForFill(r7)
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = r9.tryGetViewHolderForPositionByDeadline(r7, r4)
            android.view.View r6 = r6.itemView
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            r6.<init>(r10)
            throw r6
        Lc0:
            boolean r7 = r6.isLayoutRtl()
            if (r7 == 0) goto Lc7
            goto Lcd
        Lc7:
            int r7 = r6.getChildCount()
            int r1 = r7 + (-1)
        Lcd:
            android.view.View r6 = r6.getChildAt(r1)
        Ld1:
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.carousel.CarouselLayoutManager.onFocusSearchFailed(android.view.View,"
                    + " int, androidx.recyclerview.widget.RecyclerView$Recycler,"
                    + " androidx.recyclerview.widget.RecyclerView$State):android.view.View");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(RecyclerView.LayoutManager.getPosition(getChildAt(0)));
            accessibilityEvent.setToIndex(
                    RecyclerView.LayoutManager.getPosition(getChildAt(getChildCount() - 1)));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsAdded(int i, int i2) {
        getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onItemsRemoved(int i, int i2) {
        getItemCount();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() > 0) {
            if ((isHorizontal() ? this.mWidth : this.mHeight) > 0.0f) {
                isLayoutRtl();
                View view =
                        recycler.tryGetViewHolderForPositionByDeadline(0, Long.MAX_VALUE).itemView;
                throw new IllegalStateException(
                        "All children of a RecyclerView using CarouselLayoutManager must use"
                            + " MaskableFrameLayout as their root ViewGroup.");
            }
        }
        removeAndRecycleAllViews(recycler);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void onLayoutCompleted(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return;
        }
        RecyclerView.LayoutManager.getPosition(getChildAt(0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final boolean requestChildRectangleOnScreen(
            RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollHorizontallyBy(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!isHorizontal() || getChildCount() == 0 || i == 0) {
            return 0;
        }
        View view = recycler.tryGetViewHolderForPositionByDeadline(0, Long.MAX_VALUE).itemView;
        throw new IllegalStateException(
                "All children of a RecyclerView using CarouselLayoutManager must use"
                    + " MaskableFrameLayout as their root ViewGroup.");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final int scrollVerticallyBy(
            int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!canScrollVertically() || getChildCount() == 0 || i == 0) {
            return 0;
        }
        View view = recycler.tryGetViewHolderForPositionByDeadline(0, Long.MAX_VALUE).itemView;
        throw new IllegalStateException(
                "All children of a RecyclerView using CarouselLayoutManager must use"
                    + " MaskableFrameLayout as their root ViewGroup.");
    }

    public final void setOrientation(int i) {
        CarouselOrientationHelper$1 carouselOrientationHelper$1;
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "invalid orientation:"));
        }
        assertNotInLayoutOrScroll(null);
        CarouselOrientationHelper$1 carouselOrientationHelper$12 = this.orientationHelper;
        if (carouselOrientationHelper$12 == null || i != carouselOrientationHelper$12.orientation) {
            if (i == 0) {
                carouselOrientationHelper$1 = new CarouselOrientationHelper$1(this, 1);
            } else {
                if (i != 1) {
                    throw new IllegalArgumentException("invalid orientation");
                }
                carouselOrientationHelper$1 = new CarouselOrientationHelper$1(this, 0);
            }
            this.orientationHelper = carouselOrientationHelper$1;
            requestLayout();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void smoothScrollToPosition(int i, RecyclerView recyclerView) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(
                        recyclerView
                                .getContext()) { // from class:
                                                 // com.google.android.material.carousel.CarouselLayoutManager.1
                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public final int calculateDxToMakeVisible(View view, int i2) {
                        CarouselLayoutManager.this.getClass();
                        return 0;
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public final int calculateDyToMakeVisible(View view, int i2) {
                        CarouselLayoutManager.this.getClass();
                        return 0;
                    }

                    @Override // androidx.recyclerview.widget.LinearSmoothScroller
                    public final PointF computeScrollVectorForPosition(int i2) {
                        return CarouselLayoutManager.this.computeScrollVectorForPosition(i2);
                    }
                };
        linearSmoothScroller.mTargetPosition = i;
        startSmoothScroll(linearSmoothScroller);
    }

    @SuppressLint({"UnknownNullness"})
    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        new DebugItemDecoration();
        this.recyclerViewSizeChangeListener =
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i3,
                            int i22,
                            int i32,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        final CarouselLayoutManager carouselLayoutManager =
                                CarouselLayoutManager.this;
                        carouselLayoutManager.getClass();
                        if (i3 == i5 && i22 == i6 && i32 == i7 && i4 == i8) {
                            return;
                        }
                        view.post(
                                new Runnable() { // from class:
                                                 // com.google.android.material.carousel.CarouselLayoutManager$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        CarouselLayoutManager.this.refreshKeylineState();
                                    }
                                });
                    }
                };
        new MultiBrowseCarouselStrategy();
        requestLayout();
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            obtainStyledAttributes.getInt(0, 0);
            requestLayout();
            setOrientation(obtainStyledAttributes.getInt(0, 0));
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public final void scrollToPosition(int i) {}
}
