package com.samsung.android.settings.accessibility;

import android.app.Dialog;
import android.graphics.Rect;
import android.view.View;

import androidx.preference.Preference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecAccessibilityUtils$$ExternalSyntheticLambda0
        implements View.OnLayoutChangeListener {
    public final /* synthetic */ Dialog f$0;
    public final /* synthetic */ Preference f$1;

    public /* synthetic */ SecAccessibilityUtils$$ExternalSyntheticLambda0(
            Dialog dialog, Preference preference) {
        this.f$0 = dialog;
        this.f$1 = preference;
    }

    @Override // android.view.View.OnLayoutChangeListener
    public final void onLayoutChange(
            View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Dialog dialog = this.f$0;
        Preference preference = this.f$1;
        if (dialog.isShowing()) {
            Rect rect = new Rect();
            preference.seslGetPreferenceBounds(rect);
            dialog.semSetAnchor((rect.left + rect.right) / 2, rect.bottom);
        }
    }
}
