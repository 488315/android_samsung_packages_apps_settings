package com.google.common.base;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Preconditions {
    public static String badPositionIndex(int i, int i2, String str) {
        if (i < 0) {
            return Strings.lenientFormat("%s (%s) must not be negative", str, Integer.valueOf(i));
        }
        if (i2 >= 0) {
            return Strings.lenientFormat(
                    "%s (%s) must not be greater than size (%s)",
                    str, Integer.valueOf(i), Integer.valueOf(i2));
        }
        throw new IllegalArgumentException(
                SeslRoundedCorner$$ExternalSyntheticOutline0.m(i2, "negative size: "));
    }

    public static void checkArgument(boolean z) {
        if (!z) {
            throw new IllegalArgumentException();
        }
    }

    public static void checkElementIndex(int i, int i2) {
        String lenientFormat;
        if (i < 0 || i >= i2) {
            if (i < 0) {
                lenientFormat =
                        Strings.lenientFormat(
                                "%s (%s) must not be negative", "index", Integer.valueOf(i));
            } else {
                if (i2 < 0) {
                    throw new IllegalArgumentException(
                            SeslRoundedCorner$$ExternalSyntheticOutline0.m(i2, "negative size: "));
                }
                lenientFormat =
                        Strings.lenientFormat(
                                "%s (%s) must be less than size (%s)",
                                "index", Integer.valueOf(i), Integer.valueOf(i2));
            }
            throw new IndexOutOfBoundsException(lenientFormat);
        }
    }

    public static void checkNotNull(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException(String.valueOf(obj2));
        }
    }

    public static void checkPositionIndex(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException(badPositionIndex(i, i2, "index"));
        }
    }

    public static void checkPositionIndexes(int i, int i2, int i3) {
        if (i < 0 || i2 < i || i2 > i3) {
            throw new IndexOutOfBoundsException(
                    (i < 0 || i > i3)
                            ? badPositionIndex(i, i3, "start index")
                            : (i2 < 0 || i2 > i3)
                                    ? badPositionIndex(i2, i3, "end index")
                                    : Strings.lenientFormat(
                                            "end index (%s) must not be less than start index (%s)",
                                            Integer.valueOf(i2), Integer.valueOf(i)));
        }
    }

    public static void checkState(Object obj, boolean z) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    public static void checkArgument(Object obj, String str, boolean z) {
        if (!z) {
            throw new IllegalArgumentException(Strings.lenientFormat(str, obj));
        }
    }
}
