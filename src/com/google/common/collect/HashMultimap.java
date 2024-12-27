package com.google.common.collect;

import com.google.common.base.Preconditions;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class HashMultimap<K, V> extends HashMultimapGwtSerializationDependencies<K, V> {
    private static final long serialVersionUID = 0;
    public transient int expectedValuesPerKey;

    private HashMultimap() {
        CompactHashMap createWithExpectedSize = CompactHashMap.createWithExpectedSize();
        Preconditions.checkArgument(createWithExpectedSize.isEmpty());
        this.map = createWithExpectedSize;
        this.expectedValuesPerKey = 2;
    }

    public static HashMultimap create() {
        return new HashMultimap();
    }

    private void readObject(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.expectedValuesPerKey = 2;
        int readInt = objectInputStream.readInt();
        CompactHashMap createWithExpectedSize = CompactHashMap.createWithExpectedSize();
        this.map = createWithExpectedSize;
        this.totalSize = 0;
        Iterator it = ((CompactHashMap.ValuesView) createWithExpectedSize.values()).iterator();
        while (it.hasNext()) {
            Collection collection = (Collection) it.next();
            Preconditions.checkArgument(!collection.isEmpty());
            this.totalSize = collection.size() + this.totalSize;
        }
        for (int i = 0; i < readInt; i++) {
            Object readObject = objectInputStream.readObject();
            Object obj = (Collection) this.map.get(readObject);
            if (obj == null) {
                obj = CompactHashSet.createWithExpectedSize(this.expectedValuesPerKey);
            }
            AbstractMapBasedMultimap.WrappedSet wrappedSet =
                    new AbstractMapBasedMultimap.WrappedSet(readObject, (Set) obj);
            int readInt2 = objectInputStream.readInt();
            for (int i2 = 0; i2 < readInt2; i2++) {
                wrappedSet.add(objectInputStream.readObject());
            }
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(asMap().submap.size());
        Iterator it = ((AbstractMapBasedMultimap.AsMap.AsMapEntries) asMap().entrySet()).iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeInt(((Collection) entry.getValue()).size());
            Iterator it2 = ((Collection) entry.getValue()).iterator();
            while (it2.hasNext()) {
                objectOutputStream.writeObject(it2.next());
            }
        }
    }

    @Override // com.google.common.collect.AbstractMultimap
    public final AbstractMapBasedMultimap.AsMap asMap() {
        AbstractMapBasedMultimap.AsMap asMap = this.asMap;
        if (asMap != null) {
            return asMap;
        }
        AbstractMapBasedMultimap.AsMap asMap2 = new AbstractMapBasedMultimap.AsMap(this.map);
        this.asMap = asMap2;
        return asMap2;
    }

    @Override // com.google.common.collect.AbstractMapBasedMultimap
    public final void clear() {
        Iterator<V> it = this.map.values().iterator();
        while (it.hasNext()) {
            ((Collection) it.next()).clear();
        }
        this.map.clear();
        this.totalSize = 0;
    }

    public final boolean put(Object obj, Object obj2) {
        Collection collection = (Collection) this.map.get(obj);
        if (collection != null) {
            if (!collection.add(obj2)) {
                return false;
            }
            this.totalSize++;
            return true;
        }
        CompactHashSet createWithExpectedSize =
                CompactHashSet.createWithExpectedSize(this.expectedValuesPerKey);
        if (!createWithExpectedSize.add(obj2)) {
            throw new AssertionError("New Collection violated the Collection spec");
        }
        this.totalSize++;
        this.map.put(obj, createWithExpectedSize);
        return true;
    }
}
