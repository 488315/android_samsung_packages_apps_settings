package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.android.settings.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    public static final AnonymousClass6 HEIGHT;
    public static final AnonymousClass6 PADDING_END;
    public static final AnonymousClass6 PADDING_START;
    public static final AnonymousClass6 WIDTH;
    public int animState;
    public final ExtendedFloatingActionButtonBehavior behavior;
    public final int collapsedSize;
    public final ChangeSizeStrategy extendStrategy;
    public int extendedPaddingEnd;
    public int extendedPaddingStart;
    public final HideStrategy hideStrategy;
    public boolean isExtended;
    public boolean isTransforming;
    public int originalHeight;
    public ColorStateList originalTextCsl;
    public int originalWidth;
    public final ShowStrategy showStrategy;
    public final ChangeSizeStrategy shrinkStrategy;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ChangeSizeStrategy extends BaseMotionStrategy {
        public final boolean extending;
        public final Size size;

        public ChangeSizeStrategy(AnimatorTracker animatorTracker, Size size, boolean z) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
            this.size = size;
            this.extending = z;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final AnimatorSet createAnimator() {
            MotionSpec motionSpec = this.motionSpec;
            if (motionSpec == null) {
                if (this.defaultMotionSpec == null) {
                    this.defaultMotionSpec = MotionSpec.createFromResource(this.context, getDefaultMotionSpecResource());
                }
                motionSpec = this.defaultMotionSpec;
                motionSpec.getClass();
            }
            boolean hasPropertyValues = motionSpec.hasPropertyValues("width");
            Size size = this.size;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (hasPropertyValues) {
                PropertyValuesHolder[] propertyValues = motionSpec.getPropertyValues("width");
                propertyValues[0].setFloatValues(extendedFloatingActionButton.getWidth(), size.getWidth());
                motionSpec.setPropertyValues("width", propertyValues);
            }
            if (motionSpec.hasPropertyValues("height")) {
                PropertyValuesHolder[] propertyValues2 = motionSpec.getPropertyValues("height");
                propertyValues2[0].setFloatValues(extendedFloatingActionButton.getHeight(), size.getHeight());
                motionSpec.setPropertyValues("height", propertyValues2);
            }
            if (motionSpec.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] propertyValues3 = motionSpec.getPropertyValues("paddingStart");
                PropertyValuesHolder propertyValuesHolder = propertyValues3[0];
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder.setFloatValues(extendedFloatingActionButton.getPaddingStart(), size.getPaddingStart());
                motionSpec.setPropertyValues("paddingStart", propertyValues3);
            }
            if (motionSpec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] propertyValues4 = motionSpec.getPropertyValues("paddingEnd");
                PropertyValuesHolder propertyValuesHolder2 = propertyValues4[0];
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder2.setFloatValues(extendedFloatingActionButton.getPaddingEnd(), size.getPaddingEnd());
                motionSpec.setPropertyValues("paddingEnd", propertyValues4);
            }
            if (motionSpec.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] propertyValues5 = motionSpec.getPropertyValues("labelOpacity");
                boolean z = this.extending;
                propertyValues5[0].setFloatValues(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
                motionSpec.setPropertyValues("labelOpacity", propertyValues5);
            }
            return createAnimator(motionSpec);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final int getDefaultMotionSpecResource() {
            return this.extending ? R.animator.mtrl_extended_fab_change_size_expand_motion_spec : R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationEnd() {
            this.tracker.currentAnimator = null;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isTransforming = false;
            extendedFloatingActionButton.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            Size size = this.size;
            layoutParams.width = size.getLayoutParams().width;
            layoutParams.height = size.getLayoutParams().height;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationStart(Animator animator) {
            AnimatorTracker animatorTracker = this.tracker;
            Animator animator2 = animatorTracker.currentAnimator;
            if (animator2 != null) {
                animator2.cancel();
            }
            animatorTracker.currentAnimator = animator;
            boolean z = this.extending;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = z;
            extendedFloatingActionButton.isTransforming = true;
            extendedFloatingActionButton.setHorizontallyScrolling(true);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            boolean z = this.extending;
            extendedFloatingActionButton.isExtended = z;
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            if (!z) {
                extendedFloatingActionButton.originalWidth = layoutParams.width;
                extendedFloatingActionButton.originalHeight = layoutParams.height;
            }
            Size size = this.size;
            layoutParams.width = size.getLayoutParams().width;
            layoutParams.height = size.getLayoutParams().height;
            int paddingStart = size.getPaddingStart();
            int paddingTop = extendedFloatingActionButton.getPaddingTop();
            int paddingEnd = size.getPaddingEnd();
            int paddingBottom = extendedFloatingActionButton.getPaddingBottom();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            extendedFloatingActionButton.setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom);
            extendedFloatingActionButton.requestLayout();
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final boolean shouldCancel() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            return this.extending == extendedFloatingActionButton.isExtended || extendedFloatingActionButton.icon == null || TextUtils.isEmpty(extendedFloatingActionButton.getText());
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class HideStrategy extends BaseMotionStrategy {
        public boolean isCancelled;

        public HideStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationEnd() {
            this.tracker.currentAnimator = null;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.animState = 0;
            if (this.isCancelled) {
                return;
            }
            extendedFloatingActionButton.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationStart(Animator animator) {
            AnimatorTracker animatorTracker = this.tracker;
            Animator animator2 = animatorTracker.currentAnimator;
            if (animator2 != null) {
                animator2.cancel();
            }
            animatorTracker.currentAnimator = animator;
            this.isCancelled = false;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.animState = 1;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final boolean shouldCancel() {
            AnonymousClass6 anonymousClass6 = ExtendedFloatingActionButton.WIDTH;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (extendedFloatingActionButton.getVisibility() == 0) {
                if (extendedFloatingActionButton.animState != 1) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 2) {
                return false;
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class OnChangedCallback {
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ShowStrategy extends BaseMotionStrategy {
        public ShowStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_show_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationEnd() {
            this.tracker.currentAnimator = null;
            ExtendedFloatingActionButton.this.animState = 0;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void onAnimationStart(Animator animator) {
            AnimatorTracker animatorTracker = this.tracker;
            Animator animator2 = animatorTracker.currentAnimator;
            if (animator2 != null) {
                animator2.cancel();
            }
            animatorTracker.currentAnimator = animator;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.animState = 2;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.setAlpha(1.0f);
            extendedFloatingActionButton.setScaleY(1.0f);
            extendedFloatingActionButton.setScaleX(1.0f);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy
        public final boolean shouldCancel() {
            AnonymousClass6 anonymousClass6 = ExtendedFloatingActionButton.WIDTH;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (extendedFloatingActionButton.getVisibility() != 0) {
                if (extendedFloatingActionButton.animState != 2) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 1) {
                return false;
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    static {
        Class<Float> cls = Float.class;
        final int i = 0;
        WIDTH = new Property(cls, "width") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                switch (i) {
                    case 0:
                        return Float.valueOf(((View) obj).getLayoutParams().width);
                    case 1:
                        return Float.valueOf(((View) obj).getLayoutParams().height);
                    case 2:
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingStart());
                    default:
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingEnd());
                }
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                switch (i) {
                    case 0:
                        View view = (View) obj;
                        view.getLayoutParams().width = ((Float) obj2).intValue();
                        view.requestLayout();
                        break;
                    case 1:
                        View view2 = (View) obj;
                        view2.getLayoutParams().height = ((Float) obj2).intValue();
                        view2.requestLayout();
                        break;
                    case 2:
                        View view3 = (View) obj;
                        int intValue = ((Float) obj2).intValue();
                        int paddingTop = view3.getPaddingTop();
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        view3.setPaddingRelative(intValue, paddingTop, view3.getPaddingEnd(), view3.getPaddingBottom());
                        break;
                    default:
                        View view4 = (View) obj;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        view4.setPaddingRelative(view4.getPaddingStart(), view4.getPaddingTop(), ((Float) obj2).intValue(), view4.getPaddingBottom());
                        break;
                }
            }
        };
        final int i2 = 1;
        HEIGHT = new Property(cls, "height") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                switch (i2) {
                    case 0:
                        return Float.valueOf(((View) obj).getLayoutParams().width);
                    case 1:
                        return Float.valueOf(((View) obj).getLayoutParams().height);
                    case 2:
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingStart());
                    default:
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingEnd());
                }
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                switch (i2) {
                    case 0:
                        View view = (View) obj;
                        view.getLayoutParams().width = ((Float) obj2).intValue();
                        view.requestLayout();
                        break;
                    case 1:
                        View view2 = (View) obj;
                        view2.getLayoutParams().height = ((Float) obj2).intValue();
                        view2.requestLayout();
                        break;
                    case 2:
                        View view3 = (View) obj;
                        int intValue = ((Float) obj2).intValue();
                        int paddingTop = view3.getPaddingTop();
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        view3.setPaddingRelative(intValue, paddingTop, view3.getPaddingEnd(), view3.getPaddingBottom());
                        break;
                    default:
                        View view4 = (View) obj;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        view4.setPaddingRelative(view4.getPaddingStart(), view4.getPaddingTop(), ((Float) obj2).intValue(), view4.getPaddingBottom());
                        break;
                }
            }
        };
        final int i3 = 2;
        PADDING_START = new Property(cls, "paddingStart") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                switch (i3) {
                    case 0:
                        return Float.valueOf(((View) obj).getLayoutParams().width);
                    case 1:
                        return Float.valueOf(((View) obj).getLayoutParams().height);
                    case 2:
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingStart());
                    default:
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingEnd());
                }
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                switch (i3) {
                    case 0:
                        View view = (View) obj;
                        view.getLayoutParams().width = ((Float) obj2).intValue();
                        view.requestLayout();
                        break;
                    case 1:
                        View view2 = (View) obj;
                        view2.getLayoutParams().height = ((Float) obj2).intValue();
                        view2.requestLayout();
                        break;
                    case 2:
                        View view3 = (View) obj;
                        int intValue = ((Float) obj2).intValue();
                        int paddingTop = view3.getPaddingTop();
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        view3.setPaddingRelative(intValue, paddingTop, view3.getPaddingEnd(), view3.getPaddingBottom());
                        break;
                    default:
                        View view4 = (View) obj;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        view4.setPaddingRelative(view4.getPaddingStart(), view4.getPaddingTop(), ((Float) obj2).intValue(), view4.getPaddingBottom());
                        break;
                }
            }
        };
        final int i4 = 3;
        PADDING_END = new Property(cls, "paddingEnd") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                switch (i4) {
                    case 0:
                        return Float.valueOf(((View) obj).getLayoutParams().width);
                    case 1:
                        return Float.valueOf(((View) obj).getLayoutParams().height);
                    case 2:
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingStart());
                    default:
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        return Float.valueOf(((View) obj).getPaddingEnd());
                }
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                switch (i4) {
                    case 0:
                        View view = (View) obj;
                        view.getLayoutParams().width = ((Float) obj2).intValue();
                        view.requestLayout();
                        break;
                    case 1:
                        View view2 = (View) obj;
                        view2.getLayoutParams().height = ((Float) obj2).intValue();
                        view2.requestLayout();
                        break;
                    case 2:
                        View view3 = (View) obj;
                        int intValue = ((Float) obj2).intValue();
                        int paddingTop = view3.getPaddingTop();
                        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                        view3.setPaddingRelative(intValue, paddingTop, view3.getPaddingEnd(), view3.getPaddingBottom());
                        break;
                    default:
                        View view4 = (View) obj;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        view4.setPaddingRelative(view4.getPaddingStart(), view4.getPaddingTop(), ((Float) obj2).intValue(), view4.getPaddingBottom());
                        break;
                }
            }
        };
    }

    public ExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        return this.behavior;
    }

    public int getCollapsedSize() {
        int i = this.collapsedSize;
        if (i >= 0) {
            return i;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return (Math.min(getPaddingStart(), getPaddingEnd()) * 2) + this.iconSize;
    }

    @Override // com.google.android.material.button.MaterialButton, androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isExtended && TextUtils.isEmpty(getText()) && this.icon != null) {
            this.isExtended = false;
            this.shrinkStrategy.performNow();
        }
    }

    public final void performMotion(int i) {
        final BaseMotionStrategy baseMotionStrategy;
        if (i == 0) {
            baseMotionStrategy = this.showStrategy;
        } else if (i == 1) {
            baseMotionStrategy = this.hideStrategy;
        } else if (i == 2) {
            baseMotionStrategy = this.shrinkStrategy;
        } else {
            if (i != 3) {
                throw new IllegalStateException(SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "Unknown strategy type: "));
            }
            baseMotionStrategy = this.extendStrategy;
        }
        if (baseMotionStrategy.shouldCancel()) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!isLaidOut()) {
            getVisibility();
        } else if (!isInEditMode()) {
            if (i == 2) {
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    this.originalWidth = layoutParams.width;
                    this.originalHeight = layoutParams.height;
                } else {
                    this.originalWidth = getWidth();
                    this.originalHeight = getHeight();
                }
            }
            measure(0, 0);
            AnimatorSet createAnimator = baseMotionStrategy.createAnimator();
            createAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.5
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                    BaseMotionStrategy.this.onAnimationCancel();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    BaseMotionStrategy.this.onAnimationEnd();
                    if (this.cancelled) {
                        return;
                    }
                    BaseMotionStrategy.this.getClass();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    BaseMotionStrategy.this.onAnimationStart(animator);
                    this.cancelled = false;
                }
            });
            Iterator it = baseMotionStrategy.listeners.iterator();
            while (it.hasNext()) {
                createAnimator.addListener((Animator.AnimatorListener) it.next());
            }
            createAnimator.start();
            return;
        }
        baseMotionStrategy.performNow();
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        if (!this.isExtended || this.isTransforming) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.extendedPaddingStart = getPaddingStart();
        this.extendedPaddingEnd = getPaddingEnd();
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        if (!this.isExtended || this.isTransforming) {
            return;
        }
        this.extendedPaddingStart = i;
        this.extendedPaddingEnd = i3;
    }

    @Override // android.widget.TextView
    public final void setTextColor(int i) {
        super.setTextColor(i);
        this.originalTextCsl = getTextColors();
    }

    public final void silentlyUpdateTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.extendedFloatingActionButtonStyle);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior {
        public final boolean autoHideEnabled;
        public final boolean autoShrinkEnabled;
        public Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final /* bridge */ /* synthetic */ boolean getInsetDodgeRect(View view) {
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            if (view2 instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton);
            } else {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) {
                    updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton);
                }
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            List dependencies = coordinatorLayout.getDependencies(extendedFloatingActionButton);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view2 = (View) dependencies.get(i2);
                if (!(view2 instanceof AppBarLayout)) {
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if ((layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) && updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton)) {
                        break;
                    }
                } else {
                    if (updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton)) {
                        break;
                    }
                }
            }
            coordinatorLayout.onLayoutChild(extendedFloatingActionButton, i);
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x0059  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0063  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean updateFabVisibilityForAppBarLayout(androidx.coordinatorlayout.widget.CoordinatorLayout r7, com.google.android.material.appbar.AppBarLayout r8, com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton r9) {
            /*
                r6 = this;
                boolean r0 = r6.autoShrinkEnabled
                android.view.ViewGroup$LayoutParams r1 = r9.getLayoutParams()
                androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r1 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r1
                boolean r2 = r6.autoHideEnabled
                r3 = 0
                if (r2 != 0) goto L10
                if (r0 != 0) goto L10
                goto L18
            L10:
                int r1 = r1.mAnchorId
                int r2 = r8.getId()
                if (r1 == r2) goto L19
            L18:
                return r3
            L19:
                android.graphics.Rect r1 = r6.tmpRect
                if (r1 != 0) goto L24
                android.graphics.Rect r1 = new android.graphics.Rect
                r1.<init>()
                r6.tmpRect = r1
            L24:
                android.graphics.Rect r6 = r6.tmpRect
                com.google.android.material.internal.DescendantOffsetUtils.getDescendantRect(r7, r8, r6)
                int r6 = r6.bottom
                int r7 = r8.getTopInset()
                java.util.WeakHashMap r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                int r1 = r8.getMinimumHeight()
                r2 = 3
                r4 = 2
                r5 = 1
                if (r1 == 0) goto L3d
            L3a:
                int r1 = r1 * r4
                int r1 = r1 + r7
                goto L57
            L3d:
                int r1 = r8.getChildCount()
                if (r1 < r5) goto L4d
                int r1 = r1 - r5
                android.view.View r1 = r8.getChildAt(r1)
                int r1 = r1.getMinimumHeight()
                goto L4e
            L4d:
                r1 = r3
            L4e:
                if (r1 == 0) goto L51
                goto L3a
            L51:
                int r7 = r8.getHeight()
                int r1 = r7 / 3
            L57:
                if (r6 > r1) goto L63
                if (r0 == 0) goto L5c
                goto L5d
            L5c:
                r4 = r5
            L5d:
                com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6 r6 = com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.WIDTH
                r9.performMotion(r4)
                goto L6b
            L63:
                if (r0 == 0) goto L66
                r3 = r2
            L66:
                com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6 r6 = com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.WIDTH
                r9.performMotion(r3)
            L6b:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.ExtendedFloatingActionButtonBehavior.updateFabVisibilityForAppBarLayout(androidx.coordinatorlayout.widget.CoordinatorLayout, com.google.android.material.appbar.AppBarLayout, com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton):boolean");
        }

        public final boolean updateFabVisibilityForBottomSheet(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            boolean z = this.autoShrinkEnabled;
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams();
            if ((!this.autoHideEnabled && !z) || layoutParams.mAnchorId != view.getId()) {
                return false;
            }
            if (view.getTop() < (extendedFloatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams())).topMargin) {
                int i = z ? 2 : 1;
                AnonymousClass6 anonymousClass6 = ExtendedFloatingActionButton.WIDTH;
                extendedFloatingActionButton.performMotion(i);
            } else {
                int i2 = z ? 3 : 0;
                AnonymousClass6 anonymousClass62 = ExtendedFloatingActionButton.WIDTH;
                extendedFloatingActionButton.performMotion(i2);
            }
            return true;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attributeSet) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = obtainStyledAttributes.getBoolean(0, false);
            this.autoShrinkEnabled = obtainStyledAttributes.getBoolean(1, true);
            obtainStyledAttributes.recycle();
        }

        public void setInternalAutoHideCallback(OnChangedCallback onChangedCallback) {
        }

        public void setInternalAutoShrinkCallback(OnChangedCallback onChangedCallback) {
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$1] */
    /* JADX WARN: Type inference failed for: r9v1, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$3] */
    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132084899), attributeSet, i);
        boolean z;
        Size size;
        final int i2 = 1;
        this.animState = 0;
        AnimatorTracker animatorTracker = new AnimatorTracker();
        ShowStrategy showStrategy = new ShowStrategy(animatorTracker);
        this.showStrategy = showStrategy;
        HideStrategy hideStrategy = new HideStrategy(animatorTracker);
        this.hideStrategy = hideStrategy;
        this.isExtended = true;
        this.isTransforming = false;
        Context context2 = getContext();
        this.behavior = new ExtendedFloatingActionButtonBehavior(context2, attributeSet);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.ExtendedFloatingActionButton, i, 2132084899, new int[0]);
        MotionSpec createFromAttribute = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 5);
        MotionSpec createFromAttribute2 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 4);
        MotionSpec createFromAttribute3 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 2);
        MotionSpec createFromAttribute4 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 6);
        this.collapsedSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        int i3 = obtainStyledAttributes.getInt(3, 1);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.extendedPaddingStart = getPaddingStart();
        this.extendedPaddingEnd = getPaddingEnd();
        AnimatorTracker animatorTracker2 = new AnimatorTracker();
        final ?? r4 = new Size(this) { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
            public final /* synthetic */ ExtendedFloatingActionButton this$0;

            {
                this.this$0 = this;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                switch (i2) {
                    case 0:
                        return this.this$0.getCollapsedSize();
                    default:
                        return this.this$0.getMeasuredHeight();
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                switch (i2) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return new ViewGroup.LayoutParams(extendedFloatingActionButton.getCollapsedSize(), extendedFloatingActionButton.getCollapsedSize());
                    default:
                        return new ViewGroup.LayoutParams(-2, -2);
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                switch (i2) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
                    default:
                        return this.this$0.extendedPaddingEnd;
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                switch (i2) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
                    default:
                        return this.this$0.extendedPaddingStart;
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                switch (i2) {
                    case 0:
                        return this.this$0.getCollapsedSize();
                    default:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getMeasuredWidth() - (((extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2) * 2)) + extendedFloatingActionButton.extendedPaddingStart + extendedFloatingActionButton.extendedPaddingEnd;
                }
            }
        };
        final ?? r9 = new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.3
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                int i4 = extendedFloatingActionButton.originalHeight;
                Size size2 = r4;
                if (i4 != -1) {
                    return (i4 == 0 || i4 == -2) ? size2.getHeight() : i4;
                }
                if (!(extendedFloatingActionButton.getParent() instanceof View)) {
                    return size2.getHeight();
                }
                View view = (View) extendedFloatingActionButton.getParent();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null || layoutParams.height != -2) {
                    return (view.getHeight() - ((!(extendedFloatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton.getLayoutParams()) == null) ? 0 : marginLayoutParams.topMargin + marginLayoutParams.bottomMargin)) - (view.getPaddingBottom() + view.getPaddingTop());
                }
                return size2.getHeight();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                int i4 = ExtendedFloatingActionButton.this.originalHeight;
                if (i4 == 0) {
                    i4 = -2;
                }
                return new ViewGroup.LayoutParams(-1, i4);
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                boolean z2 = extendedFloatingActionButton.getParent() instanceof View;
                Size size2 = r4;
                if (!z2) {
                    return size2.getWidth();
                }
                View view = (View) extendedFloatingActionButton.getParent();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null || layoutParams.width != -2) {
                    return (view.getWidth() - ((!(extendedFloatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton.getLayoutParams()) == null) ? 0 : marginLayoutParams.leftMargin + marginLayoutParams.rightMargin)) - (view.getPaddingRight() + view.getPaddingLeft());
                }
                return size2.getWidth();
            }
        };
        Size size2 = new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.4
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                int i4 = ExtendedFloatingActionButton.this.originalHeight;
                return i4 == -1 ? r9.getHeight() : (i4 == 0 || i4 == -2) ? r4.getHeight() : i4;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                int i4 = extendedFloatingActionButton.originalWidth;
                if (i4 == 0) {
                    i4 = -2;
                }
                int i5 = extendedFloatingActionButton.originalHeight;
                return new ViewGroup.LayoutParams(i4, i5 != 0 ? i5 : -2);
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                int i4 = ExtendedFloatingActionButton.this.originalWidth;
                return i4 == -1 ? r9.getWidth() : (i4 == 0 || i4 == -2) ? r4.getWidth() : i4;
            }
        };
        if (i3 != 1) {
            size = i3 != 2 ? size2 : r9;
            z = true;
        } else {
            z = true;
            size = r4;
        }
        ChangeSizeStrategy changeSizeStrategy = new ChangeSizeStrategy(animatorTracker2, size, z);
        this.extendStrategy = changeSizeStrategy;
        final int i4 = 0;
        ChangeSizeStrategy changeSizeStrategy2 = new ChangeSizeStrategy(animatorTracker2, new Size(this) { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
            public final /* synthetic */ ExtendedFloatingActionButton this$0;

            {
                this.this$0 = this;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                switch (i4) {
                    case 0:
                        return this.this$0.getCollapsedSize();
                    default:
                        return this.this$0.getMeasuredHeight();
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                switch (i4) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return new ViewGroup.LayoutParams(extendedFloatingActionButton.getCollapsedSize(), extendedFloatingActionButton.getCollapsedSize());
                    default:
                        return new ViewGroup.LayoutParams(-2, -2);
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                switch (i4) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
                    default:
                        return this.this$0.extendedPaddingEnd;
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                switch (i4) {
                    case 0:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
                    default:
                        return this.this$0.extendedPaddingStart;
                }
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                switch (i4) {
                    case 0:
                        return this.this$0.getCollapsedSize();
                    default:
                        ExtendedFloatingActionButton extendedFloatingActionButton = this.this$0;
                        return (extendedFloatingActionButton.getMeasuredWidth() - (((extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2) * 2)) + extendedFloatingActionButton.extendedPaddingStart + extendedFloatingActionButton.extendedPaddingEnd;
                }
            }
        }, false);
        this.shrinkStrategy = changeSizeStrategy2;
        showStrategy.motionSpec = createFromAttribute;
        hideStrategy.motionSpec = createFromAttribute2;
        changeSizeStrategy.motionSpec = createFromAttribute3;
        changeSizeStrategy2.motionSpec = createFromAttribute4;
        obtainStyledAttributes.recycle();
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        TypedArray obtainStyledAttributes2 = context2.obtainStyledAttributes(attributeSet, R$styleable.MaterialShape, i, 2132084899);
        int resourceId = obtainStyledAttributes2.getResourceId(0, 0);
        int resourceId2 = obtainStyledAttributes2.getResourceId(1, 0);
        obtainStyledAttributes2.recycle();
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context2, resourceId, resourceId2, relativeCornerSize).build());
        this.originalTextCsl = getTextColors();
    }

    @Override // android.widget.TextView
    public final void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        this.originalTextCsl = getTextColors();
    }
}
