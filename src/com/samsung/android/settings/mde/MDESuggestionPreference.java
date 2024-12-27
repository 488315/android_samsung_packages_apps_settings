package com.samsung.android.settings.mde;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;

import com.android.settings.R;
import com.android.settings.Utils;

import com.airbnb.lottie.LottieAnimationView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MDESuggestionPreference extends SecPreference {
    public final MDESuggestionPreferenceLayoutHelper mHelper;

    public MDESuggestionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mHelper = new MDESuggestionPreferenceLayoutHelper(this);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        MDESuggestionPreferenceLayoutHelper mDESuggestionPreferenceLayoutHelper = this.mHelper;
        mDESuggestionPreferenceLayoutHelper.getClass();
        mDESuggestionPreferenceLayoutHelper.mIconView =
                (ImageView) preferenceViewHolder.findViewById(R.id.icon);
        mDESuggestionPreferenceLayoutHelper.mTitle =
                (TextView) preferenceViewHolder.findViewById(R.id.title);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.exit_button);
        mDESuggestionPreferenceLayoutHelper.mExitView = imageView;
        imageView.setOnClickListener(mDESuggestionPreferenceLayoutHelper.mExitButtonClickListener);
        mDESuggestionPreferenceLayoutHelper.mSummary =
                (TextView) preferenceViewHolder.findViewById(R.id.summary);
        LinearLayout linearLayout =
                (LinearLayout) preferenceViewHolder.findViewById(R.id.action_button_container);
        mDESuggestionPreferenceLayoutHelper.mActionButtonContainer = linearLayout;
        linearLayout.setOnClickListener(
                mDESuggestionPreferenceLayoutHelper.mActionButtonClickListener);
        ViewCompat.setAccessibilityDelegate(
                mDESuggestionPreferenceLayoutHelper.mActionButtonContainer,
                new Utils.RoleDescriptionAccessibilityDelegate(
                        mDESuggestionPreferenceLayoutHelper.mContext.getString(
                                R.string.button_tts)));
        mDESuggestionPreferenceLayoutHelper.mActionButtonTextView =
                (TextView) preferenceViewHolder.findViewById(R.id.action_button_text);
        LottieAnimationView lottieAnimationView =
                (LottieAnimationView) preferenceViewHolder.findViewById(R.id.lottie_view);
        mDESuggestionPreferenceLayoutHelper.mLottieAnimationView = lottieAnimationView;
        if (lottieAnimationView.getVisibility() != 8) {
            mDESuggestionPreferenceLayoutHelper.mLottieAnimationView.setVisibility(8);
        }
        Icon icon = mDESuggestionPreferenceLayoutHelper.mIcon;
        mDESuggestionPreferenceLayoutHelper.mIcon = icon;
        ImageView imageView2 = mDESuggestionPreferenceLayoutHelper.mIconView;
        if (imageView2 != null && icon != null) {
            imageView2.setImageIcon(icon);
        }
        CharSequence charSequence = mDESuggestionPreferenceLayoutHelper.mTitleText;
        mDESuggestionPreferenceLayoutHelper.mTitleText = charSequence;
        if (mDESuggestionPreferenceLayoutHelper.mTitle != null
                && !TextUtils.isEmpty(charSequence)) {
            mDESuggestionPreferenceLayoutHelper.mTitle.setText(charSequence);
        }
        CharSequence charSequence2 = mDESuggestionPreferenceLayoutHelper.mSummaryText;
        mDESuggestionPreferenceLayoutHelper.mSummaryText = charSequence2;
        if (mDESuggestionPreferenceLayoutHelper.mSummary != null
                && !TextUtils.isEmpty(charSequence2)) {
            mDESuggestionPreferenceLayoutHelper.mSummary.setText(charSequence2);
        }
        mDESuggestionPreferenceLayoutHelper.setActionButtonText(
                mDESuggestionPreferenceLayoutHelper.mActionButtonText);
    }

    public MDESuggestionPreference(Context context) {
        super(context, null);
        this.mHelper = new MDESuggestionPreferenceLayoutHelper(this);
    }
}
