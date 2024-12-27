package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.dexterity.TouchAndHoldFragment;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class TouchHoldDelayExperienceFragment extends TouchSettingsExperienceFragment {
    public final AnonymousClass1 mCalculateTouchDuration;
    public TouchSettingsExperienceCircleView mCircleView;
    public TouchSettingsExperienceCircleView mCircleView1;
    public TouchSettingsExperienceCircleView mCircleView2;
    public TouchSettingsExperienceCircleView mCircleView3;
    public final AnonymousClass1 mResetCircle;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$1] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$1] */
    public TouchHoldDelayExperienceFragment() {
        final int i = 0;
        this.mCalculateTouchDuration =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment.1
                    public final /* synthetic */ TouchHoldDelayExperienceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                        this.this$0;
                                long uptimeMillis = SystemClock.uptimeMillis();
                                TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment2 =
                                        this.this$0;
                                touchHoldDelayExperienceFragment.mTotalPressedTime =
                                        uptimeMillis
                                                - touchHoldDelayExperienceFragment2.mStartPressTime;
                                long j =
                                        (touchHoldDelayExperienceFragment2.mTotalPressedTime / 10)
                                                * 10;
                                touchHoldDelayExperienceFragment2.mTotalPressedTime = j;
                                if (j
                                        >= touchHoldDelayExperienceFragment2.getTouchValue(
                                                        touchHoldDelayExperienceFragment2.mContext)
                                                + 1000.0f) {
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment3 = this.this$0;
                                    touchHoldDelayExperienceFragment3.mCircleView.mState = 0;
                                    touchHoldDelayExperienceFragment3.mDoneButton.setEnabled(false);
                                } else {
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment4 = this.this$0;
                                    if (touchHoldDelayExperienceFragment4.mTotalPressedTime
                                            >= touchHoldDelayExperienceFragment4.getTouchValue(
                                                    touchHoldDelayExperienceFragment4.mContext)) {
                                        TouchHoldDelayExperienceFragment
                                                touchHoldDelayExperienceFragment5 = this.this$0;
                                        touchHoldDelayExperienceFragment5.mCircleView.mState = 1;
                                        touchHoldDelayExperienceFragment5.mDoneButton.setEnabled(
                                                true);
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
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment6 = this.this$0;
                                    touchHoldDelayExperienceFragment6.mHandler.postDelayed(
                                            touchHoldDelayExperienceFragment6
                                                    .mCalculateTouchDuration,
                                            10L);
                                    break;
                                }
                                break;
                            default:
                                this.this$0.resetCircles$2();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mResetCircle =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment.1
                    public final /* synthetic */ TouchHoldDelayExperienceFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i2) {
                            case 0:
                                TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                        this.this$0;
                                long uptimeMillis = SystemClock.uptimeMillis();
                                TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment2 =
                                        this.this$0;
                                touchHoldDelayExperienceFragment.mTotalPressedTime =
                                        uptimeMillis
                                                - touchHoldDelayExperienceFragment2.mStartPressTime;
                                long j =
                                        (touchHoldDelayExperienceFragment2.mTotalPressedTime / 10)
                                                * 10;
                                touchHoldDelayExperienceFragment2.mTotalPressedTime = j;
                                if (j
                                        >= touchHoldDelayExperienceFragment2.getTouchValue(
                                                        touchHoldDelayExperienceFragment2.mContext)
                                                + 1000.0f) {
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment3 = this.this$0;
                                    touchHoldDelayExperienceFragment3.mCircleView.mState = 0;
                                    touchHoldDelayExperienceFragment3.mDoneButton.setEnabled(false);
                                } else {
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment4 = this.this$0;
                                    if (touchHoldDelayExperienceFragment4.mTotalPressedTime
                                            >= touchHoldDelayExperienceFragment4.getTouchValue(
                                                    touchHoldDelayExperienceFragment4.mContext)) {
                                        TouchHoldDelayExperienceFragment
                                                touchHoldDelayExperienceFragment5 = this.this$0;
                                        touchHoldDelayExperienceFragment5.mCircleView.mState = 1;
                                        touchHoldDelayExperienceFragment5.mDoneButton.setEnabled(
                                                true);
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
                                    TouchHoldDelayExperienceFragment
                                            touchHoldDelayExperienceFragment6 = this.this$0;
                                    touchHoldDelayExperienceFragment6.mHandler.postDelayed(
                                            touchHoldDelayExperienceFragment6
                                                    .mCalculateTouchDuration,
                                            10L);
                                    break;
                                }
                                break;
                            default:
                                this.this$0.resetCircles$2();
                                break;
                        }
                    }
                };
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5015;
    }

    public final float getTouchValue(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "long_press_timeout", 500);
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void initViews$1() {
        init();
        this.mExperienceDescription.setText(R.string.touch_and_hold_delay_experience_description);
        Button button = this.mDoneButton;
        boolean z = true;
        if (this.mCircleView1.mState != 1
                && this.mCircleView2.mState != 1
                && this.mCircleView3.mState != 1) {
            z = false;
        }
        button.setEnabled(z);
        this.mDoneButton.setOnClickListener(
                new TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda1(this, 0));
        this.mBackButton.setOnClickListener(
                new TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda1(this, 1));
        this.mButtonContainer1.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                touchHoldDelayExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView2;
                        } else {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = touchHoldDelayExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            if (touchHoldDelayExperienceFragment.mHandler.hasCallbacks(
                                    touchHoldDelayExperienceFragment.mResetCircle)) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        touchHoldDelayExperienceFragment.mResetCircle);
                            }
                            touchHoldDelayExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            touchHoldDelayExperienceFragment.mHandler.post(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            touchHoldDelayExperienceFragment.mExperienceDescription.setVisibility(
                                    4);
                            touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(4);
                            touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(4);
                            touchHoldDelayExperienceFragment.mCircleView.reset();
                            long touchValue =
                                    (long)
                                            touchHoldDelayExperienceFragment.getTouchValue(
                                                    touchHoldDelayExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (touchHoldDelayExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TouchHoldDelayExperienceFragment
                                                        touchHoldDelayExperienceFragment2 =
                                                                TouchHoldDelayExperienceFragment
                                                                        .this;
                                                View view2 = view;
                                                if (touchHoldDelayExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    touchHoldDelayExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    touchHoldDelayExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                touchHoldDelayExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                touchHoldDelayExperienceFragment2
                                                                        .mTimer);
                                                touchHoldDelayExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                touchHoldDelayExperienceFragment.mTimer = runnable;
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = touchHoldDelayExperienceFragment.getContext();
                            if (context != null) {
                                String format =
                                        String.format(
                                                Locale.getDefault(),
                                                "%.2f",
                                                Float.valueOf(
                                                        touchHoldDelayExperienceFragment
                                                                        .mTotalPressedTime
                                                                / 1000.0f));
                                int i2 =
                                        touchHoldDelayExperienceFragment.mTotalPressedTime == 1000
                                                ? R.string
                                                        .touch_and_hold_enough_singular_contentDescription
                                                : R.string
                                                        .touch_and_hold_enough_plural_contentDescription;
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_over_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_over_time));
                                } else if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                touchHoldDelayExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mPressTimeContainer
                                            .setVisibility(4);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(
                                            0);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setText(
                                            R.string.touch_and_hold_delay_success);
                                    touchHoldDelayExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string.touch_and_hold_delay_success));
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mState = 0;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_release_before_set_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_release_before_set_time));
                                }
                            }
                            touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            touchHoldDelayExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = touchHoldDelayExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        runnable2);
                                touchHoldDelayExperienceFragment.mTimer = null;
                            }
                            if (touchHoldDelayExperienceFragment.mCircleView1.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView2.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView3.mState == 0) {
                                touchHoldDelayExperienceFragment.mCurrenStatus = status2;
                                touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(
                                        4);
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                        touchHoldDelayExperienceFragment.getString(
                                                        R.string.touch_and_hold_delay_all_fail)
                                                + "\n"
                                                + touchHoldDelayExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        touchHoldDelayExperienceFragment.mResetCircle, 5000L);
                                touchHoldDelayExperienceFragment.mTryAgainContainer.setVisibility(
                                        0);
                                touchHoldDelayExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda1(
                                                touchHoldDelayExperienceFragment, 2));
                            }
                            touchHoldDelayExperienceFragment.mDoneButton.setEnabled(
                                    touchHoldDelayExperienceFragment.mCircleView1.mState == 1
                                            || touchHoldDelayExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || touchHoldDelayExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer2.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                touchHoldDelayExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView2;
                        } else {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = touchHoldDelayExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            if (touchHoldDelayExperienceFragment.mHandler.hasCallbacks(
                                    touchHoldDelayExperienceFragment.mResetCircle)) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        touchHoldDelayExperienceFragment.mResetCircle);
                            }
                            touchHoldDelayExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            touchHoldDelayExperienceFragment.mHandler.post(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            touchHoldDelayExperienceFragment.mExperienceDescription.setVisibility(
                                    4);
                            touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(4);
                            touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(4);
                            touchHoldDelayExperienceFragment.mCircleView.reset();
                            long touchValue =
                                    (long)
                                            touchHoldDelayExperienceFragment.getTouchValue(
                                                    touchHoldDelayExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (touchHoldDelayExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TouchHoldDelayExperienceFragment
                                                        touchHoldDelayExperienceFragment2 =
                                                                TouchHoldDelayExperienceFragment
                                                                        .this;
                                                View view2 = view;
                                                if (touchHoldDelayExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    touchHoldDelayExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    touchHoldDelayExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                touchHoldDelayExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                touchHoldDelayExperienceFragment2
                                                                        .mTimer);
                                                touchHoldDelayExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                touchHoldDelayExperienceFragment.mTimer = runnable;
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = touchHoldDelayExperienceFragment.getContext();
                            if (context != null) {
                                String format =
                                        String.format(
                                                Locale.getDefault(),
                                                "%.2f",
                                                Float.valueOf(
                                                        touchHoldDelayExperienceFragment
                                                                        .mTotalPressedTime
                                                                / 1000.0f));
                                int i2 =
                                        touchHoldDelayExperienceFragment.mTotalPressedTime == 1000
                                                ? R.string
                                                        .touch_and_hold_enough_singular_contentDescription
                                                : R.string
                                                        .touch_and_hold_enough_plural_contentDescription;
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_over_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_over_time));
                                } else if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                touchHoldDelayExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mPressTimeContainer
                                            .setVisibility(4);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(
                                            0);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setText(
                                            R.string.touch_and_hold_delay_success);
                                    touchHoldDelayExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string.touch_and_hold_delay_success));
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mState = 0;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_release_before_set_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_release_before_set_time));
                                }
                            }
                            touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            touchHoldDelayExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = touchHoldDelayExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        runnable2);
                                touchHoldDelayExperienceFragment.mTimer = null;
                            }
                            if (touchHoldDelayExperienceFragment.mCircleView1.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView2.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView3.mState == 0) {
                                touchHoldDelayExperienceFragment.mCurrenStatus = status2;
                                touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(
                                        4);
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                        touchHoldDelayExperienceFragment.getString(
                                                        R.string.touch_and_hold_delay_all_fail)
                                                + "\n"
                                                + touchHoldDelayExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        touchHoldDelayExperienceFragment.mResetCircle, 5000L);
                                touchHoldDelayExperienceFragment.mTryAgainContainer.setVisibility(
                                        0);
                                touchHoldDelayExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda1(
                                                touchHoldDelayExperienceFragment, 2));
                            }
                            touchHoldDelayExperienceFragment.mDoneButton.setEnabled(
                                    touchHoldDelayExperienceFragment.mCircleView1.mState == 1
                                            || touchHoldDelayExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || touchHoldDelayExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer3.setOnTouchListener(
                new View
                        .OnTouchListener() { // from class:
                                             // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(final View view, MotionEvent motionEvent) {
                        final TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        TouchSettingsExperienceFragment.Status status =
                                touchHoldDelayExperienceFragment.mCurrenStatus;
                        TouchSettingsExperienceFragment.Status status2 =
                                TouchSettingsExperienceFragment.Status.TRY_AGAIN;
                        if (status == status2) {
                            return false;
                        }
                        int id = view.getId();
                        if (id == R.id.button_container_1) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView1;
                        } else if (id == R.id.button_container_2) {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView2;
                        } else {
                            touchHoldDelayExperienceFragment.mCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView3;
                        }
                        int action = motionEvent.getAction();
                        if (action == 0) {
                            int i = touchHoldDelayExperienceFragment.mCircleView.mState;
                            if (i == 0 || i == 1) {
                                return false;
                            }
                            if (touchHoldDelayExperienceFragment.mHandler.hasCallbacks(
                                    touchHoldDelayExperienceFragment.mResetCircle)) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        touchHoldDelayExperienceFragment.mResetCircle);
                            }
                            touchHoldDelayExperienceFragment.mStartPressTime =
                                    SystemClock.uptimeMillis();
                            touchHoldDelayExperienceFragment.mHandler.post(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            view.announceForAccessibility(ApnSettings.MVNO_NONE);
                            touchHoldDelayExperienceFragment.mExperienceDescription.setVisibility(
                                    4);
                            touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(4);
                            touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(4);
                            touchHoldDelayExperienceFragment.mCircleView.reset();
                            long touchValue =
                                    (long)
                                            touchHoldDelayExperienceFragment.getTouchValue(
                                                    touchHoldDelayExperienceFragment.mContext);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            touchSettingsExperienceCircleView.mRotationSet.setDuration(touchValue);
                            touchSettingsExperienceCircleView.mScaleSet.start();
                            if (touchHoldDelayExperienceFragment.mTimer == null) {
                                Runnable runnable =
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda4
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                TouchHoldDelayExperienceFragment
                                                        touchHoldDelayExperienceFragment2 =
                                                                TouchHoldDelayExperienceFragment
                                                                        .this;
                                                View view2 = view;
                                                if (touchHoldDelayExperienceFragment2.mCircleView
                                                        .mRotationSet.isRunning()) {
                                                    touchHoldDelayExperienceFragment2.mHandler
                                                            .postDelayed(
                                                                    touchHoldDelayExperienceFragment2
                                                                            .mTimer,
                                                                    10L);
                                                    return;
                                                }
                                                touchHoldDelayExperienceFragment2.mHandler
                                                        .removeCallbacks(
                                                                touchHoldDelayExperienceFragment2
                                                                        .mTimer);
                                                touchHoldDelayExperienceFragment2.mTimer = null;
                                                view2.setPressed(true);
                                                view2.performClick();
                                            }
                                        };
                                touchHoldDelayExperienceFragment.mTimer = runnable;
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        runnable,
                                        (long)
                                                touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext));
                            }
                        } else if (action == 1 || action == 3) {
                            Context context = touchHoldDelayExperienceFragment.getContext();
                            if (context != null) {
                                String format =
                                        String.format(
                                                Locale.getDefault(),
                                                "%.2f",
                                                Float.valueOf(
                                                        touchHoldDelayExperienceFragment
                                                                        .mTotalPressedTime
                                                                / 1000.0f));
                                int i2 =
                                        touchHoldDelayExperienceFragment.mTotalPressedTime == 1000
                                                ? R.string
                                                        .touch_and_hold_enough_singular_contentDescription
                                                : R.string
                                                        .touch_and_hold_enough_plural_contentDescription;
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                        touchHoldDelayExperienceFragment.mContext)
                                                + 1000.0f) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView2 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView2.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView2.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_over_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_over_time));
                                } else if (touchHoldDelayExperienceFragment.mTotalPressedTime
                                        >= touchHoldDelayExperienceFragment.getTouchValue(
                                                touchHoldDelayExperienceFragment.mContext)) {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView3 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView3.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mStandBy.setVisibility(4);
                                    touchSettingsExperienceCircleView3.mSuccess.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mPressTimeContainer
                                            .setVisibility(4);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setVisibility(
                                            0);
                                    touchHoldDelayExperienceFragment.mStatusTextView.setText(
                                            R.string.touch_and_hold_delay_success);
                                    touchHoldDelayExperienceFragment.mExperienceDescription
                                            .setVisibility(4);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string.touch_and_hold_delay_success));
                                } else {
                                    TouchSettingsExperienceCircleView
                                            touchSettingsExperienceCircleView4 =
                                                    touchHoldDelayExperienceFragment.mCircleView;
                                    touchSettingsExperienceCircleView4.mState = 0;
                                    touchSettingsExperienceCircleView4.mProgress.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mCircle.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mHold.setVisibility(4);
                                    touchSettingsExperienceCircleView4.mFail.setVisibility(0);
                                    touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                            R.string.touch_and_hold_delay_release_before_set_time);
                                    view.announceForAccessibility(
                                            context.getString(i2, format)
                                                    + " "
                                                    + context.getString(
                                                            R.string
                                                                    .touch_and_hold_delay_release_before_set_time));
                                }
                            }
                            touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                    touchHoldDelayExperienceFragment.mCalculateTouchDuration);
                            touchHoldDelayExperienceFragment.mStartPressTime = -1L;
                            view.setPressed(false);
                            TouchSettingsExperienceCircleView touchSettingsExperienceCircleView5 =
                                    touchHoldDelayExperienceFragment.mCircleView;
                            if (touchSettingsExperienceCircleView5.mScaleSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mScaleSet.cancel();
                            }
                            if (touchSettingsExperienceCircleView5.mRotationSet.isRunning()) {
                                touchSettingsExperienceCircleView5.mRotationSet.cancel();
                            }
                            Runnable runnable2 = touchHoldDelayExperienceFragment.mTimer;
                            if (runnable2 != null) {
                                touchHoldDelayExperienceFragment.mHandler.removeCallbacks(
                                        runnable2);
                                touchHoldDelayExperienceFragment.mTimer = null;
                            }
                            if (touchHoldDelayExperienceFragment.mCircleView1.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView2.mState == 0
                                    && touchHoldDelayExperienceFragment.mCircleView3.mState == 0) {
                                touchHoldDelayExperienceFragment.mCurrenStatus = status2;
                                touchHoldDelayExperienceFragment.mPressTimeContainer.setVisibility(
                                        4);
                                touchHoldDelayExperienceFragment.mExperienceDescription
                                        .setVisibility(0);
                                touchHoldDelayExperienceFragment.mExperienceDescription.setText(
                                        touchHoldDelayExperienceFragment.getString(
                                                        R.string.touch_and_hold_delay_all_fail)
                                                + "\n"
                                                + touchHoldDelayExperienceFragment
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .touch_settings_test_will_reset,
                                                                5,
                                                                5));
                                touchHoldDelayExperienceFragment.mHandler.postDelayed(
                                        touchHoldDelayExperienceFragment.mResetCircle, 5000L);
                                touchHoldDelayExperienceFragment.mTryAgainContainer.setVisibility(
                                        0);
                                touchHoldDelayExperienceFragment.mTryAgainButton.setOnClickListener(
                                        new TouchHoldDelayExperienceFragment$$ExternalSyntheticLambda1(
                                                touchHoldDelayExperienceFragment, 2));
                            }
                            touchHoldDelayExperienceFragment.mDoneButton.setEnabled(
                                    touchHoldDelayExperienceFragment.mCircleView1.mState == 1
                                            || touchHoldDelayExperienceFragment.mCircleView2.mState
                                                    == 1
                                            || touchHoldDelayExperienceFragment.mCircleView3.mState
                                                    == 1);
                        }
                        return true;
                    }
                });
        this.mButtonContainer1.addView(this.mCircleView1, this.mParams);
        this.mButtonContainer2.addView(this.mCircleView2, this.mParams);
        this.mButtonContainer3.addView(this.mCircleView3, this.mParams);
        final TouchSettingsExperienceCircleView touchSettingsExperienceCircleView =
                this.mCircleView1;
        touchSettingsExperienceCircleView.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment.3
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        String str;
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        TouchSettingsExperienceCircleView touchSettingsExperienceCircleView2 =
                                touchSettingsExperienceCircleView;
                        TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        touchHoldDelayExperienceFragment.getClass();
                        if (touchSettingsExperienceCircleView2.mState == 1) {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                            R.string
                                                    .touch_and_hold_enough_experience_test_complete_content_description);
                        } else {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_testing_area_content_description)
                                            + "\n"
                                            + touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_test_content_description);
                        }
                        touchSettingsExperienceCircleView2.setContentDescription(str);
                    }
                });
        final TouchSettingsExperienceCircleView touchSettingsExperienceCircleView2 =
                this.mCircleView2;
        touchSettingsExperienceCircleView2.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment.3
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        String str;
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        TouchSettingsExperienceCircleView touchSettingsExperienceCircleView22 =
                                touchSettingsExperienceCircleView2;
                        TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        touchHoldDelayExperienceFragment.getClass();
                        if (touchSettingsExperienceCircleView22.mState == 1) {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                            R.string
                                                    .touch_and_hold_enough_experience_test_complete_content_description);
                        } else {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_testing_area_content_description)
                                            + "\n"
                                            + touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_test_content_description);
                        }
                        touchSettingsExperienceCircleView22.setContentDescription(str);
                    }
                });
        final TouchSettingsExperienceCircleView touchSettingsExperienceCircleView3 =
                this.mCircleView3;
        touchSettingsExperienceCircleView3.setAccessibilityDelegate(
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchHoldDelayExperienceFragment.3
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        String str;
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        TouchSettingsExperienceCircleView touchSettingsExperienceCircleView22 =
                                touchSettingsExperienceCircleView3;
                        TouchHoldDelayExperienceFragment touchHoldDelayExperienceFragment =
                                TouchHoldDelayExperienceFragment.this;
                        touchHoldDelayExperienceFragment.getClass();
                        if (touchSettingsExperienceCircleView22.mState == 1) {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                            R.string
                                                    .touch_and_hold_enough_experience_test_complete_content_description);
                        } else {
                            str =
                                    touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_testing_area_content_description)
                                            + "\n"
                                            + touchHoldDelayExperienceFragment.getString(
                                                    R.string
                                                            .touch_and_hold_enough_experience_test_content_description);
                        }
                        touchSettingsExperienceCircleView22.setContentDescription(str);
                    }
                });
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
                    R.string.touch_and_hold_delay_header, TouchAndHoldFragment.class.getName());
        } else {
            launchFragment$1(
                    R.string.touch_and_hold_delay_header,
                    TouchHoldDelayPickerFragment.class.getName());
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

    public final void resetCircles$2() {
        this.mExperienceDescription.setVisibility(0);
        this.mExperienceDescription.setText(R.string.touch_and_hold_delay_experience_description);
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
