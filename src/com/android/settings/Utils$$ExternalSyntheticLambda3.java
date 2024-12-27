package com.android.settings;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public final /* synthetic */ class Utils$$ExternalSyntheticLambda3
        implements OnApplyWindowInsetsListener {
    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(
            View view, WindowInsetsCompat windowInsetsCompat) {
        Insets insets = windowInsetsCompat.mImpl.getInsets(143);
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsetsCompat.CONSUMED;
    }
}
