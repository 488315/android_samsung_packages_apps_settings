package com.android.settingslib.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UsageProgressBarPreference extends Preference {
    public CharSequence mBottomSummary;
    public final Pattern mNumberPattern;
    public int mPercent;
    public CharSequence mTotalSummary;
    public CharSequence mUsageSummary;

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        this.mPercent = -1;
        setLayoutResource(R.layout.preference_usage_progress_bar);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.usage_summary);
        CharSequence charSequence = this.mUsageSummary;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = ApnSettings.MVNO_NONE;
        } else {
            Matcher matcher = this.mNumberPattern.matcher(charSequence);
            if (matcher.find()) {
                SpannableString spannableString = new SpannableString(charSequence);
                spannableString.setSpan(
                        new AbsoluteSizeSpan(64, true), matcher.start(), matcher.end(), 33);
                charSequence = spannableString;
            }
        }
        textView.setText(charSequence);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.total_summary);
        CharSequence charSequence2 = this.mTotalSummary;
        if (charSequence2 != null) {
            textView2.setText(charSequence2);
        }
        TextView textView3 = (TextView) preferenceViewHolder.findViewById(R.id.bottom_summary);
        if (TextUtils.isEmpty(this.mBottomSummary)) {
            textView3.setVisibility(8);
        } else {
            textView3.setVisibility(0);
            textView3.setMovementMethod(LinkMovementMethod.getInstance());
            textView3.setText(this.mBottomSummary);
            if (!TextUtils.isEmpty(null)) {
                textView3.setContentDescription(null);
            }
        }
        ProgressBar progressBar =
                (ProgressBar) preferenceViewHolder.findViewById(android.R.id.progress);
        if (this.mPercent < 0) {
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setIndeterminate(false);
            progressBar.setProgress(this.mPercent);
        }
        FrameLayout frameLayout =
                (FrameLayout) preferenceViewHolder.findViewById(R.id.custom_content);
        frameLayout.removeAllViews();
        frameLayout.setVisibility(8);
    }

    public final void setPercent(long j, long j2) {
        if (j > j2) {
            return;
        }
        if (j2 == 0) {
            if (this.mPercent != 0) {
                this.mPercent = 0;
                notifyChanged();
                return;
            }
            return;
        }
        int i = (int) ((j / j2) * 100.0d);
        if (this.mPercent == i) {
            return;
        }
        this.mPercent = i;
        notifyChanged();
    }

    public final void setUsageSummary(CharSequence charSequence) {
        if (TextUtils.equals(this.mUsageSummary, charSequence)) {
            return;
        }
        this.mUsageSummary = charSequence;
        notifyChanged();
    }

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
        this.mPercent = -1;
        setLayoutResource(R.layout.preference_usage_progress_bar);
    }

    public UsageProgressBarPreference(Context context) {
        this(context, null);
    }
}
