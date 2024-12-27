package com.samsung.android.settings.mde;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.preference.Preference;

import com.android.settings.R;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MDESuggestionPreferenceLayoutHelper {
    public LinearLayout mActionButtonContainer;
    public String mActionButtonText;
    public TextView mActionButtonTextView;
    public final Context mContext;
    public ImageView mExitView;
    public ImageView mIconView;
    public LottieAnimationView mLottieAnimationView;
    public String mShortcutId;
    public TextView mSummary;
    public CharSequence mSummaryText;
    public TextView mTitle;
    public CharSequence mTitleText;
    public Icon mIcon = null;
    public View.OnClickListener mExitButtonClickListener = null;
    public View.OnClickListener mActionButtonClickListener = null;

    public MDESuggestionPreferenceLayoutHelper(Preference preference) {
        this.mContext = preference.getContext();
        preference.setLayoutResource(R.layout.sec_mde_suggestion_preference);
        preference.setSelectable(false);
    }

    public final void setActionButtonText(String str) {
        this.mActionButtonText = str;
        if (this.mActionButtonTextView == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.mActionButtonTextView.setText(str);
    }
}
