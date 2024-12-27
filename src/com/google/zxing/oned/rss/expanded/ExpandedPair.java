package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ExpandedPair {
    public final FinderPattern finderPattern;
    public final DataCharacter leftChar;
    public final DataCharacter rightChar;

    public ExpandedPair(
            DataCharacter dataCharacter,
            DataCharacter dataCharacter2,
            FinderPattern finderPattern) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExpandedPair)) {
            return false;
        }
        ExpandedPair expandedPair = (ExpandedPair) obj;
        return Objects.equals(this.leftChar, expandedPair.leftChar)
                && Objects.equals(this.rightChar, expandedPair.rightChar)
                && Objects.equals(this.finderPattern, expandedPair.finderPattern);
    }

    public final int hashCode() {
        return Objects.hashCode(this.finderPattern)
                ^ (Objects.hashCode(this.leftChar) ^ Objects.hashCode(this.rightChar));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        sb.append(this.leftChar);
        sb.append(" , ");
        sb.append(this.rightChar);
        sb.append(" : ");
        FinderPattern finderPattern = this.finderPattern;
        sb.append(finderPattern == null ? "null" : Integer.valueOf(finderPattern.value));
        sb.append(" ]");
        return sb.toString();
    }
}
