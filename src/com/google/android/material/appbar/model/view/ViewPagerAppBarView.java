package com.google.android.material.appbar.model.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;

import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.SeslIndicator;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000F\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010"
                + " \u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\fR\u001c\u0010\r"
                + "\u001a\u0004\u0018\u00010\u000eX\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018¨\u0006!"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "Lcom/google/android/material/appbar/model/view/AppBarView;",
            "context",
            "Landroid/content/Context;",
            "attrs",
            "Landroid/util/AttributeSet;",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "bottomLayout",
            "Landroid/view/ViewGroup;",
            "getBottomLayout",
            "()Landroid/view/ViewGroup;",
            "setBottomLayout",
            "(Landroid/view/ViewGroup;)V",
            "indicator",
            "Landroidx/appcompat/widget/SeslIndicator;",
            "getIndicator",
            "()Landroidx/appcompat/widget/SeslIndicator;",
            "setIndicator",
            "(Landroidx/appcompat/widget/SeslIndicator;)V",
            "viewpager",
            "Landroidx/viewpager2/widget/ViewPager2;",
            "getViewpager",
            "()Landroidx/viewpager2/widget/ViewPager2;",
            "setViewpager",
            "(Landroidx/viewpager2/widget/ViewPager2;)V",
            "getViewPagerBackgroundColorStateList",
            "Landroid/content/res/ColorStateList;",
            "getViewPagerIndicatorOffColor",
            ApnSettings.MVNO_NONE,
            "getViewPagerIndicatorOnColor",
            "inflate",
            ApnSettings.MVNO_NONE,
            "updateResource",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class ViewPagerAppBarView extends AppBarView {
    private ViewGroup bottomLayout;
    private SeslIndicator indicator;
    private ViewPager2 viewpager;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewPagerAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final ColorStateList getViewPagerBackgroundColorStateList(Context context) {
        int i;
        Intrinsics.checkNotNullParameter(context, "context");
        if (SeslMisc.isDefaultTheme(context)) {
            i =
                    SeslMisc.isLightTheme(context)
                            ? R.color.sesl_viewpager_background
                            : R.color.sesl_viewpager_background_dark;
        } else {
            SeslMisc.isLightTheme(context);
            i = R.color.sesl_viewpager_background_for_theme;
        }
        ColorStateList valueOf = ColorStateList.valueOf(context.getColor(i));
        Intrinsics.checkNotNullExpressionValue(
                valueOf, "valueOf(\n            Ses…)\n            )\n        )");
        return valueOf;
    }

    private final int getViewPagerIndicatorOffColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getColor(
                SeslMisc.isDefaultTheme(context)
                        ? SeslMisc.isLightTheme(context)
                                ? R.color.sesl_appbar_viewpager_indicator_off
                                : R.color.sesl_appbar_viewpager_indicator_off_dark
                        : SeslMisc.isLightTheme(context)
                                ? R.color.sesl_appbar_viewpager_indicator_off_for_theme
                                : R.color.sesl_appbar_viewpager_indicator_off_dark_for_theme);
    }

    private final int getViewPagerIndicatorOnColor(Context context) {
        int i;
        Intrinsics.checkNotNullParameter(context, "context");
        if (SeslMisc.isDefaultTheme(context)) {
            SeslMisc.isLightTheme(context);
            i = R.color.sesl_appbar_viewpager_indicator_on;
        } else {
            SeslMisc.isLightTheme(context);
            i = R.color.sesl_appbar_viewpager_indicator_on_for_theme;
        }
        return context.getColor(i);
    }

    public final ViewGroup getBottomLayout() {
        return this.bottomLayout;
    }

    public final SeslIndicator getIndicator() {
        return this.indicator;
    }

    public final ViewPager2 getViewpager() {
        return this.viewpager;
    }

    public void inflate() {
        Drawable drawable;
        Drawable drawable2;
        int i;
        final int i2 = 1;
        final int i3 = 0;
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.sesl_app_bar_viewpager, (ViewGroup) this, false);
        ViewGroup viewGroup = inflate instanceof ViewGroup ? (ViewGroup) inflate : null;
        if (viewGroup == null) {
            return;
        }
        this.viewpager = (ViewPager2) viewGroup.findViewById(R.id.app_bar_viewpager);
        this.bottomLayout = (ViewGroup) viewGroup.findViewById(R.id.bottom_layout);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        final SeslIndicator seslIndicator = new SeslIndicator(context, null);
        ArrayList arrayList = new ArrayList();
        seslIndicator.indicator = arrayList;
        Drawable drawable3 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
        if (drawable3 == null || (drawable = drawable3.mutate()) == null) {
            drawable = null;
        } else {
            drawable.setTint(
                    context.getColor(
                            SeslMisc.isDefaultTheme(context)
                                    ? SeslMisc.isLightTheme(context)
                                            ? R.color.sesl_appbar_viewpager_indicator_off
                                            : R.color.sesl_appbar_viewpager_indicator_off_dark
                                    : SeslMisc.isLightTheme(context)
                                            ? R.color.sesl_appbar_viewpager_indicator_off_for_theme
                                            : R.color
                                                    .sesl_appbar_viewpager_indicator_off_dark_for_theme));
        }
        seslIndicator.defaultCircle = drawable;
        Drawable drawable4 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
        if (drawable4 == null || (drawable2 = drawable4.mutate()) == null) {
            drawable2 = null;
        } else {
            if (SeslMisc.isDefaultTheme(context)) {
                SeslMisc.isLightTheme(context);
                i = R.color.sesl_appbar_viewpager_indicator_on;
            } else {
                SeslMisc.isLightTheme(context);
                i = R.color.sesl_appbar_viewpager_indicator_on_for_theme;
            }
            drawable2.setTint(context.getColor(i));
        }
        seslIndicator.selectCircle = drawable2;
        seslIndicator.selectedPosition = -1;
        final ViewPagerAppBarView$inflate$1$1 viewPagerAppBarView$inflate$1$1 =
                new ViewPagerAppBarView$inflate$1$1(this);
        seslIndicator.itemClickListener = viewPagerAppBarView$inflate$1$1;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((SeslIndicator.PageIndicatorMarker) it.next())
                    .setOnClickListener(
                            new View
                                    .OnClickListener() { // from class:
                                                         // androidx.appcompat.widget.SeslIndicator$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    ViewPagerAppBarView$inflate$1$1 itemClickListener =
                                            ViewPagerAppBarView$inflate$1$1.this;
                                    SeslIndicator this$0 = seslIndicator;
                                    Intrinsics.checkNotNullParameter(
                                            itemClickListener, "$itemClickListener");
                                    Intrinsics.checkNotNullParameter(this$0, "this$0");
                                    List list = this$0.indicator;
                                    Intrinsics.checkNotNullParameter(list, "<this>");
                                    int indexOf = ((ArrayList) list).indexOf(view);
                                    ViewPager2 viewpager = itemClickListener.this$0.getViewpager();
                                    if (viewpager != null) {
                                        viewpager.setCurrentItem(indexOf, true);
                                    }
                                }
                            });
        }
        this.indicator = seslIndicator;
        final ViewPager2 viewPager2 = this.viewpager;
        if (viewPager2 != null) {
            viewPager2.mIsSuggestionPagingEnabled = true;
            viewPager2.mRecyclerView.setEdgeEffectEnabled(false);
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.95f).setDuration(400L);
            viewPager2.mSuggestionStartDragAnimator = duration;
            PathInterpolator pathInterpolator = ViewPager2.CONTAINER_SCALE_INTERPOLATOR;
            duration.setInterpolator(pathInterpolator);
            viewPager2.mSuggestionStartDragAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // androidx.viewpager2.widget.ViewPager2.5
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ ViewPager2 this$0;

                        public /* synthetic */ AnonymousClass5(
                                final ViewPager2 viewPager22, final int i32) {
                            r2 = i32;
                            r1 = viewPager22;
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            switch (r2) {
                                case 0:
                                    r1.mContainerScaleValue =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    ViewPager2.access$700(r1);
                                    break;
                                default:
                                    r1.mContainerScaleValue =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    ViewPager2.access$700(r1);
                                    break;
                            }
                        }
                    });
            ValueAnimator duration2 = ValueAnimator.ofFloat(0.95f, 1.0f).setDuration(400L);
            viewPager22.mSuggestionReleaseAnimator = duration2;
            duration2.setInterpolator(pathInterpolator);
            viewPager22.mSuggestionReleaseAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // androidx.viewpager2.widget.ViewPager2.5
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ ViewPager2 this$0;

                        public /* synthetic */ AnonymousClass5(
                                final ViewPager2 viewPager22, final int i22) {
                            r2 = i22;
                            r1 = viewPager22;
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            switch (r2) {
                                case 0:
                                    r1.mContainerScaleValue =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    ViewPager2.access$700(r1);
                                    break;
                                default:
                                    r1.mContainerScaleValue =
                                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                    ViewPager2.access$700(r1);
                                    break;
                            }
                        }
                    });
            if (viewPager22.mRecyclerView.getClipChildren()) {
                viewPager22.mRecyclerView.setClipChildren(false);
            }
            Drawable drawable5 =
                    viewPager22.getContext().getDrawable(R.drawable.sesl_viewpager_background);
            viewPager22.setBackground(drawable5 != null ? drawable5.mutate() : null);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        ViewGroup viewGroup2 = this.bottomLayout;
        if (viewGroup2 != null) {
            viewGroup2.addView(this.indicator, layoutParams);
        }
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "context");
        updateResource(context2);
        addView(viewGroup);
    }

    public final void setBottomLayout(ViewGroup viewGroup) {
        this.bottomLayout = viewGroup;
    }

    public final void setIndicator(SeslIndicator seslIndicator) {
        this.indicator = seslIndicator;
    }

    public final void setViewpager(ViewPager2 viewPager2) {
        this.viewpager = viewPager2;
    }

    @Override // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Drawable drawable;
        Drawable mutate;
        Intrinsics.checkNotNullParameter(context, "context");
        ViewPager2 viewPager2 = this.viewpager;
        if (viewPager2 != null) {
            viewPager2.setBackgroundTintList(getViewPagerBackgroundColorStateList(context));
        }
        SeslIndicator seslIndicator = this.indicator;
        if (seslIndicator != null) {
            Drawable drawable2 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            Drawable drawable3 = null;
            if (drawable2 == null || (drawable = drawable2.mutate()) == null) {
                drawable = null;
            } else {
                drawable.setTint(getViewPagerIndicatorOffColor(context));
            }
            seslIndicator.setDefaultCircle(drawable);
            Drawable drawable4 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            if (drawable4 != null && (mutate = drawable4.mutate()) != null) {
                mutate.setTint(getViewPagerIndicatorOnColor(context));
                drawable3 = mutate;
            }
            seslIndicator.setSelectCircle(drawable3);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewPagerAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        inflate();
    }

    public /* synthetic */ ViewPagerAppBarView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }
}
