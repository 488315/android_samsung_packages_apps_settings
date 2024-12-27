package com.google.common.collect;

import com.google.common.base.Preconditions;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class Sets {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class ImprovedAbstractSet extends AbstractSet {
        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection,
                  // java.util.Set
        public boolean removeAll(Collection collection) {
            return Sets.removeAllImpl(this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection collection) {
            collection.getClass();
            return super.retainAll(collection);
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.common.collect.Sets$3] */
    public static AnonymousClass3 difference(final Set set, final Set set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new AbstractSet() { // from class: com.google.common.collect.Sets.3

            /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
            /* renamed from: com.google.common.collect.Sets$3$1, reason: invalid class name */
            public final class AnonymousClass1 extends UnmodifiableIterator {
                public final Iterator itr;
                public Object next;
                public AbstractIterator$State state = AbstractIterator$State.NOT_READY;

                public AnonymousClass1() {
                    this.itr = set.iterator();
                }

                @Override // java.util.Iterator
                public final boolean hasNext() {
                    AbstractIterator$State abstractIterator$State;
                    Object obj;
                    AbstractIterator$State abstractIterator$State2 = this.state;
                    AbstractIterator$State abstractIterator$State3 = AbstractIterator$State.FAILED;
                    if (abstractIterator$State2 == abstractIterator$State3) {
                        throw new IllegalStateException();
                    }
                    int ordinal = abstractIterator$State2.ordinal();
                    if (ordinal == 0) {
                        return true;
                    }
                    if (ordinal == 2) {
                        return false;
                    }
                    this.state = abstractIterator$State3;
                    while (true) {
                        boolean hasNext = this.itr.hasNext();
                        abstractIterator$State = AbstractIterator$State.DONE;
                        if (!hasNext) {
                            this.state = abstractIterator$State;
                            obj = null;
                            break;
                        }
                        obj = this.itr.next();
                        if (!set2.contains(obj)) {
                            break;
                        }
                    }
                    this.next = obj;
                    if (this.state == abstractIterator$State) {
                        return false;
                    }
                    this.state = AbstractIterator$State.READY;
                    return true;
                }

                @Override // java.util.Iterator
                public final Object next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.state = AbstractIterator$State.NOT_READY;
                    Object obj = this.next;
                    this.next = null;
                    return obj;
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean add(Object obj) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean addAll(Collection collection) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final void clear() {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean contains(Object obj) {
                return set.contains(obj) && !set2.contains(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean isEmpty() {
                return set2.containsAll(set);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                      // java.util.Set
            public final Iterator iterator() {
                return new AnonymousClass1();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean remove(Object obj) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection,
                      // java.util.Set
            public final boolean removeAll(Collection collection) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean retainAll(Collection collection) {
                throw new UnsupportedOperationException();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final int size() {
                Iterator it = set.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (!set2.contains(it.next())) {
                        i++;
                    }
                }
                return i;
            }
        };
    }

    public static boolean equalsImpl(Set set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof Set) {
            Set set2 = (Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    public static int hashCodeImpl(Set set) {
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    public static boolean removeAllImpl(Set set, Collection collection) {
        collection.getClass();
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        boolean z = false;
        if (!(collection instanceof Set) || collection.size() <= set.size()) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                z |= set.remove(it.next());
            }
            return z;
        }
        Iterator it2 = set.iterator();
        collection.getClass();
        while (it2.hasNext()) {
            if (collection.contains(it2.next())) {
                it2.remove();
                z = true;
            }
        }
        return z;
    }
}
