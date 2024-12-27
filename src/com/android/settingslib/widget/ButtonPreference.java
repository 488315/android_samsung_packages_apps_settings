package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ButtonPreference extends Preference {
    public Button mButton;
    public View.OnClickListener mClickListener;
    public int mGravity;
    public Drawable mIcon;
    public CharSequence mTitle;

    public ButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.settingslib_button_layout);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, 0);
            this.mTitle = obtainStyledAttributes.getText(4);
            this.mIcon = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 =
                    context.obtainStyledAttributes(
                            attributeSet,
                            com.android.settingslib.widget.preference.button.R$styleable
                                    .ButtonPreference,
                            i,
                            0);
            this.mGravity = obtainStyledAttributes2.getInt(0, 8388611);
            obtainStyledAttributes2.recycle();
        }
    }

    @Override // androidx.preference.Preference
    public final CharSequence getTitle() {
        return this.mTitle;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        this.mButton = (Button) preferenceViewHolder.findViewById(R.id.settingslib_button);
        setTitle(this.mTitle);
        setIcon(this.mIcon);
        setGravity(this.mGravity);
        View.OnClickListener onClickListener = this.mClickListener;
        this.mClickListener = onClickListener;
        Button button = this.mButton;
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }
        if (this.mButton != null) {
            boolean isSelectable = isSelectable();
            this.mButton.setFocusable(isSelectable);
            this.mButton.setClickable(isSelectable);
            this.mButton.setEnabled(isEnabled());
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        Button button = this.mButton;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    public final void setGravity(int i) {
        if (i == 1 || i == 16 || i == 17) {
            this.mGravity = 1;
        } else {
            this.mGravity = 8388611;
        }
        Button button = this.mButton;
        if (button != null) {
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.gravity = this.mGravity;
            this.mButton.setLayoutParams(layoutParams);
        }
    }

    @Override // androidx.preference.Preference
    public final void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (this.mButton == null || drawable == null) {
            return;
        }
        int applyDimension =
                (int)
                        TypedValue.applyDimension(
                                1, 24.0f, getContext().getResources().getDisplayMetrics());
        drawable.setBounds(0, 0, applyDimension, applyDimension);
        this.mButton.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawable, (Drawable) null, (Drawable) null, (Drawable) null);
    }

    @Override // androidx.preference.Preference
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        Button button = this.mButton;
        if (button != null) {
            button.setText(charSequence);
        }
    }

    public ButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ButtonPreference(Context context) {
        this(context, null);
    }
}
