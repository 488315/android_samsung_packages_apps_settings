package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public final class HashBiMap<K, V> extends AbstractMap<K, V> implements BiMap, Serializable {
    public transient KeySet entrySet;
    public transient int firstInInsertionOrder;
    public transient int[] hashTableKToV;
    public transient int[] hashTableVToK;
    public transient BiMap inverse;
    public transient KeySet keySet;
    public transient Object[] keys;
    public transient int lastInInsertionOrder;
    public transient int modCount;
    public transient int[] nextInBucketKToV;
    public transient int[] nextInBucketVToK;
    public transient int[] nextInInsertionOrder;
    public transient int[] prevInInsertionOrder;
    public transient int size;
    public transient KeySet valueSet;
    public transient Object[] values;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EntryForKey extends AbstractMapEntry {
        public final /* synthetic */ int $r8$classId;
        public int index;
        public final Object key;
        public final HashBiMap this$0;

        public EntryForKey(HashBiMap hashBiMap, int i, int i2) {
            this.$r8$classId = i2;
            switch (i2) {
                case 1:
                    this.this$0 = hashBiMap;
                    this.key = hashBiMap.values[i];
                    this.index = i;
                    break;
                default:
                    this.this$0 = hashBiMap;
                    this.key = hashBiMap.keys[i];
                    this.index = i;
                    break;
            }
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            switch (this.$r8$classId) {
            }
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            switch (this.$r8$classId) {
                case 0:
                    updateIndex();
                    int i = this.index;
                    if (i == -1) {
                        return null;
                    }
                    return this.this$0.values[i];
                default:
                    updateIndex$1();
                    int i2 = this.index;
                    if (i2 == -1) {
                        return null;
                    }
                    return this.this$0.keys[i2];
            }
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    updateIndex();
                    int i = this.index;
                    if (i == -1) {
                        this.this$0.put(this.key, obj);
                        return null;
                    }
                    Object obj2 = this.this$0.values[i];
                    if (Objects.equal(obj2, obj)) {
                        return obj;
                    }
                    this.this$0.replaceValueInEntry(this.index, obj);
                    return obj2;
                default:
                    updateIndex$1();
                    int i2 = this.index;
                    if (i2 == -1) {
                        this.this$0.putInverse(this.key, obj);
                        return null;
                    }
                    Object obj3 = this.this$0.keys[i2];
                    if (Objects.equal(obj3, obj)) {
                        return obj;
                    }
                    this.this$0.replaceKeyInEntry(this.index, obj);
                    return obj3;
            }
        }

        public void updateIndex() {
            int i = this.index;
            if (i != -1) {
                HashBiMap hashBiMap = this.this$0;
                if (i <= hashBiMap.size && Objects.equal(hashBiMap.keys[i], this.key)) {
                    return;
                }
            }
            HashBiMap hashBiMap2 = this.this$0;
            Object obj = this.key;
            hashBiMap2.getClass();
            this.index = hashBiMap2.findEntryByKey(Hashing.smearedHash(obj), obj);
        }

        public void updateIndex$1() {
            int i = this.index;
            if (i != -1) {
                HashBiMap hashBiMap = this.this$0;
                if (i <= hashBiMap.size && Objects.equal(this.key, hashBiMap.values[i])) {
                    return;
                }
            }
            HashBiMap hashBiMap2 = this.this$0;
            Object obj = this.key;
            hashBiMap2.getClass();
            this.index = hashBiMap2.findEntryByValue(Hashing.smearedHash(obj), obj);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Inverse<K, V> extends AbstractMap<V, K> implements BiMap, Serializable {
        private final HashBiMap<K, V> forward;
        public transient InverseEntrySet inverseEntrySet;

        public Inverse(HashBiMap hashBiMap) {
            this.forward = hashBiMap;
        }

        private void readObject(ObjectInputStream objectInputStream)
                throws ClassNotFoundException, IOException {
            objectInputStream.defaultReadObject();
            this.forward.inverse = this;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final void clear() {
            this.forward.clear();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsKey(Object obj) {
            return this.forward.containsValue(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsValue(Object obj) {
            return this.forward.containsKey(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set entrySet() {
            InverseEntrySet inverseEntrySet = this.inverseEntrySet;
            if (inverseEntrySet != null) {
                return inverseEntrySet;
            }
            InverseEntrySet inverseEntrySet2 = new InverseEntrySet(this.forward);
            this.inverseEntrySet = inverseEntrySet2;
            return inverseEntrySet2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object get(Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            hashBiMap.getClass();
            int findEntryByValue = hashBiMap.findEntryByValue(Hashing.smearedHash(obj), obj);
            if (findEntryByValue == -1) {
                return null;
            }
            return hashBiMap.keys[findEntryByValue];
        }

        @Override // com.google.common.collect.BiMap
        public final BiMap inverse() {
            return this.forward;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set keySet() {
            HashBiMap<K, V> hashBiMap = this.forward;
            KeySet keySet = hashBiMap.valueSet;
            if (keySet != null) {
                return keySet;
            }
            KeySet keySet2 = new KeySet(hashBiMap, 2);
            hashBiMap.valueSet = keySet2;
            return keySet2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object put(Object obj, Object obj2) {
            return this.forward.putInverse(obj, obj2);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object remove(Object obj) {
            HashBiMap<K, V> hashBiMap = this.forward;
            hashBiMap.getClass();
            int smearedHash = Hashing.smearedHash(obj);
            int findEntryByValue = hashBiMap.findEntryByValue(smearedHash, obj);
            if (findEntryByValue == -1) {
                return null;
            }
            Object obj2 = hashBiMap.keys[findEntryByValue];
            hashBiMap.removeEntry(findEntryByValue, Hashing.smearedHash(obj2), smearedHash);
            return obj2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final int size() {
            return this.forward.size;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Collection values() {
            return this.forward.keySet();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class InverseEntrySet extends View {
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            HashBiMap hashBiMap = this.biMap;
            hashBiMap.getClass();
            int findEntryByValue = hashBiMap.findEntryByValue(Hashing.smearedHash(key), key);
            return findEntryByValue != -1
                    && Objects.equal(this.biMap.keys[findEntryByValue], value);
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            return new EntryForKey(this.biMap, i, 1);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object key = entry.getKey();
            Object value = entry.getValue();
            int smearedHash = Hashing.smearedHash(key);
            int findEntryByValue = this.biMap.findEntryByValue(smearedHash, key);
            if (findEntryByValue == -1
                    || !Objects.equal(this.biMap.keys[findEntryByValue], value)) {
                return false;
            }
            HashBiMap hashBiMap = this.biMap;
            hashBiMap.removeEntry(
                    findEntryByValue,
                    Hashing.smearedHash(hashBiMap.keys[findEntryByValue]),
                    smearedHash);
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeySet extends View {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HashBiMap this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public /* synthetic */ KeySet(HashBiMap hashBiMap, int i) {
            super(hashBiMap);
            this.$r8$classId = i;
            this.this$0 = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    return this.this$0.containsKey(obj);
                case 1:
                    if (!(obj instanceof Map.Entry)) {
                        return false;
                    }
                    Map.Entry entry = (Map.Entry) obj;
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    HashBiMap hashBiMap = this.this$0;
                    hashBiMap.getClass();
                    int findEntryByKey = hashBiMap.findEntryByKey(Hashing.smearedHash(key), key);
                    return findEntryByKey != -1
                            && Objects.equal(value, this.this$0.values[findEntryByKey]);
                default:
                    return this.this$0.containsValue(obj);
            }
        }

        @Override // com.google.common.collect.HashBiMap.View
        public final Object forEntry(int i) {
            switch (this.$r8$classId) {
                case 0:
                    return this.this$0.keys[i];
                case 1:
                    return new EntryForKey(this.this$0, i, 0);
                default:
                    return this.this$0.values[i];
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            switch (this.$r8$classId) {
                case 0:
                    int smearedHash = Hashing.smearedHash(obj);
                    int findEntryByKey = this.this$0.findEntryByKey(smearedHash, obj);
                    if (findEntryByKey != -1) {
                        this.this$0.removeEntryKeyHashKnown(findEntryByKey, smearedHash);
                        break;
                    }
                    break;
                case 1:
                    if (obj instanceof Map.Entry) {
                        Map.Entry entry = (Map.Entry) obj;
                        Object key = entry.getKey();
                        Object value = entry.getValue();
                        int smearedHash2 = Hashing.smearedHash(key);
                        int findEntryByKey2 = this.this$0.findEntryByKey(smearedHash2, key);
                        if (findEntryByKey2 != -1
                                && Objects.equal(value, this.this$0.values[findEntryByKey2])) {
                            this.this$0.removeEntryKeyHashKnown(findEntryByKey2, smearedHash2);
                            break;
                        }
                    }
                    break;
                default:
                    int smearedHash3 = Hashing.smearedHash(obj);
                    int findEntryByValue = this.this$0.findEntryByValue(smearedHash3, obj);
                    if (findEntryByValue != -1) {
                        HashBiMap hashBiMap = this.this$0;
                        hashBiMap.removeEntry(
                                findEntryByValue,
                                Hashing.smearedHash(hashBiMap.keys[findEntryByValue]),
                                smearedHash3);
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class View extends AbstractSet {
        public final HashBiMap biMap;

        public View(HashBiMap hashBiMap) {
            this.biMap = hashBiMap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            this.biMap.clear();
        }

        public abstract Object forEntry(int i);

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                  // java.util.Set
        public final Iterator iterator() {
            return new Iterator() { // from class: com.google.common.collect.HashBiMap.View.1
                public int expectedModCount;
                public int index;
                public int indexToRemove;
                public int remaining;

                {
                    HashBiMap hashBiMap = View.this.biMap;
                    this.index = hashBiMap.firstInInsertionOrder;
                    this.indexToRemove = -1;
                    this.expectedModCount = hashBiMap.modCount;
                    this.remaining = hashBiMap.size;
                }

                @Override // java.util.Iterator
                public final boolean hasNext() {
                    if (View.this.biMap.modCount == this.expectedModCount) {
                        return this.index != -2 && this.remaining > 0;
                    }
                    throw new ConcurrentModificationException();
                }

                @Override // java.util.Iterator
                public final Object next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    Object forEntry = View.this.forEntry(this.index);
                    int i = this.index;
                    this.indexToRemove = i;
                    this.index = View.this.biMap.nextInInsertionOrder[i];
                    this.remaining--;
                    return forEntry;
                }

                @Override // java.util.Iterator
                public final void remove() {
                    if (View.this.biMap.modCount != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                    Preconditions.checkState(
                            "no calls to next() since the last call to remove()",
                            this.indexToRemove != -1);
                    HashBiMap hashBiMap = View.this.biMap;
                    int i = this.indexToRemove;
                    hashBiMap.removeEntryKeyHashKnown(i, Hashing.smearedHash(hashBiMap.keys[i]));
                    int i2 = this.index;
                    HashBiMap hashBiMap2 = View.this.biMap;
                    if (i2 == hashBiMap2.size) {
                        this.index = this.indexToRemove;
                    }
                    this.indexToRemove = -1;
                    this.expectedModCount = hashBiMap2.modCount;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.biMap.size;
        }
    }

    public static HashBiMap create() {
        HashBiMap hashBiMap = new HashBiMap();
        hashBiMap.init();
        return hashBiMap;
    }

    public static int[] createFilledWithAbsent(int i) {
        int[] iArr = new int[i];
        Arrays.fill(iArr, -1);
        return iArr;
    }

    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int readInt = objectInputStream.readInt();
        init();
        for (int i = 0; i < readInt; i++) {
            put(objectInputStream.readObject(), objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        for (Map.Entry entry : entrySet()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }

    public final int bucket(int i) {
        return (this.hashTableKToV.length - 1) & i;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        Arrays.fill(this.keys, 0, this.size, (Object) null);
        Arrays.fill(this.values, 0, this.size, (Object) null);
        Arrays.fill(this.hashTableKToV, -1);
        Arrays.fill(this.hashTableVToK, -1);
        Arrays.fill(this.nextInBucketKToV, 0, this.size, -1);
        Arrays.fill(this.nextInBucketVToK, 0, this.size, -1);
        Arrays.fill(this.prevInInsertionOrder, 0, this.size, -1);
        Arrays.fill(this.nextInInsertionOrder, 0, this.size, -1);
        this.size = 0;
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.modCount++;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        return findEntryByKey(Hashing.smearedHash(obj), obj) != -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsValue(Object obj) {
        return findEntryByValue(Hashing.smearedHash(obj), obj) != -1;
    }

    public final void deleteFromTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int bucket = bucket(i2);
        int[] iArr = this.hashTableKToV;
        int i3 = iArr[bucket];
        if (i3 == i) {
            int[] iArr2 = this.nextInBucketKToV;
            iArr[bucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i4 = this.nextInBucketKToV[i3];
        while (true) {
            int i5 = i3;
            i3 = i4;
            if (i3 == -1) {
                throw new AssertionError("Expected to find entry with key " + this.keys[i]);
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketKToV;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketKToV[i3];
        }
    }

    public final void deleteFromTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int bucket = bucket(i2);
        int[] iArr = this.hashTableVToK;
        int i3 = iArr[bucket];
        if (i3 == i) {
            int[] iArr2 = this.nextInBucketVToK;
            iArr[bucket] = iArr2[i];
            iArr2[i] = -1;
            return;
        }
        int i4 = this.nextInBucketVToK[i3];
        while (true) {
            int i5 = i3;
            i3 = i4;
            if (i3 == -1) {
                throw new AssertionError("Expected to find entry with value " + this.values[i]);
            }
            if (i3 == i) {
                int[] iArr3 = this.nextInBucketVToK;
                iArr3[i5] = iArr3[i];
                iArr3[i] = -1;
                return;
            }
            i4 = this.nextInBucketVToK[i3];
        }
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.nextInBucketKToV;
        if (iArr.length < i) {
            int expandedCapacity = ImmutableCollection.Builder.expandedCapacity(iArr.length, i);
            this.keys = Arrays.copyOf(this.keys, expandedCapacity);
            this.values = Arrays.copyOf(this.values, expandedCapacity);
            int[] iArr2 = this.nextInBucketKToV;
            int length = iArr2.length;
            int[] copyOf = Arrays.copyOf(iArr2, expandedCapacity);
            Arrays.fill(copyOf, length, expandedCapacity, -1);
            this.nextInBucketKToV = copyOf;
            int[] iArr3 = this.nextInBucketVToK;
            int length2 = iArr3.length;
            int[] copyOf2 = Arrays.copyOf(iArr3, expandedCapacity);
            Arrays.fill(copyOf2, length2, expandedCapacity, -1);
            this.nextInBucketVToK = copyOf2;
            int[] iArr4 = this.prevInInsertionOrder;
            int length3 = iArr4.length;
            int[] copyOf3 = Arrays.copyOf(iArr4, expandedCapacity);
            Arrays.fill(copyOf3, length3, expandedCapacity, -1);
            this.prevInInsertionOrder = copyOf3;
            int[] iArr5 = this.nextInInsertionOrder;
            int length4 = iArr5.length;
            int[] copyOf4 = Arrays.copyOf(iArr5, expandedCapacity);
            Arrays.fill(copyOf4, length4, expandedCapacity, -1);
            this.nextInInsertionOrder = copyOf4;
        }
        if (this.hashTableKToV.length < i) {
            int closedTableSize = Hashing.closedTableSize(i);
            this.hashTableKToV = createFilledWithAbsent(closedTableSize);
            this.hashTableVToK = createFilledWithAbsent(closedTableSize);
            for (int i2 = 0; i2 < this.size; i2++) {
                int bucket = bucket(Hashing.smearedHash(this.keys[i2]));
                int[] iArr6 = this.nextInBucketKToV;
                int[] iArr7 = this.hashTableKToV;
                iArr6[i2] = iArr7[bucket];
                iArr7[bucket] = i2;
                int bucket2 = bucket(Hashing.smearedHash(this.values[i2]));
                int[] iArr8 = this.nextInBucketVToK;
                int[] iArr9 = this.hashTableVToK;
                iArr8[i2] = iArr9[bucket2];
                iArr9[bucket2] = i2;
            }
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        KeySet keySet = this.entrySet;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet(this, 1);
        this.entrySet = keySet2;
        return keySet2;
    }

    public final int findEntryByKey(int i, Object obj) {
        int[] iArr = this.hashTableKToV;
        int[] iArr2 = this.nextInBucketKToV;
        Object[] objArr = this.keys;
        for (int i2 = iArr[bucket(i)]; i2 != -1; i2 = iArr2[i2]) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
        }
        return -1;
    }

    public final int findEntryByValue(int i, Object obj) {
        int[] iArr = this.hashTableVToK;
        int[] iArr2 = this.nextInBucketVToK;
        Object[] objArr = this.values;
        for (int i2 = iArr[bucket(i)]; i2 != -1; i2 = iArr2[i2]) {
            if (Objects.equal(objArr[i2], obj)) {
                return i2;
            }
        }
        return -1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        int findEntryByKey = findEntryByKey(Hashing.smearedHash(obj), obj);
        if (findEntryByKey == -1) {
            return null;
        }
        return this.values[findEntryByKey];
    }

    public final void init() {
        CollectPreconditions.checkNonnegative(16, "expectedSize");
        int closedTableSize = Hashing.closedTableSize(16);
        this.size = 0;
        this.keys = new Object[16];
        this.values = new Object[16];
        this.hashTableKToV = createFilledWithAbsent(closedTableSize);
        this.hashTableVToK = createFilledWithAbsent(closedTableSize);
        this.nextInBucketKToV = createFilledWithAbsent(16);
        this.nextInBucketVToK = createFilledWithAbsent(16);
        this.firstInInsertionOrder = -2;
        this.lastInInsertionOrder = -2;
        this.prevInInsertionOrder = createFilledWithAbsent(16);
        this.nextInInsertionOrder = createFilledWithAbsent(16);
    }

    public final void insertIntoTableKToV(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int bucket = bucket(i2);
        int[] iArr = this.nextInBucketKToV;
        int[] iArr2 = this.hashTableKToV;
        iArr[i] = iArr2[bucket];
        iArr2[bucket] = i;
    }

    public final void insertIntoTableVToK(int i, int i2) {
        Preconditions.checkArgument(i != -1);
        int bucket = bucket(i2);
        int[] iArr = this.nextInBucketVToK;
        int[] iArr2 = this.hashTableVToK;
        iArr[i] = iArr2[bucket];
        iArr2[bucket] = i;
    }

    @Override // com.google.common.collect.BiMap
    public final BiMap inverse() {
        BiMap biMap = this.inverse;
        if (biMap != null) {
            return biMap;
        }
        Inverse inverse = new Inverse(this);
        this.inverse = inverse;
        return inverse;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        KeySet keySet = this.keySet;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet(this, 0);
        this.keySet = keySet2;
        return keySet2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        int smearedHash = Hashing.smearedHash(obj);
        int findEntryByKey = findEntryByKey(smearedHash, obj);
        if (findEntryByKey != -1) {
            Object obj3 = this.values[findEntryByKey];
            if (Objects.equal(obj3, obj2)) {
                return obj2;
            }
            replaceValueInEntry(findEntryByKey, obj2);
            return obj3;
        }
        int smearedHash2 = Hashing.smearedHash(obj2);
        Preconditions.checkArgument(
                obj2, "Value already present: %s", findEntryByValue(smearedHash2, obj2) == -1);
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i = this.size;
        objArr[i] = obj;
        this.values[i] = obj2;
        insertIntoTableKToV(i, smearedHash);
        insertIntoTableVToK(this.size, smearedHash2);
        setSucceeds(this.lastInInsertionOrder, this.size);
        setSucceeds(this.size, -2);
        this.size++;
        this.modCount++;
        return null;
    }

    public final Object putInverse(Object obj, Object obj2) {
        int smearedHash = Hashing.smearedHash(obj);
        int findEntryByValue = findEntryByValue(smearedHash, obj);
        if (findEntryByValue != -1) {
            Object obj3 = this.keys[findEntryByValue];
            if (Objects.equal(obj3, obj2)) {
                return obj2;
            }
            replaceKeyInEntry(findEntryByValue, obj2);
            return obj3;
        }
        int i = this.lastInInsertionOrder;
        int smearedHash2 = Hashing.smearedHash(obj2);
        Preconditions.checkArgument(
                obj2, "Key already present: %s", findEntryByKey(smearedHash2, obj2) == -1);
        ensureCapacity(this.size + 1);
        Object[] objArr = this.keys;
        int i2 = this.size;
        objArr[i2] = obj2;
        this.values[i2] = obj;
        insertIntoTableKToV(i2, smearedHash2);
        insertIntoTableVToK(this.size, smearedHash);
        int i3 = i == -2 ? this.firstInInsertionOrder : this.nextInInsertionOrder[i];
        setSucceeds(i, this.size);
        setSucceeds(this.size, i3);
        this.size++;
        this.modCount++;
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        int smearedHash = Hashing.smearedHash(obj);
        int findEntryByKey = findEntryByKey(smearedHash, obj);
        if (findEntryByKey == -1) {
            return null;
        }
        Object obj2 = this.values[findEntryByKey];
        removeEntryKeyHashKnown(findEntryByKey, smearedHash);
        return obj2;
    }

    public final void removeEntry(int i, int i2, int i3) {
        int i4;
        int i5;
        Preconditions.checkArgument(i != -1);
        deleteFromTableKToV(i, i2);
        deleteFromTableVToK(i, i3);
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        int i6 = this.size - 1;
        if (i6 != i) {
            int i7 = this.prevInInsertionOrder[i6];
            int i8 = this.nextInInsertionOrder[i6];
            setSucceeds(i7, i);
            setSucceeds(i, i8);
            Object[] objArr = this.keys;
            Object obj = objArr[i6];
            Object[] objArr2 = this.values;
            Object obj2 = objArr2[i6];
            objArr[i] = obj;
            objArr2[i] = obj2;
            int bucket = bucket(Hashing.smearedHash(obj));
            int[] iArr = this.hashTableKToV;
            int i9 = iArr[bucket];
            if (i9 == i6) {
                iArr[bucket] = i;
            } else {
                int i10 = this.nextInBucketKToV[i9];
                while (true) {
                    i4 = i9;
                    i9 = i10;
                    if (i9 == i6) {
                        break;
                    } else {
                        i10 = this.nextInBucketKToV[i9];
                    }
                }
                this.nextInBucketKToV[i4] = i;
            }
            int[] iArr2 = this.nextInBucketKToV;
            iArr2[i] = iArr2[i6];
            iArr2[i6] = -1;
            int bucket2 = bucket(Hashing.smearedHash(obj2));
            int[] iArr3 = this.hashTableVToK;
            int i11 = iArr3[bucket2];
            if (i11 == i6) {
                iArr3[bucket2] = i;
            } else {
                int i12 = this.nextInBucketVToK[i11];
                while (true) {
                    i5 = i11;
                    i11 = i12;
                    if (i11 == i6) {
                        break;
                    } else {
                        i12 = this.nextInBucketVToK[i11];
                    }
                }
                this.nextInBucketVToK[i5] = i;
            }
            int[] iArr4 = this.nextInBucketVToK;
            iArr4[i] = iArr4[i6];
            iArr4[i6] = -1;
        }
        Object[] objArr3 = this.keys;
        int i13 = this.size;
        objArr3[i13 - 1] = null;
        this.values[i13 - 1] = null;
        this.size = i13 - 1;
        this.modCount++;
    }

    public final void removeEntryKeyHashKnown(int i, int i2) {
        removeEntry(i, i2, Hashing.smearedHash(this.values[i]));
    }

    public final void replaceKeyInEntry(int i, Object obj) {
        Preconditions.checkArgument(i != -1);
        int findEntryByKey = findEntryByKey(Hashing.smearedHash(obj), obj);
        int i2 = this.lastInInsertionOrder;
        if (findEntryByKey != -1) {
            throw new IllegalArgumentException("Key already present in map: " + obj);
        }
        if (i2 == i) {
            i2 = this.prevInInsertionOrder[i];
        } else if (i2 == this.size) {
            i2 = findEntryByKey;
        }
        if (-2 == i) {
            findEntryByKey = this.nextInInsertionOrder[i];
        } else if (-2 != this.size) {
            findEntryByKey = -2;
        }
        setSucceeds(this.prevInInsertionOrder[i], this.nextInInsertionOrder[i]);
        deleteFromTableKToV(i, Hashing.smearedHash(this.keys[i]));
        this.keys[i] = obj;
        insertIntoTableKToV(i, Hashing.smearedHash(obj));
        setSucceeds(i2, i);
        setSucceeds(i, findEntryByKey);
    }

    public final void replaceValueInEntry(int i, Object obj) {
        Preconditions.checkArgument(i != -1);
        int smearedHash = Hashing.smearedHash(obj);
        if (findEntryByValue(smearedHash, obj) != -1) {
            throw new IllegalArgumentException("Value already present in map: " + obj);
        }
        deleteFromTableVToK(i, Hashing.smearedHash(this.values[i]));
        this.values[i] = obj;
        insertIntoTableVToK(i, smearedHash);
    }

    public final void setSucceeds(int i, int i2) {
        if (i == -2) {
            this.firstInInsertionOrder = i2;
        } else {
            this.nextInInsertionOrder[i] = i2;
        }
        if (i2 == -2) {
            this.lastInInsertionOrder = i;
        } else {
            this.prevInInsertionOrder[i2] = i;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.size;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        KeySet keySet = this.valueSet;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet(this, 2);
        this.valueSet = keySet2;
        return keySet2;
    }
}
