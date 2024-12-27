package com.google.zxing.oned.rss.expanded;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ExpandedRow {
    public final List pairs;
    public final int rowNumber;

    public ExpandedRow(int i, List list) {
        this.pairs = new ArrayList(list);
        this.rowNumber = i;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ExpandedRow)) {
            return false;
        }
        return ((ArrayList) this.pairs).equals(((ExpandedRow) obj).pairs);
    }

    public final int hashCode() {
        return ((ArrayList) this.pairs).hashCode();
    }

    public final String toString() {
        return "{ " + this.pairs + " }";
    }
}
