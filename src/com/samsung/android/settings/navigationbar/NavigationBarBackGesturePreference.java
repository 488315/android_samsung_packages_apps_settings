package com.samsung.android.settings.navigationbar;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import androidx.preference.PreferenceViewHolder;
import com.android.settings.widget.SeekBarPreference;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.navigationbar.NavigationBarBackGestureIndicatorDrawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NavigationBarBackGesturePreference extends SeekBarPreference implements SeekBar.OnSeekBarChangeListener {
    public AnonymousClass1 mAccessibilityDelegate;
    public int mBackGestureInset;
    public int mBottomGestureInset;
    public int mDetailType;
    public NavigationBarBackGestureIndicatorView mIndicatorView;
    public boolean mIsSubDisplay;
    public NavigationBarOverlayInteractor mOverlayInteractor;
    public boolean mSeekByTouch;
    public SeekBar mSensitivitySeekBar;
    public boolean mSupportBottomGesture;
    public int mValue;

    public NavigationBarBackGesturePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mValue = 1;
    }

    public final void applyBackGestureSensitivity() {
        boolean z = this.mIsSubDisplay;
        String str = z ? "navigation_bar_back_gesture_sensitivity_sub" : "navigation_bar_back_gesture_sensitivity";
        int i = z ? 749003 : 749001;
        Settings.Global.putInt(getContext().getContentResolver(), str, this.mValue);
        NavigationBarSettingsUtil.setSensitivityInsetScale(getContext());
        LoggingHelper.insertEventLogging(7470, i, this.mValue);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8, types: [android.view.View$AccessibilityDelegate, com.samsung.android.settings.navigationbar.NavigationBarBackGesturePreference$1] */
    @Override // com.android.settings.widget.SeekBarPreference, com.android.settingslib.RestrictedPreference, com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        if (this.mOverlayInteractor == null) {
            this.mOverlayInteractor = new NavigationBarOverlayInteractor(getContext());
        }
        this.mSensitivitySeekBar = (SeekBar) preferenceViewHolder.findViewById(R.id.textClassifier);
        if (this.mAccessibilityDelegate == null) {
            ?? r3 = new View.AccessibilityDelegate() { // from class: com.samsung.android.settings.navigationbar.NavigationBarBackGesturePreference.1
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    accessibilityNodeInfo.setScrollable(true);
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                }

                @Override // android.view.View.AccessibilityDelegate
                public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                    if (i != 4096 && i != 8192) {
                        return false;
                    }
                    NavigationBarBackGesturePreference navigationBarBackGesturePreference = NavigationBarBackGesturePreference.this;
                    navigationBarBackGesturePreference.mValue = navigationBarBackGesturePreference.mSensitivitySeekBar.getProgress();
                    NavigationBarBackGesturePreference.this.applyBackGestureSensitivity();
                    return super.performAccessibilityAction(view, i, bundle);
                }
            };
            this.mAccessibilityDelegate = r3;
            this.mSensitivitySeekBar.setAccessibilityDelegate(r3);
        }
        this.mSupportBottomGesture = Settings.Global.getInt(getContext().getContentResolver(), "navigation_bar_gesture_hint", 1) == 0 && !NavigationBarSettingsUtil.isSupportSearcle();
        updateDetailInfo();
    }

    @Override // com.android.settings.widget.SeekBarPreference, android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable;
        if (z) {
            seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            float f = this.mBackGestureInset;
            Context context = getContext();
            if (NavigationBarSettingsUtil.sInsetScale == null) {
                NavigationBarSettingsUtil.sInsetScale = NavigationBarSettingsUtil.getFloatArray(context.getResources().obtainTypedArray(R.array.config_ntpServers));
            }
            int i2 = (int) (f * NavigationBarSettingsUtil.sInsetScale[i]);
            if (this.mSupportBottomGesture) {
                float f2 = this.mBottomGestureInset;
                Context context2 = getContext();
                if (NavigationBarSettingsUtil.sBottomInsetScale == null) {
                    NavigationBarSettingsUtil.sBottomInsetScale = NavigationBarSettingsUtil.getFloatArray(context2.getResources().obtainTypedArray(R.array.config_rearDisplayDeviceStates));
                }
                int i3 = (int) (NavigationBarSettingsUtil.sBottomInsetScale[i] * f2);
                NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView = this.mIndicatorView;
                if (this.mDetailType == 1) {
                    navigationBarBackGestureIndicatorView.mLeftDrawable.setWidth(navigationBarBackGestureIndicatorView.mCutoutL + i2, i3);
                    navigationBarBackGestureIndicatorView.mRightDrawable.setWidth(i2 + navigationBarBackGestureIndicatorView.mCutoutR, i3);
                }
                if (navigationBarBackGestureIndicatorView.mNavBarCanMove) {
                    int i4 = navigationBarBackGestureIndicatorView.mRotation;
                    if (i4 != 0) {
                        if (i4 == 1) {
                            navigationBarBackGestureIndicatorDrawable = navigationBarBackGestureIndicatorView.mRightDrawable;
                        } else if (i4 != 2) {
                            if (i4 == 3) {
                                navigationBarBackGestureIndicatorDrawable = navigationBarBackGestureIndicatorView.mLeftDrawable;
                            }
                        }
                        navigationBarBackGestureIndicatorDrawable.setWidth(i3, 0);
                    }
                    navigationBarBackGestureIndicatorDrawable = navigationBarBackGestureIndicatorView.mBottomDrawable;
                    navigationBarBackGestureIndicatorDrawable.setWidth(i3, 0);
                }
                navigationBarBackGestureIndicatorDrawable = navigationBarBackGestureIndicatorView.mBottomDrawable;
                navigationBarBackGestureIndicatorDrawable.setWidth(i3, 0);
            } else {
                NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView2 = this.mIndicatorView;
                NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable2 = navigationBarBackGestureIndicatorView2.mLeftDrawable;
                int i5 = navigationBarBackGestureIndicatorView2.mCutoutL + i2;
                if (i5 == 0) {
                    navigationBarBackGestureIndicatorDrawable2.mHandler.sendEmptyMessage(3);
                } else {
                    NavigationBarBackGestureIndicatorDrawable.AnonymousClass1 anonymousClass1 = navigationBarBackGestureIndicatorDrawable2.mHandler;
                    anonymousClass1.sendMessage(anonymousClass1.obtainMessage(1, i5, 0));
                }
                NavigationBarBackGestureIndicatorDrawable navigationBarBackGestureIndicatorDrawable3 = navigationBarBackGestureIndicatorView2.mRightDrawable;
                int i6 = i2 + navigationBarBackGestureIndicatorView2.mCutoutR;
                if (i6 == 0) {
                    navigationBarBackGestureIndicatorDrawable3.mHandler.sendEmptyMessage(3);
                } else {
                    NavigationBarBackGestureIndicatorDrawable.AnonymousClass1 anonymousClass12 = navigationBarBackGestureIndicatorDrawable3.mHandler;
                    anonymousClass12.sendMessage(anonymousClass12.obtainMessage(1, i6, 0));
                }
            }
            if (this.mSeekByTouch) {
                return;
            }
            this.mValue = seekBar.getProgress();
            applyBackGestureSensitivity();
        }
    }

    @Override // com.android.settings.widget.SeekBarPreference, android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        this.mSeekByTouch = true;
    }

    @Override // com.android.settings.widget.SeekBarPreference, android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        NavigationBarBackGestureIndicatorView navigationBarBackGestureIndicatorView = this.mIndicatorView;
        navigationBarBackGestureIndicatorView.mLeftDrawable.setWidth(0, 0);
        navigationBarBackGestureIndicatorView.mRightDrawable.setWidth(0, 0);
        navigationBarBackGestureIndicatorView.mBottomDrawable.setWidth(0, 0);
        this.mValue = seekBar.getProgress();
        applyBackGestureSensitivity();
        this.mSeekByTouch = false;
    }

    public final void updateDetailInfo() {
        boolean z = getContext().getResources().getConfiguration().semDisplayDeviceType == 5;
        this.mIsSubDisplay = z;
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        if (this.mSensitivitySeekBar != null) {
            this.mValue = Settings.Global.getInt(getContext().getContentResolver(), z ? "navigation_bar_back_gesture_sensitivity_sub" : "navigation_bar_back_gesture_sensitivity", 1);
            this.mSensitivitySeekBar.setMax(3);
            this.mSensitivitySeekBar.setProgress(this.mValue);
            this.mSensitivitySeekBar.setOnSeekBarChangeListener(this);
        }
        if (this.mSupportBottomGesture) {
            this.mDetailType = Settings.Global.getInt(getContext().getContentResolver(), "navigation_bar_gesture_detail_type", 1);
            this.mBottomGestureInset = getContext().getResources().getDimensionPixelSize(R.dimen.resolver_icon_margin);
        }
        this.mBackGestureInset = getContext().getResources().getDimensionPixelSize(R.dimen.config_letterboxTabletopModePositionMultiplier);
    }
}
