package com.google.common.base;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class MoreObjects$ToStringHelper {
    public final String className;
    public final ValueHolder holderHead;
    public ValueHolder holderTail;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ValueHolder {
        public ValueHolder next;
        public Object value;
    }

    public MoreObjects$ToStringHelper(String str) {
        ValueHolder valueHolder = new ValueHolder();
        this.holderHead = valueHolder;
        this.holderTail = valueHolder;
        this.className = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.className);
        sb.append('{');
        ValueHolder valueHolder = this.holderHead.next;
        String str = ApnSettings.MVNO_NONE;
        while (valueHolder != null) {
            Object obj = valueHolder.value;
            sb.append(str);
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                String deepToString = Arrays.deepToString(new Object[] {obj});
                sb.append((CharSequence) deepToString, 1, deepToString.length() - 1);
            }
            valueHolder = valueHolder.next;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }
}
