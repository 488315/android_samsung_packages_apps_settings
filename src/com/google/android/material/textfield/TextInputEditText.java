package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import androidx.appcompat.widget.AppCompatEditText;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class TextInputEditText extends AppCompatEditText {
    public final Rect parentRect;
    public final boolean textInputLayoutFocusedRectEnabled;

    public TextInputEditText(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void getFocusedRect(Rect rect) {
        super.getFocusedRect(rect);
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout == null || !this.textInputLayoutFocusedRectEnabled || rect == null) {
            return;
        }
        textInputLayout.getFocusedRect(this.parentRect);
        rect.bottom = this.parentRect.bottom;
    }

    @Override // android.view.View
    public final boolean getGlobalVisibleRect(Rect rect, Point point) {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout == null || !this.textInputLayoutFocusedRectEnabled) {
            return super.getGlobalVisibleRect(rect, point);
        }
        boolean globalVisibleRect = textInputLayout.getGlobalVisibleRect(rect, point);
        if (globalVisibleRect && point != null) {
            point.offset(-getScrollX(), -getScrollY());
        }
        return globalVisibleRect;
    }

    @Override // android.widget.TextView
    public final CharSequence getHint() {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout == null || !textInputLayout.isProvidingHint) {
            return super.getHint();
        }
        if (textInputLayout.hintEnabled) {
            return textInputLayout.hint;
        }
        return null;
    }

    public final TextInputLayout getTextInputLayout() {
        for (ViewParent parent = getParent(); parent instanceof View; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout != null && textInputLayout.isProvidingHint && super.getHint() == null) {
            String str = Build.MANUFACTURER;
            if ((str != null ? str.toLowerCase(Locale.ENGLISH) : ApnSettings.MVNO_NONE)
                    .equals("meizu")) {
                setHint(ApnSettings.MVNO_NONE);
            }
        }
    }

    @Override // androidx.appcompat.widget.AppCompatEditText, android.widget.TextView,
              // android.view.View
    public final InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
        if (onCreateInputConnection != null && editorInfo.hintText == null) {
            TextInputLayout textInputLayout = getTextInputLayout();
            CharSequence charSequence = null;
            if (textInputLayout != null && textInputLayout.hintEnabled) {
                charSequence = textInputLayout.hint;
            }
            editorInfo.hintText = charSequence;
        }
        return onCreateInputConnection;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        getTextInputLayout();
    }

    @Override // android.view.View
    public final boolean requestRectangleOnScreen(Rect rect) {
        TextInputLayout textInputLayout = getTextInputLayout();
        if (textInputLayout == null || !this.textInputLayoutFocusedRectEnabled || rect == null) {
            return super.requestRectangleOnScreen(rect);
        }
        this.parentRect.set(
                rect.left,
                rect.top,
                rect.right,
                rect.bottom + (textInputLayout.getHeight() - getHeight()));
        return super.requestRectangleOnScreen(this.parentRect);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.editTextStyle);
    }

    public TextInputEditText(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 0), attributeSet, i);
        this.parentRect = new Rect();
        int[] iArr = R$styleable.TextInputEditText;
        ThemeEnforcement.checkCompatibleTheme(context, attributeSet, i, 2132084683);
        ThemeEnforcement.checkTextAppearance(
                context, attributeSet, iArr, i, 2132084683, new int[0]);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, iArr, i, 2132084683);
        this.textInputLayoutFocusedRectEnabled = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }
}
