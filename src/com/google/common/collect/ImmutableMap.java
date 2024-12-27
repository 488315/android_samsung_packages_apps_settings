package com.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final long serialVersionUID = 912559;
    public transient ImmutableSet entrySet;
    public transient ImmutableSet keySet;
    public transient ImmutableCollection values;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public Object[] alternatingKeysAndValues;
        public DuplicateKey duplicateKey;
        public int size = 0;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class DuplicateKey {
            public final Object key;
            public final Object value1;
            public final Object value2;

            public DuplicateKey(Object obj, Object obj2, Object obj3) {
                this.key = obj;
                this.value1 = obj2;
                this.value2 = obj3;
            }

            public final IllegalArgumentException exception() {
                StringBuilder sb = new StringBuilder("Multiple entries with same key: ");
                Object obj = this.key;
                sb.append(obj);
                sb.append("=");
                sb.append(this.value1);
                sb.append(" and ");
                sb.append(obj);
                sb.append("=");
                sb.append(this.value2);
                return new IllegalArgumentException(sb.toString());
            }
        }

        public Builder(int i) {
            this.alternatingKeysAndValues = new Object[i * 2];
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x018a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final com.google.common.collect.ImmutableMap buildOrThrow() {
            /*
                Method dump skipped, instructions count: 440
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.google.common.collect.ImmutableMap.Builder.buildOrThrow():com.google.common.collect.ImmutableMap");
        }

        public final void put(Object obj, Object obj2) {
            int i = (this.size + 1) * 2;
            Object[] objArr = this.alternatingKeysAndValues;
            if (i > objArr.length) {
                this.alternatingKeysAndValues =
                        Arrays.copyOf(
                                objArr,
                                ImmutableCollection.Builder.expandedCapacity(objArr.length, i));
            }
            if (obj == null) {
                throw new NullPointerException("null key in entry: null=" + obj2);
            }
            if (obj2 == null) {
                throw new NullPointerException("null value in entry: " + obj + "=null");
            }
            Object[] objArr2 = this.alternatingKeysAndValues;
            int i2 = this.size;
            int i3 = i2 * 2;
            objArr2[i3] = obj;
            objArr2[i3 + 1] = obj2;
            this.size = i2 + 1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object keys;
        private final Object values;

        public SerializedForm(ImmutableMap immutableMap) {
            Object[] objArr = new Object[immutableMap.size()];
            Object[] objArr2 = new Object[immutableMap.size()];
            UnmodifiableIterator it = immutableMap.entrySet().iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                objArr[i] = entry.getKey();
                objArr2[i] = entry.getValue();
                i++;
            }
            this.keys = objArr;
            this.values = objArr2;
        }

        public final Object readResolve() {
            Object obj = this.keys;
            if (obj instanceof ImmutableSet) {
                ImmutableSet immutableSet = (ImmutableSet) obj;
                ImmutableCollection immutableCollection = (ImmutableCollection) this.values;
                Builder builder = new Builder(immutableSet.size());
                UnmodifiableIterator it = immutableSet.iterator();
                UnmodifiableIterator it2 = immutableCollection.iterator();
                while (it.hasNext()) {
                    builder.put(it.next(), it2.next());
                }
                return builder.buildOrThrow();
            }
            Object[] objArr = (Object[]) obj;
            Object[] objArr2 = (Object[]) this.values;
            Builder builder2 = new Builder(objArr.length);
            for (int i = 0; i < objArr.length; i++) {
                builder2.put(objArr[i], objArr2[i]);
            }
            return builder2.buildOrThrow();
        }
    }

    static {
        Map.Entry[] entryArr = new Map.Entry[0];
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        ImmutableCollection immutableCollection = this.values;
        if (immutableCollection == null) {
            RegularImmutableMap regularImmutableMap = (RegularImmutableMap) this;
            RegularImmutableMap.KeysOrValuesAsList keysOrValuesAsList =
                    new RegularImmutableMap.KeysOrValuesAsList(
                            1,
                            regularImmutableMap.size,
                            regularImmutableMap.alternatingKeysAndValues);
            this.values = keysOrValuesAsList;
            immutableCollection = keysOrValuesAsList;
        }
        return immutableCollection.contains(obj);
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    public abstract Object get(Object obj);

    @Override // java.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public final int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public final Set keySet() {
        ImmutableSet immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        RegularImmutableMap regularImmutableMap = (RegularImmutableMap) this;
        RegularImmutableMap.KeySet keySet =
                new RegularImmutableMap.KeySet(
                        regularImmutableMap,
                        new RegularImmutableMap.KeysOrValuesAsList(
                                0,
                                regularImmutableMap.size,
                                regularImmutableMap.alternatingKeysAndValues));
        this.keySet = keySet;
        return keySet;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        int size = size();
        CollectPreconditions.checkNonnegative(size, "size");
        StringBuilder sb = new StringBuilder((int) Math.min(size * 8, 1073741824L));
        sb.append('{');
        boolean z = true;
        for (Map.Entry<K, V> entry : entrySet()) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }

    @Override // java.util.Map
    public final Collection values() {
        ImmutableCollection immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        RegularImmutableMap regularImmutableMap = (RegularImmutableMap) this;
        RegularImmutableMap.KeysOrValuesAsList keysOrValuesAsList =
                new RegularImmutableMap.KeysOrValuesAsList(
                        1, regularImmutableMap.size, regularImmutableMap.alternatingKeysAndValues);
        this.values = keysOrValuesAsList;
        return keysOrValuesAsList;
    }

    public Object writeReplace() {
        return new SerializedForm(this);
    }

    @Override // java.util.Map
    public final ImmutableSet entrySet() {
        ImmutableSet immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        RegularImmutableMap regularImmutableMap = (RegularImmutableMap) this;
        RegularImmutableMap.EntrySet entrySet =
                new RegularImmutableMap.EntrySet(
                        regularImmutableMap,
                        regularImmutableMap.alternatingKeysAndValues,
                        regularImmutableMap.size);
        this.entrySet = entrySet;
        return entrySet;
    }
}
