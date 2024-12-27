package com.samsung.android.settings.notification;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settingslib.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRadioPreference extends SecPreference {
    public boolean mChecked;
    public final Context mContext;
    public boolean mEnabled;
    public ImageView mIcon;
    public volatile boolean mPreventRadioButtonCallbacks;
    public RadioButton mRadioButton;
    public TextView mSummary;
    public TextView mText;

    public SecRadioPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mEnabled = true;
        setLayoutResource(R.layout.sec_preference_radio_with_icon_for_notification);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        RadioButton radioButton =
                (RadioButton) preferenceViewHolder.findViewById(R.id.notification_radio_button);
        this.mPreventRadioButtonCallbacks = true;
        radioButton.setChecked(this.mChecked);
        this.mPreventRadioButtonCallbacks = false;
        this.mRadioButton = radioButton;
        TextView textView =
                (TextView) preferenceViewHolder.findViewById(R.id.notification_radio_summary);
        this.mSummary = textView;
        textView.setText(getSummary());
        this.mSummary.setVisibility((!this.mChecked || getSummary() == null) ? 8 : 0);
        if (AccessibilityManager.getInstance(getContext()).isEnabled()) {
            radioButton.setClickable(false);
            radioButton.setFocusable(false);
        }
        TextView textView2 =
                (TextView) preferenceViewHolder.findViewById(R.id.notification_radio_title);
        this.mText = textView2;
        textView2.setText(getTitle());
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(this.mText);
        this.mText.setVisibility(0);
        this.mIcon = (ImageView) preferenceViewHolder.findViewById(R.id.notification_radio_icon);
        Drawable icon = getIcon();
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            if (icon == null) {
                imageView.setVisibility(8);
                return;
            }
            imageView.setImageDrawable(icon);
            this.mIcon.setVisibility(0);
            this.mIcon.setAlpha(this.mEnabled ? 1.0f : 0.6f);
        }
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        RadioButton radioButton = this.mRadioButton;
        if (radioButton == null || radioButton.isChecked()) {
            return;
        }
        RadioButton radioButton2 = this.mRadioButton;
        if (this.mPreventRadioButtonCallbacks) {
            return;
        }
        boolean z = !this.mChecked;
        this.mChecked = true;
        radioButton2.setChecked(true);
        if (z) {
            callChangeListener(Boolean.TRUE);
        }
    }

    public final void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            persistBoolean(z);
            RadioButton radioButton = this.mRadioButton;
            if (radioButton != null) {
                radioButton.setChecked(z);
            } else {
                notifyChanged();
            }
        }
    }

    public final void setIconEnabled(boolean z) {
        this.mEnabled = z;
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            if (z) {
                imageView.setAlpha(1.0f);
            } else {
                imageView.setAlpha(0.6f);
            }
        }
    }

    public final void setRadioColor(boolean z) {
        ColorStateList colorAttr =
                Utils.getColorAttr(this.mContext, android.R.attr.colorControlActivated);
        ColorStateList colorAttr2 =
                Utils.getColorAttr(this.mContext, android.R.attr.textColorPrimary);
        ImageView imageView = this.mIcon;
        if (imageView != null) {
            imageView.setImageTintList(z ? colorAttr : colorAttr2);
        }
        TextView textView = this.mText;
        if (textView != null) {
            if (!z) {
                colorAttr = colorAttr2;
            }
            textView.setTextColor(colorAttr);
        }
    }

    public SecRadioPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.apnPreferenceStyle);
    }

    public SecRadioPreference(Context context) {
        this(context, null);
    }
}
