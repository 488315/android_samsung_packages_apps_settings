package com.google.common.collect;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Iterators$SingletonIterator extends UnmodifiableIterator {
    public static final Object SENTINEL = new Object();
    public Object valueOrSentinel;

    @Override // java.util.Iterator
    public final boolean hasNext() {
        return this.valueOrSentinel != SENTINEL;
    }

    @Override // java.util.Iterator
    public final Object next() {
        Object obj = this.valueOrSentinel;
        Object obj2 = SENTINEL;
        if (obj == obj2) {
            throw new NoSuchElementException();
        }
        this.valueOrSentinel = obj2;
        return obj;
    }
}
