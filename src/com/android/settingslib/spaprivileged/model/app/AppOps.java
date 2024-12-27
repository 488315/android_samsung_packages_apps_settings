package com.android.settingslib.spaprivileged.model.app;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppOps {
    public final int modeForNotAllowed;
    public final int op;
    public final boolean setModeByUid;

    public AppOps(int i, int i2, boolean z, int i3) {
        i2 = (i3 & 2) != 0 ? 2 : i2;
        z = (i3 & 4) != 0 ? false : z;
        this.op = i;
        this.modeForNotAllowed = i2;
        this.setModeByUid = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppOps)) {
            return false;
        }
        AppOps appOps = (AppOps) obj;
        return this.op == appOps.op
                && this.modeForNotAllowed == appOps.modeForNotAllowed
                && this.setModeByUid == appOps.setModeByUid;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.setModeByUid)
                + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(
                        this.modeForNotAllowed, Integer.hashCode(this.op) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AppOps(op=");
        sb.append(this.op);
        sb.append(", modeForNotAllowed=");
        sb.append(this.modeForNotAllowed);
        sb.append(", setModeByUid=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.setModeByUid, ")");
    }
}
