package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.dexterity.TapDurationPreferenceFragment;
import com.samsung.android.settings.accessibility.dexterity.TapDurationUtils;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TapDurationExperienceFragment extends TouchSettingsExperienceFragment {
    public final AnonymousClass1 mCalculateTouchDuration;
    public TouchSettingsExperienceCircleView mCircleView;
    public TouchSettingsExperienceCircleView mCircleView1;
    public TouchSettingsExperienceCircleView mCircleView2;
    public TouchSettingsExperienceCircleView mCircleView3;
    public final AnonymousClass1 mResetCircle;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$1] */
    public TapDurationExperienceFragment() {
        final int i = 0;
        this.mCalculateTouchDuration =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment.1
                    public final /* synthetic */ TapDurationExperienceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                TapDurationExperienceFragment tapDurationExperienceFragment =
                                        this.this$0;
                                long uptimeMillis = SystemClock.uptimeMillis();
                                TapDurationExperienceFragment tapDurationExperienceFragment2 =
                                        this.this$0;
                                tapDurationExperienceFragment.mTotalPressedTime =
                                        uptimeMillis
                                                - tapDurationExperienceFragment2.mStartPressTime;
                                long j =
                                        (tapDurationExperienceFragment2.mTotalPressedTime / 10)
                                                * 10;
                                tapDurationExperienceFragment2.mTotalPressedTime = j;
                                if (j
                                        >= tapDurationExperienceFragment2.getTouchValue(
                                                        tapDurationExperienceFragment2.mContext)
                                                + 1000.0f) {
                                    TapDurationExperienceFragment tapDurationExperienceFragment3 =
                                            this.this$0;
                                    tapDurationExperienceFragment3.mCircleView.mState = 0;
                                    tapDurationExperienceFragment3.mDoneButton.setEnabled(false);
                                } else {
                                    TapDurationExperienceFragment tapDurationExperienceFragment4 =
                                            this.this$0;
                                    if (tapDurationExperienceFragment4.mTotalPressedTime
                                            >= tapDurationExperienceFragment4.getTouchValue(
                                                    tapDurationExperienceFragment4.mContext)) {
                                        TapDurationExperienceFragment
                                                tapDurationExperienceFragment5 = this.this$0;
                                        tapDurationExperienceFragment5.mCircleView.mState = 1;
                                        tapDurationExperienceFragment5.mDoneButton.setEnabled(true);
                                    }
                                }
                                this.this$0.mPressTimeContainer.setVisibility(0);
                                this.this$0.mTimeTextView.setText(
                                        String.format(
                                                Locale.getDefault(),
                                                "%.2f",
                                                Float.valueOf(
                                                        this.this$0.mTotalPressedTime / 1000.0f)));
                                if (this.this$0.isResumed()) {
                                    TapDurationExperienceFragment tapDurationExperienceFragment6 =
                                            this.this$0;
                                    tapDurationExperienceFragment6.mHandler.postDelayed(
                                            tapDurationExperienceFragment6.mCalculateTouchDuration,
                                            10L);
                                    break;
                                }
                                break;
                            default:
                                this.this$0.resetCircles$1();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mResetCircle =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment.1
                    public final /* synthetic */ TapDurationExperienceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                TapDurationExperienceFragment tapDurationExperienceFragment =
                                        this.this$0;
                                long uptimeMillis = SystemClock.uptimeMillis();
                                TapDurationExperienceFragment tapDurationExperienceFragment2 =
                                        this.this$0;
                                tapDurationExperienceFragment.mTotalPressedTime =
                                        uptimeMillis
                                                - tapDurationExperienceFragment2.mStartPressTime;
                                long j =
                                        (tapDurationExperienceFragment2.mTotalPressedTime / 10)
                                                * 10;
                                tapDurationExperienceFragment2.mTotalPressedTime = j;
                                if (j
                                        >= tapDurationExperienceFragment2.getTouchValue(
                                                        tapDurationExperienceFragment2.mContext)
                                                + 1000.0f) {
                                    TapDurationExperienceFragment tapDurationExperienceFragment3 =
                                            this.this$0;
                                    tapDurationExperienceFragment3.mCircleView.mState = 0;
                                    tapDurationExperienceFragment3.mDoneButton.setEnabled(false);
                                } else {
                                    TapDurationExperienceFragment tapDurationExperienceFragment4 =
                                            this.this$0;
                                    if (tapDurationExperienceFragment4.mTotalPressedTime
                                            >= tapDurationExperienceFragment4.getTouchValue(
                                                    tapDurationExperienceFragment4.mContext)) {
                                        TapDurationExperienceFragment
                                                tapDurationExperienceFragment5 = this.this$0;
                                        tapDurationExperienceFragment5.mCircleView.mState = 1;
                                        tapDurationExperienceFragment5.mDoneButton.setEnabled(true);
                                    }
                                }
                                this.this$0.mPressTimeContainer.setVisibility(0);
                                this.this$0.mTimeTextView.setText(
                                        String.format(
                                                Locale.getDefault(),
                                                "%.2f",
                                                Float.valueOf(
                                                        this.this$0.mTotalPressedTime / 1000.0f)));
                                if (this.this$0.isResumed()) {
                                    TapDurationExperienceFragment tapDurationExperienceFragment6 =
                                            this.this$0;
                                    tapDurationExperienceFragment6.mHandler.postDelayed(
                                            tapDurationExperienceFragment6.mCalculateTouchDuration,
                                            10L);
                                    break;
                                }
                                break;
                            default:
                                this.this$0.resetCircles$1();
                                break;
                        }
                    }
                };
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5016;
    }

    public final float getTouchValue(Context context) {
        return TapDurationUtils.getTapDurationValue(context) * 1000.0f;
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void initViews$1() {
        init();
        this.mExperienceDescription.setText(R.string.tap_duration_experience_description);
        Button button = this.mDoneButton;
        boolean z = true;
        if (this.mCircleView1.mState != 1
                && this.mCircleView2.mState != 1
                && this.mCircleView3.mState != 1) {
            z = false;
        }
        button.setEnabled(z);
        this.mDoneButton.setOnClickListener(
                new TapDurationExperienceFragment$$ExternalSyntheticLambda1(this, 0));
        this.mBackButton.setOnClickListener(
                new TapDurationExperienceFragment$$ExternalSyntheticLambda1(this, 1));
        this.mButtonContainer1.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TapDurationExperienceFragment tapDurationExperienceFragment =
                                TapDurationExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                tapDurationExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView2;
                        } else {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = tapDurationExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            tapDurationExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            tapDurationExperienceFragment.mHandler.post(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            tapDurationExperienceFragment.mExperienceDescription.setVisibility(4);
                            tapDurationExperienceFragment.mStatusTextView.setVisibility(4);
                            tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                            tapDurationExperienceFragment.mCircleView.reset();
                            tapDurationExperienceFragment.mCircleView.mState = 0;
                            long touchValue =
                                    (long)
                                            tapDurationExperienceFragment.getTouchValue(
                                                    tapDurationExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    tapDurationExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (tapDurationExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TapDurationExperienceFragment
                                                        tapDurationExperienceFragment2 =
                                                                TapDurationExperienceFragment.this;
                                                View view2 = view;
                                                if (tapDurationExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    tapDurationExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    tapDurationExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                tapDurationExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                tapDurationExperienceFragment2
                                                                        .mTimer);
                                                tapDurationExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                tapDurationExperienceFragment.mTimer = runnable;
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = tapDurationExperienceFragment.getContext();
                            if (context != null) {
                                view.announceForAccessibility(
                                        context.getString(
                                                tapDurationExperienceFragment.mTotalPressedTime
                                                                == 1000
                                                        ? R.string
                                                                .touch_and_hold_enough_singular_contentDescription
                                                        : R.string
                                                                .touch_and_hold_enough_plural_contentDescription,
                                                String.format(
                                                        Locale.getDefault(),
                                                        "%.2f",
                                                        Float.valueOf(
                                                                tapDurationExperienceFragment
                                                                                .mTotalPressedTime
                                                                        / 1000.0f))));
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_over_time);
                                } else if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                tapDurationExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    tapDurationExperienceFragment.mPressTimeContainer.setVisibility(
                                            4);
                                    tapDurationExperienceFragment.mStatusTextView.setVisibility(0);
                                    tapDurationExperienceFragment.mStatusTextView.setText(
                                            R.string.tap_duration_success);
                                    tapDurationExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_release_before_set_time);
                                }
                            }
                            tapDurationExperienceFragment.mHandler.removeCallbacks(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            tapDurationExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    tapDurationExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = tapDurationExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                tapDurationExperienceFragment.mHandler.removeCallbacks(runnable2);
                                tapDurationExperienceFragment.mTimer = null;
                            }
                            if (tapDurationExperienceFragment.mCircleView1.mState == 0
                                    && tapDurationExperienceFragment.mCircleView2.mState == 0
                                    && tapDurationExperienceFragment.mCircleView3.mState == 0) {
                                tapDurationExperienceFragment.mCurrenStatus = status2;
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                                tapDurationExperienceFragment.mExperienceDescription.setText(
                                        tapDurationExperienceFragment.getString(
                                                        R.string.tap_duration_all_fail)
                                                + "\n"
                                                + tapDurationExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        tapDurationExperienceFragment.mResetCircle, 5000L);
                                tapDurationExperienceFragment.mTryAgainContainer.setVisibility(0);
                                tapDurationExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TapDurationExperienceFragment$$ExternalSyntheticLambda1(
                                                tapDurationExperienceFragment, 2));
                            }
                            tapDurationExperienceFragment.mDoneButton.setEnabled(
                                    tapDurationExperienceFragment.mCircleView1.mState == 1
                                            || tapDurationExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || tapDurationExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer2.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TapDurationExperienceFragment tapDurationExperienceFragment =
                                TapDurationExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                tapDurationExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView2;
                        } else {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = tapDurationExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            tapDurationExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            tapDurationExperienceFragment.mHandler.post(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            tapDurationExperienceFragment.mExperienceDescription.setVisibility(4);
                            tapDurationExperienceFragment.mStatusTextView.setVisibility(4);
                            tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                            tapDurationExperienceFragment.mCircleView.reset();
                            tapDurationExperienceFragment.mCircleView.mState = 0;
                            long touchValue =
                                    (long)
                                            tapDurationExperienceFragment.getTouchValue(
                                                    tapDurationExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    tapDurationExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (tapDurationExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TapDurationExperienceFragment
                                                        tapDurationExperienceFragment2 =
                                                                TapDurationExperienceFragment.this;
                                                View view2 = view;
                                                if (tapDurationExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    tapDurationExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    tapDurationExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                tapDurationExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                tapDurationExperienceFragment2
                                                                        .mTimer);
                                                tapDurationExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                tapDurationExperienceFragment.mTimer = runnable;
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = tapDurationExperienceFragment.getContext();
                            if (context != null) {
                                view.announceForAccessibility(
                                        context.getString(
                                                tapDurationExperienceFragment.mTotalPressedTime
                                                                == 1000
                                                        ? R.string
                                                                .touch_and_hold_enough_singular_contentDescription
                                                        : R.string
                                                                .touch_and_hold_enough_plural_contentDescription,
                                                String.format(
                                                        Locale.getDefault(),
                                                        "%.2f",
                                                        Float.valueOf(
                                                                tapDurationExperienceFragment
                                                                                .mTotalPressedTime
                                                                        / 1000.0f))));
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_over_time);
                                } else if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                tapDurationExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    tapDurationExperienceFragment.mPressTimeContainer.setVisibility(
                                            4);
                                    tapDurationExperienceFragment.mStatusTextView.setVisibility(0);
                                    tapDurationExperienceFragment.mStatusTextView.setText(
                                            R.string.tap_duration_success);
                                    tapDurationExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_release_before_set_time);
                                }
                            }
                            tapDurationExperienceFragment.mHandler.removeCallbacks(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            tapDurationExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    tapDurationExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = tapDurationExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                tapDurationExperienceFragment.mHandler.removeCallbacks(runnable2);
                                tapDurationExperienceFragment.mTimer = null;
                            }
                            if (tapDurationExperienceFragment.mCircleView1.mState == 0
                                    && tapDurationExperienceFragment.mCircleView2.mState == 0
                                    && tapDurationExperienceFragment.mCircleView3.mState == 0) {
                                tapDurationExperienceFragment.mCurrenStatus = status2;
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                                tapDurationExperienceFragment.mExperienceDescription.setText(
                                        tapDurationExperienceFragment.getString(
                                                        R.string.tap_duration_all_fail)
                                                + "\n"
                                                + tapDurationExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        tapDurationExperienceFragment.mResetCircle, 5000L);
                                tapDurationExperienceFragment.mTryAgainContainer.setVisibility(0);
                                tapDurationExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TapDurationExperienceFragment$$ExternalSyntheticLambda1(
                                                tapDurationExperienceFragment, 2));
                            }
                            tapDurationExperienceFragment.mDoneButton.setEnabled(
                                    tapDurationExperienceFragment.mCircleView1.mState == 1
                                            || tapDurationExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || tapDurationExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer3.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TapDurationExperienceFragment tapDurationExperienceFragment =
                                TapDurationExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                tapDurationExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView2;
                        } else {
                            tapDurationExperienceFragment.mCircleView =
                                    tapDurationExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = tapDurationExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            tapDurationExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            tapDurationExperienceFragment.mHandler.post(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            tapDurationExperienceFragment.mExperienceDescription.setVisibility(4);
                            tapDurationExperienceFragment.mStatusTextView.setVisibility(4);
                            tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                            tapDurationExperienceFragment.mCircleView.reset();
                            tapDurationExperienceFragment.mCircleView.mState = 0;
                            long touchValue =
                                    (long)
                                            tapDurationExperienceFragment.getTouchValue(
                                                    tapDurationExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    tapDurationExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (tapDurationExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TapDurationExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TapDurationExperienceFragment
                                                        tapDurationExperienceFragment2 =
                                                                TapDurationExperienceFragment.this;
                                                View view2 = view;
                                                if (tapDurationExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    tapDurationExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    tapDurationExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                tapDurationExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                tapDurationExperienceFragment2
                                                                        .mTimer);
                                                tapDurationExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                tapDurationExperienceFragment.mTimer = runnable;
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = tapDurationExperienceFragment.getContext();
                            if (context != null) {
                                view.announceForAccessibility(
                                        context.getString(
                                                tapDurationExperienceFragment.mTotalPressedTime
                                                                == 1000
                                                        ? R.string
                                                                .touch_and_hold_enough_singular_contentDescription
                                                        : R.string
                                                                .touch_and_hold_enough_plural_contentDescription,
                                                String.format(
                                                        Locale.getDefault(),
                                                        "%.2f",
                                                        Float.valueOf(
                                                                tapDurationExperienceFragment
                                                                                .mTotalPressedTime
                                                                        / 1000.0f))));
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                        tapDurationExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_over_time);
                                } else if (tapDurationExperienceFragment.mTotalPressedTime
                                        >= tapDurationExperienceFragment.getTouchValue(
                                                tapDurationExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    tapDurationExperienceFragment.mPressTimeContainer.setVisibility(
                                            4);
                                    tapDurationExperienceFragment.mStatusTextView.setVisibility(0);
                                    tapDurationExperienceFragment.mStatusTextView.setText(
                                            R.string.tap_duration_success);
                                    tapDurationExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    tapDurationExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    tapDurationExperienceFragment.mExperienceDescription.setText(
                                            R.string.tap_duration_release_before_set_time);
                                }
                            }
                            tapDurationExperienceFragment.mHandler.removeCallbacks(
                                    tapDurationExperienceFragment.mCalculateTouchDuration);
                            tapDurationExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    tapDurationExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = tapDurationExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                tapDurationExperienceFragment.mHandler.removeCallbacks(runnable2);
                                tapDurationExperienceFragment.mTimer = null;
                            }
                            if (tapDurationExperienceFragment.mCircleView1.mState == 0
                                    && tapDurationExperienceFragment.mCircleView2.mState == 0
                                    && tapDurationExperienceFragment.mCircleView3.mState == 0) {
                                tapDurationExperienceFragment.mCurrenStatus = status2;
                                tapDurationExperienceFragment.mExperienceDescription.setVisibility(
                                        0);
                                tapDurationExperienceFragment.mPressTimeContainer.setVisibility(4);
                                tapDurationExperienceFragment.mExperienceDescription.setText(
                                        tapDurationExperienceFragment.getString(
                                                        R.string.tap_duration_all_fail)
                                                + "\n"
                                                + tapDurationExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                tapDurationExperienceFragment.mHandler.postDelayed(
                                        tapDurationExperienceFragment.mResetCircle, 5000L);
                                tapDurationExperienceFragment.mTryAgainContainer.setVisibility(0);
                                tapDurationExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TapDurationExperienceFragment$$ExternalSyntheticLambda1(
                                                tapDurationExperienceFragment, 2));
                            }
                            tapDurationExperienceFragment.mDoneButton.setEnabled(
                                    tapDurationExperienceFragment.mCircleView1.mState == 1
                                            || tapDurationExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || tapDurationExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer1.addView(this.mCircleView1, this.mParams);
        this.mButtonContainer2.addView(this.mCircleView2, this.mParams);
        this.mButtonContainer3.addView(this.mCircleView3, this.mParams);
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, com.android.settings.core.InstrumentedFragment, com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mCircleView1 = new TouchSettingsExperienceCircleView(this.mContext);
        this.mCircleView2 = new TouchSettingsExperienceCircleView(this.mContext);
        this.mCircleView3 = new TouchSettingsExperienceCircleView(this.mContext);
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void onBackPressed() {
        FragmentActivity activity = getActivity();
        if (activity == null
                || activity.getIntent().getExtras() == null
                || !activity.getIntent().getExtras().getBoolean("fromPicker", false)) {
            launchFragment$1(
                    R.string.tap_duration_title, TapDurationPreferenceFragment.class.getName());
        } else {
            launchFragment$1(
                    R.string.tap_duration_title, TapDurationPickerFragment.class.getName());
        }
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        initViews$1();
        return this.mRootView;
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void removeViews() {
        if (this.mCircleView1.getParent() != null) {
            ((ViewGroup) this.mCircleView1.getParent()).removeView(this.mCircleView1);
        }
        if (this.mCircleView2.getParent() != null) {
            ((ViewGroup) this.mCircleView2.getParent()).removeView(this.mCircleView2);
        }
        if (this.mCircleView3.getParent() != null) {
            ((ViewGroup) this.mCircleView3.getParent()).removeView(this.mCircleView3);
        }
    }

    public final void resetCircles$1() {
        this.mExperienceDescription.setVisibility(0);
        this.mExperienceDescription.setText(R.string.tap_duration_experience_description);
        this.mPressTimeContainer.setVisibility(4);
        this.mCurrenStatus = TouchSettingsExperienceFragment.Status.STAND_BY;
        this.mCircleView1.reset();
        this.mCircleView1.mState = -1;
        this.mCircleView2.reset();
        this.mCircleView2.mState = -1;
        this.mCircleView3.reset();
        this.mCircleView3.mState = -1;
        this.mStatusTextView.setVisibility(4);
        this.mTryAgainContainer.setVisibility(4);
    }
}
