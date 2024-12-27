package com.google.android.setupdesign.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Annotation;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupdesign.accessibility.LinkAccessibilityHelper;
import com.google.android.setupdesign.span.BoldLinkSpan;
import com.google.android.setupdesign.span.LinkSpan;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class RichTextView extends AppCompatTextView implements LinkSpan.OnLinkClickListener {
    static Typeface spanTypeface;
    public final LinkAccessibilityHelper accessibilityHelper;

    public RichTextView(Context context) {
        super(context, null);
        if (isInEditMode()) {
            return;
        }
        LinkAccessibilityHelper linkAccessibilityHelper =
                new LinkAccessibilityHelper(new AccessibilityDelegateCompat());
        this.accessibilityHelper = linkAccessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, linkAccessibilityHelper);
    }

    public static void setSpanTypeface(Typeface typeface) {
        spanTypeface = typeface;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        LinkAccessibilityHelper linkAccessibilityHelper = this.accessibilityHelper;
        if (linkAccessibilityHelper != null) {
            AccessibilityDelegateCompat accessibilityDelegateCompat =
                    linkAccessibilityHelper.delegate;
            if ((accessibilityDelegateCompat instanceof ExploreByTouchHelper)
                    && ((ExploreByTouchHelper) accessibilityDelegateCompat)
                            .dispatchHoverEvent(motionEvent)) {
                return true;
            }
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView,
              // android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        for (Drawable drawable : getCompoundDrawablesRelative()) {
            if (drawable != null && drawable.setState(drawableState)) {
                invalidateDrawable(drawable);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        MovementMethod movementMethod = getMovementMethod();
        if (movementMethod instanceof TouchableMovementMethod) {
            TouchableMovementMethod touchableMovementMethod =
                    (TouchableMovementMethod) movementMethod;
            if (((TouchableMovementMethod.TouchableLinkMovementMethod) touchableMovementMethod)
                            .lastEvent
                    == motionEvent) {
                return ((TouchableMovementMethod.TouchableLinkMovementMethod)
                                touchableMovementMethod)
                        .lastEventResult;
            }
        }
        return onTouchEvent;
    }

    @Override // android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        Context context = getContext();
        if (charSequence instanceof Spanned) {
            SpannableString spannableString = new SpannableString(charSequence);
            for (Annotation annotation :
                    (Annotation[])
                            spannableString.getSpans(
                                    0, spannableString.length(), Annotation.class)) {
                String key = annotation.getKey();
                if ("textAppearance".equals(key)) {
                    int identifier =
                            context.getResources()
                                    .getIdentifier(
                                            annotation.getValue(),
                                            "style",
                                            context.getPackageName());
                    if (identifier == 0) {
                        RecordingInputConnection$$ExternalSyntheticOutline0.m(
                                identifier, "Cannot find resource: ", "RichTextView");
                    }
                    Object[] objArr = {new TextAppearanceSpan(context, identifier)};
                    int spanStart = spannableString.getSpanStart(annotation);
                    int spanEnd = spannableString.getSpanEnd(annotation);
                    spannableString.removeSpan(annotation);
                    spannableString.setSpan(objArr[0], spanStart, spanEnd, 0);
                } else if ("link".equals(key)) {
                    Object[] objArr2 = {
                        BuildCompatUtils.isAtLeastU()
                                ? new BoldLinkSpan(context, annotation.getValue())
                                : new LinkSpan(annotation.getValue()),
                        spanTypeface != null
                                ? new TypefaceSpan(spanTypeface)
                                : new TypefaceSpan("sans-serif-medium")
                    };
                    int spanStart2 = spannableString.getSpanStart(annotation);
                    int spanEnd2 = spannableString.getSpanEnd(annotation);
                    spannableString.removeSpan(annotation);
                    for (int i = 0; i < 2; i++) {
                        spannableString.setSpan(objArr2[i], spanStart2, spanEnd2, 0);
                    }
                }
            }
            charSequence = spannableString;
        }
        super.setText(charSequence, bufferType);
        boolean z =
                (charSequence instanceof Spanned)
                        && ((ClickableSpan[])
                                                ((Spanned) charSequence)
                                                        .getSpans(
                                                                0,
                                                                charSequence.length(),
                                                                ClickableSpan.class))
                                        .length
                                > 0;
        if (z) {
            TouchableMovementMethod.TouchableLinkMovementMethod touchableLinkMovementMethod =
                    new TouchableMovementMethod.TouchableLinkMovementMethod();
            touchableLinkMovementMethod.lastEventResult = false;
            setMovementMethod(touchableLinkMovementMethod);
        } else {
            setMovementMethod(null);
        }
        setFocusable(z);
        setRevealOnFocusHint(false);
        setFocusableInTouchMode(z);
    }

    public RichTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (isInEditMode()) {
            return;
        }
        LinkAccessibilityHelper linkAccessibilityHelper =
                new LinkAccessibilityHelper(new AccessibilityDelegateCompat());
        this.accessibilityHelper = linkAccessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, linkAccessibilityHelper);
    }
}