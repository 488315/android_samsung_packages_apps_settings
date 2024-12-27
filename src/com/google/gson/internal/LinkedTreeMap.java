package com.google.gson.internal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LinkedTreeMap<K, V> extends AbstractMap<K, V> implements Serializable {
    public static final AnonymousClass1 NATURAL_ORDER = new AnonymousClass1();
    private final boolean allowNullValues;
    private final Comparator<? super K> comparator;
    private EntrySet entrySet;
    final Node header;
    private KeySet keySet;
    int modCount;
    Node root;
    int size;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.gson.internal.LinkedTreeMap$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo((Comparable) obj2);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class EntrySet extends AbstractSet {
        public EntrySet() {}

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            LinkedTreeMap.this.clear();
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x002a A[ORIG_RETURN, RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:? A[RETURN, SYNTHETIC] */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean contains(java.lang.Object r4) {
            /*
                r3 = this;
                boolean r0 = r4 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 == 0) goto L2b
                com.google.gson.internal.LinkedTreeMap r3 = com.google.gson.internal.LinkedTreeMap.this
                java.util.Map$Entry r4 = (java.util.Map.Entry) r4
                r3.getClass()
                java.lang.Object r0 = r4.getKey()
                r2 = 0
                if (r0 == 0) goto L18
                com.google.gson.internal.LinkedTreeMap$Node r3 = r3.find(r0, r1)     // Catch: java.lang.ClassCastException -> L18
                goto L19
            L18:
                r3 = r2
            L19:
                if (r3 == 0) goto L28
                java.lang.Object r0 = r3.value
                java.lang.Object r4 = r4.getValue()
                boolean r4 = java.util.Objects.equals(r0, r4)
                if (r4 == 0) goto L28
                r2 = r3
            L28:
                if (r2 == 0) goto L2b
                r1 = 1
            L2b:
                return r1
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.google.gson.internal.LinkedTreeMap.EntrySet.contains(java.lang.Object):boolean");
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                  // java.util.Set
        public final Iterator iterator() {
            return new KeySet.AnonymousClass1(LinkedTreeMap.this, 1);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x002b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:14:0x002c  */
        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean remove(java.lang.Object r5) {
            /*
                r4 = this;
                boolean r0 = r5 instanceof java.util.Map.Entry
                r1 = 0
                if (r0 != 0) goto L6
                return r1
            L6:
                com.google.gson.internal.LinkedTreeMap r0 = com.google.gson.internal.LinkedTreeMap.this
                java.util.Map$Entry r5 = (java.util.Map.Entry) r5
                r0.getClass()
                java.lang.Object r2 = r5.getKey()
                r3 = 0
                if (r2 == 0) goto L19
                com.google.gson.internal.LinkedTreeMap$Node r0 = r0.find(r2, r1)     // Catch: java.lang.ClassCastException -> L19
                goto L1a
            L19:
                r0 = r3
            L1a:
                if (r0 == 0) goto L29
                java.lang.Object r2 = r0.value
                java.lang.Object r5 = r5.getValue()
                boolean r5 = java.util.Objects.equals(r2, r5)
                if (r5 == 0) goto L29
                r3 = r0
            L29:
                if (r3 != 0) goto L2c
                return r1
            L2c:
                com.google.gson.internal.LinkedTreeMap r4 = com.google.gson.internal.LinkedTreeMap.this
                r5 = 1
                r4.removeInternal(r3, r5)
                return r5
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.google.gson.internal.LinkedTreeMap.EntrySet.remove(java.lang.Object):boolean");
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return LinkedTreeMap.this.size;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class KeySet extends AbstractSet {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.google.gson.internal.LinkedTreeMap$KeySet$1, reason: invalid class name */
        public final class AnonymousClass1 implements Iterator {
            public final /* synthetic */ int $r8$classId;
            public int expectedModCount;
            public Node lastReturned = null;
            public Node next;
            public final /* synthetic */ LinkedTreeMap this$0;

            public AnonymousClass1(LinkedTreeMap linkedTreeMap, int i) {
                this.$r8$classId = i;
                this.this$0 = linkedTreeMap;
                this.next = linkedTreeMap.header.next;
                this.expectedModCount = linkedTreeMap.modCount;
            }

            @Override // java.util.Iterator
            public final boolean hasNext() {
                return this.next != this.this$0.header;
            }

            @Override // java.util.Iterator
            public Object next() {
                switch (this.$r8$classId) {
                    case 0:
                        return nextNode().key;
                    default:
                        return next$com$google$gson$internal$LinkedTreeMap$LinkedTreeMapIterator();
                }
            }

            public final Object
                    next$com$google$gson$internal$LinkedTreeMap$LinkedTreeMapIterator() {
                return nextNode();
            }

            public final Node nextNode() {
                Node node = this.next;
                LinkedTreeMap linkedTreeMap = this.this$0;
                if (node == linkedTreeMap.header) {
                    throw new NoSuchElementException();
                }
                if (linkedTreeMap.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                this.next = node.next;
                this.lastReturned = node;
                return node;
            }

            @Override // java.util.Iterator
            public final void remove() {
                Node node = this.lastReturned;
                if (node == null) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInternal(node, true);
                this.lastReturned = null;
                this.expectedModCount = this.this$0.modCount;
            }
        }

        public KeySet() {}

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            LinkedTreeMap.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean contains(Object obj) {
            return LinkedTreeMap.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable,
                  // java.util.Set
        public final Iterator iterator() {
            return new AnonymousClass1(LinkedTreeMap.this, 0);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            LinkedTreeMap linkedTreeMap = LinkedTreeMap.this;
            linkedTreeMap.getClass();
            Node node = null;
            if (obj != null) {
                try {
                    node = linkedTreeMap.find(obj, false);
                } catch (ClassCastException unused) {
                }
            }
            if (node != null) {
                linkedTreeMap.removeInternal(node, true);
            }
            return node != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final int size() {
            return LinkedTreeMap.this.size;
        }
    }

    public LinkedTreeMap() {
        this(true);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("Deserialization is unsupported");
    }

    private Object writeReplace() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        this.root = null;
        this.size = 0;
        this.modCount++;
        Node node = this.header;
        node.prev = node;
        node.next = node;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Node node = null;
        if (obj != null) {
            try {
                node = find(obj, false);
            } catch (ClassCastException unused) {
            }
        }
        return node != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        EntrySet entrySet = this.entrySet;
        if (entrySet != null) {
            return entrySet;
        }
        EntrySet entrySet2 = new EntrySet();
        this.entrySet = entrySet2;
        return entrySet2;
    }

    public final Node find(Object obj, boolean z) {
        int i;
        Node node;
        Comparator<? super K> comparator = this.comparator;
        Node node2 = this.root;
        AnonymousClass1 anonymousClass1 = NATURAL_ORDER;
        if (node2 != null) {
            Comparable comparable = comparator == anonymousClass1 ? (Comparable) obj : null;
            while (true) {
                i =
                        comparable != null
                                ? comparable.compareTo(node2.key)
                                : comparator.compare(obj, (Object) node2.key);
                if (i == 0) {
                    return node2;
                }
                Node node3 = i < 0 ? node2.left : node2.right;
                if (node3 == null) {
                    break;
                }
                node2 = node3;
            }
        } else {
            i = 0;
        }
        if (!z) {
            return null;
        }
        Node node4 = this.header;
        if (node2 != null) {
            node = new Node(this.allowNullValues, node2, obj, node4, node4.prev);
            if (i < 0) {
                node2.left = node;
            } else {
                node2.right = node;
            }
            rebalance(node2, true);
        } else {
            if (comparator == anonymousClass1 && !(obj instanceof Comparable)) {
                throw new ClassCastException(obj.getClass().getName().concat(" is not Comparable"));
            }
            node = new Node(this.allowNullValues, node2, obj, node4, node4.prev);
            this.root = node;
        }
        this.size++;
        this.modCount++;
        return node;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000c  */
    @Override // java.util.AbstractMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object get(java.lang.Object r3) {
        /*
            r2 = this;
            r0 = 0
            if (r3 == 0) goto L9
            r1 = 0
            com.google.gson.internal.LinkedTreeMap$Node r2 = r2.find(r3, r1)     // Catch: java.lang.ClassCastException -> L9
            goto La
        L9:
            r2 = r0
        La:
            if (r2 == 0) goto Le
            java.lang.Object r0 = r2.value
        Le:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.LinkedTreeMap.get(java.lang.Object):java.lang.Object");
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        KeySet keySet = this.keySet;
        if (keySet != null) {
            return keySet;
        }
        KeySet keySet2 = new KeySet();
        this.keySet = keySet2;
        return keySet2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object put(Object obj, Object obj2) {
        if (obj == null) {
            throw new NullPointerException("key == null");
        }
        if (obj2 == null && !this.allowNullValues) {
            throw new NullPointerException("value == null");
        }
        Node find = find(obj, true);
        Object obj3 = find.value;
        find.value = obj2;
        return obj3;
    }

    public final void rebalance(Node node, boolean z) {
        while (node != null) {
            Node node2 = node.left;
            Node node3 = node.right;
            int i = node2 != null ? node2.height : 0;
            int i2 = node3 != null ? node3.height : 0;
            int i3 = i - i2;
            if (i3 == -2) {
                Node node4 = node3.left;
                Node node5 = node3.right;
                int i4 = (node4 != null ? node4.height : 0) - (node5 != null ? node5.height : 0);
                if (i4 == -1 || (i4 == 0 && !z)) {
                    rotateLeft(node);
                } else {
                    rotateRight(node3);
                    rotateLeft(node);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                Node node6 = node2.left;
                Node node7 = node2.right;
                int i5 = (node6 != null ? node6.height : 0) - (node7 != null ? node7.height : 0);
                if (i5 == 1 || (i5 == 0 && !z)) {
                    rotateRight(node);
                } else {
                    rotateLeft(node2);
                    rotateRight(node);
                }
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                node.height = i + 1;
                if (z) {
                    return;
                }
            } else {
                node.height = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            }
            node = node.parent;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x000c  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0012  */
    @Override // java.util.AbstractMap, java.util.Map
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object remove(java.lang.Object r3) {
        /*
            r2 = this;
            r0 = 0
            if (r3 == 0) goto L9
            r1 = 0
            com.google.gson.internal.LinkedTreeMap$Node r3 = r2.find(r3, r1)     // Catch: java.lang.ClassCastException -> L9
            goto La
        L9:
            r3 = r0
        La:
            if (r3 == 0) goto L10
            r1 = 1
            r2.removeInternal(r3, r1)
        L10:
            if (r3 == 0) goto L14
            java.lang.Object r0 = r3.value
        L14:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.gson.internal.LinkedTreeMap.remove(java.lang.Object):java.lang.Object");
    }

    public final void removeInternal(Node node, boolean z) {
        Node node2;
        Node node3;
        int i;
        if (z) {
            Node node4 = node.prev;
            node4.next = node.next;
            node.next.prev = node4;
        }
        Node node5 = node.left;
        Node node6 = node.right;
        Node node7 = node.parent;
        int i2 = 0;
        if (node5 == null || node6 == null) {
            if (node5 != null) {
                replaceInParent(node, node5);
                node.left = null;
            } else if (node6 != null) {
                replaceInParent(node, node6);
                node.right = null;
            } else {
                replaceInParent(node, null);
            }
            rebalance(node7, false);
            this.size--;
            this.modCount++;
            return;
        }
        if (node5.height > node6.height) {
            Node node8 = node5.right;
            while (true) {
                Node node9 = node8;
                node3 = node5;
                node5 = node9;
                if (node5 == null) {
                    break;
                } else {
                    node8 = node5.right;
                }
            }
        } else {
            Node node10 = node6.left;
            while (true) {
                node2 = node6;
                node6 = node10;
                if (node6 == null) {
                    break;
                } else {
                    node10 = node6.left;
                }
            }
            node3 = node2;
        }
        removeInternal(node3, false);
        Node node11 = node.left;
        if (node11 != null) {
            i = node11.height;
            node3.left = node11;
            node11.parent = node3;
            node.left = null;
        } else {
            i = 0;
        }
        Node node12 = node.right;
        if (node12 != null) {
            i2 = node12.height;
            node3.right = node12;
            node12.parent = node3;
            node.right = null;
        }
        node3.height = Math.max(i, i2) + 1;
        replaceInParent(node, node3);
    }

    public final void replaceInParent(Node node, Node node2) {
        Node node3 = node.parent;
        node.parent = null;
        if (node2 != null) {
            node2.parent = node3;
        }
        if (node3 == null) {
            this.root = node2;
        } else if (node3.left == node) {
            node3.left = node2;
        } else {
            node3.right = node2;
        }
    }

    public final void rotateLeft(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node3.left;
        Node node5 = node3.right;
        node.right = node4;
        if (node4 != null) {
            node4.parent = node;
        }
        replaceInParent(node, node3);
        node3.left = node;
        node.parent = node3;
        int max = Math.max(node2 != null ? node2.height : 0, node4 != null ? node4.height : 0) + 1;
        node.height = max;
        node3.height = Math.max(max, node5 != null ? node5.height : 0) + 1;
    }

    public final void rotateRight(Node node) {
        Node node2 = node.left;
        Node node3 = node.right;
        Node node4 = node2.left;
        Node node5 = node2.right;
        node.left = node5;
        if (node5 != null) {
            node5.parent = node;
        }
        replaceInParent(node, node2);
        node2.right = node;
        node.parent = node2;
        int max = Math.max(node3 != null ? node3.height : 0, node5 != null ? node5.height : 0) + 1;
        node.height = max;
        node2.height = Math.max(max, node4 != null ? node4.height : 0) + 1;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.size;
    }

    public LinkedTreeMap(boolean z) {
        AnonymousClass1 anonymousClass1 = NATURAL_ORDER;
        this.size = 0;
        this.modCount = 0;
        this.comparator = anonymousClass1;
        this.allowNullValues = z;
        this.header = new Node(z);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Node implements Map.Entry {
        public final boolean allowNullValue;
        public int height;
        public final Object key;
        public Node left;
        public Node next;
        public Node parent;
        public Node prev;
        public Node right;
        public Object value;

        public Node(boolean z) {
            this.key = null;
            this.allowNullValue = z;
            this.prev = this;
            this.next = this;
        }

        @Override // java.util.Map.Entry
        public final boolean equals(Object obj) {
            if (!(obj instanceof Map.Entry)) {
                return false;
            }
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = this.key;
            if (obj2 == null) {
                if (entry.getKey() != null) {
                    return false;
                }
            } else if (!obj2.equals(entry.getKey())) {
                return false;
            }
            Object obj3 = this.value;
            if (obj3 == null) {
                if (entry.getValue() != null) {
                    return false;
                }
            } else if (!obj3.equals(entry.getValue())) {
                return false;
            }
            return true;
        }

        @Override // java.util.Map.Entry
        public final Object getKey() {
            return this.key;
        }

        @Override // java.util.Map.Entry
        public final Object getValue() {
            return this.value;
        }

        @Override // java.util.Map.Entry
        public final int hashCode() {
            Object obj = this.key;
            int hashCode = obj == null ? 0 : obj.hashCode();
            Object obj2 = this.value;
            return hashCode ^ (obj2 != null ? obj2.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public final Object setValue(Object obj) {
            if (obj == null && !this.allowNullValue) {
                throw new NullPointerException("value == null");
            }
            Object obj2 = this.value;
            this.value = obj;
            return obj2;
        }

        public final String toString() {
            return this.key + "=" + this.value;
        }

        public Node(boolean z, Node node, Object obj, Node node2, Node node3) {
            this.parent = node;
            this.key = obj;
            this.allowNullValue = z;
            this.height = 1;
            this.next = node2;
            this.prev = node3;
            node3.next = this;
            node2.prev = this;
        }
    }
}
