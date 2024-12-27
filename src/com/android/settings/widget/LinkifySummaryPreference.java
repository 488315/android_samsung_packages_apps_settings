package com.android.settings.widget;

import android.R;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LinkifySummaryPreference extends Preference {
    public LinkifySummaryPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.summary);
        if (textView == null || textView.getVisibility() != 0) {
            return;
        }
        CharSequence summary = getSummary();
        if (TextUtils.isEmpty(summary)) {
            return;
        }
        SpannableString spannableString = new SpannableString(summary);
        if (((ClickableSpan[])
                                spannableString.getSpans(
                                        0, spannableString.length(), ClickableSpan.class))
                        .length
                > 0) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public LinkifySummaryPreference(Context context) {
        super(context);
    }
}
