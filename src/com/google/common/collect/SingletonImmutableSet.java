package com.google.common.collect;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    public final transient Object element;

    public SingletonImmutableSet(Object obj) {
        obj.getClass();
        this.element = obj;
    }

    @Override // com.google.common.collect.ImmutableSet,
              // com.google.common.collect.ImmutableCollection
    public final ImmutableList asList() {
        return ImmutableList.construct(this.element);
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
              // java.util.Collection
    public final boolean contains(Object obj) {
        return this.element.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final int copyIntoArray(Object[] objArr) {
        objArr[0] = this.element;
        return 1;
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public final int hashCode() {
        return this.element.hashCode();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection,
              // java.util.Collection, java.lang.Iterable
    public final UnmodifiableIterator iterator() {
        Object obj = this.element;
        Iterators$SingletonIterator iterators$SingletonIterator = new Iterators$SingletonIterator();
        iterators$SingletonIterator.valueOrSentinel = obj;
        return iterators$SingletonIterator;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return 1;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return "[" + this.element.toString() + ']';
    }

    @Override // com.google.common.collect.ImmutableSet,
              // com.google.common.collect.ImmutableCollection
    public Object writeReplace() {
        return super.writeReplace();
    }
}
