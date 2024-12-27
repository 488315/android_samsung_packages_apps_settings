package com.samsung.android.settings.deviceinfo.statusinfo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ActivationPreference extends Preference {
    public static final String URL_ACTIVATION =
            new String(
                    Base64.decode(
                            "aHR0cHM6Ly9zdXBwb3J0LWNuLnNhbXN1bmcuY29tL3N1cHBvcnRjbi9JbWVpL2RlZmF1bHQuYXNweA==",
                            2));
    public final Context mContext;
    public String mErrMessage;
    public ActivationStatusPreferenceController$$ExternalSyntheticLambda0 mOnUpdateListener;
    public TextView textview;

    public ActivationPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setLayoutResource(R.layout.sec_activation_preference_layout);
    }

    public final void makeSummary(String str) {
        this.mErrMessage = str;
        if (this.textview == null) {
            return;
        }
        String string =
                Settings.System.getString(
                        this.mContext.getContentResolver(), "chn_activation_date");
        Resources resources = this.mContext.getResources();
        String str2 = URL_ACTIVATION;
        String string2 = resources.getString(R.string.activation_summary, str2);
        if (!TextUtils.isEmpty(string)) {
            StringBuilder m =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                            this.mContext
                                            .getResources()
                                            .getString(R.string.activation_in_date, string)
                                    + "\n");
            m.append(this.mContext.getResources().getString(R.string.activation_summary, str2));
            string2 = m.toString();
        } else if (!TextUtils.isEmpty(this.mErrMessage)) {
            StringBuilder m2 =
                    EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(
                            ComponentActivity$1$$ExternalSyntheticOutline0.m(
                                    new StringBuilder(), this.mErrMessage, "\n"));
            m2.append(this.mContext.getResources().getString(R.string.activation_summary, str2));
            string2 = m2.toString();
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string2);
        final int length = spannableStringBuilder.length();
        final int length2 = (length - str2.length()) - 1;
        spannableStringBuilder.setSpan(
                new ClickableSpan() { // from class:
                                      // com.samsung.android.settings.deviceinfo.statusinfo.ActivationPreference.1
                    @Override // android.text.style.ClickableSpan
                    public final void onClick(View view) {
                        ActivationStatusPreferenceController$$ExternalSyntheticLambda0
                                activationStatusPreferenceController$$ExternalSyntheticLambda0 =
                                        ActivationPreference.this.mOnUpdateListener;
                        if (activationStatusPreferenceController$$ExternalSyntheticLambda0
                                != null) {
                            activationStatusPreferenceController$$ExternalSyntheticLambda0.f$0
                                    .lambda$updateState$0();
                        }
                        Selection.setSelection(
                                (Spannable) ((TextView) view).getText(), length2, length);
                        view.invalidate();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.setData(Uri.parse(ActivationPreference.URL_ACTIVATION));
                        if (Utils.isIntentAvailable(ActivationPreference.this.mContext, intent)) {
                            ActivationPreference.this.mContext.startActivity(intent);
                        }
                    }

                    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                    public final void updateDrawState(TextPaint textPaint) {
                        super.updateDrawState(textPaint);
                        textPaint.setColor(
                                com.android.settingslib.Utils.getColorAttrDefaultColor(
                                        ActivationPreference.this.mContext,
                                        android.R.attr.textColorSecondary));
                    }
                },
                length2,
                length,
                33);
        this.textview.setText(spannableStringBuilder);
        this.textview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.textview = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
        makeSummary(this.mErrMessage);
    }

    @Override // androidx.preference.Preference
    public final void onClick() {
        ActivationStatusPreferenceController$$ExternalSyntheticLambda0
                activationStatusPreferenceController$$ExternalSyntheticLambda0 =
                        this.mOnUpdateListener;
        if (activationStatusPreferenceController$$ExternalSyntheticLambda0 != null) {
            activationStatusPreferenceController$$ExternalSyntheticLambda0.f$0
                    .lambda$updateState$0();
        }
    }
}
