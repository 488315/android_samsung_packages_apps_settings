package com.samsung.android.settings.navigationbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.widget.LayoutPreference;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarGestureDetailedPreference extends LayoutPreference {
    public int mA11yIdx;
    public int mAnimationIdx;
    public final int[] mAnimationValues;
    public final NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda1 mAnimatorListener;
    public final int[] mBottomAnimationDescs;
    public final Handler mHandler;
    public boolean mIsWaiting;
    public int mLastAnimationIdx;
    public NavigationBarOverlayInteractor mNavBarOverlayInteractor;
    public final NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda3
            mOnCheckedChangeListener;
    public final NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda2 mOnclickListener;
    public NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0 mPreviewRunnable;
    public final int[][] mSideAndBottomAnimationDescs;
    public LottieAnimationView mSwipeBottomAnimator;
    public RadioButton mSwipeFromBottom;
    public RadioButton mSwipeFromBottomAndSide;
    public LinearLayout mSwipeFromBottomAndSideContainer;
    public LinearLayout mSwipeFromBottomContainer;
    public LottieAnimationView mSwipeSideAndBottomAnimator;

    /* JADX WARN: Type inference failed for: r3v11, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r3v13, types: [com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda3] */
    public NavigationBarGestureDetailedPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mBottomAnimationDescs =
                new int[] {
                    R.string.navigationbar_swipe_from_bottom_description,
                    R.string.navigationbar_swipe_from_bottom_description_for_voice_assistant,
                    R.string.navigationbar_swipe_from_bottom_description_for_tablet,
                    R.string.navigationbar_swipe_from_bottom_description_for_tablet_voice_assistant
                };
        this.mSideAndBottomAnimationDescs =
                new int[][] {
                    new int[] {
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_1,
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_1_a11y
                    },
                    new int[] {
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_2,
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_2_a11y
                    },
                    new int[] {
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_3,
                        R.string.navigationbar_swipe_from_side_and_bottom_desc_3_a11y
                    }
                };
        this.mAnimationValues = new int[] {155, 395, 666};
        this.mIsWaiting = false;
        this.mPreviewRunnable = null;
        this.mAnimatorListener =
                new ValueAnimator
                        .AnimatorUpdateListener() { // from class:
                                                    // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        NavigationBarGestureDetailedPreference
                                navigationBarGestureDetailedPreference =
                                        NavigationBarGestureDetailedPreference.this;
                        LottieAnimationView lottieAnimationView =
                                navigationBarGestureDetailedPreference.mSwipeSideAndBottomAnimator;
                        int i = (int) lottieAnimationView.lottieDrawable.animator.frame;
                        int[] iArr = navigationBarGestureDetailedPreference.mAnimationValues;
                        if (i <= iArr[0] || i >= iArr[2]) {
                            navigationBarGestureDetailedPreference.mAnimationIdx = 0;
                        } else if (i <= iArr[1]) {
                            navigationBarGestureDetailedPreference.mAnimationIdx = 1;
                        } else {
                            navigationBarGestureDetailedPreference.mAnimationIdx = 2;
                        }
                        int i2 = navigationBarGestureDetailedPreference.mAnimationIdx;
                        int i3 = navigationBarGestureDetailedPreference.mLastAnimationIdx;
                        if (i2 != i3) {
                            if (iArr[i3] <= i) {
                                lottieAnimationView.pauseAnimation();
                                navigationBarGestureDetailedPreference.mIsWaiting = true;
                                navigationBarGestureDetailedPreference.mHandler
                                        .removeCallbacksAndMessages(null);
                                navigationBarGestureDetailedPreference.mHandler.postDelayed(
                                        new NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda4(
                                                navigationBarGestureDetailedPreference, 0),
                                        2000L);
                            } else {
                                navigationBarGestureDetailedPreference.updateDescription(
                                        i2, navigationBarGestureDetailedPreference.mA11yIdx);
                            }
                        }
                        navigationBarGestureDetailedPreference.mLastAnimationIdx =
                                navigationBarGestureDetailedPreference.mAnimationIdx;
                    }
                };
        this.mOnclickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NavigationBarGestureDetailedPreference
                                navigationBarGestureDetailedPreference =
                                        NavigationBarGestureDetailedPreference.this;
                        navigationBarGestureDetailedPreference.getClass();
                        int id = view.getId();
                        if (id == R.id.swipe_from_bottom_container) {
                            navigationBarGestureDetailedPreference.mSwipeFromBottom.setChecked(
                                    true);
                            navigationBarGestureDetailedPreference.mSwipeFromBottomAndSide
                                    .setChecked(false);
                        } else if (id == R.id.swipe_from_side_and_bottom_container) {
                            navigationBarGestureDetailedPreference.mSwipeFromBottom.setChecked(
                                    false);
                            navigationBarGestureDetailedPreference.mSwipeFromBottomAndSide
                                    .setChecked(true);
                        }
                    }
                };
        this.mOnCheckedChangeListener =
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.navigationbar.NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda3
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        NavigationBarGestureDetailedPreference
                                navigationBarGestureDetailedPreference =
                                        NavigationBarGestureDetailedPreference.this;
                        navigationBarGestureDetailedPreference.getClass();
                        NavigationBarSettingsUtil.sIsForceUpdate = true;
                        int id = compoundButton.getId();
                        if (id == R.id.swipe_from_bottom) {
                            if (z) {
                                Settings.Global.putInt(
                                        navigationBarGestureDetailedPreference
                                                .getContext()
                                                .getContentResolver(),
                                        "navigation_bar_gesture_detail_type",
                                        0);
                                navigationBarGestureDetailedPreference.mNavBarOverlayInteractor
                                        .setInteractionMode();
                                LoggingHelper.insertEventLogging(7470, 7490, 0L);
                                return;
                            }
                            return;
                        }
                        if (id == R.id.swipe_from_side_and_bottom && z) {
                            Settings.Global.putInt(
                                    navigationBarGestureDetailedPreference
                                            .getContext()
                                            .getContentResolver(),
                                    "navigation_bar_gesture_detail_type",
                                    1);
                            navigationBarGestureDetailedPreference.mNavBarOverlayInteractor
                                    .setInteractionMode();
                            LoggingHelper.insertEventLogging(7470, 7490, 1L);
                        }
                    }
                };
    }

    @Override // com.android.settingslib.widget.LayoutPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mNavBarOverlayInteractor == null) {
            this.mNavBarOverlayInteractor = new NavigationBarOverlayInteractor(getContext());
        }
        int i =
                Settings.Global.getInt(
                        getContext().getContentResolver(), "navigation_bar_gesture_detail_type", 1);
        if (this.mSwipeFromBottomContainer == null) {
            LinearLayout linearLayout =
                    (LinearLayout)
                            preferenceViewHolder.findViewById(R.id.swipe_from_bottom_container);
            this.mSwipeFromBottomContainer = linearLayout;
            linearLayout.setOnClickListener(this.mOnclickListener);
        }
        if (this.mSwipeFromBottomAndSideContainer == null) {
            LinearLayout linearLayout2 =
                    (LinearLayout)
                            preferenceViewHolder.findViewById(
                                    R.id.swipe_from_side_and_bottom_container);
            this.mSwipeFromBottomAndSideContainer = linearLayout2;
            linearLayout2.setOnClickListener(this.mOnclickListener);
            preferenceViewHolder
                    .findViewById(R.id.sec_navigationbar_gesture_side_and_bottom)
                    .setContentDescription(
                            getContext()
                                    .getResources()
                                    .getString(
                                            R.string
                                                    .navigationbar_swipe_from_side_and_bottom_description));
        }
        if (this.mSwipeFromBottom == null) {
            RadioButton radioButton =
                    (RadioButton) preferenceViewHolder.findViewById(R.id.swipe_from_bottom);
            this.mSwipeFromBottom = radioButton;
            if (i == 0) {
                radioButton.setChecked(true);
            }
            this.mSwipeFromBottom.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        if (this.mSwipeFromBottomAndSide == null) {
            RadioButton radioButton2 =
                    (RadioButton)
                            preferenceViewHolder.findViewById(R.id.swipe_from_side_and_bottom);
            this.mSwipeFromBottomAndSide = radioButton2;
            if (i == 1) {
                radioButton2.setChecked(true);
            }
            this.mSwipeFromBottomAndSide.setOnCheckedChangeListener(this.mOnCheckedChangeListener);
        }
        setAnimationProperties(preferenceViewHolder);
        if (this.mPreviewRunnable == null) {
            this.mPreviewRunnable =
                    new NavigationBarGestureDetailedPreference$$ExternalSyntheticLambda0(
                            this, preferenceViewHolder);
        }
    }

    public final void setAnimationProperties(PreferenceViewHolder preferenceViewHolder) {
        LottieAnimationView lottieAnimationView = this.mSwipeBottomAnimator;
        if (lottieAnimationView != null) {
            lottieAnimationView.setAnimation(
                    NavigationBarGestureSettingsUtil.getPreviewAnimationResId(getContext(), 0));
            this.mSwipeBottomAnimator.playAnimation$1();
        }
        LottieAnimationView lottieAnimationView2 = this.mSwipeSideAndBottomAnimator;
        if (lottieAnimationView2 != null) {
            lottieAnimationView2.setAnimation(
                    NavigationBarGestureSettingsUtil.getPreviewAnimationResId(getContext(), 1));
            this.mSwipeSideAndBottomAnimator.playAnimation$1();
        }
        ((TextView)
                        preferenceViewHolder.findViewById(
                                R.id.navigationbar_swipe_from_bottom_textview))
                .setText(
                        getContext()
                                .getResources()
                                .getString(
                                        this.mBottomAnimationDescs[
                                                (Utils.isTalkBackEnabled(getContext()) ? 1 : 0)
                                                        | (Utils.isTablet() ? 2 : 0)]));
    }

    public final void updateDescription(int i, int i2) {
        ((TextView)
                        this.mRootView.findViewById(
                                R.id.navigationbar_swipe_from_side_and_bottom_textview))
                .setText(
                        getContext()
                                .getResources()
                                .getString(this.mSideAndBottomAnimationDescs[i][i2]));
    }
}
