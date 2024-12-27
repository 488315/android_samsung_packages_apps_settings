package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApTextViewButtonLayoutPreference extends Preference {
    public boolean isEnabled;
    public boolean isPaddingSet;
    public final Context mContext;
    public boolean mDividerAllowedAbove;
    public boolean mDividerAllowedBelow;
    public int mPaddingBottomInDp;
    public int mPaddingLeftInDp;
    public int mPaddingRightInDp;
    public int mPaddingTopInDp;

    public WifiApTextViewButtonLayoutPreference(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.isEnabled = true;
        this.mDividerAllowedAbove = false;
        this.mDividerAllowedBelow = false;
        this.mPaddingTopInDp = 0;
        this.mPaddingBottomInDp = 0;
        this.mPaddingLeftInDp = 0;
        this.mPaddingRightInDp = 0;
        this.isPaddingSet = false;
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_ap_text_button_layout);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        preferenceViewHolder.mDividerAllowedAbove = this.mDividerAllowedAbove;
        preferenceViewHolder.mDividerAllowedBelow = this.mDividerAllowedBelow;
        if (this.isEnabled) {
            textView.setAlpha(1.0f);
        } else {
            TypedValue typedValue = new TypedValue();
            this.mContext
                    .getTheme()
                    .resolveAttribute(android.R.attr.disabledAlpha, typedValue, true);
            textView.setAlpha(typedValue.getFloat());
        }
        if (this.isPaddingSet) {
            int convertDpToPixel =
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingTopInDp);
            int convertDpToPixel2 =
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingBottomInDp);
            preferenceViewHolder.itemView.setPadding(
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingLeftInDp),
                    convertDpToPixel,
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingRightInDp),
                    convertDpToPixel2);
            preferenceViewHolder.itemView.setMinimumHeight(
                    WifiApSettingsUtils.convertDpToPixel(getContext(), 24));
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.isEnabled = z;
        notifyChanged();
    }

    public WifiApTextViewButtonLayoutPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApTextViewButtonLayoutPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.preferenceStyle);
    }

    public WifiApTextViewButtonLayoutPreference(Context context) {
        this(context, (AttributeSet) null);
    }

    public WifiApTextViewButtonLayoutPreference(Context context, String str) {
        this(context);
        setTitle(str);
    }
}
