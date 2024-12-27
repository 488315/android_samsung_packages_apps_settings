package com.samsung.android.settings.wifi.mobileap.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApResUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApPreference extends Preference implements Preference.OnPreferenceClickListener {
    public final Context mContext;
    public boolean mDividerAllowedAbove;
    public boolean mDividerAllowedBelow;
    public Drawable mIcon2;
    public int mIconColor;
    public int mIconHeightInPx;
    public ImageView.ScaleType mIconScaleType;
    public int mIconWidthInPx;
    public boolean mIsPaddingOverridden;
    public boolean mIsTitleSingleLine;
    public MovementMethod mMovementMethodForTitle;
    public int mPaddingBottomInDp;
    public int mPaddingLeftInDp;
    public int mPaddingRightInDp;
    public int mPaddingTopInDp;
    public int mPreferenceColor;
    public int mPreferenceType;
    public int mProgress;
    public boolean mProgressBarVisibility;
    public int mProgressColor;
    public View mRootView;
    public Drawable mSecondaryAlertIcon;
    public View.OnClickListener mSecondaryAlertIconClickListener;
    public boolean mSecondaryAlertIconVisibility;
    public Drawable mSecondaryIcon;
    public View.OnClickListener mSecondaryIconClickListener;
    public int mSecondaryIconColor;
    public boolean mSecondaryIconDividerVisibility;
    public int mSecondaryIconHeightInPx;
    public ImageView.ScaleType mSecondaryIconScaleType;
    public boolean mSecondaryIconVisibility;
    public int mSecondaryIconWidthInPx;
    public int mSummary2MaxLines;
    public String mSummary2Text;
    public final String mSummary3Text;
    public String mSummary4Text;
    public Drawable mSummaryIcon4;
    public int mSummaryMaxLines;
    public int mSummaryTextColor;
    public int mTextGravity;
    public float mTextSizeInSp;
    public Drawable mTitleIcon;
    public int mTitleTextColor;

    public WifiApPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPreferenceType = -1;
        this.mTextGravity = 8388611;
        this.mIsTitleSingleLine = true;
        this.mSecondaryIconDividerVisibility = false;
        this.mSecondaryIconClickListener = null;
        this.mSecondaryIconVisibility = false;
        this.mSecondaryAlertIconClickListener = null;
        this.mSecondaryAlertIconVisibility = true;
        this.mProgress = 0;
        this.mProgressBarVisibility = false;
        this.mProgressColor = 0;
        this.mIconScaleType = null;
        this.mIconColor = 0;
        this.mDividerAllowedAbove = true;
        this.mDividerAllowedBelow = true;
        this.mSummary2Text = ApnSettings.MVNO_NONE;
        this.mSummary3Text = ApnSettings.MVNO_NONE;
        this.mSummary4Text = ApnSettings.MVNO_NONE;
        this.mSummaryMaxLines = 2;
        this.mSummary2MaxLines = 2;
        this.mTitleTextColor = 0;
        this.mSummaryTextColor = 0;
        this.mTextSizeInSp = 0.0f;
        this.mSecondaryIconScaleType = null;
        this.mSecondaryIconColor = 0;
        this.mPreferenceColor = 2;
        this.mPaddingTopInDp = 0;
        this.mPaddingBottomInDp = 0;
        this.mPaddingLeftInDp = 0;
        this.mPaddingRightInDp = 0;
        this.mIsPaddingOverridden = false;
        this.mMovementMethodForTitle = null;
        this.mContext = context;
        setLayoutResource(R.layout.sec_wifi_ap_preference);
        this.mIconWidthInPx = WifiApSettingsUtils.convertDpToPixel(context, 40);
        this.mIconHeightInPx = WifiApSettingsUtils.convertDpToPixel(context, 40);
        this.mSecondaryIconWidthInPx = WifiApSettingsUtils.convertDpToPixel(context, 40);
        this.mSecondaryIconHeightInPx = WifiApSettingsUtils.convertDpToPixel(context, 40);
    }

    @Override // androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        ProgressBar progressBar;
        View view;
        ImageView imageView;
        boolean z;
        super.onBindViewHolder(preferenceViewHolder);
        this.mRootView = preferenceViewHolder.itemView;
        ImageView imageView2 = (ImageView) preferenceViewHolder.findViewById(android.R.id.icon);
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
        ImageView imageView3 = (ImageView) preferenceViewHolder.findViewById(R.id.icon2);
        ImageView imageView4 = (ImageView) preferenceViewHolder.findViewById(R.id.title_icon);
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(R.id.summary2_textview);
        TextView textView4 = (TextView) preferenceViewHolder.findViewById(R.id.summary3_textview);
        TextView textView5 = (TextView) preferenceViewHolder.findViewById(R.id.summary4_textview);
        ImageView imageView5 =
                (ImageView) preferenceViewHolder.findViewById(R.id.summary3_imageview);
        ImageView imageView6 =
                (ImageView) preferenceViewHolder.findViewById(R.id.summary4_imageview);
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.findViewById(R.id.text_container);
        ProgressBar progressBar2 =
                (ProgressBar) preferenceViewHolder.findViewById(R.id.progress_bar);
        View findViewById = preferenceViewHolder.findViewById(R.id.divider);
        ImageView imageView7 = (ImageView) preferenceViewHolder.findViewById(R.id.icon_secondary);
        ImageView imageView8 =
                (ImageView) preferenceViewHolder.findViewById(R.id.icon_secondary_alert_icon);
        Button button = (Button) preferenceViewHolder.findViewById(R.id.button);
        int i = this.mPreferenceColor;
        if (i == 1) {
            progressBar = progressBar2;
            view = findViewById;
            View view2 = preferenceViewHolder.itemView;
            Context context = this.mContext;
            int i2 = WifiApResUtils.$r8$clinit;
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.itemBackground, typedValue, true);
            view2.setBackgroundResource(typedValue.resourceId);
        } else if (i != 3) {
            View view3 = preferenceViewHolder.itemView;
            Context context2 = this.mContext;
            int i3 = WifiApResUtils.$r8$clinit;
            TypedValue typedValue2 = new TypedValue();
            view = findViewById;
            progressBar = progressBar2;
            context2.getTheme()
                    .resolveAttribute(android.R.attr.selectableItemBackground, typedValue2, true);
            view3.setBackgroundResource(typedValue2.resourceId);
        } else {
            progressBar = progressBar2;
            view = findViewById;
            View view4 = preferenceViewHolder.itemView;
            Context context3 = this.mContext;
            int i4 = WifiApResUtils.$r8$clinit;
            view4.setBackgroundColor(context3.getColor(R.color.sesl_round_and_bgcolor_light));
        }
        if (this.mIsPaddingOverridden) {
            preferenceViewHolder.itemView.setPadding(
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingLeftInDp),
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingTopInDp),
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingRightInDp),
                    WifiApSettingsUtils.convertDpToPixel(getContext(), this.mPaddingBottomInDp));
            preferenceViewHolder.itemView.setMinimumHeight(
                    WifiApSettingsUtils.convertDpToPixel(getContext(), 24));
        }
        preferenceViewHolder.mDividerAllowedAbove = this.mDividerAllowedAbove;
        preferenceViewHolder.mDividerAllowedBelow = this.mDividerAllowedBelow;
        if (imageView2 != null) {
            ImageView.ScaleType scaleType = this.mIconScaleType;
            if (scaleType != null) {
                imageView2.setScaleType(scaleType);
            }
            int i5 = this.mIconColor;
            if (i5 != 0) {
                imageView2.setColorFilter(i5);
            }
            imageView2.getLayoutParams().width = this.mIconWidthInPx;
            imageView2.getLayoutParams().height = this.mIconHeightInPx;
            imageView2.setEnabled(isEnabled());
        }
        if (textView != null && textView2 != null) {
            int i6 = this.mPreferenceType;
            if (i6 == 1) {
                textView.setTextAppearance(R.style.wifi_ap_small_regular_text);
                textView2.setTextAppearance(R.style.wifi_ap_big_regular_text);
            } else if (i6 == 3) {
                textView.setTextAppearance(R.style.wifi_ap_big_semi_bold_text);
                textView2.setTextAppearance(R.style.wifi_ap_small_regular_text);
            } else if (i6 == 0) {
                textView.setTextAppearance(R.style.wifi_ap_big_regular_text);
                textView2.setTextAppearance(R.style.wifi_ap_small_regular_text);
            }
        }
        if (textView != null) {
            textView.setSingleLine(this.mIsTitleSingleLine);
            int i7 = this.mTitleTextColor;
            if (i7 != 0) {
                textView.setTextColor(i7);
            }
            float f = this.mTextSizeInSp;
            if (f > 0.0f) {
                textView.setTextSize(2, f);
            }
            MovementMethod movementMethod = this.mMovementMethodForTitle;
            if (movementMethod != null) {
                textView.setMovementMethod(movementMethod);
            }
        }
        if (textView2 != null) {
            int i8 = this.mSummaryTextColor;
            if (i8 != 0) {
                textView2.setTextColor(i8);
            }
            textView2.setMaxLines(this.mSummaryMaxLines);
            if (linearLayout != null) {
                linearLayout.setGravity(this.mTextGravity);
            }
        }
        if (textView3 != null) {
            if (TextUtils.isEmpty(this.mSummary2Text)) {
                textView3.setVisibility(8);
            } else {
                textView3.setText(this.mSummary2Text);
                textView3.setVisibility(0);
            }
            textView3.setMaxLines(this.mSummary2MaxLines);
        }
        if (textView4 != null) {
            if (TextUtils.isEmpty(this.mSummary3Text)) {
                textView4.setVisibility(8);
            } else {
                textView4.setText(this.mSummary3Text);
                textView4.setVisibility(0);
                if (imageView5 != null) {
                    imageView5.setVisibility(8);
                }
            }
        }
        if (textView5 != null) {
            if (TextUtils.isEmpty(this.mSummary4Text)) {
                textView5.setVisibility(8);
            } else {
                textView5.setText(this.mSummary4Text);
                textView5.setVisibility(0);
                if (imageView6 != null) {
                    Drawable drawable = this.mSummaryIcon4;
                    if (drawable != null) {
                        imageView6.setImageDrawable(drawable);
                    }
                    imageView6.setVisibility(this.mSummaryIcon4 != null ? 0 : 8);
                }
            }
        }
        if (progressBar != null) {
            if (this.mProgressBarVisibility) {
                ProgressBar progressBar3 = progressBar;
                progressBar3.setVisibility(0);
                progressBar3.setProgress(this.mProgress);
                int i9 = this.mProgressColor;
                if (i9 != 0) {
                    progressBar3.setProgressTintList(ColorStateList.valueOf(i9));
                }
            } else {
                progressBar.setVisibility(8);
            }
        }
        if (view != null) {
            if (this.mSecondaryIconDividerVisibility) {
                view.setVisibility(0);
            } else {
                view.setVisibility(8);
            }
        }
        if (imageView7 != null) {
            ImageView.ScaleType scaleType2 = this.mSecondaryIconScaleType;
            if (scaleType2 != null) {
                imageView7.setScaleType(scaleType2);
            }
            View.OnClickListener onClickListener = this.mSecondaryIconClickListener;
            if (onClickListener != null) {
                imageView7.setOnClickListener(onClickListener);
                imageView7.setClickable(true);
                imageView7.setFocusable(true);
                imageView7.setBackgroundResource(R.drawable.sec_ic_wifi_ap_round_ripple_background);
            } else {
                imageView7.setOnClickListener(null);
                imageView7.setClickable(false);
                imageView7.setFocusable(false);
                imageView7.setBackground(null);
            }
            Drawable drawable2 = this.mSecondaryIcon;
            if (drawable2 != null) {
                imageView7.setImageDrawable(drawable2);
            }
            imageView7.setVisibility(this.mSecondaryIcon != null ? 0 : 8);
            this.mSecondaryIconVisibility = imageView7.getVisibility() == 0;
            imageView7.getLayoutParams().width = this.mSecondaryIconWidthInPx;
            imageView7.getLayoutParams().height = this.mSecondaryIconHeightInPx;
            int i10 = this.mSecondaryIconColor;
            if (i10 != 0) {
                imageView7.setColorFilter(i10);
            }
            imageView7.setEnabled(isEnabled());
        }
        if (imageView8 != null) {
            View.OnClickListener onClickListener2 = this.mSecondaryAlertIconClickListener;
            if (onClickListener2 != null) {
                imageView = imageView8;
                imageView.setOnClickListener(onClickListener2);
                z = true;
                imageView.setFocusable(true);
                imageView.setClickable(true);
            } else {
                imageView = imageView8;
                z = true;
                imageView.setOnClickListener(null);
                imageView.setFocusable(false);
                imageView.setClickable(false);
            }
            Drawable drawable3 = this.mSecondaryAlertIcon;
            if (drawable3 != null) {
                imageView.setImageDrawable(drawable3);
            }
            imageView.setVisibility(this.mSecondaryAlertIcon != null ? 0 : 8);
            this.mSecondaryAlertIconVisibility = imageView.getVisibility() == 0 ? z : false;
        }
        if (imageView4 != null) {
            Drawable drawable4 = this.mTitleIcon;
            if (drawable4 != null) {
                imageView4.setImageDrawable(drawable4);
            }
            imageView4.setFocusable(false);
            imageView4.setFocusable(false);
            imageView4.setVisibility(this.mTitleIcon != null ? 0 : 8);
        }
        if (imageView3 != null) {
            Drawable drawable5 = this.mIcon2;
            if (drawable5 != null) {
                imageView3.setImageDrawable(drawable5);
            }
            imageView3.setVisibility(this.mIcon2 == null ? 8 : 0);
        }
        if (button != null) {
            button.setVisibility(8);
        }
    }

    public boolean onPreferenceClick(Preference preference) {
        Log.i("WifiApPreference", "onPreferenceClick() - preference: " + preference);
        return true;
    }

    @Override // androidx.preference.Preference
    public final void seslSetSummaryColor(int i) {
        setSummaryTextColor(i);
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        Log.i("WifiApPreference", "setEnabled() - Triggered");
        super.setEnabled(z);
    }

    public final void setIcon2(int i) {
        Drawable drawable = this.mContext.getDrawable(i);
        if (drawable == null || this.mIcon2 == drawable) {
            return;
        }
        this.mIcon2 = drawable;
        notifyChanged();
    }

    @Override // androidx.preference.Preference
    public final void setOnPreferenceClickListener(
            Preference.OnPreferenceClickListener onPreferenceClickListener) {
        super.setOnPreferenceClickListener(onPreferenceClickListener);
        if (onPreferenceClickListener == null && this.mPreferenceColor == 2) {
            Log.i(
                    "WifiApPreference",
                    "setOnPreferenceClickListener() - setting color to  nonRipple preference"
                        + " color");
            setPreferenceColor(1);
        } else {
            if (onPreferenceClickListener == null || this.mPreferenceColor != 1) {
                return;
            }
            Log.i(
                    "WifiApPreference",
                    "setOnPreferenceClickListener() - setting color to ripple preference color");
            setPreferenceColor(2);
        }
    }

    public final void setPaddingInDp(int i, int i2) {
        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                "setPaddingInDp : 0, ", ", 0, ", i, i2, "WifiApPreference");
        this.mIsPaddingOverridden = true;
        this.mPaddingTopInDp = i;
        this.mPaddingBottomInDp = i2;
        this.mPaddingLeftInDp = 0;
        this.mPaddingRightInDp = 0;
        notifyChanged();
    }

    public final void setPreferenceColor(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "setPreferenceColor: ", "WifiApPreference");
        this.mPreferenceColor = i;
        notifyChanged();
    }

    public final void setSecondaryAlertIcon(Drawable drawable) {
        if ((drawable != null || this.mSecondaryAlertIcon == null)
                && (drawable == null || this.mSecondaryAlertIcon == drawable)) {
            return;
        }
        this.mSecondaryAlertIcon = drawable;
        notifyChanged();
    }

    public final void setSecondaryIcon(Drawable drawable) {
        if ((drawable != null || this.mSecondaryIcon == null)
                && (drawable == null || this.mSecondaryIcon == drawable)) {
            return;
        }
        this.mSecondaryIcon = drawable;
        notifyChanged();
    }

    public final void setSecondaryIconDividerVisibility(boolean z) {
        this.mSecondaryIconDividerVisibility = z;
        notifyChanged();
    }

    public final void setSpannableSummary(SpannableString spannableString) {
        Log.i("WifiApPreference", "setSpannableSummary: " + ((Object) spannableString));
        setSummary(spannableString);
    }

    public final void setSummaryTextColor(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "setSummaryTextColor() - : ", "WifiApPreference");
        this.mSummaryTextColor = i;
        notifyChanged();
    }

    public final void setTitleTextColor(int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(i, "setTextColor() - : ", "WifiApPreference");
        this.mTitleTextColor = i;
        notifyChanged();
    }

    public WifiApPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApPreference(Context context) {
        this(context, null);
    }
}
