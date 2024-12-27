package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.R$styleable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecUnclickablePreference extends SecPreference {
    public final Context mContext;
    public int mPositionMode;

    public SecUnclickablePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPositionMode = 0;
        this.mContext = context;
        setSelectable(false);
        if (attributeSet == null) {
            setLayoutResource(R.layout.sec_widget_preference_unclickable);
            seslSetSubheaderRoundedBackground(15);
            return;
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecUnclickable);
        this.mPositionMode = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        TypedArray obtainStyledAttributes2 =
                context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        setLayoutResource(
                obtainStyledAttributes2.getResourceId(
                        3, R.layout.sec_widget_preference_unclickable));
        obtainStyledAttributes2.recycle();
        TypedArray obtainStyledAttributes3 =
                context.obtainStyledAttributes(attributeSet, R$styleable.SecCategory);
        seslSetSubheaderRoundedBackground(obtainStyledAttributes3.getInt(0, 15));
        obtainStyledAttributes3.recycle();
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.title);
        if (textView != null) {
            textView.setText(getTitle());
            textView.setVisibility(0);
            if (this.mContext != null) {
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) textView.getLayoutParams();
                int dimensionPixelSize =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_widget_body_text_padding_start_end);
                int dimensionPixelSize2 =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_widget_preference_unclickable_margin_top);
                int dimensionPixelSize3 =
                        this.mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.sec_widget_preference_unclickable_margin_bottom);
                int i = this.mPositionMode;
                if (i == 1) {
                    dimensionPixelSize2 =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_widget_preference_unclickable_first_margin_top);
                    dimensionPixelSize3 =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_widget_preference_unclickable_first_margin_bottom);
                } else if (i == 2) {
                    dimensionPixelSize2 =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_widget_preference_unclickable_subheader_margin_top);
                    dimensionPixelSize3 =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_widget_preference_unclickable_subheader_margin_bottom);
                } else if (i == 3) {
                    dimensionPixelSize3 =
                            this.mContext
                                    .getResources()
                                    .getDimensionPixelSize(
                                            R.dimen
                                                    .sec_widget_preference_unclickable_subheader_margin_top);
                }
                layoutParams.setMargins(
                        dimensionPixelSize,
                        dimensionPixelSize2,
                        dimensionPixelSize,
                        dimensionPixelSize3);
                textView.setLayoutParams(layoutParams);
            }
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public SecUnclickablePreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecUnclickablePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecUnclickablePreference(Context context) {
        this(context, null);
    }
}
