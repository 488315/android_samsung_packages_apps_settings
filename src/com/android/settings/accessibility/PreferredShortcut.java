package com.android.settings.accessibility;

import android.text.TextUtils;

import com.google.common.base.Objects;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PreferredShortcut {
    public static final TextUtils.SimpleStringSplitter sStringColonSplitter =
            new TextUtils.SimpleStringSplitter(':');
    public final String mComponentName;
    public final int mType;

    public PreferredShortcut(String str, int i) {
        this.mComponentName = str;
        this.mType = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || PreferredShortcut.class != obj.getClass()) {
            return false;
        }
        PreferredShortcut preferredShortcut = (PreferredShortcut) obj;
        return this.mType == preferredShortcut.mType
                && Objects.equal(this.mComponentName, preferredShortcut.mComponentName);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[] {this.mComponentName, Integer.valueOf(this.mType)});
    }

    public final String toString() {
        return this.mComponentName + ':' + this.mType;
    }
}
