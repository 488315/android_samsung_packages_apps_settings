package com.samsung.android.settings.widget;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.SeslTouchTargetDelegate;

import com.android.settings.Utils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRoundButtonView extends Button {
    public static final /* synthetic */ int $r8$clinit = 0;

    public SecRoundButtonView(Context context) {
        this(context, null);
    }

    @Override // android.widget.TextView, android.view.View
    public final void setEnabled(boolean z) {
        if (z) {
            setAlpha(1.0f);
        } else {
            setAlpha(0.4f);
        }
        super.setEnabled(z);
    }

    @Override // android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence.toString(), bufferType);
    }

    public SecRoundButtonView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.buttonStyle);
    }

    public SecRoundButtonView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecRoundButtonView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // com.samsung.android.settings.widget.SecRoundButtonView.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                View view = (View) SecRoundButtonView.this.getParent();
                                if (view == null) {
                                    return;
                                }
                                final SecRoundButtonView secRoundButtonView =
                                        SecRoundButtonView.this;
                                int i3 = SecRoundButtonView.$r8$clinit;
                                final View view2 = (View) secRoundButtonView.getParent();
                                view2.post(
                                        new Runnable() { // from class:
                                                         // com.samsung.android.settings.widget.SecRoundButtonView.2
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                SeslTouchTargetDelegate seslTouchTargetDelegate =
                                                        new SeslTouchTargetDelegate(view2);
                                                Rect rect = new Rect();
                                                Rect rect2 = new Rect();
                                                view2.getHitRect(rect);
                                                SecRoundButtonView.this.getHitRect(rect2);
                                                int min =
                                                        Math.min(
                                                                        SecRoundButtonView.this
                                                                                .getWidth(),
                                                                        SecRoundButtonView.this
                                                                                .getHeight())
                                                                / 2;
                                                seslTouchTargetDelegate.addTouchDelegate(
                                                        SecRoundButtonView.this,
                                                        SeslTouchTargetDelegate.ExtraInsets.of(
                                                                min, min, min, min));
                                                view2.setTouchDelegate(seslTouchTargetDelegate);
                                            }
                                        });
                                SecRoundButtonView secRoundButtonView2 = SecRoundButtonView.this;
                                int fraction =
                                        (int)
                                                (secRoundButtonView2
                                                                .getResources()
                                                                .getFraction(
                                                                        com.android.settings.R
                                                                                .fraction
                                                                                .sec_widget_round_button_max_width,
                                                                        1,
                                                                        1)
                                                        * View.MeasureSpec.getSize(
                                                                ((View)
                                                                                secRoundButtonView2
                                                                                        .getParent())
                                                                        .getMeasuredWidth()));
                                if (fraction != secRoundButtonView2.getMaxWidth()) {
                                    secRoundButtonView2.setMaxWidth(fraction);
                                }
                                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                        });
        Utils.setMaxFontScale$1(context, this);
    }
}
