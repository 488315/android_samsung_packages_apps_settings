package com.google.android.setupcompat.internal;

import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Validations {
    public static void assertLengthInRange(int i, int i2, String str, String str2) {
        Preconditions.checkNotNull(str, str2.concat(" cannot be null."));
        int length = str.length();
        boolean z = length <= i2 && length >= i;
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i, "Length of ", str2, " should be in the range [", "-");
        m.append(i2);
        m.append("]");
        Preconditions.checkArgument(m.toString(), z);
    }
}
