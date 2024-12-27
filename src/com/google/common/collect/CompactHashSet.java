package com.google.common.collect;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class CompactHashSet<E> extends AbstractSet<E> implements Serializable {
    public transient Object[] elements;
    public transient int[] entries;
    public transient int metadata;
    public transient int size;
    public transient Object table;

    public static CompactHashSet createWithExpectedSize(int i) {
        CompactHashSet compactHashSet = new CompactHashSet();
        if (i < 0) {
            throw new IllegalArgumentException("Expected size must be >= 0");
        }
        compactHashSet.metadata = Ints.constrainToRange(i, 1);
        return compactHashSet;
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
            add(objectInputStream.readObject());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size());
        Iterator it = iterator();
        while (it.hasNext()) {
            objectOutputStream.writeObject(it.next());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        int min;
        if (needsAllocArrays()) {
            Preconditions.checkState("Arrays already allocated", needsAllocArrays());
            int i = this.metadata;
            int max = Math.max(4, Hashing.closedTableSize(i + 1));
            this.table = CompactHashing.createTable(max);
            this.metadata =
                    CompactHashing.maskCombine(
                            this.metadata, 32 - Integer.numberOfLeadingZeros(max - 1), 31);
            this.entries = new int[i];
            this.elements = new Object[i];
        }
        Set delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.add(obj);
        }
        int[] requireEntries = requireEntries();
        Object[] requireElements = requireElements();
        int i2 = this.size;
        int i3 = i2 + 1;
        int smearedHash = Hashing.smearedHash(obj);
        int i4 = (1 << (this.metadata & 31)) - 1;
        int i5 = smearedHash & i4;
        Object obj2 = this.table;
        Objects.requireNonNull(obj2);
        int tableGet = CompactHashing.tableGet(i5, obj2);
        if (tableGet != 0) {
            int i6 = ~i4;
            int i7 = smearedHash & i6;
            int i8 = 0;
            while (true) {
                int i9 = tableGet - 1;
                int i10 = requireEntries[i9];
                if ((i10 & i6) == i7
                        && com.google.common.base.Objects.equal(obj, requireElements[i9])) {
                    return false;
                }
                int i11 = i10 & i4;
                i8++;
                if (i11 != 0) {
                    tableGet = i11;
                } else {
                    if (i8 >= 9) {
                        LinkedHashSet linkedHashSet =
                                new LinkedHashSet(1 << (this.metadata & 31), 1.0f);
                        int i12 = isEmpty() ? -1 : 0;
                        while (i12 >= 0) {
                            linkedHashSet.add(requireElements()[i12]);
                            i12++;
                            if (i12 >= this.size) {
                                i12 = -1;
                            }
                        }
                        this.table = linkedHashSet;
                        this.entries = null;
                        this.elements = null;
                        this.metadata += 32;
                        return linkedHashSet.add(obj);
                    }
                    if (i3 > i4) {
                        i4 = resizeTable(i4, CompactHashing.newCapacity(i4), smearedHash, i2);
                    } else {
                        requireEntries[i9] = CompactHashing.maskCombine(i10, i3, i4);
                    }
                }
            }
        } else if (i3 > i4) {
            i4 = resizeTable(i4, CompactHashing.newCapacity(i4), smearedHash, i2);
        } else {
            Object obj3 = this.table;
            Objects.requireNonNull(obj3);
            CompactHashing.tableSet(i5, i3, obj3);
        }
        int length = requireEntries().length;
        if (i3 > length
                && (min = Math.min(1073741823, (Math.max(1, length >>> 1) + length) | 1))
                        != length) {
            this.entries = Arrays.copyOf(requireEntries(), min);
            this.elements = Arrays.copyOf(requireElements(), min);
        }
        requireEntries()[i2] = CompactHashing.maskCombine(smearedHash, 0, i4);
        requireElements()[i2] = obj;
        this.size = i3;
        this.metadata += 32;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        if (needsAllocArrays()) {
            return;
        }
        this.metadata += 32;
        Set delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            this.metadata = Ints.constrainToRange(size(), 3);
            delegateOrNull.clear();
            this.table = null;
            this.size = 0;
            return;
        }
        Arrays.fill(requireElements(), 0, this.size, (Object) null);
        Object obj = this.table;
        Objects.requireNonNull(obj);
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (needsAllocArrays()) {
            return false;
        }
        Set delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.contains(obj);
        }
        int smearedHash = Hashing.smearedHash(obj);
        int i = (1 << (this.metadata & 31)) - 1;
        Object obj2 = this.table;
        Objects.requireNonNull(obj2);
        int tableGet = CompactHashing.tableGet(smearedHash & i, obj2);
        if (tableGet == 0) {
            return false;
        }
        int i2 = ~i;
        int i3 = smearedHash & i2;
        do {
            int i4 = tableGet - 1;
            int i5 = requireEntries()[i4];
            if ((i5 & i2) == i3
                    && com.google.common.base.Objects.equal(obj, requireElements()[i4])) {
                return true;
            }
            tableGet = i5 & i;
        } while (tableGet != 0);
        return false;
    }

    public final Set delegateOrNull() {
        Object obj = this.table;
        if (obj instanceof Set) {
            return (Set) obj;
        }
        return null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
              // java.util.Set
    public final Iterator iterator() {
        Set delegateOrNull = delegateOrNull();
        return delegateOrNull != null
                ? delegateOrNull.iterator()
                : new Iterator() { // from class: com.google.common.collect.CompactHashSet.1
                    public int currentIndex;
                    public int expectedMetadata;
                    public int indexToRemove;

                    {
                        this.expectedMetadata = CompactHashSet.this.metadata;
                        this.currentIndex = CompactHashSet.this.isEmpty() ? -1 : 0;
                        this.indexToRemove = -1;
                    }

                    @Override // java.util.Iterator
                    public final boolean hasNext() {
                        return this.currentIndex >= 0;
                    }

                    @Override // java.util.Iterator
                    public final Object next() {
                        if (CompactHashSet.this.metadata != this.expectedMetadata) {
                            throw new ConcurrentModificationException();
                        }
                        if (!hasNext()) {
                            throw new NoSuchElementException();
                        }
                        int i = this.currentIndex;
                        this.indexToRemove = i;
                        Object obj = CompactHashSet.this.requireElements()[i];
                        CompactHashSet compactHashSet = CompactHashSet.this;
                        int i2 = this.currentIndex + 1;
                        if (i2 >= compactHashSet.size) {
                            i2 = -1;
                        }
                        this.currentIndex = i2;
                        return obj;
                    }

                    @Override // java.util.Iterator
                    public final void remove() {
                        if (CompactHashSet.this.metadata != this.expectedMetadata) {
                            throw new ConcurrentModificationException();
                        }
                        Preconditions.checkState(
                                "no calls to next() since the last call to remove()",
                                this.indexToRemove >= 0);
                        this.expectedMetadata += 32;
                        CompactHashSet compactHashSet = CompactHashSet.this;
                        compactHashSet.remove(compactHashSet.requireElements()[this.indexToRemove]);
                        CompactHashSet compactHashSet2 = CompactHashSet.this;
                        int i = this.currentIndex;
                        compactHashSet2.getClass();
                        this.currentIndex = i - 1;
                        this.indexToRemove = -1;
                    }
                };
    }

    public final boolean needsAllocArrays() {
        return this.table == null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        int i;
        int i2;
        if (needsAllocArrays()) {
            return false;
        }
        Set delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.remove(obj);
        }
        int i3 = (1 << (this.metadata & 31)) - 1;
        Object obj2 = this.table;
        Objects.requireNonNull(obj2);
        int remove =
                CompactHashing.remove(
                        obj, null, i3, obj2, requireEntries(), requireElements(), null);
        if (remove == -1) {
            return false;
        }
        Object obj3 = this.table;
        Objects.requireNonNull(obj3);
        int[] requireEntries = requireEntries();
        Object[] requireElements = requireElements();
        int size = size();
        int i4 = size - 1;
        if (remove < i4) {
            Object obj4 = requireElements[i4];
            requireElements[remove] = obj4;
            requireElements[i4] = null;
            requireEntries[remove] = requireEntries[i4];
            requireEntries[i4] = 0;
            int smearedHash = Hashing.smearedHash(obj4) & i3;
            int tableGet = CompactHashing.tableGet(smearedHash, obj3);
            if (tableGet == size) {
                CompactHashing.tableSet(smearedHash, remove + 1, obj3);
            } else {
                while (true) {
                    i = tableGet - 1;
                    i2 = requireEntries[i];
                    int i5 = i2 & i3;
                    if (i5 == size) {
                        break;
                    }
                    tableGet = i5;
                }
                requireEntries[i] = CompactHashing.maskCombine(i2, remove + 1, i3);
            }
        } else {
            requireElements[remove] = null;
            requireEntries[remove] = 0;
        }
        this.size--;
        this.metadata += 32;
        return true;
    }

    public final Object[] requireElements() {
        Object[] objArr = this.elements;
        Objects.requireNonNull(objArr);
        return objArr;
    }

    public final int[] requireEntries() {
        int[] iArr = this.entries;
        Objects.requireNonNull(iArr);
        return iArr;
    }

    public final int resizeTable(int i, int i2, int i3, int i4) {
        Object createTable = CompactHashing.createTable(i2);
        int i5 = i2 - 1;
        if (i4 != 0) {
            CompactHashing.tableSet(i3 & i5, i4 + 1, createTable);
        }
        Object obj = this.table;
        Objects.requireNonNull(obj);
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

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Set delegateOrNull = delegateOrNull();
        return delegateOrNull != null ? delegateOrNull.size() : this.size;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final Object[] toArray() {
        if (needsAllocArrays()) {
            return new Object[0];
        }
        Set delegateOrNull = delegateOrNull();
        return delegateOrNull != null
                ? delegateOrNull.toArray()
                : Arrays.copyOf(requireElements(), this.size);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final Object[] toArray(Object[] objArr) {
        if (needsAllocArrays()) {
            if (objArr.length > 0) {
                objArr[0] = null;
            }
            return objArr;
        }
        Set delegateOrNull = delegateOrNull();
        if (delegateOrNull != null) {
            return delegateOrNull.toArray(objArr);
        }
        Object[] requireElements = requireElements();
        int i = this.size;
        Preconditions.checkPositionIndexes(0, i, requireElements.length);
        if (objArr.length < i) {
            if (objArr.length != 0) {
                objArr = Arrays.copyOf(objArr, 0);
            }
            objArr = Arrays.copyOf(objArr, i);
        } else if (objArr.length > i) {
            objArr[i] = null;
        }
        System.arraycopy(requireElements, 0, objArr, 0, i);
        return objArr;
    }
}
