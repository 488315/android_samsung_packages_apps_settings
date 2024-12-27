package com.google.protobuf;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SmallSortedMap$Entry implements Map.Entry, Comparable {
    public final Comparable key;
    public final /* synthetic */ SmallSortedMap$1 this$0;
    public Object value;

    public SmallSortedMap$Entry(
            SmallSortedMap$1 smallSortedMap$1, Comparable comparable, Object obj) {
        this.this$0 = smallSortedMap$1;
        this.key = comparable;
        this.value = obj;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.key.compareTo(((SmallSortedMap$Entry) obj).key);
    }

    @Override // java.util.Map.Entry
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        Comparable comparable = this.key;
        Object key = entry.getKey();
        if (comparable == null ? key == null : comparable.equals(key)) {
            Object obj2 = this.value;
            Object value = entry.getValue();
            if (obj2 == null ? value == null : obj2.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.Map.Entry
    public final Object getKey() {
        return this.key;
    }

    @Override // java.util.Map.Entry
    public final Object getValue() {
        return this.value;
    }

    @Override // java.util.Map.Entry
    public final int hashCode() {
        Comparable comparable = this.key;
        int hashCode = comparable == null ? 0 : comparable.hashCode();
        Object obj = this.value;
        return hashCode ^ (obj != null ? obj.hashCode() : 0);
    }

    @Override // java.util.Map.Entry
    public final Object setValue(Object obj) {
        this.this$0.checkMutable();
        Object obj2 = this.value;
        this.value = obj;
        return obj2;
    }

    public final String toString() {
        return this.key + "=" + this.value;
    }
}
