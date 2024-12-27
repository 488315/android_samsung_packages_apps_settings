package com.google.protobuf;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SmallSortedMap$EmptySet {
    public static final AnonymousClass1 ITERATOR = new AnonymousClass1();
    public static final AnonymousClass2 ITERABLE = new AnonymousClass2();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.protobuf.SmallSortedMap$EmptySet$1, reason: invalid class name */
    public final class AnonymousClass1 implements Iterator {
        @Override // java.util.Iterator
        public final boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public final Object next() {
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public final void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.protobuf.SmallSortedMap$EmptySet$2, reason: invalid class name */
    public final class AnonymousClass2 implements Iterable {
        @Override // java.lang.Iterable
        public final Iterator iterator() {
            return SmallSortedMap$EmptySet.ITERATOR;
        }
    }
}
