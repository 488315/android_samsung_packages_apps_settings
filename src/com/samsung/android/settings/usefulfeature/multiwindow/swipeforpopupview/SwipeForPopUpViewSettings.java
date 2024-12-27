package com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import androidx.core.widget.NestedScrollView;
import androidx.window.embedding.ActivityEmbeddingController;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.usefulfeature.multiwindow.MultiwindowSettings;
import com.samsung.android.settings.widget.PlayPauseLottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SwipeForPopUpViewSettings extends SettingsPreferenceFragment
        implements CompoundButton.OnCheckedChangeListener {
    public static final Uri[] SETTINGS_URIS = {Settings.Global.getUriFor("open_in_pop_up_view")};
    public Context mContext;
    public SwipeForPopUpViewCornerAreaPreference mCornerAreaPreference;
    public SwipeForPopUpViewCornerAreaIndicatorView mIndicatorView;
    public NestedScrollView mNestedScrollView;
    public FrameLayout mPopUpAnimationContainer;
    public PlayPauseLottieAnimationView mPopUpImage;
    public View mPopView;
    public final AnonymousClass1 mSettingsObserver =
            new ContentObserver(
                    new Handler()) { // from class:
                                     // com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview.SwipeForPopUpViewSettings.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    SwipeForPopUpViewSettings swipeForPopUpViewSettings =
                            SwipeForPopUpViewSettings.this;
                    Uri[] uriArr = SwipeForPopUpViewSettings.SETTINGS_URIS;
                    swipeForPopUpViewSettings.updateSwitchAndPreferenceState$6();
                }
            };
    public SettingsMainSwitchBar mSwitchBar;
    public WindowManager mWindowManager;

    public final View createResetView$1(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        ViewGroup viewGroup2 =
                ((Utils.isTablet()
                                        && ActivityEmbeddingController.getInstance(getActivity())
                                                .isActivityEmbedded(getActivity()))
                                || Rune.isSamsungDexMode(this.mContext))
                        ? (ViewGroup)
                                layoutInflater.inflate(
                                        R.layout.sec_swipe_for_popup_view_tablet, viewGroup)
                        : (ViewGroup)
                                layoutInflater.inflate(
                                        R.layout.sec_swipe_for_popup_view, viewGroup);
        this.mNestedScrollView =
                (NestedScrollView) viewGroup2.findViewById(R.id.nested_scroll_view);
        View inflate = layoutInflater.inflate(R.layout.sec_swipe_popup_animation, (ViewGroup) null);
        int listHorizontalPadding = Utils.getListHorizontalPadding(this.mContext);
        ViewGroup viewGroup3 =
                (ViewGroup) viewGroup2.findViewById(R.id.popup_view_preview_container);
        viewGroup3.addView(inflate);
        FrameLayout frameLayout =
                (FrameLayout) viewGroup2.findViewById(R.id.popup_view_preview_container);
        frameLayout.semSetRoundedCorners(15);
        frameLayout.semSetRoundedCornerColor(
                15, getActivity().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        if (getResources().getConfiguration().orientation != 2
                || ((Utils.isTablet()
                                && ActivityEmbeddingController.getInstance(getActivity())
                                        .isActivityEmbedded(getActivity()))
                        || Rune.isSamsungDexMode(this.mContext))) {
            inflate.setPadding(listHorizontalPadding, 0, listHorizontalPadding, 0);
        } else {
            View findViewById = viewGroup2.findViewById(R.id.popup_view_preview_parent);
            if (findViewById != null) {
                findViewById.setVisibility(0);
            }
            inflate.setPadding(listHorizontalPadding, 0, 0, 0);
        }
        ViewGroup viewGroup4 =
                (ViewGroup) viewGroup2.findViewById(R.id.popup_view_preference_container);
        if (this.mPopView.getParent() != null) {
            ((ViewGroup) this.mPopView.getParent()).removeView(this.mPopView);
        }
        viewGroup4.addView(this.mPopView);
        FrameLayout frameLayout2 =
                (FrameLayout) viewGroup3.findViewById(R.id.help_animation_popup_container);
        this.mPopUpAnimationContainer = frameLayout2;
        frameLayout2.semSetRoundedCorners(15);
        this.mPopUpAnimationContainer.semSetRoundedCornerColor(
                15, getActivity().getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mPopUpImage =
                (PlayPauseLottieAnimationView)
                        this.mPopUpAnimationContainer.findViewById(R.id.help_animation_popup);
        if (Utils.isNightMode(this.mContext)) {
            this.mPopUpImage.setAnimation("swipeforpopupview_dark.json");
            this.mPopUpImage.playAnimation$1();
        } else {
            this.mPopUpImage.setAnimation("swipeforpopupview_light.json");
            this.mPopUpImage.playAnimation$1();
        }
        return viewGroup2;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getFragmentTitleResId(Context context) {
        return R.string.sec_swpie_for_popup_view_title;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getHierarchicalParentFragment(Context context) {
        return MultiwindowSettings.class.getName();
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return Integer.parseInt("68120");
    }

    @Override // com.android.settings.SettingsPreferenceFragment
    public final NestedScrollView getNestedScrollView() {
        return this.mNestedScrollView;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment
    public final String getTopLevelPreferenceKey(Context context) {
        return "top_level_advanced_features";
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        SettingsMainSwitchBar settingsMainSwitchBar =
                ((SettingsActivity) getActivity()).mMainSwitch;
        this.mSwitchBar = settingsMainSwitchBar;
        settingsMainSwitchBar.addOnSwitchChangeListener(this);
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        Settings.Global.putInt(
                this.mContext.getContentResolver(), "open_in_pop_up_view", z ? 1 : 0);
        SwipeForPopUpViewCornerAreaPreference swipeForPopUpViewCornerAreaPreference =
                this.mCornerAreaPreference;
        swipeForPopUpViewCornerAreaPreference.mIsSwitchEnable = z;
        SeekBar seekBar = swipeForPopUpViewCornerAreaPreference.mCornerAreaSeekBar;
        if (seekBar != null) {
            if (z) {
                seekBar.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mCornerAreaTitle.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mLeftText.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mRightText.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.updateProgress$3();
            } else {
                seekBar.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mCornerAreaTitle.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mLeftText.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mRightText.setAlpha(0.4f);
            }
            swipeForPopUpViewCornerAreaPreference.mCornerAreaSeekBar.setEnabled(
                    swipeForPopUpViewCornerAreaPreference.mIsSwitchEnable);
        }
        Settings.Global.putInt(
                swipeForPopUpViewCornerAreaPreference.mContext.getContentResolver(),
                "freeform_corner_gesture_level",
                Settings.Global.getInt(
                        swipeForPopUpViewCornerAreaPreference.mContext.getContentResolver(),
                        "freeform_corner_gesture_level",
                        2));
        LoggingHelper.insertEventLogging(Integer.parseInt("68120"), 68121, z);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        getPreferenceScreen().removeAll();
        addPreferencesFromResource(R.xml.sec_swipe_for_popup_view_settings);
        setAutoRemoveInsetCategory(false);
        SwipeForPopUpViewCornerAreaPreference swipeForPopUpViewCornerAreaPreference =
                (SwipeForPopUpViewCornerAreaPreference)
                        findPreference("swipe_for_popup_corner_area");
        this.mCornerAreaPreference = swipeForPopUpViewCornerAreaPreference;
        if (swipeForPopUpViewCornerAreaPreference != null) {
            swipeForPopUpViewCornerAreaPreference.mIndicatorView = this.mIndicatorView;
        }
        updateSwitchAndPreferenceState$6();
        createResetView$1(
                LayoutInflater.from(this.mContext),
                (ViewGroup) getActivity().findViewById(R.id.main_content));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mIndicatorView = new SwipeForPopUpViewCornerAreaIndicatorView(this.mContext);
        this.mWindowManager = (WindowManager) getActivity().getSystemService("window");
        addPreferencesFromResource(R.xml.sec_swipe_for_popup_view_settings);
        setAutoRemoveInsetCategory(false);
        SwipeForPopUpViewCornerAreaPreference swipeForPopUpViewCornerAreaPreference =
                (SwipeForPopUpViewCornerAreaPreference)
                        findPreference("swipe_for_popup_corner_area");
        this.mCornerAreaPreference = swipeForPopUpViewCornerAreaPreference;
        if (swipeForPopUpViewCornerAreaPreference != null) {
            swipeForPopUpViewCornerAreaPreference.mIndicatorView = this.mIndicatorView;
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.mPopView = super.onCreateView(layoutInflater, viewGroup, bundle);
        this.mContext = getContext();
        return createResetView$1(layoutInflater, null);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(this);
            this.mSwitchBar.hide();
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
        this.mWindowManager.removeView(this.mIndicatorView);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        WindowManager windowManager = this.mWindowManager;
        SwipeForPopUpViewCornerAreaIndicatorView swipeForPopUpViewCornerAreaIndicatorView =
                this.mIndicatorView;
        windowManager.addView(
                swipeForPopUpViewCornerAreaIndicatorView,
                swipeForPopUpViewCornerAreaIndicatorView.getLayoutParams());
        updateSwitchAndPreferenceState$6();
        for (Uri uri : SETTINGS_URIS) {
            this.mContext
                    .getContentResolver()
                    .registerContentObserver(uri, false, this.mSettingsObserver);
        }
    }

    public final void updateSwitchAndPreferenceState$6() {
        boolean z = Settings.Global.getInt(getContentResolver(), "open_in_pop_up_view", 0) != 0;
        SettingsMainSwitchBar settingsMainSwitchBar = this.mSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.setChecked(z);
            this.mSwitchBar.show();
        }
        SwipeForPopUpViewCornerAreaPreference swipeForPopUpViewCornerAreaPreference =
                this.mCornerAreaPreference;
        swipeForPopUpViewCornerAreaPreference.mIsSwitchEnable = z;
        SeekBar seekBar = swipeForPopUpViewCornerAreaPreference.mCornerAreaSeekBar;
        if (seekBar != null) {
            if (z) {
                seekBar.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mCornerAreaTitle.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mLeftText.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.mRightText.setAlpha(1.0f);
                swipeForPopUpViewCornerAreaPreference.updateProgress$3();
            } else {
                seekBar.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mCornerAreaTitle.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mLeftText.setAlpha(0.4f);
                swipeForPopUpViewCornerAreaPreference.mRightText.setAlpha(0.4f);
            }
            swipeForPopUpViewCornerAreaPreference.mCornerAreaSeekBar.setEnabled(
                    swipeForPopUpViewCornerAreaPreference.mIsSwitchEnable);
        }
        Settings.Global.putInt(
                swipeForPopUpViewCornerAreaPreference.mContext.getContentResolver(),
                "freeform_corner_gesture_level",
                Settings.Global.getInt(
                        swipeForPopUpViewCornerAreaPreference.mContext.getContentResolver(),
                        "freeform_corner_gesture_level",
                        2));
    }
}
