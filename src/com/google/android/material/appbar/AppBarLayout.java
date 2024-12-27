package com.google.android.material.appbar;

import android.animation.AnimatorInflater;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.appcompat.animation.SeslAnimationUtils;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.view.SeslContentViewInsetsCallback$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.AppBarLayoutBehavior;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.graphics.Insets;
import androidx.core.math.MathUtils;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.appbar.SeslImmersiveScrollBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class AppBarLayout extends LinearLayout implements CoordinatorLayout.AttachedBehavior, AppBarLayoutBehavior {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float appBarElevation;
    public Behavior behavior;
    public int currentOffset;
    public int downPreScrollRange;
    public int downScrollRange;
    public final boolean hasLiftOnScrollColor;
    public boolean haveChildWithInterpolator;
    public boolean isMouse;
    public WindowInsetsCompat lastInsets;
    public final boolean liftOnScroll;
    public ValueAnimator liftOnScrollColorAnimator;
    public final long liftOnScrollColorDuration;
    public final TimeInterpolator liftOnScrollColorInterpolator;
    public final ValueAnimator.AnimatorUpdateListener liftOnScrollColorUpdateListener;
    public final List liftOnScrollListeners;
    public WeakReference liftOnScrollTargetView;
    public final int liftOnScrollTargetViewId;
    public boolean liftable;
    public boolean lifted;
    public List listeners;
    public final SeslAppbarState mAppbarState;
    public Drawable mBackground;
    public int mBottomPadding;
    public float mCollapsedHeight;
    public int mCurrentOrientation;
    public int mCurrentScreenHeight;
    public final float mCustomHeightProportion;
    public float mHeightProportion;
    public int mImmersiveTopInset;
    public boolean mIsActivatedByUser;
    public boolean mIsActivatedImmersiveScroll;
    public boolean mIsCanScroll;
    public boolean mIsDetachedState;
    public Insets mLastSysInsets;
    public Insets mLastTappableInsets;
    public final Resources mResources;
    public final boolean mSetCustomProportion;
    public boolean mUseCollapsedHeight;
    public final boolean mUseCustomHeight;
    public final boolean mUseCustomPadding;
    public int pendingAction;
    public final Drawable statusBarForeground;
    public final Integer statusBarForegroundOriginalColor;
    public int[] tmpStatesArray;
    public int totalScrollRange;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class BaseBehavior<T extends AppBarLayout> extends HeaderBehavior {
        public boolean coordinatorLayoutA11yScrollable;
        public WeakReference lastNestedScrollingChildRef;
        public int lastStartedType;
        public float mDiffY_Touch;
        public boolean mDirectTouchAppbar;
        public boolean mIsFlingScrollDown;
        public boolean mIsFlingScrollUp;
        public boolean mIsScrollHold;
        public boolean mIsSetStaticDuration;
        public float mLastMotionY_Touch;
        public boolean mLifted;
        public boolean mToolisMouse;
        public int mTouchSlop;
        public float mVelocity;
        public ValueAnimator offsetAnimator;
        public int offsetDelta;
        public BaseDragCallback onDragCallback;
        public SavedState savedState;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public abstract class BaseDragCallback {
            public abstract boolean canDrag(AppBarLayout appBarLayout);
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new AnonymousClass1();
            public boolean firstVisibleChildAtMinimumHeight;
            public int firstVisibleChildIndex;
            public float firstVisibleChildPercentageShown;
            public boolean fullyExpanded;
            public boolean fullyScrolled;

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.google.android.material.appbar.AppBarLayout$BaseBehavior$SavedState$1, reason: invalid class name */
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
                    return new SavedState(parcel, null);
                }
            }

            public SavedState(Parcel parcel, ClassLoader classLoader) {
                super(parcel, classLoader);
                this.fullyScrolled = parcel.readByte() != 0;
                this.fullyExpanded = parcel.readByte() != 0;
                this.firstVisibleChildIndex = parcel.readInt();
                this.firstVisibleChildPercentageShown = parcel.readFloat();
                this.firstVisibleChildAtMinimumHeight = parcel.readByte() != 0;
            }

            @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
            public final void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeByte(this.fullyScrolled ? (byte) 1 : (byte) 0);
                parcel.writeByte(this.fullyExpanded ? (byte) 1 : (byte) 0);
                parcel.writeInt(this.firstVisibleChildIndex);
                parcel.writeFloat(this.firstVisibleChildPercentageShown);
                parcel.writeByte(this.firstVisibleChildAtMinimumHeight ? (byte) 1 : (byte) 0);
            }
        }

        public BaseBehavior() {
            this.activePointerId = -1;
            this.touchSlop = -1;
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            this.mDirectTouchAppbar = false;
            this.mTouchSlop = -1;
            this.mVelocity = 0.0f;
            this.mIsSetStaticDuration = false;
            this.mIsScrollHold = false;
        }

        public static View findFirstScrollingChild(CoordinatorLayout coordinatorLayout) {
            int childCount = coordinatorLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = coordinatorLayout.getChildAt(i);
                if ((childAt instanceof NestedScrollingChild) || (childAt instanceof AbsListView) || (childAt instanceof ScrollView)) {
                    return childAt;
                }
            }
            return null;
        }

        public static int getChildIndexOnOffset(AppBarLayout appBarLayout, int i) {
            int paddingBottom = i + (appBarLayout.lifted ? appBarLayout.getPaddingBottom() : 0);
            int childCount = appBarLayout.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = appBarLayout.getChildAt(i2);
                int top = childAt.getTop();
                int bottom = childAt.getBottom();
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if ((layoutParams.scrollFlags & 32) == 32) {
                    top -= ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    bottom += ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i3 = -paddingBottom;
                if (top <= i3 && bottom >= i3) {
                    return i2;
                }
            }
            return -1;
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x006c  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x00da A[ORIG_RETURN, RETURN] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static void updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout r8, com.google.android.material.appbar.AppBarLayout r9, int r10, int r11) {
            /*
                r0 = 1
                int r1 = java.lang.Math.abs(r10)
                int r2 = r9.getChildCount()
                r3 = 0
                r4 = r3
            Lb:
                r5 = 0
                if (r4 >= r2) goto L21
                android.view.View r6 = r9.getChildAt(r4)
                int r7 = r6.getTop()
                if (r1 < r7) goto L1f
                int r7 = r6.getBottom()
                if (r1 > r7) goto L1f
                goto L22
            L1f:
                int r4 = r4 + r0
                goto Lb
            L21:
                r6 = r5
            L22:
                if (r6 == 0) goto L67
                android.view.ViewGroup$LayoutParams r1 = r6.getLayoutParams()
                com.google.android.material.appbar.AppBarLayout$LayoutParams r1 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r1
                int r1 = r1.scrollFlags
                r2 = r1 & 1
                if (r2 == 0) goto L67
                java.util.WeakHashMap r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r2 = r6.getMinimumHeight()
                if (r11 <= 0) goto L50
                r11 = r1 & 12
                if (r11 == 0) goto L50
                int r10 = -r10
                int r11 = r6.getBottom()
                int r11 = r11 - r2
                int r1 = r9.getTopInset()
                int r11 = r11 - r1
                int r1 = r9.getImmersiveTopInset()
                int r11 = r11 - r1
                if (r10 < r11) goto L67
            L4e:
                r10 = r0
                goto L68
            L50:
                r11 = r1 & 2
                if (r11 == 0) goto L67
                int r10 = -r10
                int r11 = r6.getBottom()
                int r11 = r11 - r2
                int r1 = r9.getTopInset()
                int r11 = r11 - r1
                int r1 = r9.getImmersiveTopInset()
                int r11 = r11 - r1
                if (r10 < r11) goto L67
                goto L4e
            L67:
                r10 = r3
            L68:
                boolean r11 = r9.liftOnScroll
                if (r11 == 0) goto L74
                android.view.View r10 = findFirstScrollingChild(r8)
                boolean r10 = r9.shouldLift(r10)
            L74:
                boolean r10 = r9.setLiftedState(r10)
                if (r10 == 0) goto Lda
                androidx.coordinatorlayout.widget.DirectedAcyclicGraph r8 = r8.mChildDag
                androidx.collection.SimpleArrayMap r8 = r8.mGraph
                java.lang.Object r8 = r8.get(r9)
                java.util.ArrayList r8 = (java.util.ArrayList) r8
                if (r8 != 0) goto L87
                goto L8c
            L87:
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>(r8)
            L8c:
                if (r5 != 0) goto L92
                java.util.List r5 = java.util.Collections.emptyList()
            L92:
                int r8 = r5.size()
            L96:
                if (r3 >= r8) goto Lda
                java.lang.Object r10 = r5.get(r3)
                android.view.View r10 = (android.view.View) r10
                android.view.ViewGroup$LayoutParams r10 = r10.getLayoutParams()
                androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r10 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r10
                androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r10 = r10.mBehavior
                boolean r11 = r10 instanceof com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior
                if (r11 == 0) goto Ld8
                com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior r10 = (com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior) r10
                int r8 = r10.overlayTop
                if (r8 == 0) goto Lda
                android.graphics.drawable.Drawable r8 = r9.getBackground()
                if (r8 == 0) goto Lbd
                android.graphics.drawable.Drawable r8 = r9.getBackground()
                r8.jumpToCurrentState()
            Lbd:
                android.graphics.drawable.Drawable r8 = r9.getForeground()
                if (r8 == 0) goto Lca
                android.graphics.drawable.Drawable r8 = r9.getForeground()
                r8.jumpToCurrentState()
            Lca:
                android.animation.StateListAnimator r8 = r9.getStateListAnimator()
                if (r8 == 0) goto Lda
                android.animation.StateListAnimator r8 = r9.getStateListAnimator()
                r8.jumpToCurrentState()
                goto Lda
            Ld8:
                int r3 = r3 + r0
                goto L96
            Lda:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.updateAppBarLayoutDrawableState(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, int, int):void");
        }

        public final void animateOffsetTo(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout, int i) {
            float abs = Math.abs(this.mVelocity);
            int i2 = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
            int abs2 = (abs <= 0.0f || Math.abs(this.mVelocity) > 3000.0f) ? 250 : (int) ((3000.0f - Math.abs(this.mVelocity)) * 0.4d);
            if (abs2 <= 250) {
                abs2 = 250;
            }
            if (this.mIsSetStaticDuration) {
                this.mIsSetStaticDuration = false;
            } else {
                i2 = abs2;
            }
            if (Math.abs(this.mVelocity) < 2000.0f) {
                int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
                if (topBottomOffsetForScrollingSibling == i) {
                    ValueAnimator valueAnimator = this.offsetAnimator;
                    if (valueAnimator != null && valueAnimator.isRunning()) {
                        this.offsetAnimator.cancel();
                    }
                } else {
                    ValueAnimator valueAnimator2 = this.offsetAnimator;
                    if (valueAnimator2 == null) {
                        ValueAnimator valueAnimator3 = new ValueAnimator();
                        this.offsetAnimator = valueAnimator3;
                        valueAnimator3.setInterpolator(SeslAnimationUtils.SINE_OUT_80);
                        this.offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                                BaseBehavior.this.setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, ((Integer) valueAnimator4.getAnimatedValue()).intValue());
                            }
                        });
                    } else {
                        valueAnimator2.cancel();
                    }
                    this.offsetAnimator.setDuration(Math.min(i2, 450));
                    this.offsetAnimator.setIntValues(topBottomOffsetForScrollingSibling, i);
                    this.offsetAnimator.start();
                }
            }
            this.mVelocity = 0.0f;
        }

        public final int getTopBottomOffsetForScrollingSibling() {
            return getTopAndBottomOffset() + this.offsetDelta;
        }

        public boolean isOffsetAnimatorRunning() {
            ValueAnimator valueAnimator = this.offsetAnimator;
            return valueAnimator != null && valueAnimator.isRunning();
        }

        @Override // com.google.android.material.appbar.ViewOffsetBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            int round;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            super.onLayoutChild(coordinatorLayout, appBarLayout, i);
            int i2 = appBarLayout.pendingAction;
            SavedState savedState = this.savedState;
            if (savedState == null || (i2 & 8) != 0) {
                if (i2 != 0) {
                    boolean z = (i2 & 4) != 0;
                    if ((i2 & 2) != 0) {
                        float seslGetCollapsedHeight = (((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? (int) appBarLayout.seslGetCollapsedHeight() : 0) + (-appBarLayout.getTotalScrollRange())) - appBarLayout.getImmersiveTopInset();
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight);
                        }
                    } else if ((i2 & 512) != 0) {
                        float seslGetCollapsedHeight2 = ((appBarLayout.mIsCanScroll && (((Behavior) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).mBehavior) instanceof SeslImmersiveScrollBehavior)) ? (int) appBarLayout.seslGetCollapsedHeight() : 0) + (-appBarLayout.getTotalScrollRange());
                        if (coordinatorLayout.getContext().getResources().getConfiguration().orientation == 1 && appBarLayout.getImmersiveTopInset() == 0 && appBarLayout.mHeightProportion == 0.0f) {
                            seslGetCollapsedHeight2 = 0.0f;
                        }
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight2);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, (int) seslGetCollapsedHeight2);
                        }
                    } else if ((i2 & 1) != 0) {
                        if (z) {
                            animateOffsetTo(coordinatorLayout, appBarLayout, 0);
                        } else {
                            setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
                        }
                    }
                }
            } else if (savedState.fullyScrolled) {
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, -appBarLayout.getTotalScrollRange());
            } else if (savedState.fullyExpanded) {
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, 0);
            } else {
                View childAt = appBarLayout.getChildAt(savedState.firstVisibleChildIndex);
                int i3 = -childAt.getBottom();
                if (this.savedState.firstVisibleChildAtMinimumHeight) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    round = appBarLayout.getTopInset() + childAt.getMinimumHeight() + i3;
                } else {
                    round = Math.round(childAt.getHeight() * this.savedState.firstVisibleChildPercentageShown) + i3;
                }
                setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, round);
            }
            appBarLayout.pendingAction = 0;
            this.savedState = null;
            int clamp = MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0);
            ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
            if (viewOffsetHelper != null) {
                viewOffsetHelper.setTopAndBottomOffset(clamp);
            } else {
                this.tempTopBottomOffset = clamp;
            }
            updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0);
            appBarLayout.onOffsetChanged(getTopAndBottomOffset());
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onNestedPreFling(View view, View view2, float f) {
            this.mVelocity = f;
            if (f < -300.0f) {
                this.mIsFlingScrollDown = true;
                this.mIsFlingScrollUp = false;
            } else {
                if (f <= 300.0f) {
                    this.mVelocity = 0.0f;
                    this.mIsFlingScrollDown = false;
                    this.mIsFlingScrollUp = false;
                    return true;
                }
                this.mIsFlingScrollDown = false;
                this.mIsFlingScrollUp = true;
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onRestoreInstanceState(View view, Parcelable parcelable) {
            if (parcelable instanceof SavedState) {
                this.savedState = (SavedState) parcelable;
            } else {
                this.savedState = null;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final Parcelable onSaveInstanceState(View view) {
            android.view.AbsSavedState absSavedState = View.BaseSavedState.EMPTY_STATE;
            SavedState saveScrollState = saveScrollState(absSavedState, (AppBarLayout) view);
            return saveScrollState == null ? absSavedState : saveScrollState;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, View view2, int i, int i2) {
            ValueAnimator valueAnimator;
            boolean z = (i & 2) != 0 && (appBarLayout.liftOnScroll || (appBarLayout.getTotalScrollRange() != 0 && coordinatorLayout.getHeight() - view.getHeight() <= appBarLayout.getHeight()));
            if (z && (valueAnimator = this.offsetAnimator) != null) {
                valueAnimator.cancel();
            }
            if (appBarLayout.getBottom() <= appBarLayout.seslGetCollapsedHeight()) {
                this.mLifted = true;
                appBarLayout.setLiftedState(true);
                this.mDiffY_Touch = 0.0f;
            } else {
                this.mLifted = false;
                appBarLayout.setLiftedState(false);
            }
            if (!appBarLayout.mUseCollapsedHeight && (appBarLayout.getImmBehavior() == null || !appBarLayout.mIsCanScroll)) {
                float seslGetCollapsedHeight = appBarLayout.seslGetCollapsedHeight();
                float height = appBarLayout.getHeight() - appBarLayout.getTotalScrollRange();
                if (height != seslGetCollapsedHeight && height > 0.0f) {
                    Log.i("AppBarLayout", "Internal collapsedHeight/ oldCollapsedHeight :" + seslGetCollapsedHeight + " newCollapsedHeight :" + height);
                    appBarLayout.mUseCollapsedHeight = false;
                    appBarLayout.mCollapsedHeight = height;
                    appBarLayout.updateInternalHeight();
                }
            }
            this.lastNestedScrollingChildRef = null;
            this.lastStartedType = i2;
            this.mToolisMouse = appBarLayout.isMouse;
            return z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x002f, code lost:
        
            if (r0 != 3) goto L30;
         */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0169  */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r21, android.view.View r22, android.view.MotionEvent r23) {
            /*
                Method dump skipped, instructions count: 367
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.BaseBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
        }

        public final SavedState saveScrollState(Parcelable parcelable, AppBarLayout appBarLayout) {
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    if (parcelable == null) {
                        parcelable = AbsSavedState.EMPTY_STATE;
                    }
                    SavedState savedState = new SavedState(parcelable);
                    boolean z = topAndBottomOffset == 0;
                    savedState.fullyExpanded = z;
                    savedState.fullyScrolled = !z && (-topAndBottomOffset) >= appBarLayout.getTotalScrollRange();
                    savedState.firstVisibleChildIndex = i;
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    savedState.firstVisibleChildAtMinimumHeight = bottom == appBarLayout.getTopInset() + childAt.getMinimumHeight();
                    savedState.firstVisibleChildPercentageShown = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return null;
        }

        @Override // com.google.android.material.appbar.HeaderBehavior
        public final int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            int i4;
            boolean z;
            ArrayList arrayList;
            int i5;
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
            int i6 = 0;
            if (i2 == 0 || topBottomOffsetForScrollingSibling < i2 || topBottomOffsetForScrollingSibling > i3) {
                this.offsetDelta = 0;
            } else {
                int clamp = MathUtils.clamp(i, i2, i3);
                if (topBottomOffsetForScrollingSibling != clamp) {
                    if (appBarLayout.haveChildWithInterpolator) {
                        int abs = Math.abs(clamp);
                        int childCount = appBarLayout.getChildCount();
                        int i7 = 0;
                        while (true) {
                            if (i7 >= childCount) {
                                break;
                            }
                            View childAt = appBarLayout.getChildAt(i7);
                            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                            Interpolator interpolator = layoutParams.scrollInterpolator;
                            if (abs < childAt.getTop() || abs > childAt.getBottom()) {
                                i7++;
                            } else if (interpolator != null) {
                                int i8 = layoutParams.scrollFlags;
                                if ((i8 & 1) != 0) {
                                    i5 = childAt.getHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                                    if ((i8 & 2) != 0) {
                                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                        i5 -= childAt.getMinimumHeight();
                                    }
                                } else {
                                    i5 = 0;
                                }
                                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                if (childAt.getFitsSystemWindows()) {
                                    i5 -= appBarLayout.getTopInset();
                                }
                                if (i5 > 0) {
                                    float f = i5;
                                    i4 = (childAt.getTop() + Math.round(interpolator.getInterpolation((abs - childAt.getTop()) / f) * f)) * Integer.signum(clamp);
                                }
                            }
                        }
                    }
                    i4 = clamp;
                    ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
                    if (viewOffsetHelper != null) {
                        z = viewOffsetHelper.setTopAndBottomOffset(i4);
                    } else {
                        this.tempTopBottomOffset = i4;
                        z = false;
                    }
                    int i9 = topBottomOffsetForScrollingSibling - clamp;
                    this.offsetDelta = clamp - i4;
                    if (z) {
                        for (int i10 = 0; i10 < appBarLayout.getChildCount(); i10++) {
                            LayoutParams layoutParams2 = (LayoutParams) appBarLayout.getChildAt(i10).getLayoutParams();
                            CompressChildScrollEffect compressChildScrollEffect = layoutParams2.scrollEffect;
                            if (compressChildScrollEffect != null && (layoutParams2.scrollFlags & 1) != 0) {
                                View childAt2 = appBarLayout.getChildAt(i10);
                                float topAndBottomOffset = getTopAndBottomOffset();
                                Rect rect = compressChildScrollEffect.relativeRect;
                                childAt2.getDrawingRect(rect);
                                appBarLayout.offsetDescendantRectToMyCoords(childAt2, rect);
                                rect.offset(0, -appBarLayout.getTopInset());
                                float abs2 = compressChildScrollEffect.relativeRect.top - Math.abs(topAndBottomOffset);
                                if (abs2 <= 0.0f) {
                                    float clamp2 = 1.0f - MathUtils.clamp(Math.abs(abs2 / compressChildScrollEffect.relativeRect.height()), 0.0f, 1.0f);
                                    float height = (-abs2) - ((compressChildScrollEffect.relativeRect.height() * 0.3f) * (1.0f - (clamp2 * clamp2)));
                                    childAt2.setTranslationY(height);
                                    childAt2.getDrawingRect(compressChildScrollEffect.ghostRect);
                                    compressChildScrollEffect.ghostRect.offset(0, (int) (-height));
                                    Rect rect2 = compressChildScrollEffect.ghostRect;
                                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                    childAt2.setClipBounds(rect2);
                                } else {
                                    WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                                    childAt2.setClipBounds(null);
                                    childAt2.setTranslationY(0.0f);
                                }
                            }
                        }
                    }
                    if (!z && appBarLayout.haveChildWithInterpolator && (arrayList = (ArrayList) coordinatorLayout.mChildDag.mGraph.get(appBarLayout)) != null && !arrayList.isEmpty()) {
                        while (i6 < arrayList.size()) {
                            View view2 = (View) arrayList.get(i6);
                            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).mBehavior;
                            if (behavior != null) {
                                behavior.onDependentViewChanged(coordinatorLayout, view2, appBarLayout);
                            }
                            i6++;
                        }
                    }
                    appBarLayout.onOffsetChanged(getTopAndBottomOffset());
                    updateAppBarLayoutDrawableState(coordinatorLayout, appBarLayout, clamp, clamp < topBottomOffsetForScrollingSibling ? -1 : 1);
                    i6 = i9;
                }
            }
            updateAccessibilityActions(coordinatorLayout, appBarLayout);
            return i6;
        }

        public final void snapToChildIfNeeded(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling() - (appBarLayout.getPaddingTop() + appBarLayout.getTopInset());
            int childIndexOnOffset = getChildIndexOnOffset(appBarLayout, topBottomOffsetForScrollingSibling);
            View childAt = coordinatorLayout.getChildAt(1);
            if (childIndexOnOffset >= 0) {
                View childAt2 = appBarLayout.getChildAt(childIndexOnOffset);
                LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                int i = layoutParams.scrollFlags;
                if ((i & 4096) == 4096) {
                    this.mHasNoSnapFlag = true;
                    return;
                }
                this.mHasNoSnapFlag = false;
                if (appBarLayout.getBottom() < appBarLayout.seslGetCollapsedHeight()) {
                    if (appBarLayout.mIsCanScroll) {
                        int seslGetCollapsedHeight = ((int) appBarLayout.seslGetCollapsedHeight()) - appBarLayout.getTotalScrollRange();
                        int i2 = -appBarLayout.getTotalScrollRange();
                        int i3 = ((double) appBarLayout.getBottom()) >= ((double) appBarLayout.seslGetCollapsedHeight()) * 0.48d ? seslGetCollapsedHeight : i2;
                        if (!this.mIsFlingScrollUp) {
                            i2 = i3;
                        }
                        if (!this.mIsFlingScrollDown) {
                            seslGetCollapsedHeight = i2;
                        }
                        animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(seslGetCollapsedHeight, -appBarLayout.getTotalScrollRange(), 0));
                        return;
                    }
                    return;
                }
                int i4 = -childAt2.getTop();
                int i5 = -childAt2.getBottom();
                if (childIndexOnOffset == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (appBarLayout.getFitsSystemWindows() && childAt2.getFitsSystemWindows()) {
                        i4 -= appBarLayout.getTopInset();
                    }
                }
                if ((i & 2) == 2) {
                    if (appBarLayout.mIsCanScroll) {
                        i5 = (int) ((appBarLayout.seslGetCollapsedHeight() - appBarLayout.getPaddingBottom()) + i5);
                    } else {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        i5 += childAt2.getMinimumHeight();
                    }
                } else if ((i & 5) == 5) {
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    int minimumHeight = childAt2.getMinimumHeight() + i5;
                    if (topBottomOffsetForScrollingSibling < minimumHeight) {
                        i4 = minimumHeight;
                    } else {
                        i5 = minimumHeight;
                    }
                }
                if ((i & 32) == 32) {
                    i4 += ((LinearLayout.LayoutParams) layoutParams).topMargin;
                    i5 -= ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                }
                int i6 = (!this.mLifted ? ((double) topBottomOffsetForScrollingSibling) < ((double) (i5 + i4)) * 0.43d : ((double) topBottomOffsetForScrollingSibling) < ((double) (i5 + i4)) * 0.52d) ? i4 : i5;
                if (childAt == null) {
                    int i7 = AppBarLayout.$r8$clinit;
                    Log.w("AppBarLayout", "coordinatorLayout.getChildAt(1) is null");
                    i4 = i6;
                } else {
                    if (this.mIsFlingScrollUp) {
                        this.mIsFlingScrollUp = false;
                        this.mIsFlingScrollDown = false;
                    } else {
                        i5 = i6;
                    }
                    if (!this.mIsFlingScrollDown || childAt.getTop() <= appBarLayout.seslGetCollapsedHeight()) {
                        i4 = i5;
                    } else {
                        this.mIsFlingScrollDown = false;
                    }
                }
                animateOffsetTo(coordinatorLayout, appBarLayout, MathUtils.clamp(i4, -appBarLayout.getTotalScrollRange(), 0));
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void stopNestedScrollIfNeeded(int i, AppBarLayout appBarLayout, View view, int i2) {
            if (i2 == 1) {
                int topBottomOffsetForScrollingSibling = getTopBottomOffsetForScrollingSibling();
                if ((i >= 0 || topBottomOffsetForScrollingSibling != 0) && (i <= 0 || topBottomOffsetForScrollingSibling != (-appBarLayout.getDownNestedScrollRange()))) {
                    return;
                }
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (view instanceof NestedScrollingChild2) {
                    ((NestedScrollingChild2) view).stopNestedScroll(1);
                }
            }
        }

        public final void updateAccessibilityActions(final CoordinatorLayout coordinatorLayout, final AppBarLayout appBarLayout) {
            final View findFirstScrollingChild;
            ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
            final boolean z = false;
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
            ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
            if (appBarLayout.getTotalScrollRange() == 0 || (findFirstScrollingChild = findFirstScrollingChild(coordinatorLayout)) == null) {
                return;
            }
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                if (((LayoutParams) appBarLayout.getChildAt(i).getLayoutParams()).scrollFlags != 0) {
                    if (ViewCompat.Api29Impl.getAccessibilityDelegate(coordinatorLayout) == null) {
                        ViewCompat.setAccessibilityDelegate(coordinatorLayout, new AccessibilityDelegateCompat() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.2
                            @Override // androidx.core.view.AccessibilityDelegateCompat
                            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                                accessibilityNodeInfoCompat.setScrollable(BaseBehavior.this.coordinatorLayoutA11yScrollable);
                                accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
                            }
                        });
                    }
                    final boolean z2 = true;
                    if (getTopBottomOffsetForScrollingSibling() != (-appBarLayout.getTotalScrollRange())) {
                        ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.4
                            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                            public final boolean perform(View view) {
                                AppBarLayout.this.setExpanded(z);
                                return true;
                            }
                        });
                        z = true;
                    }
                    if (getTopBottomOffsetForScrollingSibling() != 0) {
                        if (findFirstScrollingChild.canScrollVertically(-1)) {
                            final int i2 = -appBarLayout.getDownNestedPreScrollRange();
                            if (i2 != 0) {
                                ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.3
                                    @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                                    public final boolean perform(View view) {
                                        int i3 = i2;
                                        BaseBehavior.this.onNestedPreScroll(coordinatorLayout, appBarLayout, findFirstScrollingChild, 0, i3, new int[]{0, 0}, 1);
                                        return true;
                                    }
                                });
                            }
                        } else {
                            ViewCompat.replaceAccessibilityAction(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD, new AccessibilityViewCommand() { // from class: com.google.android.material.appbar.AppBarLayout.BaseBehavior.4
                                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                                public final boolean perform(View view) {
                                    AppBarLayout.this.setExpanded(z2);
                                    return true;
                                }
                            });
                        }
                        this.coordinatorLayoutA11yScrollable = z2;
                        return;
                    }
                    z2 = z;
                    this.coordinatorLayoutA11yScrollable = z2;
                    return;
                }
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3) {
            if (((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams())).height != -2) {
                return false;
            }
            coordinatorLayout.onMeasureChild(appBarLayout, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0));
            return true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int[] iArr, int i3) {
            int i4;
            int i5;
            OverScroller overScroller;
            if (i2 != 0) {
                if (i2 < 0) {
                    int i6 = -appBarLayout.getTotalScrollRange();
                    int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange() + i6;
                    this.mIsFlingScrollDown = true;
                    this.mIsFlingScrollUp = false;
                    if (appBarLayout.getBottom() >= appBarLayout.getHeight() * 0.52d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 < -30) {
                        this.mIsFlingScrollDown = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollDown = false;
                    }
                    i5 = i6;
                    i4 = downNestedPreScrollRange;
                } else {
                    int i7 = -appBarLayout.getTotalScrollRange();
                    this.mIsFlingScrollDown = false;
                    this.mIsFlingScrollUp = true;
                    if (appBarLayout.getBottom() <= appBarLayout.getHeight() * 0.43d) {
                        this.mIsSetStaticDuration = true;
                    }
                    if (i2 > 30) {
                        this.mIsFlingScrollUp = true;
                    } else {
                        this.mVelocity = 0.0f;
                        this.mIsFlingScrollUp = false;
                    }
                    if (getTopAndBottomOffset() == i7) {
                        this.mIsScrollHold = true;
                    }
                    i4 = 0;
                    i5 = i7;
                }
                if (this.flingRunnable != null && (overScroller = this.scroller) != null) {
                    overScroller.forceFinished(true);
                }
                if (i5 != i4) {
                    iArr[1] = setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - i2, i5, i4);
                }
            }
            if (appBarLayout.liftOnScroll) {
                appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
            }
            stopNestedScrollIfNeeded(i2, appBarLayout, view, i3);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
            int childIndexOnOffset;
            if (!this.mToolisMouse && ((childIndexOnOffset = getChildIndexOnOffset(appBarLayout, getTopBottomOffsetForScrollingSibling())) < 0 || (((LayoutParams) appBarLayout.getChildAt(childIndexOnOffset).getLayoutParams()).scrollFlags & 65536) != 65536)) {
                if (i4 >= 0 || this.mIsScrollHold) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (view instanceof NestedScrollingChild2) {
                        ((NestedScrollingChild2) view).stopNestedScroll(1);
                    }
                } else {
                    iArr[1] = setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - i4, -appBarLayout.getDownNestedScrollRange(), 0);
                    stopNestedScrollIfNeeded(i4, appBarLayout, view, i5);
                }
            } else if (i4 < 0) {
                iArr[1] = setHeaderTopBottomOffset(coordinatorLayout, appBarLayout, getTopBottomOffsetForScrollingSibling() - i4, -appBarLayout.getDownNestedScrollRange(), 0);
                stopNestedScrollIfNeeded(i4, appBarLayout, view, i5);
            }
            if (i4 == 0) {
                updateAccessibilityActions(coordinatorLayout, appBarLayout);
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view, int i) {
            int i2;
            int i3 = this.mLastTouchEvent;
            if (i3 == 3 || i3 == 1 || (i2 = this.mLastInterceptTouchEvent) == 3 || i2 == 1) {
                snapToChildIfNeeded(coordinatorLayout, appBarLayout);
            }
            if (this.lastStartedType == 0 || i == 1) {
                if (appBarLayout.liftOnScroll) {
                    appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
                }
                if (this.mIsScrollHold) {
                    this.mIsScrollHold = false;
                }
            }
            this.lastNestedScrollingChildRef = new WeakReference(view);
        }

        public BaseBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            this.activePointerId = -1;
            this.touchSlop = -1;
            this.mIsFlingScrollDown = false;
            this.mIsFlingScrollUp = false;
            this.mDirectTouchAppbar = false;
            this.mTouchSlop = -1;
            this.mVelocity = 0.0f;
            this.mIsSetStaticDuration = false;
            this.mIsScrollHold = false;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Behavior extends BaseBehavior<AppBarLayout> {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public abstract class DragCallback extends BaseBehavior.BaseDragCallback {
        }

        public Behavior() {
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CompressChildScrollEffect {
        public final Rect relativeRect = new Rect();
        public final Rect ghostRect = new Rect();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LayoutParams extends LinearLayout.LayoutParams {
        public CompressChildScrollEffect scrollEffect;
        public int scrollFlags;
        public Interpolator scrollInterpolator;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public static AppBarLayout findFirstDependency(List list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view = (View) list.get(i);
                if (view instanceof AppBarLayout) {
                    return (AppBarLayout) view;
                }
            }
            return null;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean layoutDependsOn(View view, View view2) {
            return view2 instanceof AppBarLayout;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view2.getLayoutParams()).mBehavior;
            if (behavior instanceof BaseBehavior) {
                int bottom = (((view2.getBottom() - view.getTop()) + ((BaseBehavior) behavior).offsetDelta) + this.verticalLayoutGap) - getOverlapPixelsForOffset(view2);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetTopAndBottom(bottom);
            }
            if (!(view2 instanceof AppBarLayout)) {
                return false;
            }
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            if (!appBarLayout.liftOnScroll) {
                return false;
            }
            appBarLayout.setLiftedState(appBarLayout.shouldLift(view));
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view) {
            if (view instanceof AppBarLayout) {
                ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId());
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
                ViewCompat.removeActionWithId(coordinatorLayout, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId());
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(coordinatorLayout, 0);
                ViewCompat.setAccessibilityDelegate(coordinatorLayout, null);
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view, Rect rect, boolean z) {
            AppBarLayout findFirstDependency = findFirstDependency(coordinatorLayout.getDependencies(view));
            if (findFirstDependency != null) {
                Rect rect2 = new Rect(rect);
                rect2.offset(view.getLeft(), view.getTop());
                Rect rect3 = this.tempRect1;
                rect3.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect3.contains(rect2)) {
                    findFirstDependency.setExpanded(false, !z, true);
                    return true;
                }
            }
            return false;
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(0);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ScrollingViewBehavior_Layout);
            this.overlayTop = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            obtainStyledAttributes.recycle();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SeslAppbarState {
        public int mCurrentState;
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public final void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.listeners == null) {
            this.listeners = new ArrayList();
        }
        if (onOffsetChangedListener == null || ((ArrayList) this.listeners).contains(onOffsetChangedListener)) {
            return;
        }
        ((ArrayList) this.listeners).add(onOffsetChangedListener);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 8) {
            if (this.liftOnScrollTargetView != null) {
                if (motionEvent.getAxisValue(9) < 0.0f) {
                    setExpanded(false);
                } else if (motionEvent.getAxisValue(9) > 0.0f && !canScrollVertically(-1)) {
                    setExpanded(true);
                }
            } else if (motionEvent.getAxisValue(9) < 0.0f) {
                setExpanded(false);
            } else if (motionEvent.getAxisValue(9) > 0.0f) {
                setExpanded(true);
            }
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.statusBarForeground == null || getTopInset() <= 0) {
            return;
        }
        int save = canvas.save();
        canvas.translate(0.0f, -this.currentOffset);
        this.statusBarForeground.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.statusBarForeground;
        if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.scrollFlags = 1;
        return layoutParams;
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        Behavior behavior = new Behavior();
        this.behavior = behavior;
        return behavior;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDownNestedPreScrollRange() {
        /*
            r11 = this;
            r0 = 5
            r1 = 8
            int r2 = r11.downPreScrollRange
            r3 = -1
            if (r2 == r3) goto L9
            return r2
        L9:
            int r2 = r11.getChildCount()
            int r2 = r2 + (-1)
            r4 = 0
            r5 = r4
        L11:
            if (r2 < 0) goto L71
            android.view.View r6 = r11.getChildAt(r2)
            int r7 = r6.getVisibility()
            if (r7 != r1) goto L1e
            goto L62
        L1e:
            android.view.ViewGroup$LayoutParams r7 = r6.getLayoutParams()
            com.google.android.material.appbar.AppBarLayout$LayoutParams r7 = (com.google.android.material.appbar.AppBarLayout.LayoutParams) r7
            int r8 = r6.getMeasuredHeight()
            int r9 = r7.scrollFlags
            r10 = r9 & 5
            if (r10 != r0) goto L64
            int r10 = r7.topMargin
            int r7 = r7.bottomMargin
            int r10 = r10 + r7
            r7 = r9 & 8
            if (r7 == 0) goto L3f
            java.util.WeakHashMap r7 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r7 = r6.getMinimumHeight()
        L3d:
            int r7 = r7 + r10
            goto L4e
        L3f:
            r7 = r9 & 2
            if (r7 == 0) goto L4c
            java.util.WeakHashMap r7 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r7 = r6.getMinimumHeight()
            int r7 = r8 - r7
            goto L3d
        L4c:
            int r7 = r10 + r8
        L4e:
            if (r2 != 0) goto L61
            java.util.WeakHashMap r9 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r6 = r6.getFitsSystemWindows()
            if (r6 == 0) goto L61
            int r6 = r11.getTopInset()
            int r8 = r8 - r6
            int r7 = java.lang.Math.min(r7, r8)
        L61:
            int r5 = r5 + r7
        L62:
            int r2 = r2 + r3
            goto L11
        L64:
            boolean r0 = r11.mIsCanScroll
            if (r0 == 0) goto L71
            float r0 = (float) r5
            float r1 = r11.seslGetCollapsedHeight()
            float r2 = (float) r4
            float r1 = r1 + r2
            float r1 = r1 + r0
            int r5 = (int) r1
        L71:
            int r0 = java.lang.Math.max(r4, r5)
            r11.downPreScrollRange = r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.AppBarLayout.getDownNestedPreScrollRange():int");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v5, types: [android.view.View] */
    public final int getDownNestedScrollRange() {
        int minimumHeight;
        int i;
        int i2 = this.downScrollRange;
        if (i2 != -1) {
            return i2;
        }
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= childCount) {
                break;
            }
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + childAt.getMeasuredHeight();
                int i5 = layoutParams.scrollFlags;
                if ((i5 & 1) == 0) {
                    break;
                }
                i4 += measuredHeight;
                if ((i5 & 2) != 0) {
                    if (this.mIsCanScroll && (childAt instanceof CollapsingToolbarLayout)) {
                        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) childAt;
                        ViewGroup viewGroup = collapsingToolbarLayout.toolbar;
                        if (viewGroup != null) {
                            ?? r2 = collapsingToolbarLayout.toolbarDirectChild;
                            if (r2 != 0 && r2 != collapsingToolbarLayout) {
                                viewGroup = r2;
                            }
                            ViewGroup.LayoutParams layoutParams2 = viewGroup.getLayoutParams();
                            if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                                i = marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
                                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                minimumHeight = collapsingToolbarLayout.getMinimumHeight() - i;
                            }
                        }
                        i = 0;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = collapsingToolbarLayout.getMinimumHeight() - i;
                    } else {
                        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                        minimumHeight = childAt.getMinimumHeight();
                    }
                    i4 -= minimumHeight;
                }
            }
            i3++;
        }
        int max = Math.max(0, i4);
        this.downScrollRange = max;
        return max;
    }

    public final SeslImmersiveScrollBehavior getImmBehavior() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            return null;
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior;
        if (behavior instanceof SeslImmersiveScrollBehavior) {
            return (SeslImmersiveScrollBehavior) behavior;
        }
        return null;
    }

    public final int getImmersiveTopInset() {
        if (this.mIsCanScroll) {
            return this.mImmersiveTopInset;
        }
        return 0;
    }

    public final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.lastInsets;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i = this.totalScrollRange;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int measuredHeight = childAt.getMeasuredHeight();
                int i4 = layoutParams.scrollFlags;
                if ((i4 & 1) == 0) {
                    break;
                }
                int i5 = measuredHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + i3;
                if (i2 == 0) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (childAt.getFitsSystemWindows()) {
                        i5 -= getTopInset();
                    }
                }
                i3 = i5;
                if ((i4 & 2) != 0) {
                    if (this.mIsCanScroll) {
                        i3 += getTopInset() + this.mBottomPadding;
                    } else {
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        i3 -= childAt.getMinimumHeight();
                    }
                }
            }
            i2++;
        }
        int max = Math.max(0, i3);
        this.totalScrollRange = max;
        return max;
    }

    public final int getWindowHeight() {
        Insets insets;
        if (this.mIsActivatedImmersiveScroll) {
            return getContext().getResources().getDisplayMetrics().heightPixels;
        }
        Object systemService = getContext().getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        WindowMetrics currentWindowMetrics = ((WindowManager) systemService).getCurrentWindowMetrics();
        Intrinsics.checkNotNullExpressionValue(currentWindowMetrics, "wm.currentWindowMetrics");
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        WindowInsetsCompat rootWindowInsets = ViewCompat.Api23Impl.getRootWindowInsets(this);
        if (rootWindowInsets == null || (insets = rootWindowInsets.mImpl.getInsets(7)) == null) {
            insets = Insets.NONE;
        }
        int height = currentWindowMetrics.getBounds().height();
        int i = insets.top;
        int i2 = insets.bottom;
        int i3 = (height - i) - i2;
        Preference$$ExternalSyntheticOutline0.m(RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m("screenHeight(px)=", ", status=", i3, i, ", navi="), i2, "SeslAppBarHelper");
        return i3;
    }

    public final void invalidateScrollRanges() {
        Behavior behavior = this.behavior;
        BaseBehavior.SavedState saveScrollState = (behavior == null || this.totalScrollRange == -1 || this.pendingAction != 0) ? null : behavior.saveScrollState(AbsSavedState.EMPTY_STATE, this);
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        if (saveScrollState != null) {
            Behavior behavior2 = this.behavior;
            if (behavior2.savedState != null) {
                return;
            }
            behavior2.savedState = saveScrollState;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsDetachedState = false;
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Drawable drawable = this.mBackground;
        if (drawable != null) {
            setBackgroundDrawable(drawable == getBackground() ? this.mBackground : getBackground());
        } else if (getBackground() != null) {
            Drawable background = getBackground();
            this.mBackground = background;
            setBackgroundDrawable(background);
        } else {
            this.mBackground = null;
            setBackgroundColor(this.mResources.getColor(SeslMisc.isLightTheme(getContext()) ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
        }
        if (this.mCurrentScreenHeight != configuration.screenHeightDp || this.mCurrentOrientation != configuration.orientation) {
            boolean z = this.mUseCustomPadding;
            if (!z && !this.mUseCollapsedHeight) {
                Log.i("AppBarLayout", "Update bottom padding");
                int dimensionPixelSize = this.mResources.getDimensionPixelSize(R.dimen.sesl_extended_appbar_bottom_padding);
                this.mBottomPadding = dimensionPixelSize;
                setPadding(0, 0, 0, dimensionPixelSize);
                float dimensionPixelSize2 = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = dimensionPixelSize2;
            } else if (z && this.mBottomPadding == 0 && !this.mUseCollapsedHeight) {
                float dimensionPixelSize3 = this.mResources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding);
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = dimensionPixelSize3;
            }
        }
        if (!this.mSetCustomProportion) {
            this.mHeightProportion = SeslAppBarHelper$Companion.getAppBarProPortion(getContext());
        }
        updateInternalHeight();
        StringBuilder sb = new StringBuilder("onConfigurationChanged : mCollapsedHeight = ");
        sb.append(this.mCollapsedHeight);
        sb.append(", mHeightProportion = ");
        sb.append(this.mHeightProportion);
        sb.append(", mHasSuggestion = false, mUseCollapsedHeight = ");
        SeslContentViewInsetsCallback$$ExternalSyntheticOutline0.m(sb, this.mUseCollapsedHeight, "AppBarLayout");
        if (this.lifted || (this.mCurrentOrientation == 1 && configuration.orientation == 2)) {
            setExpanded(false, false, true);
        } else {
            setExpanded(true, false, true);
        }
        this.mCurrentOrientation = configuration.orientation;
        this.mCurrentScreenHeight = configuration.screenHeightDp;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        if (this.tmpStatesArray == null) {
            this.tmpStatesArray = new int[4];
        }
        int[] iArr = this.tmpStatesArray;
        int[] onCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        boolean z = this.liftable;
        iArr[0] = z ? R.attr.state_liftable : -2130970081;
        iArr[1] = (z && this.lifted) ? R.attr.state_lifted : -2130970082;
        iArr[2] = z ? R.attr.state_collapsible : -2130970074;
        iArr[3] = (z && this.lifted) ? R.attr.state_collapsed : -2130970073;
        return LinearLayout.mergeDrawableStates(onCreateDrawableState, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.mIsDetachedState = true;
        SeslImmersiveScrollBehavior immBehavior = getImmBehavior();
        if (immBehavior != null) {
            Log.i("SeslImmersiveScrollBehavior", "DetachedFromWindow");
            SeslImmersiveScrollBehavior.AnonymousClass3 anonymousClass3 = immBehavior.mOnInsetsChangedListener;
            if (anonymousClass3 != null) {
                immBehavior.mWindowInsetsController.removeOnControllableInsetsChangedListener(anonymousClass3);
                immBehavior.mOnInsetsChangedListener = null;
            }
            immBehavior.mAnimationController = null;
            immBehavior.mCancellationSignal = null;
            immBehavior.mShownAtDown = false;
        }
        super.onDetachedFromWindow();
        WeakReference weakReference = this.liftOnScrollTargetView;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.liftOnScrollTargetView = null;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = true;
        super.onLayout(z, i, i2, i3, i4);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (getFitsSystemWindows() && shouldOffsetFirstChild()) {
            int topInset = getTopInset();
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                getChildAt(childCount).offsetTopAndBottom(topInset);
            }
        }
        invalidateScrollRanges();
        this.haveChildWithInterpolator = false;
        int childCount2 = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount2) {
                break;
            }
            if (((LayoutParams) getChildAt(i5).getLayoutParams()).scrollInterpolator != null) {
                this.haveChildWithInterpolator = true;
                break;
            }
            i5++;
        }
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setBounds(0, 0, getWidth(), getTopInset());
        }
        if (!this.liftOnScroll) {
            int childCount3 = getChildCount();
            int i6 = 0;
            while (true) {
                if (i6 >= childCount3) {
                    z2 = false;
                    break;
                }
                int i7 = ((LayoutParams) getChildAt(i6).getLayoutParams()).scrollFlags;
                if ((i7 & 1) == 1 && (i7 & 10) != 0) {
                    break;
                } else {
                    i6++;
                }
            }
        }
        if (this.liftable != z2) {
            this.liftable = z2;
            refreshDrawableState();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        updateInternalHeight();
        super.onMeasure(i, i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode != 1073741824) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (getFitsSystemWindows() && shouldOffsetFirstChild()) {
                int measuredHeight = getMeasuredHeight();
                if (mode == Integer.MIN_VALUE) {
                    measuredHeight = MathUtils.clamp(getTopInset() + getMeasuredHeight(), 0, View.MeasureSpec.getSize(i2));
                } else if (mode == 0) {
                    measuredHeight += getTopInset();
                }
                setMeasuredDimension(getMeasuredWidth(), measuredHeight);
            }
        }
        invalidateScrollRanges();
    }

    public final void onOffsetChanged(int i) {
        this.currentOffset = i;
        int totalScrollRange = getTotalScrollRange();
        int height = getHeight() - ((int) seslGetCollapsedHeight());
        if (Math.abs(i) >= totalScrollRange) {
            if (this.mIsCanScroll) {
                SeslAppbarState seslAppbarState = this.mAppbarState;
                if (seslAppbarState.mCurrentState != 2) {
                    seslAppbarState.mCurrentState = 2;
                }
            } else {
                SeslAppbarState seslAppbarState2 = this.mAppbarState;
                if (seslAppbarState2.mCurrentState != 0) {
                    seslAppbarState2.mCurrentState = 0;
                }
            }
        } else if (Math.abs(i) >= height) {
            SeslAppbarState seslAppbarState3 = this.mAppbarState;
            if (seslAppbarState3.mCurrentState != 0) {
                seslAppbarState3.mCurrentState = 0;
            }
        } else if (Math.abs(i) == 0) {
            SeslAppbarState seslAppbarState4 = this.mAppbarState;
            if (seslAppbarState4.mCurrentState != 1) {
                seslAppbarState4.mCurrentState = 1;
            }
        } else {
            SeslAppbarState seslAppbarState5 = this.mAppbarState;
            if (seslAppbarState5.mCurrentState != 3) {
                seslAppbarState5.mCurrentState = 3;
            }
        }
        if (!willNotDraw()) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            postInvalidateOnAnimation();
        }
        List list = this.listeners;
        if (list != null) {
            int size = ((ArrayList) list).size();
            for (int i2 = 0; i2 < size; i2++) {
                OnOffsetChangedListener onOffsetChangedListener = (OnOffsetChangedListener) ((ArrayList) this.listeners).get(i2);
                if (onOffsetChangedListener != null) {
                    onOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    public final float seslGetCollapsedHeight() {
        return this.mCollapsedHeight + getImmersiveTopInset();
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    public final void setExpanded(boolean z) {
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        setExpanded(z, isLaidOut(), true);
    }

    public final boolean setLiftedState(boolean z) {
        if (this.lifted == z) {
            return false;
        }
        this.lifted = z;
        refreshDrawableState();
        if (!(getBackground() instanceof MaterialShapeDrawable)) {
            return true;
        }
        if (this.hasLiftOnScrollColor) {
            startLiftOnScrollColorAnimation(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
            return true;
        }
        if (!this.liftOnScroll) {
            return true;
        }
        startLiftOnScrollColorAnimation(z ? 0.0f : this.appBarElevation, z ? this.appBarElevation : 0.0f);
        return true;
    }

    @Override // android.widget.LinearLayout
    public final void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        Drawable drawable = this.statusBarForeground;
        if (drawable != null) {
            drawable.setVisible(z, false);
        }
    }

    public final boolean shouldLift(View view) {
        int i;
        if (this.liftOnScrollTargetView == null && (i = this.liftOnScrollTargetViewId) != -1) {
            View findViewById = view != null ? view.findViewById(i) : null;
            if (findViewById == null && (getParent() instanceof ViewGroup)) {
                findViewById = ((ViewGroup) getParent()).findViewById(this.liftOnScrollTargetViewId);
            }
            if (findViewById != null) {
                this.liftOnScrollTargetView = new WeakReference(findViewById);
            }
        }
        WeakReference weakReference = this.liftOnScrollTargetView;
        View view2 = weakReference != null ? (View) weakReference.get() : null;
        if (view2 != null) {
            view = view2;
        }
        return view != null && (view.canScrollVertically(-1) || view.getScrollY() > 0);
    }

    public final boolean shouldOffsetFirstChild() {
        if (getChildCount() <= 0) {
            return false;
        }
        View childAt = getChildAt(0);
        if (childAt.getVisibility() == 8) {
            return false;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return !childAt.getFitsSystemWindows();
    }

    public final void startLiftOnScrollColorAnimation(float f, float f2) {
        ValueAnimator valueAnimator = this.liftOnScrollColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        this.liftOnScrollColorAnimator = ofFloat;
        ofFloat.setDuration(this.liftOnScrollColorDuration);
        this.liftOnScrollColorAnimator.setInterpolator(this.liftOnScrollColorInterpolator);
        ValueAnimator.AnimatorUpdateListener animatorUpdateListener = this.liftOnScrollColorUpdateListener;
        if (animatorUpdateListener != null) {
            this.liftOnScrollColorAnimator.addUpdateListener(animatorUpdateListener);
        }
        this.liftOnScrollColorAnimator.start();
    }

    public final void updateInternalHeight() {
        float f;
        float f2;
        int windowHeight = getWindowHeight();
        if (this.mUseCustomHeight) {
            float f3 = this.mCustomHeightProportion;
            if (f3 != 0.0f) {
                if (this.mIsCanScroll) {
                    float windowHeight2 = getWindowHeight();
                    float immersiveTopInset = getImmersiveTopInset();
                    if (windowHeight2 == 0.0f) {
                        windowHeight2 = 1.0f;
                    }
                    f2 = immersiveTopInset / windowHeight2;
                } else {
                    f2 = 0.0f;
                }
                f = f3 + f2;
            } else {
                f = 0.0f;
            }
        } else {
            f = this.mHeightProportion;
        }
        float f4 = windowHeight * f;
        if (f4 == 0.0f) {
            if (!this.mUseCollapsedHeight && (getImmBehavior() == null || !this.mIsCanScroll)) {
                float seslGetCollapsedHeight = seslGetCollapsedHeight();
                Log.i("AppBarLayout", "update InternalCollapsedHeight from updateInternalHeight() : " + seslGetCollapsedHeight);
                this.mUseCollapsedHeight = false;
                this.mCollapsedHeight = seslGetCollapsedHeight;
            }
            f4 = seslGetCollapsedHeight();
        }
        StringBuilder sb = new StringBuilder("[calculateInternalHeight] orientation:" + this.mResources.getConfiguration().orientation + ", density:" + this.mResources.getConfiguration().densityDpi + ", windowHeight:" + windowHeight + ", heightDp:" + f4);
        if (!this.mUseCustomHeight) {
            sb.append(", [3]mHeightProportion : ");
            sb.append(this.mHeightProportion);
        } else if (this.mSetCustomProportion) {
            sb.append(", [1]mCustomHeightProportion : ");
            sb.append(this.mCustomHeightProportion);
        }
        if (this.mIsActivatedImmersiveScroll) {
            Log.i("AppBarLayout", sb.toString());
        }
        int i = (int) f4;
        boolean z = this.mUseCustomHeight;
        if (!z || (z && this.mSetCustomProportion)) {
            try {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams).height = i;
                setLayoutParams(layoutParams);
            } catch (ClassCastException e) {
                Log.e("AppBarLayout", Log.getStackTraceString(e));
            }
        }
        if (this.mIsActivatedImmersiveScroll) {
            StringBuilder sb2 = new StringBuilder("[updateInternalHeight] mUseCustomHeight : " + this.mUseCustomHeight + ", mSetCustomProportion : " + this.mSetCustomProportion + ", mSetCustomHeight : false, mIsImmersiveScroll : " + this.mIsActivatedImmersiveScroll + ", mIsSetByUser : " + this.mIsActivatedByUser + ", mImmersiveTopInset : " + this.mImmersiveTopInset);
            WindowInsets rootWindowInsets = getRootView().getRootWindowInsets();
            if (rootWindowInsets != null) {
                sb2.append("\n");
                sb2.append(rootWindowInsets);
            }
            Log.i("AppBarLayout", sb2.toString());
        }
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.statusBarForeground;
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.appBarLayoutStyle);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084664), attributeSet, i);
        Integer valueOf;
        final Integer num;
        int i2;
        this.totalScrollRange = -1;
        this.downPreScrollRange = -1;
        this.downScrollRange = -1;
        boolean z = false;
        this.pendingAction = 0;
        this.lifted = false;
        this.liftOnScrollListeners = new ArrayList();
        this.mBottomPadding = 0;
        this.mUseCollapsedHeight = false;
        this.isMouse = false;
        this.mIsDetachedState = false;
        this.mIsActivatedImmersiveScroll = false;
        this.mIsActivatedByUser = false;
        this.mIsCanScroll = false;
        this.mImmersiveTopInset = 0;
        this.mLastTappableInsets = null;
        this.mLastSysInsets = null;
        Context context2 = getContext();
        super.setOrientation(1);
        Context context3 = getContext();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context3, attributeSet, ViewUtilsLollipop.STATE_LIST_ANIM_ATTRS, i, 2132084664, new int[0]);
        try {
            if (obtainStyledAttributes.hasValue(0)) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(context3, obtainStyledAttributes.getResourceId(0, 0)));
            }
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.AppBarLayout, i, 2132084664, new int[0]);
            SeslAppbarState seslAppbarState = new SeslAppbarState();
            seslAppbarState.mCurrentState = 3;
            this.mAppbarState = seslAppbarState;
            Resources resources = getResources();
            this.mResources = resources;
            boolean isLightTheme = SeslMisc.isLightTheme(context2);
            if (obtainStyledAttributes2.hasValue(0)) {
                Drawable drawable = obtainStyledAttributes2.getDrawable(0);
                this.mBackground = drawable;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                setBackground(drawable);
            } else {
                this.mBackground = null;
                setBackgroundColor(resources.getColor(isLightTheme ? R.color.sesl_action_bar_background_color_light : R.color.sesl_action_bar_background_color_dark));
            }
            final ColorStateList colorStateList = MaterialResources.getColorStateList(context2, obtainStyledAttributes2, 7);
            this.hasLiftOnScrollColor = colorStateList != null;
            final ColorStateList colorStateListOrNull = DrawableUtils.getColorStateListOrNull(getBackground());
            if (colorStateListOrNull != null) {
                final MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
                materialShapeDrawable.setFillColor(colorStateListOrNull);
                if (colorStateList != null) {
                    Context context4 = getContext();
                    TypedValue resolve = MaterialAttributes.resolve(context4, R.attr.colorSurface);
                    if (resolve != null) {
                        int i3 = resolve.resourceId;
                        if (i3 != 0) {
                            i2 = context4.getColor(i3);
                        } else {
                            i2 = resolve.data;
                        }
                        num = Integer.valueOf(i2);
                    } else {
                        num = null;
                    }
                    this.liftOnScrollColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            Integer num2;
                            AppBarLayout appBarLayout = AppBarLayout.this;
                            ColorStateList colorStateList2 = colorStateListOrNull;
                            ColorStateList colorStateList3 = colorStateList;
                            MaterialShapeDrawable materialShapeDrawable2 = materialShapeDrawable;
                            Integer num3 = num;
                            int i4 = AppBarLayout.$r8$clinit;
                            appBarLayout.getClass();
                            int layer = MaterialColors.layer(colorStateList2.getDefaultColor(), colorStateList3.getDefaultColor(), ((Float) valueAnimator.getAnimatedValue()).floatValue());
                            materialShapeDrawable2.setFillColor(ColorStateList.valueOf(layer));
                            if (appBarLayout.statusBarForeground != null && (num2 = appBarLayout.statusBarForegroundOriginalColor) != null && num2.equals(num3)) {
                                appBarLayout.statusBarForeground.setTint(layer);
                            }
                            if (((ArrayList) appBarLayout.liftOnScrollListeners).isEmpty()) {
                                return;
                            }
                            Iterator it = ((ArrayList) appBarLayout.liftOnScrollListeners).iterator();
                            while (it.hasNext()) {
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                                if (materialShapeDrawable2.drawableState.fillColor != null) {
                                    throw null;
                                }
                            }
                        }
                    };
                    WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    setBackground(materialShapeDrawable);
                } else {
                    materialShapeDrawable.initializeElevationOverlay(context2);
                    this.liftOnScrollColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.appbar.AppBarLayout$$ExternalSyntheticLambda1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            AppBarLayout appBarLayout = AppBarLayout.this;
                            MaterialShapeDrawable materialShapeDrawable2 = materialShapeDrawable;
                            int i4 = AppBarLayout.$r8$clinit;
                            appBarLayout.getClass();
                            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            materialShapeDrawable2.setElevation(floatValue);
                            Drawable drawable2 = appBarLayout.statusBarForeground;
                            if (drawable2 instanceof MaterialShapeDrawable) {
                                ((MaterialShapeDrawable) drawable2).setElevation(floatValue);
                            }
                            Iterator it = ((ArrayList) appBarLayout.liftOnScrollListeners).iterator();
                            if (it.hasNext()) {
                                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                                throw null;
                            }
                        }
                    };
                    WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                    setBackground(materialShapeDrawable);
                }
            }
            this.liftOnScrollColorDuration = MotionUtils.resolveThemeDuration(context2, R.attr.motionDurationMedium2, getResources().getInteger(R.integer.app_bar_elevation_anim_duration));
            this.liftOnScrollColorInterpolator = MotionUtils.resolveThemeInterpolator(context2, R.attr.motionEasingStandardInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
            if (obtainStyledAttributes2.hasValue(5)) {
                setExpanded(obtainStyledAttributes2.getBoolean(5, false), false, false);
            }
            if (obtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, obtainStyledAttributes2.getDimensionPixelSize(4, 0));
            }
            if (obtainStyledAttributes2.hasValue(10)) {
                this.mUseCustomHeight = obtainStyledAttributes2.getBoolean(10, false);
            }
            if (obtainStyledAttributes2.hasValue(9)) {
                this.mSetCustomProportion = true;
                this.mCustomHeightProportion = obtainStyledAttributes2.getFloat(9, 0.39f);
            } else {
                this.mSetCustomProportion = false;
                this.mCustomHeightProportion = 0.39f;
            }
            this.mHeightProportion = SeslAppBarHelper$Companion.getAppBarProPortion(getContext());
            if (obtainStyledAttributes2.hasValue(11)) {
                this.mUseCustomPadding = obtainStyledAttributes2.getBoolean(11, false);
            }
            if (this.mUseCustomPadding) {
                this.mBottomPadding = obtainStyledAttributes2.getDimensionPixelSize(1, 0);
            } else {
                this.mBottomPadding = resources.getDimensionPixelOffset(R.dimen.sesl_extended_appbar_bottom_padding);
            }
            setPadding(0, 0, 0, this.mBottomPadding);
            float dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.sesl_action_bar_height_with_padding) + this.mBottomPadding;
            this.mUseCollapsedHeight = false;
            this.mCollapsedHeight = dimensionPixelSize;
            if (obtainStyledAttributes2.hasValue(4)) {
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(this, obtainStyledAttributes2.getDimensionPixelSize(4, 0));
            }
            if (obtainStyledAttributes2.hasValue(3)) {
                setKeyboardNavigationCluster(obtainStyledAttributes2.getBoolean(3, false));
            }
            if (obtainStyledAttributes2.hasValue(2)) {
                setTouchscreenBlocksFocus(obtainStyledAttributes2.getBoolean(2, false));
            }
            this.appBarElevation = getResources().getDimension(R.dimen.design_appbar_elevation);
            this.liftOnScroll = obtainStyledAttributes2.getBoolean(6, false);
            this.liftOnScrollTargetViewId = obtainStyledAttributes2.getResourceId(8, -1);
            Drawable drawable2 = obtainStyledAttributes2.getDrawable(12);
            Drawable drawable3 = this.statusBarForeground;
            if (drawable3 != drawable2) {
                if (drawable3 != null) {
                    drawable3.setCallback(null);
                }
                Drawable mutate = drawable2 != null ? drawable2.mutate() : null;
                this.statusBarForeground = mutate;
                if (mutate instanceof MaterialShapeDrawable) {
                    valueOf = Integer.valueOf(((MaterialShapeDrawable) mutate).resolvedTintColor);
                } else {
                    ColorStateList colorStateListOrNull2 = DrawableUtils.getColorStateListOrNull(mutate);
                    valueOf = colorStateListOrNull2 != null ? Integer.valueOf(colorStateListOrNull2.getDefaultColor()) : null;
                }
                this.statusBarForegroundOriginalColor = valueOf;
                Drawable drawable4 = this.statusBarForeground;
                if (drawable4 != null) {
                    if (drawable4.isStateful()) {
                        this.statusBarForeground.setState(getDrawableState());
                    }
                    Drawable drawable5 = this.statusBarForeground;
                    WeakHashMap weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                    drawable5.setLayoutDirection(getLayoutDirection());
                    this.statusBarForeground.setVisible(getVisibility() == 0, false);
                    this.statusBarForeground.setCallback(this);
                }
                if (this.statusBarForeground != null && getTopInset() > 0) {
                    z = true;
                }
                setWillNotDraw(!z);
                WeakHashMap weakHashMap5 = ViewCompat.sViewPropertyAnimatorMap;
                postInvalidateOnAnimation();
            }
            obtainStyledAttributes2.recycle();
            OnApplyWindowInsetsListener onApplyWindowInsetsListener = new OnApplyWindowInsetsListener() { // from class: com.google.android.material.appbar.AppBarLayout.1
                @Override // androidx.core.view.OnApplyWindowInsetsListener
                public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    int i4 = AppBarLayout.$r8$clinit;
                    WindowInsetsCompat.Impl impl = windowInsetsCompat.mImpl;
                    Insets insets = impl.getInsets(7);
                    Insets insets2 = impl.getInsets(64);
                    AppBarLayout appBarLayout = AppBarLayout.this;
                    if (!insets2.equals(appBarLayout.mLastTappableInsets) || !insets.equals(appBarLayout.mLastSysInsets)) {
                        Log.d("AppBarLayout", "[onApplyWindowInsets] sysInsets : " + insets + ", tappableInsets : " + insets2);
                        if (appBarLayout.getImmBehavior() != null) {
                            SeslImmersiveScrollBehavior immBehavior = appBarLayout.getImmBehavior();
                            if (immBehavior.mAppBarLayout != null) {
                                immBehavior.cancelWindowInsetsAnimationController();
                                immBehavior.updateSystemBarsHeight();
                                immBehavior.mAppBarLayout.onOffsetChanged(immBehavior.getTopAndBottomOffset());
                            }
                        }
                        appBarLayout.mLastSysInsets = insets;
                        appBarLayout.mLastTappableInsets = insets2;
                    }
                    WeakHashMap weakHashMap6 = ViewCompat.sViewPropertyAnimatorMap;
                    WindowInsetsCompat windowInsetsCompat2 = appBarLayout.getFitsSystemWindows() ? windowInsetsCompat : null;
                    if (!Objects.equals(appBarLayout.lastInsets, windowInsetsCompat2)) {
                        appBarLayout.lastInsets = windowInsetsCompat2;
                        appBarLayout.setWillNotDraw(!(appBarLayout.statusBarForeground != null && appBarLayout.getTopInset() > 0));
                        appBarLayout.requestLayout();
                    }
                    return windowInsetsCompat;
                }
            };
            WeakHashMap weakHashMap6 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, onApplyWindowInsetsListener);
            this.mCurrentOrientation = resources.getConfiguration().orientation;
            this.mCurrentScreenHeight = resources.getConfiguration().screenHeightDp;
            StringBuilder sb = new StringBuilder("Init : mUseCustomHeight = ");
            sb.append(this.mUseCustomHeight);
            sb.append(", mCustomHeightProportion = ");
            sb.append(this.mCustomHeightProportion);
            sb.append(", mHeightProportion = ");
            sb.append(this.mHeightProportion);
            sb.append(", mUseCustomPadding = ");
            sb.append(this.mUseCustomPadding);
            sb.append(", mCurrentScreenHeight = ");
            TooltipPopup$$ExternalSyntheticOutline0.m(sb, this.mCurrentScreenHeight, "AppBarLayout");
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LinearLayout.LayoutParams generateDefaultLayoutParams() {
        LayoutParams layoutParams = new LayoutParams(-1, -2);
        layoutParams.scrollFlags = 1;
        return layoutParams;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final /* bridge */ /* synthetic */ LinearLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateLayoutParams(layoutParams);
    }

    public final void setExpanded(boolean z, boolean z2, boolean z3) {
        int i;
        setLiftedState(!z);
        if (z) {
            i = 1;
        } else {
            i = this.mIsActivatedImmersiveScroll ? 512 : 2;
        }
        this.pendingAction = i | (z2 ? 4 : 0) | (z3 ? 8 : 0);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public final LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        LayoutParams layoutParams = new LayoutParams(context, attributeSet);
        layoutParams.scrollFlags = 1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.AppBarLayout_Layout);
        layoutParams.scrollFlags = obtainStyledAttributes.getInt(1, 0);
        layoutParams.scrollEffect = obtainStyledAttributes.getInt(0, 0) != 1 ? null : new CompressChildScrollEffect();
        if (obtainStyledAttributes.hasValue(2)) {
            layoutParams.scrollInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, obtainStyledAttributes.getResourceId(2, 0));
        }
        obtainStyledAttributes.recycle();
        return layoutParams;
    }

    public static LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            LayoutParams layoutParams2 = new LayoutParams((LinearLayout.LayoutParams) layoutParams);
            layoutParams2.scrollFlags = 1;
            return layoutParams2;
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            LayoutParams layoutParams3 = new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
            layoutParams3.scrollFlags = 1;
            return layoutParams3;
        }
        LayoutParams layoutParams4 = new LayoutParams(layoutParams);
        layoutParams4.scrollFlags = 1;
        return layoutParams4;
    }
}
