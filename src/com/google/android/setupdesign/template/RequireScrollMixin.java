package com.google.android.setupdesign.template;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.template.Mixin;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class RequireScrollMixin implements Mixin {
    public ScrollHandlingDelegate delegate;
    public OnRequireScrollStateChangedListener listener;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public boolean requiringScrollToBottom = false;
    public boolean everScrolledToBottom = false;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnRequireScrollStateChangedListener {
        void onRequireScrollStateChanged(boolean z);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ScrollHandlingDelegate {
        void pageScrollDown();

        void startListening();
    }

    public final void notifyScrollabilityChange(boolean z) {
        if (z == this.requiringScrollToBottom) {
            return;
        }
        Handler handler = this.handler;
        final boolean z2 = true;
        if (z) {
            if (this.everScrolledToBottom) {
                return;
            }
            handler.post(
                    new Runnable() { // from class:
                                     // com.google.android.setupdesign.template.RequireScrollMixin.5
                        @Override // java.lang.Runnable
                        public final void run() {
                            OnRequireScrollStateChangedListener
                                    onRequireScrollStateChangedListener =
                                            RequireScrollMixin.this.listener;
                            if (onRequireScrollStateChangedListener != null) {
                                onRequireScrollStateChangedListener.onRequireScrollStateChanged(z2);
                            }
                        }
                    });
            this.requiringScrollToBottom = true;
            return;
        }
        final boolean z3 = false;
        handler.post(
                new Runnable() { // from class:
                                 // com.google.android.setupdesign.template.RequireScrollMixin.5
                    @Override // java.lang.Runnable
                    public final void run() {
                        OnRequireScrollStateChangedListener onRequireScrollStateChangedListener =
                                RequireScrollMixin.this.listener;
                        if (onRequireScrollStateChangedListener != null) {
                            onRequireScrollStateChangedListener.onRequireScrollStateChanged(z3);
                        }
                    }
                });
        this.requiringScrollToBottom = false;
        this.everScrolledToBottom = true;
    }

    public final void requireScrollWithButton(
            Context context,
            final FooterButton footerButton,
            int i,
            final View.OnClickListener onClickListener) {
        final CharSequence text = context.getText(i);
        final CharSequence charSequence = footerButton.text;
        footerButton.onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.google.android.setupdesign.template.RequireScrollMixin.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RequireScrollMixin requireScrollMixin = RequireScrollMixin.this;
                        if (requireScrollMixin.requiringScrollToBottom) {
                            requireScrollMixin.delegate.pageScrollDown();
                            return;
                        }
                        View.OnClickListener onClickListener2 = onClickListener;
                        if (onClickListener2 != null) {
                            onClickListener2.onClick(view);
                        }
                    }
                };
        this.listener =
                new OnRequireScrollStateChangedListener() { // from class:
                                                            // com.google.android.setupdesign.template.RequireScrollMixin.4
                    @Override // com.google.android.setupdesign.template.RequireScrollMixin.OnRequireScrollStateChangedListener
                    public final void onRequireScrollStateChanged(boolean z) {
                        FooterButton.this.setText(z ? text : charSequence);
                    }
                };
        this.delegate.startListening();
    }
}
