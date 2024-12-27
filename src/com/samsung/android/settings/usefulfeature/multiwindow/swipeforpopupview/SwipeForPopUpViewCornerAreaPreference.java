package com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview;

import android.R;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.HapticFeedbackConstants;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.widget.SeekBarPreference;

import com.samsung.android.multiwindow.MultiWindowEdgeDetector;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SwipeForPopUpViewCornerAreaPreference extends SeekBarPreference
        implements SeekBar.OnSeekBarChangeListener {
    public final Context mContext;
    public SeekBar mCornerAreaSeekBar;
    public TextView mCornerAreaTitle;
    public SwipeForPopUpViewCornerAreaIndicatorView mIndicatorView;
    public boolean mIsSwitchEnable;
    public TextView mLeftText;
    public TextView mRightText;

    public SwipeForPopUpViewCornerAreaPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = getContext();
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mCornerAreaSeekBar = (SeekBar) preferenceViewHolder.findViewById(R.id.textClassifier);
        this.mCornerAreaTitle =
                (TextView)
                        preferenceViewHolder.findViewById(
                                com.android.settings.R.id
                                        .sec_swipe_for_popup_view_corner_area_title);
        this.mLeftText =
                (TextView) preferenceViewHolder.findViewById(com.android.settings.R.id.sec_small);
        this.mRightText =
                (TextView) preferenceViewHolder.findViewById(com.android.settings.R.id.sec_large);
        if (this.mIsSwitchEnable) {
            this.mCornerAreaSeekBar.setAlpha(1.0f);
            this.mCornerAreaTitle.setAlpha(1.0f);
            this.mLeftText.setAlpha(1.0f);
            this.mRightText.setAlpha(1.0f);
            updateProgress$3();
        } else {
            this.mCornerAreaSeekBar.setAlpha(0.4f);
            this.mCornerAreaTitle.setAlpha(0.4f);
            this.mLeftText.setAlpha(0.4f);
            this.mRightText.setAlpha(0.4f);
        }
        this.mCornerAreaSeekBar.setEnabled(this.mIsSwitchEnable);
        updateProgress$3();
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        if (z) {
            seekBar.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
            SwipeForPopUpViewCornerAreaIndicatorView swipeForPopUpViewCornerAreaIndicatorView =
                    this.mIndicatorView;
            int cornerGestureCustomValue =
                    MultiWindowEdgeDetector.getCornerGestureCustomValue(i + 1);
            swipeForPopUpViewCornerAreaIndicatorView.mLeftDrawable.setSize(
                    cornerGestureCustomValue);
            swipeForPopUpViewCornerAreaIndicatorView.mRightDrawable.setSize(
                    cornerGestureCustomValue);
            Settings.Global.putInt(
                    this.mContext.getContentResolver(),
                    "freeform_corner_gesture_level",
                    seekBar.getProgress() + 1);
            SwipeForPopUpViewCornerAreaIndicatorView swipeForPopUpViewCornerAreaIndicatorView2 =
                    this.mIndicatorView;
            swipeForPopUpViewCornerAreaIndicatorView2.mLeftDrawable.setSize(0);
            swipeForPopUpViewCornerAreaIndicatorView2.mRightDrawable.setSize(0);
        }
    }

    public final void updateProgress$3() {
        if (this.mCornerAreaSeekBar != null) {
            int i =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), "freeform_corner_gesture_level", 2);
            this.mCornerAreaSeekBar.setMax(4);
            this.mCornerAreaSeekBar.setProgress(i - 1);
            this.mCornerAreaSeekBar.setOnSeekBarChangeListener(this);
        }
    }
}
