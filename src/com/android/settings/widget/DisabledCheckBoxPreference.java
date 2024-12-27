package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DisabledCheckBoxPreference extends CheckBoxPreference {
    public View mCheckBox;
    public boolean mEnabledCheckBox;
    public PreferenceViewHolder mViewHolder;

    public DisabledCheckBoxPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setupDisabledCheckBoxPreference(context, attributeSet, i, i2);
    }

    public final void enableCheckbox(boolean z) {
        View view;
        this.mEnabledCheckBox = z;
        if (this.mViewHolder == null || (view = this.mCheckBox) == null) {
            return;
        }
        view.setEnabled(z);
        this.mViewHolder.itemView.setEnabled(this.mEnabledCheckBox);
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mViewHolder = preferenceViewHolder;
        this.mCheckBox = preferenceViewHolder.findViewById(R.id.checkbox);
        enableCheckbox(this.mEnabledCheckBox);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        if (textView != null) {
            textView.setSingleLine(false);
            textView.setMaxLines(2);
            textView.setEllipsize(TextUtils.TruncateAt.END);
        }
    }

    @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
    public final void performClick(View view) {
        if (this.mEnabledCheckBox) {
            super.performClick(view);
        }
    }

    public final void setupDisabledCheckBoxPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet, com.android.internal.R.styleable.Preference, i, i2);
        for (int indexCount = obtainStyledAttributes.getIndexCount() - 1;
                indexCount >= 0;
                indexCount--) {
            int index = obtainStyledAttributes.getIndex(indexCount);
            if (index == 2) {
                this.mEnabledCheckBox = obtainStyledAttributes.getBoolean(index, true);
            }
        }
        obtainStyledAttributes.recycle();
        setEnabled(true);
        enableCheckbox(this.mEnabledCheckBox);
    }

    public DisabledCheckBoxPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupDisabledCheckBoxPreference(context, attributeSet, 0, 0);
    }

    public DisabledCheckBoxPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        setupDisabledCheckBoxPreference(context, attributeSet, i, 0);
    }
}
