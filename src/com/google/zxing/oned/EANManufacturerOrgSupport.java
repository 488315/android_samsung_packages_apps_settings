package com.google.zxing.oned;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class EANManufacturerOrgSupport {
    public final List ranges = new ArrayList();
    public final List countryIdentifiers = new ArrayList();

    public final void add(String str, int[] iArr) {
        ((ArrayList) this.ranges).add(iArr);
        ((ArrayList) this.countryIdentifiers).add(str);
    }
}
