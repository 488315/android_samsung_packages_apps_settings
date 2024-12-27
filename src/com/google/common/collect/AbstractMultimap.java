package com.google.common.collect;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class AbstractMultimap {
    public transient AbstractMapBasedMultimap.AsMap asMap;
    public transient EntrySet entries;
    public transient AbstractMapBasedMultimap.KeySet keySet;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EntrySet extends AbstractCollection implements Set {
        public final /* synthetic */ AbstractMultimap this$0;

        public EntrySet(HashMultimap hashMultimap) {
            this.this$0 = hashMultimap;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            ((HashMultimap) this.this$0).clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            AbstractMultimap abstractMultimap = this.this$0;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Collection collection = (Collection) abstractMultimap.asMap().get(key);
            return collection != null && collection.contains(value);
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            return Sets.equalsImpl(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            return Sets.hashCodeImpl(this);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                  // java.util.Set
        public final Iterator iterator() {
            AbstractMapBasedMultimap abstractMapBasedMultimap =
                    (AbstractMapBasedMultimap) this.this$0;
            abstractMapBasedMultimap.getClass();
            return new Iterator(
                    (HashMultimap)
                            abstractMapBasedMultimap) { // from class:
                                                        // com.google.common.collect.AbstractMapBasedMultimap.2
                public final Iterator keyIterator;
                public final /* synthetic */ AbstractMapBasedMultimap this$0$1;
                public Object key = null;
                public Collection collection = null;
                public Iterator valueIterator = Iterators$EmptyModifiableIterator.INSTANCE;

                public AnonymousClass2(HashMultimap hashMultimap) {
                    this.this$0$1 = hashMultimap;
                    this.keyIterator = hashMultimap.map.entrySet().iterator();
                }

                @Override // java.util.Iterator
                public final boolean hasNext() {
                    return this.keyIterator.hasNext() || this.valueIterator.hasNext();
                }

                @Override // java.util.Iterator
                public final Object next() {
                    if (!this.valueIterator.hasNext()) {
                        Map.Entry entry = (Map.Entry) this.keyIterator.next();
                        this.key = entry.getKey();
                        Collection collection = (Collection) entry.getValue();
                        this.collection = collection;
                        this.valueIterator = collection.iterator();
                    }
                    return new ImmutableEntry(this.key, this.valueIterator.next());
                }

                @Override // java.util.Iterator
                public final void remove() {
                    this.valueIterator.remove();
                    Collection collection = this.collection;
                    Objects.requireNonNull(collection);
                    if (collection.isEmpty()) {
                        this.keyIterator.remove();
                    }
                    AbstractMapBasedMultimap abstractMapBasedMultimap2 = this.this$0$1;
                    abstractMapBasedMultimap2.totalSize--;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            AbstractMultimap abstractMultimap = this.this$0;
            Object key = entry.getKey();
            Object value = entry.getValue();
            Collection collection = (Collection) abstractMultimap.asMap().get(key);
            return collection != null && collection.remove(value);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return ((HashMultimap) this.this$0).totalSize;
        }
    }

    public abstract AbstractMapBasedMultimap.AsMap asMap();

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractMultimap) {
            return ((HashMultimap) this)
                    .asMap()
                    .equals(((HashMultimap) ((AbstractMultimap) obj)).asMap());
        }
        return false;
    }

    public final int hashCode() {
        return asMap().submap.hashCode();
    }

    public final String toString() {
        return asMap().submap.toString();
    }
}
