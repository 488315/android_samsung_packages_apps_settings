package com.google.protobuf;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SmallSortedMap$1 extends AbstractMap {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean isImmutable;
    public volatile SmallSortedMap$EntrySet lazyEntrySet;
    public final int maxArraySize;
    public List entryList = Collections.emptyList();
    public Map overflowEntries = Collections.emptyMap();
    public Map overflowEntriesDescending = Collections.emptyMap();

    public SmallSortedMap$1(int i) {
        this.maxArraySize = i;
    }

    public final int binarySearchInArray(Comparable comparable) {
        int i;
        int size = this.entryList.size();
        int i2 = size - 1;
        if (i2 >= 0) {
            int compareTo =
                    comparable.compareTo(((SmallSortedMap$Entry) this.entryList.get(i2)).key);
            if (compareTo > 0) {
                i = size + 1;
                return -i;
            }
            if (compareTo == 0) {
                return i2;
            }
        }
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) / 2;
            int compareTo2 =
                    comparable.compareTo(((SmallSortedMap$Entry) this.entryList.get(i4)).key);
            if (compareTo2 < 0) {
                i2 = i4 - 1;
            } else {
                if (compareTo2 <= 0) {
                    return i4;
                }
                i3 = i4 + 1;
            }
        }
        i = i3 + 1;
        return -i;
    }

    public final void checkMutable() {
        if (this.isImmutable) {
            throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        checkMutable();
        if (!this.entryList.isEmpty()) {
            this.entryList.clear();
        }
        if (this.overflowEntries.isEmpty()) {
            return;
        }
        this.overflowEntries.clear();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Comparable comparable = (Comparable) obj;
        return binarySearchInArray(comparable) >= 0 || this.overflowEntries.containsKey(comparable);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.protobuf.SmallSortedMap$EntrySet] */
    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        if (this.lazyEntrySet == null) {
            this.lazyEntrySet =
                    new AbstractSet() { // from class: com.google.protobuf.SmallSortedMap$EntrySet
                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.util.Set
                        public final boolean add(Object obj) {
                            Map.Entry entry = (Map.Entry) obj;
                            if (contains(entry)) {
                                return false;
                            }
                            SmallSortedMap$1.this.put(
                                    (Comparable) entry.getKey(), entry.getValue());
                            return true;
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.util.Set
                        public final void clear() {
                            SmallSortedMap$1.this.clear();
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.util.Set
                        public final boolean contains(Object obj) {
                            Map.Entry entry = (Map.Entry) obj;
                            Object obj2 = SmallSortedMap$1.this.get(entry.getKey());
                            Object value = entry.getValue();
                            return obj2 == value || (obj2 != null && obj2.equals(value));
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.lang.Iterable, java.util.Set
                        public Iterator iterator() {
                            final SmallSortedMap$1 smallSortedMap$1 = SmallSortedMap$1.this;
                            return new Iterator() { // from class:
                                                    // com.google.protobuf.SmallSortedMap$EntryIterator
                                public Iterator lazyOverflowIterator;
                                public boolean nextCalledBeforeRemove;
                                public int pos = -1;

                                public final Iterator getOverflowIterator() {
                                    if (this.lazyOverflowIterator == null) {
                                        this.lazyOverflowIterator =
                                                SmallSortedMap$1.this
                                                        .overflowEntries
                                                        .entrySet()
                                                        .iterator();
                                    }
                                    return this.lazyOverflowIterator;
                                }

                                @Override // java.util.Iterator
                                public final boolean hasNext() {
                                    if (this.pos + 1 >= SmallSortedMap$1.this.entryList.size()) {
                                        return !SmallSortedMap$1.this.overflowEntries.isEmpty()
                                                && getOverflowIterator().hasNext();
                                    }
                                    return true;
                                }

                                @Override // java.util.Iterator
                                public final Object next() {
                                    this.nextCalledBeforeRemove = true;
                                    int i = this.pos + 1;
                                    this.pos = i;
                                    return i < SmallSortedMap$1.this.entryList.size()
                                            ? (Map.Entry)
                                                    SmallSortedMap$1.this.entryList.get(this.pos)
                                            : (Map.Entry) getOverflowIterator().next();
                                }

                                @Override // java.util.Iterator
                                public final void remove() {
                                    if (!this.nextCalledBeforeRemove) {
                                        throw new IllegalStateException(
                                                "remove() was called before next()");
                                    }
                                    this.nextCalledBeforeRemove = false;
                                    SmallSortedMap$1 smallSortedMap$12 = SmallSortedMap$1.this;
                                    int i = SmallSortedMap$1.$r8$clinit;
                                    smallSortedMap$12.checkMutable();
                                    if (this.pos >= SmallSortedMap$1.this.entryList.size()) {
                                        getOverflowIterator().remove();
                                        return;
                                    }
                                    SmallSortedMap$1 smallSortedMap$13 = SmallSortedMap$1.this;
                                    int i2 = this.pos;
                                    this.pos = i2 - 1;
                                    smallSortedMap$13.removeArrayEntryAt(i2);
                                }
                            };
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.util.Set
                        public final boolean remove(Object obj) {
                            Map.Entry entry = (Map.Entry) obj;
                            if (!contains(entry)) {
                                return false;
                            }
                            SmallSortedMap$1.this.remove(entry.getKey());
                            return true;
                        }

                        @Override // java.util.AbstractCollection, java.util.Collection,
                                  // java.util.Set
                        public final int size() {
                            return SmallSortedMap$1.this.size();
                        }
                    };
        }
        return this.lazyEntrySet;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmallSortedMap$1)) {
            return super.equals(obj);
        }
        SmallSortedMap$1 smallSortedMap$1 = (SmallSortedMap$1) obj;
        int size = size();
        if (size != smallSortedMap$1.size()) {
            return false;
        }
        int size2 = this.entryList.size();
        if (size2 != smallSortedMap$1.entryList.size()) {
            return entrySet().equals(smallSortedMap$1.entrySet());
        }
        for (int i = 0; i < size2; i++) {
            if (!getArrayEntryAt(i).equals(smallSortedMap$1.getArrayEntryAt(i))) {
                return false;
            }
        }
        if (size2 != size) {
            return this.overflowEntries.equals(smallSortedMap$1.overflowEntries);
        }
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Comparable comparable = (Comparable) obj;
        int binarySearchInArray = binarySearchInArray(comparable);
        return binarySearchInArray >= 0
                ? ((SmallSortedMap$Entry) this.entryList.get(binarySearchInArray)).value
                : this.overflowEntries.get(comparable);
    }

    public final Map.Entry getArrayEntryAt(int i) {
        return (Map.Entry) this.entryList.get(i);
    }

    public final Iterable getOverflowEntries() {
        return this.overflowEntries.isEmpty()
                ? SmallSortedMap$EmptySet.ITERABLE
                : this.overflowEntries.entrySet();
    }

    public final SortedMap getOverflowEntriesMutable() {
        checkMutable();
        if (this.overflowEntries.isEmpty() && !(this.overflowEntries instanceof TreeMap)) {
            TreeMap treeMap = new TreeMap();
            this.overflowEntries = treeMap;
            this.overflowEntriesDescending = treeMap.descendingMap();
        }
        return (SortedMap) this.overflowEntries;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        int size = this.entryList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += ((SmallSortedMap$Entry) this.entryList.get(i2)).hashCode();
        }
        return this.overflowEntries.size() > 0 ? i + this.overflowEntries.hashCode() : i;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        checkMutable();
        Comparable comparable = (Comparable) obj;
        int binarySearchInArray = binarySearchInArray(comparable);
        if (binarySearchInArray >= 0) {
            return removeArrayEntryAt(binarySearchInArray);
        }
        if (this.overflowEntries.isEmpty()) {
            return null;
        }
        return this.overflowEntries.remove(comparable);
    }

    public final Object removeArrayEntryAt(int i) {
        checkMutable();
        Object obj = ((SmallSortedMap$Entry) this.entryList.remove(i)).value;
        if (!this.overflowEntries.isEmpty()) {
            Iterator it = getOverflowEntriesMutable().entrySet().iterator();
            List list = this.entryList;
            Map.Entry entry = (Map.Entry) it.next();
            list.add(new SmallSortedMap$Entry(this, (Comparable) entry.getKey(), entry.getValue()));
            it.remove();
        }
        return obj;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.overflowEntries.size() + this.entryList.size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Comparable comparable, Object obj) {
        checkMutable();
        int binarySearchInArray = binarySearchInArray(comparable);
        if (binarySearchInArray >= 0) {
            return ((SmallSortedMap$Entry) this.entryList.get(binarySearchInArray)).setValue(obj);
        }
        checkMutable();
        if (this.entryList.isEmpty() && !(this.entryList instanceof ArrayList)) {
            this.entryList = new ArrayList(this.maxArraySize);
        }
        int i = -(binarySearchInArray + 1);
        if (i >= this.maxArraySize) {
            return getOverflowEntriesMutable().put(comparable, obj);
        }
        int size = this.entryList.size();
        int i2 = this.maxArraySize;
        if (size == i2) {
            SmallSortedMap$Entry smallSortedMap$Entry =
                    (SmallSortedMap$Entry) this.entryList.remove(i2 - 1);
            getOverflowEntriesMutable().put(smallSortedMap$Entry.key, smallSortedMap$Entry.value);
        }
        this.entryList.add(i, new SmallSortedMap$Entry(this, comparable, obj));
        return null;
    }
}
