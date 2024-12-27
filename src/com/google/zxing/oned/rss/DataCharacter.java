package com.google.zxing.oned.rss;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class DataCharacter {
    public final int checksumPortion;
    public final int value;

    public DataCharacter(int i, int i2) {
        this.value = i;
        this.checksumPortion = i2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof DataCharacter)) {
            return false;
        }
        DataCharacter dataCharacter = (DataCharacter) obj;
        return this.value == dataCharacter.value
                && this.checksumPortion == dataCharacter.checksumPortion;
    }

    public final int hashCode() {
        return this.checksumPortion ^ this.value;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.value);
        sb.append("(");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.checksumPortion, ')');
    }
}
