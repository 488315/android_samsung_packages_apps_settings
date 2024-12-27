package com.android.settings.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AnnotationSpan extends URLSpan {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final View.OnClickListener mClickListener;

    public AnnotationSpan(View.OnClickListener onClickListener) {
        super((String) null);
        this.mClickListener = onClickListener;
    }

    public static CharSequence linkify(CharSequence charSequence, LinkInfo... linkInfoArr) {
        AnnotationSpan annotationSpan;
        SpannableString spannableString = new SpannableString(charSequence);
        Annotation[] annotationArr =
                (Annotation[])
                        spannableString.getSpans(0, spannableString.length(), Annotation.class);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(spannableString);
        for (Annotation annotation : annotationArr) {
            String value = annotation.getValue();
            int spanStart = spannableString.getSpanStart(annotation);
            int spanEnd = spannableString.getSpanEnd(annotation);
            int length = linkInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    annotationSpan = null;
                    break;
                }
                LinkInfo linkInfo = linkInfoArr[i];
                if (linkInfo.mAnnotation.equals(value)) {
                    annotationSpan = new AnnotationSpan(linkInfo.mListener);
                    break;
                }
                i++;
            }
            if (annotationSpan != null) {
                spannableStringBuilder.setSpan(
                        annotationSpan,
                        spanStart,
                        spanEnd,
                        spannableString.getSpanFlags(annotationSpan));
            }
        }
        return spannableStringBuilder;
    }

    @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.mClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
    public final void updateDrawState(TextPaint textPaint) {
        super.updateDrawState(textPaint);
        textPaint.setUnderlineText(true);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LinkInfo {
        public final Boolean mActionable;
        public final String mAnnotation;
        public final View.OnClickListener mListener;

        public LinkInfo(View.OnClickListener onClickListener) {
            this.mAnnotation = "link";
            this.mListener = onClickListener;
            this.mActionable = Boolean.TRUE;
        }

        public LinkInfo(Context context, final Intent intent) {
            this.mAnnotation = "url";
            if (intent != null) {
                this.mActionable =
                        Boolean.valueOf(
                                context.getPackageManager().resolveActivity(intent, 0) != null);
            } else {
                this.mActionable = Boolean.FALSE;
            }
            if (!this.mActionable.booleanValue()) {
                this.mListener = null;
            } else {
                this.mListener =
                        new View
                                .OnClickListener() { // from class:
                                                     // com.android.settings.utils.AnnotationSpan$LinkInfo$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                Intent intent2 = intent;
                                try {
                                    view.startActivityForResult(intent2, 0);
                                } catch (ActivityNotFoundException unused) {
                                    Log.w(
                                            "AnnotationSpan.LinkInfo",
                                            "Activity was not found for intent, " + intent2);
                                }
                            }
                        };
            }
        }
    }
}
