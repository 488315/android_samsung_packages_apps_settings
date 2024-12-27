package com.google.android.setupcompat.internal;

import android.content.res.Resources;
import android.view.ContextThemeWrapper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FallbackThemeWrapper extends ContextThemeWrapper {
    @Override // android.view.ContextThemeWrapper
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, false);
    }
}
