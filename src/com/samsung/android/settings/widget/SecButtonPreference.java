package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecButtonPreference extends Preference {
    public SecRoundButtonView mButton;
    public int mButtonBackGroundRes;
    public LinearLayout mButtonContainer;
    public int mButtonTextColor;
    public View.OnClickListener mOnClickListener;
    public String mTitle;

    public SecButtonPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.itemView.setSoundEffectsEnabled(false);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mButtonContainer =
                (LinearLayout) preferenceViewHolder.itemView.findViewById(R.id.button_container);
        SecRoundButtonView secRoundButtonView =
                (SecRoundButtonView) preferenceViewHolder.itemView.findViewById(R.id.button);
        this.mButton = secRoundButtonView;
        secRoundButtonView.setOnClickListener(this.mOnClickListener);
        this.mButton.setBackgroundResource(this.mButtonBackGroundRes);
        this.mButton.setTextColor(this.mButtonTextColor);
        if (TextUtils.isEmpty(this.mTitle)) {
            return;
        }
        this.mButton.setText(this.mTitle);
    }

    public final void setDefaultRoundButtonStyle() {
        Context context = getContext();
        this.mButtonBackGroundRes = R.drawable.sec_widget_default_round_button_background;
        this.mButtonTextColor = context.getColor(R.color.sec_widget_bottom_bar_button_text_color);
        notifyChanged();
    }

    public SecButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecButtonPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecButtonPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        seslSetSubheaderRoundedBackground(15);
        setSelectable(false);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        attributeSet,
                        R$styleable.SecButtonPreferenceStyle,
                        i,
                        R.style.ColorRoundButtonStyle);
        this.mButtonBackGroundRes =
                obtainStyledAttributes.getResourceId(
                        0, R.drawable.sec_widget_color_round_button_background);
        this.mButtonTextColor =
                obtainStyledAttributes.getColor(
                        1, context.getColor(R.color.sec_widget_color_round_button_text_color));
        obtainStyledAttributes.recycle();
        setLayoutResource(R.layout.sec_button_preference_layout);
    }
}
