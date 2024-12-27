package com.samsung.android.settings.asbase.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecSoundModeRadioPreference extends Preference {
    public boolean mChecked;
    public volatile boolean mPreventRadioButtonCallbacks;
    public RadioButton mRadioButton;
    public final AnonymousClass1 mRadioChangeListener;

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.asbase.widget.SecSoundModeRadioPreference$1] */
    public SecSoundModeRadioPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R.attr.apnPreferenceStyle);
        this.mRadioChangeListener =
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.samsung.android.settings.asbase.widget.SecSoundModeRadioPreference.1
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        SecSoundModeRadioPreference secSoundModeRadioPreference =
                                SecSoundModeRadioPreference.this;
                        if (secSoundModeRadioPreference.mPreventRadioButtonCallbacks) {
                            return;
                        }
                        boolean z2 = secSoundModeRadioPreference.mChecked != z;
                        secSoundModeRadioPreference.mChecked = z;
                        compoundButton.setChecked(z);
                        if (z2) {
                            secSoundModeRadioPreference.callChangeListener(Boolean.valueOf(z));
                        }
                    }
                };
        setLayoutResource(R.layout.sec_preference_radio_with_icon);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        RadioButton radioButton =
                (RadioButton) preferenceViewHolder.findViewById(R.id.tts_engine_radiobutton);
        radioButton.setOnCheckedChangeListener(this.mRadioChangeListener);
        this.mPreventRadioButtonCallbacks = true;
        radioButton.setChecked(this.mChecked);
        this.mPreventRadioButtonCallbacks = false;
        this.mRadioButton = radioButton;
        if (AccessibilityManager.getInstance(getContext()).isEnabled()) {
            radioButton.setClickable(false);
            radioButton.setFocusable(false);
        }
        SecPreferenceUtils.applyTitleLargerTextIfNeeded(
                (TextView) preferenceViewHolder.findViewById(android.R.id.title));
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(android.R.id.icon);
        Drawable icon = getIcon();
        if (imageView != null) {
            if (icon == null) {
                imageView.setVisibility(8);
            } else {
                imageView.setImageDrawable(icon);
                imageView.setVisibility(0);
            }
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
}
