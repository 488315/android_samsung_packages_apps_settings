package com.samsung.android.settings.navigationbar;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.Utils;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.samsung.android.settings.analyzestorage.data.constant.FileType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.util.SemLog;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarSetupWizardSettings extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mAnimationIdx;
    public LinearLayout mButtonContainer;
    public LinearLayout mGestureContainer;
    public int mLastAnimationIdx;
    public NavigationBarOverlayInteractor mNavBarOverlayInteractor;
    public SharedPreferences mPrefs;
    public LottieAnimationView mSwipeBottomAndSideAnimator;
    public int selectedMode = 0;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final int[] mSideAndBottomAnimationDescs = {
        R.string.navigationbar_swipe_from_side_and_bottom_desc_3,
        R.string.navigationbar_swipe_from_side_and_bottom_desc_2,
        R.string.navigationbar_swipe_from_side_and_bottom_desc_1
    };
    public final int[] mAnimationValues = {171, FileType.CHM, 485};
    public boolean mIsFromSuw = false;
    public boolean mIsFromSmartSwitch = false;
    public boolean isBottomGestureRestored = false;
    public final NavigationBarSetupWizardSettings$$ExternalSyntheticLambda1 mAnimatorListener =
            new ValueAnimator
                    .AnimatorUpdateListener() { // from class:
                                                // com.samsung.android.settings.navigationbar.NavigationBarSetupWizardSettings$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    final NavigationBarSetupWizardSettings navigationBarSetupWizardSettings =
                            NavigationBarSetupWizardSettings.this;
                    LottieAnimationView lottieAnimationView =
                            navigationBarSetupWizardSettings.mSwipeBottomAndSideAnimator;
                    int i = (int) lottieAnimationView.lottieDrawable.animator.frame;
                    int[] iArr = navigationBarSetupWizardSettings.mAnimationValues;
                    if (i <= iArr[0] || i >= iArr[2]) {
                        navigationBarSetupWizardSettings.mAnimationIdx = 0;
                    } else if (i <= iArr[1]) {
                        navigationBarSetupWizardSettings.mAnimationIdx = 1;
                    } else {
                        navigationBarSetupWizardSettings.mAnimationIdx = 2;
                    }
                    int i2 = navigationBarSetupWizardSettings.mAnimationIdx;
                    int i3 = navigationBarSetupWizardSettings.mLastAnimationIdx;
                    if (i2 != i3) {
                        if (iArr[i3] <= i) {
                            lottieAnimationView.pauseAnimation();
                            Handler handler = navigationBarSetupWizardSettings.mHandler;
                            handler.removeCallbacksAndMessages(null);
                            handler.postDelayed(
                                    new Runnable() { // from class:
                                                     // com.samsung.android.settings.navigationbar.NavigationBarSetupWizardSettings$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            NavigationBarSetupWizardSettings
                                                    navigationBarSetupWizardSettings2 =
                                                            NavigationBarSetupWizardSettings.this;
                                            navigationBarSetupWizardSettings2.updateDescription(
                                                    navigationBarSetupWizardSettings2
                                                            .mAnimationIdx);
                                            navigationBarSetupWizardSettings2
                                                    .mSwipeBottomAndSideAnimator.resumeAnimation();
                                        }
                                    },
                                    2000L);
                        } else {
                            navigationBarSetupWizardSettings.updateDescription(i2);
                        }
                    }
                    navigationBarSetupWizardSettings.mLastAnimationIdx =
                            navigationBarSetupWizardSettings.mAnimationIdx;
                }
            };
    public final AnonymousClass1 mOnClickListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.samsung.android.settings.navigationbar.NavigationBarSetupWizardSettings.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i = view.getId() == R.id.navigationbar_type_button_container ? 1 : 0;
                    NavigationBarSetupWizardSettings navigationBarSetupWizardSettings =
                            NavigationBarSetupWizardSettings.this;
                    navigationBarSetupWizardSettings.selectedMode = i ^ 1;
                    LinearLayout linearLayout = navigationBarSetupWizardSettings.mGestureContainer;
                    int i2 = R.drawable.sec_setupwizard_background;
                    linearLayout.setBackgroundResource(
                            i == 0
                                    ? R.drawable.sec_setupwizard_selector
                                    : R.drawable.sec_setupwizard_background);
                    LinearLayout linearLayout2 =
                            NavigationBarSetupWizardSettings.this.mButtonContainer;
                    if (i != 0) {
                        i2 = R.drawable.sec_setupwizard_selector;
                    }
                    linearLayout2.setBackgroundResource(i2);
                    NavigationBarSetupWizardSettings navigationBarSetupWizardSettings2 =
                            NavigationBarSetupWizardSettings.this;
                    if (navigationBarSetupWizardSettings2.mIsFromSmartSwitch) {
                        navigationBarSetupWizardSettings2.updateSettings();
                    }
                }
            };
    public final NavigationBarSetupWizardSettings$$ExternalSyntheticLambda2 mOnTouchListener =
            new NavigationBarSetupWizardSettings$$ExternalSyntheticLambda2();

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // android.app.Activity
    public final void onBackPressed() {
        super.onBackPressed();
        SemLog.d(
                "NavigationBarSetupWizardSettings",
                "onBackPressed() Selected : " + this.selectedMode);
        this.mPrefs.edit().putInt("NavigationBarType", this.selectedMode).apply();
        setResult(0);
        finish();
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            this.mIsFromSuw = intent.getBooleanExtra("fota_suw", false);
            this.mIsFromSmartSwitch = intent.getBooleanExtra("smart_switch", false);
            this.mIsFromSuw =
                    (!(Settings.Global.getInt(
                                            getApplicationContext().getContentResolver(),
                                            "device_provisioned",
                                            0)
                                    != 0))
                            | this.mIsFromSuw;
            SemLog.d(
                    "NavigationBarSetupWizardSettings",
                    "from suw=" + this.mIsFromSuw + ", ss=" + this.mIsFromSmartSwitch);
        }
        this.mNavBarOverlayInteractor = new NavigationBarOverlayInteractor(getApplicationContext());
        this.isBottomGestureRestored =
                NavigationBarSettingsUtil.isSimplifiedGesture()
                        && Settings.Global.getInt(
                                        getApplicationContext().getContentResolver(),
                                        "sem_bottom_gesture_restored",
                                        0)
                                == 1;
        setContentView(R.layout.samsung_navigationbar_settings_setupwizard, false);
        if (bundle != null) {
            this.selectedMode = bundle.getInt("saved_mode", 0);
        } else {
            this.selectedMode =
                    (this.isBottomGestureRestored
                                    || Settings.Global.getInt(
                                                    getApplicationContext().getContentResolver(),
                                                    "navigation_bar_gesture_while_hidden",
                                                    0)
                                            == 0)
                            ? 0
                            : 1;
        }
        SharedPreferences sharedPreferences =
                getApplicationContext().getSharedPreferences("NavigationBarSetupWizardPref", 0);
        this.mPrefs = sharedPreferences;
        sharedPreferences.edit().putInt("NavigationBarType", this.selectedMode).apply();
        setHeaderIcon(
                getApplicationContext().getDrawable(R.drawable.samsung_setting_show_navi_suw_tips));
        setHeaderTitle(R.string.navigationbar_setupwizard_title_oneui61);
        setTitle(R.string.navigationbar_setupwizard_title_oneui61);
        setPrimaryActionButton(
                R.string.next_button_label,
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.navigationbar.NavigationBarSetupWizardSettings$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NavigationBarSetupWizardSettings navigationBarSetupWizardSettings =
                                NavigationBarSetupWizardSettings.this;
                        int i = NavigationBarSetupWizardSettings.$r8$clinit;
                        navigationBarSetupWizardSettings.updateSettings();
                        navigationBarSetupWizardSettings.setResult(-1);
                        navigationBarSetupWizardSettings.finish();
                    }
                });
        if (getResources().getConfiguration().screenWidthDp > 600) {
            boolean z = getResources().getConfiguration().semDisplayDeviceType == 0;
            boolean isTablet = Utils.isTablet();
            int rotation = getApplicationContext().getDisplay().getRotation();
            boolean z2 = rotation == 1 || rotation == 3;
            LinearLayout linearLayout =
                    (LinearLayout) findViewById(R.id.navigationbar_type_button_container);
            LinearLayout linearLayout2 =
                    (LinearLayout) findViewById(R.id.navigationbar_type_gesture_container);
            if (linearLayout != null && linearLayout2 != null && (z || isTablet)) {
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams2 =
                        (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                int i = layoutParams.width;
                if (z) {
                    i =
                            z2
                                    ? -1
                                    : getResources()
                                            .getDimensionPixelSize(
                                                    R.dimen
                                                            .samsung_navigationbar_setupwizard_container_width_fold);
                    int dimensionPixelSize =
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .samsung_navigationbar_setupwizard_horizontal_padding_fold);
                    linearLayout.setPadding(
                            dimensionPixelSize,
                            linearLayout.getPaddingTop(),
                            dimensionPixelSize,
                            linearLayout.getPaddingBottom());
                    linearLayout2.setPadding(
                            dimensionPixelSize,
                            linearLayout2.getPaddingTop(),
                            dimensionPixelSize,
                            linearLayout2.getPaddingBottom());
                } else if (isTablet) {
                    i =
                            z2
                                    ? -1
                                    : getResources()
                                            .getDimensionPixelSize(
                                                    R.dimen
                                                            .samsung_navigationbar_setupwizard_container_width_tablet);
                    int dimensionPixelSize2 =
                            getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .samsung_navigationbar_setupwizard_horizontal_padding_tablet);
                    linearLayout.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
                }
                layoutParams.width = i;
                layoutParams2.width = i;
                linearLayout.setLayoutParams(layoutParams);
                linearLayout2.setLayoutParams(layoutParams2);
            }
        }
        this.mGestureContainer =
                (LinearLayout) findViewById(R.id.navigationbar_type_gesture_container);
        this.mButtonContainer =
                (LinearLayout) findViewById(R.id.navigationbar_type_button_container);
        LinearLayout linearLayout3 = this.mGestureContainer;
        AnonymousClass1 anonymousClass1 = this.mOnClickListener;
        linearLayout3.setOnClickListener(anonymousClass1);
        this.mButtonContainer.setOnClickListener(anonymousClass1);
        LinearLayout linearLayout4 = this.mGestureContainer;
        NavigationBarSetupWizardSettings$$ExternalSyntheticLambda2
                navigationBarSetupWizardSettings$$ExternalSyntheticLambda2 = this.mOnTouchListener;
        linearLayout4.setOnTouchListener(
                navigationBarSetupWizardSettings$$ExternalSyntheticLambda2);
        this.mButtonContainer.setOnTouchListener(
                navigationBarSetupWizardSettings$$ExternalSyntheticLambda2);
        int i2 = this.mPrefs.getInt("NavigationBarType", 0);
        this.selectedMode = i2;
        boolean z3 = i2 == 0;
        LinearLayout linearLayout5 = this.mGestureContainer;
        int i3 = R.drawable.sec_setupwizard_background;
        linearLayout5.setBackgroundResource(
                !z3 ? R.drawable.sec_setupwizard_selector : R.drawable.sec_setupwizard_background);
        LinearLayout linearLayout6 = this.mButtonContainer;
        if (z3) {
            i3 = R.drawable.sec_setupwizard_selector;
        }
        linearLayout6.setBackgroundResource(i3);
        registerAnimatorListener();
        LinearLayout linearLayout7 =
                (LinearLayout) findViewById(R.id.navigationbar_setupwizard_guide);
        if (this.isBottomGestureRestored) {
            linearLayout7.setVisibility(0);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        registerAnimatorListener();
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity,
              // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("saved_mode", this.selectedMode);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onStop() {
        super.onStop();
        LottieAnimationView lottieAnimationView = this.mSwipeBottomAndSideAnimator;
        if (lottieAnimationView != null) {
            lottieAnimationView.pauseAnimation();
            LottieDrawable lottieDrawable = this.mSwipeBottomAndSideAnimator.lottieDrawable;
            lottieDrawable.animator.removeAllUpdateListeners();
            lottieDrawable.animator.addUpdateListener(lottieDrawable.progressUpdateListener);
            this.mSwipeBottomAndSideAnimator.lottieDrawable.animator.removeUpdateListener(
                    this.mAnimatorListener);
        }
    }

    public final void registerAnimatorListener() {
        this.mSwipeBottomAndSideAnimator =
                (LottieAnimationView)
                        findViewById(R.id.navigationbar_gesture_side_and_bottom_animation);
        this.mAnimationIdx = 0;
        this.mLastAnimationIdx = 0;
        updateDescription(0);
        this.mSwipeBottomAndSideAnimator.lottieDrawable.animator.addUpdateListener(
                this.mAnimatorListener);
        this.mSwipeBottomAndSideAnimator.lottieDrawable.animator.setRepeatCount(-1);
        this.mSwipeBottomAndSideAnimator.playAnimation$1();
    }

    public final void updateDescription(int i) {
        ((TextView) findViewById(R.id.navigationbar_swipe_from_side_and_bottom_textview))
                .setText(
                        getApplicationContext()
                                .getResources()
                                .getString(this.mSideAndBottomAnimationDescs[i]));
    }

    public final void updateSettings() {
        NavigationBarOverlayInteractor navigationBarOverlayInteractor;
        int i = this.selectedMode;
        SemLog.d("NavigationBarSetupWizardSettings", "updateSettings() : Selected = " + i);
        this.mPrefs.edit().putInt("NavigationBarType", i).apply();
        Settings.Global.putInt(
                getApplicationContext().getContentResolver(),
                "navigation_bar_gesture_while_hidden",
                i);
        if (this.isBottomGestureRestored || i == 1) {
            Settings.Global.putInt(
                    getApplicationContext().getContentResolver(),
                    "navigation_bar_gesture_detail_type",
                    1);
            Settings.Global.putInt(
                    getApplicationContext().getContentResolver(), "navigation_bar_gesture_hint", 1);
        }
        LoggingHelper.insertEventLogging(7470, 748600, i);
        if (this.mIsFromSuw
                || (navigationBarOverlayInteractor = this.mNavBarOverlayInteractor) == null) {
            return;
        }
        navigationBarOverlayInteractor.setInteractionMode();
    }
}
