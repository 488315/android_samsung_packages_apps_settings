package com.android.settings.biometrics2.ui.view;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.setupdesign.GlifLayout;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GlifLayoutHelper {
    public final Activity activity;
    public final GlifLayout glifLayout;

    public GlifLayoutHelper(FragmentActivity fragmentActivity, GlifLayout glifLayout) {
        Intrinsics.checkNotNullParameter(glifLayout, "glifLayout");
        this.activity = fragmentActivity;
        this.glifLayout = glifLayout;
    }

    public final void setDescriptionText(CharSequence charSequence) {
        GlifLayout glifLayout = this.glifLayout;
        if (TextUtils.equals(glifLayout.getDescriptionText(), charSequence)) {
            return;
        }
        glifLayout.setDescriptionText(charSequence);
    }

    public final void setHeaderText(int i) {
        GlifLayout glifLayout = this.glifLayout;
        TextView headerTextView = glifLayout.getHeaderTextView();
        CharSequence text = headerTextView.getText();
        Activity activity = this.activity;
        CharSequence text2 = activity.getText(i);
        Intrinsics.checkNotNullExpressionValue(text2, "getText(...)");
        if (text != text2) {
            if (!TextUtils.isEmpty(text)) {
                headerTextView.setAccessibilityLiveRegion(1);
            }
            glifLayout.setHeaderText(text2);
            glifLayout.getHeaderTextView().setContentDescription(text2);
            activity.setTitle(text2);
        }
    }
}
