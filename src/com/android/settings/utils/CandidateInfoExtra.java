package com.android.settings.utils;

import android.graphics.drawable.Drawable;

import com.android.settingslib.widget.CandidateInfo;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CandidateInfoExtra extends CandidateInfo {
    public final String mKey;
    public final CharSequence mLabel;
    public final CharSequence mSummary;

    public CandidateInfoExtra(CharSequence charSequence, CharSequence charSequence2, String str) {
        super(true);
        this.mLabel = charSequence;
        this.mSummary = charSequence2;
        this.mKey = str;
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public final String getKey() {
        return this.mKey;
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public final Drawable loadIcon() {
        return null;
    }

    @Override // com.android.settingslib.widget.CandidateInfo
    public final CharSequence loadLabel() {
        return this.mLabel;
    }
}
