package com.google.android.material.appbar.model.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.SeslIndicator;
import androidx.core.view.ViewGroupKt;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000Y\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\t\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005*\u0001\u0016\b'\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0018\u001a\u00020\u0019H\u0004J\u000e\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u001cJ\u0010\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001cH\u0002J\u0018\u0010\u001f\u001a\u00020\u00192\u0006\u0010"
                + " \u001a\u00020!2\u0006\u0010\u001e\u001a\u00020\u001cH\u0002J\u000e\u0010\"\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u001cJ\u0010\u0010$\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001cH&J\u0016\u0010$\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020\u0014R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\r"
                + "\u001a\u00020\bX\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u000e\u001a\u00020\n"
                + "X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u0016\u0010\u000f\u001a\n"
                + " \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u0016\u0010\u0012\u001a\n"
                + " \u0011*\u0004\u0018\u00010\u00100\u0010X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0004\n"
                + "\u0002\u0010\u0017¨\u0006&"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/BasicViewPagerAppBarView;",
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "context",
            "Landroid/content/Context;",
            "attrs",
            "Landroid/util/AttributeSet;",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "deleteAlphaAnimator",
            "Landroid/animation/ValueAnimator;",
            "deleteAlphaDuration",
            ApnSettings.MVNO_NONE,
            "deleteAnimator",
            "Landroid/animation/AnimatorSet;",
            "deleteScaleAnimator",
            "deleteScaleDuration",
            "deleteScaleX",
            "Landroid/animation/PropertyValuesHolder;",
            "kotlin.jvm.PlatformType",
            "deleteScaleY",
            "isDeleteAnimatorRunning",
            ApnSettings.MVNO_NONE,
            "pageChangeCallback",
            "com/google/android/material/appbar/model/view/BasicViewPagerAppBarView$pageChangeCallback$1",
            "Lcom/google/android/material/appbar/model/view/BasicViewPagerAppBarView$pageChangeCallback$1;",
            "addIndicator",
            ApnSettings.MVNO_NONE,
            "initIndicator",
            "count",
            ApnSettings.MVNO_NONE,
            "internalRemoveItem",
            "index",
            "moveNextAndRemove",
            "viewPager",
            "Landroidx/viewpager2/widget/ViewPager2;",
            "removeIndicator",
            "position",
            "removeItem",
            "animate",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public abstract class BasicViewPagerAppBarView extends ViewPagerAppBarView {
    private final ValueAnimator deleteAlphaAnimator;
    private final long deleteAlphaDuration;
    private AnimatorSet deleteAnimator;
    private ValueAnimator deleteScaleAnimator;
    private final long deleteScaleDuration;
    private final PropertyValuesHolder deleteScaleX;
    private final PropertyValuesHolder deleteScaleY;
    private boolean isDeleteAnimatorRunning;
    private final BasicViewPagerAppBarView$pageChangeCallback$1 pageChangeCallback;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public BasicViewPagerAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void internalRemoveItem(int index) {
        removeItem(index);
        removeIndicator(index);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void moveNextAndRemove(ViewPager2 viewPager, final int index) {
        RecyclerView.Adapter adapter = viewPager.mRecyclerView.getAdapter();
        if (adapter == null || index < 0 || index >= adapter.getItemCount()) {
            return;
        }
        if (index != viewPager.mCurrentItem) {
            removeItem(index);
            return;
        }
        int itemCount = adapter.getItemCount();
        int i = index == itemCount + (-1) ? index - 1 : index < itemCount ? index + 1 : index;
        this.isDeleteAnimatorRunning = true;
        viewPager.setCurrentItem(i, true);
        viewPager.postDelayed(
                new Runnable() { // from class:
                                 // com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BasicViewPagerAppBarView.moveNextAndRemove$lambda$11$lambda$10(
                                BasicViewPagerAppBarView.this, index);
                    }
                },
                250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void moveNextAndRemove$lambda$11$lambda$10(
            BasicViewPagerAppBarView this$0, int i) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.isDeleteAnimatorRunning = false;
        this$0.removeItem(i);
    }

    public final void addIndicator() {
        SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            indicator.addIndicator();
        }
    }

    public final void initIndicator(int count) {
        if (count > 1) {
            for (int i = 0; i < count; i++) {
                addIndicator();
            }
        }
        ViewPager2 viewpager = getViewpager();
        if (viewpager != null) {
            viewpager.registerOnPageChangeCallback(this.pageChangeCallback);
        }
    }

    public final void removeIndicator(int position) {
        RecyclerView.Adapter adapter;
        SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            indicator.removeIndicator(position);
            ViewPager2 viewpager = getViewpager();
            if (viewpager == null
                    || (adapter = viewpager.mRecyclerView.getAdapter()) == null
                    || adapter.getItemCount() != 1) {
                return;
            }
            indicator.removeIndicator(position);
        }
    }

    public abstract void removeItem(int index);

    public final void removeItem(final int index, boolean animate) {
        RecyclerView.Adapter adapter;
        RecyclerView.ViewHolder findViewHolderForAdapterPosition;
        View view;
        if (!animate) {
            internalRemoveItem(index);
            return;
        }
        final ViewPager2 viewpager = getViewpager();
        if (viewpager == null
                || (adapter = viewpager.mRecyclerView.getAdapter()) == null
                || index < 0
                || index >= adapter.getItemCount()
                || viewpager.getChildCount() < 0) {
            return;
        }
        View view2 = ViewGroupKt.get(viewpager);
        RecyclerView recyclerView = view2 instanceof RecyclerView ? (RecyclerView) view2 : null;
        if (recyclerView == null
                || (findViewHolderForAdapterPosition =
                                recyclerView.findViewHolderForAdapterPosition(index))
                        == null
                || (view = findViewHolderForAdapterPosition.itemView) == null) {
            internalRemoveItem(index);
            return;
        }
        if (this.deleteAnimator == null) {
            if (this.deleteScaleAnimator == null) {
                ObjectAnimator ofPropertyValuesHolder =
                        ObjectAnimator.ofPropertyValuesHolder(
                                view, this.deleteScaleX, this.deleteScaleY);
                ofPropertyValuesHolder.setDuration(this.deleteScaleDuration);
                ofPropertyValuesHolder.setInterpolator(
                        AnimationUtils.loadInterpolator(
                                getContext(), R.interpolator.sesl_interpolator_22_25_0_1));
                this.deleteScaleAnimator = ofPropertyValuesHolder;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator[] valueAnimatorArr = new ValueAnimator[2];
            ValueAnimator valueAnimator = this.deleteScaleAnimator;
            if (valueAnimator == null) {
                Intrinsics.throwUninitializedPropertyAccessException("deleteScaleAnimator");
                throw null;
            }
            valueAnimatorArr[0] = valueAnimator;
            valueAnimatorArr[1] = this.deleteAlphaAnimator;
            animatorSet.playTogether(
                    CollectionsKt__CollectionsKt.listOf((Object[]) valueAnimatorArr));
            this.deleteAnimator = animatorSet;
        }
        ValueAnimator valueAnimator2 = this.deleteAlphaAnimator;
        valueAnimator2.removeAllListeners();
        valueAnimator2.addListener(
                new Animator
                        .AnimatorListener() { // from class:
                                              // com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$removeItem$1$1$3$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                        BasicViewPagerAppBarView.this.moveNextAndRemove(viewpager, index);
                        BasicViewPagerAppBarView.this.removeIndicator(index);
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animation) {
                        Intrinsics.checkNotNullParameter(animation, "animation");
                    }
                });
        AnimatorSet animatorSet2 = this.deleteAnimator;
        if (animatorSet2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("deleteAnimator");
            throw null;
        }
        animatorSet2.setTarget(view);
        animatorSet2.start();
    }

    public /* synthetic */ BasicViewPagerAppBarView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$pageChangeCallback$1] */
    public BasicViewPagerAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.deleteScaleDuration = 350L;
        this.deleteAlphaDuration = 150L;
        this.deleteScaleX =
                PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_X, 1.0f, 0.9f);
        this.deleteScaleY =
                PropertyValuesHolder.ofFloat((Property<?, Float>) View.SCALE_Y, 1.0f, 0.9f);
        ObjectAnimator ofFloat =
                ObjectAnimator.ofFloat((Object) null, (Property<Object, Float>) View.ALPHA, 0.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(
                AnimationUtils.loadInterpolator(context, R.interpolator.sesl_interpolator_0_0_1_1));
        this.deleteAlphaAnimator = ofFloat;
        this.pageChangeCallback =
                new ViewPager2
                        .OnPageChangeCallback() { // from class:
                                                  // com.google.android.material.appbar.model.view.BasicViewPagerAppBarView$pageChangeCallback$1
                    @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
                    public final void onPageSelected(int i) {
                        boolean z;
                        SeslIndicator indicator;
                        BasicViewPagerAppBarView basicViewPagerAppBarView =
                                BasicViewPagerAppBarView.this;
                        z = basicViewPagerAppBarView.isDeleteAnimatorRunning;
                        if (z || (indicator = basicViewPagerAppBarView.getIndicator()) == null) {
                            return;
                        }
                        indicator.setSelectedPosition(i);
                    }
                };
    }
}
