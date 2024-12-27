package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecCheckBoxPreference;
import androidx.preference.SecPreferenceUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRadioButtonPreference extends SecCheckBoxPreference {
    public Drawable mIcon;
    public boolean mIsIcon;
    public OnClickListener mListener;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onRadioButtonClicked(SecRadioButtonPreference secRadioButtonPreference);
    }

    public SecRadioButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.checkBoxPreferenceStyle);
    }

    @Override // androidx.preference.SecCheckBoxPreference, androidx.preference.CheckBoxPreference,
              // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        ImageView imageView =
                (ImageView) preferenceViewHolder.findViewById(com.android.settings.R.id.image_view);
        if (this.mIsIcon) {
            imageView.setImageDrawable(this.mIcon);
            preferenceViewHolder.mDividerAllowedBelow = false;
            preferenceViewHolder.mDividerAllowedAbove = false;
        } else {
            imageView.setVisibility(8);
        }
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(textView);
        if (textView != null) {
            textView.setSingleLine(false);
            textView.setMaxLines(3);
        }
    }

    @Override // androidx.preference.TwoStatePreference, androidx.preference.Preference
    public void onClick() {
        OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onRadioButtonClicked(this);
        }
    }

    @Override // androidx.preference.Preference
    public final void setIcon(Drawable drawable) {
        this.mIsIcon = true;
        this.mIcon = drawable;
        notifyChanged();
    }

    public SecRadioButtonPreference(Context context) {
        this(context, null);
    }

    public SecRadioButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        this.mListener = null;
        this.mIsIcon = false;
        setLayoutResource(com.android.settings.R.layout.sec_widget_preference_front_widget_layout);
        setWidgetLayoutResource(com.android.settings.R.layout.preference_widget_radiobutton);
    }
}
