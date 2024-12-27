package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecCheckBoxPreference;
import androidx.preference.SecPreferenceUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRadioButtonGearPreference extends SecCheckBoxPreference
        implements View.OnClickListener {
    public boolean mButtonEnableState;
    public boolean mEnableState;
    public boolean mIsSummaryColorPrimaryDark;
    public OnClickListener mListener;
    public OnGearClickListener mOnGearClickListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onRadioButtonClicked(SecRadioButtonGearPreference secRadioButtonGearPreference);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnGearClickListener {
        void onGearClick();
    }

    public SecRadioButtonGearPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.checkBoxPreferenceStyle, 0);
        this.mIsSummaryColorPrimaryDark = true;
        this.mListener = null;
        this.mEnableState = true;
        this.mButtonEnableState = true;
        setLayoutResource(
                com.android.settings.R.layout.sec_widget_preference_gear_with_front_widget_layout);
        setWidgetLayoutResource(com.android.settings.R.layout.preference_widget_radiobutton);
    }

    @Override // androidx.preference.SecCheckBoxPreference, androidx.preference.CheckBoxPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setEnabled(this.mEnableState);
        float f = 0.4f;
        preferenceViewHolder
                .findViewById(R.id.widget_frame)
                .setAlpha(this.mEnableState ? 1.0f : 0.4f);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(textView);
        boolean z = false;
        if (textView != null) {
            textView.setSingleLine(false);
            textView.setMaxLines(3);
            textView.setAlpha(this.mEnableState ? 1.0f : 0.4f);
        }
        SecPreferenceUtils.applySummaryColor(this, this.mIsSummaryColorPrimaryDark);
        LinearLayout linearLayout =
                (LinearLayout)
                        preferenceViewHolder.findViewById(
                                com.android.settings.R.id.settings_button_container);
        View findViewById =
                preferenceViewHolder.findViewById(com.android.settings.R.id.settings_button);
        if (this.mOnGearClickListener == null) {
            linearLayout.setVisibility(8);
            findViewById.setOnClickListener(null);
            return;
        }
        linearLayout.setVisibility(0);
        findViewById.setOnClickListener(this);
        if (this.mButtonEnableState && this.mEnableState) {
            f = 1.0f;
        }
        findViewById.setAlpha(f);
        if (this.mButtonEnableState && this.mEnableState) {
            z = true;
        }
        findViewById.setEnabled(z);
        if (findViewById.getContentDescription() == null
                || textView == null
                || textView.getText() == null) {
            return;
        }
        String charSequence = findViewById.getContentDescription().toString();
        String charSequence2 = textView.getText().toString();
        if (charSequence.contains(charSequence2)) {
            return;
        }
        findViewById.setContentDescription(charSequence2 + ", " + charSequence);
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public final void onClick() {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onRadioButtonClicked(this);
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        this.mEnableState = z;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        OnGearClickListener onGearClickListener;
        if (view.getId() != com.android.settings.R.id.settings_button
                || (onGearClickListener = this.mOnGearClickListener) == null) {
            return;
        }
        onGearClickListener.onGearClick();
    }
}
