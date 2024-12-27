package com.google.common.collect;

import com.google.common.base.Preconditions;

import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
class RegularImmutableList<E> extends ImmutableList<E> {
    public static final ImmutableList EMPTY = new RegularImmutableList(0, new Object[0]);
    public final transient Object[] array;
    public final transient int size;

    public RegularImmutableList(int i, Object[] objArr) {
        this.array = objArr;
        this.size = i;
    }

    @Override // com.google.common.collect.ImmutableList,
              // com.google.common.collect.ImmutableCollection
    public final int copyIntoArray(Object[] objArr) {
        System.arraycopy(this.array, 0, objArr, 0, this.size);
        return this.size;
    }

    @Override // java.util.List
    public final Object get(int i) {
        Preconditions.checkElementIndex(i, this.size);
        Object obj = this.array[i];
        Objects.requireNonNull(obj);
        return obj;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final Object[] internalArray() {
        return this.array;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final int internalArrayEnd() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final int internalArrayStart() {
        return 0;
    }

    @Override // com.google.common.collect.ImmutableCollection
    public final boolean isPartialView() {
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // com.google.common.collect.ImmutableList,
              // com.google.common.collect.ImmutableCollection
    public Object writeReplace() {
        return super.writeReplace();
    }
}
