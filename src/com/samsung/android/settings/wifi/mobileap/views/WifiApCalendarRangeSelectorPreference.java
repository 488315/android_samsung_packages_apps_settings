package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApCalendarRangeSelectorPreference extends Preference {
    public final Context mContext;
    public boolean mIsNextButtonEnabled;
    public final boolean mIsNonWhiteBackgroundRequired;
    public boolean mIsPreviousButtonEnabled;
    public View.OnClickListener mNextButtonClickListener;
    public View.OnClickListener mPreviousButtonClickListener;

    public WifiApCalendarRangeSelectorPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsNonWhiteBackgroundRequired = true;
        this.mIsPreviousButtonEnabled = true;
        this.mIsNextButtonEnabled = true;
        setLayoutResource(R.layout.sec_wifi_ap_calander_range_selector_layout_preference);
        this.mContext = context;
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton =
                (ImageButton) preferenceViewHolder.findViewById(R.id.previous_button);
        ImageButton imageButton2 =
                (ImageButton) preferenceViewHolder.findViewById(R.id.next_button);
        if (this.mIsNonWhiteBackgroundRequired) {
            preferenceViewHolder.itemView.setBackgroundColor(
                    this.mContext.getColor(R.color.sesl_round_and_bgcolor_light));
        } else {
            preferenceViewHolder.itemView.setBackground(
                    this.mContext.getDrawable(
                            R.drawable.sec_wifi_ap_default_preference_background));
        }
        if (this.mIsNextButtonEnabled) {
            imageButton2.setVisibility(0);
            imageButton2.setEnabled(true);
            imageButton2.setAlpha(1.0f);
        } else {
            imageButton2.setVisibility(0);
            imageButton2.setEnabled(false);
            imageButton2.setAlpha(0.4f);
        }
        if (this.mIsPreviousButtonEnabled) {
            imageButton.setVisibility(0);
            imageButton.setEnabled(true);
            imageButton.setAlpha(1.0f);
        } else {
            imageButton.setVisibility(0);
            imageButton.setEnabled(false);
            imageButton.setAlpha(0.4f);
        }
        imageButton.setOnClickListener(this.mPreviousButtonClickListener);
        imageButton2.setOnClickListener(this.mNextButtonClickListener);
    }

    public WifiApCalendarRangeSelectorPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }
}
