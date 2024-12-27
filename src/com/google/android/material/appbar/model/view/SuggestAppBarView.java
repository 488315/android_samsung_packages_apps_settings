package com.google.android.material.appbar.model.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
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

import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.ButtonListModel;
import com.google.android.material.appbar.model.ButtonModel;
import com.google.android.material.appbar.model.ButtonStyle;
import com.google.android.material.appbar.model.SuggestAppBarModel;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000x\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010!\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\b\u0017\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n"
                + "\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\b\u0010"
                + " \u001a\u00020!H\u0002J\u0018\u0010\"\u001a\u00020\u000f2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020&2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u0012\u0010(\u001a\u0004\u0018\u00010)2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\b\u0010*\u001a\u00020!H\u0016J\u000e\u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020-J\u0010\u0010.\u001a\u00020!2\b\u0010/\u001a\u0004\u0018\u000100J\u0016\u00101\u001a\u00020!2\u000e\u0010\u0018\u001a\n"
                + "\u0012\u0006\b\u0001\u0012\u00020\u00000\u0019J\u0010\u00102\u001a\u00020!2\b\u00103\u001a\u0004\u0018\u000104J\u0010\u00105\u001a\u00020!2\u0006\u0010\u0002\u001a\u00020\u0003H\u0016R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\t\u0010\n"
                + "\"\u0004\b\u000b\u0010\fR\u0017\u0010\r"
                + "\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n"
                + "\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0018\u001a\n"
                + "\u0012\u0006\b\u0001\u0012\u00020\u00000\u0019X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u001c\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0084\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001f¨\u00066"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarView;",
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
            "buttons",
            ApnSettings.MVNO_NONE,
            "Landroid/widget/Button;",
            "getButtons",
            "()Ljava/util/List;",
            "close",
            "Landroid/widget/ImageButton;",
            "getClose",
            "()Landroid/widget/ImageButton;",
            "setClose",
            "(Landroid/widget/ImageButton;)V",
            "model",
            "Lcom/google/android/material/appbar/model/SuggestAppBarModel;",
            "titleView",
            "Landroid/widget/TextView;",
            "getTitleView",
            "()Landroid/widget/TextView;",
            "setTitleView",
            "(Landroid/widget/TextView;)V",
            "addMargin",
            ApnSettings.MVNO_NONE,
            "generateButton",
            "buttonModel",
            "Lcom/google/android/material/appbar/model/ButtonModel;",
            "style",
            ApnSettings.MVNO_NONE,
            "getAppBarSuggestTitleColor",
            "getCloseDrawable",
            "Landroid/graphics/drawable/Drawable;",
            "inflate",
            "setButtonModules",
            "buttonModels",
            "Lcom/google/android/material/appbar/model/ButtonListModel;",
            "setCloseClickListener",
            "closeClickListener",
            "Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;",
            "setModel",
            "setTitle",
            UniversalCredentialUtil.AGENT_TITLE,
            ApnSettings.MVNO_NONE,
            "updateResource",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class SuggestAppBarView extends AppBarView {
    private ViewGroup bottomLayout;
    private final List<Button> buttons;
    private ImageButton close;
    private SuggestAppBarModel<? extends SuggestAppBarView> model;
    private TextView titleView;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SuggestAppBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    private final void addMargin() {
        View view = new View(getContext());
        view.setLayoutParams(
                new ViewGroup.LayoutParams(
                        view.getResources()
                                .getDimensionPixelOffset(R.dimen.sesl_appbar_button_side_margin),
                        -1));
        ViewGroup viewGroup = this.bottomLayout;
        if (viewGroup != null) {
            viewGroup.addView(view);
        }
    }

    private final Button generateButton(ButtonModel buttonModel, int style) {
        Button button = new Button(getContext(), null, 0, style);
        button.setText(buttonModel.text);
        String str = buttonModel.contentDescription;
        if (str != null) {
            button.setContentDescription(str);
        }
        button.setOnClickListener(
                new SuggestAppBarView$$ExternalSyntheticLambda0(buttonModel, this, 0));
        return button;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void generateButton$lambda$9$lambda$8(
            ButtonModel buttonModel, SuggestAppBarView this$0, View it) {
        Intrinsics.checkNotNullParameter(buttonModel, "$buttonModel");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        AppBarModel.OnClickListener onClickListener = buttonModel.clickListener;
        if (onClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (this$0.model != null) {
                onClickListener.onClick();
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("model");
                throw null;
            }
        }
    }

    private final int getAppBarSuggestTitleColor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean isDefaultTheme = SeslMisc.isDefaultTheme(context);
        int i = R.color.sesl_appbar_suggest_title;
        if (isDefaultTheme) {
            if (!SeslMisc.isLightTheme(context)) {
                i = R.color.sesl_appbar_suggest_title_dark;
            }
        } else if (!SeslMisc.isLightTheme(context)) {
            i = R.color.sesl_appbar_suggest_title_dark_for_theme;
        }
        return context.getColor(i);
    }

    private final Drawable getCloseDrawable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getDrawable(
                SeslMisc.isDefaultTheme(context)
                        ? SeslMisc.isLightTheme(context)
                                ? R.drawable.sesl_close_button_recoil_background
                                : R.drawable.sesl_close_button_recoil_background_dark
                        : SeslMisc.isLightTheme(context)
                                ? R.drawable.sesl_close_button_recoil_background_for_theme
                                : R.drawable.sesl_close_button_recoil_background_dark_for_theme);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void setCloseClickListener$lambda$4$lambda$3(
            AppBarModel.OnClickListener onClickListener, SuggestAppBarView this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (onClickListener != null) {
            Intrinsics.checkNotNullExpressionValue(it, "it");
            if (this$0.model != null) {
                onClickListener.onClick();
            } else {
                Intrinsics.throwUninitializedPropertyAccessException("model");
                throw null;
            }
        }
    }

    public final ViewGroup getBottomLayout() {
        return this.bottomLayout;
    }

    public final List<Button> getButtons() {
        return this.buttons;
    }

    public final ImageButton getClose() {
        return this.close;
    }

    public final TextView getTitleView() {
        return this.titleView;
    }

    public void inflate() {
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(R.layout.sesl_app_bar_suggest, (ViewGroup) this, false);
        ImageButton imageButton = null;
        ViewGroup viewGroup = inflate instanceof ViewGroup ? (ViewGroup) inflate : null;
        if (viewGroup == null) {
            return;
        }
        this.titleView = (TextView) viewGroup.findViewById(R.id.suggest_app_bar_title);
        ImageButton imageButton2 = (ImageButton) viewGroup.findViewById(R.id.suggest_app_bar_close);
        if (imageButton2 != null) {
            SeslViewReflector.semSetHoverPopupType(
                    imageButton2, SeslHoverPopupWindowReflector.getField_TYPE_NONE());
            imageButton = imageButton2;
        }
        this.close = imageButton;
        this.bottomLayout = (ViewGroup) viewGroup.findViewById(R.id.suggest_app_bar_bottom_layout);
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        updateResource(context);
        addView(viewGroup);
    }

    public final void setBottomLayout(ViewGroup viewGroup) {
        this.bottomLayout = viewGroup;
    }

    public final void setButtonModules(ButtonListModel buttonModels) {
        Intrinsics.checkNotNullParameter(buttonModels, "buttonModels");
        ViewGroup viewGroup = this.bottomLayout;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        this.buttons.clear();
        List list = buttonModels.buttonModels;
        int i = 0;
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            ButtonModel buttonModel = (ButtonModel) obj;
            boolean isLightTheme = SeslMisc.isLightTheme(getContext());
            ButtonStyle buttonStyle = buttonModels.buttonStyle;
            Button generateButton =
                    generateButton(
                            buttonModel,
                            isLightTheme ? buttonStyle.defStyleRes : buttonStyle.defStyleResDark);
            generateButton.setMaxWidth(
                    generateButton
                            .getResources()
                            .getDimensionPixelSize(
                                    list.size() > 1
                                            ? R.dimen.sesl_appbar_button_max_width
                                            : R.dimen.sesl_appbar_button_max_width_multi));
            if (i != 0) {
                addMargin();
            }
            this.buttons.add(generateButton);
            ViewGroup viewGroup2 = this.bottomLayout;
            if (viewGroup2 != null) {
                viewGroup2.addView(generateButton);
            }
            i = i2;
        }
    }

    public final void setClose(ImageButton imageButton) {
        this.close = imageButton;
    }

    public final void setCloseClickListener(AppBarModel.OnClickListener closeClickListener) {
        ImageButton imageButton = this.close;
        if (imageButton != null) {
            imageButton.setVisibility(closeClickListener == null ? 8 : 0);
            imageButton.setOnClickListener(
                    new SuggestAppBarView$$ExternalSyntheticLambda0(closeClickListener, this, 1));
        }
    }

    public final void setModel(SuggestAppBarModel<? extends SuggestAppBarView> model) {
        Intrinsics.checkNotNullParameter(model, "model");
        this.model = model;
    }

    public final void setTitle(String title) {
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setText(title);
            textView.setVisibility(TextUtils.isEmpty(title) ? 8 : 0);
        }
    }

    public final void setTitleView(TextView textView) {
        this.titleView = textView;
    }

    @Override // com.google.android.material.appbar.model.view.AppBarView
    public void updateResource(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SeslMisc.isLightTheme(context);
        TextView textView = this.titleView;
        if (textView != null) {
            textView.setTextColor(getAppBarSuggestTitleColor(context));
        }
        ImageButton imageButton = this.close;
        if (imageButton != null) {
            String string =
                    imageButton.getResources().getString(R.string.sesl_appbar_suggest_dismiss);
            Intrinsics.checkNotNullExpressionValue(
                    string, "resources.getString(andr…l_appbar_suggest_dismiss)");
            imageButton.setTooltipText(string);
            imageButton.setContentDescription(string);
            imageButton.setBackground(getCloseDrawable(context));
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SuggestAppBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.buttons = new ArrayList();
        inflate();
    }

    public /* synthetic */ SuggestAppBarView(
            Context context,
            AttributeSet attributeSet,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }
}
