package com.google.common.collect;

import androidx.preference.Preference;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    public transient Map map;
    public transient int totalSize;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AsMap extends AbstractMap {
        public transient AsMapEntries entrySet;
        public final transient Map submap;
        public transient Maps$Values values;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public final class AsMapEntries extends Sets.ImprovedAbstractSet {
            public AsMapEntries() {
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final void clear() {
                AsMap.this.clear();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean contains(Object obj) {
                Set<Map.Entry<K, V>> entrySet = AsMap.this.submap.entrySet();
                entrySet.getClass();
                try {
                    return entrySet.contains(obj);
                } catch (ClassCastException | NullPointerException unused) {
                    return false;
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean isEmpty() {
                return AsMap.this.isEmpty();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public final Iterator iterator() {
                return new KeySet.AnonymousClass1(AsMap.this);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean remove(Object obj) {
                Object obj2;
                if (!contains(obj)) {
                    return false;
                }
                Map.Entry entry = (Map.Entry) obj;
                Objects.requireNonNull(entry);
                AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                Object key = entry.getKey();
                Map map = abstractMapBasedMultimap.map;
                map.getClass();
                try {
                    obj2 = map.remove(key);
                } catch (ClassCastException | NullPointerException unused) {
                    obj2 = null;
                }
                Collection collection = (Collection) obj2;
                if (collection == null) {
                    return true;
                }
                int size = collection.size();
                collection.clear();
                abstractMapBasedMultimap.totalSize -= size;
                return true;
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean removeAll(Collection collection) {
                try {
                    collection.getClass();
                    return Sets.removeAllImpl(this, collection);
                } catch (UnsupportedOperationException unused) {
                    Iterator it = collection.iterator();
                    boolean z = false;
                    while (it.hasNext()) {
                        z |= this.remove(it.next());
                    }
                    return z;
                }
            }

            @Override // com.google.common.collect.Sets.ImprovedAbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final boolean retainAll(Collection collection) {
                int ceil;
                try {
                    collection.getClass();
                    return super.retainAll(collection);
                } catch (UnsupportedOperationException unused) {
                    int size = collection.size();
                    if (size < 3) {
                        CollectPreconditions.checkNonnegative(size, "expectedSize");
                        ceil = size + 1;
                    } else {
                        ceil = size < 1073741824 ? (int) Math.ceil(size / 0.75d) : Preference.DEFAULT_ORDER;
                    }
                    HashSet hashSet = new HashSet(ceil);
                    for (Object obj : collection) {
                        if (this.contains(obj) && (obj instanceof Map.Entry)) {
                            hashSet.add(((Map.Entry) obj).getKey());
                        }
                    }
                    return ((Sets.ImprovedAbstractSet) AsMap.this.keySet()).retainAll(hashSet);
                }
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public final int size() {
                return AsMap.this.submap.size();
            }
        }

        public AsMap(Map map) {
            this.submap = map;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final void clear() {
            Map map = this.submap;
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            if (map == abstractMapBasedMultimap.map) {
                abstractMapBasedMultimap.clear();
                return;
            }
            KeySet.AnonymousClass1 anonymousClass1 = new KeySet.AnonymousClass1(this);
            while (anonymousClass1.hasNext()) {
                anonymousClass1.next();
                anonymousClass1.remove();
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean containsKey(Object obj) {
            Map map = this.submap;
            map.getClass();
            try {
                return map.containsKey(obj);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set entrySet() {
            AsMapEntries asMapEntries = this.entrySet;
            if (asMapEntries != null) {
                return asMapEntries;
            }
            AsMapEntries asMapEntries2 = new AsMapEntries();
            this.entrySet = asMapEntries2;
            return asMapEntries2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final boolean equals(Object obj) {
            return this == obj || this.submap.equals(obj);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object get(Object obj) {
            Object obj2;
            Map map = this.submap;
            map.getClass();
            try {
                obj2 = map.get(obj);
            } catch (ClassCastException | NullPointerException unused) {
                obj2 = null;
            }
            Collection collection = (Collection) obj2;
            if (collection == null) {
                return null;
            }
            AbstractSetMultimap abstractSetMultimap = (AbstractSetMultimap) AbstractMapBasedMultimap.this;
            abstractSetMultimap.getClass();
            return new WrappedSet(obj, (Set) collection);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final int hashCode() {
            return this.submap.hashCode();
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Set keySet() {
            AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
            KeySet keySet = abstractMapBasedMultimap.keySet;
            if (keySet != null) {
                return keySet;
            }
            KeySet keySet2 = new KeySet(abstractMapBasedMultimap.map);
            abstractMapBasedMultimap.keySet = keySet2;
            return keySet2;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final Object remove(Object obj) {
            Collection collection = (Collection) this.submap.remove(obj);
            if (collection == null) {
                return null;
            }
            CompactHashSet createWithExpectedSize = CompactHashSet.createWithExpectedSize(((HashMultimap) AbstractMapBasedMultimap.this).expectedValuesPerKey);
            createWithExpectedSize.addAll(collection);
            AbstractMapBasedMultimap.this.totalSize -= collection.size();
            collection.clear();
            return createWithExpectedSize;
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final int size() {
            return this.submap.size();
        }

        @Override // java.util.AbstractMap
        public final String toString() {
            return this.submap.toString();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [com.google.common.collect.Maps$Values, java.util.Collection] */
        @Override // java.util.AbstractMap, java.util.Map
        public final Collection values() {
            Maps$Values maps$Values = this.values;
            if (maps$Values != null) {
                return maps$Values;
            }
            ?? r0 = new AbstractCollection(this) { // from class: com.google.common.collect.Maps$Values
                public final Map map;

                {
                    this.getClass();
                    this.map = this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final void clear() {
                    this.map.clear();
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final boolean contains(Object obj) {
                    return this.map.containsValue(obj);
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final boolean isEmpty() {
                    return this.map.isEmpty();
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
                public final Iterator iterator() {
                    return new Iterator(this.map.entrySet().iterator()) { // from class: com.google.common.collect.Maps$2
                        public final Iterator backingIterator;

                        {
                            r1.getClass();
                            this.backingIterator = r1;
                        }

                        @Override // java.util.Iterator
                        public final boolean hasNext() {
                            return this.backingIterator.hasNext();
                        }

                        @Override // java.util.Iterator
                        public final Object next() {
                            return ((Map.Entry) this.backingIterator.next()).getValue();
                        }

                        @Override // java.util.Iterator
                        public final void remove() {
                            this.backingIterator.remove();
                        }
                    };
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final boolean remove(Object obj) {
                    try {
                        return super.remove(obj);
                    } catch (UnsupportedOperationException unused) {
                        for (Map.Entry entry : this.map.entrySet()) {
                            if (com.google.common.base.Objects.equal(obj, entry.getValue())) {
                                this.map.remove(entry.getKey());
                                return true;
                            }
                        }
                        return false;
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final boolean removeAll(Collection collection) {
                    try {
                        collection.getClass();
                        return super.removeAll(collection);
                    } catch (UnsupportedOperationException unused) {
                        HashSet hashSet = new HashSet();
                        for (Map.Entry entry : this.map.entrySet()) {
                            if (collection.contains(entry.getValue())) {
                                hashSet.add(entry.getKey());
                            }
                        }
                        return this.map.keySet().removeAll(hashSet);
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final boolean retainAll(Collection collection) {
                    try {
                        collection.getClass();
                        return super.retainAll(collection);
                    } catch (UnsupportedOperationException unused) {
                        HashSet hashSet = new HashSet();
                        for (Map.Entry entry : this.map.entrySet()) {
                            if (collection.contains(entry.getValue())) {
                                hashSet.add(entry.getKey());
                            }
                        }
                        return this.map.keySet().retainAll(hashSet);
                    }
                }

                @Override // java.util.AbstractCollection, java.util.Collection
                public final int size() {
                    return this.map.size();
                }
            };
            this.values = r0;
            return r0;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WrappedSet extends AbstractCollection implements Set {
        public Collection delegate;
        public final Object key;
        public final /* synthetic */ AbstractMapBasedMultimap this$0$1;

        public WrappedSet(Object obj, Set set) {
            this.this$0$1 = AbstractMapBasedMultimap.this;
            this.key = obj;
            this.delegate = set;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean add(Object obj) {
            refreshIfEmpty();
            boolean isEmpty = this.delegate.isEmpty();
            boolean add = this.delegate.add(obj);
            if (add) {
                this.this$0$1.totalSize++;
                if (isEmpty) {
                    addToMap();
                }
            }
            return add;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean addAll(Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean addAll = this.delegate.addAll(collection);
            if (addAll) {
                int size2 = this.delegate.size();
                this.this$0$1.totalSize += size2 - size;
                if (size == 0) {
                    addToMap();
                }
            }
            return addAll;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void addToMap() {
            this.this$0$1.map.put(this.key, this.delegate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            int size = size();
            if (size == 0) {
                return;
            }
            this.delegate.clear();
            this.this$0$1.totalSize -= size;
            removeIfEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            refreshIfEmpty();
            return this.delegate.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean containsAll(Collection collection) {
            refreshIfEmpty();
            return this.delegate.containsAll(collection);
        }

        @Override // java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            refreshIfEmpty();
            return this.delegate.equals(obj);
        }

        @Override // java.util.Collection, java.util.Set
        public final int hashCode() {
            refreshIfEmpty();
            return this.delegate.hashCode();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            refreshIfEmpty();
            return new KeySet.AnonymousClass1(this);
        }

        public final void refreshIfEmpty() {
            Collection collection;
            if (!this.delegate.isEmpty() || (collection = (Collection) this.this$0$1.map.get(this.key)) == null) {
                return;
            }
            this.delegate = collection;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            refreshIfEmpty();
            boolean remove = this.delegate.remove(obj);
            if (remove) {
                AbstractMapBasedMultimap abstractMapBasedMultimap = this.this$0$1;
                abstractMapBasedMultimap.totalSize--;
                removeIfEmpty();
            }
            return remove;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean removeAll(Collection collection) {
            if (collection.isEmpty()) {
                return false;
            }
            int size = size();
            boolean removeAllImpl = Sets.removeAllImpl((Set) this.delegate, collection);
            if (removeAllImpl) {
                int size2 = this.delegate.size();
                AbstractMapBasedMultimap.this.totalSize += size2 - size;
                removeIfEmpty();
            }
            return removeAllImpl;
        }

        public final void removeIfEmpty() {
            if (this.delegate.isEmpty()) {
                this.this$0$1.map.remove(this.key);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean retainAll(Collection collection) {
            collection.getClass();
            int size = size();
            boolean retainAll = this.delegate.retainAll(collection);
            if (retainAll) {
                int size2 = this.delegate.size();
                this.this$0$1.totalSize += size2 - size;
                removeIfEmpty();
            }
            return retainAll;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            refreshIfEmpty();
            return this.delegate.size();
        }

        @Override // java.util.AbstractCollection
        public final String toString() {
            refreshIfEmpty();
            return this.delegate.toString();
        }
    }

    public abstract void clear();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeySet extends Sets.ImprovedAbstractSet {
        public final Map map;

        public KeySet(Map map) {
            map.getClass();
            this.map = map;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            Iterator it = iterator();
            while (true) {
                AnonymousClass1 anonymousClass1 = (AnonymousClass1) it;
                if (!anonymousClass1.hasNext()) {
                    return;
                }
                anonymousClass1.next();
                anonymousClass1.remove();
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean containsAll(Collection collection) {
            return this.map.keySet().containsAll(collection);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public final boolean equals(Object obj) {
            return this == obj || this.map.keySet().equals(obj);
        }

        @Override // java.util.AbstractSet, java.util.Collection, java.util.Set
        public final int hashCode() {
            return this.map.keySet().hashCode();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean isEmpty() {
            return this.map.isEmpty();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public final Iterator iterator() {
            return new AnonymousClass1(this, this.map.entrySet().iterator());
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            int i;
            Collection collection = (Collection) this.map.remove(obj);
            if (collection != null) {
                i = collection.size();
                collection.clear();
                AbstractMapBasedMultimap.this.totalSize -= i;
            } else {
                i = 0;
            }
            return i > 0;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return this.map.size();
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.common.collect.AbstractMapBasedMultimap$KeySet$1, reason: invalid class name */
        public final class AnonymousClass1 implements Iterator {
            public final /* synthetic */ int $r8$classId = 1;
            public Object entry;
            public final /* synthetic */ Object this$1;
            public final Iterator val$entryIterator;

            public AnonymousClass1(WrappedSet wrappedSet) {
                this.this$1 = wrappedSet;
                Collection collection = wrappedSet.delegate;
                this.entry = collection;
                this.val$entryIterator = collection instanceof List ? ((List) collection).listIterator() : collection.iterator();
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                switch (this.$r8$classId) {
                    case 0:
                        return this.val$entryIterator.hasNext();
                    case 1:
                        return this.val$entryIterator.hasNext();
                    default:
                        ((WrappedSet) this.this$1).refreshIfEmpty();
                        if (((WrappedSet) this.this$1).delegate == ((Collection) this.entry)) {
                            return this.val$entryIterator.hasNext();
                        }
                        throw new ConcurrentModificationException();
                }
            }

            @Override // java.util.Iterator
            public final Object next() {
                switch (this.$r8$classId) {
                    case 0:
                        Map.Entry entry = (Map.Entry) this.val$entryIterator.next();
                        this.entry = entry;
                        return entry.getKey();
                    case 1:
                        Map.Entry entry2 = (Map.Entry) this.val$entryIterator.next();
                        this.entry = (Collection) entry2.getValue();
                        AsMap asMap = (AsMap) this.this$1;
                        asMap.getClass();
                        Object key = entry2.getKey();
                        AbstractMapBasedMultimap abstractMapBasedMultimap = AbstractMapBasedMultimap.this;
                        Collection collection = (Collection) entry2.getValue();
                        AbstractSetMultimap abstractSetMultimap = (AbstractSetMultimap) abstractMapBasedMultimap;
                        abstractSetMultimap.getClass();
                        return new ImmutableEntry(key, new WrappedSet(key, (Set) collection));
                    default:
                        ((WrappedSet) this.this$1).refreshIfEmpty();
                        if (((WrappedSet) this.this$1).delegate == ((Collection) this.entry)) {
                            return this.val$entryIterator.next();
                        }
                        throw new ConcurrentModificationException();
                }
            }

            @Override // java.util.Iterator
            public final void remove() {
                switch (this.$r8$classId) {
                    case 0:
                        Preconditions.checkState("no calls to next() since the last call to remove()", ((Map.Entry) this.entry) != null);
                        Collection collection = (Collection) ((Map.Entry) this.entry).getValue();
                        this.val$entryIterator.remove();
                        AbstractMapBasedMultimap.this.totalSize -= collection.size();
                        collection.clear();
                        this.entry = null;
                        break;
                    case 1:
                        Preconditions.checkState("no calls to next() since the last call to remove()", ((Collection) this.entry) != null);
                        this.val$entryIterator.remove();
                        AbstractMapBasedMultimap.this.totalSize -= ((Collection) this.entry).size();
                        ((Collection) this.entry).clear();
                        this.entry = null;
                        break;
                    default:
                        this.val$entryIterator.remove();
                        WrappedSet wrappedSet = (WrappedSet) this.this$1;
                        AbstractMapBasedMultimap abstractMapBasedMultimap = wrappedSet.this$0$1;
                        abstractMapBasedMultimap.totalSize--;
                        wrappedSet.removeIfEmpty();
                        break;
                }
            }

            public AnonymousClass1(KeySet keySet, Iterator it) {
                this.this$1 = keySet;
                this.val$entryIterator = it;
            }

            public AnonymousClass1(AsMap asMap) {
                this.this$1 = asMap;
                this.val$entryIterator = asMap.submap.entrySet().iterator();
            }
        }
    }
}
