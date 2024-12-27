package com.android.settings.deviceinfo;

import android.text.SpannableStringBuilder;
import android.text.style.TtsSpan;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class PhoneNumberUtil {
    public static CharSequence expandByTts(CharSequence charSequence) {
        if (charSequence == null
                || charSequence.length() <= 0
                || charSequence.length()
                        != charSequence
                                .chars()
                                .filter(new PhoneNumberUtil$$ExternalSyntheticLambda0())
                                .count()) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        spannableStringBuilder.setSpan(
                new TtsSpan.DigitsBuilder(charSequence.toString()).build(),
                0,
                spannableStringBuilder.length(),
                33);
        return spannableStringBuilder;
    }
}
