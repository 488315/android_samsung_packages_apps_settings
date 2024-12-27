package com.google.protobuf;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.RandomAccess;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ProtobufArrayList extends AbstractProtobufList implements RandomAccess {
    public static final ProtobufArrayList EMPTY_LIST;
    public Object[] array;
    public int size;

    static {
        ProtobufArrayList protobufArrayList = new ProtobufArrayList(0, new Object[0]);
        EMPTY_LIST = protobufArrayList;
        protobufArrayList.isMutable = false;
    }

    public ProtobufArrayList(int i, Object[] objArr) {
        this.array = objArr;
        this.size = i;
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList,
              // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        ensureIsMutable();
        int i = this.size;
        Object[] objArr = this.array;
        if (i == objArr.length) {
            this.array = Arrays.copyOf(objArr, ((i * 3) / 2) + 1);
        }
        Object[] objArr2 = this.array;
        int i2 = this.size;
        this.size = i2 + 1;
        objArr2[i2] = obj;
        ((AbstractList) this).modCount++;
        return true;
    }

    public final void ensureIndexInRange$5(int i) {
        if (i < 0 || i >= this.size) {
            StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Index:", ", Size:");
            m.append(this.size);
            throw new IndexOutOfBoundsException(m.toString());
        }
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        ensureIndexInRange$5(i);
        return this.array[i];
    }

    @Override // com.google.protobuf.Internal.ProtobufList
    public final Internal.ProtobufList mutableCopyWithCapacity(int i) {
        if (i < this.size) {
            throw new IllegalArgumentException();
        }
        return new ProtobufArrayList(this.size, Arrays.copyOf(this.array, i));
    }

    @Override // com.google.protobuf.AbstractProtobufList, java.util.AbstractList, java.util.List
    public final Object remove(int i) {
        ensureIsMutable();
        ensureIndexInRange$5(i);
        Object[] objArr = this.array;
        Object obj = objArr[i];
        if (i < this.size - 1) {
            System.arraycopy(objArr, i + 1, objArr, i, (r2 - i) - 1);
        }
        this.size--;
        ((AbstractList) this).modCount++;
        return obj;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        ensureIsMutable();
        ensureIndexInRange$5(i);
        Object[] objArr = this.array;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        ((AbstractList) this).modCount++;
        return obj2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final int size() {
        return this.size;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        int i2;
        ensureIsMutable();
        if (i >= 0 && i <= (i2 = this.size)) {
            Object[] objArr = this.array;
            if (i2 < objArr.length) {
                System.arraycopy(objArr, i, objArr, i + 1, i2 - i);
            } else {
                Object[] objArr2 = new Object[((i2 * 3) / 2) + 1];
                System.arraycopy(objArr, 0, objArr2, 0, i);
                System.arraycopy(this.array, i, objArr2, i + 1, this.size - i);
                this.array = objArr2;
            }
            this.array[i] = obj;
            this.size++;
            ((AbstractList) this).modCount++;
            return;
        }
        StringBuilder m = ListPopupWindow$$ExternalSyntheticOutline0.m(i, "Index:", ", Size:");
        m.append(this.size);
        throw new IndexOutOfBoundsException(m.toString());
    }
}
