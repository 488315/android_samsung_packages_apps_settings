package com.samsung.android.settings.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecClickableTextPreference extends Preference {
    public int mBeginIndex;
    public ClickableSpan mClickableSpan;
    public int mEndIndex;
    public int mForegroundColor;
    public int mTextStyle;
    public TextView mTextView;

    public SecClickableTextPreference(Context context) {
        this(context, null);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(R.id.title);
        this.mTextView = textView;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableString spannableString = new SpannableString(getTitle());
        if (this.mClickableSpan != null && this.mEndIndex > this.mBeginIndex) {
            spannableString.setSpan(
                    new StyleSpan(this.mTextStyle), this.mBeginIndex, this.mEndIndex, 33);
            spannableString.setSpan(this.mClickableSpan, this.mBeginIndex, this.mEndIndex, 33);
            spannableString.setSpan(
                    new ForegroundColorSpan(this.mForegroundColor),
                    this.mBeginIndex,
                    this.mEndIndex,
                    33);
        }
        this.mTextView.setText(spannableString);
    }

    public SecClickableTextPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SecClickableTextPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextStyle = 0;
        this.mForegroundColor = -16776961;
        setLayoutResource(R.layout.sec_widget_preference_clickable_text_layout);
    }
}
