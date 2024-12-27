package com.android.settings.panel;

import android.view.View;

import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.WindowInsetsCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SettingsPanelActivity$$ExternalSyntheticLambda0
        implements OnApplyWindowInsetsListener {
    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(
            View view, WindowInsetsCompat windowInsetsCompat) {
        int i = SettingsPanelActivity.$r8$clinit;
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), 0);
        return windowInsetsCompat;
    }
}
