package com.samsung.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecActionButtonsPreference extends Preference {
    public final ButtonInfo mButton1Info;
    public final ButtonInfo mButton2Info;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ButtonInfo {
        public boolean mIsEnabled = true;
        public View.OnClickListener mListener;
        public SecRoundButtonView mNegativeButton;
        public SecRoundButtonView mPositiveButton;
        public CharSequence mText;
    }

    public SecActionButtonsPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        setLayoutResource(R.layout.sec_two_action_buttons);
        setSelectable(false);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mButton1Info.mPositiveButton =
                (SecRoundButtonView) preferenceViewHolder.findViewById(R.id.button1_positive);
        this.mButton1Info.mNegativeButton =
                (SecRoundButtonView) preferenceViewHolder.findViewById(R.id.button1_negative);
        this.mButton2Info.mPositiveButton =
                (SecRoundButtonView) preferenceViewHolder.findViewById(R.id.button2_positive);
        this.mButton2Info.mNegativeButton =
                (SecRoundButtonView) preferenceViewHolder.findViewById(R.id.button2_negative);
        ButtonInfo buttonInfo = this.mButton1Info;
        SecRoundButtonView secRoundButtonView = buttonInfo.mPositiveButton;
        secRoundButtonView.setText(buttonInfo.mText);
        secRoundButtonView.setOnClickListener(buttonInfo.mListener);
        secRoundButtonView.setEnabled(buttonInfo.mIsEnabled);
        SecRoundButtonView secRoundButtonView2 = buttonInfo.mNegativeButton;
        secRoundButtonView2.setText(buttonInfo.mText);
        secRoundButtonView2.setOnClickListener(buttonInfo.mListener);
        secRoundButtonView2.setEnabled(buttonInfo.mIsEnabled);
        buttonInfo.mPositiveButton.setVisibility(0);
        buttonInfo.mNegativeButton.setVisibility(4);
        ButtonInfo buttonInfo2 = this.mButton2Info;
        SecRoundButtonView secRoundButtonView3 = buttonInfo2.mPositiveButton;
        secRoundButtonView3.setText(buttonInfo2.mText);
        secRoundButtonView3.setOnClickListener(buttonInfo2.mListener);
        secRoundButtonView3.setEnabled(buttonInfo2.mIsEnabled);
        SecRoundButtonView secRoundButtonView4 = buttonInfo2.mNegativeButton;
        secRoundButtonView4.setText(buttonInfo2.mText);
        secRoundButtonView4.setOnClickListener(buttonInfo2.mListener);
        secRoundButtonView4.setEnabled(buttonInfo2.mIsEnabled);
        buttonInfo2.mPositiveButton.setVisibility(0);
        buttonInfo2.mNegativeButton.setVisibility(4);
    }

    public SecActionButtonsPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        setLayoutResource(R.layout.sec_two_action_buttons);
        setSelectable(false);
    }

    public SecActionButtonsPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        setLayoutResource(R.layout.sec_two_action_buttons);
        setSelectable(false);
    }

    public SecActionButtonsPreference(Context context) {
        super(context);
        this.mButton1Info = new ButtonInfo();
        this.mButton2Info = new ButtonInfo();
        setLayoutResource(R.layout.sec_two_action_buttons);
        setSelectable(false);
    }
}
