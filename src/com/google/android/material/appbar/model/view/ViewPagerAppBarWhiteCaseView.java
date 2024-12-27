package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.util.SeslMisc;
import androidx.appcompat.widget.SeslIndicator;
import androidx.viewpager2.widget.ViewPager2;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000,\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\b\u0017\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u000b\u001a\u00020\n"
                + "2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\f\u001a\u00020\r"
                + "2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u000e"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarWhiteCaseView;",
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "context",
            "Landroid/content/Context;",
            "attrs",
            "Landroid/util/AttributeSet;",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "getViewPagerBackgroundColorStateList",
            "Landroid/content/res/ColorStateList;",
            "getViewPagerIndicatorOffWithWhiteCaseColor",
            ApnSettings.MVNO_NONE,
            "getViewPagerIndicatorOnWithWhiteCaseColor",
            "updateResource",
            ApnSettings.MVNO_NONE,
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class ViewPagerAppBarWhiteCaseView extends ViewPagerAppBarView {
    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ViewPagerAppBarWhiteCaseView(Context context) {
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

    private final int getViewPagerIndicatorOffWithWhiteCaseColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getColor(
                SeslMisc.isDefaultTheme(context)
                        ? SeslMisc.isLightTheme(context)
                                ? R.color.sesl_appbar_viewpager_indicator_off_with_white_case
                                : R.color.sesl_appbar_viewpager_indicator_off_dark
                        : SeslMisc.isLightTheme(context)
                                ? R.color
                                        .sesl_appbar_viewpager_indicator_off_with_white_case_for_theme
                                : R.color.sesl_appbar_viewpager_indicator_off_dark_for_theme);
    }

    private final int getViewPagerIndicatorOnWithWhiteCaseColor(Context context) {
        int i;
        Intrinsics.checkNotNullParameter(context, "context");
        if (SeslMisc.isDefaultTheme(context)) {
            SeslMisc.isLightTheme(context);
            i = R.color.sesl_appbar_viewpager_indicator_on_with_white_case;
        } else {
            SeslMisc.isLightTheme(context);
            i = R.color.sesl_appbar_viewpager_indicator_on_with_white_case_for_theme;
        }
        return context.getColor(i);
    }

    @Override // com.google.android.material.appbar.model.view.ViewPagerAppBarView,
              // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Drawable drawable;
        Drawable mutate;
        Intrinsics.checkNotNullParameter(context, "context");
        ViewPager2 viewpager = getViewpager();
        if (viewpager != null) {
            viewpager.setBackgroundTintList(getViewPagerBackgroundColorStateList(context));
        }
        SeslIndicator indicator = getIndicator();
        if (indicator != null) {
            Drawable drawable2 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            Drawable drawable3 = null;
            if (drawable2 == null || (drawable = drawable2.mutate()) == null) {
                drawable = null;
            } else {
                drawable.setTint(getViewPagerIndicatorOffWithWhiteCaseColor(context));
            }
            indicator.setDefaultCircle(drawable);
            Drawable drawable4 = context.getDrawable(R.drawable.sesl_viewpager_indicator_on_off);
            if (drawable4 != null && (mutate = drawable4.mutate()) != null) {
                mutate.setTint(getViewPagerIndicatorOnWithWhiteCaseColor(context));
                drawable3 = mutate;
            }
            indicator.setSelectCircle(drawable3);
        }
    }

    public /* synthetic */ ViewPagerAppBarWhiteCaseView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ViewPagerAppBarWhiteCaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
    }
}
