package com.google.zxing.pdf417.decoder;

import com.google.zxing.pdf417.PDF417Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class BarcodeValue {
    public final Map values = new HashMap();

    public final int[] getValue() {
        ArrayList arrayList = new ArrayList();
        int i = -1;
        for (Map.Entry entry : ((HashMap) this.values).entrySet()) {
            if (((Integer) entry.getValue()).intValue() > i) {
                i = ((Integer) entry.getValue()).intValue();
                arrayList.clear();
                arrayList.add((Integer) entry.getKey());
            } else if (((Integer) entry.getValue()).intValue() == i) {
                arrayList.add((Integer) entry.getKey());
            }
        }
        return PDF417Common.toIntArray(arrayList);
    }

    public final void setValue(int i) {
        Integer num = (Integer) ((HashMap) this.values).get(Integer.valueOf(i));
        if (num == null) {
            num = 0;
        }
        Integer valueOf = Integer.valueOf(num.intValue() + 1);
        ((HashMap) this.values).put(Integer.valueOf(i), valueOf);
    }
}
