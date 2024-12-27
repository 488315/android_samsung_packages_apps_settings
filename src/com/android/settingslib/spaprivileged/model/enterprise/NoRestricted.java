package com.android.settingslib.spaprivileged.model.enterprise;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class NoRestricted implements RestrictedMode {
    public static final NoRestricted INSTANCE = new NoRestricted();

    public final boolean equals(Object obj) {
        return this == obj || (obj instanceof NoRestricted);
    }

    public final int hashCode() {
        return -895942603;
    }

    public final String toString() {
        return "NoRestricted";
    }
}
