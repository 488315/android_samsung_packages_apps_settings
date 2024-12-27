package com.google.android.setupdesign.span;

import android.content.Context;
import android.text.TextPaint;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BoldLinkSpan extends LinkSpan {
    static final int BOLD_TEXT_ADJUSTMENT = 300;
    public final Context context;

    public BoldLinkSpan(Context context, String str) {
        super(str);
        this.context = context;
    }

    @Override // com.google.android.setupdesign.span.LinkSpan, android.text.style.ClickableSpan,
              // android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setFakeBoldText(
                this.context.getResources().getConfiguration().fontWeightAdjustment == 300);
        textPaint.setUnderlineText(true);
    }
}
