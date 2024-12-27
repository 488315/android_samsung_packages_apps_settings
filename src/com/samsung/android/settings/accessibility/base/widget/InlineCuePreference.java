package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class InlineCuePreference extends Preference {
    public View.OnClickListener firstButtonClickListener;
    public CharSequence firstButtonContentDescription;
    public CharSequence firstButtonTitle;

    public InlineCuePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayoutResource(R.layout.preference_inline_cue_layout);
        setSelectable(false);
    }

    public final void initActionButton(
            TextView textView,
            CharSequence charSequence,
            CharSequence charSequence2,
            View.OnClickListener onClickListener) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        textView.semSetButtonShapeEnabled(
                getContext().getResources().getConfiguration().semButtonShapeEnabled == 1);
        textView.setOnClickListener(onClickListener);
        textView.setText(charSequence);
        textView.setContentDescription(charSequence2);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Context context = getContext();
        TextView textView =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.inline_cue_content);
        if (textView != null) {
            textView.setText(getSummary());
            textView.setTextColor(context.getColorStateList(R.color.text_color_primary));
        }
        TextView textView2 =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.action_button_1);
        if (textView2 != null) {
            initActionButton(
                    textView2,
                    this.firstButtonTitle,
                    this.firstButtonContentDescription,
                    this.firstButtonClickListener);
        }
        TextView textView3 =
                (TextView) preferenceViewHolder.itemView.findViewById(R.id.action_button_2);
        if (textView3 != null) {
            initActionButton(textView3, null, null, null);
        }
    }
}
