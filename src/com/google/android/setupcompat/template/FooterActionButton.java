package com.google.android.setupcompat.template;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FooterActionButton extends Button {
    public FooterButton footerButton;
    public boolean isPrimaryButtonStyle;

    public FooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isPrimaryButtonStyle = false;
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        FooterButton footerButton;
        View.OnClickListener onClickListener;
        if (motionEvent.getAction() == 0
                && (footerButton = this.footerButton) != null
                && !footerButton.enabled
                && footerButton.visibility == 0
                && (onClickListener = footerButton.onClickListenerWhenDisabled) != null) {
            onClickListener.onClick(this);
        }
        return super.onTouchEvent(motionEvent);
    }
}
