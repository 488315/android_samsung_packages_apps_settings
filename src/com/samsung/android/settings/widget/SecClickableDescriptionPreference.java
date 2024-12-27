package com.samsung.android.settings.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecClickableDescriptionPreference extends Preference {
    public final int mCustomBgColor;
    public final int mCustomBottomPadding;
    public final int mCustomTopPadding;
    public TextView mTextView;

    public SecClickableDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mCustomBgColor = -1;
        this.mCustomTopPadding = -1;
        this.mCustomBottomPadding = -1;
        setLayoutResource(R.layout.sec_widget_preference_clickable_description_layout);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View view = preferenceViewHolder.itemView;
        View findViewById = view.findViewById(R.id.container);
        int i = this.mCustomBgColor;
        if (i != -1) {
            findViewById.setBackgroundColor(i);
        }
        int i2 = this.mCustomTopPadding;
        if (i2 == -1) {
            i2 = findViewById.getPaddingTop();
        }
        int i3 = this.mCustomBottomPadding;
        if (i3 == -1) {
            i3 = findViewById.getPaddingBottom();
        }
        findViewById.setPadding(
                findViewById.getPaddingLeft(), i2, findViewById.getPaddingRight(), i3);
        TextView textView = (TextView) view.findViewById(R.id.title);
        this.mTextView = textView;
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textView2 = this.mTextView;
        SpannableString spannableString = new SpannableString(null);
        spannableString.setSpan(
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.widget.SecClickableDescriptionPreference.1
                    public final /* synthetic */ String val$message = null;

                    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
                    /* renamed from: com.samsung.android.settings.widget.SecClickableDescriptionPreference$1$1, reason: invalid class name and collision with other inner class name */
                    public final class DialogInterfaceOnClickListenerC00651
                            implements DialogInterface.OnClickListener {
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }

                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view2) {
                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        SecClickableDescriptionPreference.this.getContext());
                        builder.P.mMessage = this.val$message;
                        builder.setPositiveButton(
                                R.string.common_ok, new DialogInterfaceOnClickListenerC00651());
                        builder.create().show();
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setUnderlineText(true);
                    }
                },
                0,
                0,
                33);
        spannableString.setSpan(new ForegroundColorSpan(-16776961), 0, 0, 33);
        textView2.setText(spannableString);
    }
}
