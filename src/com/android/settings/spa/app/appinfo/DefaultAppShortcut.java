package com.android.settings.spa.app.appinfo;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DefaultAppShortcut {
    public final String roleName;
    public final int titleResId;

    public DefaultAppShortcut(String str, int i) {
        this.roleName = str;
        this.titleResId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultAppShortcut)) {
            return false;
        }
        DefaultAppShortcut defaultAppShortcut = (DefaultAppShortcut) obj;
        return Intrinsics.areEqual(this.roleName, defaultAppShortcut.roleName)
                && this.titleResId == defaultAppShortcut.titleResId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.titleResId) + (this.roleName.hashCode() * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DefaultAppShortcut(roleName=");
        sb.append(this.roleName);
        sb.append(", titleResId=");
        return Anchor$$ExternalSyntheticOutline0.m(sb, this.titleResId, ")");
    }
}
