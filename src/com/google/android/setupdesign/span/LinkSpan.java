package com.google.android.setupdesign.span;

import android.content.ContextWrapper;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LinkSpan extends ClickableSpan {
    public final String link;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnClickListener {
        void onClick(LinkSpan linkSpan);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnLinkClickListener {}

    public LinkSpan(String str) {
        this.link = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.text.style.ClickableSpan
    public final void onClick(View view) {
        OnClickListener onClickListener;
        if (view instanceof OnLinkClickListener) {
            ((OnLinkClickListener) view).getClass();
        }
        Object context = view.getContext();
        while (true) {
            if (!(context instanceof OnClickListener)) {
                if (!(context instanceof ContextWrapper)) {
                    onClickListener = null;
                    break;
                }
                context = ((ContextWrapper) context).getBaseContext();
            } else {
                onClickListener = (OnClickListener) context;
                break;
            }
        }
        if (onClickListener != null) {
            onClickListener.onClick(this);
            view.cancelPendingInputEvents();
        } else {
            Log.w("LinkSpan", "Dropping click event. No listener attached.");
        }
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            if (text instanceof Spannable) {
                Selection.setSelection((Spannable) text, 0);
            }
        }
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(false);
    }
}
