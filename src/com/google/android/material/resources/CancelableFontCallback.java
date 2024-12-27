package com.google.android.material.resources;

import android.graphics.Typeface;

import com.google.android.material.internal.CollapsingTextHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class CancelableFontCallback extends TextAppearanceFontCallback {
    public final CollapsingTextHelper.AnonymousClass1 applyFont;
    public boolean cancelled;
    public final Typeface fallbackFont;

    public CancelableFontCallback(
            CollapsingTextHelper.AnonymousClass1 anonymousClass1, Typeface typeface) {
        this.fallbackFont = typeface;
        this.applyFont = anonymousClass1;
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrievalFailed(int i) {
        Typeface typeface = this.fallbackFont;
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(typeface);
    }

    @Override // com.google.android.material.resources.TextAppearanceFontCallback
    public final void onFontRetrieved(Typeface typeface, boolean z) {
        if (this.cancelled) {
            return;
        }
        this.applyFont.apply(typeface);
    }
}
