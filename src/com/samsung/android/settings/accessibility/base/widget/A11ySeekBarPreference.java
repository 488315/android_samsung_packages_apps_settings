package com.samsung.android.settings.accessibility.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SeekBarPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class A11ySeekBarPreference extends SeekBarPreference {
    public final boolean centerBasedBar;
    public final int currentMode;
    public CharSequence leftLabel;
    public ColorStateList progressTintList;
    public CharSequence rightLabel;
    public final boolean seamless;
    public CharSequence stateDescription;
    public ColorStateList tickMarkTintList;

    @SuppressLint({"RestrictedApi"})
    public A11ySeekBarPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.a11ySeekBarPreferenceStyle, R.attr.seekBarPreferenceStyle));
    }

    @Override // androidx.preference.SeekBarPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setClickable(false);
        A11ySeekBar a11ySeekBar = (A11ySeekBar) preferenceViewHolder.findViewById(R.id.seekbar);
        if (a11ySeekBar != null) {
            a11ySeekBar.setContentDescription(getTitle());
            a11ySeekBar.setStateDescription(this.stateDescription);
            a11ySeekBar.setSeamless(this.seamless);
            a11ySeekBar.centerBasedBar = this.centerBasedBar;
            a11ySeekBar.invalidate();
            a11ySeekBar.setMode(this.currentMode);
            ColorStateList colorStateList = this.tickMarkTintList;
            if (colorStateList != null) {
                a11ySeekBar.setTickMarkTintList(colorStateList);
            }
            ColorStateList colorStateList2 = this.progressTintList;
            if (colorStateList2 != null) {
                a11ySeekBar.setProgressTintList(colorStateList2);
            }
        }
        if (TextUtils.isEmpty(this.leftLabel) && TextUtils.isEmpty(this.rightLabel)) {
            View findViewById = preferenceViewHolder.findViewById(R.id.seekbar_label_area);
            if (findViewById != null) {
                findViewById.setVisibility(8);
                return;
            }
            return;
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.left_label);
        if (textView != null) {
            textView.setText(this.leftLabel);
            textView.setEnabled(isEnabled());
            textView.setImportantForAccessibility(2);
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.right_label);
        if (textView2 != null) {
            textView2.setText(this.rightLabel);
            textView2.setEnabled(isEnabled());
            textView2.setImportantForAccessibility(2);
        }
    }

    public final void setStateDescription(CharSequence charSequence) {
        this.stateDescription = charSequence;
        notifyChanged();
    }

    public A11ySeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    @SuppressLint({"PrivateResource"})
    public A11ySeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.currentMode = 0;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.A11ySeekBarPreference);
        for (int i3 = 0; i3 < obtainStyledAttributes.getIndexCount(); i3++) {
            try {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == 0) {
                    this.leftLabel = obtainStyledAttributes.getString(index);
                } else if (index == 1) {
                    this.rightLabel = obtainStyledAttributes.getString(index);
                }
            } finally {
            }
        }
        obtainStyledAttributes.close();
        obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.A11ySeekBar);
        for (int i4 = 0; i4 < obtainStyledAttributes.getIndexCount(); i4++) {
            try {
                int index2 = obtainStyledAttributes.getIndex(i4);
                if (index2 == 1) {
                    this.seamless = obtainStyledAttributes.getBoolean(index2, true);
                } else if (index2 == 0) {
                    this.centerBasedBar = obtainStyledAttributes.getBoolean(index2, false);
                } else if (index2 == 2) {
                    this.currentMode = obtainStyledAttributes.getInt(2, 0);
                }
            } finally {
            }
        }
        obtainStyledAttributes.close();
    }
}
