package com.google.uwb.support.fira;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FiraProtocolVersion {
    public final boolean equals(Object obj) {
        if (!(obj instanceof FiraProtocolVersion)) {
            return false;
        }
        ((FiraProtocolVersion) obj).getClass();
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new int[] {1, 1});
    }

    public final String toString() {
        return "1.1";
    }
}
