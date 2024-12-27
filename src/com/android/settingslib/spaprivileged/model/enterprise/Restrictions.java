package com.android.settingslib.spaprivileged.model.enterprise;

import android.os.UserHandle;

import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class Restrictions {
    public final EnhancedConfirmation enhancedConfirmation;
    public final List keys;
    public final int userId;

    public Restrictions(int i, List keys, EnhancedConfirmation enhancedConfirmation) {
        Intrinsics.checkNotNullParameter(keys, "keys");
        this.userId = i;
        this.keys = keys;
        this.enhancedConfirmation = enhancedConfirmation;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Restrictions)) {
            return false;
        }
        Restrictions restrictions = (Restrictions) obj;
        return this.userId == restrictions.userId
                && Intrinsics.areEqual(this.keys, restrictions.keys)
                && Intrinsics.areEqual(
                        this.enhancedConfirmation, restrictions.enhancedConfirmation);
    }

    public final int hashCode() {
        int hashCode = (this.keys.hashCode() + (Integer.hashCode(this.userId) * 31)) * 31;
        EnhancedConfirmation enhancedConfirmation = this.enhancedConfirmation;
        return hashCode + (enhancedConfirmation == null ? 0 : enhancedConfirmation.hashCode());
    }

    public final String toString() {
        return "Restrictions(userId="
                + this.userId
                + ", keys="
                + this.keys
                + ", enhancedConfirmation="
                + this.enhancedConfirmation
                + ")";
    }

    public /* synthetic */ Restrictions(int i, List list, int i2) {
        this((i2 & 1) != 0 ? UserHandle.myUserId() : i, list, (EnhancedConfirmation) null);
    }
}
