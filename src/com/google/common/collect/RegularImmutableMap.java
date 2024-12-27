package com.google.common.collect;

import com.google.common.base.Preconditions;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class RegularImmutableMap<K, V> extends ImmutableMap<K, V> {
    public static final ImmutableMap EMPTY = new RegularImmutableMap(0, null, new Object[0]);
    private static final long serialVersionUID = 0;
    public final transient Object[] alternatingKeysAndValues;
    public final transient Object hashTable;
    public final transient int size;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
        public final transient Object[] alternatingKeysAndValues;
        public final transient int keyOffset = 0;
        public final transient ImmutableMap map;
        public final transient int size;

        public EntrySet(ImmutableMap immutableMap, Object[] objArr, int i) {
            this.map = immutableMap;
            this.alternatingKeysAndValues = objArr;
            this.size = i;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
                  // java.util.Collection
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            return value != null && value.equals(this.map.get(key));
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int copyIntoArray(Object[] objArr) {
            return asList().copyIntoArray(objArr);
        }

        @Override // com.google.common.collect.ImmutableSet
        public final ImmutableList createAsList() {
            return new ImmutableList<
                    Map.Entry<
                            Object,
                            Object>>() { // from class:
                                         // com.google.common.collect.RegularImmutableMap.EntrySet.1
                @Override // java.util.List
                public final Object get(int i) {
                    Preconditions.checkElementIndex(i, EntrySet.this.size);
                    EntrySet entrySet = EntrySet.this;
                    int i2 = i * 2;
                    Object obj = entrySet.alternatingKeysAndValues[entrySet.keyOffset + i2];
                    Objects.requireNonNull(obj);
                    EntrySet entrySet2 = EntrySet.this;
                    Object obj2 =
                            entrySet2.alternatingKeysAndValues[i2 + (entrySet2.keyOffset ^ 1)];
                    Objects.requireNonNull(obj2);
                    return new AbstractMap.SimpleImmutableEntry(obj, obj2);
                }

                @Override // com.google.common.collect.ImmutableCollection
                public final boolean isPartialView() {
                    return true;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
                public final int size() {
                    return EntrySet.this.size;
                }

                @Override // com.google.common.collect.ImmutableList,
                          // com.google.common.collect.ImmutableCollection
                public Object writeReplace() {
                    return super.writeReplace();
                }
            };
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
                  // java.util.Collection, java.lang.Iterable
        public final UnmodifiableIterator iterator() {
            return asList().listIterator(0);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableSet,
                  // com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class KeySet<K> extends ImmutableSet<K> {
        public final transient ImmutableList list;
        public final transient ImmutableMap map;

        public KeySet(ImmutableMap immutableMap, ImmutableList immutableList) {
            this.map = immutableMap;
            this.list = immutableList;
        }

        @Override // com.google.common.collect.ImmutableSet,
                  // com.google.common.collect.ImmutableCollection
        public final ImmutableList asList() {
            return this.list;
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
                  // java.util.Collection
        public final boolean contains(Object obj) {
            return this.map.get(obj) != null;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int copyIntoArray(Object[] objArr) {
            return this.list.copyIntoArray(objArr);
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
                  // java.util.Collection, java.lang.Iterable
        public final UnmodifiableIterator iterator() {
            return this.list.listIterator(0);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.map.size();
        }

        @Override // com.google.common.collect.ImmutableSet,
                  // com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static final class KeysOrValuesAsList extends ImmutableList<Object> {
        public final transient Object[] alternatingKeysAndValues;
        public final transient int offset;
        public final transient int size;

        public KeysOrValuesAsList(int i, int i2, Object[] objArr) {
            this.alternatingKeysAndValues = objArr;
            this.offset = i;
            this.size = i2;
        }

        @Override // java.util.List
        public final Object get(int i) {
            Preconditions.checkElementIndex(i, this.size);
            Object obj = this.alternatingKeysAndValues[(i * 2) + this.offset];
            Objects.requireNonNull(obj);
            return obj;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.size;
        }

        @Override // com.google.common.collect.ImmutableList,
                  // com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }
    }

    public RegularImmutableMap(int i, Object obj, Object[] objArr) {
        this.hashTable = obj;
        this.alternatingKeysAndValues = objArr;
        this.size = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x009e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x009f A[RETURN] */
    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r7.hashTable
            java.lang.Object[] r1 = r7.alternatingKeysAndValues
            int r7 = r7.size
            r2 = 0
            if (r8 != 0) goto Lc
        L9:
            r7 = r2
            goto L9c
        Lc:
            r3 = 1
            if (r7 != r3) goto L22
            r7 = 0
            r7 = r1[r7]
            java.util.Objects.requireNonNull(r7)
            boolean r7 = r7.equals(r8)
            if (r7 == 0) goto L9
            r7 = r1[r3]
            java.util.Objects.requireNonNull(r7)
            goto L9c
        L22:
            if (r0 != 0) goto L25
            goto L9
        L25:
            boolean r7 = r0 instanceof byte[]
            if (r7 == 0) goto L50
            r7 = r0
            byte[] r7 = (byte[]) r7
            int r0 = r7.length
            int r4 = r0 + (-1)
            int r0 = r8.hashCode()
            int r0 = com.google.common.collect.Hashing.smear(r0)
        L37:
            r0 = r0 & r4
            r5 = r7[r0]
            r6 = 255(0xff, float:3.57E-43)
            r5 = r5 & r6
            if (r5 != r6) goto L40
            goto L9
        L40:
            r6 = r1[r5]
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L4d
            r7 = r5 ^ 1
            r7 = r1[r7]
            goto L9c
        L4d:
            int r0 = r0 + 1
            goto L37
        L50:
            boolean r7 = r0 instanceof short[]
            if (r7 == 0) goto L7c
            r7 = r0
            short[] r7 = (short[]) r7
            int r0 = r7.length
            int r4 = r0 + (-1)
            int r0 = r8.hashCode()
            int r0 = com.google.common.collect.Hashing.smear(r0)
        L62:
            r0 = r0 & r4
            short r5 = r7[r0]
            r6 = 65535(0xffff, float:9.1834E-41)
            r5 = r5 & r6
            if (r5 != r6) goto L6c
            goto L9
        L6c:
            r6 = r1[r5]
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto L79
            r7 = r5 ^ 1
            r7 = r1[r7]
            goto L9c
        L79:
            int r0 = r0 + 1
            goto L62
        L7c:
            int[] r0 = (int[]) r0
            int r7 = r0.length
            int r7 = r7 - r3
            int r4 = r8.hashCode()
            int r4 = com.google.common.collect.Hashing.smear(r4)
        L88:
            r4 = r4 & r7
            r5 = r0[r4]
            r6 = -1
            if (r5 != r6) goto L90
            goto L9
        L90:
            r6 = r1[r5]
            boolean r6 = r8.equals(r6)
            if (r6 == 0) goto La0
            r7 = r5 ^ 1
            r7 = r1[r7]
        L9c:
            if (r7 != 0) goto L9f
            return r2
        L9f:
            return r7
        La0:
            int r4 = r4 + 1
            goto L88
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.common.collect.RegularImmutableMap.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.Map
    public final int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableMap
    public Object writeReplace() {
        return super.writeReplace();
    }
}
