package com.samsung.android.settings.navigationbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarGesturePreference extends LayoutPreference {
    public static AlertDialog sOnehandModeDisableDialog;
    public TextView mButtonTextView;
    public LinearLayout mGestureContainer;
    public LottieAnimationView mGesturePreviewAnimation;
    public RadioButton mGestureRadioBtn;
    public TextView mGestureTextView;
    public NavigationBarOverlayInteractor mNavBarOverlayInteractor;
    public RadioButton mNavBarRadioBtn;
    public LinearLayout mNavigationBarContainer;
    public final AnonymousClass2 mOnCheckedChangeListener;
    public final AnonymousClass1 mOnClickListener;
    public final NavigationBarGesturePreference$$ExternalSyntheticLambda1 mOnKeyListener;
    public final AnonymousClass3 mOnLayoutChangeListener;
    public final NavigationBarGesturePreference$$ExternalSyntheticLambda0 mOnTouchListener;
    public final int mSelectedColor;
    public ImageView mShowNavigationBarImg;
    public boolean mTouchStatus;
    public final int mUnSelectedColor;

    /* JADX WARN: Type inference failed for: r2v4, types: [com.samsung.android.settings.navigationbar.NavigationBarGesturePreference$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.samsung.android.settings.navigationbar.NavigationBarGesturePreference$2] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.samsung.android.settings.navigationbar.NavigationBarGesturePreference$3] */
    public NavigationBarGesturePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchStatus = false;
        this.mOnTouchListener = new NavigationBarGesturePreference$$ExternalSyntheticLambda0();
        this.mOnKeyListener = new NavigationBarGesturePreference$$ExternalSyntheticLambda1();
        this.mOnClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int id = view.getId();
                        if (id == R.id.navigation_bar_container) {
                            NavigationBarGesturePreference.this.mNavBarRadioBtn.setChecked(true);
                            NavigationBarGesturePreference.this.mGestureRadioBtn.setChecked(false);
                        } else if (id == R.id.gesture_container) {
                            NavigationBarGesturePreference navigationBarGesturePreference =
                                    NavigationBarGesturePreference.this;
                            navigationBarGesturePreference.mTouchStatus = true;
                            navigationBarGesturePreference.mNavBarRadioBtn.setChecked(false);
                            NavigationBarGesturePreference.this.mGestureRadioBtn.setChecked(true);
                        }
                        NavigationBarGesturePreference.this.updateTextSelectedState();
                    }
                };
        this.mOnCheckedChangeListener =
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference.2
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        NavigationBarGesturePreference navigationBarGesturePreference =
                                NavigationBarGesturePreference.this;
                        if (navigationBarGesturePreference.mNavBarOverlayInteractor == null) {
                            navigationBarGesturePreference.mNavBarOverlayInteractor =
                                    new NavigationBarOverlayInteractor(
                                            navigationBarGesturePreference.getContext());
                        }
                        int id = compoundButton.getId();
                        if (id == R.id.navigation_bar_btn) {
                            if (z) {
                                Settings.Global.putInt(
                                        NavigationBarGesturePreference.this
                                                .getContext()
                                                .getContentResolver(),
                                        "navigation_bar_gesture_while_hidden",
                                        0);
                                NavigationBarGesturePreference.this.mNavBarOverlayInteractor
                                        .setInteractionMode(
                                                "com.android.internal.systemui.navbar.threebutton");
                                LoggingHelper.insertEventLogging(7470, 7486, 0L);
                                return;
                            }
                            return;
                        }
                        if (id == R.id.gesture_btn && z) {
                            if (Settings.System.getInt(
                                                    NavigationBarGesturePreference.this
                                                            .getContext()
                                                            .getContentResolver(),
                                                    "any_screen_enabled",
                                                    0)
                                            != 0
                                    && Settings.System.getInt(
                                                    NavigationBarGesturePreference.this
                                                            .getContext()
                                                            .getContentResolver(),
                                                    "one_handed_op_wakeup_type",
                                                    0)
                                            != 0) {
                                NavigationBarGesturePreference navigationBarGesturePreference2 =
                                        NavigationBarGesturePreference.this;
                                if (navigationBarGesturePreference2.mTouchStatus) {
                                    navigationBarGesturePreference2.showOneHandModeDisablePopup();
                                    return;
                                }
                            }
                            Settings.Global.putInt(
                                    NavigationBarGesturePreference.this
                                            .getContext()
                                            .getContentResolver(),
                                    "navigation_bar_gesture_while_hidden",
                                    1);
                            if (NavigationBarSettingsUtil.isSimplifiedGesture()
                                    && !NavigationBarSettingsUtil.isSupportLegacyFeatures(
                                            NavigationBarGesturePreference.this.getContext())) {
                                Settings.Global.putInt(
                                        NavigationBarGesturePreference.this
                                                .getContext()
                                                .getContentResolver(),
                                        "navigation_bar_gesture_detail_type",
                                        1);
                                Settings.Global.putInt(
                                        NavigationBarGesturePreference.this
                                                .getContext()
                                                .getContentResolver(),
                                        "navigation_bar_gesture_hint",
                                        1);
                            }
                            NavigationBarGesturePreference.this.mNavBarOverlayInteractor
                                    .setInteractionMode();
                            LoggingHelper.insertEventLogging(7470, 7486, 1L);
                        }
                    }
                };
        this.mOnLayoutChangeListener =
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference.3
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
                        AlertDialog alertDialog =
                                NavigationBarGesturePreference.sOnehandModeDisableDialog;
                        if (alertDialog == null || !alertDialog.isShowing()) {
                            return;
                        }
                        NavigationBarGesturePreference.sOnehandModeDisableDialog.semSetAnchor(
                                NavigationBarGesturePreference.this.mGestureContainer);
                    }
                };
        this.mSelectedColor = context.getColor(R.color.sec_widget_multi_button_selected_color);
        this.mUnSelectedColor = context.getColor(R.color.sec_widget_multi_button_unselected_color);
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mNavigationBarContainer == null) {
            LinearLayout linearLayout =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.navigation_bar_container);
            this.mNavigationBarContainer = linearLayout;
            linearLayout.setOnClickListener(this.mOnClickListener);
            this.mNavigationBarContainer.setOnTouchListener(this.mOnTouchListener);
            this.mNavigationBarContainer.setOnKeyListener(this.mOnKeyListener);
        }
        if (this.mGestureContainer == null) {
            LinearLayout linearLayout2 =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.gesture_container);
            this.mGestureContainer = linearLayout2;
            linearLayout2.setOnClickListener(this.mOnClickListener);
            this.mGestureContainer.addOnLayoutChangeListener(this.mOnLayoutChangeListener);
            this.mGestureContainer.setOnTouchListener(this.mOnTouchListener);
            this.mGestureContainer.setOnKeyListener(this.mOnKeyListener);
        }
        int navBarMode = NavigationBarSettingsUtil.getNavBarMode(getContext());
        if (Rune.supportNavigationBarForHardKey() && navBarMode == 0) {
            LinearLayout linearLayout3 =
                    (LinearLayout) preferenceViewHolder.findViewById(R.id.container);
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
            layoutParams.bottomMargin =
                    (int)
                            getContext()
                                    .getResources()
                                    .getDimension(
                                            R.dimen
                                                    .samsung_navigationbar_settings_bottom_padding_hard_key);
            linearLayout3.setLayoutParams(layoutParams);
        }
        this.mButtonTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.navigation_bar_btn_textView);
        this.mGestureTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.gesture_btn_textView);
        int measuredHeight = this.mButtonTextView.getMeasuredHeight();
        if (measuredHeight != this.mGestureTextView.getMeasuredHeight()) {
            this.mButtonTextView.setMinimumHeight(measuredHeight);
        }
        if (this.mNavBarRadioBtn == null) {
            this.mNavBarRadioBtn =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.navigation_bar_btn);
        }
        if (this.mGestureRadioBtn == null) {
            this.mGestureRadioBtn =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.gesture_btn);
        }
        if (this.mShowNavigationBarImg == null) {
            this.mShowNavigationBarImg =
                    (ImageView) preferenceViewHolder.findViewById(R.id.show_navigation_bar_img);
            if (Rune.supportNavigationBarForHardKey()) {
                this.mShowNavigationBarImg.setImageResource(
                        R.drawable.setting_show_navi_img_03_edit);
            }
        }
        if (this.mGesturePreviewAnimation == null) {
            this.mGesturePreviewAnimation =
                    (LottieAnimationView)
                            preferenceViewHolder.findViewById(
                                    R.id.navigation_bar_gesture_preview_animation);
        }
        updateRadioButtonSelectState();
        updatePreviewResources();
        RadioButton radioButton = this.mNavBarRadioBtn;
        if (radioButton != null) {
            radioButton.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        RadioButton radioButton2 = this.mGestureRadioBtn;
        if (radioButton2 != null) {
            radioButton2.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        if (KnoxUtils.isApplicationRestricted(getContext(), getKey(), "hide")) {
            this.mNavigationBarContainer.setVisibility(8);
            this.mGestureContainer.setVisibility(8);
        } else if (KnoxUtils.isApplicationRestricted(getContext(), getKey(), "grayout")) {
            this.mNavigationBarContainer.setEnabled(false);
            this.mNavigationBarContainer.setAlpha(0.4f);
            this.mGestureContainer.setEnabled(false);
            this.mGestureContainer.setAlpha(0.4f);
        }
    }

    public final void showOneHandModeDisablePopup() {
        AlertDialog create =
                new AlertDialog.Builder(getContext())
                        .setMessage(
                                getContext()
                                        .getString(
                                                R.string
                                                        .navigationbar_gesture_exclusive_function_turn_off_onehand_mode_desc))
                        .setPositiveButton(
                                getContext().getString(R.string.dialog_setting_button),
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference$$ExternalSyntheticLambda2
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        NavigationBarGesturePreference
                                                navigationBarGesturePreference =
                                                        NavigationBarGesturePreference.this;
                                        navigationBarGesturePreference.getClass();
                                        Intent intent = new Intent();
                                        intent.setAction(
                                                "com.samsung.settings.ONEHAND_OPERATION_SETTING");
                                        Bundle bundle = new Bundle();
                                        bundle.putString(
                                                ":settings:fragment_args_key", "gesture_type");
                                        intent.putExtra(":settings:show_fragment_args", bundle);
                                        navigationBarGesturePreference
                                                .getContext()
                                                .startActivity(intent);
                                    }
                                })
                        .setNegativeButton(
                                android.R.string.cancel,
                                new DialogInterface
                                        .OnClickListener() { // from class:
                                                             // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference.4
                                    @Override // android.content.DialogInterface.OnClickListener
                                    public final void onClick(
                                            DialogInterface dialogInterface, int i) {
                                        Settings.Global.putInt(
                                                NavigationBarGesturePreference.this
                                                        .getContext()
                                                        .getContentResolver(),
                                                "navigation_bar_gesture_while_hidden",
                                                0);
                                        NavigationBarGesturePreference.this
                                                .updateRadioButtonSelectState();
                                    }
                                })
                        .setOnDismissListener(
                                new DialogInterface
                                        .OnDismissListener() { // from class:
                                                               // com.samsung.android.settings.navigationbar.NavigationBarGesturePreference$$ExternalSyntheticLambda3
                                    @Override // android.content.DialogInterface.OnDismissListener
                                    public final void onDismiss(DialogInterface dialogInterface) {
                                        NavigationBarGesturePreference
                                                navigationBarGesturePreference =
                                                        NavigationBarGesturePreference.this;
                                        navigationBarGesturePreference
                                                .updateRadioButtonSelectState();
                                        navigationBarGesturePreference.mTouchStatus = false;
                                    }
                                })
                        .create();
        sOnehandModeDisableDialog = create;
        create.semSetAnchor(this.mGestureContainer);
        sOnehandModeDisableDialog.show();
    }

    public final void updatePreviewResources() {
        int[] iArr = {0, 0};
        if (Rune.supportNavigationBarForHardKey()) {
            iArr[0] = R.drawable.setting_show_navi_img_03_edit;
        } else if (Utils.isRTL(getContext())) {
            if (Settings.Global.getInt(
                            getContext().getContentResolver(), "navigationbar_key_order", 0)
                    == 1) {
                iArr[0] = R.drawable.setting_show_navi_img_02_rtl_edit;
            } else {
                iArr[0] = R.drawable.setting_show_navi_img_01_rtl_edit;
            }
        } else if (Settings.Global.getInt(
                        getContext().getContentResolver(), "navigationbar_key_order", 0)
                == 1) {
            iArr[0] = R.drawable.setting_show_navi_img_02_edit;
        } else {
            iArr[0] = R.drawable.setting_show_navi_img_01_edit;
        }
        ImageView imageView = this.mShowNavigationBarImg;
        if (imageView != null) {
            imageView.setImageResource(iArr[0]);
        }
        LottieAnimationView lottieAnimationView = this.mGesturePreviewAnimation;
        if (lottieAnimationView != null) {
            lottieAnimationView.setAnimation(
                    NavigationBarGestureSettingsUtil.getPreviewAnimationResId(getContext(), -1));
            this.mGesturePreviewAnimation.playAnimation$1();
            this.mGesturePreviewAnimation.setContentDescription(
                    getContext()
                            .getResources()
                            .getString(
                                    Settings.Global.getInt(
                                                            getContext().getContentResolver(),
                                                            "navigation_bar_gesture_detail_type",
                                                            1)
                                                    == 0
                                            ? Utils.isTablet()
                                                    ? R.string
                                                            .navigationbar_swipe_from_bottom_description_for_tablet_voice_assistant
                                                    : R.string
                                                            .navigationbar_swipe_from_bottom_description_for_voice_assistant
                                            : R.string
                                                    .navigationbar_swipe_from_side_and_bottom_description));
        }
    }

    public final void updateRadioButtonSelectState() {
        int navBarMode = NavigationBarSettingsUtil.getNavBarMode(getContext());
        RadioButton radioButton = this.mNavBarRadioBtn;
        if (radioButton != null) {
            radioButton.setChecked(navBarMode == 0);
        }
        RadioButton radioButton2 = this.mGestureRadioBtn;
        if (radioButton2 != null) {
            radioButton2.setChecked(navBarMode == 1);
        }
        updateTextSelectedState();
    }

    public final void updateTextSelectedState() {
        RadioButton radioButton = this.mNavBarRadioBtn;
        boolean isChecked = radioButton != null ? radioButton.isChecked() : true;
        this.mButtonTextView.setTextColor(isChecked ? this.mSelectedColor : this.mUnSelectedColor);
        this.mGestureTextView.setTextColor(
                !isChecked ? this.mSelectedColor : this.mUnSelectedColor);
        this.mButtonTextView.setTypeface(
                Typeface.create(this.mButtonTextView.getTypeface(), isChecked ? 600 : 400, false));
        this.mGestureTextView.setTypeface(
                Typeface.create(this.mGestureTextView.getTypeface(), isChecked ? 400 : 600, false));
    }
}
