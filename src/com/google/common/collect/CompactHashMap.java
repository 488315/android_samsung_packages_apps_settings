package com.google.common.collect;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static final Object NOT_FOUND = new Object();
    public transient int[] entries;
    public transient KeySetView entrySetView;
    public transient KeySetView keySetView;
    public transient Object[] keys;
    public transient int metadata;
    public transient int size;
    public transient Object table;
    public transient Object[] values;
    public transient ValuesView valuesView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.common.collect.CompactHashMap$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator {
        public final /* synthetic */ int $r8$classId;
        public int currentIndex;
        public int expectedMetadata;
        public int indexToRemove;
        public final /* synthetic */ CompactHashMap this$0;
        public final /* synthetic */ CompactHashMap this$0$1;

        public AnonymousClass1(CompactHashMap compactHashMap, int i) {
            this.$r8$classId = i;
            this.this$0 = compactHashMap;
            this.this$0$1 = compactHashMap;
            this.expectedMetadata = compactHashMap.metadata;
            this.currentIndex = compactHashMap.isEmpty() ? -1 : 0;
            this.indexToRemove = -1;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.currentIndex >= 0;
        }

        @Override // java.util.Iterator
        public final Object next() {
            Object obj;
            if (this.this$0$1.metadata != this.expectedMetadata) {
                throw new ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.currentIndex;
            this.indexToRemove = i;
            switch (this.$r8$classId) {
                case 0:
                    CompactHashMap compactHashMap = this.this$0;
                    Object obj2 = CompactHashMap.NOT_FOUND;
                    obj = compactHashMap.requireKeys()[i];
                    break;
                case 1:
                    obj = new MapEntry(i);
                    break;
                default:
                    CompactHashMap compactHashMap2 = this.this$0;
                    Object obj3 = CompactHashMap.NOT_FOUND;
                    obj = compactHashMap2.requireValues()[i];
                    break;
            }
            CompactHashMap compactHashMap3 = this.this$0$1;
            int i2 = this.currentIndex + 1;
            if (i2 >= compactHashMap3.size) {
                i2 = -1;
            }
            this.currentIndex = i2;
            return obj;
        }

        @Override // java.util.Iterator
        public final void remove() {
            if (this.this$0$1.metadata != this.expectedMetadata) {
                throw new ConcurrentModificationException();
            }
            Preconditions.checkState(
                    "no calls to next() since the last call to remove()", this.indexToRemove >= 0);
            this.expectedMetadata += 32;
            CompactHashMap compactHashMap = this.this$0$1;
            compactHashMap.remove(compactHashMap.requireKeys()[this.indexToRemove]);
            CompactHashMap compactHashMap2 = this.this$0$1;
            int i = this.currentIndex;
            compactHashMap2.getClass();
            this.currentIndex = i - 1;
            this.indexToRemove = -1;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeySetView extends AbstractSet {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ CompactHashMap this$0;

        public /* synthetic */ KeySetView(CompactHashMap compactHashMap, int i) {
            this.$r8$classId = i;
            this.this$0 = compactHashMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.clear();
                    break;
                default:
                    this.this$0.clear();
                    break;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    return this.this$0.containsKey(obj);
                default:
                    Map delegateOrNull = this.this$0.delegateOrNull();
                    if (delegateOrNull != null) {
                        return delegateOrNull.entrySet().contains(obj);
                    }
                    if (obj instanceof Map.Entry) {
                        Map.Entry entry = (Map.Entry) obj;
                        int indexOf = this.this$0.indexOf(entry.getKey());
                        if (indexOf != -1
                                && Objects.equal(
                                        this.this$0.requireValues()[indexOf], entry.getValue())) {
                            return true;
                        }
                    }
                    return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                  // java.util.Set
        public final Iterator iterator() {
            switch (this.$r8$classId) {
                case 0:
                    CompactHashMap compactHashMap = this.this$0;
                    Map delegateOrNull = compactHashMap.delegateOrNull();
                    return delegateOrNull != null
                            ? delegateOrNull.keySet().iterator()
                            : new AnonymousClass1(compactHashMap, 0);
                default:
                    CompactHashMap compactHashMap2 = this.this$0;
                    Map delegateOrNull2 = compactHashMap2.delegateOrNull();
                    return delegateOrNull2 != null
                            ? delegateOrNull2.entrySet().iterator()
                            : new AnonymousClass1(compactHashMap2, 1);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    Map delegateOrNull = this.this$0.delegateOrNull();
                    return delegateOrNull != null
                            ? delegateOrNull.keySet().remove(obj)
                            : this.this$0.removeHelper(obj) != CompactHashMap.NOT_FOUND;
                default:
                    Map delegateOrNull2 = this.this$0.delegateOrNull();
                    if (delegateOrNull2 != null) {
                        return delegateOrNull2.entrySet().remove(obj);
                    }
                    if (obj instanceof Map.Entry) {
                        Map.Entry entry = (Map.Entry) obj;
                        if (!this.this$0.needsAllocArrays()) {
                            int hashTableMask = this.this$0.hashTableMask();
                            Object key = entry.getKey();
                            Object value = entry.getValue();
                            Object obj2 = this.this$0.table;
                            java.util.Objects.requireNonNull(obj2);
                            int remove =
                                    CompactHashing.remove(
                                            key,
                                            value,
                                            hashTableMask,
                                            obj2,
                                            this.this$0.requireEntries(),
                                            this.this$0.requireKeys(),
                                            this.this$0.requireValues());
                            if (remove != -1) {
                                this.this$0.moveLastEntry(remove, hashTableMask);
                                CompactHashMap compactHashMap = this.this$0;
                                compactHashMap.size--;
                                compactHashMap.metadata += 32;
                                return true;
                            }
                        }
                    }
                    return false;
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            switch (this.$r8$classId) {
            }
            return this.this$0.size();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MapEntry extends AbstractMapEntry {
        public final Object key;
        public int lastKnownIndex;

        public MapEntry(int i) {
            Object obj = CompactHashMap.NOT_FOUND;
            this.key = CompactHashMap.this.requireKeys()[i];
            this.lastKnownIndex = i;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != null) {
                return delegateOrNull.get(this.key);
            }
            updateLastKnownIndex();
            int i = this.lastKnownIndex;
            if (i == -1) {
                return null;
            }
            return CompactHashMap.this.requireValues()[i];
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            Map delegateOrNull = CompactHashMap.this.delegateOrNull();
            if (delegateOrNull != 0) {
                return delegateOrNull.put(this.key, obj);
            }
            updateLastKnownIndex();
            int i = this.lastKnownIndex;
            if (i == -1) {
                CompactHashMap.this.put(this.key, obj);
                return null;
            }
            Object obj2 = CompactHashMap.this.requireValues()[i];
            CompactHashMap compactHashMap = CompactHashMap.this;
            compactHashMap.requireValues()[this.lastKnownIndex] = obj;
            return obj2;
        }

        public final void updateLastKnownIndex() {
            int i = this.lastKnownIndex;
            if (i != -1 && i < CompactHashMap.this.size()) {
                Object obj = this.key;
                CompactHashMap compactHashMap = CompactHashMap.this;
                if (Objects.equal(obj, compactHashMap.requireKeys()[this.lastKnownIndex])) {
                    return;
                }
            }
            CompactHashMap compactHashMap2 = CompactHashMap.this;
            Object obj2 = this.key;
            Object obj3 = CompactHashMap.NOT_FOUND;
            this.lastKnownIndex = compactHashMap2.indexOf(obj2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ValuesView extends AbstractCollection {
        public ValuesView() {}

        @Override // java.util.AbstractCollection, java.util.Collection
        public final void clear() {
            CompactHashMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public final Iterator iterator() {
            CompactHashMap compactHashMap = CompactHashMap.this;
            Map delegateOrNull = compactHashMap.delegateOrNull();
            return delegateOrNull != null
                    ? delegateOrNull.values().iterator()
                    : new AnonymousClass1(compactHashMap, 2);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public final int size() {
            return CompactHashMap.this.size();
        }
    }

    public static CompactHashMap createWithExpectedSize() {
        CompactHashMap compactHashMap = new CompactHashMap();
        compactHashMap.metadata = Ints.constrainToRange(12, 1);
        return compactHashMap;
    }

    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        if (readInt < 0) {
            throw new InvalidObjectException(
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(readInt, "Invalid size: "));
        }
        if (readInt < 0) {
            throw new IllegalArgumentException("Expected size must be >= 0");
        }
        this.metadata = Ints.constrainToRange(readInt, 1);
        for (int i = 0; i < readInt; i++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        Map delegateOrNull = delegateOrNull();
        Iterator<Map.Entry<K, V>> it =
                delegateOrNull != null
                        ? delegateOrNull.entrySet().iterator()
                        : new AnonymousClass1(this, 1);
        while (it.hasNext()) {
            Map.Entry<K, V> next = it.next();
            objectOutputStream.writeObject(next.getKey());
            objectOutputStream.writeObject(next.getValue());
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (needsAllocArrays()) {
            return;
        }
        this.metadata += 32;
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            this.metadata = Ints.constrainToRange(size(), 3);
            delegateOrNull.clear();
            this.table = null;
            this.size = 0;
            return;
        }
        Arrays.fill(requireKeys(), 0, this.size, (Object) null);
        Arrays.fill(requireValues(), 0, this.size, (Object) null);
        Object obj = this.table;
        java.util.Objects.requireNonNull(obj);
        if (obj instanceof byte[]) {
            Arrays.fill((byte[]) obj, (byte) 0);
        } else if (obj instanceof short[]) {
            Arrays.fill((short[]) obj, (short) 0);
        } else {
            Arrays.fill((int[]) obj, 0);
        }
        Arrays.fill(requireEntries(), 0, this.size, 0);
        this.size = 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Map delegateOrNull = delegateOrNull();
        return delegateOrNull != null ? delegateOrNull.containsKey(obj) : indexOf(obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.containsValue(obj);
        }
        for (int i = 0; i < this.size; i++) {
            if (Objects.equal(obj, requireValues()[i])) {
                return true;
            }
        }
        return false;
    }

    public final Map delegateOrNull() {
        Object obj = this.table;
        if (obj instanceof Map) {
            return (Map) obj;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        KeySetView keySetView = this.entrySetView;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView keySetView2 = new KeySetView(this, 1);
        this.entrySetView = keySetView2;
        return keySetView2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.get(obj);
        }
        int indexOf = indexOf(obj);
        if (indexOf == -1) {
            return null;
        }
        return requireValues()[indexOf];
    }

    public final int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1;
    }

    public final int indexOf(Object obj) {
        if (needsAllocArrays()) {
            return -1;
        }
        int smearedHash = Hashing.smearedHash(obj);
        int hashTableMask = hashTableMask();
        Object obj2 = this.table;
        java.util.Objects.requireNonNull(obj2);
        int tableGet = CompactHashing.tableGet(smearedHash & hashTableMask, obj2);
        if (tableGet == 0) {
            return -1;
        }
        int i = ~hashTableMask;
        int i2 = smearedHash & i;
        do {
            int i3 = tableGet - 1;
            int i4 = requireEntries()[i3];
            if ((i4 & i) == i2 && Objects.equal(obj, requireKeys()[i3])) {
                return i3;
            }
            tableGet = i4 & hashTableMask;
        } while (tableGet != 0);
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        KeySetView keySetView = this.keySetView;
        if (keySetView != null) {
            return keySetView;
        }
        KeySetView keySetView2 = new KeySetView(this, 0);
        this.keySetView = keySetView2;
        return keySetView2;
    }

    public final void moveLastEntry(int i, int i2) {
        Object obj = this.table;
        java.util.Objects.requireNonNull(obj);
        int[] requireEntries = requireEntries();
        Object[] requireKeys = requireKeys();
        Object[] requireValues = requireValues();
        int size = size();
        int i3 = size - 1;
        if (i >= i3) {
            requireKeys[i] = null;
            requireValues[i] = null;
            requireEntries[i] = 0;
            return;
        }
        Object obj2 = requireKeys[i3];
        requireKeys[i] = obj2;
        requireValues[i] = requireValues[i3];
        requireKeys[i3] = null;
        requireValues[i3] = null;
        requireEntries[i] = requireEntries[i3];
        requireEntries[i3] = 0;
        int smearedHash = Hashing.smearedHash(obj2) & i2;
        int tableGet = CompactHashing.tableGet(smearedHash, obj);
        if (tableGet == size) {
            CompactHashing.tableSet(smearedHash, i + 1, obj);
            return;
        }
        while (true) {
            int i4 = tableGet - 1;
            int i5 = requireEntries[i4];
            int i6 = i5 & i2;
            if (i6 == size) {
                requireEntries[i4] = CompactHashing.maskCombine(i5, i + 1, i2);
                return;
            }
            tableGet = i6;
        }
    }

    public final boolean needsAllocArrays() {
        return this.table == null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c9  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00dc -> B:34:0x00c4). Please report as a decompilation issue!!! */
    @Override // java.util.AbstractMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object put(java.lang.Object r19, java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 354
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.common.collect.CompactHashMap.put(java.lang.Object,"
                    + " java.lang.Object):java.lang.Object");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        Map delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.remove(obj);
        }
        Object removeHelper = removeHelper(obj);
        if (removeHelper == NOT_FOUND) {
            return null;
        }
        return removeHelper;
    }

    public final Object removeHelper(Object obj) {
        boolean needsAllocArrays = needsAllocArrays();
        Object obj2 = NOT_FOUND;
        if (needsAllocArrays) {
            return obj2;
        }
        int hashTableMask = hashTableMask();
        Object obj3 = this.table;
        java.util.Objects.requireNonNull(obj3);
        int remove =
                CompactHashing.remove(
                        obj, null, hashTableMask, obj3, requireEntries(), requireKeys(), null);
        if (remove == -1) {
            return obj2;
        }
        Object obj4 = requireValues()[remove];
        moveLastEntry(remove, hashTableMask);
        this.size--;
        this.metadata += 32;
        return obj4;
    }

    public final int[] requireEntries() {
        int[] iArr = this.entries;
        java.util.Objects.requireNonNull(iArr);
        return iArr;
    }

    public final Object[] requireKeys() {
        Object[] objArr = this.keys;
        java.util.Objects.requireNonNull(objArr);
        return objArr;
    }

    public final Object[] requireValues() {
        Object[] objArr = this.values;
        java.util.Objects.requireNonNull(objArr);
        return objArr;
    }

    public final int resizeTable(int i, int i2, int i3, int i4) {
        Object createTable = CompactHashing.createTable(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            CompactHashing.tableSet(i3 & i5, i4 + 1, createTable);
        }
        Object obj = this.table;
        java.util.Objects.requireNonNull(obj);
        int[] requireEntries = requireEntries();
        for (int i6 = 0; i6 <= i; i6++) {
            int tableGet = CompactHashing.tableGet(i6, obj);
            while (tableGet != 0) {
                int i7 = tableGet - 1;
                int i8 = requireEntries[i7];
                int i9 = ((~i) & i8) | i6;
                int i10 = i9 & i5;
                int tableGet2 = CompactHashing.tableGet(i10, createTable);
                CompactHashing.tableSet(i10, tableGet, createTable);
                requireEntries[i7] = CompactHashing.maskCombine(i9, tableGet2, i5);
                tableGet = i8 & i;
            }
        }
        this.table = createTable;
        this.metadata =
                CompactHashing.maskCombine(
                        this.metadata, 32 - Integer.numberOfLeadingZeros(i5), 31);
        return i5;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        Map delegateOrNull = delegateOrNull();
        return delegateOrNull != null ? delegateOrNull.size() : this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        ValuesView valuesView = this.valuesView;
        if (valuesView != null) {
            return valuesView;
        }
        ValuesView valuesView2 = new ValuesView();
        this.valuesView = valuesView2;
        return valuesView2;
    }
}
