package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.util.SeslMisc;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslHoverPopupWindowReflector;

import com.android.settings.R;

import com.google.android.material.util.MaxFontScaleRatio;
import com.google.android.material.util.SeslTextViewHelperKt;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000@\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010 \n"
                + "\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\r"
                + "\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u000eH\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0015\u001a\u00020\u00112\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\u0017H\u0004J\u0010\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0002\u001a\u00020\u0003H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\f¨\u0006\u0019"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarItemView;",
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarView;",
            "context",
            "Landroid/content/Context;",
            "attrs",
            "Landroid/util/AttributeSet;",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "rootView",
            "Landroid/view/ViewGroup;",
            "getRootView",
            "()Landroid/view/ViewGroup;",
            "setRootView",
            "(Landroid/view/ViewGroup;)V",
            "getButtonTextColor",
            ApnSettings.MVNO_NONE,
            "getSuggestButtonTextColor",
            "inflate",
            ApnSettings.MVNO_NONE,
            "updateButton",
            "button",
            "Landroid/widget/Button;",
            "updateButtons",
            "buttons",
            ApnSettings.MVNO_NONE,
            "updateResource",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class SuggestAppBarItemView extends SuggestAppBarView {
    private ViewGroup rootView;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SuggestAppBarItemView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final int getButtonTextColor() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_button_text_color;
        if (isDefaultTheme) {
            if (!SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_button_text_color_dark;
            }
        } else if (!SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_button_text_color_dark_for_theme;
        }
        return context.getColor(i);
    }

    private final int getSuggestButtonTextColor() {
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_suggest_button_text_color;
        if (isDefaultTheme) {
            if (!SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_suggest_button_text_color_dark;
            }
        } else if (!SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_suggest_button_text_color_dark_for_theme;
        }
        return context.getColor(i);
    }

    private final void updateButton(Button button) {
        button.setTextColor(getButtonTextColor());
        SeslTextViewHelperKt.checkMaxFontScale(
                button, R.dimen.sesl_appbar_button_text_size, MaxFontScaleRatio.MEDIUM);
    }

    @Override // android.view.View
    public final ViewGroup getRootView() {
        return this.rootView;
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarView
    public void inflate() {
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.sesl_app_bar_suggest_in_viewpager,
                                (ViewGroup) this,
                                false);
        ImageButton imageButton = null;
        ViewGroup viewGroup = inflate instanceof ViewGroup ? (ViewGroup) inflate : null;
        if (viewGroup == null) {
            return;
        }
        ViewGroup viewGroup2 =
                (ViewGroup) viewGroup.findViewById(R.id.sesl_appbar_suggest_in_viewpager);
        viewGroup2.setBackgroundResource(R.drawable.sesl_viewpager_item_background);
        this.rootView = viewGroup2;
        setTitleView((TextView) viewGroup.findViewById(R.id.suggest_app_bar_title));
        ImageButton imageButton2 = (ImageButton) viewGroup.findViewById(R.id.suggest_app_bar_close);
        if (imageButton2 != null) {
            SeslViewReflector.semSetHoverPopupType(
                    imageButton2, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
            imageButton = imageButton2;
        }
        setClose(imageButton);
        setBottomLayout((ViewGroup) viewGroup.findViewById(R.id.suggest_app_bar_bottom_layout));
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        updateResource(context);
        addView(viewGroup);
    }

    public final void setRootView(ViewGroup viewGroup) {
        this.rootView = viewGroup;
    }

    public final void updateButtons(List<? extends Button> buttons) {
        Intrinsics.checkNotNullParameter(buttons, "buttons");
        Iterator<T> it = buttons.iterator();
        while (it.hasNext()) {
            updateButton((Button) it.next());
        }
    }

    @Override // com.google.android.material.appbar.model.view.SuggestAppBarView,
              // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.updateResource(context);
        boolean isLightTheme = SeslMisc.isLightTheme(context);
        ViewGroup viewGroup = this.rootView;
        if (viewGroup != null) {
            viewGroup.setBackgroundTintList(
                    ColorStateList.valueOf(
                            context.getColor(
                                    isLightTheme
                                            ? R.color.sesl_viewpager_item_background
                                            : R.color.sesl_viewpager_item_background_dark)));
        }
        TextView titleView = getTitleView();
        if (titleView != null) {
            SeslTextViewHelperKt.checkMaxFontScale(
                    titleView,
                    R.dimen.sesl_appbar_suggest_title_text_size,
                    MaxFontScaleRatio.SMALL);
        }
        updateButtons(getButtons());
    }

    public /* synthetic */ SuggestAppBarItemView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuggestAppBarItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        inflate();
    }
}
