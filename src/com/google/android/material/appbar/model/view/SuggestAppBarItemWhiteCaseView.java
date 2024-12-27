package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.util.SeslMisc;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000&\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\b\u0017\u0018\u00002\u00020\u0001B\r"
                + "\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\u0010\u0010"
                + "\t\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\n"
                + "\u001a\u00020\b2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\r"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarItemWhiteCaseView;",
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarItemView;",
            "context",
            "Landroid/content/Context;",
            "(Landroid/content/Context;)V",
            "getCloseDrawable",
            "Landroid/graphics/drawable/Drawable;",
            "getSuggestButtonTextColorWithWhiteCase",
            ApnSettings.MVNO_NONE,
            "getSuggestTitleWithWhiteCaseColor",
            "getViewPagerItemBackgroundWhiteWhiteCaseColor",
            "updateResource",
            ApnSettings.MVNO_NONE,
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class SuggestAppBarItemWhiteCaseView extends SuggestAppBarItemView {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuggestAppBarItemWhiteCaseView(Context context) {
        super(context, null, 2, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final Drawable getCloseDrawable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getDrawable(
                SeslMisc.isDefaultTheme(context)
                        ? SeslMisc.isLightTheme(context)
                                ? R.drawable.sesl_close_button_recoil_background_with_white_case
                                : R.drawable.sesl_close_button_recoil_background_dark
                        : SeslMisc.isLightTheme(context)
                                ? R.drawable
                                        .sesl_close_button_recoil_background_with_white_case_for_theme
                                : R.drawable.sesl_close_button_recoil_background_dark_for_theme);
    }

    private final int getSuggestButtonTextColorWithWhiteCase() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_suggest_button_text_color_with_white_case;
        if (isDefaultTheme) {
            if (!SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_suggest_button_text_color_dark;
            }
        } else if (!SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_suggest_button_text_color_dark_for_theme;
        }
        return context.getColor(i);
    }

    private final int getSuggestTitleWithWhiteCaseColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_appbar_suggest_title_with_white_case;
        if (isDefaultTheme) {
            if (!SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_appbar_suggest_title_dark;
            }
        } else if (!SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_appbar_suggest_title_dark_for_theme;
        }
        return context.getColor(i);
    }

    private final int getViewPagerItemBackgroundWhiteWhiteCaseColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_viewpager_item_background_dark;
        if (isDefaultTheme) {
            if (SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_viewpager_item_background_with_white_case;
            }
        } else if (SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_viewpager_item_background_with_white_case_for_theme;
        }
        return context.getColor(i);
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarItemView,
              // com.google.android.material.appbar.model.view.SuggestAppBarView,
              // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.updateResource(context);
        SeslMisc.isLightTheme(context);
        ViewGroup rootView = getRootView();
        if (rootView != null) {
            rootView.setBackgroundTintList(
                    ColorStateList.valueOf(getViewPagerItemBackgroundWhiteWhiteCaseColor(context)));
        }
        TextView titleView = getTitleView();
        if (titleView != null) {
            titleView.setTextColor(getSuggestTitleWithWhiteCaseColor(context));
        }
        ImageButton close = getClose();
        if (close != null) {
            close.setBackground(getCloseDrawable(context));
        }
        updateButtons(getButtons());
    }
}
