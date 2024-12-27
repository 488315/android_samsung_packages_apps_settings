package com.samsung.android.settings.accessibility.dexterity.touchsettings;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import com.android.settings.R;
import com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatPreferenceFragment;
import com.samsung.android.settings.accessibility.dexterity.IgnoreRepeatUtils;
import com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class IgnoreRepeatedTouchesExperienceFragment extends TouchSettingsExperienceFragment {
    public IgnoreRepeatedTouchesExperienceCircleView mCircleView1;
    public IgnoreRepeatedTouchesExperienceCircleView mCircleView2;
    public IgnoreRepeatedTouchesExperienceCircleView mCircleView3;
    public IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3 mTimer1 = null;
    public IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3 mTimer2 = null;
    public IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3 mTimer3 = null;
    public long mLastPressTime1 = -1;
    public long mLastPressTime2 = -1;
    public long mLastPressTime3 = -1;
    public final AnonymousClass1 mResetCircle = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment.1
        @Override // java.lang.Runnable
        public final void run() {
            IgnoreRepeatedTouchesExperienceFragment.this.resetCircles();
        }
    };

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 5017;
    }

    public final float getTouchValue(Context context) {
        return IgnoreRepeatUtils.getIgnoreRepeatValue(context) * 1000.0f;
    }

    public final void ignore(IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView) {
        ignoreRepeatedTouchesExperienceCircleView.mSuccess.setVisibility(4);
        ignoreRepeatedTouchesExperienceCircleView.mIgnore.setVisibility(0);
        this.mExperienceDescription.setVisibility(0);
        this.mExperienceDescription.setText(R.string.ignore_repeated_touches_ignored);
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void initViews$1() {
        init();
        this.mExperienceDescription.setText(R.string.ignore_repeated_touches_experience_description);
        this.mDoneButton.setEnabled(isAnySuccess());
        this.mDoneButton.setOnClickListener(new IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda5(this, 0));
        this.mBackButton.setOnClickListener(new IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda5(this, 1));
        final int i = 0;
        this.mButtonContainer1.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ IgnoreRepeatedTouchesExperienceFragment f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v15, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v29, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v44, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i2 = i;
                final IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment = this.f$0;
                switch (i2) {
                    case 0:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView1.mState != 0) {
                            int action = motionEvent.getAction();
                            if (action == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView = ignoreRepeatedTouchesExperienceFragment.mCircleView1;
                                if (ignoreRepeatedTouchesExperienceCircleView.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime1) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView1);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView1.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action == 1 || action == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime1 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer1 == null) {
                                    final int i3 = 0;
                                    ?? r5 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i4 = i3;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i4) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer1 = r5;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r5, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    case 1:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView2.mState != 0) {
                            int action2 = motionEvent.getAction();
                            if (action2 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView2 = ignoreRepeatedTouchesExperienceFragment.mCircleView2;
                                if (ignoreRepeatedTouchesExperienceCircleView2.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView2)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime2) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView2);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView2.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action2 == 1 || action2 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime2 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer2 == null) {
                                    final int i4 = 2;
                                    ?? r52 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i4;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer2 = r52;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r52, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    default:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView3.mState != 0) {
                            int action3 = motionEvent.getAction();
                            if (action3 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView3 = ignoreRepeatedTouchesExperienceFragment.mCircleView3;
                                if (ignoreRepeatedTouchesExperienceCircleView3.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView3)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime3) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView3);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView3.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action3 == 1 || action3 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime3 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer3 == null) {
                                    final int i5 = 1;
                                    ?? r53 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i5;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer3 = r53;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r53, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                                ignoreRepeatedTouchesExperienceFragment.mDoneButton.setEnabled(ignoreRepeatedTouchesExperienceFragment.isAnySuccess());
                            }
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        final int i2 = 1;
        this.mButtonContainer2.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ IgnoreRepeatedTouchesExperienceFragment f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v15, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v29, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v44, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i22 = i2;
                final IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment = this.f$0;
                switch (i22) {
                    case 0:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView1.mState != 0) {
                            int action = motionEvent.getAction();
                            if (action == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView = ignoreRepeatedTouchesExperienceFragment.mCircleView1;
                                if (ignoreRepeatedTouchesExperienceCircleView.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime1) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView1);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView1.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action == 1 || action == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime1 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer1 == null) {
                                    final int i3 = 0;
                                    ?? r5 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i3;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer1 = r5;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r5, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    case 1:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView2.mState != 0) {
                            int action2 = motionEvent.getAction();
                            if (action2 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView2 = ignoreRepeatedTouchesExperienceFragment.mCircleView2;
                                if (ignoreRepeatedTouchesExperienceCircleView2.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView2)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime2) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView2);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView2.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action2 == 1 || action2 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime2 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer2 == null) {
                                    final int i4 = 2;
                                    ?? r52 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i4;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer2 = r52;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r52, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    default:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView3.mState != 0) {
                            int action3 = motionEvent.getAction();
                            if (action3 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView3 = ignoreRepeatedTouchesExperienceFragment.mCircleView3;
                                if (ignoreRepeatedTouchesExperienceCircleView3.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView3)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime3) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView3);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView3.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action3 == 1 || action3 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime3 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer3 == null) {
                                    final int i5 = 1;
                                    ?? r53 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i5;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer3 = r53;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r53, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                                ignoreRepeatedTouchesExperienceFragment.mDoneButton.setEnabled(ignoreRepeatedTouchesExperienceFragment.isAnySuccess());
                            }
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        final int i3 = 2;
        this.mButtonContainer3.setOnTouchListener(new View.OnTouchListener(this) { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda0
            public final /* synthetic */ IgnoreRepeatedTouchesExperienceFragment f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r5v15, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v29, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            /* JADX WARN: Type inference failed for: r5v44, types: [com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3, java.lang.Runnable] */
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                int i22 = i3;
                final IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment = this.f$0;
                switch (i22) {
                    case 0:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView1.mState != 0) {
                            int action = motionEvent.getAction();
                            if (action == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView = ignoreRepeatedTouchesExperienceFragment.mCircleView1;
                                if (ignoreRepeatedTouchesExperienceCircleView.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime1) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView1);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView1.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action == 1 || action == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime1 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer1 == null) {
                                    final int i32 = 0;
                                    ?? r5 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i32;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer1 = r5;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r5, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    case 1:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView2.mState != 0) {
                            int action2 = motionEvent.getAction();
                            if (action2 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView2 = ignoreRepeatedTouchesExperienceFragment.mCircleView2;
                                if (ignoreRepeatedTouchesExperienceCircleView2.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView2)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime2) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView2);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView2.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action2 == 1 || action2 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime2 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer2 == null) {
                                    final int i4 = 2;
                                    ?? r52 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i4;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer2 = r52;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r52, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                            }
                            break;
                        }
                        break;
                    default:
                        if (ignoreRepeatedTouchesExperienceFragment.mCurrenStatus != TouchSettingsExperienceFragment.Status.TRY_AGAIN && ignoreRepeatedTouchesExperienceFragment.mCircleView3.mState != 0) {
                            int action3 = motionEvent.getAction();
                            if (action3 == 0) {
                                ignoreRepeatedTouchesExperienceFragment.mExperienceDescription.setVisibility(4);
                                IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView3 = ignoreRepeatedTouchesExperienceFragment.mCircleView3;
                                if (ignoreRepeatedTouchesExperienceCircleView3.mState != 0 && !ignoreRepeatedTouchesExperienceFragment.overTime(ignoreRepeatedTouchesExperienceCircleView3)) {
                                    if (SystemClock.uptimeMillis() < ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext) + ignoreRepeatedTouchesExperienceFragment.mLastPressTime3) {
                                        ignoreRepeatedTouchesExperienceFragment.ignore(ignoreRepeatedTouchesExperienceFragment.mCircleView3);
                                        break;
                                    } else {
                                        ignoreRepeatedTouchesExperienceFragment.mCircleView3.success();
                                        ignoreRepeatedTouchesExperienceFragment.mStatusTextView.setText((CharSequence) null);
                                    }
                                }
                            } else if (action3 == 1 || action3 == 3) {
                                ignoreRepeatedTouchesExperienceFragment.mLastPressTime3 = SystemClock.uptimeMillis();
                                if (ignoreRepeatedTouchesExperienceFragment.mTimer3 == null) {
                                    final int i5 = 1;
                                    ?? r53 = new Runnable() { // from class: com.samsung.android.settings.accessibility.dexterity.touchsettings.IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda3
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            int i42 = i5;
                                            IgnoreRepeatedTouchesExperienceFragment ignoreRepeatedTouchesExperienceFragment2 = ignoreRepeatedTouchesExperienceFragment;
                                            switch (i42) {
                                                case 0:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer1);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer1 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView1);
                                                    break;
                                                case 1:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer3);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer3 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView3);
                                                    break;
                                                default:
                                                    ignoreRepeatedTouchesExperienceFragment2.mHandler.removeCallbacks(ignoreRepeatedTouchesExperienceFragment2.mTimer2);
                                                    ignoreRepeatedTouchesExperienceFragment2.mTimer2 = null;
                                                    ignoreRepeatedTouchesExperienceFragment2.timeUp(ignoreRepeatedTouchesExperienceFragment2.mCircleView2);
                                                    break;
                                            }
                                        }
                                    };
                                    ignoreRepeatedTouchesExperienceFragment.mTimer3 = r53;
                                    ignoreRepeatedTouchesExperienceFragment.mHandler.postDelayed(r53, (long) ignoreRepeatedTouchesExperienceFragment.getTouchValue(ignoreRepeatedTouchesExperienceFragment.mContext));
                                }
                                ignoreRepeatedTouchesExperienceFragment.mDoneButton.setEnabled(ignoreRepeatedTouchesExperienceFragment.isAnySuccess());
                            }
                            break;
                        }
                        break;
                }
                return true;
            }
        });
        this.mButtonContainer1.addView(this.mCircleView1, this.mParams);
        this.mButtonContainer2.addView(this.mCircleView2, this.mParams);
        this.mButtonContainer3.addView(this.mCircleView3, this.mParams);
    }

    public final boolean isAnySuccess() {
        return this.mCircleView1.mState == 1 || this.mCircleView2.mState == 1 || this.mCircleView3.mState == 1;
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, com.android.settings.core.InstrumentedFragment, com.android.settingslib.core.lifecycle.ObservableFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mCircleView1 = new IgnoreRepeatedTouchesExperienceCircleView(this.mContext);
        this.mCircleView2 = new IgnoreRepeatedTouchesExperienceCircleView(this.mContext);
        this.mCircleView3 = new IgnoreRepeatedTouchesExperienceCircleView(this.mContext);
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment
    public final void onBackPressed() {
        FragmentActivity activity = getActivity();
        if (activity == null || activity.getIntent().getExtras() == null || !activity.getIntent().getExtras().getBoolean("fromPicker", false)) {
            launchFragment$1(R.string.accessibility_ignore_repeat, IgnoreRepeatPreferenceFragment.class.getName());
        } else {
            launchFragment$1(R.string.accessibility_ignore_repeat, IgnoreRepeatedTouchesPickerFragment.class.getName());
        }
    }

    @Override // com.samsung.android.settings.accessibility.dexterity.touchsettings.TouchSettingsExperienceFragment, androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        initViews$1();
        return this.mRootView;
    }

    public final boolean overTime(IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView) {
        if (ignoreRepeatedTouchesExperienceCircleView.mState != 1) {
            return false;
        }
        ignoreRepeatedTouchesExperienceCircleView.mTimeUp.setVisibility(4);
        ignoreRepeatedTouchesExperienceCircleView.mFail.setVisibility(0);
        ignoreRepeatedTouchesExperienceCircleView.mState = 0;
        this.mExperienceDescription.setVisibility(0);
        this.mExperienceDescription.setText(R.string.ignore_repeated_touches_over_time);
        this.mStatusTextView.setVisibility(4);
        this.mDoneButton.setEnabled(isAnySuccess());
        if (this.mCircleView1.mState == 0 && this.mCircleView2.mState == 0 && this.mCircleView3.mState == 0) {
            this.mCurrenStatus = TouchSettingsExperienceFragment.Status.TRY_AGAIN;
            this.mExperienceDescription.setVisibility(0);
            this.mPressTimeContainer.setVisibility(4);
            this.mExperienceDescription.setText(getString(R.string.ignore_repeated_touches_all_fail) + "\n" + getResources().getQuantityString(R.plurals.touch_settings_test_will_reset, 5, 5));
            this.mHandler.postDelayed(this.mResetCircle, 5000L);
            this.mTryAgainContainer.setVisibility(0);
            this.mTryAgainButton.setOnClickListener(new IgnoreRepeatedTouchesExperienceFragment$$ExternalSyntheticLambda5(this, 2));
        }
        return true;
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

    public final void resetCircles() {
        this.mExperienceDescription.setVisibility(0);
        this.mExperienceDescription.setText(R.string.ignore_repeated_touches_experience_description);
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

    public final void timeUp(IgnoreRepeatedTouchesExperienceCircleView ignoreRepeatedTouchesExperienceCircleView) {
        ignoreRepeatedTouchesExperienceCircleView.mIgnore.setVisibility(4);
        ignoreRepeatedTouchesExperienceCircleView.mSuccess.setVisibility(4);
        ignoreRepeatedTouchesExperienceCircleView.mTimeUp.setVisibility(0);
        ignoreRepeatedTouchesExperienceCircleView.mState = 1;
        this.mPressTimeContainer.setVisibility(4);
        this.mStatusTextView.setVisibility(0);
        this.mStatusTextView.setText(R.string.ignore_repeated_touches_success);
        this.mDoneButton.setEnabled(true);
        this.mExperienceDescription.setVisibility(4);
    }
}
