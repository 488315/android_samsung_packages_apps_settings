package com.android.settingslib.widget;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class CandidateInfo {
    public final boolean enabled;

    public CandidateInfo(boolean z) {
        this.enabled = z;
    }

    public abstract String getKey();

    public abstract Drawable loadIcon();

    public abstract CharSequence loadLabel();
}
