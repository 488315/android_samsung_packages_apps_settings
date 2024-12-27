package com.android.settingslib.collapsingtoolbar;

import android.graphics.text.LineBreakConfig;
import android.text.StaticLayout;

import com.google.android.material.appbar.CollapsingToolbarLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class CollapsingToolbarDelegate$$ExternalSyntheticLambda0
        implements CollapsingToolbarLayout.StaticLayoutBuilderConfigurer {
    @Override // com.google.android.material.appbar.CollapsingToolbarLayout.StaticLayoutBuilderConfigurer
    public final void configure(StaticLayout.Builder builder) {
        builder.setLineBreakConfig(new LineBreakConfig.Builder().setLineBreakWordStyle(1).build());
    }
}
