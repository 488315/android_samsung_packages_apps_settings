package com.google.common.collect;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ImmutableList<E> extends ImmutableCollection<E>
        implements List<E>, RandomAccess {
    public static final Itr EMPTY_ITR = new Itr(RegularImmutableList.EMPTY, 0);
    private static final long serialVersionUID = -889275714;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Itr extends UnmodifiableIterator implements ListIterator {
        public final ImmutableList list;
        public int position;
        public final int size;

        public Itr(ImmutableList immutableList, int i) {
            int size = immutableList.size();
            Preconditions.checkPositionIndex(i, size);
            this.size = size;
            this.position = i;
            this.list = immutableList;
        }

        @Override // java.util.ListIterator
        public final void add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public final boolean hasNext() {
            return this.position < this.size;
        }

        @Override // java.util.ListIterator
        public final boolean hasPrevious() {
            return this.position > 0;
        }

        @Override // java.util.Iterator, java.util.ListIterator
        public final Object next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int i = this.position;
            this.position = i + 1;
            return this.list.get(i);
        }

        @Override // java.util.ListIterator
        public final int nextIndex() {
            return this.position;
        }

        @Override // java.util.ListIterator
        public final Object previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            int i = this.position - 1;
            this.position = i;
            return this.list.get(i);
        }

        @Override // java.util.ListIterator
        public final int previousIndex() {
            return this.position - 1;
        }

        @Override // java.util.ListIterator
        public final void set(Object obj) {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        public SerializedForm(Object[] objArr) {
            this.elements = objArr;
        }

        public Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class SubList extends ImmutableList<E> {
        public final transient int length;
        public final transient int offset;

        public SubList(int i, int i2) {
            this.offset = i;
            this.length = i2;
        }

        @Override // java.util.List
        public final Object get(int i) {
            Preconditions.checkElementIndex(i, this.length);
            return ImmutableList.this.get(i + this.offset);
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final Object[] internalArray() {
            return ImmutableList.this.internalArray();
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int internalArrayEnd() {
            return ImmutableList.this.internalArrayStart() + this.offset + this.length;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final int internalArrayStart() {
            return ImmutableList.this.internalArrayStart() + this.offset;
        }

        @Override // com.google.common.collect.ImmutableCollection
        public final boolean isPartialView() {
            return true;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public final int size() {
            return this.length;
        }

        @Override // com.google.common.collect.ImmutableList,
                  // com.google.common.collect.ImmutableCollection
        public Object writeReplace() {
            return super.writeReplace();
        }

        @Override // com.google.common.collect.ImmutableList, java.util.List
        public final ImmutableList subList(int i, int i2) {
            Preconditions.checkPositionIndexes(i, i2, this.length);
            ImmutableList immutableList = ImmutableList.this;
            int i3 = this.offset;
            return immutableList.subList(i + i3, i2 + i3);
        }
    }

    public static ImmutableList asImmutableList(int i, Object[] objArr) {
        return i == 0 ? RegularImmutableList.EMPTY : new RegularImmutableList(i, objArr);
    }

    public static ImmutableList construct(Object... objArr) {
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            if (objArr[i] == null) {
                throw new NullPointerException(
                        SeslRoundedCorner$$ExternalSyntheticOutline0.m(i, "at index "));
            }
        }
        return asImmutableList(objArr.length, objArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ImmutableList copyOf(Splitter.AnonymousClass5 anonymousClass5) {
        Object[] objArr;
        if (anonymousClass5 instanceof Collection) {
            return copyOf((Collection) anonymousClass5);
        }
        Splitter.AnonymousClass1.C00501 c00501 =
                (Splitter.AnonymousClass1.C00501) anonymousClass5.iterator();
        if (!c00501.hasNext()) {
            return RegularImmutableList.EMPTY;
        }
        Object next = c00501.next();
        if (!c00501.hasNext()) {
            return construct(next);
        }
        CollectPreconditions.checkNonnegative(4, "initialCapacity");
        Object[] objArr2 = new Object[4];
        next.getClass();
        int i = 0 + 1;
        if (objArr2.length < i) {
            objArr2 =
                    Arrays.copyOf(
                            objArr2,
                            ImmutableCollection.Builder.expandedCapacity(objArr2.length, i));
        }
        int i2 = 0 + 1;
        objArr2[0] = next;
        boolean z = false;
        while (c00501.hasNext()) {
            Object next2 = c00501.next();
            next2.getClass();
            int i3 = i2 + 1;
            if (objArr2.length < i3) {
                objArr =
                        Arrays.copyOf(
                                objArr2,
                                ImmutableCollection.Builder.expandedCapacity(objArr2.length, i3));
            } else if (z) {
                objArr = (Object[]) objArr2.clone();
            } else {
                objArr2[i2] = next2;
                i2++;
            }
            objArr2 = objArr;
            z = false;
            objArr2[i2] = next2;
            i2++;
        }
        return asImmutableList(i2, objArr2);
    }

    public static ImmutableList of() {
        return RegularImmutableList.EMPTY;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
              // java.util.Collection
    public final boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public int copyIntoArray(Object[] objArr) {
        int size = size();
        for (int i = 0; i < size; i++) {
            objArr[i] = get(i);
        }
        return size;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (list instanceof RandomAccess) {
                    for (int i = 0; i < size; i++) {
                        if (Objects.equal(get(i), list.get(i))) {}
                    }
                    return true;
                }
                Iterator<E> it = iterator();
                Iterator<E> it2 = list.iterator();
                while (it.hasNext()) {
                    if (it2.hasNext() && Objects.equal(it.next(), it2.next())) {}
                }
                return !it2.hasNext();
            }
        }
        return false;
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = ~(~(get(i2).hashCode() + (i * 31)));
        }
        return i;
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (obj.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
              // java.util.Collection, java.lang.Iterable
    public final UnmodifiableIterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        for (int size = size() - 1; size >= 0; size--) {
            if (obj.equals(get(size))) {
                return size;
            }
        }
        return -1;
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.common.collect.ImmutableCollection
    public Object writeReplace() {
        return new SerializedForm(toArray(ImmutableCollection.EMPTY_ARRAY));
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
              // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.List
    public ImmutableList subList(int i, int i2) {
        Preconditions.checkPositionIndexes(i, i2, size());
        int i3 = i2 - i;
        return i3 == size() ? this : i3 == 0 ? RegularImmutableList.EMPTY : new SubList(i, i3);
    }

    @Override // java.util.List
    public final Itr listIterator(int i) {
        Preconditions.checkPositionIndex(i, size());
        if (isEmpty()) {
            return EMPTY_ITR;
        }
        return new Itr(this, i);
    }

    public static ImmutableList copyOf(Collection collection) {
        if (collection instanceof ImmutableCollection) {
            ImmutableList asList = ((ImmutableCollection) collection).asList();
            if (!asList.isPartialView()) {
                return asList;
            }
            Object[] array = asList.toArray(ImmutableCollection.EMPTY_ARRAY);
            return asImmutableList(array.length, array);
        }
        return construct(collection.toArray());
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final ImmutableList asList() {
        return this;
    }

    public static ImmutableList copyOf(Object[] objArr) {
        if (objArr.length == 0) {
            return RegularImmutableList.EMPTY;
        }
        return construct((Object[]) objArr.clone());
    }
}
