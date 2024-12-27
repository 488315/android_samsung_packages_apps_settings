package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.audio.SecSoundModeController$$ExternalSyntheticLambda0;
import com.samsung.android.settings.asbase.utils.SelectionColorUpdater;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundModePreference extends Preference {
    public final MultiButtonItem mBtnHigh;
    public final MultiButtonItem mBtnLow;
    public final MultiButtonItem mBtnMid;
    public View mCustomView;
    public View mDivider2View;
    public boolean mHighEnabled;
    public String mHighText;
    public final int mHighVisible;
    public final SecVolumeIconMotion mIconMotion;
    public boolean mIsInit;
    public SecSoundModeController$$ExternalSyntheticLambda0 mListener;
    public boolean mLowEnabled;
    public String mLowText;
    public final int mLowVisible;
    public boolean mMidEnabled;
    public String mMidText;
    public int mMidVisible;
    public TextView mOption;
    public final String mOptionText;
    public boolean mOptionVisible;
    public int mSelectedPosition;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MultiButtonItem {
        public View mButton;
        public RadioButton mDot;
        public ImageView mImgIcon;
        public ImageView mSplashView;
        public TextView mTextView;
        public ImageView mWaveL;
        public ImageView mWaveS;

        public MultiButtonItem() {}

        public final void bindView(View view, int i) {
            if (i == 0) {
                View findViewById = view.findViewById(R.id.low_button);
                this.mButton = findViewById;
                findViewById.addOnLayoutChangeListener(
                        new View
                                .OnLayoutChangeListener() { // from class:
                                                            // com.samsung.android.settings.asbase.widget.SecSoundModePreference$MultiButtonItem$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnLayoutChangeListener
                            public final void onLayoutChange(
                                    View view2,
                                    int i2,
                                    int i3,
                                    int i4,
                                    int i5,
                                    int i6,
                                    int i7,
                                    int i8,
                                    int i9) {
                                SecSoundModePreference.this.mIsInit = true;
                            }
                        });
                this.mTextView = (TextView) view.findViewById(R.id.low_button_text);
                this.mImgIcon = (ImageView) view.findViewById(R.id.volume_normal_icon);
                this.mWaveS = (ImageView) view.findViewById(R.id.volume_sound_icon_wave_s);
                this.mWaveL = (ImageView) view.findViewById(R.id.volume_sound_icon_wave_l);
                this.mDot = (RadioButton) view.findViewById(R.id.low_button_dot);
                return;
            }
            if (i == 1) {
                this.mButton = view.findViewById(R.id.mid_button);
                this.mTextView = (TextView) view.findViewById(R.id.mid_button_text);
                this.mImgIcon = (ImageView) view.findViewById(R.id.vibrate_image_view);
                this.mDot = (RadioButton) view.findViewById(R.id.mid_button_dot);
                return;
            }
            if (i != 2) {
                return;
            }
            this.mButton = view.findViewById(R.id.high_button);
            this.mTextView = (TextView) view.findViewById(R.id.high_button_text);
            this.mImgIcon = (ImageView) view.findViewById(R.id.mute_image_view);
            this.mSplashView = (ImageView) view.findViewById(R.id.volume_icon_mute_splash);
            this.mDot = (RadioButton) view.findViewById(R.id.high_button_dot);
        }

        public final void setEnabled(boolean z) {
            this.mButton.setEnabled(z);
            this.mButton.setAlpha(z ? 1.0f : 0.4f);
        }

        public final void setSelected(boolean z) {
            this.mTextView.setTypeface(
                    Typeface.create(Typeface.create("sec", 0), z ? 600 : 400, false));
            SelectionColorUpdater.updateColor(this.mTextView, z);
            SelectionColorUpdater.updateColor(this.mImgIcon, z);
            SelectionColorUpdater.updateColor(this.mWaveS, z);
            SelectionColorUpdater.updateColor(this.mWaveL, z);
            SelectionColorUpdater.updateColor(this.mSplashView, z);
            this.mDot.setChecked(z);
        }
    }

    public SecSoundModePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.layout.sec_preference_sound_mode, R.id.sound_mode);
        this.mOptionText = ApnSettings.MVNO_NONE;
        this.mLowText = ApnSettings.MVNO_NONE;
        this.mMidText = ApnSettings.MVNO_NONE;
        this.mHighText = ApnSettings.MVNO_NONE;
        this.mLowEnabled = true;
        this.mMidEnabled = true;
        this.mHighEnabled = true;
        this.mLowVisible = 0;
        this.mMidVisible = 0;
        this.mHighVisible = 0;
        this.mOptionVisible = false;
        setLayoutResource(R.layout.sec_preference_sound_mode);
        this.mIsInit = true;
        this.mBtnLow = new MultiButtonItem();
        this.mBtnMid = new MultiButtonItem();
        this.mBtnHigh = new MultiButtonItem();
        this.mIconMotion = new SecVolumeIconMotion(context);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        MultiButtonItem multiButtonItem;
        ImageView imageView;
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        this.mCustomView = view;
        this.mBtnLow.bindView(view, 0);
        this.mBtnMid.bindView(this.mCustomView, 1);
        this.mBtnHigh.bindView(this.mCustomView, 2);
        if (this.mIsInit) {
            SecVolumeIconMotion secVolumeIconMotion = this.mIconMotion;
            if (secVolumeIconMotion != null
                    && (imageView = (multiButtonItem = this.mBtnLow).mImgIcon) != null) {
                secVolumeIconMotion.startSoundAnimation(
                        1, imageView, multiButtonItem.mWaveS, multiButtonItem.mWaveL, true);
            }
            this.mIsInit = false;
        }
        this.mCustomView.setFocusable(false);
        this.mCustomView.setClickable(false);
        TextView textView = (TextView) this.mCustomView.findViewById(R.id.option);
        this.mOption = textView;
        if (textView != null) {
            boolean z = this.mOptionVisible;
            this.mOptionVisible = z;
            if (z) {
                textView.setText(this.mOptionText);
                this.mOption.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
        }
        View findViewById = this.mCustomView.findViewById(R.id.divider2);
        this.mDivider2View = findViewById;
        if (findViewById != null) {
            findViewById.setVisibility(this.mMidVisible);
        }
        final int i = 0;
        ((LinearLayout) this.mCustomView.findViewById(R.id.low_button))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.asbase.widget.SecSoundModePreference.1
                            public final /* synthetic */ SecSoundModePreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                switch (i) {
                                    case 0:
                                        SecSoundModePreference secSoundModePreference = this.this$0;
                                        secSoundModePreference.mSelectedPosition = 0;
                                        if (secSoundModePreference.mCustomView != null) {
                                            secSoundModePreference.updateButtonStatus(0);
                                        }
                                        SecSoundModePreference secSoundModePreference2 =
                                                this.this$0;
                                        int i2 = secSoundModePreference2.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda0 =
                                                        secSoundModePreference2.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda0
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda0.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference2, i2);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        SecSoundModePreference secSoundModePreference3 =
                                                this.this$0;
                                        secSoundModePreference3.mSelectedPosition = 1;
                                        if (secSoundModePreference3.mCustomView != null) {
                                            secSoundModePreference3.updateButtonStatus(1);
                                        }
                                        SecSoundModePreference secSoundModePreference4 =
                                                this.this$0;
                                        int i3 = secSoundModePreference4.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda02 =
                                                        secSoundModePreference4.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda02
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda02.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference4, i3);
                                            break;
                                        }
                                        break;
                                    default:
                                        SecSoundModePreference secSoundModePreference5 =
                                                this.this$0;
                                        secSoundModePreference5.mSelectedPosition = 2;
                                        if (secSoundModePreference5.mCustomView != null) {
                                            secSoundModePreference5.updateButtonStatus(2);
                                        }
                                        SecSoundModePreference secSoundModePreference6 =
                                                this.this$0;
                                        int i4 = secSoundModePreference6.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda03 =
                                                        secSoundModePreference6.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda03
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda03.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference6, i4);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        ((LinearLayout) this.mCustomView.findViewById(R.id.mid_button))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.asbase.widget.SecSoundModePreference.1
                            public final /* synthetic */ SecSoundModePreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                switch (i2) {
                                    case 0:
                                        SecSoundModePreference secSoundModePreference = this.this$0;
                                        secSoundModePreference.mSelectedPosition = 0;
                                        if (secSoundModePreference.mCustomView != null) {
                                            secSoundModePreference.updateButtonStatus(0);
                                        }
                                        SecSoundModePreference secSoundModePreference2 =
                                                this.this$0;
                                        int i22 = secSoundModePreference2.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda0 =
                                                        secSoundModePreference2.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda0
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda0.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference2, i22);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        SecSoundModePreference secSoundModePreference3 =
                                                this.this$0;
                                        secSoundModePreference3.mSelectedPosition = 1;
                                        if (secSoundModePreference3.mCustomView != null) {
                                            secSoundModePreference3.updateButtonStatus(1);
                                        }
                                        SecSoundModePreference secSoundModePreference4 =
                                                this.this$0;
                                        int i3 = secSoundModePreference4.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda02 =
                                                        secSoundModePreference4.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda02
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda02.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference4, i3);
                                            break;
                                        }
                                        break;
                                    default:
                                        SecSoundModePreference secSoundModePreference5 =
                                                this.this$0;
                                        secSoundModePreference5.mSelectedPosition = 2;
                                        if (secSoundModePreference5.mCustomView != null) {
                                            secSoundModePreference5.updateButtonStatus(2);
                                        }
                                        SecSoundModePreference secSoundModePreference6 =
                                                this.this$0;
                                        int i4 = secSoundModePreference6.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda03 =
                                                        secSoundModePreference6.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda03
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda03.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference6, i4);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
        final int i3 = 2;
        ((LinearLayout) this.mCustomView.findViewById(R.id.high_button))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.asbase.widget.SecSoundModePreference.1
                            public final /* synthetic */ SecSoundModePreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                switch (i3) {
                                    case 0:
                                        SecSoundModePreference secSoundModePreference = this.this$0;
                                        secSoundModePreference.mSelectedPosition = 0;
                                        if (secSoundModePreference.mCustomView != null) {
                                            secSoundModePreference.updateButtonStatus(0);
                                        }
                                        SecSoundModePreference secSoundModePreference2 =
                                                this.this$0;
                                        int i22 = secSoundModePreference2.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda0 =
                                                        secSoundModePreference2.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda0
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda0.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference2, i22);
                                            break;
                                        }
                                        break;
                                    case 1:
                                        SecSoundModePreference secSoundModePreference3 =
                                                this.this$0;
                                        secSoundModePreference3.mSelectedPosition = 1;
                                        if (secSoundModePreference3.mCustomView != null) {
                                            secSoundModePreference3.updateButtonStatus(1);
                                        }
                                        SecSoundModePreference secSoundModePreference4 =
                                                this.this$0;
                                        int i32 = secSoundModePreference4.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda02 =
                                                        secSoundModePreference4.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda02
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda02.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference4, i32);
                                            break;
                                        }
                                        break;
                                    default:
                                        SecSoundModePreference secSoundModePreference5 =
                                                this.this$0;
                                        secSoundModePreference5.mSelectedPosition = 2;
                                        if (secSoundModePreference5.mCustomView != null) {
                                            secSoundModePreference5.updateButtonStatus(2);
                                        }
                                        SecSoundModePreference secSoundModePreference6 =
                                                this.this$0;
                                        int i4 = secSoundModePreference6.mSelectedPosition;
                                        SecSoundModeController$$ExternalSyntheticLambda0
                                                secSoundModeController$$ExternalSyntheticLambda03 =
                                                        secSoundModePreference6.mListener;
                                        if (secSoundModeController$$ExternalSyntheticLambda03
                                                != null) {
                                            secSoundModeController$$ExternalSyntheticLambda03.f$0
                                                    .lambda$displayPreference$0(
                                                            secSoundModePreference6, i4);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
        this.mBtnLow.mTextView.setText(this.mLowText);
        this.mBtnMid.mTextView.setText(this.mMidText);
        this.mBtnHigh.mTextView.setText(this.mHighText);
        this.mBtnLow.setEnabled(this.mLowEnabled);
        this.mBtnMid.setEnabled(this.mMidEnabled);
        this.mBtnHigh.setEnabled(this.mHighEnabled);
        this.mBtnLow.mButton.setVisibility(this.mLowVisible);
        this.mBtnMid.mButton.setVisibility(this.mMidVisible);
        this.mBtnHigh.mButton.setVisibility(this.mHighVisible);
        updateButtonStatus(this.mSelectedPosition);
    }

    public final void setButtonEnabled(int i, boolean z) {
        if (i == 0) {
            this.mLowEnabled = z;
        } else if (i == 1) {
            this.mMidEnabled = z;
        } else if (i == 2) {
            this.mHighEnabled = z;
        }
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        setButtonEnabled(0, z);
        setButtonEnabled(1, z);
        setButtonEnabled(2, z);
    }

    public final void updateButtonStatus(int i) {
        SecVolumeIconMotion secVolumeIconMotion;
        MultiButtonItem multiButtonItem;
        ImageView imageView;
        SecVolumeIconMotion secVolumeIconMotion2;
        ImageView imageView2;
        if (i == 0) {
            if (!this.mBtnLow.mDot.isChecked()
                    && ((this.mBtnMid.mDot.isChecked() || this.mBtnHigh.mDot.isChecked())
                            && (secVolumeIconMotion = this.mIconMotion) != null
                            && (imageView = (multiButtonItem = this.mBtnLow).mImgIcon) != null)) {
                secVolumeIconMotion.startSoundAnimation(
                        1, imageView, multiButtonItem.mWaveS, multiButtonItem.mWaveL, false);
            }
            this.mBtnLow.setSelected(true);
            this.mBtnMid.setSelected(false);
            this.mBtnHigh.setSelected(false);
            return;
        }
        if (i == 1) {
            if (!this.mBtnMid.mDot.isChecked()
                    && ((this.mBtnLow.mDot.isChecked() || this.mBtnHigh.mDot.isChecked())
                            && (secVolumeIconMotion2 = this.mIconMotion) != null
                            && (imageView2 = this.mBtnMid.mImgIcon) != null)) {
                secVolumeIconMotion2.startVibrationAnimation(imageView2);
            }
            this.mBtnLow.setSelected(false);
            this.mBtnMid.setSelected(true);
            this.mBtnHigh.setSelected(false);
            return;
        }
        if (i != 2) {
            return;
        }
        if (!this.mBtnHigh.mDot.isChecked()
                && (this.mBtnLow.mDot.isChecked() || this.mBtnMid.mDot.isChecked())) {
            SecVolumeIconMotion secVolumeIconMotion3 = this.mIconMotion;
            MultiButtonItem multiButtonItem2 = this.mBtnHigh;
            secVolumeIconMotion3.startModeMuteChangeAnimation(
                    multiButtonItem2.mImgIcon, multiButtonItem2.mSplashView);
        }
        this.mBtnLow.setSelected(false);
        this.mBtnMid.setSelected(false);
        this.mBtnHigh.setSelected(true);
    }
}
