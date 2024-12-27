package com.android.settings.accessibility;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AccessibilityQuickSettingsTooltipWindow extends PopupWindow {
    public final AnonymousClass1 mAccessibilityDelegate;
    public long mCloseDelayTimeMillis;
    public final Context mContext;
    public Handler mHandler;

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.settings.accessibility.AccessibilityQuickSettingsTooltipWindow$1] */
    public AccessibilityQuickSettingsTooltipWindow(Context context) {
        super(context);
        this.mAccessibilityDelegate =
                new View
                        .AccessibilityDelegate() { // from class:
                                                   // com.android.settings.accessibility.AccessibilityQuickSettingsTooltipWindow.1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(
                            View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        accessibilityNodeInfo.addAction(
                                new AccessibilityNodeInfo.AccessibilityAction(
                                        16,
                                        AccessibilityQuickSettingsTooltipWindow.this.mContext
                                                .getString(
                                                        R.string
                                                                .accessibility_quick_settings_tooltip_dismiss)));
                    }

                    @Override // android.view.View.AccessibilityDelegate
                    public final boolean performAccessibilityAction(
                            View view, int i, Bundle bundle) {
                        if (i != 16) {
                            return super.performAccessibilityAction(view, i, bundle);
                        }
                        AccessibilityQuickSettingsTooltipWindow.this.dismiss();
                        return true;
                    }
                };
        this.mContext = context;
    }

    @Override // android.widget.PopupWindow
    public final void dismiss() {
        super.dismiss();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    public int getAvailableWindowWidth() {
        Resources resources = this.mContext.getResources();
        return resources.getDisplayMetrics().widthPixels
                - (resources.getDimensionPixelSize(R.dimen.accessibility_qs_tooltip_margin) * 2);
    }

    public final void setup(CharSequence charSequence) {
        this.mCloseDelayTimeMillis = 0L;
        setBackgroundDrawable(
                new ColorDrawable(this.mContext.getColor(android.R.color.transparent)));
        View inflate =
                ((LayoutInflater) this.mContext.getSystemService(LayoutInflater.class))
                        .inflate(R.layout.accessibility_qs_tooltip, (ViewGroup) null);
        inflate.setFocusable(true);
        inflate.setAccessibilityDelegate(this.mAccessibilityDelegate);
        setContentView(inflate);
        ((ImageView) getContentView().findViewById(R.id.qs_illustration))
                .setImageResource(R.drawable.accessibility_auto_added_qs_tooltip_illustration);
        TextView textView = (TextView) getContentView().findViewById(R.id.qs_content);
        textView.setText(charSequence);
        textView.measure(
                View.MeasureSpec.makeMeasureSpec(getAvailableWindowWidth(), Integer.MIN_VALUE),
                View.MeasureSpec.makeMeasureSpec(0, 0));
        setWidth(textView.getMeasuredWidth());
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    @Override // android.widget.PopupWindow
    public final void showAtLocation(View view, int i, int i2, int i3) {
        super.showAtLocation(view, i, i2, i3);
        if (this.mCloseDelayTimeMillis <= 0) {
            return;
        }
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.mContext.getMainLooper());
        }
        this.mHandler.removeCallbacksAndMessages(null);
        this.mHandler.postDelayed(
                new Runnable() { // from class:
                                 // com.android.settings.accessibility.AccessibilityQuickSettingsTooltipWindow$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessibilityQuickSettingsTooltipWindow.this.dismiss();
                    }
                },
                this.mCloseDelayTimeMillis);
    }
}
