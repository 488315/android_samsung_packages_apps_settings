package com.google.common.collect;

import com.google.common.base.Preconditions;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class Iterators$EmptyModifiableIterator implements Iterator<Object> {
    public static final /* synthetic */ Iterators$EmptyModifiableIterator[] $VALUES;
    public static final Iterators$EmptyModifiableIterator INSTANCE;

    static {
        Iterators$EmptyModifiableIterator iterators$EmptyModifiableIterator =
                new Iterators$EmptyModifiableIterator("INSTANCE", 0);
        INSTANCE = iterators$EmptyModifiableIterator;
        $VALUES = new Iterators$EmptyModifiableIterator[] {iterators$EmptyModifiableIterator};
    }

    public static Iterators$EmptyModifiableIterator valueOf(String str) {
        return (Iterators$EmptyModifiableIterator)
                Enum.valueOf(Iterators$EmptyModifiableIterator.class, str);
    }

    public static Iterators$EmptyModifiableIterator[] values() {
        return (Iterators$EmptyModifiableIterator[]) $VALUES.clone();
    }

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
        Preconditions.checkState("no calls to next() since the last call to remove()", false);
    }
}
